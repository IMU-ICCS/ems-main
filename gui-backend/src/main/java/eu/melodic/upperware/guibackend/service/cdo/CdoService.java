package eu.melodic.upperware.guibackend.service.cdo;

import camel.core.CamelModel;
import camel.core.NamedElement;
import camel.requirement.OptimisationRequirement;
import camel.requirement.RequirementModel;
import camel.requirement.impl.OptimisationRequirementImpl;
import eu.melodic.upperware.guibackend.controller.process.mapper.CpModelMapper;
import eu.melodic.upperware.guibackend.controller.process.response.CpModelResponse;
import eu.melodic.upperware.guibackend.controller.process.response.CpSolutionResponse;
import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.net4j.connector.ConnectorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CdoService {

    private final static String CAMEL_MODEL_LINE_PREFIX = "<core:CamelModel";
    private final static Pattern CAMEL_NAME_PATTERN = Pattern.compile("name=\"(.*?)\"");

    private CpModelMapper cpModelMapper;
    private GuiBackendProperties guiBackendProperties;

    public String getCdoName(File xmiFile) {
        String cdoName = null;
        try (Stream<String> stream = Files.lines(Paths.get(xmiFile.getAbsolutePath()))) {
            String camelModelLine = stream.filter(s -> s.startsWith(CAMEL_MODEL_LINE_PREFIX))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Your xmi model %s is invalid. Camel model part is missing.", xmiFile.getName())));
            Matcher matcher = CAMEL_NAME_PATTERN.matcher(camelModelLine);
            while (matcher.find()) {
                cdoName = matcher.group(1);
                log.info("Cdo name found: {}", cdoName);
            }
        } catch (IOException e) {
            log.error("Error by parsing xmi file: {}", e);
        }
        return cdoName;
    }

    public boolean storeFileInCdo(String cdoName, File file) {

        log.info("Storing Model {} into CDO with validationEnabled = {}", cdoName, guiBackendProperties.getCdoUploader().isValidationEnabled());
        EObject model = null;
        CDOClient client = getCdoClient();
        try {
            model = CDOClient.loadModel(file.getAbsolutePath());
        } catch (RuntimeException e) {
            client.closeSession();
            return false;
        }

        boolean successfullyStored = client.storeModel(model, cdoName, guiBackendProperties.getCdoUploader().isValidationEnabled());
        log.info("Successfully stored of model {} in CDO = {}", cdoName, successfullyStored);
        client.closeSession();
        return successfullyStored;
    }

    public boolean deleteXmi(String cdoName) {
        log.info("Deleting model {} from CDO", cdoName);
        CDOClient client = getCdoClient();
        CDOTransaction cdoTransaction = client.openTransaction();
        CDOResource cdoResource = cdoTransaction.getOrCreateResource(cdoName);
        EList<EObject> contents = cdoResource.getContents();
        if (contents.size() != 0) {
            log.info("CDO has {} resources for {} in CDO", contents.size(), cdoName);
            contents.remove(0);
            try {
                cdoTransaction.commit();
            } catch (CommitException e) {
                log.error("Error by commit transaction with deleting model", e);
                return false;
            } finally {
                cdoTransaction.close();
            }
            return true;
        } else {
            cdoTransaction.close();
            log.error("Such model doesn't exist in CDO");
            return false;
        }
    }

    public List<String> getAllXmi() {
        List<String> result = new ArrayList<>();
        CDOView cdoView = null;
        try {
            CDOClient client = getCdoClient();
            cdoView = client.openView();

            CDOQuery sql = cdoView.createQuery("sql", "select * from repo1.camel_core_camelmodel;");

            result = sql.getResult().stream()
                    .map(o -> (CamelModel) o)
                    .map(NamedElement::getName)
                    .collect(Collectors.toList());
        } catch (ConnectorException ex) {
            log.error("Error by getting uploaded models. CDO is not responding", ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error by getting uploaded models. CDO does not respond.");
        } catch (RuntimeException ex) {
            log.debug("List of available models is empty:", ex);
        } finally {
            if (cdoView != null) {
                cdoView.close();
            }
        }
        return result;
    }

    public CpModelResponse getCpModelResponse(String cpCdoPath, String applicationId) {
        String utilityFormula = getUtilityFormula(applicationId);
        CDOResource cdoResource = null;
        CDOView cdoView = null;
        try {
            CDOClient client = getCdoClient();
            cdoView = client.openView();
            cdoResource = cdoView.getResource(cpCdoPath);
            EList<EObject> contents = cdoResource.getContents();
            log.info("Get {} of cpModels", contents.size());
            ConstraintProblemImpl cpModel = (ConstraintProblemImpl) contents.get(contents.size() - 1);
            return cpModelMapper.mapConstraintProblemToCpModelResponse(cpModel, utilityFormula);
        } catch (RuntimeException ex) {
            log.error("Error by getting constraint problem. Table doesn't exist.", ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error by getting constraint problem. Required table doesn't exist.");
        } finally {
            if (cdoView != null) {
                cdoView.close();
            }
        }
    }

    public CpSolutionResponse getCpSolution(String cpCdoPath, Integer deployedSolutionId) {
        CDOResource cdoResource = null;
        CDOView cdoView = null;
        try {
            CDOClient client = getCdoClient();
            cdoView = client.openView();
            cdoResource = cdoView.getResource(cpCdoPath);
            EList<EObject> contents = cdoResource.getContents();
            log.info("Get {} of cpModels", contents.size());
            ConstraintProblemImpl cpModel = (ConstraintProblemImpl) contents.get(contents.size() - 1);
            if (deployedSolutionId != null) {
                log.info("Get solution with id: {}", deployedSolutionId);
                return cpModelMapper.mapSolutionToResponse(cpModel.getSolution().get(deployedSolutionId));
            } else {
                return cpModelMapper.mapSolutionToResponse(CPModelTool.searchLastSolution(cpModel.getSolution()));
            }
        } catch (RuntimeException ex) {
            log.error("Error by getting constraint problem solution. Table doesn't exist.", ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error by getting constraint problem. Required table doesn't exist.");
        } finally {
            if (cdoView != null) {
                cdoView.close();
            }
        }
    }

    private CDOClient getCdoClient() {
        CDOClient client = new CDOClient();
        registerPackages(client);
        return client;
    }

    private void registerPackages(CDOClient cdoClient) {
        cdoClient.registerPackage(CpPackage.eINSTANCE);
        cdoClient.registerPackage(TypesPackage.eINSTANCE);
    }

    private String getUtilityFormula(String applicationId) {
        CDOView cdoView = null;
        String utilityFormula = null;
        try {
            CDOClient client = getCdoClient();
            cdoView = client.openView();

            CDOQuery sql = cdoView.createQuery("sql", "select * from repo1.camel_core_camelmodel;");

            CamelModel camelModel = sql.getResult().stream()
                    .map(o -> (CamelModel) o)
                    .filter(camelModelTmp -> applicationId.equals(camelModelTmp.getName()))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Problem by getting camel model for application: %s", applicationId)));

            utilityFormula = getUtilityFormulaFromCamelModel(camelModel);
        } catch (Exception ex) {
            log.debug("Requested camel model doesn't exist");
        } finally {
            if (cdoView != null) {
                cdoView.close();
            }
        }
        return utilityFormula;
    }

    private String getUtilityFormulaFromCamelModel(CamelModel camelModel) {
        RequirementModel requirementModel = CdoTool.getFirstElement(camelModel.getRequirementModels());
        return requirementModel
                .getRequirements()
                .stream()
                .filter(r -> r instanceof OptimisationRequirementImpl)
                .findAny()
                .map(requirement -> ((OptimisationRequirement) requirement).getMetricVariable().getFormula())
                .orElse("default formula");
    }
}
