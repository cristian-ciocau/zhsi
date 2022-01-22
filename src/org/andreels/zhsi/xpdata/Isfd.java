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

import java.util.ArrayList;

import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRef;
import org.andreels.zhsi.ExtPlaneInterface.util.Observer;

public class Isfd extends BaseDataClass {
	
	Observer<DataRef> isfd;
	ArrayList<String> drefs = new ArrayList<String>();
	
	private final String ISFD_ALTITUDE = "laminar/B738/gauges/standby_altitude_ft";
	private final String ISPD_ALT_BARO = "laminar/B738/knobs/standby_alt_baro";
	private final String ISPD_MODE = "laminar/B738/gauges/standby_mode"; // 0 = none, 1 = APP
	private final String ISFD_STD_MODE = "laminar/B738/gauges/standby_alt_std_mode";
	private final String ISFD_INIT_TIME = "laminar/B738/display/isfd_init_time";
	
	public float isfd_altitude = 0f;
	public float isfd_alt_baro = 0f;
	public int isfd_mode = 0;
	public int isfd_std_mode = 0;
	public float isfd_init_time = 0;
	
	public Isfd(ExtPlaneInterface iface) {
		
		super(iface);
		
		drefs.add(ISFD_ALTITUDE);
		drefs.add(ISPD_ALT_BARO);
		drefs.add(ISPD_MODE);
		drefs.add(ISFD_STD_MODE);
		drefs.add(ISFD_INIT_TIME);
		
		isfd = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
			
				switch(object.getName()) {
				case ISFD_ALTITUDE: isfd_altitude = Float.parseFloat(object.getValue()[0]);
				break;
				case ISPD_ALT_BARO: isfd_alt_baro = Float.parseFloat(object.getValue()[0]);
				break;
				case ISPD_MODE: isfd_mode = Integer.parseInt(object.getValue()[0]);
				break;
				case ISFD_STD_MODE: isfd_std_mode = Integer.parseInt(object.getValue()[0]);
				break;
				case ISFD_INIT_TIME: isfd_init_time = Float.parseFloat(object.getValue()[0]);
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
			iface.observeDataRef(dref, isfd);
		}
		
	}

	@Override
	public void excludeDrefs() {
		
		for(String dref : drefs) {
			iface.excludeDataRef(dref);
			iface.unObserveDataRef(dref, isfd);
		}	
		
	}

}