/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Implementation
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import alluxio.AlluxioConfiguration;
import alluxio.AlluxioURI;
import alluxio.Configuration;
import alluxio.Constants;
import alluxio.PropertyKey;
import alluxio.cli.fs.command.CpCommand;
import alluxio.cli.fs.command.LsCommand;
import alluxio.cli.fs.command.MkdirCommand;
import alluxio.cli.fs.command.MountCommand;
import alluxio.cli.fs.command.MvCommand;
import alluxio.cli.fs.command.PersistCommand;
import alluxio.cli.fs.command.RmCommand;
import alluxio.cli.fs.command.UnmountCommand;
import alluxio.client.file.FileSystem;
import alluxio.client.file.options.MountOptions;
import alluxio.conf.InstancedConfiguration;
import alluxio.conf.Source;
import alluxio.exception.AlluxioException;
import alluxio.util.ConfigurationUtils;
import eu.melodic.upperware.dlms.exception.CopyException;
import eu.melodic.upperware.dlms.exception.CreateDatasourceException;
import eu.melodic.upperware.dlms.exception.IdNotFoundException;
import eu.melodic.upperware.dlms.exception.InvalidParameterException;
import eu.melodic.upperware.dlms.exception.NameNotFoundException;
import eu.melodic.upperware.dlms.exception.PersistException;
import eu.melodic.upperware.dlms.exception.RemoveException;
import eu.melodic.upperware.dlms.exception.UnmountException;
import eu.melodic.upperware.dlms.properties.DLMSDataSourceAccess;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of DLMSService.
 */
@Service("dlmsService")
@Slf4j
@AllArgsConstructor
public class DLMSServiceImpl implements DLMSService {

	private final DataSourceRepository dsRepository;
	private final DLMSDataSourceAccess dlmsDsAccess;

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
	public URI addDataSource(DataSource ds) {
		ensureConfiguration();

		ensureDataSourceNameIsUnused(ds);
		ensureMountPoint(ds);

		DataSource newDataSource = dsRepository.save(ds);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newDataSource.getName()).toUri();
		return location;
	}

	@Override
	public DataSource updateDataSource(DataSource ds, long id) {
		ensureIdParameterNotNegative(id);
		ensureConfiguration();

		checkId(id);
		ensureUnmount(id);
		ensureMountPoint(ds);

		ds.setId(id);
		dsRepository.save(ds);

		return null;
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
		DataSource findMe = new DataSource(ds.getName(), null, null, null);
		Example<DataSource> example = Example.of(findMe);
		if (dsRepository.findOne(example).isPresent()) {
			throw new CreateDatasourceException("Datasource with this name already exists");
		}
	}

	private void ensureMountPoint(DataSource ds) {
		String result;
		// check if key is required
		if (StringUtils.isEmpty(ds.getAccessKey())) {
			result = runMountCommand("/" + ds.getName(), ds.getUfsURI());
		} else {
			//key is required, so get access information
			List<String> userInfo = dlmsDsAccess.getDataSource().getAccountMap().get(ds.getAccessKey());
			// does both access key and secret key exists
			if (userInfo.size() > 1) {
				String accessKeyId = userInfo.get(0);
				String secretKey = userInfo.get(1);
				result = runMountCommand("/" + ds.getName(), ds.getUfsURI(), accessKeyId, secretKey);
			} else {
				log.debug("User account does not have accessKey/secreKey");

				result = null;
			}
		}

		if (!result.isEmpty() && !result.endsWith(" already exists")) {
			throw new CreateDatasourceException("Create Datasource " + ds.getName() + " failed: " + result);
		}
	}

	/**
	 * Runs the Alluxio LS command. Returns an empty String on success or the error
	 * message from Alluxio if anything went wrong.
	 */
	protected String runLsCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		try {
			LsCommand lsCommand = new LsCommand(FileSystem.Factory.get());
			CommandLine commandLine = lsCommand.parseAndValidateArgs(args);
			log.info("Running LS command with parameter(s): " + Arrays.toString(args));

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
			MkdirCommand mkdirCommand = new MkdirCommand(FileSystem.Factory.get());
			CommandLine commandLine = mkdirCommand.parseAndValidateArgs(args);
			log.info("Running MKDIR command with parameter(s): " + Arrays.toString(args));

			mkdirCommand.run(commandLine);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	/**
	 * Runs the Alluxio MOUNT command. Returns an empty String on success or the
	 * error message from Alluxio if anything went wrong.
	 */
	protected String runMountCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		AlluxioURI alluxioPath = new AlluxioURI(args[0]);
		AlluxioURI ufsPath = new AlluxioURI(args[1]);
		MountOptions mountOption = MountOptions.defaults();
		mountOption.setReadOnly(true);
		mountOption.setShared(true);
		// if access key id and secret key are passed along with the arguments
		if (args.length == 4) {
			Map<String, String> authentication = new HashMap<>();
			// different access information based on cloud provider need to be set up here. 
			// following is for aws
			authentication.put("aws.accessKeyId", args[2]);
			authentication.put("aws.secretKey", args[3]);

			mountOption.setProperties(authentication);
		}
		FileSystem mFileSystem = (FileSystem.Factory.get());
		try {
			log.info("Running MOUNT command with parameter(s): " + Arrays.toString(args));
			mFileSystem.mount(alluxioPath, ufsPath, mountOption);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	/**
	 * Runs the Alluxio UNMOUNT command. Returns an empty String on success or the
	 * error message from Alluxio if anything went wrong.
	 */
	protected String runUnmountCommand(String... args) {
		ensureCommandParameterNotEmpty(args);

		try {
			UnmountCommand unmountCommand = new UnmountCommand(FileSystem.Factory.get());
			CommandLine commandLine = unmountCommand.parseAndValidateArgs(args);
			log.info("Running UNMOUNT command with parameter(s): " + Arrays.toString(args));

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
			MvCommand mvCommand = new MvCommand(FileSystem.Factory.get());
			CommandLine commandLine = mvCommand.parseAndValidateArgs(args);
			log.info("Running MOVE command with parameter(s): " + Arrays.toString(args));

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
			CpCommand cpCommand = new CpCommand(FileSystem.Factory.get());
			CommandLine commandLine = cpCommand.parseAndValidateArgs(args);
			log.info("Running COPY command with parameter(s): " + Arrays.toString(args));

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
			RmCommand rmCommand = new RmCommand(FileSystem.Factory.get());
			CommandLine commandLine = rmCommand.parseAndValidateArgs(args);
			log.info("Running REMOVE command with parameter(s): " + Arrays.toString(args));

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
			PersistCommand persistCommand = new PersistCommand(FileSystem.Factory.get());
			CommandLine commandLine = persistCommand.parseAndValidateArgs(args);
			log.info("Running PERSIST command with parameter(s): " + Arrays.toString(args));

			persistCommand.run(commandLine);
			return "";
		} catch (IOException | AlluxioException e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
	}

	private void ensureConfiguration() {
		if (!ConfigurationUtils.masterHostConfigured()) {
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
	public DataSource updateDataSource(DataSource ds, String name) {
		ensureConfiguration();

		checkName(name);
		ensureUnmount(name);
		ensureMountPoint(ds);

		// get old data source and update it
		DataSource dsOrig = dsRepository.findByName(name);
		ds.setId(dsOrig.getId());

		dsRepository.save(ds);
		return null;
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

}