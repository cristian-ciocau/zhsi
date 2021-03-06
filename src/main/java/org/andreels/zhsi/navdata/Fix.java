/**
* Fix.java
* 
* Model class for a fix.
* 
* Copyright (C) 2007  Georg Gruetter (gruetter@gmail.com)
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

public class Fix extends NavigationObject {

    public boolean on_awy;


    public Fix(String name, float lat, float lon, boolean on_awy) {
        super(name, lat, lon);
        this.on_awy = on_awy;
    }


    public String toString() {
        return "FIX '" + this.name + "' @ (" + this.lat + "," + this.lon + ")";
    }
}