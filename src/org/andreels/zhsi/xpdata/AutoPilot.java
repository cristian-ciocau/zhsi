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

public class AutoPilot extends BaseDataClass {
	
	
	Observer<DataRef> autopilot;

	private final String MCP_HDG = "laminar/B738/autopilot/mcp_hdg_dial";
	private final String[] MCP_SPEED_KTS2 = {"laminar/B738/autopilot/mcp_speed_dial_kts2","laminar/B738/autopilot/mcp_speed_dial_kts2_fo"};
	private final String MCP_SPEED_KTS_MACH = "laminar/B738/autopilot/mcp_speed_dial_kts_mach";
	private final String MCP_SPEED_IS_MACH = "sim/cockpit2/autopilot/airspeed_is_mach";
	private final String MCP_VVI = "laminar/B738/autopilot/ap_vvi_pos";
	private final String MCP_ALT = "laminar/B738/autopilot/mcp_alt_dial";
	private final String[] CMD_STATUS = {"laminar/B738/autopilot/cmd_a_status","laminar/B738/autopilot/cmd_b_status"};
	private final String[] FD_STATUS = {"laminar/B738/autopilot/pfd_fd_cmd", "laminar/B738/autopilot/pfd_fd_cmd_fo"};
	private final String[] FD_PITCH_SHOW = {"laminar/B738/autopilot/fd_pitch_pilot_show", "laminar/B738/autopilot/fd_pitch_copilot_show"};
	private final String[] FD_ROLL_SHOW = {"laminar/B738/autopilot/fd_roll_pilot_show", "laminar/B738/autopilot/fd_roll_copilot_show"};
	private final String FD_MODE = "sim/cockpit2/autopilot/flight_director_mode";
	private final String ILS_POINTER_DISABLE = "laminar/B738/autopilot/ils_pointer_disable";
	private final String[] FD_REC_MODE = {"laminar/B738/autopilot/rec_cmd_modes","laminar/B738/autopilot/rec_cmd_modes_fo"};
	private final String ALT_DISAGREE = "laminar/B738/autopilot/alt_disagree";
	private final String IAS_DISAGREE = "laminar/B738/autopilot/ias_disagree";
	private final String[] MCP_COURSE = {"laminar/B738/autopilot/course_pilot","laminar/B738/autopilot/course_copilot"};
	private final String PFD_SPD_MODE = "laminar/B738/autopilot/pfd_spd_mode";
	private final String REC_THR_MODES = "laminar/B738/autopilot/rec_thr_modes";
	private final String REC_THR2_MODES = "laminar/B738/autopilot/rec_thr2_modes";
	private final String PFD_HDG_MODE = "laminar/B738/autopilot/pfd_hdg_mode";
	private final String REC_HDG_MODE = "laminar/B738/autopilot/rec_hdg_modes";
	private final String PFD_HDG_MODE_ARM = "laminar/B738/autopilot/pfd_hdg_mode_arm";
	private final String PFD_ALT_MODE = "laminar/B738/autopilot/pfd_alt_mode";
	private final String REC_ALT_MODES = "laminar/B738/autopilot/rec_alt_modes";
	private final String PFD_ALT_MODE_ARM = "laminar/B738/autopilot/pfd_alt_mode_arm";
	private final String AP_FD_ROLL_DEG = "sim/cockpit2/autopilot/flight_director_roll_deg";
	private final String AP_FD_PITCH_DEG = "sim/cockpit2/autopilot/flight_director_pitch_deg";
	private final String SINGLE_CH = "laminar/B738/autopilot/single_ch_status";
	private final String SINGLE_CH_G = "laminar/B738/autopilot/single_ch_g_status"; 	
	private final String SINGLE_CH_REC_MODE = "laminar/B738/autopilot/rec_sch_modes";
	private final String FLARE_STATUS = "laminar/B738/autopilot/flare_status"; //LAND 3
	private final String[] PFD_MODE = {"laminar/B738/autopilot/pfd_mode", "laminar/B738/autopilot/pfd_mode_fo"}; // 1 (ils), 2 (lnav/vnav), 3 (loc/vnav), 4 (fmc), 5 (loc/gp), 6 (gls), 7 (fac/vnav)
	private final String VS_MODE = "laminar/B738/autopilot/vs_status";
	private final String NPS_DEVIATION = "laminar/B738/autopilot/gps_horizont"; //range from -2.5 to 2.5
	private final String FAC_HORIZONT = "laminar/B738/autopilot/fac_horizont"; //range from -2.5 to 2.5
	private final String CWS_R_STATUS = "laminar/B738/autopilot/cws_r_status";
	private final String CWS_P_STATUS = "laminar/B738/autopilot/cws_p_status";

