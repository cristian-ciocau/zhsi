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

import org.andreels.zhsi.navdata.NavigationObject;
import org.andreels.zhsi.navdata.NavigationRadio;
import org.andreels.zhsi.navdata.RadioNavBeacon;

public interface XPData {
			
	// autopilot
	public float mcp_hdg();
	public float mcp_speed_kts2(String pilot);
	public int mcp_alt();
	public float mcp_vvi();
	public boolean alt_disagree();
	public boolean ias_disagree();
	public int course(String pilot);
	public int back_course(String pilot);
	public int pfd_spd_mode();
	public boolean cmd_status(String pilot);
	public boolean fd_status(String pilot);
	public boolean fd_mode();
	public float ils_pointer_disable();
	public boolean fd_pitch_show(String pilot);
	public boolean fd_roll_show(String pilot);
	public boolean fd_rec(String pilot);
	public boolean rec_thr_modes();
	public boolean rec_thr2_modes();
	public int pfd_hdg_mode();
	public boolean rec_hdg_mode();
	public int pfd_hdg_mode_arm();
	public int pfd_alt_mode();
	public boolean rec_alt_modes();
	public int pfd_alt_mode_arm();
	public float ap_fd_roll_deg();
	public float ap_fd_pitch_deg();
	public boolean single_ch();
	public boolean single_ch_g();
	public boolean single_ch_rec_mode();
	public int flare_status();
	public int pfd_mode(String pilot);
	public boolean vs_mode();
	public float mcp_speed_kts_mach();
	public boolean mcp_speed_is_mach();
	public float nps_deviation();
	public float fac_horizont();
	public boolean cws_r_status();
	public boolean cws_p_status();
	
	// aircraft
	public boolean runway_show(String pilot);
	public float runway_x(String pilot);
	public float runway_y(String pilot);
	public float airspeed(String pilot);
	public float airspeed_mach();
	public float airspeed_acceleration();
	public float groundspeed();
	public float true_airspeed_knots();
	public float aoa();
	public float heading(String pilot);
	public float magnetic_variation();
	public float track();
	public float altitude(String pilot);
	public float radio_alt_feet();
	public float latitude();
	public float longitude();
	public float roll();
	public float pitch();
	public float slip_deg();
	public float vvi(String pilot);
	public float max_speed();
	public float max_maneuver_speed();
	public boolean max_maneuver_speed_show();
	public float min_speed();
	public boolean min_speed_show();
	public float min_maneuver_speed();
	public boolean min_maneuver_speed_show();
	public boolean on_gound();
	public boolean gear_up();
	public float yaw_rotation();
//	public float fpv_alpha();
//	public float fpv_beta();
	public float flaps_1();
	public boolean flaps_1_show();
	public float flaps_2();
	public boolean flaps_2_show();
	public float flaps_5();
	public boolean flaps_5_show();
	public float flaps_10();
	public boolean flaps_10_show();
	public float flaps_15();
	public boolean flaps_15_show();
	public float flaps_25();
	public boolean flaps_25_show();
	public float flaps_up();
	public boolean flaps_up_show();
	public float stall();
	public boolean stall_show();
	public boolean spd_80_show();
	
	// avionics
	public boolean power_on();
	public boolean dc_standby_on();
	public int vspeed();
	public boolean vspeed_digit_show(String pilot);
	public boolean v1_off_show(String pilot);
	public boolean vspeed_vref_show(String pilot);
	public int vspeed_mode();
	public int no_vspd();
	public boolean nd_vert_path();
	public boolean pfd_trk_path(String pilot);
	public boolean pfd_vert_path(String pilot);
	public boolean pfd_vert_path2(String pilot);
	public int pfd_gls_loc_ghost(String pilot);
	public int pfd_gls_gs_ghost(String pilot);
	public int pfd_fac_ghost(String pilot);
	public int pfd_gp_ghost(String pilot);
	public int pfd_gs_ghost(String pilot);
	public int pfd_loc_ghost(String pilot);
	public boolean pfd_loc_exp(String pilot);
	public boolean pfd_gls_exp(String pilot);
	public int mmr_act_mode(String pilot);
	public float vnav_err();
	public float rwy_altitude();
	public boolean pfd_rwy_show();
	public boolean hdg_bug_line(String pilot);
	public float green_arc(String pilot);
	public boolean green_arc_show(String pilot);
	public int fmc_source(String pilot);
	public int time_zulu_hrs();
	public int time_zulu_min();
	public int time_local_hrs();
	public int time_local_min();
	public int time_day();
	public int time_month();
	public int chrono_mode(String pilot);
	public float chrono_needle(String pilot);
	public int chrono_minute(String pilot);
	public int chrono_display_mode(String pilot);
	public int chrono_et_mode(String pilot);
	public int chrono_et_hrs(String pilot);
	public int chrono_et_min(String pilot);
		
