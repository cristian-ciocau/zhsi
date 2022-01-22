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

public class Systems extends BaseDataClass {
	
	Observer<DataRef> systems;
	
	private final String BRAKE_TEMP_LEFT_IN = "laminar/B738/systems/brake_temp_left_in";
	private final String BRAKE_TEMP_LEFT_OUT = "laminar/B738/systems/brake_temp_left_out";
	private final String BRAKE_TEMP_RIGHT_IN = "laminar/B738/systems/brake_temp_right_in";
	private final String BRAKE_TEMP_RIGHT_OUT = "laminar/B738/systems/brake_temp_right_out";
	private final String HYD_A_QTY = "laminar/B738/hydraulic/hyd_A_qty";
	private final String HYD_B_QTY = "laminar/B738/hydraulic/hyd_B_qty";
	private final String VHF_NAV_SOURCE = "laminar/B738/toggle_switch/vhf_nav_source";
	private final String HYD_A_PRESSURE = "laminar/B738/hydraulic/A_pressure";
	private final String HYD_B_PRESSURE = "laminar/B738/hydraulic/B_pressure";
	private final String FLT_ALT = "sim/cockpit/pressure/max_allowable_altitude";
	private final String LAND_ALT = "laminar/B738/pressurization/knobs/landing_alt";
	private final String LEFT_ELEVATOR_DEFLECTION = "sim/flightmodel/controls/wing1l_elv1def";
	private final String RIGHT_ELEVATOR_DEFLECTION = "sim/flightmodel/controls/wing1r_elv1def";
	private final String LEFT_AIL_DEFLECTION = "sim/flightmodel/controls/lail1def";
	private final String RIGHT_AIL_DEFLECTION = "sim/flightmodel/controls/rail1def";
	private final String RUDDER_DEFLECTION = "sim/flightmodel/controls/ldruddef";
	private final String SPLR_DEFLECTION = "sim/flightmodel2/wing/spoiler1_deg";
	private final String STAB_TRIM = "sim/cockpit2/controls/elevator_trim";
	private final String RUDDER_TRIM = "sim/cockpit2/controls/rudder_trim";
	private final String FLAPS_L_DEFLECTION = "sim/flightmodel/controls/wing1l_fla1def";
	private final String FLAPS_R_DEFLECTION = "sim/flightmodel/controls/wing1r_fla1def";
	private final String THR_LVR1 = "laminar/B738/engine/thr_lvr1";
	private final String THR_LVR2 = "laminar/B738/engine/thr_lvr2";
	private final String LOWERDU_PAGE = "laminar/B738/systems/lowerDU_page";
	private final String LOWERDU_PAGE2 = "laminar/B738/systems/lowerDU_page2";
	private final String WINDSHEER = "laminar/b738/alert/pfd_windshear";
	private final String PULLUP = "laminar/b738/alert/pfd_pull_up";
	private final String XRAAS_MESSAGE = "xraas/ND_alert";
	private final String REVERSER_DEPLOY = "sim/flightmodel2/engines/thrust_reverser_deploy_ratio";
	private final String SIM_PAUSED = "sim/time/paused";
	private final String REL_GLS = "sim/operation/failures/rel_gls";
	
