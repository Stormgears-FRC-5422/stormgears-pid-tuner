package org.stormgears.pid_tuner;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.stormgears.utils.SmartDashboardUtils;
import org.stormgears.utils.StormTalon;

public class PidTuner {
	// Keys
	private static final String TALON_ID_KEY = "Talon ID";
	private static final String kP_KEY = "kP";
	private static final String kI_KEY = "kI";
	private static final String kD_KEY = "kD";
	private static final String kF_KEY = "kF";
	private static final String kIZONE_KEY = "kIzone";
	private static final String SPEED_KEY = "Speed";
	private static final String INFO_KEY = "Info";
	
	// Error value
	private static final int ERROR = Integer.MAX_VALUE;
	
	// Values
	private int talonId;
	private double p, i, d, f;
	private int izone;
	private int speed;
	
	// Talon Stuff
	private StormTalon talon;
	private boolean running = false;
	private static final int DEFAULT_SPEED = 6300;
	private static final int TALON_FPID_TIMEOUT = 0;

	public PidTuner() {
		setupInterface();
	}

	private void setupInterface() {
		SmartDashboard.putNumber(TALON_ID_KEY, 0);
		SmartDashboard.putNumber(kP_KEY, 0);
		SmartDashboard.putNumber(kI_KEY, 0);
		SmartDashboard.putNumber(kD_KEY, 0);
		SmartDashboard.putNumber(kF_KEY, 0);
		SmartDashboard.putNumber(kIZONE_KEY, 0);
		SmartDashboard.putNumber(SPEED_KEY, 0);
		SmartDashboard.putString(INFO_KEY, "");

		SmartDashboardUtils.putButton("Start Motor", () -> {
			info("Starting motor!");

			if (!running) {
				readNumbers();
				
				if (talonId == ERROR || p == ERROR || i == ERROR || d == ERROR || f == ERROR || izone == ERROR) {
					info("Please fill out ID, p, i, d, f, and izone text fields!");
				} else {
					talon = new StormTalon(talonId);

					if (speed == DEFAULT_SPEED) {
						info("Using default speed of " + DEFAULT_SPEED);
					}

					talon.setInverted(true);
					talon.setSensorPhase(true);
					talon.config_kF(0, f, TALON_FPID_TIMEOUT);
					talon.config_kP(0, p, TALON_FPID_TIMEOUT);
					talon.config_kI(0, i, TALON_FPID_TIMEOUT);
					talon.config_kD(0, d, TALON_FPID_TIMEOUT);
					talon.config_IntegralZone(0, izone, TALON_FPID_TIMEOUT);

					talon.set(ControlMode.Velocity, speed);
					running = true;
				}
			} else {
				info("Cannot start! Motor already running!");
			}
		});

		SmartDashboardUtils.putButton("Stop Motor", () -> {
			if (talon != null) {
				talon.set(ControlMode.Velocity, 0);
			}
			running = false;
		});
	}

	public void logData() {
		if (talon != null) {
			SmartDashboard.putNumber("Encoder Velocity", talon.getSensorCollection().getQuadratureVelocity());
			SmartDashboard.putNumber("Encoder Position", talon.getSensorCollection().getQuadraturePosition());
		}
	}

	private void info(String s) {
		SmartDashboard.putString(INFO_KEY, s);
	}
	
	private void readNumbers() {
		talonId = (int) SmartDashboard.getNumber(TALON_ID_KEY, ERROR);
		p = SmartDashboard.getNumber(kP_KEY, ERROR);
		i = SmartDashboard.getNumber(kI_KEY, ERROR);
		d = SmartDashboard.getNumber(kD_KEY, ERROR);
		f = SmartDashboard.getNumber(kF_KEY, ERROR);
		izone = (int) SmartDashboard.getNumber(kIZONE_KEY, ERROR);
		speed = (int) SmartDashboard.getNumber(SPEED_KEY, DEFAULT_SPEED);
	}
}
