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
 
package org.andreels.zhsi.ExtPlaneInterface;

import java.util.logging.Logger;

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.ZHSIStatus;
import org.andreels.zhsi.ExtPlaneInterface.command.CommandMessage;
import org.andreels.zhsi.ExtPlaneInterface.command.DataRefCommand;
import org.andreels.zhsi.ExtPlaneInterface.command.ExtPlaneCommand;
import org.andreels.zhsi.ExtPlaneInterface.comms.ExtPlaneTCPReceiver;
import org.andreels.zhsi.ExtPlaneInterface.comms.ExtPlaneTCPSender;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRef;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRefRepository;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRefRepositoryImpl;
import org.andreels.zhsi.ExtPlaneInterface.data.MessageRepository;
import org.andreels.zhsi.ExtPlaneInterface.data.MessageRepositoryImpl;
import org.andreels.zhsi.ExtPlaneInterface.util.Constants.DataType;
import org.andreels.zhsi.ExtPlaneInterface.util.ObservableAware;
import org.andreels.zhsi.ExtPlaneInterface.util.Observer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ExtPlaneInterface {

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");

	private Socket socket;

	private DataRefRepository dataRefrepository;
	private MessageRepository messageRepository;

	private ExtPlaneTCPReceiver receive = null;
	private ExtPlaneTCPSender sender = null;

	private static ExtPlaneInterface instance = null;

	private static ZHSIPreferences preferences = ZHSIPreferences.getInstance();

	private String server;
	private int port = 52000;
	private int poolSize = 4;

	private boolean isIncluded = false;

	private ExtPlaneInterface() {		
		this.server = preferences.get_preference(ZHSIPreferences.PREF_EXTPLANE_SERVER);
		this.initDataRefRepository();
		this.initMessageRepository();
	}

	public void excludeDataRef(String dataRefName) {
		this.sendMessage(new DataRefCommand(DataRefCommand.DATAREF_ACTION.UNSUBSCRIBE,dataRefName));
	}

	public DataRef getDataRef(String dataRef) {
		return this.dataRefrepository.getDataRef(dataRef);
	}

	public DataType getDataRefType(String dataRefName) {
		DataRef dr = this.dataRefrepository.getDataRef(dataRefName);

		if(dr!=null) 
			return dr.getDataType();

		return null;
	}

	public String[] getDataRefValue(String dataRefName) {
		DataRef dr = this.dataRefrepository.getDataRef(dataRefName);

		logger.fine("Getting value for DataRef: " + dataRefName);

		if(dr != null) {
			//logger.fine("DataRef: " + dataRefName + " has a value of: " + dr.getValue()[0]);
			return dr.getValue();
		}
		logger.fine("DataRef: " + dataRefName + " has a value of: NULL");
		return null;
	}

	public void includeDataRef(String dataRefName) {
		this.includeDataRef(dataRefName, null);
	}

	public void includeDataRef(String dataRefName, Float accuracy) {
		DataRefCommand drc = new DataRefCommand(DataRefCommand.DATAREF_ACTION.SUBSCRIBE,dataRefName);

		if(accuracy!=null)
			drc.setAccuracy(accuracy);

		this.sendMessage(drc);
	}

	public void setDataRefValue(String dataRefName, String... value) {
		this.sendMessage(new DataRefCommand(DataRefCommand.DATAREF_ACTION.SET, dataRefName, value));
	}

	public void sendMessage(CommandMessage message) {
		this.messageRepository.sendMessage(message);
	}

//	public void setExtPlaneUpdateInterval(String interval) {
//		this.sendMessage(new ExtPlaneCommand(ExtPlaneCommand.EXTPLANE_SETTING.UPDATE_INTERVAL, interval));
//	}

	public void start() throws Exception {
		try {
			this.connect();
		} catch(Exception e) {
			logger.info("start() Exception: " + e);
			this.stopReceiving();
			this.stopSending();
			throw e;
		}
	}

	public void stop() {
		if(isIncluded) {
			this.excludeDataRef("sim/aircraft/view/acf_tailnum:string");
			this.dataRefrepository.removeDataRef("sim/aircraft/view/acf_tailnum:string");
			isIncluded = false;
		}
		ZHSIStatus.status = ZHSIStatus.STATUS_NOT_CONNECTED;
		ZHSIStatus.zibo_loaded = false;
		logger.info("stop() Error connecting to zhsi-plugin, Server=" + server + " port=" + port);
		ZHSIStatus.receiving = false;
		//ZHSIStatus.cStatus = "<html><font color=\"red\">Error</font><font color=\"white\"> connecting to host:</font> " + server + "</html>";
		this.stopReceiving();
		this.stopSending();
	}

	public void observeDataRef(String dataRefName, Observer<DataRef> observer) {
		ObservableAware.getInstance().addObserver(dataRefName, observer);
	}

	public void unObserveDataRef(String dataRefName, Observer<DataRef> observer) {
		ObservableAware.getInstance().removeObserver(dataRefName, observer);
		this.dataRefrepository.removeDataRef(dataRefName);
	}

	private void connect() throws InterruptedException {

		while(true) {
			try {
				//socket = new Socket(server, port);
				socket = new Socket();
				this.server = preferences.get_preference(ZHSIPreferences.PREF_EXTPLANE_SERVER);
				socket.connect(new InetSocketAddress(this.server, port), 2000);
				logger.info("Connected to zhsi-plugin, Server=" + server);
				this.startSending();
				this.startReceiving();
				ZHSIStatus.status = ZHSIStatus.STATUS_CONNECTED;
				//ZHSIStatus.cStatus = "<html><font color=\"lime\">Connected to</font> " + server + " <font color=\"white\">port</font> " + port + "</html>";
				ZHSIStatus.cStatus = "Connected to: " + server;
				this.includeDataRef("sim/aircraft/view/acf_tailnum:string");
				//this.setExtPlaneUpdateInterval("0.1");
				isIncluded = true;
				break;
			} catch (UnknownHostException e) {
				logger.info("connect() UnknownHostException: " + e);
				this.stop();
				//				ZHSIStatus.status = ZHSIStatus.STATUS_NOT_CONNECTED;
				//				ZHSIStatus.zibo_loaded = false;
				//				logger.info("[ExtPlaneInterface::connect] Unkown Host : Error connecting to, Server=" + server + " port=" + port);
				//				ZHSIStatus.receiving = false;
				//				ZHSIStatus.cStatus = "<html><font color=\"red\">Unkown Host:</font><font color=\"white\"> Error connecting to:</font> " + server + "</html>";
				Thread.sleep(1000);
			} catch (IOException e1) {
				logger.info("connect() IOException: " + e1);
				this.stop();
				//				ZHSIStatus.status = ZHSIStatus.STATUS_NOT_CONNECTED;
				//				ZHSIStatus.zibo_loaded = false;
				//				logger.info("[ExtPlaneInterface::connect] Error connecting to ExtPlane plugin, Server=" + server + " port=" + port);
				//				ZHSIStatus.receiving = false;
				//				ZHSIStatus.cStatus = "<html><font color=\"red\">Error</font><font color=\"white\"> connecting to host:</font> " + server + "</html>";
				Thread.sleep(1000);
			}
		}  
	}

	private void initDataRefRepository() {
		this.dataRefrepository = new DataRefRepositoryImpl();
	}

	private void initMessageRepository() {
		this.messageRepository = new MessageRepositoryImpl();
	}

	private void startReceiving() {
		receive = new ExtPlaneTCPReceiver(socket, dataRefrepository, poolSize);
		receive.start();
		ZHSIStatus.receiving = true;
	}

	private void startSending() {
		sender = new ExtPlaneTCPSender(socket, messageRepository);
		sender.start();
	}

	private void stopReceiving() {
		if(this.receive != null) {
			this.receive.setKeep_running(false);
			this.receive = null;
			ZHSIStatus.receiving = false;	
		}
	}

	private void stopSending() {
		if(this.sender != null) {
			this.sender.setKeep_running(false);
			this.sender = null;
		}
	}

	public static ExtPlaneInterface getInstance() {
		if (instance == null) {
			instance = new ExtPlaneInterface();
		}
		return instance;
	}
}