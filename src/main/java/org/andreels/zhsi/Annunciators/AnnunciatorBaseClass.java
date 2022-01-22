/**
 * 
 * Copyright (C) 2018  Andre Els (https://www.facebook.com/sum1els737)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Andre Els
 * 
 */
package org.andreels.zhsi.Annunciators;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.ZHSIStatus;
import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRef;
import org.andreels.zhsi.ExtPlaneInterface.util.Observer;
import org.andreels.zhsi.resources.LoadResources;

public abstract class AnnunciatorBaseClass extends JPanel implements ActionListener, ComponentListener {

	private static final long serialVersionUID = 1L;
	
	protected Graphics2D g2;
	protected ZHSIPreferences preferences;
	protected LoadResources rs;
	protected ExtPlaneInterface iface;
	protected AffineTransform original_trans = new AffineTransform();

	protected Color color;
	protected String upperText;
	protected String lowerText;
	protected String dref;

	private int initialWidth = 150;
	private int initialHeight = 75;

	private int posX = 0;
	private int posY = 0;

	public float scalex = 1f;
	public float scaley = 1f;
	public float scaling_factor = 1f;
		
	public AnnunciatorBaseClass(String upperText, String lowerText, String dref, Color color) {

		this.iface = ExtPlaneInterface.getInstance();
		this.color = color;
		this.upperText = upperText;
		this.lowerText = lowerText;
		this.dref = dref;
		this.preferences = ZHSIPreferences.getInstance();
		this.rs = LoadResources.getInstance();
		this.setBackground(Color.BLACK);
		this.setSize(initialWidth, initialHeight);
		this.addComponentListener(this);
		
	}

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g2 = (Graphics2D) g;
		original_trans = g2.getTransform();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
				
	}

	public abstract void drawAnnunciator(Graphics2D g2);

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}