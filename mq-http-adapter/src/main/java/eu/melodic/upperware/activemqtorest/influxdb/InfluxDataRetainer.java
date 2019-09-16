package eu.melodic.upperware.activemqtorest.influxdb;

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

import eu.melodic.upperware.activemqtorest.MelodicConfiguration;
import eu.melodic.upperware.activemqtorest.objects.MqInstanceInfoEntry;
import eu.melodic.upperware.activemqtorest.objects.MqThresholdEntry;

@Service
public class InfluxDataRetainer {

	private Cache<Integer, MqInstanceInfoEntry> instanceInfoEntryCache;
	private Cache<Integer, MqThresholdEntry> thresholdEntryCache;

	@Autowired
	private MelodicConfiguration melodicConfiguration;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
		cacheManager.init();
		ResourcePoolsBuilder heap = ResourcePoolsBuilder.heap(melodicConfiguration.getInfluxRetainerHeapEntries());
		Expiry<Object, Object> objectObjectExpiry = Expirations.timeToLiveExpiration(Duration.of(melodicConfiguration.getInfluxRetainerExpiry(),
				TimeUnit.SECONDS));
		instanceInfoEntryCache = cacheManager.createCache("instanceInfoEntryCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(
				Integer.class, MqInstanceInfoEntry.class, heap).withExpiry(objectObjectExpiry).build());
		thresholdEntryCache = cacheManager.createCache("thresholdEntryCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(
				Integer.class, MqThresholdEntry.class, heap).withExpiry(objectObjectExpiry).build());
	}

	public Cache<Integer, MqInstanceInfoEntry> getInstanceInfoEntryCache() {
		return instanceInfoEntryCache;
	}

	public Cache<Integer, MqThresholdEntry> getThresholdEntryCache() {
		return thresholdEntryCache;
	}
}