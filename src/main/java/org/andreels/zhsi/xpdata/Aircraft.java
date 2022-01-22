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

public class Aircraft extends BaseDataClass {
	
	Observer<DataRef> aircraft;

	private final String[] HEADING  = {"sim/cockpit2/gauges/indicators/heading_AHARS_deg_mag_pilot", "sim/cockpit2/gauges/indicators/heading_AHARS_deg_mag_copilot"};
	private final String[] ALTITUDE  = {"sim/cockpit2/gauges/indicators/altitude_ft_pilot", "sim/cockpit2/gauges/indicators/altitude_ft_copilot"};
	private final String[] AIRSPEED  = {"sim/cockpit2/gauges/indicators/airspeed_kts_pilot", "sim/cockpit2/gauges/indicators/airspeed_kts_copilot"};
	private final String[] VVI  = {"sim/cockpit2/gauges/indicators/vvi_fpm_pilot", "sim/cockpit2/gauges/indicators/vvi_fpm_copilot"};
	private final String[] LAT_LON = {"sim/flightmodel/position/latitude", "sim/flightmodel/position/longitude"};
	private final String[] RUNWAY_SHOW = {"laminar/B738/pfd/runway_show", "laminar/B738/pfd/runway_show_fo"};
	private final String[] RUNWAY_X = {"laminar/B738/pfd/runway_x", "laminar/B738/pfd/runway_x_fo"};
	private final String[] RUNWAY_Y = {"laminar/B738/pfd/runway_y", "laminar/B738/pfd/runway_y_fo"};
	private final String AIRSPEED_MACH = "laminar/B738/autopilot/airspeed_mach";
	private final String TRACK = "sim/cockpit2/gauges/indicators/ground_track_mag_pilot";
	private final String PITCH = "sim/cockpit2/gauges/indicators/pitch_AHARS_deg_pilot";
	private final String ROLL = "sim/cockpit2/gauges/indicators/roll_AHARS_deg_pilot";
	private final String ON_GROUND = "sim/flightmodel/failures/onground_any";
	private final String MAGNETIC_VARIATION = "sim/flightmodel/position/magnetic_variation";
	private final String GROUNDSPEED = "sim/flightmodel/position/groundspeed"; //meters/sec
	private final String AOA = "sim/flightmodel2/misc/AoA_angle_degrees";
	private final String RADIO_ALT_FEET = "sim/cockpit2/gauges/indicators/radio_altimeter_height_ft_pilot";
	private final String TRUE_AIRSPEED_KNOTS = "sim/cockpit2/gauges/indicators/true_airspeed_kts_pilot";
	private final String AIRSPEED_ACCELERATION = "sim/cockpit2/gauges/indicators/airspeed_acceleration_kts_sec_pilot";
	private final String SLIP_DEG = "sim/cockpit2/gauges/indicators/slip_deg";	
	//
	private final String MAX_SPEED = "laminar/B738/pfd/max_speed"; //red black bars
	private final String MAX_MANEUVER_SPEED = "laminar/B738/pfd/max_maneuver_speed"; //amber bar
	private final String MAX_MANEUVER_SPEED_SHOW = "laminar/B738/pfd/max_maneuver_speed_show";
	//
	private final String MIN_SPEED = "laminar/B738/pfd/min_speed"; //red black bars
	private final String MIN_SPEED_SHOW = "laminar/B738/pfd/min_speed_show";
	private final String MIN_MANEUVER_SPEED = "laminar/B738/pfd/min_maneuver_speed"; //amber bar
	private final String MIN_MANEUVER_SPEED_SHOW = "laminar/B738/pfd/min_maneuver_speed_show";
	//
	private final String FLAPS_1 = "laminar/B738/pfd/flaps_1";
	private final String FLAPS_1_SHOW = "laminar/B738/pfd/flaps_1_show";
	private final String FLAPS_2 = "laminar/B738/pfd/flaps_2";
	private final String FLAPS_2_SHOW = "laminar/B738/pfd/flaps_2_show";
	private final String FLAPS_5 = "laminar/B738/pfd/flaps_5";
	private final String FLAPS_5_SHOW = "laminar/B738/pfd/flaps_5_show";
	private final String FLAPS_10 = "laminar/B738/pfd/flaps_10";
	private final String FLAPS_10_SHOW = "laminar/B738/pfd/flaps_10_show";
	private final String FLAPS_15 = "laminar/B738/pfd/flaps_15";
	private final String FLAPS_15_SHOW = "laminar/B738/pfd/flaps_15_show";
	private final String FLAPS_25 = "laminar/B738/pfd/flaps_25";
	private final String FLAPS_25_SHOW = "laminar/B738/pfd/flaps_25_show";
	private final String FLAPS_UP = "laminar/B738/pfd/flaps_up";
	private final String FLAPS_UP_SHOW = "laminar/B738/pfd/flaps_up_show";
	private final String STALL = "laminar/B738/pfd/pfd_stall";
	private final String STALL_SHOW = "laminar/B738/pfd/pfd_stall_show";
	private final String SPD_80_SHOW = "laminar/B738/pfd/spd_80_show";
	
