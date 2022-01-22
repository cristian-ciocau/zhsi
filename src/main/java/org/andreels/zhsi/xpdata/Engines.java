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

public class Engines extends BaseDataClass {
	
	Observer<DataRef> engines;
	
	private final String[] ENG_N1 = {"laminar/B738/engine/indicators/N1_percent_1", "laminar/B738/engine/indicators/N1_percent_2"};
	private final String[] ENG_N2 = {"laminar/B738/engine/indicators/N2_percent_1", "laminar/B738/engine/indicators/N2_percent_2"};
	private final String[] ENG_EGT = {"laminar/B738/engine/eng1_egt", "laminar/B738/engine/eng2_egt"};
	private final String[] ENG_N1_REQ = {"laminar/B738/engine/eng1_req_n1", "laminar/B738/engine/eng2_req_n1"};
	private final String[] ENG_OIL_PRESS = {"laminar/B738/engine/eng1_oil_press", "laminar/B738/engine/eng2_oil_press"};
	private final String[] OIL_TEMP_C = {"laminar/B738/engine/eng1_oil_temp", "laminar/B738/engine/eng2_oil_temp"};
	private final String[] ENG_N1_BUG = {"laminar/B738/engine/eng1_N1_bug", "laminar/B738/engine/eng2_N1_bug"};
	private final String[] FUEL_QTY_KGS = {"laminar/B738/fuel/left_tank_kgs", "laminar/B738/fuel/center_tank_kgs", "laminar/B738/fuel/right_tank_kgs"};
	private final String[] FUEL_QTY_LBS = {"laminar/B738/fuel/left_tank_lbs", "laminar/B738/fuel/center_tank_lbs", "laminar/B738/fuel/right_tank_lbs"};
	private final String[] ENG_OIL_BYPASS = {"laminar/B738/engine/eng1_oil_filter_bypass", "laminar/B738/engine/eng2_oil_filter_bypass"};
	private final String[] ENG_START_VALUE = {"laminar/B738/engine/start_valve1", "laminar/B738/engine/start_valve2"};
	private final String FUEL_FLOW = "sim/flightmodel/engine/ENGN_FF_"; //(value * 3600 / 1000) float[8]
	private final String N1_PERCENT = "sim/cockpit2/engine/indicators/N1_percent"; //float[8] 
	private final String OIL_PRESSURE_ANNUN = "sim/cockpit2/annunciators/oil_pressure_low"; //float[8] 
	private final String OIL_QTY = "sim/cockpit2/engine/indicators/oil_quantity_ratio"; // float[8]
	private final String N1_MODE = "laminar/B738/FMS/N1_mode"; //0 = MAN; 1 = TO 2 = TO 1 3 = TO 2
	private final String FUEL_FLOW_USED_SHOW = "laminar/B738/fuel_flow_used_show";
	private final String FUEL_FLOW_USED1 = "laminar/B738/fuel_flow_used1";
	private final String FUEL_FLOW_USED2 = "laminar/B738/fuel_flow_used2";
	private final String ENG1_OUT = "laminar/B738/FMS/eng1_out";
	private final String ENG2_OUT = "laminar/B738/FMS/eng2_out";
	private final String ENG1_TAI = "laminar/B738/eicas/eng1_tai";
	private final String ENG2_TAI = "laminar/B738/eicas/eng2_tai";
	private final String EGT_REDLINE1 = "laminar/B738/systems/egt_redline1";
	private final String EGT_REDLINE2 = "laminar/B738/systems/egt_redline2";
	private final String EICAS_FF1 = "laminar/B738/engine/eicas_ff1";
	private final String EICAS_FF2 = "laminar/B738/engine/eicas_ff2";
	private final String EICAS_OIL_PRESS1 = "laminar/B738/engine/eicas_oil_press1";
	private final String EICAS_OIL_PRESS2 = "laminar/B738/engine/eicas_oil_press2";
	private final String EICAS_OIL_TEMP1 = "laminar/B738/engine/eicas_oil_temp1";
	private final String EICAS_OIL_TEMP2 = "laminar/B738/engine/eicas_oil_temp2";

