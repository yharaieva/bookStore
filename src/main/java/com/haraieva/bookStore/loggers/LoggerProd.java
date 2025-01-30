package com.haraieva.bookStore.loggers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Slf4j
public class LoggerProd implements Logger {
	public LoggerProd() {
		log.info("Creating a logger, prod profile");
	}
}
