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


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.Annunciators.Annunciator;
import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRef;
import org.andreels.zhsi.ExtPlaneInterface.util.Observer;
import org.andreels.zhsi.resources.LoadResources;
import org.andreels.zhsi.xpdata.XPData;
import org.andreels.zhsi.xpdata.XPDataRepositry;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class AnnunciatorWindow extends JFrame implements ComponentListener, KeyListener {

	private static final long serialVersionUID = 1L;
	
	private static AnnunciatorWindow instance = null;
	
	private Image logo_image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ZHSI_logo.png"));
	
	ZHSIPreferences preferences;
	LoadResources rs;
	XPDataRepositry xpdrepo;
	
	private int posX = 0;
	int locationX = 300;
	int windowWidth = 500;
	private int posY = 0;
	int locationY = 300;
	int windowHeight = 500;
	
	private JPopupMenu menu;
	private AnnunPanel panel1;
	private JPanel panel;
	private JFrame window;
	
	ArrayList<Annunciator> annuns = new ArrayList<Annunciator>();
	


	private AnnunciatorWindow() {
		
		this.xpdrepo = XPDataRepositry.getInstance();
				
		this.window = this;

		this.preferences = ZHSIPreferences.getInstance();
		this.rs = LoadResources.getInstance();
		
		this.locationX = Integer.parseInt(this.preferences.get_preference(ZHSIPreferences.PREF_ANNUNWINDOW_POS_X));
		this.locationY = Integer.parseInt(this.preferences.get_preference(ZHSIPreferences.PREF_ANNUNWINDOW_POS_Y));
		this.windowHeight = Integer.parseInt(this.preferences.get_preference(ZHSIPreferences.PREF_ANNUNWINDOW_HEIGHT));
		this.windowWidth = Integer.parseInt(this.preferences.get_preference(ZHSIPreferences.PREF_ANNUNWINDOW_WIDTH));
		
		
		this.setLayout(new BorderLayout());
		this.setIconImage(this.logo_image);
		
		if (preferences.get_preference(ZHSIPreferences.PREF_ALWAYSONTOP).equals("true")) {
			this.setAlwaysOnTop(true);
		}else {
			this.setAlwaysOnTop(false);
		}
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();

			}
			@Override
			public void mouseReleased(MouseEvent e) {
	
				if(e.isPopupTrigger()) {
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				setLocation(evt.getXOnScreen()-posX,evt.getYOnScreen()-posY);
			}
		});
		this.addMouseWheelListener(new MouseWheelListener() {
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
		this.addComponentListener(this);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(this.locationX, this.locationY, this.windowWidth, this.windowHeight);
		this.setTitle("Annunciators");
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		//this.add(panel);
		panel1 = new AnnunPanel();
		panel1.setBackground(Color.BLACK);
		panel1.setLayout(null);
		this.add(panel1);
		this.setUndecorated(true);
		
		
		//pop up menu
		JSeparator sep = new JSeparator();
		menu = new JPopupMenu();
		
		JCheckBoxMenuItem configMode = new JCheckBoxMenuItem("Config Mode");
		configMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(configMode.isSelected()) {
					panel1.setBackground(Color.WHITE);
				}else {
					panel1.setBackground(Color.BLACK);
				}
			}
			
		});
		
		JMenuItem reload = new JMenuItem("Reload");
		reload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel1.updateAnnunciatorFile();
			}
			
		});
		
		menu.add(reload);

	}

	public void addMenuItem(JMenu submenu, Annunciator annun, String name) {
		
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(name);
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(item.isSelected()) {
					if(annun != null) {
						annun.setVisible(true);
					}
				}else {
					if(annun != null) {
						annun.setVisible(false);
					}
				}
			}
			
		});
		submenu.add(item);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
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
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.preferences.set_preference(ZHSIPreferences.PREF_ANNUNWINDOW_WIDTH, "" + this.getWidth());
		this.preferences.set_preference(ZHSIPreferences.PREF_ANNUNWINDOW_HEIGHT, "" + this.getHeight());
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		this.preferences.set_preference(ZHSIPreferences.PREF_ANNUNWINDOW_POS_X, "" + this.getX());
		this.preferences.set_preference(ZHSIPreferences.PREF_ANNUNWINDOW_POS_Y, "" + this.getY());
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}

	public void updateAnnunciatorFile() {
		panel1.updateAnnunciatorFile();
	}
	
	public void updateAnnuns() {
		for(Annunciator anun : annuns) {
			anun.repaint();
		}
	}
	
	public static AnnunciatorWindow getInstance() {
		if(instance == null) {
			instance = new AnnunciatorWindow();
		}
		return instance;
	}
}

class AnnunPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger("org.andreels.zhsi");
	
	private static final String ANNUN_FILE = System.getProperty("user.dir") + "/ZHSI.annunciators";
	
	
	ArrayList<Integer> a_xpos = new ArrayList<Integer>();
	ArrayList<Integer> a_ypos = new ArrayList<Integer>();
	ArrayList<Integer> a_width = new ArrayList<Integer>();
	ArrayList<Integer> a_height = new ArrayList<Integer>();
	ArrayList<String> a_toptext = new ArrayList<String>();
	ArrayList<Integer> a_toptext_y = new ArrayList<Integer>();
	ArrayList<String> a_bottomtext = new ArrayList<String>();
	ArrayList<Integer> a_bottomtext_y = new ArrayList<Integer>();
	ArrayList<Float> a_textsize = new ArrayList<Float>();
	ArrayList<String> a_dref = new ArrayList<String>();
	ArrayList<Integer> a_value = new ArrayList<Integer>();
	ArrayList<Color> a_color = new ArrayList<Color>();


	public Graphics2D g2;
	protected ZHSIPreferences preferences;
	protected LoadResources rs;
	protected ExtPlaneInterface iface;
	protected AffineTransform original_trans = new AffineTransform();
	FontMetrics fm;
	Stroke stroke = new BasicStroke();
	Alert alert = new Alert(AlertType.ERROR);
	
	Thread sub;
	private Boolean subbed = false;
	
	Observer<DataRef> annunciators;
	
	public AnnunPanel() {
		
		
		this.rs = LoadResources.getInstance();
		iface = ExtPlaneInterface.getInstance();
		

		annunciators = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				repaint();
			}
			
		};
		
		readAnnunciatorFile();
	
	}
	
	private void readAnnunciatorFile() {
		
		try {
			File file = new File(ANNUN_FILE);
			if(file.exists()) {
			
				FileInputStream file_stream = new FileInputStream(ANNUN_FILE);
				BufferedReader file_reader = new BufferedReader(new InputStreamReader(file_stream));
				String line;
				int line_number = 1;
				String[] tokens;
				int x = 0;
				int y = 0;
				int width = 0;
				String topText = null;
				String bottomText = null;
				String dRef = null;
				int val1 = 0;
				Color col1 = null;
				int val2 = 0;
				Color col2 = null;
				
				file_reader.readLine();
				String line1=null;
				//x,y,width,height,toptext,bottomtext,text1size,toptext_y,bottomtext_y,dref,value1,color1
				while ((line1 = file_reader.readLine()) != null) {
					if (line1.length() > 0) {
						line_number++;
						tokens = line1.split(",");
						if(tokens.length >= 12) {
							try {
								a_xpos.add(Integer.parseInt(tokens[0]));
								
							}catch(NumberFormatException e) {
								logger.info("ZHSI Annunciators File Error: Invalid x value, pos:0, line:" + line_number + ". Must be a number\"");
								a_xpos.add(-500);
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid x value, pos:0, line:" + line_number + ". Must be a number\"");
								alert.show();
							}
							try {
								a_ypos.add(Integer.parseInt(tokens[1]));
							}catch(NumberFormatException e) {
								logger.info("ZHSI Annunciators File Error: Invalid y value, pos:1, line:" + line_number + ". Must be a number\"");
								a_ypos.add(-500);
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid y value, pos:1, line:" + line_number + ". Must be a number\"");
								alert.show();
							}
							try {
								a_width.add(Integer.parseInt(tokens[2]));
							}catch(NumberFormatException e) {
								logger.info("ZHSI Annunciators File Error: Invalid width value, pos:2 line:" + line_number + ". Must be a number\"");
								a_width.add(0);
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid width value, pos:2 line:" + line_number + ". Must be a number\"");
								alert.show();
							}
							try {
								a_height.add(Integer.parseInt(tokens[3]));
							}catch(NumberFormatException e) {
								logger.info("ZHSI Annunciators File Error: Invalid height value, pos:3, line:" + line_number + ". Must be a number\"");
								a_height.add(0);
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid height value, pos:3, line:" + line_number + ". Must be a number\"");
								alert.show();
							}
							
							if(tokens[4].equals("null") || tokens[4].equals("")) {
								a_toptext.add(null);
								logger.info("ZHSI Annunciators File Error: Invalid toptext value, pos:4, line:" + line_number);
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid toptext value, pos:4, line:" + line_number);
								alert.show();
							}else {
								a_toptext.add(tokens[4]);
							}
							
							
							if(tokens[5].equals("null")) {
								a_bottomtext.add(null);
							}else {
								a_bottomtext.add(tokens[5]);
							}
							
							try {
								if(Float.parseFloat(tokens[6]) <= 0) {
									logger.info("ZHSI Annunciators File Error: Invalid textsize value, pos:6, line:" + line_number + ". Must be a positive number");
									a_textsize.add(0f);
									alert.setTitle("Annunciator File Error");
									alert.setHeaderText("Syntax Error in Annunciator File");
									alert.setContentText("ZHSI Annunciators File Error: Invalid textsize value, pos:6, line:" + line_number + ". Must be a positive number");
									alert.show();
								}
								a_textsize.add(Float.parseFloat(tokens[6]));
							}catch(NumberFormatException e) {
								logger.info("ZHSI Annunciators File Error: Invalid textsize value, pos:6, line:" + line_number + ". Must be a positive number");
								a_textsize.add(0f);
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid textsize value, pos:6, line:" + line_number + ". Must be a positive number");
								alert.show();
							}
							try {
								a_toptext_y.add(Integer.parseInt(tokens[7]));
							}catch(NumberFormatException e) {
								a_toptext_y.add(0);
								logger.info("ZHSI Annunciators File Error: Invalid toptext y value: pos:7, line:" + line_number + ". Must be a number");
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid toptext y value: pos:7, line:" + line_number + ". Must be a number");
								alert.show();
							}
		
							try {
								a_bottomtext_y.add(Integer.parseInt(tokens[8]));
				
							}catch(NumberFormatException e) {
								logger.info("ZHSI Annunciators File Error: Invalid bottomtext y value: pos:8, line:" + line_number + ". Must be a number");
								a_bottomtext_y.add(0);
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid bottomtext y value: pos:8, line:" + line_number + ". Must be a number");
								alert.show();
							}
							a_dref.add(tokens[9]);

							try {
								a_value.add(Integer.parseInt(tokens[10]));
							}catch(NumberFormatException e) {
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid value: pos:10, line:" + line_number + ". Must be a number");
								alert.show();
								logger.info("ZHSI Annunciators File Error: Invalid value: pos:10, line:" + line_number + ". Must be a number");
							}
							String tempc = tokens[11].toUpperCase();
							//color = amber/green/red/white/blue
							if(tempc.equals("RED")) {
								a_color.add(Color.RED);
							}else if(tempc.equals("AMBER")) {
								a_color.add(rs.color_amber);
							}else if (tempc.equals("GREEN")) {
								a_color.add(Color.GREEN);
							}else if (tempc.equals("WHITE")) {
								a_color.add(Color.WHITE);
							}else if (tempc.equals("BLUE")) {
								a_color.add(Color.BLUE);
							}else {
								a_color.add(null);
								alert.setTitle("Annunciator File Error");
								alert.setHeaderText("Syntax Error in Annunciator File");
								alert.setContentText("ZHSI Annunciators File Error: Invalid Color, pos:11, line:" + line_number + ". Options are (RED, AMBER, GREEN, WHITE, BLUE)");
								alert.show();
								logger.info("ZHSI Annunciators File Error: Invalid Color, pos:11, line:" + line_number + ". Options are (RED, AMBER, GREEN, WHITE, BLUE)");
							}
						}else {
							logger.info("ZHSI Annunciators File Error: Not enough arguments: for line(" + line_number + ")");
							alert.setTitle("Annunciator File Error");
							alert.setHeaderText("Syntax Error in Annunciator File");
							alert.setContentText("ZHSI Annunciators File Error: Not enough arguments: for line(" + line_number + ")");
							alert.show();
						
						}
					}
				}
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}


		if(sub != null) {
			if(sub.isAlive()) {
				sub.interrupt();
			}
			sub = null;
		}
			
		sub = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while(!Thread.interrupted()) {
					if(!ZHSIStatus.zibo_loaded) {
						repaint();
						for(String dref : a_dref) {
							iface.excludeDataRef(dref);
							iface.unObserveDataRef(dref, annunciators);
						}
						subbed = false;	
					}else {
						//sub refs
						if(!subbed) {
							for(String dref : a_dref) {
								iface.includeDataRef(dref);
								iface.observeDataRef(dref, annunciators);
							}
							repaint();
							subbed = true;
						}
					}
					try {
						Thread.sleep(500);
					} 
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}		
		
		});
		
		sub.start();

	}
	
	public void updateAnnunciatorFile() {
		
		for(String dref : a_dref) {
			iface.excludeDataRef(dref);
			iface.unObserveDataRef(dref, annunciators);
		}
		subbed = false;
		a_xpos.clear();
		a_ypos.clear();
		a_width.clear();
		a_height.clear();
		a_toptext.clear();
		a_toptext_y.clear();
		a_bottomtext.clear();
		a_bottomtext_y.clear();
		a_textsize.clear();
		a_dref.clear();
		a_value.clear();
		a_color.clear();

		readAnnunciatorFile();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2 = (Graphics2D) g;
		original_trans = g2.getTransform();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);


		for(int i = 0; i < a_xpos.size(); i++) {
			
			int st = a_width.get(i) / 40;
			
			g2.setColor(rs.color_annun_bg);
			
			g2.fillRoundRect(a_xpos.get(i), a_ypos.get(i), a_width.get(i), a_height.get(i), 3 * st, 3 * st);
			
			g2.setColor(Color.BLACK);
			
			g2.fillRect(a_xpos.get(i) + st, a_ypos.get(i) +  st, a_width.get(i) - st * 2, a_height.get(i) - st * 2);
			
			
			
			boolean lit = false;
			float v = 0f;
			
			if(!ZHSIStatus.zibo_loaded) {
				lit = false;
			}else {
				if(a_dref.get(i) != null) {
					String[] val = iface.getDataRefValue(a_dref.get(i));
					if(val != null) {
						v = Float.parseFloat(val[0]);
						if(v > 0) {
							lit = true;
						}else {
							lit = false;
						}
					}
				}
			}
			g2.setFont(rs.glassFont.deriveFont(a_textsize.get(i)));
			fm = g2.getFontMetrics(g2.getFont());
			if(lit) {
				if(v >= 0.5f && v < 1) {
					g2.setColor(a_color.get(i).darker());
				}else {
					g2.setColor(a_color.get(i));
				}
			}else {
				g2.setColor(rs.color_annun_bg);
			}
			st = a_width.get(i) / 35;
			g2.drawRect(a_xpos.get(i) + st, a_ypos.get(i) +  st, a_width.get(i) - st * 2, a_height.get(i) - st * 2);
			
			if(a_bottomtext.get(i) != null) {
				if(a_toptext.get(i) != null) {
					g2.drawString(a_toptext.get(i).toUpperCase(), (a_xpos.get(i) + a_width.get(i) /2) - fm.stringWidth(a_toptext.get(i).toUpperCase()) /2, a_ypos.get(i) + fm.getHeight() / 2 +  a_toptext_y.get(i));
				}
				g2.drawString(a_bottomtext.get(i).toUpperCase(), (a_xpos.get(i) + a_width.get(i) /2) - fm.stringWidth(a_bottomtext.get(i).toUpperCase()) /2, a_ypos.get(i) + fm.getHeight() /2 +  a_bottomtext_y.get(i));
			}else {
				if(a_toptext.get(i) != null) {
					g2.drawString(a_toptext.get(i).toUpperCase(), (a_xpos.get(i) + a_width.get(i) /2) - fm.stringWidth(a_toptext.get(i).toUpperCase()) /2, a_ypos.get(i) + fm.getHeight() /2 +  a_toptext_y.get(i));
				}
			}
			
			
		}
		
	}

}
