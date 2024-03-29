package com.haraieva.bookStore.fileStorage;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public interface FileStorage {

	void saveFile(byte[] data);

	File getFile(String url);
}
