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
package org.andreels.zhsi.displays;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.andreels.zhsi.resources.LoadResources;


//import java.util.logging.Logger;

public class DUGraphicsConfig {

	Graphics2D g2;
	AffineTransform original_at;
	AffineTransform trans;
	AffineTransform navaid;
	LoadResources rs;
	public float scalex = 0.64f;
	public float scaley = 0.64f;
	public float scaling_factor = 0.64f;
	
	private Font glassFont;
	private InputStream glassFontStream;
	private Font xraasFont;
	private InputStream xraasFontStream;


	//PFD dimensions
	public float adiCy = (1053f * scaley) / 2;
	public float adiCx = (1072f * scalex) / 2;
	public float altTapeTop = 148.5f * scaley;
	public float altTapeBottom = altTapeTop + 756f * scaley;
	public float altTapeLeft = 815f * scalex;
	public float altTapeRight = altTapeLeft + 135f * scalex;
	public float altTapeWidth = altTapeRight - altTapeLeft;
	public float altTapeHeight = altTapeBottom - altTapeTop;
	public float speedTapeTop = altTapeTop;
	public float speedTapeBottom = altTapeBottom;
	public float speedTapeLeft = 20f * scalex;
	public float speedTapeRight = speedTapeLeft + 135f * scalex;
	public float speedTapeWidth = speedTapeRight - speedTapeLeft;
	public float speedTapeHeight = speedTapeBottom - speedTapeTop;
	

	//Colors
	public Color color_magenta = new Color(0xFA7CFA);
	public Color color_lime = new Color(0x00FF00);
	public Color color_white = new Color(0xFFFFFF);
	public Color color_markings = new Color(0xE5E5E5);
	public Color color_amber = new Color(0xFBA81F);
	public Color color_red = new Color(0xFF0000);
	public Color color_navaid = new Color(0x00CCFF);
	public Color color_instrument_gray = new Color(0x353539);

	//Fonts

	public Font font_zzl;
	public int line_height_zzl;
	public int max_char_advance_zzl;
	public int digit_width_zzl;

	public Font font_zl;
	public int line_height_zl;
	public int max_char_advance_zl;
	public int digit_width_zl;

	public Font font_xxxxxl;
	public int line_height_xxxxxl;
	public int max_char_advance_xxxxxl;
	public int digit_width_xxxxxl;

	public Font font_xxxxl;
	public int line_height_xxxxl;
	public int max_char_advance_xxxxl;
	public int digit_width_xxxxl;

	public Font font_xxxl;
	public int line_height_xxxl;
	public int max_char_advance_xxxl;
	public int digit_width_xxxl;

	public Font font_xxl;
	public int line_height_xxl;
	public int max_char_advance_xxl;
	public int digit_width_xxl;

	public Font font_xl;
	public int line_height_xl;
	public int max_char_advance_xl;
	public int digit_width_xl;

	public Font font_l;
	public int line_height_l;
	public int max_char_advance_l;
	public int digit_width_l;

	public Font font_m;
	public int line_height_m;
	public int max_char_advance_m;
	public int digit_width_m;

	public Font font_s;
	public int line_height_s;
	public int max_char_advance_s;
	public int digit_width_s;

	public Font font_xs;
	public int line_height_xs;
	public int max_char_advance_xs;
	public int digit_width_xs;

	public Font font_xxs;
	public int line_height_xxs;
	public int max_char_advance_xxs;
	public int digit_width_xxs;

	public Font font_xxxs;
	public int line_height_xxxs;
	public int max_char_advance_xxxs;
	public int digit_width_xxxs;

	public Font font_normal;
	public int line_height_normal;
	public int max_char_advance_normal;
	public int digit_width_normal;

	//Strokes
	public Stroke stroke_one = new BasicStroke(1f);
	public Stroke stroke_onehalf = new BasicStroke(1.5f);
	public Stroke stroke_two = new BasicStroke(2f);
	public Stroke stroke_twohalf = new BasicStroke(2.5f);
	public Stroke stroke_three = new BasicStroke(3f);
	public Stroke stroke_threehalf = new BasicStroke(3.5f);
	public Stroke stroke_four = new BasicStroke(4f);
	public Stroke stroke_fourhalf = new BasicStroke(4.5f);
	public Stroke stroke_five = new BasicStroke(5f);
	public Stroke stroke_fivehalf = new BasicStroke(5.5f);
	public Stroke stroke_six = new BasicStroke(6f);
	public Stroke stroke_sixhalf = new BasicStroke(6.5f);
	public Stroke stroke_seven = new BasicStroke(7f);
	public Stroke stroke_sevenhalf = new BasicStroke(7.5f);
	public Stroke stroke_eight = new BasicStroke(8f);
	public Stroke stroke_eighthalf = new BasicStroke(8.5f);
	public Stroke stroke_nine = new BasicStroke(9f);
	public Stroke stroke_ten = new BasicStroke(10f);
	public Stroke stroke_twelve = new BasicStroke(12f);

