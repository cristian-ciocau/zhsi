/**
 * 
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

import java.text.DecimalFormat;

import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRef;
import org.andreels.zhsi.ExtPlaneInterface.util.Observer;

public class NavData extends BaseDataClass {
	
	Observer<DataRef> tc_td_decel_ed;
	Observer<DataRef> fixes;
	Observer<DataRef> runways;
	Observer<DataRef> irs;

	private DecimalFormat df3 = new DecimalFormat("000");
	
	private final String[] EFIS_DISAGREE = {"laminar/B738/nd/capt/efis_disagree", "laminar/B738/nd/fo/efis_disagree"};
	
	private final String[] TC_ID = {"laminar/B738/nd/tc_id:string", "laminar/B738/nd/tc_fo_id:string"};
	private final String[] TC_SHOW = {"laminar/B738/nd/tc_show", "laminar/B738/nd/tc_fo_show"};
	private final String[] TC_X = {"laminar/B738/nd/tc_x", "laminar/B738/nd/tc_fo_x"};
	private final String[] TC_Y = {"laminar/B738/nd/tc_y", "laminar/B738/nd/tc_fo_y"};
	
	private final String[] DECEL_ID = {"laminar/B738/nd/decel_id:string", "laminar/B738/nd/decel_fo_id:string"};
	private final String[] DECEL_SHOW = {"laminar/B738/nd/decel_show", "laminar/B738/nd/decel_fo_show"};
	private final String[] DECEL_X = {"laminar/B738/nd/decel_x", "laminar/B738/nd/decel_fo_x"};
	private final String[] DECEL_Y = {"laminar/B738/nd/decel_y", "laminar/B738/nd/decel_fo_y"};
	
	private final String[] DECEL_DEF_SHOW = {"laminar/B738/nd/decel_def_show", "laminar/B738/nd/decel_def_fo_show"};
	private final String[] DECEL_DEF_X = {"laminar/B738/nd/decel_def_x", "laminar/B738/nd/decel_def_fo_x"};
	private final String[] DECEL_DEF_Y = {"laminar/B738/nd/decel_def_y", "laminar/B738/nd/decel_def_fo_y"};
	
	private final String[] DECEL_DEF2_SHOW = {"laminar/B738/nd/decel_def2_show", "laminar/B738/nd/decel_def2_fo_show"};
	private final String[] DECEL_DEF2_X = {"laminar/B738/nd/decel_def2_x", "laminar/B738/nd/decel_def2_fo_x"};
	private final String[] DECEL_DEF2_Y = {"laminar/B738/nd/decel_def2_y", "laminar/B738/nd/decel_def2_fo_y"};
	
	private final String[] TD_ID = {"laminar/B738/nd/td_id:string", "laminar/B738/nd/td_fo_id:string"};
	private final String[] TD_SHOW = {"laminar/B738/nd/td_show", "laminar/B738/nd/td_fo_show"};
	private final String[] TD_X = {"laminar/B738/nd/td_x", "laminar/B738/nd/td_fo_x"};
	private final String[] TD_Y = {"laminar/B738/nd/td_y", "laminar/B738/nd/td_fo_y"};
	
	private final String[] ED_ID = {"laminar/B738/nd/ed_id:string", "laminar/B738/nd/ed_fo_id:string"};
	private final String[] ED_SHOW = {"laminar/B738/nd/ed_show", "laminar/B738/nd/ed_fo_show"};
	private final String[] ED_X = {"laminar/B738/nd/ed_x", "laminar/B738/nd/ed_fo_x"};
	private final String[] ED_Y = {"laminar/B738/nd/ed_y", "laminar/B738/nd/ed_fo_y"};
	
	private final String ILS_X = "laminar/B738/pfd/ils_x";
	private final String ILS_X_MOD = "laminar/B738/pfd/ils_x_mod";
	private final String ILS_Y = "laminar/B738/pfd/ils_y";	
	private final String ILS_Y_MOD = "laminar/B738/pfd/ils_y_mod";	
	private final String ILS_X0 = "laminar/B738/pfd/ils_x0";
	private final String ILS_X0_MOD = "laminar/B738/pfd/ils_x0_mod";
	private final String ILS_Y0 = "laminar/B738/pfd/ils_y0";
	private final String ILS_Y0_MOD = "laminar/B738/pfd/ils_y0_mod";
	
	private final String ILS_FO_X = "laminar/B738/pfd/ils_fo_x";
	private final String ILS_FO_X_MOD = "laminar/B738/pfd/ils_fo_x_mod";
	private final String ILS_FO_Y = "laminar/B738/pfd/ils_fo_y";	
	private final String ILS_FO_Y_MOD = "laminar/B738/pfd/ils_fo_y_mod";	
	private final String ILS_FO_X0 = "laminar/B738/pfd/ils_fo_x0";
	private final String ILS_FO_X0_MOD = "laminar/B738/pfd/ils_fo_x0_mod";
	private final String ILS_FO_Y0 = "laminar/B738/pfd/ils_fo_y0";
	private final String ILS_FO_Y0_MOD = "laminar/B738/pfd/ils_fo_y0_mod";
	
	private final String ILS_RUNWAY = "laminar/B738/pfd/ils_runway:string";
	private final String ILS_RUNWAY_MOD = "laminar/B738/pfd/ils_runway_mod:string";
	private final String ILS_RUNWAY0 = "laminar/B738/pfd/ils_runway0:string";
	private final String ILS_RUNWAY0_MOD = "laminar/B738/pfd/ils_runway0_mod:string";
	
	private final String ILS_FO_RUNWAY = "laminar/B738/pfd/ils_fo_runway:string";
	private final String ILS_FO_RUNWAY_MOD = "laminar/B738/pfd/ils_fo_runway_mod:string";
	private final String ILS_FO_RUNWAY0 = "laminar/B738/pfd/ils_fo_runway0:string";
	private final String ILS_FO_RUNWAY0_MOD = "laminar/B738/pfd/ils_fo_runway0_mod:string";

//	public String[] apt_fo_id = new String[30];
	public int[][] apt_enable = new int[2][30];
	
	public float[] efis_disagree = new float[2];
	
	public int[] tc_show = new int[2];
	public String[] tc_id = new String[2];
	public float[] tc_x = new float[2];
	public float[] tc_y = new float[2];
	
	public int[] decel_show = new int[2];
	public String[] decel_id = new String[2];
	public float[] decel_x = new float[2];
	public float[] decel_y = new float[2];
	
	public int[] decel_def_show = new int[2];
	public float[] decel_def_x = new float[2];
	public float[] decel_def_y = new float[2];
	
	public int[] decel_def2_show = new int[2];
	public float[] decel_def2_x = new float[2];
	public float[] decel_def2_y = new float[2];
	
	public int[] td_show = new int[2];
	public String[] td_id = new String[2];
	public float[] td_x = new float[2];
	public float[] td_y = new float[2];
	
	public int[] ed_show = new int[2];
	public String[] ed_id = new String[2];
	public float[] ed_x = new float[2];
	public float[] ed_y = new float[2];
	
	public float ils_x = 0.0f;
	public float ils_x_mod = 0.0f;
	public float ils_y = 0.0f;
	public float ils_y_mod = 0.0f;
	public float ils_x0 = 0.0f;
	public float ils_x0_mod = 0.0f;
	public float ils_y0 = 0.0f;
	public float ils_y0_mod = 0.0f;
	
	public float ils_fo_x = 0.0f;
	public float ils_fo_x_mod = 0.0f;
	public float ils_fo_y = 0.0f;
	public float ils_fo_y_mod = 0.0f;
	public float ils_fo_x0 = 0.0f;
	public float ils_fo_x0_mod = 0.0f;
	public float ils_fo_y0 = 0.0f;
	public float ils_fo_y0_mod = 0.0f;
	
	public String ils_runway = "";
	public String ils_runway_mod = "";
	public String ils_runway0 = "";
	public String ils_runway0_mod = "";
	
	public String ils_fo_runway = "";
	public String ils_fo_runway_mod = "";
	public String ils_fo_runway0 = "";
	public String ils_fo_runway0_mod = "";

	private final String FIX_ID0 = "laminar/B738/nd/fix_id00:string";
	private final String FIX_ID1 = "laminar/B738/nd/fix_id01:string";
	private final String FIX_ID2 = "laminar/B738/nd/fix_id02:string";
	private final String FIX_ID3 = "laminar/B738/nd/fix_id03:string";
	private final String FIX_ID4 = "laminar/B738/nd/fix_id04:string";
	
	private final String FIX_FO_ID0 = "laminar/B738/nd/fix_fo_id00:string";
	private final String FIX_FO_ID1 = "laminar/B738/nd/fix_fo_id01:string";
	private final String FIX_FO_ID2 = "laminar/B738/nd/fix_fo_id02:string";
	private final String FIX_FO_ID3 = "laminar/B738/nd/fix_fo_id03:string";
	private final String FIX_FO_ID4 = "laminar/B738/nd/fix_fo_id04:string";
	
	private final String FIX_LAT = "laminar/B738/nd/fix_lat";
	private final String FIX_LON = "laminar/B738/nd/fix_lon";
	private final String FIX_TYPE = "laminar/B738/nd/fix_type";
	
	private final String FIX_DIST_0 = "laminar/B738/nd/fix_dist_0_nm";
	private final String FIX_DIST_1 = "laminar/B738/nd/fix_dist_1_nm";
	private final String FIX_DIST_2 = "laminar/B738/nd/fix_dist_2_nm";
	
	private final String FIX_RAD_DIST_0 = "laminar/B738/nd/fix_rad_dist_0";
	private final String FIX_RAD_DIST_1 = "laminar/B738/nd/fix_rad_dist_1";
	private final String FIX_RAD_DIST_2 = "laminar/B738/nd/fix_rad_dist_2";
	
	private final String FIX_RAD00_0 = "laminar/B738/nd/fix_rad00_0:string";
	private final String FIX_RAD00_1 = "laminar/B738/nd/fix_rad00_1:string";
	private final String FIX_RAD00_2 = "laminar/B738/nd/fix_rad00_2:string";
	
	private final String FIX_RAD01_0 = "laminar/B738/nd/fix_rad01_0:string";
	private final String FIX_RAD01_1 = "laminar/B738/nd/fix_rad01_1:string";
	private final String FIX_RAD01_2 = "laminar/B738/nd/fix_rad01_2:string";
	
	private final String FIX_RAD02_0 = "laminar/B738/nd/fix_rad02_0:string";
	private final String FIX_RAD02_1 = "laminar/B738/nd/fix_rad02_1:string";
	private final String FIX_RAD02_2 = "laminar/B738/nd/fix_rad02_2:string";
	
	private final String FIX_RAD03_0 = "laminar/B738/nd/fix_rad03_0:string";
	private final String FIX_RAD03_1 = "laminar/B738/nd/fix_rad03_1:string";
	private final String FIX_RAD03_2 = "laminar/B738/nd/fix_rad03_2:string";
	
	private final String FIX_RAD04_0 = "laminar/B738/nd/fix_rad04_0:string";
	private final String FIX_RAD04_1 = "laminar/B738/nd/fix_rad04_1:string";
	private final String FIX_RAD04_2 = "laminar/B738/nd/fix_rad04_2:string";
	
	private final String FIX_RAD_DIST_0A = "laminar/B738/nd/fix_rad_dist_0a";
	private final String FIX_RAD_DIST_1A = "laminar/B738/nd/fix_rad_dist_1a";
	private final String FIX_RAD_DIST_2A = "laminar/B738/nd/fix_rad_dist_2a";
	
	private final String[] FIX_ID_SHOW = {"laminar/B738/nd/fix_show", "laminar/B738/nd/fix_fo_show"};
	
	public float[] fix_lat = new float[5];
	public float[] fix_lon = new float[5];
	public float[] fix_type = new float[5];
	
	public float[] fix_dist_0 = new float[5];
	public float[] fix_dist_1 = new float[5];
	public float[] fix_dist_2 = new float[5];
	
	public float[] fix_rad_dist_0 = new float[5];
	public float[] fix_rad_dist_1 = new float[5];
	public float[] fix_rad_dist_2 = new float[5];
	
	public String fix_rad00_0 = "";
	public String fix_rad00_1 = "";
	public String fix_rad00_2 = "";
	
	public String fix_rad01_0 = "";
	public String fix_rad01_1 = "";
	public String fix_rad01_2 = "";
	
	public String fix_rad02_0 = "";
	public String fix_rad02_1 = "";
	public String fix_rad02_2 = "";
	
	public String fix_rad03_0 = "";
	public String fix_rad03_1 = "";
	public String fix_rad03_2 = "";
	
	public String fix_rad04_0 = "";
	public String fix_rad04_1 = "";
	public String fix_rad04_2 = "";
	
	public float[] fix_rad_dist_0a = new float[5];
	public float[] fix_rad_dist_1a = new float[5];
	public float[] fix_rad_dist_2a = new float[5];
	
	public boolean[] fix_id_show = new boolean[5];
	public boolean[] fix_id_fo_show = new boolean[5];
	
	public String[] fix_id = new String[5];
	public String[] fix_fo_id = new String[5];
	
	private final String IRS_MODE = "laminar/B738/irs/irs_mode";
	private final String IRS_MODE2 = "laminar/B738/irs/irs2_mode";
	
	private final String IRS_ENTRY = "laminar/B738/irs_entry";
	private final String IRS_ENTRY_LEN = "laminar/B738/irs_entry_len";
	private final String IRS_ENTRY_HDG_SHOW = "laminar/B738/irs_entry_hdg_show";
	private final String IRS_ENTRY_POS_SHOW = "laminar/B738/irs_entry_pos_show";
	
	private final String IRS_DECIMALS_SHOW = "laminar/B738/decimals_show";
	private final String IRS_NS_SHOW = "laminar/B738/ns_show";
	private final String IRS_EW_SHOW = "laminar/B738/ew_show";
	private final String IRS_LEFT1_SHOW = "laminar/B738/irs_left1_show";
	private final String IRS_LEFT2_SHOW = "laminar/B738/irs_left2_show";
	private final String IRS_RIGHT1_SHOW = "laminar/B738/irs_right1_show";
	private final String IRS_RIGHT2_SHOW = "laminar/B738/irs_right2_show";
	private final String IRS_ALIGN_SHOW = "laminar/B738/irs_allign_show";
	private final String IRS_LAT_DEG_SHOW = "laminar/B738/lat_deg_show";
	private final String IRS_LAT_MIN_SHOW = "laminar/B738/lat_min_show";
	private final String IRS_LON_DEG_SHOW = "laminar/B738/lon_deg_show";
	private final String IRS_LON_MIN_SHOW = "laminar/B738/lon_min_show";
	
	private final String IRS_EW = "laminar/B738/longitude_EW";
	private final String IRS_NS = "laminar/B738/latitude_NS";
	
	private final String IRS_LEFT1 = "laminar/B738/irs_left1";
	private final String IRS_LEFT2 = "laminar/B738/irs_left2";
	private final String IRS_RIGHT1 = "laminar/B738/irs_right1";
	private final String IRS_RIGHT2 = "laminar/B738/irs_right2";
	
	private final String IRS_LAT_DEG = "laminar/B738/latitude_deg";
	private final String IRS_LAT_MIN = "laminar/B738/latitude_min";
	private final String IRS_LON_DEG = "laminar/B738/longitude_deg";
	private final String IRS_LON_MIN = "laminar/B738/longitude_min";
	
	private final String IRS_KNOB = "laminar/B738/toggle_switch/irs_dspl_sel";
	private final String IRS_LEFT_REMAIN = "laminar/B738/irs/alignment_left_remain";
	private final String IRS_RIGHT_REMAIN = "laminar/B738/irs/alignment_right_remain";
	
	public String irs_left_string = "";
	public String irs_right_string = "";
	public float irs_decimals_show = 0;
	public float irs_mode = 0;
	public float irs_mode2 = 0;
	private float[] irs_entry = new float[15];
	private float irs_entry_len = 0;
	private float irs_entry_hdg_show = 0;
	private float irs_entry_pos_show = 0;
	private float irs_lat_deg_show = 0;
	private float irs_lat_min_show = 0;
	private float irs_lon_deg_show = 0;
	private float irs_lon_min_show = 0;
	private float irs_ns = 0;
	private float irs_ew = 0;
	private float irs_lat_deg = 0;
	private float irs_lat_min = 0;
	private float irs_lon_deg = 0;
	private float irs_lon_min = 0;
	private float irs_knob = 0;
	private float irs_ns_show = 0;
	private float irs_ew_show = 0;
	private float irs_left2 = 0;
	private float irs_right2 = 0;
	private float irs_left1 = 0;
	private float irs_right1 = 0;
	private float irs_left2_show = 0;
	private float irs_right2_show = 0;
	private float irs_align_show = 0;
	private float irs_left1_show = 0;
	private float irs_right1_show = 0;
	private float irs_left_remain = 0;
	private float irs_right_remain = 0;
	
	public NavData(ExtPlaneInterface iface) {
		super(iface);
		
		//only for irs
		this.drefs.add(IRS_MODE);
		this.drefs.add(IRS_MODE2);
		this.drefs.add(IRS_ENTRY);
		this.drefs.add(IRS_ENTRY_LEN);
		this.drefs.add(IRS_ENTRY_HDG_SHOW);
		this.drefs.add(IRS_ENTRY_POS_SHOW);
		this.drefs.add(IRS_DECIMALS_SHOW);
		this.drefs.add(IRS_NS_SHOW);
		this.drefs.add(IRS_EW_SHOW);
		this.drefs.add(IRS_LEFT1_SHOW);
		this.drefs.add(IRS_LEFT2_SHOW);
		this.drefs.add(IRS_RIGHT1_SHOW);
		this.drefs.add(IRS_RIGHT2_SHOW);
		this.drefs.add(IRS_ALIGN_SHOW);
		this.drefs.add(IRS_LAT_DEG_SHOW);
		this.drefs.add(IRS_LAT_MIN_SHOW);
		this.drefs.add(IRS_LON_DEG_SHOW);
		this.drefs.add(IRS_LON_MIN_SHOW);
		this.drefs.add(IRS_LEFT1);
		this.drefs.add(IRS_LEFT2);
		this.drefs.add(IRS_RIGHT1);
		this.drefs.add(IRS_RIGHT2);
		this.drefs.add(IRS_EW);
		this.drefs.add(IRS_NS);
		this.drefs.add(IRS_LAT_DEG);
		this.drefs.add(IRS_LAT_MIN);
		this.drefs.add(IRS_LON_DEG);
		this.drefs.add(IRS_LON_MIN);
		this.drefs.add(IRS_KNOB);
		this.drefs.add(IRS_LEFT_REMAIN);
		this.drefs.add(IRS_RIGHT_REMAIN);
		
		irs = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				switch(object.getName()) {
				case IRS_NS_SHOW: irs_ns_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_EW_SHOW: irs_ew_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_MODE: irs_mode = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_MODE2: irs_mode2 = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_ENTRY_LEN: irs_entry_len = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_ENTRY_HDG_SHOW: irs_entry_hdg_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_ENTRY_POS_SHOW: irs_entry_pos_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_DECIMALS_SHOW: irs_decimals_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_NS: irs_ns = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_EW: irs_ew = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LAT_DEG_SHOW: irs_lat_deg_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LAT_MIN_SHOW: irs_lon_min_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LON_DEG_SHOW: irs_lon_deg_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LON_MIN_SHOW: irs_lat_min_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LAT_DEG: irs_lat_deg = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LAT_MIN: irs_lat_min = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LON_DEG: irs_lon_deg = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LON_MIN: irs_lon_min = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LEFT2: irs_left2 = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_RIGHT2: irs_right2 = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LEFT1: irs_left1 = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_RIGHT1: irs_right1 = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LEFT2_SHOW: irs_left2_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_RIGHT2_SHOW: irs_right2_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_ALIGN_SHOW: irs_align_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LEFT1_SHOW: irs_left1_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_RIGHT1_SHOW: irs_right1_show = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_LEFT_REMAIN: irs_left_remain = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_RIGHT_REMAIN: irs_right_remain = Float.parseFloat(object.getValue()[0]);
				break;
				case IRS_KNOB: irs_knob = Float.parseFloat(object.getValue()[0]);
				break;
				}
				if(object.getName().equals(IRS_ENTRY)) {
					for(int i = 0; i < 15; i++) {
						irs_entry[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				buildIrsStrings();
			}

			private void buildIrsStrings() {
				
				boolean irs_aligned = false;
				
				irs_aligned = (irs_mode == 2 || irs_mode2 == 2);
								
				if(irs_ew_show == 2 || irs_ns_show == 2) { //test
					irs_left_string = "~88888";
					irs_right_string = "~188888";
				}else {
					if(irs_knob == 1) { //TK/GS //TEST
						if(!irs_aligned) {
							irs_left_string = "";
							irs_right_string = "";
						}else {
							irs_left_string = df3.format(irs_left2).replaceAll("0", "O");
							irs_right_string = df3.format(irs_right2).replaceAll("0", "O");
						}
					}else if(irs_knob == 2) { //PPOS
						if(!irs_aligned) {
							irs_left_string = "";
							irs_right_string = "";
						}else {
							if(irs_ns == 0) {
								irs_left_string = ("N" + (int)irs_lat_deg).replaceAll("0", "O") + ("" + (int)irs_lat_min).replaceAll("0", "O");
							}else {
								irs_left_string = ("S" + (int)irs_lat_deg).replaceAll("0", "O") + ("" + (int)irs_lat_min).replaceAll("0", "O");
							}
							if(irs_ew == 0) {
								irs_right_string = "E" + df3.format(irs_lon_deg).replaceAll("0", "O") + ("" + (int)irs_lon_min).replaceAll("0", "O");
							}else {
								irs_right_string = "W" + df3.format(irs_lon_deg).replaceAll("0", "O") + ("" + (int)irs_lon_min).replaceAll("0", "O");
							}
							if(irs_entry_pos_show == 1) {
								irs_left_string = "";
								irs_right_string = "";
								if (irs_entry_len >= 1) {
									if(irs_ns == 0) {
										irs_left_string = "N";
									} else {
										irs_left_string = "S";
									}
								}
								if (irs_entry_len >= 7) {
									if(irs_ew == 0) {								
										irs_right_string = "E";
									} else {
										irs_right_string = "W";
									}
								}
								for (int i = 2; i <= 6; i++) {
									if (irs_entry_len >= i) {
										irs_left_string = irs_left_string + (int)irs_entry[i - 2];
									}
								}
								for (int i = 1; i <= (6 - irs_entry_len); i++) {
									irs_left_string = irs_left_string + "    ";
								}
								for (int i = 8; i <= 14; i++) {
									if (irs_entry_len >= i) {
										irs_right_string = irs_right_string + (int)irs_entry[i - 3];
									}
								}
								for (int i = 1; i <= (13 - irs_entry_len); i++) {
									irs_right_string = irs_right_string + "    ";
								}
								irs_left_string = irs_left_string.replaceAll("0", "O");
								irs_right_string = irs_right_string.replaceAll("0", "O");
							}
						}

					}else if(irs_knob == 3) { //WIND
						if(!irs_aligned) {
							irs_left_string = "";
							irs_right_string = "";
						}else {
							irs_left_string = df3.format(irs_left2).replaceAll("0", "O");
							irs_right_string = df3.format(irs_right2).replaceAll("0", "O");
						}
					}else if (irs_knob == 4) { //HDG/STS
						if(!irs_aligned) {
							irs_left_string = "";
							if (irs_align_show == 1) {
								irs_right_string = "" + (int)irs_right1 + "                    ";
							} else {
								irs_right_string = "";
							}
						} else {
							if(irs_left1_show == 1) {
								irs_left_string = df3.format(irs_left1).replaceAll("0", "O");
							}
							if(irs_left2_show == 1){
								irs_left_string = df3.format(irs_left2).replaceAll("0", "O");
							}
							if(irs_right1_show == 1) {
								irs_right_string = df3.format(irs_right1).replaceAll("0", "O");
							}
							if(irs_right2_show == 1) {
								irs_right_string = df3.format(irs_right2).replaceAll("0", "O");
							}
							if(irs_entry_hdg_show == 1) {
								irs_left_string = "";
								irs_right_string = "";
								for (int i = 1; i <= 3; i++) {
									if (irs_entry_len >= i) {
										irs_left_string = irs_left_string + (int)irs_entry[i - 1];
									}
								}
								for (int i = 1; i <= (3 - irs_entry_len); i++) {
									irs_left_string = irs_left_string + "    ";
								}
								irs_left_string = irs_left_string.replaceAll("0", "O");
							}
						}

					}else {
						irs_left_string = "";
						irs_right_string = "";
					}
				}
			}
			
		};
		
		tc_td_decel_ed = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				for(int i = 0; i < 2; i++) {
					
					if(object.getName().equals(EFIS_DISAGREE[i])) {
						efis_disagree[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(TC_ID[i])) {
						tc_id[i] = object.getValue()[0].replaceAll("\"", "");	
					}
					if(object.getName().equals(TC_SHOW[i])) {
						tc_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(TC_X[i])) {
						tc_x[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(TC_Y[i])) {
						tc_y[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(DECEL_ID[i])) {
						decel_id[i] = object.getValue()[0].replaceAll("\"", "");
					}
					if(object.getName().equals(DECEL_SHOW[i])) {
						decel_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(DECEL_X[i])) {
						decel_x[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(DECEL_Y[i])) {
						decel_y[i] = Float.parseFloat(object.getValue()[0]);
					}					
					if(object.getName().equals(DECEL_DEF_SHOW[i])) {
						decel_def_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(DECEL_DEF_X[i])) {
						decel_def_x[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(DECEL_DEF_Y[i])) {
						decel_def_y[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(DECEL_DEF2_SHOW[i])) {
						decel_def2_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(DECEL_DEF2_X[i])) {
						decel_def2_x[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(DECEL_DEF2_Y[i])) {
						decel_def2_y[i] = Float.parseFloat(object.getValue()[0]);
					}					
					if(object.getName().equals(TD_ID[i])) {
						td_id[i] = object.getValue()[0].replaceAll("\"", "");
					}
					if(object.getName().equals(TD_SHOW[i])) {
						td_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(TD_X[i])) {
						td_x[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(TD_Y[i])) {
						td_y[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(ED_ID[i])) {
						ed_id[i] = object.getValue()[0].replaceAll("\"", "");
					}
					if(object.getName().equals(ED_SHOW[i])) {
						ed_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if(object.getName().equals(ED_X[i])) {
						ed_x[i] = Float.parseFloat(object.getValue()[0]);
					}
					if(object.getName().equals(ED_Y[i])) {
						ed_y[i] = Float.parseFloat(object.getValue()[0]);
					}

				}
			}
			
		};
		
		fixes = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				if(object.getName().equals(FIX_ID_SHOW[0])) {
					for(int i = 0; i < 5; i++) {
						int val = Integer.parseInt(object.getValue()[i]);
						if(val == 1) {
							fix_id_show[i] = true;
						}else {
							fix_id_show[i] = false;
						}
					}
				}
				
				switch(object.getName()) {
		
					case FIX_ID0: fix_id[0] = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_ID1: fix_id[1] = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_ID2: fix_id[2] = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_ID3: fix_id[3] = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_ID4: fix_id[4] = object.getValue()[0].replaceAll("\"", "");
					break;
					
					case FIX_FO_ID0: fix_fo_id[0] = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_FO_ID1: fix_fo_id[1] = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_FO_ID2: fix_fo_id[2] = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_FO_ID3: fix_fo_id[3] = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_FO_ID4: fix_fo_id[4] = object.getValue()[0].replaceAll("\"", "");
					break;
					
					case FIX_RAD00_0: fix_rad00_0 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD00_1: fix_rad00_1 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD00_2: fix_rad00_2 = object.getValue()[0].replaceAll("\"", "");
					break;
					
					case FIX_RAD01_0: fix_rad01_0 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD01_1: fix_rad01_1 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD01_2: fix_rad01_2 = object.getValue()[0].replaceAll("\"", "");
					break;
					
					case FIX_RAD02_0: fix_rad02_0 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD02_1: fix_rad02_1 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD02_2: fix_rad02_2 = object.getValue()[0].replaceAll("\"", "");
					break;
					
					case FIX_RAD03_0: fix_rad03_0 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD03_1: fix_rad03_1 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD03_2: fix_rad03_2 = object.getValue()[0].replaceAll("\"", "");
					break;

					case FIX_RAD04_0: fix_rad04_0 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD04_1: fix_rad04_1 = object.getValue()[0].replaceAll("\"", "");
					break;
					case FIX_RAD04_2: fix_rad04_2 = object.getValue()[0].replaceAll("\"", "");
					break;
				
				}
								
				if(object.getName().equals(FIX_LAT)) {
					for(int i = 0; i < 5; i++) {
						fix_lat[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_LON)) {
					for(int i = 0; i < 5; i++) {
						fix_lon[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_TYPE)) {
					for(int i = 0; i < 5; i++) {
						fix_type[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_DIST_0)) {
					for(int i = 0; i < 5; i++) {
						fix_dist_0[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_DIST_1)) {
					for(int i = 0; i < 5; i++) {
						fix_dist_1[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_DIST_2)) {
					for(int i = 0; i < 5; i++) {
						fix_dist_2[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_RAD_DIST_0)) {
					for(int i = 0; i < 5; i++) {
						fix_rad_dist_0[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_RAD_DIST_1)) {
					for(int i = 0; i < 5; i++) {
						fix_rad_dist_1[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_RAD_DIST_2)) {
					for(int i = 0; i < 5; i++) {
						fix_rad_dist_2[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_RAD_DIST_0A)) {
					for(int i = 0; i < 5; i++) {
						fix_rad_dist_0a[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_RAD_DIST_1A)) {
					for(int i = 0; i < 5; i++) {
						fix_rad_dist_1a[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				if(object.getName().equals(FIX_RAD_DIST_2A)) {
					for(int i = 0; i < 5; i++) {
						fix_rad_dist_2a[i] = Float.parseFloat(object.getValue()[i]);
					}
				}
				
				if(object.getName().equals(FIX_ID_SHOW[1])) {
					for(int i = 0; i < 5; i++) {
						int val = Integer.parseInt(object.getValue()[i]);
						if(val == 1) {
							fix_id_fo_show[i] = true;
						}else {
							fix_id_fo_show[i] = false;
						}
					}
				}
			}
			
		};
		
		runways = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {

				switch(object.getName()) {

				case ILS_X: ils_x = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_X_MOD: ils_x_mod = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_Y: ils_y = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_Y_MOD: ils_y_mod = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_X0: ils_x0 = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_X0_MOD: ils_x0_mod = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_Y0: ils_y0 = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_Y0_MOD: ils_y0_mod = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_FO_X: ils_fo_x = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_FO_X_MOD: ils_fo_x_mod = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_FO_Y: ils_fo_y = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_FO_Y_MOD: ils_fo_y_mod = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_FO_X0: ils_fo_x0 = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_FO_X0_MOD: ils_fo_x0_mod = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_FO_Y0: ils_fo_y0 = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_FO_Y0_MOD: ils_fo_y0_mod = Float.parseFloat(object.getValue()[0]);
				break;
				case ILS_RUNWAY: ils_runway = object.getValue()[0].replaceAll("\"", "");
				break;
				case ILS_RUNWAY_MOD: ils_runway_mod = object.getValue()[0].replaceAll("\"", "");
				break;
				case ILS_RUNWAY0: ils_runway0 = object.getValue()[0].replaceAll("\"", "");
				break;
				case ILS_RUNWAY0_MOD: ils_runway0_mod = object.getValue()[0].replaceAll("\"", "");
				break;
				case ILS_FO_RUNWAY: ils_fo_runway = object.getValue()[0].replaceAll("\"", "");
				break;
				case ILS_FO_RUNWAY_MOD: ils_fo_runway_mod = object.getValue()[0].replaceAll("\"", "");
				break;
				case ILS_FO_RUNWAY0: ils_fo_runway0 = object.getValue()[0].replaceAll("\"", "");
				break;
				case ILS_FO_RUNWAY0_MOD: ils_fo_runway0_mod = object.getValue()[0].replaceAll("\"", "");
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
		
		//irs
		for(String dref : drefs) {
			iface.includeDataRef(dref);
			iface.observeDataRef(dref, irs);
		}
	
		for(int i = 0; i < 2; i++) {

			iface.includeDataRef(EFIS_DISAGREE[i]);
			//
			iface.includeDataRef(TC_ID[i]);
			iface.includeDataRef(TC_SHOW[i]);
			iface.includeDataRef(TC_X[i], 0.001f);
			iface.includeDataRef(TC_Y[i], 0.001f);
			//
			iface.includeDataRef(DECEL_ID[i]);
			iface.includeDataRef(DECEL_SHOW[i]);
			iface.includeDataRef(DECEL_X[i], 0.001f);
			iface.includeDataRef(DECEL_Y[i], 0.001f);
			//
			iface.includeDataRef(DECEL_DEF_SHOW[i]);
			iface.includeDataRef(DECEL_DEF_X[i], 0.001f);
			iface.includeDataRef(DECEL_DEF_Y[i], 0.001f);
			//
			iface.includeDataRef(DECEL_DEF2_SHOW[i]);
			iface.includeDataRef(DECEL_DEF2_X[i], 0.001f);
			iface.includeDataRef(DECEL_DEF2_Y[i], 0.001f);
			//
			iface.includeDataRef(TD_ID[i]);
			iface.includeDataRef(TD_SHOW[i]);
			iface.includeDataRef(TD_X[i], 0.001f);
			iface.includeDataRef(TD_Y[i], 0.001f);
			//
			iface.includeDataRef(ED_ID[i]);
			iface.includeDataRef(ED_SHOW[i]);
			iface.includeDataRef(ED_X[i], 0.001f);
			iface.includeDataRef(ED_Y[i], 0.001f);
			//
			iface.observeDataRef(EFIS_DISAGREE[i], tc_td_decel_ed);
			//
			iface.observeDataRef(TC_ID[i], tc_td_decel_ed);
			iface.observeDataRef(TC_SHOW[i], tc_td_decel_ed);
			iface.observeDataRef(TC_X[i], tc_td_decel_ed);
			iface.observeDataRef(TC_Y[i], tc_td_decel_ed);
			//
			iface.observeDataRef(DECEL_ID[i], tc_td_decel_ed);
			iface.observeDataRef(DECEL_SHOW[i], tc_td_decel_ed);
			iface.observeDataRef(DECEL_X[i], tc_td_decel_ed);
			iface.observeDataRef(DECEL_Y[i], tc_td_decel_ed);			
			//
			iface.observeDataRef(DECEL_DEF_SHOW[i], tc_td_decel_ed);
			iface.observeDataRef(DECEL_DEF_X[i], tc_td_decel_ed);
			iface.observeDataRef(DECEL_DEF_Y[i], tc_td_decel_ed);			
			//
			iface.observeDataRef(DECEL_DEF2_SHOW[i], tc_td_decel_ed);
			iface.observeDataRef(DECEL_DEF2_X[i], tc_td_decel_ed);
			iface.observeDataRef(DECEL_DEF2_Y[i], tc_td_decel_ed);			
			//
			iface.observeDataRef(TD_ID[i], tc_td_decel_ed);
			iface.observeDataRef(TD_SHOW[i], tc_td_decel_ed);
			iface.observeDataRef(TD_X[i], tc_td_decel_ed);
			iface.observeDataRef(TD_Y[i], tc_td_decel_ed);
			//
			iface.observeDataRef(ED_ID[i], tc_td_decel_ed);
			iface.observeDataRef(ED_SHOW[i], tc_td_decel_ed);
			iface.observeDataRef(ED_X[i], tc_td_decel_ed);
			iface.observeDataRef(ED_Y[i], tc_td_decel_ed);
			
		}
		
		iface.includeDataRef(FIX_LAT);
		iface.includeDataRef(FIX_LON);
		iface.includeDataRef(FIX_TYPE);		
		iface.includeDataRef(FIX_DIST_0);
		iface.includeDataRef(FIX_DIST_1);
		iface.includeDataRef(FIX_DIST_2);
		iface.includeDataRef(FIX_RAD_DIST_0);
		iface.includeDataRef(FIX_RAD_DIST_1);
		iface.includeDataRef(FIX_RAD_DIST_2);
		iface.includeDataRef(FIX_RAD_DIST_0A);
		iface.includeDataRef(FIX_RAD_DIST_1A);
		iface.includeDataRef(FIX_RAD_DIST_2A);
		
		iface.observeDataRef(FIX_LAT, fixes);
		iface.observeDataRef(FIX_LON, fixes);
		iface.observeDataRef(FIX_TYPE, fixes);
		iface.observeDataRef(FIX_DIST_0, fixes);
		iface.observeDataRef(FIX_DIST_1, fixes);
		iface.observeDataRef(FIX_DIST_2, fixes);
		iface.observeDataRef(FIX_RAD_DIST_0, fixes);
		iface.observeDataRef(FIX_RAD_DIST_1, fixes);
		iface.observeDataRef(FIX_RAD_DIST_2, fixes);
		iface.observeDataRef(FIX_RAD_DIST_0A, fixes);
		iface.observeDataRef(FIX_RAD_DIST_1A, fixes);
		iface.observeDataRef(FIX_RAD_DIST_2A, fixes);
		
		iface.includeDataRef(FIX_ID0);
		iface.includeDataRef(FIX_ID1);
		iface.includeDataRef(FIX_ID2);
		iface.includeDataRef(FIX_ID3);
		iface.includeDataRef(FIX_ID4);
		iface.includeDataRef(FIX_FO_ID0);
		iface.includeDataRef(FIX_FO_ID1);
		iface.includeDataRef(FIX_FO_ID2);
		iface.includeDataRef(FIX_FO_ID3);
		iface.includeDataRef(FIX_FO_ID4);
		iface.includeDataRef(FIX_RAD00_0);
		iface.includeDataRef(FIX_RAD00_1);
		iface.includeDataRef(FIX_RAD00_2);
		iface.includeDataRef(FIX_RAD01_0);
		iface.includeDataRef(FIX_RAD01_1);
		iface.includeDataRef(FIX_RAD01_2);
		iface.includeDataRef(FIX_RAD02_0);
		iface.includeDataRef(FIX_RAD02_1);
		iface.includeDataRef(FIX_RAD02_2);
		iface.includeDataRef(FIX_RAD03_0);
		iface.includeDataRef(FIX_RAD03_1);
		iface.includeDataRef(FIX_RAD03_2);
		iface.includeDataRef(FIX_RAD04_0);
		iface.includeDataRef(FIX_RAD04_1);
		iface.includeDataRef(FIX_RAD04_2);
		
		iface.observeDataRef(FIX_ID0, fixes);
		iface.observeDataRef(FIX_ID1, fixes);
		iface.observeDataRef(FIX_ID2, fixes);
		iface.observeDataRef(FIX_ID3, fixes);
		iface.observeDataRef(FIX_ID4, fixes);
		iface.observeDataRef(FIX_FO_ID0, fixes);
		iface.observeDataRef(FIX_FO_ID1, fixes);
		iface.observeDataRef(FIX_FO_ID2, fixes);
		iface.observeDataRef(FIX_FO_ID3, fixes);
		iface.observeDataRef(FIX_FO_ID4, fixes);
		iface.observeDataRef(FIX_RAD00_0, fixes);
		iface.observeDataRef(FIX_RAD00_1, fixes);
		iface.observeDataRef(FIX_RAD00_2, fixes);
		iface.observeDataRef(FIX_RAD01_0, fixes);
		iface.observeDataRef(FIX_RAD01_1, fixes);
		iface.observeDataRef(FIX_RAD01_2, fixes);
		iface.observeDataRef(FIX_RAD02_0, fixes);
		iface.observeDataRef(FIX_RAD02_1, fixes);
		iface.observeDataRef(FIX_RAD02_2, fixes);
		iface.observeDataRef(FIX_RAD03_0, fixes);
		iface.observeDataRef(FIX_RAD03_1, fixes);
		iface.observeDataRef(FIX_RAD03_2, fixes);
		iface.observeDataRef(FIX_RAD04_0, fixes);
		iface.observeDataRef(FIX_RAD04_1, fixes);
		iface.observeDataRef(FIX_RAD04_2, fixes);
		
		for(int i = 0; i < 2; i++) {

			iface.includeDataRef(FIX_ID_SHOW[i]);
			iface.observeDataRef(FIX_ID_SHOW[i], fixes);
		}
		
		iface.includeDataRef(ILS_X, 0.01f);
		iface.includeDataRef(ILS_X_MOD, 0.01f);
		iface.includeDataRef(ILS_Y, 0.01f);
		iface.includeDataRef(ILS_Y_MOD, 0.01f);
		iface.includeDataRef(ILS_X0, 0.01f);
		iface.includeDataRef(ILS_X0_MOD, 0.01f);
		iface.includeDataRef(ILS_Y0, 0.01f);
		iface.includeDataRef(ILS_Y0_MOD, 0.01f);
		
		iface.includeDataRef(ILS_FO_X, 0.01f);
		iface.includeDataRef(ILS_FO_X_MOD, 0.01f);
		iface.includeDataRef(ILS_FO_Y, 0.01f);
		iface.includeDataRef(ILS_FO_Y_MOD, 0.01f);
		iface.includeDataRef(ILS_FO_X0, 0.01f);
		iface.includeDataRef(ILS_FO_X0_MOD, 0.01f);
		iface.includeDataRef(ILS_FO_Y0, 0.01f);
		iface.includeDataRef(ILS_FO_Y0_MOD, 0.01f);
		
		iface.includeDataRef(ILS_RUNWAY);
		iface.includeDataRef(ILS_RUNWAY_MOD);
		iface.includeDataRef(ILS_RUNWAY0);
		iface.includeDataRef(ILS_RUNWAY0_MOD);
		
		iface.includeDataRef(ILS_FO_RUNWAY);
		iface.includeDataRef(ILS_FO_RUNWAY_MOD);
		iface.includeDataRef(ILS_FO_RUNWAY0);
		iface.includeDataRef(ILS_FO_RUNWAY0_MOD);
		
		iface.observeDataRef(ILS_X, runways);
		iface.observeDataRef(ILS_X_MOD, runways);
		iface.observeDataRef(ILS_Y, runways);
		iface.observeDataRef(ILS_Y_MOD, runways);
		iface.observeDataRef(ILS_X0, runways);
		iface.observeDataRef(ILS_X0_MOD, runways);
		iface.observeDataRef(ILS_Y0, runways);
		iface.observeDataRef(ILS_Y0_MOD, runways);
		iface.observeDataRef(ILS_FO_X, runways);
		iface.observeDataRef(ILS_FO_X_MOD, runways);
		iface.observeDataRef(ILS_FO_Y, runways);
		iface.observeDataRef(ILS_FO_Y_MOD, runways);
		iface.observeDataRef(ILS_FO_X0, runways);
		iface.observeDataRef(ILS_FO_X0_MOD, runways);
		iface.observeDataRef(ILS_FO_Y0, runways);
		iface.observeDataRef(ILS_FO_Y0_MOD, runways);
		iface.observeDataRef(ILS_RUNWAY, runways);
		iface.observeDataRef(ILS_RUNWAY_MOD, runways);
		iface.observeDataRef(ILS_RUNWAY0, runways);
		iface.observeDataRef(ILS_RUNWAY0_MOD, runways);
		iface.observeDataRef(ILS_FO_RUNWAY, runways);
		iface.observeDataRef(ILS_FO_RUNWAY_MOD, runways);
		iface.observeDataRef(ILS_FO_RUNWAY0, runways);
		iface.observeDataRef(ILS_FO_RUNWAY0_MOD, runways);
		
	}


	@Override
	public void excludeDrefs() {
		
		//irs
		for(String dref : drefs) {
			iface.excludeDataRef(dref);
			iface.unObserveDataRef(dref, irs);
		}	
		
		for(int i = 0; i < 2; i++) {
			
			iface.excludeDataRef(EFIS_DISAGREE[i]);
			//
			iface.excludeDataRef(TC_ID[i]);
			iface.excludeDataRef(TC_SHOW[i]);
			iface.excludeDataRef(TC_X[i]);
			iface.excludeDataRef(TC_Y[i]);
			//
			iface.excludeDataRef(DECEL_ID[i]);
			iface.excludeDataRef(DECEL_SHOW[i]);
			iface.excludeDataRef(DECEL_X[i]);
			iface.excludeDataRef(DECEL_Y[i]);
			//
			iface.excludeDataRef(DECEL_DEF_SHOW[i]);
			iface.excludeDataRef(DECEL_DEF_X[i]);
			iface.excludeDataRef(DECEL_DEF_Y[i]);
			//
			iface.excludeDataRef(DECEL_DEF2_SHOW[i]);
			iface.excludeDataRef(DECEL_DEF2_X[i]);
			iface.excludeDataRef(DECEL_DEF2_Y[i]);
			//
			iface.excludeDataRef(TD_ID[i]);
			iface.excludeDataRef(TD_SHOW[i]);
			iface.excludeDataRef(TD_X[i]);
			iface.excludeDataRef(TD_Y[i]);
			//
			iface.excludeDataRef(ED_ID[i]);
			iface.excludeDataRef(ED_SHOW[i]);
			iface.excludeDataRef(ED_X[i]);
			iface.excludeDataRef(ED_Y[i]);
			//
			iface.unObserveDataRef(EFIS_DISAGREE[i], tc_td_decel_ed);
			//
			iface.unObserveDataRef(TC_ID[i], tc_td_decel_ed);
			iface.unObserveDataRef(TC_SHOW[i], tc_td_decel_ed);
			iface.unObserveDataRef(TC_X[i], tc_td_decel_ed);
			iface.unObserveDataRef(TC_Y[i], tc_td_decel_ed);
			//
			iface.unObserveDataRef(DECEL_ID[i], tc_td_decel_ed);
			iface.unObserveDataRef(DECEL_SHOW[i], tc_td_decel_ed);
			iface.unObserveDataRef(DECEL_X[i], tc_td_decel_ed);
			iface.unObserveDataRef(DECEL_Y[i], tc_td_decel_ed);
			//
			iface.unObserveDataRef(DECEL_DEF_SHOW[i], tc_td_decel_ed);
			iface.unObserveDataRef(DECEL_DEF_X[i], tc_td_decel_ed);
			iface.unObserveDataRef(DECEL_DEF_Y[i], tc_td_decel_ed);
			//
			iface.unObserveDataRef(DECEL_DEF2_SHOW[i], tc_td_decel_ed);
			iface.unObserveDataRef(DECEL_DEF2_X[i], tc_td_decel_ed);
			iface.unObserveDataRef(DECEL_DEF2_Y[i], tc_td_decel_ed);
			//
			iface.unObserveDataRef(TD_ID[i], tc_td_decel_ed);
			iface.unObserveDataRef(TD_SHOW[i], tc_td_decel_ed);
			iface.unObserveDataRef(TD_X[i], tc_td_decel_ed);
			iface.unObserveDataRef(TD_Y[i], tc_td_decel_ed);
			//
			iface.unObserveDataRef(ED_ID[i], tc_td_decel_ed);
			iface.unObserveDataRef(ED_SHOW[i], tc_td_decel_ed);
			iface.unObserveDataRef(ED_X[i], tc_td_decel_ed);
			iface.unObserveDataRef(ED_Y[i], tc_td_decel_ed);

		}
		
		iface.excludeDataRef(FIX_LAT);
		iface.excludeDataRef(FIX_LON);
		iface.excludeDataRef(FIX_TYPE);			
		iface.excludeDataRef(FIX_DIST_0);
		iface.excludeDataRef(FIX_DIST_1);
		iface.excludeDataRef(FIX_DIST_2);
		iface.excludeDataRef(FIX_RAD_DIST_0);
		iface.excludeDataRef(FIX_RAD_DIST_1);
		iface.excludeDataRef(FIX_RAD_DIST_2);
		iface.excludeDataRef(FIX_RAD_DIST_0A);
		iface.excludeDataRef(FIX_RAD_DIST_1A);
		iface.excludeDataRef(FIX_RAD_DIST_2A);

		iface.unObserveDataRef(FIX_LAT, fixes);
		iface.unObserveDataRef(FIX_LON, fixes);
		iface.unObserveDataRef(FIX_TYPE, fixes);
		iface.unObserveDataRef(FIX_DIST_0, fixes);
		iface.unObserveDataRef(FIX_DIST_1, fixes);
		iface.unObserveDataRef(FIX_DIST_2, fixes);
		iface.unObserveDataRef(FIX_RAD_DIST_0, fixes);
		iface.unObserveDataRef(FIX_RAD_DIST_1, fixes);
		iface.unObserveDataRef(FIX_RAD_DIST_2, fixes);
		iface.unObserveDataRef(FIX_RAD_DIST_0A, fixes);
		iface.unObserveDataRef(FIX_RAD_DIST_1A, fixes);
		iface.unObserveDataRef(FIX_RAD_DIST_2A, fixes);
		
		iface.excludeDataRef(FIX_ID0);
		iface.excludeDataRef(FIX_ID1);
		iface.excludeDataRef(FIX_ID2);
		iface.excludeDataRef(FIX_ID3);
		iface.excludeDataRef(FIX_ID4);
		iface.excludeDataRef(FIX_FO_ID0);
		iface.excludeDataRef(FIX_FO_ID1);
		iface.excludeDataRef(FIX_FO_ID2);
		iface.excludeDataRef(FIX_FO_ID3);
		iface.excludeDataRef(FIX_FO_ID4);
		iface.excludeDataRef(FIX_RAD00_0);
		iface.excludeDataRef(FIX_RAD00_1);
		iface.excludeDataRef(FIX_RAD00_2);
		iface.excludeDataRef(FIX_RAD01_0);
		iface.excludeDataRef(FIX_RAD01_1);
		iface.excludeDataRef(FIX_RAD01_2);
		iface.excludeDataRef(FIX_RAD02_0);
		iface.excludeDataRef(FIX_RAD02_1);
		iface.excludeDataRef(FIX_RAD02_2);
		iface.excludeDataRef(FIX_RAD03_0);
		iface.excludeDataRef(FIX_RAD03_1);
		iface.excludeDataRef(FIX_RAD03_2);
		iface.excludeDataRef(FIX_RAD04_0);
		iface.excludeDataRef(FIX_RAD04_1);
		iface.excludeDataRef(FIX_RAD04_2);
		
		iface.unObserveDataRef(FIX_ID0, fixes);
		iface.unObserveDataRef(FIX_ID1, fixes);
		iface.unObserveDataRef(FIX_ID2, fixes);
		iface.unObserveDataRef(FIX_ID3, fixes);
		iface.unObserveDataRef(FIX_ID4, fixes);
		iface.unObserveDataRef(FIX_FO_ID0, fixes);
		iface.unObserveDataRef(FIX_FO_ID1, fixes);
		iface.unObserveDataRef(FIX_FO_ID2, fixes);
		iface.unObserveDataRef(FIX_FO_ID3, fixes);
		iface.unObserveDataRef(FIX_FO_ID4, fixes);
		iface.unObserveDataRef(FIX_RAD00_0, fixes);
		iface.unObserveDataRef(FIX_RAD00_1, fixes);
		iface.unObserveDataRef(FIX_RAD00_2, fixes);
		iface.unObserveDataRef(FIX_RAD01_0, fixes);
		iface.unObserveDataRef(FIX_RAD01_1, fixes);
		iface.unObserveDataRef(FIX_RAD01_2, fixes);
		iface.unObserveDataRef(FIX_RAD02_0, fixes);
		iface.unObserveDataRef(FIX_RAD02_1, fixes);
		iface.unObserveDataRef(FIX_RAD02_2, fixes);
		iface.unObserveDataRef(FIX_RAD03_0, fixes);
		iface.unObserveDataRef(FIX_RAD03_1, fixes);
		iface.unObserveDataRef(FIX_RAD03_2, fixes);
		iface.unObserveDataRef(FIX_RAD04_0, fixes);
		iface.unObserveDataRef(FIX_RAD04_1, fixes);
		iface.unObserveDataRef(FIX_RAD04_2, fixes);
		
		for(int i = 0; i < 2; i++) {

			iface.excludeDataRef(FIX_ID_SHOW[i]);
			iface.unObserveDataRef(FIX_ID_SHOW[i], fixes);
		}
		
		iface.excludeDataRef(ILS_X);
		iface.excludeDataRef(ILS_X_MOD);
		iface.excludeDataRef(ILS_Y);
		iface.excludeDataRef(ILS_Y_MOD);
		iface.excludeDataRef(ILS_X0);
		iface.excludeDataRef(ILS_X0_MOD);
		iface.excludeDataRef(ILS_Y0);
		iface.excludeDataRef(ILS_Y0_MOD);
		
		iface.excludeDataRef(ILS_FO_X);
		iface.excludeDataRef(ILS_FO_X_MOD);
		iface.excludeDataRef(ILS_FO_Y);
		iface.excludeDataRef(ILS_FO_Y_MOD);
		iface.excludeDataRef(ILS_FO_X0);
		iface.excludeDataRef(ILS_FO_X0_MOD);
		iface.excludeDataRef(ILS_FO_Y0);
		iface.excludeDataRef(ILS_FO_Y0_MOD);
		
		iface.excludeDataRef(ILS_RUNWAY);
		iface.excludeDataRef(ILS_RUNWAY_MOD);
		iface.excludeDataRef(ILS_RUNWAY0);
		iface.excludeDataRef(ILS_RUNWAY0_MOD);
		
		iface.excludeDataRef(ILS_FO_RUNWAY);
		iface.excludeDataRef(ILS_FO_RUNWAY_MOD);
		iface.excludeDataRef(ILS_FO_RUNWAY0);
		iface.excludeDataRef(ILS_FO_RUNWAY0_MOD);
		
		iface.unObserveDataRef(ILS_X, runways);
		iface.unObserveDataRef(ILS_X_MOD, runways);
		iface.unObserveDataRef(ILS_Y, runways);
		iface.unObserveDataRef(ILS_Y_MOD, runways);
		iface.unObserveDataRef(ILS_X0, runways);
		iface.unObserveDataRef(ILS_X0_MOD, runways);
		iface.unObserveDataRef(ILS_Y0, runways);
		iface.unObserveDataRef(ILS_Y0_MOD, runways);
		iface.unObserveDataRef(ILS_FO_X, runways);
		iface.unObserveDataRef(ILS_FO_X_MOD, runways);
		iface.unObserveDataRef(ILS_FO_Y, runways);
		iface.unObserveDataRef(ILS_FO_Y_MOD, runways);
		iface.unObserveDataRef(ILS_FO_X0, runways);
		iface.unObserveDataRef(ILS_FO_X0_MOD, runways);
		iface.unObserveDataRef(ILS_FO_Y0, runways);
		iface.unObserveDataRef(ILS_FO_Y0_MOD, runways);
		iface.unObserveDataRef(ILS_RUNWAY, runways);
		iface.unObserveDataRef(ILS_RUNWAY_MOD, runways);
		iface.unObserveDataRef(ILS_RUNWAY0, runways);
		iface.unObserveDataRef(ILS_RUNWAY0_MOD, runways);
		iface.unObserveDataRef(ILS_FO_RUNWAY, runways);
		iface.unObserveDataRef(ILS_FO_RUNWAY_MOD, runways);
		iface.unObserveDataRef(ILS_FO_RUNWAY0, runways);
		iface.unObserveDataRef(ILS_FO_RUNWAY0_MOD, runways);
		
	}

}
