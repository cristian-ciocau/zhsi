/**
* AzimuthalEquidistantProjection.java
* 
* Projections used in the navigation displays
*  - MovingMap
*  - WeatherRadar
*  - Terrain
* 
* Copyright (C) 2007  Georg Gruetter (gruetter@gmail.com)
* Copyright (C) 2009-2014  Marc Rogiers (marrog.123@gmail.com)
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

package org.andreels.zhsi.utils;

public class AzimuthalEquidistantProjection implements Projection {
    
	private double phi1;
    private double sin_phi1;
    private double cos_phi1;
    private double lambda0;
    
    private double phi;
    private double sin_phi;
    private double cos_phi;
    private double lambda;
    private double d_lambda;
    private double sin_d_lambda;
    private double cos_d_lambda;
    
    private float mylat;
    private float mylon;
    
    private double rho;
    private double theta;
    
    private float x;
    private float y;
    
    private float pixels_per_nm;
    private float map_center_x;
    private float map_center_y;
    
    private String pilot;
    
    public AzimuthalEquidistantProjection(String pilot) {
    	// Set default values to avoid exceptions
    	pixels_per_nm=1;
    	map_center_x=0;
    	map_center_y=0;
    	this.pilot = pilot;
    }
    public AzimuthalEquidistantProjection() {
    	// Set default values to avoid exceptions
    	pixels_per_nm=1;
    	map_center_x=0;
    	map_center_y=0;
    }
    
    public void setScale(float ppnm) {
    	pixels_per_nm = ppnm;
    }
    
    public void setCenter(float x, float y) {
    	map_center_x = x;
    	map_center_y = y;
    }

    public void setAcf(float acf_lat, float acf_lon) {
        phi1 = Math.toRadians(acf_lat);
        mylat = acf_lat;
        mylon = acf_lon;
        lambda0 = Math.toRadians(acf_lon);
        sin_phi1 = Math.sin(phi1);
        cos_phi1 = Math.cos(phi1);
    }
    
    public void setPoint(float lat, float lon) {

        phi = Math.toRadians(lat);
        lambda = Math.toRadians(lon);
        sin_phi = Math.sin(phi);
        cos_phi = Math.cos(phi);
        d_lambda = lambda - lambda0;
        sin_d_lambda = Math.sin(d_lambda);
        cos_d_lambda = Math.cos(d_lambda);
        rho = Math.acos(sin_phi1 * sin_phi + cos_phi1 * cos_phi * cos_d_lambda);
        theta = Math.atan2(cos_phi1 * sin_phi - sin_phi1 * cos_phi * cos_d_lambda, cos_phi * sin_d_lambda);
        if(Double.isNaN(rho)) {
        	rho = 0;
        }
        x = (float)(rho * Math.sin(theta));
        y = - (float)(rho * Math.cos(theta));
    }
    
    public float getX() {
        return Math.round(this.map_center_x - y * 180.0f / Math.PI * 60.0f * this.pixels_per_nm); 
    }
    
    public float getY() {
        return Math.round(this.map_center_y - x * 180.0f / Math.PI * 60.0f * this.pixels_per_nm);
    }

	public float getBearing(float lat, float lon, float myheading) {

	    float longitude1 = mylon;
	    float longitude2 = lon;
	    double latitude1 = Math.toRadians(mylat);
	    float latitude2 = (float) Math.toRadians(lat);
	    double longDiff= Math.toRadians(longitude2-longitude1);
	         
	    double y= Math.sin(longDiff)*Math.cos(latitude2);
	    double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
	    double bearing = (Math.toDegrees(Math.atan2(y, x))+360)%360;

		return (float) bearing - myheading;
	}
	
    public float getPixels_per_nm(String pilot) {
		return pixels_per_nm;
	}
    
    public float getAirPlaneX(float lat, float lon, float pixels_per_nm, float map_center_x) {
		
    	double phi = Math.toRadians(lat);
    	double lambda = Math.toRadians(lon);
    	double sin_phi = Math.sin(phi);
    	double cos_phi = Math.cos(phi);
    	double d_lambda = lambda - lambda0;
    	double sin_d_lambda = Math.sin(d_lambda);
    	double cos_d_lambda = Math.cos(d_lambda);
    	double rho = Math.acos(sin_phi1 * sin_phi + cos_phi1 * cos_phi * cos_d_lambda);
    	double theta = Math.atan2(cos_phi1 * sin_phi - sin_phi1 * cos_phi * cos_d_lambda, cos_phi * sin_d_lambda);
        float y = - (float)(rho * Math.cos(theta));
        return Math.round(map_center_x - y * 180.0f / Math.PI * 60.0f * pixels_per_nm);		
    }
	
    public float getAirPlaneY(float lat, float lon, float pixels_per_nm, float map_center_y) {
		
    	double phi = Math.toRadians(lat);
    	double lambda = Math.toRadians(lon);
    	double sin_phi = Math.sin(phi);
    	double cos_phi = Math.cos(phi);
    	double d_lambda = lambda - lambda0;
    	double sin_d_lambda = Math.sin(d_lambda);
    	double cos_d_lambda = Math.cos(d_lambda);
    	double rho = Math.acos(sin_phi1 * sin_phi + cos_phi1 * cos_phi * cos_d_lambda);
    	double theta = Math.atan2(cos_phi1 * sin_phi - sin_phi1 * cos_phi * cos_d_lambda, cos_phi * sin_d_lambda);
        float x = (float)(rho * Math.sin(theta));
        return Math.round(map_center_y - x * 180.0f / Math.PI * 60.0f * pixels_per_nm);		
    }
	
}