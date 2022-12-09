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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.andreels.zhsi.ExtPlaneInterface.ExtPlaneInterface;
import org.andreels.zhsi.ExtPlaneInterface.command.ExtPlaneCommand;
import org.andreels.zhsi.elevationData.GlobeElevationBuilder;
import org.andreels.zhsi.gauges.FlapGauge;
import org.andreels.zhsi.navdata.Airport;
import org.andreels.zhsi.navdata.AptNavXP900DatNavigationObjectBuilder;
import org.andreels.zhsi.navdata.CoordinateSystem;
import org.andreels.zhsi.navdata.NavigationObjectRepository;
import org.andreels.zhsi.resources.LoadResources;
import org.andreels.zhsi.utils.AptFileParser;
import org.andreels.zhsi.weather.XPlaneDataPacketDecoder;
import org.andreels.zhsi.weather.XPlaneWeatherReceiver;
import org.andreels.zhsi.xpdata.XPData;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.swing.SwingUtilities;


public class ZHSIController implements Initializable {
	
	private ExtPlaneInterface iface;
	
	private static final String LOG_FILE = System.getProperty("user.dir") + "/ZHSI.log";
	private static final String ANNUN_FILE = System.getProperty("user.dir") + "/ZHSI.annunciators";

	private final String CPT_OUTBD_TITLE = "Captain OutBoard Display";
	private final String CPT_INBD_TITLE = "Captain InBoard Display";
	private final String FO_OUTBD_TITLE = "FO OutBoard Display";
	private final String FO_INBD_TITLE = "FO InBoard Display";
	private final String UPPER_DU_TITLE = "Upper EICAS Display";
	private final String LOWER_DU_TITLE = "Lower EICAS Display";

	private Stage stage;
	private String version;
	private ZHSIPreferences preferences;
	private LoadResources rs;
	private XPData xpd;
	private NavigationObjectRepository nor;
	Thread ext_iface;
	private static Logger logger = Logger.getLogger("org.andreels.zhsi");

	@FXML
	private MenuItem appClose;

	public final int WEATHER_UDP_PORT = 48003;
	
	@FXML
	private JFXButton btnDash;
	@FXML
	private JFXButton btnDu;
	@FXML
	private JFXButton btnInst;
	@FXML
	private JFXButton btnOptions;
	@FXML
	private JFXButton btnSettings;
	@FXML
	private JFXButton btnLog;
	@FXML
	private JFXButton btnAnnunciators;
	@FXML
	private JFXButton btnAbout;
	@FXML
	private ScrollPane dashPane;
	@FXML
	private ScrollPane duPane;
	@FXML
	private ScrollPane instPane;
	@FXML
	private ScrollPane annunPane;
	@FXML
	private ScrollPane optionsPane;
	@FXML
	private ScrollPane settingsPane;
	@FXML
	private ScrollPane logPane;
	@FXML
	private ScrollPane aboutPane;
	@FXML
	private Circle roundLedWeatherStatus;
	@FXML
	private Circle roundLedTerrainStatus;
	@FXML
	private Circle roundLedNavStatus;
	@FXML
	private Circle roundLedZiboStatus;
	@FXML
	private Circle roundLedXPStatus;
	@FXML
	private Label paneHeading;
	@FXML
	private Label paneSubHeading;
	@FXML
	private Label statusBarText;
	@FXML
	private TextArea txtAreaAbout;
	@FXML
	private Rectangle ledXPStatus;
	@FXML
	private Rectangle ledZiboStatus;
	@FXML
	private Rectangle ledNavStatus;
	@FXML
	private Rectangle ledTerrainStatus;
	@FXML
	private Rectangle ledWeatherStatus;

	@FXML
	private Label lblVersion;
	@FXML
	private Label lblNearestAirportValue;
	@FXML
	private Label lblPlatform;
	@FXML
	private Label lblFreeMem;
	@FXML
	private Label lblAllocMem;
	@FXML
	private Label lblMaxMem;
	@FXML
	private Label lblTotalFreeMem;
	@FXML
	private Label lblEgpwsStatusHeading;
	@FXML
	private Label lblZHSIversionAbout;
	
	/*
	@FXML
	private ColorPicker inst_gray_color;
	@FXML
	private ColorPicker inst_white_color;
	@FXML
	private ColorPicker inst_magenta_color;
	@FXML
	private ColorPicker inst_lime_color;
	@FXML
	private ColorPicker inst_amber_color;
	@FXML
	private ColorPicker inst_red_color;
	@FXML
	private ColorPicker inst_cyan_color;
	@FXML
	private ColorPicker inst_horizon_sky_color;
	@FXML
	private ColorPicker inst_horizon_ground_color;
	*/

	@FXML
	private JFXToggleButton duAlwaysOnTop;
	@FXML
	private JFXToggleButton startUIMinimized;
	@FXML
	private JFXToggleButton fuelInKgs;
	@FXML
	private JFXToggleButton fuelDisplay;
	@FXML
	private JFXToggleButton hardwareThrottle;
	@FXML
	private JFXToggleButton compactEngine;
	@FXML
	private JFXToggleButton annunWindow;
	@FXML
	private JFXToggleButton stabTrim;
	@FXML
	private JFXToggleButton rudderTrim;
	@FXML
	private JFXToggleButton enableXraas;
	@FXML
	private JFXToggleButton roundAltimeter;

	@FXML
	private JFXToggleButton cptOutBdToggle;
	@FXML
	private JFXToggleButton cptInBdToggle;
	@FXML
	private JFXToggleButton foOutBdToggle;
	@FXML
	private JFXToggleButton foInBdToggle;
	@FXML
	private JFXToggleButton upperEicasToggle;
	@FXML
	private JFXToggleButton lowerEicasToggle;
	@FXML
	private JFXToggleButton showFPS;
	@FXML
	private JFXToggleButton isfdToggle;
	@FXML
	private JFXToggleButton flapToggle;
	@FXML
	private JFXToggleButton cptChronoToggle;
	@FXML
	private JFXToggleButton foChronoToggle;
	@FXML
	private JFXToggleButton elecToggle;
	@FXML
	private JFXToggleButton irsToggle;
	@FXML
	private JFXToggleButton fltaltToggle;
	@FXML
	private JFXToggleButton landaltToggle;
	@FXML
	private JFXToggleButton rmiToggle;
	
	@FXML
	private JFXTextField txtXPDir;
	@FXML
	private JFXTextField txtXPIp;
	@FXML
	private JFXTextField txtEGPWSDir;

	@FXML
	private JFXButton btnBrowseXP;
	@FXML
	private JFXButton btnXPIp;
	@FXML
	private JFXButton btnBrowseEgpws;
	
	@FXML
	private TextArea logTextArea;
	@FXML
	private TextArea annunTextArea;
	@FXML
	private Text xplaneStatus;
	@FXML
	private Text ziboStatus;
	@FXML
	private Text navStatus;
	@FXML
	private Text weatherStatus;
	@FXML
	private Text egpwsStatus;
	@FXML
	private Label lblXplaneStatusBox;
	@FXML
	private Label lblNavStatusBox;
	@FXML
	private Label lblZiboStatusBox;
	@FXML
	private Label lblTerrainStatusBox;
	@FXML
	private Label lblWeatherStatusBox;
	@FXML
	private JFXSlider dcVoltsXslider;
	@FXML
	private JFXSlider dcAmpsXslider;
	@FXML
	private JFXSlider acVoltsXslider;
	@FXML
	private JFXSlider acAmpsXslider;
	@FXML
	private JFXSlider cpsFreqXslider;

	
	private DisplayUnit cptOutBd = null;
	private DisplayUnit cptInBd = null;
	private DisplayUnit foOutBd = null;
	private DisplayUnit foInBd = null;
	private DisplayUnit upperDu = null;
	private DisplayUnit lowerDu = null;
	private FlapGauge flapGauge = null;
	private Instrument isfd = null;
	private Instrument irs = null;
	private Instrument fltalt = null;
	private Instrument landalt = null;
	private Instrument rmi = null;
	private Instrument elecPanel = null;
	private Instrument cptChrono = null;
	private Instrument foChrono = null;
	public ModelFactory model_instance;
	
