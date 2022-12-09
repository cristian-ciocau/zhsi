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
package org.andreels.zhsi;

public class ZHSIStatus {
    public static final String STATUS_STARTUP = "Starting Up...";
    public static final String STATUS_NOT_CONNECTED = "Not Connected";
    public static final String STATUS_CONNECTED = "Connected";
    public static final String STATUS_SHUTDOWN = "Shutdown";

    public static final String STATUS_NAV_DB_LOADED = "NAV Databases loaded";
    public static final String STATUS_NAV_DB_NOT_LOADED = "NAV Databases not loaded";
    public static final String STATUS_NAV_DB_NOT_FOUND = "NAV Databases not found";

    public static final String STATUS_EGPWS_DB_LOADED = "EGPWS Databases loaded";
    public static final String STATUS_EGPWS_DB_NOT_LOADED = "EGPWS Databases not loaded";
    public static final String STATUS_EGPWS_DB_NOT_FOUND = "EGPWS Databases not found";
    public static final String STATUS_EGPWS_PART_LOADED = "EGPWS Databases partially loaded";
    
    public static String status = STATUS_STARTUP;
    public static String nav_db_status = STATUS_NAV_DB_NOT_LOADED;
    public static String nav_db_cycle = "";
    
    public static String cStatus = "";
    public static String wStatus = "";
    public static String navStatus = "";
    public static String egpwsStatus = "";
    
    public static String egpws_db_status = STATUS_EGPWS_DB_NOT_LOADED;
    public static String weather_status = STATUS_STARTUP;
    
    public static boolean receiving = false;
    public static boolean zibo_loaded = false;
    public static boolean weather_receiving = false;
    
    public static boolean cptoutdb_running = false;
    public static boolean cptindb_running = false;
    public static boolean fooutdb_running = false;
    public static boolean foindb_running = false;
    public static boolean uppereicas_running = false;
    public static boolean lowereicas_running = false;
    
    public static boolean a10g_loaded = false;
    public static boolean b10g_loaded = false;
    public static boolean c10g_loaded = false;
    public static boolean d10g_loaded = false;
    public static boolean e10g_loaded = false;
    public static boolean f10g_loaded = false;
    public static boolean g10g_loaded = false;
    public static boolean h10g_loaded = false;
    public static boolean i10g_loaded = false;
    public static boolean j10g_loaded = false;
    public static boolean k10g_loaded = false;
    public static boolean l10g_loaded = false;
    public static boolean m10g_loaded = false;
    public static boolean n10g_loaded = false;
    public static boolean o10g_loaded = false;
    public static boolean p10g_loaded = false;
    public static String a10g_string = "GLOBE Tile A - Not found or not loaded.";
    public static String b10g_string = "GLOBE Tile B - Not found or not loaded.";
    public static String c10g_string = "GLOBE Tile C - Not found or not loaded.";
    public static String d10g_string = "GLOBE Tile D - Not found or not loaded.";
    public static String e10g_string = "GLOBE Tile E - Not found or not loaded.";
    public static String f10g_string = "GLOBE Tile F - Not found or not loaded.";
    public static String g10g_string = "GLOBE Tile G - Not found or not loaded.";
    public static String h10g_string = "GLOBE Tile H - Not found or not loaded.";
    public static String i10g_string = "GLOBE Tile I - Not found or not loaded.";
    public static String j10g_string = "GLOBE Tile J - Not found or not loaded.";
    public static String k10g_string = "GLOBE Tile K - Not found or not loaded.";
    public static String l10g_string = "GLOBE Tile L - Not found or not loaded.";
    public static String m10g_string = "GLOBE Tile M - Not found or not loaded.";
    public static String n10g_string = "GLOBE Tile N - Not found or not loaded.";
    public static String o10g_string = "GLOBE Tile O - Not found or not loaded.";
    public static String p10g_string = "GLOBE Tile P - Not found or not loaded.";

    
    public static boolean non_commercial_registered = false;
    public static String user_name = "";
    public static String user_lastname = "";
    public static String user_email = "";
    
    public static boolean nearest_airport_valid = false;
    public static int nearest_airport_elev = 0;
    public static int nearest_airport_bearing = 0;
    public static String nearest_airport = "";
    public static String nearest_airport_icao = "";
    public static float nearest_airport_dist = 0f;
    
