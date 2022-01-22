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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.andreels.zhsi.ZHSIStatus;
import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;
import org.andreels.zhsi.ExtPlaneInterface.data.DataRefRepository;

public class ExtPlaneTCPReceiver extends StoppableThread {

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");
    
    private Socket socket;
    private ExecutorService pool;
    private DataRefRepository repository;
    private ExtPlaneInterface iface;

    public ExtPlaneTCPReceiver(Socket socket, DataRefRepository repository, int poolSize) {

        super();
        this.socket = socket;
        this.repository = repository;
        this.keep_running = true;
        
        this.iface = ExtPlaneInterface.getInstance();

        pool = Executors.newFixedThreadPool(poolSize);

        Thread.currentThread().setPriority(MIN_PRIORITY);

    }

    @Override
    public void run() {

        try {
        	
            BufferedReader inFromServer = null;
            String valor = null;
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
            while(keep_running) {
                valor = inFromServer.readLine();

                if (valor != null) {
                	pool.execute(new InputHandler(this.repository, valor));
                } else {
                	//ZHSIStatus.status = ZHSIStatus.STATUS_NOT_CONNECTED;
                	//ZHSIStatus.zibo_loaded = false;
                	//ZHSIStatus.cStatus = "Error: socket closed unexpectantly : X-Plane probably crashed !";
                	//ZHSIStatus.receiving = false;
                	logger.info("run() valor is null, socket.close()");
                	socket.close();
                }       
            }
			
        } catch (IOException e) {
            logger.info("run() IOException: " + e);
            //ZHSIStatus.status = ZHSIStatus.STATUS_NOT_CONNECTED;
            //ZHSIStatus.zibo_loaded = false;
        	//ZHSIStatus.cStatus = "Error: Getting data from server";
        	//ZHSIStatus.receiving = false;
        	
        	try {
				logger.info("run() socket.close()");
        		socket.close();
				logger.info("run() iface.start()");
				iface.start();
			} catch (Exception e1) {
				logger.info("run() Exception: " + e1);
				e1.printStackTrace();
			}
        } finally {
			logger.info("run() pool.shutdown()");
            pool.shutdown();
        }
    }
}