	private DUHeartbeat cptoutbd_heartbeat;
	private DUHeartbeat cptinbd_heartbeat;
	private DUHeartbeat fooutbd_heartbeat;
	private DUHeartbeat foinbd_heartbeat;
	private DUHeartbeat uppereicas_heartbeat;
	private DUHeartbeat lowereicas_heartbeat;
	
	AnnunciatorWindow annunicatorWindow;

	private boolean EGPWSDirOK = false;

	Runtime java_run = Runtime.getRuntime();
	String Os = System.getProperty("os.name");
	String OsVersion = System.getProperty("os.version");
	String OsArch = System.getProperty("os.arch");
	Timer updateUiTimer = new Timer();

	public ZHSIController() throws Exception {

		this.model_instance = new XPlaneModelFactory();
		this.xpd = this.model_instance.getInstance();
		this.nor = NavigationObjectRepository.get_instance();


		updateUiTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					updateStatus();
				});
			}
		}, 1000, 1000);

		this.rs = LoadResources.getInstance();
		this.preferences = ZHSIPreferences.getInstance();

		Handler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL);
		handler.setFormatter(new ZHSILogFormatter());
		handler.setFilter(null);
		logger.addHandler(handler);

		handler = new FileHandler(System.getProperty("user.dir") + "/ZHSI.log");
		handler.setLevel(Level.ALL);
		handler.setFormatter(new ZHSILogFormatter());
		handler.setFilter(null);
		logger.addHandler(handler);

		logger.setLevel(Level.ALL);
		logger.setUseParentHandlers(false);
		

		logger.info("Java version: " + System.getProperty("java.runtime.version") + " detected");
		logger.info("Platform: " + Os + " Version: " + OsVersion + " (" + OsArch + ") - Available Processors: "
				+ java_run.availableProcessors());
		logger.info("Free  Memory: " + (java_run.freeMemory() / 1024 / 1024) + "M");
		logger.info("Total Memory: " + (java_run.totalMemory() / 1024 / 1024) + "M");
		logger.info("Max  Memory: " + (java_run.maxMemory() / 1024 / 1024) + "M");

		ZHSIStatus.status = ZHSIStatus.STATUS_STARTUP;

		logger.config("Selected loglevel: " + this.preferences.get_preference(ZHSIPreferences.PREF_LOGLEVEL));
		logger.setLevel(Level.parse(this.preferences.get_preference(ZHSIPreferences.PREF_LOGLEVEL)));

		//
		iface = ExtPlaneInterface.getInstance();
		
		// run it in a new thread
		ext_iface = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					iface.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		ext_iface.start();

		// weather receiver
		XPlaneDataPacketDecoder packet_decoder = new XPlaneDataPacketDecoder(model_instance);
		XPlaneWeatherReceiver weather_receiver = new XPlaneWeatherReceiver(WEATHER_UDP_PORT, false, null);
		weather_receiver.add_reception_observer(packet_decoder);
		weather_receiver.start();

		File navDataDir = new File("./zhsi_nav_data");
		navDataDir.mkdir();

		long oldAptVersion = Long.parseLong(this.preferences.get_preference(ZHSIPreferences.APTNAV_VERSION));
		
		// X-Plane 11
		String apt_file_location = preferences.get_preference(ZHSIPreferences.PREF_APTNAV_DIR) + "/Custom Scenery/Global Airports/Earth nav data/apt.dat";
		if (new File(apt_file_location).exists()) {
			// do nothing
		} else {
			// X-Plane 12
			apt_file_location = preferences.get_preference(ZHSIPreferences.PREF_APTNAV_DIR) + "/Global Scenery/Global Airports/Earth nav data/apt.dat";
		}
			
		if (new File("./zhsi_nav_data/apt.dat").exists()) {
			apt_file_location = "./zhsi_nav_data/apt.dat";
			logger.info("local apt.dat file found in " + apt_file_location + ", will use that if needed ..");
		}
		
		long currentAptVersion = new File(apt_file_location).lastModified();
		AptFileParser apt = new AptFileParser(apt_file_location);

		if (new File("./zhsi_nav_data/zhsi_apt.dat").exists() && new File("./zhsi_nav_data/zhsi_rwy.dat").exists()) {
			// check if current is newer
			if ((currentAptVersion > oldAptVersion) || (currentAptVersion != oldAptVersion)) {
				logger.info("APT dat file: zhsi_apt.dat, exists, but outdated, trying to generate a new file...");
				logger.info("RWY dat file: zhsi_rwy.dat, exists, but outdated, trying to generate a new file...");
				apt.parseFile();
				this.preferences.set_preference(ZHSIPreferences.APTNAV_VERSION, "" + currentAptVersion);
			} else {
				logger.info("APT dat file: current zhsi_apt.dat up to date, skipping file generation");
				logger.info("RWY dat file: current zhsi_rwy.dat up to date, skipping file generation");
			}
		} else {
			apt.parseFile();
		}

		// navdata builder
		Thread nav_builder = new Thread(new Runnable() {

			@Override
			public void run() {
				AptNavXP900DatNavigationObjectBuilder nob;
				try {
					nob = new AptNavXP900DatNavigationObjectBuilder(
							preferences.get_preference(ZHSIPreferences.PREF_APTNAV_DIR));
					if (!ZHSIStatus.nav_db_status.equals(ZHSIStatus.STATUS_NAV_DB_NOT_FOUND)) {
						nob.read_all_tables();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		nav_builder.start();

		Thread elevation_builder = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					GlobeElevationBuilder geb = new GlobeElevationBuilder(
							preferences.get_preference(ZHSIPreferences.PREF_EGPWS_DB_DIR));
					if (!ZHSIStatus.nav_db_status.equals(ZHSIStatus.STATUS_EGPWS_DB_NOT_FOUND)) {
						geb.map_database();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		elevation_builder.start();

		Thread updateNearestAirport = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
					}
					if (xpd.latitude() != 0.0f || xpd.longitude() != 0.0f) {
						try {
							String nearest_iaco = nor.find_nrst_arpt(xpd.latitude(), xpd.longitude(), 1700f, true);
							if (nearest_iaco != null) {
								Airport airport = nor.get_airport(nearest_iaco);
								if (airport != null) {
									float distance = xpd.rough_distance_to(airport);
									float bearing = CoordinateSystem.get_bearing(xpd.latitude(), xpd.longitude(),
											airport.lat, airport.lon);
									ZHSIStatus.nearest_airport_valid = true;
									ZHSIStatus.nearest_airport_elev = airport.elev;
									ZHSIStatus.nearest_airport_icao = airport.icao_code;
									ZHSIStatus.nearest_airport = airport.name;
									ZHSIStatus.nearest_airport_dist = distance;
									ZHSIStatus.nearest_airport_bearing = (int) bearing;
									// lblNearestAirportValue.setText(airport.icao_code + " : " + airport.name + "
									// (distance : " + String.format("%.2f", distance) + "nm, bearing : " +
									// (int)bearing + " degrees)");
									// lblNearestAirportValue.setText(airport.icao_code + " : " + airport.name + "
									// (distance : " + String.format("%.2f", distance) + "nm)");
								} else {
									ZHSIStatus.nearest_airport_valid = false;
									// lblNearestAirportValue.setTextFill(Color.RED);
									// lblNearestAirportValue.setText("Error: NAV DB Not Loaded");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

		});
		updateNearestAirport.start();

	}

	@FXML
	public void toggleShowFPS() {
		if (showFPS.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_SHOW_FPS, "true");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_SHOW_FPS, "false");
		}
	}

	@FXML
	public void toggleInstrument() {
		
		// ISFD
		if (isfdToggle.isSelected()) {
			if (isfd == null) {
				SwingUtilities.invokeLater(() -> {
					isfd = new Instrument(this.model_instance, "ISFD", "Captain");
				});
			}
			SwingUtilities.invokeLater(() -> {
				isfd.setVisible(true);
			});
			this.preferences.set_preference(ZHSIPreferences.PREF_ISFD_ENABLE, "true");
		} else if (!isfdToggle.isSelected()) {
			if (isfd != null) {
				SwingUtilities.invokeLater(() -> {
					isfd.dispose();
					isfd = null;
				});
			}
			this.preferences.set_preference(ZHSIPreferences.PREF_ISFD_ENABLE, "false");
		}
		
		// Chrono Cpt
		if (cptChronoToggle.isSelected()) {
			if (cptChrono == null) {
				SwingUtilities.invokeLater(() -> {
					cptChrono = new Instrument(this.model_instance, "Captain Chrono", "Captain");
				});
			}
			SwingUtilities.invokeLater(() -> {
				cptChrono.setVisible(true);
			});
			this.preferences.set_preference(ZHSIPreferences.PREF_CPTCHRONO_ENABLE, "true");
		} else if (!cptChronoToggle.isSelected()) {
			if (cptChrono != null) {
				SwingUtilities.invokeLater(() -> {
					cptChrono.dispose();
					cptChrono = null;
				});
			}
			this.preferences.set_preference(ZHSIPreferences.PREF_CPTCHRONO_ENABLE, "false");
		}
		
		// Chrono FO
		if (foChronoToggle.isSelected()) {
			if (foChrono == null) {
				SwingUtilities.invokeLater(() -> {
					foChrono = new Instrument(this.model_instance, "First Officer Chrono", "First Officer");
				});
			}
			SwingUtilities.invokeLater(() -> {
				foChrono.setVisible(true);
			});
			this.preferences.set_preference(ZHSIPreferences.PREF_FOCHRONO_ENABLE, "true");
		} else if (!foChronoToggle.isSelected()) {
			if (foChrono != null) {
				SwingUtilities.invokeLater(() -> {
					foChrono.dispose();
					foChrono = null;
				});
			}
			this.preferences.set_preference(ZHSIPreferences.PREF_FOCHRONO_ENABLE, "false");
		}
		
		// Flap
		if (flapToggle.isSelected()) {
			if (flapGauge == null) {
				SwingUtilities.invokeLater(() -> {
					flapGauge = new FlapGauge(this.model_instance, "Flap Gauge");
				});
			}
			SwingUtilities.invokeLater(() -> {
				flapGauge.setVisible(true);
			});
			this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_ENABLE, "true");
		} else if (!flapToggle.isSelected()) {
			if (flapGauge != null) {
				SwingUtilities.invokeLater(() -> {
					flapGauge.dispose();
					flapGauge = null;
				});
			}
			this.preferences.set_preference(ZHSIPreferences.PREF_FLAP_GAUGE_ENABLE, "false");
		}
		
		// Electrical Panel
		if (elecToggle.isSelected()) {
			if (elecPanel == null) {
				SwingUtilities.invokeLater(() -> {
					elecPanel = new Instrument(model_instance, "Electrical Display", "Captain");
				});
			}
			SwingUtilities.invokeLater(() -> {
				elecPanel.setVisible(true);
			});
			this.preferences.set_preference(ZHSIPreferences.PREF_ELECPANEL_ENABLE, "true");
		} else if (!elecToggle.isSelected()) {
			if (elecPanel != null) {
				SwingUtilities.invokeLater(() -> {
					elecPanel.dispose();
					elecPanel = null;
				});
			}
			this.preferences.set_preference(ZHSIPreferences.PREF_ELECPANEL_ENABLE, "false");
		}
		
		// IRS
		if (irsToggle.isSelected()) {
			if (irs == null) {
				SwingUtilities.invokeLater(() -> {
					irs = new Instrument(model_instance, "IRS Display", "Captain");
				});
			}
			SwingUtilities.invokeLater(() -> {
				irs.setVisible(true);
			});
			this.preferences.set_preference(ZHSIPreferences.PREF_IRSPANEL_ENABLE, "true");
		} else if (!irsToggle.isSelected()) {
			if (irs != null) {
				SwingUtilities.invokeLater(() -> {
					irs.dispose();
					irs = null;
				});
			}
			this.preferences.set_preference(ZHSIPreferences.PREF_IRSPANEL_ENABLE, "false");
		}
		
		// FLT ALT
		if (fltaltToggle.isSelected()) {
			if (fltalt == null) {
				SwingUtilities.invokeLater(() -> {
					fltalt = new Instrument(model_instance, "Flt Alt Display", "Captain");
				});
			}
			SwingUtilities.invokeLater(() -> {
				fltalt.setVisible(true);
			});
			this.preferences.set_preference(ZHSIPreferences.PREF_FLTALTPANEL_ENABLE, "true");
		} else if (!fltaltToggle.isSelected()) {
			if (fltalt != null) {
				SwingUtilities.invokeLater(() -> {
					fltalt.dispose();
					fltalt = null;
				});
			}
			this.preferences.set_preference(ZHSIPreferences.PREF_FLTALTPANEL_ENABLE, "false");
		}
		
		// LAND ALT
		if (landaltToggle.isSelected()) {
			if (landalt == null) {
				SwingUtilities.invokeLater(() -> {
					landalt = new Instrument(model_instance, "Land Alt Display", "Captain");
				});
			}
			SwingUtilities.invokeLater(() -> {
				landalt.setVisible(true);
			});
			this.preferences.set_preference(ZHSIPreferences.PREF_LANDALTPANEL_ENABLE, "true");
		} else if (!landaltToggle.isSelected()) {
			if (landalt != null) {
				SwingUtilities.invokeLater(() -> {
					landalt.dispose();
					landalt = null;
				});
			}
			this.preferences.set_preference(ZHSIPreferences.PREF_LANDALTPANEL_ENABLE, "false");
		}
		
		// RMI
		if (rmiToggle.isSelected()) {
			if (rmi == null) {
				SwingUtilities.invokeLater(() -> {
					rmi = new Instrument(this.model_instance, "RMI", "Captain");
				});
			}
			SwingUtilities.invokeLater(() -> {
				rmi.setVisible(true);
			});
			this.preferences.set_preference(ZHSIPreferences.PREF_RMI_ENABLE, "true");
		} else if (!rmiToggle.isSelected()) {
			if (rmi != null) {
				SwingUtilities.invokeLater(() -> {
					rmi.dispose();
					rmi = null;
				});
			}
			this.preferences.set_preference(ZHSIPreferences.PREF_RMI_ENABLE, "false");
		}
	}
	
	@FXML
	public void buttonAction(Event event) {
		String id = ((Control)event.getSource()).getId();
		switch(id) {
		case "btnDash":
			paneHeading.setText("DashBoard");
			paneSubHeading.setText("ZHSI DashBoard");
			dashPane.setVisible(true);
			duPane.setVisible(false);
			instPane.setVisible(false);
			annunPane.setVisible(false);
			optionsPane.setVisible(false);
			settingsPane.setVisible(false);
			logPane.setVisible(false);
			aboutPane.setVisible(false);
			break;
		case "btnDu" :
			paneHeading.setText("DU Displays");
			paneSubHeading.setText("ZHSI Cockpit Display Units");
			dashPane.setVisible(false);
			duPane.setVisible(true);
			instPane.setVisible(false);
			annunPane.setVisible(false);
			optionsPane.setVisible(false);
			settingsPane.setVisible(false);
			logPane.setVisible(false);
			aboutPane.setVisible(false);
			break;
		case "btnInst" :
			paneHeading.setText("Gauges / Instruments");
			paneSubHeading.setText("ZHSI Gauges and Instruments");
			dashPane.setVisible(false);
			duPane.setVisible(false);
			instPane.setVisible(true);
			annunPane.setVisible(false);
			optionsPane.setVisible(false);
			settingsPane.setVisible(false);
			logPane.setVisible(false);
			aboutPane.setVisible(false);
			break;
		case "btnAnnunciators" :
			paneHeading.setText("Annunciators");
			paneSubHeading.setText("ZHSI Annunciators");
			dashPane.setVisible(false);
			duPane.setVisible(false);
			instPane.setVisible(false);
			annunPane.setVisible(true);
			optionsPane.setVisible(false);
			settingsPane.setVisible(false);
			logPane.setVisible(false);
			aboutPane.setVisible(false);
			readAnnunFile();
			break;
		case "btnOptions" :
			paneHeading.setText("Options");
			paneSubHeading.setText("ZHSI Options");
			dashPane.setVisible(false);
			duPane.setVisible(false);
			instPane.setVisible(false);
			annunPane.setVisible(false);
			optionsPane.setVisible(true);
			settingsPane.setVisible(false);
			logPane.setVisible(false);
			aboutPane.setVisible(false);
			break;
		case "btnSettings" :
			paneHeading.setText("Settings");
			paneSubHeading.setText("ZHSI Settings");
			dashPane.setVisible(false);
			duPane.setVisible(false);
			instPane.setVisible(false);
			annunPane.setVisible(false);
			optionsPane.setVisible(false);
			settingsPane.setVisible(true);
			logPane.setVisible(false);
			aboutPane.setVisible(false);
			break;
		case "btnLog" :
			readLogFile();
			paneHeading.setText("Log");
			paneSubHeading.setText("ZHSI Log file");
			dashPane.setVisible(false);
			duPane.setVisible(false);
			instPane.setVisible(false);
			annunPane.setVisible(false);
			optionsPane.setVisible(false);
			settingsPane.setVisible(false);
			logPane.setVisible(true);
			aboutPane.setVisible(false);
			break;
		case "btnAbout" :
			paneHeading.setText("About");
			paneSubHeading.setText("ZHSI About");
			dashPane.setVisible(false);
			duPane.setVisible(false);
			instPane.setVisible(false);
			annunPane.setVisible(false);
			optionsPane.setVisible(false);
			settingsPane.setVisible(false);
			logPane.setVisible(false);
			aboutPane.setVisible(true);
			break;
		case "btnLogRefresh" :
			readLogFile();
			break;
		case "btnAnnunRefresh" :
			readAnnunFile();
			break;
		case "btnAnnunSave" :
			if(annunicatorWindow == null) {
				annunicatorWindow = AnnunciatorWindow.getInstance();
				annunicatorWindow.updateAnnunciatorFile();
			}else {
				annunicatorWindow.updateAnnunciatorFile();
			}
			saveAnnunFile();
			break;
		}
		
	}
	
	@FXML
	public void ledMouseEntered(Event event){
		String id = ((Circle)event.getSource()).getId();
		switch(id) {
		case "roundLedXPStatus" :
			statusBarText.setText("X-Plane Connection Status");
			break;
		case "roundLedZiboStatus" :
			statusBarText.setText("Zibo Status");
			break;
		case "roundLedNavStatus" :
			statusBarText.setText("Nav DB Status");
			break;
		case "roundLedTerrainStatus" :
			statusBarText.setText("Terrain GLOBE Status");
			break;
		case "roundLedWeatherStatus" :
			statusBarText.setText("Weather Status");
			break;
		}
	}
	
	@FXML
	public void ledMouseExited(Event event){
		statusBarText.setText("");
	}
	
	@FXML
	public void toggleDu(Event event) {
		
		String id = ((Control)event.getSource()).getId();
		switch(id) {
		case "cptOutBdToggle":
			if(cptOutBdToggle.isSelected()) {
				if (cptOutBd == null) {
					SwingUtilities.invokeLater(() -> {
						cptOutBd = new DisplayUnit(this.model_instance, CPT_OUTBD_TITLE);
					});
				}
				SwingUtilities.invokeLater(() -> {
					cptOutBd.setVisible(true);
					startHeartbeat(cptoutbd_heartbeat, cptOutBd);
				});
				this.preferences.set_preference(ZHSIPreferences.PREF_CPT_OUTBD_ENABLE, "true");
			}else {
				SwingUtilities.invokeLater(() -> {
					disableDisplay(cptOutBd, cptoutbd_heartbeat);
				});	
				ZHSIStatus.cptoutdb_running = false;
				this.preferences.set_preference(ZHSIPreferences.PREF_CPT_OUTBD_ENABLE, "false");
			}
			break;
		case "cptInBdToggle":
			if (cptInBdToggle.isSelected()) {
				if (cptInBd == null) {
					SwingUtilities.invokeLater(() -> {
						cptInBd = new DisplayUnit(this.model_instance, CPT_INBD_TITLE);
					});
				}
				SwingUtilities.invokeLater(() -> {
					cptInBd.setVisible(true);
					startHeartbeat(cptinbd_heartbeat, cptInBd);
				});
				this.preferences.set_preference(ZHSIPreferences.PREF_CPT_INBD_ENABLE, "true");
			} else {
				SwingUtilities.invokeLater(() -> {
					disableDisplay(cptInBd, cptinbd_heartbeat);
				});
				ZHSIStatus.cptindb_running = false;
				this.preferences.set_preference(ZHSIPreferences.PREF_CPT_INBD_ENABLE, "false");
			}
			break;
		case "foOutBdToggle":
			if (foOutBdToggle.isSelected()) {
				if (foOutBd == null) {
					SwingUtilities.invokeLater(() -> {
						foOutBd = new DisplayUnit(this.model_instance, FO_OUTBD_TITLE);
					});
				}
				SwingUtilities.invokeLater(() -> {
					foOutBd.setVisible(true);
					startHeartbeat(fooutbd_heartbeat, foOutBd);
				});
				this.preferences.set_preference(ZHSIPreferences.PREF_FO_OUTBD_ENABLE, "true");
			} else {
				SwingUtilities.invokeLater(() -> {
					disableDisplay(foOutBd, fooutbd_heartbeat);
				});
				ZHSIStatus.fooutdb_running = false;
				this.preferences.set_preference(ZHSIPreferences.PREF_FO_OUTBD_ENABLE, "false");
			}
			break;
		case "foInBdToggle":
			if (foInBdToggle.isSelected()) {
				if (foInBd == null) {
					SwingUtilities.invokeLater(() -> {
						foInBd = new DisplayUnit(this.model_instance, FO_INBD_TITLE);
					});
				}
				SwingUtilities.invokeLater(() -> {
					foInBd.setVisible(true);
					startHeartbeat(foinbd_heartbeat, foInBd);
				});
				this.preferences.set_preference(ZHSIPreferences.PREF_FO_INBD_ENABLE, "true");
			} else {
				SwingUtilities.invokeLater(() -> {
					disableDisplay(foInBd, foinbd_heartbeat);
				});
				ZHSIStatus.foindb_running = false;
				this.preferences.set_preference(ZHSIPreferences.PREF_FO_INBD_ENABLE, "false");
			}
			break;
		case "upperEicasToggle":
			if (upperEicasToggle.isSelected()) {
				if (upperDu == null) {
					SwingUtilities.invokeLater(() -> {
						upperDu = new DisplayUnit(this.model_instance, UPPER_DU_TITLE);
					});
				}
				SwingUtilities.invokeLater(() -> {
					upperDu.setVisible(true);
					startHeartbeat(uppereicas_heartbeat, upperDu);
				});
				this.preferences.set_preference(ZHSIPreferences.PREF_UP_EICAS_ENABLE, "true");
			} else {
				SwingUtilities.invokeLater(() -> {
					disableDisplay(upperDu, uppereicas_heartbeat);
				});
				this.preferences.set_preference(ZHSIPreferences.PREF_UP_EICAS_ENABLE, "false");
			}
			break;
		case "lowerEicasToggle":
			if (lowerEicasToggle.isSelected()) {
				if (lowerDu == null) {
					SwingUtilities.invokeLater(() -> {
						lowerDu = new DisplayUnit(this.model_instance, LOWER_DU_TITLE);
					});
				}
				SwingUtilities.invokeLater(() -> {
					lowerDu.setVisible(true);
					startHeartbeat(lowereicas_heartbeat, lowerDu);
				});
				this.preferences.set_preference(ZHSIPreferences.PREF_LO_EICAS_ENABLE, "true");
			} else {
				SwingUtilities.invokeLater(() -> {
					disableDisplay(lowerDu, lowereicas_heartbeat);
				});
				this.preferences.set_preference(ZHSIPreferences.PREF_LO_EICAS_ENABLE, "false");
			}
			break;
		}	
	}
	
	@FXML
	public void browseXP() {
		DirectoryChooser xpDirChooser = new DirectoryChooser();
		xpDirChooser.setTitle("Select your X-Plane Directory");
		File xpDir = xpDirChooser.showDialog(stage);
		if (xpDir == null) {
			// No Directory selected
		} else {
			File xpwin = new File(xpDir.getAbsolutePath() + "/X-Plane.exe");
			File xpmac = new File(xpDir.getAbsolutePath() + "/X-Plane.app");
			File xplin = new File(xpDir.getAbsolutePath() + "/X-Plane-x86_64");
			if ((xpwin.exists() || xpmac.exists() || xplin.exists())) {
				this.preferences.set_preference(ZHSIPreferences.PREF_APTNAV_DIR, xpDir.getAbsolutePath());
				txtXPDir.setText(xpDir.getAbsolutePath());
			} else {
				this.preferences.set_preference(ZHSIPreferences.PREF_APTNAV_DIR, "null");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid X-Plane Directory");
				alert.setHeaderText("X-Plane Directory is not valid!");
				alert.setContentText("Please choose the correct X-Plane Directory");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						txtXPDir.clear();
					}
				});
			}

		}
	}

	@FXML
	public void browseEgpws() {
		DirectoryChooser egpwsDirChooser = new DirectoryChooser();
		egpwsDirChooser.setTitle("Select the GLOBE tiles Directory");
		File egpwsDir = egpwsDirChooser.showDialog(stage);
		if (egpwsDir == null) {
			// No Directory selected
		} else {
			File[] listFiles = egpwsDir.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isFile()) {
					String tempFile = listFiles[i].getName();
					if (tempFile.endsWith("10g")) {
						EGPWSDirOK = true;
					} else {
						EGPWSDirOK = false;
					}
				}
			}
			if (egpwsDir.exists() && EGPWSDirOK) {
				this.preferences.set_preference(ZHSIPreferences.PREF_EGPWS_DB_DIR, egpwsDir.getAbsolutePath());
				txtEGPWSDir.setText(egpwsDir.getAbsolutePath());
			} else {
				this.preferences.set_preference(ZHSIPreferences.PREF_EGPWS_DB_DIR, "null");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid GLOBE tiles Directory");
				alert.setHeaderText("GLOBE tiles Directory is not valid!");
				alert.setContentText("Please choose the correct GLOBE tiles Directory");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						txtEGPWSDir.clear();
					}
				});
			}

		}
	}

	@FXML
	public void applyIPAddress() {
		if (validIP(txtXPIp.getText())) {
			this.preferences.set_preference(ZHSIPreferences.PREF_EXTPLANE_SERVER, txtXPIp.getText());
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Invalid IP Address");
			alert.setHeaderText("IP Address (" + txtXPIp.getText() + ") not valid");
			alert.setContentText("Please enter a correct IP Address");
			alert.showAndWait().ifPresent(rs -> {
				if (rs == ButtonType.OK) {
					txtXPIp.clear();
				}
			});
		}
	}

	private void disableDisplay(DisplayUnit display, DUHeartbeat heartbeat) {
		if (display != null) {
			display.dispose();
			display = null;
		}
		if(heartbeat != null) {
			heartbeat.setKeep_running(false);
			heartbeat = null;
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void updateStatus() {

		//this.lblFreeMem.setText((java_run.freeMemory() / 1024 / 1024) + "M");
		//this.lblAllocMem.setText((java_run.totalMemory() / 1024 / 1024) + "M");
		if((java_run.maxMemory() / 1024 / 1024) > 1216) {
			this.lblMaxMem.setTextFill(Color.LIME);
		}else {
			this.lblMaxMem.setTextFill(Color.RED);
		}
		this.lblMaxMem.setText((java_run.maxMemory() / 1024 / 1024) + "M");
		//this.lblTotalFreeMem.setText((java_run.freeMemory() + (java_run.maxMemory() - java_run.totalMemory())) / 1024 / 1024 + "M");

		if (ZHSIStatus.receiving) {
			this.ledXPStatus.setFill(Color.LIME);
			this.roundLedXPStatus.setFill(Color.LIME);
			//this.xplaneStatus.setText("X-Plane Connected");
			this.lblXplaneStatusBox.setText("Successfully connected to the ZHSI plugin (IP Address :"
					+ this.preferences.get_preference(ZHSIPreferences.PREF_EXTPLANE_SERVER) + " port 52000)");
			if (ZHSIStatus.nearest_airport_valid) {
				this.lblNearestAirportValue.setText(ZHSIStatus.nearest_airport_icao + " : " + ZHSIStatus.nearest_airport
						+ " (distance : " + String.format("%.2f", ZHSIStatus.nearest_airport_dist) + "nm)");
			} else {
				this.lblNearestAirportValue.setText("trying to find nearest airport ...");
			}
			
		} else {
			this.ledXPStatus.setFill(Color.RED);
			this.roundLedXPStatus.setFill(Color.RED);
			this.lblNearestAirportValue.setText("Error: X-Plane not connected");
		}
		if (ZHSIStatus.zibo_loaded) {
			this.ledZiboStatus.setFill(Color.LIME);
			this.roundLedZiboStatus.setFill(Color.LIME);
			this.lblZiboStatusBox.setText("Zibo Loaded");
		} else {
			this.ledZiboStatus.setFill(Color.RED);
			this.roundLedZiboStatus.setFill(Color.RED);
			this.lblZiboStatusBox.setText("Zibo not loaded");
		}
		if (ZHSIStatus.nav_db_status == ZHSIStatus.STATUS_NAV_DB_LOADED) {
			this.ledNavStatus.setFill(Color.LIME);
			this.roundLedNavStatus.setFill(Color.LIME);
			this.lblNavStatusBox.setText("Nav Database has been loaded. AIRAC cycle detected : " + ZHSIStatus.nav_db_cycle);
		} else {
			this.ledNavStatus.setFill(Color.RED);
			this.roundLedNavStatus.setFill(Color.RED);
			this.lblNavStatusBox.setText("Nav DB not loaded");
		}
		if (ZHSIStatus.weather_receiving) {
			this.ledWeatherStatus.setFill(Color.LIME);
			this.roundLedWeatherStatus.setFill(Color.LIME);
			this.lblWeatherStatusBox.setText("Receiving weather data from X-Plane on UDP port " + this.WEATHER_UDP_PORT);
		} else {
			this.ledWeatherStatus.setFill(Color.RED);
			this.roundLedWeatherStatus.setFill(Color.RED);
			this.lblWeatherStatusBox.setText("Not receiving weather");
		}
		if (ZHSIStatus.egpws_db_status == ZHSIStatus.STATUS_EGPWS_DB_LOADED) {
			this.ledTerrainStatus.setFill(Color.LIME);
			this.roundLedTerrainStatus.setFill(Color.LIME);
			this.lblTerrainStatusBox.setText("All Terrain GLOBE tiles loaded");

		} else if (ZHSIStatus.egpws_db_status == ZHSIStatus.STATUS_EGPWS_PART_LOADED) {
			this.ledTerrainStatus.setFill(Color.DARKORANGE);
			this.roundLedTerrainStatus.setFill(Color.DARKORANGE);
			this.lblTerrainStatusBox.setText("Terrain GLOBE tiles partially loaded");
			
		} else {
			this.ledTerrainStatus.setFill(Color.RED);
			this.roundLedTerrainStatus.setFill(Color.RED);
			this.lblTerrainStatusBox.setText("Terrain GLOBE tiles not loaded");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		paneHeading.setText("DashBoard");
		paneSubHeading.setText("ZHSI DashBoard");
		dashPane.setVisible(true);
		duPane.setVisible(false);
		instPane.setVisible(false);
		annunPane.setVisible(false);
		optionsPane.setVisible(false);
		settingsPane.setVisible(false);
		logPane.setVisible(false);
		aboutPane.setVisible(false);


		
		File zhsi_annun = new File(ANNUN_FILE);
		
		try {
			if(zhsi_annun.createNewFile()) {
				FileWriter writer = new FileWriter(zhsi_annun);
				writer.write("x,y,width,height,toptext,bottomtext,textsize,toptext y,bottomtext y,dataref,value,color");
				writer.close();
				logger.fine("ZHSI.annunciators file created");
			}else {
				logger.fine("ZHSI.annunciators already exists");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dcVoltsXslider.valueProperty().addListener((obs, oldval, newVal) -> {
			this.preferences.set_preference(ZHSIPreferences.PREF_DCVOLTS_X, "" + (int) dcVoltsXslider.getValue());
		});
		dcAmpsXslider.valueProperty().addListener((obs, oldval, newVal) -> {
			this.preferences.set_preference(ZHSIPreferences.PREF_DCAMPS_X, "" + (int) dcAmpsXslider.getValue());
		});
		acVoltsXslider.valueProperty().addListener((obs, oldval, newVal) -> {
			this.preferences.set_preference(ZHSIPreferences.PREF_ACVOLTS_X, "" + (int) acVoltsXslider.getValue());
		});
		acAmpsXslider.valueProperty().addListener((obs, oldval, newVal) -> {
			this.preferences.set_preference(ZHSIPreferences.PREF_ACAMPS_X, "" + (int) acAmpsXslider.getValue());
		});
		cpsFreqXslider.valueProperty().addListener((obs, oldval, newVal) -> {
			this.preferences.set_preference(ZHSIPreferences.PREF_CPSFREQ_X, "" + (int) cpsFreqXslider.getValue());
		});
		dcVoltsXslider.setValue(Double.parseDouble(this.preferences.get_preference(ZHSIPreferences.PREF_DCVOLTS_X)));
		dcAmpsXslider.setValue(Double.parseDouble(this.preferences.get_preference(ZHSIPreferences.PREF_DCAMPS_X)));
		acVoltsXslider.setValue(Double.parseDouble(this.preferences.get_preference(ZHSIPreferences.PREF_ACVOLTS_X)));
		acAmpsXslider.setValue(Double.parseDouble(this.preferences.get_preference(ZHSIPreferences.PREF_ACAMPS_X)));
		cpsFreqXslider.setValue(Double.parseDouble(this.preferences.get_preference(ZHSIPreferences.PREF_CPSFREQ_X)));

		// Captain
		if (this.preferences.get_preference(ZHSIPreferences.PREF_CPT_OUTBD_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				cptOutBd = new DisplayUnit(this.model_instance, CPT_OUTBD_TITLE);			
				cptOutBd.setVisible(true);
				startHeartbeat(cptoutbd_heartbeat, cptOutBd);
			});
			cptOutBdToggle.setSelected(true);
		} else {
			cptOutBdToggle.setSelected(false);
			ZHSIStatus.cptoutdb_running = false;
			disableDisplay(cptOutBd, cptoutbd_heartbeat);
		}
		if (this.preferences.get_preference(ZHSIPreferences.PREF_CPT_INBD_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				cptInBd = new DisplayUnit(this.model_instance, CPT_INBD_TITLE);
				cptInBd.setVisible(true);
				startHeartbeat(cptinbd_heartbeat, cptInBd);
			});
			cptInBdToggle.setSelected(true);
		} else {
			cptInBdToggle.setSelected(false);
			ZHSIStatus.cptindb_running = false;
			disableDisplay(cptInBd, cptinbd_heartbeat);
		}
		
		// Fist Officer
		if (this.preferences.get_preference(ZHSIPreferences.PREF_FO_OUTBD_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				foOutBd = new DisplayUnit(this.model_instance, FO_OUTBD_TITLE);			
				foOutBd.setVisible(true);
				startHeartbeat(fooutbd_heartbeat, foOutBd);
			});
			foOutBdToggle.setSelected(true);
		} else {
			foOutBdToggle.setSelected(false);
			ZHSIStatus.fooutdb_running = false;
			disableDisplay(foOutBd, fooutbd_heartbeat);
		}
		if (this.preferences.get_preference(ZHSIPreferences.PREF_FO_INBD_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				foInBd = new DisplayUnit(this.model_instance, FO_INBD_TITLE);				
				foInBd.setVisible(true);
				startHeartbeat(foinbd_heartbeat, foInBd);
			});
			foInBdToggle.setSelected(true);
		} else {
			foInBdToggle.setSelected(false);
			ZHSIStatus.foindb_running = false;
			disableDisplay(foInBd, foinbd_heartbeat);
		}
		
		// Upper EICAS
		if (this.preferences.get_preference(ZHSIPreferences.PREF_UP_EICAS_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				upperDu = new DisplayUnit(this.model_instance, UPPER_DU_TITLE);
				upperDu.setVisible(true);
				startHeartbeat(uppereicas_heartbeat, upperDu);
			});
			upperEicasToggle.setSelected(true);
		} else {
			upperEicasToggle.setSelected(false);
			disableDisplay(upperDu, uppereicas_heartbeat);
		}
		
		// Lower EICAS
		if (this.preferences.get_preference(ZHSIPreferences.PREF_LO_EICAS_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				lowerDu = new DisplayUnit(this.model_instance, LOWER_DU_TITLE);		
				lowerDu.setVisible(true);
				startHeartbeat(lowereicas_heartbeat, lowerDu);
			});
			lowerEicasToggle.setSelected(true);
		} else {
			lowerEicasToggle.setSelected(false);
			disableDisplay(lowerDu, lowereicas_heartbeat);
		}
		
		// ISFD
		if (this.preferences.get_preference(ZHSIPreferences.PREF_ISFD_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				isfd = new Instrument(this.model_instance, "ISFD", "Captain");
				isfd.setVisible(true);
			});
			isfdToggle.setSelected(true);
		} else {
			isfdToggle.setSelected(false);
			if (isfd != null) {
				isfd.dispose();
				isfd = null;
			}
		}
		
		// RMI
		if (this.preferences.get_preference(ZHSIPreferences.PREF_RMI_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				rmi = new Instrument(this.model_instance, "RMI", "Captain");
				rmi.setVisible(true);
			});
			this.rmiToggle.setSelected(true);
		} else {
			rmiToggle.setSelected(false);
			if (rmi != null) {
				rmi.dispose();
				rmi = null;
			}
		}

		// Chrono
		if (this.preferences.get_preference(ZHSIPreferences.PREF_CPTCHRONO_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				cptChrono = new Instrument(this.model_instance, "Captain Chrono", "Captain");
				cptChrono.setVisible(true);
			});
			cptChronoToggle.setSelected(true);
		} else {
			cptChronoToggle.setSelected(false);
			if (cptChrono != null) {
				cptChrono.dispose();
				cptChrono = null;
			}
		}
		
		if (this.preferences.get_preference(ZHSIPreferences.PREF_FOCHRONO_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				foChrono = new Instrument(this.model_instance, "First Officer Chrono", "First Officer");
				foChrono.setVisible(true);
			});
			foChronoToggle.setSelected(true);
		} else {
			foChronoToggle.setSelected(false);
			if (foChrono != null) {
				foChrono.dispose();
				foChrono = null;
			}
		}
		
		// Electrical Panel
		if (this.preferences.get_preference(ZHSIPreferences.PREF_ELECPANEL_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				elecPanel = new Instrument(model_instance, "Electrical Display", "Captain");	
				elecPanel.setVisible(true);
			});
			elecToggle.setSelected(true);
		} else {
			elecToggle.setSelected(false);
			if (elecPanel != null) {
				elecPanel.dispose();
				elecPanel = null;
			}
		}
		
		// IRS
		if (this.preferences.get_preference(ZHSIPreferences.PREF_IRSPANEL_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				irs = new Instrument(model_instance, "IRS Display", "Captain");		
				irs.setVisible(true);
			});
			irsToggle.setSelected(true);
		} else {
			irsToggle.setSelected(false);
			if (irs != null) {
				irs.dispose();
				irs = null;
			}
		}
		
		// FLT ALT
		if (this.preferences.get_preference(ZHSIPreferences.PREF_FLTALTPANEL_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				fltalt = new Instrument(model_instance, "Flt Alt Display", "Captain");		
				fltalt.setVisible(true);
			});
			fltaltToggle.setSelected(true);
		} else {
			fltaltToggle.setSelected(false);
			if (fltalt != null) {
				fltalt.dispose();
				fltalt = null;
			}
		}
		
		// LAND ALT
		if (this.preferences.get_preference(ZHSIPreferences.PREF_LANDALTPANEL_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				landalt = new Instrument(model_instance, "Land Alt Display", "Captain");		
				landalt.setVisible(true);
			});
			landaltToggle.setSelected(true);
		} else {
			landaltToggle.setSelected(false);
			if (landalt != null) {
				landalt.dispose();
				landalt = null;
			}
		}
		
		// Flap Gauge
		if (this.preferences.get_preference(ZHSIPreferences.PREF_FLAP_GAUGE_ENABLE).equals("true")) {
			SwingUtilities.invokeLater(() -> {
				flapGauge = new FlapGauge(this.model_instance, "Flap Gauge");			
				flapGauge.setVisible(true);
			});
			flapToggle.setSelected(true);
		} else {
			flapToggle.setSelected(false);
			if (flapGauge != null) {
				flapGauge.dispose();
				flapGauge = null;
			}
		}
		
		//
		if (this.preferences.get_preference(ZHSIPreferences.PREF_SHOW_FPS).equals("true")) {
			showFPS.setSelected(true);
		} else {
			showFPS.setSelected(false);
		}
		if (!this.preferences.get_preference(ZHSIPreferences.PREF_APTNAV_DIR).equals("null")) {
			txtXPDir.setText(this.preferences.get_preference(ZHSIPreferences.PREF_APTNAV_DIR));
		}
		if (!this.preferences.get_preference(ZHSIPreferences.PREF_EGPWS_DB_DIR).equals("null")) {
			txtEGPWSDir.setText(this.preferences.get_preference(ZHSIPreferences.PREF_EGPWS_DB_DIR));
		}
		this.txtXPIp.setText(this.preferences.get_preference(ZHSIPreferences.PREF_EXTPLANE_SERVER));

		// Options page
		if (this.preferences.get_preference(ZHSIPreferences.PREF_START_MINIMIZED).equals("true"))
			this.startUIMinimized.setSelected(true);
		if (this.preferences.get_preference(ZHSIPreferences.PREF_ALWAYSONTOP).equals("true"))
			this.duAlwaysOnTop.setSelected(true);
		if (this.preferences.get_preference(ZHSIPreferences.PREF_FUEL_LBS).equals("true")) {
			this.fuelInKgs.setSelected(false);
		} else {
			this.fuelInKgs.setSelected(true);
		}
		if (this.preferences.get_preference(ZHSIPreferences.PREF_FUEL_OVER_UNDER).equals("true")) {
			this.fuelDisplay.setSelected(true);
		} else {
			this.fuelDisplay.setSelected(false);
		}
		if (this.preferences.get_preference(ZHSIPreferences.PREF_HARDWARE_LEVERS).equals("true")) {
			this.hardwareThrottle.setSelected(true);
		} else {
			this.hardwareThrottle.setSelected(false);
		}
		if (this.preferences.get_preference(ZHSIPreferences.PREF_TRIM_INDICATOR).equals("true")) {
			this.stabTrim.setSelected(true);
		} else {
			this.stabTrim.setSelected(false);
		}
		if (this.preferences.get_preference(ZHSIPreferences.PREF_RUDDER_INDICATOR).equals("true")) {
			this.rudderTrim.setSelected(true);
		} else {
			this.rudderTrim.setSelected(false);
		}
