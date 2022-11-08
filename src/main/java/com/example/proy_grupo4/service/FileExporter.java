package com.example.proy_grupo4.service;

import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public interface FileExporter {
	
	public Path export(String fileContent, String fileName);
	
}
