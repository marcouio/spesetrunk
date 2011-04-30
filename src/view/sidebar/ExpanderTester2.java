package view.sidebar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class ExpanderTester2 extends JFrame {

	public ExpanderTester2() {}

	public static void main(String[] args) {

		ExpanderTester2 frame = new ExpanderTester2();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1024, 708);

		JPanel listPanel = new JPanel(new BorderLayout());

		SideBar sideBar = new SideBar();

		JPanel pannello = new JPanel();
		pannello.setLayout(new GridLayout(0, 1));

		SidebarSectionModel primoLivelloModel = new SidebarSectionModel("Prima", pannello, "Pannello");
		ComponenteSideBar primoLivelloComponente = new ComponenteSideBar(sideBar, primoLivelloModel, 50, 30);
		sideBar.addSection(primoLivelloComponente);

		final ToggleBtn b = new ToggleBtn(new ImageIcon(new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB)));
		SidebarSectionModel secondoLivelloModel = new SidebarSectionModel("ecco", b, "Pannello");
		ComponenteSideBar secondoLivelloComponente = new ComponenteSideBar(primoLivelloComponente, secondoLivelloModel, 50, 30);
		// b.setSize(400, 400);
		b.setHorizontalAlignment(SwingConstants.LEFT);

		pannello.add(secondoLivelloComponente);
		final ToggleBtn b1 = new ToggleBtn(new ImageIcon(new BufferedImage(40, 40, BufferedImage.TYPE_INT_RGB)));
		SidebarSectionModel terzoLivelloModel = new SidebarSectionModel("ecco", b1, "Pannello");
		ComponenteSideBar terzoLivelloComponente = new ComponenteSideBar(secondoLivelloComponente, terzoLivelloModel, 50, 340);
		// b.setSize(400, 400);
		b1.setHorizontalAlignment(SwingConstants.LEFT);

		secondoLivelloComponente.add(terzoLivelloComponente);

		final JScrollPane scroll = new JScrollPane();
		scroll.getViewport().add(listPanel);
		// frame.add(scroll);

		listPanel.add(sideBar, BorderLayout.WEST);

		// frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(scroll);
		frame.setVisible(true);
	}
}