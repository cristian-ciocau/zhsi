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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.Timer;

import org.andreels.zhsi.displays.blank.BLANK;
import org.andreels.zhsi.displays.engine.ENGINE;
import org.andreels.zhsi.displays.mfd.MFD;
import org.andreels.zhsi.displays.mfd.MFD2;
import org.andreels.zhsi.displays.nd.ND2;
import org.andreels.zhsi.displays.pfd.PFD;
import org.andreels.zhsi.resources.LoadResources;
import org.andreels.zhsi.xpdata.XPData;

public class DisplayUnit extends JFrame implements ComponentListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private Image logo_image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ZHSI_logo.png"));
	
	JPanel display;
	ZHSIPreferences preferences;
	LoadResources rs;
	ModelFactory model_factory;
	XPData xpd;
	PFD pfd;
	ND2 nd;
	ND2 nd_cpt;
	ND2 nd_fo;
	ENGINE eng;
	MFD mfd;
	BLANK blk;
	Thread ndloop;
	
	int posX = 0;
	int locationX = 300;
	int duWidth = 686;
	int posY = 0;
	int locationY = 300;
	int duHeight = 674;

	private String title = null;

	public boolean isCptOutDU = false;
	public boolean isCptInDU = false;
	public boolean isFoOutDU = false;
	public boolean isFoInDU = false;
	public boolean isUpperEicas = false;
	public boolean isLowerEicas = false;

	public String pilot = null;

	private String PREF_POS_X;
	private String PREF_POS_Y;
	private String PREF_WIDTH;
	private String PREF_HEIGHT;
	
	public DisplayUnit(ModelFactory model_instance, String title) {
		
		this.preferences = ZHSIPreferences.getInstance();
		this.model_factory = model_instance;
		this.xpd = this.model_factory.getInstance();
		this.title = title;
		this.setLayout(new BorderLayout());
		this.setIconImage(this.logo_image);

		if (this.title == "Captain OutBoard Display") { // displays PFD only

			ZHSIStatus.cptoutdb_running = true;
			this.isCptOutDU = true;
			this.pilot = "cpt";
			this.display = new JPanel();
			this.display.setBackground(Color.BLACK);
			this.display.setLayout(new CardLayout());
			this.pfd = new PFD(this.model_factory, this.title, this.pilot);
			this.blk = new BLANK(this.title);
			this.add(this.display);
			this.display.add(pfd, "PFD");
			this.display.add(blk, "BLANK");
			this.PREF_POS_X = ZHSIPreferences.PREF_CPT_OUTBD_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_CPT_OUTBD_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_CPT_OUTBD_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_CPT_OUTBD_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.duWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.duHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));

		} else if (this.title == "Captain InBoard Display") { // can display all
		
			ZHSIStatus.cptindb_running = true;
			this.isCptInDU = true;
			this.pilot = "cpt";
			this.display = new JPanel();
			this.display.setBackground(Color.BLACK);
			this.display.setLayout(new CardLayout());
			this.add(this.display);
			this.pfd = new PFD(this.model_factory, this.title,this.pilot);
			this.nd = new ND2(this.model_factory, this.title, this.pilot);
			this.eng = new ENGINE(this.model_factory, this.title, this.pilot);
			this.mfd = new MFD(this.model_factory, this.title, this.pilot);
			this.blk = new BLANK(this.title);
			this.display.add(nd, "ND");
			this.display.add(pfd, "PFD");
			this.display.add(eng, "ENGINE");
			this.display.add(mfd, "MFD");
			this.display.add(blk, "BLANK");
			this.PREF_POS_X = ZHSIPreferences.PREF_CPT_INBD_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_CPT_INBD_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_CPT_INBD_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_CPT_INBD_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.duWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.duHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));

		} else if(this.title == "FO OutBoard Display") { // displays PFD only
		
			ZHSIStatus.fooutdb_running = true;
			this.isFoOutDU = true;
			this.pilot = "fo";
			this.display = new JPanel();
			this.display.setBackground(Color.BLACK);
			this.display.setLayout(new CardLayout());
			this.pfd = new PFD(this.model_factory, this.title, this.pilot);
			this.blk = new BLANK(this.title);
			this.add(this.display);
			this.display.add(pfd, "PFD");
			this.display.add(blk, "BLANK");
			this.PREF_POS_X = ZHSIPreferences.PREF_FO_OUTBD_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_FO_OUTBD_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_FO_OUTBD_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_FO_OUTBD_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.duWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.duHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));

		} else if(this.title == "FO InBoard Display") { // can display all
			
			ZHSIStatus.foindb_running = true;
			this.isFoInDU = true;
			this.pilot = "fo";
			this.display = new JPanel();
			this.display.setBackground(Color.BLACK);
			this.display.setLayout(new CardLayout());
			this.add(this.display);
			this.pfd = new PFD(this.model_factory, this.title, this.pilot);
			this.nd = new ND2(this.model_factory, this.title, this.pilot);
			this.eng = new ENGINE(this.model_factory, this.title, this.pilot);
			this.mfd = new MFD(this.model_factory, this.title, this.pilot);
			this.blk = new BLANK(this.title);
			this.display.add(nd, "ND");
			this.display.add(pfd, "PFD");
			this.display.add(eng, "ENGINE");
			this.display.add(mfd, "MFD");
			this.display.add(blk, "BLANK");
			this.PREF_POS_X = ZHSIPreferences.PREF_FO_INBD_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_FO_INBD_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_FO_INBD_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_FO_INBD_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.duWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.duHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));

		} else if(this.title == "Upper EICAS Display") { // can display engine
			
			ZHSIStatus.uppereicas_running = true;
			this.isUpperEicas = true;
			this.display = new JPanel();
			this.display.setBackground(Color.BLACK);
			this.display.setLayout(new CardLayout());
			this.add(this.display);
			this.eng = new ENGINE(this.model_factory, this.title, this.pilot);
			this.blk = new BLANK(this.title);
			this.display.add(eng, "ENGINE");
			this.display.add(blk, "BLANK");
			this.PREF_POS_X = ZHSIPreferences.PREF_UP_EICAS_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_UP_EICAS_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_UP_EICAS_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_UP_EICAS_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.duWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.duHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));

		} else { // can display mfd, engine or ND
			
			ZHSIStatus.lowereicas_running = true;
			this.isLowerEicas = true;
			this.display = new JPanel();
			this.display.setBackground(Color.BLACK);
			this.display.setLayout(new CardLayout());
			this.add(this.display);
			this.eng = new ENGINE(this.model_factory, this.title, this.pilot);
			this.mfd = new MFD(this.model_factory, this.title, this.pilot);
			this.nd_cpt = new ND2(this.model_factory, this.title, "cpt");
			this.nd_fo = new ND2(this.model_factory, this.title, "fo");
			this.blk = new BLANK(this.title);
			this.display.add(this.mfd, "MFD");
			this.display.add(this.eng, "ENGINE");
			this.display.add(this.nd_cpt, "ND");
			this.display.add(this.nd_fo, "ND");
			this.display.add(this.blk, "BLANK");
			this.PREF_POS_X = ZHSIPreferences.PREF_LO_EICAS_POS_X;
			this.PREF_POS_Y = ZHSIPreferences.PREF_LO_EICAS_POS_Y;
			this.PREF_WIDTH = ZHSIPreferences.PREF_LO_EICAS_WIDTH;
			this.PREF_HEIGHT = ZHSIPreferences.PREF_LO_EICAS_HEIGHT;
			this.locationX = Integer.parseInt(preferences.get_preference(this.PREF_POS_X));
			this.locationY = Integer.parseInt(preferences.get_preference(this.PREF_POS_Y));
			this.duWidth = Integer.parseInt(preferences.get_preference(this.PREF_WIDTH));
			this.duHeight = Integer.parseInt(preferences.get_preference(this.PREF_HEIGHT));

		}

		JPopupMenu menu = new JPopupMenu("Popup");
		menu.setBackground(Color.DARK_GRAY);
		menu.setBorder(null);
		JLabel popUpTitle = new JLabel(this.title);
		popUpTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JMenuItem saveLocation = new JMenuItem("Save DU Size and Location");
		saveLocation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		saveLocation.setToolTipText("Saves the current Display Unit size and location");
		saveLocation.addActionListener((ActionEvent event) -> {
			this.saveDisplayLocations();
		});
		menu.add(popUpTitle);
		menu.add(new JSeparator());
		menu.add(saveLocation);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(this.locationX, this.locationY, this.duWidth, this.duHeight);
		setTitle(this.title);
		setUndecorated(true);
		
		if (preferences.get_preference(ZHSIPreferences.PREF_ALWAYSONTOP).equals("true")) {
			setAlwaysOnTop(true);
		} else {
			setAlwaysOnTop(false);
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent windowEvent) {

				if (title == "Captain OutBoard Display") {
					ZHSIStatus.cptoutdb_running = false;	
				}else if (title == "Captain InBoard Display") {
					ZHSIStatus.cptindb_running = false;
					//nd2.stopRender();
				}else if(title == "FO OutBoard Display") {
					ZHSIStatus.fooutdb_running = false;	
				}else if(title == "FO InBoard Display") {
					ZHSIStatus.foindb_running = false;
					//nd2.stopRender();
				}else if(title == "Upper EICAS Display") {
					ZHSIStatus.uppereicas_running = false;	
				}else{
					ZHSIStatus.lowereicas_running = false;
				}
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
		
	}
	
	public void saveDisplayLocations() {
		int xx = (int) this.getLocationOnScreen().getX();
		int yy = (int) this.getLocationOnScreen().getY();
		this.preferences.set_preference(this.PREF_POS_X, Integer.toString(xx));
		this.preferences.set_preference(this.PREF_POS_Y, Integer.toString(yy));
		this.preferences.set_preference(this.PREF_WIDTH, Integer.toString(this.getWidth()));
		this.preferences.set_preference(this.PREF_HEIGHT, Integer.toString(this.getHeight()));
	}

	public void heartbeat() {
		
		if(this.isCptInDU || this.isFoInDU) {
			
			switch(this.xpd.main_panel_du(pilot)) {
			case -1:
				this.pfd.setVisible(false);
				this.nd.setVisible(false);
				this.eng.setVisible(false);
				this.mfd.setVisible(false);
				this.blk.setVisible(true);
				break;
			case 0:
				this.pfd.setVisible(false);
				this.nd.setVisible(true);
				this.eng.setVisible(false);
				this.mfd.setVisible(false);
				this.blk.setVisible(false);
				break;
			case 1:
				this.pfd.setVisible(false);
				this.nd.setVisible(false);
				this.eng.setVisible(true);
				this.mfd.setVisible(false);
				this.blk.setVisible(false);
				break;
			case 2:
				this.pfd.setVisible(true);
				this.nd.setVisible(false);
				this.eng.setVisible(false);
				this.mfd.setVisible(false);
				this.blk.setVisible(false);
				break;
			case 3:
				this.pfd.setVisible(false);
				this.nd.setVisible(false);
				this.eng.setVisible(false);
				this.mfd.setVisible(true);
				this.blk.setVisible(false);
				break;
			}

		} else if(this.isCptOutDU || this.isFoOutDU) {
			
			if(this.xpd.main_panel_du(pilot) == 2){ // PFD on INDB
				this.pfd.setVisible(false);
				this.blk.setVisible(true);
			} else {
				this.pfd.setVisible(true);
				this.blk.setVisible(false);
			}
			
		} else if(this.isUpperEicas) {
			
			if((this.xpd.main_panel_du("cpt") == 1 || this.xpd.main_panel_du("fo") == 1) ||
			   (this.xpd.lower_panel_du("cpt") == -1 && this.xpd.lower_panel_du("fo") == 0) ||
			   (this.xpd.lower_panel_du("fo") == -1 && this.xpd.lower_panel_du("cpt") == 0)) {
				this.eng.setVisible(false);
				this.blk.setVisible(true); 
			} else {
				this.eng.setVisible(true);
				this.blk.setVisible(false);
			}
			
		} else if(this.isLowerEicas) {
		
			if(this.xpd.lower_panel_du("cpt") == 1) {
				this.mfd.setVisible(false);
				this.eng.setVisible(false);
				this.nd_cpt.setVisible(true);
				this.nd_fo.setVisible(false);
			}
			else if(this.xpd.lower_panel_du("fo") == 1) {
				this.mfd.setVisible(false);
				this.eng.setVisible(false);
				this.nd_cpt.setVisible(false);
				this.nd_fo.setVisible(true);
			}
			else if(this.xpd.lower_panel_du("cpt") == -1 || this.xpd.lower_panel_du("fo") == -1) {
				this.mfd.setVisible(false);
				this.eng.setVisible(true);
				this.nd_cpt.setVisible(false);
				this.nd_fo.setVisible(false);
			} else {
				this.mfd.setVisible(true);
				this.eng.setVisible(false);
				this.nd_cpt.setVisible(false);
				this.nd_fo.setVisible(false);
			}
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		if(this.isShowing()) {
			this.preferences.set_preference(this.PREF_POS_X, Integer.toString((int) this.getLocationOnScreen().getX()));
			this.preferences.set_preference(this.PREF_POS_Y, Integer.toString((int) this.getLocationOnScreen().getY()));
		}
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		this.preferences.set_preference(this.PREF_WIDTH, Integer.toString(this.getWidth()));
		this.preferences.set_preference(this.PREF_HEIGHT, Integer.toString(this.getHeight()));
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.setSize(new Dimension(getWidth() - 2, getHeight()));
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.setSize(new Dimension(getWidth() + 2, getHeight()));
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			this.setSize(new Dimension(getWidth(),getHeight() - 2));
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.setSize(new Dimension(getWidth(),getHeight() + 2));
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}