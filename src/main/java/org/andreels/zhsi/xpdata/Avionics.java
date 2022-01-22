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

public class Avionics extends BaseDataClass {
	
	Observer<DataRef> avionics;

	private final String POWER = "laminar/B738/electric/batbus_status"; 
	private final String AC_STANDBY_STATUS = "laminar/B738/electric/ac_stdbus_status"; // Captain DUs and ENGINE shown
	private final String DC_STANDBY_STATUS = "laminar/B738/electric/dc_stdbus_status"; // FO DUs and MFD shown
	private final String[] HDG_BUG_LINE = {"laminar/B738/nd/hdg_bug_line", "laminar/B738/nd/hdg_bug_line_fo"};
	private final String[] MINIMUMS = {"laminar/B738/pfd/dh_pilot", "laminar/B738/pfd/dh_copilot"};
	private final String[] GREEN_ARC = {"laminar/B738/EFIS/green_arc", "laminar/B738/EFIS/green_arc_fo"};
	private final String[] GREEN_ARC_SHOW = {"laminar/B738/EFIS/green_arc_show", "laminar/B738/EFIS/green_arc_fo_show"};
	private final String[] FMC_SOURCE = {"laminar/B738/nd/capt/fmc_source","laminar/B738/nd/fo/fmc_source"};
	private final String VSPEED = "laminar/B738/pfd/vspeed"; //vspeed value
	private final String[] VSPEED_DIGIT_SHOW = {"laminar/B738/pfd/vspeed_digit_show", "laminar/B738/pfd/vspeed_digit_show_fo"}; //show vspeed digits boolean
	private final String[] V1_OFF_SHOW = {"laminar/B738/pfd/v1_off_show", "laminar/B738/pfd/v1_off_show_fo"}; //show v1 off boolean
	private final String[] VSPEED_VREF_SHOW = {"laminar/B738/pfd/vspeed_vref_show", "laminar/B738/pfd/vspeed_vref_show_fo"}; //show word VREF boolean
	private final String VSPEED_MODE = "laminar/B738/pfd/vspeed_mode"; //0 = none, 1 = v1, 2 = vr, 3 = ---, 4 = invalid entry
	private final String NO_VSPD = "laminar/B738/pfd/no_vspd"; 
	private final String ND_VERT_PATH = "laminar/B738/pfd/nd_vert_path"; //shows ND vert path
	private final String[] PFD_TRK_PATH = {"laminar/B738/pfd/pfd_trk_path", "laminar/B738/pfd/pfd_trk_path_fo"};
	private final String[] PFD_VERT_PATH = {"laminar/B738/pfd/pfd_vert_path", "laminar/B738/pfd/pfd_vert_path_fo"};
	private final String[] PFD_VERT_PATH2 = {"laminar/B738/pfd/pfd_vert_path2", "laminar/B738/pfd/pfd_vert_path2_fo"};
	private final String[] PFD_GLS_LOC_GHOST = {"laminar/B738/pfd/pfd_gls_loc_ghost", "laminar/B738/pfd/pfd_gls_loc_ghost_fo"};
	private final String[] PFD_GLS_GS_GHOST = {"laminar/B738/pfd/pfd_gls_gs_ghost", "laminar/B738/pfd/pfd_gls_gs_ghost_fo"};
	private final String[] PFD_FAC_GHOST = {"laminar/B738/pfd/pfd_fac_ghost", "laminar/B738/pfd/pfd_fac_ghost_fo"};
	private final String[] PFD_GP_GHOST = {"laminar/B738/pfd/pfd_gp_ghost", "laminar/B738/pfd/pfd_gp_ghost_fo"};
	private final String[] PFD_GS_GHOST = {"laminar/B738/pfd/pfd_gs_ghost", "laminar/B738/pfd/pfd_gs_ghost_fo"};
	private final String[] PFD_LOC_GHOST = {"laminar/B738/pfd/pfd_loc_ghost", "laminar/B738/pfd/pfd_loc_ghost_fo"};
	private final String[] PFD_LOC_EXP = {"laminar/B738/PFD/loc_exp", "laminar/B738/PFD/loc_exp_fo"};
	private final String[] PFD_GLS_EXP = {"laminar/B738/PFD/gls_exp", "laminar/B738/PFD/gls_exp_fo"};
	private final String[] MMR_ACT_MODE = {"laminar/B738/mmr/cpt/act_mode", "laminar/B738/mmr/fo/act_mode"};
	private final String VNAV_ERR = "laminar/B738/fms/vnav_err_pfd"; //value of vert path
	private final String RWY_ALTITUDE = "laminar/B738/pfd/rwy_altitude";
	private final String RWY_SHOW = "laminar/B738/pfd/rwy_show";
	
