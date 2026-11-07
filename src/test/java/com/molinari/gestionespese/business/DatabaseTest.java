package com.molinari.gestionespese.business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {

	private static final String[] TABELLE = { "utenti", "gruppi", "lookAndFeel", "risparmio", "cat_spese", "budget",
			"entrate", "single_spesa", "note" };

	@Test
	public void queryCreazioneTabelleFunzionanoSuH2Oracle() throws Exception {
		final String url = "jdbc:h2:mem:gestionespese_" + System.nanoTime() + ";MODE=ORACLE";

		try (Connection cn = DriverManager.getConnection(url, "sa", ""); Statement st = cn.createStatement()) {
			for (final String sql : Database.queryCreazioneTabelle()) {
				st.executeUpdate(sql);
			}

			for (final String tabella : TABELLE) {
				try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + tabella)) {
					Assert.assertTrue("Tabella non creata: " + tabella, rs.next());
				}
			}

			st.executeUpdate(
					"INSERT INTO lookAndFeel (nome, valore, usato) VALUES ('Nimbus', 'x', 1), ('Metal', 'y', 0)");
			try (ResultSet rs = st.executeQuery("SELECT idLook FROM lookAndFeel ORDER BY idLook")) {
				Assert.assertTrue(rs.next());
				Assert.assertEquals(1, rs.getInt(1));
				Assert.assertTrue(rs.next());
				Assert.assertEquals(2, rs.getInt(1));
				Assert.assertFalse(rs.next());
			}
		}
	}
}
