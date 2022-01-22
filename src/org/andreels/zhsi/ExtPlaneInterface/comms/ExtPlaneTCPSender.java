/**
 * 
 * Copyright (C) 2015 Pau G.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * @author Pau G.
 */

package org.andreels.zhsi.ExtPlaneInterface.comms;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import org.andreels.zhsi.ExtPlaneInterface.command.CommandMessage;
import org.andreels.zhsi.ExtPlaneInterface.data.MessageRepository;

public class ExtPlaneTCPSender extends StoppableThread {

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");

	private Socket socket;
	private MessageRepository repository;

	int i = 0;

	public ExtPlaneTCPSender(Socket socket, MessageRepository repository) {
		this.socket = socket;
		this.repository = repository;
		this.keep_running = true;
		Thread.currentThread().setPriority(MIN_PRIORITY);
	}

	@Override
	public void run() {

		try {
			String command = null;
			CommandMessage message = null;

			this.setName("ExtPlane-SenderThread");
			logger.fine("Running Thread " + this.getName());
			PrintWriter writer = new PrintWriter(socket.getOutputStream());

			while (keep_running) {
				message = this.repository.getNextMessage();
				if (message != null) {
					command = message.getCommand();
					logger.finest("Sending message: " + command);
					i++;
					writer.println(command);
					writer.flush();
					command = null;
					logger.finest("Total number of dataref subs: " + i);
				} else {
					sleep(250);
				}
			}

		} catch (Exception e) {
			logger.severe("ERROR sending data.");
		} finally {
			
		}
	}
}