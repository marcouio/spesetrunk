package com.molinari.gestionespese.view.report;

import javax.swing.JCheckBox;

public class EntrateReportDatiParameter {
	private JCheckBox chckbxEntrateMensCategorie;
	private JCheckBox chckbxEntratePerCategorie;
	private JCheckBox chckbxEntrateMensili;
	private JCheckBox chckbxEntrateAnnuali;

	public EntrateReportDatiParameter(JCheckBox chckbxEntrateMensCategorie, JCheckBox chckbxEntratePerCategorie,
			JCheckBox chckbxEntrateMensili, JCheckBox chckbxEntrateAnnuali) {
		this.chckbxEntrateMensCategorie = chckbxEntrateMensCategorie;
		this.chckbxEntratePerCategorie = chckbxEntratePerCategorie;
		this.chckbxEntrateMensili = chckbxEntrateMensili;
		this.chckbxEntrateAnnuali = chckbxEntrateAnnuali;
	}

	public JCheckBox getChckbxEntrateMensCategorie() {
		return chckbxEntrateMensCategorie;
	}

	public JCheckBox getChckbxEntratePerCategorie() {
		return chckbxEntratePerCategorie;
	}

	public JCheckBox getChckbxEntrateMensili() {
		return chckbxEntrateMensili;
	}

	public JCheckBox getChckbxEntrateAnnuali() {
		return chckbxEntrateAnnuali;
	}
}