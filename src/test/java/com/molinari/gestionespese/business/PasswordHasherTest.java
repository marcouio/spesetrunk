package com.molinari.gestionespese.business;

import org.junit.Assert;
import org.junit.Test;

public class PasswordHasherTest {

	@Test
	public void testEncodeIsDeterministicAndPrefixed() {
		final String first = PasswordHasher.encode("secret");
		final String second = PasswordHasher.encode("secret");

		Assert.assertTrue(first.startsWith("sha256:"));
		Assert.assertEquals(first, second);
		Assert.assertNotEquals("secret", first);
	}

	@Test
	public void testMatchesEncodedPassword() {
		final String encoded = PasswordHasher.encode("secret");

		Assert.assertTrue(PasswordHasher.matches("secret", encoded));
		Assert.assertFalse(PasswordHasher.matches("wrong", encoded));
	}

	@Test
	public void testMatchesLegacyPlainPassword() {
		Assert.assertTrue(PasswordHasher.matches("guest", "guest"));
		Assert.assertFalse(PasswordHasher.matches("other", "guest"));
	}

	@Test
	public void testEncodeDoesNotHashTwice() {
		final String encoded = PasswordHasher.encode("secret");

		Assert.assertEquals(encoded, PasswordHasher.encode(encoded));
	}
}
