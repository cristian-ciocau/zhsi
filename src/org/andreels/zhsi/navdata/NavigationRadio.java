/**
* NavigationRadio.java
* 
* Facade for all flight simulator variables and aggregated information that 
* belongs to a navigation radio, like e. g. frequency, distance to tuned
* navigation object or reception flag.
* 
* Copyright (C) 2007  Georg Gruetter (gruetter@gmail.com)
* Copyright (C) 2009  Marc Rogiers (marrog.123@gmail.com)
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

import java.util.logging.Logger;

import org.andreels.zhsi.utils.AzimuthalEquidistantProjection;
import org.andreels.zhsi.xpdata.XPlaneData;


public class NavigationRadio {

	private static Logger logger = Logger.getLogger("org.andreels.zhsi");

    public static final int RADIO_TYPE_NAV = 0;
    public static final int RADIO_TYPE_ADF = 1;
    
    public static final int CAPTAIN_SIDE = 1;
    public static final int FO_SIDE = 2;
    
    public static final int VOR_RECEPTION_OFF = 0;
    public static final int VOR_RECEPTION_TO = 1;
    public static final int VOR_RECEPTION_FROM = 2;

    /**
     * number of bank. either 1 or 2
     */
    private int bank;

    /**
     * type of radio - must be a RADIO_TYPE constant
     */
    private int type;

    /**
     * frequency tuned into the radio
     */
    private float frequency;


    /**
     * the navaid's id
     */
    private String nav_id;

    private float sim_data_id_freq;
    private String sim_data_id_nav_id;
    private float sim_data_id_deflection; // relative bearing
    private float sim_data_id_dme_distance;
    private float sim_data_id_dme_time;
    private int sim_data_id_obs; // OBS
    private float sim_data_id_course; // OBS or the Localizer's frontcourse
    private int sim_data_id_fromto;
    private float sim_data_id_cdi_dots; // deviation in dots
    private int sim_data_id_gs_active;
    private float sim_data_id_gs_dots;
    

    // links to single_instances
    //private XPlaneSimDataRepository sim_data_repository;
    private NavigationObjectRepository navobj_repository;
    private AzimuthalEquidistantProjection projection;
    // link to the radionavaid that we are tuned to
    private RadioNavigationObject rnav_object;
	private XPlaneData xpd;


    // VOR, VOR-DME, LOC, ILS, IGS or DME
    public NavigationRadio(
            int bank,
            int radio_type,
            float id_freq,
            String id_nav_id,
            float id_deflection,
            float id_dme_distance,
            float id_dme_time,
            float id_course,
            int id_fromto,
            float id_cdi_dots,
            int id_gs_active,
            float id_gs_dots,
            XPlaneData xpd) {
        this.bank = bank;
        this.type = radio_type;
        this.frequency = 0;
        this.nav_id = "";
        this.sim_data_id_freq = id_freq;
        this.sim_data_id_nav_id = id_nav_id;
        this.sim_data_id_deflection = id_deflection;
        this.sim_data_id_dme_distance = id_dme_distance;
        this.sim_data_id_dme_time = id_dme_time;
        this.sim_data_id_course = id_course;
        this.sim_data_id_fromto = id_fromto;
        this.sim_data_id_cdi_dots = id_cdi_dots;
        this.sim_data_id_gs_active = id_gs_active;
        this.sim_data_id_gs_dots = id_gs_dots;
        this.xpd = xpd;
        this.navobj_repository = NavigationObjectRepository.get_instance();

  
    }

    // ADF
    public NavigationRadio(
            int bank,
            int radio_type,
            float id_freq,
            String id_nav_id,
            float id_deflection,
            float id_dme_distance,
            XPlaneData xpd) {
        this.bank = bank;
        this.type = radio_type;
        this.frequency = 0;
        this.nav_id = "";
        this.sim_data_id_freq = id_freq;
        this.sim_data_id_nav_id = id_nav_id;
        this.sim_data_id_deflection = id_deflection;
        this.sim_data_id_dme_distance = id_dme_distance;
        this.sim_data_id_course = -1;
        this.sim_data_id_fromto = -1;
        this.sim_data_id_cdi_dots = -1;
        this.sim_data_id_gs_active = -1;
        this.sim_data_id_gs_dots = -1;
        this.xpd = xpd;
        this.navobj_repository = NavigationObjectRepository.get_instance();


    }


    public boolean receiving() {
        update_radio_data();
        return ! nav_id.equals("");
    }


    public RadioNavigationObject get_radio_nav_object() {
        update_radio_data();
        return this.rnav_object;
    }


    public int get_fromto() {
        
    	switch(this.bank) {
			case CAPTAIN_SIDE:
				switch(this.type) {
				case RADIO_TYPE_NAV: return xpd.nav1_fromto();
				}
			case FO_SIDE:
				switch(this.type) {
				case RADIO_TYPE_NAV: return xpd.nav2_fromto();
				}
			default: return 0;
    	}
 
	}


    public float get_rel_bearing() {
        
    	if (this.bank == CAPTAIN_SIDE && this.type == RADIO_TYPE_NAV) {
    		return xpd.nav1_rel_bearing();
    	} else if (this.bank == FO_SIDE && this.type == RADIO_TYPE_NAV) {
    		return xpd.nav2_rel_bearing();
    	} else if (this.bank == CAPTAIN_SIDE && this.type == RADIO_TYPE_ADF) {
    		return xpd.adf1_rel_bearing();
    	} else if (this.bank == FO_SIDE && this.type == RADIO_TYPE_ADF) {
    		return xpd.adf2_rel_bearing();
    	} else {
    		return 0f;
    	}

    }


    public float get_frequency() {
        update_radio_data();
        return this.frequency;
    }
    

    public String get_nav_id() {
        update_radio_data();
        return this.nav_id;
        
    }


    public float get_radial() {
    	return 0;   	
    }
    
    public float get_vor_arrow_rotation() {
    	return 0;
    }


    public float get_distance() {
 
    	if (this.bank == 1 && this.type == RADIO_TYPE_NAV) {
    		return xpd.nav1_dme_nm();

    	} else if (this.bank == 2 && this.type == RADIO_TYPE_NAV) {
    		return xpd.nav2_dme_nm();

    	} else if (this.bank == 1 && this.type == RADIO_TYPE_ADF) {
    		return xpd.nav1_dme_nm();

    	} else if(this.bank == 2 && this.type == RADIO_TYPE_ADF) {
    		return xpd.adf2_dme_nm();
    	} else {
    		return 0f;
    	}

    }


    public float get_ete() {
    	return 0;
    }


    public int get_bank() {
        return this.bank;
    }


    public boolean freq_is_nav() {
        update_radio_data();
        return (this.type == RADIO_TYPE_NAV);
    }


    public boolean freq_is_localizer() {
        update_radio_data();
        return ((this.type == RADIO_TYPE_NAV) && (this.rnav_object != null) && (this.rnav_object instanceof Localizer));
    }


    public boolean freq_is_adf() {
        update_radio_data();
        return (this.type == RADIO_TYPE_ADF);
    }


    private void update_radio_data() {
    	float current_freq = 0f;
    	String current_nav_id = null;
   	  	
    	if (this.bank == 1 && this.type == RADIO_TYPE_NAV) {
    		 current_freq = this.xpd.nav1_freq();
    		 if (this.xpd.nav1_id() == null ) {
    			 current_nav_id = "";
    		 } else {
    			 current_nav_id = this.xpd.nav1_id(); 
    		 }
    	} else if (this.bank == 2 && this.type == RADIO_TYPE_NAV) {
    		 current_freq = this.xpd.nav2_freq();
    		 if (this.xpd.nav2_id() == null ) {
    			 current_nav_id = "";
    		 } else {
    			 current_nav_id = this.xpd.nav2_id(); 
    		 }
    	} else if (this.bank == 1 && this.type == RADIO_TYPE_ADF) {
    		 current_freq = this.xpd.adf1_freq();
    		 if (this.xpd.adf1_id() == null ) {
    			 current_nav_id = "";
    		 } else {
    			 current_nav_id = this.xpd.adf1_id(); 
    		 }
    	} else if(this.bank == 2 && this.type == RADIO_TYPE_ADF) {
    		 current_freq = this.xpd.adf2_freq();
       		 if (this.xpd.adf2_id() == null ) {
    			 current_nav_id = "";
    		 } else {
    			 current_nav_id = this.xpd.adf2_id(); 
    		 }
    	}
    	
        if (current_freq > 10000.0f) {
            current_freq = (current_freq/100.0f);
        }
       	
		if ( (this.frequency != current_freq) || ( ! this.nav_id.equals(current_nav_id) )|| ( this.rnav_object == null )|| ( this.xpd.rough_distance_to(this.rnav_object) > this.rnav_object.range * NavigationObjectRepository.RANGE_MULTIPLIER ) ) {
			this.frequency = current_freq;
			this.nav_id = current_nav_id;
			this.rnav_object = this.navobj_repository.find_tuned_nav_object(xpd.latitude(), xpd.longitude(), current_freq, current_nav_id);
		}   

    }

	@Override
	public String toString() {
		return "NavigationRadio [bank=" + bank + ", type=" + type + ", frequency=" + frequency + ", nav_id=" + nav_id
				+ ", sim_data_id_freq=" + sim_data_id_freq + ", sim_data_id_nav_id=" + sim_data_id_nav_id
				+ ", sim_data_id_deflection=" + sim_data_id_deflection + ", sim_data_id_dme_distance="
				+ sim_data_id_dme_distance + ", sim_data_id_dme_time=" + sim_data_id_dme_time + ", sim_data_id_obs="
				+ sim_data_id_obs + ", sim_data_id_course=" + sim_data_id_course + ", sim_data_id_fromto="
				+ sim_data_id_fromto + ", sim_data_id_cdi_dots=" + sim_data_id_cdi_dots + ", sim_data_id_gs_active="
				+ sim_data_id_gs_active + ", sim_data_id_gs_dots=" + sim_data_id_gs_dots + ", rnav_object="
				+ rnav_object + "]";
	}
    
}