package com.trade.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreTradesApplication {
	private static final Logger LOGGER=LoggerFactory.getLogger(StoreTradesApplication.class);
	
	public static void main(String[] args) {
		LOGGER.info("Starting TradeStoore application....");
		SpringApplication.run(StoreTradesApplication.class, args);
	}

}
