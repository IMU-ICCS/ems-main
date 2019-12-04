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
import eu.melodic.upperware.activemqtorest.entry.MqAdapterStatus;
import eu.melodic.upperware.activemqtorest.entry.ExtractedMetricsDescriptions;

@Service
public class MqAdapterStatusHolder {

	@Autowired
	private MqAdapterStatus mqAdapterStatus;

	@Autowired
	private MelodicConfiguration melodicConfiguration;

	private Cache<String, ExtractedMetricsDescriptions> metricDescriptionCache;

	public void increaseMsgCount() {
		mqAdapterStatus.setMsqCount(mqAdapterStatus.getMsqCount() + 1);
	}

	public void increaseErrorCount() {
		mqAdapterStatus.setErrorCount(mqAdapterStatus.getErrorCount() + 1);
	}

	public void addExtractedMetricDescription(String description, ExtractedMetricsDescriptions extractedMetricsDescriptions){
		metricDescriptionCache.put(description, extractedMetricsDescriptions);
	}

	public MqAdapterStatus getMqAdapterStatus() {
		Map<String, ExtractedMetricsDescriptions> metricDescriptions = Maps.newHashMap();
		metricDescriptionCache.iterator().forEachRemaining(s -> {
			metricDescriptions.put(s.getKey(), s.getValue());
		});
		mqAdapterStatus.setRecentExtractedMetricsDescriptions(metricDescriptions);
		return this.mqAdapterStatus;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
		cacheManager.init();
		ResourcePoolsBuilder heap = ResourcePoolsBuilder.heap(10);
		Expiry<Object, Object> objectObjectExpiry = Expirations.timeToLiveExpiration(Duration.of(melodicConfiguration.getMqRecentMetricsExpiryInterval(),
				TimeUnit.SECONDS));
		metricDescriptionCache = cacheManager.createCache("metricDescriptionCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(
				String.class, ExtractedMetricsDescriptions.class, heap).withExpiry(objectObjectExpiry).build());
	}
}
