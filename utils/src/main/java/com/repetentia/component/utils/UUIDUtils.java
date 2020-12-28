package com.repetentia.component.utils;

import java.util.UUID;

public class UUIDUtils {
	public static UUID generate() {
		final UUID uuid = UUID.randomUUID();
		return uuid;
	}
}
