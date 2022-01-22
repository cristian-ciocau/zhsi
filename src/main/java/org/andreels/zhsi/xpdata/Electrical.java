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

public class Electrical extends BaseDataClass {
	
	Observer<DataRef> electrical;

	private final String STANDBY_BAT_POS = "laminar/B738/electric/standby_bat_pos";	
	private final String AC_FREQ_VALUE = "laminar/B738/ac_freq_value";
	private final String AC_AMP_VALUE  = "laminar/B738/ac_amp_value";
	private final String AC_VOLT_VALUE = "laminar/B738/ac_volt_value";
	private final String DC_AMP_VALUE  = "laminar/B738/dc_amp_value";
	private final String DC_VOLT_VALUE = "laminar/B738/dc_volt_value";
	
	public int standby_bat_pos = 0;
	public float ac_freq_value = 0;
	public float ac_amp_value  = 0;
	public float ac_volt_value = 0;
	public float dc_amp_value  = 0;
	public float dc_volt_value = 0;
	
	public Electrical(ExtPlaneInterface iface) {
		
		super(iface);
		
		this.drefs.add(STANDBY_BAT_POS);
		this.drefs.add(AC_FREQ_VALUE);
		this.drefs.add(AC_AMP_VALUE);
		this.drefs.add(AC_VOLT_VALUE);
		this.drefs.add(DC_AMP_VALUE);
		this.drefs.add(DC_VOLT_VALUE);
		
		electrical = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				switch(object.getName()) {
				case STANDBY_BAT_POS: standby_bat_pos = Integer.parseInt(object.getValue()[0]);
				break;
				case AC_FREQ_VALUE: ac_freq_value = Float.parseFloat(object.getValue()[0]);
				break;
				case AC_AMP_VALUE: ac_amp_value = Float.parseFloat(object.getValue()[0]);
				break;
				case AC_VOLT_VALUE: ac_volt_value = Float.parseFloat(object.getValue()[0]);
				break;
				case DC_AMP_VALUE: dc_amp_value = Float.parseFloat(object.getValue()[0]);
				break;
				case DC_VOLT_VALUE: dc_volt_value = Float.parseFloat(object.getValue()[0]);
				break;
				}
				
			}
			
		};
		
	}

	@Override
	public void subscribeDrefs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void includeDrefs() {
		
		for(String dref : drefs) {
			iface.includeDataRef(dref);
			iface.observeDataRef(dref, electrical);
		}	
		
	}

	@Override
	public void excludeDrefs() {
		
		for(String dref : drefs) {
			iface.excludeDataRef(dref);
			iface.unObserveDataRef(dref, electrical);
		}	
		
	}

}