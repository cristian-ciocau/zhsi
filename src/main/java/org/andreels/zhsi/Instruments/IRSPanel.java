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
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;

public class IRSPanel extends InstrumentBaseClass {

	private static final long serialVersionUID = 1L;
	
	private Font irsFont = rs.irsPanelFont.deriveFont(40f);
	private FontMetrics fm;

	private Color irs_color = new Color(Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.PREF_IRS_COLOR).substring(2,4),16),
										Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.PREF_IRS_COLOR).substring(4,6),16),
										Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.PREF_IRS_COLOR).substring(6,8),16));

	public IRSPanel(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void drawInstrument(Graphics2D g2) {
		
		if(this.xpd.power_on()) {
			
			g2.scale(this.scalex, this.scaley);
			g2.setColor(irs_color);
			g2.setFont(irsFont);
			fm = g2.getFontMetrics(irsFont);

			g2.drawString(this.xpd.irs_left_string(), 211 - fm.stringWidth(this.xpd.irs_left_string()), 55);
			g2.drawString(this.xpd.irs_right_string(), 478 - fm.stringWidth(this.xpd.irs_right_string()), 55);
			
			if (this.xpd.irs_decimals_show()) {
				//left dots
				g2.fillOval(105, 2, 8, 8);
				g2.fillOval(205, 2, 8, 8);
				g2.fillOval(175, 60, 8, 8);
				//right dots
				g2.fillOval(375, 2, 8, 8);
				g2.fillOval(475, 2, 8, 8);
				g2.fillOval(445, 60, 8, 8);
			}

			g2.setTransform(original_trans);
		}
	}
}