	//clocks
	private final String TIME_DAY = "sim/cockpit2/clock_timer/current_day";
	private final String TIME_MONTH = "sim/cockpit2/clock_timer/current_month";
	private final String TIME_ZULU_HRS = "sim/cockpit2/clock_timer/zulu_time_hours";
	private final String TIME_ZULU_MIN = "sim/cockpit2/clock_timer/zulu_time_minutes";
	private final String TIME_LOCAL_HRS = "sim/cockpit2/clock_timer/local_time_hours";
	private final String TIME_LOCAL_MIN = "sim/cockpit2/clock_timer/local_time_minutes";
	
	//
	private final String CPT_CHRONO_MODE = "laminar/B738/clock/captain/chrono_mode";
	private final String CPT_CHRONO_NEEDLE = "laminar/B738/clock/captain/chrono_seconds_needle";
	private final String CPT_CHRONO_MINUTE = "laminar/B738/clock/captain/chrono_minutes";
	private final String CPT_CHRONO_DISPLAY_MODE = "laminar/B738/clock/clock_display_mode_capt";
	private final String CPT_CHRONO_ET_MODE = "laminar/B738/clock/captain/et_mode";
	private final String CPT_CHRONO_ET_HRS = "laminar/B738/clock/captain/et_hours";
	private final String CPT_CHRONO_ET_MIN = "laminar/B738/clock/captain/et_minutes";
	//
	private final String FO_CHRONO_MODE = "laminar/B738/clock/fo/chrono_mode";
	private final String FO_CHRONO_NEEDLE = "laminar/B738/clock/fo/chrono_seconds_needle";
	private final String FO_CHRONO_MINUTE = "laminar/B738/clock/fo/chrono_minutes";
	private final String FO_CHRONO_DISPLAY_MODE = "laminar/B738/clock/clock_display_mode_fo";
	private final String FO_CHRONO_ET_MODE = "laminar/B738/clock/fo/et_mode";
	private final String FO_CHRONO_ET_HRS = "laminar/B738/clock/fo/et_hours";
	private final String FO_CHRONO_ET_MIN = "laminar/B738/clock/fo/et_minutes";
	
