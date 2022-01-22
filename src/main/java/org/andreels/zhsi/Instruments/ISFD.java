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


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import org.andreels.zhsi.ModelFactory;



public class ISFD extends InstrumentBaseClass {
	
	private Rectangle2D horizon_clip = new Rectangle2D.Float();
	private Rectangle2D horizon_ground = new Rectangle2D.Float();
	private Rectangle2D horizon_sky = new Rectangle2D.Float();
	private Rectangle2D altbox_clip = new Rectangle2D.Float();
	private Rectangle2D init_box = new Rectangle2D.Float();
	private RoundRectangle2D pitchMarkingsClip = new RoundRectangle2D.Float();
	private Line2D horizon_line = new Line2D.Float();
	private Color horizon_ground_color = new Color(0x5C3603); // was 0x995720  0x6E4424
	private Color horizon_sky_color = new Color(0x0172DE); //  was 0x0099CD 0x00BFFF was 0x3775B1

	private float ias;
	private float heading;
	private float alt;
	private float roll;
	private float pitch;
	
	private Font topFont;
	private Font speedFont;
	private Font altFont;
	private Font altTapeFont;
	private Font topFontBaro;
	private Font pitchFont;
	
	private float adiY = 234.5f;
	private int bottom_tr_x[] = {0, -10, 10};
	private int bottom_tr_y[] = {15, 0, 0};
	private int top_tr_x[] = {0, -10, 10};
	private int top_tr_y[] = {15, 0,0};
	
	private int v_pointer_x[] = {96, 106, 116, 106};
	private int h_pointer_y[] = {160, 150, 160, 170};
	
	private int hdef_xdelta = 0;
	private int vdef_ydelta = 0;
	

