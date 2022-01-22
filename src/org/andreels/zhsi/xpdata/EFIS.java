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

public class EFIS extends BaseDataClass {
	
	Observer<DataRef> efis;
	
	private final String[] MAP_RANGE = {"laminar/B738/EFIS/capt/map_range", "laminar/B738/EFIS/fo/map_range"};
	private final String[] BARO_STD_SET = {"laminar/B738/EFIS/baro_set_std_pilot", "laminar/B738/EFIS/baro_set_std_copilot"};
	private final String[] BARO_SEL_SHOW = {"laminar/B738/EFIS/baro_sel_pilot_show", "laminar/B738/EFIS/baro_sel_copilot_show"};
	private final String[] TCAS_ON = {"laminar/B738/EFIS/tcas_on", "laminar/B738/EFIS/tcas_on_fo"};
	private final String[] BARO_SEL_IN_HG = {"laminar/B738/EFIS/baro_sel_in_hg_pilot", "laminar/B738/EFIS/baro_sel_in_hg_copilot"};
	private final String[] BARO_IN_HPA = {"laminar/B738/EFIS_control/capt/baro_in_hpa", "laminar/B738/EFIS_control/fo/baro_in_hpa"}; //if switch is IN or HPA
	private final String[] MINIMUMS = {"laminar/B738/EFIS_control/cpt/minimums", "laminar/B738/EFIS_control/fo/minimums"};
	private final String[] BARO_MIN = {"laminar/B738/EFIS_control/pfd/baro_min_cpt", "laminar/B738/EFIS_control/pfd/baro_min_fo"};
	private final String[] BARO_MIN_SHOW = {"laminar/B738/EFIS_control/pfd/baro_min_cpt_show", "laminar/B738/EFIS_control/pfd/baro_min_fo_show"};
	private final String[] MAP_MODE = {"laminar/B738/EFIS_control/capt/map_mode_pos", "laminar/B738/EFIS_control/fo/map_mode_pos"};
	private final String[] RADIO_ALT_BUG = {"sim/cockpit2/gauges/actuators/radio_altimeter_bug_ft_pilot", "sim/cockpit2/gauges/actuators/radio_altimeter_bug_ft_copilot"}; //value
	private final String[] RADIO_ALT_LIT = {"sim/cockpit2/gauges/indicators/radio_altimeter_dh_lit_pilot", "sim/cockpit2/gauges/indicators/radio_altimeter_dh_lit_copilot"}; //if shown
	private final String[] WXR_ON = {"laminar/B738/EFIS/EFIS_wx_on", "laminar/B738/EFIS/fo/EFIS_wx_on"};
	private final String[] VOR_ON = {"laminar/B738/EFIS/EFIS_vor_on", "laminar/B738/EFIS/fo/EFIS_vor_on"}; //STA
	private final String[] WPT_ON = {"laminar/B738/EFIS/EFIS_fix_on", "laminar/B738/EFIS/fo/EFIS_fix_on"}; //FIX
	private final String[] ARPT_ON = {"laminar/B738/EFIS/EFIS_airport_on", "laminar/B738/EFIS/fo/EFIS_airport_on"}; //ARPT
	private final String[] DATA_ON = {"laminar/B738/EFIS/capt/data_status", "laminar/B738/EFIS/fo/data_status"}; //DATA
	private final String[] POS_ON = {"laminar/B738/EFIS_control/capt/push_button/pos", "laminar/B738/EFIS_control/fo/push_button/pos"}; //toggle
	private final String[] TERR_ON = {"laminar/B738/EFIS_control/capt/terr_on", "laminar/B738/EFIS_control/fo/terr_on"}; //TERR
	private final String[] VOR1_POS = {"laminar/B738/EFIS_control/capt/vor1_off_pos", "laminar/B738/EFIS_control/fo/vor1_off_pos"};
	private final String[] VOR2_POS = {"laminar/B738/EFIS_control/capt/vor2_off_pos", "laminar/B738/EFIS_control/fo/vor2_off_pos"};
	private final String[] RST = {"laminar/B738/EFIS_control/capt/push_button/rst", "laminar/B738/EFIS_control/fo/push_button/rst"}; //toggle
	private final String[] FPV = {"laminar/B738/EFIS_control/capt/push_button/fpv", "laminar/B738/EFIS_control/fo/push_button/fpv"}; //toggle
	private final String[] FPV_SHOW = {"laminar/B738/PFD/capt/fpv_on", "laminar/B738/PFD/fo/fpv_on"};
	private final String[] FPV_HORIZ = {"laminar/B738/pfd/fpv_horiz", "laminar/B738/pfd/fpv_horiz_fo"};
	private final String[] FPV_VERT = {"laminar/B738/pfd/fpv_vert", "laminar/B738/pfd/fpv_vert_fo"};
	private final String[] MTRS = {"laminar/B738/PFD/capt/alt_mode_is_meters", "laminar/B738/PFD/fo/alt_mode_is_meters"};
	private final String[] EXP_MAP = {"laminar/B738/EFIS_control/capt/exp_map", "laminar/B738/EFIS_control/fo/exp_map"};
	private final String[] VSD_MAP = {"laminar/B738/EFIS_control/capt/vsd_map", "laminar/B738/EFIS_control/fo/vsd_map"};

