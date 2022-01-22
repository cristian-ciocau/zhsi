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

public class FltAltPanel extends InstrumentBaseClass {

	private static final long serialVersionUID = 1L;
	
	private Font flptaltFont = rs.irsPanelFont.deriveFont(40f);
	private FontMetrics fm;
	private Color flptaltColor = new Color(0xff8000);

		
	public FltAltPanel(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void drawInstrument(Graphics2D g2) {
		
		if(this.xpd.power_on()) {
			
			g2.scale(this.scalex, this.scaley);
			g2.setColor(flptaltColor);
			g2.setFont(flptaltFont);
			fm = g2.getFontMetrics(flptaltFont);

			g2.drawString(String.format("%d", this.xpd.flt_alt()), 205 - fm.stringWidth(String.format("%d", this.xpd.flt_alt())), 55);

			g2.setTransform(original_trans);
		}
	}
}