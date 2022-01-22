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

import java.util.Base64;

import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRef;
import org.andreels.zhsi.ExtPlaneInterface.util.Observer;

public class FMS extends BaseDataClass {
	
	Observer<DataRef> fms_data;
	Observer<DataRef> active_route;
	Observer<DataRef> modified_route;
	
	// fms_data
	private final String ANP = "laminar/B738/fms/anp";
	private final String VANP = "laminar/B738/fms/vanp";
	private final String RNP = "laminar/B738/fms/rnp";
	private final String VRNP = "laminar/B738/fms/vrnp";
	private final String XTRACK_ND = "laminar/B738/fms/xtrack_nd";
	private final String TRANS_ALT = "laminar/B738/FMS/fmc_trans_alt";
	private final String TRANS_LVL = "laminar/B738/FMS/fmc_trans_lvl";
	private final String[] FPLN_ACTIVE = {"laminar/B738/fms/fpln_acive","laminar/B738/fms/fpln_acive_fo"};
	private final String[] PFD_GP_PATH = {"laminar/B738/fms/pfd_gp_path","laminar/B738/fms/pfd_gp_path_fo"};
	private final String[] PFD_FAC_HORIZONTAL = {"laminar/B738/fms/pfd_fac_horizontal","laminar/B738/fms/pfd_fac_horizontal_fo"};
	private final String[] IAN_INFO = {"laminar/B738/fms/ian_info","laminar/B738/fms/ian_info_fo"};
	private final String[] FAC_EXP = {"laminar/B738/PFD/fac_exp","laminar/B738/PFD/fac_exp_fo"};
	private final String FPLN_NAV_ID = "laminar/B738/fms/fpln_nav_id:string"; //active waypoint name to be displayed top right only when above is true
	private final String FPLN_NAV_ID_ETA = "laminar/B738/fms/id_eta:string"; // eta to active waypoint in zulu format 0648.1
	private final String RW_FAC_ID2 = "laminar/B738/fms/rw_fac_id2:string";
	private final String RW_DIST2 = "laminar/B738/fms/rw_dist2";
	private final String FPLN_NAV_ID_DIST = "laminar/B738/fms/lnav_dist_next"; //distance in NM to next waypoint
	private final String FMS_VREF = "laminar/B738/FMS/vref";
	private final String FMS_VR_SET = "laminar/B738/FMS/vr_set";
	private final String FMS_V1_SET = "laminar/B738/FMS/v1_set";
	private final String FMS_V1 = "laminar/B738/FMS/v1";
	private final String FMS_V2_15 = "laminar/B738/FMS/v2_15";
	private final String FMS_VREF_BUGS = "laminar/B738/FMS/vref_bugs";
	private final String FMS_V1R_BUGS = "laminar/B738/FMS/v1r_bugs";
	private final String LEGS_MOD_ACTIVE = "laminar/B738/fms/legs_mod_active";
	private final String[] LEGS_STEP_CTR_IDX = {"laminar/B738/fms/legs_step_ctr", "laminar/B738/fms/legs_step_ctr_fo"};
	private final String MISSED_APP_WPT_IDX = "laminar/B738/fms/missed_app_wpt_idx";
	private final String[] ILS_ROTATE0 = {"laminar/B738/pfd/ils_rotate0", "laminar/B738/pfd/ils_fo_rotate0"}; // dep runway
	private final String[] ILS_ROTATE0_MOD = {"laminar/B738/pfd/ils_rotate0_mod", "laminar/B738/pfd/ils_fo_rotate0_mod"}; // dep runway
	private final String[] ILS_SHOW0 = {"laminar/B738/pfd/ils_show0", "laminar/B738/pfd/ils_fo_show0"};
	private final String[] ILS_SHOW0_MOD = {"laminar/B738/pfd/ils_show0_mod", "laminar/B738/pfd/ils_fo_show0_mod"};
	private final String[] ILS_ROTATE = {"laminar/B738/pfd/ils_rotate", "laminar/B738/pfd/ils_fo_rotate"}; // dest runway
	private final String[] ILS_ROTATE_MOD = {"laminar/B738/pfd/ils_rotate_mod", "laminar/B738/pfd/ils_fo_rotate_mod"}; // dest runway
	private final String[] ILS_SHOW = {"laminar/B738/pfd/ils_show", "laminar/B738/pfd/ils_fo_show"};
	private final String[] ILS_SHOW_MOD = {"laminar/B738/pfd/ils_show_mod", "laminar/B738/pfd/ils_fo_show_mod"};
	private final String[] NAV_TXT1 = {"laminar/B738/pfd/cpt_nav_txt1", "laminar/B738/pfd/fo_nav_txt1"};
	private final String[] NAV_TXT1a = {"laminar/B738/pfd/cpt_nav_txt1a", "laminar/B738/pfd/fo_nav_txt1a"};
	private final String[] NAV_TXT2 = {"laminar/B738/pfd/cpt_nav_txt2", "laminar/B738/pfd/fo_nav_txt2"};
	private final String ILS_DISABLE = "laminar/B738/FMS/ils_disable";
	private final String VNAV_IDX = "laminar/B738/fms/vnav_idx_seq";
	private final String VNAV_IDX_MOD = "laminar/B738/fms/vnav_idx_mod";
	private final String INTDIR_ACT = "laminar/B738/fms/intdir_act";
	private final String INTDIR_CRS = "laminar/B738/fms/intdir_crs";
	private final String INTDIR_CRS2 = "laminar/B738/fms/intdir_crs2";
	private final String DIR_SEG1_END_ANGLE = "laminar/B738/fms/dir_seg1_end_angle";
	private final String DIR_SEG1_START_ANGLE = "laminar/B738/fms/dir_seg1_start_angle";
	private final String DIR_SEG1_CTR_LAT = "laminar/B738/fms/dir_seg1_ctr_lat";
	private final String DIR_SEG1_CTR_LON = "laminar/B738/fms/dir_seg1_ctr_lon";
	private final String DIR_SEG1_RADIUS = "laminar/B738/fms/dir_seg1_radius";
	private final String DIR_SEG1_TURN = "laminar/B738/fms/dir_seg1_turn";
	private final String DIR_SEG3_END_ANGLE = "laminar/B738/fms/dir_seg3_end_angle";
	private final String DIR_SEG3_START_ANGLE = "laminar/B738/fms/dir_seg3_start_angle";
	private final String DIR_SEG3_CTR_LAT = "laminar/B738/fms/dir_seg3_ctr_lat";
	private final String DIR_SEG3_CTR_LON = "laminar/B738/fms/dir_seg3_ctr_lon";
	private final String DIR_SEG3_RADIUS = "laminar/B738/fms/dir_seg3_radius";
	private final String DIR_SEG3_TURN = "laminar/B738/fms/dir_seg3_turn";
	private final String FMS_TRACK = "laminar/B738/fms/gps_track_degtm";
	private final String GP_ERR_PFD = "laminar/B738/fms/gp_err_pfd";
	private final String APPROACH_FLAPS = "laminar/B738/FMS/approach_flaps";
	private final String APPROACH_SPEED = "laminar/B738/FMS/approach_speed";
	
