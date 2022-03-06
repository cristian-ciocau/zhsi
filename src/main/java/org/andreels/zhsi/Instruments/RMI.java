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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;

public class RMI extends InstrumentBaseClass {

	private static final long serialVersionUID = 1L;

	private GeneralPath clipShape = new GeneralPath();
	private GeneralPath arrow1Path = new GeneralPath();
	private GeneralPath arrow2Path = new GeneralPath();

	private BufferedImage img_markings = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D markings = img_markings.createGraphics();
	private BufferedImage img_arrow1 = new BufferedImage(50, 500, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D arrow1 = img_arrow1.createGraphics();
	private BufferedImage img_arrow2 = new BufferedImage(50, 500, BufferedImage.TYPE_INT_ARGB);
	private Graphics2D arrow2 = img_arrow2.createGraphics();
	
	private boolean imagesRedrawRequired = true;
	private Font topFont;
	private Font hdgFont;
	
	public Color rmi_color = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.PREF_RMI_COLOR).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.PREF_RMI_COLOR).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.PREF_RMI_COLOR).substring(6,8),16));
	
	private int[] x_points = {0, -8, 8};
	private int[] y_points = {-200, -230, -230};
	private int[] x_points1 = {0, -8, 8};
	private int[] y_points1 = {-225, -240, -240};
	private int[] x_points2 = {0, -8, 8};
	private int[] y_points2 = {-215, -230, -230};
	private int[] x_points3 = {0, -8, 8};
	private int[] y_points3 = {-200, -215, -215};
	
	private int[] x_points4 = {0, 22, 0, 22, -2, -2, 24, 24};
	private int[] y_points4 = {0, 12, 24, 36, 48, 132, 192, 0};
	private int[] x_points5 = {0, -22, 0, -22, 2, 2, -24, -24};
	private int[] y_points5 = {0, 12, 24, 36, 48, 132, 192, 0};
	
	private int[] x_points6 = {3, 12, 21, 3};
	private int[] y_points6 = {78, 60, 78, 78};
	private int[] x_points7 = {-3, -12, -21, -3};
	private int[] y_points7 = {78, 60, 78, 78};
	private int[] x_points8 = {-9, -12, -15, -9};
	private int[] y_points8 = {74, 69, 74, 74};
	
	private int[] x_points9 = {0, 75, 80, 85, 100, 145, 195, 110, 0, 0};
	private int[] y_points9 = {-5, -13, 5, -14, -15, -8, 8, 8, 20, -5};

	public RMI(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);
		// TODO Auto-generated constructor stub

		clipShape.moveTo(465, 250);
		clipShape.curveTo(465, 540, 35, 540, 35, 250);
		clipShape.lineTo(35, 180);
		clipShape.curveTo(55, -30, 445, -30, 465, 180);
		clipShape.closePath();
		topFont = rs.chronoFont.deriveFont(24f);
		hdgFont = rs.chronoFont.deriveFont(16f);
		

	}

	@Override
	public void drawInstrument(Graphics2D g2) {

		if(imagesRedrawRequired) {
			
			
			markings.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			markings.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			markings.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			markings.clearRect(0, 0, 500, 500);
			markings.setColor(rs.color_markings);
			markings.translate(250, 250);

			for(int i = 0; i < 360; i += 5) {
				if(i % 30 == 0) {
					//markings.setFont(rs.glass30);
					markings.setFont(rs.chronoFont.deriveFont(40f));
					markings.setStroke(rs.stroke4);
					markings.drawLine(0, -180, 0, -210);
					if(i > 100) {
						markings.drawString("" + i / 10, -20, -145);
					}else {
						markings.drawString("" + i / 10, -10, -145);
					}

				}else if(i % 10 == 0) {
					markings.setStroke(rs.stroke4);
					markings.drawLine(0, -180, 0, -210);
				}else {
					markings.setStroke(rs.stroke2);
					markings.drawLine(0, -180, 0, -200);	
				}
				markings.rotate(Math.toRadians(5), 0, 0);
			}

			markings.setTransform(original_trans);
			
			
			arrow1.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			arrow1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			arrow1.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			arrow1.setComposite(AlphaComposite.Clear);
			arrow1.fillRect(0, 0, 50, 500);
			arrow1.setComposite(AlphaComposite.Src);
	
			arrow1.setColor(rs.color_markings);
			arrow1.translate(25, 250);
			
			arrow1Path.moveTo(-11, -125);
			arrow1Path.lineTo(-17, -125);
			arrow1Path.curveTo(-6, -135, -3, -160, 0, -173);
			arrow1Path.curveTo(3, -160, 6, -135, 17, -125);
			arrow1Path.closePath();
			
			arrow1.fill(arrow1Path);
			
			arrow1.setStroke(rs.stroke5dash155);
			
			arrow1.drawLine(0, -170, 0, 175);
			
			arrow1.setTransform(original_trans);
			
			
			arrow2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			arrow2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			arrow2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			arrow2.setComposite(AlphaComposite.Clear);
			arrow2.fillRect(0, 0, 50, 500);
			arrow2.setComposite(AlphaComposite.Src);
	
			arrow2.setColor(rs.color_markings);
			arrow2.translate(25, 250);
			
			arrow2.setStroke(rs.stroke4round);
			
			arrow2Path.moveTo(-11, -125);
			arrow2Path.lineTo(-19, -125);
			arrow2Path.curveTo(-7, -135, -5, -160, 0, -173);
			arrow2Path.curveTo(5, -160, 7, -135, 19, -125);
			arrow2Path.lineTo(11, -125);
			arrow2Path.lineTo(11, 132);
			arrow2Path.lineTo(0, 149);
			arrow2Path.lineTo(0, 175);
			arrow2Path.lineTo(0, 149);
			arrow2Path.lineTo(-11, 132);
			arrow2Path.closePath();
			
			arrow2.draw(arrow2Path);
			
			arrow2.setTransform(original_trans);
		
			imagesRedrawRequired = false;
		}

		g2.setColor(new Color(0x101010));
		//g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());


		g2.scale(this.scalex, this.scaley);
		g2.setClip(clipShape);
		g2.setTransform(original_trans);

		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		//markings
		g2.scale(this.scalex, this.scaley);
		g2.rotate(Math.toRadians(this.xpd.heading("cpt") * -1), 250, 250);
		g2.drawImage(img_markings, 0, 0, null);
		g2.setTransform(original_trans);

		//flags
		g2.setClip(original_clipshape);
		
		//triangles
		g2.scale(this.scalex, this.scaley);
		g2.setColor(rs.color_markings);
		g2.translate(250, 250);
				
		g2.fillPolygon(x_points, y_points, 3);
		
		g2.rotate(Math.toRadians(45f), 0,0);
		
		g2.fillPolygon(x_points1, y_points1, 3);
		
		g2.rotate(Math.toRadians(45f), 0,0);
		
		g2.fillPolygon(x_points2, y_points2, 3);
		
		g2.rotate(Math.toRadians(45f), 0,0);
		
		g2.fillPolygon(x_points2, y_points2, 3);
		
		g2.rotate(Math.toRadians(45f), 0,0);
		
		g2.fillPolygon(x_points3, y_points3, 3);
		
		g2.rotate(Math.toRadians(45f), 0,0);
		
		g2.fillPolygon(x_points2, y_points2, 3);
		
		g2.rotate(Math.toRadians(45f), 0,0);
		
		g2.fillPolygon(x_points2, y_points2, 3);
		
		g2.rotate(Math.toRadians(45f), 0,0);
		
		g2.fillPolygon(x_points1, y_points1, 3);
		
		g2.setTransform(original_trans);
		
		//needles
		g2.scale(this.scalex, this.scaley);
		g2.translate(250, 250);
		g2.rotate(Math.toRadians(this.xpd.rmi_arrow1()), 0, 0);
		g2.drawImage(img_arrow1, -25, -250, null);
		g2.setTransform(original_trans);
		
		g2.scale(this.scalex, this.scaley);
		g2.translate(250, 250);
		g2.rotate(Math.toRadians(this.xpd.rmi_arrow2()), 0, 0);
		g2.drawImage(img_arrow2, -25, -250, null);
		g2.setTransform(original_trans);
		
		//center
		g2.scale(this.scalex, this.scaley);
		g2.translate(250, 250);
		g2.setColor(new Color(0x202020));
		g2.fillOval(-35, -35, 70, 70);
		g2.setColor(new Color(0x101010));
		g2.fillOval(-32, -32, 64, 64);
		g2.setColor(Color.BLACK);
		g2.fillOval(-28, -28, 56, 56);
		g2.setTransform(original_trans);

		//top text markings
		g2.setColor(rs.color_markings);
		g2.scale(this.scalex, this.scaley);
		g2.setFont(topFont);
		g2.drawString("V", 15, 310);
		g2.drawString("O", 14, 335);
		g2.drawString("R", 17, 360);
		g2.drawString("V", 470, 310);
		g2.drawString("O", 469, 335);
		g2.drawString("R", 472, 360);
		g2.drawString("ADF", 140, 480);
		g2.drawString("ADF", 310, 480);
		g2.setTransform(original_trans);
		
		// Bearing Pointer No. 1 Warning Flag
		if (this.xpd.rmi_arrow1_no_avail() == 1.0) {
			g2.scale(this.scalex, this.scaley);
			g2.setColor(rmi_color);
			
			g2.translate(45, 175);
			g2.fillPolygon(x_points4, y_points4, 8);
			g2.setColor(Color.BLACK);
			g2.setStroke(rs.stroke3);
			g2.drawPolyline(x_points6, y_points6, 4);
			g2.drawLine(11,78,11,96);		
			g2.setTransform(original_trans);
		}
		
		// Bearing Pointer No. 2 Warning Flag
		if (this.xpd.rmi_arrow2_no_avail() == 1.0) {
			g2.scale(this.scalex, this.scaley);
			g2.setColor(rmi_color);
			g2.translate(455, 175);
			g2.fillPolygon(x_points5, y_points5, 8);
			g2.setColor(Color.BLACK);
			g2.setStroke(rs.stroke3);
			g2.drawPolyline(x_points7, y_points7, 4);
			g2.drawPolyline(x_points8, y_points8, 4);
			g2.drawLine(-10,78,-10,96);
			g2.drawLine(-14,78,-14,96);				
			g2.setTransform(original_trans);
		}
		
		// hdg flag
		if (this.xpd.battery_on() == 0) {
			g2.scale(this.scalex, this.scaley);
			g2.setColor(rmi_color);
			g2.translate(170, 40);
			g2.fillPolygon(x_points9, y_points9, 10);
			g2.setFont(hdgFont);
			g2.setColor(Color.BLACK);
			g2.drawString("HDG", 20, 8);
			g2.setTransform(original_trans);
		}		
	}
}