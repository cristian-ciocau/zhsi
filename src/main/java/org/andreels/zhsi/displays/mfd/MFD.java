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
 
package org.andreels.zhsi.displays.mfd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.displays.DUBaseClass;



public class MFD extends DUBaseClass {

	private static final long serialVersionUID = 1L;
	
	private Arc2D.Float eng1n2 = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng1n2_fill = new Arc2D.Float(Arc2D.PIE);
	private Arc2D.Float eng2n2 = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng2n2_fill = new Arc2D.Float(Arc2D.PIE);
	private RoundRectangle2D hydraulicsRect = new RoundRectangle2D.Float();
	private RoundRectangle2D wheel1 = new RoundRectangle2D.Float();
	private RoundRectangle2D wheel2 = new RoundRectangle2D.Float();
	private RoundRectangle2D wheel3 = new RoundRectangle2D.Float();
	private RoundRectangle2D wheel4 = new RoundRectangle2D.Float();
	
	private boolean brake_temp_left_in_amber = false;
	private boolean brake_temp_left_out_amber = false; 
	private boolean brake_temp_right_in_amber = false;
	private boolean brake_temp_right_out_amber = false; 	
	

	public MFD(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);
	}

	public void drawInstrument(Graphics2D g2) {
		
		if (this.xpd.power_on() && this.xpd.dc_standby_on()) {
			
			if (this.xpd.lowerdu_page() == 1 && this.xpd.lowerdu_page2() == 0) {
				displayENG();
			}
			
			if (this.xpd.lowerdu_page2() == 1) {		
				displaySYS();
			}		
		}
	}
	
	private void displaySYS() {
		
		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(gc.color_navaid);
		g2.translate(536, 50);
		g2.setStroke(new BasicStroke(4f));
		hydraulicsRect.setRoundRect(-350, 0, 700, 220, 55f, 55f);
		g2.draw(hydraulicsRect);
		g2.setColor(Color.BLACK);
		g2.fillRect(-100, -25, 200, 50);
		g2.setColor(gc.color_navaid);
		g2.setFont(rs.glassFont.deriveFont(30f));
		g2.drawString("HYDRAULIC", -85, 12);
		g2.drawString("QTY %", -280, 100);
		g2.drawString("PRESS", -280, 170);
		g2.drawString("A", -85, 60);
		g2.drawString("B", 140, 60);
		g2.setTransform(original_trans);
		g2.setColor(gc.color_markings);
	
		//A QTY
		gc.drawText("" + (int)this.xpd.hyd_a_qty(), 463, 903, 34, 0, 0, 0, "center", g2);
		//B QTY
		gc.drawText("" + (int)this.xpd.hyd_b_qty(), 686, 903, 34, 0, 0, 0, "center", g2);
		//A PRESS
		gc.drawText("" + (int)this.xpd.hyd_a_pressure(), 463, 833, 34, 0, 0, 0, "center", g2);
		//B PRESS
		gc.drawText("" + (int)this.xpd.hyd_b_pressure(), 686, 833, 34, 0, 0, 0, "center", g2);
		
		if (this.xpd.brake_temp() == 1) {
			
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(536, 30);
			g2.setColor(gc.color_navaid);
			g2.setStroke(new BasicStroke(4f));
			g2.drawLine(-250, 330, 250, 330);
			g2.setColor(Color.BLACK);
			g2.fillRect(-100, 300, 200, 50);
			g2.setColor(gc.color_navaid);
			g2.setFont(rs.glassFont.deriveFont(30f));
			g2.drawString("L", -295, 340);
			g2.drawString("R", 280, 340);
			g2.drawString("BRAKE TEMP", -90, 340);
			g2.setColor(gc.color_markings);
			wheel1.setRoundRect(-400, 370, 100, 140, 100, 100);
			wheel2.setRoundRect(-280, 370, 100, 140, 100, 100);
			wheel3.setRoundRect(300, 370, 100, 140, 100, 100);
			wheel4.setRoundRect(180, 370, 100, 140, 100, 100);
			g2.draw(wheel1);
			g2.draw(wheel2);
			g2.draw(wheel3);
			g2.draw(wheel4);
			
			if (this.xpd.brake_temp_left_out() < 2.41) {
				g2.drawRect(-415, 415, 15, 45);
			} else if ((this.xpd.brake_temp_left_out() <= 4.91 && !brake_temp_left_out_amber) || this.xpd.brake_temp_left_out() < 3.41) {
				brake_temp_left_out_amber = false;
				g2.fillRect(-415, 415, 15, 45);
			} else {
				brake_temp_left_out_amber = true;
				g2.setColor(gc.color_amber);
				g2.fillRect(-415, 415, 15, 45);
				g2.setColor(gc.color_markings);
			}
			
			if (this.xpd.brake_temp_left_in() < 2.41) {
				g2.drawRect(-180, 415, 15, 45);
			} else if ((this.xpd.brake_temp_left_in() <= 4.91 && !brake_temp_left_in_amber) || this.xpd.brake_temp_left_in() < 3.41) {
				brake_temp_left_in_amber = false;
				g2.fillRect(-180, 415, 15, 45);
			} else {
				brake_temp_left_in_amber = true;
				g2.setColor(gc.color_amber);
				g2.fillRect(-180, 415, 15, 45);
				g2.setColor(gc.color_markings);
			}
			
			if (this.xpd.brake_temp_right_in() < 2.41) {
				g2.drawRect(165, 415, 15, 45);
			} else if ((this.xpd.brake_temp_right_in() <= 4.91 && !brake_temp_right_in_amber) || this.xpd.brake_temp_right_in() < 3.41) {
				brake_temp_right_in_amber = false;
				g2.fillRect(165, 415, 15, 45);
			} else {
				brake_temp_right_in_amber = true;
				g2.setColor(gc.color_amber);
				g2.fillRect(165, 415, 15, 45);
				g2.setColor(gc.color_markings);
			}
			
			if (this.xpd.brake_temp_right_out() < 2.41) {
				g2.drawRect(400, 415, 15, 45);
			} else if ((this.xpd.brake_temp_right_out() <= 4.91 && !brake_temp_right_out_amber) || this.xpd.brake_temp_right_out() < 3.41) {
				brake_temp_right_out_amber = false;
				g2.fillRect(400, 415, 15, 45);
			} else {
				brake_temp_right_out_amber = true;
				g2.setColor(gc.color_amber);
				g2.fillRect(400, 415, 15, 45);
				g2.setColor(gc.color_markings);
			}
			
			g2.setTransform(original_trans);
										
			if (this.xpd.brake_temp_left_out() <= 4.91) {
				gc.drawText("" + this.xpd.brake_temp_left_out(), 60, 575, 28, 0, 0, 0, "left", g2);
			} else {
				g2.setColor(gc.color_amber);
				gc.drawText("" + this.xpd.brake_temp_left_out(), 60, 575, 28, 0, 0, 0, "left", g2);
				g2.setColor(gc.color_markings);
			}
			
			if (this.xpd.brake_temp_left_in() <= 4.91) {
				gc.drawText("" + this.xpd.brake_temp_left_in(), 385, 575, 28, 0, 0, 0, "left", g2);
			} else {
				g2.setColor(gc.color_amber);
				gc.drawText("" + this.xpd.brake_temp_left_in(), 385, 575, 28, 0, 0, 0, "left", g2);
				g2.setColor(gc.color_markings);
			}
			
			if (this.xpd.brake_temp_right_in() <= 4.91) {			
				gc.drawText("" + this.xpd.brake_temp_right_in(), 640, 575, 28, 0, 0, 0, "left", g2);
			} else {
				g2.setColor(gc.color_amber);
				gc.drawText("" + this.xpd.brake_temp_right_in(), 640, 575, 28, 0, 0, 0, "left", g2);
				g2.setColor(gc.color_markings);
			}
			
			if (this.xpd.brake_temp_right_out() <= 4.91) {
				gc.drawText("" + this.xpd.brake_temp_right_out(), 965, 575, 28, 0, 0, 0, "left", g2);
			} else {
				g2.setColor(gc.color_amber);
				gc.drawText("" + this.xpd.brake_temp_right_out(), 965, 575, 28, 0, 0, 0, "left", g2);
				g2.setColor(gc.color_markings);
			}
			
			g2.setTransform(original_trans);
			
		}
		
		if (this.xpd.flight_control() == 1) {
						
			g2.scale(gc.scalex, gc.scaley);
			g2.translate(536, 80);
			g2.setColor(gc.color_markings);
			g2.setStroke(new BasicStroke(4f));
			
			//ELEV
			g2.drawLine(0, 530, 0, 760);
			g2.drawLine(-12, 530, 12, 530);//top line
			g2.drawLine(-12, 645, 12, 645);//middle line
			g2.drawLine(-12, 760, 12, 760);//bottom line
			
			//RUDDER
			g2.drawLine(-180, 870, 180, 870);
			g2.drawLine(-180, 858, -180, 882); //left line
			g2.drawLine(0, 858, 0, 882); //middle line
			g2.drawLine(180, 858, 180, 882); // right line
						
			//LEFT AIL
			g2.drawLine(-420, 530, -420, 790);
			g2.drawLine(-432, 530, -408, 530);
			g2.drawLine(-432, 660, -408, 660);
			g2.drawLine(-432, 790, -408, 790);
			
			//LEFT SPLR
			g2.drawLine(-280, 530, -280, 670);
			g2.drawLine(-292, 530, -268, 530);
			g2.drawLine(-292, 670, -268, 670);
			
			//RIGHT AIL
			g2.drawLine(420, 530, 420, 790);
			g2.drawLine(432, 530, 408, 530);
			g2.drawLine(432, 660, 408, 660);
			g2.drawLine(432, 790, 408, 790);
			
			//RIGHT SPLR
			g2.drawLine(280, 530, 280, 670);
			g2.drawLine(292, 530, 268, 530);
			g2.drawLine(292, 670, 268, 670);
			
			g2.setColor(gc.color_navaid);
			g2.setFont(rs.glassFont.deriveFont(26f));
			
			//ELEV text
			g2.drawString("ELEV", -33, 810);
			//RUDDER text
			g2.drawString("RUDDER", -50, 940);
			//LEFT AIL text
			g2.drawString("AIL", -443, 840);
			//LEFT SPLR text
			g2.drawString("FLT", -305, 720);
			g2.drawString("SPLR", -315, 750);
			//RIGHT AIL text
			g2.drawString("AIL", 395, 840);
			//RIGHT SPLR text
			g2.drawString("FLT", 255, 720);
			g2.drawString("SPLR", 245, 750);
			
			g2.setColor(gc.color_markings);
			
			//Triangles
			
			//RUDDER
			
			int rudder_x_delta = (int)((180 * this.xpd.rudder_deflection()) / 28f); //full range 180px
			
			int[] rudder_t_x = {0 + rudder_x_delta,-12 + rudder_x_delta, 12 + rudder_x_delta };
			int[] rudder_t_y = {870,900,900};
			g2.fillPolygon(rudder_t_x, rudder_t_y, 3);
			
			//LEFT AIL
			
			int left_ail_y_delta = (int)((130 * this.xpd.left_ail_deflection()) / 28f); //full range 130px
			
			int[] left_ail_x = {-420,-452,-452};
			int[] left_ail_y = {660 - left_ail_y_delta,648 - left_ail_y_delta,672 - left_ail_y_delta};
			g2.fillPolygon(left_ail_x, left_ail_y, 3);

			//LEFT SPLR
			
			int left_splr_y_delta = (int)((140 * this.xpd.left_splr_deflection()) / 40f); //full range 140px
			
			int[] left_splr_1_x = {-280,-310,-310};
			int[] left_splr_1_y = {670 - left_splr_y_delta, 658 - left_splr_y_delta, 682 - left_splr_y_delta};
			g2.fillPolygon(left_splr_1_x, left_splr_1_y, 3);
			
			//ELEV
			
			int elev_1_y_delta = (int)((115 * this.xpd.left_elevator_deflection()) / 20f); //full range 1115px
			
			int[] elev_1_x = {0,-30,-30};
			int[] elev_1_y = {645 - elev_1_y_delta, 633 - elev_1_y_delta, 657 - elev_1_y_delta};
			g2.fillPolygon(elev_1_x, elev_1_y, 3);
			
			int elev_2_y_delta = (int)((115 * this.xpd.right_elevator_deflection()) / 20f); //full range 115px
			
			int[] elev_2_x = {0,30,30};
			int[] elev_2_y = {645 - elev_2_y_delta ,633 - elev_2_y_delta ,657 - elev_2_y_delta};
			g2.fillPolygon(elev_2_x, elev_2_y, 3);
			
			//RIGHT SPLR
				
			int right_splr_y_delta = (int)((140 * this.xpd.right_splr_deflection()) / 40f); //full range 140px
					
			int[] right_splr_1_x = {280,310,310};
			int[] right_splr_1_y = {670 - right_splr_y_delta, 658 - right_splr_y_delta, 682 - right_splr_y_delta};
			g2.fillPolygon(right_splr_1_x, right_splr_1_y, 3);
			
			//RIGHT AIL
			
			int right_ail_y_delta = (int)((130 * this.xpd.right_ail_deflection()) / 28f); //full range 130px
			
			int[] right_ail_x = {420,452,452};
			int[] right_ail_y = {660 - right_ail_y_delta, 648 - right_ail_y_delta, 672 - right_ail_y_delta};
			g2.fillPolygon(right_ail_x, right_ail_y, 3);
					
			g2.setTransform(original_trans);
		}		
	}

	private void displayENG() {
		
		displayN2(180, 30, eng1n2, eng1n2_fill, this.xpd.eng1_n2(), 210);
		
		g2.setColor(gc.color_markings);
		gc.drawText(gc.mfd_n2.format(this.xpd.eng1_n2()), 430, 939, 44, 0, 0, 0, "right", g2);
		
		displayN2(480, 30, eng2n2, eng2n2_fill, this.xpd.eng2_n2(), 210);
		
		g2.setColor(gc.color_markings);
		gc.drawText(gc.mfd_n2.format(this.xpd.eng2_n2()), 730, 939, 44, 0, 0, 0, "right", g2);
		
		
		int text_x = 430;
		int text_y = 250;
		g2.setStroke(gc.stroke_five);
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("N", text_x, text_y);
		g2.drawString("2", text_x + 20, text_y + 7);		
		g2.setTransform(original_trans);
		
		//fuel flow

		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		if (this.xpd.fuel_flow_used_show()) {
			g2.drawString("FUEL USED", text_x - 60, text_y + 100);
		} else {
			g2.drawString("FF", text_x, text_y + 100);
		}
				
		g2.setColor(gc.color_markings);
		g2.drawRect(text_x - 190, text_y + 68, 100, 45);
		g2.drawRect(text_x + 125, text_y + 68, 100, 45);
		g2.setFont(rs.glassFont.deriveFont(38f));
		//if(this.xpd.eng1_n2() > 0f) {
		if(this.xpd.eicas_ff1() || this.xpd.fuel_flow_used_show()) {
			g2.drawString(gc.mfd_ff.format(this.xpd.fuel_flow_dspl_1()), text_x - 180, text_y + 107);	
		}
		//if(this.xpd.eng2_n2() > 0f) {
		if(this.xpd.eicas_ff2() || this.xpd.fuel_flow_used_show()) {
			g2.drawString(gc.mfd_ff.format(this.xpd.fuel_flow_dspl_2()), text_x + 130, text_y + 107);
		}
		g2.setTransform(original_trans);
		
		//oil press
		
		int oil_press1_ydelta = (int) (110 * (this.xpd.eng_oil_press_1() / 100));
		int oil_press2_ydelta = (int) (110 * (this.xpd.eng_oil_press_2() / 100));
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("OIL", text_x, text_y + 230);
		g2.drawString("PRESS", text_x - 20, text_y + 260);	
		g2.setColor(gc.color_markings);
		g2.drawRect(text_x - 160, text_y + 215, 70, 40);
		g2.drawRect(text_x + 125, text_y + 215, 70, 40);	
		g2.drawLine(text_x - 75, text_y + 180 , text_x - 75, text_y + 290);
		g2.drawLine(text_x + 110, text_y + 180 , text_x + 110, text_y + 290);
		int[]oilpresst1_x = {text_x - 75,text_x - 50,text_x - 50 };
		int[]oilpresst1_y = {text_y + 290 - oil_press1_ydelta, text_y + 280 - oil_press1_ydelta, text_y + 300 - oil_press1_ydelta};	
		int[]oilpresst2_x = {text_x + 110, text_x + 85,text_x + 85};
		int[]oilpresst2_y = {text_y + 290 -oil_press2_ydelta, text_y + 280 -oil_press2_ydelta, text_y + 300 -oil_press2_ydelta};
		//if(this.xpd.eng_oil_press_1() > 0f) {
		if(this.xpd.eicas_oil_press1()) {
			g2.fillPolygon(oilpresst1_x, oilpresst1_y, 3);
		}
		//if(this.xpd.eng_oil_press_2() > 0f) {
		if(this.xpd.eicas_oil_press2()) {
			g2.fillPolygon(oilpresst2_x, oilpresst2_y, 3);
		}
		
				
		g2.setTransform(original_trans);
		//if(this.xpd.eng_oil_press_1() <= 13) {
		//	g2.setColor(gc.color_red);
		//}else if (this.xpd.eng_oil_press_1() > 13 && this.xpd.eng_oil_press_1() <= 22) {
		//	g2.setColor(gc.color_amber);
		//}else {
			g2.setColor(gc.color_markings);
		//}
		// if(this.xpd.eng_oil_press_1() > 0f) {
		if(this.xpd.eicas_oil_press1()) {
			gc.drawText(gc.mfd_oil_press.format(this.xpd.eng_oil_press_1()), 335, 555, 30, 0, 0, 0, "right", g2);
		}
		
		
		//if(this.xpd.eng_oil_press_2() <= 13) {
		//	g2.setColor(gc.color_red);
		//}else if (this.xpd.eng_oil_press_2() > 13 && this.xpd.eng_oil_press_2() <= 22) {
		//	g2.setColor(gc.color_amber);
		//}else {
		//	g2.setColor(gc.color_markings);
		//}
		// if(this.xpd.eng_oil_press_2() > 0f) {
		if(this.xpd.eicas_oil_press2()) {
			gc.drawText(gc.mfd_oil_press.format(this.xpd.eng_oil_press_2()), 620, 555, 30, 0, 0, 0, "right", g2);
		}
		
		
		//oil temp
		int oil_temp1_ydelta = (int) (110 * (this.xpd.oil_temp_c_1() / 155));
		int oil_temp2_ydelta = (int) (110 * (this.xpd.oil_temp_c_2() / 155));
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("OIL", text_x, text_y + 400);
		g2.drawString("TEMP", text_x - 15, text_y + 430);
		g2.setColor(gc.color_markings);
		g2.drawRect(text_x - 160, text_y + 385, 70, 40);
		g2.drawRect(text_x + 125, text_y + 385, 70, 40);
		g2.drawLine(text_x - 75, text_y + 350 , text_x - 75, text_y + 460);
		g2.drawLine(text_x + 110, text_y + 350 , text_x + 110, text_y + 460);	
		int[]oiltempt1_x = {text_x - 75,text_x - 50,text_x - 50 };
		int[]oiltempt1_y = {text_y + 460 - oil_temp1_ydelta, text_y + 450 - oil_temp1_ydelta, text_y + 470 - oil_temp1_ydelta};
		int[]oiltempt2_x = {text_x + 110, text_x + 85,text_x + 85};
		int[]oiltempt2_y = {text_y + 460 - oil_temp2_ydelta , text_y + 450 - oil_temp2_ydelta, text_y + 470 - oil_temp2_ydelta};
		if (this.xpd.eicas_oil_temp1()) {
			g2.fillPolygon(oiltempt1_x, oiltempt1_y, 3);
		}
		if (this.xpd.eicas_oil_temp2()) {
			g2.fillPolygon(oiltempt2_x, oiltempt2_y, 3);
		}
		g2.setTransform(original_trans);
		if(this.xpd.oil_temp_c_1() >= 165) {
			g2.setColor(gc.color_red);
		}else if(this.xpd.oil_temp_c_1() >= 153 && this.xpd.oil_temp_c_1() < 165) {
			g2.setColor(gc.color_amber);
		}else {
			g2.setColor(gc.color_markings);
		}
		
		if (this.xpd.eicas_oil_temp1()) {
			gc.drawText(gc.mfd_oil_temp.format(this.xpd.oil_temp_c_1()), 335, 385, 30, 0, 0, 0, "right", g2);
		}
		
		if(this.xpd.oil_temp_c_2() >= 165) {
			g2.setColor(gc.color_red);
		}else if(this.xpd.oil_temp_c_2() >= 153 && this.xpd.oil_temp_c_2() < 165) {
			g2.setColor(gc.color_amber);
		}else {
			g2.setColor(gc.color_markings);
		}
		
		if (this.xpd.eicas_oil_temp2()) {
			gc.drawText(gc.mfd_oil_temp.format(this.xpd.oil_temp_c_2()), 620, 385, 30, 0, 0, 0, "right", g2);
		}
		
		//oil qty
		
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("OIL QTY", text_x - 35, text_y + 560);	
		g2.setColor(gc.color_markings);
		g2.drawRect(text_x - 140, text_y + 525, 50, 40);
		g2.drawRect(text_x + 125, text_y + 525, 50, 40);
		g2.setTransform(original_trans);
		gc.drawText(gc.mfd_oil_qty.format(this.xpd.oil_qty_1()), 335, 245, 30, 0, 0, 0, "right", g2);
		gc.drawText(gc.mfd_oil_qty.format(this.xpd.oil_qty_2()), 600, 245, 30, 0, 0, 0, "right", g2);

		
		//vibration
		int vib1_ydelta = (int) (110 * (getVib1(this.xpd.n1_percent1()) / 5.47));
		int vib2_ydelta = (int) (110 * (getVib2(this.xpd.n1_percent2()) / 5.47));
		g2.scale(gc.scalex, gc.scaley);
		g2.setFont(rs.glassFont.deriveFont(26f));
		g2.setColor(gc.color_navaid);
		g2.drawString("VIB", text_x, text_y + 700);
		g2.setColor(gc.color_markings);
		g2.drawRect(text_x - 150, text_y + 665, 60, 40);
		g2.drawRect(text_x + 125, text_y + 665, 60, 40);
		g2.setFont(rs.glassFont.deriveFont(30f));
		
		g2.drawString(gc.mfd_vib.format(getVib1(this.xpd.n1_percent1())), text_x - 145, text_y + 698);
		g2.drawString(gc.mfd_vib.format(getVib2(this.xpd.n1_percent2())), text_x + 130, text_y + 698);
		
		g2.drawLine(text_x - 75, text_y + 630 , text_x - 75, text_y + 740);
		g2.drawLine(text_x - 75, text_y + 650 , text_x - 65, text_y + 650);
		g2.drawLine(text_x + 110, text_y + 630 , text_x + 110, text_y + 740);
		g2.drawLine(text_x + 100, text_y + 650 , text_x + 110, text_y + 650);

		int[]vibt1_x = {text_x - 75,text_x - 50, text_x - 50 };
		int[]vibt1_y = {text_y + 740 - vib1_ydelta, text_y + 730 - vib1_ydelta, text_y + 750 - vib1_ydelta};
		
		int[]vibt2_x = {text_x + 110, text_x + 85,text_x + 85};
		int[]vibt2_y = {text_y + 740 - vib2_ydelta, text_y + 730 - vib2_ydelta, text_y + 750 - vib2_ydelta};
		
		g2.fillPolygon(vibt1_x, vibt1_y, 3);
		g2.fillPolygon(vibt2_x, vibt2_y, 3);
		
		g2.setTransform(original_trans);
		
		//red lines
		g2.setStroke(gc.stroke_six);
		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(Color.RED);
		//oil press
		g2.drawLine(text_x - 80, text_y + 290, text_x - 65, text_y + 290);
		g2.drawLine(text_x + 100, text_y + 290, text_x + 115, text_y + 290);
		//oil temp
		g2.drawLine(text_x - 80, text_y + 350, text_x - 65, text_y + 350);
		g2.drawLine(text_x + 100, text_y + 350, text_x + 115, text_y + 350);
		g2.setTransform(original_trans);
		
		
		//amber lines

		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(gc.color_amber);
		//oil press
		//g2.drawLine(text_x - 75, text_y + 275, text_x - 65, text_y + 275);
		//g2.drawLine(text_x + 100, text_y + 275, text_x + 110, text_y + 275);
		//oil temp
		g2.drawLine(text_x - 75, text_y + 360, text_x - 65, text_y + 360);
		g2.drawLine(text_x + 100, text_y + 360, text_x + 110, text_y + 360);
		g2.setTransform(original_trans);
		g2.setStroke(gc.stroke_four);
		
	}

	private void displayN2(int x, int y, Arc2D.Float dial, Arc2D.Float dialbg, float value, int diameter) {
		
		float rotation = (value / 100f) * 200f;
	
		//background
		g2.scale(gc.scalex, gc.scaley);
		g2.translate(x, y);
		g2.setColor(gc.color_instrument_gray);
		dialbg.setFrame(0, 0, diameter, diameter);
		dialbg.setAngleStart(0);
		dialbg.setAngleExtent(rotation * -1);
		g2.fill(dialbg);
		g2.setTransform(original_trans);
		
		//outline
		g2.scale(gc.scalex, gc.scaley);
		g2.translate(x, y);
		g2.setColor(gc.color_markings);
		g2.setStroke(gc.stroke_threehalf);
		eng1n2.setFrame(0, 0, diameter, diameter);
		eng1n2.setAngleStart(0);
		eng1n2.setAngleExtent(-210);
		g2.draw(eng1n2);
		
		// needle
		g2.rotate(Math.toRadians(rotation), diameter/2, diameter/2);
		g2.setStroke(gc.stroke_six);
		g2.drawLine(diameter/2, diameter/2, diameter, diameter/2);
		g2.setTransform(original_trans);
		
		//red lines
		g2.scale(gc.scalex, gc.scaley);
		g2.translate(x, y);
		g2.setColor(Color.RED);
		g2.setStroke(gc.stroke_six);
		g2.rotate(Math.toRadians(212f), diameter/2, diameter/2);
		g2.drawLine(diameter - 2, diameter/2, diameter + 20, diameter/2);
		g2.setTransform(original_trans);
		
		//rect
		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(gc.color_markings);
		g2.setStroke(gc.stroke_four);
		g2.setFont(rs.glassFont.deriveFont(42f));
		g2.drawRect(x + diameter/2, y + 40, 150, 50);
		g2.setTransform(original_trans);
		
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