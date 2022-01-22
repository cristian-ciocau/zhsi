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

public class Radios extends BaseDataClass {
	
	Observer<DataRef> radios;

	private final String[] NAV_FREQ = {"sim/cockpit2/radios/actuators/nav1_frequency_hz", "sim/cockpit2/radios/actuators/nav2_frequency_hz"};
	private final String[] NAV_ID = {"sim/cockpit2/radios/indicators/nav1_nav_id:string", "sim/cockpit2/radios/indicators/nav2_nav_id:string"};
	private final String[] NAV_REL_BEARING = {"laminar/B738/pfd/vor1_arrow", "laminar/B738/pfd/vor2_arrow"};
	private final String[] NAV_DME_DIST_NM = {"sim/cockpit2/radios/indicators/nav1_dme_distance_nm", "sim/cockpit2/radios/indicators/nav2_dme_distance_nm"};
	private final String[] NAV_DME_TIME_SEC = {"sim/cockpit/radios/nav1_dme_time_secs","sim/cockpit/radios/nav2_dme_time_secs"};
	private final String[] NAV_FROMTO = {"sim/cockpit/radios/nav1_fromto", "sim/cockpit/radios/nav2_fromto2"};
	private final String[] NAV_HDEF_DOT = {"sim/cockpit2/radios/indicators/nav1_hdef_dots_pilot", "sim/cockpit2/radios/indicators/nav2_hdef_dots_copilot"};
	private final String[] NAV_VDEF_DOT = {"sim/cockpit2/radios/indicators/nav1_vdef_dots_pilot", "sim/cockpit2/radios/indicators/nav2_vdef_dots_copilot"};
	private final String[] NAV_CDI = {"sim/cockpit/radios/nav1_CDI", "sim/cockpit/radios/nav2_CDI"};
	private final String[] ADF_FREQ = {"sim/cockpit2/radios/actuators/adf1_frequency_hz", "sim/cockpit2/radios/actuators/adf2_frequency_hz"};
	private final String[] ADF_ID = {"sim/cockpit2/radios/indicators/adf1_nav_id:string", "sim/cockpit2/radios/indicators/adf2_nav_id:string"};
	private final String[] ADF_REL_BEARING = {"laminar/B738/pfd/adf1_arrow", "laminar/B738/pfd/adf2_arrow"};
	private final String[] ADF_DME_DIST_NM = {"sim/cockpit2/radios/indicators/adf1_dme_distance_nm", "sim/cockpit2/radios/indicators/adf2_dme_distance_nm"};
	private final String[] NAV_COURSE = {"sim/cockpit/radios/nav1_course_degm", "sim/cockpit/radios/nav2_course_degm"};
	
