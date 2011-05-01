package view.sidebar;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

public class ExpanderTester3 extends JFrame {

	private static final long serialVersionUID = 1L;

	public ExpanderTester3() {
		JPanel listPanel = new JPanel(new BorderLayout());

		SideBar sideBar = new SideBar();

		JTree tree = new JTree();

		SidebarSectionModel m1 = new SidebarSectionModel("Colours", tree, "JTree");

		JPanel pannello = new JPanel();
		pannello.setLayout(new BoxLayout(pannello, 1));
		for (int i = 0; i < 10; i++) {
			ToggleBtn b = new ToggleBtn("c");
			pannello.add(b);
		}
		SidebarSectionModel m12 = new SidebarSectionModel("papsdasd", pannello, "Pannello");
		SideBar ss1 = new SideBar(sideBar, m1, 0, 0);
		SideBar ss22 = new SideBar(sideBar, m12, 0, 0);
		sideBar.addSection(ss1);
		sideBar.addSection(ss22);

		DefaultTableModel tModel = new DefaultTableModel(4, 5);
		tModel.setValueAt("Einstein", 0, 0);
		tModel.setValueAt("Plato", 1, 0);
		tModel.setValueAt("Nietsche", 2, 0);

		JTable table = new JTable(tModel);

		SidebarSectionModel m2 = new SidebarSectionModel("Thinkers", table, "JTable");
		SideBar ss2 = new SideBar(sideBar, m2, 0, 0);
		sideBar.addSection(ss2);

		JPanel pannell = new JPanel();

		pannell.setLayout(new BoxLayout(pannell, 1));
		for (int i = 0; i < 5; i++) {
			SidebarSectionModel modelInterno = new SidebarSectionModel("Thinkers", new JPanel(), "JTable");
			ComponenteSideBar button = new ComponenteSideBar(sideBar, modelInterno, 0, 0);
			pannell.add(button);
		}

		SidebarSectionModel m22 = new SidebarSectionModel("provaa", pannell, "JTable");
		SideBar componente = new SideBar(sideBar, m22, 0, 0);
		sideBar.addSection(componente);

		listPanel.add(sideBar, BorderLayout.WEST);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(listPanel);
		this.setVisible(true);

	}

	public static void main(String[] args) {

		ExpanderTester3 frame = new ExpanderTester3();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1024, 708);

	}
}