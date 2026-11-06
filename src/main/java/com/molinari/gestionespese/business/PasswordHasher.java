package com.molinari.gestionespese.business;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordHasher {

	private static final String PREFIX = "sha256:";

	private PasswordHasher() {
		// do nothing
	}

	public static String encode(final String password) {
		if (password == null || password.startsWith(PREFIX)) {
			return password;
		}
		return hash(password);
	}

	public static boolean matches(final String rawPassword, final String storedPassword) {
		if (storedPassword == null) {
			return rawPassword == null;
		}
		if (storedPassword.startsWith(PREFIX)) {
			return storedPassword.equals(hash(rawPassword));
		}
		return storedPassword.equals(rawPassword);
	}

	private static String hash(final String password) {
		if (password == null) {
			return null;
		}
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] encoded = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			final StringBuilder sb = new StringBuilder(PREFIX);
			for (final byte b : encoded) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 non disponibile", e);
		}
	}
}