	// tcas
	public float tcas_x(String pilot, int index);
	public float tcas_y(String pilot, int index);
	public String tcas_alt(int index);
	public String tcas_alt_fo(int index);
	public boolean tcas_ai_show(String pilot, int index);
	public boolean tcas_show(String pilot);
	public boolean tcas_test_show(String pilot);
	public boolean tcas_fail_show(String pilot);
	public boolean tcas_off_show(String pilot);
	public float tcas_type_show(String pilot, int index);
	public float tcas_alt_dn_up_show(String pilot, int index);
	public float tcas_arrow_dn_up_show(String pilot, int index);
	public boolean tcas_traffic_ra(String pilot);
	public boolean tcas_traffic_ta(String pilot);
	public boolean tcas_pfd_ra_dn();
	public boolean tcas_pfd_ra_up();
	public float tcas_pfd_ra_dn2(String pilot);
	public float tcas_pfd_ra_up2(String pilot);
	
	// environment
	public float wind_speed_knots();	
	public float wind_heading();
	public float outside_air_temp_c();
	
	// efis
	public float baro_sel_in_hg(String pilot);
	public boolean baro_in_hpa(String pilot);
	public boolean baro_std_set(String pilot);
	public boolean baro_sel_show(String pilot);
	public int map_range(String pilot);
	public int map_mode(String pilot);
	public boolean map_mode_app(String pilot);
	public boolean map_mode_vor(String pilot);
	public boolean map_mode_map(String pilot);
	public boolean map_mode_pln(String pilot);
	public boolean efis_apt_mode(String pilot);
	public boolean efis_sta_mode(String pilot);
	public boolean efis_wpt_mode(String pilot);
	public boolean tcas_on(String pilot);
	public int vor1_pos(String pilot);
	public int vor2_pos(String pilot);
	public float minimums(String pilot);
	public int minimums_mode(String pilot);
	public float baro_min(String pilot);
	public int baro_min_show(String pilot);
	public boolean efis_terr_on(String pilot);
	public int efis_terr(String pilot);
	public boolean efis_wxr_on(String pilot);
	public int efis_wxr(String pilot);
	public boolean efis_ctr_map(String pilot);
	public int efis_map_ctr(String pilot);
	public boolean efis_vsd_map(String pilot);
	public boolean efis_data_on(String pilot);
	public boolean efis_mtrs_on(String pilot);
	public boolean efis_fpv_show(String pilot);
	public float efis_fpv_horiz(String pilot);
	public float efis_fpv_vert(String pilot);
	
