/*
 * 
 * Copyright (C) 2018  Andre Els (https://www.facebook.com/sum1els737)
 * 
 * This is the Base Class for the DU displays
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
 
package org.andreels.zhsi.displays;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.ZHSIStatus;
import org.andreels.zhsi.resources.LoadResources;
import org.andreels.zhsi.xpdata.XPData;

public abstract class DUBaseClass extends JPanel implements ActionListener, ComponentListener{

	private static final long serialVersionUID = 1L;
	
	public Graphics2D g2;
	public DUGraphicsConfig gc;
	public ZHSIPreferences preferences;
	public LoadResources rs;
	public ModelFactory model_instance;
	public XPData xpd;
	public String title;
	public String pilot;
	public Rectangle2D fade = new Rectangle2D.Float();
	public Rectangle2D du = new Rectangle2D.Float();
	public Shape original_clipshape;
	public Stroke original_stroke;
	public AffineTransform original_trans = new AffineTransform();
	boolean fonts_need_updating = true;
	public float simFadeValue = 0.0f;
	private long lastTime;
	public double fps = 0f;
	public Color transparent_color = new Color(0, true);
	private Toolkit t;
	protected float scale_factor = 1f;
	protected float scalex = 1f;
	protected float scaley = 1f;
	
	
	//strokes
	public Stroke stroke5round = new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		
	private boolean showMessage = true;
	Timer showMessageTimer;
	private boolean showMessage2 = false;
	Timer showMessageTimer2;
	
	Timer updateTimer = new Timer(1000, this);

	
	public DUBaseClass(ModelFactory model_factory, String title, String pilot) {

		t = Toolkit.getDefaultToolkit();
		this.setOpaque(false);
		this.model_instance = model_factory;
		this.xpd = this.model_instance.getInstance();
		this.gc = new DUGraphicsConfig();
		this.preferences = ZHSIPreferences.getInstance();
		this.rs = LoadResources.getInstance();
		this.setBackground(Color.BLACK);
		this.title = title;
		this.pilot = pilot;
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
		
		long now = System.nanoTime();
		
		if(fonts_need_updating) {
			gc.updateFonts(g2);
			fonts_need_updating = false;
		}
			
		if (!ZHSIStatus.receiving) {
			
			drawFailCross(g2);			
			
		} else {
			
			if(ZHSIStatus.zibo_loaded) {
				simFadeValue = this.xpd.du_brightness(this.title);
				drawInstrument(g2);
				drawFade(g2);

			}else {
				simFadeValue = 0.0f;
				drawTestScreen();
			}

		}
		long timePassed = now - lastTime;
		lastTime = now;
		fps = 1_000_000_000 / timePassed;
		if (this.preferences.get_preference(ZHSIPreferences.PREF_SHOW_FPS).equals("true")) {
			g2.scale(gc.scalex, gc.scaley);
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, 120, 40);
			g2.setFont(rs.verdana26);
			g2.setColor(Color.ORANGE);
			g2.drawString("FPS: " + Math.round(fps), 5, 30);
			g2.setTransform(original_trans);
		}
		t.sync();
	}
	
	public abstract void drawInstrument(Graphics2D g2);

	
	private void drawFade(Graphics2D g2) {
		
		float fadeValue = 255 * (1 - simFadeValue);
		Color fadeColor = new Color(0, 0, 0, (int) fadeValue);
		g2.setColor(fadeColor);
		gc.drawRect2D(fade, 0, 0, 1072, 1053, 0, true, g2);	
	}


	public void drawFailCross(Graphics2D g2) {

		Stroke original_stroke = g2.getStroke();
		g2.scale(gc.scalex, gc.scaley);
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
	
	
	public void drawTestScreen() {
			
		g2.scale(gc.scalex, gc.scaley);
		g2.rotate(Math.toRadians(-45f), 1072/2, 1053/2);
		g2.setColor(gc.color_markings);
		
		g2.fillRect(-200, -280, 1472, 220);
		g2.fillRect(-200, 80, 1472, 220);
		g2.fillRect(-300, 430, 1672, 220);
		g2.fillRect(-200, 780, 1472, 220);
		g2.fillRect(-200, 1130, 1472, 200);
		
		g2.setTransform(original_trans);
		
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
		
		gc.setScalex(this.getWidth() / 1072f);
		gc.setScaley(this.getHeight() / 1053f);
		gc.setScalingfactor(this.getHeight() / 1053f);
		this.scale_factor = Math.min(this.getWidth(), this.getHeight()) / 800f;
		this.scalex = this.getWidth() / 800f;
		this.scaley = this.getHeight() / 800f;
		gc.updateDimensions();
		fonts_need_updating = true;
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