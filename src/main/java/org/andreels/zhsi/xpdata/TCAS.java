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

public class TCAS extends BaseDataClass {
	
	Observer<DataRef> tcas;
		
	private final String[] TCAS_X = {"laminar/B738/TCAS/x", "laminar/B738/TCAS/x_fo"};
	private final String[] TCAS_Y = {"laminar/B738/TCAS/y", "laminar/B738/TCAS/y_fo"};
	private final String[] TCAS_TYPE_SHOW = {"laminar/B738/TCAS/type_show", "laminar/B738/TCAS/type_show_fo"}; // 1 = red box, 2 = amber circle, 3 = solid white diamond, 4 = white diamond
	private final String[] TCAS_SHOW = {"laminar/B738/EFIS/tcas_show", "laminar/B738/EFIS/tcas_show_fo"};
	private final String[] TCAS_TEST_SHOW = {"laminar/B738/EFIS/tcas_test_show", "laminar/B738/EFIS/tcas_test_show_fo"};
	private final String[] TCAS_FAIL_SHOW = {"laminar/B738/EFIS/tcas_fail_show", "laminar/B738/EFIS/tcas_fail_show_fo"};
	private final String[] TCAS_OFF_SHOW = {"laminar/B738/EFIS/tcas_off_show", "laminar/B738/EFIS/tcas_off_show_fo"};
	private final String[] TCAS_AI_SHOW = {"laminar/B738/EFIS/tcas_ai_show", "laminar/B738/EFIS/tcas_ai_show_fo"};
	private final String[] TCAS_ALT_DN_UP_SHOW = {"laminar/B738/TCAS/alt_dn_up_show", "laminar/B738/TCAS/alt_dn_up_show_fo"};
	private final String[] TCAS_ARROW_DN_UP_SHOW = {"laminar/B738/TCAS/arrow_dn_up_show", "laminar/B738/TCAS/arrow_dn_up_show_fo"};
	private final String[] TCAS_ALT = {"laminar/B738/TCAS/alt:string","laminar/B738/TCAS/alt2:string","laminar/B738/TCAS/alt3:string","laminar/B738/TCAS/alt4:string","laminar/B738/TCAS/alt5:string","laminar/B738/TCAS/alt6:string","laminar/B738/TCAS/alt7:string","laminar/B738/TCAS/alt8:string","laminar/B738/TCAS/alt9:string","laminar/B738/TCAS/alt10:string","laminar/B738/TCAS/alt11:string","laminar/B738/TCAS/alt12:string","laminar/B738/TCAS/alt13:string","laminar/B738/TCAS/alt14:string","laminar/B738/TCAS/alt15:string","laminar/B738/TCAS/alt16:string","laminar/B738/TCAS/alt17:string","laminar/B738/TCAS/alt18:string","laminar/B738/TCAS/alt19:string"};
	private final String[] TCAS_ALT_FO = {"laminar/B738/TCAS/alt_fo:string","laminar/B738/TCAS/alt_fo2:string","laminar/B738/TCAS/alt_fo3:string","laminar/B738/TCAS/alt_fo4:string","laminar/B738/TCAS/alt_fo5:string","laminar/B738/TCAS/alt_fo6:string","laminar/B738/TCAS/alt_fo7:string","laminar/B738/TCAS/alt_fo8:string","laminar/B738/TCAS/alt_fo9:string","laminar/B738/TCAS/alt_fo10:string","laminar/B738/TCAS/alt_fo11:string","laminar/B738/TCAS/alt_fo12:string","laminar/B738/TCAS/alt_fo13:string","laminar/B738/TCAS/alt_fo14:string","laminar/B738/TCAS/alt_fo15:string","laminar/B738/TCAS/alt_fo16:string","laminar/B738/TCAS/alt_fo17:string","laminar/B738/TCAS/alt_fo18:string","laminar/B738/TCAS/alt_fo19:string"};
	private final String[] TCAS_TRAFFIC_RA = {"laminar/B738/TCAS/traffic_ra", "laminar/B738/TCAS/traffic_ra_fo"};
	private final String[] TCAS_TRAFFIC_TA = {"laminar/B738/TCAS/traffic_ta", "laminar/B738/TCAS/traffic_ta_fo"};
	private final String TCAS_PFD_RA_DN = "laminar/B738/TCAS/tcas_pfd_ra_dn";
	private final String TCAS_PFD_RA_UP = "laminar/B738/TCAS/tcas_pfd_ra_up";
	private final String[] TCAS_PFD_RA_DN2 = {"laminar/B738/TCAS/tcas_pfd_ra_dn2", "laminar/B738/TCAS/tcas_pfd_ra_dn2_fo"};
	private final String[] TCAS_PFD_RA_UP2 = {"laminar/B738/TCAS/tcas_pfd_ra_up2", "laminar/B738/TCAS/tcas_pfd_ra_up2_fo"};
	
