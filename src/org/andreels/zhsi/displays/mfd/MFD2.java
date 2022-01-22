package org.andreels.zhsi.displays.mfd;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;

import org.andreels.zhsi.ModelFactory;
import org.andreels.zhsi.displays.DUBaseClass;

public class MFD2 extends DUBaseClass {

	private static final long serialVersionUID = 1L;
	
	private Arc2D.Float eng1n2 = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng1n2_fill = new Arc2D.Float(Arc2D.PIE);
	private Arc2D.Float eng2n2 = new Arc2D.Float(Arc2D.OPEN);
	private Arc2D.Float eng2n2_fill = new Arc2D.Float(Arc2D.PIE);
	private RoundRectangle2D hydraulicsRect = new RoundRectangle2D.Float();
	private RoundRectangle2D wheel1 = new RoundRectangle2D.Float();
	private RoundRectangle2D wheel2 = new RoundRectangle2D.Float();
	private RoundRectangle2D wheel3 = new RoundRectangle2D.Float();
	private RoundRectangle2D wheel4 = new RoundRectangle2D.Float();

	public MFD2(ModelFactory model_factory, String title, String pilot) {
		super(model_factory, title, pilot);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawInstrument(Graphics2D g2) {
	
		displayENG();
		
	}

	private void displayENG() {
		
		displayN2(100, 30, eng1n2, eng1n2_fill, this.xpd.eng1_n2(), 190);
		
		g2.setColor(gc.color_markings);

		gc.drawText3(gc.mfd_n2.format(this.xpd.eng1_n2()), 330, 107, this.scalex, this.scaley, 40f, 0f, rs.color_markings, false, "right", g2);

		displayN2(380, 30, eng2n2, eng2n2_fill, this.xpd.eng2_n2(), 190);

		gc.drawText3(gc.mfd_n2.format(this.xpd.eng2_n2()), 610, 107, this.scalex, this.scaley, 40f, 0f, rs.color_markings, false, "right", g2);

		int text_x = 320;
		int text_y = 220;
		g2.scale(this.scalex, this.scaley);
		g2.setFont(rs.glass22);
		g2.setColor(gc.color_navaid);
		g2.drawString("N", text_x, text_y);
		g2.drawString("2", text_x + 17, text_y + 7);	
		g2.setTransform(original_trans);
		
		//fuel flow

		g2.scale(this.scalex, this.scaley);
		g2.setFont(rs.glass22);
		g2.setColor(gc.color_navaid);
		g2.drawString("FF", text_x, text_y + 100);
		g2.setColor(gc.color_markings);
		g2.drawRect(text_x - 190, text_y + 68, 100, 45);
		g2.drawRect(text_x + 125, text_y + 68, 100, 45);
		g2.setFont(rs.glass40);
		if(this.xpd.eng1_n2() > 0f) {
			g2.drawString(gc.mfd_ff.format(this.xpd.fuel_flow1()), text_x - 183, text_y + 107);	
		}
		if(this.xpd.eng2_n2() > 0f) {
			g2.drawString(gc.mfd_ff.format(this.xpd.fuel_flow2()), text_x + 133, text_y + 107);
		}
		g2.setTransform(original_trans);
		
	}
	
	private void displayN2(int x, int y, Arc2D.Float dial, Arc2D.Float dialbg, float value, int diameter) {
		
		float rotation = (value / 100f) * 200f;
	
		//background
		g2.scale(this.scalex, this.scaley);
		g2.translate(x, y);
		g2.setColor(gc.color_instrument_gray);
		dialbg.setFrame(0, 0, diameter, diameter);
		dialbg.setAngleStart(0);
		dialbg.setAngleExtent(rotation * -1);
		g2.fill(dialbg);
		g2.setTransform(original_trans);
		
		//outline
		g2.scale(this.scalex, this.scaley);
		g2.translate(x, y);
		g2.setColor(gc.color_markings);
		g2.setStroke(gc.stroke_threehalf);
		eng1n2.setFrame(0, 0, diameter, diameter);
		eng1n2.setAngleStart(0);
		eng1n2.setAngleExtent(-210);
		g2.draw(eng1n2);
		
		// needle
		g2.rotate(Math.toRadians(rotation), diameter/2, diameter/2);
		g2.setStroke(gc.stroke_six);
		g2.drawLine(diameter/2, diameter/2, diameter, diameter/2);
		g2.setTransform(original_trans);
		
		//red lines
		g2.scale(this.scalex, this.scaley);
		g2.translate(x, y);
		g2.setColor(Color.RED);
		g2.setStroke(gc.stroke_six);
		g2.rotate(Math.toRadians(212f), diameter/2, diameter/2);
		g2.drawLine(diameter - 2, diameter/2, diameter + 20, diameter/2);
		g2.setTransform(original_trans);
		
		//rect
		g2.scale(this.scalex, this.scaley);
		g2.setColor(gc.color_markings);
		g2.setStroke(gc.stroke_threehalf);
		g2.drawRect(x + diameter/2, y + 35, 140, 50);
		g2.setTransform(original_trans);
		
	}
	
	private float getVib(float n1) {

		//0 to 10 = 0 to 1.81
		//10 to 37 = 1.81 to 1.21
		//37 to 105 = 1.21 to 1.47
				
		float vib = 0f;
		
        if(n1 > 37){
            
            float old_min = 37f;
            float old_max = 105f;
            float new_min = 1.21f;
            float new_max = 1.47f;
            vib = ( ( n1 - old_min ) / (old_max - old_min) ) * (new_max - new_min) + new_min;
            
        } else if (n1 > 10) {
            
            float old_min = 10f;
            float old_max = 37f;
            float new_min = 1.81f;
            float new_max = 1.21f;
            vib = ( ( n1 - old_min ) / (old_max - old_min) ) * (new_max - new_min) + new_min;
            
        } else {
           
            float old_min = 0f;
            float old_max = 10f;
            float new_min = 0f;
            float new_max = 1.81f;
            vib = ( ( n1 - old_min ) / (old_max - old_min) ) * (new_max - new_min) + new_min; 
        }
		return vib;
	}

}