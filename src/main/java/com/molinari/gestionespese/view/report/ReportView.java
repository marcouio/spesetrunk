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
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.checkbox.CheckBoxBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.messages.I18NManager;

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
		getContainer().setSize(padre.getWidth(), padre.getHeight());
		final JLabel istruzioni = new LabelTestoPiccolo(I18NManager.getSingleton().getMessaggio("selectreport"), getContainer());
		istruzioni.setText(I18NManager.getSingleton().getMessaggio("select") + ":");
		istruzioni.setBounds(12, 12, 207, 20);
		getContainer().add(istruzioni);

		final JCheckBox chckbxEntrateAnnuali = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("yearincome"), getContainer());
		chckbxEntrateAnnuali.setBounds(22, 40, 197, 23);
		getContainer().add(chckbxEntrateAnnuali);

		final JCheckBox chckbxUsciteAnnuali = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("yearoutcome"), getContainer());
		chckbxUsciteAnnuali.setBounds(22, 67, 197, 23);
		getContainer().add(chckbxUsciteAnnuali);

		final JCheckBox chckbxEntrateMensili = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("monthlyincome"), getContainer());
		chckbxEntrateMensili.setBounds(22, 94, 197, 23);
		getContainer().add(chckbxEntrateMensili);

		final JCheckBox chckbxUsciteMensili = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("monthlyoutcome"), getContainer());
		chckbxUsciteMensili.setBounds(22, 121, 197, 23);
		getContainer().add(chckbxUsciteMensili);

		final JCheckBox chckbxSpesePerCategorie = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("catspeseyear"), getContainer());
		chckbxSpesePerCategorie.setBounds(22, 148, 197, 23);
		getContainer().add(chckbxSpesePerCategorie);

		final JCheckBox chckbxEntratePerCategorie = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("catentrateyear"), getContainer());
		chckbxEntratePerCategorie.setBounds(22, 175, 197, 23);
		getContainer().add(chckbxEntratePerCategorie);

		final JCheckBox chckbxSpeseMensCat = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("catspesemonth"), getContainer());
		chckbxSpeseMensCat.setBounds(22, 229, 197, 23);
		getContainer().add(chckbxSpeseMensCat);

		final JCheckBox chckbxEntrateMensCategorie = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("catentratemonth"), getContainer());
		chckbxEntrateMensCategorie.setBounds(22, 202, 197, 23);
		getContainer().add(chckbxEntrateMensCategorie);

		final JCheckBox chckbxSpeseVariabili1 = new CheckBoxBase("% "+ I18NManager.getSingleton().getMessaggio("spesevar"), getContainer());
		chckbxSpeseVariabili1.setBounds(22, 255, 197, 23);
		getContainer().add(chckbxSpeseVariabili1);

		final JCheckBox chckbxSpeseFutili1 = new CheckBoxBase("% " + I18NManager.getSingleton().getMessaggio("spesefut"), getContainer());
		chckbxSpeseFutili1.setBounds(22, 282, 197, 23);
		getContainer().add(chckbxSpeseFutili1);

		final JCheckBox chckbxMedie = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("annualaverages"), getContainer());
		chckbxMedie.setBounds(22, 336, 197, 23);
		getContainer().add(chckbxMedie);

		final JCheckBox chckbxAvanzo = new CheckBoxBase(I18NManager.getSingleton().getMessaggio("avanzo"), getContainer());
		chckbxAvanzo.setBounds(22, 309, 197, 23);
		getContainer().add(chckbxAvanzo);

		final JButton btnGeneraReport = new ButtonBase(I18NManager.getSingleton().getMessaggio("reports"), getContainer());
		btnGeneraReport.setBounds(22, 366, 197, 25);
		getContainer().add(btnGeneraReport);

		btnGeneraReport.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			protected void actionPerformedOverride(ActionEvent e) {
				super.actionPerformedOverride(e);

				SpeseReportDatiParameter speseParameter = new SpeseReportDatiParameter(chckbxSpeseVariabili1, chckbxSpeseMensCat, chckbxSpesePerCategorie, chckbxUsciteMensili, chckbxUsciteAnnuali, chckbxSpeseFutili1);
				EntrateReportDatiParameter entrateParameter = new EntrateReportDatiParameter(chckbxEntrateMensCategorie, chckbxEntratePerCategorie, chckbxEntrateMensili, chckbxEntrateAnnuali);
				settaValoriReportDati(speseParameter, entrateParameter, chckbxAvanzo, chckbxMedie);

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

	private void settaValoriReportDati(SpeseReportDatiParameter parameterObject,
			EntrateReportDatiParameter speseParameter, final JCheckBox chckbxAvanzo,
			final JCheckBox chckbxMedie) {
		
		inserisciUsciteVariabili(parameterObject.getChckbxSpeseVariabili1().isSelected());
		inserisciEntrateCatMensili(speseParameter.getChckbxEntrateMensCategorie().isSelected());
		inserisciUsciteCatMensili(parameterObject.getChckbxSpeseMensCat().isSelected());
		inserisciEntrateCatAnnuali(speseParameter.getChckbxEntratePerCategorie().isSelected());
		inserisciUsciteCatAnnuali(parameterObject.getChckbxSpesePerCategorie().isSelected());
		inserisciUsciteMensili(parameterObject.getChckbxUsciteMensili().isSelected());
		inserisciEntrateMensili(speseParameter.getChckbxEntrateMensili().isSelected());
		inserisciUsciteAnnuali(parameterObject.getChckbxUsciteAnnuali().isSelected());
		inserisciEntrateAnnuali(speseParameter.getChckbxEntrateAnnuali().isSelected());
		inserisciUsciteFutili(parameterObject.getChckbxSpeseFutili1().isSelected());
		inserisciAvanzo(chckbxAvanzo.isSelected());
		inserisciMediaEntrate(chckbxMedie.isSelected());
		inserisciMediaUscite(chckbxMedie.isSelected());
	}
}
