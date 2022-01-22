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
package org.andreels.zhsi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

import org.andreels.zhsi.Instruments.ElecPanel;
import org.andreels.zhsi.Instruments.IRSPanel;
import org.andreels.zhsi.Instruments.FltAltPanel;
import org.andreels.zhsi.Instruments.LandAltPanel;
import org.andreels.zhsi.Instruments.Chrono;
import org.andreels.zhsi.Instruments.ISFD;
import org.andreels.zhsi.Instruments.RMI;
import org.andreels.zhsi.xpdata.XPData;

public class Instrument extends JFrame implements ComponentListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Image logo_image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ZHSI_logo.png"));
	
	ZHSIPreferences preferences;
	ModelFactory model_instance;
	String pilot;
	String title;
	XPData xpd;
	ElecPanel elecPanel = null;
	IRSPanel irsPanel = null;
	FltAltPanel fltaltPanel = null;
	LandAltPanel landaltPanel = null;
	Chrono cptChrono = null;
	Chrono foChrono = null;
	RMI rmi = null;
	ISFD isfd = null;
	
	int posX = 0;
	int locationX = 300;
	int instWidth = 300;
	int posY = 0;
	int locationY = 300;
	int instHeight = 300;
	
	private String PREF_POS_X;
	private String PREF_POS_Y;
	private String PREF_WIDTH;
	private String PREF_HEIGHT;
	
	public Instrument(ModelFactory model_instance, String title, String pilot) {
		
		this.preferences = ZHSIPreferences.getInstance();
		this.model_instance = model_instance;
		this.xpd = this.model_instance.getInstance();
		this.setLayout(new BorderLayout());
		this.setIconImage(this.logo_image);
		this.title = title;
		this.pilot = pilot;
		
		if (this.title == "ISFD") {
			isfd = new ISFD(model_instance, title, pilot);
			this.add(isfd);
			this.PREF_POS_X = ZHSIPreferences.PREF_ISFD_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_ISFD_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_ISFD_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_ISFD_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.instWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.instHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));
		}
		else if (this.title == "Electrical Display") {
			elecPanel = new ElecPanel(model_instance, title, pilot);
			this.add(elecPanel);
			this.PREF_POS_X = ZHSIPreferences.PREF_ELECPANEL_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_ELECPANEL_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_ELECPANEL_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_ELECPANEL_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.instWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.instHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));
		}
		else if (this.title == "IRS Display") {
			irsPanel = new IRSPanel(model_instance, title, pilot);
			this.add(irsPanel);
			this.PREF_POS_X = ZHSIPreferences.PREF_IRSPANEL_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_IRSPANEL_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_IRSPANEL_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_IRSPANEL_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.instWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.instHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));
		}
		else if (this.title == "Flt Alt Display") {
			fltaltPanel = new FltAltPanel(model_instance, title, pilot);
			this.add(fltaltPanel);
			this.PREF_POS_X = ZHSIPreferences.PREF_FLTALTPANEL_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_FLTALTPANEL_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_FLTALTPANEL_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_FLTALTPANEL_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.instWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.instHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));
		}
		else if (this.title == "Land Alt Display") {
			landaltPanel = new LandAltPanel(model_instance, title, pilot);
			this.add(landaltPanel);
			this.PREF_POS_X = ZHSIPreferences.PREF_LANDALTPANEL_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_LANDALTPANEL_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_LANDALTPANEL_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_LANDALTPANEL_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.instWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.instHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));
		}
		else if(this.title == "Captain Chrono") {
			this.pilot = "cpt";
			cptChrono = new Chrono(model_instance, title, this.pilot);
			this.add(cptChrono);
			this.PREF_POS_X = ZHSIPreferences.PREF_CPTCHRONO_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_CPTCHRONO_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_CPTCHRONO_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_CPTCHRONO_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.instWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.instHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));
		}
		else if(this.title == "First Officer Chrono") {
			this.pilot = "fo";
			foChrono = new Chrono(model_instance, title, this.pilot);
			this.add(foChrono);
			this.PREF_POS_X = ZHSIPreferences.PREF_FOCHRONO_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_FOCHRONO_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_FOCHRONO_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_FOCHRONO_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.instWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.instHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));
		}
		else if(this.title == "RMI") {
			rmi = new RMI(model_instance, title, pilot);
			this.add(rmi);
			this.PREF_POS_X = ZHSIPreferences.PREF_RMI_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_RMI_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_RMI_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_RMI_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.instWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.instHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));
		}
		
		if (preferences.get_preference(ZHSIPreferences.PREF_ALWAYSONTOP).equals("true")) {
			this.setAlwaysOnTop(true);
		}else {
			this.setAlwaysOnTop(false);
		}
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				setLocation(evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
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
		addComponentListener(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(this.locationX, this.locationY, this.instWidth, this.instHeight);
		setTitle(this.title);
		setUndecorated(true);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.setSize(new Dimension(getWidth() - 2,getHeight()));
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.setSize(new Dimension(getWidth() + 2,getHeight()));
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			this.setSize(new Dimension(getWidth(),getHeight() - 2));
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.setSize(new Dimension(getWidth(),getHeight() + 2));
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

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		this.preferences.set_preference(this.PREF_POS_X, Integer.toString((int) this.getLocationOnScreen().getX()));
		this.preferences.set_preference(this.PREF_POS_Y, Integer.toString((int) this.getLocationOnScreen().getY()));
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.preferences.set_preference(this.PREF_WIDTH, Integer.toString(this.getWidth()));
		this.preferences.set_preference(this.PREF_HEIGHT, Integer.toString(this.getHeight()));
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}