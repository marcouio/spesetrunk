package com.molinari.gestionespese.view.datainsert;

import java.awt.Container;

import com.molinari.gestionespese.view.login.RegisterListener;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;

public class PanelRegister implements DataPanelView {

	private static final int HEIGHT_FIELD = 30;
	private static final int HEIGHT_LABEL = 15;
	private PannelloBase pan;
	private LabelBase labUser;
	private LabelBase labPass;
	private LabelBase labName;
	private LabelBase labSurname;
	private TextFieldBase tfName;
	private TextFieldBase tfSurname;
	private TextFieldBase tfPass;
	private TextFieldBase tfUser;
	private ButtonBase button;
	
	public PanelRegister(Container padre) {
		
		this.setPan(new PannelloBase(padre));
		this.getPan().setSize(padre.getWidth(), padre.getHeight());
		
		int width = pan.getWidth() - 20;
		
		labUser = new LabelBase(pan);
		labUser.setBounds(10, 0, width, HEIGHT_LABEL);
		
		tfUser = new TextFieldBase(pan);
		tfUser.setSize(width, HEIGHT_FIELD);
		tfUser.posizionaSottoA(labUser, 0, 10);
		
		labPass = new LabelBase(pan);
		labPass.setSize(width, HEIGHT_LABEL);
		labPass.posizionaSottoA(tfUser, 0, 15);
		
		tfPass = new TextFieldBase(pan);
		tfPass.setSize(width, HEIGHT_FIELD);
		tfPass.posizionaSottoA(labPass, 0, 10);
		
		labName = new LabelBase(pan);
		labName.setSize(width, HEIGHT_LABEL);
		labName.posizionaSottoA(tfPass, 0, 15);
		
		tfName = new TextFieldBase(pan);
		tfName.setSize(width, HEIGHT_FIELD);
		tfName.posizionaSottoA(labName, 0, 10);
		
		labSurname = new LabelBase(pan);
		labSurname.setSize(width, HEIGHT_LABEL);
		labSurname.posizionaSottoA(tfName, 0, 15);
		
		tfSurname = new TextFieldBase(pan);
		tfSurname.setSize(width, HEIGHT_FIELD);
		tfSurname.posizionaSottoA(labSurname, 0, 10);
		
		button = new ButtonBase(pan);
		button.setSize(width, HEIGHT_FIELD);
		button.posizionaSottoA(tfSurname, 0, 15);
		
		button.addActionListener(new RegisterListener(tfName, tfSurname, tfUser, tfPass));
		
		updateLabel();
	}

	public void updateLabel() {
		labName.setText(I18NManager.getSingleton().getMessaggio("name"));
		labPass.setText("Password");
		labSurname.setText(I18NManager.getSingleton().getMessaggio("surname"));
		labUser.setText("Username");
		button.setText(I18NManager.getSingleton().getMessaggio("register"));
	}

	@Override
	public PannelloBase getPan() {
		return pan;
	}

	public void setPan(PannelloBase pan) {
		this.pan = pan;
	}

	@Override
	public boolean aggiorna() {
		updateLabel();
		return true;
	}
}
