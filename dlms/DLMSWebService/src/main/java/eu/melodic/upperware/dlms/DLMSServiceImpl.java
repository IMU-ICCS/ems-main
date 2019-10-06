/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Implementation
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import alluxio.AlluxioURI;
import alluxio.Constants;
import alluxio.cli.fs.command.*;
import alluxio.client.file.FileSystem;
import alluxio.client.file.FileSystemContext;
import alluxio.conf.InstancedConfiguration;
import alluxio.conf.PropertyKey;
import alluxio.exception.AlluxioException;
import alluxio.grpc.MountPOptions;
import alluxio.util.ConfigurationUtils;
import eu.melodic.upperware.dlms.exception.*;
import eu.melodic.upperware.dlms.properties.DLMSDataSourceAccess;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Implementation of DLMSService.
 */
@Service
@Slf4j
@AllArgsConstructor
public class DLMSServiceImpl implements DLMSService {

	private final DataSourceRepository dsRepository;
	private final DLMSDataSourceAccess dlmsDsAccess;
	private final AppCompDataSourceRepository acDsRepository;
	private final AcDsMountPointRepository acDsMpRepository;

	private final InstancedConfiguration conf;

	@Override
	public DataSource getDataSourceById(long id) {
		ensureIdParameterNotNegative(id);
		ensureConfiguration();

		return dsRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
	}

	@Override
	public DataSource getDataSourceByName(String name) {
		ensureConfiguration();

		checkName(name);
		return dsRepository.findByName(name);
	}

	@Override
	public boolean hasDataSourceByName(String name) {
		ensureConfiguration();

		return dsRepository.existsByName(name);
	}

	@Override
	public List<DataSource> getAllDataSources() {
		return dsRepository.findAll();
	}
	
	@Override
	public List<AcDsMountPoint> getAllAcDsMp() {
		return acDsMpRepository.findAll();
	}
	
	@Override
	public AcDsMountPoint getAcDsMpByName(String name) {
		ensureConfiguration();

		checkAcName(name);
		return acDsMpRepository.findByAcName(name);
	}
	
	@Override
	public String getAlluxioCmd(String cmpName) {
		ensureConfiguration();
		log.info("Checking Configuration for {}... OK", cmpName);
		checkAcName(cmpName);
        log.info("Checking AcName for {}... OK", cmpName);
		AcDsMountPoint mp= acDsMpRepository.findByAcName(cmpName);
	
		String localMountPoint = mp.getToLocalMountPoint();
		// create directory first
		return String.format("mkdir -p %s && alluxio/integration/fuse/bin/alluxio-fuse mount %s /%s", localMountPoint, localMountPoint, mp.getMountPoint());
	}

	@Override
	public void calculateAcDsMp() {
		List<AppCompDataSource> acDsList = acDsRepository.findAll();
		List<DataSource> dsList = dsRepository.findAll();

		List<AcDsMountPoint> acDsMountPointList = combineAcDsMountPoint(acDsList, dsList);
		if (acDsMountPointList.size() > 0) {
			acDsMpRepository.saveAll(acDsMountPointList);
		} else {
			log.debug("There are 0 mount points");
		}
	}

	private List<AcDsMountPoint> combineAcDsMountPoint(List<AppCompDataSource> acDsList, List<DataSource> dsList) {
		List<AcDsMountPoint> acDsMountPointList = new ArrayList<>();
		for (AppCompDataSource acDs : acDsList) {
			String dsName = acDs.getDataSource();
			String acName = acDs.getName();

			final Optional<DataSource> dataSource = matchingDs(dsName, dsList);
			if (dataSource.isPresent()) {
				final DataSource ds = dataSource.get();
				AcDsMountPoint acDsMountPoint = new AcDsMountPoint(acName, dsName, ds.getMountPoint(), ds.getLocalMountPont());
				acDsMountPointList.add(acDsMountPoint);
			} else {
				log.error("The datasource {} did not have a mount point", dsName);
			}
		}
		return acDsMountPointList;

	}

	private Optional<DataSource> matchingDs(String dsName, List<DataSource> dsList) {

		return dsList.stream()
				.filter(ds -> ds.getName().equals(dsName))
				.findAny();
	}

	@Override
	public void deleteById(long id) {
		ensureIdParameterNotNegative(id);
		ensureConfiguration();

		checkId(id);
		ensureUnmount(id);
		dsRepository.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		ensureConfiguration();

		checkName(name);
		ensureUnmount(name);
		dsRepository.deleteByName(name);
	}

	@Override
	public void addDataSource(DataSource ds) {
		ensureConfiguration();
        log.info("Checking Configuration for DataSource: {}... OK", ds.getName());
		ensureDataSourceNameIsUnused(ds);
        log.info("Checking DataSourceNameIsUnused for DataSource: {}... OK", ds.getName());
		ensureMountPoint(ds);
        log.info("Checking MountPoint for DataSource: {}... OK", ds.getName());

        dsRepository.save(ds);
	}

	@Override
	public void updateDataSource(DataSource ds, long id) {
		ensureIdParameterNotNegative(id);
		ensureConfiguration();

		checkId(id);
		ensureUnmount(id);
		ensureMountPoint(ds);

		ds.setId(id);
		dsRepository.save(ds);
	}

