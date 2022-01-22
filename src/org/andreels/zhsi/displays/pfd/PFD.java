/**
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
 
package org.andreels.zhsi.displays.pfd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.displays.DUBaseClass;
import org.andreels.zhsi.navdata.Localizer;
import org.andreels.zhsi.navdata.NavigationRadio;
import org.andreels.zhsi.navdata.RadioNavigationObject;


public class PFD extends DUBaseClass {

	private static final long serialVersionUID = 1L;

	NavigationRadio nav_radio;
	NavigationRadio nav1_radio;
	NavigationRadio nav2_radio;
	
	private AffineTransform headingTransForm;

	private Line2D speedMarkings = new Line2D.Float();
	private Line2D altMarkings = new Line2D.Float();
	private Line2D alt500Markings = new Line2D.Float();
	private Line2D alt1kMarkings = new Line2D.Float();
	private Line2D alt1kTopLine = new Line2D.Float();
	private Line2D alt1kBottomLine = new Line2D.Float();
	private Line2D vsNeedle = new Line2D.Float();
	private Line2D vsApBug1 = new Line2D.Float();
	private Line2D vsApBug2 = new Line2D.Float();
	private Line2D fd_hor_bar = new Line2D.Float();
	private Line2D fd_ver_bar = new Line2D.Float();
	private Line2D tcas_hor_bar = new Line2D.Float();
	private Line2D tcas_left_bar = new Line2D.Float();
	private Line2D tcas_right_bar = new Line2D.Float();

	private Rectangle2D speedTape = new Rectangle2D.Float();
	private Rectangle2D altTape = new Rectangle2D.Float();
	private Rectangle2D slip_deg = new Rectangle2D.Float();
	private Rectangle2D altBox_clip = new Rectangle2D.Float();
	private Rectangle2D speedBox_clip = new Rectangle2D.Float();
	private Rectangle2D rec_hdg_mode = new Rectangle2D.Float();
	private Rectangle2D rec_alt_modes = new Rectangle2D.Float();
	private Rectangle2D rec_thr_modes = new Rectangle2D.Float();
	private Rectangle2D rec_thr2_modes = new Rectangle2D.Float();
	private Rectangle2D qnh_amber_box = new Rectangle2D.Float();
	private Arc2D.Float aoaArc = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float roundRadioAtl = new Arc2D.Float(Arc2D.OPEN);
	private RoundRectangle2D adiClip = new RoundRectangle2D.Float();
	private Rectangle2D fdRec = new Rectangle2D.Float();
	private RoundRectangle2D pitchMarkingsClip = new RoundRectangle2D.Float();
	private Rectangle2D vsTapeClip = new Rectangle2D.Float();
	
	private int[] single_ch_Rec_xPoints = {415, 555, 555, 520, 520, 455, 455, 415};
	private int[] single_ch_Rec_yPoints = {150, 150, 200, 200, 245, 245, 200, 200};
	private int[] white_bug_x = new int[5];
	private int[] white_bug_y = new int[5];
	private int[] speed_trend_arrow_x = new int[3];
	private int[] speed_trend_arrow_y = new int[3];
	
	private BufferedImage img_alt20 = new BufferedImage(55, 300, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D _alt20 = img_alt20.createGraphics();
	private BufferedImage img_ias10 = new BufferedImage(30, 300, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D _ias10 = img_ias10.createGraphics();
	
	private BufferedImage img_SpeedTape = new BufferedImage(145, 756, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D _speedTape = img_SpeedTape.createGraphics();
	private BufferedImage img_AltTape = new BufferedImage(145, 756, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D _altTape = img_AltTape.createGraphics();
	
	private Stroke alt_marking_stroke = new BasicStroke(4f);
	private Stroke alt500_marking_stroke = new BasicStroke(8f);
	private Stroke alt1k_marking_stroke = new BasicStroke(9f);
	private Stroke alt1k_lines_stroke = new BasicStroke(4f);
	private Font alt_hundred_font = rs.glassFont.deriveFont(26f);
	private Font alt_thousand_font = rs.glassFont.deriveFont(34f);
	private Font ias_tape_font = rs.glassFont.deriveFont(32f);

	private Color horizon_ground_color = new Color(0x5C3603); // was 0x995720  0x6E4424
	private Color horizon_sky_color = new Color(0x0172DE); //  was 0x0099CD 0x00BFFF was 0x3775B1
	
	// class variables
	
	float ias;
	float heading;
	float alt;
	float roll;
	float pitch;
	float landing_alt;
	
	long time_plus_ten_seconds = 0;
	float airspeed_mach_prev = 0;
	
	public PFD(ModelFactory model_factory, String title, String pilot) {
		
		super(model_factory, title, pilot);		
	
	}

	public void drawInstrument(Graphics2D g2) {
			
		ias = this.xpd.airspeed(pilot);

		if (ias < 45) {
			ias = 45f;
		}

		heading = this.xpd.heading(pilot);
		alt = this.xpd.altitude(pilot);
		landing_alt = this.xpd.rwy_altitude();
		roll = this.xpd.roll();
		pitch = this.xpd.pitch() * 11.5f;
		
		adiClip.setRoundRect(225.551 * gc.scalex, 262 * gc.scaley, 519 * gc.scalex, 529 * gc.scaley, 100 * gc.scaling_factor, 100 * gc.scaling_factor);

		if (this.xpd.power_on()) { // only draw if the aircraft has power
		
			if ((this.pilot == "cpt" && this.xpd.power_on()) || (this.pilot == "fo" && this.xpd.dc_standby_on())) {
								
				// ADI
				drawADI(g2);

				// FMA
				drawFMA(g2);
				
				// Vs Tape
				drawVsTape(g2);

				// Alt Tape
				drawAltTape(g2);

				// Speed Tape
				drawSpeedTape(g2);

				// Angle of Attack
				if(preferences.get_preference(ZHSIPreferences.PREF_AOA_INDICATOR).equals("true")) {
					drawAoA(g2);
				}else {
					// Upper Radio Altitude
					drawUpperRadioAltitude();
				}
				
				// ILS
				drawILS(g2);

				// Heading Track
				drawHeadingTrack(g2);
				
				// Vert Path Scale
				if(this.xpd.pfd_vert_path(pilot)) {
					drawVertPathScale();
				} else if(this.xpd.pfd_vert_path2(pilot)) {
					drawVertPath2Scale();
				}
				
				// Horiz Path Scale
				if(this.xpd.pfd_trk_path(pilot)) {
					drawHorizPathScale();
				}

				// Failure Flags
				drawFailureFlags(g2);
				
				// Pitch Limit Indication
				if(this.xpd.stall_show()) {
					drawPitchLimitIndication(g2);
				}
				
				// Rising Runway
				if(this.xpd.runway_show(pilot)) {
					drawRisingRunway(g2);
				}
				
				// Lower Radio Altitude
				if(preferences.get_preference(ZHSIPreferences.PREF_AOA_INDICATOR).equals("true")) {
					drawLowerRadioAltitude(g2);
				}
				
				// Draw markers
				drawMarkers(g2);
				
			}
		}
	}

	private void drawVertPathScale() {
		
		if(this.xpd.irs_aligned()) {

			float ydelta = (280f * this.xpd.vnav_err()) / 800f;
		
			if(ydelta > 140) {
				ydelta = 140;
			}else if(ydelta < -140) {
				ydelta = -140;
			}
			
			gc.displayImage(rs.img_vert_nps_scale, 0f, 745.431f, 375.066f, g2);
			gc.displayImage(rs.img_vert_nps_pointer, 0f, 754.178f, 519.218f + ydelta, g2);
			
		}
		
	}
	
	private void drawVertPath2Scale() {
		
		if(this.xpd.irs_aligned()) {
		
			gc.displayImage(rs.img_vert_nps_scale_2, 0f, 745.431f, 375.066f, g2);
		
		}
		
	}

	private void drawHorizPathScale() {
		
		// if(this.xpd.irs_aligned() && this.xpd.fms_legs_num() >= 1) {
		if(this.xpd.irs_aligned() && this.xpd.pfd_rwy_show()) {
	
			float xdelta = this.xpd.nps_deviation() * -56f;
			gc.displayImage(rs.img_horiz_nps_pointer, 0f, 477.718f + xdelta, 233.372f, g2);
			gc.displayImage(rs.img_horiz_nps_scale, 0f, 333.57f, 239.881f, g2);

		}
		
	}
	
	private void drawLowerRadioAltitude(Graphics2D g2) {
			
			if (this.xpd.radio_alt_feet() < 2500) {
				//altimeter
				String radio_alt = "";
				if (this.xpd.radio_alt_feet() <= 100) {
					radio_alt = String.format("%d", Math.round(this.xpd.radio_alt_feet()));
				} else if (this.xpd.radio_alt_feet() <= 500) {
					radio_alt = String.format("%d", Math.round(this.xpd.radio_alt_feet() / 10) * 10);
				} else {
					radio_alt = String.format("%d", Math.round(this.xpd.radio_alt_feet() / 20) * 20);
				}
				if(this.xpd.on_gound()) {
					radio_alt = "-4";
				}	
				g2.setColor(Color.BLACK);
				g2.fillRect((int)(420 * gc.scalex), (int)(730 * gc.scaley), (int)(130 * gc.scaley), (int)(50 * gc.scaley));
				g2.setColor(Color.WHITE);
				gc.drawText(radio_alt, 485, 281, 40, 0, 0, 0, "center", g2);
				g2.setTransform(original_trans);
			}

	}

	private void drawUpperRadioAltitude() {

		float radioAlt = this.xpd.radio_alt_feet();
		String radio_alt = "";
		if (this.xpd.radio_alt_feet() <= 100) {
			radio_alt = String.format("%d", Math.round(this.xpd.radio_alt_feet()));
		} else if (this.xpd.radio_alt_feet() <= 500) {
			radio_alt = String.format("%d", Math.round(this.xpd.radio_alt_feet() / 10) * 10);
		} else {
			radio_alt = String.format("%d", Math.round(this.xpd.radio_alt_feet() / 20) * 20);
		}
		float radio_alt_rotate = (radioAlt / 1000f) * 360f;

		g2.scale(gc.scalex, gc.scaley);
		g2.translate(630, 100);
		if (this.xpd.radio_alt_feet() < this.xpd.minimums(pilot)) {
			g2.setColor(gc.color_amber);
		}else {
			g2.setColor(gc.color_markings);
		}

		g2.setStroke(gc.stroke_nine);

		roundRadioAtl.setFrame(0, 0, 140, 140);
		roundRadioAtl.setAngleStart(90);
		roundRadioAtl.setAngleExtent(radio_alt_rotate * -1);
		if(!this.xpd.on_gound() && radioAlt < 999) {
			g2.draw(roundRadioAtl);
			g2.setStroke(gc.stroke_six);
			g2.drawLine(70, 12, 70, 1);	
			for(int i = 0; i < 9; i++) {
				g2.rotate(Math.toRadians(36), 70, 70);
				g2.drawLine(70, 12, 70, 1);
			}
		}
		if(this.xpd.on_gound() && ias > 46) {
			g2.setStroke(gc.stroke_six);
			g2.drawLine(70, 8, 70, 1);	
			for(int i = 0; i < 9; i++) {
				g2.rotate(Math.toRadians(36), 70, 70);
				g2.drawLine(70, 8, 70, 1);
			}
		}

		g2.setTransform(original_trans);

		g2.scale(gc.scalex, gc.scaley);
		g2.translate(630, 100);

		if (this.xpd.radio_alt_feet() < 999) {
			if(this.xpd.on_gound()) {
				g2.setColor(gc.color_markings);
				radio_alt = "-4";
			}

			g2.setFont(rs.glassFont.deriveFont(42f));
			g2.drawString(radio_alt, 34, 90);
		}
		g2.setTransform(original_trans);

		if(!this.xpd.on_gound() && radioAlt < 999) {
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(630, 100);
			if (this.xpd.radio_alt_feet() < this.xpd.minimums(pilot)) {
				g2.setColor(gc.color_amber);
			} else {
				g2.setColor(gc.color_lime);
			}
			g2.rotate(Math.toRadians((this.xpd.minimums(pilot) / 1000f) * 360f), 70, 70);
			Polygon triangle = new Polygon();
			triangle.addPoint(70, 10);
			triangle.addPoint(63, 23);
			triangle.addPoint(77, 23);
			if(this.xpd.minimums(pilot) > 0 && this.xpd.radio_alt_feet() < 998) {
				g2.draw(triangle);
			}
			g2.setTransform(original_trans);
		}

		if(this.xpd.radio_alt_feet() < 2500 && this.xpd.radio_alt_feet() > 998) {
			g2.setColor(gc.color_markings);
			gc.drawText(radio_alt, 695, 860, 48, 0, 0, 0, "center", g2);
		}
		
	}
	
	private void drawPitchLimitIndication(Graphics2D g2) {
		
		if (this.xpd.irs_aligned()) {
		
			g2.setColor(gc.color_amber);
			g2.scale(gc.scalex, gc.scaley);
			g2.setStroke(rs.stroke4);
			g2.drawLine(360, 390, 400, 390);
			g2.drawLine(400, 390, 400, 400);
			g2.setStroke(rs.stroke3);
			g2.drawLine(395, 390, 385, 390 - (int)this.xpd.stall());
			g2.drawLine(385, 390, 375, 390 - (int)this.xpd.stall());
			g2.drawLine(375, 390, 365, 390 - (int)this.xpd.stall());
			g2.setStroke(rs.stroke4);
			g2.drawLine(570, 390, 610, 390);
			g2.drawLine(570, 390, 570, 400);
			g2.setStroke(rs.stroke3);
			g2.drawLine(575, 390, 585, 390 - (int)this.xpd.stall());
			g2.drawLine(585, 390, 595, 390 - (int)this.xpd.stall());
			g2.drawLine(595, 390, 605, 390 - (int)this.xpd.stall());
			g2.setTransform(original_trans);
			
		}
		
	}
	
	private void drawRisingRunway(Graphics2D g2) {
		
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		
		int x_min = 230;
		int x_max = 740;
		
		if (this.xpd.irs_aligned()) {
			
			g2.setColor(gc.color_lime);

			g2.scale(gc.scalex, gc.scaley);
			g2.setStroke(rs.stroke4);
			
			x1 = 485 - 120 + (int)(this.xpd.runway_x(pilot) * 2.3);
			y1 = 565 - (int)this.xpd.runway_y(pilot) * 2;
			x2 = 485 + 120 + (int)(this.xpd.runway_x(pilot) * 2.3);
			y2 = 565 - (int)this.xpd.runway_y(pilot) * 2;
			
			if (x1 < x_min) {
				x1 = x_min;
			}
			
			if (x2 > x_max) {
				x2 = x_max;
			}
			
			g2.drawLine(x1,y1, x2, y2);
			
			x1 = 485 - 140 + (int)(this.xpd.runway_x(pilot) * 2.3);
			y1 = 595 - (int)this.xpd.runway_y(pilot) * 2;
			x2 = 485 + 140 + (int)(this.xpd.runway_x(pilot) * 2.3);
			y2 = 595 - (int)this.xpd.runway_y(pilot) * 2;
			
			if (x1 < x_min) {
				x1 = x_min;
			}
			
			if (x2 > x_max) {
				x2 = x_max;
			}
			
			g2.drawLine(x1,y1, x2, y2);
			
			x1 = 485 - 120 + (int)(this.xpd.runway_x(pilot) * 2.3);
			y1 = 565 - (int)this.xpd.runway_y(pilot) * 2;
			x2 = 485 - 140 + (int)(this.xpd.runway_x(pilot) * 2.3);
			y2 = 595 - (int)this.xpd.runway_y(pilot) * 2;
			
			if (x2 > x_min) {
				g2.drawLine(x1,y1, x2, y2);
			}
			
			x1 = 485 + 120 + (int)(this.xpd.runway_x(pilot) * 2.3);
			y1 = 565 - (int)this.xpd.runway_y(pilot) * 2;
			x2 = 485 + 140 + (int)(this.xpd.runway_x(pilot) * 2.3);
			y2 = 595 - (int)this.xpd.runway_y(pilot) * 2;
			
			if (x2 < x_max) {
				g2.drawLine(x1,y1, x2, y2);
			}
			
			// n/a
			
			g2.drawLine(485 + (int)(this.xpd.runway_x(pilot) * 2.3), 565 - (int)this.xpd.runway_y(pilot) * 2, 485 + (int)(this.xpd.runway_x(pilot) * 2.3), 595 - (int)this.xpd.runway_y(pilot) * 2);
			
			g2.setColor(gc.color_magenta);
			
			g2.drawLine(480 + (int)(this.xpd.runway_x(pilot) * 2.3), 600 - (int)this.xpd.runway_y(pilot) * 2, 480 + (int)(this.xpd.runway_x(pilot) * 2.3), 770);
			g2.drawLine(490 + (int)(this.xpd.runway_x(pilot) * 2.3), 600 - (int)this.xpd.runway_y(pilot) * 2, 490 + (int)(this.xpd.runway_x(pilot) * 2.3), 770);
			
			g2.setTransform(original_trans);
					
		}
		
	}
	
	private void drawMarkers(Graphics2D g2) {
		
		if(this.xpd.irs_aligned()) {
		
			// fill oval
			
			if (this.xpd.inner_marker_lit() == 1 || this.xpd.middle_marker_lit() == 1 || this.xpd.outer_marker_lit() == 1) {
				
				g2.setStroke(gc.stroke_six);
				g2.scale(gc.scalex, gc.scaley);
				g2.setColor(Color.BLACK);
				g2.fillOval(675,275,60,60);

			}
			
			// inner marker
			
			if (this.xpd.inner_marker_lit() == 1) {
			
				g2.setColor(Color.WHITE);
				g2.drawOval(675,275,60,60);
				g2.setFont(rs.glassFont.deriveFont(30f));
				g2.drawString("IM", 685, 318);
				g2.setTransform(original_trans);
			
			}
			
			// middle marker
			
			else if (this.xpd.middle_marker_lit() == 1) {
			
				g2.setColor(gc.color_amber);
				g2.drawOval(675,275,60,60);
				g2.setFont(rs.glassFont.deriveFont(30f));
				g2.drawString("MM", 685, 318);
				g2.setTransform(original_trans);
				
			}
			
			else if (this.xpd.outer_marker_lit() == 1) {
			
			// outer marker
			
				g2.setColor(Color.CYAN);
				g2.drawOval(675,275,60,60);
				g2.setFont(rs.glassFont.deriveFont(30f));
				g2.drawString("OM", 685, 318);
				g2.setTransform(original_trans);
				
			}
		
		}
		
	}

	private void drawILS(Graphics2D g2) {
		
		nav1_radio = this.xpd.get_nav_radio(1);
		nav2_radio = this.xpd.get_nav_radio(2);

		if (this.pilot == "cpt") {
			nav_radio = this.xpd.get_nav_radio(1);
		} else {
			nav_radio = this.xpd.get_nav_radio(2);
		}
										
		if (this.xpd.irs_aligned()) {
				
			if (this.xpd.nav_txt1(this.pilot).length() > 0) {
				g2.setColor(gc.color_markings);
				g2.setFont(rs.glassFont.deriveFont(30f));
				g2.scale(gc.scalex, gc.scaley);
				int nav_txt1_idx = 0;
				char[] nav_txt1_line = this.xpd.nav_txt1(this.pilot).toCharArray();
				while (nav_txt1_idx < this.xpd.nav_txt1(this.pilot).length()) {
					g2.drawChars(nav_txt1_line, nav_txt1_idx, 1, 230 + (nav_txt1_idx * 18), 140);
					nav_txt1_idx = nav_txt1_idx + 1;
				}
				g2.setTransform(original_trans);
			}
			
			if (this.xpd.nav_txt1a(this.pilot).length() > 0) {
				g2.setColor(gc.color_amber);
				g2.setFont(rs.glassFont.deriveFont(30f));
				g2.scale(gc.scalex, gc.scaley);
				int nav_txt1a_idx = 0;
				char[] nav_txt1a_line = this.xpd.nav_txt1a(this.pilot).toCharArray();
				while (nav_txt1a_idx < this.xpd.nav_txt1a(this.pilot).length()) {
					g2.drawChars(nav_txt1a_line, nav_txt1a_idx, 1, 230 + (nav_txt1a_idx * 18), 140);
					if (nav_txt1a_line[nav_txt1a_idx] != ' ') {
						g2.setStroke(rs.stroke3);
						g2.drawLine(230 + (nav_txt1a_idx * 18), 130, 230 + (nav_txt1a_idx * 18) + 18, 130);
					}
					nav_txt1a_idx = nav_txt1a_idx + 1;
				}			
				g2.setTransform(original_trans);
				g2.setColor(gc.color_markings);
			}
			
			if (this.xpd.nav_txt2(this.pilot).length() > 0) {
				g2.setColor(gc.color_markings);
				g2.setFont(rs.glassFont.deriveFont(30f));
				g2.scale(gc.scalex, gc.scaley);
				int nav_txt2_idx = 0;
				char[] nav_txt2_line = this.xpd.nav_txt2(this.pilot).toCharArray();
				while (nav_txt2_idx < this.xpd.nav_txt2(this.pilot).length()) {
					g2.drawChars(nav_txt2_line, nav_txt2_idx, 1, 230 + (nav_txt2_idx * 18), 185);
					nav_txt2_idx = nav_txt2_idx + 1;
				}
				g2.setTransform(original_trans);
			}
			
			switch (this.xpd.pfd_mode(this.pilot)) {
				case 1:
					gc.drawText("ILS", 220, 810, 40, 0, 0, 0, "left", g2);
					break;
				case 2:
					gc.drawText2("LNAV/VNAV", 220, 810, 30, 0, gc.color_markings, false, "left", g2);
					break;
				case 3:
					gc.drawText2("LOC/VNAV", 220, 810, 30, 0, gc.color_markings, false, "left", g2);
					break;
				case 4:
					gc.drawText2("FMC", 220, 810, 30, 0, gc.color_markings, false, "left", g2);
					break;
				case 5:
					gc.drawText2("LOC/GP", 220, 810, 30, 0, gc.color_markings, false, "left", g2);
					break;
				case 6:
					gc.drawText("GLS", 220, 810, 40, 0, 0, 0, "left", g2);
					break;
				case 7:
					gc.drawText2("FAC/VNAV", 220, 810, 30, 0, gc.color_markings, false, "left", g2);
					break;
				default:
					break;
			}
			
			g2.setColor(gc.color_markings);
										
			if (this.pilot == "cpt") {
				
				// localiser
								
				// fac
				
				if (this.xpd.pfd_fac_horizontal(pilot) >= 1) {
					
					float fac_horizont_xdelta = (80f * this.xpd.fac_horizont());
					if (this.xpd.fac_exp(pilot) == 1) {
						fac_horizont_xdelta = fac_horizont_xdelta * 4;
					}
					if (fac_horizont_xdelta > 200) {
						fac_horizont_xdelta = 200;
					}
					if (fac_horizont_xdelta < -200) {
						fac_horizont_xdelta = -200;
					}
					
					if (this.xpd.pfd_fac_ghost(pilot) == 1) {
						gc.displayImage(rs.img_hdef_ind_ghost, 0, 465.547f - fac_horizont_xdelta , 234.278f, g2);
					} else if (this.xpd.pfd_fac_ghost(pilot) == 0) {
						if (this.xpd.fac_horizont() > 2.49f || this.xpd.fac_horizont() < -2.49f) {
							gc.displayImage(rs.img_hdef_ind, 0, 465.547f - fac_horizont_xdelta , 234.278f, g2);
						} else {
							gc.displayImage(rs.img_hdef_ind_filled, 0, 465.547f - fac_horizont_xdelta, 234.278f, g2);
						}
					}		
					
					if (this.xpd.pfd_fac_ghost(pilot) == 0) {
						if(this.xpd.fac_exp(pilot) == 1) {
							gc.displayImage(rs.img_hdef_exp_loc, 0, 335.5, 230, g2);
						} else {
							gc.displayImage(rs.img_hdef_dots, 0, 335.5, 230, g2);
						}
					}
				}
						
				// localiser
								
				// nav1
				
				if (this.xpd.vhf_nav_source() == 0 && this.xpd.mmr_act_mode(this.pilot) == 1 && this.xpd.rel_gls() <= 5) {
									
					if (this.xpd.ils_pointer_disable() <= 1 && this.xpd.nav1_no_id() == 0 && this.xpd.nav1_display_horizontal() == 1) {
						
						float hdef_xdelta = (80f * this.xpd.nav1_hdef_dot());
						if (this.xpd.pfd_loc_exp(pilot)) {
							hdef_xdelta = (80f * this.xpd.nav1_hdef_dot() * 4);
						}
						if (hdef_xdelta > 200) {
							hdef_xdelta = 200;
						}
						if (hdef_xdelta < -200) {
							hdef_xdelta = -200;
						}
																	
						if (this.xpd.pfd_loc_ghost(pilot) == 1) {
							gc.displayImage(rs.img_hdef_ind_ghost, 0, 465.547f + hdef_xdelta , 234.278f, g2);
						} else if (this.xpd.pfd_loc_ghost(pilot) == 0) {
							if (this.xpd.nav1_hdef_dot() > 2.49f || this.xpd.nav1_hdef_dot() < -2.49f) {
								gc.displayImage(rs.img_hdef_ind, 0, 465.547f + hdef_xdelta , 234.278f, g2);
							} else {
								gc.displayImage(rs.img_hdef_ind_filled, 0, 465.547f + hdef_xdelta, 234.278f, g2);
							}
						}						
					}
					
					if (this.xpd.pfd_loc_ghost(pilot) == 0) {
						if (this.xpd.ils_pointer_disable() <= 0) {
							if(this.xpd.pfd_loc_exp(pilot)) {
								gc.displayImage(rs.img_hdef_exp_loc, 0, 335.5, 230, g2);
							} else {
								gc.displayImage(rs.img_hdef_dots, 0, 335.5, 230, g2);
							}
						}
						if (this.xpd.ils_pointer_disable() >= 1) {
							if(this.xpd.pfd_loc_exp(pilot)) {
								gc.displayImage(rs.img_hdef_exp_loc_a, 0, 335.5, 230, g2);
							} else {
								gc.displayImage(rs.img_hdef_dots_a, 0, 335.5, 230, g2);
							}
						}
					}
				}
					
				// glide slope
				
				// gp
				
				if (this.xpd.pfd_gp_path(pilot) >= 1) {
					
					float gp_err_pfd_ydelta = this.xpd.gp_err_pfd();
					gp_err_pfd_ydelta = gp_err_pfd_ydelta * 200 / 500;
					if (gp_err_pfd_ydelta > 200) {
						gp_err_pfd_ydelta = 200;
					}
					if (gp_err_pfd_ydelta < -200) {
						gp_err_pfd_ydelta = -200;
					}
					
					if (this.xpd.pfd_gp_ghost(pilot) == 1) {
						gc.displayImage(rs.img_vdef_ind_ghost, 0, 749.828, 507.047 + gp_err_pfd_ydelta, g2);
					} else if (this.xpd.pfd_gp_ghost(pilot) == 0) {
						if (this.xpd.gp_err_pfd() > 499.0f || this.xpd.gp_err_pfd() < -499.0f) {
							gc.displayImage(rs.img_vdef_ind, 0, 749.828, 507.047 + gp_err_pfd_ydelta, g2);
						} else {
							gc.displayImage(rs.img_vdef_ind_filled, 0, 749.828f, 507.047f + gp_err_pfd_ydelta, g2);
						}
					}

					if (this.xpd.pfd_gp_ghost(pilot) == 0) {
						gc.displayImage(rs.img_vdef_dots, 0, 745.551f, 377, g2);
					}
				}
				
				// glide slope
												
				// nav1
				
				if (this.xpd.ian_info(pilot) <= 0 && this.xpd.vhf_nav_source() == 0 && this.xpd.ils_disable() <= 0 && this.xpd.mmr_act_mode(this.pilot) == 1 && this.xpd.rel_gls() <= 5) {
								
					if (this.xpd.ils_pointer_disable() <= 1 && this.xpd.nav1_no_id() == 0 && this.xpd.nav1_flag_glideslope() == 0 && this.xpd.nav1_display_vertical() == 1) {
						
						float vdef_ydelta = (70f * this.xpd.nav1_vdef_dot());
						
						if (this.xpd.pfd_gs_ghost(pilot) == 1) {
							gc.displayImage(rs.img_vdef_ind_ghost, 0, 749.828, 507.047 - vdef_ydelta, g2);
						} else if (this.xpd.pfd_gs_ghost(pilot) == 0) {
							if (this.xpd.nav1_vdef_dot() > 2.49f || this.xpd.nav1_vdef_dot() < -2.49f) {
								gc.displayImage(rs.img_vdef_ind, 0, 749.828, 507.047 - vdef_ydelta, g2);
							} else {
								gc.displayImage(rs.img_vdef_ind_filled, 0, 749.828f, 507.047f - vdef_ydelta, g2);
							}
						}
					}
					
					if (this.xpd.pfd_gs_ghost(pilot) == 0) {
						if (this.xpd.ils_pointer_disable() <= 0) {
							gc.displayImage(rs.img_vdef_dots, 0, 745.551f, 377, g2);
						}
						if (this.xpd.ils_pointer_disable() >= 1) {
							gc.displayImage(rs.img_vdef_dots_a, 0, 745.551f, 377, g2);
						}
					}
				}
			}
						
			if (this.pilot == "fo") {
				
				// localiser
								
				// fac
				
				if (this.xpd.pfd_fac_horizontal(pilot) >= 1) {
					
					float fac_horizont_xdelta = (80f * this.xpd.fac_horizont());
					if (this.xpd.fac_exp(pilot) == 1) {
						fac_horizont_xdelta = fac_horizont_xdelta * 4;
					}
					if (fac_horizont_xdelta > 200) {
						fac_horizont_xdelta = 200;
					}
					if (fac_horizont_xdelta < -200) {
						fac_horizont_xdelta = -200;
					}
					
					if (this.xpd.pfd_fac_ghost(pilot) == 1) {
						gc.displayImage(rs.img_hdef_ind_ghost, 0, 465.547f - fac_horizont_xdelta , 234.278f, g2);
					} else if (this.xpd.pfd_fac_ghost(pilot) == 0) {
						if (this.xpd.fac_horizont() > 2.49f || this.xpd.fac_horizont() < -2.49f) {
							gc.displayImage(rs.img_hdef_ind, 0, 465.547f - fac_horizont_xdelta , 234.278f, g2);
						} else {
							gc.displayImage(rs.img_hdef_ind_filled, 0, 465.547f - fac_horizont_xdelta, 234.278f, g2);
						}
					}		
					
					if (this.xpd.pfd_fac_ghost(pilot) == 0) {
						if(this.xpd.fac_exp(pilot) == 1) {
							gc.displayImage(rs.img_hdef_exp_loc, 0, 335.5, 230, g2);
						} else {
							gc.displayImage(rs.img_hdef_dots, 0, 335.5, 230, g2);
						}
					}
				}
				
				// localiser
				
				// nav2
				
				if (this.xpd.vhf_nav_source() == 0 && this.xpd.mmr_act_mode(this.pilot) == 1 && this.xpd.rel_gls() <= 5) {
									
					if (this.xpd.ils_pointer_disable() <= 1 && this.xpd.nav2_no_id() == 0 && this.xpd.nav2_display_horizontal() == 1) {
						
						float hdef_xdelta = (80f * this.xpd.nav2_hdef_dot());
						if (this.xpd.pfd_loc_exp(pilot)) {
							hdef_xdelta = (80f * this.xpd.nav2_hdef_dot() * 4);
						}
						if (hdef_xdelta > 200) {
							hdef_xdelta = 200;
						}
						if (hdef_xdelta < -200) {
							hdef_xdelta = -200;
						}
											
						if (this.xpd.pfd_loc_ghost(pilot) == 1) {
							gc.displayImage(rs.img_hdef_ind_ghost, 0, 465.547f + hdef_xdelta , 234.278f, g2);
						} else if (this.xpd.pfd_loc_ghost(pilot) == 0) {
							if (this.xpd.nav2_hdef_dot() > 2.49f || this.xpd.nav2_hdef_dot() < -2.49f) {
								gc.displayImage(rs.img_hdef_ind, 0, 465.547f + hdef_xdelta , 234.278f, g2);
							} else {
								gc.displayImage(rs.img_hdef_ind_filled, 0, 465.547f + hdef_xdelta, 234.278f, g2);
							}
						}
					}
					
					if (this.xpd.pfd_loc_ghost(pilot) == 0) {
						if (this.xpd.ils_pointer_disable() <= 0) {
							if(this.xpd.pfd_loc_exp(pilot)) {
								gc.displayImage(rs.img_hdef_exp_loc, 0, 335.5, 230, g2);
							} else {
								gc.displayImage(rs.img_hdef_dots, 0, 335.5, 230, g2);
							}
						}
						if (this.xpd.ils_pointer_disable() >= 1) {
							if(this.xpd.pfd_loc_exp(pilot)) {
								gc.displayImage(rs.img_hdef_exp_loc_a, 0, 335.5, 230, g2);
							} else {
								gc.displayImage(rs.img_hdef_dots_a, 0, 335.5, 230, g2);
							}
						}
					}
				}
												
				// glide slope
				
				// gp
				
				if (this.xpd.pfd_gp_path(pilot) >= 1) {
					
					float gp_err_pfd_ydelta = this.xpd.gp_err_pfd();
					gp_err_pfd_ydelta = gp_err_pfd_ydelta * 200 / 500;
					if (gp_err_pfd_ydelta > 200) {
						gp_err_pfd_ydelta = 200;
					}
					if (gp_err_pfd_ydelta < -200) {
						gp_err_pfd_ydelta = -200;
					}
					
					if (this.xpd.pfd_gp_ghost(pilot) == 1) {
						gc.displayImage(rs.img_vdef_ind_ghost, 0, 749.828, 507.047 + gp_err_pfd_ydelta, g2);
					} else if (this.xpd.pfd_gp_ghost(pilot) == 0) {
						if (this.xpd.gp_err_pfd() > 499.0f || this.xpd.gp_err_pfd() < -499.0f) {
							gc.displayImage(rs.img_vdef_ind, 0, 749.828, 507.047 + gp_err_pfd_ydelta, g2);
						} else {
							gc.displayImage(rs.img_vdef_ind_filled, 0, 749.828f, 507.047f + gp_err_pfd_ydelta, g2);
						}
					}
					
					if (this.xpd.pfd_gp_ghost(pilot) == 0) {
						gc.displayImage(rs.img_vdef_dots, 0, 745.551f, 377, g2);
					}
				}
				
				// glide slope
				
				// nav2
				
				if (this.xpd.ian_info(pilot) <= 0 && this.xpd.vhf_nav_source() == 0 && this.xpd.ils_disable() <= 0 && this.xpd.mmr_act_mode(this.pilot) == 1 && this.xpd.rel_gls() <= 5) {
							
					if (this.xpd.ils_pointer_disable() <= 1 && this.xpd.nav2_no_id() == 0 && this.xpd.nav2_flag_glideslope() == 0 && this.xpd.nav2_display_vertical() == 1) {
						
						float vdef_ydelta = (70f * this.xpd.nav2_vdef_dot());
					
						if (this.xpd.pfd_gs_ghost(pilot) == 1) {
							gc.displayImage(rs.img_vdef_ind_ghost, 0, 749.828, 507.047 - vdef_ydelta, g2);
						} else if (this.xpd.pfd_gs_ghost(pilot) == 0) {
							if (this.xpd.nav2_vdef_dot() > 2.49f || this.xpd.nav2_vdef_dot() < -2.49f) {
								gc.displayImage(rs.img_vdef_ind, 0, 749.828, 507.047 - vdef_ydelta, g2);
							} else {
								gc.displayImage(rs.img_vdef_ind_filled, 0, 749.828f, 507.047f - vdef_ydelta, g2);
							}
						}
					}
					
					if (this.xpd.pfd_gs_ghost(pilot) == 0) {
						if (this.xpd.ils_pointer_disable() <= 0) {
							gc.displayImage(rs.img_vdef_dots, 0, 745.551f, 377, g2);
						}
						if (this.xpd.ils_pointer_disable() >= 1) {
							gc.displayImage(rs.img_vdef_dots_a, 0, 745.551f, 377, g2);
						}
					}
				}		
			}
				
			// GLS
					
			if (this.pilot == "cpt") {
				
				// glide slope
				
				if (this.xpd.ils_disable() <= 0 && this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() == 0 && this.xpd.gls1_type() <= 0 && this.xpd.gls1_active() == 1) {
									
					float gls1_vert_dev_ydelta = (70f * this.xpd.gls1_vert_dev());
														
					if (this.xpd.pfd_gls_gs_ghost(pilot) == 1) {
						gc.displayImage(rs.img_vdef_ind_ghost, 0, 749.828, 507.047 - gls1_vert_dev_ydelta, g2);
					} else if (this.xpd.pfd_gls_gs_ghost(pilot) == 0) {
						if (this.xpd.gls1_vert_dev() > 2.49f || this.xpd.gls1_vert_dev() < -2.49f) {
							gc.displayImage(rs.img_vdef_ind, 0, 749.828, 507.047 - gls1_vert_dev_ydelta, g2);
						} else {
							gc.displayImage(rs.img_vdef_ind_filled, 0, 749.828f, 507.047f - gls1_vert_dev_ydelta, g2);
						}
					}
					
					if (this.xpd.pfd_gls_gs_ghost(pilot) == 0) {
						gc.displayImage(rs.img_vdef_dots, 0, 745.551f, 377, g2);
					}
					
				}
				
				// localiser
				
				if (this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() == 0 && this.xpd.gls1_type() <= 0 && this.xpd.gls1_active() == 1) {
					
					float gls1_horz_dev_xdelta = (80f * this.xpd.gls1_horz_dev());
																
					if (this.xpd.pfd_gls_loc_ghost(pilot) == 1) {
						gc.displayImage(rs.img_hdef_ind_ghost, 0, 465.547f - gls1_horz_dev_xdelta , 234.278f, g2);
					} else if (this.xpd.pfd_gls_loc_ghost(pilot) == 0) {
						if (this.xpd.gls1_horz_dev() > 2.49f || this.xpd.gls1_horz_dev() < -2.49f) {
							gc.displayImage(rs.img_hdef_ind, 0, 465.547f - gls1_horz_dev_xdelta , 234.278f, g2);
						} else {
							gc.displayImage(rs.img_hdef_ind_filled, 0, 465.547f - gls1_horz_dev_xdelta, 234.278f, g2);
						}
					}

					if (this.xpd.pfd_gls_loc_ghost(pilot) == 0) {
						gc.displayImage(rs.img_hdef_dots, 0, 335.5, 230, g2);
					}
				}					
			}
			
			// GLS
			
			if (this.pilot == "fo") {
				
				// glide slope
				
				if (this.xpd.ils_disable() <= 0 && this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() == 0 && this.xpd.gls2_type() <= 0 && this.xpd.gls2_active() == 1) {
					
					float gls2_vert_dev_ydelta = (70f * this.xpd.gls2_vert_dev());
										
					if (this.xpd.pfd_gls_gs_ghost(pilot) == 1) {
						gc.displayImage(rs.img_vdef_ind_ghost, 0, 749.828, 507.047 - gls2_vert_dev_ydelta, g2);
					} else if (this.xpd.pfd_gls_gs_ghost(pilot) == 0) {
						if (this.xpd.gls2_vert_dev() > 2.49f || this.xpd.gls2_vert_dev() < -2.49f) {
							gc.displayImage(rs.img_vdef_ind, 0, 749.828, 507.047 - gls2_vert_dev_ydelta, g2);
						} else {
							gc.displayImage(rs.img_vdef_ind_filled, 0, 749.828f, 507.047f - gls2_vert_dev_ydelta, g2);
						}
					}
					
					if (this.xpd.pfd_gls_gs_ghost(pilot) == 0) {
							gc.displayImage(rs.img_vdef_dots, 0, 745.551f, 377, g2);
					}
				}
				
				// localiser
				
				if (this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() == 0 && this.xpd.gls2_type() <= 0 && this.xpd.gls2_active() == 1) {
							
					float gls2_horz_dev_xdelta = (80f * this.xpd.gls2_horz_dev());
										
					if (this.xpd.pfd_gls_loc_ghost(pilot) == 1) {
						gc.displayImage(rs.img_hdef_ind_ghost, 0, 465.547f - gls2_horz_dev_xdelta , 234.278f, g2);
					} else if (this.xpd.pfd_gls_loc_ghost(pilot) == 0) {
						if (this.xpd.gls2_horz_dev() > 2.49f || this.xpd.gls2_horz_dev() < -2.49f) {
							gc.displayImage(rs.img_hdef_ind, 0, 465.547f - gls2_horz_dev_xdelta , 234.278f, g2);
						} else {
							gc.displayImage(rs.img_hdef_ind_filled, 0, 465.547f - gls2_horz_dev_xdelta, 234.278f, g2);
						}
					}
					
					if (this.xpd.pfd_gls_loc_ghost(pilot) == 0) {
						gc.displayImage(rs.img_hdef_dots, 0, 335.5, 230, g2);
					}
				}						
			}	
		}
	}

	private void drawAoA(Graphics2D g2) {

		if (this.xpd.irs_aligned()) {

			float aoaRotate = 0;
			float aoa = 0.0f;

			if(!this.xpd.on_gound() && this.xpd.groundspeed() > 80) {
				aoaRotate = (this.xpd.aoa() * 9f) * -1 ;
				aoa = this.xpd.aoa();
			}
			g2.setColor(gc.color_markings);
			//tickmarks
			g2.setStroke(gc.stroke_six);
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(625, 100);
			g2.drawLine(70, 128, 70, 140);
			g2.rotate(Math.toRadians(-45),70, 70);
			g2.drawLine(70, 128, 70, 140);
			g2.rotate(Math.toRadians(-45),70, 70);
			g2.drawLine(70, 128, 70, 140);
			g2.rotate(Math.toRadians(-45),70, 70);
			g2.drawLine(70, 128, 70, 140);
			g2.rotate(Math.toRadians(-45),70, 70);
			g2.drawLine(70, 128, 70, 140);
			g2.rotate(Math.toRadians(-45),70, 70);
			g2.drawLine(70, 128, 70, 140);
			g2.setTransform(original_trans);

			//arch
			g2.setStroke(gc.stroke_six);
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(625, 100);
			aoaArc.setFrame(0, 0, 140, 140);
			aoaArc.setAngleStart(-90);
			aoaArc.setAngleExtent(225);
			g2.draw(aoaArc);
			g2.setTransform(original_trans);

			//needle

			g2.scale(gc.scalex, gc.scaley);
			g2.translate(625, 100);
			g2.rotate(Math.toRadians(aoaRotate + 45),70, 70);
			g2.setStroke(gc.stroke_eight);
			g2.drawLine(70, 70, 140, 70);
			g2.setTransform(original_trans);

			if(!this.xpd.on_gound()) {
				//stick shaker indicator
				g2.scale(gc.scalex, gc.scaley);
				g2.translate(625, 100);
				g2.setStroke(gc.stroke_eight);
				g2.rotate(Math.toRadians(-170),70, 70);
				g2.setColor(Color.RED);
				g2.drawLine(70, 142, 70, 150);
				g2.setTransform(original_trans);
			}

			g2.setColor(Color.WHITE);
			gc.drawText(String.format("%.1f", aoa), 640, 850, 28, 0, 0, 0, "left", g2);	
		}
	}

	private void drawFMA(Graphics2D g2) {

		gc.displayImage(rs.img_FMA, 0, 177.152, 974.450, g2);

		// FD CMD modes

		String fdMode;
		Color fdModeColor = gc.color_lime;
		
		if (this.xpd.irs_aligned()) {
		
			if ((this.xpd.cmd_status("cpt") || this.xpd.cmd_status("fo")) && !this.xpd.single_ch() && !this.xpd.single_ch_g() && this.xpd.flare_status() == 0) {
				fdMode = "CMD";
			} else if (this.xpd.fd_status(pilot) && !this.xpd.single_ch() && !this.xpd.single_ch_g() && this.xpd.flare_status() == 0) {
				fdMode = "FD";
			} else if (this.xpd.single_ch()) {
				fdModeColor = gc.color_amber;
				fdMode = "CH";
			} else if (this.xpd.single_ch_g()) {
				fdModeColor = gc.color_lime;
				fdMode = "CH";
			} else if (this.xpd.flare_status() != 0) {
				fdMode = "LAND 3";
			} else {
				fdMode = null;
			}
			
			//cws r and p
			if(this.xpd.cws_r_status()) gc.drawText2("CWS R", 420f, 945f, 24f, 0f, rs.color_amber, false, "center", g2);
			if(this.xpd.cws_p_status())	gc.drawText2("CWS P", 550f, 945f, 24f, 0f, rs.color_amber, false, "center", g2);
			
			if(this.xpd.single_ch() || this.xpd.single_ch_g()) {
				gc.drawText2("SINGLE", 485, 863, 36, 0, fdModeColor, false, "center", g2);
			}
			
			if(fdMode != null) {
				gc.drawText2(fdMode, 485, 810, 44, 0, fdModeColor, false, "center", g2);
			}
			
			if(this.xpd.single_ch_rec_mode()) {
				g2.setColor(gc.color_amber);
				g2.setStroke(gc.stroke_five);
				g2.scale(gc.scalex, gc.scaley);
				g2.drawPolygon(single_ch_Rec_xPoints, single_ch_Rec_yPoints, single_ch_Rec_xPoints.length);
				g2.setTransform(original_trans);
			}
						
			if (this.xpd.fd_rec(pilot)) {
				g2.setColor(gc.color_lime);
				g2.setStroke(gc.stroke_three);
				gc.drawRect2D(fdRec, 400, 808, 175, 44, 0, false, g2);
				g2.setStroke(original_stroke);
			}

			// Column one
			
			String at_enganged_mode = null;
			switch (this.xpd.pfd_spd_mode()) {
				case 1:
					at_enganged_mode = "ARM";
					break;
				case 2:
					at_enganged_mode = "N1";
					break;
				case 3:
					at_enganged_mode = "MCP SPD";
					break;
				case 4:
					at_enganged_mode = "FMC SPD";
					break;
				case 5:
					at_enganged_mode = "GA";
					break;
				case 6:
					at_enganged_mode = "THR HLD";
					break;
				case 7:
					at_enganged_mode = "RETARD";
					break;
				default:
					at_enganged_mode = "";
			}

			if (at_enganged_mode == "ARM") {
				g2.setColor(Color.WHITE);
			} else {
				g2.setColor(gc.color_lime);
			}
			gc.drawText(at_enganged_mode, 280, 1009, 30, 0, 0, 0, "center", g2);

			g2.setColor(gc.color_lime);
			g2.setStroke(gc.stroke_three);
			
			if (this.xpd.rec_thr_modes()) {
				gc.drawRect2D(rec_thr_modes, 178.884f, 1004.270f, 200.750f, 36.787f, 0, false, g2);
			}
			if (this.xpd.rec_thr2_modes()) {
				gc.drawRect2D(rec_thr2_modes, 178.884f, 1004.270f, 200.750f, 36.787f, 0, false, g2);
			}

			// Column two, row one

			String roll_engage_mode = null;
			switch (this.xpd.pfd_hdg_mode()) {
				case 1:
					roll_engage_mode = "HDG SEL";
					break;
				case 2:
					roll_engage_mode = "VOR/LOC";
					break;
				case 3:
					roll_engage_mode = "LNAV";
					break;
				case 4:
					roll_engage_mode = "ROLLOUT";
					break;
				case 5:
					roll_engage_mode = "FAC";
					break;
				default:
					roll_engage_mode = "";
			}
			gc.drawText(roll_engage_mode, 490, 1009, 30, 0, 0, 0, "center", g2);
			if (this.xpd.rec_hdg_mode()) {
				gc.drawRect2D(rec_hdg_mode, 384.634f, 1004.270f, 200.750f, 36.787f, 0, false, g2);
			}

			// column two / row two

			String roll_armed_mode = null;
			switch (this.xpd.pfd_hdg_mode_arm()) {
				case 1:
					roll_armed_mode = "VOR/LOC";
					break;
				case 2:
					roll_armed_mode = "ROLLOUT";
					break;
				case 3:
					roll_armed_mode = "LNAV";
					break;
				case 4:
					roll_armed_mode = "FAC";
					break;
				case 5:
					roll_armed_mode = "LNAV VOR/LOC";
					break;
				case 6:
					roll_armed_mode = "LNAV ROLLOUT";
					break;
				case 7:
					roll_armed_mode = "LNAV FAC";
					break;
				default:
					roll_armed_mode = "";
			}
			
			g2.setColor(Color.WHITE);
			gc.drawText2(roll_armed_mode, 490, 982, 24f, 0, gc.color_markings, false, "center", g2);

			// Column three / row one

			String pitch_engaged_mode = null;
			switch (this.xpd.pfd_alt_mode()) {
				case 1:
					pitch_engaged_mode = "V/S";
					break;
				case 2:
					pitch_engaged_mode = "MCP SPD";
					break;
				case 3:
					pitch_engaged_mode = "ALT/ACQ";
					break;
				case 4:
					pitch_engaged_mode = "ALT HOLD";
					break;
				case 5:
					pitch_engaged_mode = "G/S";
					break;
				case 6:
					pitch_engaged_mode = "FLARE";
					break;
				case 7:
					pitch_engaged_mode = "G/P";
					break;
				case 8:
					pitch_engaged_mode = "VNAV SPD";
					break;
				case 9:
					pitch_engaged_mode = "VNAV PTH";
					break;
				case 10:
					pitch_engaged_mode = "VNAV ALT";
					break;
				case 11:
					pitch_engaged_mode = "TO/GA";
					break;
				default:
					pitch_engaged_mode = "";
			}
			
			g2.setColor(gc.color_lime);
			gc.drawText(pitch_engaged_mode, 690, 1009, 30, 0, 0, 0, "center", g2);
			
			if (this.xpd.rec_alt_modes()) {
				gc.drawRect2D(rec_alt_modes, 590f, 1004.270f, 200.750f, 36.787f, 0, false, g2);
			}

			// Column three / row two
			
			String pitch_armed_mode = null;
				switch (this.xpd.pfd_alt_mode_arm()) {
					case 1:
						pitch_armed_mode = "G/S";
						break;
					case 2:
						pitch_armed_mode = "V/S";
						break;
					case 3:
						pitch_armed_mode = "FLARE";
						break;
					case 4:
						pitch_armed_mode = "G/P";
						break;
					case 5:
						pitch_armed_mode = "VNAV";
						break;
					case 6:
						pitch_armed_mode = "G/S V/S";
						break;
					default:
						pitch_armed_mode = "";
			}
			gc.drawText2(pitch_armed_mode, 690, 982, 24f, 0, gc.color_markings, false, "center", g2);
		}
	}

	private void drawSpeedTape(Graphics2D g2) {

		g2.setColor(gc.color_magenta);
		if (!this.xpd.mcp_speed_is_mach()) {
			gc.drawText(gc.df3.format(this.xpd.mcp_speed_kts_mach()), 66f, 940, 44, 0, 0, 0, "left", g2);
		} else {
			gc.drawText(gc.mach_format_top.format(this.xpd.mcp_speed_kts_mach()), 66f, 940, 44, 0, 0, 0, "left", g2);
		}
		
		int ias5 = Math.round(ias / 10.0f) * 10;
		
		_speedTape.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		_speedTape.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		_speedTape.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		_speedTape.clearRect(0, 0, 145, 756);
		_speedTape.setColor(gc.color_instrument_gray);
		_speedTape.fillRect(0, 0, 135, 756);

		for (int ias_mark = ias5 - 60; ias_mark <= ias5 + 60; ias_mark += 10) {
			if (ias_mark >= 30) {
				_speedTape.setColor(gc.color_markings);
				_speedTape.setStroke(alt_marking_stroke);
				float ias_y = 377f - Math.round(((float) ias_mark - ias) * 756f / 122f);
				speedMarkings.setLine(113, ias_y, 135, ias_y);
				_speedTape.draw(speedMarkings);
				if (ias_mark % 20 == 0) {
					String mark_str = "" + ias_mark;
					_speedTape.setFont(ias_tape_font);

					if (ias_mark < 100) {
						_speedTape.drawString(mark_str, 60, ias_y + 16);
					} else {
						_speedTape.drawString(mark_str, 40, ias_y + 16);
					}
				}
			}
		}
		
		gc.displayImage(img_SpeedTape, 0, 20, 148.5f, g2);
		
		// speedbox
		gc.displayImage(rs.img_speedBox, 0, 10, 469.5, g2);
				
		g2.clipRect((int) (13 * gc.scalex), (int) (477 * gc.scaley), (int) (96 * gc.scalex), (int) (101 * gc.scaley));
	
		int ias_int = (int) ias;
		int ias_round = Math.round(ias);
		int ias_deca = (ias_round / 10) % 10;
		int ias_hecto = (ias_round / 100) % 10;
		
		float ias_frac = ias - (float) ias_int;
		
		float ias_ydelta = 46f * ias_frac;
				
		_ias10.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		_ias10.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		_ias10.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		_ias10.clearRect(0, 0, 30, 500);
		_ias10.setFont(rs.glassFont.deriveFont(46f));
		_ias10.setColor(gc.color_markings);
		
		_ias10.drawString("" + (ias_int + 2) % 10, 0, 58);
		_ias10.drawString("" + (ias_int + 1) % 10, 0, 104);
		_ias10.drawString("" + ias_int % 10, 0, 150);
		_ias10.drawString("" + (ias_int - 1) % 10, 0, 196);
		_ias10.drawString("" + (ias_int - 2) % 10, 0, 242);
		
		gc.displayImage(img_ias10, 0, 78, 358 - ias_ydelta, g2);
		
		g2.setColor(gc.color_markings);
		
		gc.drawText("" + ias_deca % 10, 48, 508, 46f, 0, 0, 0, "left", g2);

		if (ias > 99) {
			gc.drawText("" + ias_hecto % 10, 18, 508, 46f, 0, 0, 0, "left", g2);
		}

		g2.setClip(original_clipshape);

		float speedBug_cy = rs.img_speedBug.getHeight() / 2 * gc.scaley;
		float ap_spdbug_y = (this.getHeight() / 2 - (Math.round((this.xpd.mcp_speed_kts2(this.pilot) - ias) * gc.speedTapeHeight / 122.0f )) - speedBug_cy);

		if (ap_spdbug_y < gc.speedTapeTop) {
			ap_spdbug_y = gc.speedTapeTop - speedBug_cy;
		} else if (ap_spdbug_y > gc.speedTapeTop + gc.speedTapeHeight) {
			ap_spdbug_y = gc.speedTapeTop + gc.speedTapeHeight - speedBug_cy;
		}
		g2.clipRect((int) gc.speedTapeLeft, (int) gc.speedTapeTop, (int) (gc.speedTapeWidth * 2f), (int) gc.speedTapeHeight);
		g2.drawImage(rs.img_speedBug, (int)(135.014 * gc.scalex), (int)ap_spdbug_y, (int)(rs.img_speedBug.getWidth() * gc.scalex), (int)(rs.img_speedBug.getHeight() * gc.scaley), null);
		g2.setClip(original_clipshape);

		g2.setColor(Color.WHITE);
		if (this.xpd.airspeed_mach() >= 0.4f) {
			gc.drawText("" + gc.mach_format.format(this.xpd.airspeed_mach()), 45, 85, 44, 0, 0, 0, "left", g2);
		} else {
			gc.drawText("GS", 25, 85, 24, 0, 0, 0, "left", g2);
			gc.drawText("" + gc.df3hash.format(this.xpd.groundspeed()), 135, 85, 44, 0, 0, 0, "right", g2);
		}
		
		if ((airspeed_mach_prev >= 0.4f && this.xpd.airspeed_mach() < 0.4f) || (airspeed_mach_prev < 0.4f && this.xpd.airspeed_mach() >= 0.4f)) {
			time_plus_ten_seconds = System.currentTimeMillis() + 10000;
		}
		
		if (System.currentTimeMillis() < time_plus_ten_seconds) {
			g2.scale(gc.scalex, gc.scaley);
			g2.setStroke(gc.stroke_four);
			g2.drawRect(20, 923, 130, 50);
			g2.setTransform(original_trans);
		}
		
		airspeed_mach_prev = this.xpd.airspeed_mach();

		if(!this.xpd.on_gound()) {
			drawSpeedTapeSpeedBars();
		}
		if(this.xpd.irs_aligned()) {
			drawSpeedTrentVector();
		}
		
		drawFlapSpeeds();
		
		// vspeed
		
		if(this.xpd.approach_flaps() >= 15 && this.xpd.vspeed_mode() <= 0) {
			gc.drawText2("REF", 180, 180, 24f, 0, gc.color_lime, false, "left", g2);
			gc.drawText2(this.xpd.approach_flaps() + "/" + this.xpd.approach_speed(), 180, 155, 24f, 0, gc.color_lime, false, "left", g2);
		}

		if (this.xpd.vspeed_mode() == 1) {
			gc.drawText2("V1", 200, 170, 30, 0, gc.color_lime, false, "left", g2); //mode
		} else if(this.xpd.vspeed_mode() == 2) {
			gc.drawText2("VR", 200, 170, 30, 0, gc.color_lime, false, "left", g2); //mode
		} else if(this.xpd.vspeed_mode() == 4) {
			gc.drawText2("INVALID", 200, 170, 30, 0, gc.color_lime, false, "left", g2); //mode
			gc.drawText2("ENTRY", 200, 135, 30, 0, gc.color_lime, false, "left", g2); //mode
		}
		
		if (this.xpd.vspeed_vref_show(this.pilot)) {
			gc.drawText2("VREF", 200, 170, 30, 0, gc.color_lime, false, "left", g2); //mode
		}
		
		if (this.xpd.vspeed_digit_show(this.pilot)) {
			gc.drawText2("" + this.xpd.vspeed(), 200, 135, 30, 0, gc.color_lime, false, "left", g2); //digits
		}
		
		// fms v1 
		
		if(this.xpd.v1_off_show(this.pilot)) {
			gc.drawText2("V1", 160, 845, 30, 0, gc.color_lime, false, "left", g2);			
			gc.drawText2("" + (int) this.xpd.fms_v1(), 160, 810, 30, 0, gc.color_lime, false, "left", g2); //digits		
		}
	
	}

	private void drawFlapSpeeds() {
		
		float v1 = this.xpd.fms_v1_set();
		float vr = this.xpd.fms_vr_set();
		float v2_15 = this.xpd.fms_v2_15();
		float vref = this.xpd.fms_vref();
		float flaps_1 = this.xpd.flaps_1();
		float flaps_2 = this.xpd.flaps_2();
		float flaps_5 = this.xpd.flaps_5();
		float flaps_10 = this.xpd.flaps_10();
		float flaps_15 = this.xpd.flaps_15();
		float flaps_25 = this.xpd.flaps_25();
		float flaps_up = this.xpd.flaps_up();
		
		g2.clipRect(0, (int) gc.speedTapeTop, (int) this.getWidth(), (int) gc.speedTapeHeight);
		
		if (this.xpd.fms_v1r_bugs()) {
			drawVspeed(v1, "V1");
			drawVRspeed(vr, "R");
		}
		if (v2_15 > 0.0f ) 
			drawWhiteBug(v2_15);
		if (this.xpd.spd_80_show())
			drawWhiteBug(80f);
		if (this.xpd.flaps_1_show()) 
			drawFlapsSpeed(flaps_1, "1");
		if (this.xpd.flaps_2_show()) 
			drawFlapsSpeed(flaps_2, "2");
		if (this.xpd.flaps_5_show())
			drawFlapsSpeed(flaps_5, "5");
		if (this.xpd.flaps_10_show())
			drawFlapsSpeed(flaps_10, "10");
		if (this.xpd.flaps_15_show())
			drawFlapsSpeed(flaps_15, "15");
		if (this.xpd.flaps_25_show())
			drawFlapsSpeed(flaps_25, "25");
		if (this.xpd.flaps_up_show())
			drawFlapsSpeed(flaps_up, "UP");		
		if (this.xpd.fms_vref_bugs()) 
			drawVspeed(vref, "REF"); 
		
		g2.setClip(original_clipshape);
	}

	private void drawWhiteBug(float bug) {

		int bug_y = (int) (this.getHeight() / 2 - Math.round( (bug - ias) * gc.speedTapeHeight / 122.0f ));

		white_bug_x[0] = (int) (gc.speedTapeRight - gc.speedTapeWidth * 0.125f);
		white_bug_x[1] = (int) (gc.speedTapeRight);
		white_bug_x[2] = (int) (gc.speedTapeRight + gc.speedTapeWidth * 0.1f);
		white_bug_x[3] = (int) (gc.speedTapeRight + gc.speedTapeWidth * 0.1f);
		white_bug_x[4] = (int) (gc.speedTapeRight);
		
		white_bug_y[0] = bug_y;
		white_bug_y[1] = (int) (bug_y - gc.speedTapeWidth * 0.05);
		white_bug_y[2] = (int) (bug_y - gc.speedTapeWidth * 0.05);
		white_bug_y[3] = (int) (bug_y + gc.speedTapeWidth * 0.05);
		white_bug_y[4] = (int) (bug_y + gc.speedTapeWidth * 0.05);

		g2.setColor(gc.color_markings);
		g2.fillPolygon(white_bug_x, white_bug_y, 5);

	}

	private void drawFlapsSpeed(float flaps, String string) {
		
		int flaps_y = (int) (this.getHeight() / 2 - Math.round( (flaps - ias) * gc.speedTapeHeight / 122.0f ));
		g2.setColor(gc.color_lime);
		g2.setStroke(gc.stroke_four);
		g2.drawLine((int)(gc.speedTapeLeft + gc.speedTapeWidth * 0.95f), flaps_y, (int)(gc.speedTapeLeft + gc.speedTapeWidth * 1.05f), flaps_y);
		g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
		g2.drawString(string, gc.speedTapeLeft + gc.speedTapeWidth * 1.08f, flaps_y + (24f * gc.scaling_factor) / 2);

	}

	private void drawVRspeed(float vr,  String string) {
		
		int vr_y = (int) (this.getHeight() / 2 - Math.round( (vr - ias) * gc.speedTapeHeight / 122.0f ));
		g2.setColor(gc.color_lime);
		g2.setStroke(gc.stroke_four);
		g2.drawLine((int)(gc.speedTapeLeft + gc.speedTapeWidth * 0.95f), vr_y, (int)(gc.speedTapeLeft + gc.speedTapeWidth * 1.05f), vr_y);
		g2.setFont(rs.glassFont.deriveFont(22f * gc.scaling_factor));
		g2.drawString(string, gc.speedTapeLeft + gc.speedTapeWidth * 1.3f, vr_y + (22f * gc.scaling_factor) / 2);
		
	}

	private void drawVspeed(float v,  String string) {
		
		int v_y = (int) (this.getHeight() / 2 - Math.round( (v - ias) * gc.speedTapeHeight / 122.0f ));
		g2.setColor(gc.color_lime);
		g2.setStroke(gc.stroke_four);
		g2.drawLine((int)(gc.speedTapeLeft + gc.speedTapeWidth * 0.95f), v_y, (int)(gc.speedTapeLeft + gc.speedTapeWidth * 1.05f), v_y);
		g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
		g2.drawString(string, gc.speedTapeLeft + gc.speedTapeWidth * 1.08f, v_y + (24f * gc.scaling_factor) / 2);
		
	}

	private void drawSpeedTrentVector() {
				
		if(ias > 10) {
			float ias_trend;
			ias_trend = this.xpd.airspeed_acceleration() * 10f;
			if ( Math.abs(ias_trend) > 4.5f && this.xpd.groundspeed() > 10) {
				if ( ( ias + ias_trend ) < 0.0f ) {
					ias_trend = - ias;
				}
				int asi10_y = (int) (this.getHeight() / 2 - Math.round( ias_trend * gc.speedTapeHeight / 122.0f ));
				if ( asi10_y < gc.speedTapeTop ) {
					asi10_y = (int) gc.speedTapeTop;
				} else if ( asi10_y > gc.speedTapeBottom ) {
					asi10_y = (int) (gc.speedTapeBottom);
				}
				int arrow_dx = (int) (gc.speedTapeWidth * 0.06f );
				int arrow_dy = (int) (gc.speedTapeWidth * 0.1f * (int) Math.signum(ias_trend));
				g2.setColor(gc.color_lime);
				g2.setStroke(gc.stroke_five);
				g2.drawLine((int) (gc.speedTapeLeft + gc.speedTapeWidth * 0.85f), (int) this.getHeight() / 2, (int) (gc.speedTapeLeft + gc.speedTapeWidth * 0.85f), asi10_y + arrow_dy);
				
				speed_trend_arrow_x[0] = (int) (gc.speedTapeLeft + gc.speedTapeWidth* 0.85f);
				speed_trend_arrow_x[1] = (int) (gc.speedTapeLeft + gc.speedTapeWidth* 0.85f) - arrow_dx;
				speed_trend_arrow_x[2] = (int) (gc.speedTapeLeft + gc.speedTapeWidth* 0.85f) + arrow_dx;
				
				speed_trend_arrow_y[0] = asi10_y;
				speed_trend_arrow_y[1] = asi10_y + arrow_dy;
				speed_trend_arrow_y[2] = asi10_y + arrow_dy;
				
				g2.drawPolygon(speed_trend_arrow_x, speed_trend_arrow_y, 3);
			}
		}		
	}

	private void drawSpeedTapeSpeedBars() {

		float halfstroke = (int) (gc.speedTapeWidth * 0.07);
		float vmax = this.xpd.max_speed();
		float max_m = this.xpd.max_maneuver_speed();
		float amber_max_y = (this.getHeight() / 2 - (Math.round( (max_m - ias) * gc.speedTapeHeight / 122.0f )));
		float min_m = this.xpd.min_maneuver_speed();
		float amber_min_y = (int) (this.getHeight() / 2 - Math.round( (min_m - ias) * gc.speedTapeHeight / 122.0f ));
		float vmin = this.xpd.min_speed();
		float red_min_y = (this.getHeight() / 2 - (Math.round( (vmin - ias) * gc.speedTapeHeight / 122.0f )));


		g2.clipRect(0, (int) gc.speedTapeTop, (int) this.getWidth(), (int) gc.speedTapeHeight);
		//MAX SPEED
		g2.setColor(Color.RED);
		float red_dashes[] = {halfstroke*2.0f, halfstroke*2.0f};

		g2.setStroke(new BasicStroke(2.0f * halfstroke, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, red_dashes, 2.0f));

		float red_max_y = (this.getHeight() / 2 - (Math.round( (vmax - ias) * gc.speedTapeHeight / 122.0f )));
		if ( red_max_y > gc.speedTapeTop ) {
			// draw a thick red dashed line *from* red_max_y *to* the top
			g2.drawLine((int) (gc.speedTapeRight + halfstroke), (int) (red_max_y), (int) (gc.speedTapeRight + halfstroke), (int) gc.speedTapeTop);
		}else {
			// we don't draw, but set the top for the amber line
			red_max_y = (int) gc.speedTapeTop;
		}

		//MIN SPEED
		if(this.xpd.min_speed_show()) {
			if ( red_min_y < gc.speedTapeBottom ) {
				// draw a thick red dashed line *from* red_min_y *to* zero
				g2.drawLine((int) (gc.speedTapeRight + halfstroke),  (int)red_min_y, (int) (gc.speedTapeRight + halfstroke), (int) gc.speedTapeBottom);
			}else {
				red_min_y = gc.speedTapeBottom;
			}
		}
		g2.setStroke(original_stroke);


		//MAX_MANEUVER_SPEED
		if(this.xpd.max_maneuver_speed_show()) {
			g2.setColor(gc.color_amber);
			g2.setStroke(gc.stroke_four);
			if (amber_max_y > gc.speedTapeTop && amber_max_y > red_max_y) {
				// draw an amber line between red_max_y and amber_max_y
				g2.drawLine((int) (gc.speedTapeLeft + gc.speedTapeWidth * 1.075), (int) (red_max_y), (int) (gc.speedTapeLeft + gc.speedTapeWidth * 1.075), (int)amber_max_y);
				g2.drawLine((int) (gc.speedTapeRight) ,(int) amber_max_y, (int) (gc.speedTapeRight + halfstroke), (int) amber_max_y);	
			}
		}
		//MIN_MANEUVER_SPEED
		if(this.xpd.min_maneuver_speed_show()) {
			g2.setColor(gc.color_amber);
			g2.setStroke(gc.stroke_four);
			if (amber_min_y < gc.speedTapeBottom) {
				g2.drawLine((int) (gc.speedTapeLeft + gc.speedTapeWidth * 1.075), (int)red_min_y, (int) (gc.speedTapeLeft + gc.speedTapeWidth * 1.075), (int) amber_min_y);
				g2.drawLine((int) (gc.speedTapeRight), (int) amber_min_y, (int) (gc.speedTapeRight + halfstroke), (int) amber_min_y);
			}
		}

		g2.setStroke(original_stroke);
		g2.setClip(original_clipshape);
	}

	private void drawVsTape(Graphics2D g2) {

		if (this.xpd.irs_aligned()) {

			int up_down;
			int vvabs;
			int vvy;
			float vvi = this.xpd.vvi(pilot);
			float ap_vvi = (int)this.xpd.mcp_vvi();

			float y_center = this.getHeight() / 2;
			float y_1000 = 110f * gc.scaley;
			float y_2000 = 184f * gc.scaley;
			float y_6000 = 243f * gc.scaley;

			// background
			gc.displayImage(rs.img_vsTape, 0, 984.691, 264.751, g2);

			// AP VS bug
			if (ap_vvi != 0 && this.xpd.vs_mode()) {

				up_down = (int) Math.signum(ap_vvi);
				vvabs = (int) Math.abs(ap_vvi);
				vvabs = Math.min(vvabs, 6250);

				if (vvabs > 2000) {
					vvy = (int) (y_2000 + (vvabs - 2000) * (y_6000 - y_2000) / 4000);
				} else if (vvabs > 1000) {
					vvy = (int) (y_1000 + (vvabs - 1000) * (y_2000 - y_1000) / 1000);
				} else {
					vvy = (int) ((vvabs) * (y_1000) / 1000);
				}

				g2.setColor(gc.color_magenta);
				g2.setStroke(gc.stroke_twohalf);
				vsApBug1.setLine(1015 * gc.scalex, (y_center - vvy * up_down) - 2, 1035 * gc.scalex,
						(y_center - vvy * up_down) - 2);
				vsApBug2.setLine(1015 * gc.scalex, (y_center - vvy * up_down) + 2, 1035 * gc.scalex,
						(y_center - vvy * up_down) + 2);
				g2.draw(vsApBug1);
				g2.draw(vsApBug2);
			}

			// VSI needle

			up_down = (int) Math.signum(vvi);
			vvabs = (int) Math.abs(vvi);
			vvabs = Math.min(vvabs, 6250);

			if (vvabs > 2000) {
				vvy = (int) (y_2000 + (vvabs - 2000) * (y_6000 - y_2000) / 4000);
			} else if (vvabs > 1000) {
				vvy = (int) (y_1000 + (vvabs - 1000) * (y_2000 - y_1000) / 1000);
			} else {
				vvy = (int) ((vvabs) * (y_1000) / 1000);
			}

			g2.setColor(gc.color_markings);
			g2.setStroke(gc.stroke_five);
			vsTapeClip.setFrame(984.691f * gc.scalex, 264.751f * gc.scaley, 82.822f * gc.scalex, 523.498f * gc.scaley);
			g2.setClip(vsTapeClip);
			vsNeedle.setLine(1030 * gc.scalex, y_center - vvy * up_down, 1100 * gc.scalex, y_center);
			g2.draw(vsNeedle);
			g2.setClip(original_clipshape);

			// VS readout
			if (vvi > 400 || vvi < -400) {

				vvi = Math.round(vvi);
				vvi = Math.abs(vvi);
				vvi = 50 * (Math.round(vvi / 50));
				if (up_down == -1) {
					gc.drawText(String.format("%.0f", vvi), 1050, 220, 26, 0, 0, 0, "right", g2);
				} else {
					gc.drawText(String.format("%.0f", vvi), 1050, 808, 26, 0, 0, 0, "right", g2);
				}

			}

			g2.setStroke(original_stroke);
		}


	}

	private void drawFailureFlags(Graphics2D g2) {

		// if (this.xpd.on_gound() && (this.xpd.fms_vr_set() == 0 && this.xpd.fms_vref() == 0)) {
		if (this.xpd.no_vspd() == 1) {	
			gc.displayImage(rs.img_no_vspd, 0, 170, 546, g2);
		}
		if (!this.xpd.irs_aligned()) {

			gc.displayImage(rs.img_att_fail, 0, 445.391, 592, g2);
			gc.displayImage(rs.img_hdg_fail, 0, 445.391, 30, g2);
			gc.displayImage(rs.img_vert_fail, 0, 1017, 440, g2);

		}
		if (this.xpd.alt_disagree()) {
			gc.displayImage(rs.img_alt_dis, 0, 830, 189, g2);
		
		}
		if (this.xpd.ias_disagree()) {
			gc.displayImage(rs.img_ias_dis, 0, 26.180, 189, g2);
		}

		if (!this.xpd.pfd_rwy_show()) {
			gc.displayImage(rs.img_ldg_alt_fail, 0, 970, 145, g2);
		}
			
		if(this.xpd.windsheer()) {
			gc.drawText2("WINDSHEER", 490, 190 , 44, 0, gc.color_red, true, "center", g2);
		}
		if(this.xpd.pullup()) {
			gc.drawText2("PULL UP", 490, 190 , 44, 0, gc.color_red, true, "center", g2);
		}

	}

	private void drawHeadingTrack(Graphics2D g2) {

		if (this.xpd.irs_aligned()) {

			float trackRotate = this.xpd.track() - heading;
			float bugRotate = this.xpd.mcp_hdg() - heading;
			
			if(bugRotate > 180.0f) bugRotate -= 360.0f;
			if(bugRotate < -180.0f) bugRotate += 360.0f;
			bugRotate = bugRotate*7.5f/5f;
			
			if(trackRotate > 180.0f) trackRotate -= 360.0f;
			if(trackRotate < -180.0f) trackRotate += 360.0f;
			trackRotate = trackRotate*7.5f/5f;
			
			g2.scale(gc.scalex, gc.scaley);
			
			g2.translate(484.95f, 1290f);
			
			rs._headingTrack.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			rs._headingTrack.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			rs._headingTrack.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			headingTransForm = rs._headingTrack.getTransform();
			
			rs._headingTrack.translate(375, 375);
			rs._headingTrack.setColor(rs.color_instrument_gray);
			rs._headingTrack.fillOval(-375, -375, 750, 750);
			rs._headingTrack.setColor(rs.color_markings);
			rs._headingTrack.setStroke(rs.stroke4);

			int hdg5 = (int)Math.round( this.heading / 5.0f ) * 5;
			
			rs._headingTrack.rotate(Math.toRadians(-52.5f), 0, 0);
			
			for(int i = -35; i <= 35; i+=5) {
				int mark5 = ((hdg5 + i) + 360) % 360;
				if(mark5 % 10 == 0) {
					String marktext = "" + mark5/10;
					if(mark5 % 30 == 0) {
						rs._headingTrack.setFont(rs.glass30);
						if(mark5 < 100) {
							rs._headingTrack.drawString(marktext, -10f, -315f);
						}else {
							rs._headingTrack.drawString(marktext, -18f, -315f);
						}
						
						rs._headingTrack.drawLine(0, -350, 0, -373);
					}else {
						rs._headingTrack.setFont(rs.glass22);
						rs._headingTrack.drawString(marktext, -10f, -320f);
						rs._headingTrack.drawLine(0, -350, 0, -373);
					}
				}else {
					rs._headingTrack.drawLine(0, -364, 0, -373);
				}
				rs._headingTrack.rotate(Math.toRadians(7.5f), 0, 0);
			}
			
			rs._headingTrack.setTransform(headingTransForm);

			g2.rotate(Math.toRadians((hdg5-this.heading)*7.5f/5f), 0, 0);
			g2.drawImage(rs.img_HeadingTrack, -375, -375, null);
			
			g2.setTransform(original_trans);
			
			

			// gc.displayImage(rs.img_headingTrackBug, bugRotate, 296.925, -405.521, g2);
			// gc.displayImage(rs.img_headingTrack_headingMarker, 0, 299.292, 86.719, g2);
	
			//gc.displayImage(rs.img_headingTrackCricle, heading * -1, 110, -615, g2);
			g2.setColor(gc.color_magenta);
			gc.drawText(gc.df3.format(this.xpd.mcp_hdg()), 420, 10, 30, 0, 0, 0, "right", g2);
			gc.drawText("H", 430, 10, 24, 0, 0, 0, "left", g2);
			g2.setColor(gc.color_lime);
			gc.drawText("MAG", 550, 10, 24, 0, 0, 0, "left", g2);


			//trackLine
			g2.setStroke(gc.stroke_six);
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(110, 918);
			g2.setColor(Color.WHITE);
			g2.rotate(Math.toRadians(trackRotate), 375, 375);
			g2.drawLine(375, 375, 375, 0);
			g2.drawLine(367, 95, 383, 95);
			g2.setTransform(original_trans);

			//headingbug
			gc.displayImage(rs.img_headingTrackBug, bugRotate, 464.605, -635.850, g2);

			//headingPointer
			gc.displayImage(rs.img_headingTrack_headingMarker, 0, 469, 135.967, g2);

		}
	}

	private void drawAltTape(Graphics2D g2) {
		
		
		//meters
		if(this.xpd.efis_mtrs_on(this.pilot)) {
			String alt_str_meters = "" + Math.round(this.xpd.mcp_alt() * 0.3048f);
			gc.drawText2(alt_str_meters, 930, 990, 32, 0, gc.color_magenta, false, "right", g2);
			gc.drawText2("M", 932, 990, 28, 0, gc.color_navaid, false, "left", g2);
		}
		
		

		String alt_str = gc.df3.format(this.xpd.mcp_alt() % 1000);
		g2.setColor(gc.color_magenta);
		gc.drawText(alt_str, 875, 940, 34, 0, 0, 0, "left", g2);
		int ap1000 = this.xpd.mcp_alt() / 1000;
		if ( ap1000 > 0 ) {
			gc.drawText("" + ap1000, 875, 940, 44, 0, 0, 0, "right", g2);
		}else {
			gc.drawText("" + 0, 875, 940, 44, 0, 0, 0, "right", g2);
		}
		if (!this.xpd.on_gound() && this.xpd.fd_mode()) {
			if((this.xpd.altitude(pilot) >= (this.xpd.mcp_alt() - 900)) && (this.xpd.altitude(pilot) <= (this.xpd.mcp_alt() - 300)) || (this.xpd.altitude(pilot) <= (this.xpd.mcp_alt() + 900)) && (this.xpd.altitude(pilot) >= (this.xpd.mcp_alt() + 300))) {
				g2.scale(gc.scalex, gc.scaley);
				g2.setColor(gc.color_markings);
				g2.setStroke(gc.stroke_seven);
				g2.drawRect(815, 65, 135, 58);
				g2.setTransform(original_trans);
			}
		}

		float alt_tape = Math.round(alt + 0f);
		
		_altTape.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		_altTape.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		_altTape.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				
		_altTape.clearRect(0, 0, 145, 756);
		
		_altTape.setColor(gc.color_instrument_gray);
		_altTape.fillRect(10, 0, 135, 756);
		
		int alt100 = Math.round(alt_tape / 100.0f) * 100;
		for (int alt_mark = alt100 - 400; alt_mark <= alt100 + 400; alt_mark += 100) {

			int alt_y = (int) (377f - Math.round(((float) alt_mark - alt_tape) * 756f / 800f));
			_altTape.setColor(gc.color_markings);
			_altTape.setStroke(alt_marking_stroke);
			altMarkings.setLine(10, alt_y, 28, alt_y);
			_altTape.draw(altMarkings);

			if (alt_mark % 500 == 0) {
				_altTape.setStroke(alt500_marking_stroke);
				alt500Markings.setLine(10 , alt_y, 28, alt_y);
				_altTape.draw(alt500Markings);
			}
			if (alt_mark % 200 == 0) {

				if (alt_mark % 1000 == 0) {

					_altTape.setStroke(alt1k_marking_stroke);
					alt1kMarkings.setLine(5, alt_y, 28, alt_y);
					_altTape.draw(alt1kMarkings);
					
					_altTape.setStroke(alt1k_lines_stroke);
					alt1kTopLine.setLine(35, alt_y - 25f, 145, alt_y - 25f);
					alt1kBottomLine.setLine(35, alt_y + 22f, 145, alt_y + 22f);
					_altTape.draw(alt1kTopLine);
					_altTape.draw(alt1kBottomLine);
				}

				_altTape.setFont(alt_thousand_font);
				if ((alt_mark >= 1000) || (alt_mark <= -1000)) {
					if (alt_mark < 10000) {
						_altTape.drawString("" + Math.abs((alt_mark / 1000)), 65f, alt_y + 13f);
					} else {
						_altTape.drawString("" + (alt_mark / 1000), 45f,  alt_y + 13f );
					}
				}

				if ((alt_mark < 0) && (alt_mark > -1000)) {
					_altTape.drawString("-", 60f, alt_y + 16f);
				}else if(alt_mark <= -1000) {
					_altTape.drawString("-", 42f, alt_y + 16f);
				}

				_altTape.setFont(alt_hundred_font);
				_altTape.drawString("" + Math.abs((alt_mark / 100) % 10), 87f, alt_y + 13f);
				_altTape.drawString("00", 105f , alt_y + 13f);
			}
		}
		
		
		
		gc.displayImage(img_AltTape, 0f, 815, 148.5f, g2);

		//Alt box background
		if(this.xpd.efis_mtrs_on(this.pilot)) {
			g2.setColor(gc.color_instrument_gray);
			g2.fillRect((int)(850 * gc.scalex), (int)(430 * gc.scaley), (int)(115 * gc.scalex), (int)(153 * gc.scaley));
			//gc.displayImage(img_altBox_mtrs_bg, 0, 834.911, 473.050, g2);
		}else {
			g2.setColor(gc.color_instrument_gray);
			g2.fillRect((int)(850 * gc.scalex), (int)(473 * gc.scaley), (int)(115 * gc.scalex), (int)(110 * gc.scaley));
			//gc.displayImage(img_altBox_bg, 0, 834.911, 473.050, g2);
			
		}
		
		if(this.xpd.pfd_rwy_show()) {
			
			//landing alt indicator
			g2.clipRect(0, (int) gc.altTapeTop, this.getWidth(), (int) gc.altTapeHeight);
			int landing_alt_y = (int) ((555 * gc.scaley) - ((rs.img_landing_alt_indicator.getHeight() / 2) * gc.scaley)) - Math.round( (landing_alt - alt) * gc.altTapeHeight / 800f );
			int landing_alt_white_y = (int) ((555 * gc.scaley) - ((rs.img_landing_alt_indicator.getHeight() / 2) * gc.scaley)) - Math.round( ((landing_alt + 1000f) - alt) * gc.altTapeHeight / 800f );
			int landing_alt_amber_y = (int) ((555 * gc.scaley) - ((rs.img_landing_alt_indicator.getHeight() / 2) * gc.scaley)) - Math.round( ((landing_alt + 500f) - alt) * gc.altTapeHeight / 800f );
			g2.drawImage(rs.img_landing_alt_indicator, (int)(818f * gc.scalex) , landing_alt_y, (int)(rs.img_landing_alt_indicator.getWidth() * gc.scalex), (int)(rs.img_landing_alt_indicator.getHeight() * gc.scaley), null);
			//height above touchdown
			g2.setStroke(gc.stroke_four);
			g2.setColor(gc.color_markings);
			g2.drawLine((int)(817f * gc.scalex), landing_alt_y, (int)(817f * gc.scalex), landing_alt_white_y);
			g2.drawLine((int)(817f * gc.scalex), landing_alt_white_y, (int)(825f * gc.scalex), landing_alt_white_y);
			g2.setColor(gc.color_amber);
			g2.drawLine((int)(817f * gc.scalex), landing_alt_y, (int)(817f * gc.scalex), landing_alt_amber_y);
			
		}
	
		if (this.xpd.minimums_mode(pilot) == 0 && this.xpd.baro_min_show(pilot) == 0) {
			
			// baro minimums
			g2.clipRect(0, (int) gc.altTapeTop, this.getWidth(), (int) gc.altTapeHeight);
			int baro_pointer_y = (int) ((527.5f * gc.scaley) - ((rs.img_green_baro_pointer.getHeight() / 2) * gc.scaley)) - Math.round((this.xpd.baro_min(pilot) - alt) * gc.altTapeHeight / 800f);
			if(alt < this.xpd.baro_min(pilot) && this.xpd.vvi(pilot) < -20f) {
				g2.drawImage(rs.img_amber_baro_bug, (int)(780.14f * gc.scalex) , baro_pointer_y, (int)(rs.img_amber_baro_bug.getWidth() * gc.scalex), (int)(rs.img_amber_baro_bug.getHeight() * gc.scaley), null);
			}else {
				g2.drawImage(rs.img_green_baro_bug, (int)(780.14f * gc.scalex) , baro_pointer_y, (int)(rs.img_green_baro_bug.getWidth() * gc.scalex), (int)(rs.img_green_baro_bug.getHeight() * gc.scaley), null);
			}
			g2.setClip(original_clipshape);
		
		}
		
		if (this.xpd.minimums_mode(pilot) == 1 && this.xpd.baro_min_show(pilot) == 1) {
			
			// baro minimums
			g2.clipRect(0, (int) gc.altTapeTop, this.getWidth(), (int) gc.altTapeHeight);
			int baro_pointer_y = (int) ((527.5f * gc.scaley) - ((rs.img_green_baro_pointer.getHeight() / 2) * gc.scaley)) - Math.round((this.xpd.baro_min(pilot) - alt) * gc.altTapeHeight / 800f);
			if(alt < this.xpd.baro_min(pilot) && this.xpd.vvi(pilot) < -20f) {
				g2.drawImage(rs.img_amber_baro_pointer, (int)(780.14f * gc.scalex) , baro_pointer_y, (int)(rs.img_amber_baro_pointer.getWidth() * gc.scalex), (int)(rs.img_amber_baro_pointer.getHeight() * gc.scaley), null);
			}else {
				g2.drawImage(rs.img_green_baro_pointer, (int)(780.14f * gc.scalex) , baro_pointer_y, (int)(rs.img_green_baro_pointer.getWidth() * gc.scalex), (int)(rs.img_green_baro_pointer.getHeight() * gc.scaley), null);
			}
			g2.setClip(original_clipshape);
		
		}

		//Alt Bug

		int alt_y = (int) ((527.5f * gc.scaley) - ((rs.img_altBug.getHeight() / 2) * gc.scaley)) - Math.round( (this.xpd.mcp_alt() - alt) * gc.altTapeHeight / 800f );
		if ( alt_y < gc.altTapeTop ) {
			alt_y = (int) gc.altTapeTop - (int)((rs.img_altBug.getHeight() / 2) * gc.scaley);
		} else if ( alt_y > gc.altTapeTop + gc.altTapeHeight ) {
			alt_y = (int) (gc.altTapeTop + gc.altTapeHeight) - (int)((rs.img_altBug.getHeight() / 2) * gc.scaley);
		}
		g2.clipRect(0, (int) gc.altTapeTop, this.getWidth(), (int) gc.altTapeHeight);
		g2.drawImage(rs.img_altBug, (int)(804 * gc.scalex) , alt_y, (int)(rs.img_altBug.getWidth() * gc.scalex), (int)(rs.img_altBug.getHeight() * gc.scaley), null);
		g2.setClip(original_clipshape);

		// altbox
		//meters
		if(this.xpd.efis_mtrs_on(this.pilot)) {
			gc.displayImage(rs.img_altBox_mtrs, 0, 834.977, 475.158, g2);
			String alt_str_meters = "" + Math.round(this.xpd.altitude(this.pilot) * 0.3048f);
			gc.drawText2(alt_str_meters, 965, 585, 32, 0, gc.color_markings, true, "right", g2);
			gc.drawText2("M", 968, 585, 28, 0, gc.color_navaid, true, "left", g2);
			
		}else {
			gc.displayImage(rs.img_altBox, 0, 834.977, 475.158, g2);
		}

		
		g2.clipRect((int) (862 * gc.scalex), (int) (480 * gc.scaley), (int) (137 * gc.scalex), (int) (95 * gc.scaley));

//		// alt readout
		

		if (alt >= 0.0f) {

			int alt_int = (int)(alt);
			int alt_round = Math.round(alt / 10) * 10;
			int alt_20 = alt_int / 20 * 20;
			float alt_frac = (alt - (float) alt_20) / 20.0f;
			int alt_100 = (alt_round / 100) % 10;
			//int alt_100 = (alt_int / 100) % 10;
			int alt_1k = (alt_round / 1000) % 10;
			//int alt_1k = (alt_int / 1000) % 10;
			int alt_10k = (alt_round / 10000) % 10;
			//int alt_10k = (alt_int / 10000) % 10;

			float alt_ydelta = 40f * alt_frac;

			_alt20.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			_alt20.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			_alt20.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			_alt20.clearRect(0, 0, 55, 300);
			_alt20.setFont(rs.glassFont.deriveFont(38f));
			_alt20.setColor(gc.color_markings);
			_alt20.drawString("" + gc.df2.format((alt_20 + 40) % 100), 0, 70);
			_alt20.drawString("" + gc.df2.format((alt_20 + 20) % 100), 0, 110);
			_alt20.drawString("" + gc.df2.format(alt_20 % 100), 0, 150);
			
		
			
			if (alt_20 == 0) {
				_alt20.drawString("" + gc.df2.format((alt_20 + 20) % 100), 0, 190);
				_alt20.drawString("" + gc.df2.format((alt_20 + 40) % 100), 0, 230);
			}else {
				_alt20.drawString("" + gc.df2.format((alt_20 - 20) % 100), 0, 190);
				_alt20.drawString("" + gc.df2.format((alt_20 - 40) % 100), 0, 230);
			}
			
			g2.setColor(gc.color_markings);

			gc.displayImage(img_alt20, 0, 942, 359 - alt_ydelta, g2);
			
//			alt_20 %= 100;
//			
//			if(alt_20 == 80) {
//				alt_ydelta = 80f * alt_frac;
//				gc.drawText("" + alt_100, 917, 509f - alt_ydelta, 38f, 0, 0, 0, "left", g2);
//				gc.drawText("" + (alt_100 + 1) % 10, 917, 589f - alt_ydelta, 38f, 0, 0, 0, "left", g2);
//			}else {
//				gc.drawText("" + alt_100, 917, 509, 38f, 0, 0, 0, "left", g2);
//			}
//			
//			// thousands
//			if ( ( alt_100 == 9 ) && ( alt_20 == 80 ) ) {
//				alt_ydelta = 80f * alt_frac;
//				gc.drawText("" + alt_1k, 890, 507 - alt_ydelta, 44f, 0, 0, 0, "left", g2);
//				gc.drawText("" + (alt_1k + 1) % 10, 890, 587 - alt_ydelta, 44f, 0, 0, 0, "left", g2);
//			}else {
//				gc.drawText("" + alt_1k, 890, 507, 44f, 0, 0, 0, "left", g2);
//			}
			gc.drawText("" + alt_100, 917, 509, 38f, 0, 0, 0, "left", g2);
			gc.drawText("" + alt_1k, 890, 507, 44f, 0, 0, 0, "left", g2);
			
			
			// ten-thousands
			if (alt_round >= 10000) {
				gc.drawText("" + alt_10k, 865, 507, 44f, 0, 0, 0, "left", g2);
			} else {
				gc.displayImage(rs.img_green_hatch, 0, 861.663, 504.403, g2);
			}

		} else {

			int alt_int = -(int)(alt);
			int alt_round = Math.round(-alt / 10) * 10;
			int alt_20 = alt_int / 20 * 20;
			float alt_frac = (-alt - (float) alt_20) / 20.0f;
			int alt_100 = (alt_round / 100) % 10;
			int alt_1k = (alt_round / 1000) % 10;
			int alt_10k = (alt_round / 10000) % 10;

			float alt_ydelta = 40f * alt_frac;

			g2.setColor(gc.color_markings);
			_alt20.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			_alt20.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			_alt20.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			_alt20.clearRect(0, 0, 55, 300);
			_alt20.setFont(rs.glassFont.deriveFont(38f));
			_alt20.setColor(gc.color_markings);
			_alt20.drawString("" + gc.df2.format((alt_20 - 40) % 100), 0, 70);
			_alt20.drawString("" + gc.df2.format((Math.abs(alt_20 - 20)) % 100), 0, 110);
			_alt20.drawString("" + gc.df2.format(alt_20 % 100), 0, 150);
			
			if (alt_20 == 0) {
				_alt20.drawString("" + gc.df2.format((Math.abs(alt_20 - 20)) % 100), 0, 190);
				_alt20.drawString("" + gc.df2.format((Math.abs(alt_20 - 40)) % 100), 0, 230);
			}else {
				_alt20.drawString("" + gc.df2.format((alt_20 + 20) % 100), 0, 190);
				_alt20.drawString("" + gc.df2.format((alt_20 + 40) % 100), 0, 230);
			}

			gc.displayImage(img_alt20, 0, 942, 359 + alt_ydelta, g2);
			

			gc.drawText("" + alt_100, 917, 509, 38f, 0, 0, 0, "left", g2);

			// thousands
			gc.drawText("" + alt_1k, 890, 507, 44f, 0, 0, 0, "left", g2);

			if(alt < 0) {
				gc.drawText("-", 863, 505, 44f, 0, 0, 0, "left", g2);
			}
		}

		g2.setClip(original_clipshape);
		
		// QNH
		String qnh;
		String qnhMode;
	
		if (this.xpd.baro_in_hpa(pilot)) {
			qnh = gc.qnh_hpa.format(this.xpd.baro_sel_in_hg(pilot) * 33.86389f);
			qnhMode = "HPA";
		} else {
			qnh = gc.qnh_in.format(this.xpd.baro_sel_in_hg(pilot));
			qnhMode = "IN.";
		}
		
		if (this.xpd.baro_std_set(pilot)) {
			if ((this.xpd.altitude(pilot) < this.xpd.trans_lvl()) && this.xpd.vvi(pilot) < 0 && !this.xpd.on_gound()) {
				g2.setColor(gc.color_amber);
				g2.setStroke(gc.stroke_four);
				gc.drawRect2D(qnh_amber_box, 840, 83, 90, 44, 0, false, g2);
				gc.drawText("STD", 846, 90, 40, 0, 0, 0, "left", g2);
			} else {				
				g2.setColor(gc.color_lime);					
				gc.drawText("STD", 846, 90, 40, 0, 0, 0, "left", g2);
			}			
			if (this.xpd.baro_sel_show(pilot)) {
				g2.setColor(Color.WHITE);
				gc.drawText(qnh, 855, 55f, 24, 0, 0, 0, "left", g2);
				gc.drawText(qnhMode, 930f, 55f, 20, 0, 0, 0, "left", g2);
			}			
		} else {
			if (this.xpd.altitude(pilot) > this.xpd.trans_alt() && this.xpd.vvi(pilot) > 0) {
				g2.setColor(gc.color_amber);
				g2.setStroke(gc.stroke_four);
				gc.drawRect2D(qnh_amber_box, 820, 83, 170, 44, 0, false, g2);
				gc.drawText(qnh, 827, 90, 32, 0, 0, 0, "left", g2);
				gc.drawText(qnhMode, 940, 90, 24, 0, 0, 0, "left", g2);
			} else { 
				g2.setColor(gc.color_lime);
				gc.drawText(qnh, 827, 90, 32, 0, 0, 0, "left", g2);
				gc.drawText(qnhMode, 940, 90, 24, 0, 0, 0, "left", g2);
			}
		}

		//BARO
		g2.setColor(gc.color_lime);
		String mins_mode = null;
		if (this.xpd.minimums_mode(pilot) == 0) { //0 = radio, 1 = baro
			mins_mode = "RADIO";
		} else {
			mins_mode = "BARO";
		}
		
		if (this.xpd.minimums(pilot) !=    -1 && this.xpd.minimums_mode(pilot) == 0 ||
			this.xpd.minimums(pilot) != -1001 && this.xpd.minimums_mode(pilot) == 1) {
			if (this.xpd.minimums_mode(pilot) == 1 || preferences.get_preference(ZHSIPreferences.PREF_AOA_INDICATOR).equals("true")) {
				gc.drawText(mins_mode, 720, 195, 25, 0, 0, 0, "right", g2);
				int dh_minimums = (int) Math.floor(this.xpd.minimums(pilot) + 0.5);
				gc.drawText("" + dh_minimums, 720, 160, 30, 0, 0, 0, "right", g2);
			} else if ((this.xpd.on_gound() && ias < 47) || this.xpd.radio_alt_feet() > 998) {
				gc.drawText(mins_mode, 610, 945, 25, 0, 0, 0, "left", g2);
				int dh_minimums = (int) Math.floor(this.xpd.minimums(pilot) + 0.5);
				gc.drawText("" + dh_minimums, 700, 945, 30, 0, 0, 0, "left", g2);
			}
		}

	}

	private void drawADI(Graphics2D g2) {

		if (this.xpd.irs_aligned()) {
						
			g2.setClip(adiClip);
			
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(484.95f, 523.5f + pitch);
			g2.rotate(Math.toRadians(-roll), 0, 0);
			
			// sky
			g2.setColor(horizon_sky_color);
			g2.fillRect(-1172, -1172, 2344, 1172);
			// ground
			g2.setColor(horizon_ground_color);
			g2.fillRect(-1172, 0, 2344, 1172);
			// line
			g2.setColor(rs.color_markings);
			g2.setStroke(gc.stroke_six);
			g2.drawLine(-1172, 0, 1172, 0);
			
			g2.setTransform(original_trans);
			
			// pitch markings
			
			g2.setColor(rs.color_markings);
			g2.scale(gc.scalex, gc.scaley);
			
			pitchMarkingsClip.setRoundRect(274.95f, 313.5f, 420, 800, 240, 240);
			g2.clip(pitchMarkingsClip);
			
			g2.translate(484.95f, 523.5f + pitch);
			g2.rotate(Math.toRadians(-roll), 0, 0 - pitch);
			g2.setStroke(gc.stroke_four);
			
			g2.setFont(rs.glass22);
			
			g2.drawString("40", -150, - 440);
			g2.drawString("40", 120, - 440);
			g2.drawLine(-115, -448, 115, -448); //40
			g2.drawLine(-30, -420, 30, -420);
			g2.drawLine(-60, -392, 60, -392);
			g2.drawLine(-30, -364, 30, -364);
			
			g2.drawString("30", -150, -330);
			g2.drawString("30", 120, -330);
			g2.drawLine(-115, -336, 115, -336); //30
			g2.drawLine(-30, -308, 30, -308);
			g2.drawLine(-60, -280, 60, -280);
			g2.drawLine(-30, -252, 30, -252);
			
			g2.drawString("20", -150, -216);
			g2.drawString("20", 120, -216);
			g2.drawLine(-115, -224, 115, -224); //20
			g2.drawLine(-30, -196, 30, -196);
			g2.drawLine(-60, -168, 60, -168);
			g2.drawLine(-30, -140, 30, -140);
			
			g2.drawString("10", -150, -104);
			g2.drawString("10", 120, -104);
			g2.drawLine(-115, -112, 115, -112); //10
			g2.drawLine(-30, -84, 30, -84);
			g2.drawLine(-60, -56, 60, -56);
			g2.drawLine(-30, -28, 30, -28);
			
			g2.drawLine(-30, 28, 30, 28);
			g2.drawLine(-60, 56, 60, 56);
			g2.drawLine(-30, 84, 30, 84);
			g2.drawLine(-115, 112, 115, 112); //10
			g2.drawString("10", -150, + 30 * 4);
			g2.drawString("10", 120, + 30 * 4);
			
			g2.drawLine(-30, 140, 30, 140);
			g2.drawLine(-60, 168, 60, 168);
			g2.drawLine(-30, 196, 30, 196);
			g2.drawLine(-115, 224, 115, 224); //20
			g2.drawString("20", -150, + 29 * 8);
			g2.drawString("20", 120, + 29 * 8);
			
			g2.drawLine(-30, 252, 30, 252);
			g2.drawLine(-60, 280, 60, 280);
			g2.drawLine(-30, 308, 30, 308);
			g2.drawLine(-115, 336, 115, 336); //30
			g2.drawString("30", -150, + 28.8f * 12);
			g2.drawString("30", 120, + 28.8f * 12);
		
			g2.drawLine(-30, 364, 30, 364);
			g2.drawLine(-60, 392, 60, 392);
			g2.drawLine(-30, 420, 30, 420);
			g2.drawLine(-115, 448, 115, 448); //40
			g2.drawString("40", -150, + 28.5f * 16);
			g2.drawString("40", 120, + 28.5f * 16);
									
			g2.setTransform(original_trans);
						
			g2.setClip(original_clipshape);

			if (roll <= -35 || roll >= 35) {
				gc.displayImage(rs.img_bankPointer_amber, roll * -1, 465.466, 279.980, g2);
			} else {
				gc.displayImage(rs.img_bankPointer, roll * -1, 465.466, 279.980, g2);
			}
			gc.displayImage(rs.img_bankIndicators, 0, 245.041f, 648.251f, g2);
			
			//slip indicator
			
			g2.scale(gc.scalex, gc.scaley);

			g2.rotate(Math.toRadians(-roll), 484.95f, 523.5f);
			
			g2.drawImage(rs.img_slipIndicator, (int)(465.466f - this.xpd.slip_deg() * 3f), 309, null);
			
			g2.setTransform(original_trans);
		}

		gc.displayImage(rs.img_aircraftWings, 0, 262.366f, 490.525f, g2);
		
		// tcas resolution advisories
			
		float tcas_y_delta = 0;	
			
		if (this.xpd.irs_aligned() && this.xpd.tcas_pfd_ra_dn()) {		
			g2.setStroke(new BasicStroke(8f * gc.scaling_factor));
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(485, 527);
			g2.setColor(Color.RED);
			tcas_y_delta = this.xpd.tcas_pfd_ra_dn2(pilot) * -11.18f;
			tcas_left_bar.setLine(-30f, tcas_y_delta, -130f, -250);
			tcas_hor_bar.setLine(-30f, tcas_y_delta, 30f, tcas_y_delta);		
			tcas_right_bar.setLine(130f, -250, 30f, tcas_y_delta);
			g2.draw(tcas_left_bar);
			g2.draw(tcas_hor_bar);
			g2.draw(tcas_right_bar);
			g2.setTransform(original_trans);		
		}
			
		if (this.xpd.irs_aligned() && this.xpd.tcas_pfd_ra_up()) {
			g2.setStroke(new BasicStroke(8f * gc.scaling_factor));
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(485, 527);
			g2.setColor(Color.RED);
			tcas_y_delta = this.xpd.tcas_pfd_ra_up2(pilot) * -11.18f;
			tcas_left_bar.setLine(-30f, tcas_y_delta, -130f, 250);
			tcas_hor_bar.setLine(-30f, tcas_y_delta, 30f, tcas_y_delta);		
			tcas_right_bar.setLine(130f, 250, 30f, tcas_y_delta);
			g2.draw(tcas_left_bar);
			g2.draw(tcas_hor_bar);
			g2.draw(tcas_right_bar);
			g2.setTransform(original_trans);
		}
		
		// flight directors

		if(this.xpd.irs_aligned() && this.xpd.fd_mode()) {

			g2.setStroke(new BasicStroke(11f * gc.scaling_factor));

			g2.scale(gc.scalex, gc.scaley);
			g2.translate(485, 527);
			g2.setColor(gc.color_magenta);

			if(this.xpd.fd_pitch_show(pilot)) {
				float y_delta = (this.xpd.pitch() - this.xpd.ap_fd_pitch_deg()) * 11.18f;
				fd_hor_bar.setLine(-160f, y_delta, 160f, y_delta);
				g2.draw(fd_hor_bar);
			}

			if(this.xpd.fd_roll_show(pilot)) {
				float x_delta = (-this.xpd.roll() + this.xpd.ap_fd_roll_deg()) * 4f;
				fd_ver_bar.setLine(x_delta, -160f, x_delta, 160f);
				g2.draw(fd_ver_bar);
			}

			g2.setTransform(original_trans);
			
		}
			
		gc.displayImage(rs.img_aircraftNose, 0, 262.366f, 490.525f, g2);
		
		//FPV
		//scale -16 to 16, negative goes right
		if(this.xpd.efis_fpv_show(pilot) && !this.xpd.on_gound()) {
			//float xdelta = this.xpd.fpv_beta() * 10f;
			//float ydelta = this.xpd.fpv_alpha() * 11f;
			float xdelta = this.xpd.efis_fpv_horiz(pilot) * 10f;
			float ydelta = this.xpd.efis_fpv_vert(pilot) * 11f;

			g2.scale(gc.scalex, gc.scaley);
			g2.translate(485 - xdelta, 525 + ydelta);
			g2.setColor(rs.color_markings);
			g2.setStroke(rs.stroke5);
			
			g2.drawLine(0, -15, 0, -30);
			g2.drawLine(-50, 0, -15, 0);
			g2.drawOval(-15, -15, 30, 30);
			g2.drawLine(15, 0, 50, 0);
			
			g2.setTransform(original_trans);
		}

	}

}