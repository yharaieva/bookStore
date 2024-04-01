package com.haraieva.bookStore.fileStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Profile("dev")
@Slf4j
public class FileStorageDev implements FileStorage {
	private String fileDirectory = "/Users/yharaieva/Pictures";

	@Override
	public void saveFile(byte[] data) {
		log.info("Saving a file, dev profile");
		// тут еще название файлика нужно
		Path path = Paths.get(fileDirectory);
		try {
			Files.write(path, data);
		} catch (IOException e) {
			log.error("There was an error saving a file for dev environment ", e);
			e.printStackTrace();
		}
	}

	@Override
	public File getFile(String url) {
		log.info("Getting a file, dev profile");
		return Paths.get(URI.create(url)).toFile();
	}
}
