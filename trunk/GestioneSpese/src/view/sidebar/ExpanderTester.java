package view.sidebar;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;

public class ExpanderTester extends JFrame {

	private static final long serialVersionUID = 1L;

	public ExpanderTester() {}

	public static void main(String[] args) {

		ExpanderTester frame = new ExpanderTester();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1024, 708);

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

		DefaultListModel model = new DefaultListModel();
		model.add(0, "Bill Gates");
		model.add(1, "Steven Spielberg");
		model.add(2, "Donald Trump");
		model.add(2, "Steve Jobs");
		model.add(2, "Steve Jobs");
		model.add(2, "Steve Jobs");
		model.add(2, "Steve Jobs");
		model.add(2, "Steve Jobs");
		model.add(2, "Steve Jobs");

		model.add(2, "Steve Jobs");
		model.add(2, "Steve Jobs");

		JList list = new JList();

		list.setModel(model);

		SidebarSectionModel m3 = new SidebarSectionModel("Dealers", list, "JList");
		SideBar ss3 = new SideBar(sideBar, m3, 0, 0);
		sideBar.addSection(ss3);

		listPanel.add(sideBar, BorderLayout.WEST);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(listPanel);
		frame.setVisible(true);
	}
}