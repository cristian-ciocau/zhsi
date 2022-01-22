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
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import org.andreels.zhsi.ModelFactory;

public class Chrono extends InstrumentBaseClass {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Ellipse2D.Float clockClip = new Ellipse2D.Float();
	private int seconds = 0;
	private float seconds1 = 0.0f;
	private Timer secondsTimer;
	private Timer secondsTimer1;
	
	private Color clockBgColor = new Color(42, 23, 69, 150); //0x6A5ACD // 42, 23, 69, 120
	private Color clockBgColor2 = new Color(42, 23, 69, 235); //0x6A5ACD // 
	GradientPaint gp = new GradientPaint(0, -1500, Color.WHITE , 0, 50, clockBgColor);
	
	private BufferedImage img_Needle = new BufferedImage(16, 100, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D needle = img_Needle.createGraphics();
	

	public Chrono(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);

		needle.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		needle.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		needle.setColor(new Color(255,255,255,200));
		int[] needle_x = {4,0,16,12};
		int[] needle_y = {100,0,0,100};
		needle.fillPolygon(needle_x, needle_y, 4);

		
		secondsTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				seconds++;
				if(seconds > 59) {
					seconds = 0;
				}
			}
			
		});
		secondsTimer.setInitialDelay(1000);
		secondsTimer.setDelay(1000);
		
		secondsTimer1 = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				seconds1 += 0.01f;
				if(seconds1 > 59) {
					seconds1 = 0;
				}
			}
			
		});
		secondsTimer1.setInitialDelay(1);
		secondsTimer1.setDelay(10);
		
	}

	@Override
	public void drawInstrument(Graphics2D g2) {
		
		
		g2.scale(this.scalex, this.scaley);
		g2.translate(250f, 250f);
		clockClip.setFrame(-250, -250, 500, 500);
		g2.setClip(clockClip);
		
		
		
		g2.setPaint(gp);
		g2.fillRect(-250, -250, 500, 500);
		g2.setTransform(original_trans);
		g2.setClip(original_clipshape);
		
//		float fadeValue = 255 * (1 - this.xpd.du_brightness(this.title) * 0.8f);
//		Color fadeColor = new Color(0, 0, 0, (int) fadeValue);
//		g2.setColor(fadeColor);
//		g2.scale(this.scalex, this.scaley);
//		g2.fillRect(0, 0, 500, 500);
//		g2.setTransform(original_trans);
		
		g2.scale(this.scalex, this.scaley);
		g2.translate(250f, 250f);
		
		g2.setColor(rs.color_chrono);
		
		
		for(int i = 0; i < 360; i += 6) {
			
			if(i % 30 == 0) {
				g2.setStroke(rs.stroke6);
				g2.drawLine(0, -210, 0, -245);
			}else {
				g2.setStroke(rs.stroke4);
				g2.drawLine(0, -210, 0, -230);
			}
			
			g2.rotate(Math.toRadians(6f), 0,0);
		}
		
		g2.setTransform(original_trans);
		
		//seconds needle
		
		g2.scale(this.scalex, this.scaley);
		g2.translate(250f, 250f);

		//text
		g2.setFont(rs.chronoFont.deriveFont(44f));
		//60
		g2.drawString("60", -23, -160);
		g2.drawString("10", 125, -70);
		g2.drawString("20", 125, 100);
		g2.drawString("30", -23, 195);
		g2.drawString("40", -175, 100);
		g2.drawString("50", -175, -70);
		
		g2.setFont(rs.chronoFont.deriveFont(28f));
		if(this.xpd.chrono_display_mode(pilot) == 1 || this.xpd.chrono_display_mode(pilot) == 3) {
			g2.drawString("TIME", -30, -110);
		}else {
			g2.drawString("DATE", -30, -110);
		}
		
		
		//time
		
		//backgrounds
		g2.setColor(clockBgColor2);
		g2.setFont(rs.chronoFont.deriveFont(30f));
		g2.drawString("ET", -40, 110);
		g2.drawString("CHR", 20, 110);
		g2.setFont(rs.chronoSegFont.deriveFont(58f));
		g2.drawString("88:88", -100, -35);
		g2.drawString("88:88", -100, 80);

		
		g2.setColor(rs.color_chrono);
		//UTC/MAN
		g2.setFont(rs.chronoBFont.deriveFont(22f));
		if(this.xpd.chrono_display_mode(pilot) == 1 || this.xpd.chrono_display_mode(pilot) == 2) {
			g2.drawString("UTC", 140, -205);
		}else {
			g2.drawString("MAN", 110, -225);
		}
		
		if(this.xpd.chrono_et_mode(pilot) == 1) {
			g2.drawString("RUN", -225, 180);
		}else if (this.xpd.chrono_et_mode(pilot) == 0) {
			g2.drawString("HLD", -205, 200);
		}
		
		
		
		//time and date
		g2.setFont(rs.chronoSegFont.deriveFont(58f));
		if(this.xpd.chrono_display_mode(pilot) == 1 || this.xpd.chrono_display_mode(pilot) == 3) {
			if(this.xpd.chrono_display_mode(pilot) == 3) {
				g2.drawString(rs.df2.format(this.xpd.time_local_hrs()) + ":" + rs.df2.format(this.xpd.time_local_min()), -100, -35);
			}else {
				g2.drawString(rs.df2.format(this.xpd.time_zulu_hrs()) + ":" + rs.df2.format(this.xpd.time_zulu_min()), -100, -35);
			}
		}else {
			g2.drawString(rs.df2.format(this.xpd.time_month()) + " " + rs.df2.format(this.xpd.time_day()), -100, -35);
		}
		

		
		if(this.xpd.chrono_mode(pilot) == 0 || this.xpd.chrono_mode(pilot) == 1 || this.xpd.chrono_et_mode(pilot) == 0 || this.xpd.chrono_et_mode(pilot) == 1) {

			
			if((this.xpd.chrono_et_mode(pilot) == 0 || this.xpd.chrono_et_mode(pilot) == 1) && !(this.xpd.chrono_mode(pilot) == 0 || this.xpd.chrono_mode(pilot) == 1)) {
				//ET
				g2.setFont(rs.chronoSegFont.deriveFont(58f));
				g2.drawString(rs.df2.format(this.xpd.chrono_et_hrs(pilot)) + ":" + rs.df2.format((int)this.xpd.chrono_et_min(pilot)), -100, 80);
				g2.setFont(rs.chronoFont.deriveFont(30f));
				g2.drawString("ET", -40, 110);
			}else {
				//CHR
				g2.setFont(rs.chronoSegFont.deriveFont(58f));
				g2.drawString(rs.df2.format(this.xpd.chrono_minute(pilot)) + ":" + rs.df2.format((int)this.xpd.chrono_needle(pilot)), -100, 80);
				g2.setFont(rs.chronoFont.deriveFont(30f));
				g2.drawString("CHR", 20, 110);
			}
			
			
			
		}
		
		g2.rotate(Math.toRadians(this.xpd.chrono_needle(pilot) * 6f), 0, 0);
		
		g2.drawImage(img_Needle, -8, -245, null);
		
		g2.setTransform(original_trans);
		
		//g2.dispose();
	}

}
