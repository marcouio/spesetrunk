package com.molinari.gestionespese.business;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class DriveQuickstartTest {

	@Test
	public void testExtractFileIdFromOpenUrl() {
		final String url = "https://drive.google.com/open?id=11UFiDjwwK2GBvvtVoD2kqYYzaN_65kLF";

		Assert.assertEquals("11UFiDjwwK2GBvvtVoD2kqYYzaN_65kLF", DriveQuickstart.extractFileId(url));
	}

	@Test
	public void testExtractFileIdFromFilePath() {
		final String url = "https://drive.google.com/file/d/ABCdef123_xyz/view?usp=sharing";

		Assert.assertEquals("ABCdef123_xyz", DriveQuickstart.extractFileId(url));
	}

	@Test
	public void testExtractFileIdFromDownloadUrl() {
		final String url = "https://drive.google.com/uc?export=download&id=ABCdef123_xyz";

		Assert.assertEquals("ABCdef123_xyz", DriveQuickstart.extractFileId(url));
	}

	@Test
	public void testExtractFileIdFromRawId() {
		Assert.assertEquals("11UFiDjwwK2GBvvtVoD2kqYYzaN_65kLF",
				DriveQuickstart.extractFileId("11UFiDjwwK2GBvvtVoD2kqYYzaN_65kLF"));
	}

	@Test
	public void testExtractFileIdReturnsNullForUnknownUrl() {
		Assert.assertNull(DriveQuickstart.extractFileId("https://example.com/foo"));
		Assert.assertNull(DriveQuickstart.extractFileId(null));
	}

	@Test
	public void testIsDriveUrl() {
		Assert.assertTrue(DriveQuickstart.isDriveUrl("https://drive.google.com/open?id=abc"));
		Assert.assertTrue(DriveQuickstart.isDriveUrl("https://docs.google.com/uc?id=abc"));
		Assert.assertFalse(DriveQuickstart.isDriveUrl("https://example.com"));
		Assert.assertFalse(DriveQuickstart.isDriveUrl(null));
	}

	@Test(expected = IOException.class)
	public void testDownloadFileFromUrlWithoutFileIdFails() throws IOException {
		DriveQuickstart.downloadFileFromUrl("database.sqlite.mv.db", "https://example.com/foo");
	}
}