	//
	public int power = 0;
	public int ac_standby = 0;
	public int dc_standby = 0;
	public int[] hdg_bug_line = new int[2];
	public float[] minimums = new float[2];
	public float[] green_arc = new float[2];
	public int[] green_arc_show = new int[2];
	public int[] fmc_source = new int[2];
	public float vspeed = 0.0f;
	public int[] vspeed_digit_show = new int[2];
	public int[] v1_off_show = new int[2];
	public int[] vspeed_vref_show = new int[2];
	public int vspeed_mode = 0;
	public int no_vspd = 0;
	public int nd_vert_path = 0;
	public int[] pfd_trk_path = new int[2];
	public int[] pfd_vert_path = new int[2];
	public int[] pfd_vert_path2 = new int[2];
	public int[] pfd_gls_loc_ghost = new int[2];
	public int[] pfd_gls_gs_ghost = new int[2];
	public int[] pfd_fac_ghost = new int[2];
	public int[] pfd_gp_ghost = new int[2];
	public int[] pfd_gs_ghost = new int[2];
	public int[] pfd_loc_ghost = new int[2];
	public int[] pfd_loc_exp = new int[2];
	public int[] pfd_gls_exp = new int[2];
	public int[] mmr_act_mode = new int[2];
	public float vnav_err = 0.0f;
	public float rwy_altitude = 0.0f;
	public float rwy_show = 0.0f;
	public int time_zulu_hrs = 0;
	public int time_zulu_min = 0;
	public int time_local_hrs = 0;
	public int time_local_min = 0;
	public int time_day = 0;
	public int time_month = 0;
	public int cpt_chrono_mode = 0;
	public float cpt_chrono_needle = 0;
	public float cpt_chrono_minute = 0.0f;
	public float cpt_chrono_display_mode = 0f;
	public float cpt_chrono_et_mode = 0.0f;
	public float cpt_chrono_et_hrs = 0.0f;
	public float cpt_chrono_et_min = 0.0f;
	public int fo_chrono_mode = 0;
	public float fo_chrono_needle = 0;
	public float fo_chrono_minute = 0.0f;
	public float fo_chrono_display_mode = 0f;
	public float fo_chrono_et_mode = 0.0f;
	public float fo_chrono_et_hrs = 0.0f;
	public float fo_chrono_et_min = 0.0f;

