/**
 * 
 * Copyright (C) 2018  Andre Els (https://www.facebook.com/sum1els737)
 * 
 * Inspired by XHSI (http://xhsi.sourceforge.net)
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
package org.andreels.zhsi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class ZHSIPreferences {

	private static final String PROPERTY_FILENAME = System.getProperty("user.dir") + "/ZHSI.properties";

	public static final String DU_WIDTH = "686";
	public static final String DU_HEIGHT = "674";
	public static final String INST_WIDTH = "300";
	public static final String INST_HEIGHT = "300";
	
	public static final String PREF_APTNAV_DIR = "aptnav.dir";
	public static final String APTNAV_VERSION = "aptnav.version";
	public static final String PREF_EGPWS_DB_DIR = "egpws_database.dir";
	public static final String PREF_EXTPLANE_SERVER = "extplane.ip";
	public static final String PREF_PORT = "port";
	public static final String PREF_GROUP = "multicast.group";
	public static final String PREF_MULTICAST = "multicast.enable";
	public static final String PREF_LOGLEVEL = "loglevel";

	public static final String PREF_START_MINIMIZED = "start.minimized.enable";
	public static final String PREF_ALWAYSONTOP = "alwaysontop.enable";
	public static final String PREF_FRAMERATE = "framerate";
	
	public static final String PREF_UI_POS_X = "ui.posx";
	public static final String PREF_UI_POS_Y = "ui.posy";
	
	public static final String PREF_TERRAIN_WEATHER_BUFFEREDIMAGE_X = "terrain.weather.bufferedimage.x";
	public static final String PREF_TERRAIN_WEATHER_BUFFEREDIMAGE_Y = "terrain.weather.bufferedimage.y";

	public static final String PREF_CPT_OUTBD_ENABLE = "cptoutbd.enable";
	public static final String PREF_CPT_INBD_ENABLE = "cptinbd.enable";
	public static final String PREF_FO_OUTBD_ENABLE = "fooutbd.enable";
	public static final String PREF_FO_INBD_ENABLE = "foinbd.enable";
	public static final String PREF_UP_EICAS_ENABLE = "upeicas.enable";
	public static final String PREF_LO_EICAS_ENABLE = "loeicas.enable";

	public static final String PREF_CPT_OUTBD_POS_X = "cptoutbd.posx";
	public static final String PREF_CPT_OUTBD_POS_Y = "cptoutbd.posy";
	public static final String PREF_CPT_OUTBD_WIDTH = "cptoutbd.width";
	public static final String PREF_CPT_OUTBD_HEIGHT = "cptoutbd.height";

	public static final String PREF_CPT_INBD_POS_X = "cptinbd.posx";
	public static final String PREF_CPT_INBD_POS_Y = "cptinbd.posy";
	public static final String PREF_CPT_INBD_WIDTH = "cptinbd.width";
	public static final String PREF_CPT_INBD_HEIGHT = "cptinbd.height";

	public static final String PREF_FO_OUTBD_POS_X = "fooutbd.posx";
	public static final String PREF_FO_OUTBD_POS_Y = "fooutbd.posy";
	public static final String PREF_FO_OUTBD_WIDTH = "fooutbd.width";
	public static final String PREF_FO_OUTBD_HEIGHT = "fooutbd.height";

	public static final String PREF_FO_INBD_POS_X = "foinbd.posx";
	public static final String PREF_FO_INBD_POS_Y = "foinbd.posy";
	public static final String PREF_FO_INBD_WIDTH = "foinbd.width";
	public static final String PREF_FO_INBD_HEIGHT = "foinbd.height";

	public static final String PREF_UP_EICAS_POS_X = "upeicas.posx";
	public static final String PREF_UP_EICAS_POS_Y = "upeicas.posy";
	public static final String PREF_UP_EICAS_WIDTH = "upeicas.width";
	public static final String PREF_UP_EICAS_HEIGHT = "upeicas.height";

	public static final String PREF_LO_EICAS_POS_X = "loeicas.posx";
	public static final String PREF_LO_EICAS_POS_Y = "loeicas.posy";
	public static final String PREF_LO_EICAS_WIDTH = "loeicas.width";
	public static final String PREF_LO_EICAS_HEIGHT = "loeicas.height";
	
	public static final String PREF_ISFD_ENABLE = "isfd.enable";
	public static final String PREF_ISFD_POS_X = "isfd.posx";
	public static final String PREF_ISFD_POS_Y = "isfd.posy";
	public static final String PREF_ISFD_WIDTH = "isfd.width";
	public static final String PREF_ISFD_HEIGHT = "isfd.height";
	
	public static final String PREF_RMI_ENABLE = "rmi.enable";
	public static final String PREF_RMI_POS_X = "rmi.posx";
	public static final String PREF_RMI_POS_Y = "rmi.posy";
	public static final String PREF_RMI_WIDTH = "rmi.width";
	public static final String PREF_RMI_HEIGHT = "rmi.height";
	
	public static final String PREF_CPTCHRONO_ENABLE = "cptchrono.enable";
	public static final String PREF_CPTCHRONO_POS_X = "cptchrono.posx";
	public static final String PREF_CPTCHRONO_POS_Y = "cptchrono.posy";
	public static final String PREF_CPTCHRONO_WIDTH = "cptchrono.width";
	public static final String PREF_CPTCHRONO_HEIGHT = "cptchrono.height";
	
	public static final String PREF_FOCHRONO_ENABLE = "fochromo.enable";
	public static final String PREF_FOCHRONO_POS_X = "fochromo.posx";
	public static final String PREF_FOCHRONO_POS_Y = "fochromo.posy";
	public static final String PREF_FOCHRONO_WIDTH = "fochromo.width";
	public static final String PREF_FOCHRONO_HEIGHT = "fochromo.height";
	
	public static final String PREF_ELECPANEL_ENABLE = "elecpanel.enable";
	public static final String PREF_ELECPANEL_POS_X = "elecpanel.posx";
	public static final String PREF_ELECPANEL_POS_Y = "elecpanel.posy";
	public static final String PREF_ELECPANEL_WIDTH = "elecpanel.width";
	public static final String PREF_ELECPANEL_HEIGHT = "elecpanel.height";
	
	public static final String PREF_IRSPANEL_ENABLE = "irspanel.enable";
	public static final String PREF_IRSPANEL_POS_X = "irspanel.posx";
	public static final String PREF_IRSPANEL_POS_Y = "irspanel.posy";
	public static final String PREF_IRSPANEL_WIDTH = "irspanel.width";
	public static final String PREF_IRSPANEL_HEIGHT = "irspanel.height";
	
	public static final String PREF_FLTALTPANEL_ENABLE = "fltaltpanel.enable";
	public static final String PREF_FLTALTPANEL_POS_X = "fltaltpanel.posx";
	public static final String PREF_FLTALTPANEL_POS_Y = "fltaltpanel.posy";
	public static final String PREF_FLTALTPANEL_WIDTH = "fltaltpanel.width";
	public static final String PREF_FLTALTPANEL_HEIGHT = "fltaltpanel.height";
	
	public static final String PREF_LANDALTPANEL_ENABLE = "landaltpanel.enable";
	public static final String PREF_LANDALTPANEL_POS_X = "landaltpanel.posx";
	public static final String PREF_LANDALTPANEL_POS_Y = "landaltpanel.posy";
	public static final String PREF_LANDALTPANEL_WIDTH = "landaltpanel.width";
	public static final String PREF_LANDALTPANEL_HEIGHT = "landaltpanel.height";
	
	public static final String PREF_ANNUNWINDOW_ENABLE = "annunwindow.enable";
	public static final String PREF_ANNUNWINDOW_POS_X = "annunwindow.posx";
	public static final String PREF_ANNUNWINDOW_POS_Y = "annunwindow.posy";
	public static final String PREF_ANNUNWINDOW_WIDTH = "annunwindow.width";
	public static final String PREF_ANNUNWINDOW_HEIGHT = "annunwindow.height";
	
	public static final String PREF_FUEL_OVER_UNDER = "fuel.overunder";
	public static final String PREF_FUEL_LBS = "fuel.lbs";
	public static final String PREF_AOA_INDICATOR = "aoa.enabled";
	public static final String PREF_TRIM_INDICATOR = "stab.trim.enabled";
	public static final String PREF_RUDDER_INDICATOR = "rudder.trim.enabled";
	public static final String PREF_HARDWARE_LEVERS = "hw.throttle.levers.enabled";
	public static final String PREF_RANGE_RINGS = "range.rings.enabled";
	public static final String PREF_XRAAS_ENABLE = "xraas.enabled";
	public static final String PREF_XRAAS_FONT_SIZE = "xraas.font.size";
	
	public static final String PREF_FLAP_GAUGE_ENABLE = "flapgauge.enable";
	public static final String PREF_FLAP_GAUGE_POS_X = "flapgauge.posx";
	public static final String PREF_FLAP_GAUGE_POS_Y = "flapgauge.posy";
	public static final String PREF_FLAP_GAUGE_WIDTH = "flapgauge.width";
	public static final String PREF_FLAP_GAUGE_HEIGHT = "flapgauge.height";
	
	public static final String PREF_WEATHER_DETAIL = "weather.detail";
	public static final String PREF_TERRAIN_DETAIL = "terrain.detail";
	
	public static final String PREF_ARPT_MIN_RWY_LENGTH = "airport.minimum.runway.length";
	
	public static final String PREF_DCVOLTS_X = "elec.dcvolts.x";
	public static final String PREF_DCAMPS_X = "elec.dcamps.x";
	public static final String PREF_ACVOLTS_X = "elec.acvolts.x";
	public static final String PREF_ACAMPS_X = "elec.acamps.x";
	public static final String PREF_CPSFREQ_X = "elec.cpsfreq.x";
	
	public static final String USER_FIRST_NAME = "user.firstname";
	public static final String USER_LAST_NAME = "user.lastname";
	public static final String USER_EMAIL = "user.email";
	public static final String USER_KEY = "user.key";
	
	public static final String PREF_SHOW_FPS = "show.fps";
	
	/*
	public static final String INST_HOR_SKY_COLOR = "horizon.sky.color";
	public static final String INST_HOR_GRD_COLOR = "horizon.ground.color";
	public static final String INST_GRAY_COLOR = "inst.gray.color";
	public static final String INST_WHITE_COLOR = "inst.white.color";
	public static final String INST_MAGENTA_COLOR = "inst.magenta.color";
	public static final String INST_RED_COLOR = "inst.red.color";
	public static final String INST_AMBER_COLOR = "inst.amber.color";
	public static final String INST_LIME_COLOR = "inst.lime.color";
	public static final String INST_CYAN_COLOR = "inst.cyan.color";
	*/
	
	public static final String WXR_COLOR_0 = "wxr.color.0";
	public static final String WXR_COLOR_1 = "wxr.color.1";
	public static final String WXR_COLOR_2 = "wxr.color.2";
	public static final String WXR_COLOR_3 = "wxr.color.3";
	public static final String WXR_COLOR_4 = "wxr.color.4";
	public static final String WXR_COLOR_5 = "wxr.color.5";
	public static final String WXR_COLOR_6 = "wxr.color.6";
	public static final String WXR_COLOR_7 = "wxr.color.7";
	public static final String WXR_COLOR_8 = "wxr.color.8";
	public static final String WXR_COLOR_9 = "wxr.color.9";
	
	public static final String PREF_RMI_COLOR = "rmi.color";
	public static final String PREF_IRS_COLOR = "irs.color";

	private static ZHSIPreferences instance;
	private Properties preferences;
	private boolean unsaved_changes;

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");

	/**
	 * Attempts to load preferences file. In case no preferences file can be found,
	 * a new preferences file with default values is created.
	 */
	 
	private ZHSIPreferences() {
		this.preferences = new Properties();
		this.unsaved_changes = false;
		load_preferences();
		ensure_preferences_complete();
		validate_preferences();
	}

	private void load_preferences() {

		logger.fine("Reading " + PROPERTY_FILENAME);
		try {
			FileInputStream fis = new FileInputStream(PROPERTY_FILENAME);
			this.preferences.load(fis);
			if (fis != null)
				fis.close();
		} catch (IOException e) {
			logger.warning("Could not read properties file. Creating a new property file with default values ("
					+ e.toString() + ") ... ");
		}
	}

	private void ensure_preferences_complete() {

		if (preferences == null) {
			throw new RuntimeException("Prefereces object not initialized!");
		}

		if (!this.preferences.containsKey(PREF_APTNAV_DIR)) {
			this.preferences.setProperty(PREF_APTNAV_DIR, "");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(APTNAV_VERSION)) {
			this.preferences.setProperty(APTNAV_VERSION, "0");
			this.unsaved_changes = true;
		}

		if (!this.preferences.containsKey(PREF_EGPWS_DB_DIR)) {
			this.preferences.setProperty(PREF_EGPWS_DB_DIR, "");
			this.unsaved_changes = true;
		}

		if (!this.preferences.containsKey(PREF_EXTPLANE_SERVER)) {
			this.preferences.setProperty(PREF_EXTPLANE_SERVER, "127.0.0.1");
			this.unsaved_changes = true;
		}

		if (!this.preferences.containsKey(PREF_LOGLEVEL)) {
			this.preferences.setProperty(PREF_LOGLEVEL, "CONFIG");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_START_MINIMIZED)) {
			this.preferences.setProperty(PREF_START_MINIMIZED, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ALWAYSONTOP)) {
			this.preferences.setProperty(PREF_ALWAYSONTOP, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FRAMERATE)) {
			this.preferences.setProperty(PREF_FRAMERATE, "30");
			this.unsaved_changes = true;
		}

		if (!this.preferences.containsKey(PREF_CPT_OUTBD_ENABLE)) {
			this.preferences.setProperty(PREF_CPT_OUTBD_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPT_INBD_ENABLE)) {
			this.preferences.setProperty(PREF_CPT_INBD_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_OUTBD_ENABLE)) {
			this.preferences.setProperty(PREF_FO_OUTBD_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_INBD_ENABLE)) {
			this.preferences.setProperty(PREF_FO_INBD_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_UP_EICAS_ENABLE)) {
			this.preferences.setProperty(PREF_UP_EICAS_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_LO_EICAS_ENABLE)) {
			this.preferences.setProperty(PREF_LO_EICAS_ENABLE, "false");
			this.unsaved_changes = true;
		}

		if (!this.preferences.containsKey(PREF_CPT_OUTBD_POS_X)) {
			this.preferences.setProperty(PREF_CPT_OUTBD_POS_X, "300");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPT_OUTBD_POS_Y)) {
			this.preferences.setProperty(PREF_CPT_OUTBD_POS_Y, "300");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPT_OUTBD_WIDTH)) {
			this.preferences.setProperty(PREF_CPT_OUTBD_WIDTH, DU_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPT_OUTBD_HEIGHT)) {
			this.preferences.setProperty(PREF_CPT_OUTBD_HEIGHT, DU_HEIGHT);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPT_INBD_POS_X)) {
			this.preferences.setProperty(PREF_CPT_INBD_POS_X, "320");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPT_INBD_POS_Y)) {
			this.preferences.setProperty(PREF_CPT_INBD_POS_Y, "320");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPT_INBD_WIDTH)) {
			this.preferences.setProperty(PREF_CPT_INBD_WIDTH, DU_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPT_INBD_HEIGHT)) {
			this.preferences.setProperty(PREF_CPT_INBD_HEIGHT, DU_HEIGHT);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_OUTBD_POS_X)) {
			this.preferences.setProperty(PREF_FO_OUTBD_POS_X, "340");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_OUTBD_POS_Y)) {
			this.preferences.setProperty(PREF_FO_OUTBD_POS_Y, "340");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_OUTBD_WIDTH)) {
			this.preferences.setProperty(PREF_FO_OUTBD_WIDTH, DU_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_OUTBD_HEIGHT)) {
			this.preferences.setProperty(PREF_FO_OUTBD_HEIGHT, DU_HEIGHT);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_INBD_POS_X)) {
			this.preferences.setProperty(PREF_FO_INBD_POS_X, "360");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_INBD_POS_Y)) {
			this.preferences.setProperty(PREF_FO_INBD_POS_Y, "360");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_INBD_WIDTH)) {
			this.preferences.setProperty(PREF_FO_INBD_WIDTH, DU_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FO_INBD_HEIGHT)) {
			this.preferences.setProperty(PREF_FO_INBD_HEIGHT, DU_HEIGHT);
			this.unsaved_changes = true;
		}

		if (!this.preferences.containsKey(PREF_UP_EICAS_POS_X)) {
			this.preferences.setProperty(PREF_UP_EICAS_POS_X, "380");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_UP_EICAS_POS_Y)) {
			this.preferences.setProperty(PREF_UP_EICAS_POS_Y, "380");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_UP_EICAS_WIDTH)) {
			this.preferences.setProperty(PREF_UP_EICAS_WIDTH, DU_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_UP_EICAS_HEIGHT)) {
			this.preferences.setProperty(PREF_UP_EICAS_HEIGHT, DU_HEIGHT);
			this.unsaved_changes = true;
		}

		if (!this.preferences.containsKey(PREF_LO_EICAS_POS_X)) {
			this.preferences.setProperty(PREF_LO_EICAS_POS_X, "400");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_LO_EICAS_POS_Y)) {
			this.preferences.setProperty(PREF_LO_EICAS_POS_Y, "400");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_LO_EICAS_WIDTH)) {
			this.preferences.setProperty(PREF_LO_EICAS_WIDTH, DU_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_LO_EICAS_HEIGHT)) {
			this.preferences.setProperty(PREF_LO_EICAS_HEIGHT, DU_HEIGHT);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ISFD_ENABLE)) {
			this.preferences.setProperty(PREF_ISFD_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ISFD_POS_X)) {
			this.preferences.setProperty(PREF_ISFD_POS_X, "500");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ISFD_POS_Y)) {
			this.preferences.setProperty(PREF_ISFD_POS_Y, "500");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ISFD_WIDTH)) {
			this.preferences.setProperty(PREF_ISFD_WIDTH, INST_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ISFD_HEIGHT)) {
			this.preferences.setProperty(PREF_ISFD_HEIGHT, INST_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_RMI_ENABLE)) {
			this.preferences.setProperty(PREF_RMI_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_RMI_POS_X)) {
			this.preferences.setProperty(PREF_RMI_POS_X, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_RMI_POS_Y)) {
			this.preferences.setProperty(PREF_RMI_POS_Y, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_RMI_WIDTH)) {
			this.preferences.setProperty(PREF_RMI_WIDTH, INST_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_RMI_HEIGHT)) {
			this.preferences.setProperty(PREF_RMI_HEIGHT, INST_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPTCHRONO_ENABLE)) {
			this.preferences.setProperty(PREF_CPTCHRONO_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPTCHRONO_POS_X)) {
			this.preferences.setProperty(PREF_CPTCHRONO_POS_X, "500");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPTCHRONO_POS_Y)) {
			this.preferences.setProperty(PREF_CPTCHRONO_POS_Y, "500");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPTCHRONO_WIDTH)) {
			this.preferences.setProperty(PREF_CPTCHRONO_WIDTH, INST_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPTCHRONO_HEIGHT)) {
			this.preferences.setProperty(PREF_CPTCHRONO_HEIGHT, INST_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FOCHRONO_ENABLE)) {
			this.preferences.setProperty(PREF_FOCHRONO_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FOCHRONO_POS_X)) {
			this.preferences.setProperty(PREF_FOCHRONO_POS_X, "500");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FOCHRONO_POS_Y)) {
			this.preferences.setProperty(PREF_FOCHRONO_POS_Y, "500");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FOCHRONO_WIDTH)) {
			this.preferences.setProperty(PREF_FOCHRONO_WIDTH, INST_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FOCHRONO_HEIGHT)) {
			this.preferences.setProperty(PREF_FOCHRONO_HEIGHT, INST_WIDTH);
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_UI_POS_X)) {
			this.preferences.setProperty(PREF_UI_POS_X, "none");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_UI_POS_Y)) {
			this.preferences.setProperty(PREF_UI_POS_Y, "none");
			this.unsaved_changes = true;
		}

		if (!this.preferences.containsKey(PREF_TERRAIN_WEATHER_BUFFEREDIMAGE_X)) {
			this.preferences.setProperty(PREF_TERRAIN_WEATHER_BUFFEREDIMAGE_X, "1029");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_TERRAIN_WEATHER_BUFFEREDIMAGE_Y)) {
			this.preferences.setProperty(PREF_TERRAIN_WEATHER_BUFFEREDIMAGE_Y, "1011");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FUEL_OVER_UNDER)) {
			this.preferences.setProperty(PREF_FUEL_OVER_UNDER, "true");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FUEL_LBS)) {
			this.preferences.setProperty(PREF_FUEL_LBS, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_AOA_INDICATOR)) {
			this.preferences.setProperty(PREF_AOA_INDICATOR, "true");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_TRIM_INDICATOR)) {
			this.preferences.setProperty(PREF_TRIM_INDICATOR, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_RUDDER_INDICATOR)) {
			this.preferences.setProperty(PREF_RUDDER_INDICATOR, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_HARDWARE_LEVERS)) {
			this.preferences.setProperty(PREF_HARDWARE_LEVERS, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_RANGE_RINGS)) {
			this.preferences.setProperty(PREF_RANGE_RINGS, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_XRAAS_ENABLE)) {
			this.preferences.setProperty(PREF_XRAAS_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_XRAAS_FONT_SIZE)) {
			this.preferences.setProperty(PREF_XRAAS_FONT_SIZE, "100");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLAP_GAUGE_ENABLE)) {
			this.preferences.setProperty(PREF_FLAP_GAUGE_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLAP_GAUGE_POS_X)) {
			this.preferences.setProperty(PREF_FLAP_GAUGE_POS_X, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLAP_GAUGE_POS_Y)) {
			this.preferences.setProperty(PREF_FLAP_GAUGE_POS_Y, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLAP_GAUGE_WIDTH)) {
			this.preferences.setProperty(PREF_FLAP_GAUGE_WIDTH, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLAP_GAUGE_HEIGHT)) {
			this.preferences.setProperty(PREF_FLAP_GAUGE_HEIGHT, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ELECPANEL_ENABLE)) {
			this.preferences.setProperty(PREF_ELECPANEL_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ELECPANEL_POS_X)) {
			this.preferences.setProperty(PREF_ELECPANEL_POS_X, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ELECPANEL_POS_Y)) {
			this.preferences.setProperty(PREF_ELECPANEL_POS_Y, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ELECPANEL_WIDTH)) {
			this.preferences.setProperty(PREF_ELECPANEL_WIDTH, "400");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ELECPANEL_HEIGHT)) {
			this.preferences.setProperty(PREF_ELECPANEL_HEIGHT, "250");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_IRSPANEL_ENABLE)) {
			this.preferences.setProperty(PREF_IRSPANEL_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_IRSPANEL_POS_X)) {
			this.preferences.setProperty(PREF_IRSPANEL_POS_X, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_IRSPANEL_POS_Y)) {
			this.preferences.setProperty(PREF_IRSPANEL_POS_Y, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_IRSPANEL_WIDTH)) {
			this.preferences.setProperty(PREF_IRSPANEL_WIDTH, "500");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_IRSPANEL_HEIGHT)) {
			this.preferences.setProperty(PREF_IRSPANEL_HEIGHT, "70");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLTALTPANEL_ENABLE)) {
			this.preferences.setProperty(PREF_FLTALTPANEL_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLTALTPANEL_POS_X)) {
			this.preferences.setProperty(PREF_FLTALTPANEL_POS_X, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLTALTPANEL_POS_Y)) {
			this.preferences.setProperty(PREF_FLTALTPANEL_POS_Y, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLTALTPANEL_WIDTH)) {
			this.preferences.setProperty(PREF_FLTALTPANEL_WIDTH, "250");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_FLTALTPANEL_HEIGHT)) {
			this.preferences.setProperty(PREF_FLTALTPANEL_HEIGHT, "70");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_LANDALTPANEL_ENABLE)) {
			this.preferences.setProperty(PREF_LANDALTPANEL_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_LANDALTPANEL_POS_X)) {
			this.preferences.setProperty(PREF_LANDALTPANEL_POS_X, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_LANDALTPANEL_POS_Y)) {
			this.preferences.setProperty(PREF_LANDALTPANEL_POS_Y, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_LANDALTPANEL_WIDTH)) {
			this.preferences.setProperty(PREF_LANDALTPANEL_WIDTH, "250");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_LANDALTPANEL_HEIGHT)) {
			this.preferences.setProperty(PREF_LANDALTPANEL_HEIGHT, "70");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ANNUNWINDOW_ENABLE)) {
			this.preferences.setProperty(PREF_ANNUNWINDOW_ENABLE, "false");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ANNUNWINDOW_POS_X)) {
			this.preferences.setProperty(PREF_ANNUNWINDOW_POS_X, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ANNUNWINDOW_POS_Y)) {
			this.preferences.setProperty(PREF_ANNUNWINDOW_POS_Y, "200");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ANNUNWINDOW_WIDTH)) {
			this.preferences.setProperty(PREF_ANNUNWINDOW_WIDTH, "500");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ANNUNWINDOW_HEIGHT)) {
			this.preferences.setProperty(PREF_ANNUNWINDOW_HEIGHT, "500");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_WEATHER_DETAIL)) {
			this.preferences.setProperty(PREF_WEATHER_DETAIL, "high");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_TERRAIN_DETAIL)) {
			this.preferences.setProperty(PREF_TERRAIN_DETAIL, "high");
			this.unsaved_changes = true;
		}

		if (!this.preferences.containsKey(PREF_DCVOLTS_X)) {
			this.preferences.setProperty(PREF_DCVOLTS_X, "70");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_DCAMPS_X)) {
			this.preferences.setProperty(PREF_DCAMPS_X, "70");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ACVOLTS_X)) {
			this.preferences.setProperty(PREF_ACVOLTS_X, "360");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ACAMPS_X)) {
			this.preferences.setProperty(PREF_ACAMPS_X, "210");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_CPSFREQ_X)) {
			this.preferences.setProperty(PREF_CPSFREQ_X, "360");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_ARPT_MIN_RWY_LENGTH)) {
			this.preferences.setProperty(PREF_ARPT_MIN_RWY_LENGTH, "1700");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_SHOW_FPS)) {
			this.preferences.setProperty(PREF_SHOW_FPS, "false");
			this.unsaved_changes = true;
		}
		
		/*
		if (!this.preferences.containsKey(INST_HOR_SKY_COLOR)) {
			this.preferences.setProperty(INST_HOR_SKY_COLOR, "0x0172DEFF");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(INST_HOR_GRD_COLOR)) {
			this.preferences.setProperty(INST_HOR_GRD_COLOR, "0x5C3603FF");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(INST_GRAY_COLOR)) {
			this.preferences.setProperty(INST_GRAY_COLOR, "0x353539FF");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(INST_WHITE_COLOR)) {
			this.preferences.setProperty(INST_WHITE_COLOR, "0xE5E5E5FF");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(INST_MAGENTA_COLOR)) {
			this.preferences.setProperty(INST_MAGENTA_COLOR, "0xFA7CFAFF");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(INST_LIME_COLOR)) {
			this.preferences.setProperty(INST_LIME_COLOR, "0x00FF00FF");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(INST_AMBER_COLOR)) {
			this.preferences.setProperty(INST_AMBER_COLOR, "0xFBA81FFF");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(INST_RED_COLOR)) {
			this.preferences.setProperty(INST_RED_COLOR, "0xFF0000FF");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(INST_CYAN_COLOR)) {
			this.preferences.setProperty(INST_CYAN_COLOR, "0x00CCFFFF");
			this.unsaved_changes = true;
		}
		*/
		
		if (!this.preferences.containsKey(WXR_COLOR_0)) {
			this.preferences.setProperty(WXR_COLOR_0, "0x000000");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(WXR_COLOR_1)) {
			this.preferences.setProperty(WXR_COLOR_1, "0x005000");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(WXR_COLOR_2)) {
			this.preferences.setProperty(WXR_COLOR_2, "0x002800");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(WXR_COLOR_3)) {
			this.preferences.setProperty(WXR_COLOR_3, "0x006400");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(WXR_COLOR_4)) {
			this.preferences.setProperty(WXR_COLOR_4, "0x007800");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(WXR_COLOR_5)) {
			this.preferences.setProperty(WXR_COLOR_5, "0x787800");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(WXR_COLOR_6)) {
			this.preferences.setProperty(WXR_COLOR_6, "0x8C8C00");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(WXR_COLOR_7)) {
			this.preferences.setProperty(WXR_COLOR_7, "0x8C0000");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(WXR_COLOR_8)) {
			this.preferences.setProperty(WXR_COLOR_8, "0xA00000");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(WXR_COLOR_9)) {
			this.preferences.setProperty(WXR_COLOR_9, "0xA000A0");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_RMI_COLOR)) {
			this.preferences.setProperty(PREF_RMI_COLOR, "0xFF0000");
			this.unsaved_changes = true;
		}
		
		if (!this.preferences.containsKey(PREF_IRS_COLOR)) {
			this.preferences.setProperty(PREF_IRS_COLOR, "0xFF8000");
			this.unsaved_changes = true;
		}
		
		if (this.unsaved_changes) {
			store_preferences();
		}

	}

	private void validate_preferences() {
		// TODO Auto-generated method stub
	}

	/**
	 * @param key
	 *            - the key of the preference
	 * @param value
	 *            - the new value of the preference
	 *
	 * @throws RuntimeException
	 *             - in case key is null or empty
	 */
	 
	public void set_preference(String key, String value) {
		if ((key == null) || (key.trim().equals("")))
			throw new RuntimeException("key must not be null or empty!");
		this.preferences.setProperty(key, value);
		logger.fine("set preference '" + key + "' = '" + value + "'");
		this.unsaved_changes = true;
		store_preferences();
		validate_preferences();
	}

	private void store_preferences() {
		if (this.unsaved_changes) {
			try {
				FileOutputStream fos = new FileOutputStream(PROPERTY_FILENAME);
				preferences.store(fos, null);
				if (fos != null)
					fos.close();
			} catch (IOException e2) {
				logger.warning("Could not store preferences file! (" + e2.toString() + ") ... ");
			}
			this.unsaved_changes = false;
		}
	}

	/**
	 * @param key
	 *            - the key of the preference to be returned
	 * @return - the value of the preference
	 *
	 * @throws RuntimeException
	 *             - in case no preference key is set
	 * @throws RuntimeException
	 *             - in case key is null or empty
	 */
	 
	public String get_preference(String key) {
		return this.preferences.getProperty(key);
	}

	static {
		instance = new ZHSIPreferences();
	}

	public static ZHSIPreferences getInstance() {
		return instance;
	}

}