	public float mcp_hdg = 0;
	public float[] mcp_speed_kts2 = new float[2];
	public float mcp_speed_kts_mach = 0f;
	public float mcp_speed_is_mach = 0f;
	public float mcp_vvi = 0;
	public int mcp_alt = 0;
	public int[] cmd_status = new int[2];
	public int[] fd_status = new int[2];
	public int[] fd_show = new int[2];
	public int fd_mode = 0;
	public float ils_pointer_disable = 0f;
	public int[] fd_pitch_show = new int[2];
	public int[] fd_roll_show = new int[2];
	public int[] fd_rec_mode = new int[2];
	public int[] mcp_course = new int[2];
	public int alt_disagree = 0;
	public int ias_disagree = 0;
	public int pfd_spd_mode = 0;
	public int rec_thr_modes = 0;
	public int rec_thr2_modes = 0;
	public int pfd_hdg_mode = 0;
	public int rec_hdg_mode = 0;
	public int pfd_hdg_mode_arm = 0;
	public int pfd_alt_mode = 0;
	public int rec_alt_modes = 0;
	public int pfd_alt_mode_arm = 0;
	public int single_ch_rec_mode = 0;
	public float ap_fd_roll_deg = 0f;
	public float ap_fd_pitch_deg = 0f;
	public int single_ch = 0;
	public int single_ch_g = 0;
	public int flare_status = 0;
	public int[] pfd_mode = new int[2];
	public int vs_mode = 0;
	public float nps_deviation = 0f;
	public float fac_horizont = 0f;
	public int cws_r_status = 0;
	public int cws_p_status = 0;
	

