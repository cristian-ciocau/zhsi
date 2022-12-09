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
package org.andreels.zhsi.xpdata;

import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRef;
import org.andreels.zhsi.ExtPlaneInterface.util.Observer;

public class ButtonsSwitches extends BaseDataClass {
	
	Observer<DataRef> ButtonsSwitches;
	
	private final String INST_BRIGHTNESS = "sim/cockpit2/switches/instrument_brightness_ratio";
	private final String[] MAIN_PANEL_DU = {"laminar/B738/toggle_switch/main_pnl_du_capt", "laminar/B738/toggle_switch/main_pnl_du_fo"};
	private final String[] LOWER_PANEL_DU = {"laminar/B738/toggle_switch/lower_du_capt", "laminar/B738/toggle_switch/lower_du_fo"};
	private final String FUEL_TANK_CTR1_POS = "laminar/B738/fuel/fuel_tank_pos_ctr1";
	private final String FUEL_TANK_CTR2_POS = "laminar/B738/fuel/fuel_tank_pos_ctr2";
	private final String FUEL_TANK_LFT1_POS = "laminar/B738/fuel/fuel_tank_pos_lft1";
	private final String FUEL_TANK_LFT2_POS = "laminar/B738/fuel/fuel_tank_pos_lft2";
	private final String FUEL_TANK_RGT1_POS = "laminar/B738/fuel/fuel_tank_pos_rgt1";
	private final String FUEL_TANK_RGT2_POS = "laminar/B738/fuel/fuel_tank_pos_rgt2";
	private final String TRANSPONDER_POS = "laminar/B738/knob/transponder_pos";
	private final String DC_POWER_KNOB = "laminar/B738/knob/dc_power";
	private final String AC_POWER_KNOB = "laminar/B738/knob/ac_power";
	
