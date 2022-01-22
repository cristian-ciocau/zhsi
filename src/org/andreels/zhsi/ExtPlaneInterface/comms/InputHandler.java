/**
 * 
 * Copyright (C) 2015  Pau G.
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
 * @author Pau G.
 */
 
package org.andreels.zhsi.ExtPlaneInterface.comms;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.andreels.zhsi.ZHSIStatus;
import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRef;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRefFactory;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRefRepository;
import org.andreels.zhsi.ExtPlaneInterface.util.ObservableAware;

public class InputHandler implements Runnable {

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");
	private static final String DATA_REF_PATTERN = "^u(i|f|d|ia|fa|b)\\s.+";
	private static final String VERSION_PATTERN = "^ZHSIPLUGIN\\s.*";

	private final String data;
	private final DataRefRepository repository;

	private DataRef dataRef;

	InputHandler(DataRefRepository repository, String data) {
		this.data = data;
		this.repository = repository;
	}

	public void run() {

		if(isDataRef(data)) {

			DataRefFactory drf = new DataRefFactory(data);

			dataRef = repository.getDataRef(drf.getDataRefName());
			dataRef = drf.getInstance(dataRef);
			repository.setDataRef(dataRef);

			ObservableAware.getInstance().update(dataRef);
		
			logger.finest(dataRef.toString());

		} else if(isVersion(data)) {
			logger.info("ZHSI Plugin Version" + data.replace("ZHSIPLUGIN", ""));
			ZHSIStatus.cStatus = "Connected to X-Plane " + data.replace("ZHSIPLUGIN", "");
		}
	}

	private boolean isDataRef(String data) {
		return Pattern.matches(InputHandler.DATA_REF_PATTERN, data);
	}

	private boolean isVersion(String data) {
		return Pattern.matches(InputHandler.VERSION_PATTERN, data);
	}
}