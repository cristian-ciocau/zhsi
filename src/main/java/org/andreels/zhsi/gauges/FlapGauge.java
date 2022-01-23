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

package org.andreels.zhsi.gauges;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.Timer;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.resources.LoadResources;
import org.andreels.zhsi.xpdata.XPData;

public class FlapGauge extends JFrame implements ComponentListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Image logo_image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ZHSI_logo.png"));

	private BufferedImage img_flapGauge_bg;
	private BufferedImage img_L_needle;
	private BufferedImage img_R_needle;
	
	int posX = 0;
	int locationX = 300;
	int gWidth = 200;
	int posY = 0;
	int locationY = 300;
	int gHeight = 200;
	
	float scaling_factor = 0.5f;
	
	private String title = "Flap Gauge";
	
	JPanel flapGaugePanel;
	ZHSIPreferences preferences;
	LoadResources rs;
	ModelFactory model_factory;
	XPData xpd;
	
	public FlapGauge(ModelFactory model_instance, String title) {
		this.model_factory = model_instance;
		this.xpd = model_instance.getInstance();
		this.title = title;
		this.setIconImage(this.logo_image);
		this.preferences = ZHSIPreferences.getInstance();
		this.rs = LoadResources.getInstance();
		this.locationX = Integer.parseInt(preferences.get_preference(ZHSIPreferences.PREF_FLAP_GAUGE_POS_X));
		this.locationY = Integer.parseInt(preferences.get_preference(ZHSIPreferences.PREF_FLAP_GAUGE_POS_Y));
		this.gWidth = Integer.parseInt(preferences.get_preference(ZHSIPreferences.PREF_FLAP_GAUGE_WIDTH));
		this.gHeight = Integer.parseInt(preferences.get_preference(ZHSIPreferences.PREF_FLAP_GAUGE_HEIGHT));

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(this.locationX, this.locationY, this.gWidth, this.gHeight);
		setTitle(this.title);
		this.getContentPane().setBackground(Color.BLACK);
		flapGaugePanel = new flapGaugePanel();
		this.getContentPane().add(flapGaugePanel);
		setUndecorated(true);
		if (preferences.get_preference(ZHSIPreferences.PREF_ALWAYSONTOP).equals("true")) {
			setAlwaysOnTop(true);
		}else {
			setAlwaysOnTop(false);
		}
		JPopupMenu menu = new JPopupMenu("Popup");
		menu.setBackground(Color.DARK_GRAY);
		menu.setBorder(null);
		JLabel popUpTitle = new JLabel(this.title);
		popUpTitle.setForeground(Color.WHITE);
		popUpTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JMenuItem saveLocation = new JMenuItem("Save Gauge Size and Location");
		saveLocation.setBackground(Color.GRAY);
		saveLocation.setForeground(Color.WHITE);
		saveLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		saveLocation.setToolTipText("Saves the current gauge size and location");
		saveLocation.addActionListener((ActionEvent event) -> {
			this.saveDisplayLocations();
		});
		menu.add(popUpTitle);
		menu.add(new JSeparator());
		menu.add(saveLocation);
		this.addComponentListener(this);
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				setLocation(evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
				if(e.isPopupTrigger()) {
					//menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					//menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() < 0) {
					setSize(new Dimension(getWidth() + 2, getHeight() + 2));
				}
				if (e.getWheelRotation() > 0) {
					setSize(new Dimension(getWidth() - 2, getHeight() - 2));
				}	
			} 	
		});
		this.addKeyListener(this);
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.setSize(new Dimension(getWidth() - 2,getHeight() - 2));
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.setSize(new Dimension(getWidth() + 2,getHeight() + 2));
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private void saveDisplayLocations() {
		int xx = (int) this.getLocationOnScreen().getX();
		int yy = (int) this.getLocationOnScreen().getY();
		this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_POS_X, Integer.toString(xx));
		this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_POS_Y, Integer.toString(yy));
		this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_WIDTH, Integer.toString(this.getWidth()));
		this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_HEIGHT, Integer.toString(this.getHeight()));
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		if(this.isShowing()) {
			int xx = (int) this.getLocationOnScreen().getX();
			int yy = (int) this.getLocationOnScreen().getY();
			this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_POS_X, Integer.toString(xx));
			this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_POS_Y, Integer.toString(yy));
		}

		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		this.scaling_factor = this.getWidth() / 400f;
		this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_WIDTH, Integer.toString(this.getWidth()));
		//this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_HEIGHT, Integer.toString(this.getHeight()));
		this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_HEIGHT, Integer.toString(this.getWidth()));
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class flapGaugePanel extends JPanel implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		AffineTransform original_at;
		Timer updateTimer = new Timer(1000, this);
		;		
		public flapGaugePanel(){
			try {
				img_flapGauge_bg = ImageIO.read(getClass().getResource("/gauges/img_flapGauge_bg.png"));
				img_L_needle = ImageIO.read(getClass().getResource("/gauges/img_L_needle.png"));
				img_R_needle = ImageIO.read(getClass().getResource("/gauges/img_R_needle.png"));
			}catch (Exception e) {
				e.printStackTrace();
			}
			this.setBackground(Color.BLACK);
			updateTimer.setInitialDelay(1);
			updateTimer.setDelay(Math.round(1000 / Integer.parseInt(preferences.get_preference(ZHSIPreferences.PREF_FRAMERATE))));
			updateTimer.start();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			float l_needle_rotate = 0f;
			float r_needle_rotate = 0f;
			
			
			if(xpd.flaps_l_deflection() > 33f) { //flaps 40 is 270deg
				
				l_needle_rotate = ((30f / 16f) * (xpd.flaps_l_deflection() - 33f)) + 240f;
				
			}else if(xpd.flaps_l_deflection() > 27f){ //flaps 30 is 240deg
				
				l_needle_rotate = ((30f / 6f) * (xpd.flaps_l_deflection() - 27f)) + 210f;
				
			}else if(xpd.flaps_l_deflection() > 17f){ //flaps 25 is 210deg
				
				l_needle_rotate = ((30f / 10f) * (xpd.flaps_l_deflection() - 17f)) + 180f;
				
			}else if(xpd.flaps_l_deflection() > 14f){ //flaps 15 is 180deg
				
				l_needle_rotate = ((30f / 3f) * (xpd.flaps_l_deflection() - 14f)) + 150f;
				
			}else if(xpd.flaps_l_deflection() > 11f){ //flaps 10 is 150deg
				
				l_needle_rotate = ((37.5f / 3f) * (xpd.flaps_l_deflection() - 11f)) + 112.5f;
				
			}else if(xpd.flaps_l_deflection() > 7f){ //flaps 5 is 112.5deg
				
				l_needle_rotate = ((35f / 4f) * (xpd.flaps_l_deflection() - 7f)) + 77.5f;
				
			}else if(xpd.flaps_l_deflection() > 4f){ //flaps 2 is 77.5deg
				
				l_needle_rotate = ((42.5f / 3f) * (xpd.flaps_l_deflection() - 4f)) + 35f;
							
			}else { //flaps 1 is 35deg

				l_needle_rotate = (35f / 4f * xpd.flaps_l_deflection());
				
			}
			
			if(xpd.flaps_r_deflection() > 33f) {
				
				r_needle_rotate = ((30f / 16f) * (xpd.flaps_r_deflection() - 33f)) + 240f;
				
			}else if(xpd.flaps_r_deflection() > 27f){
				
				r_needle_rotate = ((30f / 6f) * (xpd.flaps_r_deflection() - 27f)) + 210f;
				
			}else if(xpd.flaps_r_deflection() > 17f){
				
				r_needle_rotate = ((30f / 10f) * (xpd.flaps_r_deflection() - 17f)) + 180f;
				
			}else if(xpd.flaps_r_deflection() > 14f){
				
				r_needle_rotate = ((30f / 3f) * (xpd.flaps_r_deflection() - 14f)) + 150f;
				
			}else if(xpd.flaps_r_deflection() > 11f){
				
				r_needle_rotate = ((37.5f / 3f) * (xpd.flaps_r_deflection() - 11f)) + 112.5f;
				
			}else if(xpd.flaps_r_deflection() > 7f){
				
				r_needle_rotate = ((35f / 4f) * (xpd.flaps_r_deflection() - 7f)) + 77.5f;
				
			}else if(xpd.flaps_r_deflection() > 4f){
				
				r_needle_rotate = ((42.5f / 3f) * (xpd.flaps_r_deflection() - 4f)) + 35f;
				
			}else {
				
				r_needle_rotate = (35f / 4f * xpd.flaps_r_deflection());
			}
			
			original_at = g2.getTransform();
			g2.scale(scaling_factor, scaling_factor);
			g2.drawImage(img_flapGauge_bg, 0, 0, 400, 400, null);
			//
			g2.rotate(Math.toRadians(r_needle_rotate), 200, 200);
			g2.drawImage(img_R_needle, 0, 0, 400, 400, null);
			g2.setTransform(original_at);
			//
			original_at = g2.getTransform();
			g2.scale(scaling_factor, scaling_factor);
			g2.rotate(Math.toRadians(l_needle_rotate), 200, 200);
			g2.drawImage(img_L_needle, 0, 0, 400, 400, null);
			g2.setTransform(original_at);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (this.getParent().isShowing() && this.isVisible()) {
				repaint();
			}
			
		}
		
	}

}