	public float[] eng_n1 = new float[2];
	public float[] eng_n2 = new float[2];
	public float[] eng_egt = new float[2];
	public float[] eng_n1_req = new float[2];
	public float[] fuel_qty_kgs = new float[3];
	public float[] fuel_qty_lbs = new float[3];
	public int[] eng_oil_bypass = new int[2];
	public float[] eng_start_value = new float[2];
	public float[] fuel_flow = new float[2];
	public float[] n1_percent = new float[2];
	public int[] oil_pressure_annun = new int[2];
	public float[] oil_qty = new float[2];
	public float[] oil_temp_c = new float[2];
	public float[] eng_oil_press = new float[2];
	public float[] eng_n1_bug = new float[2];
	public int n1_mode = 0;
	public float eng1_tai = 0.0f;
	public float eng2_tai = 0.0f;
	public float egt_redline1 = 0.0f;
	public float egt_redline2 = 0.0f;
	public float eicas_ff1 = 0.0f;
	public float eicas_ff2 = 0.0f;
	public float eicas_oil_press1 = 0.0f;
	public float eicas_oil_press2 = 0.0f;
	public float eicas_oil_temp1 = 0.0f;
	public float eicas_oil_temp2 = 0.0f;
	public int fuel_flow_used_show = 0;
	public float fuel_flow_used1 = 0f;
	public float fuel_flow_used2 = 0f;
	public float eng1_out = 0f;
	public float eng2_out = 0f;
	

