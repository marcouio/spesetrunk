package com.molinari.gestionespese.view.datainsert;

import java.awt.Container;

import com.molinari.gestionespese.view.login.ListenerLogin;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.container.PannelloBase;
import com.molinari.utility.graphic.component.label.LabelBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;

public class PanelLogin implements DataPanelView {

	private static final int HEIGHT_FIELD = 30;
	private static final int HEIGHT_LABEL = 15;

	private PannelloBase pan;
	private LabelBase labUser;
	private LabelBase labPass;
	private ButtonBase button;
	
	public PanelLogin(Container padre) {
		
		this.pan = new PannelloBase(padre);
		this.pan.setSize(padre.getWidth(), padre.getHeight());
		
		int width = pan.getWidth() - 20;
		labUser = new LabelBase(pan);
		labUser.setBounds(10, 0, width, HEIGHT_LABEL);
		
		TextFieldBase tfUser = new TextFieldBase(pan);
		tfUser.setSize(width, HEIGHT_FIELD);
		tfUser.posizionaSottoA(labUser, 0, 10);
		
		labPass = new LabelBase(pan);
		labPass.setSize(width, HEIGHT_LABEL);
		labPass.posizionaSottoA(tfUser, 0, 15);
		
		TextFieldBase tfPass = new TextFieldBase(pan);
		tfPass.setSize(width, HEIGHT_FIELD);
		tfPass.posizionaSottoA(labPass, 0, 10);
		
		button =  new ButtonBase(pan);
		button.setSize(width, HEIGHT_FIELD);
		button.posizionaSottoA(tfPass, 0, 15);
		
		button.addActionListener(new ListenerLogin(tfUser, tfPass));
		
		
		initLabel();
	}

	public void initLabel() {
		labPass.setText("Username");
		labUser.setText("Password");
		button.setText("Login");
	}

	@Override
	public PannelloBase getPan() {
		return pan;
	}

	@Override
	public boolean aggiorna() {
		initLabel();
		return true;
	}
	
}
