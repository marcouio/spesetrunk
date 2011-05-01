package view.sidebar;

import java.awt.Dimension;

public class ComponenteSideBar extends SideBar {

	private static final long serialVersionUID = 1L;

	public ComponenteSideBar() {
		super();
	}

	public ComponenteSideBar(SideBar owner, SidebarSectionModel model, int minComponentHeightMod, int minComponentWidthMod) {
		super(owner, model, minComponentHeightMod, minComponentWidthMod);
	}

	@Override
	public Dimension getPreferredSize() {
		if (getContentPane() != null)
			return getContentPane().getSize();
		else
			return super.getPreferredSize();
	}
}
