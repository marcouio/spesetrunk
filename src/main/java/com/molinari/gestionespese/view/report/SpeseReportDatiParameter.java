package com.molinari.gestionespese.view.report;

import javax.swing.JCheckBox;

public class SpeseReportDatiParameter {
	private JCheckBox chckbxSpeseVariabili1;
	private JCheckBox chckbxSpeseMensCat;
	private JCheckBox chckbxSpesePerCategorie;
	private JCheckBox chckbxUsciteMensili;
	private JCheckBox chckbxUsciteAnnuali;
	private JCheckBox chckbxSpeseFutili1;

	public SpeseReportDatiParameter(JCheckBox chckbxSpeseVariabili1, JCheckBox chckbxSpeseMensCat,
			JCheckBox chckbxSpesePerCategorie, JCheckBox chckbxUsciteMensili, JCheckBox chckbxUsciteAnnuali,
			JCheckBox chckbxSpeseFutili1) {
		this.chckbxSpeseVariabili1 = chckbxSpeseVariabili1;
		this.chckbxSpeseMensCat = chckbxSpeseMensCat;
		this.chckbxSpesePerCategorie = chckbxSpesePerCategorie;
		this.chckbxUsciteMensili = chckbxUsciteMensili;
		this.chckbxUsciteAnnuali = chckbxUsciteAnnuali;
		this.chckbxSpeseFutili1 = chckbxSpeseFutili1;
	}

	public JCheckBox getChckbxSpeseVariabili1() {
		return chckbxSpeseVariabili1;
	}

	public JCheckBox getChckbxSpeseMensCat() {
		return chckbxSpeseMensCat;
	}

	public JCheckBox getChckbxSpesePerCategorie() {
		return chckbxSpesePerCategorie;
	}

	public JCheckBox getChckbxUsciteMensili() {
		return chckbxUsciteMensili;
	}

	public JCheckBox getChckbxUsciteAnnuali() {
		return chckbxUsciteAnnuali;
	}

	public JCheckBox getChckbxSpeseFutili1() {
		return chckbxSpeseFutili1;
	}
}