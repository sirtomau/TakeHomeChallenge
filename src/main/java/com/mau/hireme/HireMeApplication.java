package com.mau.hireme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableCaching
// @EnableCaching is needed in order to let Spring know that we want to use caching
// In particular we will use it on top of the translation service (see PokemonTranslationService)
// to improve performance and to avoid overloading backend services, the data
// we retrieve from the backend services is pretty static anyway.
public class HireMeApplication {

	private Logger logger = LoggerFactory.getLogger(HireMeApplication.class);

	public static String hostName = null;
	public static LocalDateTime upSince = null;
	public static long translationServiceInvocations = 0;
	public static long pokeapiInvocations = 0;
	public static long translationapiInvocations = 0;

	@Autowired
	CacheManager cacheManager;

	public static void main(String[] args) {
		SpringApplication.run(HireMeApplication.class, args);
		initUsefulInfo();
	}

	@PostConstruct
	private void postStartLogs() {
		// For how the app is configured right now, the manager in use should be
		// the default org.springframework.cache.concurrent.ConcurrentMapCacheManager.
		// More sophisticated and configurable cache implementations can be used just doing
		// changes in the configuration (ie cache size, cache eviction policy, etc..),
		// but with no impact on the code.
		// TODO - replace caching implementation (and logic) if needed
		logger.info("Caching with "+cacheManager.getClass());
	}

	private static void initUsefulInfo() {
		try {
			upSince = LocalDateTime.now();
			hostName =  InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostName = "unknown";
		}
	}

}