	private final String YAW_ROTATION = "sim/flightmodel/position/R";
	
	private final String GEAR_DEPLOY_RATIO = "sim/flightmodel2/gear/deploy_ratio";
	

	public float[] heading = new float[2];
	public float[] altitude = new float[2];
	public float[] airspeed = new float[2];
	public float[] lat_lon = new float[2];
	public int[] runway_show = new int[2];
	public float[] runway_x = new float[2];
	public float[] runway_y = new float[2];
	public float[] vvi = new float[2];
	public float track = 0f;
	public float pitch = 0f;
	public float roll = 0f;
	public int on_ground = 1;
	public float magnetic_variation = 0f;
	public float groundspeed = 0;
	public float aoa = 0f;
	public float airspeed_mach = 0f;
	public float radio_alt_feet = 0f;
	public float true_airspeed_knots = 0f;
	public float airspeed_acceleration = 0f;
	public float slip_deg = 0f;
	//
	public float max_speed = 0.0f;
	public float max_maneuver_speed = 0.0f;
	public int max_maneuver_speed_show = 0;
	//
	public float min_speed = 0.0f;
	public int min_speed_show = 0;
	public float min_maneuver_speed = 0.0f;
	public int min_maneuver_speed_show = 0;
	//
	public float flaps_1 = 0f;
	public double flaps_1_show = 0f;
	public float flaps_2 = 0f;
	public double flaps_2_show = 0f;
	public float flaps_5 = 0f;
	public double flaps_5_show = 0f;
	public float flaps_10 = 0f;
	public double flaps_10_show = 0f;
	public float flaps_15 = 0f;
	public double flaps_15_show = 0f;
	public float flaps_25 = 0f;
	public double flaps_25_show = 0f;
	public float flaps_up = 0f;
	public double flaps_up_show = 0f;
	public float stall = 0f;
	public int stall_show = 0;
	public int spd_80_show = 0;
	//
	public float yaw_rotation = 0.0f;

	public float gear_deploy_ratio = 0.0f;