	public float[][] tcas_x = new float[2][19];
	public float[][] tcas_y = new float[2][19];
	public float[][] tcas_type_show = new float[2][19];
	public float[][] tcas_ai_show = new float[2][19];
	public float[][] tcas_alt_dn_up_show = new float[2][19];
	public float[][] tcas_arrow_dn_up_show = new float[2][19];
	public String[] tcas_alt = new String[19];
	public String[] tcas_alt_fo = new String[19];
	public float[] tcas_show = new float[2];
	public float[] tcas_test_show = new float[2];
	public float[] tcas_fail_show = new float[2];
	public float[] tcas_off_show = new float[2];
	public float[] tcas_traffic_ra = new float[2];
	public float[] tcas_traffic_ta = new float[2];
	public float tcas_pfd_ra_dn = 0f;
	public float tcas_pfd_ra_up = 0f;
	public float[] tcas_pfd_ra_dn2 = new float[2];
	public float[] tcas_pfd_ra_up2 = new float[2];
	
	public TCAS(ExtPlaneInterface iface) {	
		super(iface);
		
		tcas = new Observer<DataRef>() { // display TCAS info on ND

			@Override
			public void update(DataRef object) {
				
				switch(object.getName()) {
					case TCAS_PFD_RA_DN:
						tcas_pfd_ra_dn = Float.parseFloat(object.getValue()[0]);
						break;
					case TCAS_PFD_RA_UP:
						tcas_pfd_ra_up = Float.parseFloat(object.getValue()[0]);
						break;
				}
				
				for (int i = 0; i < 2; i++) {
					if (object.getName().equals(TCAS_X[i])) {
						for(int j = 0; j < 19; j++) {
							tcas_x[i][j] = Float.parseFloat(object.getValue()[j]);
						}
					}
					if (object.getName().equals(TCAS_Y[i])) {
						for(int j = 0; j < 19; j++) {
							tcas_y[i][j] = Float.parseFloat(object.getValue()[j]);
						}
					}
					if (object.getName().equals(TCAS_TYPE_SHOW[i])) {
						for(int j = 0; j < 19; j++) {
							tcas_type_show[i][j] = Float.parseFloat(object.getValue()[j]);
						}
					}
					if (object.getName().equals(TCAS_AI_SHOW[i])) {
						for(int j = 0; j < 19; j++) {
							tcas_ai_show[i][j] = Float.parseFloat(object.getValue()[j]);
						}
					}
					if (object.getName().equals(TCAS_SHOW[i])) {
						tcas_show[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(TCAS_TEST_SHOW[i])) {
						tcas_test_show[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(TCAS_FAIL_SHOW[i])) {
						tcas_fail_show[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(TCAS_OFF_SHOW[i])) {
						tcas_off_show[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(TCAS_TRAFFIC_RA[i])) {
						tcas_traffic_ra[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(TCAS_TRAFFIC_TA[i])) {
						tcas_traffic_ta[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(TCAS_PFD_RA_DN2[i])) {
						tcas_pfd_ra_dn2[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(TCAS_PFD_RA_UP2[i])) {
						tcas_pfd_ra_up2[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(TCAS_ALT_DN_UP_SHOW[i])) {
						for(int j = 0; j < 19; j++) {
							tcas_alt_dn_up_show[i][j] = Float.parseFloat(object.getValue()[j]);
						}
					}
					if (object.getName().equals(TCAS_ARROW_DN_UP_SHOW[i])) {
						for(int j = 0; j < 19; j++) {
							tcas_arrow_dn_up_show[i][j] = Float.parseFloat(object.getValue()[j]);
						}
					}	
				}
				
				for (int i = 0; i < 19; i++) {
					if(object.getName().equals(TCAS_ALT[i])) {
						tcas_alt[i] = object.getValue()[0].replaceAll("\"", "");
					}
					if(object.getName().equals(TCAS_ALT_FO[i])) {
						tcas_alt_fo[i] = object.getValue()[0].replaceAll("\"", "");
					}
				}
			}
		};
	}

	public void subscribeDrefs() {

	}

	@Override
	public void includeDrefs() {
		
		iface.includeDataRef(TCAS_PFD_RA_DN);
		iface.includeDataRef(TCAS_PFD_RA_UP);
		
		iface.observeDataRef(TCAS_PFD_RA_DN, tcas);
		iface.observeDataRef(TCAS_PFD_RA_UP, tcas);
		
		for(int i = 0; i < 2; i++) {
			
			iface.includeDataRef(TCAS_X[i]);
			iface.includeDataRef(TCAS_Y[i]);
			iface.includeDataRef(TCAS_TYPE_SHOW[i]);
			iface.includeDataRef(TCAS_SHOW[i]);
			iface.includeDataRef(TCAS_TEST_SHOW[i]);
			iface.includeDataRef(TCAS_FAIL_SHOW[i]);
			iface.includeDataRef(TCAS_OFF_SHOW[i]);
			iface.includeDataRef(TCAS_AI_SHOW[i]);
			iface.includeDataRef(TCAS_ALT_DN_UP_SHOW[i]);
			iface.includeDataRef(TCAS_ARROW_DN_UP_SHOW[i]);
			iface.includeDataRef(TCAS_TRAFFIC_RA[i]);
			iface.includeDataRef(TCAS_TRAFFIC_TA[i]);
			iface.includeDataRef(TCAS_PFD_RA_DN2[i], 0.1f);
			iface.includeDataRef(TCAS_PFD_RA_UP2[i], 0.1f);
			iface.observeDataRef(TCAS_X[i], tcas);
			iface.observeDataRef(TCAS_Y[i], tcas);
			iface.observeDataRef(TCAS_TYPE_SHOW[i], tcas);
			iface.observeDataRef(TCAS_AI_SHOW[i], tcas);
			iface.observeDataRef(TCAS_SHOW[i], tcas);
			iface.observeDataRef(TCAS_TEST_SHOW[i], tcas);
			iface.observeDataRef(TCAS_FAIL_SHOW[i], tcas);
			iface.observeDataRef(TCAS_OFF_SHOW[i], tcas);
			iface.observeDataRef(TCAS_ALT_DN_UP_SHOW[i], tcas);
			iface.observeDataRef(TCAS_ARROW_DN_UP_SHOW[i], tcas);
			iface.observeDataRef(TCAS_TRAFFIC_RA[i], tcas);
			iface.observeDataRef(TCAS_TRAFFIC_TA[i], tcas);
			iface.observeDataRef(TCAS_PFD_RA_DN2[i], tcas);
			iface.observeDataRef(TCAS_PFD_RA_UP2[i], tcas);
		}
		
		for(int i = 0; i < 19; i++) {
			iface.includeDataRef(TCAS_ALT[i]);
			iface.includeDataRef(TCAS_ALT_FO[i]);
			iface.observeDataRef(TCAS_ALT[i], tcas);
			iface.observeDataRef(TCAS_ALT_FO[i], tcas);
		}
		
	}

	@Override
	public void excludeDrefs() {
		
		iface.excludeDataRef(TCAS_PFD_RA_DN);
		iface.excludeDataRef(TCAS_PFD_RA_UP);
		
		iface.unObserveDataRef(TCAS_PFD_RA_DN, tcas);
		iface.unObserveDataRef(TCAS_PFD_RA_UP, tcas);
		
		for(int i = 0; i < 2; i++) {
			
			iface.excludeDataRef(TCAS_X[i]);
			iface.excludeDataRef(TCAS_Y[i]);
			iface.excludeDataRef(TCAS_TYPE_SHOW[i]);
			iface.excludeDataRef(TCAS_SHOW[i]);
			iface.excludeDataRef(TCAS_TEST_SHOW[i]);
			iface.excludeDataRef(TCAS_FAIL_SHOW[i]);
			iface.excludeDataRef(TCAS_OFF_SHOW[i]);
			iface.excludeDataRef(TCAS_AI_SHOW[i]);
			iface.excludeDataRef(TCAS_ALT_DN_UP_SHOW[i]);
			iface.excludeDataRef(TCAS_ARROW_DN_UP_SHOW[i]);
			iface.excludeDataRef(TCAS_TRAFFIC_RA[i]);
			iface.excludeDataRef(TCAS_TRAFFIC_TA[i]);
			iface.excludeDataRef(TCAS_PFD_RA_DN2[i]);
			iface.excludeDataRef(TCAS_PFD_RA_UP2[i]);
			iface.unObserveDataRef(TCAS_X[i], tcas);
			iface.unObserveDataRef(TCAS_Y[i], tcas);
			iface.unObserveDataRef(TCAS_TYPE_SHOW[i], tcas);
			iface.unObserveDataRef(TCAS_AI_SHOW[i], tcas);
			iface.unObserveDataRef(TCAS_SHOW[i], tcas);
			iface.unObserveDataRef(TCAS_TEST_SHOW[i], tcas);
			iface.unObserveDataRef(TCAS_FAIL_SHOW[i], tcas);
			iface.unObserveDataRef(TCAS_OFF_SHOW[i], tcas);
			iface.unObserveDataRef(TCAS_ALT_DN_UP_SHOW[i], tcas);
			iface.unObserveDataRef(TCAS_ARROW_DN_UP_SHOW[i], tcas);
			iface.unObserveDataRef(TCAS_TRAFFIC_RA[i], tcas);
			iface.unObserveDataRef(TCAS_TRAFFIC_TA[i], tcas);
			iface.unObserveDataRef(TCAS_PFD_RA_DN2[i], tcas);
			iface.unObserveDataRef(TCAS_PFD_RA_UP2[i], tcas);
		}
		
		for(int i = 0; i < 19; i++) {
			iface.excludeDataRef(TCAS_ALT[i]);
			iface.excludeDataRef(TCAS_ALT_FO[i]);
			iface.unObserveDataRef(TCAS_ALT[i], tcas);
			iface.unObserveDataRef(TCAS_ALT_FO[i], tcas);
		}
		
	}
	
}