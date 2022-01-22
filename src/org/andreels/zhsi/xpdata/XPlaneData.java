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

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.navdata.CoordinateSystem;
import org.andreels.zhsi.navdata.NavigationObject;
import org.andreels.zhsi.navdata.NavigationRadio;
import org.andreels.zhsi.navdata.RadioNavBeacon;
import org.andreels.zhsi.navdata.RadioNavigationObject;

public class XPlaneData implements XPData {

	XPDataRepositry xpdrepo;
	ZHSIPreferences preferences;

	private NavigationRadio nav1_radio;
	private NavigationRadio nav2_radio;
	private NavigationRadio adf1_radio;
	private NavigationRadio adf2_radio;

	public XPlaneData() {
		this.xpdrepo = XPDataRepositry.getInstance();
		this.preferences = ZHSIPreferences.getInstance();

		this.nav1_radio = new NavigationRadio(1, NavigationRadio.RADIO_TYPE_NAV, // radio_type
				nav1_freq(), // id_freq // nav1_freq()
				nav1_id(), // id_nav_id // sim/cockpit2/radios/indicators/nav1_nav_id
				nav1_rel_bearing(), // id_deflection // //sim/cockpit/radios/nav1_dir_degt
				nav1_dme_nm(), // id_dme_distance //sim/cockpit2/radios/indicators/nav1_dme_distance_nm
				nav1_time_sec(), // id_dme_time // //sim/cockpit/radios/nav1_dme_time_secs
				nav1_course(), // id_course //
				nav1_fromto(), // id_fromto //sim/cockpit/radios/nav1_fromto
				nav1_hdef_dot(), // id_cdi_dots // sim/cockpit2/radios/indicators/nav1_hdef_dots_pilot
				nav1_cdi(), // id_gs_active // sim/cockpit/radios/nav1_CDI // Are we receiving an expected glide slop for nav1
				nav1_vdef_dot(), // id_gs_dots //sim/cockpit2/radios/indicators/nav1_vdef_dots_pilot
				this);

		this.nav2_radio = new NavigationRadio(2, NavigationRadio.RADIO_TYPE_NAV, // radio_type
				nav2_freq(), // id_freq // nav1_freq()
				nav2_id(), // id_nav_id // sim/cockpit2/radios/indicators/nav2_nav_id
				nav2_rel_bearing(), // id_deflection // //sim/cockpit/radios/nav2_dir_degt
				nav2_dme_nm(), // id_dme_distance //sim/cockpit2/radios/indicators/nav2_dme_distance_nm
				nav2_time_sec(), // id_dme_time // //sim/cockpit/radios/nav2_dme_time_secs
				nav2_course(), // id_course // 
				nav2_fromto(), // id_fromto //sim/cockpit/radios/nav2_fromto
				nav2_hdef_dot(), // id_cdi_dots // sim/cockpit2/radios/indicators/nav2_hdef_dots_pilot
				nav2_cdi(), // id_gs_active // sim/cockpit/radios/nav2_CDI // Are we receiving an expected glide slop for nav1
				nav2_vdef_dot(), // id_gs_dots //sim/cockpit2/radios/indicators/nav2_vdef_dots_pilot
				this);

		this.adf1_radio = new NavigationRadio(1, NavigationRadio.RADIO_TYPE_ADF, // radio_type
				adf1_freq(), // id_freq //sim/cockpit2/radios/actuators/adf1_frequency_hz
				adf1_id(), // id_nav_id //sim/cockpit2/radios/indicators/adf1_nav_id
				adf1_rel_bearing(), // id_deflection //sim/cockpit/radios/adf1_dir_degt
				adf1_dme_nm(), // wtf? //sim/cockpit2/radios/indicators/adf1_dme_distance_nm
				this);

		this.adf2_radio = new NavigationRadio(2, NavigationRadio.RADIO_TYPE_ADF, // radio_type
				adf2_freq(), // id_freq //sim/cockpit2/radios/actuators/adf2_frequency_hz
				adf2_id(), // id_nav_id //sim/cockpit2/radios/indicators/adf2_nav_id
				adf2_rel_bearing(), // id_deflection //sim/cockpit/radios/adf2_dir_degt
				adf2_dme_nm(), // wtf? //sim/cockpit2/radios/indicators/adf2_dme_distance_nm
				this);
	}

	public RadioNavBeacon get_tuned_navaid(int bank, String pilot) {

		NavigationRadio radio;
		RadioNavigationObject rnav_object;

		// find the VOR or NDB, depending
		radio = get_selected_radio(bank, pilot);
		if (radio != null && radio.receiving()) {

			rnav_object = radio.get_radio_nav_object();
			if ((rnav_object != null) && (rnav_object instanceof RadioNavBeacon)) {
				return (RadioNavBeacon) rnav_object;
			}
		}
		return null;

	}

	private NavigationRadio get_selected_radio(int bank, String pilot) {
		
		if (bank == 1) {
			if (vor1_pos(pilot) == 1) {
				return this.nav1_radio;
			} else if (vor1_pos(pilot) == -1) {
				return this.adf1_radio;
			} else {
				return null;
			}
		} else if (bank == 2) {
			if (vor2_pos(pilot) == 1) {
				return this.nav2_radio;
			} else if (vor2_pos(pilot) == -1) {
				return this.adf2_radio;
			} else {
				return null;
			}
		} else
			return null;
	}
	
	public boolean sim_paused() {
		return (this.xpdrepo.systems.sim_paused != 0);
	}
	
	public int rel_gls() {
		return this.xpdrepo.systems.rel_gls;
	}

	public boolean power_on() {
		return (this.xpdrepo.avionics.power != 0);
	}

	public boolean on_gound() {
		return (this.xpdrepo.aircraft.on_ground != 0);
	}

	public boolean dc_standby_on() {
		return (this.xpdrepo.avionics.dc_standby != 0);
	}
	