	// fms
	public float anp();
	public float vanp();
	public float rnp();
	public float vrnp();
	public float xtrack_nd();
	public int trans_alt();
	public int trans_lvl();
	public String active_waypoint();
	public boolean fpln_active();
	public int pfd_gp_path(String pilot);
	public int pfd_fac_horizontal(String pilot);
	public int ian_info(String pilot);
	public int fac_exp(String pilot);
	public float fms_vref();
	public String fpln_nav_id_eta(); 
	public String rw_fac_id2(); 
	public float rw_dist2(); 
	public float fpln_nav_id_dist();
	public float fms_vr_set();
	public float fms_v1_set();
	public float fms_v1();
	public float fms_v2_15();
	public boolean fms_vref_bugs();
	public boolean fms_v1r_bugs();
	public int approach_speed();
	public int approach_flaps();
	public boolean legs_mod_active();
	public int legs_step_ctr_idx(String pilot);
	public float ils_rotate0(String pilot);
	public float ils_rotate0_mod(String pilot);
	public float ils_rotate(String pilot);
	public float ils_rotate_mod(String pilot);
	public boolean ils_show0(String pilot);
	public boolean ils_show0_mod(String pilot);
	public boolean ils_show(String pilot);
	public boolean ils_show_mod(String pilot);
	public String nav_txt1(String pilot);
	public String nav_txt1a(String pilot);
	public String nav_txt2(String pilot);
	public int missed_app_wpt_idx();
	//active waypoint curve data	
	public float[] legs_rad_lat();
	public float[] legs_rad_lon();
	public float[] legs_rad_turn();
	public float[] legs_radius();
	public float[] legs_seg1_radius();
	public float[] legs_seg1_ctr_lat();
	public float[] legs_seg1_ctr_lon();
	public float[] legs_seg1_turn();
	public float[] legs_seg1_start_angle();
	public float[] legs_seg1_end_angle();
	public float[] legs_seg3_start_angle();
	public float[] legs_seg3_end_angle();
	public float[] legs_seg3_turn();
	public float[] legs_seg3_radius();
	public float[] legs_seg3_ctr_lat();
	public float[] legs_seg3_ctr_lon();
	public float[] legs_af_beg();
	public float[] legs_af_end();
	public float[] legs_bypass();
	//active waypoint data
	public int num_of_wpts();
	public String[] waypoints();
	public int vnav_idx();
	public String origin_arpt();
	public String dest_arpt();
	public float[] legs_lat();
	public float[] legs_lon();
//	public float[] legs_alt_calc();
	public float[] legs_alt_rest1();
	public float[] legs_alt_rest2();
	public float[] legs_dist();
	public float[] legs_alt_rest_type();
	public float[] legs_eta();
	public float[] legs_hold_dist();
	public float[] legs_hold_time();
	public float[] legs_crs_mag();
	public float[] legs_spd();
	public float[] legs_type();
	//modified waypoint data
	public int mod_num_of_wpts();
	public String[] mod_waypoints();
	public int mod_vnav_idx();
	public String mod_origin_arpt();
	public String mod_dest_arpt();
	public float[] mod_legs_lat();
	public float[] mod_legs_lon();
//	public float[] mod_legs_alt_calc();
	public float[] mod_legs_alt_rest1();
	public float[] mod_legs_alt_rest2();
	public float[] mod_legs_alt_rest_type();
	public float[] mod_legs_hold_dist();
	public float[] mod_legs_hold_time();
	public float[] mod_legs_crs_mag();
	public float[] mod_legs_spd();
	public float[] mod_legs_rad_lat();
	public float[] mod_legs_rad_lon();
	public float[] mod_legs_rad_turn();
	public float[] mod_legs_type();
	public int ils_disable();

	public boolean intdir_act();
	public int intdir_crs();
	public int intdir_crs2();
	public float dir_seg1_end_angle();
	public float dir_seg1_start_angle();
	public float dir_seg1_ctr_lat();
	public float dir_seg1_ctr_lon();
	public float dir_seg1_radius();
	public int dir_seg1_turn();
	public float dir_seg3_end_angle();
	public float dir_seg3_start_angle();
	public float dir_seg3_ctr_lat();
	public float dir_seg3_ctr_lon();
	public float dir_seg3_radius();
	public int dir_seg3_turn();
	public float gp_err_pfd();
	public float fms_track();
	