	public float anp;
	public float vanp;
	public float rnp;
	public float vrnp;
	public float xtrack_nd;
	public int trans_alt;
	public int trans_lvl;
	public int[] fpln_active = new int[2];
	public int[] pfd_gp_path = new int[2];
	public int[] pfd_fac_horizontal = new int[2];
	public int[] ian_info = new int[2];
	public int[] fac_exp = new int[2];
	public String fpln_nav_id;
	public String active_waypoint;
	public String fpln_nav_id_eta;
	public String rw_fac_id2;
	public float rw_dist2;
	public float fpln_nav_id_dist = 0f;
	public float fms_vref = 0f;
	public float fms_vr_set = 0f;
	public float fms_v1_set = 0f;
	public float fms_v1 = 0f;
	public float fms_v2_15 = 0f;
	public double fms_vref_bugs = 0f;
	public double fms_v1r_bugs = 0f;
	public int legs_mod_active = 0;
	public int[] legs_step_ctr_idx = new int[2];
	public float[] ils_rotate0 = new float[2];
	public float[] ils_rotate0_mod = new float[2];
	public int[] ils_show0 = new int[2];
	public int[] ils_show0_mod = new int[2];
	public float[] ils_rotate = new float[2];
	public float[] ils_rotate_mod = new float[2];
	public int[] ils_show = new int[2];
	public int[] ils_show_mod = new int[2];
	public String[] nav_txt1 = {"", ""};
	public String[] nav_txt1a = {"", ""};
	public String[] nav_txt2 = {"", ""};
	public int missed_app_wpt_idx = 0;
	public int ils_disable = 0;
	public int vnav_idx = 0;
	public int vnav_idx_mod = 0;
	public int intdir_act = 0;
	public int intdir_crs = 0;
	public int intdir_crs2 = 0;
	public float dir_seg1_end_angle = 0;
	public float dir_seg1_start_angle = 0;
	public float dir_seg1_ctr_lat = 0;
	public float dir_seg1_ctr_lon = 0;
	public float dir_seg1_radius = 0;
	public int dir_seg1_turn = 0;
	public float dir_seg3_end_angle = 0;
	public float dir_seg3_start_angle = 0;
	public float dir_seg3_ctr_lat = 0;
	public float dir_seg3_ctr_lon = 0;
	public float dir_seg3_radius = 0;
	public int dir_seg3_turn = 0;
	public float approach_flaps = 0f;
	public float approach_speed = 0f;
	public float fms_track = 0f;
	public float gp_err_pfd = 0f;
	
	//fms active route lnav curves
	
	private final String LEGS_RAD_LAT = "laminar/B738/fms/legs_rad_lat"; //[128]
	private final String LEGS_RAD_LON = "laminar/B738/fms/legs_rad_lon"; //[128]
	private final String LEGS_RAD_TURN = "laminar/B738/fms/legs_rad_turn"; //[128]
	private final String LEGS_RADIUS = "laminar/B738/fms/legs_radius"; //[128]
	private final String LEGS_SEG1_RADIUS = "laminar/B738/fms/legs_seg1_radius"; //[128]
	private final String LEGS_SEG1_CTR_LAT = "laminar/B738/fms/legs_seg1_ctr_lat"; //[128]
	private final String LEGS_SEG1_CTR_LON = "laminar/B738/fms/legs_seg1_ctr_lon"; //[128]
	private final String LEGS_SEG1_TURN = "laminar/B738/fms/legs_seg1_turn"; //[128]
	private final String LEGS_SEG1_START_ANGLE = "laminar/B738/fms/legs_seg1_start_angle"; //[128]
	private final String LEGS_SEG1_END_ANGLE = "laminar/B738/fms/legs_seg1_end_angle"; //[128]
	private final String LEGS_SEG3_START_ANGLE = "laminar/B738/fms/legs_seg3_start_angle"; //[128]
	private final String LEGS_SEG3_END_ANGLE = "laminar/B738/fms/legs_seg3_end_angle"; //[128]
	private final String LEGS_SEG3_TURN = "laminar/B738/fms/legs_seg3_turn"; //[128]
	private final String LEGS_SEG3_RADIUS = "laminar/B738/fms/legs_seg3_radius"; //[128]
	private final String LEGS_SEG3_CTR_LAT = "laminar/B738/fms/legs_seg3_ctr_lat"; //[128]
	private final String LEGS_SEG3_CTR_LON = "laminar/B738/fms/legs_seg3_ctr_lon"; //[128]
	private final String LEGS_AF_BEG = "laminar/B738/fms/legs_af_beg"; //[128]
	private final String LEGS_AF_END = "laminar/B738/fms/legs_af_end"; //[128]
	private final String LEGS_BYPASS = "laminar/B738/fms/legs_bypass"; //[128]
	
	public float[] legs_rad_lat = new float[128];
	public float[] legs_rad_lon = new float[128];
	public float[] legs_rad_turn = new float[128];
	public float[] legs_radius = new float[128];
	public float[] legs_seg1_radius = new float[128];
	public float[] legs_seg1_ctr_lat = new float[128];
	public float[] legs_seg1_ctr_lon = new float[128];
	public float[] legs_seg1_turn = new float[128];
	public float[] legs_seg1_start_angle = new float[128];
	public float[] legs_seg1_end_angle = new float[128];
	public float[] legs_seg3_start_angle = new float[128];
	public float[] legs_seg3_end_angle = new float[128];
	public float[] legs_seg3_turn = new float[128];
	public float[] legs_seg3_radius = new float[128];
	public float[] legs_seg3_ctr_lat = new float[128];
	public float[] legs_seg3_ctr_lon = new float[128];
	public float[] legs_af_beg = new float[128];
	public float[] legs_af_end = new float[128];
	public float[] legs_bypass = new float[128];
	
	//fms active route
	
	private final String LEGS = "laminar/B738/fms/legs"; //only active flight plan (string, space separated)
	private final String LEGS_LAT = "laminar/B738/fms/legs_lat"; //only active flight plan [128]
	private final String LEGS_LON = "laminar/B738/fms/legs_lon"; //only active flight plan [128]
//	private final String LEGS_ALT_CALC = "laminar/B738/fms/legs_alt_calc"; //[128] altitude for legs
	private final String LEGS_ALT_REST1 = "laminar/B738/fms/legs_alt_rest1";
	private final String LEGS_ALT_REST2 = "laminar/B738/fms/legs_alt_rest2";
	private final String LEGS_DIST = "laminar/B738/fms/legs_dist"; //[128]
	private final String LEGS_ALT_REST_TYPE = "laminar/B738/fms/legs_alt_rest_type"; // 1 =below, 2 = above, 3 = between (block), 4 = dest runway
	private final String LEGS_ETA = "laminar/B738/fms/legs_eta"; //[128]
	private final String LEGS_HOLD_DIST = "laminar/B738/fms/legs_hold_dist"; //[128]
	private final String LEGS_HOLD_TIME = "laminar/B738/fms/legs_hold_time"; //[128]
	private final String LEGS_CRS_MAG = "laminar/B738/fms/legs_crs_mag"; //[128]
	private final String LEGS_SPD = "laminar/B738/fms/legs_spd"; //[128]
	private final String LEGS_TYPE = "laminar/B738/fms/legs_type"; // 0 waypoint, 5 is flyover waypoint
	private final String NUM_OF_WPTS = "laminar/B738/fms/num_of_wpts";

	public int num_of_wpts = 0;
	public String[] waypoints;
	public String origin_arpt;
	public String dest_arpt;

	public float[] legs_lat = new float[128];
	public float[] legs_lon = new float[128];
//	public float[] legs_alt_calc = new float[128];
	public float[] legs_alt_rest1 = new float[128];
	public float[] legs_alt_rest2 = new float[128];
	public float[] legs_dist = new float[128];
	public float[] legs_alt_rest_type = new float[128];
	public float[] legs_eta = new float[128];	
	public float[] legs_hold_dist = new float[128];	
	public float[] legs_hold_time = new float[128];
	public float[] legs_crs_mag = new float[128];
	public float[] legs_spd = new float[128];
	public float[] legs_type = new float[128];
	
	//fms modified route
	
