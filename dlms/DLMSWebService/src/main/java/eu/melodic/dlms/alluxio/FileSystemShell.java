/*
 * Alluxio FileSystem Shell
 */

package eu.melodic.dlms.alluxio;

import alluxio.Constants;
import alluxio.PropertyKey;
import alluxio.cli.AbstractShell;
import alluxio.cli.Command;
import alluxio.client.file.FileSystem;
import alluxio.util.ConfigurationUtils;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;


/**
 * Class for handling FileSystemShell commands
 */
public final class FileSystemShell extends AbstractShell {
  private static final Logger LOG = LoggerFactory.getLogger(FileSystemShell.class);

  private static final Map<String, String[]> CMD_ALIAS = ImmutableMap.<String, String[]>builder()
      .put("lsr", new String[] {"ls", "-R"})
      .put("rmr", new String[] {"rm", "-R"})
      .build();

  /**
   * Main method, starts a new FileSystemShell.
   *
   * @param argv array of arguments given by the user's input from the terminal
   */
  public static void runCommand(String[] argv) throws IOException {
    int ret;

    if (!ConfigurationUtils.masterHostConfigured()) {
      System.out.println(String.format(
          "Cannot run alluxio shell; master hostname is not "
              + "configured. Please modify %s to either set %s or configure zookeeper with "
              + "%s=true and %s=[comma-separated zookeeper master addresses]",
          Constants.SITE_PROPERTIES, PropertyKey.MASTER_HOSTNAME.toString(),
          PropertyKey.ZOOKEEPER_ENABLED.toString(), PropertyKey.ZOOKEEPER_ADDRESS.toString()));
      System.exit(1);
    }

    try (FileSystemShell shell = new FileSystemShell()) {
      ret = shell.run(argv);
    }
  }

  /**
   * Creates a new instance of {@link FileSystemShell}.
   */
  public FileSystemShell() {
    super(CMD_ALIAS);
  }

  @Override
  protected String getShellName() {
    return "fs";
  }

  @Override
  protected Map<String, Command> loadCommands() {
    return FileSystemShellUtils.loadCommands(FileSystem.Factory.get());
  }
}