	// navdata
	public boolean irs_aligned();
	public boolean apt_enable(String pilot, int index);
	public boolean efis_disagree(String pilot);
	public String tc_id(String pilot);
	public boolean tc_show(String pilot);
	public float tc_x(String pilot);
	public float tc_y(String pilot);
	public String decel_id(String pilot);
	public boolean decel_show(String pilot);
	public float decel_x(String pilot);
	public float decel_y(String pilot);
	public boolean decel_def_show(String pilot);
	public float decel_def_x(String pilot);
	public float decel_def_y(String pilot);
	public boolean decel_def2_show(String pilot);
	public float decel_def2_x(String pilot);
	public float decel_def2_y(String pilot);
	public String td_id(String pilot);
	public boolean td_show(String pilot);
	public float td_x(String pilot);
	public float td_y(String pilot);
	public String ed_id(String pilot);
	public boolean ed_show(String pilot);
	public float ed_x(String pilot);
	public float ed_y(String pilot);
	public boolean[] fix_show(String pilot);
	public String[] fix_id(String pilot);
	public float[] fix_lat();
	public float[] fix_lon();
	public float[] fix_type();
	public float[] fix_dist_0();
	public float[] fix_dist_1();
	public float[] fix_dist_2();
	public float[] fix_rad_dist_0();
	public float[] fix_rad_dist_1();
	public float[] fix_rad_dist_2();
	public String fix_rad00_0();
	public String fix_rad00_1();
	public String fix_rad00_2();
	public String fix_rad01_0();
	public String fix_rad01_1();
	public String fix_rad01_2();
	public String fix_rad02_0();
	public String fix_rad02_1();
	public String fix_rad02_2();
	public String fix_rad03_0();
	public String fix_rad03_1();
	public String fix_rad03_2();
	public String fix_rad04_0();
	public String fix_rad04_1();
	public String fix_rad04_2();
	public float[] fix_rad_dist_0a();
	public float[] fix_rad_dist_1a();
	public float[] fix_rad_dist_2a();
	public float ils_x(String pilot);
	public float ils_x_mod(String pilot);
	public float ils_y(String pilot);
	public float ils_y_mod(String pilot);
	public float ils_x0(String pilot);
	public float ils_x0_mod(String pilot);
	public float ils_y0(String pilot);
	public float ils_y0_mod(String pilot);
	public String ils_runway(String pilot);
	public String ils_runway_mod(String pilot);
	public String ils_runway0(String pilot);
	public String ils_runway0_mod(String pilot);
	public String irs_left_string();
	public String irs_right_string();
	public boolean irs_decimals_show();

	// radios
	public float nav1_freq();
	public String nav1_id();
	public float nav1_rel_bearing();
	public float nav1_dme_nm();
	public float nav1_time_sec();
	public int nav1_fromto();
	public float nav1_hdef_dot();
	public float nav1_vdef_dot();
	public int nav1_cdi();
	public float adf1_freq();
	public String adf1_id();
	public float adf1_rel_bearing();
	public float adf1_dme_nm();
	public float nav1_course();
	public float nav2_freq();
	public String nav2_id();
	public float nav2_rel_bearing();
	public float nav2_dme_nm();
	public float nav2_time_sec();
	public int nav2_fromto();
	public float nav2_hdef_dot();
	public float nav2_vdef_dot();
	public int nav2_cdi();
	public float adf2_freq();
	public String adf2_id();
	public float adf2_rel_bearing();
	public float adf2_dme_nm();
	public float nav2_course();
	public int nav1_type();
	public int nav2_type();
	public float rmi_arrow1();
	public float rmi_arrow2();
	public float rmi_arrow1_no_avail();
	public float rmi_arrow2_no_avail();
	public int inner_marker_lit();
	public int middle_marker_lit();
	public int outer_marker_lit();
	public int nav1_flag_glideslope();
	public int nav2_flag_glideslope();
	public int nav1_display_vertical();
	public int nav2_display_vertical();	
	public int nav1_display_horizontal();
	public int nav2_display_horizontal();	
	public float gls1_vert_dev();	
	public float gls2_vert_dev();	
	public float gls1_horz_dev();	
	public float gls2_horz_dev();
	public int gls1_type();	
	public int gls2_type();	
	public int gls1_active();	
	public int gls2_active();
	public String gls1_id();
	public String gls2_id();	
	public float gls1_dme();
	public float gls2_dme();
	public int nav1_no_id();
	public int nav2_no_id();	

