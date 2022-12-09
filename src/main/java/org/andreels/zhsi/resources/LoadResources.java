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
package org.andreels.zhsi.resources;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class LoadResources extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static LoadResources instance = null;

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");
	

	//fonts
	public Font glassFont;
	public InputStream glassFontStream;
	public Font xraasFont;
	public InputStream xraasFontStream;
	public Font isfdFont;
	public InputStream isfdFontStream;
	public Font isfdThinFont;
	public InputStream isfdThinFontStream;
	public Font chronoFont;
	public InputStream chronoFontStream;
	public Font chronoBFont;
	public InputStream chronoBFontStream;
	public Font chronoSegFont;
	public InputStream chronoSegFontStream;
	public Font elecPanelFont;
	public InputStream elecPanelFontStream;
	public Font irsPanelFont;
	public InputStream irsPanelFontStream;
	
	public Font glass16;
	public Font glass18;
	public Font glass20;
	public Font glass22;
	public Font glass24;
	public Font glass26;
	public Font glass28;
	public Font glass30;
	public Font glass32;
	public Font glass34;
	public Font glass36;
	public Font glass38;
	public Font glass40;
	public Font glass42;
	public Font glass44;
	public Font glass46;
	public Font verdana26 = new Font("Verdana", Font.PLAIN, 26);
	
	
	//strokes
	private float[] dash10 = {10f,10f};
	private float[] dash1530 = {15f,30f};
	private float[] dash155 = {15f,5f};
	public Stroke stroke2 = new BasicStroke(2f);
	public Stroke stroke2_5 = new BasicStroke(2.5f);
	public Stroke stroke2_5round = new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	public Stroke stroke3 = new BasicStroke(3f);
	public Stroke stroke4 = new BasicStroke(4f);
	public Stroke stroke4round = new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	public Stroke stroke4dash10 = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash10, 0.0f);
	public Stroke stroke5 = new BasicStroke(5f);
	public Stroke stroke5dash10 = new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash10, 0.0f);
	public Stroke stroke5dash1530 = new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1530, 0.0f);
	public Stroke stroke5dash155 = new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash155, 0.0f);
	public Stroke stroke5round = new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	public Stroke stroke6 = new BasicStroke(6f);
	
	
	
	//formats
	public DecimalFormat df2 = new DecimalFormat("00");
	public DecimalFormat df3 = new DecimalFormat("000");
	public DecimalFormat df4 = new DecimalFormat("0000");
	public DecimalFormat qnh_in = new DecimalFormat("00.00");
	
	
	private Rectangle2D horizon_bg = new Rectangle2D.Float();
	private Rectangle2D horizon_ground = new Rectangle2D.Float();
	private Rectangle2D horizon_sky = new Rectangle2D.Float();
	private Line2D horizon_line = new Line2D.Float();
	private Color horizon_ground_color = new Color(0x5C3603); // was 0x995720  0x6E4424
	private Color horizon_sky_color = new Color(0x0172DE); //  was 0x0099CD 0x00BFFF was 0x3775B1
	
	//Colors
	public Color color_magenta = new Color(0xFA7CFA);
	public Color color_lime = new Color(0x00FF00);
	public Color color_white = new Color(0xFFFFFF);
	public Color color_markings = new Color(0xE5E5E5);
	public Color color_amber = new Color(0xFBA81F);
	public Color color_red = new Color(0xFF0000);
	public Color color_navaid = new Color(0x00CCFF); //was  0x4D88FF
	public Color color_instrument_gray = new Color(0x353539);
	public Color color_chrono = new Color(0xD8EAFF);
	
	public Color color_annun_bg = new Color(0x191919);
	
	//PFD

