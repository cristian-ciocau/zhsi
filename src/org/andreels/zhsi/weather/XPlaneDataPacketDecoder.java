/**
 * XPlaneDataPacketDecoder.java
 * 
 * Decodes the data contained in received data packets. Flight simulator data
 * and FMS routes are extracted from received data packets and sent to the
 * XPlaneSimDataRepository and the FMS respectively. XPlaneDataPacketDecoder
 * also calls the tick_updates method of XPlaneSimDataRepository, which in turn
 * triggers repainting of the UI.
 * 
 * ZHSI usage is for weather packets only - Andre Els
 * 
 * Copyright (C) 2007  Georg Gruetter (gruetter@gmail.com)
 * Copyright (C) 2009-2010  Marc Rogiers (marrog.123@gmail.com)
 * Copyright (C) 2009-2014 qwerty (XFMC section)
 * Copyright (C) 2015 Nicolas Carel (QPAC + Weather section)
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
package org.andreels.zhsi.weather;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.logging.Logger;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.xpdata.XPData;

public class XPlaneDataPacketDecoder implements XPlaneDataPacketObserver {

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");
	
	private boolean received_weather_packet = false;
	WeatherRepository weather_repository = null;
	ZHSIPreferences preferences;
	ModelFactory model_factory;
	XPData xpd;
	
	public XPlaneDataPacketDecoder(ModelFactory sim_model) {
		this.model_factory = sim_model;
		this.xpd = this.model_factory.getInstance();
		this.preferences = ZHSIPreferences.getInstance();
		this.weather_repository = WeatherRepository.getInstance();
		
	}
	
	public void new_sim_data(byte[] sim_data, int length) throws Exception {
		
		String packet_type = new String(sim_data, 0, 4).trim();
		
		if (packet_type.equals("xRAD")) {
			if (this.received_weather_packet == false)
				logger.fine("Received first Weather packet");
			DataInputStream data_stream = new DataInputStream(new ByteArrayInputStream(sim_data));
			data_stream.skipBytes(5);    // skip the bytes containing the packet type id + 1 byte "M"
			byte byteBuffer[] = new byte[8]; // Largest data type is 64-bits (8 bytes)
			byte slice[] = new byte[70]; // X-Plane weather slice
			/*
			 * Weather packet structure
			 * int lon_deg_wes;
			 * int lat_deg_sou;
			 * int lat_min_sou;
			 * byte weather_cell[61]; 
			 * http://developer.x-plane.com/?article=weather-radar-data       	
			 */

			/*
			 * X-Plane does not send network data in network byte order
			 */
			data_stream.readFully(byteBuffer, 0, 4);
			int lon = (byteBuffer[3]) << 24 | (byteBuffer[2] & 0xff) << 16 |
					(byteBuffer[1] & 0xff) << 8 | (byteBuffer[0] & 0xff);
			data_stream.readFully(byteBuffer, 0, 4);
			int lat = (byteBuffer[3]) << 24 | (byteBuffer[2] & 0xff) << 16 |
					(byteBuffer[1] & 0xff) << 8 | (byteBuffer[0] & 0xff);
			data_stream.readFully(byteBuffer, 0, 4);
			/*
			 * lat_offset range : -1 to 61 = 62 rows
			 */
			byte lat_offset = byteBuffer[0];
			lat_offset++;

			logger.finest("Weather packet lon="+lon + " lat="+lat + " lat_offset=" + lat_offset + " length="+length+ " bytes");
			if (length < 81 ) {
				logger.fine("Incomplete weather packet");
			} else {
				data_stream.readFully(slice, 0, 61);
				this.weather_repository.updateArea(lat, lon, lat_offset, slice);
				this.received_weather_packet = true;
			}
		}
		
	}

}
