/** 
 * Copyright (C) 2018  Andre Els (https://www.facebook.com/sum1els737)
 * 
 * Main class (entry point)
 * 
 * Inspired by XHSI (http://xhsi.sourceforge.net)
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

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ZHSI extends Application {
	
	private static final String LIB_DIR = System.getProperty("user.dir") + "/lib/";
	
	private static Logger logger = Logger.getLogger("org.andreels.zhsi");
	private String version = "3.4.9";
	private ZHSIPreferences preferences;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
				
		this.preferences = ZHSIPreferences.getInstance();
		logger.info("ZHSI version " + version + " started");
		
		if(new File(LIB_DIR).exists() && new File(LIB_DIR).isDirectory()) {
			logger.info("ZHSI jfoenix library found");
		} else {
			logger.info("ZHSI jfoenix library not found - You need the \"lib\" folder in the same location as this application");
			JOptionPane.showMessageDialog(null, "ZHSI jfoenix library (folder: /lib) not found, unable to start", "ZHSI Startup Error", JOptionPane.ERROR_MESSAGE);
		}
		
		primaryStage.setTitle("ZHSI " + version);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ZHSI.class.getResource("zhsifx.fxml"));
		AnchorPane mainPane = loader.load();
		Scene mainScene = new Scene(mainPane, 800,600);
		mainScene.getStylesheets().add(ZHSI.class.getResource("zhsifx.css").toExternalForm());
		ZHSIController controller = loader.getController();
		controller.setStage(primaryStage);
		controller.setVersion(version);
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(true);
		primaryStage.getIcons().add(new Image(ZHSI.class.getResourceAsStream("/org/andreels/zhsi/ZHSI_logo.png")));
		primaryStage.setOnCloseRequest(event -> {
			controller.disconnect();
			Platform.exit();
			System.exit(0);
		});

		if(this.preferences.get_preference(ZHSIPreferences.PREF_START_MINIMIZED).equals("true")) {
			primaryStage.setIconified(true);
		}

		primaryStage.show();
		primaryStage.xProperty().addListener((obs, oldVal, newVal) -> {
			this.preferences.set_preference(ZHSIPreferences.PREF_UI_POS_X, "" + Math.round(primaryStage.getX()));
		});
		primaryStage.yProperty().addListener((obs, oldVal, newVal) -> {
			this.preferences.set_preference(ZHSIPreferences.PREF_UI_POS_Y, "" + Math.round(primaryStage.getY()));
		});
		
	}

	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
		
	}
	
}