	private final String NAV_TYPE = "sim/cockpit/radios/nav_type";	
	private final String RMI_ARROW1 = "laminar/B738/radio/arrow1";
	private final String RMI_ARROW2 = "laminar/B738/radio/arrow2";
	private final String RMI_ARROW1_NO_AVAIL = "laminar/B738/radio/arrow1_no_available";
	private final String RMI_ARROW2_NO_AVAIL = "laminar/B738/radio/arrow2_no_available";	
	private final String INNER_MARKER_LIT = "sim/cockpit2/radios/indicators/inner_marker_lit";
	private final String MIDDLE_MARKER_LIT = "sim/cockpit2/radios/indicators/middle_marker_lit";
	private final String OUTER_MARKER_LIT = "sim/cockpit2/radios/indicators/outer_marker_lit";	
	private final String NAV1_FLAG_GLIDESLOPE = "sim/cockpit2/radios/indicators/nav1_flag_glideslope";
	private final String NAV2_FLAG_GLIDESLOPE = "sim/cockpit2/radios/indicators/nav2_flag_glideslope";
	private final String NAV1_DISPLAY_VERTICAL = "sim/cockpit2/radios/indicators/nav1_display_vertical";
	private final String NAV2_DISPLAY_VERTICAL = "sim/cockpit2/radios/indicators/nav2_display_vertical";
	private final String NAV1_DISPLAY_HORIZONTAL = "sim/cockpit2/radios/indicators/nav1_display_horizontal";
	private final String NAV2_DISPLAY_HORIZONTAL = "sim/cockpit2/radios/indicators/nav2_display_horizontal";
	private final String GLS1_VERT_DEV = "laminar/B738/nav/gls1_vert_dev";
	private final String GLS2_VERT_DEV = "laminar/B738/nav/gls2_vert_dev";
	private final String GLS1_HORZ_DEV = "laminar/B738/nav/gls1_horz_dev";
	private final String GLS2_HORZ_DEV = "laminar/B738/nav/gls2_horz_dev";
	private final String GLS1_TYPE = "laminar/B738/nav/gls1_type";
	private final String GLS2_TYPE = "laminar/B738/nav/gls2_type";
	private final String GLS1_ACTIVE = "laminar/B738/nav/gls1_active";
	private final String GLS2_ACTIVE = "laminar/B738/nav/gls2_active";
	private final String GLS1_ID = "laminar/B738/nav/gls1_id:string";
	private final String GLS2_ID = "laminar/B738/nav/gls2_id:string";
	private final String GLS1_DME = "laminar/B738/nav/gls1_dme";
	private final String GLS2_DME = "laminar/B738/nav/gls2_dme";
	private final String NAV1_NO_ID = "laminar/B738/nav/nav1_no_id";
	private final String NAV2_NO_ID = "laminar/B738/nav/nav2_no_id";

	public int[] nav_freq = new int[2];
	public String[] nav_id = new String[2];
	public float[] nav_rel_bearing = new float[2];
	public float[] nav_dme_dist_nm = new float[2];
	public float[] nav_dme_time_sec = new float[2];
	public int[] nav_fromto = new int[2];
	public float[] nav_hdef_dot = new float[2];
	public float[] nav_vdef_dot = new float[2];
	public int[] nav_cdi = new int[2];
	public int[] adf_freq = new int[2];
	public String[] adf_id = new String[2];
	public float[] adf_rel_bearing = new float[2];
	public float[] adf_dme_dist_nm = new float[2];
	public float[] nav_course = new float[2];
	
	public int[] nav_type = new int[6];
	public float rmi_arrow1 = 0f;
	public float rmi_arrow2 = 0f;
	public float rmi_arrow1_no_avail = 0f;
	public float rmi_arrow2_no_avail = 0f;
	public int inner_marker_lit = 0;
	public int middle_marker_lit = 0; 
	public int outer_marker_lit = 0; 
	
	public int nav1_flag_glideslope = 0; 
	public int nav2_flag_glideslope = 0;
	public int nav1_display_vertical = 0; 
	public int nav2_display_vertical = 0; 
	public int nav1_display_horizontal = 0; 
	public int nav2_display_horizontal = 0; 
	public float gls1_vert_dev = 0;
	public float gls2_vert_dev = 0;
	public float gls1_horz_dev = 0;
	public float gls2_horz_dev = 0;
	public int gls1_type = 0;
	public int gls2_type = 0;
	public int gls1_active = 0;
	public int gls2_active = 0;
	public String gls1_id = "";
	public String gls2_id = "";
	public float gls1_dme = 0;
	public float gls2_dme = 0;
	public int nav1_no_id = 0;
	public int nav2_no_id = 0;	

