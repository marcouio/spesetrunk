package view.sidebar;


import javax.swing.JComponent;

public class SidebarSectionModel {

private String title;
private JComponent componenteGrafico;
private String tooltip;

public SidebarSectionModel(String title,
  JComponent sectionContent,
  String tooltip) {
 this.title = title;
 this.componenteGrafico = sectionContent;
 this.tooltip = tooltip;
}

public String getText() {
 return title;
}

public JComponent getComponenteGrafico(){
 return componenteGrafico;
}

public String getTooltip() {
 return tooltip;
}
}