//	public BufferedImage img_horizon = new BufferedImage(2344, 2344, BufferedImage.TYPE_INT_ARGB);
//	private Graphics2D horizon = img_horizon.createGraphics();
	
	public BufferedImage img_slipIndicator = new BufferedImage(40, 12, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D slipIndicator = img_slipIndicator.createGraphics();
	

	
	public BufferedImage img_HeadingTrack = new BufferedImage(750, 200, BufferedImage.TYPE_INT_ARGB);
	public Graphics2D _headingTrack = img_HeadingTrack.createGraphics();
	
	public BufferedImage img_CompassRose = new BufferedImage(1517, 400, BufferedImage.TYPE_INT_ARGB);
	public Graphics2D _compassRose = img_CompassRose.createGraphics();
	
	public BufferedImage img_FMA;
	public BufferedImage img_vsTape;
	public BufferedImage img_headingTrackBug;
	//public BufferedImage img_headingTrackCricle;
	public BufferedImage img_headingTrack_headingMarker;
	//public BufferedImage img_trackIndicator;
	public BufferedImage img_bankIndicators;
	public BufferedImage img_bankPointer;
	public BufferedImage img_bankPointer_amber;
	public BufferedImage img_speedBox;
	public BufferedImage img_altBox;
	public BufferedImage img_altBox_bg;
	public BufferedImage img_altBox_mtrs;
	public BufferedImage img_altBox_mtrs_bg;
	public BufferedImage img_green_hatch;
//	public BufferedImage img_pitchMarkings;
	public BufferedImage img_aircraftWings;
	public BufferedImage img_aircraftNose;
	public BufferedImage img_fd2_1;
	public BufferedImage img_wings2_1;
	public BufferedImage img_speedBug;
	public BufferedImage img_altBug;
	public BufferedImage img_hdef_dots;
	public BufferedImage img_hdef_dots_a;
	public BufferedImage img_hdef_exp_loc;
	public BufferedImage img_hdef_exp_loc_a;
	public BufferedImage img_hdef_ind;
	public BufferedImage img_hdef_ind_filled;
	public BufferedImage img_hdef_ind_ghost;
	public BufferedImage img_vdef_dots;
	public BufferedImage img_vdef_dots_a;
	public BufferedImage img_vdef_ind;
	public BufferedImage img_vdef_ind_filled;
	public BufferedImage img_vdef_ind_ghost;
	public BufferedImage img_landing_alt_indicator;
	public BufferedImage img_green_baro_bug;
	public BufferedImage img_amber_baro_bug;
	public BufferedImage img_green_baro_pointer;
	public BufferedImage img_amber_baro_pointer;
	public BufferedImage img_horiz_nps_scale;
	public BufferedImage img_vert_nps_scale;
	public BufferedImage img_vert_nps_scale_2;
	public BufferedImage img_horiz_nps_pointer;
	public BufferedImage img_vert_nps_pointer;
	
	//ND
	public BufferedImage img_nd_arpt;
	public BufferedImage img_nd_arpt_fix;
	public BufferedImage img_nd_dme;
	public BufferedImage img_nd_dme_tuned;
	public BufferedImage img_nd_dme_fix;
	public BufferedImage img_nd_vor;
	public BufferedImage img_nd_vor_tuned;
	public BufferedImage img_nd_vor_fix;
	public BufferedImage img_nd_vortac;
	public BufferedImage img_nd_vortac_tuned;
	public BufferedImage img_nd_vortac_fix;
	public BufferedImage img_nd_wpt;
	public BufferedImage img_nd_wpt_fix;
	//
//	public BufferedImage img_top_mask;
//	public BufferedImage img_compass_rose_text;
//	public BufferedImage img_compass_rose_mask;
//	public BufferedImage img_compass_rose;
	public BufferedImage img_ctr_compass_rose;
//	public BufferedImage img_heading_box;
	public BufferedImage img_plane_triangle;
	public BufferedImage img_plane_pln;
	public BufferedImage img_plane_vor_symbol;
//	public BufferedImage img_track_box;
//	public BufferedImage img_range_markers;
	public BufferedImage img_white_waypoint;
	public BufferedImage img_magenta_waypoint;
	public BufferedImage img_white_flyover_waypoint;
	public BufferedImage img_magenta_flyover_waypoint;
//	public BufferedImage img_pln_circles;
//	public BufferedImage img_pln_mask;
//	public BufferedImage img_selected_heading_bug;
//	public BufferedImage img_selected_heading_line;
//	public BufferedImage img_heading_pointer;
//	public BufferedImage img_track_line;
//	public BufferedImage img_wind_arrow;
	public BufferedImage img_Adf1_Arrow;
	public BufferedImage img_Adf2_Arrow;
	public BufferedImage img_Vor1_Arrow;
	public BufferedImage img_Vor2_Arrow;
	public BufferedImage img_Vor1_Arrow_ctr;
	public BufferedImage img_Vor2_Arrow_ctr;
	public BufferedImage img_Adf1_Arrow_ctr;
	public BufferedImage img_Adf2_Arrow_ctr;
//	public BufferedImage img_Range_Arcs;
	public BufferedImage img_Selected_Course_Line;
	public BufferedImage img_Selected_Course_Line_ctr;
	//
	public BufferedImage img_tcas_amber_arrowdn;
	public BufferedImage img_tcas_amber_arrowup;
	public BufferedImage img_tcas_ambercircle;
	public BufferedImage img_tcas_diamond;
	public BufferedImage img_tcas_red_arrowdn;
	public BufferedImage img_tcas_red_arrowup;
	public BufferedImage img_tcas_redbox;
	public BufferedImage img_tcas_solid_diamond;
	public BufferedImage img_tcas_white_arrowdn;
	public BufferedImage img_tcas_white_arrowup;
	
	//isfd
	public BufferedImage img_isfd_compass;
	public BufferedImage img_isfd_wings;
	public BufferedImage img_isfd_bank_pointer;
	
	
	//failure flags
	public BufferedImage img_alt_dis;
	public BufferedImage img_att_fail;
	public BufferedImage img_hdg_fail;
	public BufferedImage img_ias_dis;
	public BufferedImage img_ldg_alt_fail;
	public BufferedImage img_no_vspd;
	public BufferedImage img_vert_fail;
	public BufferedImage img_map_fail;
	
	private LoadResources() {
		
		try {

			glassFontStream = getClass().getResourceAsStream("/org/andreels/zhsi/resources/boeingGlass.otf");
			glassFont = Font.createFont(Font.TRUETYPE_FONT, glassFontStream);
			xraasFontStream = getClass().getResourceAsStream("/org/andreels/zhsi/resources/VT323-Regular.ttf");
			xraasFont = Font.createFont(Font.TRUETYPE_FONT, xraasFontStream);
			isfdFontStream = getClass().getResourceAsStream("/org/andreels/zhsi/resources/Roboto-Regular.ttf");
			isfdFont = Font.createFont(Font.TRUETYPE_FONT, isfdFontStream);
			isfdThinFontStream = getClass().getResourceAsStream("/org/andreels/zhsi/resources/Roboto-Thin.ttf");
			isfdThinFont = Font.createFont(Font.TRUETYPE_FONT, isfdThinFontStream);
			chronoFontStream = getClass().getResourceAsStream("/org/andreels/zhsi/resources/CenturyGothic.ttf");
			chronoFont = Font.createFont(Font.TRUETYPE_FONT, chronoFontStream);
			chronoBFontStream = getClass().getResourceAsStream("/org/andreels/zhsi/resources/GOTHICB.TTF");
			chronoBFont = Font.createFont(Font.TRUETYPE_FONT, chronoBFontStream);
			chronoSegFontStream = getClass().getResourceAsStream("/org/andreels/zhsi/resources/DSEG7Classic-Regular.ttf");
			chronoSegFont = Font.createFont(Font.TRUETYPE_FONT, chronoSegFontStream);
			elecPanelFontStream = getClass().getResourceAsStream("/org/andreels/zhsi/resources/repet___.ttf");
			elecPanelFont = Font.createFont(Font.TRUETYPE_FONT, elecPanelFontStream);
			irsPanelFontStream = getClass().getResourceAsStream("/org/andreels/zhsi/resources/DSEG14Classic-Regular.ttf");
			irsPanelFont = Font.createFont(Font.TRUETYPE_FONT, irsPanelFontStream);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}
	
		this.glass16 = this.glassFont.deriveFont(16f);
		this.glass18 = this.glassFont.deriveFont(18f);
		this.glass20 = this.glassFont.deriveFont(20f);
		this.glass22 = this.glassFont.deriveFont(22f);
		this.glass24 = this.glassFont.deriveFont(24f);
		this.glass26 = this.glassFont.deriveFont(26f);
		this.glass28 = this.glassFont.deriveFont(28f);
		this.glass30 = this.glassFont.deriveFont(30f);
		this.glass32 = this.glassFont.deriveFont(32f);
		this.glass34 = this.glassFont.deriveFont(34f);
		this.glass36 = this.glassFont.deriveFont(36f);
		this.glass38 = this.glassFont.deriveFont(38f);
		this.glass40 = this.glassFont.deriveFont(40f);
		this.glass42 = this.glassFont.deriveFont(42f);
		this.glass44 = this.glassFont.deriveFont(44f);
		this.glass46 = this.glassFont.deriveFont(46f);
		
		try {
			//PFD
			img_FMA = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_FMA.png"));
			img_vsTape = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_vsTape.png"));
			img_headingTrackBug = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_headingTrackBug.png"));
//			img_headingTrackCricle = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_headingTrackCricle.png"));
			img_headingTrack_headingMarker = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_headingTrack_headingMarker.png"));
			img_bankIndicators = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_bankIndicators.png"));
			img_bankPointer = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_bankPointer.png"));
			img_bankPointer_amber = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_bankPointer_amber.png"));
			img_speedBox = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_speedBox.png"));
			img_altBox = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_altBox.png"));
			img_altBox_bg = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_altBox_bg.png"));
			img_altBox_mtrs = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_altBox_mtrs.png"));
			img_altBox_mtrs_bg = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_altBox_mtrs_bg.png"));
			img_green_hatch = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_green_hatch.png"));
//			img_pitchMarkings = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_pitchMarkings.png"));
			img_aircraftWings = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_aircraftWings.png"));
			img_aircraftNose = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_aircraftNose.png"));
			img_fd2_1 = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_fd2_1.png"));
			img_wings2_1 = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_wings2_1.png"));
			img_speedBug = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_speedBug.png"));
			img_altBug = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_altBug.png"));
			img_hdef_dots = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_hdef_dots.png"));
			img_hdef_dots_a = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_hdef_dots_a.png"));
			img_hdef_exp_loc = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_hdef_exp_loc.png"));
			img_hdef_exp_loc_a = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_hdef_exp_loc_a.png"));
			img_hdef_ind = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_hdef_ind.png"));
			img_hdef_ind_filled = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_hdef_ind_filled.png"));
			img_hdef_ind_ghost = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_hdef_ind_ghost.png"));
			img_vdef_dots = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_vdef_dots.png"));
			img_vdef_dots_a = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_vdef_dots_a.png"));
			img_vdef_ind = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_vdef_ind.png"));
			img_vdef_ind_filled = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_vdef_ind_filled.png"));
			img_vdef_ind_ghost = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_vdef_ind_ghost.png"));
			img_landing_alt_indicator = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_landing_alt_indicator.png"));
			img_green_baro_bug = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_green_baro_bug.png"));
			img_amber_baro_bug = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_amber_baro_bug.png"));
			img_green_baro_pointer = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_green_baro_pointer.png"));
			img_amber_baro_pointer = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_amber_baro_pointer.png"));
			img_horiz_nps_scale = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_horiz_nps_scale.png"));
			img_vert_nps_scale = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_vert_nps_scale.png"));
			img_vert_nps_scale_2 = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_vert_nps_scale_2.png"));
			img_horiz_nps_pointer = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_horiz_nps_pointer.png"));
			img_vert_nps_pointer = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_vert_nps_pointer.png"));
			img_slipIndicator = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/pfd/img_slipIndicator.png"));
			
			//ND
			img_nd_arpt = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_arpt.png"));
			img_nd_arpt_fix = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_arpt_fix.png"));
			img_nd_dme = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_dme.png"));
			img_nd_dme_tuned = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_dme_tuned.png"));
			img_nd_dme_fix = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_dme_fix.png"));
			img_nd_vor = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_vor.png"));
			img_nd_vor_tuned = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_vor_tuned.png"));
			img_nd_vor_fix = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_vor_fix.png"));
			img_nd_vortac = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_vortac.png"));
			img_nd_vortac_tuned = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_vortac_tuned.png"));
			img_nd_vortac_fix = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_vortac_fix.png"));
			img_nd_wpt = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_wpt.png"));
			img_nd_wpt_fix = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_nd_wpt_fix.png"));
			//
			//img_top_mask = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_top_mask.png"));
//			img_compass_rose_text = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_compass_rose_text.png"));
			//img_compass_rose_mask = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_compass_rose_mask.png"));
//			img_compass_rose = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_compass_rose.png"));
			//img_heading_box = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_heading_box.png"));
			img_plane_triangle = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_plane_triangle.png"));
			img_plane_vor_symbol = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_plane_vor_symbol.png"));
			img_plane_pln = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_plane_pln.png"));
			//img_track_box = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_track_box.png"));
			//img_range_markers = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_range_markers.png"));
			img_ctr_compass_rose = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_ctr_compass_rose.png"));
			img_white_waypoint = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_white_waypoint.png"));
			img_magenta_waypoint = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_magenta_waypoint.png"));
			img_white_flyover_waypoint = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_white_flyover_waypoint.png"));
			img_magenta_flyover_waypoint = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_magenta_flyover_waypoint.png"));
			//img_pln_circles = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_pln_circles.png"));
//			img_pln_mask = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_pln_mask.png"));
//			img_selected_heading_bug = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_selected_heading_bug.png"));
//			img_selected_heading_line = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_selected_heading_line.png"));
//			img_track_line = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_track_line.png"));
//			img_heading_pointer = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_heading_pointer.png"));
//			img_wind_arrow = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_wind_arrow.png"));
			img_Adf1_Arrow = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Adf1_Arrow.png"));
			img_Adf2_Arrow = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Adf2_Arrow.png"));
			img_Vor1_Arrow = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Vor1_Arrow.png"));
			img_Vor2_Arrow = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Vor2_Arrow.png"));
			img_Vor1_Arrow_ctr = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Vor1_Arrow_ctr.png"));
			img_Vor2_Arrow_ctr = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Vor2_Arrow_ctr.png"));
			img_Adf1_Arrow_ctr = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Adf1_Arrow_ctr.png"));
			img_Adf2_Arrow_ctr = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Adf2_Arrow_ctr.png"));
			//img_Range_Arcs = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Range_Arcs.png"));
			img_Selected_Course_Line = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Selected_Course_Line.png"));
			img_Selected_Course_Line_ctr = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_Selected_Course_Line_ctr.png"));
			//
			img_tcas_amber_arrowdn = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_amber_arrowdn.png"));
			img_tcas_amber_arrowup = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_amber_arrowup.png"));
			img_tcas_ambercircle = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_ambercircle.png"));
			img_tcas_diamond = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_diamond.png"));
			img_tcas_red_arrowdn = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_red_arrowdn.png"));
			img_tcas_red_arrowup = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_red_arrowup.png"));
			img_tcas_redbox = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_redbox.png"));
			img_tcas_solid_diamond = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_solid_diamond.png"));
			img_tcas_white_arrowdn = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_white_arrowdn.png"));
			img_tcas_white_arrowup = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/nd/img_tcas_white_arrowup.png"));
			
			//isfd
			img_isfd_compass = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/isfd/img_isfd_compass.png"));
			img_isfd_wings = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/isfd/img_isfd_wings.png"));
			img_isfd_bank_pointer = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/isfd/img_isfd_bank_pointer.png"));
			
			//failure msgs
			img_alt_dis = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/failureflags/img_alt_dis.png"));
			img_att_fail = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/failureflags/img_att_fail.png"));
			img_hdg_fail = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/failureflags/img_hdg_fail.png"));
			img_ias_dis = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/failureflags/img_ias_dis.png"));
			img_ldg_alt_fail = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/failureflags/img_ldg_alt_fail.png"));
			img_no_vspd = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/failureflags/img_no_vspd.png"));
			img_vert_fail = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/failureflags/img_vert_fail.png"));
			img_map_fail = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/failureflags/img_map_fail.png"));
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//draw horizon to BufferedImage
//		horizon.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//		horizon.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//				RenderingHints.VALUE_ANTIALIAS_ON);
//		horizon.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//		horizon_bg.setFrame(0, 0, 2344, 2344);
//		horizon.setColor(Color.WHITE);
//		horizon.fill(horizon_bg);
//		horizon_sky.setFrame(0, 0, 2344, 1172);
//		horizon.setColor(horizon_sky_color);
//		horizon.fill(horizon_sky);
//		horizon_ground.setFrame(0, 1172, 2344, 1172);
//		horizon.setColor(horizon_ground_color);
//		horizon.fill(horizon_ground);
//		horizon_line.setLine(0, 1172, 2344, 1172);
//		horizon.setStroke(new BasicStroke(3f));
//		horizon.setColor(Color.WHITE);
//		horizon.draw(horizon_line);
		
		slipIndicator.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		slipIndicator.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		slipIndicator.setColor(color_markings);
		slipIndicator.setStroke(stroke3);
		slipIndicator.drawRect(2, 2, 36, 8);

	}


	public static LoadResources getInstance() {
		if(instance == null) {
			instance = new LoadResources();
		}
		return instance;
	}
}