	public Engines(ExtPlaneInterface iface) {
		super(iface);
		
		engines = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				switch(object.getName()) {
				case FUEL_FLOW:
					for(int i = 0; i < 2; i++) {
						if(object.getName().equals(FUEL_FLOW)) {
							fuel_flow[i] = Float.parseFloat(object.getValue()[i]);
						}
					}
					break;
				case N1_PERCENT:
					for(int i = 0; i < 2; i++) {
						if(object.getName().equals(N1_PERCENT)) {
							n1_percent[i] = Float.parseFloat(object.getValue()[i]);
						}
					}
					break;
				case OIL_PRESSURE_ANNUN:
					for(int i = 0; i < 2; i++) {
						if(object.getName().equals(OIL_PRESSURE_ANNUN)) {
							oil_pressure_annun[i] = Integer.parseInt(object.getValue()[i]);
						}
					}
					break;
				case OIL_QTY:
					for(int i = 0; i < 2; i++) {
						if(object.getName().equals(OIL_QTY)) {
							oil_qty[i] = Float.parseFloat(object.getValue()[i]);
						}
					}
					break;
				case N1_MODE:
					n1_mode = Integer.parseInt(object.getValue()[0]);
					break;
				case ENG1_TAI:
					eng1_tai = Float.parseFloat(object.getValue()[0]);
					break;
				case ENG2_TAI:
					eng2_tai = Float.parseFloat(object.getValue()[0]);
					break;
				case EGT_REDLINE1:
					egt_redline1 = Float.parseFloat(object.getValue()[0]);
					break;
				case EGT_REDLINE2:
					egt_redline2 = Float.parseFloat(object.getValue()[0]);
					break;
				case EICAS_FF1:
					eicas_ff1 = Float.parseFloat(object.getValue()[0]);
					break;
				case EICAS_FF2:
					eicas_ff2 = Float.parseFloat(object.getValue()[0]);
					break;
				case EICAS_OIL_PRESS1:
					eicas_oil_press1 = Float.parseFloat(object.getValue()[0]);
					break;
				case EICAS_OIL_PRESS2:
					eicas_oil_press2 = Float.parseFloat(object.getValue()[0]);
					break;
				case EICAS_OIL_TEMP1:
					eicas_oil_temp1 = Float.parseFloat(object.getValue()[0]);
					break;
				case EICAS_OIL_TEMP2:
					eicas_oil_temp2 = Float.parseFloat(object.getValue()[0]);
					break;
				case FUEL_FLOW_USED_SHOW:
					fuel_flow_used_show = Integer.parseInt(object.getValue()[0]);
					break;
				case FUEL_FLOW_USED1:
					fuel_flow_used1 = Float.parseFloat(object.getValue()[0]);
					break;
				case FUEL_FLOW_USED2:
					fuel_flow_used2 = Float.parseFloat(object.getValue()[0]);
					break;
				case ENG1_OUT:
					eng1_out = Float.parseFloat(object.getValue()[0]);
					break;
				case ENG2_OUT:
					eng2_out = Float.parseFloat(object.getValue()[0]);
					break;
				}
				
				for(int i = 0; i < 2; i++) {

					if (object.getName().equals(ENG_N1[i])) {
						eng_n1[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(ENG_N2[i])) {
						eng_n2[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(ENG_EGT[i])) {
						eng_egt[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(ENG_N1_REQ[i])) {
						eng_n1_req[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(ENG_OIL_BYPASS[i])) {
						eng_oil_bypass[i] = Integer.parseInt(object.getValue()[0]);
					}
					if (object.getName().equals(ENG_START_VALUE[i])) {
						eng_start_value[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(ENG_OIL_PRESS[i])) {
						eng_oil_press[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(OIL_TEMP_C[i])) {
						oil_temp_c[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(ENG_N1_BUG[i])) {
						eng_n1_bug[i] = Float.parseFloat(object.getValue()[0]);
					}
				}
				for(int i = 0; i < 3; i++) {

					if (object.getName().equals(FUEL_QTY_KGS[i])) {
						fuel_qty_kgs[i] = (float) Math.floor(Float.parseFloat(object.getValue()[0]));
					}
				}				
				for(int i = 0; i < 3; i++) {

					if (object.getName().equals(FUEL_QTY_LBS[i])) {
						fuel_qty_lbs[i] = (float) Math.floor(Float.parseFloat(object.getValue()[0]));
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
	
		iface.includeDataRef(FUEL_FLOW, 0.001f);
		iface.includeDataRef(N1_PERCENT, 0.001f);
		iface.includeDataRef(OIL_QTY);
		iface.includeDataRef(OIL_PRESSURE_ANNUN);	
		iface.includeDataRef(N1_MODE);
		iface.includeDataRef(ENG1_TAI, 0.1f);
		iface.includeDataRef(ENG2_TAI, 0.1f);
		iface.includeDataRef(EGT_REDLINE1);
		iface.includeDataRef(EGT_REDLINE2);
		iface.includeDataRef(EICAS_FF1, 0.1f);
		iface.includeDataRef(EICAS_FF2, 0.1f);
		iface.includeDataRef(EICAS_OIL_PRESS1, 0.1f);
		iface.includeDataRef(EICAS_OIL_PRESS2, 0.1f);
		iface.includeDataRef(EICAS_OIL_TEMP1, 0.1f);
		iface.includeDataRef(EICAS_OIL_TEMP2, 0.1f);
		iface.includeDataRef(FUEL_FLOW_USED_SHOW, 0.1f);
		iface.includeDataRef(FUEL_FLOW_USED1, 0.1f);
		iface.includeDataRef(FUEL_FLOW_USED2, 0.1f);
		iface.includeDataRef(ENG1_OUT);
		iface.includeDataRef(ENG2_OUT);
		
		iface.observeDataRef(FUEL_FLOW, engines);
		iface.observeDataRef(N1_PERCENT, engines);
		iface.observeDataRef(OIL_QTY, engines);
		iface.observeDataRef(OIL_PRESSURE_ANNUN, engines);	
		iface.observeDataRef(N1_MODE, engines);
		iface.observeDataRef(ENG1_TAI, engines);
		iface.observeDataRef(ENG2_TAI, engines);
		iface.observeDataRef(EGT_REDLINE1, engines);
		iface.observeDataRef(EGT_REDLINE2, engines);
		iface.observeDataRef(EICAS_FF1, engines);
		iface.observeDataRef(EICAS_FF2, engines);
		iface.observeDataRef(EICAS_OIL_PRESS1, engines);
		iface.observeDataRef(EICAS_OIL_PRESS2, engines);
		iface.observeDataRef(EICAS_OIL_TEMP1, engines);
		iface.observeDataRef(EICAS_OIL_TEMP2, engines);
		iface.observeDataRef(FUEL_FLOW_USED_SHOW, engines);
		iface.observeDataRef(FUEL_FLOW_USED1, engines);
		iface.observeDataRef(FUEL_FLOW_USED2, engines);
		iface.observeDataRef(ENG1_OUT, engines);
		iface.observeDataRef(ENG2_OUT, engines);
		
		for(int i = 0; i < 2; i++) {
			iface.includeDataRef(ENG_N1[i], 0.001f);
			iface.includeDataRef(ENG_N2[i], 0.001f);
			iface.includeDataRef(ENG_EGT[i], 0.01f);
			iface.includeDataRef(ENG_N1_REQ[i], 0.001f);
			iface.includeDataRef(ENG_OIL_PRESS[i], 0.01f);
			iface.includeDataRef(OIL_TEMP_C[i]);
			iface.includeDataRef(ENG_N1_BUG[i]);
			iface.includeDataRef(ENG_OIL_BYPASS[i], 0.01f);
			iface.includeDataRef(ENG_START_VALUE[i]);
			iface.observeDataRef(ENG_N1[i], engines);
			iface.observeDataRef(ENG_N2[i], engines);
			iface.observeDataRef(ENG_EGT[i], engines);
			iface.observeDataRef(ENG_N1_REQ[i], engines);
			iface.observeDataRef(ENG_OIL_PRESS[i], engines);
			iface.observeDataRef(OIL_TEMP_C[i], engines);
			iface.observeDataRef(ENG_N1_BUG[i], engines);
			iface.observeDataRef(ENG_OIL_BYPASS[i], engines);
			iface.observeDataRef(ENG_START_VALUE[i], engines);
		}
		for(int i = 0; i < 3; i++) {
			iface.includeDataRef(FUEL_QTY_KGS[i], 1f);
			iface.observeDataRef(FUEL_QTY_KGS[i], engines);
		}
		for(int i = 0; i < 3; i++) {
			iface.includeDataRef(FUEL_QTY_LBS[i], 1f);
			iface.observeDataRef(FUEL_QTY_LBS[i], engines);
		}
		
	}


	@Override
	public void excludeDrefs() {
		
		iface.excludeDataRef(FUEL_FLOW);
		iface.excludeDataRef(N1_PERCENT);
		iface.excludeDataRef(OIL_QTY);
		iface.excludeDataRef(OIL_PRESSURE_ANNUN);	
		iface.excludeDataRef(N1_MODE);
		iface.excludeDataRef(ENG1_TAI);
		iface.excludeDataRef(ENG2_TAI);
		iface.excludeDataRef(EGT_REDLINE1);
		iface.excludeDataRef(EGT_REDLINE2);
		iface.excludeDataRef(EICAS_FF1);
		iface.excludeDataRef(EICAS_FF2);
		iface.excludeDataRef(EICAS_OIL_PRESS1);
		iface.excludeDataRef(EICAS_OIL_PRESS2);
		iface.excludeDataRef(EICAS_OIL_TEMP1);		
		iface.excludeDataRef(EICAS_OIL_TEMP2);
		iface.excludeDataRef(FUEL_FLOW_USED_SHOW);
		iface.excludeDataRef(FUEL_FLOW_USED1);
		iface.excludeDataRef(FUEL_FLOW_USED2);
		iface.excludeDataRef(ENG1_OUT);
		iface.excludeDataRef(ENG2_OUT);
		
		iface.unObserveDataRef(FUEL_FLOW, engines);
		iface.unObserveDataRef(N1_PERCENT, engines);
		iface.unObserveDataRef(OIL_QTY, engines);
		iface.unObserveDataRef(OIL_PRESSURE_ANNUN, engines);
		iface.unObserveDataRef(N1_MODE, engines);
		iface.unObserveDataRef(ENG1_TAI, engines);
		iface.unObserveDataRef(ENG2_TAI, engines);
		iface.unObserveDataRef(EGT_REDLINE1, engines);
		iface.unObserveDataRef(EGT_REDLINE2, engines);
		iface.unObserveDataRef(EICAS_FF1, engines);
		iface.unObserveDataRef(EICAS_FF2, engines);
		iface.unObserveDataRef(EICAS_OIL_PRESS1, engines);
		iface.unObserveDataRef(EICAS_OIL_PRESS2, engines);
		iface.unObserveDataRef(EICAS_OIL_TEMP1, engines);
		iface.unObserveDataRef(EICAS_OIL_TEMP2, engines);
		iface.unObserveDataRef(FUEL_FLOW_USED_SHOW, engines);
		iface.unObserveDataRef(FUEL_FLOW_USED1, engines);
		iface.unObserveDataRef(FUEL_FLOW_USED2, engines);
		iface.unObserveDataRef(ENG1_OUT, engines);
		iface.unObserveDataRef(ENG2_OUT, engines);
		
		for(int i = 0; i < 2; i++) {
			iface.excludeDataRef(ENG_N1[i]);
			iface.excludeDataRef(ENG_N2[i]);
			iface.excludeDataRef(ENG_EGT[i]);
			iface.excludeDataRef(ENG_N1_REQ[i]);
			iface.excludeDataRef(ENG_OIL_PRESS[i]);
			iface.excludeDataRef(OIL_TEMP_C[i]);
			iface.excludeDataRef(ENG_N1_BUG[i]);
			iface.excludeDataRef(ENG_OIL_BYPASS[i]);
			iface.excludeDataRef(ENG_START_VALUE[i]);
			iface.unObserveDataRef(ENG_N1[i], engines);
			iface.unObserveDataRef(ENG_N2[i], engines);
			iface.unObserveDataRef(ENG_EGT[i], engines);
			iface.unObserveDataRef(ENG_N1_REQ[i], engines);
			iface.unObserveDataRef(ENG_OIL_PRESS[i], engines);
			iface.unObserveDataRef(OIL_TEMP_C[i], engines);
			iface.unObserveDataRef(ENG_N1_BUG[i], engines);
			iface.unObserveDataRef(ENG_OIL_BYPASS[i], engines);
			iface.unObserveDataRef(ENG_START_VALUE[i], engines);
		}
		for(int i = 0; i < 3; i++) {
			iface.excludeDataRef(FUEL_QTY_KGS[i]);
			iface.unObserveDataRef(FUEL_QTY_KGS[i], engines);
		}
		for(int i = 0; i < 3; i++) {
			iface.excludeDataRef(FUEL_QTY_LBS[i]);
			iface.unObserveDataRef(FUEL_QTY_LBS[i], engines);
		}
		
	}

}
