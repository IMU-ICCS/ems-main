package eu.melodic.upperware.activemqtorest.plugin;

public interface IPlugin {
	String getName();

	void execute();

	boolean isReady();
}
