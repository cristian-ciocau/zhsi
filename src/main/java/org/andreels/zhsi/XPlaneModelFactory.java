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
 
package org.andreels.zhsi;

import org.andreels.zhsi.xpdata.XPData;
import org.andreels.zhsi.xpdata.XPlaneData;

public class XPlaneModelFactory implements ModelFactory {
	
	private XPData single_data_instance;
	
	public XPlaneModelFactory() {
		this.single_data_instance = new XPlaneData();
	}

	public XPData getInstance() {
		return this.single_data_instance;
	}
}