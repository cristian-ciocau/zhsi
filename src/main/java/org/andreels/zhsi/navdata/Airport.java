/**
 * Airport.java
 * 
 * Model class for an airport
 * 
 * Copyright (C) 2007  Georg Gruetter (gruetter@gmail.com)
 * Copyright (C) 2009  Marc Rogiers (marrog.123@gmail.com)
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
package org.andreels.zhsi.navdata;

import java.util.ArrayList;

public class Airport extends NavigationObject {

	public String icao_code;
	public ArrayList<Runway> runways;
	public float longest;
	public int elev;
	public int surface;
	public ArrayList<ComRadio> com_radios;

	public Airport(String name, String icao_code, float lat, float lon, ArrayList<Runway> runways, float longest,
			int elev, ArrayList<ComRadio> com_radios) {
		super(name, lat, lon);
		this.icao_code = icao_code.trim();
		this.runways = runways;
		this.longest = longest;
		this.elev = elev;
		this.com_radios = com_radios;
	}

	public Airport(String airport_icao_code, float arp_lat, float arp_lon, int elev) {
		super(airport_icao_code, arp_lat, arp_lon);
		this.icao_code = airport_icao_code;
		this.elev = elev;
	}

	public Airport(String airport_icao_code, String airport_name, float arp_lat, float arp_lon, int elevation,
			int surface, float longest) {
		super(airport_name, arp_lat, arp_lon);
		this.icao_code = airport_icao_code;
		this.elev = elevation;
		this.surface = surface;
		this.longest = longest;
	}


	public String toString() {
		return "Airport '" + this.name + "' @ (" + this.lat + "," + this.lon + ")";
	}
}
