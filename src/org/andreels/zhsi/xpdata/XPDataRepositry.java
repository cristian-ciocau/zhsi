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
 */

package org.andreels.zhsi.xpdata;

import java.util.logging.Logger;

import org.andreels.zhsi.ZHSIStatus;
import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;


public class XPDataRepositry {

	ExtPlaneInterface iface;
	
	Thread checkZiboThread;
	String[] ziboStatus = null;

	boolean isObserved = false;
	
	private static Logger logger = Logger.getLogger("org.andreels.zhsi");
	
	// Initially all data was in this one class, but got rather large, so I split them up into their own classes as below
	
	private final String STATUS = "sim/aircraft/view/acf_tailnum:string";
	
	public TCAS tcas;
	public FMS fms;
	public AutoPilot autopilot;
	public Aircraft aircraft;
	public Avionics avionics;
	public Engines engines;
	public EFIS efis;
	public NavData navdata;
	public Radios radios;
	public Environment environment;
	public Systems systems;
	public ButtonsSwitches buttonsswitches;
	public Isfd isfd;
	public Electrical elec;
	
	private static XPDataRepositry instance;
	
	
	private XPDataRepositry(){
		
		this.iface = ExtPlaneInterface.getInstance();
		
		this.tcas = new TCAS(this.iface);
		this.fms = new FMS(this.iface);
		this.autopilot = new AutoPilot(this.iface);
		this.aircraft = new Aircraft(this.iface);
		this.avionics = new Avionics(this.iface);
		this.engines = new Engines(this.iface);
		this.efis = new EFIS(this.iface);
		this.radios = new Radios(this.iface);
		this.navdata = new NavData(this.iface);
		this.environment = new Environment(this.iface);
		this.systems = new Systems(this.iface);
		this.buttonsswitches = new ButtonsSwitches(this.iface);
		this.isfd = new Isfd(this.iface);
		this.elec = new Electrical(this.iface);
		
		subscribeZibo();
		
	}

	
	public void subscribeZibo() {
		
		checkZiboThread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				while(true) {
					try {
						if(!ZHSIStatus.zibo_loaded) {
							if(isObserved) {
								logger.info("Zibo737 Unloaded");
								excludeDatarefs();
								isObserved = false;
							}
							ziboStatus = iface.getDataRefValue(STATUS);
							if(ziboStatus != null) {
							 logger.fine("X-Plane returned value: " +iface.getDataRefValue(STATUS)[0] + " for DataRef: " + STATUS);
								if(iface.getDataRefValue(STATUS)[0].replaceAll("\"", "").equals("ZB736") ||
								   iface.getDataRefValue(STATUS)[0].replaceAll("\"", "").equals("ZB737") ||
								   iface.getDataRefValue(STATUS)[0].replaceAll("\"", "").equals("ZB738") ||
								   iface.getDataRefValue(STATUS)[0].replaceAll("\"", "").equals("ZB739")) {
									logger.info("Zibo737 Loaded");
									ZHSIStatus.zibo_loaded = true;
									includeDatarefs();
									isObserved = true;
								}
							}else {
								logger.fine("Not getting a valid value from X-Plane for Dataref: " + STATUS);
							}
						}
						Thread.sleep(2000);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		
		checkZiboThread.start();
		
	}
	

	private void includeDatarefs() {
				
		this.tcas.includeDrefs();
		this.fms.includeDrefs();
		this.autopilot.includeDrefs();
		this.aircraft.includeDrefs();
		this.avionics.includeDrefs();
		this.engines.includeDrefs();
		this.efis.includeDrefs();
		this.radios.includeDrefs();
		this.navdata.includeDrefs();
		this.environment.includeDrefs();
		this.systems.includeDrefs();
		this.buttonsswitches.includeDrefs();
		this.isfd.includeDrefs();
		this.elec.includeDrefs();
		
	}
	
	
	private void excludeDatarefs() {
				
		this.tcas.excludeDrefs();
		this.fms.excludeDrefs();
		this.autopilot.excludeDrefs();
		this.aircraft.excludeDrefs();
		this.avionics.excludeDrefs();
		this.engines.excludeDrefs();
		this.efis.excludeDrefs();
		this.radios.excludeDrefs();
		this.navdata.excludeDrefs();
		this.environment.excludeDrefs();
		this.systems.excludeDrefs();
		this.buttonsswitches.excludeDrefs();
		this.isfd.excludeDrefs();
		this.elec.excludeDrefs();
			
	}
	
	
	static {
		instance = new XPDataRepositry();
	}
	
	
	public static XPDataRepositry getInstance() {
		return instance;
	}

}