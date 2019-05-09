package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.molinari.gestionespese.view.AlertMaker;
import com.molinari.gestionespese.view.impostazioni.AbstractCategorieView;

@RunWith(MockitoJUnitRunner.class)
public class AscoltatoreInserisciCategoriaTest {

	@Mock
	private AbstractCategorieView catView;
	
	@Spy
	private AscoltatoreInserisciCategoria ascoltaInsCat;

	@Mock
	private AlertMaker alertMaker;
	
	@Before
	public void setUp() {
		Mockito.when(ascoltaInsCat.getCategorieView()).thenReturn(catView);
		Mockito.when(ascoltaInsCat.getAlertMaker()).thenReturn(alertMaker);
	}
	
	@Test
	public void testFillInAll() {
		Mockito.when(ascoltaInsCat.getCategorieView().esistonoCampiNonValorizzati()).thenReturn(true);
		ascoltaInsCat.actionPerformedOverride(null);
		
		Mockito.verify(ascoltaInsCat).sendAlertFillInAll();
	}
	
	@Test
	public void testGroupExists() {
		Mockito.when(ascoltaInsCat.getCategorieView().categoryAlreadyExists()).thenReturn(true);
		ascoltaInsCat.actionPerformedOverride(null);
		
		Mockito.verify(ascoltaInsCat).sendAlertSameName();
	}
}