	private final String LEGS_2 = "laminar/B738/fms/legs_2";
	private final String LEGS_LAT_2 = "laminar/B738/fms/legs_lat_2"; //only active flight plan [128]
	private final String LEGS_LON_2 = "laminar/B738/fms/legs_lon_2"; //only active flight plan [128]
//	private final String LEGS_ALT_CALC_2 = "laminar/B738/fms/legs_alt_calc_2"; //[128] altitude for legs
	private final String LEGS_ALT_REST1_2 = "laminar/B738/fms/legs_alt_rest1_2";
	private final String LEGS_ALT_REST2_2 = "laminar/B738/fms/legs_alt_rest2_2";
	private final String LEGS_ALT_REST_TYPE_2 = "laminar/B738/fms/legs_alt_rest_type_2"; // 1 = below, 2 = above, 3 = between (block), 4 = dest runway
	private final String LEGS_HOLD_DIST_2 = "laminar/B738/fms/legs_hold_dist_2"; //[128]
	private final String LEGS_HOLD_TIME_2 = "laminar/B738/fms/legs_hold_time_2"; //[128]
	private final String LEGS_CRS_MAG_2 = "laminar/B738/fms/legs_crs_mag_2"; //[128]
	private final String LEGS_SPD_2 = "laminar/B738/fms/legs_spd_2"; //[128]
	private final String LEGS_RAD_LAT_2 = "laminar/B738/fms/legs_rad_lat_2"; //[128]
	private final String LEGS_RAD_LON_2 = "laminar/B738/fms/legs_rad_lon_2"; //[128]
	private final String LEGS_RAD_TURN_2 = "laminar/B738/fms/legs_rad_turn_2"; //[128]
	private final String LEGS_TYPE_2 = "laminar/B738/fms/legs_type_2"; // 0 waypoint, 5 is flyover waypoint
	private final String NUM_OF_WPTS_2 = "laminar/B738/fms/num_of_wpts_2";
	
	public int num_of_wpts_2 = 0;
	public String[] waypoints_2;
	public String origin_arpt_2;
	public String dest_arpt_2;
	public float[] legs_lat_2 = new float[128];
	public float[] legs_lon_2 = new float[128];
//	public float[] legs_alt_calc_2 = new float[128];
	public float[] legs_alt_rest1_2 = new float[128];
	public float[] legs_alt_rest2_2 = new float[128];
	public float[] legs_alt_rest_type_2 = new float[128];
	public float[] legs_hold_dist_2 = new float[128];	
	public float[] legs_hold_time_2 = new float[128];
	public float[] legs_crs_mag_2 = new float[128];
	public float[] legs_spd_2 = new float[128];
	public float[] legs_rad_lat_2 = new float[128];
	public float[] legs_rad_lon_2 = new float[128];
	public float[] legs_rad_turn_2 = new float[128];
	public float[] legs_type_2 = new float[128];
		
