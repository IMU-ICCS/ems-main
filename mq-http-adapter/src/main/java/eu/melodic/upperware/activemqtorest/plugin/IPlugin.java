package eu.melodic.upperware.activemqtorest.plugin;

public interface IPlugin {
	public String getName();

	public void execute();

	public boolean isReady();
}