	private boolean[] is_pos_on = new boolean[2];
	private boolean[] is_rst_on = new boolean[2];
	private boolean[] is_fpv_on = new boolean[2];
	public int[] map_range = new int[2];
	public int[] baro_std_set = new int[2];
	public int[] baro_sel_show = new int[2];
	public int[] tcas_on = new int[2];
	public float[] baro_sel_in_hg = new float[2];
	public int[] baro_in_hpa = new int[2];
	public int[] minimums = new int[2];
	public float[] baro_min = new float[2];
	public int[] baro_min_show = new int[2];
	public int[] map_mode = new int[2];
	public int[] radio_alt_bug = new int[2];
	public int[] radio_alt_lit = new int[2];
	public int[] wxr_on = new int[2];
	public int[] vor_on = new int[2];
	public int[] wpt_on = new int[2];
	public int[] arpt_on = new int[2];
	public int[] data_on = new int[2];
	public int[] pos_on = new int[2];
	public int[] terr_on = new int[2];
	public int[] vor1_pos = new int[2];
	public int[] vor2_pos = new int[2];
	public int[] rst = new int[2];
	public int[] fpv = new int[2];
	public int[] mtrs = new int[2];
	public int[] exp_map = new int[2];
	public int[] vsd_map = new int[2];
	public int[] fpv_show = new int[2];
	public float[] fpv_horiz = new float[2];
	public float[] fpv_vert = new float[2];