	public Radios(ExtPlaneInterface iface) {

		super(iface);	

//		nav_freq[0] = 0;
				
		radios = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				if (object.getName().equals(RMI_ARROW1)) {
					rmi_arrow1 = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(RMI_ARROW2)) {
					rmi_arrow2 = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(RMI_ARROW1_NO_AVAIL)) {
					rmi_arrow1_no_avail = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(RMI_ARROW2_NO_AVAIL)) {
					rmi_arrow2_no_avail = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(INNER_MARKER_LIT)) {
					inner_marker_lit = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(MIDDLE_MARKER_LIT)) {
					middle_marker_lit = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(OUTER_MARKER_LIT)) {
					outer_marker_lit = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(NAV1_FLAG_GLIDESLOPE)) {
					nav1_flag_glideslope = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(NAV2_FLAG_GLIDESLOPE)) {
					nav2_flag_glideslope = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(NAV1_DISPLAY_VERTICAL)) {
					nav1_display_vertical = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(NAV2_DISPLAY_VERTICAL)) {
					nav2_display_vertical = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(NAV1_DISPLAY_HORIZONTAL)) {
					nav1_display_horizontal = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(NAV2_DISPLAY_HORIZONTAL)) {
					nav2_display_horizontal = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(GLS1_VERT_DEV)) {
					gls1_vert_dev = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(GLS2_VERT_DEV)) {
					gls2_vert_dev = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(GLS1_HORZ_DEV)) {
					gls1_horz_dev = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(GLS2_HORZ_DEV)) {
					gls2_horz_dev = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(GLS1_TYPE)) {
					gls1_type = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(GLS2_TYPE)) {
					gls2_type = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(GLS1_ACTIVE)) {
					gls1_active = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(GLS2_ACTIVE)) {
					gls2_active = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(GLS1_ID)) {
					gls1_id = object.getValue()[0].replaceAll("\"", "");	
				}
				if (object.getName().equals(GLS2_ID)) {
					gls2_id = object.getValue()[0].replaceAll("\"", "");	
				}
				if (object.getName().equals(GLS1_DME)) {
					gls1_dme = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(GLS2_DME)) {
					gls2_dme = Float.parseFloat(object.getValue()[0]);
				}
				if (object.getName().equals(NAV1_NO_ID)) {
					nav1_no_id = Integer.parseInt(object.getValue()[0]);
				}
				if (object.getName().equals(NAV2_NO_ID)) {
					nav2_no_id = Integer.parseInt(object.getValue()[0]);
				}
				
				for (int i = 0; i < 2; i++) {
					if(object.getName().equals(NAV_FREQ[i])) {
						nav_freq[i] = Integer.parseInt(object.getValue()[0]);					
					}
					if(object.getName().equals(NAV_ID[i])) {
						nav_id[i] = object.getValue()[0].replaceAll("\"", "");			
					}
					if(object.getName().equals(NAV_REL_BEARING[i])) {
						nav_rel_bearing[i] = Float.parseFloat(object.getValue()[0]);					
					}
					if(object.getName().equals(NAV_DME_DIST_NM[i])) {
						nav_dme_dist_nm[i] = Float.parseFloat(object.getValue()[0]);					
					}
					if(object.getName().equals(NAV_DME_TIME_SEC[i])) {
						nav_dme_time_sec[i] = Float.parseFloat(object.getValue()[0]);					
					}
					if(object.getName().equals(NAV_FROMTO[i])) {
						nav_fromto[i] = Integer.parseInt(object.getValue()[0]);					
					}
					if(object.getName().equals(NAV_HDEF_DOT[i])) {
						nav_hdef_dot[i] = Float.parseFloat(object.getValue()[0]);					
					}
					if(object.getName().equals(NAV_VDEF_DOT[i])) {
						nav_vdef_dot[i] = Float.parseFloat(object.getValue()[0]);					
					}
					if(object.getName().equals(NAV_CDI[i])) {
						nav_cdi[i] = Integer.parseInt(object.getValue()[0]);					
					}
					if(object.getName().equals(ADF_FREQ[i])) {
						adf_freq[i] = Integer.parseInt(object.getValue()[0]);					
					}
					if(object.getName().equals(ADF_ID[i])) {
						adf_id[i] = object.getValue()[0].replaceAll("\"", "");				
					}
					if(object.getName().equals(ADF_REL_BEARING[i])) {
						adf_rel_bearing[i] = Float.parseFloat(object.getValue()[0]);					
					}
					if(object.getName().equals(ADF_DME_DIST_NM[i])) {
						adf_dme_dist_nm[i] = Float.parseFloat(object.getValue()[0]);					
					}
					if(object.getName().equals(NAV_COURSE[i])) {
						nav_course[i] = Float.parseFloat(object.getValue()[0]);					
					}
				}
				if (object.getName().equals(NAV_TYPE)) {
					for(int i = 0; i < 6; i++) {
						nav_type[i] = Integer.parseInt(object.getValue()[i]);
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
		
		iface.includeDataRef(NAV_TYPE);
		iface.observeDataRef(NAV_TYPE, radios);
		iface.includeDataRef(RMI_ARROW1, 0.01f);
		iface.observeDataRef(RMI_ARROW1, radios);
		iface.includeDataRef(RMI_ARROW2, 0.01f);
		iface.observeDataRef(RMI_ARROW2, radios);
		iface.includeDataRef(RMI_ARROW1_NO_AVAIL, 0.01f);
		iface.observeDataRef(RMI_ARROW1_NO_AVAIL, radios);
		iface.includeDataRef(RMI_ARROW2_NO_AVAIL, 0.01f);
		iface.observeDataRef(RMI_ARROW2_NO_AVAIL, radios);
		iface.includeDataRef(INNER_MARKER_LIT);
		iface.observeDataRef(INNER_MARKER_LIT, radios);
		iface.includeDataRef(MIDDLE_MARKER_LIT);
		iface.observeDataRef(MIDDLE_MARKER_LIT, radios);
		iface.includeDataRef(OUTER_MARKER_LIT);
		iface.observeDataRef(OUTER_MARKER_LIT, radios);
		iface.includeDataRef(NAV1_FLAG_GLIDESLOPE);
		iface.observeDataRef(NAV1_FLAG_GLIDESLOPE, radios);
		iface.includeDataRef(NAV2_FLAG_GLIDESLOPE);
		iface.observeDataRef(NAV2_FLAG_GLIDESLOPE, radios);
		iface.includeDataRef(NAV1_DISPLAY_VERTICAL);
		iface.observeDataRef(NAV1_DISPLAY_VERTICAL, radios);
		iface.includeDataRef(NAV2_DISPLAY_VERTICAL);
		iface.observeDataRef(NAV2_DISPLAY_VERTICAL, radios);
		iface.includeDataRef(NAV1_DISPLAY_HORIZONTAL);
		iface.observeDataRef(NAV1_DISPLAY_HORIZONTAL, radios);
		iface.includeDataRef(NAV2_DISPLAY_HORIZONTAL);
		iface.observeDataRef(NAV2_DISPLAY_HORIZONTAL, radios);
		iface.includeDataRef(GLS1_VERT_DEV);
		iface.observeDataRef(GLS1_VERT_DEV, radios);
		iface.includeDataRef(GLS2_VERT_DEV);
		iface.observeDataRef(GLS2_VERT_DEV, radios);
		iface.includeDataRef(GLS1_HORZ_DEV);
		iface.observeDataRef(GLS1_HORZ_DEV, radios);
		iface.includeDataRef(GLS2_HORZ_DEV);
		iface.observeDataRef(GLS2_HORZ_DEV, radios);
		iface.includeDataRef(GLS1_TYPE);
		iface.observeDataRef(GLS1_TYPE, radios);
		iface.includeDataRef(GLS2_TYPE);
		iface.observeDataRef(GLS2_TYPE, radios);
		iface.includeDataRef(GLS1_ACTIVE);
		iface.observeDataRef(GLS1_ACTIVE, radios);
		iface.includeDataRef(GLS2_ACTIVE);
		iface.observeDataRef(GLS2_ACTIVE, radios);
		iface.includeDataRef(GLS1_ID);
		iface.observeDataRef(GLS1_ID, radios);
		iface.includeDataRef(GLS2_ID);
		iface.observeDataRef(GLS2_ID, radios);
		iface.includeDataRef(GLS1_DME, 0.01f);
		iface.observeDataRef(GLS1_DME, radios);
		iface.includeDataRef(GLS2_DME, 0.01f);
		iface.observeDataRef(GLS2_DME, radios);
		iface.includeDataRef(NAV1_NO_ID);
		iface.observeDataRef(NAV1_NO_ID, radios);
		iface.includeDataRef(NAV2_NO_ID);
		iface.observeDataRef(NAV2_NO_ID, radios);
		
		for (int i = 0; i < 2; i++) {
			iface.includeDataRef(NAV_FREQ[i]);
			iface.includeDataRef(NAV_ID[i]);
			iface.includeDataRef(NAV_REL_BEARING[i], 0.01f);
			iface.includeDataRef(NAV_DME_DIST_NM[i], 0.01f);
			iface.includeDataRef(NAV_DME_TIME_SEC[i]);
			iface.includeDataRef(NAV_FROMTO[i]);
			iface.includeDataRef(NAV_HDEF_DOT[i]);
			iface.includeDataRef(NAV_VDEF_DOT[i]);
			iface.includeDataRef(NAV_CDI[i]);
			iface.includeDataRef(ADF_FREQ[i]);
			iface.includeDataRef(ADF_ID[i]);
			iface.includeDataRef(ADF_REL_BEARING[i], 0.01f);
			iface.includeDataRef(ADF_DME_DIST_NM[i], 0.01f);
			iface.includeDataRef(NAV_COURSE[i], 0.1f);
			
			iface.observeDataRef(NAV_FREQ[i], radios);
			iface.observeDataRef(NAV_ID[i], radios);
			iface.observeDataRef(NAV_REL_BEARING[i], radios);
			iface.observeDataRef(NAV_DME_DIST_NM[i], radios);
			iface.observeDataRef(NAV_DME_TIME_SEC[i], radios);
			iface.observeDataRef(NAV_FROMTO[i], radios);
			iface.observeDataRef(NAV_HDEF_DOT[i], radios);
			iface.observeDataRef(NAV_VDEF_DOT[i], radios);
			iface.observeDataRef(NAV_CDI[i], radios);
			iface.observeDataRef(ADF_FREQ[i], radios);
			iface.observeDataRef(ADF_ID[i], radios);
			iface.observeDataRef(ADF_REL_BEARING[i], radios);
			iface.observeDataRef(ADF_DME_DIST_NM[i], radios);
			iface.observeDataRef(NAV_COURSE[i], radios);
		}
		
	}

	@Override
	public void excludeDrefs() {
		
		iface.excludeDataRef(NAV_TYPE);
		iface.unObserveDataRef(NAV_TYPE, radios);
		iface.excludeDataRef(RMI_ARROW1);
		iface.unObserveDataRef(RMI_ARROW1, radios);
		iface.excludeDataRef(RMI_ARROW2);
		iface.unObserveDataRef(RMI_ARROW2, radios);
		iface.excludeDataRef(RMI_ARROW1_NO_AVAIL);
		iface.unObserveDataRef(RMI_ARROW1_NO_AVAIL, radios);
		iface.excludeDataRef(RMI_ARROW2_NO_AVAIL);
		iface.unObserveDataRef(RMI_ARROW2_NO_AVAIL, radios);
		iface.excludeDataRef(INNER_MARKER_LIT);
		iface.unObserveDataRef(INNER_MARKER_LIT, radios);
		iface.excludeDataRef(MIDDLE_MARKER_LIT);
		iface.unObserveDataRef(MIDDLE_MARKER_LIT, radios);
		iface.excludeDataRef(OUTER_MARKER_LIT);
		iface.unObserveDataRef(OUTER_MARKER_LIT, radios);
		iface.excludeDataRef(NAV1_FLAG_GLIDESLOPE);
		iface.unObserveDataRef(NAV1_FLAG_GLIDESLOPE, radios);
		iface.excludeDataRef(NAV2_FLAG_GLIDESLOPE);
		iface.unObserveDataRef(NAV2_FLAG_GLIDESLOPE, radios);
		iface.excludeDataRef(NAV1_DISPLAY_VERTICAL);
		iface.unObserveDataRef(NAV1_DISPLAY_VERTICAL, radios);
		iface.excludeDataRef(NAV2_DISPLAY_VERTICAL);
		iface.unObserveDataRef(NAV2_DISPLAY_VERTICAL, radios);
		iface.excludeDataRef(NAV1_DISPLAY_HORIZONTAL);
		iface.unObserveDataRef(NAV1_DISPLAY_HORIZONTAL, radios);
		iface.excludeDataRef(NAV2_DISPLAY_HORIZONTAL);
		iface.unObserveDataRef(NAV2_DISPLAY_HORIZONTAL, radios);
		iface.excludeDataRef(GLS1_VERT_DEV);
		iface.unObserveDataRef(GLS1_VERT_DEV, radios);
		iface.excludeDataRef(GLS2_VERT_DEV);
		iface.unObserveDataRef(GLS2_VERT_DEV, radios);
		iface.excludeDataRef(GLS1_HORZ_DEV);
		iface.unObserveDataRef(GLS1_HORZ_DEV, radios);
		iface.excludeDataRef(GLS2_HORZ_DEV);
		iface.unObserveDataRef(GLS2_HORZ_DEV, radios);
		iface.excludeDataRef(GLS1_TYPE);
		iface.unObserveDataRef(GLS1_TYPE, radios);
		iface.excludeDataRef(GLS2_TYPE);
		iface.unObserveDataRef(GLS2_TYPE, radios);
		iface.excludeDataRef(GLS1_ACTIVE);
		iface.unObserveDataRef(GLS1_ACTIVE, radios);
		iface.excludeDataRef(GLS2_ACTIVE);
		iface.unObserveDataRef(GLS2_ACTIVE, radios);
		iface.excludeDataRef(GLS1_ID);
		iface.unObserveDataRef(GLS1_ID, radios);
		iface.excludeDataRef(GLS2_ID);
		iface.unObserveDataRef(GLS2_ID, radios);
		iface.excludeDataRef(GLS1_DME);
		iface.unObserveDataRef(GLS1_DME, radios);
		iface.excludeDataRef(GLS2_DME);
		iface.unObserveDataRef(GLS2_DME, radios);
		iface.excludeDataRef(NAV1_NO_ID);
		iface.unObserveDataRef(NAV1_NO_ID, radios);
		iface.excludeDataRef(NAV2_NO_ID);
		iface.unObserveDataRef(NAV2_NO_ID, radios);
		
		for (int i = 0; i < 2; i++) {
			iface.excludeDataRef(NAV_FREQ[i]);
			iface.excludeDataRef(NAV_ID[i]);
			iface.excludeDataRef(NAV_REL_BEARING[i]);
			iface.excludeDataRef(NAV_DME_DIST_NM[i]);
			iface.excludeDataRef(NAV_DME_TIME_SEC[i]);
			iface.excludeDataRef(NAV_FROMTO[i]);
			iface.excludeDataRef(NAV_HDEF_DOT[i]);
			iface.excludeDataRef(NAV_VDEF_DOT[i]);
			iface.excludeDataRef(NAV_CDI[i]);
			iface.excludeDataRef(ADF_FREQ[i]);
			iface.excludeDataRef(ADF_ID[i]);
			iface.excludeDataRef(ADF_REL_BEARING[i]);
			iface.excludeDataRef(ADF_DME_DIST_NM[i]);
			iface.excludeDataRef(NAV_COURSE[i]);
			
			iface.unObserveDataRef(NAV_FREQ[i], radios);
			iface.unObserveDataRef(NAV_ID[i], radios);
			iface.unObserveDataRef(NAV_REL_BEARING[i], radios);
			iface.unObserveDataRef(NAV_DME_DIST_NM[i], radios);
			iface.unObserveDataRef(NAV_DME_TIME_SEC[i], radios);
			iface.unObserveDataRef(NAV_FROMTO[i], radios);
			iface.unObserveDataRef(NAV_HDEF_DOT[i], radios);
			iface.unObserveDataRef(NAV_VDEF_DOT[i], radios);
			iface.unObserveDataRef(NAV_CDI[i], radios);
			iface.unObserveDataRef(ADF_FREQ[i], radios);
			iface.unObserveDataRef(ADF_ID[i], radios);
			iface.unObserveDataRef(ADF_REL_BEARING[i], radios);
			iface.unObserveDataRef(ADF_DME_DIST_NM[i], radios);
			iface.unObserveDataRef(NAV_COURSE[i], radios);
		}
		
	}

}