	public Avionics(ExtPlaneInterface iface) {		
		super(iface);
		
		drefs.add(POWER);
		drefs.add(AC_STANDBY_STATUS);
		drefs.add(DC_STANDBY_STATUS);
		drefs.add(VSPEED);
		drefs.add(VSPEED_MODE);
		drefs.add(NO_VSPD);
		drefs.add(ND_VERT_PATH);
		drefs.add(RWY_ALTITUDE);
		drefs.add(RWY_SHOW);
		drefs.add(TIME_DAY);
		drefs.add(TIME_MONTH);
		drefs.add(TIME_ZULU_HRS);
		drefs.add(TIME_ZULU_MIN);
		drefs.add(TIME_LOCAL_HRS);
		drefs.add(TIME_LOCAL_MIN);
		drefs.add(CPT_CHRONO_MODE);
		drefs.add(CPT_CHRONO_MINUTE);
		drefs.add(CPT_CHRONO_DISPLAY_MODE);
		drefs.add(CPT_CHRONO_ET_MODE);
		drefs.add(CPT_CHRONO_ET_HRS);
		drefs.add(CPT_CHRONO_ET_MIN);
		drefs.add(FO_CHRONO_MODE);
		drefs.add(FO_CHRONO_MINUTE);
		drefs.add(FO_CHRONO_DISPLAY_MODE);
		drefs.add(FO_CHRONO_ET_MODE);
		drefs.add(FO_CHRONO_ET_HRS);
		drefs.add(FO_CHRONO_ET_MIN);

		avionics = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				switch(object.getName()) {
				
				case POWER: power = Integer.parseInt(object.getValue()[0]);
					break;
				case AC_STANDBY_STATUS: ac_standby = Integer.parseInt(object.getValue()[0]);
					break;
				case DC_STANDBY_STATUS: dc_standby = Integer.parseInt(object.getValue()[0]);
					break;
				case VSPEED: vspeed = Float.parseFloat(object.getValue()[0]);
					break;
				case VSPEED_MODE: vspeed_mode = Integer.parseInt(object.getValue()[0]);
					break;
				case NO_VSPD: no_vspd = Integer.parseInt(object.getValue()[0]);
					break;
				case ND_VERT_PATH: nd_vert_path = Integer.parseInt(object.getValue()[0]);
					break;
				case VNAV_ERR: vnav_err = Float.parseFloat(object.getValue()[0]);
					break;
				case RWY_ALTITUDE: rwy_altitude = Float.parseFloat(object.getValue()[0]);
					break;
				case RWY_SHOW: rwy_show = Float.parseFloat(object.getValue()[0]);
					break;
				case TIME_ZULU_HRS: time_zulu_hrs = Integer.parseInt(object.getValue()[0]);
					break;
				case TIME_ZULU_MIN: time_zulu_min = Integer.parseInt(object.getValue()[0]);
					break;
				case TIME_LOCAL_HRS: time_local_hrs = Integer.parseInt(object.getValue()[0]);
					break;
				case TIME_LOCAL_MIN: time_local_min = Integer.parseInt(object.getValue()[0]);
					break;
				case TIME_DAY: time_day = Integer.parseInt(object.getValue()[0]);
					break;
				case TIME_MONTH: time_month = Integer.parseInt(object.getValue()[0]);
					break;
				case CPT_CHRONO_MINUTE: cpt_chrono_minute = Float.parseFloat(object.getValue()[0]);
					break;
				case CPT_CHRONO_DISPLAY_MODE: cpt_chrono_display_mode = Float.parseFloat(object.getValue()[0]);
					break;
				case CPT_CHRONO_ET_MODE: cpt_chrono_et_mode = Float.parseFloat(object.getValue()[0]);
					break;
				case CPT_CHRONO_ET_HRS: cpt_chrono_et_hrs = Float.parseFloat(object.getValue()[0]);
					break;
				case CPT_CHRONO_ET_MIN: cpt_chrono_et_min = Float.parseFloat(object.getValue()[0]);
					break;
				case CPT_CHRONO_NEEDLE: cpt_chrono_needle = Float.parseFloat(object.getValue()[0]);
					break;
				case CPT_CHRONO_MODE: cpt_chrono_mode = Integer.parseInt(object.getValue()[0]);
					break;
				case FO_CHRONO_MINUTE: fo_chrono_minute = Float.parseFloat(object.getValue()[0]);
					break;
				case FO_CHRONO_DISPLAY_MODE: fo_chrono_display_mode = Float.parseFloat(object.getValue()[0]);
					break;
				case FO_CHRONO_ET_MODE: fo_chrono_et_mode = Float.parseFloat(object.getValue()[0]);
					break;
				case FO_CHRONO_ET_HRS: fo_chrono_et_hrs = Float.parseFloat(object.getValue()[0]);
					break;
				case FO_CHRONO_ET_MIN: fo_chrono_et_min = Float.parseFloat(object.getValue()[0]);
					break;
				case FO_CHRONO_NEEDLE: fo_chrono_needle = Float.parseFloat(object.getValue()[0]);
					break;
				case FO_CHRONO_MODE: fo_chrono_mode = Integer.parseInt(object.getValue()[0]);
					break;
				}
				
				
				for (int i = 0; i < 2; i++) {
					if(object.getName().equals(HDG_BUG_LINE[i])) {
						hdg_bug_line[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(MINIMUMS[i])) {
						minimums[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(GREEN_ARC[i])) {
						green_arc[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(GREEN_ARC_SHOW[i])) {
						green_arc_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(FMC_SOURCE[i])) {
						fmc_source[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(VSPEED_DIGIT_SHOW[i])) {
						vspeed_digit_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(V1_OFF_SHOW[i])) {
						v1_off_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(VSPEED_VREF_SHOW[i])) {
						vspeed_vref_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_TRK_PATH[i])) {
						pfd_trk_path[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_VERT_PATH[i])) {
						pfd_vert_path[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_VERT_PATH2[i])) {
						pfd_vert_path2[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_GLS_LOC_GHOST[i])) {
						pfd_gls_loc_ghost[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_GLS_GS_GHOST[i])) {
						pfd_gls_gs_ghost[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_FAC_GHOST[i])) {
						pfd_fac_ghost[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_GP_GHOST[i])) {
						pfd_gp_ghost[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_GS_GHOST[i])) {
						pfd_gs_ghost[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_LOC_GHOST[i])) {
						pfd_loc_ghost[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_LOC_EXP[i])) {
						pfd_loc_exp[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_GLS_EXP[i])) {
						pfd_gls_exp[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(MMR_ACT_MODE[i])) {
						mmr_act_mode[i] = Integer.parseInt(object.getValue()[0]);
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
		
		for(String dref : drefs) {
			iface.includeDataRef(dref);
			iface.observeDataRef(dref, avionics);
		}
		
		iface.includeDataRef(VNAV_ERR, 0.1f);
		iface.includeDataRef(CPT_CHRONO_NEEDLE, 0.2f);
		iface.includeDataRef(FO_CHRONO_NEEDLE, 0.2f);

		//

		iface.observeDataRef(VNAV_ERR, avionics);
		iface.observeDataRef(CPT_CHRONO_NEEDLE, avionics);
		iface.observeDataRef(FO_CHRONO_NEEDLE, avionics);

		
		for (int i = 0; i < 2; i++) {
			iface.includeDataRef(HDG_BUG_LINE[i]);
			iface.includeDataRef(MINIMUMS[i]);
			iface.includeDataRef(GREEN_ARC[i]);
			iface.includeDataRef(GREEN_ARC_SHOW[i]);
			iface.includeDataRef(FMC_SOURCE[i]);
			iface.includeDataRef(VSPEED_DIGIT_SHOW[i]);
			iface.includeDataRef(V1_OFF_SHOW[i]);
			iface.includeDataRef(VSPEED_VREF_SHOW[i]);
			iface.includeDataRef(PFD_TRK_PATH[i]);
			iface.includeDataRef(PFD_VERT_PATH[i]);
			iface.includeDataRef(PFD_VERT_PATH2[i]);
			iface.includeDataRef(PFD_GLS_LOC_GHOST[i]);
			iface.includeDataRef(PFD_GLS_GS_GHOST[i]);
			iface.includeDataRef(PFD_FAC_GHOST[i]);
			iface.includeDataRef(PFD_GP_GHOST[i]);
			iface.includeDataRef(PFD_GS_GHOST[i]);
			iface.includeDataRef(PFD_LOC_GHOST[i]);
			iface.includeDataRef(PFD_LOC_EXP[i]);
			iface.includeDataRef(PFD_GLS_EXP[i]);
			iface.includeDataRef(MMR_ACT_MODE[i]);
			iface.observeDataRef(HDG_BUG_LINE[i], avionics);
			iface.observeDataRef(MINIMUMS[i], avionics);
			iface.observeDataRef(GREEN_ARC[i], avionics);
			iface.observeDataRef(GREEN_ARC_SHOW[i], avionics);
			iface.observeDataRef(FMC_SOURCE[i], avionics);
			iface.observeDataRef(VSPEED_DIGIT_SHOW[i], avionics);
			iface.observeDataRef(V1_OFF_SHOW[i], avionics);
			iface.observeDataRef(VSPEED_VREF_SHOW[i], avionics);
			iface.observeDataRef(PFD_TRK_PATH[i], avionics);
			iface.observeDataRef(PFD_VERT_PATH[i], avionics);
			iface.observeDataRef(PFD_VERT_PATH2[i], avionics);
			iface.observeDataRef(PFD_GLS_LOC_GHOST[i], avionics);
			iface.observeDataRef(PFD_GLS_GS_GHOST[i], avionics);
			iface.observeDataRef(PFD_FAC_GHOST[i], avionics);
			iface.observeDataRef(PFD_GP_GHOST[i], avionics);
			iface.observeDataRef(PFD_GS_GHOST[i], avionics);
			iface.observeDataRef(PFD_LOC_GHOST[i], avionics);
			iface.observeDataRef(PFD_LOC_EXP[i], avionics);
			iface.observeDataRef(PFD_GLS_EXP[i], avionics);
			iface.observeDataRef(MMR_ACT_MODE[i], avionics);
		}
		
	}

	@Override
	public void excludeDrefs() {
		
		for(String dref : drefs) {
			iface.excludeDataRef(dref);
			iface.unObserveDataRef(dref, avionics);
		}	

		iface.excludeDataRef(VNAV_ERR);
		iface.excludeDataRef(CPT_CHRONO_NEEDLE);
		iface.excludeDataRef(FO_CHRONO_NEEDLE);

		//

		iface.unObserveDataRef(VNAV_ERR, avionics);
		iface.unObserveDataRef(CPT_CHRONO_NEEDLE, avionics);
		iface.unObserveDataRef(FO_CHRONO_NEEDLE, avionics);
		
		for (int i = 0; i < 2; i++) {
			iface.excludeDataRef(HDG_BUG_LINE[i]);
			iface.excludeDataRef(MINIMUMS[i]);
			iface.excludeDataRef(GREEN_ARC[i]);
			iface.excludeDataRef(GREEN_ARC_SHOW[i]);
			iface.excludeDataRef(FMC_SOURCE[i]);
			iface.excludeDataRef(VSPEED_DIGIT_SHOW[i]);
			iface.excludeDataRef(V1_OFF_SHOW[i]);
			iface.excludeDataRef(VSPEED_VREF_SHOW[i]);
			iface.excludeDataRef(PFD_TRK_PATH[i]);
			iface.excludeDataRef(PFD_VERT_PATH[i]);
			iface.excludeDataRef(PFD_VERT_PATH2[i]);
			iface.excludeDataRef(PFD_GLS_LOC_GHOST[i]);
			iface.excludeDataRef(PFD_GLS_GS_GHOST[i]);
			iface.excludeDataRef(PFD_FAC_GHOST[i]);
			iface.excludeDataRef(PFD_GP_GHOST[i]);
			iface.excludeDataRef(PFD_GS_GHOST[i]);
			iface.excludeDataRef(PFD_LOC_GHOST[i]);
			iface.excludeDataRef(PFD_LOC_EXP[i]);
			iface.excludeDataRef(PFD_GLS_EXP[i]);
			iface.excludeDataRef(MMR_ACT_MODE[i]);
			iface.unObserveDataRef(HDG_BUG_LINE[i], avionics);
			iface.unObserveDataRef(MINIMUMS[i], avionics);
			iface.unObserveDataRef(GREEN_ARC[i], avionics);
			iface.unObserveDataRef(GREEN_ARC_SHOW[i], avionics);
			iface.unObserveDataRef(FMC_SOURCE[i], avionics);
			iface.unObserveDataRef(VSPEED_DIGIT_SHOW[i], avionics);
			iface.unObserveDataRef(V1_OFF_SHOW[i], avionics);
			iface.unObserveDataRef(VSPEED_VREF_SHOW[i], avionics);
			iface.unObserveDataRef(PFD_TRK_PATH[i], avionics);
			iface.unObserveDataRef(PFD_VERT_PATH[i], avionics);
			iface.unObserveDataRef(PFD_VERT_PATH2[i], avionics);
			iface.unObserveDataRef(PFD_GLS_LOC_GHOST[i], avionics);
			iface.unObserveDataRef(PFD_GLS_GS_GHOST[i], avionics);
			iface.unObserveDataRef(PFD_FAC_GHOST[i], avionics);
			iface.unObserveDataRef(PFD_GP_GHOST[i], avionics);
			iface.unObserveDataRef(PFD_GS_GHOST[i], avionics);
			iface.unObserveDataRef(PFD_LOC_GHOST[i], avionics);
			iface.unObserveDataRef(PFD_LOC_EXP[i], avionics);
			iface.unObserveDataRef(PFD_GLS_EXP[i], avionics);
			iface.unObserveDataRef(MMR_ACT_MODE[i], avionics);
		}
		
	}

}