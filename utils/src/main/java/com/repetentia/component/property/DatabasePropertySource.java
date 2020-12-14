package com.repetentia.component.property;

import java.util.Map;
import java.util.Random;

import org.springframework.core.env.PropertySource;

public class DatabasePropertySource extends PropertySource<Map<String, Object>> {
	int num;
	String name;
	Map<String, Object> source;
	
	public DatabasePropertySource(String name, Map<String, Object> source) {
		
		super(name, source);
		this.name = name;
		this.source = source;
		this.num = new Random().nextInt();
	}

	@Override
	public Object getProperty(String name) {
		return source.get(name);
	}
}