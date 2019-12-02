package eu.melodic.upperware.activemqtorest.activemq;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.expiry.Expiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import eu.melodic.upperware.activemqtorest.MelodicConfiguration;
import eu.melodic.upperware.activemqtorest.entry.ActiveMqStatistics;

@Service
public class ActiveMqStatisticHolder {

	@Autowired
	private ActiveMqStatistics activeMqStatistics;

	@Autowired
	private MelodicConfiguration melodicConfiguration;

	private Cache<String, String> metricDescriptionCache;

	public void increaseMsgCount() {
		activeMqStatistics.setMsqCount(activeMqStatistics.getMsqCount() + 1);
	}

	public void increaseErrorCount() {
		activeMqStatistics.setErrorCount(activeMqStatistics.getErrorCount() + 1);
	}

	public void addExtractedMetricDescription(String description, String lastOccurrence){
		metricDescriptionCache.put(description, lastOccurrence);
	}

	public ActiveMqStatistics getActiveMqStatistics() {
		Map<String, String> metricDescriptions = Maps.newHashMap();
		metricDescriptionCache.iterator().forEachRemaining(s -> {
			metricDescriptions.put(s.getKey(), s.getValue());
		});
		activeMqStatistics.setRecentExtractedMetricsDescriptions(metricDescriptions);
		return this.activeMqStatistics;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
		cacheManager.init();
		ResourcePoolsBuilder heap = ResourcePoolsBuilder.heap(10);
		Expiry<Object, Object> objectObjectExpiry = Expirations.timeToLiveExpiration(Duration.of(melodicConfiguration.getMqRecentMetricsExpiryInterval(),
				TimeUnit.SECONDS));
		metricDescriptionCache = cacheManager.createCache("metricDescriptionCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(
				String.class, String.class, heap).withExpiry(objectObjectExpiry).build());
	}
}
