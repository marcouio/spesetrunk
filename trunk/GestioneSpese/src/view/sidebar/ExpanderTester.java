package view.sidebar;


import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;


public class ExpanderTester extends JFrame{

 public ExpanderTester(){
 }

 public static void main (String[] args) {

  ExpanderTester frame = new ExpanderTester();
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setBounds(0, 0, 1024, 708);

  JTabbedPane tabbedPane = new JTabbedPane();

  JPanel listPanel = new JPanel(new BorderLayout());

  SideBar sideBar = new SideBar();

  JTree tree = new JTree();

  SidebarSectionModel m1 = new SidebarSectionModel("Colours", tree, "JTree");

  JPanel pannello = new JPanel();
  pannello.setLayout(new BoxLayout(pannello, 1));
  for(int i = 0;i<10; i++){
	  JButton b = new JButton("c");
	  pannello.add(b);
  }
  SidebarSectionModel m12 = new SidebarSectionModel("papsdasd", pannello, "Pannello");
  ComponenteSideBar ss1 = new ComponenteSideBar(sideBar, m1,40,40);
  ComponenteSideBar ss22 = new ComponenteSideBar(sideBar, m12,40,40);
  sideBar.addSection(ss1);
  sideBar.addSection(ss22);

  DefaultTableModel tModel = new DefaultTableModel(4,5);
  tModel.setValueAt("Einstein", 0, 0);
  tModel.setValueAt("Plato", 1, 0);
  tModel.setValueAt("Nietsche", 2, 0);

  JTable table = new JTable(tModel);

  SidebarSectionModel m2 = new SidebarSectionModel("Thinkers", table, "JTable");
  ComponenteSideBar ss2 = new ComponenteSideBar(sideBar, m2,40,40);
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
  ComponenteSideBar ss3 = new ComponenteSideBar(sideBar, m3,40,40);
  sideBar.addSection(ss3);

  listPanel.add(sideBar, BorderLayout.WEST);
  listPanel.add(new JLabel("[html][body][h1]central panel[/html]", JLabel.CENTER));

  JPanel treePanel = new JPanel();
  JPanel tablePanel = new JPanel();

  tabbedPane.add("Slider Bar", listPanel);
//  tabbedPane.add("Tree", treePanel);
//  tabbedPane.add("Table", tablePanel);

  frame.getContentPane().setLayout(new BorderLayout());
  frame.getContentPane().add(tabbedPane);
  frame.setVisible(true);
 }
}