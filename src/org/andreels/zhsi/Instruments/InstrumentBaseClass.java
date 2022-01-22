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
package org.andreels.zhsi.Instruments;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.ZHSIStatus;
import org.andreels.zhsi.resources.LoadResources;
import org.andreels.zhsi.xpdata.XPData;

public abstract class InstrumentBaseClass extends JPanel implements ActionListener, ComponentListener { 

	private static final long serialVersionUID = 1L;
	
	private int initialWidth = 500;
	private int initialHeight = 500;
	
	public Graphics2D g2;
	public ZHSIPreferences preferences;
	public LoadResources rs;
	public ModelFactory model_instance;
	public XPData xpd;
	public String title;
	public String pilot;
	public Shape original_clipshape;
	public Stroke original_stroke;
	public AffineTransform original_trans = new AffineTransform();
	public Color color_instrument_white = new Color(0xE5E5E5);
	public Color color_instrument_gray = new Color(0x353539);
	
	public Stroke stroke3 = new BasicStroke(3f);
	
	public float scalex = 1f;
	public float scaley = 1f;
	public float scaling_factor = 1f;
	
	Timer updateTimer = new Timer(1000, this);
	
	public InstrumentBaseClass(ModelFactory model_factory, String title, String pilot) {
				
		this.model_instance = model_factory;
		this.xpd = this.model_instance.getInstance();
		this.preferences = ZHSIPreferences.getInstance();
		this.rs = LoadResources.getInstance();
		this.setBackground(Color.BLACK);
		this.title = title;
		this.pilot = pilot;
		if (this.title.equals("Electrical Display")) {
			initialWidth = 400;
			initialHeight = 250;
		} else if(this.title.equals("IRS Display")) {
			initialWidth = 500;
			initialHeight = 70;
		} else if(this.title.equals("Flt Alt Display")) {
			initialWidth = 250;
			initialHeight = 70;
		} else if(this.title.equals("Land Alt Display")) {
			initialWidth = 250;
			initialHeight = 70;
		}
		this.addComponentListener(this);
		updateTimer.setInitialDelay(1);
		updateTimer.setDelay(Math.round(1000 / Integer.parseInt(preferences.get_preference(ZHSIPreferences.PREF_FRAMERATE))));
		updateTimer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		original_clipshape = g2.getClip();
		original_stroke = g2.getStroke();
		original_trans = g2.getTransform();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	
		
		if (!ZHSIStatus.receiving) {
			
			drawFailCross();
			
		} else {
			
			if(ZHSIStatus.zibo_loaded) {

				drawInstrument(g2);
				drawFade(g2);
				
			}
		}
	}
	
	private void drawFade(Graphics2D g2) {
		//fade
		float fadeValue = 255 * (1 - this.xpd.du_brightness(this.title));
		Color fadeColor = new Color(0, 0, 0, (int) fadeValue);
		g2.setColor(fadeColor);
		g2.scale(this.scalex, this.scaley);
		g2.fillRect(0, 0, 500, 500);
		
	}

	public abstract void drawInstrument(Graphics2D g2);
	
	public void drawFailCross() {

		Stroke original_stroke = g2.getStroke();
		g2.scale(this.scalex, this.scaley);
		g2.setStroke(new BasicStroke(8f));
		g2.setFont(new Font("Verdana", Font.PLAIN, 32));
		g2.setColor(Color.WHITE);
		g2.drawString(title, 20, 40);
		g2.setColor(Color.RED);
		g2.drawString("ERROR : NO CONNECTION TO X-PLANE", 20 , 150);
		g2.setColor(Color.WHITE);
		g2.drawString("width: " + this.getWidth() + " height: " + this.getHeight(), 20, 200);
		g2.drawString("xpos: " + this.getLocationOnScreen().getX() + " ypos: " + this.getLocationOnScreen().getY(),20, 250);
		g2.drawString("Click and Drag to Move", 20, 350);
		g2.drawString("Use mouse scroll wheel or arrow keys to resize", 20, 400);
		g2.setColor(Color.RED);
		g2.setTransform(original_trans);
		g2.drawRect(0, 0, this.getWidth(), this.getHeight());
		g2.setStroke(original_stroke);

	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		this.scalex = this.getWidth() / (float)initialWidth;
		this.scaley = this.getHeight() / (float)initialHeight;
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(this.getParent() != null) {
			if (this.getParent().isShowing() && this.isVisible()) {
				repaint();
			}
		}	
	}
}