	public boolean runway_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.aircraft.runway_show[0] != 0);
		default:
			return (this.xpdrepo.aircraft.runway_show[1] != 0);
		}
	}
	
	public float runway_x(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.aircraft.runway_x[0];
		default:
			return this.xpdrepo.aircraft.runway_x[1];
		}
	}
	
	public float runway_y(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.aircraft.runway_y[0];
		default:
			return this.xpdrepo.aircraft.runway_y[1];
		}
	}

	public float airspeed(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.aircraft.airspeed[0];
		default:
			return this.xpdrepo.aircraft.airspeed[1];
		}
	}

	public float altitude(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.aircraft.altitude[0];
		} else {
			return this.xpdrepo.aircraft.altitude[1];
		}
	}

	public float heading(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.aircraft.heading[0];
		} else {
			return this.xpdrepo.aircraft.heading[1];
		}
	}

	public float roll() {
		return this.xpdrepo.aircraft.roll;
	}

	public float pitch() {
		return this.xpdrepo.aircraft.pitch;
	}
	
	public float slip_deg() {
		return this.xpdrepo.aircraft.slip_deg;
	}

	public float track() {
		return this.xpdrepo.aircraft.track;
	}

	public float magnetic_variation() {
		return this.xpdrepo.aircraft.magnetic_variation;
	}
	
	public float mcp_speed_kts2(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.autopilot.mcp_speed_kts2[0];
		} else {
			return this.xpdrepo.autopilot.mcp_speed_kts2[1];
		}
	}
	
	public float mcp_speed_kts_mach() {
		return this.xpdrepo.autopilot.mcp_speed_kts_mach;
	}
	
	public boolean mcp_speed_is_mach() {
		return (this.xpdrepo.autopilot.mcp_speed_is_mach != 0);
	}
	
	public float nps_deviation() {
		return this.xpdrepo.autopilot.nps_deviation;
	}
	
	public float fac_horizont() {
		return this.xpdrepo.autopilot.fac_horizont;
	}

	public float mcp_hdg() {
		return this.xpdrepo.autopilot.mcp_hdg;
	}

	public float latitude() {
		return this.xpdrepo.aircraft.lat_lon[0];
	}

	public float longitude() {
		return this.xpdrepo.aircraft.lat_lon[1];
	}
	
	public float vvi(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.aircraft.vvi[0];
		} else {
			return this.xpdrepo.aircraft.vvi[1];
		}
	}

	public float mcp_vvi() {
		return this.xpdrepo.autopilot.mcp_vvi;
	}

	public boolean cmd_status(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.autopilot.cmd_status[0] != 0);
		} else {
			return (this.xpdrepo.autopilot.cmd_status[1] != 0);
		}
	}

	public boolean fd_status(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.autopilot.fd_status[0] != 0);
		} else {
			return (this.xpdrepo.autopilot.fd_status[1] != 0);
		}
	}

	public boolean fd_pitch_show(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.autopilot.fd_pitch_show[0] != 0);
		} else {
			return (this.xpdrepo.autopilot.fd_pitch_show[1] != 0);
		}
	}
	
	public boolean fd_roll_show(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.autopilot.fd_roll_show[0] != 0);
		} else {
			return (this.xpdrepo.autopilot.fd_roll_show[1] != 0);
		}
	}
	
	public boolean fd_mode() {
		return (this.xpdrepo.autopilot.fd_mode != 0);
	}
	
	public float ils_pointer_disable() {
		return this.xpdrepo.autopilot.ils_pointer_disable;
	}

	public boolean fd_rec(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.autopilot.fd_rec_mode[0] != 0);
		} else {
			return (this.xpdrepo.autopilot.fd_rec_mode[1] != 0);
		}
	}

	public float du_brightness(String title) {

		switch (title) {
		case "Captain OutBoard Display":
			return this.xpdrepo.buttonsswitches.du_brightness[0];

		case "FO OutBoard Display":
			return this.xpdrepo.buttonsswitches.du_brightness[1];

		case "Captain InBoard Display":
			return this.xpdrepo.buttonsswitches.du_brightness[2];

		case "FO InBoard Display":
			return this.xpdrepo.buttonsswitches.du_brightness[3];
			
		case "Upper EICAS Display":
			return this.xpdrepo.buttonsswitches.du_brightness[4];
			
		case "Lower EICAS Display":
			return this.xpdrepo.buttonsswitches.du_brightness[5];
			
		case "Captain Chrono":
			return this.xpdrepo.buttonsswitches.du_brightness[8];
			
		case "IRS Display":
			return this.xpdrepo.buttonsswitches.du_brightness[12];
			
		case "First Officer Chrono":
			return this.xpdrepo.buttonsswitches.du_brightness[18];
			
		case "ISFD":
			return this.xpdrepo.buttonsswitches.du_brightness[20];
			
		default:
			return 1;
		}
	}
	
	public boolean tcas_ai_show(String pilot, int index) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.tcas.tcas_ai_show[0][index] != 0);
		default:
			return (this.xpdrepo.tcas.tcas_ai_show[1][index] != 0);
		}
	}
	
	public boolean tcas_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.tcas.tcas_show[0] != 0);
		default:
			return (this.xpdrepo.tcas.tcas_show[1] != 0);
		}
	}
	
	public boolean tcas_test_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.tcas.tcas_test_show[0] != 0);
		default:
			return (this.xpdrepo.tcas.tcas_test_show[1] != 0);
		}
	}
	
	public boolean tcas_fail_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.tcas.tcas_fail_show[0] != 0);
		default:
			return (this.xpdrepo.tcas.tcas_fail_show[1] != 0);
		}
	}
	
	public boolean tcas_off_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.tcas.tcas_off_show[0] != 0);
		default:
			return (this.xpdrepo.tcas.tcas_off_show[1] != 0);
		}
	}

	public float tcas_x(String pilot, int index) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.tcas.tcas_x[0][index];
		default:
			return this.xpdrepo.tcas.tcas_x[1][index];
		}
	}

	public float tcas_y(String pilot, int index) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.tcas.tcas_y[0][index];
		default:
			return this.xpdrepo.tcas.tcas_y[1][index];
		}
	}
	
	public float tcas_alt_dn_up_show(String pilot, int index) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.tcas.tcas_alt_dn_up_show[0][index];
		default:
			return this.xpdrepo.tcas.tcas_alt_dn_up_show[1][index];
		}
	}
	public float tcas_arrow_dn_up_show(String pilot, int index) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.tcas.tcas_arrow_dn_up_show[0][index];
		default:
			return this.xpdrepo.tcas.tcas_arrow_dn_up_show[1][index];
		}
	}
	
	public float tcas_type_show(String pilot, int index) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.tcas.tcas_type_show[0][index];
		default:
			return this.xpdrepo.tcas.tcas_type_show[1][index];
		}
	}

	public String tcas_alt(int index) {
		return this.xpdrepo.tcas.tcas_alt[index];
	}

	public String tcas_alt_fo(int index) {
		return this.xpdrepo.tcas.tcas_alt_fo[index];
	}

	public boolean alt_disagree() {
		return (this.xpdrepo.autopilot.alt_disagree != 0);
	}

	public boolean ias_disagree() {
		return (this.xpdrepo.autopilot.ias_disagree != 0);
	}

	public float baro_sel_in_hg(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.efis.baro_sel_in_hg[0];
		} else {
			return this.xpdrepo.efis.baro_sel_in_hg[1];
		}
	}

	public boolean baro_std_set(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.baro_std_set[0] != 0);
		} else {
			return (this.xpdrepo.efis.baro_std_set[1] != 0);
		}
	}
	
	public boolean baro_sel_show(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.baro_sel_show[0] != 0);
		} else {
			return (this.xpdrepo.efis.baro_sel_show[1] != 0);
		}
	}

	public boolean baro_in_hpa(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.baro_in_hpa[0] != 0);
		} else {
			return (this.xpdrepo.efis.baro_in_hpa[1] != 0);
		}
	}

	public float anp() {
		return this.xpdrepo.fms.anp;
	}
	
	public float vanp() {
		return this.xpdrepo.fms.vanp;
	}

	public float rnp() {
		return this.xpdrepo.fms.rnp;
	}
	
	public float vrnp() {
		return this.xpdrepo.fms.vrnp;
	}
	
	public float xtrack_nd() {
		return this.xpdrepo.fms.xtrack_nd;
	}

	public int trans_alt() {
		return this.xpdrepo.fms.trans_alt;
	}
	
	public int trans_lvl() {
		return this.xpdrepo.fms.trans_lvl;
	}

	public int map_range(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.efis.map_range[0];
		} else {
			return this.xpdrepo.efis.map_range[1];
		}
	}
	public int map_mode(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.efis.map_mode[0];
		} else {
			return this.xpdrepo.efis.map_mode[1];
		}
	}

	public boolean map_mode_app(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.map_mode[0] == 0);
		} else {
			return (this.xpdrepo.efis.map_mode[1] == 0);
		}
	}

	public boolean map_mode_vor(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.map_mode[0] == 1);
		} else {
			return (this.xpdrepo.efis.map_mode[1] == 1);
		}
	}

	public boolean map_mode_map(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.map_mode[0] == 2);
		} else {
			return (this.xpdrepo.efis.map_mode[1] == 2);
		}
	}

	public boolean map_mode_pln(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.map_mode[0] == 3);
		} else {
			return (this.xpdrepo.efis.map_mode[1] == 3);
		}
	}

	public boolean apt_enable(String pilot, int index) {
		if (pilot == "cpt") {
			return (this.xpdrepo.navdata.apt_enable[0][index] != 0);
		} else {
			return (this.xpdrepo.navdata.apt_enable[1][index] != 0);
		}
	}
	
	public boolean efis_disagree(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.navdata.efis_disagree[0] != 0.0);
		} else {
			return (this.xpdrepo.navdata.efis_disagree[1] != 0.0);
		}
	}
	
	public String tc_id(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.tc_id[0];
		} else {
			return this.xpdrepo.navdata.tc_id[1];
		}	
	}

	public boolean tc_show(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.navdata.tc_show[0] != 0);
		} else {
			return (this.xpdrepo.navdata.tc_show[1] != 0);
		}
	}
	
	public float tc_x(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.tc_x[0];
		} else {
			return this.xpdrepo.navdata.tc_x[1];
		}
	}
	
	public float tc_y(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.tc_y[0];
		} else {
			return this.xpdrepo.navdata.tc_y[1];
		}
	}

	public String decel_id(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.decel_id[0];
		} else {
			return this.xpdrepo.navdata.decel_id[1];
		}	
	}

	public boolean decel_show(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.navdata.decel_show[0] != 0);
		} else {
			return (this.xpdrepo.navdata.decel_show[1] != 0);
		}
	}

	public float decel_x(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.decel_x[0];
		} else {
			return this.xpdrepo.navdata.decel_x[1];
		}
	}

	public float decel_y(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.decel_y[0];
		} else {
			return this.xpdrepo.navdata.decel_y[1];
		}
	}
	
	public boolean decel_def_show(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.navdata.decel_def_show[0] != 0);
		} else {
			return (this.xpdrepo.navdata.decel_def_show[1] != 0);
		}
	}

	public float decel_def_x(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.decel_def_x[0];
		} else {
			return this.xpdrepo.navdata.decel_def_x[1];
		}
	}

	public float decel_def_y(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.decel_def_y[0];
		} else {
			return this.xpdrepo.navdata.decel_def_y[1];
		}
	}
	
	public boolean decel_def2_show(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.navdata.decel_def2_show[0] != 0);
		} else {
			return (this.xpdrepo.navdata.decel_def2_show[1] != 0);
		}
	}

	public float decel_def2_x(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.decel_def2_x[0];
		} else {
			return this.xpdrepo.navdata.decel_def2_x[1];
		}
	}

	public float decel_def2_y(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.decel_def2_y[0];
		} else {
			return this.xpdrepo.navdata.decel_def2_y[1];
		}
	}

	public String td_id(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.td_id[0];
		} else {
			return this.xpdrepo.navdata.td_id[1];
		}	
	}

	public boolean td_show(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.navdata.td_show[0] != 0);
		} else {
			return (this.xpdrepo.navdata.td_show[1] != 0);
		}
	}

	public float td_x(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.td_x[0];
		} else {
			return this.xpdrepo.navdata.td_x[1];
		}
	}

	public float td_y(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.td_y[0];
		} else {
			return this.xpdrepo.navdata.td_y[1];
		}
	}
	
	public String ed_id(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.ed_id[0];
		} else {
			return this.xpdrepo.navdata.ed_id[1];
		}	
	}

	public boolean ed_show(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.navdata.ed_show[0] != 0);
		} else {
			return (this.xpdrepo.navdata.ed_show[1] != 0);
		}
	}

	public float ed_x(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.ed_x[0];
		} else {
			return this.xpdrepo.navdata.ed_x[1];
		}
	}

	public float ed_y(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.navdata.ed_y[0];
		} else {
			return this.xpdrepo.navdata.ed_y[1];
		}
	}

	public boolean efis_apt_mode(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.arpt_on[0] != 0);
		} else {
			return (this.xpdrepo.efis.arpt_on[1] != 0);
		}
	}

	public boolean efis_sta_mode(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.vor_on[0] != 0);
		} else {
			return (this.xpdrepo.efis.vor_on[1] != 0);
		}
	}

	public boolean efis_wpt_mode(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.wpt_on[0] != 0);
		} else {
			return (this.xpdrepo.efis.wpt_on[1] != 0);
		}
	}

	public boolean tcas_on(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.efis.tcas_on[0] != 0);
		} else {
			return (this.xpdrepo.efis.tcas_on[1] != 0);
		}
	}

	public float nav1_freq() {
		return this.xpdrepo.radios.nav_freq[0];
	}

	public float nav2_freq() {
		return this.xpdrepo.radios.nav_freq[1];
	}

	public int course(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.autopilot.mcp_course[0];
		} else {
			return this.xpdrepo.autopilot.mcp_course[1];
		}
	}

	public int back_course(String pilot) {
		if (pilot == "cpt") {
			return (this.xpdrepo.autopilot.mcp_course[0] + 180) % 360;
		} else {
			return (this.xpdrepo.autopilot.mcp_course[1] + 180) % 360;
		}
	}

	public float rough_distance_to(NavigationObject nav_object) {
		return CoordinateSystem.rough_distance(latitude(), longitude(), nav_object.lat, nav_object.lon);
	}

	public String nav1_id() {
		return this.xpdrepo.radios.nav_id[0];
	}

	public float nav1_rel_bearing() {
		return this.xpdrepo.radios.nav_rel_bearing[0];
	}

	public float nav1_dme_nm() {
		return this.xpdrepo.radios.nav_dme_dist_nm[0];
	}

	public float nav1_time_sec() {
		return this.xpdrepo.radios.nav_dme_time_sec[0];
	}

	public int nav1_fromto() {
		return this.xpdrepo.radios.nav_fromto[0];
	}

	public float nav1_hdef_dot() {
		return this.xpdrepo.radios.nav_hdef_dot[0];
	}

	public float nav1_vdef_dot() {
		return this.xpdrepo.radios.nav_vdef_dot[0];
	}

	public int nav1_cdi() {
		return this.xpdrepo.radios.nav_cdi[0];
	}

	public float adf1_freq() {
		return this.xpdrepo.radios.adf_freq[0];
	}

	public String adf1_id() {
		return this.xpdrepo.radios.adf_id[0];
	}

	public float adf1_rel_bearing() {
		return this.xpdrepo.radios.adf_rel_bearing[0];
	}

	public float adf1_dme_nm() {
		return this.xpdrepo.radios.adf_dme_dist_nm[0];
	}

	public String nav2_id() {
		return this.xpdrepo.radios.nav_id[1];
	}

	public float nav2_rel_bearing() {
		return this.xpdrepo.radios.nav_rel_bearing[1];
	}

	public float nav2_dme_nm() {
		return this.xpdrepo.radios.nav_dme_dist_nm[1];
	}

	public float nav2_time_sec() {
		return this.xpdrepo.radios.nav_dme_time_sec[1];
	}

	public int nav2_fromto() {
		return this.xpdrepo.radios.nav_fromto[1];
	}

	public float nav2_hdef_dot() {
		return this.xpdrepo.radios.nav_hdef_dot[1];
	}

	public float nav2_vdef_dot() {
		return this.xpdrepo.radios.nav_vdef_dot[1];
	}

	public int nav2_cdi() {
		return this.xpdrepo.radios.nav_cdi[1];
	}

	public float adf2_freq() {
		return this.xpdrepo.radios.adf_freq[1];
	}

	public String adf2_id() {
		return this.xpdrepo.radios.adf_id[1];
	}

	public float adf2_rel_bearing() {
		return this.xpdrepo.radios.adf_rel_bearing[1];
	}

	public float adf2_dme_nm() {
		return this.xpdrepo.radios.adf_dme_dist_nm[1];
	}

	public int vor1_pos(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.efis.vor1_pos[0];
		} else {
			return this.xpdrepo.efis.vor1_pos[1];
		}
	}

	public int vor2_pos(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.efis.vor2_pos[0];
		} else {
			return this.xpdrepo.efis.vor2_pos[1];
		}
	}

	public String active_waypoint() {
		return this.xpdrepo.fms.active_waypoint;
	}

	public boolean fpln_active() {
		return (this.xpdrepo.fms.fpln_active[0] != 0 || this.xpdrepo.fms.fpln_active[1] != 0);
	}
	
	public int pfd_gp_path(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.fms.pfd_gp_path[0];
		} else {
			return this.xpdrepo.fms.pfd_gp_path[1];
		}
	}
	
	public int pfd_fac_horizontal(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.fms.pfd_fac_horizontal[0];
		} else {
			return this.xpdrepo.fms.pfd_fac_horizontal[1];
		}
	}
	
	public int ian_info(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.fms.ian_info[0];
		} else {
			return this.xpdrepo.fms.ian_info[1];
		}
	}
	
	public int fac_exp(String pilot) {
		if (pilot == "cpt") {
			return this.xpdrepo.fms.fac_exp[0];
		} else {
			return this.xpdrepo.fms.fac_exp[1];
		}
	}

	public String fpln_nav_id_eta() {
		return this.xpdrepo.fms.fpln_nav_id_eta;
	}
	
	public String rw_fac_id2() {
		return this.xpdrepo.fms.rw_fac_id2;
	}
	
	public float rw_dist2() {
		return this.xpdrepo.fms.rw_dist2;
	}

	public float fpln_nav_id_dist() {
		return this.xpdrepo.fms.fpln_nav_id_dist;
	}

	public int pfd_spd_mode() {
		return this.xpdrepo.autopilot.pfd_spd_mode;
	}

	public boolean rec_thr_modes() {
		return (this.xpdrepo.autopilot.rec_thr_modes != 0);
	}

	public boolean rec_thr2_modes() {
		return (this.xpdrepo.autopilot.rec_thr2_modes != 0);
	}

	public int pfd_hdg_mode() {
		return this.xpdrepo.autopilot.pfd_hdg_mode;
	}

	public boolean rec_hdg_mode() {
		return (this.xpdrepo.autopilot.rec_hdg_mode != 0);
	}

	public int pfd_hdg_mode_arm() {
		return this.xpdrepo.autopilot.pfd_hdg_mode_arm;
	}

	public int pfd_alt_mode() {
		return this.xpdrepo.autopilot.pfd_alt_mode;
	}

	public boolean rec_alt_modes() {
		return (this.xpdrepo.autopilot.rec_alt_modes != 0);
	}

	public int pfd_alt_mode_arm() {
		return this.xpdrepo.autopilot.pfd_alt_mode_arm;
	}

	public float groundspeed() {
		if(this.xpdrepo.aircraft.groundspeed > 0.1f) {
			return this.xpdrepo.aircraft.groundspeed * 1.94384f;
		}else {
			return 0f;
		}
	}

	public float aoa() {
		return this.xpdrepo.aircraft.aoa;
	}

	public int mcp_alt() {
		return this.xpdrepo.autopilot.mcp_alt;
	}

	public float airspeed_mach() {
		return this.xpdrepo.aircraft.airspeed_mach;
	}

	public boolean hdg_bug_line(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.hdg_bug_line[0] != 0);
		default:
			return (this.xpdrepo.avionics.hdg_bug_line[1] != 0);
		}
	}

	public float minimums(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.minimums[0];
		default:
			return this.xpdrepo.avionics.minimums[1];
		}
	}

	public int minimums_mode(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.efis.minimums[0];
		default:
			return this.xpdrepo.efis.minimums[1];
		}
	}
	
	public float baro_min(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.efis.baro_min[0];
		default:
			return this.xpdrepo.efis.baro_min[1];
		}
	}
	
	public int baro_min_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.efis.baro_min_show[0];
		default:
			return this.xpdrepo.efis.baro_min_show[1];
		}
	}

	public float radio_alt_feet() {
		return this.xpdrepo.aircraft.radio_alt_feet;
	}

	public NavigationRadio get_nav_radio(int bank) {
		if (bank == 1) {
			return this.nav1_radio;
		} else if (bank == 2) {
			return this.nav2_radio;
		} else {
			return null;
		}
	}
	
	public float n1_percent1() {
		return this.xpdrepo.engines.n1_percent[0];
	}

	public float n1_percent2() {
		return this.xpdrepo.engines.n1_percent[1];
	}

	public float eng1_n1() {
		return this.xpdrepo.engines.eng_n1[0];
	}

	public float eng2_n1() {
		return this.xpdrepo.engines.eng_n1[1];
	}

	public float eng1_n2() {
		return this.xpdrepo.engines.eng_n2[0];
	}

	public float eng2_n2() {
		return this.xpdrepo.engines.eng_n2[1];
	}

	public float eng1_egt() {
		return this.xpdrepo.engines.eng_egt[0];
	}

	public float eng2_egt() {
		return this.xpdrepo.engines.eng_egt[1];
	}

	public float eng1_n1_req() {
		return this.xpdrepo.engines.eng_n1_req[0];
	}

	public float eng2_n1_req() {
		return this.xpdrepo.engines.eng_n1_req[1];
	}

	public int main_panel_du(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.buttonsswitches.main_panel_du[0];
		default:
			return this.xpdrepo.buttonsswitches.main_panel_du[1];
		}
	}

	public int lower_panel_du(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.buttonsswitches.lower_panel_du[0];
		default:
			return this.xpdrepo.buttonsswitches.lower_panel_du[1];
		}
	}

	public float fuel_qty_kgs_1() {
		return this.xpdrepo.engines.fuel_qty_kgs[0];
	}

	public float fuel_qty_kgs_2() {
		return this.xpdrepo.engines.fuel_qty_kgs[2];
	}

	public float fuel_qty_kgs_c() {
		return this.xpdrepo.engines.fuel_qty_kgs[1];
	}
	
	public float fuel_qty_lbs_1() {
		return this.xpdrepo.engines.fuel_qty_lbs[0];
	}

	public float fuel_qty_lbs_2() {
		return this.xpdrepo.engines.fuel_qty_lbs[2];
	}

	public float fuel_qty_lbs_c() {
		return this.xpdrepo.engines.fuel_qty_lbs[1];
	}

	public float fuel_flow1() { //(value * 3600 / 1000)
		if(this.preferences.get_preference(ZHSIPreferences.PREF_FUEL_LBS).equals("true")) {
			if(this.fuel_flow_used_show()) {
				return Math.round(this.xpdrepo.engines.fuel_flow_used1 * 2.20462f / 10f) /100f;
			}else {
				return ((this.xpdrepo.engines.fuel_flow[0] * 3600f) / 1000f) * 2.20462f;
			}
		}else{
			if(this.fuel_flow_used_show()) {
				return Math.round(this.xpdrepo.engines.fuel_flow_used1 / 10f) /100f;
			}else {
				return (this.xpdrepo.engines.fuel_flow[0] * 3600f) / 1000f;
			}
			
		}
		
	}

	public float fuel_flow2() {
		if(this.preferences.get_preference(ZHSIPreferences.PREF_FUEL_LBS).equals("true")) {
			if(this.fuel_flow_used_show()) {
				return Math.round(this.xpdrepo.engines.fuel_flow_used2 * 2.20462f / 10f) /100f;
			}else {
				return ((this.xpdrepo.engines.fuel_flow[1] * 3600f) / 1000f) * 2.20462f;
			}
			
		}else {
			if(this.fuel_flow_used_show()) {
				return Math.round(this.xpdrepo.engines.fuel_flow_used2 / 10f) /100f;
			}else {
				return (this.xpdrepo.engines.fuel_flow[1] * 3600f) / 1000f;
			}
			
		}
		
	}
	
	@Override
	public float fuel_flow_used1() {
		return this.xpdrepo.engines.fuel_flow_used1;
	}

	@Override
	public float fuel_flow_used2() {
		return this.xpdrepo.engines.fuel_flow_used2;

	}

	public boolean eng_start_value_1() {
		return (this.xpdrepo.engines.eng_start_value[0] != 0);
	}

	public boolean eng_start_value_2() {
		return (this.xpdrepo.engines.eng_start_value[1] != 0);
	}

	public boolean eng_oil_bypass_1() {
		return (this.xpdrepo.engines.eng_oil_bypass[0] != 0);
	}

	public boolean eng_oil_bypass_2() {
		return (this.xpdrepo.engines.eng_oil_bypass[1] != 0);
	}

	public boolean oil_pressure_annun_1() {
		return (this.xpdrepo.engines.oil_pressure_annun[0] != 0);
	}

	public boolean oil_pressure_annun_2() {
		return (this.xpdrepo.engines.oil_pressure_annun[1] != 0);
	}

	public float oil_qty_1() {
		return this.xpdrepo.engines.oil_qty[0] * 21.127f;
	}

	public float oil_qty_2() {
		return this.xpdrepo.engines.oil_qty[1] * 21.127f;
	}

	public float oil_temp_c_1() {
		return this.xpdrepo.engines.oil_temp_c[0];
	}

	public float oil_temp_c_2() {
		return this.xpdrepo.engines.oil_temp_c[1];
	}

	public float eng_oil_press_1() {
		return this.xpdrepo.engines.eng_oil_press[0];
	}

	public float eng_oil_press_2() {
		return this.xpdrepo.engines.eng_oil_press[1];
	}

	public float eng_n1_bug_1() {
		return this.xpdrepo.engines.eng_n1_bug[0] * 100f;
	}

	public float eng_n1_bug_2() {
		return this.xpdrepo.engines.eng_n1_bug[1] * 100f;
	}

	public float outside_air_temp_c() {
		return this.xpdrepo.environment.outside_air_temp_c;
	}

	public String n1_mode() {
		switch(this.xpdrepo.engines.n1_mode) {
		case 0:
			return "MAN";
		case 1:
			return "TO";
		case 2:
			return "TO 1";
		case 3:
			return "TO 2";
		case 4:
			return "D-TO";
		case 5:
			return "D-TO 1";
		case 6:
			return "D-TO 2";
		case 7:
			return "CLB";
		case 8:
			return "CLB 1";
		case 9:
			return "CLB 2";
		case 10:
			return "CRZ";
		case 11:
			return "G/A";
		case 12:
			return "CON";
		default:
			return "---";
		}
	}

	public float wind_speed_knots() {
		return this.xpdrepo.environment.wind_speed_knots;
	}

	public float true_airspeed_knots() {
		return this.xpdrepo.aircraft.true_airspeed_knots;
	}

	public float fms_vref() {
		return this.xpdrepo.fms.fms_vref;
	}
	public float fms_vr_set() {
		return this.xpdrepo.fms.fms_vr_set;
	}

	public float wind_heading() {
		return this.xpdrepo.environment.wind_heading;
	}

	public float ap_fd_roll_deg() {
		return this.xpdrepo.autopilot.ap_fd_roll_deg;
	}

	public float ap_fd_pitch_deg() {
		return this.xpdrepo.autopilot.ap_fd_pitch_deg;
	}

	public boolean mfd_eng() {
		return (this.xpdrepo.systems.lowerdu_page == 1);
	}

	public boolean mfd_sys() {
		return (this.xpdrepo.systems.lowerdu_page == 2 || this.xpdrepo.systems.lowerdu_page2 == 1);
	}

	public float brake_temp_left_in() {
		return this.xpdrepo.systems.brake_temp_left_in;
	}

	public float brake_temp_left_out() {
		return this.xpdrepo.systems.brake_temp_left_out;
	}

	public float brake_temp_right_in() {
		return this.xpdrepo.systems.brake_temp_right_in;
	}

	public float brake_temp_right_out() {
		return this.xpdrepo.systems.brake_temp_right_out;
	}

	public float hyd_a_qty() {
		return this.xpdrepo.systems.hyd_a_qty;
	}

	public float hyd_b_qty() {
		return this.xpdrepo.systems.hyd_b_qty;
	}
	
	public int vhf_nav_source() {
		return this.xpdrepo.systems.vhf_nav_source;
	}

	public int hyd_a_pressure() {
		return this.xpdrepo.systems.hyd_a_pressure;
	}

	public int hyd_b_pressure() {
		return this.xpdrepo.systems.hyd_b_pressure;
	}
	
	public int flt_alt() {
		return this.xpdrepo.systems.flt_alt;
	}
	
	public int land_alt() {
		return this.xpdrepo.systems.land_alt;
	}

	public float left_elevator_deflection() {
		return this.xpdrepo.systems.left_elevator_deflection;
	}

	public float right_elevator_deflection() {
		return this.xpdrepo.systems.right_elevator_deflection;
	}

	public float left_ail_deflection() {
		return this.xpdrepo.systems.left_ail_deflection;
	}

	public float right_ail_deflection() {
		return this.xpdrepo.systems.right_ail_deflection;
	}

	public float rudder_deflection() {
		return this.xpdrepo.systems.rudder_deflection;
	}

	public float left_splr_deflection() {
		return this.xpdrepo.systems.left_splr_deflection;
	}

	public float right_splr_deflection() {
		return this.xpdrepo.systems.right_splr_deflection;
	}

	public float stab_trim() {
		return this.xpdrepo.systems.stab_trim;
	}
	
	public float rudder_trim() {
		return this.xpdrepo.systems.rudder_trim;
	}

	public boolean efis_terr_on(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.efis.terr_on[0] != 0);
		default:
			return (this.xpdrepo.efis.terr_on[1] != 0);
		}
		
	}
	
	public int efis_terr(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.efis.terr_on[0];
		default:
			return this.xpdrepo.efis.terr_on[1];
		}
		
	}
	
	public boolean efis_wxr_on(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.efis.wxr_on[0] != 0);
		default:
			return (this.xpdrepo.efis.wxr_on[1] != 0);
		}
		
	}
	public int efis_wxr(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.efis.wxr_on[0];
		default:
			return this.xpdrepo.efis.wxr_on[1];
		}
		
	}

	public float max_speed() {
		return this.xpdrepo.aircraft.max_speed;
	}

	public float max_maneuver_speed() {
		return this.xpdrepo.aircraft.max_maneuver_speed;
	}

	public boolean max_maneuver_speed_show() {
		return (this.xpdrepo.aircraft.max_maneuver_speed_show != 0);
	}

	public float min_speed() {
		return this.xpdrepo.aircraft.min_speed;
	}

	public boolean min_speed_show() {
		return (this.xpdrepo.aircraft.min_speed_show != 0);
	}

	public float min_maneuver_speed() {
		return this.xpdrepo.aircraft.min_maneuver_speed;
	}

	public boolean min_maneuver_speed_show() {
		return (this.xpdrepo.aircraft.min_maneuver_speed_show != 0);
	}

	public float fms_v1_set() {
		return this.xpdrepo.fms.fms_v1_set;
	}
	
	public float fms_v1() {
		return this.xpdrepo.fms.fms_v1;
	}

	public float flaps_1() {
		return this.xpdrepo.aircraft.flaps_1;
	}

	public boolean flaps_1_show() {
		return (this.xpdrepo.aircraft.flaps_1_show > 0.5);
	}
	
	public float flaps_2() {
		return this.xpdrepo.aircraft.flaps_2;
	}

	public boolean flaps_2_show() {
		return (this.xpdrepo.aircraft.flaps_2_show > 0.5);
	}

	public float flaps_5() {
		return this.xpdrepo.aircraft.flaps_5;
	}

	public boolean flaps_5_show() {
		return (this.xpdrepo.aircraft.flaps_5_show > 0.5);
	}
	
	public float flaps_10() {
		return this.xpdrepo.aircraft.flaps_10;
	}

	public boolean flaps_10_show() {
		return (this.xpdrepo.aircraft.flaps_10_show > 0.5);
	}

	public float flaps_15() {
		return this.xpdrepo.aircraft.flaps_15;
	}

	public boolean flaps_15_show() {
		return (this.xpdrepo.aircraft.flaps_15_show > 0.5);
	}
	
	public float flaps_25() {
		return this.xpdrepo.aircraft.flaps_25;
	}

	public boolean flaps_25_show() {
		return (this.xpdrepo.aircraft.flaps_25_show > 0.5);
	}
	
	public float flaps_up() {
		return this.xpdrepo.aircraft.flaps_up;
	}

	public boolean flaps_up_show() {
		return (this.xpdrepo.aircraft.flaps_up_show > 0.5);
	}
	
	public float stall() {
		return this.xpdrepo.aircraft.stall;
	}

	public boolean stall_show() {
		return (this.xpdrepo.aircraft.stall_show != 0);
	}
	
	public boolean spd_80_show() {
		return (this.xpdrepo.aircraft.spd_80_show != 0);
	}

	public float airspeed_acceleration() {
		return this.xpdrepo.aircraft.airspeed_acceleration;
	}

	public float fms_v2_15() {
		return this.xpdrepo.fms.fms_v2_15;
	}
	
	public boolean fms_vref_bugs() {
		return (this.xpdrepo.fms.fms_vref_bugs > 0.5);
	}
	
	public boolean fms_v1r_bugs() {
		return (this.xpdrepo.fms.fms_v1r_bugs > 0.5);
	}

	public float green_arc(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.green_arc[0];
		default:
			return this.xpdrepo.avionics.green_arc[1];
		}
	}

	public boolean green_arc_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.green_arc_show[0] != 0);
		default:
			return (this.xpdrepo.avionics.green_arc_show[1] != 0);
		}
	}
	
	public int fmc_source(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.fmc_source[0];
		default:
			return this.xpdrepo.avionics.fmc_source[1];
		}
	}

	public float flaps_l_deflection() {
		return this.xpdrepo.systems.flaps_l_deflection;
	}

	public float flaps_r_deflection() {
		return this.xpdrepo.systems.flaps_r_deflection;
	}

	public boolean fuel_tank_ctr1_pos() {
		return (this.xpdrepo.buttonsswitches.fuel_tank_ctr1_pos != 0);
	}

	public boolean fuel_tank_ctr2_pos() {
		return (this.xpdrepo.buttonsswitches.fuel_tank_ctr2_pos != 0);
	}

	public boolean fuel_tank_lft1_pos() {
		return (this.xpdrepo.buttonsswitches.fuel_tank_lft1_pos != 0);
	}

	public boolean fuel_tank_lft2_pos() {
		return (this.xpdrepo.buttonsswitches.fuel_tank_lft2_pos != 0);
	}

	public boolean fuel_tank_rgt1_pos() {
		return (this.xpdrepo.buttonsswitches.fuel_tank_rgt1_pos != 0);
	}

	public boolean fuel_tank_rgt2_pos() {
		return (this.xpdrepo.buttonsswitches.fuel_tank_rgt2_pos != 0);
	}

	public boolean legs_mod_active() {
		return (this.xpdrepo.fms.legs_mod_active != 0);
	}

	public int legs_step_ctr_idx(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.fms.legs_step_ctr_idx[0];
		default:
			return this.xpdrepo.fms.legs_step_ctr_idx[1];
		}
	}
	
	public float ils_rotate0(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.fms.ils_rotate0[0];
		default:
			return this.xpdrepo.fms.ils_rotate0[1];
		}
	}
	
	public float ils_rotate0_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.fms.ils_rotate0_mod[0];
		default:
			return this.xpdrepo.fms.ils_rotate0_mod[1];
		}
	}
	
	public float ils_rotate(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.fms.ils_rotate[0];
		default:
			return this.xpdrepo.fms.ils_rotate[1];
		}
	}
	
	public float ils_rotate_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.fms.ils_rotate_mod[0];
		default:
			return this.xpdrepo.fms.ils_rotate_mod[1];
		}
	}
	
	@Override
	public boolean ils_show0(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.fms.ils_show0[0] != 0);
		default:
			return (this.xpdrepo.fms.ils_show0[1] != 0);
		}
	}
	
	@Override
	public boolean ils_show0_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.fms.ils_show0_mod[0] != 0);
		default:
			return (this.xpdrepo.fms.ils_show0_mod[1] != 0);
		}
	}

	@Override
	public boolean ils_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.fms.ils_show[0] != 0);
		default:
			return (this.xpdrepo.fms.ils_show[1] != 0);
		}
	}
	
	@Override
	public boolean ils_show_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.fms.ils_show_mod[0] != 0);
		default:
			return (this.xpdrepo.fms.ils_show_mod[1] != 0);
		}
	}
	
	public String nav_txt1(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.fms.nav_txt1[0];
		default:
			return this.xpdrepo.fms.nav_txt1[1];
		}
	}
	
	public String nav_txt1a(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.fms.nav_txt1a[0];
		default:
			return this.xpdrepo.fms.nav_txt1a[1];
		}
	}
	
	public String nav_txt2(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.fms.nav_txt2[0];
		default:
			return this.xpdrepo.fms.nav_txt2[1];
		}
	}
	
	public float ils_x(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_x;
		default:
			return this.xpdrepo.navdata.ils_fo_x;
		}	
	}
	
	public float ils_x_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_x_mod;
		default:
			return this.xpdrepo.navdata.ils_fo_x_mod;
		}	
	}
	
	public float ils_y(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_y;
		default:
			return this.xpdrepo.navdata.ils_fo_y;
		}	
	}
	
	public float ils_y_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_y_mod;
		default:
			return this.xpdrepo.navdata.ils_fo_y_mod;
		}	
	}
	
	public float ils_x0(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_x0;
		default:
			return this.xpdrepo.navdata.ils_fo_x0;
		}	
	}
	
	public float ils_x0_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_x0_mod;
		default:
			return this.xpdrepo.navdata.ils_fo_x0_mod;
		}	
	}
	
	public float ils_y0(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_y0;
		default:
			return this.xpdrepo.navdata.ils_fo_y0;
		}	
	}
	
	public float ils_y0_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_y0_mod;
		default:
			return this.xpdrepo.navdata.ils_fo_y0_mod;
		}	
	}
	
	public String ils_runway(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_runway;
		default:
			return this.xpdrepo.navdata.ils_fo_runway;
		}
	}
	
	public String ils_runway_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_runway_mod;
		default:
			return this.xpdrepo.navdata.ils_fo_runway_mod;
		}
	}
	
	public String ils_runway0(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_runway0;
		default:
			return this.xpdrepo.navdata.ils_fo_runway0;
		}
	}
	
	public String ils_runway0_mod(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.ils_runway0_mod;
		default:
			return this.xpdrepo.navdata.ils_fo_runway0_mod;
		}
	}

	public boolean tcas_traffic_ra(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.tcas.tcas_traffic_ra[0] != 0.0f);
		default:
			return (this.xpdrepo.tcas.tcas_traffic_ra[1] != 0.0f);
		}
	}

	public boolean tcas_traffic_ta(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.tcas.tcas_traffic_ta[0] != 0.0f);
		default:
			return (this.xpdrepo.tcas.tcas_traffic_ta[1] != 0.0f);
		}
	}
	
	public boolean tcas_pfd_ra_dn() {
		return (this.xpdrepo.tcas.tcas_pfd_ra_dn != 0.0f);
	}
	
	public boolean tcas_pfd_ra_up() {
		return (this.xpdrepo.tcas.tcas_pfd_ra_up != 0.0f);
	}
	
	public float tcas_pfd_ra_dn2(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.tcas.tcas_pfd_ra_dn2[0];
		default:
			return this.xpdrepo.tcas.tcas_pfd_ra_dn2[1];
		}
	}
	
	public float tcas_pfd_ra_up2(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.tcas.tcas_pfd_ra_up2[0];
		default:
			return this.xpdrepo.tcas.tcas_pfd_ra_up2[1];
		}
	}

	public int transponder_pos() {
		return this.xpdrepo.buttonsswitches.transponder_pos;

	}

	public boolean gear_up() {
		return (this.xpdrepo.aircraft.gear_deploy_ratio == 0);
	}

	public float nav1_course() {
		return this.xpdrepo.radios.nav_course[0];
	}
	
	public float nav2_course() {
		return this.xpdrepo.radios.nav_course[1];
	}

	public boolean efis_ctr_map(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.efis.exp_map[0] != 1);
		default:
			return (this.xpdrepo.efis.exp_map[1] != 1);
		}
	}
	
	public int efis_map_ctr(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.efis.exp_map[0];
		default:
			return this.xpdrepo.efis.exp_map[1];
		}
	}

	public boolean efis_vsd_map(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.efis.vsd_map[0] != 0);
		default:
			return (this.xpdrepo.efis.vsd_map[1] != 0);
		}
	}

	public int nav1_type() {
		return this.xpdrepo.radios.nav_type[0];
	}

	public int nav2_type() {
		return this.xpdrepo.radios.nav_type[1];
	}

	public boolean single_ch() {
		return (this.xpdrepo.autopilot.single_ch != 0);
	}
	
	public boolean single_ch_g() {
		return (this.xpdrepo.autopilot.single_ch_g != 0);
	}
	
	public boolean single_ch_rec_mode() {
		return (this.xpdrepo.autopilot.single_ch_rec_mode != 0);
	}
	
	public int flare_status() {
		return this.xpdrepo.autopilot.flare_status;
	}

	public int missed_app_wpt_idx() {
		return this.xpdrepo.fms.missed_app_wpt_idx;
	}

	public boolean efis_data_on(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.efis.data_on[0] != 0);
		default:
			return (this.xpdrepo.efis.data_on[1] != 0);
		}
	}
	public boolean efis_mtrs_on(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.efis.mtrs[0] != 0);
		default:
			return (this.xpdrepo.efis.mtrs[1] != 0);
		}
	}

	public String xraas_message() {
		return this.xpdrepo.systems.xraas_msg;
	}

	public int xraas_color() {
		return this.xpdrepo.systems.xraas_color;
	}

	public boolean windsheer() {
		return (this.xpdrepo.systems.windsheer != 0);
	}

	public boolean pullup() {
		return (this.xpdrepo.systems.pullup != 0);
	}

	public int vspeed() {
		return (int)this.xpdrepo.avionics.vspeed;
	}

	public boolean vspeed_digit_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.vspeed_digit_show[0] != 0);
		default:
			return (this.xpdrepo.avionics.vspeed_digit_show[1] != 0);
		}
	}
	
	public boolean v1_off_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.v1_off_show[0] != 0);
		default:
			return (this.xpdrepo.avionics.v1_off_show[1] != 0);
		}
	}

	public boolean vspeed_vref_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.vspeed_vref_show[0] != 0);
		default:
			return (this.xpdrepo.avionics.vspeed_vref_show[1] != 0);
		}
	}

	public int vspeed_mode() {
		return this.xpdrepo.avionics.vspeed_mode;
	}
	
	public int no_vspd() {
		return this.xpdrepo.avionics.no_vspd;
	}

	public boolean reverser1_moved() {
		return this.xpdrepo.systems.reverser1_moved;
	}

	public boolean reverser1_deployed() {
		return this.xpdrepo.systems.reverser1_deployed;
	}

	public boolean reverser2_moved() {
		return this.xpdrepo.systems.reverser2_moved;
	}

	public boolean reverser2_deployed() {
		return this.xpdrepo.systems.reverser2_deployed;
	}
	
	public float[] legs_rad_lat() {
		return this.xpdrepo.fms.legs_rad_lat;
	}
	
	public float[] legs_rad_lon() {
		return this.xpdrepo.fms.legs_rad_lon;
	}
	
	public float[] legs_rad_turn() {
		return this.xpdrepo.fms.legs_rad_turn;
	}
	
	public float[] legs_radius() {
		return this.xpdrepo.fms.legs_radius;
	}
	
	public float[] legs_seg1_radius() {
		return this.xpdrepo.fms.legs_seg1_radius;
	}
	
	public float[] legs_seg1_ctr_lat() {
		return this.xpdrepo.fms.legs_seg1_ctr_lat;
	}
	
	public float[] legs_seg1_ctr_lon() {
		return this.xpdrepo.fms.legs_seg1_ctr_lon;
	}
	
	public float[] legs_seg1_turn() {
		return this.xpdrepo.fms.legs_seg1_turn;
	}
	
	public float[] legs_seg1_start_angle() {
		return this.xpdrepo.fms.legs_seg1_start_angle;
	}
	
	public float[] legs_seg1_end_angle() {
		return this.xpdrepo.fms.legs_seg1_end_angle;
	}
	
	public float[] legs_seg3_start_angle() {
		return this.xpdrepo.fms.legs_seg3_start_angle;
	}
	
	public float[] legs_seg3_end_angle() {
		return this.xpdrepo.fms.legs_seg3_end_angle;
	}
	
	public float[] legs_seg3_turn() {
		return this.xpdrepo.fms.legs_seg3_turn;
	}
	
	public float[] legs_seg3_radius() {
		return this.xpdrepo.fms.legs_seg3_radius;
	}
	
	public float[] legs_seg3_ctr_lat() {
		return this.xpdrepo.fms.legs_seg3_ctr_lat;
	}
	
	public float[] legs_seg3_ctr_lon() {
		return this.xpdrepo.fms.legs_seg3_ctr_lon;
	}
	
	public float[] legs_af_beg() {
		return this.xpdrepo.fms.legs_af_beg;
	}
	
	public float[] legs_af_end() {
		return this.xpdrepo.fms.legs_af_end;
	}
	
	public float[] legs_bypass() {
		return this.xpdrepo.fms.legs_bypass;
	}
	
	public int num_of_wpts() {
		return this.xpdrepo.fms.num_of_wpts;
	}

	public String[] waypoints() {
		return this.xpdrepo.fms.waypoints;
	}
	
	public int vnav_idx() {
		return this.xpdrepo.fms.vnav_idx;
	}
	
	public String origin_arpt() {
		return this.xpdrepo.fms.origin_arpt;
	}

	public String dest_arpt() {
		return this.xpdrepo.fms.dest_arpt;
	}

	public float[] legs_lat() {
		return this.xpdrepo.fms.legs_lat;
	}

	public float[] legs_lon() {
		return this.xpdrepo.fms.legs_lon;
	}

