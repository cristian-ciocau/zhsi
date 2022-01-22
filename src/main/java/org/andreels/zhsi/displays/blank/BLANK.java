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
 
package org.andreels.zhsi.displays.blank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import org.andreels.zhsi.ZHSIPreferences;
import org.andreels.zhsi.displays.DUGraphicsConfig;
import org.andreels.zhsi.resources.LoadResources;


public class BLANK extends JPanel implements ComponentListener {

	private static final long serialVersionUID = 1L;
	
	Graphics2D g2;
	DUGraphicsConfig blank_gc;
	ZHSIPreferences preferences;
	LoadResources rs;
	
	String title;
	
	public BLANK(String title) {

		this.blank_gc = new DUGraphicsConfig();
		this.preferences = ZHSIPreferences.getInstance();
		this.rs = LoadResources.getInstance();
		this.setBackground(Color.BLACK);
		this.title = title;
		this.addComponentListener(this);
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
	}


	@Override
	public void componentHidden(ComponentEvent arg0) {
	}

	
	@Override
	public void componentMoved(ComponentEvent arg0) {	
	}

	
	@Override
	public void componentResized(ComponentEvent arg0) {
		blank_gc.setScalex(this.getWidth() / 686f);
		blank_gc.setScaley(this.getHeight() / 674f);
		blank_gc.setScalingfactor(this.getHeight() / 674f);
	}

	
	@Override
	public void componentShown(ComponentEvent arg0) {	
	}
	
}