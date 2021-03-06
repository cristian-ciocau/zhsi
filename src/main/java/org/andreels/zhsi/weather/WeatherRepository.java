/**
* WeatherRepository.java
* 
* Manages and provides access to the weather data
* via various accessors and search methods.
* 
* Copyright (C) 2017 Nicolas Carel
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

package org.andreels.zhsi.weather;

import java.util.ArrayList;
import java.util.logging.Logger;

public class WeatherRepository {
	
	private static Logger logger = Logger.getLogger("org.andreels.zhsi");
	private static WeatherRepository single_instance;
	private ArrayList<WeatherArea> weather_areas;
	private long last_update;
	
	private WeatherRepository() {
		init();
	}
	
	private void init() {
		weather_areas = new ArrayList<WeatherArea>();
		last_update=0;
	}

	public static WeatherRepository getInstance() {
		if(single_instance == null) {
			single_instance = new WeatherRepository();
		}
		return single_instance;
	}
	public void addWeatherArea(WeatherArea area) {
		weather_areas.add(area);
		this.dumpAreas();
	}
	
    public void dumpAreas() {
    	logger.fine("Dumping Weather areas... ");
    	for (WeatherArea area:weather_areas ) {
    		logger.fine(" min_lat: " + area.min_lat);
    		logger.fine(" max_lat: " + area.max_lat);
    		logger.fine(" min_lon: " + area.min_lon);
    		logger.fine(" max_lon: " + area.max_lon);
    		logger.fine(" number_of_rows: " + area.number_of_rows);
    		logger.fine(" number_of_columns: " + area.number_of_columns);
    		logger.fine(" grid_size_x: " + area.grid_size_x);
    		logger.fine(" grid_size_y: " + area.grid_size_y);
    		logger.fine(" grid_step_x: " + area.grid_step_x);
    		logger.fine(" grid_step_y: " + area.grid_step_y);
    	}
    }
	
	public WeatherArea findWeatherArea( float lat, float lon ) {
		// find the first aera containing the coordinates
		for (WeatherArea area:weather_areas ) {
			if (lat >= area.min_lat && lat < area.max_lat && lon >= area.min_lon && lon < area.max_lon) {
				return area;
			}
		}
		return null;
	}

	public void updateArea(float lat, float lon, int slice_pos, byte[] slice) {
		WeatherArea area = findWeatherArea(lat, lon);
		if (area == null) {
			area = new WeatherArea( 61,  62,  lat,  lat+1,  lon,  lon+1);
			this.addWeatherArea(area);
		}
		area.write_slice(slice_pos, slice);
		last_update = System.currentTimeMillis();
	}
	
	public boolean updated() {
		return System.currentTimeMillis() - last_update < 6000;
	}
	
	public String get_area_name( float lat, float lon ) {
		WeatherArea area = findWeatherArea( lat, lon );
		if (area != null) { 
			return area.name;
		} else {
			return "N/A";
		}
	}
	
	public int get_lat_offset( float lat, float lon ) {
		WeatherArea area = findWeatherArea( lat, lon );
		if (area != null) { 
			return area.get_lat_offset(  lat,  lon );
		} else {
			return -1;
		}
	}

	public int get_lon_offset( float lat, float lon ) {
		WeatherArea area = findWeatherArea( lat, lon );
		if (area != null) { 
			return area.get_lon_offset(  lat,  lon );
		} else {
			return -1;
		}
	}
	
	public float get_storm_level( float lat, float lon ) {
		float level = -2.0f; // area not found value
		WeatherArea area = findWeatherArea( lat, lon );
		if (area != null) level = area.get_storm_level(lat, lon);
		return level;
	}
	
	public float get_interpolated_storm_level( float lat, float lon ) {
		WeatherArea area = findWeatherArea( lat, lon );
		return (area != null) ? area.get_interpolated_storm_level(lat, lon) : -2.0f;
	}

}
