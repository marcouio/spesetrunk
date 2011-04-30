package view.sidebar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class ComponenteSideBar extends SideBar{

	private static final long	serialVersionUID	= 1L;
	public int	minComponentHeight	= 60;
	public int	minComponentWidth	= 50;

	private JPanel			pannelloCategoria	= new JPanel();
	private SideBar			sideBarOwner;
	private JComponent		contentPane;
	private ToggleBtn bottone;						

	public ArrayList<ComponenteSideBar> componentiInterneSideBar = new ArrayList<ComponenteSideBar>();
	
	public static int		EXPAND_FULLY;
	public static int		EXPAND_MINIMALLY;

	// private ArrowPanel arrowPanel;

	/**
	 * Construct a new sidebar section with the specified owner and model.
	 * 
	 * @param owner
	 *            - SideBar
	 * @param model
	 */
	public ComponenteSideBar(SideBar owner, SidebarSectionModel model, int minComponentHeightMod, int minComponentWidthMod) {
		minComponentHeight = (minComponentHeightMod!=0)?minComponentHeightMod:minComponentHeight;
		minComponentWidth =  (minComponentWidthMod!=0)?minComponentHeightMod:minComponentHeight;
		
		setLayout(null);

		add(pannelloCategoria);
		getPannelloCategoria().setLayout(new GridLayout(0, 1));
		getPannelloCategoria().setPreferredSize(new Dimension(this.getPreferredSize().width,this.minComponentHeight));
		getPannelloCategoria().setBorder(BorderFactory.createLineBorder(Color.GRAY));
		bottone = new ToggleBtn(model.getText(),new ImageIcon("C:\\Documents and Settings\\marco.molinari\\Documenti\\Immagini\\Immagini UML\\decision.jpg"));
		bottone.setBorder(null);
        bottone.setBackground(Color.WHITE);
        bottone.setToolTipText(model.getTooltip());
        bottone.setHorizontalAlignment(SwingConstants.LEFT); //allinea il contenuto del bottone a sinistra
        final Icon icona1 = bottone.getMyIcon();
        bottone.setRolloverIcon(icona1);
        bottone.setRolloverEnabled(true);
        getPannelloCategoria().add(bottone);
      
        bottone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isExpanded()) {
					expand();
				} else{
					
					collapse();
				}
			}
		});
		
		contentPane = model.getComponenteGrafico();

		// centering
		if (model.getComponenteGrafico() != null) {
			add(model.getComponenteGrafico());// , BorderLayout.CENTER);
		}
		revalidate();

		setOwner(owner);
	}
//Questo permette di settare le dimensioni del contenuto ()
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);

		pannelloCategoria.setBounds(0, 0, w, this.minComponentHeight);

		if (contentPane.isVisible())
			contentPane.setBounds(0, this.minComponentHeight, w,
							contentPane.getPreferredSize().height); 
		else
			contentPane.setBounds(0, 0, 0, 0);
	}

	public void setOwner(SideBar newOwner) {
		if (newOwner == sideBarOwner)
			return;

		sideBarOwner = newOwner; // must be before newOwner.addSection() to
									// avoid
		// infinite recursion
		if (newOwner != null)
			newOwner.addSection(this); // add to new Side-bar
	}

	
	
	@Override
	public void addSection(ComponenteSideBar newSection) {
		super.addSection(newSection);
	}
	public void expand() {

		if (this != null) {
			sideBarOwner.setCurrentSection(this); // must be called before
		}
		if(contentPane instanceof JPanel)
		setMaximumSize(new Dimension(Integer.MAX_VALUE,
						this.minComponentHeight
										+ contentPane.getPreferredSize().height));// Integer.MAX_VALUE,
		// Integer.MAX_VALUE));
		getContentPane().setVisible(true);
		revalidate();
	}

	public void collapse() {
		sideBarOwner.setCurrentSection(this);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, getPannelloCategoria()
						.getPreferredSize().height));
		getContentPane().setVisible(false);
		revalidate();
	}

	public JComponent getContentPane() {
		return contentPane;
	}

	protected JPanel getPannelloCategoria() {
		return pannelloCategoria;
	}

	public Dimension getMinimumSize() {
		return new Dimension(60, this.minComponentHeight);
	}

	public Dimension getPreferredSize() {
		return new Dimension(60, this.minComponentHeight);
	}

	public boolean isExpanded() {
		return getContentPane().isVisible();
	}

	public ToggleBtn getBottone() {
		return bottone;
	}

	public void setBottone(ToggleBtn bottone) {
		this.bottone = bottone;
	}
}