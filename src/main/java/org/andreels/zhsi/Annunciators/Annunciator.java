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
package org.andreels.zhsi.Annunciators;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;

public class Annunciator extends AnnunciatorBaseClass {

	private static final long serialVersionUID = 1L;
	
	int w;
	boolean lit = false;
	
	public Annunciator(String upperText, String lowerText, String dref, Color color) {
		super(upperText, lowerText, dref, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawAnnunciator(Graphics2D g2) {
				
		if (dref != null) {
			String[] val = iface.getDataRefValue(dref);
			if (val != null) {
				if (val[0].equals("1")) {
					lit = true;
				} else {
					lit = false;
				}
			}
		}
	
		g2.scale(this.scalex, this.scaley);

		g2.setColor(rs.color_annun_bg);

		g2.fillRoundRect(0, 0, 150, 75, 10, 10);
		
		if(lit) {
			
			g2.setColor(this.color.darker());
			g2.fillRect(5, 5, 140, 65);
		}
		
		g2.setColor(Color.BLACK);
		g2.fillRect(6, 6, 138, 63);
		
		g2.setFont(rs.glass22);
		
		FontMetrics fm = g2.getFontMetrics(rs.glass22);
		
		if (lit) {
			g2.setColor(this.color);

		} else {
			g2.setColor(rs.color_annun_bg);
			g2.setColor(this.color);
		}
		
		if (this.lowerText != null) {
			
			w = fm.stringWidth(this.upperText);
			g2.drawString(this.upperText, (150 / 2) - (w / 2), (75 / 2) - 2);
			
			w = fm.stringWidth(this.lowerText);
			g2.drawString(this.lowerText, (150 / 2) - (w / 2), (75 / 2) + fm.getHeight());
			
		} else {
			
			w = fm.stringWidth(this.upperText);
			g2.drawString(this.upperText, (150 / 2) - (w / 2), (75 / 2) + (fm.getHeight() / 2));
			
		}		
		g2.setTransform(original_trans);		
	}
}