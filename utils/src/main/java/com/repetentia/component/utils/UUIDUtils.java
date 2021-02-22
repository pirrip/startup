package com.repetentia.component.utils;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

public class UUIDUtils {
	public static UUID generateUUID() {
		final UUID uuid = UUID.randomUUID();
		return uuid;
	}
	
	public static String generateDefault() {
		return generateUUID().toString();
	}
	
	public static String generate() {
		UUID uuid = generateUUID();
		long lsb = uuid.getLeastSignificantBits();
		long msb = uuid.getMostSignificantBits();
		String [] sb = {Long.toHexString(msb), Long.toHexString(lsb)};
		return  String.join("", sb);
	}	

	public static long sqno() {
		UUID uuid = generateUUID();
		long msb = uuid.getMostSignificantBits();
		String hex = Long.toHexString(msb);
		
		return RandomUtils.nextLong(0, Long.MAX_VALUE);
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10000; i ++) {
			System.out.println(sqno());
		}
	}
}
