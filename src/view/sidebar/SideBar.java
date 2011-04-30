package view.sidebar;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class SideBar extends JPanel {

	private static final long	serialVersionUID	= 1L;

	// The preferred initial width of the side bar  //LA LARGHEZZA LA SETTA QUI E VALE PER TUTTO!!!
	private static int			PREFERRED_WIDTH		= 200;

	// box layout to contain side bar sections arranged vertically
	private BoxLayout			boxLayout			= new BoxLayout(this,BoxLayout.Y_AXIS);

	public static final ArrayList<ComponenteSideBar> componentiSideBar = new ArrayList<ComponenteSideBar>();
	public ArrayList<ComponenteSideBar> componentiInterneSideBar = new ArrayList<ComponenteSideBar>();	
	// the currently expanded section
	private ComponenteSideBar		currentSection		= null;

	public SideBar() {

		setLayout(boxLayout);
		setMinimumSize(new Dimension(0, 0));
		setPreferredSize(new Dimension(PREFERRED_WIDTH, 1));
		setFocusable(false);
		revalidate();
	}

	public void addSection(ComponenteSideBar newSection) {
		add(newSection, null);
		componentiInterneSideBar.add(newSection);
		componentiSideBar.add(newSection);
		newSection.setOwner(this);
		newSection.collapse();
	}

	public boolean isCurrentSection(ComponenteSideBar section) {
		return (section != null) && (currentSection != null)
						&& section.equals(currentSection);
	}

	public ComponenteSideBar getCurrentSection() {
		return currentSection;
	}

	public void deselezionaAllButtons(){
		for(int i = 0;i<componentiSideBar.size(); i++){
			ComponenteSideBar comp = componentiSideBar.get(i);
			comp.getBottone().setSelected(false);
		}
	}
	
	public void setCurrentSection(ComponenteSideBar section) {
		currentSection = section;
		deselezionaAllButtons();
		section.getBottone().setSelected(true);
	}
}