	public AutoPilot(ExtPlaneInterface iface) {
		super(iface);
		
		// AP
		autopilot = new Observer<DataRef>() { // create FMS objects when data changes

			@Override
			public void update(DataRef object) {

				switch (object.getName()) {
				case MCP_VVI:
					mcp_vvi = Float.parseFloat(object.getValue()[0]);
					break;
				case MCP_HDG:
					mcp_hdg = Float.parseFloat(object.getValue()[0]);
					break;
				case MCP_SPEED_KTS_MACH:
					mcp_speed_kts_mach = Float.parseFloat(object.getValue()[0]);
					break;
				case MCP_SPEED_IS_MACH:
					mcp_speed_is_mach = Float.parseFloat(object.getValue()[0]);
					break;
				case MCP_ALT:
					mcp_alt = Integer.parseInt(object.getValue()[0]);
					break;
				case ALT_DISAGREE:
					alt_disagree = Integer.parseInt(object.getValue()[0]);
					break;
				case IAS_DISAGREE:
					ias_disagree = Integer.parseInt(object.getValue()[0]);
					break;
				case PFD_SPD_MODE:
					pfd_spd_mode = Integer.parseInt(object.getValue()[0]);
					break;
				case REC_THR_MODES:
					rec_thr_modes = Integer.parseInt(object.getValue()[0]);
					break;
				case REC_THR2_MODES:
					rec_thr2_modes = Integer.parseInt(object.getValue()[0]);
					break;
				case PFD_HDG_MODE:
					pfd_hdg_mode = Integer.parseInt(object.getValue()[0]);
					break;
				case REC_HDG_MODE:
					rec_hdg_mode = Integer.parseInt(object.getValue()[0]);
					break;
				case PFD_HDG_MODE_ARM:
					pfd_hdg_mode_arm = Integer.parseInt(object.getValue()[0]);
					break;
				case PFD_ALT_MODE:
					pfd_alt_mode = Integer.parseInt(object.getValue()[0]);
					break;
				case REC_ALT_MODES:
					rec_alt_modes = Integer.parseInt(object.getValue()[0]);
					break;
				case PFD_ALT_MODE_ARM:
					pfd_alt_mode_arm = Integer.parseInt(object.getValue()[0]);
					break;
				case AP_FD_ROLL_DEG:
					ap_fd_roll_deg = Float.parseFloat(object.getValue()[0]);
					break;
				case AP_FD_PITCH_DEG:
					ap_fd_pitch_deg = Float.parseFloat(object.getValue()[0]);
					break;
				case SINGLE_CH:
					single_ch = Integer.parseInt(object.getValue()[0]);
					break;
				case SINGLE_CH_G:
					single_ch_g = Integer.parseInt(object.getValue()[0]);
					break;
				case SINGLE_CH_REC_MODE:
					single_ch_rec_mode = Integer.parseInt(object.getValue()[0]);
					break;	
				case FLARE_STATUS:
					flare_status = Integer.parseInt(object.getValue()[0]);
					break;
				case VS_MODE:
					vs_mode = Integer.parseInt(object.getValue()[0]);
					break;
				case NPS_DEVIATION:
					nps_deviation = Float.parseFloat(object.getValue()[0]);
					break;
				case FAC_HORIZONT:
					fac_horizont = Float.parseFloat(object.getValue()[0]);
					break;
				case CWS_R_STATUS:
					cws_r_status = Integer.parseInt(object.getValue()[0]);
					break;
				case CWS_P_STATUS:
					cws_p_status = Integer.parseInt(object.getValue()[0]);
					break;
				case FD_MODE:
					fd_mode = Integer.parseInt(object.getValue()[0]);
					break;
				case ILS_POINTER_DISABLE:
					ils_pointer_disable = Float.parseFloat(object.getValue()[0]);
					break;
				}

				for (int i = 0; i < 2; i++) {
					if (object.getName().equals(CMD_STATUS[i])) {
						cmd_status[i] = Integer.parseInt(object.getValue()[0]);
					}
					if (object.getName().equals(MCP_SPEED_KTS2[i])) {
						mcp_speed_kts2[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(FD_STATUS[i])) {
						fd_status[i] = Integer.parseInt(object.getValue()[0]);
					}
					if (object.getName().equals(FD_PITCH_SHOW[i])) {
						fd_pitch_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if (object.getName().equals(FD_ROLL_SHOW[i])) {
						fd_roll_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if (object.getName().equals(FD_REC_MODE[i])) {
						fd_rec_mode[i] = Integer.parseInt(object.getValue()[0]);
					}
					if (object.getName().equals(MCP_COURSE[i])) {
						mcp_course[i] = Integer.parseInt(object.getValue()[0]);
					}
					if (object.getName().equals(PFD_MODE[i])) {
						pfd_mode[i] = Integer.parseInt(object.getValue()[0]);
					}
				}

			}

		};
	}
	public void subscribeDrefs() {


	}
	@Override
	public void includeDrefs() {
		

		iface.includeDataRef(MCP_VVI);
		iface.includeDataRef(MCP_HDG, 0.1f);
		iface.includeDataRef(MCP_SPEED_KTS_MACH, 0.01f);
		iface.includeDataRef(MCP_SPEED_IS_MACH);
		iface.includeDataRef(MCP_ALT);
		iface.includeDataRef(ALT_DISAGREE);
		iface.includeDataRef(IAS_DISAGREE);
		iface.includeDataRef(PFD_SPD_MODE);
		iface.includeDataRef(REC_THR_MODES);
		iface.includeDataRef(REC_THR2_MODES);
		iface.includeDataRef(PFD_HDG_MODE);
		iface.includeDataRef(REC_HDG_MODE);
		iface.includeDataRef(PFD_HDG_MODE_ARM);
		iface.includeDataRef(PFD_ALT_MODE);
		iface.includeDataRef(REC_ALT_MODES);
		iface.includeDataRef(PFD_ALT_MODE_ARM);
		iface.includeDataRef(AP_FD_ROLL_DEG, 0.01f);
		iface.includeDataRef(AP_FD_PITCH_DEG, 0.01f);
		iface.includeDataRef(SINGLE_CH);
		iface.includeDataRef(SINGLE_CH_G);
		iface.includeDataRef(SINGLE_CH_REC_MODE);
		iface.includeDataRef(FLARE_STATUS);
		iface.includeDataRef(VS_MODE);
		iface.includeDataRef(NPS_DEVIATION);
		iface.includeDataRef(FAC_HORIZONT);
		iface.includeDataRef(CWS_R_STATUS);
		iface.includeDataRef(CWS_P_STATUS);
		iface.includeDataRef(FD_MODE);
		iface.includeDataRef(ILS_POINTER_DISABLE);
		//
		iface.observeDataRef(MCP_VVI, autopilot);
		iface.observeDataRef(MCP_HDG, autopilot);
		iface.observeDataRef(MCP_SPEED_KTS_MACH, autopilot);
		iface.observeDataRef(MCP_SPEED_IS_MACH, autopilot);
		iface.observeDataRef(MCP_ALT, autopilot);
		iface.observeDataRef(ALT_DISAGREE, autopilot);
		iface.observeDataRef(IAS_DISAGREE, autopilot);
		iface.observeDataRef(PFD_SPD_MODE, autopilot);
		iface.observeDataRef(REC_THR_MODES, autopilot);
		iface.observeDataRef(REC_THR2_MODES, autopilot);
		iface.observeDataRef(PFD_HDG_MODE, autopilot);
		iface.observeDataRef(REC_HDG_MODE, autopilot);
		iface.observeDataRef(PFD_HDG_MODE_ARM, autopilot);
		iface.observeDataRef(PFD_ALT_MODE, autopilot);
		iface.observeDataRef(REC_ALT_MODES, autopilot);
		iface.observeDataRef(PFD_ALT_MODE_ARM, autopilot);
		iface.observeDataRef(AP_FD_ROLL_DEG, autopilot);
		iface.observeDataRef(AP_FD_PITCH_DEG, autopilot);
		iface.observeDataRef(SINGLE_CH, autopilot);
		iface.observeDataRef(SINGLE_CH_G, autopilot);
		iface.observeDataRef(SINGLE_CH_REC_MODE, autopilot);
		iface.observeDataRef(FLARE_STATUS, autopilot);
		iface.observeDataRef(VS_MODE, autopilot);
		iface.observeDataRef(NPS_DEVIATION, autopilot);
		iface.observeDataRef(FAC_HORIZONT, autopilot);
		iface.observeDataRef(CWS_R_STATUS, autopilot);
		iface.observeDataRef(CWS_P_STATUS, autopilot);
		iface.observeDataRef(FD_MODE, autopilot);
		iface.observeDataRef(ILS_POINTER_DISABLE, autopilot);
		
		for (int i = 0; i < 2; i++) {
			iface.includeDataRef(CMD_STATUS[i]);
			iface.includeDataRef(MCP_SPEED_KTS2[i], 0.001f);
			iface.includeDataRef(FD_STATUS[i]);
			iface.includeDataRef(FD_PITCH_SHOW[i]);
			iface.includeDataRef(FD_ROLL_SHOW[i]);
			iface.includeDataRef(FD_REC_MODE[i]);
			iface.includeDataRef(MCP_COURSE[i]);
			iface.includeDataRef(PFD_MODE[i]);
			iface.observeDataRef(CMD_STATUS[i], autopilot);
			iface.observeDataRef(MCP_SPEED_KTS2[i], autopilot);
			iface.observeDataRef(FD_STATUS[i], autopilot);
			iface.observeDataRef(FD_PITCH_SHOW[i], autopilot);
			iface.observeDataRef(FD_ROLL_SHOW[i], autopilot);
			iface.observeDataRef(FD_REC_MODE[i], autopilot);
			iface.observeDataRef(MCP_COURSE[i], autopilot);
			iface.observeDataRef(PFD_MODE[i], autopilot);
		}	
		
	}
	@Override
	public void excludeDrefs() {
		

		iface.excludeDataRef(MCP_VVI);
		iface.excludeDataRef(MCP_HDG);
		iface.excludeDataRef(MCP_SPEED_KTS_MACH);
		iface.excludeDataRef(MCP_SPEED_IS_MACH);
		iface.excludeDataRef(MCP_ALT);
		iface.excludeDataRef(ALT_DISAGREE);
		iface.excludeDataRef(IAS_DISAGREE);
		iface.excludeDataRef(PFD_SPD_MODE);
		iface.excludeDataRef(REC_THR_MODES);
		iface.excludeDataRef(REC_THR2_MODES);
		iface.excludeDataRef(PFD_HDG_MODE);
		iface.excludeDataRef(REC_HDG_MODE);
		iface.excludeDataRef(PFD_HDG_MODE_ARM);
		iface.excludeDataRef(PFD_ALT_MODE);
		iface.excludeDataRef(REC_ALT_MODES);
		iface.excludeDataRef(PFD_ALT_MODE_ARM);
		iface.excludeDataRef(AP_FD_ROLL_DEG);
		iface.excludeDataRef(AP_FD_PITCH_DEG);
		iface.excludeDataRef(SINGLE_CH);
		iface.excludeDataRef(SINGLE_CH_G);
		iface.excludeDataRef(SINGLE_CH_REC_MODE);
		iface.excludeDataRef(FLARE_STATUS);
		iface.excludeDataRef(VS_MODE);
		iface.excludeDataRef(NPS_DEVIATION);
		iface.excludeDataRef(FAC_HORIZONT);
		iface.excludeDataRef(CWS_R_STATUS);
		iface.excludeDataRef(CWS_P_STATUS);
		iface.excludeDataRef(FD_MODE);
		iface.excludeDataRef(ILS_POINTER_DISABLE);
		//
		iface.unObserveDataRef(MCP_VVI, autopilot);
		iface.unObserveDataRef(MCP_HDG, autopilot);
		iface.unObserveDataRef(MCP_SPEED_KTS_MACH, autopilot);
		iface.unObserveDataRef(MCP_SPEED_IS_MACH, autopilot);
		iface.unObserveDataRef(MCP_ALT, autopilot);
		iface.unObserveDataRef(ALT_DISAGREE, autopilot);
		iface.unObserveDataRef(IAS_DISAGREE, autopilot);
		iface.unObserveDataRef(PFD_SPD_MODE, autopilot);
		iface.unObserveDataRef(REC_THR_MODES, autopilot);
		iface.unObserveDataRef(REC_THR2_MODES, autopilot);
		iface.unObserveDataRef(PFD_HDG_MODE, autopilot);
		iface.unObserveDataRef(REC_HDG_MODE, autopilot);
		iface.unObserveDataRef(PFD_HDG_MODE_ARM, autopilot);
		iface.unObserveDataRef(PFD_ALT_MODE, autopilot);
		iface.unObserveDataRef(REC_ALT_MODES, autopilot);
		iface.unObserveDataRef(PFD_ALT_MODE_ARM, autopilot);
		iface.unObserveDataRef(AP_FD_ROLL_DEG, autopilot);
		iface.unObserveDataRef(AP_FD_PITCH_DEG, autopilot);
		iface.unObserveDataRef(SINGLE_CH, autopilot);
		iface.unObserveDataRef(SINGLE_CH_G, autopilot);
		iface.unObserveDataRef(SINGLE_CH_REC_MODE, autopilot);
		iface.unObserveDataRef(FLARE_STATUS, autopilot);
		iface.unObserveDataRef(VS_MODE, autopilot);
		iface.unObserveDataRef(NPS_DEVIATION, autopilot);
		iface.unObserveDataRef(FAC_HORIZONT, autopilot);
		iface.unObserveDataRef(CWS_R_STATUS, autopilot);
		iface.unObserveDataRef(CWS_P_STATUS, autopilot);
		iface.unObserveDataRef(FD_MODE, autopilot);
		iface.unObserveDataRef(ILS_POINTER_DISABLE, autopilot);
		
		for (int i = 0; i < 2; i++) {
			iface.excludeDataRef(CMD_STATUS[i]);
			iface.excludeDataRef(MCP_SPEED_KTS2[i]);
			iface.excludeDataRef(FD_STATUS[i]);
			iface.excludeDataRef(FD_PITCH_SHOW[i]);
			iface.excludeDataRef(FD_ROLL_SHOW[i]);
			iface.excludeDataRef(FD_REC_MODE[i]);
			iface.excludeDataRef(MCP_COURSE[i]);
			iface.excludeDataRef(PFD_MODE[i]);
			iface.unObserveDataRef(CMD_STATUS[i], autopilot);
			iface.unObserveDataRef(MCP_SPEED_KTS2[i], autopilot);
			iface.unObserveDataRef(FD_STATUS[i], autopilot);
			iface.unObserveDataRef(FD_PITCH_SHOW[i], autopilot);
			iface.unObserveDataRef(FD_ROLL_SHOW[i], autopilot);
			iface.unObserveDataRef(FD_REC_MODE[i], autopilot);
			iface.unObserveDataRef(MCP_COURSE[i], autopilot);
			iface.unObserveDataRef(PFD_MODE[i], autopilot);
		}	
		
	}
}
