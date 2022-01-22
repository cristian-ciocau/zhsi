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
package org.andreels.zhsi.displays.nd;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.navdata.CoordinateSystem;
import org.andreels.zhsi.resources.LoadResources;
import org.andreels.zhsi.utils.AzimuthalEquidistantProjection;
import org.andreels.zhsi.utils.Projection;
import org.andreels.zhsi.weather.WeatherRepository;
import org.andreels.zhsi.xpdata.XPData;

public class RenderWeather extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LoadResources rs;
	private ZHSIPreferences preferences;
	private Projection map_projection;
	private XPData xpd;
	private WeatherRepository weather_repository;
	
	AffineTransform original_at;
	
	private String pilot;
	float center_lat;
	float center_lon;
	
	public Color wxr_colors[] = new Color[10];
	
	Color colorAreaNotFound = new Color(192,64,64,64);
	Color colorSliceNotLoaded = new Color(128,128,64,64);
	Color colorBackGround = new Color(0,64,64,64);
	
	public BufferedImage img_weather1 = new BufferedImage(1029, 1011, BufferedImage.TYPE_INT_ARGB);
	public BufferedImage img_weather2 = new BufferedImage(1029, 1011, BufferedImage.TYPE_INT_ARGB);
	Graphics2D weather1 = img_weather1.createGraphics();
	Graphics2D weather2 = img_weather2.createGraphics();
	
	public RenderWeather(XPData xpd, String pilot) {
		this.pilot = pilot;
		this.xpd = xpd;
		this.map_projection = new AzimuthalEquidistantProjection();
		this.rs = LoadResources.getInstance();
		this.weather_repository = WeatherRepository.getInstance();
		this.preferences = ZHSIPreferences.getInstance();
		
		/*
		wxr_colors[0] = new Color(0,0,0);
		wxr_colors[1] = new Color(0,5,0);
		wxr_colors[2] = new Color(0,40,0);
		wxr_colors[3] = new Color(0,100,0);
		wxr_colors[4] = new Color(0,120,0);
		wxr_colors[5] = new Color(120,120,0);
		wxr_colors[6] = new Color(140,140,0);
		wxr_colors[7] = new Color(140,0,0);
		wxr_colors[8] = new Color(160,0,0);
		wxr_colors[9] = new Color(160,0,160); 
		*/
		
		wxr_colors[0] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_0).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_0).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_0).substring(6,8),16));
		wxr_colors[1] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_1).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_1).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_1).substring(6,8),16));
		wxr_colors[2] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_2).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_2).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_2).substring(6,8),16));
		wxr_colors[3] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_3).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_3).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_3).substring(6,8),16));
		wxr_colors[4] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_4).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_4).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_4).substring(6,8),16));
		wxr_colors[5] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_5).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_5).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_5).substring(6,8),16));
		wxr_colors[6] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_6).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_6).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_6).substring(6,8),16));
		wxr_colors[7] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_7).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_7).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_7).substring(6,8),16));
		wxr_colors[8] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_8).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_8).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_8).substring(6,8),16));
		wxr_colors[9] = new Color(
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_9).substring(2,4),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_9).substring(4,6),16),
						Integer.valueOf(this.preferences.get_preference(ZHSIPreferences.WXR_COLOR_9).substring(6,8),16));
	}
	
	public void renderWeather(Graphics2D g2, float pixels_per_nm, float max_range, float map_up, float map_center_x, float map_center_y, float scaling_factor, int image, boolean ctr) {

		if(image == 1) {
			weather1.clearRect(0, 0, 1029, 1011);
		}else {
			weather2.clearRect(0, 0, 1029, 1011);
		}
		
		this.center_lat = this.xpd.latitude();
		this.center_lon = this.xpd.longitude();
		this.map_projection.setCenter(1029 / 2, 1011 / 2);
		this.map_projection.setAcf(this.center_lat, this.center_lon);
		this.map_projection.setScale(pixels_per_nm);
		
		float delta_lat = max_range * CoordinateSystem.deg_lat_per_nm();
		float delta_lon = max_range * CoordinateSystem.deg_lon_per_nm(this.center_lat);
        float lat_max = this.center_lat + delta_lat * 1f;
        float lat_min = this.center_lat - delta_lat * 1f;
        float lon_max = this.center_lon + delta_lon * 1f;
        float lon_min = this.center_lon - delta_lon * 1f;
		float detail = 250f;
		int size = (int) (10 * scaling_factor);
		boolean fine = true;
		if(this.preferences.get_preference(ZHSIPreferences.PREF_WEATHER_DETAIL).equals("medium")) {
			detail = 250f / 2;
			size = (int) (15 * scaling_factor);
			fine = false;
		}else if(this.preferences.get_preference(ZHSIPreferences.PREF_WEATHER_DETAIL).equals("low")) {
			detail = 250f / 3;
			size = (int) (20 * scaling_factor);
			fine = false;
		}
        float lat_step = (lat_max - lat_min) / detail;
        float lon_step = (lon_max - lon_min) / detail;
		float gain = 1.2f;
		
		float storm_level;
		
		for (float lat=lat_min; lat<= lat_max; lat+=lat_step) {
			for (float lon=lon_min; lon<=lon_max; lon+=lon_step) {

				if (fine)
            		storm_level = Math.min(9.0f, gain * weather_repository.get_interpolated_storm_level(lat, lon));
            	else
            		storm_level = Math.min(9.0f, gain * weather_repository.get_storm_level(lat, lon));
				if(image == 1) {
					weather1.setColor(weather_color(storm_level));
				}else {
					weather2.setColor(weather_color(storm_level));
				}
				
				map_projection.setPoint(lat, lon);
            	int x = (int) map_projection.getX();
            	int y = (int) map_projection.getY();
            	
            	if (storm_level>0) {
            		if(image == 1) {
            			weather1.fillRect(x, y, size, size);
            		}else {
            			weather2.fillRect(x, y, size, size);
            		}
            		
            	}
			}
		}
//		File ouputfile = new File("weather.png");
//		try {
//			ImageIO.write(img_weather1, "png", ouputfile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	public Color weather_color(float storm_level) {
		/*
		 * Error codes :
		 * -1 : slice not loaded
		 * -2 : area not found
		 * -3 :
		 */
		if (storm_level > 0 && storm_level < 10.0) {
			return wxr_colors[(int)storm_level];
		} else if (storm_level == -1) {
			return colorSliceNotLoaded;
		} else if (storm_level == -2) {
			return colorAreaNotFound;
		} else 
			return colorBackGround;
	}

}