//	public float[] legs_alt_calc() {
//		return this.xpdrepo.fms.legs_alt_calc;
//	}

	public float[] legs_alt_rest1() {
		return this.xpdrepo.fms.legs_alt_rest1;
	}

	public float[] legs_alt_rest2() {
		return this.xpdrepo.fms.legs_alt_rest2;
	}
	
	public float[] legs_dist() {
		return this.xpdrepo.fms.legs_dist;
	}
	
	public float[] legs_alt_rest_type() {
		return this.xpdrepo.fms.legs_alt_rest_type;
	}
	
	public float[] legs_eta() {
		return this.xpdrepo.fms.legs_eta;
	}

	public float[] legs_hold_dist() {
		return this.xpdrepo.fms.legs_hold_dist;
	}

	public float[] legs_hold_time() {
		return this.xpdrepo.fms.legs_hold_time;
	}
	
	public float[] legs_crs_mag() {
		return this.xpdrepo.fms.legs_crs_mag;
	}
	
	public float[] legs_spd() {
		return this.xpdrepo.fms.legs_spd;
	}
	
	public float[] legs_type() {
		return this.xpdrepo.fms.legs_type;
	}

	public int mod_num_of_wpts() {
		return this.xpdrepo.fms.num_of_wpts_2;
	}

	public String[] mod_waypoints() {
		return this.xpdrepo.fms.waypoints_2;
	}
	
	public int mod_vnav_idx() {
		return this.xpdrepo.fms.vnav_idx_mod;
	}
	
	public String mod_origin_arpt() {
		return this.xpdrepo.fms.origin_arpt_2;
	}

	public String mod_dest_arpt() {
		return this.xpdrepo.fms.dest_arpt_2;
	}

	public float[] mod_legs_lat() {
		return this.xpdrepo.fms.legs_lat_2;
	}

	public float[] mod_legs_lon() {
		return this.xpdrepo.fms.legs_lon_2;
	}