	@Override
	public void migrateFile(String pathFrom, String pathTo) {
		ensureConfiguration();

		runCopy(pathFrom, pathTo);
		runRemove(pathFrom);
		runPersist(pathTo);
	}

	@Override
	public void migrateDirectory(String pathFrom, String pathTo) {
		ensureConfiguration();

		runCopy("-R", pathFrom, pathTo);
		runRemove("-R", pathFrom);
		runPersist(pathTo);
	}

	@Override
	public void migrateDatasource(long id, String pathTo) {
		ensureConfiguration();

		checkId(id);
		DataSource ds = dsRepository.findById(id).get();

		String result = runCopyCommand("-R", ds.getMountPoint(), pathTo);
		if (!result.isEmpty()) {
			throw new CopyException(result);
		}
		runUnmount(ds);
		runPersist(pathTo);

		dsRepository.delete(ds);
	}

	private void ensureUnmount(long id) {
		DataSource ds = dsRepository.getOne(id);
		runUnmount(ds);
	}

	private void ensureUnmount(String name) {
		DataSource ds = dsRepository.findByName(name);
		runUnmount(ds);
	}

	private void ensureDataSourceNameIsUnused(DataSource ds) {
		DataSource findMe = new DataSource(ds.getName(), null, null, null, null);
		Example<DataSource> example = Example.of(findMe);
		if (dsRepository.findOne(example).isPresent()) {
			throw new CreateDatasourceException("Datasource with this name already exists");
		}
	}

	private void ensureMountPoint(DataSource ds) {
	    try {
            // check if key is required
            if (StringUtils.isEmpty(ds.getAccessKey())) {
                log.info("Mounting datasource {} without accessKey", ds.getName());
                runMountCommand("/" + ds.getMountPoint(), ds.getUfsURI(), ds.isReadOnly());
            } else {
                log.info("Mounting datasource {} with accessKey", ds.getName());
                // key is required, so get access information
                List<String> userInfo = dlmsDsAccess.getDataSource().getAccountMap().get(ds.getAccessKey());
                // does both access key and secret key exists
                if (CollectionUtils.isNotEmpty(userInfo) && userInfo.size() == 2) {
                    String accessKeyId = userInfo.get(0);
                    String secretKey = userInfo.get(1);
                    runMountCommand("/" + ds.getMountPoint(), ds.getUfsURI(), ds.isReadOnly(), accessKeyId, secretKey);
                } else {
					throw new CreateDatasourceException("User account does not have accessKey/secretKey");
				}
            }
        } catch (AlluxioException | IOException e) {
            throw new CreateDatasourceException("Create Datasource " + ds.getMountPoint() + " failed");
        }
	}

