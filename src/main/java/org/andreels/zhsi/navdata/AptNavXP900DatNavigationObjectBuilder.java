/**
 * AptNavXP900DatNavigationObjectBuilder.java
 *
 * Reads X-Planes earth nav data databases nav.dat, fix.dat and apt.dat and
 * stores extracted data in NavigationObjectRepository.
 *
 * Copyright (C) 2007  Georg Gruetter (gruetter@gmail.com)
 * Copyright (C) 2009  Marc Rogiers (marrog.123@gmail.com)
 * 
 * 2018 : Modified by Andre Els for ZHSI purposes !!
 *      https://www.facebook.com/sum1els737
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.ZHSIStatus;

public class AptNavXP900DatNavigationObjectBuilder {

	private String NAV_file = "/earth_nav.dat";
	private String NAV_xplane = "/Resources/default data" + NAV_file;
	private String NAV_local = "./zhsi_nav_data" + NAV_file;
	private String NAV_custom = "/Custom Data" + NAV_file;

	private String FIX_file = "/earth_fix.dat";
	private String FIX_xplane = "/Resources/default data" + FIX_file;
	private String FIX_local = "./zhsi_nav_data" + FIX_file;
	private String FIX_custom = "/Custom Data" + FIX_file;

	private String AWY_file = "/earth_awy.dat";
	private String AWY_xplane = "/Resources/default data" + AWY_file;
	private String AWY_local = "./zhsi_nav_data" + AWY_file;
	private String AWY_custom = "/Custom Data" + AWY_file;

	private String pathname_to_aptnav;
	private NavigationObjectRepository nor;
	private Fix fix;
	private ZHSIPreferences preferences;

	BufferedReader ini_reader = null;
	BufferedReader apt_file_reader = null;
	BufferedReader awy_file_reader = null;
	BufferedReader rwy_file_reader = null;
	BufferedReader fix_file_reader = null;
	BufferedReader nav_file_reader = null;
	BufferedReader zibo_apt_file_reader = null;
	FileInputStream scenery_packs_ini = null;
	FileInputStream custom_apt_file = null;
	FileInputStream aptnav_apt_file = null;
	FileInputStream awy_file = null;
	FileInputStream fix_file = null;
	FileInputStream nav_file = null;
	FileInputStream zibo_apt = null;
	FileInputStream zhsi_apt = null;
	FileInputStream zhsi_rwy = null;
	
	private String local_apt_dat = "./zhsi_nav_data/apt.dat";
	private String rwy_file = "./zhsi_nav_data/zhsi_rwy.dat";
	private String apt_file = "./zhsi_nav_data/zhsi_apt.dat";
	

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");

	public AptNavXP900DatNavigationObjectBuilder(String pathname_to_aptnav) throws Exception {
		this.pathname_to_aptnav = pathname_to_aptnav;
		this.nor = NavigationObjectRepository.get_instance();
		this.preferences = ZHSIPreferences.getInstance();
	}

	public void read_all_tables() throws FileNotFoundException, IOException {
		
		if (new File(NAV_local).exists() && new File(FIX_local).exists() && new File(AWY_local).exists() && new File(local_apt_dat).exists()) {
			
			read_rwy_file();
			read_apt_file();
			read_awy_table();
			read_fix_table();
			read_nav_table();
			if(!ZHSIStatus.nav_db_cycle.isEmpty()) {
				ZHSIStatus.navStatus = "AIRAC Cycle: " + ZHSIStatus.nav_db_cycle;
				ZHSIStatus.nav_db_status = ZHSIStatus.STATUS_NAV_DB_LOADED;
			}
			
			
		}else if (new File(this.pathname_to_aptnav).exists()) {
			try {
				//scenery_packs_ini = new FileInputStream(this.pathname_to_aptnav + "/Custom Scenery/scenery_packs.ini");
				//ini_reader = new BufferedReader(new InputStreamReader(scenery_packs_ini));
				//String line;
				//String[] tokens;
				//logger.info("Start reading AptNav resource files in " + preferences.get_preference(ZHSIPreferences.PREF_APTNAV_DIR));
				ZHSIStatus.navStatus = "Loading NAV DB ....";
//								while ((line = ini_reader.readLine()) != null) {
//									tokens = line.split("\\s+", 2);
//									if ((tokens.length == 2) && tokens[0].equals("SCENERY_PACK")) {
//										File custom_apt_f = new File(
//												this.pathname_to_aptnav + "/" + tokens[1] + "/Earth nav data/apt.dat");
//										if (custom_apt_f.exists()) {
//											custom_apt_file = new FileInputStream(
//													this.pathname_to_aptnav + "/" + tokens[1] + "/Earth nav data/apt.dat");
//											logger.config("Loading Custom Scenery APT " + custom_apt_f.getPath());
//											read_an_apt_file(custom_apt_file);
//										}
//									}
//								}
//								if (new File(this.pathname_to_aptnav + this.APT_file).exists()) {
//									aptnav_apt_file = new FileInputStream(this.pathname_to_aptnav + this.APT_file);
//									logger.info("Reading APT database ( " + this.pathname_to_aptnav + this.APT_file + " ) Reading from a single directory is DEPRECATED !");
//									read_an_apt_file(aptnav_apt_file);
//								}
				//scan_apt_files("Resources/default scenery");
				//read_zibo_apt_file();
				read_rwy_file();
				read_apt_file();
				read_awy_table();
				read_fix_table();
				read_nav_table();
				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//scenery_packs_ini.close();
				//scenery_packs_ini = null;
				//ini_reader.close();
				//ini_reader = null;
				if(!ZHSIStatus.nav_db_cycle.isEmpty()) {
					ZHSIStatus.navStatus = "AIRAC Cycle: " + ZHSIStatus.nav_db_cycle;
					ZHSIStatus.nav_db_status = ZHSIStatus.STATUS_NAV_DB_LOADED;
				}
			}
		} else {
			logger.warning("XPlane directory is wrong or local files not present");
			ZHSIStatus.navStatus = "Wrong X-Plane Folder";
			ZHSIStatus.nav_db_status = ZHSIStatus.STATUS_NAV_DB_NOT_LOADED;
		}
	}

	private void read_rwy_file() throws FileNotFoundException {
		
		if (new File(rwy_file).exists()) {
			logger.config("Reading RWY database from ( " + rwy_file + " )");
			zhsi_rwy = new FileInputStream(rwy_file);
			rwy_file_reader = new BufferedReader(new InputStreamReader(zhsi_rwy));
			
			try {
				//String line;
				String tokens[];
				String airport_icao_code;
				float length;
				int surface;
				String rwy_num1;
				float thr1_lat;
				float thr1_lon;
				String rwy_num2;
				float thr2_lat;
				float thr2_lon;
				float width = 0f;
				ArrayList<Runway> runways = new ArrayList<Runway>();
				
				rwy_file_reader.readLine();
				String line1=null;
				while ((line1 = rwy_file_reader.readLine()) != null) {
					tokens = line1.split(";");
					airport_icao_code = tokens[0];
					length = Float.parseFloat(tokens[1]);
					surface = Integer.parseInt(tokens[2]);
					rwy_num1 = tokens[3];
					thr1_lat =  Float.parseFloat(tokens[4]);
					thr1_lon =  Float.parseFloat(tokens[5]);
					rwy_num2 = tokens[6];
					thr2_lat =  Float.parseFloat(tokens[7]);
					thr2_lon =  Float.parseFloat(tokens[8]);
					runways = new ArrayList<Runway>();
					if((surface == 1 || surface == 2)) {
						Runway new_rwy = new Runway(airport_icao_code, length, width, surface, rwy_num1, thr1_lat, thr1_lon, rwy_num2, thr2_lat, thr2_lon);
						nor.add_nav_object(new_rwy);
						runways.add(new_rwy);
					}
				}

			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					zhsi_rwy.close();
					zhsi_rwy = null;
					rwy_file_reader.close();
					rwy_file_reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			logger.warning("No " + rwy_file + " file found !!");
		}
	
	}

	private void read_apt_file()  throws FileNotFoundException {
		
		if (new File(apt_file).exists()) {
			logger.config("Reading APT database from ( " + apt_file + " )");
			zhsi_apt = new FileInputStream(apt_file);
			apt_file_reader = new BufferedReader(new InputStreamReader(zhsi_apt));
			
			try {

				String tokens[];
				String airport_icao_code = "";
				String airport_name = "";
				float lat= 0f;
				float lon = 0f;
				float longest = 0f;
				int surface = 99;
				int elevation = 0;
				

				apt_file_reader.readLine();
				String line1=null;
				while ((line1 = apt_file_reader.readLine()) != null) {
					tokens = line1.split(";");
					airport_icao_code = tokens[0];
					airport_name = tokens[1];
					elevation = Integer.parseInt(tokens[2]);
					lat =  Float.parseFloat(tokens[3]);
					lon =  Float.parseFloat(tokens[4]);
					surface = Integer.parseInt(tokens[5]);
					longest = Float.parseFloat(tokens[6]);
					if ((surface == 1 || surface == 2) && longest > 1200) {
						nor.add_nav_object(new Airport(airport_icao_code, airport_name, lat, lon, elevation, surface, longest));
					}
					
				}

			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					zhsi_apt.close();
					zhsi_apt = null;
					apt_file_reader.close();
					apt_file_reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			logger.warning("No " + apt_file + " file found !!");
		}
	}

	@SuppressWarnings("unused")
	private void read_zibo_apt_file() throws FileNotFoundException {
		String zibo_apt_file = "C:\\Users\\Andre\\Desktop\\X-Plane 11\\Aircraft\\Extra Aircraft\\B737-800X\\B738X_apt.dat";
		if (new File(zibo_apt_file).exists()) {
			logger.config("Reading APT database from ( " + zibo_apt_file + " )");
			zibo_apt = new FileInputStream(zibo_apt_file);
		}
		zibo_apt_file_reader = new BufferedReader(new InputStreamReader(zibo_apt));

		try {
			String line;
			String tokens[];
			String airport_icao_code = "";
			float arp_lat = 0f;
			float arp_lon = 0f;
			int transition_alt = 0;
			int transition_lvl = 0;
			int elevation = 0;

			zibo_apt_file_reader.readLine();
			String line1=null;
			while ((line1 = zibo_apt_file_reader.readLine()) != null) {
				tokens = line1.split("\\s+");
				airport_icao_code = tokens[0];
				arp_lat = Float.parseFloat(tokens[1]);
				arp_lon = Float.parseFloat(tokens[2]);
				//transition_alt = Integer.parseInt(tokens[3]);
				//transition_lvl = Integer.parseInt(tokens[4]);
				elevation = Integer.parseInt(tokens[5]);
				
				nor.add_nav_object(new Airport(airport_icao_code, arp_lat, arp_lon, elevation));
			}

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				zibo_apt.close();
				zibo_apt = null;
				zibo_apt_file_reader.close();
				zibo_apt_file_reader = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void read_an_apt_file(FileInputStream apt_file) {
		try {
			apt_file_reader = new BufferedReader(new InputStreamReader(apt_file));
			String line;
			long line_number = 0;
			int info_type;
			String[] tokens;
			String airport_icao_code = "";
			String airport_name = "";
			boolean current_airport_saved = true; // this is a trick to say that there is no previous airport when
			// starting
			// to read the first
			ArrayList<Runway> runways = new ArrayList<Runway>();
			float width;
			int surface;
			String rwy_num1;
			float thr1_lat;
			float thr1_lon;
			String rwy_num2;
			float thr2_lat;
			float thr2_lon;
			float length;
			float lat = 0;
			float lon = 0;
			float tower_lat = 0;
			float tower_lon = 0;
			float arp_lat = 0;
			float arp_lon = 0;
			float longest = 0;
			float rwy_count = 0;
			float lat_sum = 0;
			float lon_sum = 0;
			float hard_rwy_count = 0;
			float hard_lat_sum = 0;
			float hard_lon_sum = 0;
			boolean tower = false;
			int elev = 0;
			ArrayList<ComRadio> comms = new ArrayList<ComRadio>();

			while ((line = apt_file_reader.readLine()) != null) {
				if (line.length() > 0) {

					line_number++;

					line = line.trim();
					if ((line_number > 2) /* && ( ! line.equals("99") ) */ ) {
						try {
							if (line.equals("99")) {
								// a line with a fake airport to force saving the last
								tokens = "1 9999 0 0 XXXX Fake Airport to force saving the last".split("\\s+", 6);
								info_type = Integer.parseInt(tokens[0]);
							} else {
								tokens = line.split("\\s+", 6);
								info_type = Integer.parseInt(tokens[0]);
							}
							if (info_type == 1) {
								// hold it, save the previous airport before proceeding with this one...
								if (!current_airport_saved) {
									// when this is the first airport that whe read, there is no previous airport
									// that is why current_airport_saved is initialized to true
									// position of the ARP (Aerodrome Reference Point)
									if (tower) {
										// ARP = tower position
										arp_lat = tower_lat;
										arp_lon = tower_lon;
									} else if (hard_rwy_count == 1) {
										// ARP = the center of the one and only hard runway
										arp_lat = hard_lat_sum;
										arp_lon = hard_lon_sum;
									} else if (rwy_count == 1) {
										// ARP = the center of the one and only non-hard runway
										arp_lat = lat_sum;
										arp_lon = lon_sum;
									} else if (hard_rwy_count > 1) {
										// no tower, but several hard runways
										// ARP = center of all hard runways
										arp_lat = hard_lat_sum / hard_rwy_count;
										arp_lon = hard_lon_sum / hard_rwy_count;
									} else {
										// no hard runways and no tower
										// ARP = center of all non-hard runways
										arp_lat = lat_sum / rwy_count;
										arp_lon = lon_sum / rwy_count;
									}
									nor.add_nav_object(new Airport(airport_name, airport_icao_code, arp_lat, arp_lon, runways, longest, elev, comms));
								}
								// process the new airport header
								// elev = Integer.parseInt(line.substring(5, 10).trim());
								elev = Integer.parseInt(tokens[1]);
								// airport_icao_code = line.substring(15, 19);
								airport_icao_code = tokens[4];
								// airport_name = line.substring(20);
								airport_name = tokens[5];
								current_airport_saved = false;
								runways = new ArrayList<Runway>();
								arp_lat = 0;
								arp_lon = 0;
								longest = 0;
								rwy_count = 0;
								lat_sum = 0;
								lon_sum = 0;
								hard_rwy_count = 0;
								hard_lat_sum = 0;
								hard_lon_sum = 0;
								tower = false;
								comms = new ArrayList<ComRadio>();

								// we dont't save this airport right away, we collect information about the
								// runways first
							} else if (info_type == 100) {
								// a runway
								// we need more tokens

								tokens = line.split("\\s+", 26);
								width = Float.parseFloat(tokens[1]);
								surface = Integer.parseInt(tokens[2]);
								rwy_num1 = tokens[8];
								thr1_lat = Float.parseFloat(tokens[9]);
								thr1_lon = Float.parseFloat(tokens[10]);
								rwy_num2 = tokens[17];
								thr2_lat = Float.parseFloat(tokens[18]);
								thr2_lon = Float.parseFloat(tokens[19]);
								length = CoordinateSystem.rough_distance(thr1_lat, thr1_lon, thr2_lat, thr2_lon) * 1851.852f; // meters!
								lat = (thr1_lat + thr2_lat) / 2;
								lon = (thr1_lon + thr2_lon) / 2;
								Runway new_rwy = new Runway(airport_icao_code, length, width, surface, rwy_num1, thr1_lat, thr1_lon, rwy_num2, thr2_lat, thr2_lon);
								nor.add_nav_object(new_rwy);
								// runways.add( nor.get_runway(airport_icao_code, lat, lon) );
								runways.add(new_rwy);
								// find the longest runway for this airport
								if (length > longest)
									longest = length;
								// calculate an average of all threshold lats and lons to define the ARP
								rwy_count += 1;
								lat_sum += lat;
								lon_sum += lon;
								if ((surface == Runway.RWY_ASPHALT) || (surface == Runway.RWY_CONCRETE)) {
									hard_rwy_count += 1;
									hard_lat_sum += lat;
									hard_lon_sum += lon;
								}
							} else if (info_type == 14) {
								// if defined in the file, the tower position can be used as the ARP
								tower = true;
								tower_lat = Float.parseFloat(tokens[1]);
								tower_lon = Float.parseFloat(tokens[2]);
							} else if ((info_type >= 50) && (info_type < 60)) {
								// COM Radio
								// we need the name, which can include spaces, in the third token
								tokens = line.split("\\s+", 3);
								comms.add(new ComRadio(airport_icao_code, tokens.length == 3 ? tokens[2] : "",
										(float) Float.parseFloat(tokens[1]) / 100.0f));
							}
						} catch (Exception e) {
							logger.warning("\nParse error in " + line_number + "(" + e + ") " + line);
						}
					}

				} // line !isEmpty
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				apt_file.close();
				apt_file = null;
				apt_file_reader.close();
				apt_file_reader = null;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@SuppressWarnings("unused")
	private void scan_apt_files(String basedir) throws Exception {
		File scenery_dir = new File(this.pathname_to_aptnav + "/" + basedir);
		// get the list of packs in scenery_dir
		String[] scenery_packs = scenery_dir.list();
		if ((scenery_packs != null) && (scenery_packs.length > 0)) {
			// sort alphabetically
			Arrays.sort(scenery_packs);
			for (int i = 0; i != scenery_packs.length; i++) {
				// check if we have a scenery pack directory
				if (new File(this.pathname_to_aptnav + "/" + basedir + "/" + scenery_packs[i]).isDirectory()) {
					if (new File(this.pathname_to_aptnav + "/" + basedir + "/" + scenery_packs[i]
							+ "/Earth nav data/apt.dat").exists()) {
						// File apt_file = new File(this.pathname_to_aptnav + "/" + basedir + "/" +
						// scenery_packs[i] + "/Earth nav data/apt.dat");
						// check if we have an apt.dat file in this pack
						// if (apt_file.exists()) {
						FileInputStream apt_file_stream = new FileInputStream(this.pathname_to_aptnav + "/" + basedir
								+ "/" + scenery_packs[i] + "/Earth nav data/apt.dat");
						logger.config("Loading " + basedir + " APT " + this.pathname_to_aptnav + "/" + basedir + "/"
								+ scenery_packs[i] + "/Earth nav data/apt.dat");
						read_an_apt_file(apt_file_stream);
					}
				}
			}

		}
	}

	public void read_fix_table() throws FileNotFoundException {
		
		if (new File(this.FIX_local).exists()) {
			logger.config("Reading FIX database ( " + this.FIX_local + " ) - not using X-Plane path!");
			fix_file = new FileInputStream(this.FIX_local);
			processFixFile();

		} else if (new File(this.pathname_to_aptnav + this.FIX_custom).exists()) {
			logger.config("Reading FIX database ( " + this.pathname_to_aptnav + this.FIX_custom + " )");
			fix_file = new FileInputStream(this.pathname_to_aptnav + this.FIX_custom);
			processFixFile();

		} else if (new File(this.pathname_to_aptnav + this.FIX_xplane).exists()) {
			logger.config("Reading FIX database ( " + this.pathname_to_aptnav + this.FIX_xplane + " )");
			fix_file = new FileInputStream(this.pathname_to_aptnav + this.FIX_xplane);
			processFixFile();

		} else if (new File(this.pathname_to_aptnav + this.FIX_file).exists()) {
			logger.info("Reading FIX database ( " + this.pathname_to_aptnav + this.FIX_file
					+ " ) Reading from a single directory is DEPRECATED !");
			fix_file = new FileInputStream(this.pathname_to_aptnav + this.FIX_file);
			processFixFile();
		}else {
			logger.warning("No "+ FIX_file +" file found !!");
		}

	}

	private void processFixFile() {
		fix_file_reader = new BufferedReader(new InputStreamReader(fix_file));

		try {

			String line;
			String[] tokens;
			long line_number = 0;
			boolean version11 = false;

			while ((line = fix_file_reader.readLine()) != null) {
				if (line.length() > 0) {
					line_number++;
					line = line.trim();
					if ((line_number == 2) && (line.length() >= 32)) {

						tokens = line.split("\\s+", 2);
						logger.info("FIX file format : " + tokens[0]);
						version11 = tokens[0].equals("1100") || tokens[0].equals("1101");

					} else if ((line_number > 2) && (!line.equals("99"))) {
						try {
							tokens = line.split("\\s+", 5);
							nor.add_nav_object(new Fix(tokens[2], Float.parseFloat(tokens[0]),
									Float.parseFloat(tokens[1]), version11 && tokens[3].equals("ENRT")));
						} catch (Exception e) {
							logger.warning("Parse error in " + fix_file.toString() + ":" + line_number + " '" + line
									+ "' (" + e + ")");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fix_file.close();
				fix_file = null;
				fix_file_reader.close();
				fix_file_reader = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void read_awy_table() throws FileNotFoundException {

		
		//check if local nav data exists
		if (new File(this.AWY_local).exists()) {
			logger.config("Reading AWY database ( " + this.AWY_local + " ) - not using X-Plane path!");
			awy_file = new FileInputStream(this.AWY_local);
			processAwyFile();
		}
		// checks custom first, stuff like updated AIRAC etc ..
		else if (new File(this.pathname_to_aptnav + this.AWY_custom).exists()) {
			logger.config("Reading AWY database ( " + this.pathname_to_aptnav + this.AWY_custom + " )");
			awy_file = new FileInputStream(this.pathname_to_aptnav + this.AWY_custom);
			processAwyFile();

			// if not, checks default data, think default comes with AIRAC 1701 ... OLD !!!
		} else if (new File(this.pathname_to_aptnav + this.AWY_xplane).exists()) {
			logger.config("Reading AWY database ( " + this.pathname_to_aptnav + this.AWY_xplane + " )");
			awy_file = new FileInputStream(this.pathname_to_aptnav + this.AWY_xplane);
			processAwyFile();

			// or check if file exist in root xplane dir - very unlikely
		} else if (new File(this.pathname_to_aptnav + this.AWY_file).exists()) {
			logger.info("Reading AWY database ( " + this.pathname_to_aptnav + this.AWY_file
					+ " ) Reading from a single directory is DEPRECATED !");
			awy_file = new FileInputStream(this.pathname_to_aptnav + this.AWY_file);
			processAwyFile();
		} else {
			logger.warning("No "+ AWY_file +" file found !!");
		}


	}

	private void processAwyFile() {
		
		awy_file_reader = new BufferedReader(new InputStreamReader(awy_file));

		try {
			String line;
			String[] tokens;
			long line_number = 0;
			boolean version11 = false;
			while ((line = awy_file_reader.readLine()) != null) {

				if (line.length() > 0) {

					line_number++;

					line = line.trim();
					if ((line_number == 2) && (line.length() >= 32)) {

						tokens = line.split("\\s+", 2);
						logger.info("AWY file format : " + tokens[0]);
						version11 = tokens[0].equals("1100");

					} else if ((!version11) && (line_number > 2) && (!line.equals("99"))) {
						// if the AWY file is version 1100, then the FIX file will be version 1100 too,
						// and we will already know which fixes are terminal or enroute
						try {
							tokens = line.split("\\s+", 10);
							// tokens[] 0=WPT1, 1=lat1, 2=lon1, 3=WPT2, 4=lat2, 5=lon2, 6=low(1)/high(2),
							// 7=bottom, 8=top, 9=ID(s)
							fix = nor.get_fix(tokens[0], Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
							if (fix != null)
								fix.on_awy = true;
						} catch (Exception e) {
							logger.warning("Parse error in " + awy_file.toString() + ":" + line_number + " '" + line
									+ "' (" + e + ")");
						}
					}
				} // line !isEmpty
			} // while readLine
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				awy_file.close();
				awy_file = null;
				awy_file_reader.close();
				awy_file_reader = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void read_nav_table() throws FileNotFoundException {

		if (new File(this.NAV_local).exists()) {
			logger.config("Reading NAV database ( " + this.NAV_local + " ) - not using X-Plane path!");
			nav_file = new FileInputStream(this.NAV_local);
			processNavFile();

		}
		else if (new File(this.pathname_to_aptnav + this.NAV_custom).exists()) {
			logger.config("Reading NAV database ( " + this.pathname_to_aptnav + this.NAV_custom + " )");
			nav_file = new FileInputStream(this.pathname_to_aptnav + this.NAV_custom);
			processNavFile();

		} else if (new File(this.pathname_to_aptnav + this.NAV_xplane).exists()) {
			logger.config("Reading NAV database ( " + this.pathname_to_aptnav + this.NAV_xplane + " )");
			nav_file = new FileInputStream(this.pathname_to_aptnav + this.NAV_xplane);
			processNavFile();

		} else if (new File(this.pathname_to_aptnav + this.NAV_file).exists()) {
			logger.info("Reading NAV database ( " + this.pathname_to_aptnav + this.NAV_file
					+ " ) Reading from a single directory is DEPRECATED !");
			nav_file = new FileInputStream(this.pathname_to_aptnav + this.NAV_file);
			processNavFile();
		}else {
			logger.warning("No "+ NAV_file +" file found !!");
		}

	}

	private void processNavFile() {
		
		nav_file_reader = new BufferedReader(new InputStreamReader(nav_file));

		try {
			String line;
			int info_type;
			String[] tokens;
			long line_number = 0;
			boolean version11 = false;
			// int rowcode_field = 0;
			int lat_field = 1;
			int lon_field = 2;
			int elev_field = 3;
			int freq_field = 4;
			int range_field = 5;
			int bearing_field = 6;
			int ident_field = 7;
			int arpt_field = 8;
			// int region_field;
			int rwy_field;
			int name_field;

			RadioNavigationObject coupled_rno;
			RadioNavigationObject stand_alone_dme;
			Localizer coupled_loc;
			RadioNavigationObject twin_rno;
			Localizer twin_loc;
			boolean has_a_twin;
			String twin_ilt;
			Localizer new_loc;

			while ((line = nav_file_reader.readLine()) != null) {

				if (line.length() > 0) {
					// line.isEmpty() doesn't work on java 1.5

					line_number++;

					line = line.trim();

					if ((line_number == 2) && (line.length() >= 32)) {

						// the file format version and cycle number info is on line 2
						tokens = line.split("\\s+", 8);
						logger.info("NAV file format : " + tokens[0]);
						version11 = tokens[0].equals("1100") || tokens[0].equals("1150");
						if (version11)
							logger.info("X-Plane 11");
						if ((tokens[5].length() > 2) && (tokens[5].charAt(tokens[5].length() - 1) == ',')) {
							// usually, the cycle number is followed by a comma
							ZHSIStatus.nav_db_cycle = tokens[5].substring(0, tokens[5].length() - 1);
						} else {
							ZHSIStatus.nav_db_cycle = tokens[5];
						}

					} else if ((line_number > 2) && (!line.equals("99"))) {
						try {

							// a generic split that works for types 2, 3 and 13
							tokens = line.split("\\s+", version11 ? 11 : 9);
							info_type = Integer.parseInt(tokens[0]);

							if ((info_type == 2) || (info_type == 3) || (info_type == 13)) {

								if (version11) {
									name_field = 10;
								} else {
									name_field = 8;
								}
								// 2=NDB, 3=VOR (VOR, VOR-DME, VORTAC) 13=DME (Stand-alone DME, TACAN)
								nor.add_nav_object(new RadioNavBeacon(tokens[name_field], // name
										tokens[ident_field], // ident
										info_type, Float.parseFloat(tokens[lat_field]), // lat
										Float.parseFloat(tokens[lon_field]), // lon
										Integer.parseInt(tokens[elev_field]), // elev MSL
										Float.parseFloat(tokens[freq_field]), // freq
										Integer.parseInt(tokens[range_field]), // range
										Float.parseFloat(tokens[bearing_field]) // NDB: zero , VOR: offset
										));
								if(info_type == 13) {
									stand_alone_dme = nor.find_tuned_nav_object(Float.parseFloat(tokens[lat_field]),
											Float.parseFloat(tokens[lon_field]),
											Float.parseFloat(tokens[freq_field]) / 100.0f, tokens[ident_field]);
									if (stand_alone_dme != null) {
										stand_alone_dme.has_dme = true;
										stand_alone_dme.dme_lat = Float.parseFloat(tokens[lat_field]);
										stand_alone_dme.dme_lon = Float.parseFloat(tokens[lon_field]);
									}
								}

							} else if ((info_type == 4) || (info_type == 5)) {

								// ILS or LOC
								tokens = line.split("\\s+", version11 ? 12 : 11);
								if (version11) {
									rwy_field = 10;
									name_field = 11;
								} else {
									rwy_field = 9;
									name_field = 10;
								}

								// search for a twin, i.e. an ILS with the same frequency at the same airport
								twin_rno = nor.find_tuned_nav_object(Float.parseFloat(tokens[lat_field]),
										Float.parseFloat(tokens[lon_field]),
										Float.parseFloat(tokens[freq_field]) / 100.0f, "");
								has_a_twin = ((twin_rno != null) && (twin_rno instanceof Localizer));
								twin_ilt = "";
								if (has_a_twin) {
									twin_loc = (Localizer) twin_rno;
									twin_loc.has_twin = true;
									twin_loc.twin_ilt = tokens[ident_field];
									twin_ilt = twin_loc.ilt;
								}
								new_loc = new Localizer(tokens[arpt_field] + " " + tokens[rwy_field], // arpt ICAO + RWY
										tokens[ident_field], // ident
										info_type, Float.parseFloat(tokens[lat_field]), // lat
										Float.parseFloat(tokens[lon_field]), // lon
										Integer.parseInt(tokens[elev_field]), // elev MSL
										Float.parseFloat(tokens[freq_field]), // freq
										Integer.parseInt(tokens[range_field]), // range
										Float.parseFloat(tokens[bearing_field]), // bearing, true degrees
										tokens[arpt_field], // ICAO
										tokens[rwy_field], // RWY,
										tokens[name_field], has_a_twin, twin_ilt);
								nor.add_nav_object(new_loc);
								// add this localizer to the runway
								
								if (nor.get_airport(tokens[arpt_field]) == null) {
									logger.fine("Error NAV.dat: no AIRPORT found for ILS/LOC " + tokens[arpt_field] + " " + tokens[rwy_field] + " " + tokens[ident_field]);
								} else {
									Runway rwy = nor.get_runway(tokens[arpt_field], tokens[rwy_field], Float.parseFloat(tokens[lat_field]), Float.parseFloat(tokens[lon_field]), true);
									if (rwy != null) {
										rwy.localizers.add(new_loc);
									} else {
										rwy = nor.get_runway(tokens[arpt_field], tokens[rwy_field].substring(1), Float.parseFloat(tokens[lat_field]), Float.parseFloat(tokens[lon_field]), true);
										if (rwy != null) {
											rwy.localizers.add(new_loc);
										} else {
											logger.warning("Error NAV.dat: no RUNWAY found for ILS/LOC " + tokens[arpt_field] + " " + tokens[rwy_field] + " " + tokens[ident_field]);
										}
									}
								}

							} else if (info_type == 6) {

								// update the ILS (or IGS) with this GS
								// (we can do this in the same loop, since the file is sorted by info_type; the
								// ILS will already be stored)
								tokens = line.split("\\s+", version11 ? 12 : 11);
								if (version11) {
									rwy_field = 10;
									name_field = 11;
								} else {
									rwy_field = 9;
									name_field = 10;
								}

								// tokens[] 0=type, 1=lat, 2=lon, 3=elev, 4=freq, 5=range,
								// 6=glide_angle*100000+course, 7=ident, 8=arpt, 9=rwy, 10="GS"
								coupled_rno = nor.find_tuned_nav_object(Float.parseFloat(tokens[lat_field]),
										Float.parseFloat(tokens[lon_field]),
										Float.parseFloat(tokens[freq_field]) / 100.0f, tokens[ident_field]);
								if ((coupled_rno != null) && (coupled_rno instanceof Localizer)) {
									coupled_loc = (Localizer) coupled_rno;
									coupled_loc.has_gs = true;
									// when an ILS has a GS, we are more interested in the elev of the GS than the
									// LOC
									coupled_loc.elevation = Integer.parseInt(tokens[elev_field]);
								} else {
									logger.warning("Error NAV.dat: no ILS for GS " + tokens[ident_field] + " "
											+ tokens[freq_field]);
								}

							} else if (info_type == 12) {

								// update the VOR, LOC, ILS or IGS with this DME
								// (we can do this in the same loop, since the file is sorted by info_type)
								tokens = line.split("\\s+", 9);
								// tokens[] 0=type, 1=lat, 2=lon, 3=elev, 4=freq, 5=range, 6=bias, 7=ident,
								// 8=name
								coupled_rno = nor.find_tuned_nav_object(Float.parseFloat(tokens[lat_field]),
										Float.parseFloat(tokens[lon_field]),
										Float.parseFloat(tokens[freq_field]) / 100.0f, tokens[ident_field]);
								if (coupled_rno != null) {
									coupled_rno.has_dme = true;
									coupled_rno.dme_lat = Float.parseFloat(tokens[lat_field]);
									coupled_rno.dme_lon = Float.parseFloat(tokens[lon_field]);
								} else {
									logger.warning("Error NAV.dat: no VOR or LOC for DME " + tokens[ident_field] + " "
											+ tokens[freq_field]);
								}
							}

						} catch (Exception e) {
							logger.warning("Parse error in " + nav_file.toString() + ":" + line_number + " '" + line
									+ "' (" + e + ")");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				nav_file.close();
				nav_file = null;
				nav_file_reader.close();
				nav_file_reader = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void preference_changed(String key) {

		logger.config("Preference " + key + " changed");
		if (key.equals(ZHSIPreferences.PREF_APTNAV_DIR)) {
			// reload navigation databases
			this.pathname_to_aptnav = ZHSIPreferences.getInstance().get_preference(ZHSIPreferences.PREF_APTNAV_DIR);
			if (ZHSIStatus.nav_db_status.equals(ZHSIStatus.STATUS_NAV_DB_NOT_FOUND) == false) {
				try {
					logger.config("Reload navigation tables");
					read_all_tables();
				} catch (Exception e) {
					logger.warning("Could not read navigation tables! (" + e.toString() + ")");
				}
			} else {
				logger.warning("Could not find AptNav Resources! (Status:" + ZHSIStatus.nav_db_status + ")");
			}
		}

	}
}