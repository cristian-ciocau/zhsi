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
 
package org.andreels.zhsi.displays.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.displays.DUBaseClass;


public class ENGINE extends DUBaseClass {

	private static final long serialVersionUID = 1L;
	
	private RoundRectangle2D overUnder = new RoundRectangle2D.Float();
	private Arc2D.Float eng1n1 = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng1n1_fill = new Arc2D.Float(Arc2D.PIE);
	private Arc2D.Float eng2n1 = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float n1_diff = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng2n1_fill = new Arc2D.Float(Arc2D.PIE);
	private Arc2D.Float eng1egt = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng1egt_fill = new Arc2D.Float(Arc2D.PIE);
	private Arc2D.Float eng2egt = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng2egt_fill = new Arc2D.Float(Arc2D.PIE);
	private Arc2D.Float fuel1 = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float fuel2 = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float fuelc = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng1egt_amber = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng2egt_amber = new Arc2D.Float(Arc2D.OPEN);
	
	private Color tai1_color;
	private Color tai2_color;
	
	boolean fuel_kgs = false;
	boolean fuel_low_1 = false;
	boolean fuel_low_c = false;
	boolean fuel_low_2 = false;
	boolean fuel_config = false;
	boolean fuel_imbal = false;
	
	String n1_mode = null;
	
	Color rev_color1;
	Color rev_color2;
	
	int text_x = 300;
	int text_y = 590;
	
	public BufferedImage img_stab_trim_tape;
	public BufferedImage img_rudder_trim_tape;

