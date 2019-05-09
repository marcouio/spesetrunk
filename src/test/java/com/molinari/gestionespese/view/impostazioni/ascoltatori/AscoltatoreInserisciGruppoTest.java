package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.molinari.gestionespese.view.AlertMaker;
import com.molinari.gestionespese.view.impostazioni.AbstractGruppiView;

@RunWith(MockitoJUnitRunner.class)
public class AscoltatoreInserisciGruppoTest {

	@Mock
	private AbstractGruppiView gruppiView;
	
	@Spy
	private AscoltatoreInserisciGruppo ascoltaInsGrp;

	@Mock
	private AlertMaker alertMaker;
	
	@Before
	public void setUp() {
		Mockito.when(ascoltaInsGrp.getGruppiView()).thenReturn(gruppiView);
		Mockito.when(ascoltaInsGrp.getAlertMaker()).thenReturn(alertMaker);
	}
	
	@Test
	public void testFillInAll() {
		Mockito.when(ascoltaInsGrp.getGruppiView().esistonoCampiNonValorizzati()).thenReturn(true);
		ascoltaInsGrp.actionPerformedOverride(null);
		
		Mockito.verify(ascoltaInsGrp).sendAlertFillInAll();
	}
	
	@Test
	public void testGroupExists() {
		Mockito.when(ascoltaInsGrp.getGruppiView().groupAlreadyExists()).thenReturn(true);
		ascoltaInsGrp.actionPerformedOverride(null);
		
		Mockito.verify(ascoltaInsGrp).sendAlertSameName();
	}
}
