package com.haraieva.bookStore.loggers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Slf4j
public class LoggerDev implements Logger {

	public LoggerDev() {
		log.info("Creating a logger, dev profile");
	}
}