	public ENGINE(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);
		try {
			img_stab_trim_tape = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/engine/img_stab_trim_tape.png"));
			img_rudder_trim_tape = ImageIO.read(getClass().getResource("/org/andreels/zhsi/resources/engine/img_rudder_trim_tape.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawInstrument(Graphics2D g2) {

		if(this.xpd.power_on()) {
	
			//unit
			if(this.preferences.get_preference(ZHSIPreferences.PREF_FUEL_LBS).equals("true")) {
				fuel_kgs = false;
			}else {
				fuel_kgs = true;
			}

			//low
			if(this.xpd.fuel_qty_kgs_1() < 907f && (this.xpd.fuel_tank_lft1_pos() || this.xpd.fuel_tank_lft2_pos())) {
				fuel_low_1 = true;
			}else {
				fuel_low_1 = false;
			}
			if(this.xpd.fuel_qty_kgs_c() < 907f && (this.xpd.fuel_tank_ctr1_pos() || this.xpd.fuel_tank_ctr2_pos())) {
				fuel_low_c = true;
			}else {
				fuel_low_c = false;
			}
			if(this.xpd.fuel_qty_kgs_2() < 907f && (this.xpd.fuel_tank_rgt1_pos() || this.xpd.fuel_tank_rgt2_pos())) {
				fuel_low_2 = true;
			}else {
				fuel_low_2 = false;
			}

			//imbal
			if((((this.xpd.fuel_qty_kgs_1() - this.xpd.fuel_qty_kgs_2()) > 453f) || ((this.xpd.fuel_qty_kgs_2() - this.xpd.fuel_qty_kgs_1()) > 453f)) && !this.xpd.on_gound()) {
				fuel_imbal = true;
			}else {
				fuel_imbal = false;
			}

			//config
			if((this.xpd.eng1_n2() > 0f || this.xpd.eng2_n2() > 0f) && (this.xpd.fuel_qty_kgs_c() > 726f) && (!this.xpd.fuel_tank_ctr1_pos() && !this.xpd.fuel_tank_ctr2_pos())) {
				fuel_config = true;
			}else {
				fuel_config = false;
			}
			
			String tat = "";
			if (this.xpd.outside_air_temp_c() > 0) {
				tat = "+" + (int)(Math.round(this.xpd.outside_air_temp_c())) + "c";
			}else {
				tat = (int)(Math.round(this.xpd.outside_air_temp_c())) + "c";
			}
			
			g2.scale(gc.scalex, gc.scaley);
			g2.setFont(rs.glassFont.deriveFont(24f));
			g2.setColor(gc.color_navaid);
			g2.drawString("TAT", 70, 60);
			g2.setColor(gc.color_markings);
			g2.setFont(rs.glassFont.deriveFont(30f));
			g2.drawString(tat, 130, 60);
	
			g2.setColor(gc.color_lime);
			g2.setFont(rs.glassFont.deriveFont(26f));
			g2.drawString(this.xpd.n1_mode(), 300, 60);
			g2.setTransform(original_trans);		
			
			drawDial(65, 80, eng1n1,eng1n1_fill, this.xpd.eng1_n1(), 210, g2);
			
			drawDial(350, 80, eng2n1,eng2n1_fill, this.xpd.eng2_n1(), 210, g2);
			
			drawDial(65, 310, eng1egt,eng1egt_fill, this.xpd.eng1_egt(), 210, g2);
			
			drawDial(350, 310, eng2egt,eng2egt_fill, this.xpd.eng2_egt(), 210, g2);
			
			if(this.preferences.get_preference(ZHSIPreferences.PREF_COMPACT_DISPLAY).equals("true")) {
				showCompactDisplay();
			}else {
				displayFuelFlow();
			}
					
			drawEngAnnunciators();
			
			if (preferences.get_preference(ZHSIPreferences.PREF_FUEL_OVER_UNDER).equals("true")) {
				if (fuel_kgs) {
					displayOverUnderFuel(620, 860, this.xpd.fuel_qty_kgs_1(), this.xpd.fuel_qty_kgs_c(), this.xpd.fuel_qty_kgs_2());
				} else {
					displayOverUnderFuel(620, 860, this.xpd.fuel_qty_lbs_1(), this.xpd.fuel_qty_lbs_c(), this.xpd.fuel_qty_lbs_2());
				}	
			} else {
				if (fuel_kgs) {
					displaySidebySideFuel(this.xpd.fuel_qty_kgs_1(), this.xpd.fuel_qty_kgs_c(), this.xpd.fuel_qty_kgs_2());
				} else {
					displaySidebySideFuel(this.xpd.fuel_qty_lbs_1(), this.xpd.fuel_qty_lbs_c(), this.xpd.fuel_qty_lbs_2());
				}
			}
			
			g2.scale(gc.scalex, gc.scaley);
			g2.setColor(gc.color_markings);
			g2.setStroke(gc.stroke_four);
			g2.setFont(rs.glassFont.deriveFont(42f));
			g2.drawRect(170, 120, 150, 50);
			g2.drawString("" + gc.dme_formatter.format(this.xpd.eng1_n1()), 200, 163);
			g2.drawRect(170, 350, 110, 50);
			// if(this.xpd.eng1_n2() >= 3.9f) {
			if(this.xpd.eicas_ff1()) {
				g2.drawString("" + Math.round(this.xpd.eng1_egt()), 185, 393);
			}
						
			g2.drawRect(450, 120, 150, 50);
			g2.drawString("" + gc.dme_formatter.format(this.xpd.eng2_n1()), 480, 163);
			g2.drawRect(450, 350, 110, 50);
			// if(this.xpd.eng2_n2() > 3.9f) {
			if(this.xpd.eicas_ff2()) {
				g2.drawString("" + Math.round(this.xpd.eng2_egt()), 465, 393);
			}
			g2.setTransform(original_trans);
			
			//N1 limits readout			
			if(this.xpd.n1_mode() != "---") {
				g2.scale(gc.scalex, gc.scaley);
				g2.setColor(gc.color_lime);
				g2.setFont(rs.glassFont.deriveFont(32f));
				if(!(this.xpd.reverser1_moved() || this.xpd.reverser1_deployed())) {
					g2.drawString("" + gc.dme_formatter.format(this.xpd.eng_n1_bug_1()), 220, 110);
				}
				if(!(this.xpd.reverser2_moved() || this.xpd.reverser2_deployed())) {
					g2.drawString("" + gc.dme_formatter.format(this.xpd.eng_n1_bug_2()), 500, 110);
				}
				g2.setTransform(original_trans);
			}
			
			//Reversers
			g2.scale(gc.scalex, gc.scaley);
			if(this.xpd.reverser1_moved() || this.xpd.reverser1_deployed()) {
				g2.setFont(rs.glassFont.deriveFont(32f));
				if (this.xpd.reverser1_deployed()) {
					rev_color1 = gc.color_lime;
				}else {
					rev_color1 = gc.color_amber;
				}
				g2.setColor(rev_color1);
				g2.drawString("REV", 220, 110);
			}
			if(this.xpd.reverser2_moved() || this.xpd.reverser2_deployed()) {
				if (this.xpd.reverser2_deployed()) {
					rev_color2 = gc.color_lime;
				}else {
					rev_color2 = gc.color_amber;
				}
				g2.setColor(rev_color2);
				g2.drawString("REV", 500, 110);
			}
			g2.setTransform(original_trans);
			
			g2.scale(gc.scalex, gc.scaley);
			g2.setColor(gc.color_markings);
			g2.setFont(rs.glassFont.deriveFont(26f));
			g2.drawString("10", 90, 170);
			g2.drawString("8", 90, 225);
			g2.drawString("6", 125, 260);
			g2.drawString("4", 170, 268);
			g2.drawString("2", 215, 245);
			g2.drawString("0", 240, 200);
			
			g2.drawString("10", 375, 170);
			g2.drawString("8", 375, 225);
			g2.drawString("6", 410, 260);
			g2.drawString("4", 455, 268);
			g2.drawString("2", 500, 245);
			g2.drawString("0", 525, 200);
			
			g2.setTransform(original_trans);
			
			//TAI
			if(this.xpd.eng1_tai() == 1f) {
				tai1_color = gc.color_amber;
			}else {
				tai1_color = gc.color_lime;
			}
			if(this.xpd.eng2_tai() == 1f) {
				tai2_color = gc.color_amber;
			}else {
				tai2_color = gc.color_lime;
			}
			if(this.xpd.eng1_tai() >= 0.5f) {
				gc.drawText2("TAI", 120, 930, 24f, 0, tai1_color, false, "center", g2);
			}
			
			if(this.xpd.eng2_tai() >= 0.5f) {
				gc.drawText2("TAI", 400, 930, 24f, 0, tai2_color, false, "center", g2);

			}
			
			//STAB TRIM
			if(preferences.get_preference(ZHSIPreferences.PREF_TRIM_INDICATOR).equals("true")) {
				drawStabTrim();
			}
			
			//RUDDER TRIM
			if(preferences.get_preference(ZHSIPreferences.PREF_RUDDER_INDICATOR).equals("true")) {
				drawRudderTrim();
			}
			
			drawFaiulures();

		}
	}

	private void drawFaiulures() {
		
		if(this.xpd.eng1_out()) {	
			gc.drawText2("ENG FAIL", 175, 600, 32f, 0, gc.color_amber, false, "center", g2);	
		}
		if(this.xpd.eng2_out()) {	
			gc.drawText2("ENG FAIL", 460, 600, 32f, 0, gc.color_amber, false, "center", g2);	
		}		
	}

	private void showCompactDisplay() {
				
		//N2
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("N", text_x, text_y);
		g2.drawString("2", text_x + 20, text_y + 7);
		g2.setColor(gc.color_markings);
		g2.setTransform(original_trans);
		gc.drawText(gc.mfd_n2.format(this.xpd.eng1_n2()), text_x - 115, text_y - 125, 30f, 0, 0, 0, "right", g2);
		gc.drawText(gc.mfd_n2.format(this.xpd.eng2_n2()), text_x + 195, text_y - 125, 30f, 0, 0, 0, "right", g2);
				
		//FF
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		if (this.xpd.fuel_flow_used_show()) {
			g2.drawString("FUEL USED", text_x - 60, text_y + 50);
		} else {
			g2.drawString("FF", text_x, text_y + 50);
		}
		
		g2.setColor(gc.color_markings);
		g2.setFont(rs.glassFont.deriveFont(30f));
		// if (this.xpd.eng1_n2() > 0f) {
		if (this.xpd.eicas_ff1() || this.xpd.fuel_flow_used_show()) {	
			g2.drawString(gc.mfd_ff.format(this.xpd.fuel_flow1()), text_x - 180, text_y + 51);	
		}
		// if (this.xpd.eng2_n2() > 0f) {
		if (this.xpd.eicas_ff2() || this.xpd.fuel_flow_used_show()) {
			g2.drawString(gc.mfd_ff.format(this.xpd.fuel_flow2()), text_x + 130, text_y + 51);
		}
		
		//OIL PRESS	
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("OIL PRESS", text_x - 57, text_y + 100);
		g2.setColor(gc.color_markings);
		g2.setTransform(original_trans);
		if(this.xpd.eicas_oil_press1()) {
			gc.drawText(gc.mfd_oil_press.format(this.xpd.eng_oil_press_1()), text_x - 115, text_y - 225, 30f, 0, 0, 0, "right", g2);
		}
		if(this.xpd.eicas_oil_press2()) {
			gc.drawText(gc.mfd_oil_press.format(this.xpd.eng_oil_press_2()), text_x + 165, text_y - 225, 30f, 0, 0, 0, "right", g2);
		}
		
		//OIL TEMP
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("OIL TEMP", text_x - 47, text_y + 150);
		g2.setColor(gc.color_markings);
		g2.setTransform(original_trans);
		if (this.xpd.eicas_oil_temp1()) {
			gc.drawText(gc.mfd_oil_temp.format(this.xpd.oil_temp_c_1()), text_x - 115, text_y - 275, 30, 0, 0, 0, "right", g2);
		}
		if (this.xpd.eicas_oil_temp2()) {
			gc.drawText(gc.mfd_oil_temp.format(this.xpd.oil_temp_c_2()), text_x + 165, text_y - 275, 30, 0, 0, 0, "right", g2);
		}
			
		//OIL QTY
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("OIL QTY", text_x - 37, text_y + 200);
		g2.setColor(gc.color_markings);
		g2.setTransform(original_trans);
		gc.drawText(gc.mfd_oil_qty.format(this.xpd.oil_qty_1()), text_x - 115, text_y - 325, 30, 0, 0, 0, "right", g2);
		gc.drawText(gc.mfd_oil_qty.format(this.xpd.oil_qty_2()), text_x + 165, text_y - 325, 30, 0, 0, 0, "right", g2);
		
		//VIB
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("VIB", text_x - 12, text_y + 250);
		g2.setColor(gc.color_markings);
		g2.setTransform(original_trans);
		gc.drawText(gc.mfd_vib.format(getVib1(this.xpd.n1_percent1())), text_x - 115, text_y - 375, 30, 0, 0, 0, "right", g2);
		gc.drawText(gc.mfd_vib.format(getVib2(this.xpd.n1_percent2())), text_x + 165, text_y - 375, 30, 0, 0, 0, "right", g2);
	}

	private void drawStabTrim() {
		
		// this.xpd.stab_trim() = -0.39 = 5 units, therefore (-0.39 + 1) / 5 = 8.1967 
				
		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(gc.color_markings);
		int stab_trim_y_delta = (int)((110 * this.xpd.stab_trim()) - 3);
		g2.drawString(String.format("%.2f", (this.xpd.stab_trim() + 1) * 8.1967), 805, 485 + stab_trim_y_delta);	
		int[] stab_trim_t_x = {875, 898, 898, 875};
		int[] stab_trim_t_y = {465 + stab_trim_y_delta, 470 + stab_trim_y_delta, 480 + stab_trim_y_delta, 485 + stab_trim_y_delta};
		g2.fillPolygon(stab_trim_t_x, stab_trim_t_y, 4);		
		g2.setStroke(gc.stroke_four);
		g2.setColor(Color.BLACK);
		g2.drawLine(880, 475 + stab_trim_y_delta, 898, 475 + stab_trim_y_delta);
		g2.setTransform(original_trans);
		gc.displayImage(img_stab_trim_tape, 0, 900, 440, g2);
				
	}
	
	private void drawRudderTrim() {
		
		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(gc.color_markings);
		g2.drawString(String.format("%+.2f", this.xpd.rudder_trim() * 5 / 0.3), 815 + this.xpd.rudder_trim() * 140, 730);
		g2.fillPolygon(new int[] {(int)(840 + this.xpd.rudder_trim() * 140), (int)(850 + this.xpd.rudder_trim() * 140), (int)(860 + this.xpd.rudder_trim() * 140)}, new int[] {700, 690, 700}, 3);
		g2.setTransform(original_trans);
		gc.displayImage(img_rudder_trim_tape, 0, 702, 370, g2);
		
	}

	private void displayFuelFlow() {
		
		g2.setStroke(gc.stroke_four);
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		if(this.xpd.fuel_flow_used_show()) {
			g2.drawString("FUEL USED", text_x - 60, text_y + 50);
		}else {
			g2.drawString("FF", text_x, text_y + 50);
		}
		g2.setColor(gc.color_markings);
		g2.drawRect(text_x - 190, text_y + 17, 80, 43);
		g2.drawRect(text_x + 125, text_y + 17, 80, 43);
		g2.setFont(rs.glassFont.deriveFont(30f));
		// if(this.xpd.eng1_n2() > 0f) {
		if (this.xpd.eicas_ff1() || this.xpd.fuel_flow_used_show()) {
			g2.drawString(gc.mfd_ff.format(this.xpd.fuel_flow1()), text_x - 180, text_y + 51);	
		}
		// if(this.xpd.eng2_n2() > 0f) {
		if (this.xpd.eicas_ff2() || this.xpd.fuel_flow_used_show()) {
			g2.drawString(gc.mfd_ff.format(this.xpd.fuel_flow2()), text_x + 135, text_y + 51);
		}
		g2.setTransform(original_trans);
		
	}

	private void displayOverUnderFuel(int x, int y, float qty1, float qtyc, float qty2) {
		
		float fuelqty1 = qty1 / 1000f;
		float fuelqtyc = qtyc / 1000f;
		float fuelqty2 = qty2 / 1000f;
		
		String fuelString1 = String.format("%.2f", fuelqty1);
		String fuelStringc = String.format("%.2f", fuelqtyc);
		String fuelString2 = String.format("%.2f", fuelqty2);
		String fuelTotal = String.format("%.1f", fuelqty1 + fuelqtyc + fuelqty2);

		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(gc.color_navaid);
		g2.translate(x, y);
		g2.setStroke(gc.stroke_fivehalf);
		overUnder.setRoundRect(0, 0, 400, 115, 30, 30);
		g2.draw(overUnder);
		g2.setColor(Color.BLACK);
		g2.fillRect(28, -14, 145, 28);
		g2.fillRect(198, -14, 170, 28);
		g2.setColor(gc.color_navaid);
		g2.setFont(rs.glassFont.deriveFont(28f));
		g2.drawString("FUEL QTY", 30, 14);
		if(fuel_kgs) {
			g2.drawString("KGS X 1000", 202, 14);
		}else {
			g2.drawString("LBS X 1000", 202, 14);
		}
		g2.drawString("TOTAL", 10, 165);
		
		if(fuel_low_1) {
			g2.setColor(gc.color_amber);
			g2.setFont(rs.glassFont.deriveFont(28f));
			g2.drawString("LOW", 40, 105);
		}else {
			if(fuel_imbal && fuelqty1 < fuelqty2) {
				g2.setColor(gc.color_amber);
				g2.setFont(rs.glassFont.deriveFont(28f));
				g2.drawString("IMBAL", 20, 105);
			}else {
				g2.setColor(gc.color_markings);
			}
			
		}
		g2.setFont(rs.glassFont.deriveFont(38f));
		g2.drawString(fuelString1, 25, 75);		

		if (fuel_low_c) {
			g2.setColor(gc.color_markings);
		} else {
			if(fuel_config) {
				g2.setColor(gc.color_amber);
				g2.setFont(rs.glassFont.deriveFont(28f));
				g2.drawString("CONFIG", 135, 105);	
			} else {
				g2.setColor(gc.color_markings);
			}
		}
		g2.setFont(rs.glassFont.deriveFont(38f));
		g2.drawString(fuelStringc, 150, 75);

		if(fuel_low_2) {
			g2.setColor(gc.color_amber);
			g2.setFont(rs.glassFont.deriveFont(28f));
			g2.drawString("LOW", 290, 105);
		}else {
			if(fuel_imbal && fuelqty2 < fuelqty1) {
				g2.setColor(gc.color_amber);
				g2.setFont(rs.glassFont.deriveFont(28f));
				g2.drawString("IMBAL", 275, 105);
			}else {
				g2.setColor(gc.color_markings);
			}
			
		}
		g2.setFont(rs.glassFont.deriveFont(38f));
		g2.drawString(fuelString2, 275, 75);
		
		g2.setColor(gc.color_markings);
		g2.setFont(rs.glassFont.deriveFont(44f));
		g2.drawString(fuelTotal, 130, 165);
		g2.setTransform(original_trans);
		
	}

	private void displaySidebySideFuel(float qty1, float qtyc, float qty2) {
	
		String fuel_unit = "KG";
		if(!fuel_kgs) {
			fuel_unit = "LB";
		}
		boolean fuel_less_1 = false;
		boolean fuel_less_2 = false;
		
		if(qty1 < qty2) {
			fuel_less_1 = true;
		}else {
			fuel_less_1 = false;
		}
		if(qty2 < qty1) {
			fuel_less_2 = true;
		}else {
			fuel_less_2 = false;
		}
		
		drawSidebySideFuel(770, 750, fuelc, qtyc, 160, "CTR", fuel_low_c, false, false, fuel_config, g2);
		drawSidebySideFuel(670, 900, fuel1, qty1, 160, "1", fuel_low_1, fuel_imbal, fuel_less_1, false, g2);
		drawSidebySideFuel(870, 900, fuel2, qty2, 160, "2", fuel_low_2, fuel_imbal, fuel_less_2, false, g2);
		
		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(gc.color_navaid);
		g2.setFont(rs.glassFont.deriveFont(24f));
		g2.drawString("FUEL", 820, 910);
		g2.drawString(fuel_unit, 835, 940);
		g2.setTransform(original_trans);
		
	}

	private void drawSidebySideFuel(int x, int y, Arc2D.Float gauge, float qty, int diameter, String tank, boolean fuel_low, boolean imbal, boolean less, boolean config, Graphics2D g2) {
		
		String fuelString = String.format("%6s", (int) qty);
		float fuelqty = (qty / 3900f) * 250f;
		if(!fuel_kgs) {
			fuelqty = (qty / 8598f) * 250f;
		}
		float txtOffset = diameter * 0.45f;
		
		if(tank == "CTR") {
			txtOffset = diameter * 0.35f;
			fuelqty = (qty / 13000f) * 250f;
			if(!fuel_kgs) {
				fuelqty = (qty / 28660f) * 250f;
			}
			fuelString = String.format("%6s", (int) qty);
		}
		
		g2.scale(gc.scalex, gc.scaley);
		g2.translate(x, y);
		if((fuel_low && (tank == "1" || tank == "2")) || (imbal && less) || config) {
			g2.setColor(gc.color_amber);
		}else {
			g2.setColor(gc.color_markings);
		}
		
		g2.setStroke(gc.stroke_ten);
		gauge.setFrame(0,0,diameter, diameter);
		gauge.setAngleStart(215);
		gauge.setAngleExtent(fuelqty * -1);
		g2.draw(gauge);
		g2.setTransform(original_trans);
		g2.scale(gc.scalex, gc.scaley);
		g2.translate(x, y);
		g2.setColor(gc.color_markings);
		g2.setStroke(gc.stroke_four);
		g2.rotate(Math.toRadians(-215), diameter/2, diameter/2);
		g2.drawLine(diameter - 12, diameter/2, diameter, diameter/2);
		for(int i = 0; i < 10; i++) {
			g2.rotate(Math.toRadians(25f), diameter/2, diameter/2);
			g2.drawLine(diameter -12, diameter/2, diameter, diameter/2);
		}
		g2.setTransform(original_trans);
		
		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(gc.color_navaid);
		g2.setFont(rs.glassFont.deriveFont(24f));
		g2.drawString(tank, x + txtOffset , y + (diameter * 0.3f));
		
		if(fuel_low) {
			g2.setColor(gc.color_markings);
			if(tank == "1" || tank == "2") {
				g2.setColor(gc.color_amber);
				g2.setFont(rs.glassFont.deriveFont(24f));
				g2.drawString("LOW", x + (diameter * 0.35f), y + (diameter * 0.75f));
			}
		} else {
			if((imbal && less) || config) {
				g2.setColor(gc.color_amber);
				g2.setFont(rs.glassFont.deriveFont(24f));
				if(config) {
					g2.drawString("CONFIG", x + (diameter * 0.20f), y + (diameter * 0.75f));
				}else {
					g2.drawString("IMBAL", x + (diameter * 0.35f), y + (diameter * 0.75f));
				}
				
			}else {
				g2.setColor(gc.color_markings);
			}	
		}
		g2.setFont(rs.glassFont.deriveFont(32f));
		g2.drawString(fuelString, x + (diameter * 0.25f), y + (diameter * 0.6f));
		g2.setTransform(original_trans);			
	}

	private void drawEngAnnunciators() {
				
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("N", 300, 310);
		g2.drawString("1", 320, 317);
		g2.drawString("EGT", 290, 530);
		
		g2.setColor(gc.color_instrument_gray);
		g2.fillRect(700, 30, 78, 32);
		g2.fillRect(900, 30, 78, 32);
		g2.setColor(gc.color_navaid);
		g2.drawString("ENG 1", 700, 60);
		g2.drawString("ENG 2", 900, 60);
		
		if(this.xpd.eng_start_value_1()) {
			//ENG1 Start Valve
			g2.setColor(gc.color_amber);
			g2.fillRect(650, 63, 180, 60);
			g2.setFont(rs.glassFont.deriveFont(24f));
			g2.setColor(Color.BLACK);
			g2.drawString("START VALVE", 658, 91);
			g2.drawString("OPEN", 710, 116);
		}
		if(this.xpd.eng_oil_bypass_1()) {
			//ENG1 Oil Filter Bypass
			g2.setColor(gc.color_amber);
			g2.fillRect(650, 123, 180, 60);
			g2.setFont(rs.glassFont.deriveFont(24f));
			g2.setColor(Color.BLACK);
			g2.drawString("OIL FILTER", 667, 151);
			g2.drawString("BYPASS", 697, 176);
		}
		if(this.xpd.oil_pressure_annun_1()) {
			//ENG1 Low Oil Press
			g2.setColor(gc.color_amber);
			g2.fillRect(650, 183, 180, 60);
			g2.setFont(rs.glassFont.deriveFont(24f));
			g2.setColor(Color.BLACK);
			g2.drawString("LOW OIL", 690, 211);
			g2.drawString("PRESSURE", 675, 236);
		}		
		if(this.xpd.eng_start_value_2()) {
			//ENG2 Start Valve
			g2.setColor(gc.color_amber);
			g2.fillRect(850, 63, 180, 60);
			g2.setFont(rs.glassFont.deriveFont(24f));
			g2.setColor(Color.BLACK);
			g2.drawString("START VALVE", 858, 91);
			g2.drawString("OPEN", 910, 116);
		}
		if(this.xpd.eng_oil_bypass_2()) {
			//ENG2 Oil Filter Bypass
			g2.setColor(gc.color_amber);
			g2.fillRect(850, 123, 180, 60);
			g2.setFont(rs.glassFont.deriveFont(24f));
			g2.setColor(Color.BLACK);
			g2.drawString("OIL FILTER", 867, 151);
			g2.drawString("BYPASS", 897, 176);
		}
		if(this.xpd.oil_pressure_annun_2()) {
			//ENG2 Low Oil Press
			g2.setColor(gc.color_amber);
			g2.fillRect(850, 183, 180, 60);
			g2.setFont(rs.glassFont.deriveFont(24f));
			g2.setColor(Color.BLACK);
			g2.drawString("LOW OIL", 890, 211);
			g2.drawString("PRESSURE", 875, 236);
		}

		g2.setColor(gc.color_instrument_gray);
		g2.setStroke(gc.stroke_six);
		g2.drawRect(650, 63, 180, 60);
		g2.drawRect(650, 123, 180, 60);
		g2.drawRect(650, 183, 180, 60);
		
		g2.drawRect(850, 63, 180, 60);
		g2.drawRect(850, 123, 180, 60);
		g2.drawRect(850, 183, 180, 60);
		
		g2.setTransform(original_trans);
		
	}

	private void drawDial(int x, int y, Arc2D.Float dial, Arc2D.Float backg , float value, int diameter, Graphics2D g2) {
		
		float tq_rotate = 0f;
		float rotation = 0f;
		float req_rotation;
		float n1_bug_rotation;
		float n1_value;
		float ff;
		boolean egt_redline = false;
		
		if (dial == eng1n1) {
			n1_bug_rotation = (this.xpd.eng_n1_bug_1() / 100f) * 200f;
			n1_value = value;
			req_rotation = this.xpd.eng1_n1_req() * 202f;
			tq_rotate = this.xpd.thr_lvr1() * 202f;
			ff = this.xpd.fuel_flow1();			
		} else {
			n1_bug_rotation = (this.xpd.eng_n1_bug_2() / 100f) * 200f;
			n1_value = value;
			req_rotation = this.xpd.eng2_n1_req() * 202f;
			tq_rotate = this.xpd.thr_lvr2() * 202f;
			ff = this.xpd.fuel_flow2();			
		}
		
		if(dial == eng1n1 || dial == eng2n1) {
			rotation = (value / 100f) * 202f;
		}
		
		if(dial == eng1egt || dial == eng2egt) {
			rotation = (value / 1000f) * 210f;
			if(dial == eng1egt) {
				egt_redline = this.xpd.egt_redline1();
			}else {
				egt_redline = this.xpd.egt_redline2();
			}
		}

		//background
		g2.scale(gc.scalex, gc.scaley);
		g2.translate(x, y);
		if((dial == eng1n1 || dial == eng2n1) && n1_value < 103) {
			g2.setColor(gc.color_instrument_gray);
		}else {
			g2.setColor(Color.RED);
		}
		if(dial == eng1egt || dial == eng2egt) {
			g2.setColor(gc.color_instrument_gray);
		}
		backg.setFrame(0, 0, diameter, diameter);
		backg.setAngleStart(0);
		backg.setAngleExtent(rotation * -1);

		// if(dial == eng1egt && this.xpd.eng1_n2() >= 3.9f) {
		if(dial == eng1egt && this.xpd.eicas_ff1()) {
			g2.fill(backg);
		}
		// if(dial == eng2egt && this.xpd.eng2_n2() >= 3.9f) {
		if(dial == eng2egt && this.xpd.eicas_ff2()) {
			g2.fill(backg);
		}
		if (dial == eng1n1 || dial == eng2n1) {
			g2.fill(backg);
		}
		g2.setTransform(original_trans);
				
		//outline
		g2.scale(gc.scalex, gc.scaley);
		g2.translate(x, y);
		g2.setColor(gc.color_markings);
		g2.setStroke(gc.stroke_threehalf);
		dial.setFrame(0, 0, diameter, diameter);
		dial.setAngleStart(0);
		dial.setAngleExtent(-208);
		g2.draw(dial);

		if((dial == eng1n1 || dial == eng2n1)  && ff > 0f) {
			//N1 diff
			n1_diff.setFrame(-15, -15, diameter + 30, diameter + 30);
			n1_diff.setAngleStart(rotation * -1f);
			n1_diff.setAngleExtent((rotation) - (req_rotation));
			g2.draw(n1_diff);
		}

		
		// needle
		g2.rotate(Math.toRadians(rotation), diameter/2, diameter/2);
		g2.setStroke(gc.stroke_six);
		int needle_offset = 0;
		if(ff > 0f) {
			needle_offset = 15;
		}
		// if(dial == eng1egt && this.xpd.eng1_n2() >= 3.9f) {
		if(dial == eng1egt && this.xpd.eicas_ff1()) {
			g2.drawLine(diameter/2, diameter/2, diameter - 3, diameter/2);
		}
		// if(dial == eng2egt && this.xpd.eng2_n2() >= 3.9f) {
		if(dial == eng2egt && this.xpd.eicas_ff2()) {
			g2.drawLine(diameter/2, diameter/2, diameter - 3, diameter/2);
		}
		if (dial == eng1n1 || dial == eng2n1) {
			g2.drawLine(diameter/2, diameter/2, diameter + needle_offset, diameter/2);
		}
		g2.setTransform(original_trans);
		
		g2.setStroke(gc.stroke_four);
		if(dial == eng1n1 || dial == eng2n1) {
			//commanded position
			if(ff > 0f) {
				g2.setStroke(gc.stroke_six);
				g2.scale(gc.scalex, gc.scaley);
				g2.translate(x, y);
				g2.rotate(Math.toRadians(req_rotation), diameter/2, diameter/2);
				g2.drawLine(diameter + 5, diameter/2, diameter + 15, diameter/2);
				g2.setTransform(original_trans);
			}
			
			// hardware throttle lever position
			if(this.preferences.get_preference(ZHSIPreferences.PREF_HARDWARE_LEVERS).equals("true") && ff > 0f) {
				g2.scale(gc.scalex, gc.scaley);
				g2.translate(x, y);
				g2.rotate(Math.toRadians(tq_rotate), diameter/2, diameter/2);
				int[]thr_lvl_triangle_x = {diameter + 20, diameter + 30, diameter + 30};
				int[]thr_lvl_triangle_y = {diameter/2, diameter/2 -8, diameter/2 + 8};
				if(rotation - tq_rotate >= 2f || rotation - tq_rotate <= -2f) {
					g2.setColor(gc.color_amber);
				}else {
					g2.setColor(gc.color_lime);
				}	
				g2.fillPolygon(thr_lvl_triangle_x, thr_lvl_triangle_y, 3);
				g2.setTransform(original_trans);
				g2.setColor(gc.color_markings);
			}
			
			// tick marks
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(x, y);
			for(int i = 0; i < 11; i++) {
				g2.drawLine(diameter - 13, diameter/2, diameter, diameter/2);
				g2.rotate(Math.toRadians(20),  diameter/2, diameter/2);
			}
			g2.setTransform(original_trans);
			
			// N1 Limits
			if(this.xpd.n1_mode() != "---") {
				g2.scale(gc.scalex, gc.scaley);
				g2.translate(x, y);
				g2.setColor(gc.color_lime);
				g2.rotate(Math.toRadians(n1_bug_rotation), diameter/2, diameter/2);
				g2.drawLine(diameter + 3, diameter/2, diameter + 13, diameter/2);
				g2.drawLine(diameter + 13, diameter/2, diameter + 20, diameter/2 + 8);
				g2.drawLine(diameter + 13, diameter/2, diameter + 20, diameter/2 - 8);
				g2.setTransform(original_trans);
			}
						
		}
		
		//egt amber bands
		if (dial == eng1egt || dial == eng2egt) {
			
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(x, y);
			g2.setColor(gc.color_amber);
			g2.setStroke(gc.stroke_six);
			eng1egt_amber.setFrame(0, 0, diameter, diameter);
			eng1egt_amber.setAngleStart(-205);
			eng1egt_amber.setAngleExtent(-5);
			g2.draw(eng1egt_amber);
			g2.rotate(Math.toRadians(205f), diameter/2, diameter/2);
			g2.drawLine(diameter, diameter/2, diameter + 10, diameter/2);
			g2.setTransform(original_trans);
		}
		
		//red lines
		g2.scale(gc.scalex, gc.scaley);
		g2.translate(x, y);
		g2.setColor(Color.RED);
		g2.setStroke(gc.stroke_six);
		g2.rotate(Math.toRadians(210f), diameter/2, diameter/2);
		g2.drawLine(diameter - 2, diameter/2, diameter + 20, diameter/2);
		g2.setTransform(original_trans);
		
		//egt red line
		if(egt_redline) {
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(x, y);
			g2.setColor(Color.RED);
			g2.setStroke(gc.stroke_six);
			g2.rotate(Math.toRadians(160f), diameter/2, diameter/2);
			g2.drawLine(diameter + 2 , diameter/2, diameter + 15, diameter/2);
			g2.setTransform(original_trans);
		}
	}
	
	private float getVib1(float n1) {
		
		//0 to 10 = 0 to 1.81
		//10 to 37 = 1.81 to 1.21
		//37 to 105 = 1.21 to 1.47
				
		float vib = 0f;
		
        if(n1 > 37){
            
            float old_min = 37f;
            float old_max = 105f;
            float new_min = 1.21f;
            float new_max = 1.47f;
            vib = ( ( n1 - old_min ) / (old_max - old_min) ) * (new_max - new_min) + new_min;
            
        } else if (n1 > 10) {
            
            float old_min = 10f;
            float old_max = 37f;
            float new_min = 1.81f;
            float new_max = 1.21f;
            vib = ( ( n1 - old_min ) / (old_max - old_min) ) * (new_max - new_min) + new_min;
            
        } else {
           
            float old_min = 0f;
            float old_max = 10f;
            float new_min = 0f;
            float new_max = 1.81f;
            vib = ( ( n1 - old_min ) / (old_max - old_min) ) * (new_max - new_min) + new_min; 
        }
		return vib;
	}
	
	private float getVib2(float n1) {

		//0 to 10 = 0 to 1.98
		//10 to 37 = 1.98 to 1.03
		//37 to 105 = 1.03 to 1.64
				
		float vib = 0f;
		
        if(n1 > 37){
            
            float old_min = 37f;
            float old_max = 105f;
            float new_min = 1.03f;
            float new_max = 1.64f;
            vib = ( ( n1 - old_min ) / (old_max - old_min) ) * (new_max - new_min) + new_min;
            
        } else if (n1 > 10) {
            
            float old_min = 10f;
            float old_max = 37f;
            float new_min = 1.98f;
            float new_max = 1.03f;
            vib = ( ( n1 - old_min ) / (old_max - old_min) ) * (new_max - new_min) + new_min;
            
        } else {
           
            float old_min = 0f;
            float old_max = 10f;
            float new_min = 0f;
            float new_max = 1.98f;
            vib = ( ( n1 - old_min ) / (old_max - old_min) ) * (new_max - new_min) + new_min; 
        }
		return vib;
	}

}