	public float brake_temp_left_in = 0f;
	public float brake_temp_left_out = 0f;
	public float brake_temp_right_in = 0f;
	public float brake_temp_right_out = 0f;
	public float hyd_a_qty = 0f;
	public float hyd_b_qty = 0f;
	public int vhf_nav_source = 0;
	public int hyd_a_pressure = 0;
	public int hyd_b_pressure = 0;
	public int flt_alt = 0;
	public int land_alt = 0;
	public float left_elevator_deflection = 0f;
	public float right_elevator_deflection = 0f;
	public float left_ail_deflection = 0f;
	public float right_ail_deflection = 0f;
	public float rudder_deflection = 0f;
	public float left_splr_deflection = 0f;
	public float right_splr_deflection = 0f;
	public float stab_trim = 0f;
	public float rudder_trim = 0f;
	public float flaps_l_deflection = 0f;
	public float flaps_r_deflection = 0f;
	public int sim_paused = 0;
	public int rel_gls = 0;
	public int lowerdu_page = 0;
	public int lowerdu_page2 = 0;
	public int windsheer = 0;
	public int pullup = 0;
	public float thr_lvr1 = 0.0f;
	public float thr_lvr2 = 0.0f;
	public String xraas_msg = null;
	public int xraas_color = 0;
	private float reverser1 = 0.0f;
	private float reverser2 = 0.0f;
	public boolean reverser1_moved = false;
	public boolean reverser2_moved = false;
	public boolean reverser1_deployed = false;
	public boolean reverser2_deployed = false;
	
		
	public Systems(ExtPlaneInterface iface) {
		super(iface);
		
		systems = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				switch(object.getName()) {
				case BRAKE_TEMP_LEFT_IN:
					brake_temp_left_in = Float.parseFloat(object.getValue()[0]);
					break;
				case BRAKE_TEMP_LEFT_OUT:
					brake_temp_left_out = Float.parseFloat(object.getValue()[0]);
					break;
				case BRAKE_TEMP_RIGHT_IN:
					brake_temp_right_in = Float.parseFloat(object.getValue()[0]);
					break;
				case BRAKE_TEMP_RIGHT_OUT:
					brake_temp_right_out = Float.parseFloat(object.getValue()[0]);
					break;
				case HYD_A_QTY:
					hyd_a_qty = Float.parseFloat(object.getValue()[0]);
					break;
				case HYD_B_QTY:
					hyd_b_qty = Float.parseFloat(object.getValue()[0]);
					break;
				case VHF_NAV_SOURCE:
					vhf_nav_source = Integer.parseInt(object.getValue()[0]);
					break;
				case HYD_A_PRESSURE:
					hyd_a_pressure = Integer.parseInt(object.getValue()[0]);
					break;
				case HYD_B_PRESSURE:
					hyd_b_pressure = Integer.parseInt(object.getValue()[0]);
					break;
				case FLT_ALT:
					flt_alt = Integer.parseInt(object.getValue()[0]);
					break;
				case LAND_ALT:
					land_alt = Integer.parseInt(object.getValue()[0]);
					break;
				case LEFT_ELEVATOR_DEFLECTION:
					left_elevator_deflection = Float.parseFloat(object.getValue()[0]);
					break;
				case RIGHT_ELEVATOR_DEFLECTION:
					right_elevator_deflection = Float.parseFloat(object.getValue()[0]);
					break;
				case LEFT_AIL_DEFLECTION:
					left_ail_deflection = Float.parseFloat(object.getValue()[0]);
					break;
				case RIGHT_AIL_DEFLECTION:
					right_ail_deflection = Float.parseFloat(object.getValue()[0]);
					break;
				case RUDDER_DEFLECTION:
					rudder_deflection = Float.parseFloat(object.getValue()[0]);
					break;
				case SPLR_DEFLECTION:
					left_splr_deflection = Float.parseFloat(object.getValue()[0]);
					right_splr_deflection = Float.parseFloat(object.getValue()[1]);
					break;
				case STAB_TRIM:
					stab_trim = Float.parseFloat(object.getValue()[0]);
					break;
				case RUDDER_TRIM:
					rudder_trim = Float.parseFloat(object.getValue()[0]);
					break;
				case FLAPS_L_DEFLECTION:
					flaps_l_deflection = Float.parseFloat(object.getValue()[0]);
					break;
				case FLAPS_R_DEFLECTION:
					flaps_r_deflection = Float.parseFloat(object.getValue()[0]);
					break;
				case SIM_PAUSED:
					sim_paused = Integer.parseInt(object.getValue()[0]);
					break;
				case REL_GLS:
					rel_gls = Integer.parseInt(object.getValue()[0]);
					break;
				case LOWERDU_PAGE:
					lowerdu_page = Integer.parseInt(object.getValue()[0]);
					break;
				case LOWERDU_PAGE2:
					lowerdu_page2 = Integer.parseInt(object.getValue()[0]);
					break;
				case WINDSHEER:
					windsheer = Integer.parseInt(object.getValue()[0]);
					break;
				case PULLUP:
					pullup = Integer.parseInt(object.getValue()[0]);
					break;
				case XRAAS_MESSAGE:
					decode_xraas_msg(Integer.parseInt(object.getValue()[0]));
					break;
				case THR_LVR1:
					thr_lvr1 = Float.parseFloat(object.getValue()[0]);
					break;
				case THR_LVR2:
					thr_lvr2 = Float.parseFloat(object.getValue()[0]);
					break;
				case REVERSER_DEPLOY:
					reverser1 = Float.parseFloat(object.getValue()[0]);
					if(reverser1 > 0.01f) {
						if(reverser1 > 0.995f) {
							reverser1_moved = false;
							reverser1_deployed = true;
						}else {
							reverser1_moved = true;
							reverser1_deployed = false;
						}
					}else {
						reverser1_moved = false;
						reverser1_deployed = false;
					}
					reverser2 = Float.parseFloat(object.getValue()[1]);
					if(reverser2 > 0.01f) {
						if(reverser2 > 0.995f) {
							reverser2_moved = false;
							reverser2_deployed = true;
						}else {
							reverser2_moved = true;
							reverser2_deployed = false;
						}
					}else {
						reverser2_moved = false;
						reverser2_deployed = false;
					}
					break;
				}
			}
		};
	}
	
	private void decode_xraas_msg(int dr_value) {
		int msg_type = dr_value & 0x3f;
		xraas_color = (dr_value >> 6) & 0x3;
		
		switch(msg_type) {
		case 1:
			xraas_msg = "FLAPS";
			break;
		case 2:
			xraas_msg = "TOO HIGH";
			break;
		case 3:
			xraas_msg = "TOO FAST";
			break;
		case 4:
			xraas_msg = "UNSTABLE";
			break;
		case 5:
			xraas_msg = "TAXIWAY";
			break;
		case 6:
			xraas_msg = "SHORT RUNWAY";
			break;
		case 7:
			xraas_msg = "ALTM SETTING";
			break;
		case 8:
		case 9:
			String msg;
			if(msg_type == 8) {
				 msg = "APP";
			}else {
				 msg = "ON";
			}
			int rwy_ID = (dr_value >> 8) & 0x3f;
			int rwy_suffix = (dr_value >> 14) & 0x3;
			int rwy_len = (dr_value >> 16) & 0xff;
			
			if(rwy_ID == 0) {
				xraas_msg = msg + " TAXIWAY";
			}else if(rwy_ID == 37) {
				xraas_msg = msg + " RWYS";
			}else {
				if(rwy_len == 0) {
					xraas_msg = msg + " " + rwy_ID + " " + decode_rwy_suffix(rwy_suffix);
				}else {
					xraas_msg = msg + " " + rwy_ID + " " + decode_rwy_suffix(rwy_suffix) + "" + rwy_len;
				}
			}
			break;
		case 10:
			xraas_msg = "LONG LANDING";
			break;
		case 11:
			xraas_msg = "DEEP LANDING";
			break;
		default:
			xraas_msg = null;		
		}
	}
	private String decode_rwy_suffix(int val) {
		if(val == 1) {
			return "R";
		}else if(val == 2) {
			return "L";
		}else if(val == 3) {
			return "C";	
		}else {
			return "";
		}
	}

	@Override
	public void subscribeDrefs() {

	
	}

	@Override
	public void includeDrefs() {
		
		iface.includeDataRef(BRAKE_TEMP_LEFT_IN);
		iface.includeDataRef(BRAKE_TEMP_LEFT_OUT);
		iface.includeDataRef(BRAKE_TEMP_RIGHT_IN);
		iface.includeDataRef(BRAKE_TEMP_RIGHT_OUT);
		iface.includeDataRef(THR_LVR1, 0.01f);
		iface.includeDataRef(THR_LVR2, 0.01f);
		iface.includeDataRef(HYD_A_QTY);
		iface.includeDataRef(HYD_B_QTY);
		iface.includeDataRef(VHF_NAV_SOURCE);
		iface.includeDataRef(HYD_A_PRESSURE);
		iface.includeDataRef(HYD_B_PRESSURE);
		iface.includeDataRef(FLT_ALT);
		iface.includeDataRef(LAND_ALT);
		iface.includeDataRef(LEFT_ELEVATOR_DEFLECTION, 0.1f);
		iface.includeDataRef(RIGHT_ELEVATOR_DEFLECTION, 0.1f);
		iface.includeDataRef(LEFT_AIL_DEFLECTION, 0.1f);
		iface.includeDataRef(RIGHT_AIL_DEFLECTION, 0.1f);
		iface.includeDataRef(RUDDER_DEFLECTION, 0.1f);
		iface.includeDataRef(SPLR_DEFLECTION, 0.1f);
		iface.includeDataRef(STAB_TRIM);
		iface.includeDataRef(RUDDER_TRIM);
		iface.includeDataRef(FLAPS_L_DEFLECTION, 0.001f);
		iface.includeDataRef(FLAPS_R_DEFLECTION, 0.001f);
		iface.includeDataRef(SIM_PAUSED);
		iface.includeDataRef(REL_GLS);
		iface.includeDataRef(LOWERDU_PAGE);
		iface.includeDataRef(LOWERDU_PAGE2);
		iface.includeDataRef(WINDSHEER);
		iface.includeDataRef(PULLUP);
		iface.includeDataRef(XRAAS_MESSAGE);
		iface.includeDataRef(REVERSER_DEPLOY, 0.001f);
		
		iface.observeDataRef(BRAKE_TEMP_LEFT_IN, systems);
		iface.observeDataRef(BRAKE_TEMP_LEFT_OUT, systems);
		iface.observeDataRef(BRAKE_TEMP_RIGHT_IN, systems);
		iface.observeDataRef(BRAKE_TEMP_RIGHT_OUT, systems);
		iface.observeDataRef(THR_LVR1, systems);
		iface.observeDataRef(THR_LVR2, systems);
		iface.observeDataRef(HYD_A_QTY, systems);
		iface.observeDataRef(HYD_B_QTY, systems);
		iface.observeDataRef(VHF_NAV_SOURCE, systems);
		iface.observeDataRef(HYD_A_PRESSURE, systems);
		iface.observeDataRef(HYD_B_PRESSURE, systems);
		iface.observeDataRef(FLT_ALT, systems);
		iface.observeDataRef(LAND_ALT, systems);
		iface.observeDataRef(LEFT_ELEVATOR_DEFLECTION, systems);
		iface.observeDataRef(RIGHT_ELEVATOR_DEFLECTION, systems);
		iface.observeDataRef(LEFT_AIL_DEFLECTION, systems);
		iface.observeDataRef(RIGHT_AIL_DEFLECTION, systems);
		iface.observeDataRef(RUDDER_DEFLECTION, systems);
		iface.observeDataRef(SPLR_DEFLECTION, systems);
		iface.observeDataRef(STAB_TRIM, systems);
		iface.observeDataRef(RUDDER_TRIM, systems);
		iface.observeDataRef(FLAPS_L_DEFLECTION, systems);
		iface.observeDataRef(FLAPS_R_DEFLECTION, systems);
		iface.observeDataRef(SIM_PAUSED, systems);
		iface.observeDataRef(REL_GLS, systems);
		iface.observeDataRef(LOWERDU_PAGE, systems);
		iface.observeDataRef(LOWERDU_PAGE2, systems);
		iface.observeDataRef(WINDSHEER, systems);
		iface.observeDataRef(PULLUP, systems);
		iface.observeDataRef(XRAAS_MESSAGE, systems);
		iface.observeDataRef(REVERSER_DEPLOY, systems);	
		
	}

	@Override
	public void excludeDrefs() {
		
		iface.excludeDataRef(BRAKE_TEMP_LEFT_IN);
		iface.excludeDataRef(BRAKE_TEMP_LEFT_OUT);
		iface.excludeDataRef(BRAKE_TEMP_RIGHT_IN);
		iface.excludeDataRef(BRAKE_TEMP_RIGHT_OUT);
		iface.excludeDataRef(THR_LVR1);
		iface.excludeDataRef(THR_LVR2);
		iface.excludeDataRef(HYD_A_QTY);
		iface.excludeDataRef(HYD_B_QTY);
		iface.excludeDataRef(VHF_NAV_SOURCE);
		iface.excludeDataRef(HYD_A_PRESSURE);
		iface.excludeDataRef(HYD_B_PRESSURE);
		iface.excludeDataRef(FLT_ALT);
		iface.excludeDataRef(LAND_ALT);
		iface.excludeDataRef(LEFT_ELEVATOR_DEFLECTION);
		iface.excludeDataRef(RIGHT_ELEVATOR_DEFLECTION);
		iface.excludeDataRef(LEFT_AIL_DEFLECTION);
		iface.excludeDataRef(RIGHT_AIL_DEFLECTION);
		iface.excludeDataRef(RUDDER_DEFLECTION);
		iface.excludeDataRef(SPLR_DEFLECTION);
		iface.excludeDataRef(STAB_TRIM);
		iface.excludeDataRef(RUDDER_TRIM);
		iface.excludeDataRef(FLAPS_L_DEFLECTION);
		iface.excludeDataRef(FLAPS_R_DEFLECTION);
		iface.excludeDataRef(SIM_PAUSED);
		iface.excludeDataRef(REL_GLS);
		iface.excludeDataRef(LOWERDU_PAGE);
		iface.excludeDataRef(LOWERDU_PAGE2);
		iface.excludeDataRef(WINDSHEER);
		iface.excludeDataRef(PULLUP);
		iface.excludeDataRef(XRAAS_MESSAGE);
		iface.excludeDataRef(REVERSER_DEPLOY);
		
		iface.unObserveDataRef(BRAKE_TEMP_LEFT_IN, systems);
		iface.unObserveDataRef(BRAKE_TEMP_LEFT_OUT, systems);
		iface.unObserveDataRef(BRAKE_TEMP_RIGHT_IN, systems);
		iface.unObserveDataRef(BRAKE_TEMP_RIGHT_OUT, systems);
		iface.unObserveDataRef(THR_LVR1, systems);
		iface.unObserveDataRef(THR_LVR2, systems);
		iface.unObserveDataRef(HYD_A_QTY, systems);
		iface.unObserveDataRef(HYD_B_QTY, systems);
		iface.unObserveDataRef(VHF_NAV_SOURCE, systems);
		iface.unObserveDataRef(HYD_A_PRESSURE, systems);
		iface.unObserveDataRef(HYD_B_PRESSURE, systems);
		iface.unObserveDataRef(FLT_ALT, systems);
		iface.unObserveDataRef(LAND_ALT, systems);
		iface.unObserveDataRef(LEFT_ELEVATOR_DEFLECTION, systems);
		iface.unObserveDataRef(RIGHT_ELEVATOR_DEFLECTION, systems);
		iface.unObserveDataRef(LEFT_AIL_DEFLECTION, systems);
		iface.unObserveDataRef(RIGHT_AIL_DEFLECTION, systems);
		iface.unObserveDataRef(RUDDER_DEFLECTION, systems);
		iface.unObserveDataRef(SPLR_DEFLECTION, systems);
		iface.unObserveDataRef(STAB_TRIM, systems);
		iface.unObserveDataRef(RUDDER_TRIM, systems);
		iface.unObserveDataRef(FLAPS_L_DEFLECTION, systems);
		iface.unObserveDataRef(FLAPS_R_DEFLECTION, systems);
		iface.unObserveDataRef(SIM_PAUSED, systems);
		iface.unObserveDataRef(REL_GLS, systems);
		iface.unObserveDataRef(LOWERDU_PAGE, systems);
		iface.unObserveDataRef(LOWERDU_PAGE2, systems);
		iface.unObserveDataRef(WINDSHEER, systems);
		iface.unObserveDataRef(PULLUP, systems);
		iface.unObserveDataRef(XRAAS_MESSAGE, systems);
		iface.unObserveDataRef(REVERSER_DEPLOY, systems);	
		
	}
}
