package com.destiny.squirrel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

@Slf4j
public class SquirrelImportSelector implements ImportSelector {
	
	@Override
	public String[] selectImports(AnnotationMetadata metadata) {
		return new String[]{SquirrelConfig.class.getName()};
	}
	
	
}
