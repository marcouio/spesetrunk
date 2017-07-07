package com.molinari.gestionespese.view.entrateuscite;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.Utenti;


public class UsciteViewTest {

	@Test
	public void testNonEsistonoCampiNonValorizzati() {
		UsciteView spy = Mockito.spy(UsciteView.class);
		
		boolean nonEsistonoCampiNonValorizzati = spy.nonEsistonoCampiNonValorizzati();
		Assert.assertEquals(false, nonEsistonoCampiNonValorizzati);
		
		
		spy.setCategoria(new CatSpese());
		nonEsistonoCampiNonValorizzati = spy.nonEsistonoCampiNonValorizzati();
		Assert.assertEquals(false, nonEsistonoCampiNonValorizzati);
		
		spy.setcData("12/12/2000");
		nonEsistonoCampiNonValorizzati = spy.nonEsistonoCampiNonValorizzati();
		Assert.assertEquals(false, nonEsistonoCampiNonValorizzati);
		
		spy.setcDescrizione("desc");
		nonEsistonoCampiNonValorizzati = spy.nonEsistonoCampiNonValorizzati();
		Assert.assertEquals(false, nonEsistonoCampiNonValorizzati);
		
		spy.setcNome("nome");
		nonEsistonoCampiNonValorizzati = spy.nonEsistonoCampiNonValorizzati();
		Assert.assertEquals(false, nonEsistonoCampiNonValorizzati);
		
		spy.setDataIns("12/12/2000");
		nonEsistonoCampiNonValorizzati = spy.nonEsistonoCampiNonValorizzati();
		Assert.assertEquals(false, nonEsistonoCampiNonValorizzati);
		
		spy.setdEuro(234.0);
		nonEsistonoCampiNonValorizzati = spy.nonEsistonoCampiNonValorizzati();
		Assert.assertEquals(false, nonEsistonoCampiNonValorizzati);
		
		spy.setUtenti(new Utenti());
		nonEsistonoCampiNonValorizzati = spy.nonEsistonoCampiNonValorizzati();
		Assert.assertEquals(true, nonEsistonoCampiNonValorizzati);
	}

}
