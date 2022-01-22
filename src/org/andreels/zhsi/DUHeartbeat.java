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
package org.andreels.zhsi;

import java.util.logging.Logger;

import org.andreels.zhsi.ExtPlaneInterface.comms.StoppableThread;

public class DUHeartbeat extends StoppableThread {
	
	private static Logger logger = Logger.getLogger("org.andreels.zhsi");
	
	private int watch_interval;
	private DisplayUnit du;

	public DUHeartbeat(DisplayUnit du, int watch_interval) {
		this.du = du;
		this.watch_interval = watch_interval;
		this.keep_running = true;
		
	}
	public void run() {
		while (this.keep_running) {
			try {
				Thread.sleep(this.watch_interval);
				this.du.heartbeat();
			} catch (Exception e) {
				this.keep_running = false;
				logger.warning("Caught exception in Watchdog! Stopping. (" + e.toString());
			}
		}
		logger.fine("DU-Heartbeat stopped");
	}
}