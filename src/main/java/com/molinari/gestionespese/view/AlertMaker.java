package com.molinari.gestionespese.view;

import com.molinari.utility.graphic.component.alert.Alert;

public class AlertMaker {

	public void sendSevereMsg(String msg) {
		Alert.segnalazioneErroreGrave(msg);
	}
}
