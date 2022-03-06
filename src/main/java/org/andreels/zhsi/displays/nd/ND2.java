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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.ZHSIStatus;
import org.andreels.zhsi.displays.DUBaseClass;
import org.andreels.zhsi.navdata.Airport;
import org.andreels.zhsi.navdata.CoordinateSystem;
import org.andreels.zhsi.navdata.Fix;
import org.andreels.zhsi.navdata.NavigationObject;
import org.andreels.zhsi.navdata.NavigationObjectRepository;
import org.andreels.zhsi.navdata.NavigationRadio;
import org.andreels.zhsi.navdata.RadioNavBeacon;
import org.andreels.zhsi.navdata.RadioNavigationObject;
import org.andreels.zhsi.navdata.Runway;
import org.andreels.zhsi.utils.AzimuthalEquidistantProjection;
import org.andreels.zhsi.utils.Projection;
import org.andreels.zhsi.utils.RunningAverager;

public class ND2 extends DUBaseClass {

	private static final long serialVersionUID = 1L;

	private RunningAverager turn_speed_averager = new RunningAverager(30);
	private NavigationObjectRepository nor;
	private Projection map_projection;
	private RenderTerrain2 terrain;
	private RenderWeather weather;
	
	private String peak_max_string = "";
	private String peak_min_string = "";

	private Color peak_max_color = new Color(0,0,0);
	private Color peak_min_color = new Color(0,0,0);

	private AffineTransform temp_trans;
	private AffineTransform main_map;
	private AffineTransform airport_trans;
	private AffineTransform vordme_trans;
	private AffineTransform vor_trans;
	private AffineTransform fix_trans;
	private AffineTransform fms_trans;
	private AffineTransform compassRoseTransform;

	private float scaling_factor = 0.64f;
	private float map_up;
	private float center_lon;
	private float center_lat;
	private float map_center_x = 686f / 2;
	private float map_center_y = 674f / 2;
	private float max_range;
	private float pixels_per_nm;
	private float rose_radius;
	private float pixels_per_deg_lat;
	private float pixels_per_deg_lon;
	private float track_line_rotate = 0f;
	private float terrain_cycle = 0f;
	private float weather_cycle = 0f;
	private float initial_sweep_rotate = 0f;
	private float compass_rotate = 0f;
	private float heading_bug_rotate = 0f;
	private float current_heading_bug_rotate = 0f;

	private int heading_box_offset = 0;
	private int old_map_range_value = 0;
	private int old_map_mode_value = 0;
	private int old_terr_on_value = 0;
	private int old_wxr_on_value = 0;
	private int old_map_ctr_int = 0;
	private int plane_x = 0;
	private int plane_y = 0;
	private int[] vnav_path_xpoints = {1075, 972, 1075};
	private int[] vnav_path_ypoints = {662, 662, 630};

	private float terrain_maximum = 0;

	private RadioNavBeacon nav1;
	private RadioNavBeacon nav2;
	private RadioNavigationObject nav1_object;
	private RadioNavigationObject nav2_object;
	private NavigationRadio nav1_radio;
	private NavigationRadio nav2_radio;
	private NavigationObject dest;
	private NavigationObject drawRunway_airport;
	private NavigationObject drawRunway_runway;

	private String map_range;
	private String max_range_str;
	private String tunedVOR1dme = "";
	private String tunedVOR2dme = "";
	private String tunedVOR1 = "";
	private String tunedVOR2 = "";
	private String tunedDME1 = "";
	private String tunedDME2 = "";

	private boolean app_mode = false;
	private boolean app_ctr_mode = false;
	private boolean vor_mode = false;
	private boolean vor_ctr_mode = false;
	private boolean map_mode = false;
	private boolean map_ctr_mode = false;
	private boolean pln_mode = false;
	private boolean isMapCenter = false;
	private boolean vorTo = false;
	private boolean terrain_invert = false;
	private boolean weather_invert = false;
	private boolean initial_sweep_required = false;
	private boolean egpws_loaded = false;

	private Arc2D map_clip = new Arc2D.Float();
	private Arc2D terrain_clip = new Arc2D.Float();
	private Arc2D terrain_sweep_clip = new Arc2D.Float();
	private Arc2D weather_clip = new Arc2D.Float();
	private Arc2D weather_sweep_clip = new Arc2D.Float();
	private Arc2D compass_arc1 = new Arc2D.Float();
	private Arc2D range_arc1 = new Arc2D.Float();
	private Arc2D range_arc2 = new Arc2D.Float();
	private Arc2D range_arc3 = new Arc2D.Float();
	private Arc2D pln_mode_circle1 = new Arc2D.Float();
	private Arc2D pln_mode_circle2 = new Arc2D.Float();
	private Arc2D altRangeArc = new Arc2D.Float();

	ArrayList<Point2D> waypoints = new ArrayList<Point2D>();
	ArrayList<String> waypoint_names = new ArrayList<String>();
	ArrayList<Point2D> leg_start = new ArrayList<Point2D>();
	ArrayList<Point2D> leg_end = new ArrayList<Point2D>();
	ArrayList<Point2D> mod_waypoints = new ArrayList<Point2D>();
	ArrayList<String> mod_waypoint_names = new ArrayList<String>();
	ArrayList<Point2D> mod_leg_start = new ArrayList<Point2D>();
	ArrayList<Point2D> mod_leg_end = new ArrayList<Point2D>();

	QuadCurve2D q = new QuadCurve2D.Float();