	public ISFD(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);
		this.pilot = "cpt";
		this.topFont = rs.isfdFont.deriveFont(40f);
		this.speedFont = rs.isfdFont.deriveFont(42f);
		this.altFont = rs.isfdFont.deriveFont(36f);
		this.altTapeFont = rs.isfdFont.deriveFont(30f);
		this.topFontBaro = rs.isfdFont.deriveFont(32f);
		this.pitchFont = rs.isfdFont.deriveFont(22f);

	}

	private static final long serialVersionUID = 1L;

	@Override
	public void drawInstrument(Graphics2D g2) {
		
		if(this.xpd.power_on()) {
			
			if (this.xpd.isfd_init_time() > 0.0) {
				
				g2.scale(this.scalex, this.scaley);				
				g2.setColor(rs.color_amber);
				g2.setStroke(rs.stroke3);
				g2.setFont(altTapeFont);
				g2.drawString("WAIT ATT", 180, 200);
				g2.drawString("INIT " + String.format("%02.0f", this.xpd.isfd_init_time()) + "s", 190, 320);
				g2.drawRect(185, 290, 120, 35);
				g2.setTransform(original_trans);
				
			} else {
					
				ias = this.xpd.airspeed("cpt");

				if (ias < 30) {
					ias = 30f;
				}

				heading = this.xpd.heading(pilot);
				alt = this.xpd.isfd_altitude();
				roll = this.xpd.roll();
				pitch = this.xpd.pitch() * 9.1f;

				//draw horizon
				g2.scale(this.scalex, this.scaley);
				horizon_clip.setFrame(77, 60, 315, 370);
				g2.setClip(horizon_clip);
				g2.translate(adiY, 250f + pitch);
				g2.rotate(Math.toRadians(-roll), 0, 0);
				horizon_sky.setFrame(-600, -600, 1200, 600);
				g2.setColor(horizon_sky_color);
				g2.fill(horizon_sky);
				horizon_ground.setFrame(-600, 0, 1200, 600);
				g2.setColor(horizon_ground_color);
				g2.fill(horizon_ground);
				horizon_line.setLine(-600, 0, 1200, 0);
				g2.setStroke(stroke3);
				g2.setColor(this.color_instrument_white);
				g2.draw(horizon_line);
				
				g2.setTransform(original_trans);
				g2.setClip(original_clipshape);
				
				//pitch marks
				g2.scale(this.scalex, this.scaley);
				pitchMarkingsClip.setRoundRect(88f, 80f, 295f, 350f, 280f, 280f);
				g2.clip(pitchMarkingsClip);
				//g2.draw(pitchMarkingsClip);
				g2.translate(adiY, 250f + pitch);
				g2.rotate(Math.toRadians(-roll), 0, 0 - pitch);
				g2.setFont(pitchFont);
				g2.setStroke(rs.stroke2);
				//

				g2.drawString("40", -102, -361);
				g2.drawLine(-75, -368, 75, -368); //40
				g2.drawLine(-20, -345, 20, -345);
				g2.drawLine(-35, -322, 35, -322);
				g2.drawLine(-20, -299, 20, -299);
				//
				g2.drawString("30", -102, -269);
				g2.drawLine(-75, -276, 75, -276); //30
				g2.drawLine(-20, -253, 20, -253);
				g2.drawLine(-35, -230, 35, -230);
				g2.drawLine(-20, -207, 20, -207);
				//
				g2.drawString("20", -102, -177);
				g2.drawLine(-75, -184, 75, -184); //20
				g2.drawLine(-20, -161, 20, -161);
				g2.drawLine(-35, -138, 35, -138);
				g2.drawLine(-20, -115, 20, -115);
				//
				g2.drawString("10", -100, -85);
				g2.drawLine(-75, -92, 75, -92); //10
				g2.drawLine(-20, -69, 20, -69);
				g2.drawLine(-35, -46, 35, -46);
				g2.drawLine(-20, -23, 20, -23);
				//center
				g2.drawLine(-20, 23, 20, 23);
				g2.drawLine(-35, 46, 35, 46);
				g2.drawLine(-20, 69, 20, 69);
				g2.drawLine(-75, 92, 75, 92); //10
				g2.drawString("10", -100, 98); 
				//
				g2.drawLine(-20, 115, 20, 115);
				g2.drawLine(-35, 138, 35, 138);
				g2.drawLine(-20, 161, 20, 161);
				g2.drawLine(-75, 184, 75, 184); //20
				g2.drawString("20", -102, 191);
				//
				g2.drawLine(-20, 207, 20, 207);
				g2.drawLine(-35, 230, 35, 230);
				g2.drawLine(-20, 253, 20, 253);
				g2.drawLine(-75, 276, 75, 276); //30
				g2.drawString("30", -102, 283); 
				//
				g2.drawLine(-20, 299, 20, 299);
				g2.drawLine(-35, 322, 35, 322);
				g2.drawLine(-20, 345, 20, 345);
				g2.drawLine(-75, 368, 75, 368); //40
				g2.drawString("40", -102, 374); 
				
				g2.setTransform(original_trans);
				g2.setClip(original_clipshape);
				
				//bank angle marks and wings
				g2.scale(this.scalex, this.scaley);
				g2.setColor(rs.color_markings);
				g2.setStroke(rs.stroke2_5round);
				g2.translate(adiY, 250f);
				
				//wings
				g2.drawImage(rs.img_isfd_wings, -rs.img_isfd_wings.getWidth() /2, -6, rs.img_isfd_wings.getWidth(), rs.img_isfd_wings.getHeight(), null);
				
				g2.rotate(Math.toRadians(-60f), 0, 0); //-60
				g2.drawLine(0, -175, 0, -185);
				g2.rotate(Math.toRadians(15f), 0, 0); //-45
				g2.drawLine(0, -175, 0, -185);
				g2.rotate(Math.toRadians(15f), 0, 0); //-30
				g2.drawLine(0, -175, 0, -200);
				g2.rotate(Math.toRadians(10f), 0, 0); //-20
				g2.drawLine(0, -175, 0, -185);
				g2.rotate(Math.toRadians(10f), 0, 0); //-10
				g2.drawLine(0, -175, 0, -185);
				g2.rotate(Math.toRadians(20f), 0, 0); //10
				g2.drawLine(0, -175, 0, -185);
				g2.rotate(Math.toRadians(10f), 0, 0); //20
				g2.drawLine(0, -175, 0, -185);
				g2.rotate(Math.toRadians(10f), 0, 0); //30
				g2.drawLine(0, -175, 0, -200);
				g2.rotate(Math.toRadians(15f), 0, 0); //45
				g2.drawLine(0, -175, 0, -185);
				g2.rotate(Math.toRadians(15f), 0, 0); //60
				g2.drawLine(0, -175, 0, -185);
				
				g2.setTransform(original_trans);
				
				//bank angle pointer
				
				g2.scale(this.scalex, this.scaley);
				g2.translate(adiY, 250f);
				g2.rotate(Math.toRadians(-roll), 0, 0);
				
				g2.drawImage(rs.img_isfd_bank_pointer, -rs.img_isfd_bank_pointer.getWidth() / 2, -175 , rs.img_isfd_bank_pointer.getWidth(), rs.img_isfd_bank_pointer.getHeight(), null);
				
				
				g2.setTransform(original_trans);
				
				//top triangle
				g2.scale(this.scalex, this.scaley);
				g2.translate(adiY, 60f);
				g2.fillPolygon(top_tr_x, top_tr_y, 3);
			
				g2.setTransform(original_trans);
				g2.setClip(original_clipshape);
				

				//draw compass

				g2.scale(this.scalex, this.scaley);
				g2.translate(adiY, 815f);
				g2.rotate(Math.toRadians(-heading),0,0);
				g2.drawImage(rs.img_isfd_compass, -375, -375, null);
				g2.setTransform(original_trans);
				
				//bottom triangle
				
				g2.scale(this.scalex, this.scaley);
				g2.translate(adiY, 430f);
				g2.drawPolygon(bottom_tr_x, bottom_tr_y, 3);
				g2.setTransform(original_trans);
				
				if(this.xpd.isfd_mode() == 1) {
					
					//glideslope deviation
					
					g2.scale(this.scalex, this.scaley);
					g2.translate(adiY, 250f);
					

					g2.setStroke(stroke3);
					g2.setColor(Color.BLACK);
					g2.fillRect(96, -118, 20, 236);
					g2.setColor(rs.color_markings);
					g2.drawLine(97, 0, 114, 0);
					g2.drawOval(101, -50, 10, 10);
					g2.drawOval(101, -100, 10, 10);
					g2.drawOval(101, 40, 10, 10);
					g2.drawOval(101, 90, 10, 10);
					
					// pointer is in view when glideslope signal is received
					if(this.xpd.nav1_cdi() == 1) {
						vdef_ydelta = (int) (48f * this.xpd.nav1_vdef_dot());

						if(vdef_ydelta > 96) {
							//vdef_ydelta = 96;
						}
						if(vdef_ydelta < -96) {
							//vdef_ydelta = -96;
						}
						g2.setColor(rs.color_magenta);
						int v_pointer_y[] = {0  + vdef_ydelta, -14  + vdef_ydelta, 0  + vdef_ydelta, 14  + vdef_ydelta};
						g2.fillPolygon(v_pointer_x, v_pointer_y, 4);
					}

					//localizer deviation
					g2.setColor(Color.BLACK);
					g2.fillRect(-118, 150 , 236, 20);
					g2.setColor(rs.color_markings);
					g2.drawLine(0, 150, 0, 169);
					g2.drawOval(-100, 155, 10, 10);
					g2.drawOval(-50, 155, 10, 10);
					g2.drawOval(40, 155, 10, 10);
					g2.drawOval(90, 155, 10, 10);
					
					// pointer in view when localizer signal is received
					if(this.xpd.nav1_type() == 4 || this.xpd.nav1_type() == 5) {
						hdef_xdelta = (int) (48f * this.xpd.nav1_hdef_dot());
						if(hdef_xdelta > 96) {
							//hdef_xdelta = 96;
						}
						if(hdef_xdelta < -96) {
						//	hdef_xdelta = -96;
						}
						g2.setColor(rs.color_magenta);
						int h_pointer_x[] = {-14 + hdef_xdelta, 0 + hdef_xdelta, 14 + hdef_xdelta, 0 + hdef_xdelta};
						g2.fillPolygon(h_pointer_x, h_pointer_y, 4);
					}
				}
				


				
				g2.setTransform(original_trans);
				
	//			g2.scale(this.scalex, this.scaley);
	//			g2.translate(229.95, 765);
	//			g2.setColor(this.color_instrument_gray);
	//			g2.fillOval(-325, -325, 650, 650);
	//			g2.setColor(this.color_instrument_white);
	//			g2.drawLine(0, 0, 0, -325);
	//			
	//			int hdg5 = (int)Math.round( heading / 5.25f ) * 5;
	//			
	//			g2.rotate(Math.toRadians((hdg5 - heading) * 5.25f / 5f), 0, 0);
	//					
	//			g2.rotate(Math.toRadians(-7f * 5.25f), 0, 0);
	//			
	//			
	//			
	//			g2.setFont(new Font("Arial", Font.BOLD, 20));
	//			
	//			for(int i= -35; i <= 35; i+= 5) {
		//
	//				int mark5 = hdg5 + i;
	//				int mark = (mark5+360)%360;
		//
	//				if(mark % 10 == 0) {
	//					
	//					if(mark % 30 == 0) {
		//	
	//					}
	//					String marktext = "" + mark/10;
	//					g2.drawString(marktext, 0 , -290);
	//					deg_marks10.setLine(0, -310, 0, -324);
	//					g2.draw(deg_marks10);
	//					//g2.drawLine(0, -462, 0, -472);
	//					
	//				}else {
	//					deg_marks5.setLine(0, -320, 0, -324);
	//					g2.draw(deg_marks5);
	//				}
	//				g2.rotate(Math.toRadians(5.25f), 0, 0);
		//
	//			}
		//
	//			g2.setTransform(original_trans);
				
				
	//			g2.scale(this.scalex, this.scaley);
	//			g2.translate(229.95, 920);
		//
	//			
	//			int hdg5 = (int)Math.round( heading / 5.0f ) * 5;
	//			
	//			g2.rotate(Math.toRadians((hdg10 - heading) * 7.5f / 5.0f), 0, 0);
	//			g2.rotate(Math.toRadians(-3f * 7.5f), 0, 0);
	//			
	//			g2.setColor(this.color_instrument_white);
		//
	//			
	//			for(int i= -30; i <= 30; i+= 10) {
		//
	//				int mark10 = hdg10 + i;
	//				int mark = (mark10+360)%360;
		//
	//				
	//				
	//				String marktext = "" + mark/10;
	//				System.out.println(mark);
	//				g2.drawString(marktext, 0 , -445);
	//				deg_marks10.setLine(0, -460, 0, -472);
	//				g2.draw(deg_marks10);
	//				//g2.drawLine(0, -462, 0, -472);
	//				g2.rotate(Math.toRadians(7.5f), 0, 0);
		//
	//				
	//			}
	//			g2.setTransform(original_trans);
				
	//			g2.scale(this.scalex, this.scaley);
	//			g2.translate(229.95, 920);
		//
	//			int hdg5 = (int)Math.round( heading / 10.0f ) * 10;
	//			
	//			g2.rotate(Math.toRadians((hdg5 - heading) * 7.5f / 10.0f), 0, 0);
	//			g2.rotate(Math.toRadians(-2.5f * 7.5f), 0, 0);
	//			
	//			g2.setColor(this.color_instrument_white);
	//			
	//			for(int i= -30; i <= 30; i+= 10) {
	//				g2.drawLine(0, -468, 0, -472);
	//				g2.rotate(Math.toRadians(7.5f), 0, 0);
		//
	//			}
	//			g2.setTransform(original_trans);

				//draw speed tape
				
				g2.scale(this.scalex, this.scaley);
				
				g2.setColor(this.color_instrument_gray);
				g2.fillRect(0, 0, 77, 500);
				
				g2.setColor(rs.color_markings);
				g2.setStroke(rs.stroke2_5);
				g2.setFont(altTapeFont);
				
				int ias10 = Math.round(ias / 10.0f) * 10;
				
				for (int ias_mark = ias10 - 60; ias_mark <= ias10 + 60; ias_mark += 10) {
					
					if (ias_mark >= 30) {
						int ias_y = (int) 250f - Math.round(((float) ias_mark - ias) * 4.75f);
						
						if (ias_mark % 20 == 10) {
							g2.drawLine(60, ias_y, 77, ias_y);
						}
						
						if (ias_mark % 20 == 0) {
							if(ias_mark < 100) {
								g2.drawString("" + ias_mark, 30f, ias_y + 10f);
							}else {
								g2.drawString("" + ias_mark, 10f, ias_y + 10f);
							}
							
							g2.drawLine(70, ias_y, 77, ias_y);
						}
						
						
					}
					
				}
				
				g2.setTransform(original_trans);
				
				
				//draw alt tape
				
				g2.scale(this.scalex, this.scaley);
				
				g2.setColor(this.color_instrument_gray);
				g2.fillRect(392, 0, 108, 500);
				
				g2.setColor(rs.color_markings);
				g2.setFont(altTapeFont);
				
				int alt100 = Math.round(alt / 100.0f) * 100;
				
				g2.setStroke(rs.stroke2_5);
				
				for (int alt_mark = alt100 - 400; alt_mark <= alt100 + 400; alt_mark += 100) {
					
					int alt_y = (int) (250f - Math.round(((float) alt_mark - Math.round(alt)) * 500f / 900f));

					if (alt_mark % 200 == 100 || alt_mark % 200 == -100) {
						
						g2.drawLine(392, alt_y, 412, alt_y);
					}
					
					if (alt_mark % 200 == 0) {
						if ((alt_mark >= 1000) || (alt_mark <= -1000)) {
							if (alt_mark < 10000) {
								g2.drawString("" + Math.abs((alt_mark / 1000)), 419f, alt_y + 9f);
							} else {
								g2.drawString("" + Math.abs((alt_mark / 1000)), 402f,  alt_y + 9f );
							}
						}
						
						//for negative values
						if ((alt_mark < 0) && (alt_mark > -1000)) {
							g2.drawString("-", 420f, alt_y + 9f);
						}else if(alt_mark <= -1000) {
							g2.drawString("-", 420f, alt_y + 9f);
						}

						g2.drawString("" + Math.abs((alt_mark / 100) % 10), 436f, alt_y + 9f);
						g2.drawString("00", 453f , alt_y + 9f);
					}
				}
				
				g2.setTransform(original_trans);
				
				//draw black lines
				g2.scale(this.scalex, this.scaley);
				g2.setStroke(rs.stroke4);
				g2.setColor(Color.BLACK);
				//g2.fillOval(80, 0, 80, 500);
				g2.drawLine(77, 0, 77, 500);
				g2.drawLine(392  , 0, 392, 500);
				g2.setTransform(original_trans);
				
				//draw speed box
				g2.setStroke(rs.stroke2_5);
				g2.scale(this.scalex, this.scaley);
				g2.translate(-2f, 250f);
				g2.setColor(Color.BLACK);
				g2.fillRect(0, -33, 82, 66);
				g2.setColor(this.color_instrument_white);
				g2.drawRect(0, -33, 82, 66);
				
				g2.clipRect(0, -32, 81, 65);
				
				//speed readout
				g2.setFont(speedFont);
				g2.setColor(rs.color_markings);
				
				int ias_int = (int) ias;
				int ias_round = Math.round(ias);
				int ias_deca = (ias_round / 10) % 10;
				int ias_hecto = (ias_round / 100) % 10;
				
				float ias_frac = ias - (float) ias_int;
				
				float ias_ydelta = 33f * ias_frac;

				if (ias > 99) {
					g2.drawString("" + ias_hecto % 10, 3f, 14f);
				}
				g2.drawString("" + ias_deca % 10, 28f, 14f);
				
				g2.drawString("" + (ias_int + 2) % 10, 55f, 14f - 33f - 33f + ias_ydelta);
				g2.drawString("" + (ias_int + 1) % 10, 55f, 14f - 33f + ias_ydelta);
				g2.drawString("" + ias_int % 10, 55f, 14f + ias_ydelta);
				g2.drawString("" + (ias_int - 1) % 10, 55f, 14f + 33f + ias_ydelta);
				g2.drawString("" + (ias_int - 2) % 10, 55f, 14f + 33f + 33f + ias_ydelta);
				

			
				g2.setTransform(original_trans);
				g2.setClip(original_clipshape);
			
				//draw alt box
				
				g2.scale(this.scalex, this.scaley);
				g2.translate(358f, 250f);
				g2.setColor(Color.BLACK);
				altbox_clip.setFrame(0, -26, 145, 53);
				g2.fillRect(0, -30, 145, 60);
				g2.setColor(this.color_instrument_white);
				g2.drawRect(0, -30, 145, 60);
				
				//alt readout

				g2.setClip(altbox_clip);
				g2.setFont(speedFont);
				
				if(alt >= 0) {
					
					int alt_int = (int)(alt);
					int alt_round = Math.round(alt / 10) * 10;
					int alt_20 = alt_int / 20 * 20;
					float alt_frac = (alt - (float) alt_20) / 20.0f;
					int alt_100 = (alt_round / 100) % 10;
					int alt_1k = (alt_round / 1000) % 10;
					int alt_10k = (alt_round / 10000) % 10;
					
					if(alt_10k < 1) {
						g2.drawImage(rs.img_green_hatch, 12, -17, 20, 32, null);
					}else {
						g2.setColor(rs.color_markings);
						g2.drawString("" + alt_10k, 8f, 14f);
					}
					
					g2.setColor(rs.color_markings);
					g2.drawString("" + alt_1k, 35f, 14f);
					g2.setFont(altFont);
					g2.drawString("" + alt_100, 65f, 12f);
					
					float ydelta = 33f * alt_frac;
					
					g2.drawString("" + rs.df2.format((alt_20 + 40) % 100), 88f, -21 - 33f + ydelta);
					g2.drawString("" + rs.df2.format((alt_20 + 20) % 100), 88f, -21f + ydelta);
					
					g2.drawString("" + rs.df2.format(alt_20 % 100), 88f, 12f + ydelta);
					
					g2.drawString("" + rs.df2.format(Math.abs((alt_20 - 20) % 100)), 88f, 45f + ydelta);
					g2.drawString("" + rs.df2.format(Math.abs((alt_20 - 40) % 100)), 88f, 78f + ydelta);
					
				} else {
					
					int alt_int = -(int)(alt);
					int alt_round = Math.round(-alt / 10) * 10;
					int alt_20 = alt_int / 20 * 20;
					float alt_frac = (-alt - (float) alt_20) / 20.0f;
					int alt_100 = (alt_round / 100) % 10;
					int alt_1k = (alt_round / 1000) % 10;

					
					g2.setColor(rs.color_markings);
					float ydelta = 33f * alt_frac;
					
					g2.setFont(speedFont);
					g2.drawString("-", 15f, 14f);
					
					g2.setColor(rs.color_markings);
					g2.drawString("" + alt_1k, 35f, 14f);
					g2.setFont(altFont);
					g2.drawString("" + alt_100, 65f, 12f);
					
					
					g2.drawString("" + rs.df2.format(Math.abs((alt_20 - 40) % 100)), 88f, -21 - 33f - ydelta);
					g2.drawString("" + rs.df2.format(Math.abs((alt_20 - 20) % 100)), 88f, -21f - ydelta);
					
					g2.drawString("" + rs.df2.format(alt_20 % 100), 88f, 12f - ydelta);
					
					g2.drawString("" + rs.df2.format(Math.abs((alt_20 + 20) % 100)), 88f, 45f - ydelta);
					g2.drawString("" + rs.df2.format(Math.abs((alt_20 + 40) % 100)), 88f, 78f - ydelta);
					
					
				}
				

				//
				g2.setTransform(original_trans);
				g2.setClip(original_clipshape);
				

				//approach mode && baro setting
				
				g2.setColor(rs.color_lime);	
				g2.scale(this.scalex, this.scaley);	
				g2.setFont(topFont);
				
				if(this.xpd.isfd_mode() == 1) {
					g2.drawString("APP", 85, 45);	
				}
				
				
				g2.setFont(topFontBaro);
				
				if(!this.xpd.isfd_std_mode()) {
					
					if(this.xpd.isfd_alt_baro() > 100) { //HPA
						
						g2.drawString("" + Math.round(this.xpd.isfd_alt_baro()), 250, 42);
						g2.drawString("HPA", 328, 42);
						
					}else { // IN
						
						g2.drawString("" + rs.qnh_in.format(this.xpd.isfd_alt_baro()), 250, 42);
						g2.drawString("IN", 360, 42);
					}
				}else {
					g2.drawString("STD", 300, 42);
				}

				g2.setTransform(original_trans);
				
			}
		
		}
		
	}
	
}