//	public float[] mod_legs_alt_calc() {
//		return this.xpdrepo.fms.legs_alt_calc_2;
//	}

	public float[] mod_legs_alt_rest1() {
		return this.xpdrepo.fms.legs_alt_rest1_2;
	}

	public float[] mod_legs_alt_rest2() {
		return this.xpdrepo.fms.legs_alt_rest2_2;
	}

	public float[] mod_legs_alt_rest_type() {
		return this.xpdrepo.fms.legs_alt_rest_type_2;
	}
	
	public float[] mod_legs_hold_dist() {
		return this.xpdrepo.fms.legs_hold_dist_2;
	}
	
	public float[] mod_legs_hold_time() {
		return this.xpdrepo.fms.legs_hold_time_2;
	}
	
	public float[] mod_legs_crs_mag() {
		return this.xpdrepo.fms.legs_crs_mag_2;
	}
	
	public float[] mod_legs_spd() {
		return this.xpdrepo.fms.legs_spd_2;
	}
	
	public float[] mod_legs_rad_lat() {
		return this.xpdrepo.fms.legs_rad_lat_2;
	}
	
	public float[] mod_legs_rad_lon() {
		return this.xpdrepo.fms.legs_rad_lon_2;
	}
	
	public float[] mod_legs_rad_turn() {
		return this.xpdrepo.fms.legs_rad_turn_2;
	}
	
	public float[] mod_legs_type() {
		return this.xpdrepo.fms.legs_type_2;
	}

	public int pfd_mode(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.autopilot.pfd_mode[0];
		default:
			return this.xpdrepo.autopilot.pfd_mode[1];
		}
	}
	
	public boolean vs_mode() {
		return (this.xpdrepo.autopilot.vs_mode != 0);
	}

	@Override
	public String[] fix_id(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.fix_id;
		default:
			return this.xpdrepo.navdata.fix_fo_id;
		}
		
	}

	@Override
	public boolean[] fix_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.navdata.fix_id_show;
		default:
			return this.xpdrepo.navdata.fix_id_fo_show;
		}
		
	}
	
	@Override
	public float[] fix_lat() {
		return this.xpdrepo.navdata.fix_lat;
	}
	
	@Override
	public float[] fix_lon() {
		return this.xpdrepo.navdata.fix_lon;
	}
	
	@Override
	public float[] fix_type() {
		return this.xpdrepo.navdata.fix_type;
	}

	@Override
	public float[] fix_dist_0() {
		return this.xpdrepo.navdata.fix_dist_0;
	}

	@Override
	public float[] fix_dist_1() {
		return this.xpdrepo.navdata.fix_dist_1;
	}

	@Override
	public float[] fix_dist_2() {
		return this.xpdrepo.navdata.fix_dist_2;
	}
	
	@Override
	public float[] fix_rad_dist_0() {
		return this.xpdrepo.navdata.fix_rad_dist_0;
	}

	@Override
	public float[] fix_rad_dist_1() {
		return this.xpdrepo.navdata.fix_rad_dist_1;
	}

	@Override
	public float[] fix_rad_dist_2() {
		return this.xpdrepo.navdata.fix_rad_dist_2;
	}
	
	@Override
	public String fix_rad00_0() {
		return this.xpdrepo.navdata.fix_rad00_0;
	}
	
	@Override
	public String fix_rad00_1() {
		return this.xpdrepo.navdata.fix_rad00_1;
	}
	
	@Override
	public String fix_rad00_2() {
		return this.xpdrepo.navdata.fix_rad00_2;
	}
	
	@Override
	public String fix_rad01_0() {
		return this.xpdrepo.navdata.fix_rad01_0;
	}
	
	@Override
	public String fix_rad01_1() {
		return this.xpdrepo.navdata.fix_rad01_1;
	}
	
	@Override
	public String fix_rad01_2() {
		return this.xpdrepo.navdata.fix_rad01_2;
	}
	
	@Override
	public String fix_rad02_0() {
		return this.xpdrepo.navdata.fix_rad02_0;
	}
	
	@Override
	public String fix_rad02_1() {
		return this.xpdrepo.navdata.fix_rad02_1;
	}
	
	@Override
	public String fix_rad02_2() {
		return this.xpdrepo.navdata.fix_rad02_2;
	}
	
	@Override
	public String fix_rad03_0() {
		return this.xpdrepo.navdata.fix_rad03_0;
	}
	
	@Override
	public String fix_rad03_1() {
		return this.xpdrepo.navdata.fix_rad03_1;
	}
	
	@Override
	public String fix_rad03_2() {
		return this.xpdrepo.navdata.fix_rad03_2;
	}
	
	@Override
	public String fix_rad04_0() {
		return this.xpdrepo.navdata.fix_rad04_0;
	}
	
	@Override
	public String fix_rad04_1() {
		return this.xpdrepo.navdata.fix_rad04_1;
	}
	
	@Override
	public String fix_rad04_2() {
		return this.xpdrepo.navdata.fix_rad04_2;
	}
	
	@Override
	public float[] fix_rad_dist_0a() {
		return this.xpdrepo.navdata.fix_rad_dist_0a;
	}

	@Override
	public float[] fix_rad_dist_1a() {
		return this.xpdrepo.navdata.fix_rad_dist_1a;
	}

	@Override
	public float[] fix_rad_dist_2a() {
		return this.xpdrepo.navdata.fix_rad_dist_2a;
	}

	@Override
	public int ils_disable() {
		return this.xpdrepo.fms.ils_disable;
	}

	@Override
	public boolean intdir_act() {
		return (this.xpdrepo.fms.intdir_act != 0);
	}
	
	@Override
	public int intdir_crs() {
		return this.xpdrepo.fms.intdir_crs;
	}

	@Override
	public int intdir_crs2() {
		return this.xpdrepo.fms.intdir_crs2;
	}
	
	@Override
	public float dir_seg1_end_angle() {
		return this.xpdrepo.fms.dir_seg1_end_angle;
	}
	
	@Override
	public float dir_seg1_start_angle() {
		return this.xpdrepo.fms.dir_seg1_start_angle;
	}
	
	@Override
	public float dir_seg1_ctr_lat() {
		return this.xpdrepo.fms.dir_seg1_ctr_lat;
	}
	
	@Override
	public float dir_seg1_ctr_lon() {
		return this.xpdrepo.fms.dir_seg1_ctr_lon;
	}
	
	@Override
	public float dir_seg1_radius() {
		return this.xpdrepo.fms.dir_seg1_radius;
	}
	
	@Override
	public int dir_seg1_turn() {
		return this.xpdrepo.fms.dir_seg1_turn;
	}
	
	@Override
	public float dir_seg3_end_angle() {
		return this.xpdrepo.fms.dir_seg3_end_angle;
	}
	
	@Override
	public float dir_seg3_start_angle() {
		return this.xpdrepo.fms.dir_seg3_start_angle;
	}
	
	@Override
	public float dir_seg3_ctr_lat() {
		return this.xpdrepo.fms.dir_seg3_ctr_lat;
	}
	
	@Override
	public float dir_seg3_ctr_lon() {
		return this.xpdrepo.fms.dir_seg3_ctr_lon;
	}
	
	@Override
	public float dir_seg3_radius() {
		return this.xpdrepo.fms.dir_seg3_radius;
	}
	
	@Override
	public int dir_seg3_turn() {
		return this.xpdrepo.fms.dir_seg3_turn;
	}
	
	@Override
	public float gp_err_pfd() {
		return this.xpdrepo.fms.gp_err_pfd;
	}

	@Override
	public float yaw_rotation() {
		return this.xpdrepo.aircraft.yaw_rotation;
	}

	@Override
	public boolean nd_vert_path() {
		return (this.xpdrepo.avionics.nd_vert_path > 0);
	}
	
	@Override
	public boolean pfd_trk_path(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.pfd_trk_path[0] != 0);
		default:
			return (this.xpdrepo.avionics.pfd_trk_path[1] != 0);
		}
	}
	
	@Override
	public boolean pfd_vert_path(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.pfd_vert_path[0] != 0);
		default:
			return (this.xpdrepo.avionics.pfd_vert_path[1] != 0);
		}
	}
	
	@Override
	public boolean pfd_vert_path2(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.pfd_vert_path2[0] != 0);
		default:
			return (this.xpdrepo.avionics.pfd_vert_path2[1] != 0);
		}
	}
	
	@Override
	public int pfd_gls_loc_ghost(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.pfd_gls_loc_ghost[0];
		default:
			return this.xpdrepo.avionics.pfd_gls_loc_ghost[1];
		}
	}
	
	@Override
	public int pfd_gls_gs_ghost(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.pfd_gls_gs_ghost[0];
		default:
			return this.xpdrepo.avionics.pfd_gls_gs_ghost[1];
		}
	}
	
	@Override
	public int pfd_fac_ghost(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.pfd_fac_ghost[0];
		default:
			return this.xpdrepo.avionics.pfd_fac_ghost[1];
		}
	}
	
	@Override
	public int pfd_gp_ghost(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.pfd_gp_ghost[0];
		default:
			return this.xpdrepo.avionics.pfd_gp_ghost[1];
		}
	}
	
	@Override
	public int pfd_gs_ghost(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.pfd_gs_ghost[0];
		default:
			return this.xpdrepo.avionics.pfd_gs_ghost[1];
		}
	}
	
	@Override
	public int pfd_loc_ghost(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.pfd_loc_ghost[0];
		default:
			return this.xpdrepo.avionics.pfd_loc_ghost[1];
		}
	}
	
	@Override
	public boolean pfd_loc_exp(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.pfd_loc_exp[0] != 0);
		default:
			return (this.xpdrepo.avionics.pfd_loc_exp[1] != 0);
		}
	}
	
	@Override
	public boolean pfd_gls_exp(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.avionics.pfd_gls_exp[0] != 0);
		default:
			return (this.xpdrepo.avionics.pfd_gls_exp[1] != 0);
		}
	}
	
	@Override
	public int mmr_act_mode(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.mmr_act_mode[0];
		default:
			return this.xpdrepo.avionics.mmr_act_mode[1];
		}
	}

	@Override
	public float vnav_err() {
		return this.xpdrepo.avionics.vnav_err;
	}

	@Override
	public float thr_lvr1() {
		return this.xpdrepo.systems.thr_lvr1;
	}

	@Override
	public float thr_lvr2() {
		return this.xpdrepo.systems.thr_lvr2;
	}

	@Override
	public float rwy_altitude() {
		return this.xpdrepo.avionics.rwy_altitude;
	}

	@Override
	public boolean pfd_rwy_show() {
		return (this.xpdrepo.avionics.rwy_show >= 2);
	}

	@Override
	public float eng1_tai() {
		return this.xpdrepo.engines.eng1_tai;
	}

	@Override
	public float eng2_tai() {
		return this.xpdrepo.engines.eng2_tai;
	}

	public boolean egt_redline1() {
		return (this.xpdrepo.engines.egt_redline1 != 0);
	}
	
	public boolean egt_redline2() {
		return (this.xpdrepo.engines.egt_redline2 != 0);
	}

	public boolean eicas_ff1() {
		return (this.xpdrepo.engines.eicas_ff1 != 0);
	}

	public boolean eicas_ff2() {
		return (this.xpdrepo.engines.eicas_ff2 != 0);
	}

	public boolean eicas_oil_temp1() {
		return (this.xpdrepo.engines.eicas_oil_temp1 != 0);
	}

	public boolean eicas_oil_temp2() {
		return (this.xpdrepo.engines.eicas_oil_temp2 != 0);
	}

	public boolean eicas_oil_press1() {
		return (this.xpdrepo.engines.eicas_oil_press1 != 0);
	}

	public boolean eicas_oil_press2() {
		return (this.xpdrepo.engines.eicas_oil_press2 != 0);
	}

	@Override
	public float isfd_altitude() {
		return this.xpdrepo.isfd.isfd_altitude;
	}

	@Override
	public float isfd_alt_baro() {
		return this.xpdrepo.isfd.isfd_alt_baro;
	}

	@Override
	public int isfd_mode() {
		return this.xpdrepo.isfd.isfd_mode;
	}

	@Override
	public boolean isfd_std_mode() {
		return (this.xpdrepo.isfd.isfd_std_mode != 0);
	}
	
	@Override
	public float isfd_init_time() {
		return this.xpdrepo.isfd.isfd_init_time;
	}

	@Override
	public boolean fuel_flow_used_show() {
		return (this.xpdrepo.engines.fuel_flow_used_show != 0);
	}

	@Override
	public boolean cws_r_status() {
		return (this.xpdrepo.autopilot.cws_r_status != 0);
	}

	@Override
	public boolean cws_p_status() {
		return (this.xpdrepo.autopilot.cws_p_status != 0);
	}

	@Override
	public float chrono_needle(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.cpt_chrono_needle;
		default:
			return this.xpdrepo.avionics.fo_chrono_needle;
		}
	}
	@Override
	public int chrono_mode(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.avionics.cpt_chrono_mode;
		default:
			return this.xpdrepo.avionics.fo_chrono_mode;
		}
	}

	@Override
	public int time_zulu_hrs() {
		return this.xpdrepo.avionics.time_zulu_hrs;
	}

	@Override
	public int time_zulu_min() {
		return this.xpdrepo.avionics.time_zulu_min;
	}
	
	@Override
	public int time_local_hrs() {
		return this.xpdrepo.avionics.time_local_hrs;
	}

	@Override
	public int time_local_min() {
		return this.xpdrepo.avionics.time_local_min;
	}

	@Override
	public int chrono_minute(String pilot) {
		switch (pilot) {
		case "cpt":
			return (int)this.xpdrepo.avionics.cpt_chrono_minute;
		default:
			return (int)this.xpdrepo.avionics.fo_chrono_minute;
		}
	}

	@Override
	public int chrono_display_mode(String pilot) {
		switch (pilot) {
		case "cpt":
			return (int)this.xpdrepo.avionics.cpt_chrono_display_mode;
		default:
			return (int)this.xpdrepo.avionics.fo_chrono_display_mode;
		}
	}

	@Override
	public int time_day() {
		return this.xpdrepo.avionics.time_day;
	}

	@Override
	public int time_month() {
		return this.xpdrepo.avionics.time_month;
	}

	@Override
	public int chrono_et_mode(String pilot) {
		switch (pilot) {
		case "cpt":
			return (int)this.xpdrepo.avionics.cpt_chrono_et_mode;
		default:
			return (int)this.xpdrepo.avionics.fo_chrono_et_mode;
		}
	}

	@Override
	public int chrono_et_hrs(String pilot) {
		switch (pilot) {
		case "cpt":
			return (int)this.xpdrepo.avionics.cpt_chrono_et_hrs;
		default:
			return (int)this.xpdrepo.avionics.fo_chrono_et_hrs;
		}
	}

	@Override
	public int chrono_et_min(String pilot) {
		switch (pilot) {
		case "cpt":
			return (int)this.xpdrepo.avionics.cpt_chrono_et_min;
		default:
			return (int)this.xpdrepo.avionics.fo_chrono_et_min;
		}
	}

	@Override
	public boolean eng1_out() {
		return (this.xpdrepo.engines.eng1_out != 0);
	}

	@Override
	public boolean eng2_out() {
		return (this.xpdrepo.engines.eng2_out != 0);
	}

	@Override
	public int ac_power_knob() {
		return this.xpdrepo.buttonsswitches.ac_power_knob;
	}

	@Override
	public int dc_power_knob() {
		return this.xpdrepo.buttonsswitches.dc_power_knob;
	}
	
	@Override
	public int standby_bat_pos() {
		return this.xpdrepo.elec.standby_bat_pos;
	}

	@Override
	public float ac_freq_value() {
		return this.xpdrepo.elec.ac_freq_value;
	}

	@Override
	public float ac_amp_value() {
		return this.xpdrepo.elec.ac_amp_value;
	}

	@Override
	public float ac_volt_value() {
		return this.xpdrepo.elec.ac_volt_value;
	}

	@Override
	public float dc_amp_value() {
		return this.xpdrepo.elec.dc_amp_value;
	}

	@Override
	public float dc_volt_value() {
		return this.xpdrepo.elec.dc_volt_value;
	}

	@Override
	public String irs_left_string() {
		return this.xpdrepo.navdata.irs_left_string;
	}

	@Override
	public String irs_right_string() {
		return this.xpdrepo.navdata.irs_right_string;
	}
	
	@Override
	public boolean irs_aligned() {
		return (this.xpdrepo.navdata.irs_mode == 2 || this.xpdrepo.navdata.irs_mode2 == 2);
	}

	@Override
	public boolean irs_decimals_show() {
		return (this.xpdrepo.navdata.irs_decimals_show != 0);
	}

	@Override
	public int approach_speed() {
		return (int)this.xpdrepo.fms.approach_speed;
	}

	@Override
	public int approach_flaps() {
		return (int)this.xpdrepo.fms.approach_flaps;
	}