	// engines
	public float eng1_n1();
	public float eng2_n1();
	public float eng1_n2();
	public float eng2_n2();
	public float eng1_n1_req();
	public float eng2_n1_req();
	public float eng1_egt();
	public float eng2_egt();
	public float fuel_qty_kgs_1();
	public float fuel_qty_kgs_2();
	public float fuel_qty_kgs_c();
	public float fuel_qty_lbs_1();
	public float fuel_qty_lbs_2();
	public float fuel_qty_lbs_c();
	public float fuel_flow1();
	public float fuel_flow2();
	public float n1_percent1();
	public float n1_percent2();
	public boolean eng_start_value_1();
	public boolean eng_start_value_2();
	public boolean eng_oil_bypass_1();
	public boolean eng_oil_bypass_2();
	public boolean oil_pressure_annun_1();
	public boolean oil_pressure_annun_2();
	public float oil_qty_1();
	public float oil_qty_2();
	public float oil_temp_c_1();
	public float oil_temp_c_2();
	public float eng_oil_press_1();
	public float eng_oil_press_2();
	public float eng_n1_bug_1();
	public float eng_n1_bug_2();
	public String n1_mode();
	public boolean reverser1_moved();
	public boolean reverser1_deployed();
	public boolean reverser2_moved();
	public boolean reverser2_deployed();
	public float eng1_tai();
	public float eng2_tai();
	public boolean egt_redline1();
	public boolean egt_redline2();
	public boolean eicas_ff1();
	public boolean eicas_ff2();
	public boolean eicas_oil_press1();
	public boolean eicas_oil_press2();
	public boolean eicas_oil_temp1();
	public boolean eicas_oil_temp2();
	public boolean fuel_flow_used_show();
	public float fuel_flow_used1();
	public float fuel_flow_used2();
	public boolean eng1_out();
	public boolean eng2_out();
		
	// buttons and switches
	public int main_panel_du(String pilot);	
	public int lower_panel_du(String pilot);
	public float du_brightness(String title);
	public boolean fuel_tank_ctr1_pos();
	public boolean fuel_tank_ctr2_pos();
	public boolean fuel_tank_lft1_pos();
	public boolean fuel_tank_lft2_pos();
	public boolean fuel_tank_rgt1_pos();
	public boolean fuel_tank_rgt2_pos();
	public int transponder_pos();
	public int ac_power_knob();
	public int dc_power_knob();
	
	// systems
	public boolean mfd_eng();
	public boolean mfd_sys();
	public boolean sim_paused();
	public int rel_gls();
	public float brake_temp_left_in();
	public float brake_temp_left_out();
	public float brake_temp_right_in();
	public float brake_temp_right_out();	
	public float hyd_a_qty();
	public float hyd_b_qty();
	public int vhf_nav_source();
	public int hyd_a_pressure();
	public int hyd_b_pressure();
	public int flt_alt();
	public int land_alt();
	public float left_elevator_deflection();
	public float right_elevator_deflection();
	public float left_ail_deflection();
	public float right_ail_deflection();
	public float rudder_deflection();
	public float left_splr_deflection();
	public float right_splr_deflection();
	public float stab_trim();
	public float rudder_trim();
	public float flaps_l_deflection();
	public float flaps_r_deflection();
	public String xraas_message();
	public int xraas_color();
	public boolean windsheer();
	public boolean pullup();
	public float thr_lvr1();
	public float thr_lvr2();
	
	// isfd
	public float isfd_altitude();
	public float isfd_alt_baro();
	public int isfd_mode();
	public boolean isfd_std_mode();
	public float isfd_init_time();
		
	// electrical
	public int standby_bat_pos();
	public float ac_freq_value();
	public float ac_volt_value();
	public float ac_amp_value();
	public float dc_volt_value();
	public float dc_amp_value();

	// other
	public float rough_distance_to(NavigationObject nav_object);
	public RadioNavBeacon get_tuned_navaid(int bank, String pilot);
	public NavigationRadio get_nav_radio(int bank);
	
}