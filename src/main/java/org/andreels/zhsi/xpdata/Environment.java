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

public class Environment extends BaseDataClass {

	Observer<DataRef> environment;

	private final String OUTSIDE_AIR_TEMP_C = "sim/cockpit2/temperature/outside_air_LE_temp_degc";
	private final String WIND_SPEED_KNOTS = "sim/cockpit2/gauges/indicators/wind_speed_kts";
	private final String WIND_HEADING = "sim/cockpit2/gauges/indicators/wind_heading_deg_mag";
	
	public float outside_air_temp_c = 0f;
	public float wind_speed_knots = 0f;
	public float wind_heading = 0f;
	
	public Environment(ExtPlaneInterface iface) {

		super(iface);
		
		drefs.add(OUTSIDE_AIR_TEMP_C);
		drefs.add(WIND_SPEED_KNOTS);
		drefs.add(WIND_HEADING);
		
		environment = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {
				
				switch(object.getName()) {
				case OUTSIDE_AIR_TEMP_C:
					outside_air_temp_c = Float.parseFloat(object.getValue()[0]);
					break;
				case WIND_SPEED_KNOTS:
					wind_speed_knots = Float.parseFloat(object.getValue()[0]);
					break;
				case WIND_HEADING:
					wind_heading = Float.parseFloat(object.getValue()[0]);
					break;
				}
			}
		};
	}
	
	@Override
	public void subscribeDrefs() {
				
	}

	public void includeDrefs() {
		
		iface.includeDataRef(OUTSIDE_AIR_TEMP_C, 0.1f);
		iface.includeDataRef(WIND_SPEED_KNOTS, 0.1f);
		iface.includeDataRef(WIND_HEADING, 0.1f);
		
		iface.observeDataRef(OUTSIDE_AIR_TEMP_C, environment);
		iface.observeDataRef(WIND_SPEED_KNOTS, environment);
		iface.observeDataRef(WIND_HEADING, environment);
		
	}
	
	public void excludeDrefs() {
		
		for(String dref : drefs) {
			iface.excludeDataRef(dref);
			iface.unObserveDataRef(dref, environment);
		}	
	}
}