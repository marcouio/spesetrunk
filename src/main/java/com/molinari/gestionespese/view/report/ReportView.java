package com.molinari.gestionespese.view.report;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.molinari.gestionespese.business.DBUtil;
import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.internazionalizzazione.I18NManager;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.CheckBoxF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;

import controller.ControlloreBase;
import grafica.componenti.alert.Alert;
import grafica.componenti.contenitori.PannelloBase;

public class ReportView extends AbstractReportView implements Finestra {


	/**
	 * Create the panel
	 *
	 * @throws FileNotFoundException
	 */
	public ReportView(Container cont) throws FileNotFoundException {
		super(cont);
		setReportData(new ReportData());
		PannelloBase padre = (PannelloBase) ((PannelloBase)getContainer()).getContenitorePadre();
		getContainer().setLayout(null);
		getContainer().setSize(padre.getWidth(), padre.getHeight());;
		final JLabel istruzioni = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("selectreport"));
		istruzioni.setText(I18NManager.getSingleton().getMessaggio("select") + ":");
		istruzioni.setBounds(12, 12, 207, 20);
		getContainer().add(istruzioni);

		final JCheckBox chckbxEntrateAnnuali = new CheckBoxF(I18NManager.getSingleton().getMessaggio("yearincome"));
		chckbxEntrateAnnuali.setBounds(22, 40, 197, 23);
		getContainer().add(chckbxEntrateAnnuali);

		final JCheckBox chckbxUsciteAnnuali = new CheckBoxF(I18NManager.getSingleton().getMessaggio("yearoutcome"));
		chckbxUsciteAnnuali.setBounds(22, 67, 197, 23);
		getContainer().add(chckbxUsciteAnnuali);

		final JCheckBox chckbxEntrateMensili = new CheckBoxF(I18NManager.getSingleton().getMessaggio("monthlyincome"));
		chckbxEntrateMensili.setBounds(22, 94, 197, 23);
		getContainer().add(chckbxEntrateMensili);

		final JCheckBox chckbxUsciteMensili = new CheckBoxF(I18NManager.getSingleton().getMessaggio("monthlyoutcome"));
		chckbxUsciteMensili.setBounds(22, 121, 197, 23);
		getContainer().add(chckbxUsciteMensili);

		final JCheckBox chckbxSpesePerCategorie = new CheckBoxF(I18NManager.getSingleton().getMessaggio("catspeseyear"));
		chckbxSpesePerCategorie.setBounds(22, 148, 197, 23);
		getContainer().add(chckbxSpesePerCategorie);

		final JCheckBox chckbxEntratePerCategorie = new CheckBoxF(I18NManager.getSingleton().getMessaggio(
				"catentrateyear"));
		chckbxEntratePerCategorie.setBounds(22, 175, 197, 23);
		getContainer().add(chckbxEntratePerCategorie);

		final JCheckBox chckbxSpeseMensCat = new CheckBoxF(I18NManager.getSingleton().getMessaggio("catspesemonth"));
		chckbxSpeseMensCat.setBounds(22, 229, 197, 23);
		getContainer().add(chckbxSpeseMensCat);

		final JCheckBox chckbxEntrateMensCategorie = new CheckBoxF(I18NManager.getSingleton().getMessaggio(
				"catentratemonth"));
		chckbxEntrateMensCategorie.setBounds(22, 202, 197, 23);
		getContainer().add(chckbxEntrateMensCategorie);

		final JCheckBox chckbxSpeseVariabili1 = new CheckBoxF("% "
				+ I18NManager.getSingleton().getMessaggio("spesevar"));
		chckbxSpeseVariabili1.setBounds(22, 255, 197, 23);
		getContainer().add(chckbxSpeseVariabili1);

		final JCheckBox chckbxSpeseFutili1 = new CheckBoxF("% " + I18NManager.getSingleton().getMessaggio("spesefut"));
		chckbxSpeseFutili1.setBounds(22, 282, 197, 23);
		getContainer().add(chckbxSpeseFutili1);

		final JCheckBox chckbxMedie = new CheckBoxF(I18NManager.getSingleton().getMessaggio("annualaverages"));
		chckbxMedie.setBounds(22, 336, 197, 23);
		getContainer().add(chckbxMedie);

		final JCheckBox chckbxAvanzo = new CheckBoxF(I18NManager.getSingleton().getMessaggio("avanzo"));
		chckbxAvanzo.setBounds(22, 309, 197, 23);
		getContainer().add(chckbxAvanzo);

		final JButton btnGeneraReport = new ButtonF(I18NManager.getSingleton().getMessaggio("reports"));
		btnGeneraReport.setBounds(22, 366, 197, 25);
		getContainer().add(btnGeneraReport);

		btnGeneraReport.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			protected void actionPerformedOverride(ActionEvent e) {
				super.actionPerformedOverride(e);

				settaValoriReportDati(chckbxSpeseVariabili1, chckbxEntrateMensCategorie, chckbxSpeseMensCat,
						chckbxEntratePerCategorie, chckbxSpesePerCategorie, chckbxUsciteMensili, chckbxEntrateMensili,
						chckbxUsciteAnnuali, chckbxEntrateAnnuali, chckbxSpeseFutili1, chckbxAvanzo, chckbxMedie);

				try {
					final IScrittoreReport scrittoreReport = new ScrittoreReportTxt(reportData);
					scrittoreReport.generaReport();
				} catch (final Exception e11) {
					ControlloreBase.getLog().log(Level.SEVERE, e11.getMessage(), e11);
				}
				Alert.segnalazioneInfo("Aggiornato Report: " + DBUtil.dataToString(new Date(), "dd/MM/yyyy HH:mm"));
			}

		});
		ControlloreBase.getLog().info("Registrato Report: " + DBUtil.dataToString(new Date(), "dd/MM/yyyy HH:mm"));

	}
	
	private void settaValoriReportDati(final JCheckBox chckbxSpeseVariabili1,
			final JCheckBox chckbxEntrateMensCategorie, final JCheckBox chckbxSpeseMensCat,
			final JCheckBox chckbxEntratePerCategorie, final JCheckBox chckbxSpesePerCategorie,
			final JCheckBox chckbxUsciteMensili, final JCheckBox chckbxEntrateMensili,
			final JCheckBox chckbxUsciteAnnuali, final JCheckBox chckbxEntrateAnnuali,
			final JCheckBox chckbxSpeseFutili1, final JCheckBox chckbxAvanzo, final JCheckBox chckbxMedie) {
		
		inserisciUsciteVariabili(chckbxSpeseVariabili1.isSelected());
		inserisciEntrateCatMensili(chckbxEntrateMensCategorie.isSelected());
		inserisciUsciteCatMensili(chckbxSpeseMensCat.isSelected());
		inserisciEntrateCatAnnuali(chckbxEntratePerCategorie.isSelected());
		inserisciUsciteCatAnnuali(chckbxSpesePerCategorie.isSelected());
		inserisciUsciteMensili(chckbxUsciteMensili.isSelected());
		inserisciEntrateMensili(chckbxEntrateMensili.isSelected());
		inserisciUsciteAnnuali(chckbxUsciteAnnuali.isSelected());
		inserisciEntrateAnnuali(chckbxEntrateAnnuali.isSelected());
		inserisciUsciteFutili(chckbxSpeseFutili1.isSelected());
		inserisciAvanzo(chckbxAvanzo.isSelected());
		inserisciMediaEntrate(chckbxMedie.isSelected());
		inserisciMediaUscite(chckbxMedie.isSelected());
	}
}
