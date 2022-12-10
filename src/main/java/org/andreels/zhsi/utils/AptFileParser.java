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
package org.andreels.zhsi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.navdata.CoordinateSystem;

public class AptFileParser {

	private ZHSIPreferences preferences;

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");

	int progress = 0;

	private String xplane_path;
	private String apt_file_location;

	FileInputStream original_apt_file = null;
	BufferedReader apt_file_reader = null;
	FileOutputStream new_apt_file = null;
	FileOutputStream new_rwy_file = null;
	OutputStreamWriter apt_file_writer = null;
	OutputStreamWriter rwy_file_writer = null;

	public AptFileParser(String apt_file) throws Exception {
		this.preferences = ZHSIPreferences.getInstance();
		this.xplane_path = preferences.get_preference(ZHSIPreferences.PREF_APTNAV_DIR);
		this.apt_file_location = apt_file;
	}

	public void parseFile() {
		if (new File(this.apt_file_location).exists()) {
			try {
				String line;
				String[] tokens;
				int info_type;
				long line_number = 0;
				String airport_icao_code = "";
				String airport_name = "";
				boolean hasRunway = false;
				double lat = 0f;
				double lon = 0f;
				double arpt_lat = 0f;
				double arpt_lon = 0f;
				float longest = 0f;
				int surface_longest = 99;
				int surface = 99;
				int elev = 0;
				float thr1_lat = 0f;
				float thr1_lon = 0f;
				float thr2_lat = 0f;
				float thr2_lon = 0f;
				float tower_lat = 0f;
				float tower_lon = 0f;
				boolean hasTower = false;
				float length = 0f;
				String rwy_num1 = "";
				String rwy_num2 = "";
				boolean current_airport_saved = true;

				original_apt_file = new FileInputStream(this.apt_file_location);
				apt_file_reader = new BufferedReader(new InputStreamReader(original_apt_file));
				new_apt_file = new FileOutputStream("./zhsi_nav_data/zhsi_apt.dat");
				new_apt_file = new FileOutputStream("./zhsi_nav_data/zhsi_apt.dat", true);
				new_rwy_file = new FileOutputStream("./zhsi_nav_data/zhsi_rwy.dat");
				new_rwy_file = new FileOutputStream("./zhsi_nav_data/zhsi_rwy.dat", true);
				apt_file_writer = new OutputStreamWriter(new_apt_file);
				rwy_file_writer = new OutputStreamWriter(new_rwy_file);

				apt_file_writer.write("icao;name;elevation;lat;lon;surface_longest;longest\n");
				rwy_file_writer.write(
						"icao;length;surface;rwy_num1;threshold lat1;threshold lon1;rwy_num2;threshold lat2;threshold lon2\n");

				while ((line = apt_file_reader.readLine()) != null) {
					if (line.length() > 0) {
						line_number++;
						line = line.trim();
						if (line_number > 2) {
							if (line.equals("99")) {
								// a line with a fake airport to force saving the last
								tokens = "1 9999 0 0 XXXX Fake Airport to force saving the last".split("\\s+", 6);
								info_type = Integer.parseInt(tokens[0]);
							} else {
								tokens = line.split("\\s+", 6);
								// handle data corruption ie #20
								if (isStringInt(tokens[0])) {
									info_type = Integer.parseInt(tokens[0]);
								} else {
									info_type = 0;
								}
							}

							if (info_type == 1 || info_type == 16 || info_type == 17) {

								if (!current_airport_saved) {

									if (hasTower) {
										if (arpt_lat == 0 || arpt_lon == 0) {
											arpt_lat = tower_lat;
											arpt_lon = tower_lon;
										}
									}

									if (hasRunway) {
										if (arpt_lat == 0 || arpt_lon == 0) {
											arpt_lat = lat;
											arpt_lon = lon;
										}
									}

									if (hasRunway) {
										apt_file_writer.append(airport_icao_code + ";" + airport_name + ";" + elev + ";"
												+ arpt_lat + ";" + arpt_lon + ";" + surface_longest + ";" + longest + "\n");
									}
								}

								airport_icao_code = tokens[4];
								airport_name = tokens[5];
								elev = Integer.parseInt(tokens[1]);
								arpt_lat = 0f;
								arpt_lon = 0f;
								longest = 0;
								surface_longest = 99;
								hasTower = false;
								hasRunway = false;
								current_airport_saved = false;

							} else if (info_type == 1302 && tokens[1].equals("datum_lat")) {

								if (tokens.length > 2) {

									arpt_lat = Double.parseDouble(tokens[2]);
								} else {
									arpt_lat = 0f;
								}

							} else if (info_type == 1302 && tokens[1].equals("datum_lon")) {

								if (tokens.length > 2) {
									arpt_lon = Double.parseDouble(tokens[2]);
								} else {
									arpt_lon = 0f;
								}

							} else if (info_type == 1302 && tokens[1].equals("icao_code")) {

								if (tokens.length > 2) {
									airport_icao_code = tokens[2];
								}

							} else if (info_type == 100) {

								hasRunway = true;

								tokens = line.split("\\s+", 26);
								surface = Integer.parseInt(tokens[2]);
								rwy_num1 = tokens[8];
								thr1_lat = Float.parseFloat(tokens[9]);
								thr1_lon = Float.parseFloat(tokens[10]);
								rwy_num2 = tokens[17];
								thr2_lat = Float.parseFloat(tokens[18]);
								thr2_lon = Float.parseFloat(tokens[19]);
								length = CoordinateSystem.rough_distance(thr1_lat, thr1_lon, thr2_lat, thr2_lon)
										* 1851.852f; // meters!
								lat = (thr1_lat + thr2_lat) / 2;
								lon = (thr1_lon + thr2_lon) / 2;
								if (length > longest) {
									longest = length;
									surface_longest = surface;
									}
								rwy_file_writer.append(airport_icao_code + ";" + length + ";" + surface + ";" + rwy_num1
										+ ";" + thr1_lat + ";" + thr1_lon + ";" + rwy_num2 + ";" + thr2_lat + ";"
										+ thr2_lon + "\n");

							} else if (info_type == 14) {

								hasTower = true;

								if (tokens[1] != null) {
									tower_lat = Float.parseFloat(tokens[1]);
								}

								if (tokens[2] != null) {
									tower_lon = Float.parseFloat(tokens[2]);
								}

							}
						}

					}

				}

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				try {
					original_apt_file.close();
					original_apt_file = null;
					apt_file_reader.close();
					apt_file_reader = null;
					apt_file_writer.close();
					apt_file_writer = null;
					rwy_file_writer.close();
					rwy_file_writer = null;
					new_apt_file.close();
					new_rwy_file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			logger.warning("ERROR: XPlane Directory is wrong, unable to create DB files !!");
		}

	}
	
	public boolean isStringInt(String s) {
		
		try {
			Integer.parseInt(s);
			return true;
		} 
		catch (NumberFormatException ex) {
			return false;
		}
	}
	
}