	/**
	 * Runs the Alluxio LS command. Returns an empty String on success or the error
	 * message from Alluxio if anything went wrong.
	 */
	protected String runLsCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		try {
			LsCommand lsCommand = new LsCommand(FileSystemContext.create(conf));
			CommandLine commandLine = lsCommand.parseAndValidateArgs(args);
			log.info("Running LS command with parameter(s): {}", Arrays.toString(args));

			lsCommand.run(commandLine);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	/**
	 * Runs the Alluxio MKDIR command. Returns an empty String on success or the
	 * error message from Alluxio if anything went wrong.
	 */
	protected String runMkDirCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		try {
			MkdirCommand mkdirCommand = new MkdirCommand(FileSystemContext.create(conf));
			CommandLine commandLine = mkdirCommand.parseAndValidateArgs(args);
			log.info("Running MKDIR command with parameter(s): {}", Arrays.toString(args));

			mkdirCommand.run(commandLine);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	/**
	 * Runs the Alluxio MOUNT command without authentication
	 */
	protected void runMountCommand(String alluxioPath, String ufsPath, boolean isReadOnly) throws IOException, AlluxioException {
		runMountCommand(alluxioPath, ufsPath, isReadOnly, null, null);
	}

	/**
	 * Runs the Alluxio MOUNT command. Returns an empty String on success or the
	 * error message from Alluxio if anything went wrong.
	 */
	protected void runMountCommand(String alluxPath, String ufPath, boolean isReadOnly, String accessKeyId,
			String secretKey) throws IOException, AlluxioException {
		AlluxioURI alluxioPath = new AlluxioURI(alluxPath);
		AlluxioURI ufsPath = new AlluxioURI(ufPath);
		MountPOptions mountOption = MountPOptions.getDefaultInstance();

		MountPOptions.Builder mountBuilder = mountOption.toBuilder();
		mountBuilder.setReadOnly(isReadOnly);
		mountBuilder.setShared(true);

		// different access information based on cloud provider need to be set up here.
        // following is for aws
        if (StringUtils.isNotBlank(accessKeyId) && StringUtils.isNotBlank(secretKey)) {
            Map<String, String> authentication = new HashMap<>();
            authentication.put("aws.accessKeyId", accessKeyId);
			authentication.put("aws.secretKey", secretKey);

			mountBuilder.putAllProperties(authentication);
		}
		mountOption = mountBuilder.build();
        log.info("Running MOUNT command with parameter(s): alluxPath: {}, ufsPath: {}, and isReadOnly: {}", alluxPath, ufPath, isReadOnly);
        FileSystem mFileSystem = FileSystem.Factory.create(conf);
        mFileSystem.mount(alluxioPath, ufsPath, mountOption);
	}

	/**
	 * Runs the Alluxio UNMOUNT command. Returns an empty String on success or the
	 * error message from Alluxio if anything went wrong.
	 */
	protected String runUnmountCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		try {
			UnmountCommand unmountCommand = new UnmountCommand(FileSystemContext.create(conf));
			CommandLine commandLine = unmountCommand.parseAndValidateArgs(args);
			log.info("Running UNMOUNT command with parameter(s): {}", Arrays.toString(args));

			unmountCommand.run(commandLine);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	/**
	 * Runs the Alluxio MV (move) command. Returns an empty String on success or the
	 * error message from Alluxio if anything went wrong.
	 */
	protected String runMoveCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		try {
			MvCommand mvCommand = new MvCommand(FileSystemContext.create(conf));
			CommandLine commandLine = mvCommand.parseAndValidateArgs(args);
			log.info("Running MOVE command with parameter(s): {}", Arrays.toString(args));

			mvCommand.run(commandLine);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	/**
	 * Runs the Alluxio CP (copy) command. Returns an empty String on success or the
	 * error message from Alluxio if anything went wrong.
	 */
	protected String runCopyCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		try {
			CpCommand cpCommand = new CpCommand(FileSystemContext.create(conf));
			CommandLine commandLine = cpCommand.parseAndValidateArgs(args);
			log.info("Running COPY command with parameter(s): {}", Arrays.toString(args));

			cpCommand.run(commandLine);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	/**
	 * Runs the Alluxio RM (remove) command. Returns an empty String on success or
	 * the error message from Alluxio if anything went wrong.
	 */
	protected String runRemoveCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		try {
			RmCommand rmCommand = new RmCommand(FileSystemContext.create(conf));
			CommandLine commandLine = rmCommand.parseAndValidateArgs(args);
			log.info("Running REMOVE command with parameter(s): {}", Arrays.toString(args));

			rmCommand.run(commandLine);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	/**
	 * Runs the Alluxio PERSIST command. Returns an empty String on success or the
	 * error message from Alluxio if anything went wrong.
	 */
	protected String runPersistCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		try {
			PersistCommand persistCommand = new PersistCommand(FileSystemContext.create(conf));
			CommandLine commandLine = persistCommand.parseAndValidateArgs(args);
			log.info("Running PERSIST command with parameter(s): {}", Arrays.toString(args));

			persistCommand.run(commandLine);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	private void ensureConfiguration() {
		if (!ConfigurationUtils.masterHostConfigured(conf)) {
			log.error(String.format(
					"Cannot run alluxio shell; master hostname is not "
							+ "configured. Please modify %s to either set %s or configure zookeeper with "
							+ "%s=true and %s=[comma-separated zookeeper master addresses]",
					Constants.SITE_PROPERTIES, PropertyKey.MASTER_HOSTNAME.toString(),
					PropertyKey.ZOOKEEPER_ENABLED.toString(), PropertyKey.ZOOKEEPER_ADDRESS.toString()));
			System.exit(1);
		}
		log.info("Master host configuration OK");
	}

	private void ensureIdParameterNotNegative(long id) {
		if (id < 0) {
			throw new InvalidParameterException("Parameter must be >= 0");
		}
	}

	private void ensureCommandParameterNotEmpty(String[] args) {
		if (args == null || args.length == 0) {
			throw new InvalidParameterException("Parameter must not be empty");
		}
	}

	@Override
	public void updateDataSource(DataSource ds, String name) {
		ensureConfiguration();

		checkName(name);
		ensureUnmount(name);
		ensureMountPoint(ds);

		// get old data source and update it
		DataSource dsOrig = dsRepository.findByName(name);
		ds.setId(dsOrig.getId());

		dsRepository.save(ds);
	}

	private void runUnmount(DataSource ds) {
		String result = runUnmountCommand(ds.getMountPoint());
		if (!result.isEmpty())
			throw new UnmountException("Unmount failed: " + result);

	}

	private void runPersist(String pathTo) {
		String result = runPersistCommand(pathTo);
		if (!result.isEmpty())
			throw new PersistException(result);

	}

	private void runRemove(String... args) {
		String result = runRemoveCommand(args);
		if (!result.isEmpty())
			throw new RemoveException(result);

	}

	private void runCopy(String... args) {
		String result = runCopyCommand(args);
		if (!result.isEmpty())
			throw new CopyException(result);

	}

	private void checkId(long id) {
		if (!dsRepository.existsById(id))
			throw new IdNotFoundException(id);

	}

	private void checkName(String name) {
		if (!dsRepository.existsByName(name))
			throw new NameNotFoundException(name);
	}
	
	private void checkAcName(String name) {
		if (!acDsMpRepository.existsByAcName(name))
			throw new AcNameNotFoundException(name);
	}





}