//	@Override
//	public float fpv_alpha() {
//		return this.xpdrepo.aircraft.fpv_alpha;
//	}
//	@Override
//	public float fpv_beta() {
//		return this.xpdrepo.aircraft.fpv_beta;
//	}

	@Override
	public boolean efis_fpv_show(String pilot) {
		switch (pilot) {
		case "cpt":
			return (this.xpdrepo.efis.fpv_show[0] != 0);
		default:
			return (this.xpdrepo.efis.fpv_show[1] != 0);
		}
	}
	
	@Override
	public float efis_fpv_horiz(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.efis.fpv_horiz[0];
		default:
			return this.xpdrepo.efis.fpv_horiz[1];
		}
	}
	
	@Override
	public float efis_fpv_vert(String pilot) {
		switch (pilot) {
		case "cpt":
			return this.xpdrepo.efis.fpv_vert[0];
		default:
			return this.xpdrepo.efis.fpv_vert[1];
		}
	}

	@Override
	public float fms_track() {
		return this.xpdrepo.fms.fms_track;
	}

	@Override
	public float rmi_arrow1() {
		return this.xpdrepo.radios.rmi_arrow1;
	}

	@Override
	public float rmi_arrow2() {
		return this.xpdrepo.radios.rmi_arrow2;
	}

	@Override
	public float rmi_arrow1_no_avail() {
		return this.xpdrepo.radios.rmi_arrow1_no_avail;
	}

	@Override
	public float rmi_arrow2_no_avail() {
		return this.xpdrepo.radios.rmi_arrow2_no_avail;
	}
	
	@Override
	public int inner_marker_lit() {
		return this.xpdrepo.radios.inner_marker_lit;
	}
	
	@Override
	public int middle_marker_lit() {
		return this.xpdrepo.radios.middle_marker_lit;
	}
	
	@Override
	public int outer_marker_lit() {
		return this.xpdrepo.radios.outer_marker_lit;
	}
	
	@Override
	public int nav1_flag_glideslope() {
		return this.xpdrepo.radios.nav1_flag_glideslope;
	}
	
	@Override
	public int nav2_flag_glideslope() {
		return this.xpdrepo.radios.nav2_flag_glideslope;
	}
	
	@Override
	public int nav1_display_vertical() {
		return this.xpdrepo.radios.nav1_display_vertical;
	}
	
	@Override
	public int nav2_display_vertical() {
		return this.xpdrepo.radios.nav2_display_vertical;
	}
	
	@Override
	public int nav1_display_horizontal() {
		return this.xpdrepo.radios.nav1_display_horizontal;
	}
	
	@Override
	public int nav2_display_horizontal() {
		return this.xpdrepo.radios.nav2_display_horizontal;
	}
	
	@Override
	public float gls1_vert_dev() {
		return this.xpdrepo.radios.gls1_vert_dev;
	}
	
	@Override
	public float gls2_vert_dev() {
		return this.xpdrepo.radios.gls2_vert_dev;
	}
	
	@Override
	public float gls1_horz_dev() {
		return this.xpdrepo.radios.gls1_horz_dev;
	}
	
	@Override
	public float gls2_horz_dev() {
		return this.xpdrepo.radios.gls2_horz_dev;
	}
	
	@Override
	public int gls1_type() {
		return this.xpdrepo.radios.gls1_type;
	}
	
	@Override
	public int gls2_type() {
		return this.xpdrepo.radios.gls2_type;
	}
	
	@Override
	public int gls1_active() {
		return this.xpdrepo.radios.gls1_active;
	}
	
	@Override
	public int gls2_active() {
		return this.xpdrepo.radios.gls2_active;
	}
	
	@Override
	public String gls1_id() {
		return this.xpdrepo.radios.gls1_id;
	}
	
	@Override
	public String gls2_id() {
		return this.xpdrepo.radios.gls2_id;
	}
	
	@Override
	public float gls1_dme() {
		return this.xpdrepo.radios.gls1_dme;
	}
	
	@Override
	public float gls2_dme() {
		return this.xpdrepo.radios.gls2_dme;
	}
	
	@Override
	public int nav1_no_id() {
		return this.xpdrepo.radios.nav1_no_id;
	}
	
	@Override
	public int nav2_no_id() {
		return this.xpdrepo.radios.nav2_no_id;
	}

}