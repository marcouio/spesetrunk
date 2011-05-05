package view.bottoni;

import java.awt.GridLayout;

import javax.swing.JPanel;

import view.sidebar.ToggleBtn;

public class Bottone extends JPanel {

	private static final long serialVersionUID = 1L;

	private final ToggleBtn   bottone;
	private JPanel            contentPane      = new JPanel();

	public Bottone(ToggleBtn bottone) {
		this.bottone = bottone;
		this.add(bottone);
		contentPane.setLayout(new GridLayout(1, 0));
		this.setLayout(new GridLayout(2, 1));
	}

	public ToggleBtn getBottone() {
		return bottone;
	}

	public JPanel getContent() {
		return contentPane;
	}

	public void setContent(JPanel content) {
		this.contentPane = content;
		this.add(contentPane);
		contentPane.setVisible(false);
	}

}