	float vor_dash[] = new float[2];
	public Stroke vor_dashes = new BasicStroke(3f);

	float vor1_radial_dash[] = new float[4];
	float vor2_radial_dash[] = new float[6];
	public Stroke vor1_radial_dashes = new BasicStroke(3f);
	public Stroke vor2_radial_dashes = new BasicStroke(3f);

	private float inactive_route_dash[] = new float[2];
	public Stroke inactive_route_dashes = new BasicStroke(3f);
	public Stroke runway_center_line = new BasicStroke(5f);


	public DecimalFormat df2 = new DecimalFormat("00");
	public DecimalFormat df3 = new DecimalFormat("000");
	public DecimalFormat df3hash = new DecimalFormat("###");
	public DecimalFormat df4 = new DecimalFormat("0000");
	public DecimalFormat qnh_in = new DecimalFormat("00.00");
	public DecimalFormat qnh_hpa = new DecimalFormat("0000");
	public DecimalFormat mach_format_top = new DecimalFormat("#.00");
	public DecimalFormat mach_format = new DecimalFormat("#.000");
	public DecimalFormat dme_formatter = new DecimalFormat("##0.0");
	public DecimalFormat ils_dme_formatter = new DecimalFormat("##0.0");
	public DecimalFormat nav_freq_formatter = new DecimalFormat("000.00");
	public DecimalFormat adf_freq_formatter = new DecimalFormat("000.0");
	public DecimalFormatSymbols format_symbols = mach_format.getDecimalFormatSymbols();
	//MFD
	public DecimalFormat mfd_ff = new DecimalFormat("0.00");
	public DecimalFormat mfd_oil_press = new DecimalFormat("###");
	public DecimalFormat mfd_oil_temp = new DecimalFormat("###");
	public DecimalFormat mfd_oil_qty = new DecimalFormat("##");
	public DecimalFormat mfd_vib = new DecimalFormat("0.0");
	public DecimalFormat mfd_n2 = new DecimalFormat("0.0");


	//private static Logger logger = Logger.getLogger("org.andreels.zhsi");

	public DUGraphicsConfig() {
		try {

			glassFontStream = getClass().getResourceAsStream("/boeingGlass.otf");
			glassFont = Font.createFont(Font.TRUETYPE_FONT, glassFontStream);
			xraasFontStream = getClass().getResourceAsStream("/VT323-Regular.ttf");
			xraasFont = Font.createFont(Font.TRUETYPE_FONT, xraasFontStream);

		} catch (IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}
		this.rs = LoadResources.getInstance();
		this.trans = new AffineTransform();
		this.navaid = new AffineTransform();

	}

	public void setScalex(float scalex) {
		this.scalex = scalex;
	}
	public void setScaley(float scaley) {
		this.scaley = scaley;
	}
	public void setScalingfactor(float scaling_factor) {
		this.scaling_factor = scaling_factor;
		updateStrokes();
		updateDimensions();
	}

