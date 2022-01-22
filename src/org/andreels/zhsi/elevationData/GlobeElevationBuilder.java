/**
* GlobeElevationBuilder.java
*
* Reads Globe elevation database and maps each file in memory
*
* Copyright (C) 2017 Nicolas Carel
* 
* Globe Manual:
* https://ngdc.noaa.gov/mgg/topo/report/globedocumentationmanual.pdf
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

package org.andreels.zhsi.elevationData;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Logger;

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.ZHSIStatus;

public class GlobeElevationBuilder {
	
    private String globe_file[] = {
    		"/a10g", "/b10g", "/c10g", "/d10g",
    		"/e10g", "/f10g", "/g10g", "/h10g", 
    		"/i10g", "/j10g", "/k10g", "/l10g",
    		"/m10g", "/n10g", "/o10g", "/p10g"};
    
    private int globe_size[] = {
    		103680000, 103680000, 103680000, 103680000, 
    		129600000, 129600000, 129600000, 129600000, 
    		129600000, 129600000, 129600000, 129600000,
    		103680000, 103680000, 103680000, 103680000 };
    
    private int globe_rows[] = {
    		4800, 4800, 4800, 4800, 
    		6000, 6000, 6000, 6000, 
    		6000, 6000, 6000, 6000,
    		4800, 4800, 4800, 4800 };
    
    private float min_lat[] = {
    		50.0f, 50.0f, 50.0f, 50.0f, 
    		0.0f, 0.0f, 0.0f, 0.0f, 
    		-50.0f, -50.0f, -50.0f, -50.0f,
    		-90.0f, -90.0f, -90.0f, -90.0f };

    private float max_lat[] = {
    		90.0f, 90.0f, 90.0f, 90.0f, 
    		50.0f, 50.0f, 50.0f, 50.0f,
    		0.0f, 0.0f, 0.0f, 0.0f,
    		-50.0f, -50.0f, -50.0f, -50.0f };

    private float min_lon[] = {
    		-180.0f, -90.0f, 0.0f, 90.0f,
    		-180.0f, -90.0f, 0.0f, 90.0f,
    		-180.0f, -90.0f, 0.0f, 90.0f,
    		-180.0f, -90.0f, 0.0f, 90.0f };

    private float max_lon[] = {
    		-90.0f, 0.0f, 90.0f, 180.0f,
    		-90.0f, 0.0f, 90.0f, 180.0f,
    		-90.0f, 0.0f, 90.0f, 180.0f,
    		-90.0f, 0.0f, 90.0f, 180.0f };
    
    private int globe_columns = 10800;
    
    private String pathname_to_globe_db;
    
    private ElevationRepository elevation_repository;
    
    private static Logger logger = Logger.getLogger("org.andreels.zhsi");
    

    public GlobeElevationBuilder(String pathname_to_globe_db) throws Exception {
        this.pathname_to_globe_db = pathname_to_globe_db;
        this.elevation_repository = ElevationRepository.get_instance();
    }
    
    public void preference_changed(String key) {

        logger.config("Preference "+key+" changed");
        if (key.equals(ZHSIPreferences.PREF_EGPWS_DB_DIR)) {
            // reload navigation databases
            this.pathname_to_globe_db = ZHSIPreferences.getInstance().get_preference(ZHSIPreferences.PREF_EGPWS_DB_DIR);
            if (ZHSIStatus.egpws_db_status.equals(ZHSIStatus.STATUS_EGPWS_DB_NOT_FOUND) == false) {
            	logger.config("Reload GLOBE database");
            	map_database();
            } else {
                logger.warning("Could not find GLOBE Resources! (Status:" + ZHSIStatus.egpws_db_status + ")");
            }
        }

    }
    
    public void map_database() {
    	
        if (new File(this.pathname_to_globe_db).exists()) {
            logger.info("Start mapping GLOBE database files in " + ZHSIPreferences.PREF_EGPWS_DB_DIR);

            for (int i=0; i<16; i++) {
            	
                logger.info("Mapping GLOBE database files " + pathname_to_globe_db + globe_file[i]);
                try {
                	File file = new File(pathname_to_globe_db + globe_file[i]);
                	logger.fine("Getting file channel " + globe_file[i]);
                	@SuppressWarnings("resource")
					FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
                	logger.fine("Mapping byteBuffer " + globe_file[i]);
                	MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,0,fileChannel.size());
                	logger.fine("Creating area " + globe_file[i]);
                    ElevationArea area = new ElevationArea(byteBuffer, globe_columns, globe_rows[i], min_lat[i],  max_lat[i],  min_lon[i],  max_lon[i], globe_file[i]);
                    logger.fine("Add area " + globe_file[i]);
                    elevation_repository.addElevationArea(area);
                    logger.info("GLOBE file: " + file + " loaded succesfully");
                    //ZHSIStatus.egpwsStatus = "GLOBE database loaded!";
                   //ZHSIStatus.egpws_db_status = ZHSIStatus.STATUS_EGPWS_DB_LOADED;
                    ZHSIStatus.set_elev_area_status(globe_file[i], true);
                } catch (Exception e) {
                    logger.warning("Could not map GLOBE file (" + e.toString() + ")");
                    ZHSIStatus.set_elev_area_status(globe_file[i], false);
                    ZHSIStatus.egpwsStatus = "GLOBE Errors (check log)";
                }
            }           
            elevation_repository.dumpAreas();
        } else {
            logger.warning("GLOBE resources directory is wrong!");
            ZHSIStatus.egpwsStatus = "GLOBE resources directory is wrong!";
            ZHSIStatus.egpws_db_status = ZHSIStatus.STATUS_EGPWS_DB_NOT_LOADED;
        }
    }
}