//		if (this.preferences.get_preference(ZHSIPreferences.PREF_COMPACT_DISPLAY).equals("true")) {
//			this.compactEngine.setSelected(true);
//		} else {
//			this.compactEngine.setSelected(false);
//		}
		if (this.preferences.get_preference(ZHSIPreferences.PREF_XRAAS_ENABLE).equals("true"))
			this.enableXraas.setSelected(true);
		
		if (this.preferences.get_preference(ZHSIPreferences.PREF_AOA_INDICATOR).equals("true")) {
			this.roundAltimeter.setSelected(false);
		} else {
			this.roundAltimeter.setSelected(true);
		}
		
		if (this.preferences.get_preference(ZHSIPreferences.PREF_ANNUNWINDOW_ENABLE).equals("true")) {
			this.annunWindow.setSelected(true);
			if(annunicatorWindow == null) {
				annunicatorWindow = AnnunciatorWindow.getInstance();
			}
			annunicatorWindow.setVisible(true);
		} else {
			this.annunWindow.setSelected(false);
			if (annunicatorWindow != null) {
				annunicatorWindow.setVisible(false);
				annunicatorWindow.dispose();
				annunicatorWindow = null;
			}
		}
		
		

	}
	
	private void startHeartbeat(DUHeartbeat heartbeat, DisplayUnit du) {
		if(heartbeat == null) {
			heartbeat = new DUHeartbeat(du, 500);
		}
		heartbeat.start();
	}

	/*
	@FXML
	public void changeColor() {
		this.preferences.set_preference(ZHSIPreferences.INST_GRAY_COLOR, this.inst_gray_color.getValue().toString());
		this.preferences.set_preference(ZHSIPreferences.INST_WHITE_COLOR, this.inst_white_color.getValue().toString());
		this.preferences.set_preference(ZHSIPreferences.INST_HOR_SKY_COLOR, this.inst_horizon_sky_color.getValue().toString());
		this.preferences.set_preference(ZHSIPreferences.INST_HOR_GRD_COLOR, this.inst_horizon_ground_color.getValue().toString());
		this.preferences.set_preference(ZHSIPreferences.INST_MAGENTA_COLOR, this.inst_magenta_color.getValue().toString());
		this.preferences.set_preference(ZHSIPreferences.INST_LIME_COLOR, this.inst_lime_color.getValue().toString());
		this.preferences.set_preference(ZHSIPreferences.INST_RED_COLOR, this.inst_red_color.getValue().toString());
		this.preferences.set_preference(ZHSIPreferences.INST_AMBER_COLOR, this.inst_amber_color.getValue().toString());
		this.preferences.set_preference(ZHSIPreferences.INST_CYAN_COLOR, this.inst_cyan_color.getValue().toString());

	}
	*/

	@FXML
	public void updateOptions() {
		if (startUIMinimized.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_START_MINIMIZED, "true");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_START_MINIMIZED, "false");
		}
		if (duAlwaysOnTop.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_ALWAYSONTOP, "true");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_ALWAYSONTOP, "false");
		}
		if (fuelInKgs.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_FUEL_LBS, "false");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_FUEL_LBS, "true");
		}
		if (fuelDisplay.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_FUEL_OVER_UNDER, "true");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_FUEL_OVER_UNDER, "false");
		}
		if (hardwareThrottle.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_HARDWARE_LEVERS, "true");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_HARDWARE_LEVERS, "false");
		}
		if (stabTrim.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_TRIM_INDICATOR, "true");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_TRIM_INDICATOR, "false");
		}
		if (rudderTrim.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_RUDDER_INDICATOR, "true");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_RUDDER_INDICATOR, "false");
		}