	public float[] du_brightness = new float[32];
	public int[] main_panel_du = new int[2];
	public int[] lower_panel_du = new int[2];
	public int fuel_tank_ctr1_pos = 0;
	public int fuel_tank_ctr2_pos = 0;
	public int fuel_tank_lft1_pos = 0;
	public int fuel_tank_lft2_pos = 0;
	public int fuel_tank_rgt1_pos = 0;
	public int fuel_tank_rgt2_pos = 0;
	public int transponder_pos = 0;
	public int ac_power_knob = 0;
	public int dc_power_knob = 0;

	
	public ButtonsSwitches(ExtPlaneInterface iface) {
		super(iface);
		
		ButtonsSwitches = new Observer<DataRef>() { // create FMS objects when data changes

			@Override
			public void update(DataRef object) {
				
				switch(object.getName()) {
				case INST_BRIGHTNESS:
					for(int i = 0; i < 32; i++) {
						du_brightness[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case FUEL_TANK_CTR1_POS:
					fuel_tank_ctr1_pos = Integer.parseInt(object.getValue()[0]);
					break;
				case FUEL_TANK_CTR2_POS:
					fuel_tank_ctr2_pos = Integer.parseInt(object.getValue()[0]);
					break;
				case FUEL_TANK_LFT1_POS:
					fuel_tank_lft1_pos = Integer.parseInt(object.getValue()[0]);
					break;
				case FUEL_TANK_LFT2_POS:
					fuel_tank_lft2_pos = Integer.parseInt(object.getValue()[0]);
					break;
				case FUEL_TANK_RGT1_POS:
					fuel_tank_rgt1_pos = Integer.parseInt(object.getValue()[0]);
					break;
				case FUEL_TANK_RGT2_POS:
					fuel_tank_rgt2_pos = Integer.parseInt(object.getValue()[0]);
					break;
				case TRANSPONDER_POS:
					transponder_pos = Integer.parseInt(object.getValue()[0]);
					break;
				case DC_POWER_KNOB:
					dc_power_knob = Integer.parseInt(object.getValue()[0]);
					break;
				case AC_POWER_KNOB:
					ac_power_knob = Integer.parseInt(object.getValue()[0]);
					break;
				}
				
				for(int i = 0; i < 2; i++) {
					if(object.getName().equals(MAIN_PANEL_DU[i])) {
						main_panel_du[i] = Integer.parseInt(object.getValue()[0]);						
					}
					if(object.getName().equals(LOWER_PANEL_DU[i])) {
						lower_panel_du[i] = Integer.parseInt(object.getValue()[0]);						
					}
				}
			}

		};
	}
	@Override
	public void subscribeDrefs() {


	}
	@Override
	public void includeDrefs() {
		
		iface.includeDataRef(INST_BRIGHTNESS);
		iface.includeDataRef(FUEL_TANK_CTR1_POS);
		iface.includeDataRef(FUEL_TANK_CTR2_POS);
		iface.includeDataRef(FUEL_TANK_LFT1_POS);
		iface.includeDataRef(FUEL_TANK_LFT2_POS);
		iface.includeDataRef(FUEL_TANK_RGT1_POS);
		iface.includeDataRef(FUEL_TANK_RGT2_POS);
		iface.includeDataRef(TRANSPONDER_POS);
		iface.includeDataRef(AC_POWER_KNOB);
		iface.includeDataRef(DC_POWER_KNOB);
		//
		iface.observeDataRef(INST_BRIGHTNESS, ButtonsSwitches);
		iface.observeDataRef(FUEL_TANK_CTR1_POS, ButtonsSwitches);
		iface.observeDataRef(FUEL_TANK_CTR2_POS, ButtonsSwitches);
		iface.observeDataRef(FUEL_TANK_LFT1_POS, ButtonsSwitches);
		iface.observeDataRef(FUEL_TANK_LFT2_POS, ButtonsSwitches);
		iface.observeDataRef(FUEL_TANK_RGT1_POS, ButtonsSwitches);
		iface.observeDataRef(FUEL_TANK_RGT2_POS, ButtonsSwitches);
		iface.observeDataRef(TRANSPONDER_POS, ButtonsSwitches);
		iface.observeDataRef(AC_POWER_KNOB, ButtonsSwitches);
		iface.observeDataRef(DC_POWER_KNOB, ButtonsSwitches);
		
		for(int i = 0; i < 2; i++) {
			iface.includeDataRef(MAIN_PANEL_DU[i]);
			iface.includeDataRef(LOWER_PANEL_DU[i]);
			iface.observeDataRef(MAIN_PANEL_DU[i], ButtonsSwitches);
			iface.observeDataRef(LOWER_PANEL_DU[i], ButtonsSwitches);
		}
		
	}
	@Override
	public void excludeDrefs() {
		
		iface.excludeDataRef(INST_BRIGHTNESS);
		iface.excludeDataRef(FUEL_TANK_CTR1_POS);
		iface.excludeDataRef(FUEL_TANK_CTR2_POS);
		iface.excludeDataRef(FUEL_TANK_LFT1_POS);
		iface.excludeDataRef(FUEL_TANK_LFT2_POS);
		iface.excludeDataRef(FUEL_TANK_RGT1_POS);
		iface.excludeDataRef(FUEL_TANK_RGT2_POS);
		iface.excludeDataRef(TRANSPONDER_POS);
		iface.excludeDataRef(AC_POWER_KNOB);
		iface.excludeDataRef(DC_POWER_KNOB);
		//
		iface.unObserveDataRef(INST_BRIGHTNESS, ButtonsSwitches);
		iface.unObserveDataRef(FUEL_TANK_CTR1_POS, ButtonsSwitches);
		iface.unObserveDataRef(FUEL_TANK_CTR2_POS, ButtonsSwitches);
		iface.unObserveDataRef(FUEL_TANK_LFT1_POS, ButtonsSwitches);
		iface.unObserveDataRef(FUEL_TANK_LFT2_POS, ButtonsSwitches);
		iface.unObserveDataRef(FUEL_TANK_RGT1_POS, ButtonsSwitches);
		iface.unObserveDataRef(FUEL_TANK_RGT2_POS, ButtonsSwitches);
		iface.unObserveDataRef(TRANSPONDER_POS, ButtonsSwitches);
		iface.unObserveDataRef(AC_POWER_KNOB, ButtonsSwitches);
		iface.unObserveDataRef(DC_POWER_KNOB, ButtonsSwitches);
		for(int i = 0; i < 2; i++) {
			iface.excludeDataRef(MAIN_PANEL_DU[i]);
			iface.excludeDataRef(LOWER_PANEL_DU[i]);
			iface.unObserveDataRef(MAIN_PANEL_DU[i], ButtonsSwitches);
			iface.unObserveDataRef(LOWER_PANEL_DU[i], ButtonsSwitches);
		}
		
	}

}