	private float[] heading_line_dash = {20f,50f};
	private Stroke heading_bug_line = new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, heading_line_dash, 0.0f);
	private float[] vsd_line_dash = {22f,22f};
	private Stroke vsd_line = new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, vsd_line_dash, 0.0f);
	private Stroke stroke2_5 = new BasicStroke(2.5f);
	private Stroke stroke3 = new BasicStroke(3f);
	private Stroke stroke4 = new BasicStroke(4f);
	private Stroke stroke5 = new BasicStroke(5f);

	public ND2(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);
		this.nor = NavigationObjectRepository.get_instance();
		this.map_projection = new AzimuthalEquidistantProjection(this.pilot);
		terrain = new RenderTerrain2(this.xpd, this.pilot);
		weather = new RenderWeather(this.xpd, this.pilot);
		if(ZHSIStatus.egpws_db_status.equals(ZHSIStatus.STATUS_EGPWS_DB_LOADED) || ZHSIStatus.egpws_db_status.equals(ZHSIStatus.STATUS_EGPWS_PART_LOADED)) {
			egpws_loaded = true;
		}else {
			egpws_loaded = false;
		}
		
	}

	public void drawInstrument(Graphics2D g2) {
		
		updateStuff();
			
		if(this.xpd.power_on()) {
			
			if ((this.pilot == "cpt" && this.xpd.power_on()) || (this.pilot == "fo" && this.xpd.dc_standby_on())) {

				if(this.xpd.irs_aligned()) {

					if(this.map_mode || this.map_ctr_mode || this.vor_mode || this.app_mode) {
						drawTerrain();
					}

					if(this.xpd.efis_wxr_on(pilot) && ZHSIStatus.weather_receiving && (this.map_mode || this.map_ctr_mode || this.vor_mode || this.app_mode)) {
						drawWeather();
					}

					drawMap();
					
					showTunedNavAids();
					
				}

				//track line above map, but below FMC route
				if(this.xpd.irs_aligned()) {
					drawTrackLine();
				}

				if(this.xpd.irs_aligned() && (this.map_mode || this.map_ctr_mode || this.pln_mode)) {
					
					if (this.xpd.vnav_idx() >= 2) {						
						drawActiveRoute();
					}
					
					if((this.xpd.legs_mod_active() && this.xpd.mod_vnav_idx() >= 2) || (!this.xpd.fpln_active() && this.xpd.mod_vnav_idx() >= 2)) {
						drawModRoute();
					}

				}
				
				if(this.xpd.irs_aligned()) {
					displayRunways();
				}

				if(this.xpd.tcas_show(pilot)) {
					displayTCASTraffic();
				}

				if(this.xpd.nd_vert_path() && (this.map_mode || this.map_ctr_mode)) {
					displayNdVnavPath();
				}

				if(this.xpd.irs_aligned()) {
					drawTopLeft();
					drawTopRight();
				}
				
				if(this.xpd.irs_aligned()) {
					drawCompassRose();
				}

				if(this.xpd.irs_aligned()) {
					drawVorAdf();
				}
				
				if(this.xpd.irs_aligned() && this.max_range <= 80 && (this.map_mode || this.map_ctr_mode || this.vor_mode || this.app_mode) && this.xpd.tcas_on(pilot)) {
					drawTcasRings();
				}
				
				drawForeGround();
				
				if(this.xpd.efis_vsd_map(pilot)) {
					drawVSD();
				}		
			}
		}
	}

	private void drawTcasRings() {
				
		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(rs.color_markings);
		g2.setStroke(rs.stroke2_5);
		g2.setTransform(original_trans);
		
		int smallCircleSize = 0;
		int bigCircleSize = 0;
		
		if(this.max_range == 80) {
			bigCircleSize = (int) (0.53f * this.pixels_per_nm);
			smallCircleSize =  (int) (0.5f * this.pixels_per_nm);
		}else if(this.max_range == 40) {
			bigCircleSize = (int) (0.8f * this.pixels_per_nm);
			smallCircleSize =  (int) (0.55f * this.pixels_per_nm);
		}else if(this.max_range == 20) {
			bigCircleSize = (int) (0.5f * this.pixels_per_nm);
			smallCircleSize =  (int) (0.35f * this.pixels_per_nm);
		}else if(this.max_range == 10) {
			bigCircleSize = (int) (0.3f * this.pixels_per_nm);
			smallCircleSize =  (int) (0.2f * this.pixels_per_nm);
		}else {
			bigCircleSize = (int) (0.15f * this.pixels_per_nm);
			smallCircleSize =  (int) (0.095f * this.pixels_per_nm);
		}
		
		g2.translate(this.map_center_x, this.map_center_y);
		g2.rotate(Math.toRadians(current_heading_bug_rotate), 0, 0);



		for(int i = 0; i < 360; i += 30) {
			
			if(i % 90 == 0) {
				g2.fillOval(bigCircleSize / -2,  (int)(-3f * this.pixels_per_nm)+ (bigCircleSize / -2), bigCircleSize, bigCircleSize);
			}else {
				g2.fillOval(smallCircleSize / -2,  (int)(-3f * this.pixels_per_nm)+ (smallCircleSize / -2), smallCircleSize, smallCircleSize);
			}
			g2.rotate(Math.toRadians(30f), 0,0);
		}
		
		g2.setTransform(original_trans);
		
	}
	
	private void drawVSD() {
		
		g2.scale(gc.scalex, gc.scaley);
		
		int y_max = 695;
		int y_ground_lvl = 930;
			
		float ref_alt = this.xpd.altitude(pilot);
		
		// background
		
		g2.setStroke(rs.stroke6);
		g2.translate(536, 650);
		g2.setColor(Color.BLACK);
		g2.fillRect(-370, 0, 740, 350);
		g2.setColor(rs.color_instrument_gray);
		g2.drawRect(-370, 0, 740, 350);
		g2.fillRect(-370, 50, 150, 300);
		g2.fillRect(-370, 290, 740, 60);
		
		// mcp alt
		
		g2.setColor(rs.color_magenta);
		g2.drawString(gc.df3.format(this.xpd.mcp_alt() % 1000), -300, 40);
		int ap1000 = this.xpd.mcp_alt() / 1000;
		if (ap1000 > 9 ) {
			g2.drawString("" + ap1000, -338, 40);
		}else {
			g2.drawString("" + ap1000, -320, 40);
		}
		
		int width_scale_pixels = 480;
		double factor_x = 0;
		
		switch (xpd.map_range(pilot)) {
			case 0:
				factor_x = width_scale_pixels / 2.5;
				break;
			case 1:
				factor_x = width_scale_pixels / 5;
				break;
			case 2:
				factor_x = width_scale_pixels / 10;
				break;
			case 3:
				factor_x = width_scale_pixels / 20;
				break;
			case 4:
				factor_x = width_scale_pixels / 40;
				break;
			case 5:
				factor_x = width_scale_pixels / 80;
				break;
			case 6:
				factor_x = width_scale_pixels / 160;
				break;
			case 7:
				// factor_x = width_scale_pixels / 320;
				// for some reason the above calculation does not work correctly so hard coded
				factor_x = 1.50;
				break;
		}
		
		// distance scale
		
		g2.setStroke(rs.stroke4);
		g2.setColor(rs.color_markings);
		g2.drawLine(-125, 295, -125, 315);
		g2.drawLine(-5, 295, -5, 305);
		g2.drawLine(115, 295, 115, 315);
		g2.drawLine(235, 295, 235, 305);
		g2.drawLine(355, 295, 355, 315);
				
		g2.setTransform(original_trans);
				
		// distance scale text
		
		gc.drawText2("0", 410, 55f, 26f, 0, rs.color_markings, false, "center", g2);
		gc.drawText2(this.map_range, 650, 55f, 26f, 0, rs.color_markings, false, "center", g2);
		gc.drawText2(this.max_range_str, 890, 55f, 26f, 0, rs.color_markings, false, "center", g2);
		
		g2.scale(gc.scalex, gc.scaley);
		
		// height scale

		int height_scale_1_3 = 0;
		int height_scale_2_3 = 0;
		float height_scale_max = 0f;
		float height_scale_pixels = 240f;

		if (terrain_maximum <= 3000 && ref_alt <= 3000) {
			height_scale_1_3 = 1000;
			height_scale_2_3 = 2000;
			height_scale_max = 3000f;
		} else if (terrain_maximum <= 6000 && ref_alt <= 6000) {
			height_scale_1_3 = 2000;
			height_scale_2_3 = 4000;
			height_scale_max = 6000f;
		} else if (terrain_maximum <= 12000 && ref_alt <= 12000) {
			height_scale_1_3 = 4000;
			height_scale_2_3 = 8000;
			height_scale_max = 12000f;
		} else if (terrain_maximum <= 24000 && ref_alt <= 24000) {
			height_scale_1_3 = 8000;
			height_scale_2_3 = 16000;
			height_scale_max = 24000f;
		} else {
			height_scale_1_3 = 16000;
			height_scale_2_3 = 32000;
			height_scale_max = 48000f;
		}
		
		float factor = height_scale_pixels / height_scale_max;
		
		// terrain colours
		
		float elev_red = ref_alt + 2000;
		float elev_orange = ref_alt - 500;
		if (elev_orange <= 0.0f)
			elev_orange = 0.0f;
		float elev_green = ref_alt - 2000;
		if (elev_orange <= 0.0f)
			elev_orange = 0.0f;
		
		// alt bug
		
		int alt_bug_y = (int) (y_ground_lvl - Math.round(this.xpd.mcp_alt() * factor));

		if (alt_bug_y < y_max) { 
			alt_bug_y = y_max;  
		} else if (alt_bug_y > y_ground_lvl) {
			alt_bug_y = y_ground_lvl;
		}
				
		// mcp alt bug
		
		g2.setStroke(rs.stroke4);
		g2.setColor(rs.color_magenta);
		
		// bug
		
		int[] alt_x = {305, 320, 320, 305, 305, 317, 305};
		int[] alt_y = {alt_bug_y - 15, alt_bug_y - 15, alt_bug_y + 15, alt_bug_y + 15, alt_bug_y + 12, alt_bug_y, alt_bug_y - 12};
		g2.drawPolygon(alt_x, alt_y, 7);
		
		// bug line
		
		g2.setStroke(rs.stroke5dash1530);
		g2.drawLine(320, alt_bug_y, 900, alt_bug_y);
		
		// baro minimums
		
		if (this.xpd.minimums_mode(pilot) == 1 && this.xpd.baro_min_show(pilot) == 1) {
			
			int baro_bug_y = (int) (y_ground_lvl - Math.round(this.xpd.baro_min(pilot) * factor));
			
			if (baro_bug_y < y_max) { 
				baro_bug_y = y_max;  
			} else if (baro_bug_y > y_ground_lvl) {
				baro_bug_y = y_ground_lvl;
			}
			
			g2.setStroke(rs.stroke4);
			if(this.xpd.altitude(pilot) < this.xpd.baro_min(pilot) && this.xpd.vvi(pilot) < -20f) {
				g2.setColor(rs.color_amber);
			} else {
				g2.setColor(rs.color_lime);
			}
			 
			// bug 
			 
			int[] baro_x = {305, 320, 305};
			int[] baro_y = {baro_bug_y - 15, baro_bug_y, baro_bug_y + 15};
			g2.drawPolygon(baro_x, baro_y, 3);
			
			// bug line
			
			g2.setStroke(rs.stroke5dash1530);
			g2.drawLine(320, baro_bug_y, 900, baro_bug_y);
			
		}

		// plane
		
		int alt_plane_y = (int) (y_ground_lvl - (Math.round(ref_alt * factor)));
		
		int plane_x[] = {340, 340, 405};
		int plane_y[] = {(int)alt_plane_y, (int)(alt_plane_y - 25), (int)alt_plane_y};
		g2.setColor(Color.BLACK);
		g2.fillPolygon(plane_x, plane_y, 3);
		g2.setStroke(rs.stroke4);
		g2.setColor(rs.color_markings);
		g2.drawPolygon(plane_x, plane_y, 3);
		
		// mcp selected vertical speed
		
		double theta_x = 0;
		double theta_y = 0;
		
		double theta = 0;
		int opposite = 0;
		int hypotenuse = 0;
		
		if (this.xpd.vs_mode() & alt_plane_y <= y_ground_lvl) {
			g2.setColor(rs.color_magenta);
			g2.setStroke(rs.stroke5dash10);
			
			if (this.xpd.groundspeed() > 100)
				theta_x = this.xpd.groundspeed() / 60 * factor_x; // nm per minute in pixels
			else
				theta_x = 100 / 60 * factor_x; // nm per minute in pixels
			theta_y = this.xpd.mcp_vvi() * factor; // feet per minute in pixels
			
			if (theta_x == 0f)
				theta = 0;
			else
				theta = Math.toDegrees(Math.atan(theta_y / theta_x)) * -1;
			
			if (theta <= 0)
				opposite = 660 - alt_plane_y;
			else 
				opposite = 935 - alt_plane_y;

			hypotenuse = (int) (opposite / Math.sin(Math.toRadians(theta)));
			if (hypotenuse < 0)
				hypotenuse = hypotenuse * -1;
			if (hypotenuse > width_scale_pixels)
				hypotenuse = width_scale_pixels;

		    g2.rotate(Math.toRadians(theta), 405, alt_plane_y);
			g2.drawLine(405, alt_plane_y, 405 + hypotenuse, alt_plane_y);
			g2.rotate(Math.toRadians(theta * -1), 405, alt_plane_y);
		}

		g2.setStroke(rs.stroke4);
		g2.setColor(rs.color_markings);
  
		// vertical flight path vector
		
		if (this.xpd.groundspeed() > 100)
			theta_x = this.xpd.groundspeed() / 60 * factor_x; // nm per minute in pixels
		else
			theta_x = 100 / 60 * factor_x; // nm per minute in pixels
		theta_y = this.xpd.vvi(pilot) * factor; // feet per minute in pixels
		
		if (theta_x == 0f)
			theta = 0;
		else
			theta = Math.toDegrees(Math.atan(theta_y / theta_x)) * -1;
		
		if (theta <= 0)
			opposite = 660 - alt_plane_y;
		else 
			opposite = 935 - alt_plane_y;

		hypotenuse = (int) (opposite / Math.sin(Math.toRadians(theta)));
		if (hypotenuse < 0)
			hypotenuse = hypotenuse * -1;
		if (hypotenuse > ((width_scale_pixels / 2) - 10))
			hypotenuse = ((width_scale_pixels / 2) - 10);		
		
		if (alt_plane_y <= y_ground_lvl) {
			g2.rotate(Math.toRadians(theta), 405, alt_plane_y);
			g2.drawLine(405, alt_plane_y, 405 + hypotenuse, alt_plane_y);
			g2.rotate(Math.toRadians(theta * -1), 405, alt_plane_y);		
		}
		
		g2.setTransform(original_trans);
		g2.scale(gc.scalex, gc.scaley);
			
		// alt tape
		
		g2.drawLine(305, y_ground_lvl, 315, y_ground_lvl);
		g2.drawString("" + 0, 195f, y_ground_lvl + 9f);
		g2.drawLine(305, y_ground_lvl - 40, 315, y_ground_lvl - 40);
		g2.drawLine(305, y_ground_lvl - 80, 315, y_ground_lvl - 80);
		g2.drawString("" + height_scale_1_3, 195f, y_ground_lvl - 80 + 9f);
		g2.drawLine(305, y_ground_lvl - 120, 315, y_ground_lvl - 120);
		g2.drawLine(305, y_ground_lvl - 160, 315, y_ground_lvl - 160);
		g2.drawString("" + height_scale_2_3, 195f, y_ground_lvl - 160 + 9f);
		g2.drawLine(305, y_ground_lvl - 200, 315, y_ground_lvl - 200);
				
		// draw terrain
		
		int spacing = 12;
		int x_begin = 412;
		int targets = 40;
		int x_end = x_begin + spacing;
		float my_range = 0f;
		float increase = this.max_range / targets;
		float elev_at_dist_1 = 0;
		float elev_at_dist_2 = 0;
		boolean first = true;
		
		if (egpws_loaded) {
			g2.setStroke(rs.stroke2);
			g2.setColor(Color.GREEN.darker());
			terrain_maximum = 0;
			elev_at_dist_1 = terrain.elevation_at_distance(my_range) * 3.28084f; 
			if (elev_at_dist_1 > terrain_maximum)
				terrain_maximum = elev_at_dist_1;
			if (elev_at_dist_1 < -1500) // water
				elev_at_dist_1 = 0;
		
			for (int cnt = 0; cnt < targets; cnt++) {
				
				my_range = my_range + increase;
				elev_at_dist_2 = terrain.elevation_at_distance(my_range) * 3.28084f;
				
				if (elev_at_dist_2 > terrain_maximum)
					terrain_maximum = elev_at_dist_2;
				
				if (elev_at_dist_2 < -1500) // water
					elev_at_dist_2 = 0;
				
				if (first) {
					drawVSDTerrainSlope(320, elev_at_dist_1, x_begin, elev_at_dist_1, elev_red, elev_orange, elev_green, y_ground_lvl, factor);
					drawVSDTerrainVertical(366, 0.0f, 366, elev_at_dist_1, elev_red, elev_orange, elev_green, y_ground_lvl, factor);
					first = false;
				}
				
				drawVSDTerrainSlope(x_begin, elev_at_dist_1, x_end, elev_at_dist_2, elev_red, elev_orange, elev_green, y_ground_lvl, factor);
				if (cnt % 5 == 0) {
					drawVSDTerrainVertical(x_begin, 0.0f, x_begin, elev_at_dist_1, elev_red, elev_orange, elev_green, y_ground_lvl, factor);
				}
									
				x_begin = x_begin + spacing;
				x_end = x_end + spacing;
				elev_at_dist_1 = elev_at_dist_2;
			
			}
			
			drawVSDTerrainVertical(x_begin, 0.0f, x_begin, elev_at_dist_1, elev_red, elev_orange, elev_green, y_ground_lvl, factor);
			
		}
		
		// draw waypoint altitude constraints
		
		boolean found_act_wpt = false;
		
		float constraint_nm = 0.0f;
		int constraint_x = 0;
		int constraint_rest1_y = 0;
		int constraint_rest2_y = 0;
		String constraint_string = "";
		
		if (this.xpd.vnav_idx() >= 2) {
		
			for (int i = this.xpd.vnav_idx() - 2; i < this.xpd.num_of_wpts(); i++) {
													
				if (this.xpd.waypoints()[i].equals(this.xpd.active_waypoint())) {					
					found_act_wpt = true;
					g2.setColor(rs.color_magenta);					
				} else {					
					g2.setColor(rs.color_markings);					
				} 
				
				if (found_act_wpt) {
					if (this.xpd.waypoints()[i].equals(this.xpd.active_waypoint())) {
						constraint_nm = constraint_nm +	this.xpd.fpln_nav_id_dist();
					} else {
						constraint_nm = constraint_nm + this.xpd.legs_dist()[i];
					}
				}
				
				if (constraint_nm * factor_x > width_scale_pixels) {
					break;
				}
				
				if (found_act_wpt && constraint_nm > 0.0 && this.xpd.legs_dist()[i] > 0.0 && (this.xpd.legs_alt_rest1()[i] > 0.0 || this.xpd.legs_alt_rest2()[i] > 0.0)) {
					
					constraint_x = (int) (405 + (constraint_nm * (float) (factor_x)));
					constraint_rest1_y = (int) (y_ground_lvl - (this.xpd.legs_alt_rest1()[i] * factor));
					constraint_rest2_y = (int) (y_ground_lvl - (this.xpd.legs_alt_rest2()[i] * factor));
					
					g2.setFont(rs.glassFont.deriveFont(20f));
					
					constraint_string = this.xpd.waypoints()[i];
										
					g2.drawString(constraint_string, constraint_x - (constraint_string.length() * 6), y_max - 20);
					
					g2.setStroke(rs.stroke5dash10);
					
					if (this.xpd.legs_alt_rest_type()[i] == 3.0) {
						g2.drawLine(constraint_x, y_max + 25, constraint_x, y_ground_lvl);
					} else {
						g2.drawLine(constraint_x, y_max + 5, constraint_x, y_ground_lvl);
					}
					
					g2.setStroke(rs.stroke2);
					
					if (this.xpd.legs_alt_rest_type()[i] == 0.0 || this.xpd.legs_alt_rest_type()[i] == 4.0) {
						constraint_string = "" + (int) this.xpd.legs_alt_rest1()[i];
						g2.drawString(constraint_string, constraint_x - (constraint_string.length() * 6), y_max);
					}
					
					if (this.xpd.legs_alt_rest_type()[i] == 1.0) {
						constraint_string = "" + (int) this.xpd.legs_alt_rest1()[i] + "B";
						g2.drawString(constraint_string, constraint_x - (constraint_string.length() * 6), y_max);
					}
					
					if (this.xpd.legs_alt_rest_type()[i] == 2.0) {
						constraint_string = "" + (int) this.xpd.legs_alt_rest1()[i] + "A";
						g2.drawString(constraint_string, constraint_x - (constraint_string.length() * 6), y_max);
					}
					
					if (this.xpd.legs_alt_rest_type()[i] == 3.0) {
						constraint_string = "" + (int) this.xpd.legs_alt_rest2()[i] + "B";
						g2.drawString(constraint_string, constraint_x - (constraint_string.length() * 6), y_max);
					}
					
					if (this.xpd.legs_alt_rest_type()[i] == 3.0) {
						constraint_string = "" + (int) this.xpd.legs_alt_rest1()[i] + "A";
						g2.drawString(constraint_string, constraint_x - (constraint_string.length() * 6), y_max + 20);
					}
					
					if (this.xpd.legs_alt_rest1()[i] > 0.0 && this.xpd.legs_alt_rest1()[i] <= height_scale_max && (this.xpd.legs_alt_rest_type()[i] == 0.0 || this.xpd.legs_alt_rest_type()[i] == 4.0)) {						
						
						// below
						int rest1_x[] = {constraint_x, constraint_x - 10, constraint_x + 10};
						int rest1_y[] = {constraint_rest1_y, constraint_rest1_y - 10, constraint_rest1_y - 10};
						g2.drawPolygon(rest1_x, rest1_y, 3);
						
						// above 						
						int rest2_x[] = {constraint_x, constraint_x - 10, constraint_x + 10};
						int rest2_y[] = {constraint_rest1_y, constraint_rest1_y + 10, constraint_rest1_y + 10};
						g2.drawPolygon(rest2_x, rest2_y, 3);
						
					}
					
					if (this.xpd.legs_alt_rest1()[i] > 0.0 && this.xpd.legs_alt_rest1()[i] <= height_scale_max && this.xpd.legs_alt_rest_type()[i] == 1.0) {						
						
						// below
						int rest1_x[] = {constraint_x, constraint_x - 10, constraint_x + 10};
						int rest1_y[] = {constraint_rest1_y, constraint_rest1_y - 10, constraint_rest1_y - 10};
						g2.drawPolygon(rest1_x, rest1_y, 3);
						
					}
					
					if (this.xpd.legs_alt_rest1()[i] > 0.0 && this.xpd.legs_alt_rest1()[i] <= height_scale_max && this.xpd.legs_alt_rest_type()[i] == 2.0) {						
						
						// above 						
						int rest1_x[] = {constraint_x, constraint_x - 10, constraint_x + 10};
						int rest1_y[] = {constraint_rest1_y, constraint_rest1_y + 10, constraint_rest1_y + 10};
						g2.drawPolygon(rest1_x, rest1_y, 3);
						
					}
					
					if (this.xpd.legs_alt_rest2()[i] > 0.0 && this.xpd.legs_alt_rest2()[i] <= height_scale_max && this.xpd.legs_alt_rest_type()[i] == 3.0) {						
						
						// below
						int rest1_x[] = {constraint_x, constraint_x - 10, constraint_x + 10};
						int rest1_y[] = {constraint_rest2_y, constraint_rest2_y - 10, constraint_rest2_y - 10};
						g2.drawPolygon(rest1_x, rest1_y, 3);
					
					}
						
					if (this.xpd.legs_alt_rest1()[i] > 0.0 && this.xpd.legs_alt_rest1()[i] <= height_scale_max && this.xpd.legs_alt_rest_type()[i] == 3.0) {
						
						// above 						
						int rest2_x[] = {constraint_x, constraint_x - 10, constraint_x + 10};
						int rest2_y[] = {constraint_rest1_y, constraint_rest1_y + 10, constraint_rest1_y + 10};
						g2.drawPolygon(rest2_x, rest2_y, 3);
						
					}
			
				}
				
			}
				
		}
		
		//

		g2.setTransform(original_trans);
		g2.setClip(original_clipshape);
		
	}
	
	
	private void drawVSDTerrainVertical(int x1, float elev_at_dist_1, int x2, float elev_at_dist_2, float elev_red, float elev_orange, float elev_green, int y_ground_lvl, float factor) {
				
		if (elev_at_dist_2 > elev_red) {
			g2.setColor(Color.RED);
			g2.drawLine(x1, (int)(y_ground_lvl - (elev_red * factor)), x2, (int)(y_ground_lvl - (elev_at_dist_2 * factor)));
		}
		if (elev_at_dist_2 > elev_orange) {
			g2.setColor(Color.ORANGE);
			if (elev_at_dist_2 > elev_red)
				g2.drawLine(x1, (int)(y_ground_lvl - (elev_orange * factor)), x2, (int)(y_ground_lvl - (elev_red * factor)));
			else
				g2.drawLine(x1, (int)(y_ground_lvl - (elev_orange * factor)), x2, (int)(y_ground_lvl - (elev_at_dist_2 * factor)));
		}
		g2.setColor(Color.GREEN.darker());
		if (elev_at_dist_2 > elev_orange)
			g2.drawLine(x1, (int)(y_ground_lvl - (elev_at_dist_1 * factor)), x2, (int)(y_ground_lvl - (elev_orange * factor)));
		else
			g2.drawLine(x1, (int)(y_ground_lvl - (elev_at_dist_1 * factor)), x2, (int)(y_ground_lvl - (elev_at_dist_2 * factor)));
			
	}
	
	
	private void drawVSDTerrainSlope(int x1, float elev_at_dist_1, int x2, float elev_at_dist_2, float elev_red, float elev_orange, float elev_green, int y_ground_lvl, float factor) {
				
		if (elev_at_dist_1 > elev_red || elev_at_dist_2 > elev_red) {
			g2.setColor(Color.RED);
			g2.drawLine(x1, (int)(y_ground_lvl - (elev_at_dist_1 * factor)), x2, (int)(y_ground_lvl - (elev_at_dist_2 * factor)));
		}
		else if (elev_at_dist_1 > elev_orange || elev_at_dist_2 > elev_orange) {
			g2.setColor(Color.ORANGE);
			g2.drawLine(x1, (int)(y_ground_lvl - (elev_at_dist_1 * factor)), x2, (int)(y_ground_lvl - (elev_at_dist_2 * factor)));
		}
		else {
			g2.setColor(Color.GREEN.darker());
			g2.drawLine(x1, (int)(y_ground_lvl - (elev_at_dist_1 * factor)), x2, (int)(y_ground_lvl - (elev_at_dist_2 * factor)));
		}
	
	}
	

	private void displayRunways() {
		
		g2.clip(map_clip);
		
		main_map = g2.getTransform();
		
		if(this.xpd.ils_show(pilot)) {
			//waiting for zibo to fix
			//drawRunway(this.xpd.dest_arpt(), this.xpd.ils_runway(pilot), this.xpd.ils_x(pilot), this.xpd.ils_y(pilot), this.xpd.ils_rotate(pilot));
			drawRunway(this.xpd.dest_arpt(), this.xpd.ils_runway("cpt"), this.xpd.ils_x(pilot), this.xpd.ils_y(pilot), this.xpd.ils_rotate(pilot));
		}
		
		if(this.xpd.ils_show_mod(pilot)) {
			//waiting for zibo to fix
			//drawRunway(this.xpd.dest_arpt(), this.xpd.ils_runway_mod(pilot), this.xpd.ils_x_mod(pilot), this.xpd.ils_y_mod(pilot), this.xpd.ils_rotate_mod(pilot));
			drawRunway(this.xpd.dest_arpt(), this.xpd.ils_runway_mod("cpt"), this.xpd.ils_x_mod(pilot), this.xpd.ils_y_mod(pilot), this.xpd.ils_rotate_mod(pilot));
		}
		
		if(this.xpd.ils_show0(pilot)) {
			//waiting for zibo to fix			
			//drawRunway(this.xpd.origin_arpt(), this.xpd.ils_runway0(pilot), this.xpd.ils_x0(pilot), this.xpd.ils_y0(pilot), this.xpd.ils_rotate0(pilot));
			drawRunway(this.xpd.origin_arpt(), this.xpd.ils_runway0("cpt"), this.xpd.ils_x0(pilot), this.xpd.ils_y0(pilot), this.xpd.ils_rotate0(pilot));
		}
		
		if(this.xpd.ils_show0_mod(pilot)) {
			//waiting for zibo to fix
			//drawRunway(this.xpd.origin_arpt(), this.xpd.ils_runway0_mod(pilot), this.xpd.ils_x0_mod(pilot), this.xpd.ils_y0_mod(pilot), this.xpd.ils_rotate0_mod(pilot));	
			drawRunway(this.xpd.origin_arpt(), this.xpd.ils_runway0_mod("cpt"), this.xpd.ils_x0_mod(pilot), this.xpd.ils_y0_mod(pilot), this.xpd.ils_rotate0_mod(pilot));	
		}
				
		g2.setTransform(main_map);
		
		g2.setClip(original_clipshape);
		
	}


	private void drawRunway(String airport_in, String rwy_number, float x, float y, float rotation) {
				
		float rwy_x = zibo_x(x);
		float rwy_y = zibo_y(y);
		g2.setStroke(gc.stroke_four);
		g2.setColor(gc.color_markings);
		
		// calculate runway length in pixels
		float runway_length_in_nm = (float) (2000 / 1852); // default 2000 feet
		drawRunway_airport = nor.get_airport(airport_in);
		Airport drawRunway_apt = (Airport)drawRunway_airport;
		if (drawRunway_airport != null) {
			drawRunway_runway = nor.get_runway(airport_in, rwy_number, drawRunway_apt.lat, drawRunway_apt.lon, false);
			if(drawRunway_runway != null) {
				Runway drawRunway_rwy = (Runway)drawRunway_runway;
				runway_length_in_nm = (float) (drawRunway_rwy.length / 1852);
			}
		}
		
		// runway
		g2.rotate(Math.toRadians(rotation),rwy_x, rwy_y);
		g2.drawLine((int)rwy_x, (int)(rwy_y - (10 * gc.scaley)), (int)(rwy_x - (runway_length_in_nm * pixels_per_nm)), (int)(rwy_y - (10 * gc.scaley)));
		g2.drawLine((int)rwy_x, (int)(rwy_y + (10 * gc.scaley)), (int)(rwy_x - (runway_length_in_nm * pixels_per_nm)), (int)(rwy_y + (10 * gc.scaley)));
		
		// dashed runway centerlines extend 14.2 nm (fcom)
		g2.setStroke(gc.runway_center_line);
		g2.drawLine((int)(rwy_x - (runway_length_in_nm * pixels_per_nm)), (int)(rwy_y), (int)(rwy_x - (runway_length_in_nm * pixels_per_nm) - pixels_per_nm * 14.2f), (int)rwy_y);
		g2.drawLine((int)(rwy_x), (int)(rwy_y), (int)(rwy_x + pixels_per_nm * 14.2f), (int)rwy_y);
		
		// runway number
		g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));
		g2.drawString(rwy_number, rwy_x + (12f * gc.scalex), rwy_y - (12f * gc.scaley));
		g2.setTransform(original_trans);
		
	}
	
	
	private void displayNdVnavPath() {
				
		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(Color.BLACK); 		
		g2.fillPolygon(vnav_path_xpoints, vnav_path_ypoints, vnav_path_xpoints.length);
		g2.fillRect(972, 660, 105, 275);
		g2.setColor(gc.color_markings);
		g2.setStroke(stroke4);                                                                             
		//draw scale
		g2.drawLine(1015, 670, 1015, 880);
		//top horizontal
		g2.drawLine(990, 670, 1015, 670);
		//middle
		g2.drawLine(980, 775, 1015, 775);
		// bottom horizontal
		g2.drawLine(990, 880, 1015, 880);
		
		// diamond calculations
		float ydelta = (210 * this.xpd.vnav_err()) / 800f;

		if(ydelta > 105) {
			ydelta = 105;
		}else if(ydelta < -105) {
			ydelta = -105;
		}
		
		// deviation band 
		float band = this.xpd.vrnp();
		
		int db_y_diamond_above = (int)(775 - ydelta - 8);
		int db_y_diamond_max = (int)(775 - ydelta - ((band / 400) * 105));
		
		if (db_y_diamond_max < 669) {
			db_y_diamond_max = 669;
		}
		
		int db_y_diamond_below = (int)(775 - ydelta + 8);
		int db_y_diamond_min = (int)(775 - ydelta + ((band / 400) * 105));
		
		if (db_y_diamond_min > 881) {
			db_y_diamond_min = 881;
		}

		if (band != 0.0) {
			
			g2.setColor(gc.color_magenta);
			
			if (db_y_diamond_above > 670) {
				g2.drawLine(1035, db_y_diamond_above, 1035, db_y_diamond_max);
				
				if (db_y_diamond_max != 669) {
					g2.drawLine(1018, db_y_diamond_max, 1035, db_y_diamond_max);
				}
				
			}

			if (db_y_diamond_below < 880) {
				g2.drawLine(1035, db_y_diamond_below, 1035, db_y_diamond_min);
				
				if (db_y_diamond_min != 881) {
					g2.drawLine(1018, db_y_diamond_min, 1035, db_y_diamond_min);
				}
				
			}
			
		}
		
		g2.setTransform(original_trans);
		
		// vrnp & vanp
		if (this.xpd.vanp() > this.xpd.vrnp()) {
			gc.drawText2("RNP", 1000, 320, 28, 0, gc.color_amber, true, "right", g2);
			gc.drawText2(String.format("%3.0f", this.xpd.vrnp()), 1000, 290, 28, 0, gc.color_amber, true, "right", g2);
			gc.drawText2("ANP", 1000, 240, 28, 0, gc.color_amber, true, "right", g2);
			gc.drawText2(String.format("%3.0f", this.xpd.vanp()), 1000, 210, 28, 0, gc.color_amber, true, "right", g2);
		} else {
			gc.drawText2("RNP", 1000, 320, 28, 0, gc.color_lime, true, "right", g2);
			gc.drawText2(String.format("%3.0f", this.xpd.vrnp()), 1000, 290, 28, 0, gc.color_lime, true, "right", g2);
			gc.drawText2("ANP", 1000, 240, 28, 0, gc.color_lime, true, "right", g2);
			gc.drawText2(String.format("%3.0f", this.xpd.vanp()), 1000, 210, 28, 0, gc.color_lime, true, "right", g2);
		}
	
		// text above
		if(this.xpd.vnav_err() > 30) {
			gc.drawText2("" + (int)Math.abs(Math.round(this.xpd.vnav_err())), 1020, 400, 28, 0, gc.color_markings, true, "right", g2);	  
		}
			
		// text below
		if(this.xpd.vnav_err() < -30) {
			gc.drawText2("" + (int)Math.abs(Math.round(this.xpd.vnav_err())), 1020, 135, 28, 0, gc.color_markings, true, "right", g2);
		}
			
		// diamond
		gc.displayImage(rs.img_hdef_ind, 0, 1015 , (278 - rs.img_hdef_ind.getHeight() / 2) + ydelta, g2);
		
	}

	private void displayTCASTraffic() {

		g2.clip(map_clip);
		
		main_map = g2.getTransform();

		for(int i = 0; i < 19; i ++) {
			
			if(this.xpd.tcas_ai_show(pilot, i)) {

				if(this.xpd.tcas_type_show(pilot, i) == 1.0f) { // red box
					showTraffic(i, Color.RED, rs.img_tcas_redbox, rs.img_tcas_red_arrowdn, rs.img_tcas_red_arrowup);
				}
				if(this.xpd.tcas_type_show(pilot, i) == 2.0f) { // amber circle
					showTraffic(i, gc.color_amber, rs.img_tcas_ambercircle, rs.img_tcas_amber_arrowdn, rs.img_tcas_amber_arrowup);
				}
				if(this.xpd.tcas_type_show(pilot, i) == 3.0f) { // solid white
					showTraffic(i, gc.color_markings,rs.img_tcas_solid_diamond, rs.img_tcas_white_arrowdn, rs.img_tcas_white_arrowup);
				}
				if(this.xpd.tcas_type_show(pilot, i) == 4.0f) {
					showTraffic(i, gc.color_markings, rs.img_tcas_diamond, rs.img_tcas_white_arrowdn, rs.img_tcas_white_arrowup);
				}
			}
		}

		g2.setTransform(main_map);

		g2.setClip(original_clipshape);
		
	}

	private void showTraffic(int i, Color color, BufferedImage image, BufferedImage downArrow, BufferedImage upArrow) {

		g2.setColor(color);
		String altString;
		
		if(pilot == "cpt") {
			altString = this.xpd.tcas_alt(i);
		} else {
			altString = this.xpd.tcas_alt_fo(i);
		}
		
		float x = zibo_x(this.xpd.tcas_x(pilot, i));
		float y = zibo_y(this.xpd.tcas_y(pilot, i));

		g2.translate(x, y);

		g2.drawImage(image, (int)(0 - image.getWidth() * gc.scaling_factor / 2), (int)(0 - image.getHeight() * gc.scaling_factor / 2), (int)(image.getWidth() * gc.scaling_factor), (int)(image.getHeight() * gc.scaling_factor), null);

		if(this.xpd.tcas_arrow_dn_up_show(pilot, i) == 1) { //arrow down 
			g2.drawImage(downArrow, (int)(0 + 25f * gc.scaling_factor), (int)(0 - downArrow.getHeight() * gc.scaling_factor / 2), (int)(downArrow.getWidth() * gc.scaling_factor), (int)(downArrow.getHeight() * gc.scaling_factor), null);
		}
		
		if(this.xpd.tcas_arrow_dn_up_show(pilot, i) == 2) { //up down  {
			g2.drawImage(upArrow, (int)(0 + 25f * gc.scaling_factor), (int)(0 - upArrow.getHeight() * gc.scaling_factor / 2), (int)(upArrow.getWidth() * gc.scaling_factor), (int)(upArrow.getHeight() * gc.scaling_factor), null);
		}
		
		g2.setFont(rs.glassFont.deriveFont(28f * gc.scaling_factor));

		if(altString != null) {
			
			if(this.xpd.tcas_alt_dn_up_show(pilot, i) == 0) { // underneath
				g2.drawString(altString, 0 - 30f * gc.scaling_factor, 0 + 50f * gc.scaling_factor);
			} else {
				g2.drawString(altString, 0 - 30f * gc.scaling_factor, 0 - 30f * gc.scaling_factor);
			}
		}
		
		g2.setTransform(original_trans);

	}

	private void drawActiveRoute() {
		
		drawRoute(	true,
					this.xpd.num_of_wpts(),
					this.xpd.waypoints(),
					this.xpd.vnav_idx(),
					this.xpd.origin_arpt(),
					this.xpd.dest_arpt(),
					this.xpd.legs_lat(), 
					this.xpd.legs_lon(), 
					this.xpd.legs_type(), 
//					this.xpd.legs_alt_calc(), 
					this.xpd.legs_alt_rest1(), 
					this.xpd.legs_alt_rest2(), 
					this.xpd.legs_alt_rest_type(),
					this.xpd.legs_seg1_ctr_lat(),
					this.xpd.legs_seg1_ctr_lon(),
					this.xpd.legs_seg1_radius(),
					this.xpd.legs_seg1_turn(),
					this.xpd.legs_seg1_start_angle(),
					this.xpd.legs_seg1_end_angle(),				
					this.xpd.legs_seg3_ctr_lat(),
					this.xpd.legs_seg3_ctr_lon(),
					this.xpd.legs_seg3_radius(),
					this.xpd.legs_seg3_turn(),
					this.xpd.legs_seg3_start_angle(),
					this.xpd.legs_seg3_end_angle(),
					this.xpd.legs_af_beg(),
					this.xpd.legs_af_end(),
					this.xpd.legs_bypass(),
					this.xpd.legs_rad_lat(),
					this.xpd.legs_rad_lon(),
					this.xpd.legs_rad_turn(),
					this.xpd.legs_crs_mag(),
					this.xpd.legs_spd(),
					this.xpd.legs_hold_dist(),
					this.xpd.legs_hold_time(),
					this.xpd.legs_radius(),			
					this.xpd.legs_eta());
		
	}

	private void drawModRoute() {
		
		drawRoute(	false,
					this.xpd.mod_num_of_wpts(),
					this.xpd.mod_waypoints(),
					this.xpd.mod_vnav_idx(),
					this.xpd.mod_origin_arpt(),
					this.xpd.mod_dest_arpt(),
					this.xpd.mod_legs_lat(),
					this.xpd.mod_legs_lon(),
					this.xpd.mod_legs_type(),
//					this.xpd.mod_legs_alt_calc(),
					this.xpd.mod_legs_alt_rest1(),
					this.xpd.mod_legs_alt_rest2(),
					this.xpd.mod_legs_alt_rest_type(),
					this.xpd.legs_seg1_ctr_lat(),
					this.xpd.legs_seg1_ctr_lon(),
					this.xpd.legs_seg1_radius(),
					this.xpd.legs_seg1_turn(),
					this.xpd.legs_seg1_start_angle(),
					this.xpd.legs_seg1_end_angle(),
					this.xpd.legs_seg3_ctr_lat(),
					this.xpd.legs_seg3_ctr_lon(),
					this.xpd.legs_seg3_radius(),
					this.xpd.legs_seg3_turn(),
					this.xpd.legs_seg3_start_angle(),
					this.xpd.legs_seg3_end_angle(),
					this.xpd.legs_af_beg(),
					this.xpd.legs_af_end(),
					this.xpd.legs_bypass(),
					this.xpd.mod_legs_rad_lat(),
					this.xpd.mod_legs_rad_lon(),
					this.xpd.mod_legs_rad_turn(),
					this.xpd.mod_legs_crs_mag(),
					this.xpd.mod_legs_spd(),
					this.xpd.mod_legs_hold_dist(),
					this.xpd.mod_legs_hold_time(),
					this.xpd.legs_radius(),
					this.xpd.legs_eta());

	}				
		
	private void drawRoute(	boolean isActive,
							int num_of_wpts, 
							String[] wpts,
							int vnav_idx,
							String origin_arpt,
							String dest_arpt,
							float[] lats, 
							float[] lons, 
							float[] type, 
//							float[] alt_calc, 
							float[] alt_rest1, 
							float[] alt_rest2, 
							float[] alt_rest_type,
							float[] legs_seg1_ctr_lat,
							float[] legs_seg1_ctr_lon,
							float[] legs_seg1_radius, 
							float[] legs_seg1_turn,
							float[] legs_seg1_start_angle,
							float[] legs_seg1_end_angle,
							float[] legs_seg3_ctr_lat,
							float[] legs_seg3_ctr_lon,
							float[] legs_seg3_radius,
							float[] legs_seg3_turn,
							float[] legs_seg3_start_angle,
							float[] legs_seg3_end_angle,
							float[] legs_af_beg,
							float[] legs_af_end,
							float[] legs_bypass,
							float[] legs_rad_lat,
							float[] legs_rad_lon,
							float[] legs_rad_turn,
							float[] legs_crs_mag,
							float[] legs_spd,
							float[] legs_hold_dist,
							float[] legs_hold_time,
							float[] legs_radius,					
							float[] legs_eta) {

		g2.clip(map_clip);
				
		main_map = g2.getTransform();
		
		g2.transform(AffineTransform.getRotateInstance(Math.toRadians(map_up), map_center_x, map_center_y));
		
		//draw route

		if(vnav_idx >= 2) {
			
			float lat_start;
			float lon_start;
			float lat_end;
			float lon_end;
			int	next_idx;
			
			// af variables
			int phi = 0;
			float arc_origin_x_af = 0;
			float arc_origin_y_af = 0;
			int radius_af = 0;
			int diameter_af = 0;
			int start_angle_af = 0;
			int arc_angle_af = 0;
			float end_angle_af = 0;
			float[] latlon_af = {0,0};
			
			// seg1 variables
			float arc_origin_x = 0;
			float arc_origin_y = 0;
			float arc_end_x = 0;
			float arc_end_y = 0;
			int radius = 0;
			int diameter = 0;
			int start_angle = 0;
			int arc_angle = 0;
			float end_angle = 0;
			
			float[] legs_seg1_start_lat = new float[128];
			float[] legs_seg1_start_lon = new float[128];
			float[] legs_seg1_end_lat = new float[128];
			float[] legs_seg1_end_lon = new float[128];
			float[] latlon_seg1_start = {0,0};
			float[] latlon_seg1_end = {0,0};
			float legs_seg1_end_x = 0;
			float legs_seg1_end_y = 0;
			
			// seg3 variables
			float arc_origin_x_seg3 = 0;
			float arc_origin_y_seg3 = 0;
			int radius_seg3 = 0;
			int diameter_seg3 = 0;
			int start_angle_seg3 = 0;
			int arc_angle_seg3 = 0;
			float end_angle_seg3 = 0;
			float[] latlon_seg3 = {0,0};
			float legs_seg3_start_x = 0;
			float legs_seg3_start_y = 0;
				
			float[] legs_seg3_start_lat = new float[128];
			float[] legs_seg3_start_lon = new float[128];
			float[] legs_seg3_end_lat = new float[128];
			float[] legs_seg3_end_lon = new float[128];
			float[] latlon_seg3_start = {0,0};
			float[] latlon_seg3_end = {0,0};
			
			// dir seg1 variables
			float[] latlon_dir_seg1_end = {0,0};
			float[] latlon_dir_seg3_end = {0,0};
			float[] latlon_dir_seg3_start = {0,0};
			float dir_seg1_end_x = 0;
			float dir_seg1_end_y = 0;
			float dir_seg3_end_x = 0;
			float dir_seg3_end_y = 0;
			float dir_seg3_start_x = 0;
			float dir_seg3_start_y = 0;
			
			for(int i = 0; i < num_of_wpts - 1; i++) {
				
				if ((int)legs_seg1_turn[i] == 2 || (int)legs_seg1_turn[i] == 3) { 
					latlon_seg1_start = CoordinateSystem.latlon_at_dist_heading(legs_seg1_ctr_lat[i], legs_seg1_ctr_lon[i], legs_seg1_radius[i], legs_seg1_start_angle[i]);
					legs_seg1_start_lat[i] = latlon_seg1_start[0];
					legs_seg1_start_lon[i] = latlon_seg1_start[1];
					latlon_seg1_end = CoordinateSystem.latlon_at_dist_heading(legs_seg1_ctr_lat[i], legs_seg1_ctr_lon[i], legs_seg1_radius[i], legs_seg1_end_angle[i]);
					legs_seg1_end_lat[i] = latlon_seg1_end[0];
					legs_seg1_end_lon[i] = latlon_seg1_end[1];
				}
				
				if ((int)legs_seg3_turn[i] == 2 || (int)legs_seg3_turn[i] == 3) {
					latlon_seg3_start = CoordinateSystem.latlon_at_dist_heading(legs_seg3_ctr_lat[i], legs_seg3_ctr_lon[i], legs_seg3_radius[i], legs_seg3_start_angle[i]);
					legs_seg3_start_lat[i] = latlon_seg3_start[0];
					legs_seg3_start_lon[i] = latlon_seg3_start[1];
					latlon_seg3_end = CoordinateSystem.latlon_at_dist_heading(legs_seg3_ctr_lat[i], legs_seg3_ctr_lon[i], legs_seg3_radius[i], legs_seg3_end_angle[i]);
					legs_seg3_end_lat[i] = latlon_seg3_end[0];
					legs_seg3_end_lon[i] = latlon_seg3_end[1];
				}
				
			}
			
					
			int vnav_index = Math.max(0, vnav_idx - 2);
			
			// start should be before any bypassed waypoints
			while ((int) legs_bypass[vnav_index] == 1) {
				vnav_index = vnav_index - 1;
			}			
			vnav_index = Math.max(0, vnav_index);
				
			for(int i = vnav_index; i < num_of_wpts - 1; i++) {
				
				// calculate next idx
				next_idx = i + 1;
				while ((int) legs_bypass[next_idx] == 1) {
					next_idx = next_idx + 1;
				}
					
				// checks if there is a departing runway in the route
				if(i < 3 && wpts[i].equals(origin_arpt) && wpts[next_idx].startsWith("RW")) {
					lat_start = lats[next_idx];
					lon_start = lons[next_idx];
				}else {
					lat_start = lats[i];
					lon_start = lons[i];
				}
			
				// get the start of the leg
				map_projection.setPoint(lat_start, lon_start); 
				int x = (int) map_projection.getX();
				int y = (int) map_projection.getY();
				
				// do not draw last line to destination airport
				if (wpts[next_idx].equals(dest_arpt)) {
					lat_end = lats[i];
					lon_end = lons[i];
				} else {
					// do not draw discontinuity
					if (wpts[i].contains("DISCONTINUITY")) {
						lat_end = lats[i];
						lon_end = lons[i];
					// do not draw vector
					} else if (wpts[i].contains("VECTOR")) {
						lat_end = lats[i];
						lon_end = lons[i];
					} else {
						lat_end = lats[next_idx];
						lon_end = lons[next_idx];
					}
				}
				
				// get the end of the leg
				map_projection.setPoint(lat_end, lon_end); 
				int xx = (int) map_projection.getX();
				int yy = (int) map_projection.getY();
				
				// arc calculations

				// radii
				if (legs_seg1_radius[i] > 0.1) {
					
					// af
					if (legs_af_beg[next_idx] != 0.0 || legs_af_end[next_idx] != 0.0) {
						
						// origin of the arc
						map_projection.setPoint(legs_rad_lat[next_idx], legs_rad_lon[next_idx]);
						arc_origin_x_af = map_projection.getX();
						arc_origin_y_af = map_projection.getY();
						
						// radius in pixels per nm
						radius_af = (int)(legs_radius[next_idx] * pixels_per_nm);
						diameter_af = (int)(legs_radius[next_idx] * pixels_per_nm * 2);
						
						// calulate heading from arc origin to arc start point
						start_angle_af = 0 + (int) (90 - legs_af_beg[next_idx]);
						end_angle_af   = 0 + (int) (90 - legs_af_end[next_idx]);
			
						// calculate arc angle
						phi = (int) Math.abs(start_angle_af - end_angle_af) % 360;
						arc_angle_af = phi > 180 ? 360 - phi : phi;
						
						// calculate lat/lon at beginning of arc 
						latlon_af = CoordinateSystem.latlon_at_dist_heading(legs_rad_lat[next_idx], legs_rad_lon[next_idx], legs_radius[next_idx], legs_af_beg[next_idx]);

					}
					
					// seg1
					
					// origin of the arc
					map_projection.setPoint(legs_seg1_ctr_lat[i], legs_seg1_ctr_lon[i]); 
					arc_origin_x = map_projection.getX();
					arc_origin_y = map_projection.getY();
					
					// radius in pixels per nm
					radius = (int)(legs_seg1_radius[i] * pixels_per_nm);
					diameter = (int)(legs_seg1_radius[i] * pixels_per_nm * 2);
					
					// start & end angles
					start_angle = 0 + (int) (90 - legs_seg1_start_angle[i]);
					end_angle = 0 + (int) (90 - legs_seg1_end_angle[i]);
					
					// calculate arc angle - do not change this formula
					if ((int)legs_seg1_turn[i] == 2) // left
						arc_angle = (int)(legs_seg1_start_angle[i] - legs_seg1_end_angle[i]);
					if ((int)legs_seg1_turn[i] == 3) // right
						arc_angle = (int)(legs_seg1_end_angle[i] - legs_seg1_start_angle[i]);
					if (arc_angle < 0)
						arc_angle = arc_angle + 360;
										
					// calculate point at end of arc
					map_projection.setPoint(legs_seg1_end_lat[i], legs_seg1_end_lon[i]);
					arc_end_x = map_projection.getX();
					arc_end_y = map_projection.getY();
					
					//g2.drawString((int) start_angle + ":" + (int) end_angle + ":" + arc_angle, arc_origin_x, arc_origin_y);
					
					// seg 3
					if ((int)legs_seg3_turn[i] != 0) {
						
						// origin of the arc
						map_projection.setPoint(legs_seg3_ctr_lat[i], legs_seg3_ctr_lon[i]); 
						arc_origin_x_seg3 = map_projection.getX();
						arc_origin_y_seg3 = map_projection.getY();
						
						// radius in pixels per nm
						radius_seg3 = (int)(legs_seg3_radius[i] * pixels_per_nm);
						diameter_seg3 = (int)(legs_seg3_radius[i] * pixels_per_nm * 2);
						
						// start & end angles
						start_angle_seg3 = 0 + (int) (90 - legs_seg3_start_angle[i]);
						end_angle_seg3 = 0 + (int) (90 - legs_seg3_end_angle[i]);
						
						// calculate arc angle - do not change this formula
						if ((int)legs_seg3_turn[i] == 2) // left
							arc_angle_seg3 = (int)(legs_seg3_start_angle[i] - legs_seg3_end_angle[i]);
						if ((int)legs_seg3_turn[i] == 3) // right
							arc_angle_seg3 = (int)(legs_seg3_end_angle[i] - legs_seg3_start_angle[i]);
						if (arc_angle_seg3 < 0)
							arc_angle_seg3 = arc_angle_seg3 + 360;
											
						// calculate point at end of arc seg3
						map_projection.setPoint(legs_seg3_end_lat[i], legs_seg3_end_lon[i]);
						arc_end_x = map_projection.getX(); // on purpose
						arc_end_y = map_projection.getY(); // on purpose
						
						// g2.drawString(i + ":" + legs_seg3_end_lat[i] + ":" + legs_seg3_end_lon[i] + ":" + legs_seg1_end_lat[i] + ":" + legs_seg1_end_lon[i], arc_origin_x, arc_origin_y);
					
					}
					
				// rad							
				} else if ((int)legs_rad_turn[next_idx] != -1 && legs_rad_lat[next_idx] != 0.0 && legs_rad_lon[next_idx] != 0.0) {
						
					// origin of the arc
					map_projection.setPoint(legs_rad_lat[next_idx], legs_rad_lon[next_idx]);
					arc_origin_x = map_projection.getX();
					arc_origin_y = map_projection.getY();
					
					// radius in pixels per nm
					radius = (int)(legs_radius[next_idx] * pixels_per_nm);
					diameter = (int)(legs_radius[next_idx] * pixels_per_nm * 2);
					
					// calulate heading from arc origin to arc start point
					if (legs_af_beg[next_idx] != 0.0) {
						start_angle = 0 + (int) (90 - legs_af_beg[next_idx]);
					} else {
						start_angle = 0 + (int) (90 - CoordinateSystem.heading_from_a_to_b(legs_rad_lat[next_idx], legs_rad_lon[next_idx], lats[i], lons[i]));
					}
					
					if (legs_af_end[next_idx] != 0.0) {
						end_angle = 0 + (int) (90 - legs_af_end[next_idx]);
					} else {
						end_angle = 0 + (int) (90 - CoordinateSystem.heading_from_a_to_b(legs_rad_lat[next_idx], legs_rad_lon[next_idx], lats[next_idx], lons[next_idx]));
					}
			
					// calculate arc angle
					phi = (int) Math.abs(start_angle - end_angle) % 360;
					arc_angle = phi > 180 ? 360 - phi : phi;
												
				}
				
				// decide what color to draw with

				if(isActive) {
					//set color and stroke
					if(this.xpd.fpln_active()) {
						g2.setStroke(this.stroke3);
						g2.setColor(gc.color_magenta);

						if(i >= this.xpd.missed_app_wpt_idx() - 2 && this.xpd.missed_app_wpt_idx() > 1) {
							float[] inactive_line_dash = {20f * gc.scaling_factor, 20f * gc.scaling_factor};
							g2.setStroke(new BasicStroke(4f * gc.scaling_factor, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 20.0f * gc.scaling_factor, inactive_line_dash, 0.0f));
							g2.setColor(gc.color_navaid);
						}
					}else {
						g2.setStroke(this.stroke3);
						g2.setColor(gc.color_markings);
					}
					
				} else {
					//set color and stroke
					float[] inactive_line_dash = {20f * gc.scaling_factor, 20f * gc.scaling_factor};
					g2.setStroke(new BasicStroke(4f * gc.scaling_factor, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 20.0f * gc.scaling_factor, inactive_line_dash, 0.0f));

					if(this.xpd.fpln_active()) {
						g2.setColor(gc.color_markings);
					}else {
						g2.setColor(gc.color_navaid);
					}
				}
				
				//draw hold
				
				if (((int)legs_rad_turn[i] == 0 || (int)legs_rad_turn[i] == 1) && i > vnav_idx - 2) {
					if ((i == vnav_idx && this.xpd.vnav_decel_hold_dist() == 0.0) || // ie decel point
						(i == vnav_idx - 1)) { 
						drawHold(legs_rad_turn[i], legs_crs_mag[i], legs_hold_dist[i], legs_hold_time[i], legs_spd[i], lats[i], lons[i], 1);
					} else {
						drawHold(legs_rad_turn[i], legs_crs_mag[i], legs_hold_dist[i], legs_hold_time[i], legs_spd[i], lats[i], lons[i], 0);
					}
				}
				
				//draw legs

				if(isActive) {
					
					if ((int) legs_bypass[i] != 1) {
						
						// first leg of a direct
						
						// if(this.xpd.intdir_act() && i == vnav_idx - 2) { 
						if(this.xpd.intdir_act() && i == vnav_index) { 
						
							if (legs_seg1_radius[vnav_idx - 1] > 0.1) {
								map_projection.setPoint(legs_seg1_start_lat[vnav_idx - 1], legs_seg1_start_lon[vnav_idx - 1]);
							} else {
								map_projection.setPoint(lats[vnav_idx - 1], lons[vnav_idx - 1]);
							}
							xx = (int) map_projection.getX();
							yy = (int) map_projection.getY();
						
							// intersection direct
							if(this.xpd.intdir_crs() != -1) {
								
								//get point based on FMS track and distance
								float track = (this.xpd.fms_track() - 180) % 360;
								float[] latlon = CoordinateSystem.latlon_at_dist_heading(lats[vnav_idx - 1], lons[vnav_idx - 1], this.max_range * 2, track);
								map_projection.setPoint(latlon[0], latlon[1]);
								int xxx = (int) map_projection.getX();
								int yyy = (int) map_projection.getY();
								
								g2.drawLine(xxx, yyy, xx, yy);									
												
							} else {
								
								// normal direct
								
								// seg1
								if (this.xpd.dir_seg1_turn() != 0) {
																	
									// radius in pixels per nm
									map_projection.setPoint(this.xpd.dir_seg1_ctr_lat(), this.xpd.dir_seg1_ctr_lon());
									arc_origin_x = map_projection.getX();
									arc_origin_y = map_projection.getY();
									
									// radius in pixels per nm
									radius = (int)(this.xpd.dir_seg1_radius() * pixels_per_nm);
									diameter = (int)(this.xpd.dir_seg1_radius() * pixels_per_nm * 2); 
									
									// start & end angles
									start_angle = 0 + (int) (90 - this.xpd.dir_seg1_start_angle());
									end_angle = 0 + (int) (90 - this.xpd.dir_seg1_end_angle());
									
									// calculate arc angle - do not change this formula
									if (this.xpd.dir_seg1_turn() == 2) // left
										arc_angle = (int)(this.xpd.dir_seg1_start_angle() - this.xpd.dir_seg1_end_angle());
									if (this.xpd.dir_seg1_turn() == 3) // right
										arc_angle = (int)(this.xpd.dir_seg1_end_angle() - this.xpd.dir_seg1_start_angle());
									if (arc_angle < 0)
										arc_angle = arc_angle + 360;
									
									// calculate point at end of arc
									latlon_dir_seg1_end = CoordinateSystem.latlon_at_dist_heading(this.xpd.dir_seg1_ctr_lat(), this.xpd.dir_seg1_ctr_lon(), this.xpd.dir_seg1_radius(), this.xpd.dir_seg1_end_angle());
									map_projection.setPoint(latlon_dir_seg1_end[0], latlon_dir_seg1_end[1]);
									dir_seg1_end_x = map_projection.getX();
									dir_seg1_end_y = map_projection.getY();
									
									if (this.xpd.dir_seg1_turn() == 2) { // left
										g2.drawArc((int) arc_origin_x - radius, (int) arc_origin_y - radius, diameter, diameter, start_angle, arc_angle);
									} else if (this.xpd.dir_seg1_turn() == 3) { // right
										g2.drawArc((int) arc_origin_x - radius, (int) arc_origin_y - radius, diameter, diameter, start_angle, -arc_angle);
									}
									
									// seg3
									if (this.xpd.dir_seg3_turn() != 0) {
										
										// line between dir seg1 & seg3
										latlon_dir_seg3_start = CoordinateSystem.latlon_at_dist_heading(this.xpd.dir_seg3_ctr_lat(), this.xpd.dir_seg3_ctr_lon(), this.xpd.dir_seg3_radius(), this.xpd.dir_seg3_start_angle());
										map_projection.setPoint(latlon_dir_seg3_start[0], latlon_dir_seg3_start[1]); 
										dir_seg3_start_x = map_projection.getX();
										dir_seg3_start_y = map_projection.getY();
										g2.drawLine((int) dir_seg1_end_x, (int) dir_seg1_end_y, (int) dir_seg3_start_x, (int) dir_seg3_start_y);
																	
										// radius in pixels per nm
										map_projection.setPoint(this.xpd.dir_seg3_ctr_lat(), this.xpd.dir_seg3_ctr_lon());
										arc_origin_x = map_projection.getX();
										arc_origin_y = map_projection.getY();
										
										// radius in pixels per nm
										radius = (int)(this.xpd.dir_seg3_radius() * pixels_per_nm);
										diameter = (int)(this.xpd.dir_seg3_radius() * pixels_per_nm * 2);
										
										// start & end angles
										start_angle = 0 + (int) (90 - this.xpd.dir_seg3_start_angle());
										end_angle = 0 + (int) (90 - this.xpd.dir_seg3_end_angle());
										
										// calculate arc angle - do not change this formula
										if (this.xpd.dir_seg3_turn() == 2) // left
											arc_angle = (int)(this.xpd.dir_seg3_start_angle() - this.xpd.dir_seg3_end_angle());
										if (this.xpd.dir_seg3_turn() == 3) // right
											arc_angle = (int)(this.xpd.dir_seg3_end_angle() - this.xpd.dir_seg3_start_angle());
										if (arc_angle < 0)
											arc_angle = arc_angle + 360;
										
										// calculate point at end of arc
										latlon_dir_seg3_end = CoordinateSystem.latlon_at_dist_heading(this.xpd.dir_seg3_ctr_lat(), this.xpd.dir_seg3_ctr_lon(), this.xpd.dir_seg3_radius(), this.xpd.dir_seg3_end_angle());
										map_projection.setPoint(latlon_dir_seg3_end[0], latlon_dir_seg3_end[1]);
										dir_seg3_end_x = map_projection.getX();
										dir_seg3_end_y = map_projection.getY();
										
										if (this.xpd.dir_seg3_turn() == 2) { // left
											g2.drawArc((int) arc_origin_x - radius, (int) arc_origin_y - radius, diameter, diameter, start_angle, arc_angle);
										} else if (this.xpd.dir_seg3_turn() == 3) { // right
											g2.drawArc((int) arc_origin_x - radius, (int) arc_origin_y - radius, diameter, diameter, start_angle, -arc_angle);
										}
										g2.drawLine((int) dir_seg3_end_x, (int) dir_seg3_end_y, xx, yy);
										
									} else {
										
										g2.drawLine((int) dir_seg1_end_x, (int) dir_seg1_end_y, xx, yy);
										
									}
									
								} else {
																
									if(this.pln_mode) {
										g2.drawLine(plane_x, plane_y, xx, yy);
									} else {
										g2.drawLine((int)this.map_center_x, (int)this.map_center_y, xx, yy);
									}
								
								}
							
							}
							
						} else {
					
							// radii turn
							if (legs_seg1_radius[i] > 0.1 &&
								!((int)legs_rad_turn[next_idx] == 0 || (int)legs_rad_turn[next_idx] == 1) && // ignore hold
								!(i == 0 && wpts[i].equals(origin_arpt) && !(wpts[next_idx].startsWith("RW")))) { // skip departure airport with no runway selected
								
								// seg1
								if ((int)legs_seg1_turn[i] == 2) {
									// left
									g2.drawArc((int) arc_origin_x - radius, (int) arc_origin_y - radius, diameter, diameter, start_angle, arc_angle);
								} else if ((int)legs_seg1_turn[i] == 3) {
									// right
									g2.drawArc((int) arc_origin_x - radius, (int) arc_origin_y - radius, diameter, diameter, start_angle, -arc_angle);				
								}
								
								// seg3
								if ((int)legs_seg3_turn[i] == 2 || (int)legs_seg3_turn[i] == 3) {
									map_projection.setPoint(legs_seg1_end_lat[i], legs_seg1_end_lon[i]); 
									legs_seg1_end_x = map_projection.getX();
									legs_seg1_end_y = map_projection.getY();
									map_projection.setPoint(legs_seg3_start_lat[i], legs_seg3_start_lon[i]); 
									legs_seg3_start_x = map_projection.getX();
									legs_seg3_start_y = map_projection.getY();
									g2.drawLine((int) legs_seg1_end_x, (int) legs_seg1_end_y, (int) legs_seg3_start_x, (int) legs_seg3_start_y);
								}
								if ((int)legs_seg3_turn[i] == 2) {
									// left
									g2.drawArc((int) arc_origin_x_seg3 - radius_seg3, (int) arc_origin_y_seg3 - radius_seg3, diameter_seg3, diameter_seg3, start_angle_seg3, arc_angle_seg3);
								} else if ((int)legs_seg3_turn[i] == 3) {
									// right
									g2.drawArc((int) arc_origin_x_seg3 - radius_seg3, (int) arc_origin_y_seg3 - radius_seg3, diameter_seg3, diameter_seg3, start_angle_seg3, -arc_angle_seg3);			
								}
								
								// with af turn
								if (legs_af_beg[next_idx] != 0.0 || legs_af_end[next_idx] != 0.0) {
									
									if ((int)legs_rad_turn[next_idx] == 2) {
										// left
										g2.drawArc((int) arc_origin_x_af - radius_af, (int) arc_origin_y_af - radius_af, diameter_af, diameter_af, start_angle_af, arc_angle_af);
									} else if ((int)legs_rad_turn[next_idx] == 3) {
										// right
										g2.drawArc((int) arc_origin_x_af - radius_af, (int) arc_origin_y_af - radius_af, diameter_af, diameter_af, start_angle_af, -arc_angle_af);
									}
															
								} else {
									
									// with line																
									if (legs_seg1_radius[next_idx] > 0.1) {
										// end of arc to start of arc
										map_projection.setPoint(legs_seg1_start_lat[next_idx], legs_seg1_start_lon[next_idx]); 
										g2.drawLine((int) arc_end_x, (int) arc_end_y, (int) map_projection.getX(), (int) map_projection.getY());
									} else {
										// end of arc to waypoint
										g2.drawLine((int) arc_end_x, (int) arc_end_y, xx, yy);
									}
								}
							
							// rad turn
							} else if ((int)legs_rad_turn[next_idx] != -1 && legs_rad_lat[next_idx] != 0.0 && legs_rad_lon[next_idx] != 0.0) {
								
								if ((int)legs_rad_turn[next_idx] == 2) {
									// left
									g2.drawArc((int) arc_origin_x - radius, (int) arc_origin_y - radius, diameter, diameter, start_angle, arc_angle);
								} else if ((int)legs_rad_turn[next_idx] == 3) {
									// right
									g2.drawArc((int) arc_origin_x - radius, (int) arc_origin_y - radius, diameter, diameter, start_angle, -arc_angle);
								}
							
							// no turn
							} else {
								
								// waypoint to ???
								if (legs_seg1_radius[next_idx] > 0.1 && !(x == xx && y == yy)) {
									// waypoint to start of arc
									map_projection.setPoint(legs_seg1_start_lat[next_idx], legs_seg1_start_lon[next_idx]); 
									g2.drawLine(x, y, (int) map_projection.getX(), (int) map_projection.getY()); 
								} else {
									// waypoint to waypoint
									g2.drawLine(x, y, xx, yy);
								}
							
							}
						
						}
						
					}
			
				} else {
					
					if ((int) legs_bypass[i] != 1) {
					
						// first leg of non active (direct) 
						
						// if ((this.xpd.intdir_act() || this.xpd.legs_mod_active()) && i == vnav_idx - 2) {
						if ((this.xpd.intdir_act() || this.xpd.legs_mod_active()) && i == vnav_index) {
				
							map_projection.setPoint(lats[next_idx], lons[next_idx]);
							xx = (int) map_projection.getX();
							yy = (int) map_projection.getY();
													
							// intersection direct
							if (this.xpd.intdir_crs2() != -1) {
								
								//get point based on FMS track and distance				
								float track = (this.xpd.intdir_crs2() - 180 - this.xpd.magnetic_variation()) % 360;							
								float[] latlon = CoordinateSystem.latlon_at_dist_heading(lats[next_idx], lons[next_idx], this.max_range * 2, track);	
								map_projection.setPoint(latlon[0], latlon[1]);
								int xxx = (int) map_projection.getX();
								int yyy = (int) map_projection.getY();
								
								g2.drawLine(xxx, yyy, xx, yy);
							
							} else {
								
								if (this.pln_mode) {
									g2.drawLine(plane_x, plane_y, xx, yy);
								} else {
									g2.drawLine((int)this.map_center_x, (int)this.map_center_y, xx, yy);
								}

							}
										
						} else {
				
							// non active normal leg							
							g2.drawLine(x, y, xx, yy);
							
						}
						
					}
			
				}

			}			


		
			// draw waypoints
			
			int index = 0;
			if (this.xpd.intdir_act()) {
				index = vnav_idx - 1;
			} else {
				index = vnav_idx - 2;
			}
			
			for(int i = index; i < num_of_wpts; i++) {
							
				map_projection.setPoint(lats[i], lons[i]);
				int wpt_x = (int) map_projection.getX();
				int wpt_y = (int) map_projection.getY();
			
				fms_trans = g2.getTransform();
				g2.rotate(Math.toRadians(this.map_up * -1), wpt_x, wpt_y);
							
				// draw the waypoint
				if (!((i >= 1 && wpts[i - 1].equals(origin_arpt) && wpts[i].startsWith("RW")) || wpts[i].equals(dest_arpt) || wpts[i].contains("DISCONTINUITY") || wpts[i].contains("VECTOR"))) {
					
					if (isActive) {
						
						if (this.xpd.fpln_active()) {
							
							if (wpts[i].equals(xpd.active_waypoint())) {
								g2.setColor(gc.color_magenta);
								if ((int)type[i] == 5) {
									gc.displayNavAid(rs.img_magenta_flyover_waypoint, wpt_x, wpt_y, g2);
								} else {
									gc.displayNavAid(rs.img_magenta_waypoint, wpt_x, wpt_y, g2);
								}							
							} else {
								g2.setColor(gc.color_markings);
								if ((int)type[i] == 5) {
									gc.displayNavAid(rs.img_white_flyover_waypoint, wpt_x, wpt_y, g2);
								} else {
									gc.displayNavAid(rs.img_white_waypoint, wpt_x, wpt_y, g2);
								}
							}
						}
						
					} else {
						
						g2.setColor(gc.color_markings);
						gc.displayNavAid(rs.img_white_waypoint, wpt_x, wpt_y, g2);
						
					}

					if (i > vnav_idx - 3) {
						
						g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));
						g2.drawString(wpts[i], wpt_x + (30f * gc.scaling_factor), wpt_y + (30f * gc.scaling_factor));

						// format eta zulu
						  
						String eta_zulu = "";
						if (this.xpd.efis_data_on(this.pilot) && isActive) {
							if (legs_eta[i] == 0.0) {
								eta_zulu = "----Z";
							} else {
								int eta_hours = (int) Math.floor(legs_eta[i]);
								int eta_minutes = (int) Math.floor(60 * (legs_eta[i] - eta_hours));
								eta_zulu = String.format("%02d", eta_hours) + String.format("%02d", eta_minutes) + "Z";
							}
						}
					
						//draw alt restrictions
						
						if(alt_rest1[i] != 0 || alt_rest2[i] != 0) {
							if(alt_rest_type[i] == 0.0f) { //  at
								g2.drawString("" + (int)alt_rest1[i], wpt_x + (30 * gc.scaling_factor), wpt_y + (60 * gc.scaling_factor));
								if(this.xpd.efis_data_on(this.pilot)) {
									//draw eta if available
									g2.drawString(eta_zulu, wpt_x + (30 * gc.scaling_factor), wpt_y + (90 * gc.scaling_factor));		
								}	
							}else if(alt_rest_type[i] == 1.0f) { // below
								g2.drawString("" + (int)alt_rest1[i] + "B", wpt_x + (30 * gc.scaling_factor), wpt_y + (60 * gc.scaling_factor));
								if(this.xpd.efis_data_on(this.pilot)) {
									//draw eta if available
									g2.drawString(eta_zulu, wpt_x + (30 * gc.scaling_factor), wpt_y + (90 * gc.scaling_factor));		
								}	
							}else if(alt_rest_type[i] == 2.0f) { // above
								g2.drawString("" + (int)alt_rest1[i] + "A", wpt_x + (30 * gc.scaling_factor), wpt_y + (60 * gc.scaling_factor));
								if(this.xpd.efis_data_on(this.pilot)) {
									//draw eta if available
									g2.drawString(eta_zulu, wpt_x + (30 * gc.scaling_factor), wpt_y + (90 * gc.scaling_factor));		
								}	
							}else if(alt_rest_type[i] == 3.0f) { // between
								g2.drawString("" + (int)alt_rest2[i] + "B", wpt_x + (30 * gc.scaling_factor), wpt_y + (60 * gc.scaling_factor));
								g2.drawString("" + (int)alt_rest1[i] + "A", wpt_x + (30 * gc.scaling_factor), wpt_y + (90 * gc.scaling_factor));
								if(this.xpd.efis_data_on(this.pilot)) {
									//draw eta if available
									g2.drawString(eta_zulu, wpt_x + (30 * gc.scaling_factor), wpt_y + (120 * gc.scaling_factor));		
								}	
							}else if(alt_rest_type[i] == 4.0f) { // destination runway
								g2.drawString("" + (int)alt_rest1[i], wpt_x + (30 * gc.scaling_factor), wpt_y + (60 * gc.scaling_factor));
								if(this.xpd.efis_data_on(this.pilot)) {
									//draw eta if available
									g2.drawString(eta_zulu, wpt_x + (30 * gc.scaling_factor), wpt_y + (90 * gc.scaling_factor));		
								}	
							}	
						} else {
							if(this.xpd.efis_data_on(this.pilot)) {
								//draw eta if available
								g2.drawString(eta_zulu, wpt_x + (30 * gc.scaling_factor), wpt_y + (60 * gc.scaling_factor));							
							}	
						}
					}				
				}
								
				g2.setTransform(fms_trans);
				
			}
		}
			
		g2.setTransform(main_map);
				
		//draw tc/decel/td/ed
		
		//tc
		if(this.xpd.tc_show(this.pilot)) {
			float xx = zibo_x(this.xpd.tc_x(this.pilot));
			float yy = zibo_y(this.xpd.tc_y(this.pilot));
			g2.setStroke(stroke3);
			g2.setColor(gc.color_lime);
			g2.drawOval((int)(xx - (10f * gc.scalex)), (int)(yy - (10f * gc.scaley)), (int)(20f * gc.scaling_factor), (int)(20f * gc.scaling_factor));
			g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));
			if(this.xpd.tc_id(this.pilot) != null) {
				g2.drawString(this.xpd.tc_id(this.pilot), xx + (35f * gc.scalex), yy + (15 * gc.scaley));
			}
		}
		//decel
		if(this.xpd.decel_show(this.pilot)) {
			float xx = zibo_x(this.xpd.decel_x(this.pilot));
			float yy = zibo_y(this.xpd.decel_y(this.pilot));
			g2.setStroke(stroke3);
			g2.setColor(gc.color_lime);
			g2.drawOval((int)(xx - (10f * gc.scalex)), (int)(yy - (10f * gc.scaley)), (int)(20f * gc.scaling_factor), (int)(20f * gc.scaling_factor));
			g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));
			if(this.xpd.decel_id(this.pilot) != null) {
				g2.drawString(this.xpd.decel_id(this.pilot), xx + (35f * gc.scalex), yy + (15 * gc.scaley));
			}
		}
		//decel_def
		if(this.xpd.decel_def_show(this.pilot)) {
			float xx = zibo_x(this.xpd.decel_def_x(this.pilot));
			float yy = zibo_y(this.xpd.decel_def_y(this.pilot));
			g2.setStroke(stroke3);
			g2.setColor(gc.color_lime);
			g2.drawOval((int)(xx - (10f * gc.scalex)), (int)(yy - (10f * gc.scaley)), (int)(20f * gc.scaling_factor), (int)(20f * gc.scaling_factor));
		}
		//decel_def2
		if(this.xpd.decel_def2_show(this.pilot)) {
			float xx = zibo_x(this.xpd.decel_def2_x(this.pilot));
			float yy = zibo_y(this.xpd.decel_def2_y(this.pilot));
			g2.setStroke(stroke3);
			g2.setColor(gc.color_lime);
			g2.drawOval((int)(xx - (10f * gc.scalex)), (int)(yy - (10f * gc.scaley)), (int)(20f * gc.scaling_factor), (int)(20f * gc.scaling_factor));
		}
		//td
		if(this.xpd.td_show(this.pilot)) {
			float xx = zibo_x(this.xpd.td_x(this.pilot));
			float yy = zibo_y(this.xpd.td_y(this.pilot));
			g2.setStroke(stroke3);
			g2.setColor(gc.color_lime);
			g2.drawOval((int)(xx - (10f * gc.scalex)), (int)(yy - (10f * gc.scaley)), (int)(20f * gc.scaling_factor), (int)(20f * gc.scaling_factor));
			g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));
			if(this.xpd.td_id(this.pilot) != null) {
				g2.drawString(this.xpd.td_id(this.pilot), xx + (35f * gc.scalex), yy + (15 * gc.scaley));
			}
		}
		//ed
		if(this.xpd.ed_show(this.pilot)) {
			float xx = zibo_x(this.xpd.ed_x(this.pilot));
			float yy = zibo_y(this.xpd.ed_y(this.pilot));
			g2.setStroke(stroke3);
			g2.setColor(gc.color_lime);
			g2.drawOval((int)(xx - (10f * gc.scalex)), (int)(yy - (10f * gc.scaley)), (int)(20f * gc.scaling_factor), (int)(20f * gc.scaling_factor));
			g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));
			if(this.xpd.ed_id(this.pilot) != null) {
				g2.drawString(this.xpd.ed_id(this.pilot), xx + (35f * gc.scalex), yy + (15 * gc.scaley));
			}
		}
		
		g2.setClip(original_clipshape);
		
	}

	private void drawHold(float turn, float course, float dist, float time, float speed, float x, float y, int active) {
		
		map_projection.setPoint(x, y); 
		float xx = map_projection.getX();
		float yy = map_projection.getY();
		
		// g2.drawString(turn + ":" + course + ":" + dist + ":" + time + ":" + speed, xx, yy);
		
		course = course - this.xpd.magnetic_variation();
		
		g2.rotate(Math.toRadians(course), xx, yy);
		
		double xspeed = 0;
		double leg_dist = 0;
		double distance = 0;
		int radius = 0;
		int yyy = 0;
		
		if (active == 1) {
		
			xspeed = this.xpd.true_airspeed_knots() + this.xpd.wind_speed_knots(); // max possible speed
					
			if (time != 0) {
				leg_dist = xspeed * time / 3600;
			} else if (dist != 0) {
				leg_dist = dist;
			} else {
				leg_dist = xspeed * 90 / 3600; // default to 1.5 min legs
			}
					
			if (this.xpd.altitude(pilot) <= 18800) {
				distance = Math.cos(Math.toRadians(23)) * leg_dist;
			} else {
				distance = Math.cos(Math.toRadians(15)) * leg_dist;
			}
			yyy = (int) (yy + (distance * pixels_per_nm));
			
			// int radius = (int) (tas * pixels_per_nm / (20 * Math.PI));
		
			if (this.xpd.altitude(pilot) <= 18800) {
				radius = (int) ((xspeed * xspeed) / (68620 * Math.tan(Math.toRadians(23))) * pixels_per_nm);
			} else {
				radius = (int) ((xspeed * xspeed) / (68620 * Math.tan(Math.toRadians(15))) * pixels_per_nm);
			}
			
		} else {
			
			// default for non active hold	
			xspeed = 170;
			leg_dist = 2;
			distance = Math.cos(Math.toRadians(23)) * leg_dist;
			yyy = (int) (yy + (distance * pixels_per_nm));
			radius = (int) ((xspeed * xspeed) / (68620 * Math.tan(Math.toRadians(23))) * pixels_per_nm);
			
		}
		
		// right
		if (turn == 1) {
			g2.drawLine((int)xx, (int)yy, (int)xx, yyy);
			g2.drawArc((int)xx, yyy - radius, radius * 2, radius * 2, 180, 180);
			g2.drawLine((int)xx + radius * 2, (int)yy, (int)xx + radius * 2, yyy);
			g2.drawArc((int)xx, (int)(yyy - (distance * pixels_per_nm) - radius)  , radius * 2, radius * 2, 0, 180);
		// left
		} else {
			g2.drawLine((int)xx, (int)yy, (int)xx, yyy);
			g2.drawArc((int)xx - radius * 2, yyy - radius, radius * 2, radius * 2, 0, -180);
			g2.drawLine((int)xx - radius * 2, (int)yy, (int)xx - radius * 2, yyy);
			g2.drawArc((int)xx - radius * 2, (int)(yyy - (distance * pixels_per_nm) - radius)  , radius * 2, radius * 2, 0, 180);
		}
		
		g2.rotate(Math.toRadians(course * -1), xx, yy);
	
	}
	

	private void drawWeather() {

		weather_cycle += 0.008f;
		if (weather_cycle > 1f) {
			weather_cycle = 0f;
			weather_invert = !weather_invert;
		}

		int start = 180;
		int extent = Math.round(weather_cycle * -180f);

		if (weather_invert) {
			extent = -180 - extent;
		}

		weather_sweep_clip.setFrame(this.map_center_x - this.rose_radius, this.map_center_y - this.rose_radius, this.rose_radius * 2, this.rose_radius * 2);
		weather_sweep_clip.setArcType(Arc2D.PIE);
		weather_sweep_clip.setAngleStart(start);
		weather_sweep_clip.setAngleExtent(extent);

		if(extent <= 0 && extent >= -2) {
			weather.renderWeather(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, false);
		}

		if(extent <= -179 && extent >= -180) {
			weather.renderWeather(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, false);
		}
		if(!isMapCenter) {
			weather_clip.setArcByCenter(this.map_center_x, this.map_center_y, 760f * gc.scaling_factor, 0f, 180f, Arc2D.PIE);
		} else {
			weather_clip.setArcByCenter(this.map_center_x, this.map_center_y, 375f * gc.scaling_factor, 0f, 180f, Arc2D.CHORD);
		}

		g2.clip(weather_clip);

		if(weather.img_weather1 != null) {
			g2.translate(this.map_center_x, this.map_center_y);
			g2.transform(AffineTransform.getRotateInstance(Math.toRadians(map_up), 0, 0));
			g2.drawImage(weather.img_weather1, -(weather.img_weather1.getWidth()/2), -(weather.img_weather1.getHeight()/2), null);
			g2.setTransform(original_trans);
		}
		if(weather.img_weather2 != null) {
			g2.clip(weather_sweep_clip);
			g2.translate(this.map_center_x, this.map_center_y);
			g2.transform(AffineTransform.getRotateInstance(Math.toRadians(map_up), 0, 0));
			g2.drawImage(weather.img_weather2, -(weather.img_weather2.getWidth()/2 - 3), -(weather.img_weather2.getHeight()/2 - 3), null);
			g2.setTransform(original_trans);
			g2.setClip(original_clipshape);
		}

	}

	private void drawTerrain() {

		terrain_cycle += 0.008f;
		if (terrain_cycle > 1f) {
			terrain_cycle = 0f;
			terrain_invert = !terrain_invert;
		}

		int start = 180;
		int extent = Math.round(terrain_cycle * -180f);

		if (terrain_invert) {
			extent = -180 - extent;
		}

		terrain_sweep_clip.setFrame(this.map_center_x - this.rose_radius, this.map_center_y - this.rose_radius, this.rose_radius * 2, this.rose_radius * 2);
		terrain_sweep_clip.setArcType(Arc2D.PIE);
		terrain_sweep_clip.setAngleStart(start);
		terrain_sweep_clip.setAngleExtent(extent);

		if(extent <= 0 && extent >= -2) {
			if(isMapCenter) {
				terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, true);
			}else {
				terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, false);
			}

		}
		if(extent <= -179 && extent >= -180) {
			if(isMapCenter) {
				terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, true);
			}else {
				terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, false);

			}		
		}

		if(!isMapCenter) {
			terrain_clip.setArcByCenter(this.map_center_x, this.map_center_y, 760f * gc.scaling_factor, 0f, 180f, Arc2D.PIE);
		}else {
			terrain_clip.setArcByCenter(this.map_center_x, this.map_center_y, 375f * gc.scaling_factor, 0f, 180f, Arc2D.CHORD);
		}
		g2.clip(terrain_clip);

		if(this.xpd.efis_terr_on(pilot) && egpws_loaded) {

			if(terrain.img_terrain1 != null) {
				g2.translate(this.map_center_x, this.map_center_y);
				g2.transform(AffineTransform.getRotateInstance(Math.toRadians(map_up), 0, 0));
				g2.drawImage(terrain.img_terrain1, -(terrain.img_terrain1.getWidth()/2), -(terrain.img_terrain1.getHeight()/2), null);
				g2.setTransform(original_trans);
			}
			if(terrain.img_terrain2 != null) {
				g2.clip(terrain_sweep_clip);
				g2.translate(this.map_center_x, this.map_center_y);
				g2.transform(AffineTransform.getRotateInstance(Math.toRadians(map_up), 0, 0));
				g2.drawImage(terrain.img_terrain2, -(terrain.img_terrain2.getWidth()/2 - 3), -(terrain.img_terrain2.getHeight()/2 - 3), null);
				g2.setTransform(original_trans);
				g2.setClip(original_clipshape);
			}

			if(initial_sweep_rotate > 0f) {
				initial_sweep_rotate = initial_sweep_rotate - 1.4f;
			}else if (initial_sweep_rotate < 0f) {
				initial_sweep_rotate = 0f;
				initial_sweep_required = false;
			}

			if(initial_sweep_required) {
				g2.translate(this.map_center_x, this.map_center_y);
				g2.setColor(Color.BLACK);
				Arc2D.Float initial_sweep = new Arc2D.Float(Arc2D.PIE);
				initial_sweep.setFrame((int)-this.rose_radius, (int)-this.rose_radius, (int)(this.rose_radius * 2), (int)(this.rose_radius * 2));
				initial_sweep.setAngleStart(0f);
				initial_sweep.setAngleExtent(initial_sweep_rotate);
				g2.fill(initial_sweep);
				g2.setTransform(original_trans);

			}

		} else {
			initial_sweep_required = true;
			initial_sweep_rotate = 180f;
		}
		g2.setClip(original_clipshape);
	}

	private void drawTrackLine() {
		
		if (!this.pln_mode) {
			
			g2.translate(this.map_center_x, this.map_center_y);
			g2.scale(gc.scalex, gc.scaley);
			g2.setColor(gc.color_markings);
			g2.rotate(Math.toRadians(track_line_rotate), 0, 0);
			g2.setStroke(stroke5);
			
			if (isMapCenter) {
				//ctr mode
				g2.drawLine(-10, 188, 10, 188);
				g2.drawLine(-10, -188, 10, -188);
				g2.drawLine(0, -160, 0, -405);
				g2.drawLine(0, 150, 0, 380);
			} else {
				//expanded mode
				if (!this.xpd.on_gound()) {
					g2.drawLine(0, -165, 0, -783);
				} else {
					g2.drawLine(0, -15, 0, -783);
				}
				
				if(!(this.xpd.efis_terr_on(pilot) || this.xpd.efis_wxr_on(pilot) || this.xpd.tcas_on(pilot))) {
					g2.drawLine(-10, -190, 10, -190);
					g2.drawLine(-10, -379, 10, -379);
					g2.drawLine(-10, -569, 10, -569);
				}
			}
			
			if (this.xpd.efis_vsd_map(pilot)) {
				g2.setColor(Color.CYAN);
				g2.setStroke(vsd_line);
				g2.drawLine(-10, -300, -10, 0);
				g2.drawLine(+10, -300, +10, 0);
				g2.setColor(gc.color_markings);
				g2.setStroke(stroke5);
			}

			if (this.map_mode || this.map_ctr_mode) {
			
				float turn_speed = turn_speed_averager.running_average(this.xpd.yaw_rotation()); // turn speed in deg/s
				float turn_radius = turn_radius(turn_speed, this.xpd.groundspeed()); // turn radius in nm
				
				if (this.max_range > 20) {
					// first segment : 30sec
					draw_position_trend_vector_segment(g2, turn_radius, turn_speed, pixels_per_nm, 0f, 23f);
					// second segment : 60sec
					draw_position_trend_vector_segment(g2, turn_radius, turn_speed, pixels_per_nm, 38f, 68f);
					// third segment : 90sec
					draw_position_trend_vector_segment(g2, turn_radius, turn_speed, pixels_per_nm, 83f, 113f);
				} else if (this.max_range == 20) {
					// first segment : 30sec
					draw_position_trend_vector_segment(g2, turn_radius, turn_speed, pixels_per_nm, 0f, 30f);
					// second segment : 60sec
					draw_position_trend_vector_segment(g2, turn_radius, turn_speed, pixels_per_nm, 40f, 70f);
				} else if (this.max_range <= 10) {
					// first segment : 30sec
					draw_position_trend_vector_segment(g2, turn_radius, turn_speed, pixels_per_nm, 0f, 27f);
				}
			}				
		}
		
		g2.setTransform(original_trans);
		
	}

	@SuppressWarnings("unchecked")
	private void drawMap() {

		float delta_lat = this.max_range * CoordinateSystem.deg_lat_per_nm();
		float delta_lon = this.max_range * CoordinateSystem.deg_lon_per_nm(this.center_lat);
		float lat_max = this.center_lat + delta_lat * 1.5f;
		float lat_min = this.center_lat - delta_lat * 1.5f;
		float lon_max = this.center_lon + delta_lon * 1.5f;
		float lon_min = this.center_lon - delta_lon * 1.5f;
		this.pixels_per_deg_lat = rose_radius / delta_lat;
		this.pixels_per_deg_lon = rose_radius / delta_lon;
		if(this.pln_mode) {
			map_clip.setArcByCenter(this.map_center_x, this.map_center_y, 425f * gc.scaling_factor, 0f, 360f, Arc2D.CHORD);
		} else {
			map_clip.setArcByCenter(this.map_center_x, this.map_center_y, 760f * gc.scaling_factor, 0f, 360f, Arc2D.CHORD);
		}
		
		g2.clip(map_clip);
	
		main_map = g2.getTransform();
		
		g2.transform(AffineTransform.getRotateInstance(Math.toRadians(map_up), map_center_x, map_center_y));
		
		// lookup destination airport
		if (this.xpd.vnav_idx() >= 1) {
			if (this.xpd.dest_arpt() != null) {
				dest = nor.get_airport(this.xpd.dest_arpt());				
			}	
		}			
		
		for (int lat = (int) lat_min; lat <= (int) lat_max; lat++) {
			for (int lon = (int) lon_min; lon <= (int) lon_max; lon++) {
				//if (this.xpd.efis_apt_mode(pilot) && this.max_range <= 320 && !(this.xpd.map_mode_vor(pilot) || this.xpd.map_mode_app(pilot))) {
				if (this.xpd.efis_apt_mode(pilot) && this.xpd.map_range(pilot) <= 6 && !(this.xpd.map_mode_vor(pilot) || this.xpd.map_mode_app(pilot))) {
					draw_nav_objects(NavigationObject.NO_TYPE_AIRPORT, nor.get_nav_objects(NavigationObject.NO_TYPE_AIRPORT, lat, lon));
				}
				//if (this.xpd.efis_sta_mode(pilot) && this.max_range <= 160 && !(this.xpd.map_mode_vor(pilot) || this.xpd.map_mode_app(pilot))) {
				if (this.xpd.efis_sta_mode(pilot) && this.xpd.map_range(pilot) <= 5 && !(this.xpd.map_mode_vor(pilot) || this.xpd.map_mode_app(pilot))) {
					draw_nav_objects(NavigationObject.NO_TYPE_VOR, nor.get_nav_objects(NavigationObject.NO_TYPE_VOR, lat, lon));
				}
				//if (this.xpd.efis_wpt_mode(pilot) && this.max_range <= 40 && !(this.xpd.map_mode_vor(pilot) || this.xpd.map_mode_app(pilot))) {
				if (this.xpd.efis_wpt_mode(pilot) && this.xpd.map_range(pilot) <= 3 && !(this.xpd.map_mode_vor(pilot) || this.xpd.map_mode_app(pilot))) {
					draw_nav_objects(NavigationObject.NO_TYPE_FIX, nor.get_nav_objects(NavigationObject.NO_TYPE_FIX, lat, lon));
				}
			}			
		}
		
		boolean[] cdufix_drawn = {false,false,false,false,false};
						
		// display runway fixes
		for(int i = 0; i < 5; i++) {
			if(this.xpd.fix_show(pilot)[i]) {
				if(this.xpd.fix_id(pilot)[i].startsWith("RW")) {
					String dest_runway = this.xpd.fix_id(pilot)[i].replaceAll("RW", "");
					if (!this.xpd.dest_arpt().isEmpty()) { // no active flight plan
						NavigationObject dest_runway_fix = nor.get_runway(this.xpd.dest_arpt(), dest_runway, dest.lat, dest.lon, false);
						if(dest_runway_fix != null) {
							Runway rwy = (Runway) dest_runway_fix;				
							if(rwy != null) {
								if(dest_runway.equals(rwy.rwy_num1)) {
									map_projection.setPoint(rwy.lat1, rwy.lon1);
								}else {
									map_projection.setPoint(rwy.lat2, rwy.lon2);
								}
								float cdufix_x = map_projection.getX();
								float cdufix_y = map_projection.getY();
								drawCduFix(cdufix_x, cdufix_y, 4, this.xpd.fix_id(pilot)[i]);
								displayFixRings(i, cdufix_x, cdufix_y);
								displayFixRadials(i, cdufix_x, cdufix_y);
								cdufix_drawn[i] = true;
							}
						}
					}
				}
			}
		}
		
		// display cdu fixes			
		for(int i = 0; i < 5; i++) {				
			if(this.xpd.fix_show(pilot)[i] && !cdufix_drawn[i]) {
				map_projection.setPoint(this.xpd.fix_lat()[i], this.xpd.fix_lon()[i]);
				float cdufix_x = map_projection.getX();
				float cdufix_y = map_projection.getY();
				drawCduFix(cdufix_x, cdufix_y, this.xpd.fix_type()[i], this.xpd.fix_id(pilot)[i]);
				displayFixRings(i, cdufix_x, cdufix_y);
				displayFixRadials(i, cdufix_x, cdufix_y);						
			}				
		}

		g2.setTransform(main_map);
		
		g2.setClip(original_clipshape);
	
	}
	
	private void displayFixRings(int i, float cdufix_x, float cdufix_y) {
				
		if(this.xpd.fix_rad_dist_0()[i] > 0f) {
			
			if(this.xpd.fix_rad_dist_0()[i] == 1f) {
				
			} else {
				int dist = (int) (this.xpd.fix_dist_0()[i] * 2f);
				g2.setStroke(gc.runway_center_line);
				g2.drawOval((int)(cdufix_x - (dist * pixels_per_nm / 2)), (int)(cdufix_y - (dist * pixels_per_nm / 2)), (int)(dist * pixels_per_nm), (int)(dist * pixels_per_nm));
				temp_trans = g2.getTransform();
				g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
				g2.rotate(Math.toRadians(this.map_up * -1), cdufix_x, cdufix_y);
				g2.drawString("" + dist /2, cdufix_x + 20 * gc.scalex, (cdufix_y - (dist * pixels_per_nm / 2)) - 10 * gc.scaley);
				g2.drawString("" + dist /2, cdufix_x + 20 * gc.scalex, (cdufix_y + (dist * pixels_per_nm / 2)) + 30 * gc.scaley);
				g2.setTransform(temp_trans);
			}
		}
		
		if(this.xpd.fix_rad_dist_1()[i] > 0f) {

			if(this.xpd.fix_rad_dist_1()[i] == 1f) {
				
			} else {
				int dist = (int) (this.xpd.fix_dist_1()[i] * 2f);
				g2.setStroke(gc.runway_center_line);
				g2.drawOval((int)(cdufix_x - (dist * pixels_per_nm / 2)), (int)(cdufix_y - (dist * pixels_per_nm / 2)), (int)(dist * pixels_per_nm), (int)(dist * pixels_per_nm));
				g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
				temp_trans = g2.getTransform();
				g2.rotate(Math.toRadians(this.map_up * -1), cdufix_x, cdufix_y);
				g2.drawString("" + dist /2, cdufix_x + 20 * gc.scalex, (cdufix_y - (dist * pixels_per_nm / 2)) - 10 * gc.scaley);
				g2.drawString("" + dist /2, cdufix_x + 20 * gc.scalex, (cdufix_y + (dist * pixels_per_nm / 2)) + 30 * gc.scaley);
				g2.setTransform(temp_trans);
			}
		}
		
		if(this.xpd.fix_rad_dist_2()[i] > 0f) {

			if(this.xpd.fix_rad_dist_2()[i] == 1f) {
				
			} else {
				int dist = (int) (this.xpd.fix_dist_2()[i] * 2f);
				g2.setStroke(gc.runway_center_line);
				g2.drawOval((int)(cdufix_x - (dist * pixels_per_nm / 2)), (int)(cdufix_y - (dist * pixels_per_nm / 2)), (int)(dist * pixels_per_nm), (int)(dist * pixels_per_nm));
				g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
				temp_trans = g2.getTransform();
				g2.rotate(Math.toRadians(this.map_up * -1), cdufix_x, cdufix_y);
				g2.drawString("" + dist /2, cdufix_x + 20 * gc.scalex, (cdufix_y - (dist * pixels_per_nm / 2)) - 10 * gc.scaley);
				g2.drawString("" + dist /2, cdufix_x + 20 * gc.scalex, (cdufix_y + (dist * pixels_per_nm / 2)) + 30 * gc.scaley);
				g2.setTransform(temp_trans);
			}
		}		
	}
	
	
	private void displayFixRadials(int i, float cdufix_x, float cdufix_y) {
		
		int fix_rad = 0;
				
		if(this.xpd.fix_rad_dist_0a()[i] == 1f) {
			
			switch (i) {
				case 0: fix_rad = Integer.parseInt(this.xpd.fix_rad00_0());
					break;
				case 1: fix_rad = Integer.parseInt(this.xpd.fix_rad01_0());
					break;
				case 2: fix_rad = Integer.parseInt(this.xpd.fix_rad02_0());
					break;
				case 3: fix_rad = Integer.parseInt(this.xpd.fix_rad03_0());
					break;
				case 4: fix_rad = Integer.parseInt(this.xpd.fix_rad04_0());
					break;
			}
			
			int dist = (int) (720 * pixels_per_nm);
			
			float radial = (360 + fix_rad - this.xpd.magnetic_variation()) % 360;
			
			temp_trans = g2.getTransform();
			
			g2.setStroke(gc.runway_center_line);
			g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
			
			g2.rotate((float) Math.toRadians(radial), cdufix_x, cdufix_y);
			g2.drawLine((int)cdufix_x, (int)cdufix_y - dist, (int)cdufix_x, (int)cdufix_y);
			
			g2.rotate((float) Math.toRadians(90), cdufix_x, cdufix_y);
			g2.drawString(String.format("%03d", fix_rad), (int) (cdufix_x - (this.rose_radius / 2) * gc.scalex), (int) (cdufix_y - 10 * gc.scaley));
			
			g2.setTransform(temp_trans);
			
		}	
		
				
		if(this.xpd.fix_rad_dist_1a()[i] == 1f) {
			
			switch (i) {
				case 0: fix_rad = Integer.parseInt(this.xpd.fix_rad00_1());
					break;
				case 1: fix_rad = Integer.parseInt(this.xpd.fix_rad01_1());
					break;
				case 2: fix_rad = Integer.parseInt(this.xpd.fix_rad02_1());
					break;
				case 3: fix_rad = Integer.parseInt(this.xpd.fix_rad03_1());
					break;
				case 4: fix_rad = Integer.parseInt(this.xpd.fix_rad04_1());
					break;
			}
			
			int dist = (int) (720 * pixels_per_nm);
			
			float radial = (360 + fix_rad - this.xpd.magnetic_variation()) % 360;
			
			temp_trans = g2.getTransform();
			
			g2.setStroke(gc.runway_center_line);
			g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
			
			g2.rotate((float) Math.toRadians(radial), cdufix_x, cdufix_y);
			g2.drawLine((int)cdufix_x, (int)cdufix_y - dist, (int)cdufix_x, (int)cdufix_y);
			
			g2.rotate((float) Math.toRadians(90), cdufix_x, cdufix_y);
			g2.drawString(String.format("%03d", fix_rad), (int) (cdufix_x - (this.rose_radius / 2) * gc.scalex), (int) (cdufix_y - 10 * gc.scaley));
			
			g2.setTransform(temp_trans);
			
		}	
		
				
		if(this.xpd.fix_rad_dist_2a()[i] == 1f) {
			
			switch (i) {
				case 0: fix_rad = Integer.parseInt(this.xpd.fix_rad00_2());
					break;
				case 1: fix_rad = Integer.parseInt(this.xpd.fix_rad01_2());
					break;
				case 2: fix_rad = Integer.parseInt(this.xpd.fix_rad02_2());
					break;
				case 3: fix_rad = Integer.parseInt(this.xpd.fix_rad03_2());
					break;
				case 4: fix_rad = Integer.parseInt(this.xpd.fix_rad04_2());
					break;
			}
			
			int dist = (int) (720 * pixels_per_nm);
			
			float radial = (360 + fix_rad - this.xpd.magnetic_variation()) % 360;
			
			temp_trans = g2.getTransform();
			
			g2.setStroke(gc.runway_center_line);
			g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
			
			g2.rotate((float) Math.toRadians(radial), cdufix_x, cdufix_y);
			g2.drawLine((int)cdufix_x, (int)cdufix_y - dist, (int)cdufix_x, (int)cdufix_y);
			
			g2.rotate((float) Math.toRadians(90), cdufix_x, cdufix_y);
			g2.drawString(String.format("%03d", fix_rad), (int) (cdufix_x - (this.rose_radius / 2) * gc.scalex), (int) (cdufix_y - 10 * gc.scaley));
			
			g2.setTransform(temp_trans);
			
		}	
		
	}	
	
	private void showTunedNavAids() {

		g2.clip(map_clip);

		if(this.map_mode || this.map_ctr_mode || this.pln_mode) {
			
			main_map = g2.getTransform();
			
			if (this.nav1 != null) {
				main_map = g2.getTransform();
				drawNavStation(this.nav1, this.xpd.course("cpt"), this.xpd.back_course("cpt"), this.xpd.nav1_rel_bearing());
				g2.setTransform(main_map);
			}
			
			if (this.nav2 != null) {
				main_map = g2.getTransform();
				drawNavStation(this.nav2, this.xpd.course("fo"), this.xpd.back_course("fo"), this.xpd.nav2_rel_bearing());
				g2.setTransform(main_map);				
			}
			
		}
		
		g2.setClip(original_clipshape);
	}

	private void drawNavStation(RadioNavBeacon station, int crs, int bcrs, float rel_bearing) {
		
		int course_line = (int) (station.range * this.pixels_per_nm * 1.5f);
		
		float rotate = (float) Math.toRadians((360 - this.xpd.magnetic_variation()) % 360);
		float rotate_text = (float) Math.toRadians((360 + crs + 90) % 360);
		
		if (!this.pln_mode) {
			rotate = (float) Math.toRadians((360 + this.xpd.track() - this.xpd.magnetic_variation()) % 360);
			rotate_text = (float) Math.toRadians((360 - this.xpd.track() + crs + 90) % 360);
		}				
		
		g2.transform(AffineTransform.getRotateInstance(Math.toRadians(map_up), map_center_x, map_center_y));
		map_projection.setPoint(station.lat, station.lon);
		float nav_x = map_projection.getX(); 
		float nav_y = map_projection.getY();
		
		g2.rotate(rotate, nav_x, nav_y);
				
		if (station.type == RadioNavBeacon.TYPE_VOR) {
			
			if (station.has_dme) {
							
				// display nav aid				
				g2.setColor(gc.color_lime);
				gc.displayNavAid(rs.img_nd_vortac_tuned, nav_x, nav_y, g2);

				// display station ilt
				g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
				g2.drawString(station.ilt, nav_x - rs.img_nd_vortac_tuned.getWidth() + rs.img_nd_vortac_tuned.getWidth() * 0.75f, nav_y - rs.img_nd_vortac_tuned.getHeight() / 3f);						
				
			} else {
				
				// display nav aid
				g2.setColor(gc.color_lime);
				gc.displayNavAid(rs.img_nd_vor_tuned, nav_x, nav_y, g2);
				
				// display station ilt
				g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
				g2.drawString(station.ilt, nav_x - rs.img_nd_vor_tuned.getWidth() + rs.img_nd_vor_tuned.getWidth() * 0.75f, nav_y - rs.img_nd_vor_tuned.getHeight() / 3f);
			
			}
			
			// display text
			g2.rotate(rotate_text, nav_x, nav_y);
			g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));
			g2.drawString(String.format("%03d", crs), (int) (nav_x - 100 * gc.scalex), (int) (nav_y - 10 * gc.scaley));
			g2.drawString(String.format("%03d", bcrs), (int) (nav_x + 80 * gc.scalex), (int) (nav_y - 10 * gc.scaley));
			
			// display dashed line
			g2.rotate((float) Math.toRadians(90), nav_x, nav_y);
			g2.setStroke(gc.vor_dashes);
			g2.drawLine((int)nav_x, (int)nav_y - course_line, (int)nav_x, (int)nav_y);
			g2.drawLine((int)nav_x, (int)nav_y, (int)nav_x, (int)nav_y + course_line);
			
		} else if (station.type == RadioNavBeacon.TYPE_STANDALONE_DME) {

			// draw standalone dme
			g2.setColor(gc.color_lime);
			gc.displayNavAid(rs.img_nd_dme_tuned, nav_x, nav_y, g2);
			
			// display station ilt
			g2.setFont(rs.glassFont.deriveFont(24f * gc.scaling_factor));
			g2.drawString(station.ilt, nav_x - rs.img_nd_dme_tuned.getWidth() + rs.img_nd_dme_tuned.getWidth() * 0.75f, nav_y - rs.img_nd_dme_tuned.getHeight() / 3f);
			
		}		
	}

	private void draw_nav_objects(int type, ArrayList<NavigationObject> nav_objects) {

		NavigationObject navobj = null;
		RadioNavBeacon rnb = null;

		float min_rwy = Float.parseFloat(this.preferences.get_preference(ZHSIPreferences.PREF_ARPT_MIN_RWY_LENGTH));

		for (int i = 0; i < nav_objects.size(); i++) {
			navobj = nav_objects.get(i);
			map_projection.setPoint(navobj.lat, navobj.lon);
			float x = map_projection.getX();
			float y = map_projection.getY();
			double dist = Math.hypot(x - this.map_center_x, y - this.map_center_y); // distance in px
			float max_dist = rose_radius * 2f;
			if (dist < max_dist) {
				if (type == NavigationObject.NO_TYPE_AIRPORT) {
					if (((Airport) navobj).longest >= min_rwy) {
						drawAirport(x, y, (Airport) navobj);
					}
				}else if (type == NavigationObject.NO_TYPE_VOR) {
					rnb = (RadioNavBeacon) navobj;
					if (rnb.type == RadioNavBeacon.TYPE_VOR) {
						if (rnb.has_dme) {
							drawVORDME(x, y, (RadioNavBeacon) navobj); //hexagon with leaves
						}else {
							drawVOR(x, y, (RadioNavBeacon) navobj); //hexagon
						}
					}else if (rnb.type == RadioNavBeacon.TYPE_STANDALONE_DME) {
						drawDME(x, y, (RadioNavBeacon) navobj); // Y symbol
					}
				}else if (type == NavigationObject.NO_TYPE_FIX) {
					drawFix(x, y, (Fix) navobj);				
				} 
			}
		}		
	}
	
	private void drawCduFix(float x, float y, float type, String name) { // for CDU fixes only

		fix_trans = g2.getTransform();
		g2.rotate(Math.toRadians(this.map_up * -1), x, y);
		g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));
		g2.setColor(gc.color_lime);
		switch ((int)type) {
			case 1: gc.displayNavAid(rs.img_nd_vor_fix, x, y, g2);
					break;
			case 2:	gc.displayNavAid(rs.img_nd_vortac_fix, x, y, g2);
					break;
			case 3:	gc.displayNavAid(rs.img_nd_dme_fix, x, y, g2);
					break;
			case 4:	gc.displayNavAid(rs.img_nd_wpt_fix, x, y, g2);
					break;
			case 5:	gc.displayNavAid(rs.img_nd_dme_fix, x, y, g2);
					break;
			case 9:	gc.displayNavAid(rs.img_nd_arpt_fix, x, y, g2);
					break;
			default:gc.displayNavAid(rs.img_nd_vor_fix, x, y, g2);
					break;
		}
		g2.drawString(name, (int)(x + (30 * gc.scalex)), (int)(y + (30 * gc.scaley)));
		g2.setTransform(fix_trans);

	}
	
	private void drawFix(float x, float y, Fix fix) {

		fix_trans = g2.getTransform();
		g2.rotate(Math.toRadians(this.map_up * -1), x, y);
		g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));		
		g2.setColor(gc.color_navaid);
		gc.displayNavAid(rs.img_nd_wpt, x, y, g2);
		g2.drawString(fix.name, (int)(x + (30 * gc.scalex)), (int)(y + (30 * gc.scaley)));
		g2.setTransform(fix_trans);

	}

	private void drawDME(float x, float y, RadioNavBeacon dme) {// Y symbol
		
		if(nav1 != null && dme.ilt.equals(nav1.ilt)) {
			tunedDME1 = nav1.ilt;
		}else {
			tunedDME1 = "";
		}
		if(nav2!= null && dme.ilt.equals(nav2.ilt)) {
			tunedDME2 = nav2.ilt;
		}else {
			tunedDME2 = "";
		}
		
		if(!(dme.ilt.equals(tunedDME1) || dme.ilt.equals(tunedDME2))) {
			vordme_trans = g2.getTransform();
			g2.rotate(Math.toRadians(this.map_up * -1), x, y);
			g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));			
			g2.setColor(gc.color_navaid);
			gc.displayNavAid(rs.img_nd_dme, x, y, g2);
			g2.drawString(dme.ilt, (int)(x + (30 * gc.scalex)), (int)(y + (30 * gc.scaley)));
			g2.setTransform(vordme_trans);
		}		
	}

	private void drawVOR(float x, float y, RadioNavBeacon vor) { // hexagon

		if(nav1 != null && vor.ilt.equals(nav1.ilt)) {
			tunedVOR1 = nav1.ilt;
		}else {
			tunedVOR1 = "";
		}
		if(nav2!= null && vor.ilt.equals(nav2.ilt)) {
			tunedVOR2 = nav2.ilt;
		}else {
			tunedVOR2 = "";
		}
		
		if(!(vor.ilt.equals(tunedVOR1) || vor.ilt.equals(tunedVOR2))) {
			vor_trans = g2.getTransform();
			g2.rotate(Math.toRadians(this.map_up * -1), x, y);
			g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));			
			g2.setColor(gc.color_navaid);
			gc.displayNavAid(rs.img_nd_vor, x, y, g2);
			g2.drawString(vor.ilt, (int)(x + (30 * gc.scalex)), (int)(y + (30 * gc.scaley)));
			g2.setTransform(vor_trans);		
		}
	}

	private void drawVORDME(float x, float y, RadioNavBeacon vordme) { // hexagon with leaves

		if(nav1 != null && vordme.ilt.equals(nav1.ilt)) {
			tunedVOR1dme = nav1.ilt;
		}else {
			tunedVOR1dme = "";
		}
		if(nav2!= null && vordme.ilt.equals(nav2.ilt)) {
			tunedVOR2dme = nav2.ilt;
		}else {
			tunedVOR2dme = "";
		}
				
		if(!(vordme.ilt.equals(tunedVOR1dme) || vordme.ilt.equals(tunedVOR2dme))) {	
			vordme_trans = g2.getTransform();
			g2.rotate(Math.toRadians(this.map_up * -1), x, y);
			g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));		
			g2.setColor(gc.color_navaid);
			gc.displayNavAid(rs.img_nd_vortac, x, y, g2);		
			g2.drawString(vordme.ilt, (int)(x + (30 * gc.scalex)), (int)(y + (30 * gc.scaley)));
			g2.setTransform(vordme_trans);
		}		
	}
	
	private void drawAirport(float x, float y, Airport airport) {
		
		airport_trans = g2.getTransform();
		g2.rotate(Math.toRadians(this.map_up * -1), x, y);
		g2.setFont(rs.glassFont.deriveFont(26f * gc.scaling_factor));		
		g2.setColor(gc.color_navaid);
		gc.displayNavAid(rs.img_nd_arpt, x, y, g2);				
		g2.drawString(airport.icao_code, (int)(x + (30 * gc.scalex)), (int)(y + (30 * gc.scaley)));		
		g2.setTransform(airport_trans);
	}

	private void drawVorAdf() {

		int vor1_efis_pos = this.xpd.vor1_pos(pilot);
		int vor2_efis_pos = this.xpd.vor2_pos(pilot);

		String nav1_type = "VOR 1";
		Color nav1_color = gc.color_lime;
		String nav1_id = "";
		String nav1_dme = "---";
		String nav2_type = "VOR 2";
		Color nav2_color = gc.color_lime;
		String nav2_id = "";
		String nav2_dme = "---";
		String fmcSource = "FMC L";

		// backgrounds
		temp_trans = g2.getTransform();
		g2.setColor(Color.BLACK);
		g2.scale(gc.scalex, gc.scaley);
		if (vor1_efis_pos != 0) {
			g2.fillRect(0, 930, 150, 199); // VOR1
		}
		if (vor2_efis_pos != 0) {
			g2.fillRect(905, 930, 170, 199); // VOR2
		}
		if (this.xpd.fmc_source(pilot) == 2) {
			fmcSource = "FMC R";
		}
		if (this.map_mode || this.map_ctr_mode || this.pln_mode) {
			g2.fillRect(149, 1003, 105, 50); // FMC source
		}
		if ((this.map_mode || this.map_ctr_mode) && this.xpd.rnp() > 0) {
			g2.fillRect(445, 983, 65, 70); // RNP
			g2.fillRect(565, 983, 65, 70); // ANP
		}
		if ((this.map_mode || this.map_ctr_mode) && this.xpd.rnp() > 0 && this.xpd.anp() <= this.xpd.rnp() &&
			((xpd.map_range(pilot) >= 2 && this.xpd.xtrack_nd() >= -99.0 && this.xpd.xtrack_nd() <= 99) ||
			(xpd.map_range(pilot) < 2 && this.xpd.xtrack_nd() >= -9.99 && this.xpd.xtrack_nd() <= 9.99))) {
			if (isMapCenter) {
				g2.fillRect(495, 930 - 328, 85, 40); // XTRACK_ND
			} else {
				g2.fillRect(495, 930, 85, 40); // XTRACK_ND
			}
		}
		
		g2.setTransform(temp_trans);

		if (this.nav1 != null) {
			nav1_id = this.nav1.ilt;
			if (this.nav1.has_dme) {
				nav1_dme = gc.dme_formatter.format(xpd.nav1_dme_nm());
			}
			if (this.nav1.type == RadioNavBeacon.TYPE_NDB && !this.xpd.map_mode_pln(pilot)) {
				if(isMapCenter) {
					gc.displayImage(rs.img_Adf1_Arrow_ctr, this.xpd.adf1_rel_bearing(), 516.048f, 132.152f, g2);
				}else {
					gc.displayImage(rs.img_Adf1_Arrow, this.xpd.adf1_rel_bearing(), 516.048f, -587.912f, g2);
				}
			} else if (this.nav1.type == RadioNavBeacon.TYPE_VOR && !this.xpd.map_mode_pln(pilot)) {
				if(isMapCenter) {
					gc.displayImage(rs.img_Vor1_Arrow_ctr, this.xpd.nav1_rel_bearing(), 516.048f, 132.152f, g2);
				}else {
					gc.displayImage(rs.img_Vor1_Arrow, this.xpd.nav1_rel_bearing(), 516.048f, -587.912f, g2);
				}

			}
		} else {
			if (vor1_efis_pos == 1) {
				nav1_id = gc.nav_freq_formatter.format(this.xpd.nav1_freq() / 100);
			} else {
				nav1_id = gc.adf_freq_formatter.format(this.xpd.adf1_freq());
			}
		}
		if (this.nav2 != null) {
			nav2_id = this.nav2.ilt;
			if (this.nav2.has_dme) {
				nav2_dme = gc.dme_formatter.format(xpd.nav2_dme_nm());
			}
			if (this.nav2.type == RadioNavBeacon.TYPE_NDB && !this.xpd.map_mode_pln(pilot)) {
				if(isMapCenter) {
					gc.displayImage(rs.img_Adf2_Arrow_ctr, this.xpd.adf2_rel_bearing(), 516.048f, 132.152f, g2);
				}else {
					gc.displayImage(rs.img_Adf2_Arrow, this.xpd.adf2_rel_bearing(), 516.048f, -587.912f, g2);
				}

			} else if (this.nav2.type == RadioNavBeacon.TYPE_VOR && !this.xpd.map_mode_pln(pilot)) {
				if(isMapCenter) {
					gc.displayImage(rs.img_Vor2_Arrow_ctr, this.xpd.nav2_rel_bearing(), 516.048f, 132.152f, g2);
				}else {
					gc.displayImage(rs.img_Vor2_Arrow, this.xpd.nav2_rel_bearing(), 516.048f, -587.912f, g2);
				}		
			}
		} else {
			if (vor2_efis_pos == 1) {
				nav2_id = gc.nav_freq_formatter.format(this.xpd.nav2_freq() / 100);
			} else {
				nav2_id = gc.adf_freq_formatter.format(this.xpd.adf2_freq());
			}
		}
		if (vor1_efis_pos == 1) {
			nav1_type = "VOR 1";
			nav1_color = gc.color_lime;
		} else {
			nav1_type = "ADF 1";
			nav1_color = gc.color_navaid;
		}

		if (vor2_efis_pos == 1) {
			nav2_type = "VOR 2";
			nav2_color = gc.color_lime;
		} else {
			nav2_type = "ADF 2";
			nav2_color = gc.color_navaid;
		}

		if (!this.pln_mode) {
			// VOR1 / ADF1
			if (vor1_efis_pos != 0) {
				gc.drawText2(nav1_type, 10, 90, 30, 0, nav1_color, false, "left", g2);
				gc.drawText2(nav1_id, 10, 55, 30, 0, nav1_color, false, "left", g2);
			}

			if (vor1_efis_pos == 1) {
				gc.drawText2("DME", 10, 20, 24, 0, nav1_color, false, "left", g2);
				gc.drawText2(nav1_dme, 75, 20, 30, 0, nav1_color, false, "left", g2);
			}

			// VOR2 / ADF2
			if (vor2_efis_pos != 0) {
				gc.drawText2(nav2_type, 908, 90, 30, 0, nav2_color, false, "left", g2);
				gc.drawText2(nav2_id, 908, 55, 30, 0, nav2_color, false, "left", g2);
			}
			if (vor2_efis_pos == 1) {
				gc.drawText2("DME", 908, 20, 24, 0, nav2_color, false, "left", g2);
				gc.drawText2(nav2_dme, 973, 20, 30, 0, nav2_color, false, "left", g2);
			}
		}

		if (this.map_mode || this.map_ctr_mode || this.pln_mode) {
			gc.drawText2(fmcSource, 175, 20, 24, 0, gc.color_lime, false, "left", g2);
		}

		// ANP RNP
		if (this.xpd.rnp() > 0) {
			Color color;
			if (this.xpd.anp() > this.xpd.rnp()) {
				color = gc.color_amber;
			} else {
				color = gc.color_lime;
			}
			if (this.map_mode || this.map_ctr_mode) {
				gc.drawText2("RNP", 478, 45, 24, 0, color, false, "center", g2);
				gc.drawText2(gc.mfd_ff.format(this.xpd.rnp()), 450, 20, 24, 0, color, false, "left", g2);
				gc.drawText2("ANP", 598, 45, 24, 0, color, false, "center", g2);
				gc.drawText2(gc.mfd_ff.format(Math.round(this.xpd.anp() * 100f) / 100f), 570, 20, 24, 0, color, false, "left", g2);
			}
		}
		
		// XTRACK_ND
		if ((this.map_mode || this.map_ctr_mode) && this.xpd.rnp() > 0 && this.xpd.anp() <= this.xpd.rnp() &&
			((xpd.map_range(pilot) >= 2 && this.xpd.xtrack_nd() >= -99.0 && this.xpd.xtrack_nd() <= 99) ||
			(xpd.map_range(pilot) < 2 && this.xpd.xtrack_nd() >= -9.99 && this.xpd.xtrack_nd() <= 9.99))) { 
			float xtrack_nd = this.xpd.xtrack_nd();
			if (xtrack_nd < 0) {
				xtrack_nd = xtrack_nd * -1;
			}
			int xtrack_nd_y = 95;
			if (isMapCenter) {
				xtrack_nd_y = xtrack_nd_y + 328;
			}
			if (xpd.map_range(pilot) >= 2) {
				gc.drawText2(String.format("%3.1f", xtrack_nd), 555, xtrack_nd_y, 24, 0, gc.color_markings, true, "right", g2);
			} else {
				gc.drawText2(String.format("%3.2f", xtrack_nd), 555, xtrack_nd_y, 24, 0, gc.color_markings, true, "right", g2);
			}
			if (this.xpd.xtrack_nd() <= -0.005) {
				gc.drawText2("L", 575, xtrack_nd_y, 24, 0, gc.color_markings, true, "right", g2);
			}
			if (this.xpd.xtrack_nd() >= 0.005) {
				gc.drawText2("R", 575, xtrack_nd_y, 24, 0, gc.color_markings, true, "right", g2);
			}
		}
	}

	private void drawCompassRose() {

		String heading_track;
		compass_rotate = 0f;
		heading_bug_rotate = 0f;
		current_heading_bug_rotate = 0f;
		String display_heading;

		if ((this.map_mode || this.map_ctr_mode) && this.xpd.track_up() == 1) {
			display_heading = gc.df3.format(this.xpd.track());
			compass_rotate = this.xpd.track() * -1;
			heading_bug_rotate = this.xpd.mcp_hdg() - this.xpd.track();
			heading_track = "TRK";
			current_heading_bug_rotate = this.xpd.heading(pilot) - this.xpd.track();
			track_line_rotate = 0f;
		} else {
			display_heading = gc.df3.format(this.xpd.heading(pilot));
			compass_rotate = this.xpd.heading(pilot) * -1;
			heading_bug_rotate = this.xpd.mcp_hdg() - this.xpd.heading(pilot);
			heading_track = "HDG";
			current_heading_bug_rotate = 0f;
			track_line_rotate = this.xpd.track() - this.xpd.heading(pilot);
		}
		
		if(this.map_ctr_mode || this.app_ctr_mode || this.vor_ctr_mode) {
			heading_box_offset = 51;
		} else {
			heading_box_offset = 0;
		}

		if(!this.pln_mode) {
			
			g2.translate(this.map_center_x, 0);
			g2.scale(gc.scalex, gc.scaley);
			g2.setColor(Color.BLACK);
			g2.fillRect(-125, 8 + heading_box_offset , 245, 60);
			g2.setColor(gc.color_markings);
			g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
			int[] trackBox_xPoints = {-60, -60, 60, 60};
			int[] trackBox_yPoints = {10 + heading_box_offset, 65 + heading_box_offset, 65 + heading_box_offset, 10 + heading_box_offset};
			g2.drawPolyline(trackBox_xPoints, trackBox_yPoints, trackBox_xPoints.length);
			g2.setTransform(original_trans);

			if(this.xpd.irs_aligned()) {

				gc.drawText2(display_heading, 535f, 1000f - heading_box_offset, 48f, 0f, gc.color_markings, false, "center", g2);
				gc.drawText2(heading_track, 410f, 1000f - heading_box_offset, 30f, 0f, gc.color_lime, false, "left", g2);
				gc.drawText2("MAG", 600f, 1000f - heading_box_offset, 30f, 0f, gc.color_lime, false, "left", g2);
				g2.rotate(Math.toRadians(current_heading_bug_rotate), this.map_center_x, this.map_center_y);
				g2.scale(gc.scalex, gc.scaley);
				g2.translate(536 , 854.5);
				int[] cur_heading_bug_xPoints = {0, -15, 15, 0};
				int[] cur_heading_bug_yPoints = {-758 + heading_box_offset, -785 + heading_box_offset, -785 + heading_box_offset, -758 + heading_box_offset};
				g2.drawPolyline(cur_heading_bug_xPoints, cur_heading_bug_yPoints, 4);
				g2.setTransform(original_trans);
			
			}
			
			if(!isMapCenter) {
				
				g2.translate(this.map_center_x, this.map_center_y);
				g2.scale(gc.scalex, gc.scaley);
				g2.setColor(gc.color_markings);
				g2.setStroke(rs.stroke5);
				range_arc1.setArcByCenter(0, 0, 189.5f, 0f, 180f, Arc2D.OPEN);//range arcs
				range_arc2.setArcByCenter(0, 0, 379f, 0f, 180f, Arc2D.OPEN);//range arcs
				range_arc3.setArcByCenter(0, 0, 568.5f, 0f, 180f, Arc2D.OPEN); //range arcs
				
				if((this.xpd.tcas_on(pilot) || this.xpd.efis_terr_on(pilot) || this.xpd.efis_wxr_on(pilot))) {
					g2.draw(range_arc1);//range arcs
					g2.draw(range_arc2);//range arcs
					g2.draw(range_arc3);//range arcs
				}
				
				g2.setTransform(original_trans);
				
				if(this.xpd.irs_aligned()) {
				
					g2.scale(gc.scalex, gc.scaley);
								
					g2.translate(-222.5, 96);
					
					rs._compassRose.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
							RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					rs._compassRose.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					rs._compassRose.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					rs._compassRose.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
							RenderingHints.VALUE_FRACTIONALMETRICS_ON);

					compassRoseTransform = rs._compassRose.getTransform();

					rs._compassRose.setComposite(AlphaComposite.Clear);					
					rs._compassRose.fillRect(0, 0, 1517, 450);					
					rs._compassRose.setComposite(AlphaComposite.Src);
					rs._compassRose.translate(758.5, 758.5);
					rs._compassRose.setColor(rs.color_markings);
					rs._compassRose.setStroke(rs.stroke5);
					rs._compassRose.setFont(rs.glass34);
					
					int hdg5 = (int) Math.round(-compass_rotate / 5.0f) * 5;

					rs._compassRose.rotate(Math.toRadians(-50f), 0, 0);

					for (int i = -50; i <= 50; i += 5) {

						int mark5 = ((hdg5 + i) + 360) % 360;

						if (mark5 % 10 == 0) {

							String marktext = "" + mark5 / 10;
							rs._compassRose.drawLine(0, -712, 0, -756);

							if (mark5 % 30 == 0) {

								// draw headings

								if (this.xpd.irs_aligned()) {
									if (mark5 > 100) {
										rs._compassRose.drawString(marktext, -22f, -675f);
									} else {
										rs._compassRose.drawString(marktext, -11f, -675f);
									}
								}
							}

						} else {

							rs._compassRose.drawLine(0, -740, 0, -756);

						}

						rs._compassRose.rotate(Math.toRadians(5f), 0, 0);

					}

					rs._compassRose.setTransform(compassRoseTransform);

					g2.rotate(Math.toRadians((hdg5 - -compass_rotate)), 758.5, 758.5);
					g2.drawImage(rs.img_CompassRose, 0, 0, null);
					g2.setTransform(original_trans);

					g2.translate(this.map_center_x, this.map_center_y);
					g2.scale(gc.scalex, gc.scaley);
					g2.setColor(rs.color_markings);
					g2.setStroke(rs.stroke5);
					compass_arc1.setArcByCenter(0, 0, 760f, 0f, 360f, Arc2D.CHORD); //
					g2.draw(compass_arc1);
					g2.setTransform(original_trans);
				
				}

			} else {
				
				//ctr mode
				if(this.app_ctr_mode || this.vor_ctr_mode) {
					g2.translate(this.map_center_x, this.map_center_y);
					g2.scale(gc.scalex, gc.scaley);

					g2.setStroke(stroke5);
					g2.setColor(gc.color_markings);
					g2.rotate(Math.toRadians(45f), 0, 0);
					g2.drawLine(0, -380, 0, -435);
					g2.rotate(Math.toRadians(45f), 0, 0);
					g2.drawLine(0, -380, 0, -435);
					g2.rotate(Math.toRadians(45f), 0, 0);
					g2.drawLine(0, -380, 0, -435);
					g2.rotate(Math.toRadians(45f), 0, 0);
					g2.drawLine(0, -380, 0, -435);
					g2.rotate(Math.toRadians(45f), 0, 0);
					g2.drawLine(0, -380, 0, -435);
					g2.rotate(Math.toRadians(45f), 0, 0);
					g2.drawLine(0, -380, 0, -435);
					g2.rotate(Math.toRadians(45f), 0, 0);
					g2.drawLine(0, -380, 0, -435);
					g2.setTransform(original_trans);
				}
				gc.displayImage(rs.img_ctr_compass_rose, compass_rotate, 161, 151.500, g2);
			}
		
		}

		if(this.pln_mode) {
			
			g2.translate(this.map_center_x, this.map_center_y);
			g2.scale(gc.scalex, gc.scaley);
			g2.setColor(gc.color_markings);
			g2.setStroke(stroke5);
			pln_mode_circle1.setArcByCenter(0, 0, 212.5f, 0f, 360f, Arc2D.OPEN);
			pln_mode_circle2.setArcByCenter(0, 0, 425f, 0f, 360f, Arc2D.OPEN);
			g2.draw(pln_mode_circle1);
			g2.draw(pln_mode_circle2);
			g2.setColor(Color.BLACK);
			g2.fillRect(-40, 410, 80, 30);
			g2.fillRect(-40, 195, 80, 30);
			g2.fillRect(-40, -225, 80, 30);
			g2.fillRect(-40, -440, 80, 30);
			g2.setTransform(original_trans);
			
			// north east south west
			
			g2.setColor(gc.color_lime);
			g2.translate(this.getWidth() /2,  this.getHeight() /2);
			g2.scale(gc.scalex, gc.scaley);
			
			g2.setFont(rs.glass44);
			
			g2.drawString("N", -34, -460);
			g2.drawString("E", 467, 20);
			g2.drawString("S", -16, 498);
			g2.drawString("W", -503, 20);
			
			//arrow
			g2.setStroke(rs.stroke5round);
			
			g2.drawLine(14, -460, 14, -498);
			g2.drawLine(5, -490, 14 , -498);
			g2.drawLine(23, -490, 14 , -498);

			g2.setTransform(original_trans);

			gc.drawText2("" + (int)this.max_range, 532f, 940f, 26f, 0f, gc.color_markings, false, "center", g2);
			gc.drawText2(map_range, 532f, 727.375f, 26f, 0f, gc.color_markings, false, "center", g2);
			gc.drawText2(map_range, 532f, 306.375f, 26f, 0f, gc.color_markings, false, "center", g2);
			gc.drawText2("" + (int)this.max_range, 532f, 93f, 26f, 0f, gc.color_markings, false, "center", g2);

			g2.clip(map_clip);
			g2.transform(AffineTransform.getRotateInstance(Math.toRadians(map_up), map_center_x, map_center_y));
			g2.rotate(Math.toRadians(this.xpd.track() - this.xpd.magnetic_variation()), plane_x, plane_y);
			g2.drawImage(rs.img_plane_pln, (int)(plane_x - (rs.img_plane_pln.getWidth() * gc.scalex)/2),  (int)(plane_y - 10 * gc.scaley) , (int)(rs.img_plane_pln.getWidth() * gc.scalex), (int)(rs.img_plane_pln.getHeight() * gc.scaley), null);
			g2.setTransform(original_trans);
			g2.setClip(original_clipshape);
			
		}

		//green arc
		g2.scale(gc.scalex, gc.scaley);
		g2.translate(1072 / 2, 1053 - 198.55);
		g2.setColor(gc.color_lime);
		if (this.xpd.green_arc_show(pilot)) {
			if (this.xpd.efis_vsd_map(pilot)) {
				altRangeArc.setArcByCenter(0, 0 + 350f - (605f * this.xpd.green_arc(pilot)) , 350f, 62, 56, Arc2D.OPEN);
			} else if (isMapCenter) {
				altRangeArc.setArcByCenter(0, 0 + 350f - (700f * this.xpd.green_arc(pilot)) , 350f, 62, 56, Arc2D.OPEN);
			} else {
				altRangeArc.setArcByCenter(0, 0 + 350f - (680f * this.xpd.green_arc(pilot)) , 350f, 62, 56, Arc2D.OPEN);
			}
				
			g2.draw(altRangeArc);
		}
		g2.setTransform(original_trans);

		// selected course line
		
		if (this.vor_mode || this.vor_ctr_mode) {
			
			if (this.vor_mode) {
				gc.displayImage(rs.img_Selected_Course_Line, (this.xpd.course(pilot) - this.xpd.heading(pilot))%360, 365.050f, -561.099f, g2);
			} else {
				gc.displayImage(rs.img_Selected_Course_Line_ctr, (this.xpd.course(pilot) - this.xpd.heading(pilot))%360, 365.050f, 179.309f, g2);
			}
			
			if (!this.xpd.efis_disagree(this.pilot)) {

				g2.translate(this.map_center_x, this.map_center_y);
				g2.scale(gc.scalex, gc.scaley);
				g2.setColor(gc.color_magenta);
				g2.rotate(Math.toRadians((this.xpd.course(pilot) - this.xpd.heading(pilot))%360), 0, 0);

				if (this.pilot == "cpt" && nav1_object != null && nav1_radio.freq_is_nav()) {
					g2.translate(0 + (this.xpd.nav1_hdef_dot() * 80f), 0);
					g2.drawRect(-8, -100, 16, 200);
				}
				
				if (this.pilot == "fo" && nav2_object != null && nav2_radio.freq_is_nav()) {
					g2.translate(0 + (this.xpd.nav2_hdef_dot() * 80f), 0);
					g2.drawRect(-8, -100, 16, 200);
				}
				
				g2.setTransform(original_trans);
				
				if (this.pilot == "cpt" && nav1_object != null && !nav1_radio.freq_is_localizer()) {
					if (vorTo) {
						gc.drawText2("TO", 800, 150, 30f, 0, gc.color_markings, true, "left", g2);
					} else {
						gc.drawText2("FROM", 800, 150, 30f, 0, gc.color_markings, true, "left", g2);
					}
				} else if (this.pilot == "fo" && nav2_object != null && !nav2_radio.freq_is_localizer()){
					if (vorTo) {
						gc.drawText2("TO", 800, 150, 30f, 0, gc.color_markings, true, "left", g2);
					} else {
						gc.drawText2("FROM", 800, 150, 30f, 0, gc.color_markings, true, "left", g2);
					}
				}
			
			}
			
		} else if (this.app_mode || this.app_ctr_mode) {
			
			if (this.app_mode) {
				gc.displayImage(rs.img_Selected_Course_Line, (this.xpd.course(pilot) - this.xpd.heading(pilot))%360, 365.050f, -561.099f, g2);
			} else {
				gc.displayImage(rs.img_Selected_Course_Line_ctr, (this.xpd.course(pilot) - this.xpd.heading(pilot))%360, 365.050f, 179.309f, g2);
			}
			
			if (!this.xpd.efis_disagree(this.pilot)) {
				
				g2.translate(this.map_center_x, this.map_center_y);
				g2.scale(gc.scalex, gc.scaley);
				g2.setColor(gc.color_magenta);
				g2.rotate(Math.toRadians((this.xpd.course(pilot) - this.xpd.heading(pilot))%360), 0, 0);
				
				if (this.pilot == "cpt") {
					
					// loc fac
					if (this.xpd.pfd_fac_horizontal(pilot) >= 1 && (this.xpd.pfd_fac_ghost(pilot) == 0 || this.xpd.pfd_fac_ghost(pilot) == 1)) {
						g2.translate(0 + (this.xpd.fac_horizont() * -80f), 0);
						if (this.xpd.fac_horizont() >= -2.49f && this.xpd.fac_horizont() <= 2.49f) {
							g2.fillRect(-8, -100, 16, 200);
						} else {
							g2.drawRect(-8, -100, 16, 200);
						}
					}
					
					// loc nav1
					if (this.xpd.vhf_nav_source() == 0 && this.xpd.mmr_act_mode(this.pilot) == 1 && this.xpd.rel_gls() <= 5) {
						if (this.xpd.ils_pointer_disable() <= 1 && this.xpd.nav1_no_id() == 0 && this.xpd.nav1_display_horizontal() == 1) {
							if (this.xpd.pfd_loc_ghost(pilot) == 0 || this.xpd.pfd_loc_ghost(pilot) == 1) {
								g2.translate(0 + (this.xpd.nav1_hdef_dot() * 80f), 0);
								if (this.xpd.nav1_hdef_dot() >= -2.49f && this.xpd.nav1_hdef_dot() <= 2.49f) {
									g2.fillRect(-8, -100, 16, 200);
								} else {
									g2.drawRect(-8, -100, 16, 200);
								}
							}
						}
					}
					
					// loc gls
					if (this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() == 0 && this.xpd.gls1_type() <= 0 && this.xpd.gls1_active() == 1) {					                           
						if (this.xpd.pfd_gls_loc_ghost(pilot) == 0 || this.xpd.pfd_gls_loc_ghost(pilot) == 1) {
							g2.translate(0 + (this.xpd.gls1_horz_dev() * -80f), 0);
							if (this.xpd.gls1_horz_dev() >= -2.49f && this.xpd.gls1_horz_dev() <= 2.49f) {
								g2.fillRect(-8, -100, 16, 200);
							} else {
								g2.drawRect(-8, -100, 16, 200);
							}	
						}
					}							
				}
				
				if (this.pilot == "fo") {
					
					// loc fac
					if (this.xpd.pfd_fac_horizontal(pilot) >= 1 && (this.xpd.pfd_fac_ghost(pilot) == 0 || this.xpd.pfd_fac_ghost(pilot) == 1)) {
						g2.translate(0 + (this.xpd.fac_horizont() * -80f), 0);
						if (this.xpd.fac_horizont() >= -2.49f && this.xpd.fac_horizont() <= 2.49f) {
							g2.fillRect(-8, -100, 16, 200);
						} else {
							g2.drawRect(-8, -100, 16, 200);
						}
					}
					
					// loc nav2
					if (this.xpd.vhf_nav_source() == 0 && this.xpd.mmr_act_mode(this.pilot) == 1 && this.xpd.rel_gls() <= 5) {
						if (this.xpd.ils_pointer_disable() <= 1 && this.xpd.nav2_no_id() == 0 && this.xpd.nav2_display_horizontal() == 1) {
							if (this.xpd.pfd_loc_ghost(pilot) == 0 || this.xpd.pfd_loc_ghost(pilot) == 1) {
								g2.translate(0 + (this.xpd.nav2_hdef_dot() * 80f), 0);
								if (this.xpd.nav2_hdef_dot() >= -2.49f && this.xpd.nav2_hdef_dot() <= 2.49f) {
									g2.fillRect(-8, -100, 16, 200);
								} else {
									g2.drawRect(-8, -100, 16, 200);
								}
							}
						}
					}
					
					// loc gls
					if (this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() == 0 && this.xpd.gls2_type() <= 0 && this.xpd.gls2_active() == 1) {
						if (this.xpd.pfd_gls_loc_ghost(pilot) == 0 || this.xpd.pfd_gls_loc_ghost(pilot) == 1) {
							g2.translate(0 + (this.xpd.gls2_horz_dev() * -80f), 0);
							if (this.xpd.gls2_horz_dev() >= -2.49f && this.xpd.gls2_horz_dev() <= 2.49f) {
								g2.fillRect(-8, -100, 16, 200);
							} else {
								g2.drawRect(-8, -100, 16, 200);
							}	
						}
					}					
				}
				
				g2.setTransform(original_trans);
				
			}
		}

		// heading bug and line
		if (this.xpd.irs_aligned() && !this.pln_mode) {
			
			int radius = -758;
			if (isMapCenter) {
				radius = -380;
			}
			g2.setColor(gc.color_magenta);
			g2.translate(this.map_center_x, this.map_center_y);
			g2.rotate(Math.toRadians(heading_bug_rotate), 0, 0);
			g2.scale(gc.scalex, gc.scaley);
			g2.setStroke(stroke4);
			int[] xpoints = {-27, 27, 27, 12, 0, -12, -27};
			int[] ypoints = {radius, radius, radius - 20, radius - 20, radius, radius - 20, radius - 20};
			g2.drawPolygon(xpoints, ypoints, 7);
			g2.setStroke(heading_bug_line);
			if (this.xpd.hdg_bug_line(pilot)) {			
				g2.drawLine(0, 0, 0, radius);
			}
			g2.setTransform(original_trans);
		}
		
		// app mode glideslope
		if (this.app_mode || this.app_ctr_mode) {
			
			g2.scale(gc.scalex, gc.scaley);
			
			if (this.app_mode) {
				g2.translate(1000f, 680f);
			} else {
				g2.translate(1000f, 526.5f);
			}
					
			g2.setColor(rs.color_magenta);
			g2.setStroke(rs.stroke5);
			int[] pointer_x = {15, 30, 45, 30};
			
			if (this.pilot == "cpt") {
				
				// glide slope gp
				if (this.xpd.pfd_gp_path(pilot) >= 1 && (this.xpd.pfd_gp_ghost(pilot) == 0 || this.xpd.pfd_gp_ghost(pilot) == 1)) {
					int vdef_ydelta = (int)(this.xpd.gp_err_pfd() * -200 / 500);
					if (vdef_ydelta > 200) {
						vdef_ydelta = 200;
					}
					if (vdef_ydelta < -200) {
						vdef_ydelta = -200;
					}
					int[] pointer_y = {0 + vdef_ydelta, -25 + vdef_ydelta, 0 + vdef_ydelta, 25 + vdef_ydelta};
					if (this.xpd.gp_err_pfd() >= -499.0f && this.xpd.gp_err_pfd() <= 499.0f) {
						g2.fillPolygon(pointer_x, pointer_y, 4);
					} else {
						g2.drawPolygon(pointer_x, pointer_y, 4);
					}			
					g2.setColor(rs.color_markings);
					drawVdefDots();
				}
				
				// glide slope nav1
				if (this.xpd.ian_info(pilot) <= 0 && this.xpd.vhf_nav_source() == 0 && this.xpd.ils_disable() <= 0 && this.xpd.mmr_act_mode(this.pilot) == 1 && this.xpd.rel_gls() <= 5) {	
					if (this.xpd.ils_pointer_disable() <= 1 && this.xpd.nav1_no_id() == 0 && this.xpd.nav1_flag_glideslope() == 0 && this.xpd.nav1_display_vertical() == 1) {						
						if (this.xpd.pfd_gs_ghost(pilot) == 0 || this.xpd.pfd_gs_ghost(pilot) == 1) {
							int vdef_ydelta = (int)(90f * this.xpd.nav1_vdef_dot());
							int[] pointer_y = {0 + vdef_ydelta, -25 + vdef_ydelta, 0 + vdef_ydelta, 25 + vdef_ydelta};
							if (this.xpd.nav1_vdef_dot() >= -2.49f && this.xpd.nav1_vdef_dot() <= 2.49f) {
								g2.fillPolygon(pointer_x, pointer_y, 4);
							} else {
								g2.drawPolygon(pointer_x, pointer_y, 4);
							}						
						}						
					}					
					if (this.xpd.ils_pointer_disable() <= 0) {
						g2.setColor(rs.color_markings);
						drawVdefDots();
					}
					if (this.xpd.ils_pointer_disable() >= 1) {
						g2.setColor(rs.color_amber);
						drawVdefDots();
					}
				}
				
				// glide slope gls
				if (this.xpd.ils_disable() <= 0 && this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() == 0 && this.xpd.gls1_type() <= 0 && this.xpd.gls1_active() == 1) {
					if (this.xpd.pfd_gls_gs_ghost(pilot) == 0 || this.xpd.pfd_gls_gs_ghost(pilot) == 1) {
						int vdef_ydelta = (int)(90f * this.xpd.gls1_vert_dev());
						int[] pointer_y = {0 + vdef_ydelta, -25 + vdef_ydelta, 0 + vdef_ydelta, 25 + vdef_ydelta};
						if (this.xpd.gls1_vert_dev() >= -2.49f && this.xpd.gls1_vert_dev() <= 2.49f) {
							g2.fillPolygon(pointer_x, pointer_y, 4);
						} else {
							g2.drawPolygon(pointer_x, pointer_y, 4);
						}					
					}
					g2.setColor(rs.color_markings);
					drawVdefDots();
				}
			}
			
			if (this.pilot == "fo") {
				
				// glide slope gp
				if (this.xpd.pfd_gp_path(pilot) >= 1 && (this.xpd.pfd_gp_ghost(pilot) == 0 || this.xpd.pfd_gp_ghost(pilot) == 1)) {
					int vdef_ydelta = (int)(this.xpd.gp_err_pfd() * -200 / 500);
					if (vdef_ydelta > 200) {
						vdef_ydelta = 200;
					}
					if (vdef_ydelta < -200) {
						vdef_ydelta = -200;
					}
					int[] pointer_y = {0 + vdef_ydelta, -25 + vdef_ydelta, 0 + vdef_ydelta, 25 + vdef_ydelta};
					if (this.xpd.gp_err_pfd() >= -499.0f && this.xpd.gp_err_pfd() <= 499.0f) {
						g2.fillPolygon(pointer_x, pointer_y, 4);
					} else {
						g2.drawPolygon(pointer_x, pointer_y, 4);
					}
					g2.setColor(rs.color_markings);
					drawVdefDots();
				}
				
				// glide slope nav2
				if (this.xpd.ian_info(pilot) <= 0 && this.xpd.vhf_nav_source() == 0 && this.xpd.ils_disable() <= 0 && this.xpd.mmr_act_mode(this.pilot) == 1 && this.xpd.rel_gls() <= 5) {	
					if (this.xpd.ils_pointer_disable() <= 1 && this.xpd.nav2_no_id() == 0 && this.xpd.nav2_flag_glideslope() == 0 && this.xpd.nav2_display_vertical() == 1) {						
						if (this.xpd.pfd_gs_ghost(pilot) == 0 || this.xpd.pfd_gs_ghost(pilot) == 1) {
							int vdef_ydelta = (int)(90f * this.xpd.nav2_vdef_dot());
							int[] pointer_y = {0 + vdef_ydelta, -25 + vdef_ydelta, 0 + vdef_ydelta, 25 + vdef_ydelta};
							if (this.xpd.nav2_vdef_dot() >= -2.49f && this.xpd.nav2_vdef_dot() <= 2.49f) {
								g2.fillPolygon(pointer_x, pointer_y, 4);
							} else {
								g2.drawPolygon(pointer_x, pointer_y, 4);
							}
						}						
					}
					if (this.xpd.ils_pointer_disable() <= 0) {
						g2.setColor(rs.color_markings);
						drawVdefDots();
					}
					if (this.xpd.ils_pointer_disable() >= 1) {
						g2.setColor(rs.color_amber);
						drawVdefDots();
					}
				}
				
				// glide slope gls
				if (this.xpd.ils_disable() <= 0 && this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() == 0 && this.xpd.gls2_type() <= 0 && this.xpd.gls2_active() == 1) {
					if (this.xpd.pfd_gls_gs_ghost(pilot) == 0 || this.xpd.pfd_gls_gs_ghost(pilot) == 1) {
						int vdef_ydelta = (int)(90f * this.xpd.gls2_vert_dev());
						int[] pointer_y = {0 + vdef_ydelta, -25 + vdef_ydelta, 0 + vdef_ydelta, 25 + vdef_ydelta};
						if (this.xpd.gls2_vert_dev() >= -2.49f && this.xpd.gls2_vert_dev() <= 2.49f) {
							g2.fillPolygon(pointer_x, pointer_y, 4);
						} else {
							g2.drawPolygon(pointer_x, pointer_y, 4);
						}
					}
					g2.setColor(rs.color_markings);
					drawVdefDots();
				}
			}
						
			g2.setTransform(original_trans);
			
		}
	}
	
	private void drawVdefDots() {
				
		g2.drawOval(20, -189, 18, 18);
		g2.drawOval(20, -99, 18, 18);
		g2.drawLine(2, 0, 58, 0);
		g2.drawOval(20, 81, 18, 18);
		g2.drawOval(20, 171, 18, 18);
		
	}

	private void drawTopRight() {

		if(isMapCenter && this.xpd.fpln_active()) { //background
			g2.setColor(Color.BLACK);
			g2.scale(gc.scalex, gc.scaley);
			g2.fillRect(780, 0, 295, 150);
			g2.setTransform(original_trans);
		}

		if (this.xpd.irs_aligned()) {
			
			// active waypoint
			if (this.map_ctr_mode || this.map_mode || this.pln_mode) {
				if (this.xpd.fpln_active()) {
					if (!(this.xpd.active_waypoint() == null || this.xpd.active_waypoint().equals("-----"))) {
						gc.drawText2(this.xpd.active_waypoint(), 900, 1000, 32, 0, gc.color_magenta, false, "left", g2);
						if(this.xpd.fpln_nav_id_eta() != null) {
							gc.drawText2(this.xpd.fpln_nav_id_eta(), 1008, 960, 32, 0, gc.color_markings, false, "right", g2);
							gc.drawText2("Z", 1012, 960, 24, 0, gc.color_markings, false, "left", g2);
						}
						gc.drawText2(gc.dme_formatter.format(this.xpd.fpln_nav_id_dist()), 967, 925, 32, 0, gc.color_markings, false, "right", g2);
						gc.drawText2("NM", 970, 925, 24, 0, gc.color_markings, false, "left", g2);
					}
				}
			}
			// vor info
			if (this.vor_mode || this.vor_ctr_mode) {
				String nav_type = "";
				String nav_id = "";
				int nav_course = this.xpd.course(pilot);
				float nav_dme = 0f;
				boolean receiving = false;
				if (pilot == "cpt") {
					if (nav1_radio.receiving()) {
						if (nav1 != null && nav1_radio.freq_is_nav()) {
							receiving = true;
							nav_type = "VOR 1";
							nav_id = nav1.ilt;
						} else {
							receiving = false;
						}
						if (nav1 != null && nav1.type != RadioNavBeacon.TYPE_NDB) {
							nav_dme = this.xpd.nav1_dme_nm();
						}
					}
				} else {
					if (nav2_radio.receiving()) {
						if (nav2 != null && nav2_radio.freq_is_nav()) {
							receiving = true;
							nav_type = "VOR 2";
							nav_id = nav2.ilt;
						} else {
							receiving = false;
						}
						if (nav2 != null && nav2.type != RadioNavBeacon.TYPE_NDB) {
							nav_dme = this.xpd.nav2_dme_nm();
						}
					}
				}
				if (receiving) {
					gc.drawText2(nav_type, 790, 1000, 32, 0, gc.color_lime, false, "left", g2);
					gc.drawText2(nav_id, 940, 1000, 32, 0, gc.color_markings, false, "left", g2);
					gc.drawText2("CRS", 920, 965, 26, 0, gc.color_markings, false, "left", g2);
					gc.drawText2(gc.df3.format(nav_course), 970, 965, 30, 0, gc.color_markings, false, "left", g2);
					gc.drawText2("DME", 920, 930, 26, 0, gc.color_markings, false, "left", g2);
					if (nav_dme > 0) {
						gc.drawText2(gc.dme_formatter.format(nav_dme), 1045, 930, 26, 0, gc.color_markings, false, "right", g2);
					} else {
						gc.drawText2("---", 1045, 930, 26, 0, gc.color_markings, false, "right", g2);
					}
				}
			}
			// app info
			if (this.app_mode || this.app_ctr_mode) {

				String nav_type = "";
				String nav_id = "";
				int nav_course = 0;
				float nav_dme = 0f;
				boolean receiving = false;

				if (pilot == "cpt") {
					// fmc
					if (this.xpd.mmr_act_mode(this.pilot) == 0 && this.xpd.ian_info(this.pilot) == 1 && (this.xpd.fmc_source(this.pilot) == 1 || this.xpd.fmc_source(this.pilot) == 2)) {
						receiving = true;
						if (this.xpd.fmc_source(this.pilot) == 1) {
							nav_type = "FMC L";
						}
						if (this.xpd.fmc_source(this.pilot) == 2) {
							nav_type = "FMC R";
						}
						if (this.xpd.rw_fac_id2() != null) {
							nav_id = this.xpd.rw_fac_id2();
						} else {
							nav_id = "";
						}
						nav_course = Math.round(this.xpd.course(pilot));
						nav_dme = this.xpd.rw_dist2();
					}
					// ils
					if (nav1_radio.receiving()) {
						if (nav1_radio != null && nav1_radio.freq_is_localizer()) {
							receiving = true;
							nav_type = "ILS 1";
							if(nav1_object != null) {
								nav_id = nav1_object.ilt;
							}else {
								nav_id = "";
							}
							nav_course = Math.round(this.xpd.course(pilot));
							nav_dme = this.xpd.nav1_dme_nm();
						}
					}
					// gls
					if (this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() <= 0 && this.xpd.ian_info(this.pilot) <= 0 && this.xpd.gls1_active() == 1) {
						receiving = true;
						nav_type = "GLS 1";
						if (this.xpd.gls1_id() != null) {
							nav_id = this.xpd.gls1_id();
						} else {
							nav_id = "";
						}
						nav_course = Math.round(this.xpd.course(pilot));
						nav_dme = this.xpd.gls1_dme();
					}
				} else {
					// fmc
					if (this.xpd.mmr_act_mode(this.pilot) == 0 && this.xpd.ian_info(this.pilot) == 1 && (this.xpd.fmc_source(this.pilot) == 1 || this.xpd.fmc_source(this.pilot) == 2)) {
						receiving = true;
						if (this.xpd.fmc_source(this.pilot) == 1) {
							nav_type = "FMC L";
						}
						if (this.xpd.fmc_source(this.pilot) == 2) {
							nav_type = "FMC R";
						}
						if (this.xpd.rw_fac_id2() != null) {
							nav_id = this.xpd.rw_fac_id2();
						} else {
							nav_id = "";
						}
						nav_course = Math.round(this.xpd.course(pilot));
						nav_dme = this.xpd.rw_dist2();
					}
					// ils
					if (nav2_radio.receiving()) {
						if (nav2_radio != null && nav2_radio.freq_is_localizer()) {
							receiving = true;
							nav_type = "ILS 2";
							if(nav2_object != null) {
								nav_id = nav2_object.ilt;
							}else {
								nav_id = "";
							}
							nav_course = Math.round(this.xpd.course(pilot));
							nav_dme = this.xpd.nav2_dme_nm();
						}
					}
					// gls
					if (this.xpd.mmr_act_mode(this.pilot) == 2 && this.xpd.vhf_nav_source() <= 0 && this.xpd.ian_info(this.pilot) <= 0 && this.xpd.gls2_active() == 1) {
						receiving = true;
						nav_type = "GLS 2";
						if (this.xpd.gls2_id() != null) {
							nav_id = this.xpd.gls2_id();
						} else {
							nav_id = "";
						}
						nav_course = Math.round(this.xpd.course(pilot));
						nav_dme = this.xpd.gls2_dme();
					}
				}
				if (receiving) {
					gc.drawText2(nav_type, 790, 1000, 32, 0, gc.color_lime, false, "left", g2);
					gc.drawText2(nav_id, 940, 1000, 32, 0, gc.color_markings, false, "left", g2);
					gc.drawText2("CRS", 920, 965, 26, 0, gc.color_markings, false, "left", g2);
					gc.drawText2(gc.df3.format(nav_course), 970, 965, 30, 0, gc.color_markings, false, "left", g2);
					gc.drawText2("DME", 920, 930, 26, 0, gc.color_markings, false, "left", g2);
					if (nav_dme > 0) {
						gc.drawText2(gc.dme_formatter.format(nav_dme), 1045, 930, 26, 0, gc.color_markings, false, "right", g2);
					} else {
						gc.drawText2("---", 1045, 930, 26, 0, gc.color_markings, false, "right", g2);
					}
				}
			}
		}
	}

	private void drawTopLeft() {

		//backgrounds - especially for CTR modes
		if(isMapCenter) {
			g2.setColor(Color.BLACK);
			g2.scale(gc.scalex, gc.scaley);

			g2.fillRect(0, 0, 255, 90);
			g2.fillRect(0, 89, 130, 100);

			g2.setTransform(original_trans);
		}


		if (this.xpd.irs_aligned()) {
			
			// ground speed
			
			gc.drawText2("GS", 20, 1010, 24, 0, gc.color_markings, false, "left", g2);
			if (this.xpd.groundspeed() < 30) {
				gc.drawText2(gc.df3hash.format(this.xpd.groundspeed()), 112, 1005, 40, 0, gc.color_markings, false,
						"right", g2);
			} else {
				gc.drawText2(gc.df3hash.format(this.xpd.groundspeed()), 55, 1010, 32, 0, gc.color_markings, false,
						"left", g2);
			}
			
			// true air speed

			String tas = "---";
			if (this.xpd.true_airspeed_knots() > 100) {
				tas = gc.df3hash.format(this.xpd.true_airspeed_knots());
			}
			gc.drawText2("TAS", 145, 1010, 24, 0, gc.color_markings, false, "left", g2);
			gc.drawText2(tas, 190, 1010, 32, 0, gc.color_markings, false, "left", g2);

			// wind speed & direction

			String wind_speed = "---";
			String wind_direction = "---";
			boolean display_arrow = false;

			if (this.xpd.true_airspeed_knots() > 101 && this.xpd.wind_speed_knots() > 6) {
				wind_speed = gc.df3hash.format(this.xpd.wind_speed_knots());
				wind_direction = gc.df3.format(this.xpd.wind_heading());
				display_arrow = true;
			}

			gc.drawText2(wind_direction + "°/" + wind_speed, 20, 970, 32, 0, gc.color_markings, false, "left", g2);
			
			if (this.pln_mode) {
				display_arrow = false;
			}
			
			if (display_arrow) {
				temp_trans = g2.getTransform();
				g2.scale(gc.scalex, gc.scaley);
				g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2.setColor(gc.color_markings);
				g2.translate(70, 140);
				g2.rotate(Math.toRadians((this.xpd.heading(pilot) - this.xpd.wind_heading() - 180f) * -1), 0, 0);
				if(display_arrow) {
					g2.drawLine(0, -40, -8, -35);
					g2.drawLine(0, -40, 0, 40);
					g2.drawLine(0, -40, 8, -35);
				}
				g2.setTransform(temp_trans);
			}
		}
	}

	private void drawForeGround() {

		g2.scale(gc.scalex, gc.scaley);
		g2.setColor(Color.BLACK);
		if((this.xpd.efis_apt_mode(pilot) || this.xpd.efis_wpt_mode(pilot) || this.xpd.efis_sta_mode(pilot)) && (this.map_mode || this.map_ctr_mode || this.pln_mode)) {
			//int[] xpoints = {0, 80, 0};
			//int[] ypoints = {662, 662, 630};
			//g2.fillPolygon(xpoints, ypoints, xpoints.length); //ARPT WPT STA
			g2.fillRect(0, 660, 80, 85); // ARPT WPT STA
		}
		if(this.xpd.efis_terr_on(pilot) || this.xpd.efis_wxr_on(pilot)) {
			g2.fillRect(0, 743, 100, 115); //Terrain WXR
		}

		g2.fillRect(0, 855, 100, 80); //TCAS

		g2.setTransform(original_trans);

		if(this.xpd.irs_aligned()) {

			if (this.xpd.efis_apt_mode(pilot) && (this.map_mode || this.map_ctr_mode || this.pln_mode)) {
				gc.drawText2("ARPT", 10f, 365f, 24f, 0f, gc.color_navaid, false, "left", g2);
			}
			if (this.xpd.efis_wpt_mode(pilot) && (this.map_mode || this.map_ctr_mode || this.pln_mode)) {
				gc.drawText2("WPT", 10f, 340f, 24f, 0f, gc.color_navaid, false, "left", g2);
			}
			if (this.xpd.efis_sta_mode(pilot) && (this.map_mode || this.map_ctr_mode || this.pln_mode)) {
				gc.drawText2("STA", 10f, 315f, 24f, 0f, gc.color_navaid, false, "left", g2);
			}
			if (this.xpd.efis_terr_on(pilot) && egpws_loaded) {
				if(this.map_mode || this.map_ctr_mode || this.vor_mode || this.app_mode) {
					gc.drawText2("TERR", 10f, 275f, 30f, 0f, gc.color_navaid, false, "left", g2);

					float terr_max = terrain.terrain_max;
					float terr_min = terrain.terrain_min;

					if(terrain.terrain_max > terrain.ref_alt + 2000) {
						peak_max_color = Color.RED;
					}else if(terrain.terrain_max > terrain.ref_alt -500) {
						peak_max_color = Color.ORANGE;
					}else {
						peak_max_color = Color.GREEN;
					}
					if(terrain.terrain_min > terrain.ref_alt + 2000) {
						peak_min_color = Color.RED;
					}else if(terrain.terrain_min > terrain.ref_alt -500) {
						peak_min_color = Color.ORANGE;
					}else {
						peak_min_color = Color.GREEN;
					}
					if (terrain.terrain_min < 0) terr_min = 0;

					peak_max_string = gc.df3.format(Math.round(terr_max/100));
					peak_min_string = gc.df3.format(Math.round(terr_min/100));

					if(peak_min_string != null) {
						gc.drawText2(peak_max_string, 10f, 240f, 30f, 0f, peak_max_color, false, "left", g2);
					}
					if(peak_min_string != null && Math.round(terr_min/100) > 0) {
						gc.drawText2(peak_min_string, 10f, 200f, 30f, 0f, peak_min_color, false, "left", g2);	
					}
				}
			}
			if (this.xpd.efis_wxr_on(pilot) && !this.xpd.map_mode_pln(pilot)) {
				gc.drawText2("WX-A", 10f, 275f, 29f, 0f, gc.color_navaid, false, "left", g2);
			}			
			if (this.xpd.tcas_test_show(pilot)) {
				gc.drawText2("TCAS", 10f, 160f, 25f, 0f, gc.color_navaid, true, "left", g2);
				gc.drawText2("TEST", 10f, 130f, 25f, 0f, gc.color_navaid, true, "left", g2);
			} else {
				if (this.xpd.transponder_pos() >= 4 && this.xpd.tcas_on(pilot) && !this.xpd.tcas_fail_show(pilot) && !this.xpd.tcas_off_show(pilot) && !this.xpd.map_mode_pln(pilot)) {
					gc.drawText2("TFC", 10f, 160f, 25f, 0f, gc.color_navaid, false, "left", g2);
				}
				if (this.xpd.transponder_pos() == 4 && !this.xpd.tcas_fail_show(pilot) && !this.xpd.tcas_off_show(pilot)) {
					gc.drawText2("TA ONLY", 10f, 130f, 25f, 0f, gc.color_navaid, true, "left", g2);
				}
			}
		}

		if (this.xpd.tcas_off_show(pilot)) {
			gc.drawText2("TCAS", 10f, 160f, 27f, 0f, gc.color_amber, false, "left", g2);
			gc.drawText2("OFF", 10f, 130f, 27f, 0f, gc.color_amber, false, "left", g2);	
		}
		if (this.xpd.tcas_fail_show(pilot)) {
			gc.drawText2("TCAS", 10f, 160f, 27f, 0f, gc.color_amber, false, "left", g2);
			gc.drawText2("FAIL", 10f, 130f, 27f, 0f, gc.color_amber, false, "left", g2);	
		}

		//airplane symbol
		if (this.map_mode || this.map_ctr_mode || this.vor_mode || this.app_mode) {
			if (isMapCenter) {
				gc.displayImage(rs.img_plane_triangle, 0, 502, 122.050 + 328, g2);
			} else {
				gc.displayImage(rs.img_plane_triangle, 0, 502, 122.050, g2);
			}
		} else if (this.vor_ctr_mode || this.app_ctr_mode){
			gc.displayImage(rs.img_plane_vor_symbol, 0, 488f, 476.600f, g2);
		}
		
		//map range
		if(!this.pln_mode && this.xpd.irs_aligned()) {
			g2.translate(this.map_center_x, this.map_center_y);
			g2.rotate(Math.toRadians(track_line_rotate), 0, 0);
			g2.translate(-this.map_center_x, -this.map_center_y);
			if (isMapCenter) {
				if(!(this.app_ctr_mode || this.vor_ctr_mode)) {
					gc.drawText2(this.map_range, 525f, (1053f / 2) + 187.5f, 30f, 0f, gc.color_markings, true, "right", g2);
					gc.drawText2(this.map_range, 525f, (1053f / 2) - 187.5f, 30f, 0f, gc.color_markings, true, "right", g2);
				}
			} else {
				gc.drawText2(this.map_range, 525f, 595f, 30f, 0f, gc.color_markings, true, "right", g2);
			}
			g2.setTransform(original_trans);
		}

		//failure messages

		if (this.xpd.efis_disagree(this.pilot)) {
			gc.drawText2("EFIS MODE/NAV FREQ DISAGREE", 535, 680, 36, 0, gc.color_amber, true, "center", g2);
		}

		if (!this.xpd.irs_aligned()) {
			gc.displayImage(rs.img_hdg_fail, 0, 496.441f, 995f, g2);
			gc.displayImage(rs.img_map_fail, 0, 496.441f, 398f, g2);
			gc.drawText2("TERR POS", 15, 560f, 26f, 0f, gc.color_amber, false, "left", g2);
		}

		if(this.xpd.tcas_traffic_ra(pilot)) {

			gc.drawText2("TRAFFIC", 900f, 580f, 32f, 0, Color.RED, true, "left", g2);

		}else if(this.xpd.tcas_traffic_ta(pilot)) {

			gc.drawText2("TRAFFIC", 900f, 580f, 32f, 0, gc.color_amber, true, "left", g2);
		}

		//xraas
		if(this.xpd.xraas_message() != null && this.preferences.get_preference(ZHSIPreferences.PREF_XRAAS_ENABLE).equals("true")) {
			Color xraas_color = gc.color_amber;
			if(this.xpd.xraas_color() == 0) {
				xraas_color = Color.GREEN;
			}
			gc.xraasText(this.xpd.xraas_message(), 540, 530, Float.parseFloat(this.preferences.get_preference(ZHSIPreferences.PREF_XRAAS_FONT_SIZE)), 0, xraas_color, false, "center", g2);
		}
	}

	private void updateStuff() {

		//set center lat/lon - will change for CTR waypoints
		if(!this.pln_mode) {
			this.center_lat = this.xpd.latitude();
			this.center_lon = this.xpd.longitude();
		}else {
			if(this.xpd.legs_step_ctr_idx(pilot) != 0) {
				if(this.xpd.fpln_active() && this.xpd.legs_mod_active()) {
//					this.center_lat = FMS.active_entries[this.xpd.legs_step_ctr_idx(pilot) - 1].getLat();
//					this.center_lon = FMS.active_entries[this.xpd.legs_step_ctr_idx(pilot) - 1].getLon();
					this.center_lat = this.xpd.mod_legs_lat()[this.xpd.legs_step_ctr_idx(pilot) - 1];
					this.center_lon = this.xpd.mod_legs_lon()[this.xpd.legs_step_ctr_idx(pilot) - 1];

				}else if(this.xpd.fpln_active()) {
//					this.center_lat = FMS.modified_entries[this.xpd.legs_step_ctr_idx(pilot) - 1].getLat();
//					this.center_lon = FMS.modified_entries[this.xpd.legs_step_ctr_idx(pilot) - 1].getLon();
					this.center_lat = this.xpd.legs_lat()[this.xpd.legs_step_ctr_idx(pilot) - 1];
					this.center_lon = this.xpd.legs_lon()[this.xpd.legs_step_ctr_idx(pilot) - 1];
					//System.out.println("center lat=" + this.center_lat + " center_lon=" + this.center_lon);
				}else {
					this.center_lat = this.xpd.mod_legs_lat()[this.xpd.legs_step_ctr_idx(pilot) - 1];
					this.center_lon = this.xpd.mod_legs_lon()[this.xpd.legs_step_ctr_idx(pilot) - 1];
				}

			}else {
				this.center_lat = this.xpd.latitude();
				this.center_lon = this.xpd.longitude();
			}
		}

		this.rose_radius = 758f * gc.scaley;

		// get tuned radios
		nav1 = this.xpd.get_tuned_navaid(1, pilot);
		nav2 = this.xpd.get_tuned_navaid(2, pilot);
		nav1_radio = this.xpd.get_nav_radio(1);
		nav2_radio = this.xpd.get_nav_radio(2);
		nav1_object = nav1_radio.get_radio_nav_object();
		nav2_object = nav2_radio.get_radio_nav_object();

		if(this.pilot == "cpt" && this.xpd.nav1_fromto() == 1) {
			vorTo = true;
		}else if(this.pilot == "fo" && this.xpd.nav2_fromto() == 1) {
			vorTo = true;
		}else {
			vorTo = false;
		}


		// map modes booleans
		if (this.xpd.efis_ctr_map(pilot) && this.xpd.irs_aligned()) {

			if (this.xpd.map_mode_app(pilot) && this.xpd.irs_aligned()) {
				app_mode = false;
				app_ctr_mode = true;
				vor_mode = false;
				vor_ctr_mode = false;
				map_mode = false;
				map_ctr_mode = false;
				pln_mode = false;
			} else if (this.xpd.map_mode_vor(pilot) && this.xpd.irs_aligned()) {
				app_mode = false;
				app_ctr_mode = false;
				vor_mode = false;
				vor_ctr_mode = true;
				map_mode = false;
				map_ctr_mode = false;
				pln_mode = false;
			} else if (this.xpd.map_mode_map(pilot)) {
				app_mode = false;
				app_ctr_mode = false;
				vor_mode = false;
				vor_ctr_mode = false;
				map_mode = false;
				map_ctr_mode = true;
				pln_mode = false;
			} else if (this.xpd.map_mode_pln(pilot) && this.xpd.irs_aligned()) {
				app_mode = false;
				app_ctr_mode = false;
				vor_mode = false;
				vor_ctr_mode = false;
				map_mode = false;
				map_ctr_mode = false;
				pln_mode = true;
			}
		} else {

			if (this.xpd.map_mode_app(pilot) && this.xpd.irs_aligned()) {
				app_mode = true;
				app_ctr_mode = false;
				vor_mode = false;
				vor_ctr_mode = false;
				map_mode = false;
				map_ctr_mode = false;
				pln_mode = false;
			} else if (this.xpd.map_mode_vor(pilot) && this.xpd.irs_aligned()) {
				app_mode = false;
				app_ctr_mode = false;
				vor_mode = true;
				vor_ctr_mode = false;
				map_mode = false;
				map_ctr_mode = false;
				pln_mode = false;
			} else if (this.xpd.map_mode_map(pilot)) {
				app_mode = false;
				app_ctr_mode = false;
				vor_mode = false;
				vor_ctr_mode = false;
				map_mode = true;
				map_ctr_mode = false;
				pln_mode = false;
			} else if (this.xpd.map_mode_pln(pilot) && this.xpd.irs_aligned()) {
				app_mode = false;
				app_ctr_mode = false;
				vor_mode = false;
				vor_ctr_mode = false;
				map_mode = false;
				map_ctr_mode = false;
				pln_mode = true;
			}
		}
		
		if (this.app_ctr_mode || this.vor_ctr_mode || this.map_ctr_mode || this.pln_mode) {
			this.map_center_x = this.getWidth() / 2;
			this.map_center_y = this.getHeight() / 2;
			this.isMapCenter = true;
		} else {
			this.map_center_x = this.getWidth() / 2;
			this.map_center_y = this.getHeight() - (198.5f * gc.scaley);
			this.isMapCenter = false;
		}

		// get map orientation
	
		if (this.xpd.map_mode_map(pilot) && this.xpd.track_up() == 1) {
			if (this.xpd.on_gound()) {
				this.map_up = ((int) this.xpd.track() - this.xpd.magnetic_variation()) * -1;
			} else {
				this.map_up = (this.xpd.track() - this.xpd.magnetic_variation()) * -1;
			}
		} else if (this.xpd.map_mode_pln(pilot)) {
			this.map_up = 0.0f - this.xpd.magnetic_variation() * -1;
		} else {
			if (this.xpd.on_gound()) {
				this.map_up = ((int) this.xpd.heading(pilot) - this.xpd.magnetic_variation()) * -1;
			} else {
				this.map_up = (this.xpd.heading(pilot) - this.xpd.magnetic_variation()) * -1;
			}
		}

		if (this.isMapCenter) {

			switch (xpd.map_range(pilot)) {
			case 0:

				if (this.map_ctr_mode || this.app_ctr_mode || this.vor_ctr_mode) {
					this.map_range = "1.25";
					this.max_range = 2.5f;
					this.max_range_str = "2.5";
					this.pixels_per_nm = (375f / 2.5f) * gc.scaling_factor;
				} else {
					this.map_range = "2.5";
					this.max_range = 5f;
					this.max_range_str = "5";
					this.pixels_per_nm = (425f / 5f) * gc.scaling_factor;
				}
				break;
			case 1:

				if (this.map_ctr_mode || this.app_ctr_mode || this.vor_ctr_mode) {
					this.map_range = "2.5";
					this.max_range = 5f;
					this.max_range_str = "5";
					this.pixels_per_nm = (375f / 5f) * gc.scaling_factor;
				} else {
					this.map_range = "5";
					this.max_range = 10f;
					this.max_range_str = "10";
					this.pixels_per_nm = (425f / 10f) * gc.scaling_factor;
				}
				break;
			case 2:

				if (this.map_ctr_mode || this.app_ctr_mode || this.vor_ctr_mode) {
					this.map_range = "5";
					this.max_range = 10f;
					this.max_range_str = "10";
					this.pixels_per_nm = (375f / 10f) * gc.scaling_factor;
				} else {
					this.map_range = "10";
					this.max_range = 20f;
					this.max_range_str = "20";
					this.pixels_per_nm = (425f / 20f) * gc.scaling_factor;
				}
				break;
			case 3:

				if (this.map_ctr_mode || this.app_ctr_mode || this.vor_ctr_mode) {
					this.map_range = "10";
					this.max_range = 20f;
					this.max_range_str = "20";
					this.pixels_per_nm = (375f / 20f) * gc.scaling_factor;
				} else {
					this.map_range = "20";
					this.max_range = 40f;
					this.max_range_str = "40";
					this.pixels_per_nm = (425f / 40f) * gc.scaling_factor;
				}
				break;
			case 4:

				if (this.map_ctr_mode || this.app_ctr_mode || this.vor_ctr_mode) {
					this.map_range = "20";
					this.max_range = 40f;
					this.max_range_str = "40";
					this.pixels_per_nm = (375f / 40f) * gc.scaling_factor;
				} else {
					this.map_range = "40";
					this.max_range = 80f;
					this.max_range_str = "80";
					this.pixels_per_nm = (425f / 80f) * gc.scaling_factor;
				}
				break;
			case 5:

				if (this.map_ctr_mode || this.app_ctr_mode || this.vor_ctr_mode) {
					this.map_range = "40";
					this.max_range = 80f;
					this.max_range_str = "80";
					this.pixels_per_nm = (375f / 80f) * gc.scaling_factor;
				} else {
					this.map_range = "80";
					this.max_range = 160;
					this.max_range_str = "160";
					this.pixels_per_nm = (425f / 160f) * gc.scaling_factor;
				}
				break;
			case 6:

				if (this.map_ctr_mode || this.app_ctr_mode || this.vor_ctr_mode) {
					this.map_range = "80";
					this.max_range = 160f;
					this.max_range_str = "160";
					this.pixels_per_nm = (375f / 160f) * gc.scaling_factor;
				} else {
					this.map_range = "160";
					this.max_range = 320;
					this.max_range_str = "320";
					this.pixels_per_nm = (425f / 320f) * gc.scaling_factor;
				}
				break;
			case 7:

				if (this.map_ctr_mode || this.app_ctr_mode || this.vor_ctr_mode) {
					this.map_range = "160";
					this.max_range = 320f;
					this.max_range_str = "320";
					this.pixels_per_nm = (375f / 320f) * gc.scaling_factor;
				} else {
					this.map_range = "320";
					this.max_range = 640;
					this.max_range_str = "640";
					this.pixels_per_nm = (425f / 640f) * gc.scaling_factor;
				}
				break;
			}
		} else {
			switch (xpd.map_range(pilot)) {
			case 0:
				this.map_range = "2.5";
				this.max_range = 5;
				this.max_range_str = "5";
				this.pixels_per_nm = (758f / 5f) * gc.scaling_factor;
				break;
			case 1:
				this.map_range = "5";
				this.max_range = 10;
				this.max_range_str = "10";
				this.pixels_per_nm = (758f / 10f) * gc.scaling_factor;
				break;
			case 2:
				this.map_range = "10";
				this.max_range = 20;
				this.max_range_str = "20";
				this.pixels_per_nm = (758f / 20f) * gc.scaling_factor;
				break;
			case 3:
				this.map_range = "20";
				this.max_range = 40;
				this.max_range_str = "40";
				this.pixels_per_nm = (758f / 40f) * gc.scaling_factor;
				break;
			case 4:
				this.map_range = "40";
				this.max_range = 80;
				this.max_range_str = "80";
				this.pixels_per_nm = (758f / 80f) * gc.scaling_factor;
				break;
			case 5:
				this.map_range = "80";
				this.max_range = 160;
				this.max_range_str = "160";
				this.pixels_per_nm = (758f / 160f) * gc.scaling_factor;
				break;
			case 6:
				this.map_range = "160";
				this.max_range = 320;
				this.max_range_str = "320";
				this.pixels_per_nm = (758f / 320f) * gc.scaling_factor;
				break;
			case 7:
				this.map_range = "320";
				this.max_range = 640;
				this.max_range_str = "640";
				this.pixels_per_nm = (758f / 640f) * gc.scaling_factor;
				break;
			}
		}
		if(this.xpd.efis_terr(pilot) != old_terr_on_value) {
			terrain_cycle = 0;
			terrain_invert = false;
			old_terr_on_value = this.xpd.efis_terr(pilot);
		}

		if(this.xpd.efis_wxr(pilot) != old_wxr_on_value) {
			weather_cycle = 0;
			weather_invert = false;
			old_wxr_on_value = this.xpd.efis_wxr(pilot);
		}


		if(this.xpd.efis_map_ctr(pilot) != old_map_ctr_int) {
			old_map_ctr_int = this.xpd.efis_map_ctr(pilot);
			terrain_cycle = 0;
			terrain_invert = false;
			initial_sweep_required = true;
			initial_sweep_rotate = 180f;
			terrain_cycle = 0;
			weather_cycle = 0;
			terrain_invert = false;
			weather_invert = false;
			if(this.xpd.efis_terr_on(pilot)) {
				if(this.map_ctr_mode) {
					if(this.xpd.efis_terr_on(pilot) && egpws_loaded) {
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, true);
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, true);
					}
				}else {
					if(this.xpd.efis_terr_on(pilot) && egpws_loaded) {
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, false);
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, false);
					}
					if(this.xpd.efis_wxr_on(pilot) && ZHSIStatus.weather_receiving) {
						weather.renderWeather(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, false);
						weather.renderWeather(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, false);
					}
				}
			}
		}

		if(this.xpd.map_range(pilot) != old_map_range_value) {
			old_map_range_value = this.xpd.map_range(pilot);
			initial_sweep_required = true;
			initial_sweep_rotate = 180f;
			terrain_cycle = 0;
			weather_cycle = 0;
			terrain_invert = false;
			weather_invert = false;
			if(this.xpd.efis_terr_on(pilot)) {
				if(this.map_ctr_mode) {
					if(this.xpd.efis_terr_on(pilot) && egpws_loaded) {
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, true);
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, true);
					}
				}else {
					if(this.xpd.efis_terr_on(pilot) && ZHSIStatus.egpws_db_status.equals(ZHSIStatus.STATUS_EGPWS_DB_LOADED)) {
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, false);
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, false);
					}
					if(this.xpd.efis_wxr_on(pilot) && ZHSIStatus.weather_receiving) {
						weather.renderWeather(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, false);
						weather.renderWeather(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, false);
					}
				}
			}
		}
		if(this.xpd.map_mode(pilot) != old_map_mode_value) {
			old_map_mode_value = this.xpd.map_mode(pilot);
			initial_sweep_required = true;
			initial_sweep_rotate = 180f;
			terrain_cycle = 0;
			weather_cycle = 0;
			if(this.xpd.efis_terr_on(pilot)) {
				if(this.map_ctr_mode) {
					if(this.xpd.efis_terr_on(pilot) && egpws_loaded) {
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, true);
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, true);
					}
				}else {
					if(this.xpd.efis_terr_on(pilot) && egpws_loaded) {
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, false);
						terrain.renderTerrain(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, false);
					}
					if(this.xpd.efis_wxr_on(pilot) && ZHSIStatus.weather_receiving) {
						weather.renderWeather(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 1, false);
						weather.renderWeather(g2, pixels_per_nm, max_range, map_up, map_center_x, map_center_y, scaling_factor, 2, false);
					}
				}
			}
		}

		this.map_projection.setAcf(this.center_lat, this.center_lon);
		this.map_projection.setCenter(this.map_center_x, this.map_center_y);
		this.map_projection.setScale(this.pixels_per_nm);
		
		plane_x = (int) this.map_projection.getAirPlaneX(this.xpd.latitude(), this.xpd.longitude(), this.pixels_per_nm, this.map_center_x);
		plane_y = (int) this.map_projection.getAirPlaneY(this.xpd.latitude(), this.xpd.longitude(), this.pixels_per_nm, this.map_center_y);

	}
	
	private float zibo_x(float x) {
		
		float x_px = map_center_x + (x * 75.5f * gc.scalex);
		return x_px;
	
	}
	
	private float zibo_y(float y) {
		
		if(isMapCenter) {
			
			y = y - 4.1f;
			if(this.xpd.efis_vsd_map(pilot)) {
				y = y - 1f;
			}
		}
		
		float y_px = map_center_y - (y * 75.25f * gc.scaley);
		return y_px;
		
	}

	private float turn_radius(float turn_speed, float speed) {

		return Math.abs(speed / (turn_speed * 20.0f * (float) Math.PI));

	}
	
	private void draw_position_trend_vector_segment(Graphics2D g2, float turn_radius, float turn_speed, float pixels_per_nm, float vector_start, float vector_end) {

		g2.scale(gc.scalex, gc.scaley);
		g2.setStroke(rs.stroke3);
		g2.setTransform(original_trans);
		g2.translate(this.map_center_x, this.map_center_y);
		g2.rotate(Math.toRadians(track_line_rotate), 0, 0);
		g2.setColor(rs.color_markings);
				
		float turn_radius_pixels = turn_radius * this.pixels_per_nm;
	
		if (turn_speed >= 0) {
			// right turn
			g2.draw(new Arc2D.Float(
					0,
					0 - turn_radius_pixels - (10f * gc.scaley),
					turn_radius_pixels * 2f,
					turn_radius_pixels * 2f,
					180.0f - (vector_end * turn_speed),
					(vector_end-vector_start) * turn_speed,
					Arc2D.OPEN));
		} else {
			// left turn
			g2.draw(new Arc2D.Float(			
					0 - (turn_radius_pixels * 2f),
					0 - turn_radius_pixels - (10f * gc.scaley),
					turn_radius_pixels * 2f,
					turn_radius_pixels * 2f,
					vector_start * Math.abs(turn_speed),
					(vector_end-vector_start) * Math.abs(turn_speed),
					Arc2D.OPEN));
		}
	}
}