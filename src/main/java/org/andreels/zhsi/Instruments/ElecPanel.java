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

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;

public class ElecPanel extends InstrumentBaseClass {

	private static final long serialVersionUID = 1L;
		
	private Font elecFont = rs.elecPanelFont.deriveFont(30f);
	private FontMetrics fm;
	private int dcVoltsX = 70;
	private int dcVoltsY = 200;
	private int dcAmpsX = 70;
	private int dcAmpsY = 80;
	private int cpsFreqX = 360;
	private int cpsFreqY = 80;
	private int acVoltsX = 360;
	private int acVoltsY = 200;
	private int acAmpsX = 210;
	private int acAmpsY = 200;
	
	String cps_freq = "";
	String dc_volts = "";
	String dc_amps = "";
	String ac_volts = "";
	String ac_amps = "";


	public ElecPanel(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);
		
	}


	@Override
	public void drawInstrument(Graphics2D g2) {
			
		if (this.xpd.power_on() && (this.xpd.standby_bat_pos() == -1 || this.xpd.standby_bat_pos() == 1)) {
		//if (this.xpd.power_on()) {
			
			dcVoltsX = Integer.parseInt(this.preferences.get_preference(ZHSIPreferences.PREF_DCVOLTS_X));
			dcAmpsX = Integer.parseInt(this.preferences.get_preference(ZHSIPreferences.PREF_DCAMPS_X));
			cpsFreqX = Integer.parseInt(this.preferences.get_preference(ZHSIPreferences.PREF_CPSFREQ_X));
			acVoltsX = Integer.parseInt(this.preferences.get_preference(ZHSIPreferences.PREF_ACVOLTS_X));
			acAmpsX = Integer.parseInt(this.preferences.get_preference(ZHSIPreferences.PREF_ACAMPS_X));
			
			switch(this.xpd.dc_power_knob()) {
			case 0:
				dc_volts = "" + (int) Math.round(this.xpd.dc_volt_value());
				dc_amps = "";
			break;
			case 1:
				dc_volts = "" + (int) Math.round(this.xpd.dc_volt_value());
				dc_amps = "";
			break;
			case 2:
				dc_volts = "" + (int) Math.round(this.xpd.dc_volt_value());
				dc_amps = "" + (int) Math.round(this.xpd.dc_amp_value());
			break;
			case 3:
				dc_volts = "" + (int) Math.round(this.xpd.dc_volt_value());
				dc_amps = "" + (int) Math.round(this.xpd.dc_amp_value());
			break;
			case 4:
				dc_volts = "" + (int) Math.round(this.xpd.dc_volt_value());
				dc_amps = "" + (int) Math.round(this.xpd.dc_amp_value());
			break;
			case 5:
				dc_volts = "" + (int) Math.round(this.xpd.dc_volt_value());
				dc_amps = "" + (int) Math.round(this.xpd.dc_amp_value());
			break;
			default:
				dc_volts = "";
				dc_amps = " ";
			}
			
			switch(this.xpd.ac_power_knob()) {
			case 0:
				cps_freq = "" + (int) Math.round(this.xpd.ac_freq_value());
				ac_volts = "" + (int) Math.round(this.xpd.ac_volt_value());
				ac_amps = "";
				break;
			case 1:
				cps_freq = "" + (int) Math.round(this.xpd.ac_freq_value());
				ac_volts = "" + (int) Math.round(this.xpd.ac_volt_value());
				ac_amps = "";
				break;
			case 2:
				cps_freq = "" + (int) Math.round(this.xpd.ac_freq_value());
				ac_volts = "" + (int) Math.round(this.xpd.ac_volt_value());
				ac_amps = "" + (int) Math.round(this.xpd.ac_amp_value());
				break;
			case 3:
				cps_freq = "" + (int) Math.round(this.xpd.ac_freq_value());
				ac_volts = "" + (int) Math.round(this.xpd.ac_volt_value());
				ac_amps = "" + (int) Math.round(this.xpd.ac_amp_value());
				break;
			case 4:
				cps_freq = "" + (int) Math.round(this.xpd.ac_freq_value());
				ac_volts = "" + (int) Math.round(this.xpd.ac_volt_value());
				ac_amps = "" + (int) Math.round(this.xpd.ac_amp_value());
				break;
			case 5:
				cps_freq = "" + (int) Math.round(this.xpd.ac_freq_value());
				ac_volts = "" + (int) Math.round(this.xpd.ac_volt_value());
				ac_amps = "";
				break;
			default:
				cps_freq = "";
				ac_volts = "";
				ac_amps = "";
			}
			
			
			g2.scale(this.scalex, this.scaley);
			//
			g2.setColor(rs.color_lime);
			g2.setFont(elecFont);
			fm = g2.getFontMetrics(elecFont);

			//dc amps
			g2.drawString(dc_amps, dcAmpsX - fm.stringWidth(dc_amps), dcAmpsY);

			//dc volts
			g2.drawString(dc_volts, dcVoltsX - fm.stringWidth(dc_volts), dcVoltsY);
			
			//cps freg
			g2.drawString(cps_freq, cpsFreqX - fm.stringWidth(cps_freq), cpsFreqY);
			
			//ac volts
			g2.drawString(ac_volts, acVoltsX - fm.stringWidth(ac_volts), acVoltsY);
			
			//ac amps
			g2.drawString(ac_amps, acAmpsX - fm.stringWidth(ac_amps), acAmpsY);
			
			if(this.xpd.ac_power_knob() == 6 && this.xpd.dc_power_knob() == 6) {
				
				g2.drawString("NO FAULTS", 40, 80);
				g2.drawString("STORED", 40, 200);
				
			}
			
			g2.setTransform(original_trans);
		}		
	}
}