	public void updateStrokes() {
		this.stroke_one = new BasicStroke(1f * scaling_factor);
		this.stroke_onehalf = new BasicStroke(1.5f * scaling_factor);
		this.stroke_two = new BasicStroke(2f * scaling_factor);
		this.stroke_twohalf = new BasicStroke(2.5f * scaling_factor);
		this.stroke_three = new BasicStroke(3f * scaling_factor);
		this.stroke_threehalf = new BasicStroke(3.5f * scaling_factor);
		this.stroke_four = new BasicStroke(4f * scaling_factor);
		this.stroke_fourhalf = new BasicStroke(4.5f * scaling_factor);
		this.stroke_five = new BasicStroke(5f * scaling_factor);
		this.stroke_fivehalf = new BasicStroke(5.5f * scaling_factor);
		this.stroke_six = new BasicStroke(6f * scaling_factor);
		this.stroke_sixhalf = new BasicStroke(6.5f * scaling_factor);
		this.stroke_seven = new BasicStroke(7f * scaling_factor);
		this.stroke_sevenhalf = new BasicStroke(7.5f * scaling_factor);
		this.stroke_eight = new BasicStroke(8f * scaling_factor);
		this.stroke_eighthalf = new BasicStroke(8.5f * scaling_factor);
		this.stroke_nine = new BasicStroke(9f * scaling_factor);
		this.stroke_ten = new BasicStroke(10f * scaling_factor);
		this.vor_dash[0] = 20.0f * scaling_factor;
		this.vor_dash[1] = 10.0f * scaling_factor;
		this.vor1_radial_dash[0] = 20f * scaling_factor;
		this.vor1_radial_dash[1] = 20f * scaling_factor;
		this.vor1_radial_dash[2] = 10f * scaling_factor;
		this.vor1_radial_dash[3] = 20f * scaling_factor;	
		this.vor2_radial_dash[0] = 20f * scaling_factor;
		this.vor2_radial_dash[1] = 10f * scaling_factor;
		this.vor2_radial_dash[2] = 10f * scaling_factor;
		this.vor2_radial_dash[3] = 10f * scaling_factor;
		this.vor2_radial_dash[4] = 10f * scaling_factor;
		this.vor2_radial_dash[5] = 10f * scaling_factor;
		this.inactive_route_dash[0] = 20f * this.scaling_factor;
		this.inactive_route_dash[1] = 20f * this.scaling_factor;

		this.vor_dashes = new BasicStroke(3f * scaling_factor, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f * scaling_factor, vor_dash, 0.0f);
		this.vor1_radial_dashes = new BasicStroke(3f * scaling_factor, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f * scaling_factor, vor1_radial_dash, 0.0f);
		this.vor2_radial_dashes = new BasicStroke(3f * scaling_factor, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f * scaling_factor, vor2_radial_dash, 0.0f);
		this.inactive_route_dashes = new BasicStroke(3f * scaling_factor, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f * scaling_factor, inactive_route_dash, 0.0f);
		this.runway_center_line = new BasicStroke(4f * scaling_factor, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f * scaling_factor, inactive_route_dash, 0.0f);
	}