	public Aircraft(ExtPlaneInterface iface) {
		
		super(iface);
		
		aircraft = new Observer<DataRef>() {

			@Override
			public void update(DataRef object) {

				switch(object.getName()) {
				case TRACK: track = Float.parseFloat(object.getValue()[0]);
				break;
				case PITCH: pitch = Float.parseFloat(object.getValue()[0]);
				break;
				case ROLL: roll = Float.parseFloat(object.getValue()[0]);
				break;
				case ON_GROUND: on_ground = Integer.parseInt(object.getValue()[0]);
				break;
				case MAGNETIC_VARIATION: magnetic_variation = Float.parseFloat(object.getValue()[0]);
				break;
				case GROUNDSPEED: groundspeed = Float.parseFloat(object.getValue()[0]);
				break;
				case AOA: aoa = Float.parseFloat(object.getValue()[0]);
				break;
				case AIRSPEED_MACH:
					if(!object.getValue()[0].equals("nan")) {
						airspeed_mach = Float.parseFloat(object.getValue()[0]);
					}
					
				break;
				case RADIO_ALT_FEET: radio_alt_feet = Float.parseFloat(object.getValue()[0]);
				break;
				case TRUE_AIRSPEED_KNOTS:
					if(!object.getValue()[0].equals("nan")) {
						true_airspeed_knots = Float.parseFloat(object.getValue()[0]);
					}
					
				break;
				case AIRSPEED_ACCELERATION: airspeed_acceleration = Float.parseFloat(object.getValue()[0]);
				break;
				case MAX_SPEED: max_speed = Float.parseFloat(object.getValue()[0]);
				break;
				case MAX_MANEUVER_SPEED: max_maneuver_speed = Float.parseFloat(object.getValue()[0]);
				break;
				case MAX_MANEUVER_SPEED_SHOW: max_maneuver_speed_show = Integer.parseInt(object.getValue()[0]);
				break;
				case MIN_SPEED: min_speed = Float.parseFloat(object.getValue()[0]);
				break;
				case MIN_SPEED_SHOW: min_speed_show = Integer.parseInt(object.getValue()[0]);
				break;
				case MIN_MANEUVER_SPEED: min_maneuver_speed = Float.parseFloat(object.getValue()[0]);
				break;
				case MIN_MANEUVER_SPEED_SHOW: min_maneuver_speed_show = Integer.parseInt(object.getValue()[0]);
				break;
				case FLAPS_1: flaps_1 = Float.parseFloat(object.getValue()[0]);
				break;
				case FLAPS_1_SHOW: flaps_1_show = Double.parseDouble(object.getValue()[0]);
				break;
				case FLAPS_2: flaps_2 = Float.parseFloat(object.getValue()[0]);
				break;
				case FLAPS_2_SHOW: flaps_2_show = Double.parseDouble(object.getValue()[0]);
				break;
				case FLAPS_5: flaps_5 = Float.parseFloat(object.getValue()[0]);
				break;
				case FLAPS_5_SHOW: flaps_5_show = Double.parseDouble(object.getValue()[0]);
				break;
				case FLAPS_10: flaps_10 = Float.parseFloat(object.getValue()[0]);
				break;
				case FLAPS_10_SHOW: flaps_10_show = Double.parseDouble(object.getValue()[0]);
				break;
				case FLAPS_15: flaps_15 = Float.parseFloat(object.getValue()[0]);
				break;
				case FLAPS_15_SHOW: flaps_15_show = Double.parseDouble(object.getValue()[0]);
				break;
				case FLAPS_25: flaps_25 = Float.parseFloat(object.getValue()[0]);
				break;
				case FLAPS_25_SHOW: flaps_25_show = Double.parseDouble(object.getValue()[0]);
				break;
				case FLAPS_UP: flaps_up = Float.parseFloat(object.getValue()[0]);
				break;
				case FLAPS_UP_SHOW: flaps_up_show = Double.parseDouble(object.getValue()[0]);
				break;
				case STALL: stall = Float.parseFloat(object.getValue()[0]);
				break;
				case STALL_SHOW: stall_show = Integer.parseInt(object.getValue()[0]);
				break;
				case SPD_80_SHOW: spd_80_show = Integer.parseInt(object.getValue()[0]);
				break;
				case GEAR_DEPLOY_RATIO: gear_deploy_ratio = Float.parseFloat(object.getValue()[0]);
				break;
				case YAW_ROTATION: yaw_rotation = Float.parseFloat(object.getValue()[0]);
				break;
				case SLIP_DEG: slip_deg = Float.parseFloat(object.getValue()[0]);
				break;
				}
				for(int i = 0; i < 2; i++) {

					if (object.getName().equals(HEADING[i])) {
						heading[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(ALTITUDE[i])) {
						altitude[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(AIRSPEED[i])) {
						airspeed[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(VVI[i])) {
						vvi[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(LAT_LON[i])) {
						lat_lon[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(RUNWAY_SHOW[i])) {
						runway_show[i] = Integer.parseInt(object.getValue()[0]);
					}
					if (object.getName().equals(RUNWAY_X[i])) {
						runway_x[i] = Float.parseFloat(object.getValue()[0]);
					}
					if (object.getName().equals(RUNWAY_Y[i])) {
						runway_y[i] = Float.parseFloat(object.getValue()[0]);
					}
				}

			}

		};
	}
	
	public void subscribeDrefs() {
		
	}

	@Override
	public void includeDrefs() {
		
		iface.includeDataRef(TRACK, 0.001f);
		iface.includeDataRef(PITCH);
		iface.includeDataRef(ROLL);
		iface.includeDataRef(ON_GROUND);
		iface.includeDataRef(MAGNETIC_VARIATION, 0.001f);
		iface.includeDataRef(GROUNDSPEED, 0.01f);
		iface.includeDataRef(AOA, 0.01f);
		iface.includeDataRef(AIRSPEED_MACH, 0.001f);
		iface.includeDataRef(RADIO_ALT_FEET, 1f);
		iface.includeDataRef(TRUE_AIRSPEED_KNOTS, 0.1f);
		iface.includeDataRef(AIRSPEED_ACCELERATION, 0.01f);
		iface.includeDataRef(MAX_SPEED);
		iface.includeDataRef(MAX_MANEUVER_SPEED);
		iface.includeDataRef(MAX_MANEUVER_SPEED_SHOW);
		iface.includeDataRef(MIN_SPEED);
		iface.includeDataRef(MIN_SPEED_SHOW);
		iface.includeDataRef(MIN_MANEUVER_SPEED);
		iface.includeDataRef(MIN_MANEUVER_SPEED_SHOW);
		iface.includeDataRef(SLIP_DEG);
		iface.includeDataRef(FLAPS_1);
		iface.includeDataRef(FLAPS_1_SHOW, 0.1f);
		iface.includeDataRef(FLAPS_2);
		iface.includeDataRef(FLAPS_2_SHOW, 0.1f);
		iface.includeDataRef(FLAPS_5);
		iface.includeDataRef(FLAPS_5_SHOW, 0.1f);
		iface.includeDataRef(FLAPS_10);
		iface.includeDataRef(FLAPS_10_SHOW, 0.1f);
		iface.includeDataRef(FLAPS_15);
		iface.includeDataRef(FLAPS_15_SHOW, 0.1f);
		iface.includeDataRef(FLAPS_25);
		iface.includeDataRef(FLAPS_25_SHOW, 0.1f);
		iface.includeDataRef(FLAPS_UP);
		iface.includeDataRef(FLAPS_UP_SHOW, 0.1f);
		iface.includeDataRef(STALL);
		iface.includeDataRef(STALL_SHOW);
		iface.includeDataRef(SPD_80_SHOW);
		iface.includeDataRef(GEAR_DEPLOY_RATIO, 1f);
		iface.includeDataRef(YAW_ROTATION, 0.01f);
		//
		iface.observeDataRef(TRACK, aircraft);
		iface.observeDataRef(PITCH, aircraft);
		iface.observeDataRef(ROLL, aircraft);
		iface.observeDataRef(ON_GROUND, aircraft);
		iface.observeDataRef(MAGNETIC_VARIATION, aircraft);
		iface.observeDataRef(GROUNDSPEED, aircraft);
		iface.observeDataRef(AOA, aircraft);
		iface.observeDataRef(AIRSPEED_MACH, aircraft);
		iface.observeDataRef(RADIO_ALT_FEET, aircraft);
		iface.observeDataRef(TRUE_AIRSPEED_KNOTS, aircraft);
		iface.observeDataRef(AIRSPEED_ACCELERATION, aircraft);
		iface.observeDataRef(MAX_SPEED, aircraft);
		iface.observeDataRef(MAX_MANEUVER_SPEED, aircraft);
		iface.observeDataRef(MAX_MANEUVER_SPEED_SHOW, aircraft);
		iface.observeDataRef(MIN_SPEED, aircraft);
		iface.observeDataRef(MIN_SPEED_SHOW, aircraft);
		iface.observeDataRef(MIN_MANEUVER_SPEED, aircraft);
		iface.observeDataRef(MIN_MANEUVER_SPEED_SHOW, aircraft);
		iface.observeDataRef(SLIP_DEG, aircraft);
		iface.observeDataRef(FLAPS_1, aircraft);
		iface.observeDataRef(FLAPS_1_SHOW, aircraft);
		iface.observeDataRef(FLAPS_2, aircraft);
		iface.observeDataRef(FLAPS_2_SHOW, aircraft);
		iface.observeDataRef(FLAPS_5, aircraft);
		iface.observeDataRef(FLAPS_5_SHOW, aircraft);
		iface.observeDataRef(FLAPS_10, aircraft);
		iface.observeDataRef(FLAPS_10_SHOW, aircraft);
		iface.observeDataRef(FLAPS_15, aircraft);
		iface.observeDataRef(FLAPS_15_SHOW, aircraft);
		iface.observeDataRef(FLAPS_25, aircraft);
		iface.observeDataRef(FLAPS_25_SHOW, aircraft);
		iface.observeDataRef(FLAPS_UP, aircraft);
		iface.observeDataRef(FLAPS_UP_SHOW, aircraft);
		iface.observeDataRef(STALL, aircraft);
		iface.observeDataRef(STALL_SHOW, aircraft);
		iface.observeDataRef(SPD_80_SHOW, aircraft);
		iface.observeDataRef(GEAR_DEPLOY_RATIO, aircraft);
		iface.observeDataRef(YAW_ROTATION, aircraft);

		for(int i = 0; i < 2; i++) {
			iface.includeDataRef(HEADING[i], 0.01f);
			iface.includeDataRef(ALTITUDE[i], 0.01f);
			iface.includeDataRef(AIRSPEED[i], 0.01f);
			iface.includeDataRef(VVI[i], 0.01f);
			iface.includeDataRef(LAT_LON[i], 0.0001f);
			iface.includeDataRef(RUNWAY_SHOW[i]);
			iface.includeDataRef(RUNWAY_X[i]);
			iface.includeDataRef(RUNWAY_Y[i]);
			iface.observeDataRef(HEADING[i], aircraft);
			iface.observeDataRef(ALTITUDE[i], aircraft);
			iface.observeDataRef(AIRSPEED[i], aircraft);
			iface.observeDataRef(VVI[i], aircraft);
			iface.observeDataRef(LAT_LON[i], aircraft);
			iface.observeDataRef(RUNWAY_SHOW[i], aircraft);
			iface.observeDataRef(RUNWAY_X[i], aircraft);
			iface.observeDataRef(RUNWAY_Y[i], aircraft);
		}
		
	}

	@Override
	public void excludeDrefs() {
		
		iface.excludeDataRef(TRACK);
		iface.excludeDataRef(PITCH);
		iface.excludeDataRef(ROLL);
		iface.excludeDataRef(ON_GROUND);
		iface.excludeDataRef(MAGNETIC_VARIATION);
		iface.excludeDataRef(GROUNDSPEED);
		iface.excludeDataRef(AOA);
		iface.excludeDataRef(AIRSPEED_MACH);
		iface.excludeDataRef(RADIO_ALT_FEET);
		iface.excludeDataRef(TRUE_AIRSPEED_KNOTS);
		iface.excludeDataRef(AIRSPEED_ACCELERATION);
		iface.excludeDataRef(MAX_SPEED);
		iface.excludeDataRef(MAX_MANEUVER_SPEED);
		iface.excludeDataRef(MAX_MANEUVER_SPEED_SHOW);
		iface.excludeDataRef(MIN_SPEED);
		iface.excludeDataRef(MIN_SPEED_SHOW);
		iface.excludeDataRef(MIN_MANEUVER_SPEED);
		iface.excludeDataRef(MIN_MANEUVER_SPEED_SHOW);
		iface.excludeDataRef(SLIP_DEG);
		iface.excludeDataRef(FLAPS_1);
		iface.excludeDataRef(FLAPS_1_SHOW);
		iface.excludeDataRef(FLAPS_2);
		iface.excludeDataRef(FLAPS_2_SHOW);
		iface.excludeDataRef(FLAPS_5);
		iface.excludeDataRef(FLAPS_5_SHOW);
		iface.excludeDataRef(FLAPS_10);
		iface.excludeDataRef(FLAPS_10_SHOW);
		iface.excludeDataRef(FLAPS_15);
		iface.excludeDataRef(FLAPS_15_SHOW);
		iface.excludeDataRef(FLAPS_25);
		iface.excludeDataRef(FLAPS_25_SHOW);
		iface.excludeDataRef(FLAPS_UP);
		iface.excludeDataRef(FLAPS_UP_SHOW);
		iface.excludeDataRef(STALL);
		iface.excludeDataRef(STALL_SHOW);
		iface.excludeDataRef(SPD_80_SHOW);
		iface.excludeDataRef(GEAR_DEPLOY_RATIO);
		iface.excludeDataRef(YAW_ROTATION);
		//
		iface.unObserveDataRef(TRACK, aircraft);
		iface.unObserveDataRef(PITCH, aircraft);
		iface.unObserveDataRef(ROLL, aircraft);
		iface.unObserveDataRef(ON_GROUND, aircraft);
		iface.unObserveDataRef(MAGNETIC_VARIATION, aircraft);
		iface.unObserveDataRef(GROUNDSPEED, aircraft);
		iface.unObserveDataRef(AOA, aircraft);
		iface.unObserveDataRef(AIRSPEED_MACH, aircraft);
		iface.unObserveDataRef(RADIO_ALT_FEET, aircraft);
		iface.unObserveDataRef(TRUE_AIRSPEED_KNOTS, aircraft);
		iface.unObserveDataRef(AIRSPEED_ACCELERATION, aircraft);
		iface.unObserveDataRef(MAX_SPEED, aircraft);
		iface.unObserveDataRef(MAX_MANEUVER_SPEED, aircraft);
		iface.unObserveDataRef(MAX_MANEUVER_SPEED_SHOW, aircraft);
		iface.unObserveDataRef(MIN_SPEED, aircraft);
		iface.unObserveDataRef(MIN_SPEED_SHOW, aircraft);
		iface.unObserveDataRef(MIN_MANEUVER_SPEED, aircraft);
		iface.unObserveDataRef(MIN_MANEUVER_SPEED_SHOW, aircraft);
		iface.unObserveDataRef(SLIP_DEG, aircraft);
		iface.unObserveDataRef(FLAPS_1, aircraft);
		iface.unObserveDataRef(FLAPS_1_SHOW, aircraft);
		iface.unObserveDataRef(FLAPS_2, aircraft);
		iface.unObserveDataRef(FLAPS_2_SHOW, aircraft);
		iface.unObserveDataRef(FLAPS_5, aircraft);
		iface.unObserveDataRef(FLAPS_5_SHOW, aircraft);
		iface.unObserveDataRef(FLAPS_10, aircraft);
		iface.unObserveDataRef(FLAPS_10_SHOW, aircraft);
		iface.unObserveDataRef(FLAPS_15, aircraft);
		iface.unObserveDataRef(FLAPS_15_SHOW, aircraft);
		iface.unObserveDataRef(FLAPS_25, aircraft);
		iface.unObserveDataRef(FLAPS_25_SHOW, aircraft);
		iface.unObserveDataRef(FLAPS_UP, aircraft);
		iface.unObserveDataRef(FLAPS_UP_SHOW, aircraft);
		iface.unObserveDataRef(STALL, aircraft);
		iface.unObserveDataRef(STALL_SHOW, aircraft);
		iface.unObserveDataRef(SPD_80_SHOW, aircraft);
		iface.unObserveDataRef(GEAR_DEPLOY_RATIO, aircraft);
		iface.unObserveDataRef(YAW_ROTATION, aircraft);
		
		for(int i = 0; i < 2; i++) {
			iface.excludeDataRef(HEADING[i]);
			iface.excludeDataRef(ALTITUDE[i]);
			iface.excludeDataRef(AIRSPEED[i]);
			iface.excludeDataRef(VVI[i]);
			iface.excludeDataRef(LAT_LON[i]);
			iface.excludeDataRef(RUNWAY_SHOW[i]);
			iface.excludeDataRef(RUNWAY_X[i]);
			iface.excludeDataRef(RUNWAY_Y[i]);
			iface.unObserveDataRef(HEADING[i], aircraft);
			iface.unObserveDataRef(ALTITUDE[i], aircraft);
			iface.unObserveDataRef(AIRSPEED[i], aircraft);
			iface.unObserveDataRef(VVI[i], aircraft);
			iface.unObserveDataRef(LAT_LON[i], aircraft);
			iface.unObserveDataRef(RUNWAY_SHOW[i], aircraft);
			iface.unObserveDataRef(RUNWAY_X[i], aircraft);
			iface.unObserveDataRef(RUNWAY_Y[i], aircraft);
			
		}	
	}
}