	public FMS(ExtPlaneInterface iface) {
		super(iface);
		
		drefs.add(APPROACH_SPEED);
		drefs.add(APPROACH_FLAPS);
		drefs.add(FMS_TRACK);
		
		// fms_data
		fms_data = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				switch (object.getName()) {
				case ANP:
					anp = Float.parseFloat(object.getValue()[0]);
					break;
				case VANP:
					vanp = Float.parseFloat(object.getValue()[0]);
					break;
				case RNP:
					rnp = Float.parseFloat(object.getValue()[0]);
					break;
				case VRNP:
					vrnp = Float.parseFloat(object.getValue()[0]);
					break;
				case XTRACK_ND:
					xtrack_nd = Float.parseFloat(object.getValue()[0]);
					break;
				case TRANS_ALT:
					trans_alt = Integer.parseInt(object.getValue()[0]);
					break;
				case TRANS_LVL:
					trans_lvl = Integer.parseInt(object.getValue()[0]);
					break;
				case FPLN_NAV_ID:
					active_waypoint = object.getValue()[0].replaceAll("\"", "");
					break;
				case FMS_VREF:
					fms_vref = Float.parseFloat(object.getValue()[0]);
					break;
				case FMS_VREF_BUGS:
					fms_vref_bugs = Double.parseDouble(object.getValue()[0]);
					break;
				case FMS_V1R_BUGS:
					fms_v1r_bugs = Double.parseDouble(object.getValue()[0]);
					break;
				case FPLN_NAV_ID_ETA:
					fpln_nav_id_eta = object.getValue()[0].replaceAll("\"", "");
					break;
				case RW_FAC_ID2:
					rw_fac_id2 = object.getValue()[0].replaceAll("\"", "");
					break;
				case RW_DIST2:
					rw_dist2 = Float.parseFloat(object.getValue()[0]);
					break;
				case FPLN_NAV_ID_DIST:
					fpln_nav_id_dist = Float.parseFloat(object.getValue()[0]);
					break;
				case FMS_VR_SET:
					fms_vr_set = Float.parseFloat(object.getValue()[0]);
					break;
				case FMS_V1_SET:
					fms_v1_set = Float.parseFloat(object.getValue()[0]);
					break;
				case FMS_V1:
					fms_v1 = Float.parseFloat(object.getValue()[0]);
					break;
				case FMS_V2_15:
					fms_v2_15 = Float.parseFloat(object.getValue()[0]);
					break;
				case LEGS_MOD_ACTIVE:
					legs_mod_active = Integer.parseInt(object.getValue()[0]);
					break;
				case MISSED_APP_WPT_IDX:
					missed_app_wpt_idx = Integer.parseInt(object.getValue()[0]);
					break;
				case ILS_DISABLE:
					ils_disable = Integer.parseInt(object.getValue()[0]);
					break;
				case VNAV_IDX:
					vnav_idx = Integer.parseInt(object.getValue()[0]);
					break;
				case VNAV_IDX_MOD:
					vnav_idx_mod = Integer.parseInt(object.getValue()[0]);
					break;
				case INTDIR_ACT:
					intdir_act = Integer.parseInt(object.getValue()[0]);
					break;
				case INTDIR_CRS:
					intdir_crs = Integer.parseInt(object.getValue()[0]);
					break;
				case INTDIR_CRS2:
					intdir_crs2 = Integer.parseInt(object.getValue()[0]);
					break;
				case DIR_SEG1_END_ANGLE:
					dir_seg1_end_angle = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG1_START_ANGLE:
					dir_seg1_start_angle = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG1_CTR_LAT:
					dir_seg1_ctr_lat = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG1_CTR_LON:
					dir_seg1_ctr_lon = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG1_RADIUS:
					dir_seg1_radius = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG1_TURN:
					dir_seg1_turn = Integer.parseInt(object.getValue()[0]);
					break;
				case DIR_SEG3_END_ANGLE:
					dir_seg3_end_angle = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG3_START_ANGLE:
					dir_seg3_start_angle = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG3_CTR_LAT:
					dir_seg3_ctr_lat = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG3_CTR_LON:
					dir_seg3_ctr_lon = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG3_RADIUS:
					dir_seg3_radius = Float.parseFloat(object.getValue()[0]);
					break;
				case DIR_SEG3_TURN:
					dir_seg3_turn = Integer.parseInt(object.getValue()[0]);
					break;
				case APPROACH_SPEED:
					approach_speed = Float.parseFloat(object.getValue()[0]);
					break;
				case APPROACH_FLAPS:
					approach_flaps = Float.parseFloat(object.getValue()[0]);
					break;
				case FMS_TRACK:
					fms_track = Float.parseFloat(object.getValue()[0]);
					break;
				case GP_ERR_PFD:
					gp_err_pfd = Float.parseFloat(object.getValue()[0]);
					break;
				}
				
				for(int i = 0; i < 2; i++) {
					if(object.getName().equals(LEGS_STEP_CTR_IDX[i])) {
						legs_step_ctr_idx[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(FPLN_ACTIVE[i])) {
						fpln_active[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_GP_PATH[i])) {
						pfd_gp_path[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(PFD_FAC_HORIZONTAL[i])) {
						pfd_fac_horizontal[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(IAN_INFO[i])) {
						ian_info[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(FAC_EXP[i])) {
						fac_exp[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(ILS_ROTATE0[i])) {
						ils_rotate0[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(ILS_ROTATE0_MOD[i])) {
						ils_rotate0_mod[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(ILS_SHOW0[i])) {
						ils_show0[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(ILS_SHOW0_MOD[i])) {
						ils_show0_mod[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(ILS_ROTATE[i])) {
						ils_rotate[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(ILS_ROTATE_MOD[i])) {
						ils_rotate_mod[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(ILS_SHOW[i])) {
						ils_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(ILS_SHOW_MOD[i])) {
						ils_show_mod[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(NAV_TXT1[i])) {
						byte[] nav_txt1Byte = Base64.getDecoder().decode(object.getValue()[0]);
						nav_txt1[i] = new String(nav_txt1Byte).replaceAll("[^\\x20-\\x7e]", "");
						nav_txt1[i] = nav_txt1[i].replaceAll("`","°");
					}
					if(object.getName().equals(NAV_TXT1a[i])) {
						byte[] nav_txt1aByte = Base64.getDecoder().decode(object.getValue()[0]);
						nav_txt1a[i] = new String(nav_txt1aByte).replaceAll("[^\\x20-\\x7e]", "");
						nav_txt1a[i] = nav_txt1a[i].replaceAll("`","°");
					}
					if(object.getName().equals(NAV_TXT2[i])) {
						byte[] nav_txt2Byte = Base64.getDecoder().decode(object.getValue()[0]);
						nav_txt2[i] = new String(nav_txt2Byte).replaceAll("[^\\x20-\\x7e]", "");
						nav_txt2[i] = nav_txt2[i].replaceAll("`","°");
					}
				}
			}	
		};
		
		//fms active route
		active_route = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				switch (object.getName()) {

				case LEGS_RAD_LAT:
					for(int i = 0; i < 128; i++) {
						legs_rad_lat[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_RAD_LON:
					for(int i = 0; i < 128; i++) {
						legs_rad_lon[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_RAD_TURN:
					for(int i = 0; i < 128; i++) {
						legs_rad_turn[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_RADIUS:
					for(int i = 0; i < 128; i++) {
						legs_radius[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG1_RADIUS:
					for(int i = 0; i < 128; i++) {
						legs_seg1_radius[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG1_CTR_LAT:
					for(int i = 0; i < 128; i++) {
						legs_seg1_ctr_lat[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG1_CTR_LON:
					for(int i = 0; i < 128; i++) {
						legs_seg1_ctr_lon[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG1_TURN:
					for(int i = 0; i < 128; i++) {
						legs_seg1_turn[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG1_START_ANGLE:
					for(int i = 0; i < 128; i++) {
						legs_seg1_start_angle[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG1_END_ANGLE:
					for(int i = 0; i < 128; i++) {
						legs_seg1_end_angle[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG3_START_ANGLE:
					for(int i = 0; i < 128; i++) {
						legs_seg3_start_angle[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG3_END_ANGLE:
					for(int i = 0; i < 128; i++) {
						legs_seg3_end_angle[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG3_TURN:
					for(int i = 0; i < 128; i++) {
						legs_seg3_turn[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG3_RADIUS:
					for(int i = 0; i < 128; i++) {
						legs_seg3_radius[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG3_CTR_LAT:
					for(int i = 0; i < 128; i++) {
						legs_seg3_ctr_lat[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SEG3_CTR_LON:
					for(int i = 0; i < 128; i++) {
						legs_seg3_ctr_lon[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_AF_BEG:
					for(int i = 0; i < 128; i++) {
						legs_af_beg[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;	
				case LEGS_AF_END:
					for(int i = 0; i < 128; i++) {
						legs_af_end[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;	
				case LEGS_BYPASS:
					for(int i = 0; i < 128; i++) {
						legs_bypass[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;	
				case LEGS:
					byte[] legsByte = Base64.getDecoder().decode(object.getValue()[0]);
					String decodedString = new String(legsByte);
					waypoints = decodedString.split("\\s+");
					num_of_wpts = waypoints.length;
					origin_arpt = waypoints[0];
					dest_arpt = waypoints[waypoints.length - 1];
					break;
				case LEGS_LAT:
					for(int i = 0; i < 128; i++) {
						legs_lat[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_LON:
					for(int i = 0; i < 128; i++) {
						legs_lon[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
//				case LEGS_ALT_CALC:
//					for(int i = 0; i < 128; i++) {
//						legs_alt_calc[i] = Float.parseFloat(object.getValue()[i]);
//					}
//					break;
				case LEGS_ALT_REST1:
					for(int i = 0; i < 128; i++) {
						legs_alt_rest1[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_ALT_REST2:
					for(int i = 0; i < 128; i++) {
						legs_alt_rest2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_DIST:
					for(int i = 0; i < 128; i++) {
						legs_dist[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_ALT_REST_TYPE:
					for(int i = 0; i < 128; i++) {
						legs_alt_rest_type[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_ETA:
					for(int i = 0; i < 128; i++) {
						legs_eta[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_HOLD_DIST:
					for(int i = 0; i < 128; i++) {
						legs_hold_dist[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_HOLD_TIME:
					for(int i = 0; i < 128; i++) {
						legs_hold_time[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_CRS_MAG:
					for(int i = 0; i < 128; i++) {
						legs_crs_mag[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SPD:
					for(int i = 0; i < 128; i++) {
						legs_spd[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_TYPE:
					for(int i = 0; i < 128; i++) {
						legs_type[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				}
			}		
		};
		
		//fms modified route
		modified_route = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				switch (object.getName()) {
				case LEGS_2:
					byte[] legsByte = Base64.getDecoder().decode(object.getValue()[0]);
					String decodedString = new String(legsByte);
					waypoints_2 = decodedString.split("\\s+");
					num_of_wpts_2 = waypoints_2.length;
					origin_arpt_2 = waypoints_2[0];
					dest_arpt_2 = waypoints_2[waypoints_2.length - 1];
					break;
				case LEGS_LAT_2:
					for(int i = 0; i < 128; i++) {
						legs_lat_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_LON_2:
					for(int i = 0; i < 128; i++) {
						legs_lon_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
//				case LEGS_ALT_CALC_2:
//					for(int i = 0; i < 128; i++) {
//						legs_alt_calc_2[i] = Float.parseFloat(object.getValue()[i]);
//					}
//					break;
				case LEGS_ALT_REST1_2:
					for(int i = 0; i < 128; i++) {
						legs_alt_rest1_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_ALT_REST2_2:
					for(int i = 0; i < 128; i++) {
						legs_alt_rest2_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_ALT_REST_TYPE_2:
					for(int i = 0; i < 128; i++) {
						legs_alt_rest_type_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_HOLD_DIST_2:
					for(int i = 0; i < 128; i++) {
						legs_hold_dist_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_HOLD_TIME_2:
					for(int i = 0; i < 128; i++) {
						legs_hold_time_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_CRS_MAG_2:
					for(int i = 0; i < 128; i++) {
						legs_crs_mag_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_SPD_2:
					for(int i = 0; i < 128; i++) {
						legs_spd_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_RAD_LAT_2:
					for(int i = 0; i < 128; i++) {
						legs_rad_lat_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_RAD_LON_2:
					for(int i = 0; i < 128; i++) {
						legs_rad_lon_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_RAD_TURN_2:
					for(int i = 0; i < 128; i++) {
						legs_rad_turn_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
				case LEGS_TYPE_2:
					for(int i = 0; i < 128; i++) {
						legs_type_2[i] = Float.parseFloat(object.getValue()[i]);
					}
					break;
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
			iface.observeDataRef(dref, fms_data);
		}
		
		iface.includeDataRef(ANP, 0.001f);
		iface.includeDataRef(VANP, 0.001f);
		iface.includeDataRef(RNP, 0.001f);
		iface.includeDataRef(VRNP, 0.001f);
		iface.includeDataRef(XTRACK_ND, 0.01f);
		iface.includeDataRef(TRANS_ALT);
		iface.includeDataRef(TRANS_LVL);
		iface.includeDataRef(FPLN_NAV_ID);
		iface.includeDataRef(FMS_VREF);
		iface.includeDataRef(FMS_VREF_BUGS);
		iface.includeDataRef(FMS_V1R_BUGS);
		iface.includeDataRef(FPLN_NAV_ID_ETA);
		iface.includeDataRef(RW_FAC_ID2);
		iface.includeDataRef(RW_DIST2, 0.01f);
		iface.includeDataRef(FPLN_NAV_ID_DIST, 0.01f);
		iface.includeDataRef(FMS_VR_SET);
		iface.includeDataRef(FMS_V1_SET);
		iface.includeDataRef(FMS_V1);
		iface.includeDataRef(FMS_V2_15);
		iface.includeDataRef(LEGS_MOD_ACTIVE);
		iface.includeDataRef(MISSED_APP_WPT_IDX);
		iface.includeDataRef(ILS_DISABLE);
		iface.includeDataRef(VNAV_IDX);
		iface.includeDataRef(VNAV_IDX_MOD);
		iface.includeDataRef(INTDIR_ACT);
		iface.includeDataRef(INTDIR_CRS);
		iface.includeDataRef(INTDIR_CRS2);
		iface.includeDataRef(DIR_SEG1_END_ANGLE, 0.001f);
		iface.includeDataRef(DIR_SEG1_START_ANGLE, 0.001f);
		iface.includeDataRef(DIR_SEG1_CTR_LAT);
		iface.includeDataRef(DIR_SEG1_CTR_LON);
		iface.includeDataRef(DIR_SEG1_RADIUS, 0.001f);
		iface.includeDataRef(DIR_SEG1_TURN, 0.1f);
		iface.includeDataRef(DIR_SEG3_END_ANGLE, 0.001f);
		iface.includeDataRef(DIR_SEG3_START_ANGLE, 0.001f);
		iface.includeDataRef(DIR_SEG3_CTR_LAT);
		iface.includeDataRef(DIR_SEG3_CTR_LON);
		iface.includeDataRef(DIR_SEG3_RADIUS, 0.001f);
		iface.includeDataRef(DIR_SEG3_TURN, 0.1f);
		iface.includeDataRef(GP_ERR_PFD, 1f);
		
		iface.observeDataRef(ANP, fms_data);
		iface.observeDataRef(VANP, fms_data);
		iface.observeDataRef(RNP, fms_data);
		iface.observeDataRef(VRNP, fms_data);
		iface.observeDataRef(XTRACK_ND, fms_data);
		iface.observeDataRef(TRANS_ALT, fms_data);
		iface.observeDataRef(TRANS_LVL, fms_data);
		iface.observeDataRef(FPLN_NAV_ID, fms_data);
		iface.observeDataRef(FMS_VREF, fms_data);
		iface.observeDataRef(FMS_VREF_BUGS, fms_data);
		iface.observeDataRef(FMS_V1R_BUGS, fms_data);
		iface.observeDataRef(FPLN_NAV_ID_ETA, fms_data);
		iface.observeDataRef(RW_FAC_ID2, fms_data);
		iface.observeDataRef(RW_DIST2, fms_data);
		iface.observeDataRef(FPLN_NAV_ID_DIST, fms_data);
		iface.observeDataRef(FMS_VR_SET, fms_data);
		iface.observeDataRef(FMS_V1_SET, fms_data);
		iface.observeDataRef(FMS_V1, fms_data);
		iface.observeDataRef(FMS_V2_15, fms_data);
		iface.observeDataRef(LEGS_MOD_ACTIVE, fms_data);
		iface.observeDataRef(MISSED_APP_WPT_IDX, fms_data);
		iface.observeDataRef(ILS_DISABLE, fms_data);
		iface.observeDataRef(VNAV_IDX, fms_data);
		iface.observeDataRef(VNAV_IDX_MOD, fms_data);
		iface.observeDataRef(INTDIR_ACT, fms_data);
		iface.observeDataRef(INTDIR_CRS, fms_data);
		iface.observeDataRef(INTDIR_CRS2, fms_data);
		iface.observeDataRef(DIR_SEG1_END_ANGLE, fms_data);
		iface.observeDataRef(DIR_SEG1_START_ANGLE, fms_data);
		iface.observeDataRef(DIR_SEG1_CTR_LAT, fms_data);
		iface.observeDataRef(DIR_SEG1_CTR_LON, fms_data);
		iface.observeDataRef(DIR_SEG1_RADIUS, fms_data);
		iface.observeDataRef(DIR_SEG1_TURN, fms_data);
		iface.observeDataRef(DIR_SEG3_END_ANGLE, fms_data);
		iface.observeDataRef(DIR_SEG3_START_ANGLE, fms_data);
		iface.observeDataRef(DIR_SEG3_CTR_LAT, fms_data);
		iface.observeDataRef(DIR_SEG3_CTR_LON, fms_data);
		iface.observeDataRef(DIR_SEG3_RADIUS, fms_data);
		iface.observeDataRef(DIR_SEG3_TURN, fms_data);
		iface.observeDataRef(GP_ERR_PFD, fms_data);
		
		for(int i = 0; i < 2; i++) {
			iface.includeDataRef(FPLN_ACTIVE[i]);
			iface.observeDataRef(FPLN_ACTIVE[i], fms_data);
			iface.includeDataRef(PFD_GP_PATH[i]);
			iface.observeDataRef(PFD_GP_PATH[i], fms_data);
			iface.includeDataRef(PFD_FAC_HORIZONTAL[i]);
			iface.observeDataRef(PFD_FAC_HORIZONTAL[i], fms_data);
			iface.includeDataRef(IAN_INFO[i]);
			iface.observeDataRef(IAN_INFO[i], fms_data);
			iface.includeDataRef(FAC_EXP[i]);
			iface.observeDataRef(FAC_EXP[i], fms_data);
			iface.includeDataRef(ILS_ROTATE0[i]);
			iface.observeDataRef(ILS_ROTATE0[i], fms_data);
			iface.includeDataRef(ILS_ROTATE0_MOD[i]);
			iface.observeDataRef(ILS_ROTATE0_MOD[i], fms_data);
			iface.includeDataRef(ILS_ROTATE[i]);
			iface.observeDataRef(ILS_ROTATE[i], fms_data);
			iface.includeDataRef(ILS_ROTATE_MOD[i]);
			iface.observeDataRef(ILS_ROTATE_MOD[i], fms_data);
			iface.includeDataRef(ILS_SHOW0[i]);
			iface.observeDataRef(ILS_SHOW0[i], fms_data);
			iface.includeDataRef(ILS_SHOW0_MOD[i]);
			iface.observeDataRef(ILS_SHOW0_MOD[i], fms_data);
			iface.includeDataRef(ILS_SHOW[i]);
			iface.observeDataRef(ILS_SHOW[i], fms_data);
			iface.includeDataRef(ILS_SHOW_MOD[i]);
			iface.observeDataRef(ILS_SHOW_MOD[i], fms_data);
			iface.includeDataRef(NAV_TXT1[i]);
			iface.observeDataRef(NAV_TXT1[i], fms_data);
			iface.includeDataRef(NAV_TXT1a[i]);
			iface.observeDataRef(NAV_TXT1a[i], fms_data);
			iface.includeDataRef(NAV_TXT2[i]);
			iface.observeDataRef(NAV_TXT2[i], fms_data);
			iface.includeDataRef(LEGS_STEP_CTR_IDX[i]);
			iface.observeDataRef(LEGS_STEP_CTR_IDX[i], fms_data);
		}
		
		iface.includeDataRef(LEGS_RAD_LAT);
		iface.includeDataRef(LEGS_RAD_LON);
		iface.includeDataRef(LEGS_RAD_TURN, 0.1f);
		iface.includeDataRef(LEGS_RADIUS, 0.001f);
		iface.includeDataRef(LEGS_SEG1_RADIUS, 0.001f);
		iface.includeDataRef(LEGS_SEG1_CTR_LAT);
		iface.includeDataRef(LEGS_SEG1_CTR_LON);
		iface.includeDataRef(LEGS_SEG1_TURN, 0.1f);
		iface.includeDataRef(LEGS_SEG1_START_ANGLE, 0.001f);
		iface.includeDataRef(LEGS_SEG1_END_ANGLE, 0.001f);
		iface.includeDataRef(LEGS_SEG3_START_ANGLE, 0.001f);
		iface.includeDataRef(LEGS_SEG3_END_ANGLE, 0.001f);
		iface.includeDataRef(LEGS_SEG3_TURN, 0.1f);
		iface.includeDataRef(LEGS_SEG3_RADIUS, 0.001f);
		iface.includeDataRef(LEGS_SEG3_CTR_LAT);
		iface.includeDataRef(LEGS_SEG3_CTR_LON);
		iface.includeDataRef(LEGS_AF_BEG, 0.001f);
		iface.includeDataRef(LEGS_AF_END, 0.001f);
		iface.includeDataRef(LEGS_BYPASS, 0.1f);
		
		iface.observeDataRef(LEGS_RAD_LAT, active_route);
		iface.observeDataRef(LEGS_RAD_LON, active_route);
		iface.observeDataRef(LEGS_RAD_TURN, active_route);
		iface.observeDataRef(LEGS_RADIUS, active_route);
		iface.observeDataRef(LEGS_SEG1_RADIUS, active_route);
		iface.observeDataRef(LEGS_SEG1_CTR_LAT, active_route);
		iface.observeDataRef(LEGS_SEG1_CTR_LON, active_route);
		iface.observeDataRef(LEGS_SEG1_TURN, active_route);
		iface.observeDataRef(LEGS_SEG1_START_ANGLE, active_route);
		iface.observeDataRef(LEGS_SEG1_END_ANGLE, active_route);
		iface.observeDataRef(LEGS_SEG3_START_ANGLE, active_route);
		iface.observeDataRef(LEGS_SEG3_END_ANGLE, active_route);
		iface.observeDataRef(LEGS_SEG3_TURN, active_route);
		iface.observeDataRef(LEGS_SEG3_RADIUS, active_route);
		iface.observeDataRef(LEGS_SEG3_CTR_LAT, active_route);
		iface.observeDataRef(LEGS_SEG3_CTR_LON, active_route);
		iface.observeDataRef(LEGS_AF_BEG, active_route);
		iface.observeDataRef(LEGS_AF_END, active_route);
		iface.observeDataRef(LEGS_BYPASS, active_route);
		
		iface.includeDataRef(LEGS);
		iface.includeDataRef(LEGS_LAT);
		iface.includeDataRef(LEGS_LON);
//		iface.includeDataRef(LEGS_ALT_CALC);
		iface.includeDataRef(LEGS_ALT_REST1);
		iface.includeDataRef(LEGS_ALT_REST2);
		iface.includeDataRef(LEGS_DIST);
		iface.includeDataRef(LEGS_ALT_REST_TYPE);
		iface.includeDataRef(LEGS_ETA);
		iface.includeDataRef(LEGS_HOLD_DIST);
		iface.includeDataRef(LEGS_HOLD_TIME);
		iface.includeDataRef(LEGS_CRS_MAG);
		iface.includeDataRef(LEGS_SPD);
		iface.includeDataRef(LEGS_TYPE);

		iface.observeDataRef(LEGS, active_route);
		iface.observeDataRef(LEGS_LAT, active_route);
		iface.observeDataRef(LEGS_LON, active_route);
//		iface.observeDataRef(LEGS_ALT_CALC, active_route);
		iface.observeDataRef(LEGS_ALT_REST1, active_route);
		iface.observeDataRef(LEGS_ALT_REST2, active_route);
		iface.observeDataRef(LEGS_DIST, active_route);
		iface.observeDataRef(LEGS_ALT_REST_TYPE, active_route);
		iface.observeDataRef(LEGS_ETA, active_route);	
		iface.observeDataRef(LEGS_HOLD_DIST, active_route);
		iface.observeDataRef(LEGS_HOLD_TIME, active_route);
		iface.observeDataRef(LEGS_CRS_MAG, active_route);
		iface.observeDataRef(LEGS_SPD, active_route);
		iface.observeDataRef(LEGS_TYPE, active_route);

		iface.includeDataRef(LEGS_2);
		iface.includeDataRef(LEGS_LAT_2);
		iface.includeDataRef(LEGS_LON_2);
//		iface.includeDataRef(LEGS_ALT_CALC_2);
		iface.includeDataRef(LEGS_ALT_REST1_2);
		iface.includeDataRef(LEGS_ALT_REST2_2);
		iface.includeDataRef(LEGS_ALT_REST_TYPE_2);
		iface.includeDataRef(LEGS_HOLD_DIST_2);
		iface.includeDataRef(LEGS_HOLD_TIME_2);
		iface.includeDataRef(LEGS_CRS_MAG_2);
		iface.includeDataRef(LEGS_SPD_2);
		iface.includeDataRef(LEGS_RAD_LAT_2);
		iface.includeDataRef(LEGS_RAD_LON_2);
		iface.includeDataRef(LEGS_RAD_TURN_2);
		iface.includeDataRef(LEGS_TYPE_2);

		iface.observeDataRef(LEGS_2, modified_route);
		iface.observeDataRef(LEGS_LAT_2, modified_route);
		iface.observeDataRef(LEGS_LON_2, modified_route);
//		iface.observeDataRef(LEGS_ALT_CALC_2, modified_route);
		iface.observeDataRef(LEGS_ALT_REST1_2, modified_route);
		iface.observeDataRef(LEGS_ALT_REST2_2, modified_route);
		iface.observeDataRef(LEGS_ALT_REST_TYPE_2, modified_route);
		iface.observeDataRef(LEGS_HOLD_DIST_2, modified_route);
		iface.observeDataRef(LEGS_HOLD_TIME_2, modified_route);
		iface.observeDataRef(LEGS_CRS_MAG_2, modified_route);
		iface.observeDataRef(LEGS_SPD_2, modified_route);
		iface.observeDataRef(LEGS_RAD_LAT_2, modified_route);
		iface.observeDataRef(LEGS_RAD_LON_2, modified_route);
		iface.observeDataRef(LEGS_RAD_TURN_2, modified_route);
		iface.observeDataRef(LEGS_TYPE_2, modified_route);
		
	}

	@Override
	public void excludeDrefs() {
		
		for(String dref : drefs) {
			iface.excludeDataRef(dref);
			iface.unObserveDataRef(dref, fms_data);
		}	
		
		iface.excludeDataRef(ANP);
		iface.excludeDataRef(VANP);
		iface.excludeDataRef(RNP);
		iface.excludeDataRef(VRNP);
		iface.excludeDataRef(XTRACK_ND);
		iface.excludeDataRef(TRANS_ALT);
		iface.excludeDataRef(TRANS_LVL);
		iface.excludeDataRef(FPLN_NAV_ID);
		iface.excludeDataRef(FMS_VREF);
		iface.excludeDataRef(FMS_VREF_BUGS);
		iface.excludeDataRef(FMS_V1R_BUGS);
		iface.excludeDataRef(FPLN_NAV_ID_ETA);
		iface.excludeDataRef(RW_FAC_ID2);
		iface.excludeDataRef(RW_DIST2);
		iface.excludeDataRef(FPLN_NAV_ID_DIST);
		iface.excludeDataRef(FMS_VR_SET);
		iface.excludeDataRef(FMS_V1_SET);
		iface.excludeDataRef(FMS_V1);
		iface.excludeDataRef(FMS_V2_15);
		iface.excludeDataRef(LEGS_MOD_ACTIVE);
		iface.excludeDataRef(MISSED_APP_WPT_IDX);
		iface.excludeDataRef(ILS_DISABLE);
		iface.excludeDataRef(VNAV_IDX);
		iface.excludeDataRef(VNAV_IDX_MOD);
		iface.excludeDataRef(INTDIR_ACT);
		iface.excludeDataRef(INTDIR_CRS);
		iface.excludeDataRef(INTDIR_CRS2);
		iface.excludeDataRef(DIR_SEG1_END_ANGLE);
		iface.excludeDataRef(DIR_SEG1_START_ANGLE);
		iface.excludeDataRef(DIR_SEG1_CTR_LAT);
		iface.excludeDataRef(DIR_SEG1_CTR_LON);
		iface.excludeDataRef(DIR_SEG1_RADIUS);
		iface.excludeDataRef(DIR_SEG1_TURN);
		iface.excludeDataRef(DIR_SEG3_END_ANGLE);
		iface.excludeDataRef(DIR_SEG3_START_ANGLE);
		iface.excludeDataRef(DIR_SEG3_CTR_LAT);
		iface.excludeDataRef(DIR_SEG3_CTR_LON);
		iface.excludeDataRef(DIR_SEG3_RADIUS);
		iface.excludeDataRef(DIR_SEG3_TURN);
		iface.excludeDataRef(GP_ERR_PFD);
		
		iface.unObserveDataRef(ANP, fms_data);
		iface.unObserveDataRef(VANP, fms_data);
		iface.unObserveDataRef(RNP, fms_data);
		iface.unObserveDataRef(VRNP, fms_data);
		iface.unObserveDataRef(XTRACK_ND, fms_data);
		iface.unObserveDataRef(TRANS_ALT, fms_data);
		iface.unObserveDataRef(TRANS_LVL, fms_data);
		iface.unObserveDataRef(FPLN_NAV_ID, fms_data);
		iface.unObserveDataRef(FMS_VREF, fms_data);
		iface.unObserveDataRef(FMS_VREF_BUGS, fms_data);
		iface.unObserveDataRef(FMS_V1R_BUGS, fms_data);
		iface.unObserveDataRef(FPLN_NAV_ID_ETA, fms_data);
		iface.unObserveDataRef(RW_FAC_ID2, fms_data);
		iface.unObserveDataRef(RW_DIST2, fms_data);
		iface.unObserveDataRef(FPLN_NAV_ID_DIST, fms_data);
		iface.unObserveDataRef(FMS_VR_SET, fms_data);
		iface.unObserveDataRef(FMS_V1_SET, fms_data);
		iface.unObserveDataRef(FMS_V1, fms_data);
		iface.unObserveDataRef(FMS_V2_15, fms_data);
		iface.unObserveDataRef(LEGS_MOD_ACTIVE, fms_data);
		iface.unObserveDataRef(MISSED_APP_WPT_IDX, fms_data);
		iface.unObserveDataRef(ILS_DISABLE, fms_data);
		iface.unObserveDataRef(VNAV_IDX, fms_data);
		iface.unObserveDataRef(VNAV_IDX_MOD, fms_data);
		iface.unObserveDataRef(INTDIR_ACT, fms_data);
		iface.unObserveDataRef(INTDIR_CRS, fms_data);
		iface.unObserveDataRef(INTDIR_CRS2, fms_data);
		iface.unObserveDataRef(DIR_SEG1_END_ANGLE, fms_data);
		iface.unObserveDataRef(DIR_SEG1_START_ANGLE, fms_data);
		iface.unObserveDataRef(DIR_SEG1_CTR_LAT, fms_data);
		iface.unObserveDataRef(DIR_SEG1_CTR_LON, fms_data);
		iface.unObserveDataRef(DIR_SEG1_RADIUS, fms_data);
		iface.unObserveDataRef(DIR_SEG1_TURN, fms_data);
		iface.unObserveDataRef(DIR_SEG3_END_ANGLE, fms_data);
		iface.unObserveDataRef(DIR_SEG3_START_ANGLE, fms_data);
		iface.unObserveDataRef(DIR_SEG3_CTR_LAT, fms_data);
		iface.unObserveDataRef(DIR_SEG3_CTR_LON, fms_data);
		iface.unObserveDataRef(DIR_SEG3_RADIUS, fms_data);
		iface.unObserveDataRef(DIR_SEG3_TURN, fms_data);
		iface.unObserveDataRef(GP_ERR_PFD, fms_data);
		
		for(int i = 0; i < 2; i++) {
			iface.excludeDataRef(FPLN_ACTIVE[i]);
			iface.unObserveDataRef(FPLN_ACTIVE[i], fms_data);
			iface.excludeDataRef(PFD_GP_PATH[i]);
			iface.unObserveDataRef(PFD_GP_PATH[i], fms_data);
			iface.excludeDataRef(PFD_FAC_HORIZONTAL[i]);
			iface.unObserveDataRef(PFD_FAC_HORIZONTAL[i], fms_data);
			iface.excludeDataRef(IAN_INFO[i]);
			iface.unObserveDataRef(IAN_INFO[i], fms_data);
			iface.excludeDataRef(FAC_EXP[i]);
			iface.unObserveDataRef(FAC_EXP[i], fms_data);
			iface.excludeDataRef(ILS_ROTATE0[i]);
			iface.unObserveDataRef(ILS_ROTATE0[i], fms_data);
			iface.excludeDataRef(ILS_ROTATE0_MOD[i]);
			iface.unObserveDataRef(ILS_ROTATE0_MOD[i], fms_data);
			iface.excludeDataRef(ILS_ROTATE[i]);
			iface.unObserveDataRef(ILS_ROTATE[i], fms_data);
			iface.excludeDataRef(ILS_ROTATE_MOD[i]);
			iface.unObserveDataRef(ILS_ROTATE_MOD[i], fms_data);
			iface.excludeDataRef(ILS_SHOW0[i]);
			iface.unObserveDataRef(ILS_SHOW0[i], fms_data);
			iface.excludeDataRef(ILS_SHOW0_MOD[i]);
			iface.unObserveDataRef(ILS_SHOW0_MOD[i], fms_data);
			iface.excludeDataRef(ILS_SHOW[i]);
			iface.unObserveDataRef(ILS_SHOW[i], fms_data);
			iface.excludeDataRef(ILS_SHOW_MOD[i]);
			iface.unObserveDataRef(ILS_SHOW_MOD[i], fms_data);
			iface.excludeDataRef(NAV_TXT1[i]);
			iface.unObserveDataRef(NAV_TXT1[i], fms_data);
			iface.excludeDataRef(NAV_TXT1a[i]);
			iface.unObserveDataRef(NAV_TXT1a[i], fms_data);
			iface.excludeDataRef(NAV_TXT2[i]);
			iface.unObserveDataRef(NAV_TXT2[i], fms_data);
			iface.excludeDataRef(LEGS_STEP_CTR_IDX[i]);
			iface.unObserveDataRef(LEGS_STEP_CTR_IDX[i], fms_data);
		}
		
		iface.excludeDataRef(LEGS_RAD_LAT);
		iface.excludeDataRef(LEGS_RAD_LON);
		iface.excludeDataRef(LEGS_RAD_TURN);
		iface.excludeDataRef(LEGS_RADIUS);
		iface.excludeDataRef(LEGS_SEG1_RADIUS);
		iface.excludeDataRef(LEGS_SEG1_CTR_LAT);
		iface.excludeDataRef(LEGS_SEG1_CTR_LON);
		iface.excludeDataRef(LEGS_SEG1_TURN);
		iface.excludeDataRef(LEGS_SEG1_START_ANGLE);
		iface.excludeDataRef(LEGS_SEG1_END_ANGLE);
		iface.excludeDataRef(LEGS_SEG3_START_ANGLE);
		iface.excludeDataRef(LEGS_SEG3_END_ANGLE);
		iface.excludeDataRef(LEGS_SEG3_TURN);
		iface.excludeDataRef(LEGS_SEG3_RADIUS);
		iface.excludeDataRef(LEGS_SEG3_CTR_LAT);
		iface.excludeDataRef(LEGS_SEG3_CTR_LON);
		iface.excludeDataRef(LEGS_AF_BEG);
		iface.excludeDataRef(LEGS_AF_END);
		iface.excludeDataRef(LEGS_BYPASS);

		iface.unObserveDataRef(LEGS_RAD_LAT, active_route);
		iface.unObserveDataRef(LEGS_RAD_LON, active_route);
		iface.unObserveDataRef(LEGS_RAD_TURN, active_route);
		iface.unObserveDataRef(LEGS_RADIUS, active_route);
		iface.unObserveDataRef(LEGS_SEG1_RADIUS, active_route);
		iface.unObserveDataRef(LEGS_SEG1_CTR_LAT, active_route);
		iface.unObserveDataRef(LEGS_SEG1_CTR_LON, active_route);
		iface.unObserveDataRef(LEGS_SEG1_TURN, active_route);
		iface.unObserveDataRef(LEGS_SEG1_START_ANGLE, active_route);
		iface.unObserveDataRef(LEGS_SEG1_END_ANGLE, active_route);
		iface.unObserveDataRef(LEGS_SEG3_START_ANGLE, active_route);
		iface.unObserveDataRef(LEGS_SEG3_END_ANGLE, active_route);
		iface.unObserveDataRef(LEGS_SEG3_TURN, active_route);
		iface.unObserveDataRef(LEGS_SEG3_RADIUS, active_route);
		iface.unObserveDataRef(LEGS_SEG3_CTR_LAT, active_route);
		iface.unObserveDataRef(LEGS_SEG3_CTR_LON, active_route);
		iface.unObserveDataRef(LEGS_AF_BEG, active_route);
		iface.unObserveDataRef(LEGS_AF_END, active_route);
		iface.unObserveDataRef(LEGS_BYPASS, active_route);
		
		iface.excludeDataRef(LEGS);
		iface.excludeDataRef(LEGS_LAT);
		iface.excludeDataRef(LEGS_LON);
//		iface.excludeDataRef(LEGS_ALT_CALC);
		iface.excludeDataRef(LEGS_ALT_REST1);
		iface.excludeDataRef(LEGS_ALT_REST2);
		iface.excludeDataRef(LEGS_DIST);
		iface.excludeDataRef(LEGS_ALT_REST_TYPE);
		iface.excludeDataRef(LEGS_ETA);
		iface.excludeDataRef(LEGS_HOLD_DIST);
		iface.excludeDataRef(LEGS_HOLD_TIME);
		iface.excludeDataRef(LEGS_CRS_MAG);
		iface.excludeDataRef(LEGS_SPD);
		iface.excludeDataRef(LEGS_TYPE);

		iface.unObserveDataRef(LEGS, active_route);
		iface.unObserveDataRef(LEGS_LAT, active_route);
		iface.unObserveDataRef(LEGS_LON, active_route);
//		iface.unObserveDataRef(LEGS_ALT_CALC, active_route);
		iface.unObserveDataRef(LEGS_ALT_REST1, active_route);
		iface.unObserveDataRef(LEGS_ALT_REST2, active_route);
		iface.unObserveDataRef(LEGS_DIST, active_route);
		iface.unObserveDataRef(LEGS_ALT_REST_TYPE, active_route);
		iface.unObserveDataRef(LEGS_ETA, active_route);		
		iface.unObserveDataRef(LEGS_HOLD_DIST, active_route);
		iface.unObserveDataRef(LEGS_HOLD_TIME, active_route);
		iface.unObserveDataRef(LEGS_CRS_MAG, active_route);
		iface.unObserveDataRef(LEGS_SPD, active_route);
		iface.unObserveDataRef(LEGS_TYPE, active_route);

		iface.excludeDataRef(LEGS_2);
		iface.excludeDataRef(LEGS_LAT_2);
		iface.excludeDataRef(LEGS_LON_2);
//		iface.excludeDataRef(LEGS_ALT_CALC_2);
		iface.excludeDataRef(LEGS_ALT_REST1_2);
		iface.excludeDataRef(LEGS_ALT_REST2_2);
		iface.excludeDataRef(LEGS_ALT_REST_TYPE_2);
		iface.excludeDataRef(LEGS_HOLD_DIST_2);
		iface.excludeDataRef(LEGS_HOLD_TIME_2);
		iface.excludeDataRef(LEGS_CRS_MAG_2);
		iface.excludeDataRef(LEGS_SPD_2);
		iface.excludeDataRef(LEGS_RAD_LAT_2);
		iface.excludeDataRef(LEGS_RAD_LON_2);
		iface.excludeDataRef(LEGS_RAD_TURN_2);
		iface.excludeDataRef(LEGS_TYPE_2);

		iface.unObserveDataRef(LEGS_2, modified_route);
		iface.unObserveDataRef(LEGS_LAT_2, modified_route);
		iface.unObserveDataRef(LEGS_LON_2, modified_route);
//		iface.unObserveDataRef(LEGS_ALT_CALC_2, modified_route);
		iface.unObserveDataRef(LEGS_ALT_REST1_2, modified_route);
		iface.unObserveDataRef(LEGS_ALT_REST2_2, modified_route);
		iface.unObserveDataRef(LEGS_ALT_REST_TYPE_2, modified_route);
		iface.unObserveDataRef(LEGS_HOLD_DIST_2, modified_route);
		iface.unObserveDataRef(LEGS_HOLD_TIME_2, modified_route);
		iface.unObserveDataRef(LEGS_CRS_MAG_2, modified_route);
		iface.unObserveDataRef(LEGS_SPD_2, modified_route);
		iface.unObserveDataRef(LEGS_RAD_LAT_2, modified_route);
		iface.unObserveDataRef(LEGS_RAD_LON_2, modified_route);
		iface.unObserveDataRef(LEGS_RAD_TURN_2, modified_route);
		iface.unObserveDataRef(LEGS_TYPE_2, modified_route);
		
	}
}