	public EFIS(ExtPlaneInterface iface) {
		super(iface);
		
		// EFIS
		efis = new Observer<DataRef>() { 

			@Override
			public void update(DataRef object) {

				for(int i = 0; i < 2; i++) {
					if (object.getName().equals(MAP_RANGE[i])) { map_range[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(BARO_STD_SET[i])) { baro_std_set[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(BARO_SEL_SHOW[i])) { baro_sel_show[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(TCAS_ON[i])) { tcas_on[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(BARO_SEL_IN_HG[i])) { baro_sel_in_hg[i] = Float.parseFloat(object.getValue()[0]); }
					if (object.getName().equals(BARO_IN_HPA[i])) { baro_in_hpa[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(MINIMUMS[i])) { minimums[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(BARO_MIN[i])) { baro_min[i] = Float.parseFloat(object.getValue()[0]); }
					if (object.getName().equals(BARO_MIN_SHOW[i])) { baro_min_show[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(RADIO_ALT_BUG[i])) { radio_alt_bug[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(RADIO_ALT_LIT[i])) { radio_alt_lit[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(MAP_MODE[i])) { map_mode[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(WXR_ON[i])) { wxr_on[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(VOR_ON[i])) { vor_on[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(WPT_ON[i])) { wpt_on[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(ARPT_ON[i])) { arpt_on[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(DATA_ON[i])) { data_on[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(TERR_ON[i])) { terr_on[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(VOR1_POS[i])) { vor1_pos[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(VOR2_POS[i])) { vor2_pos[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(EXP_MAP[i])) { exp_map[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(VSD_MAP[i])) { vsd_map[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(MTRS[i])) { mtrs[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(FPV_SHOW[i])) { fpv_show[i] = Integer.parseInt(object.getValue()[0]); }
					if (object.getName().equals(FPV_HORIZ[i])) { fpv_horiz[i] = Float.parseFloat(object.getValue()[0]); }
					if (object.getName().equals(FPV_VERT[i])) { fpv_vert[i] = Float.parseFloat(object.getValue()[0]); }

					if (object.getName().equals(POS_ON[i])) {

						if(object.getValue()[0].equals("1") && !is_pos_on[i]) {
							pos_on[i] = 1;
							is_pos_on[i] = true;
						}else if(object.getValue()[0].equals("1") && is_pos_on[i]) {
							pos_on[i] = 0;
							is_pos_on[i] = false;							
						}
					}
					if (object.getName().equals(RST[i])) {

						if(object.getValue()[0].equals("1") && !is_rst_on[i]) {
							rst[i] = 1;
							is_rst_on[i] = true;
						}else if(object.getValue()[0].equals("1") && is_rst_on[i]) {
							rst[i] = 0;
							is_rst_on[i] = false;							
						}
					}
					if (object.getName().equals(FPV[i])) {

						if(object.getValue()[0].equals("1") && !is_fpv_on[i]) {
							fpv[i] = 1;
							is_fpv_on[i] = true;
						}else if(object.getValue()[0].equals("1") && is_fpv_on[i]) {
							fpv[i] = 0;
							is_fpv_on[i] = false;							
						}
					}
					//						if (object.getName().equals(MTRS[i])) {
					//
					//							if(object.getValue()[0].equals("1") && !is_mtrs_on[i]) {
					//								mtrs[i] = 1;
					//								is_mtrs_on[i] = true;
					//							}else if(object.getValue()[0].equals("1") && is_mtrs_on[i]) {
					//								mtrs[i] = 0;
					//								is_mtrs_on[i] = false;							
					//							}
					//						}
				}
			}

		};
	}



	@Override
	public void subscribeDrefs() {

	}



	@Override
	public void includeDrefs() {
		
		for(int i = 0; i < 2; i++) {

			iface.includeDataRef(MAP_RANGE[i]);
			iface.includeDataRef(BARO_STD_SET[i]);
			iface.includeDataRef(BARO_SEL_SHOW[i]);
			iface.includeDataRef(TCAS_ON[i]);
			iface.includeDataRef(BARO_SEL_IN_HG[i]);
			iface.includeDataRef(BARO_IN_HPA[i]);
			iface.includeDataRef(MINIMUMS[i]);
			iface.includeDataRef(BARO_MIN[i]);
			iface.includeDataRef(BARO_MIN_SHOW[i]);
			iface.includeDataRef(RADIO_ALT_BUG[i]);
			iface.includeDataRef(RADIO_ALT_LIT[i]);
			iface.includeDataRef(WXR_ON[i]);
			iface.includeDataRef(MAP_MODE[i]);
			iface.includeDataRef(VOR_ON[i]);
			iface.includeDataRef(WPT_ON[i]);
			iface.includeDataRef(ARPT_ON[i]);
			iface.includeDataRef(DATA_ON[i]);
			iface.includeDataRef(TERR_ON[i]);
			iface.includeDataRef(VOR1_POS[i]);
			iface.includeDataRef(VOR2_POS[i]);
			iface.includeDataRef(POS_ON[i]);
			iface.includeDataRef(RST[i]);
			iface.includeDataRef(FPV[i]);
			iface.includeDataRef(MTRS[i]);
			iface.includeDataRef(EXP_MAP[i]);
			iface.includeDataRef(VSD_MAP[i]);
			iface.includeDataRef(FPV_SHOW[i]);
			iface.includeDataRef(FPV_HORIZ[i], 0.01f);
			iface.includeDataRef(FPV_VERT[i], 0.01f);

			iface.observeDataRef(MAP_RANGE[i], efis);
			iface.observeDataRef(BARO_STD_SET[i], efis);
			iface.observeDataRef(BARO_SEL_SHOW[i], efis);
			iface.observeDataRef(TCAS_ON[i], efis);
			iface.observeDataRef(BARO_SEL_IN_HG[i], efis);
			iface.observeDataRef(BARO_IN_HPA[i], efis);
			iface.observeDataRef(MINIMUMS[i], efis);
			iface.observeDataRef(BARO_MIN[i], efis);
			iface.observeDataRef(BARO_MIN_SHOW[i], efis);
			iface.observeDataRef(RADIO_ALT_BUG[i], efis);
			iface.observeDataRef(RADIO_ALT_LIT[i], efis);
			iface.observeDataRef(WXR_ON[i], efis);
			iface.observeDataRef(MAP_MODE[i], efis);
			iface.observeDataRef(VOR_ON[i], efis);
			iface.observeDataRef(WPT_ON[i], efis);
			iface.observeDataRef(ARPT_ON[i], efis);
			iface.observeDataRef(DATA_ON[i], efis);
			iface.observeDataRef(TERR_ON[i], efis);
			iface.observeDataRef(VOR1_POS[i], efis);
			iface.observeDataRef(VOR2_POS[i], efis);
			iface.observeDataRef(POS_ON[i], efis);
			iface.observeDataRef(RST[i], efis);
			iface.observeDataRef(FPV[i], efis);
			iface.observeDataRef(MTRS[i], efis);
			iface.observeDataRef(EXP_MAP[i], efis);
			iface.observeDataRef(VSD_MAP[i], efis);
			iface.observeDataRef(FPV_SHOW[i], efis);
			iface.observeDataRef(FPV_HORIZ[i], efis);
			iface.observeDataRef(FPV_VERT[i], efis);
			
		}
		
	}



	@Override
	public void excludeDrefs() {
		
		for(int i = 0; i < 2; i++) {

			iface.excludeDataRef(MAP_RANGE[i]);
			iface.excludeDataRef(BARO_STD_SET[i]);
			iface.excludeDataRef(BARO_SEL_SHOW[i]);
			iface.excludeDataRef(TCAS_ON[i]);
			iface.excludeDataRef(BARO_SEL_IN_HG[i]);
			iface.excludeDataRef(BARO_IN_HPA[i]);
			iface.excludeDataRef(MINIMUMS[i]);
			iface.excludeDataRef(BARO_MIN[i]);
			iface.excludeDataRef(BARO_MIN_SHOW[i]);
			iface.excludeDataRef(RADIO_ALT_BUG[i]);
			iface.excludeDataRef(RADIO_ALT_LIT[i]);
			iface.excludeDataRef(WXR_ON[i]);
			iface.excludeDataRef(MAP_MODE[i]);
			iface.excludeDataRef(VOR_ON[i]);
			iface.excludeDataRef(WPT_ON[i]);
			iface.excludeDataRef(ARPT_ON[i]);
			iface.excludeDataRef(DATA_ON[i]);
			iface.excludeDataRef(TERR_ON[i]);
			iface.excludeDataRef(VOR1_POS[i]);
			iface.excludeDataRef(VOR2_POS[i]);
			iface.excludeDataRef(POS_ON[i]);
			iface.excludeDataRef(RST[i]);
			iface.excludeDataRef(FPV[i]);
			iface.excludeDataRef(MTRS[i]);
			iface.excludeDataRef(EXP_MAP[i]);
			iface.excludeDataRef(VSD_MAP[i]);
			iface.excludeDataRef(FPV_SHOW[i]);
			iface.excludeDataRef(FPV_HORIZ[i]);
			iface.excludeDataRef(FPV_VERT[i]);

			iface.unObserveDataRef(MAP_RANGE[i], efis);
			iface.unObserveDataRef(BARO_STD_SET[i], efis);
			iface.unObserveDataRef(BARO_SEL_SHOW[i], efis);
			iface.unObserveDataRef(TCAS_ON[i], efis);
			iface.unObserveDataRef(BARO_SEL_IN_HG[i], efis);
			iface.unObserveDataRef(BARO_IN_HPA[i], efis);
			iface.unObserveDataRef(MINIMUMS[i], efis);
			iface.unObserveDataRef(BARO_MIN[i], efis);
			iface.unObserveDataRef(BARO_MIN_SHOW[i], efis);
			iface.unObserveDataRef(RADIO_ALT_BUG[i], efis);
			iface.unObserveDataRef(RADIO_ALT_LIT[i], efis);
			iface.unObserveDataRef(WXR_ON[i], efis);
			iface.unObserveDataRef(MAP_MODE[i], efis);
			iface.unObserveDataRef(VOR_ON[i], efis);
			iface.unObserveDataRef(WPT_ON[i], efis);
			iface.unObserveDataRef(ARPT_ON[i], efis);
			iface.unObserveDataRef(DATA_ON[i], efis);
			iface.unObserveDataRef(TERR_ON[i], efis);
			iface.unObserveDataRef(VOR1_POS[i], efis);
			iface.unObserveDataRef(VOR2_POS[i], efis);
			iface.unObserveDataRef(POS_ON[i], efis);
			iface.unObserveDataRef(RST[i], efis);
			iface.unObserveDataRef(FPV[i], efis);
			iface.unObserveDataRef(MTRS[i], efis);
			iface.unObserveDataRef(EXP_MAP[i], efis);
			iface.unObserveDataRef(VSD_MAP[i], efis);
			iface.unObserveDataRef(FPV_SHOW[i], efis);
			iface.unObserveDataRef(FPV_HORIZ[i], efis);
			iface.unObserveDataRef(FPV_VERT[i], efis);
			
		}
		
	}	

}