//		if (compactEngine.isSelected()) {
//			this.preferences.set_preference(ZHSIPreferences.PREF_COMPACT_DISPLAY, "true");
//		} else {
//			this.preferences.set_preference(ZHSIPreferences.PREF_COMPACT_DISPLAY, "false");
//		}
		if (enableXraas.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_XRAAS_ENABLE, "true");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_XRAAS_ENABLE, "false");
		}
		if (roundAltimeter.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_AOA_INDICATOR, "false");
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_AOA_INDICATOR, "true");
		}
		if (annunWindow.isSelected()) {
			this.preferences.set_preference(ZHSIPreferences.PREF_ANNUNWINDOW_ENABLE, "true");
			if(annunicatorWindow == null) {
				annunicatorWindow = AnnunciatorWindow.getInstance();
			}
			annunicatorWindow.setVisible(true);
		} else {
			this.preferences.set_preference(ZHSIPreferences.PREF_ANNUNWINDOW_ENABLE, "false");
			if (annunicatorWindow != null) {
				annunicatorWindow.setVisible(false);
				annunicatorWindow.dispose();
				annunicatorWindow = null;
			}
		}
	}

	public void setVersion(String version) {
		this.version = version;
		this.lblVersion.setText("v" + this.version);
		txtAreaAbout.setText("ZHSI (Zibo737 Glass Cockpit Suite)\n"
				+ "version " + this.version + "\n\n"
				+ "Official Release Repo:\n https://gitlab.com/sum1els737/zhsi-releases\n\n"
				+ "Known Issues:\n https://gitlab.com/sum1els737/zhsi-releases/issues\n\n"
				+ "ChangeLog:\n https://gitlab.com/sum1els737/zhsi-releases/raw/master/CHANGELOG\n\n"
				+ "Wiki:\n https://gitlab.com/sum1els737/zhsi-releases/wikis/ZHSI\n\n"
				+ "Facebook Group:\n https://www.facebook.com/groups/554691138327236/\n\n"
				+ "\n\n"
				+ "THIS SOFTWARE IS PROVIDED BY THE AUTHOR \"AS IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.");

	}

	private void readLogFile() {
		try {
			FileReader reader = new FileReader(LOG_FILE);
			BufferedReader br = new BufferedReader(reader);
			String str;
			
			this.logTextArea.clear();
			while((str = br.readLine()) != null) {
				this.logTextArea.appendText(str + "\n");
			}
			
			reader.close();
			reader = null;
			br.close();
			br = null;
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		this.logTextArea.selectPositionCaret(this.logTextArea.getLength());
	}
	
	private void readAnnunFile() {
		try {
			FileReader reader = new FileReader(ANNUN_FILE);
			BufferedReader br = new BufferedReader(reader);
			String str;
			
			this.annunTextArea.clear();
			while((str = br.readLine()) != null) {
				this.annunTextArea.appendText(str + "\n");
			}
			
			reader.close();
			reader = null;
			br.close();
			br = null;
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		//this.annunTextArea.selectPositionCaret(this.annunTextArea.getLength());
	}
	
	private void saveAnnunFile() {
		try {
			FileWriter writer = new FileWriter(ANNUN_FILE);

			writer.write(this.annunTextArea.getText());
			writer.close();
			writer = null;
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean validIP(String ip) {
		try {
			if (ip == null || ip.isEmpty()) {
				return false;
			}

			String[] parts = ip.split("\\.");
			if (parts.length != 4) {
				return false;
			}

			for (String s : parts) {
				int i = Integer.parseInt(s);
				if ((i < 0) || (i > 255)) {
					return false;
				}
			}
			if (ip.endsWith(".")) {
				return false;
			}
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private boolean isMac() {
		// return (System.getProperty("mrj.version") != null);
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("mac") >= 0);
	}

	@FXML
	public void appClose() {
		disconnect();
		Platform.exit();
		System.exit(0);
	}
	
	public void disconnect() {

		if(iface != null) {
			logger.info("Disconnecting ...");
			iface.sendMessage(new ExtPlaneCommand(ExtPlaneCommand.EXTPLANE_SETTING.DISCONNECT));
		}
	}

}
