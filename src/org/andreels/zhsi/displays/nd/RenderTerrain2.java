/*
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
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.ZHSIStatus;
import org.andreels.zhsi.elevationData.ElevationRepository;
import org.andreels.zhsi.navdata.Airport;
import org.andreels.zhsi.navdata.CoordinateSystem;
import org.andreels.zhsi.navdata.NavigationObjectRepository;
import org.andreels.zhsi.resources.LoadResources;
import org.andreels.zhsi.utils.AzimuthalEquidistantProjection;
import org.andreels.zhsi.utils.Projection;
import org.andreels.zhsi.xpdata.XPData;

public class RenderTerrain2 extends Component {
	 
	private static final long serialVersionUID = 1L;

	private LoadResources rs;
	private ElevationRepository elevRepository;
	private NavigationObjectRepository nor;
	private ZHSIPreferences preferences;
	private Projection map_projection;
	private XPData xpd;
	private String pilot;
	float center_lat;
	float center_lon;
	float map_center_x = 343f;
	float map_center_y = 139f;
	public float peak_max = 0f;
	public float peak_min = 8500f;
	public float ref_alt = 0f;
	public float terrain_max;
	public float terrain_min;
	private float nearest_elev = 0f;
	private boolean peak_mode = false;
	private float high_band;
	private float middle_band;
	private float low_band;
	AffineTransform original_at;
	Color terrain_red = new Color(0xff3300);
	Color terrain_amber1 = new Color(0xFF9242);
	Color terrain_amber2 = new Color(0xFFAD42);
	Color terrain_green  = new Color(0x33cc33);
	Rectangle2D terrain_red_rect = new Rectangle2D.Float();
	TexturePaint terrain_red_text;
	TexturePaint terrain_red_text1;
	Rectangle2D terrain_amber_rect = new Rectangle2D.Float();
	TexturePaint terrain_amber_text;
	Rectangle2D terrain_green_rect = new Rectangle2D.Float();
	TexturePaint terrain_green_text;
	Ellipse2D terrain_dots = new Ellipse2D.Float();

	public BufferedImage img_terrain1 = new BufferedImage(1029, 1011, BufferedImage.TYPE_INT_ARGB);
	public BufferedImage img_terrain2 = new BufferedImage(1029, 1011, BufferedImage.TYPE_INT_ARGB);
	Graphics2D terrain1 = img_terrain1.createGraphics();
	Graphics2D terrain2 = img_terrain2.createGraphics();

	public RenderTerrain2(XPData xpd, String pilot) {
		this.pilot = pilot;
		this.xpd = xpd;
		this.elevRepository = ElevationRepository.get_instance();
		this.map_projection = new AzimuthalEquidistantProjection();
		this.rs = LoadResources.getInstance();
		this.nor = NavigationObjectRepository.get_instance();
		this.preferences = ZHSIPreferences.getInstance();
		this.nearest_elev = this.xpd.altitude(pilot);
	}
	
	public float elevation_at_distance(float distance) {
	
		return (elevRepository.get_elevation_at_distance(this.xpd.latitude(), this.xpd.longitude(), distance, (this.xpd.track() - this.xpd.magnetic_variation())));
		
	}
	
	public void renderTerrain(Graphics2D g2, float pixels_per_nm, float max_range, float map_up, float map_center_x, float map_center_y, float scaling_factor, int image, boolean ctr) {

		nearest_elev = ZHSIStatus.nearest_airport_elev;

		this.center_lat = this.xpd.latitude();
		this.center_lon = this.xpd.longitude();
		this.map_projection.setCenter(1029 / 2, 1011 / 2);
		this.map_projection.setAcf(this.center_lat, this.center_lon);
		this.map_projection.setScale(pixels_per_nm);
		int size;
		if (ctr) {
			size = 3;
		} else {
			size = 5;
		}

		original_at = terrain1.getTransform();

		ref_alt = ref_altitude();
				
		if(this.xpd.efis_terr_on(pilot) && (ZHSIStatus.egpws_db_status.equals(ZHSIStatus.STATUS_EGPWS_DB_LOADED) || ZHSIStatus.egpws_db_status.equals(ZHSIStatus.STATUS_EGPWS_PART_LOADED))) {

			if(image == 1) {

				generateTerrain(terrain1, 1, max_range, size, ctr, scaling_factor);

			}else {

				generateTerrain(terrain2, 2, max_range, size, ctr, scaling_factor);
			}
			
			terrain_max = peak_max;
			terrain_min = peak_min;
		
			if (terrain_max < (ref_alt - 500)) { // YQ296 Terrain Display
				peak_mode = true;
				high_band = terrain_max - 20f;
				middle_band = terrain_min + (terrain_max - terrain_min) / 1.5f;
				low_band = terrain_min + (terrain_max - terrain_min) / 1.7f;
			} else {
				peak_mode = false;
			}
		}
	}
	
	
	private void generateTerrain(Graphics2D terrain, int image, float max_range, int size, boolean ctr, float scaling_factor) {

		float delta_lat = max_range * CoordinateSystem.deg_lat_per_nm();
		float delta_lon = max_range * CoordinateSystem.deg_lon_per_nm(this.center_lat);
		float multiplier = 1f;
		if(ctr) {
			multiplier = 1.3f;
		}else {
			multiplier = 1f;
		}
		float lat_max = this.center_lat + delta_lat * multiplier;
		float lat_min = this.center_lat - delta_lat * multiplier;
		float lon_max = this.center_lon + delta_lon * multiplier;
		float lon_min = this.center_lon - delta_lon * multiplier;

		float detail = 270f;
		int size2 = 2;
		int size4 = 4;
		int size5 = 5;
		int size7 = 7;
		if(this.preferences.get_preference(ZHSIPreferences.PREF_TERRAIN_DETAIL).equals("medium")) {
			detail = 140f;
			size2 = 2 * 2;
			size4 = 4 * 2;
			size5 = 5 * 2;
			size7 = 7 * 2;
		}else if(this.preferences.get_preference(ZHSIPreferences.PREF_TERRAIN_DETAIL).equals("low")) {
			detail = 100f;
			size2 = 2 * 2;
			size4 = 4 * 2;
			size5 = 5 * 2;
			size7 = 7 * 2;
		}
		float lat_step = (lat_max - lat_min) / (detail * scaling_factor);
		float lon_step = (lon_max - lon_min) / (detail * scaling_factor);

		int below_500ft_250ft_gear_down = 250;
		if(this.xpd.gear_up()) {
			below_500ft_250ft_gear_down = 500;
		}
		peak_max = 0f;
		peak_min= 8500f;

		terrain.clearRect(0, 0, 1029, 1011);

		for (float lat=lat_min; lat<= lat_max; lat+=lat_step) {
			for (float lon=lon_min; lon<=lon_max; lon+=lon_step) {
				
				float elevation_meters = elevRepository.get_elevation(lat, lon);
				
				if(elevation_meters != -500) { // -500 in meters is water/ocean - Do not show
					
					float elevation = elevation_meters * 3.2808f;
					
					map_projection.setPoint(lat, lon);
					int x = (int) map_projection.getX();
					int y = (int) map_projection.getY();

					if(CoordinateSystem.isAhead(this.center_lat, this.center_lon, lat, lon, this.xpd.track())) {
						peak_min = Math.min(peak_min, elevation);
						peak_max = Math.max(peak_max, elevation);
					}
					
					// YQ296 Terrain Display
					
					if(peak_mode) {
						
						if(elevation > high_band) {
							terrain.setColor(Color.GREEN);
							terrain.fillRect(x, y, size7, size7);
						}else if(elevation >= middle_band && elevation <= high_band) {
							terrain.setColor(Color.GREEN);
							int[] xpoints = {x, x + size4, x + size4};
							int[] ypoints = {y, y, y + size4};
							terrain.fillPolygon(xpoints, ypoints, 3);
						}else if(elevation >= low_band && elevation <= middle_band) {
							terrain.setColor(Color.GREEN);
							int[] xpoints = {x, x + size2, x + size2};
							int[] ypoints = {y, y, y + size2};
							terrain.fillPolygon(xpoints, ypoints, 3);
						}
											
					}else {

						if(elevation >= nearest_elev - 200 && elevation <= nearest_elev + 200) {
							// Terrain within 400 feet of the nearest airport runway elevation does not show
						}else if (elevation < ref_alt - 2000) {
							// Terrain more than 2,000 feet below airplane altitude does not show
						}else if(elevation >= ref_alt - 2000 && elevation < ref_alt - below_500ft_250ft_gear_down) {
							// Green : terrain 2000 feet below to 500 feet (250 feet with gear down) below the airplane's current altitude
							terrain.setColor(Color.GREEN.darker());
							int[] xpoints = {x, x + size5, x + size5};
							int[] ypoints = {y, y, y + size5};
							terrain.fillPolygon(xpoints, ypoints, 3);
						}else if(elevation >= ref_alt - below_500ft_250ft_gear_down && elevation <= ref_alt + 2000) {
							// Amber : terrain 500 feet (250 feet with gear down) below to 2000 feet above the airplane's current altitude
							terrain.setColor(Color.ORANGE);
							int[] xpoints = {x, x + size5, x + size5};
							int[] ypoints = {y, y, y + size5};
							terrain.fillPolygon(xpoints, ypoints, 3);
						}else if (elevation > ref_alt + 2000) {
							// Red : Terrain more than 2,000 feet above airplane's altitude
							terrain.setColor(Color.RED);
							int[] xpoints = {x, x + size5, x + size5};
							int[] ypoints = {y, y, y + size5};
							terrain.fillPolygon(xpoints, ypoints, 3);					
						}
					}
				}
			}
		}
		terrain.setTransform(original_at);
	}
	
	
	public float ref_altitude() {
		float alt = this.xpd.altitude(pilot);
		float vvi = this.xpd.vvi(pilot);
		return (vvi < -1000 ? alt + vvi/2 : alt);
	}


	private TexturePaint create_texture_paint(Color text_color, int size, int density) {
		BufferedImage texture_image = new BufferedImage(size,size,BufferedImage.TYPE_INT_ARGB);
		int rgb = text_color.getRGB();
		switch(density) {
		case 0:
			for(int x = 0; x < size; x++) {
				for(int y = (x%2); y < size; y+=2) {
					texture_image.setRGB(x, y, rgb);
				}
			}
			break;
		case 1:
			for(int x = 0; x < size; x+=2) {
				for(int y = (x%2); y < size; y+=2) {
					texture_image.setRGB(x, y, rgb);
				}
			}
			break;
		default:
			for(int x = 0; x < size; x+=4) {
				for(int y = 2; y < size; y+=4) {
					texture_image.setRGB(x, y, rgb);
				}
			}
			break;
		}
		TexturePaint paint = new TexturePaint(texture_image, new Rectangle(0,0,size,size));
		return paint;
	}
}
