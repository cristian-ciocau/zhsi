/**
 * CoordinateSystem.java
 * 
 * Provides various computation methods for calculating distances.
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

import java.util.logging.Logger;

public class CoordinateSystem {

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");


	public static float nm_per_deg_lat() {
		return 60.0f;
	}


	public static float nm_per_deg_lon(float lat) {
		return (float) (Math.cos(Math.toRadians(lat)) * 60.0f);
	}


	public static float deg_lon_per_nm(float lat) {
		return (float) (1.0f / nm_per_deg_lon(lat));
	}


	public static float deg_lat_per_nm() {
		return 1.0f / 60.0f;
	}


	//    public static float km_per_deg_lon(float lat) {
	//        //return (float) (Math.cos(Math.toRadians(lat)) * 2.0f * Math.PI * (6370.0f/360.0f));
	//        return (float) (Math.cos(Math.toRadians(lat)) * 40000.0f / 360.0f);
	//    }


	//    public static float deg_lon_per_km(float lat) {
	//        return (float) (1.0f / km_per_deg_lon(lat));
	//    }


	//    public static float deg_lat_per_km() {
	//        return 1.0f / (60.0f * 1.852f);
	//    }


	//    public static float km_per_deg_lat() {
	//        return 1.0f / deg_lat_per_km();
	//    }


	public static float distance(float lat1, float lon1, float lat2, float lon2) {

		double a1 = Math.toRadians(lat1);
		double b1 = Math.toRadians(lon1);
		double a2 = Math.toRadians(lat2);
		double b2 = Math.toRadians(lon2);

		// why 3443.9f? Wikipedia says that the average radius of the earth is 3440.07NM

		return (float) Math.acos(
				(Math.cos(a1) * Math.cos(b1) * Math.cos(a2) * Math.cos(b2)) +
				(Math.cos(a1) * Math.sin(b1) * Math.cos(a2) * Math.sin(b2)) +
				(Math.sin(a1) * Math.sin(a2))
				) * 3443.9f;
	}


	public static float rough_distance(float lat1, float lon1, float lat2, float lon2) {

		return (float) Math.sqrt(
				Math.pow( nm_per_deg_lon( (lat1+lat2)/2) * (lon1-lon2), 2 ) +
				Math.pow( nm_per_deg_lat() * (lat1-lat2), 2 ) );

	}
	
	public static float get_vor_bearing(float mylat, float mylon, float lat, float lon, float offset, float magvar, float track) {
		
		double dLon = lon - mylon;
		double y = Math.sin(dLon) * Math.cos(lat);
		double x = Math.cos(mylat) * Math.sin(lat) - Math.sin(mylat) * Math.cos(lat) * Math.cos(dLon);
		float brng = (float) Math.toDegrees(Math.atan2(y, x));
		brng = brng - offset - (track + 360) % 360;
		//if(brng < 0) brng += 360;
		return (brng + 360) % 360;
	}
	
	public static float get_bearing(float mylat, float mylon, float lat, float lon) {
		
		double dLon = lon - mylon;
		double y = Math.sin(dLon) * Math.cos(lat);
		double x = Math.cos(mylat) * Math.sin(lat) - Math.sin(mylat) * Math.cos(lat) * Math.cos(dLon);
		float brng = (float) Math.toDegrees(Math.atan2(y, x));
		if(brng < 0) brng += 360;
		return brng;
	}

	public static float[] latlon_at_dist_heading(float lat, float lon, float dist, float track) {

		float[] newLatLon = new float[2];

		float earthRadius = 6378.1f;
		float heading =  (float) Math.toRadians(track);
		float distance  = dist * 1.852f; // distance in km
		
		float mylat = (float) Math.toRadians(lat);
		float mylon = (float) Math.toRadians(lon);

		float lat2 = (float) Math.asin( Math.sin(mylat) * Math.cos(distance / earthRadius) + Math.cos(mylat) * Math.sin(distance / earthRadius) * Math.cos(heading));
		float lon2 = (float) (mylon + Math.atan2(Math.sin(heading) * Math.sin(distance / earthRadius) * Math.cos(mylat), Math.cos(distance / earthRadius) - Math.sin(mylat) * Math.sin(lat2)));

		lat2 = (float) Math.toDegrees(lat2);
		lon2 = (float) Math.toDegrees(lon2);

		newLatLon[0] = lat2;
		newLatLon[1] = lon2;

		return newLatLon;
	}

	public static boolean isAhead(float mylat, float mylon, float lat, float lon, float heading) {

		double longitude1 = mylon;
		double longitude2 = lon;
		double latitude1 = Math.toRadians(mylat);
		double latitude2 = Math.toRadians(lat);
		double longDiff= Math.toRadians(longitude2-longitude1);
		double y= Math.sin(longDiff)*Math.cos(latitude2);
		double x= Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
		float bearing = (float) ((Math.toDegrees(Math.atan2(y, x)) - heading +360)%360);
		bearing = Math.abs(bearing);
		if ((bearing >= 270f || bearing <= 90f)) {
			return true;
		}else {
			return false;
		}
	}
	
	public static float heading_from_a_to_b(double lat1, double lon1, double lat2, double lon2) {
		
		// double cdPi = 3.14159265358979323846;
					
		lon1 = lon1 * -1; // Formula expects east longitudes to be negative the opposite of X-Plane!
		lon2 = lon2 * -1; // Formula expects east longitudes to be negative the opposite of X-Plane!

		double tc1 = modulus(Math.atan2(Math.sin(Math.toRadians(lon1) - Math.toRadians(lon2))*Math.cos(Math.toRadians(lat2)), Math.cos(Math.toRadians(lat1))*Math.sin(Math.toRadians(lat2)) - Math.sin(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.cos(Math.toRadians(lon1) - Math.toRadians(lon2))), 2 * Math.PI);
		tc1 = Math.toDegrees(tc1);
		return (float) tc1;
	}
	
	public static double modulus(double y, double x) {
		return (y - x * Math.floor(y / x));
	}
	
}