    public static void set_elev_area_status(String area, boolean status) {

    	switch (area) {
    	case "/a10g": if (status) { a10g_loaded = true; a10g_string = "GLOBE Tile A - Loaded succesfully.";} else { a10g_loaded = false; }
    	break;
    	case "/b10g": if (status) { b10g_loaded = true; b10g_string = "GLOBE Tile B - Loaded succesfully.";} else { b10g_loaded = false; }
    	break;
    	case "/c10g": if (status) { c10g_loaded = true; c10g_string = "GLOBE Tile C - Loaded succesfully.";} else { c10g_loaded = false; }
    	break;
    	case "/d10g": if (status) { d10g_loaded = true; d10g_string = "GLOBE Tile D - Loaded succesfully.";} else { d10g_loaded = false; }
    	break;
    	case "/e10g": if (status) { e10g_loaded = true; e10g_string = "GLOBE Tile E - Loaded succesfully.";} else { e10g_loaded = false; }
    	break;
    	case "/f10g": if (status) { f10g_loaded = true; f10g_string = "GLOBE Tile F - Loaded succesfully.";} else { f10g_loaded = false; }
    	break;
    	case "/g10g": if (status) { g10g_loaded = true; g10g_string = "GLOBE Tile G - Loaded succesfully.";} else { g10g_loaded = false; }
    	break;
    	case "/h10g": if (status) { h10g_loaded = true; h10g_string = "GLOBE Tile H - Loaded succesfully.";} else { h10g_loaded = false; }
    	break;
    	case "/i10g": if (status) { i10g_loaded = true; i10g_string = "GLOBE Tile I - Loaded succesfully.";} else { i10g_loaded = false; }
    	break;
    	case "/j10g": if (status) { j10g_loaded = true; j10g_string = "GLOBE Tile J - Loaded succesfully.";} else { j10g_loaded = false; }
    	break;
    	case "/k10g": if (status) { k10g_loaded = true; k10g_string = "GLOBE Tile K - Loaded succesfully.";} else { k10g_loaded = false; }
    	break;
    	case "/l10g": if (status) { l10g_loaded = true; l10g_string = "GLOBE Tile L - Loaded succesfully.";} else { l10g_loaded = false; }
    	break;
    	case "/m10g": if (status) { m10g_loaded = true; m10g_string = "GLOBE Tile M - Loaded succesfully.";} else { m10g_loaded = false; }
    	break;
    	case "/n10g": if (status) { n10g_loaded = true; n10g_string = "GLOBE Tile N - Loaded succesfully.";} else { n10g_loaded = false; }
    	break;
    	case "/o10g": if (status) { o10g_loaded = true; o10g_string = "GLOBE Tile O - Loaded succesfully.";} else { o10g_loaded = false; }
    	break;
    	case "/p10g": if (status) { p10g_loaded = true; p10g_string = "GLOBE Tile P - Loaded succesfully.";} else { p10g_loaded = false; }
    	break;
    	}
    	if(a10g_loaded &&
    			b10g_loaded &&
    			c10g_loaded &&
    			d10g_loaded &&
    			e10g_loaded &&
    			f10g_loaded &&
    			g10g_loaded &&
    			h10g_loaded &&
    			i10g_loaded &&
    			j10g_loaded &&
    			k10g_loaded &&
    			l10g_loaded &&
    			m10g_loaded &&
    			n10g_loaded &&
    			o10g_loaded &&
    			p10g_loaded) {
    		egpws_db_status = ZHSIStatus.STATUS_EGPWS_DB_LOADED;
    	} 
    	else if(a10g_loaded ||
    			b10g_loaded ||
    			c10g_loaded ||
    			d10g_loaded ||
    			e10g_loaded ||
    			f10g_loaded ||
    			g10g_loaded ||
    			h10g_loaded ||
    			i10g_loaded ||
    			j10g_loaded ||
    			k10g_loaded ||
    			l10g_loaded ||
    			m10g_loaded ||
    			n10g_loaded ||
    			o10g_loaded ||
    			p10g_loaded) {

    		egpws_db_status = ZHSIStatus.STATUS_EGPWS_PART_LOADED;
    	}
    }   
}