	public void updateFonts(Graphics2D g2) {

		this.font_zzl = glassFont.deriveFont(scalex * 64f);
		this.font_zl = glassFont.deriveFont(scalex * 44f);
		this.font_xxxxxl = glassFont.deriveFont(scalex * 34f);
		this.font_xxxxl = glassFont.deriveFont(scalex * 28f);
		this.font_xxxl = glassFont.deriveFont(scalex * 26f);
		this.font_xxl = glassFont.deriveFont(scalex * 24f);
		this.font_xl = glassFont.deriveFont(scalex * 22f);
		this.font_l = glassFont.deriveFont(scalex * 18f);
		this.font_m = glassFont.deriveFont(scalex * 16f);
		this.font_s = glassFont.deriveFont(scalex * 14f);
		this.font_xs = glassFont.deriveFont(scalex * 12f);
		this.font_xxs = glassFont.deriveFont(scalex * 10f);
		this.font_xxxs = glassFont.deriveFont(scalex * 8f);
		this.font_normal = glassFont.deriveFont(scalex * 14f);


		FontMetrics fm;


		fm = g2.getFontMetrics(this.font_zzl);
		this.line_height_zzl = fm.getAscent();
		this.max_char_advance_zzl = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_zzl =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_zl);
		this.line_height_zl = fm.getAscent();
		this.max_char_advance_zl = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_zl =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_xxxxxl);
		this.line_height_xxxxxl = fm.getAscent();
		this.max_char_advance_xxxxxl = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_xxxxxl =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_xxxxl);
		this.line_height_xxxxl = fm.getAscent();
		this.max_char_advance_xxxxl = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_xxxxl =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_xxxl);
		this.line_height_xxxl = fm.getAscent();
		this.max_char_advance_xxxl = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_xxxl =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_xxl);
		this.line_height_xxl = fm.getAscent();
		this.max_char_advance_xxl = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_xxl =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_xl);
		this.line_height_xl = fm.getAscent();
		this.max_char_advance_xl = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_xl =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_l);
		this.line_height_l = fm.getAscent();
		this.max_char_advance_l = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_l =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_m);
		this.line_height_m = fm.getAscent();
		this.max_char_advance_m = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_m =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_s);
		this.line_height_s = fm.getAscent();
		this.max_char_advance_s = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_s =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_xs);
		this.line_height_xs = fm.getAscent();
		this.max_char_advance_xs = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_xs =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_xxs);
		this.line_height_xxs = fm.getAscent();
		this.max_char_advance_xxs = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_xxs =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_xxxs);
		this.line_height_xxxs = fm.getAscent();
		this.max_char_advance_xxxs = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_xxxs =  fm.stringWidth("88") - fm.stringWidth("8");

		fm = g2.getFontMetrics(this.font_normal);
		this.line_height_normal = fm.getAscent();
		this.max_char_advance_normal = fm.stringWidth("WW") - fm.stringWidth("W");
		this.digit_width_normal =  fm.stringWidth("88") - fm.stringWidth("8");


	}

	public void updateDimensions() {

		//PFD dimensions
		adiCy = (1053 * scaley) / 2;
		adiCx = (1072 * scalex) / 2;

		altTapeTop = 148.5f * scaley;
		altTapeBottom = altTapeTop + 756 * scaley;
		altTapeLeft = 815 * scalex;
		altTapeRight = altTapeLeft + 135 * scalex;
		altTapeWidth = altTapeRight - altTapeLeft;
		altTapeHeight = altTapeBottom - altTapeTop;
		speedTapeTop = altTapeTop;
		speedTapeBottom = altTapeBottom;
		speedTapeLeft = 20 * scalex;
		speedTapeRight = speedTapeLeft + 135 * scalex;
		speedTapeWidth = speedTapeRight - speedTapeLeft;
		speedTapeHeight = speedTapeBottom - speedTapeTop;

	}

	public void displayImage(Image image, float rotation, double x, double y, Graphics2D g2) {
		original_at = g2.getTransform();
		float img_cx;
		float img_cy;
		double img_x = x;
		double img_y = ((1053 - y) - image.getHeight(null));
		trans.scale(scalex,scaley);
		img_cx = ((float) (image.getWidth(null)))/2;
		img_cy = ((float) (image.getHeight(null)))/2;
		trans.translate(img_x, img_y);
		trans.rotate(Math.toRadians(rotation), img_cx, img_cy);
		g2.drawImage(image, trans, null);
		trans = original_at;
		g2.setTransform(original_at);


	}
	public void displayNavAid(Image image, float x, float y, Graphics2D g2) {


		g2.drawImage(image, (int)x - (int) ((image.getWidth(null) / 2) * scalex),  (int)y -  (int) ((image.getHeight(null) / 2) * scaley), (int) (image.getWidth(null) * scalex), (int) (image.getHeight(null) * scaley), null);




	}
	public void displayHorizon(Image image, float rotation, double x, double y, float pitch, Graphics2D g2) {

		original_at = g2.getTransform();
		float img_cx;
		float img_cy;
		double img_x = x;
		double img_y = ((1053 - y) - image.getHeight(null));
		trans.scale(scalex,scaley);
		img_cx = image.getWidth(null) / 2;
		//img_cx = ((float) (image.getWidth(null)))/2;
		img_cy = image.getHeight(null) / 2;
		//img_cy = ((float) (image.getHeight(null)))/2;
		trans.translate(img_x, img_y);
		trans.rotate(Math.toRadians(rotation), img_cx, img_cy);
		trans.translate(0, 0 + pitch);
		g2.drawImage(image, trans, null);
		trans = original_at;
		g2.setTransform(original_at);

	}
	public void drawRect2D(Rectangle2D name, float x, float y, float w, float h, float deg, boolean fill, Graphics2D g2) {

		original_at = g2.getTransform();
		name.setRect(x * scalex, (1053 - y - h) * scaley, w * scalex, h * scaley);
		if (deg != 0) {
			trans.rotate(Math.toRadians(deg), name.getX() + name.getWidth() * 0.5f, name.getY() + name.getHeight() * 0.5f);
		}else {
			trans.translate(0, 0);
		}
		if (fill) {
			g2.fill(trans.createTransformedShape(name));
		}else {
			g2.draw(trans.createTransformedShape(name));
		}
		trans = original_at;
		g2.setTransform(original_at);



	}
	/**
	 * 
	 * returns a text object to be drawn at the coordinates below:
	 * 
	 * @param string - string to be drawn
	 * @param x - x coordinates
	 * @param y - y coordinates
	 * @param size - size of the text in px
	 * @param deg - degree of rotation
	 * @param anchorx - x anchor of rotation
	 * @param anchory - y anchor of rotation
	 * @param align - alignment (left, center, right)
	 * @param g2
	 */
	public void drawText(String string, float x, float y, float size, float deg, float anchorx, float anchory, String align, Graphics2D g2) {

		original_at = g2.getTransform();
		Font newMy737 = glassFont.deriveFont(size * this.scaling_factor);
		if(deg != 0) {
			if (anchorx == 0 && anchory == 0) {
				trans.rotate(Math.toRadians(deg), g2.getFontMetrics(newMy737).stringWidth(string) * 0.5f , 0 - g2.getFontMetrics(newMy737).stringWidth(string) * 0.15f);
			}else {
				trans.rotate(Math.toRadians(deg), g2.getFontMetrics(newMy737).stringWidth(string) * 0.5f + (anchorx * scalex) , 0 - g2.getFontMetrics(newMy737).stringWidth(string) * 0.15f + ((y - anchory) * scaley));
			}
		}else if (align == "right") {
			trans.translate(0 - g2.getFontMetrics(newMy737).stringWidth(string), 0);
		}else if (align == "center") {
			trans.translate(0 - g2.getFontMetrics(newMy737).stringWidth(string) * 0.5f, 0);
		}else {
			trans.translate(0, 0);
		}
		g2.setFont(newMy737.deriveFont(trans));
		g2.drawString(string, x * scalex, (1053 - y) * scaley);
		trans = original_at;
		g2.setTransform(original_at);
	}

	public void drawText2(String text, float x, float y, float size, float rotate, Color color, boolean fill, String align, Graphics2D g2) {
		Color before_color = g2.getColor();
		AffineTransform before_at = g2.getTransform();
		g2.scale(this.scalex, this.scaley);
		Font newMy737 = glassFont.deriveFont(size);
		g2.setFont(newMy737);
		FontMetrics fm = g2.getFontMetrics(newMy737);
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		if(align == "right") {
			g2.translate(x - width, 1053 - y);
		}else if(align == "center") {
			g2.translate(x - (width * 0.5f), 1053 - y);
		}else {
			g2.translate(x, 1053 - y);
		}
		g2.rotate(Math.toRadians(rotate), width / 2, -height /2);
		if(fill) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0 - height, width, height);
		}
		g2.setColor(color);
		g2.drawString(text, 0, 0);
		g2.setColor(before_color);
		g2.setTransform(before_at);

	}
	public void drawText3(String text, float x, float y, float scalex, float scaley, float size, float rotate, Color color, boolean fill, String align, Graphics2D g2) {
		Color before_color = g2.getColor();
		AffineTransform before_at = g2.getTransform();
		g2.scale(scalex, scaley);
		Font newMy737 = glassFont.deriveFont(size);
		g2.setFont(newMy737);
		FontMetrics fm = g2.getFontMetrics(newMy737);
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		if(align == "right") {
			g2.translate(x - width, y);
		}else if(align == "center") {
			g2.translate(x - (width * 0.5f), y);
		}else {
			g2.translate(x, y);
		}
		g2.rotate(Math.toRadians(rotate), width / 2, -height /2);
		if(fill) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0 - height, width, height);
		}
		g2.setColor(color);
		g2.drawString(text, 0, 0);
		g2.setColor(before_color);
		g2.setTransform(before_at);

	}
	public void xraasText(String text, float x, float y, float size, float rotate, Color color, boolean fill, String align, Graphics2D g2) {
		Color before_color = g2.getColor();
		AffineTransform before_at = g2.getTransform();
		g2.scale(this.scalex, this.scaley);
		Font _xraasFont = xraasFont.deriveFont(size);
		g2.setFont(_xraasFont);
		FontMetrics fm = g2.getFontMetrics(_xraasFont);
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		if(align == "right") {
			g2.translate(x - width, 1053 - y);
		}else if(align == "center") {
			g2.translate(x - (width * 0.5f), 1053 - y);
		}else {
			g2.translate(x, 1053 - y);
		}
		g2.rotate(Math.toRadians(rotate), width / 2, -height /2);
		if(fill) {
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0 - height, width, height);
		}
		g2.setColor(color);
		g2.drawString(text, 0, 0);
		g2.setColor(before_color);
		g2.setTransform(before_at);

	}

}