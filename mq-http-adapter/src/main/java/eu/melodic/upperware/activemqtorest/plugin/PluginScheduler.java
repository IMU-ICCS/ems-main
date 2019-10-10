package eu.melodic.upperware.activemqtorest.plugin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PluginScheduler {

	@Autowired
	List<IPlugin> pluginList;

	@Scheduled(fixedRateString = "${mq-adapter.plugins.rate:60000}")
	public void executePlugins() {
		for(IPlugin plugin : pluginList){
			log.debug("Using Plugin '{}'", plugin.getName());
			if(plugin.isReady()){
				log.debug("Executing..");
				plugin.execute();
			} else {
				log.debug("Plugin not ready.");
			}
		}
	}

}
