package com.repetentia.component.utils;

import java.util.UUID;

public class FileUtils {
	public static String generate() {
		UUID uuid = UUIDUtils.generate();
		String msb = Long.toHexString(uuid.getMostSignificantBits());
		String lsb = Long.toHexString(uuid.getLeastSignificantBits());
		return msb + lsb;
	}
}
