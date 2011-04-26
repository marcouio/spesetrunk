package view.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

public class DirectionalFlowLayout extends FlowLayout {

	private static final long serialVersionUID     = 1L;

	public static final int   HORIZONTAL_DIRECTION = 0;
	public static final int   VERTICAL_DIRECTION   = 1;

	private final int         direction;

	public DirectionalFlowLayout(int i, int direction) {
		super(i);
		this.direction = direction;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub
		super.addLayoutComponent(name, comp);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
		super.removeLayoutComponent(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container target) {
		// TODO Auto-generated method stub
		return super.preferredLayoutSize(target);
	}

	@Override
	public Dimension minimumLayoutSize(Container target) {
		// TODO Auto-generated method stub
		return super.minimumLayoutSize(target);
	}

	@Override
	public void layoutContainer(Container target) {
		synchronized (target.getTreeLock()) {
			Insets insets = target.getInsets();
			int maxwidth = target.getWidth()
			                - (insets.left + insets.right + getHgap() * 2);
			int nmembers = target.getComponentCount();
			int x = 0, y = insets.top + getVgap();
			int rowh = 0, start = 0;

			boolean ltr = target.getComponentOrientation().isLeftToRight();

			boolean useBaseline = getAlignOnBaseline();
			int[] ascent = null;
			int[] descent = null;

			if (useBaseline) {
				ascent = new int[nmembers];
				descent = new int[nmembers];
			}

			if (direction == 0) {
				for (int i = 0; i < nmembers; i++) {
					Component m = target.getComponent(i);
					if (m.isVisible()) {
						Dimension d = m.getPreferredSize();
						m.setSize(d.width, d.height);

						if (useBaseline) {
							int baseline = m.getBaseline(d.width, d.height);
							if (baseline >= 0) {
								ascent[i] = baseline;
								descent[i] = d.height - baseline;
							} else {
								ascent[i] = -1;
							}
						}
						if ((x == 0) || ((x + d.width) <= maxwidth)) {
							if (x > 0) {
								x += getHgap();
							}
							x += d.width;
							rowh = Math.max(rowh, d.height);
						} else {
							rowh = moveComponents(target, insets.left
							                + getHgap(), y, maxwidth - x, rowh,
							                start, i, ltr, useBaseline, ascent,
							                descent);
							x = d.width;
							y += getVgap() + rowh;
							rowh = d.height;
							start = i;
						}
					}
				}

				moveComponents(target, insets.left + getHgap(), y, maxwidth - x,
				                rowh, start, nmembers, ltr, useBaseline, ascent,
				                descent);
			} else {
				for (int i = 0; i < nmembers; i++) {
					Component m = target.getComponent(i);
					if (m.isVisible()) {
						Dimension d = m.getPreferredSize();
						m.setSize(d.width, d.height);

						if (useBaseline) {
							int baseline = m.getBaseline(d.width, d.height);
							if (baseline >= 0) {
								ascent[i] = baseline;
								descent[i] = d.height - baseline;
							} else {
								ascent[i] = -1;
							}
						}
						rowh = moveComponents(target, insets.left + getHgap(), y,
						                    maxwidth - x, rowh, start, i, ltr,
						                    useBaseline, ascent, descent);
						x = d.width;
						y += getVgap() + rowh;
						rowh = d.height;
						start = i;
					}
				}
				moveComponents(target, insets.left + getHgap(), y,
				                maxwidth - x, rowh, start, nmembers, ltr,
				                useBaseline, ascent, descent);
			}
		}
	}

	/**
	 * Centers the elements in the specified row, if there is any slack.
	 * 
	 * @param target
	 *            the component which needs to be moved
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param width
	 *            the width dimensions
	 * @param height
	 *            the height dimensions
	 * @param rowStart
	 *            the beginning of the row
	 * @param rowEnd
	 *            the the ending of the row
	 * @param useBaseline
	 *            Whether or not to align on baseline.
	 * @param ascent
	 *            Ascent for the components. This is only valid if useBaseline
	 *            is true.
	 * @param descent
	 *            Ascent for the components. This is only valid if useBaseline
	 *            is true.
	 * @return actual row height
	 */
	private int moveComponents(Container target, int x, int y, int width,
	                int height, int rowStart, int rowEnd, boolean ltr,
	                boolean useBaseline, int[] ascent, int[] descent) {
		switch (getAlignment()) {
			case LEFT:
				x += ltr ? 0 : width;
				break;
			case CENTER:
				x += width / 2;
				break;
			case RIGHT:
				x += ltr ? width : 0;
				break;
			case LEADING:
				break;
			case TRAILING:
				x += width;
				break;
		}
		int maxAscent = 0;
		int nonbaselineHeight = 0;
		int baselineOffset = 0;
		if (useBaseline) {
			int maxDescent = 0;
			for (int i = rowStart; i < rowEnd; i++) {
				Component m = target.getComponent(i);
				if (m.isVisible()) {
					if (ascent[i] >= 0) {
						maxAscent = Math.max(maxAscent, ascent[i]);
						maxDescent = Math.max(maxDescent, descent[i]);
					} else {
						nonbaselineHeight = Math.max(m.getHeight(),
						                nonbaselineHeight);
					}
				}
			}
			height = Math.max(maxAscent + maxDescent, nonbaselineHeight);
			baselineOffset = (height - maxAscent - maxDescent) / 2;
		}
		for (int i = rowStart; i < rowEnd; i++) {
			Component m = target.getComponent(i);
			if (m.isVisible()) {
				int cy;
				if (useBaseline && ascent[i] >= 0) {
					cy = y + baselineOffset + maxAscent - ascent[i];
				} else {
					cy = y + (height - m.getHeight()) / 2;
				}
				if (ltr) {
					m.setLocation(x, cy);
				} else {
					m.setLocation(target.getWidth() - x - m.getWidth(), cy);
				}
				x += m.getWidth() + getHgap();
			}
		}
		return height;
	}

}
