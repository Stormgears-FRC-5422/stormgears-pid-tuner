package org.stormgears.pid_tuner;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
	private static final String VELOCITY_KEY = "Velocity";
	private static final String POSITION_KEY = "Position";
	private static final String INFO_KEY = "Info";
	
	// Error value
	private static final int ERROR = Integer.MAX_VALUE;
	
	// Values
	private int talonId;
	private double p, i, d, f;
	private int izone;
	private int velocity;
	private int position;
	
	// Talon Stuff
	private StormTalon talon;
	private boolean running = false;
	private static final int DEFAULT_SPEED = 6300;
	private static final int DEFAULT_POSITION = 0;
	private static final int TALON_FPID_TIMEOUT = 0;
	private SendableChooser<ControlMode> modeChooser;

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
		SmartDashboard.putNumber(VELOCITY_KEY, 0);
		SmartDashboard.putNumber(POSITION_KEY, 0);
		SmartDashboard.putString(INFO_KEY, "");

		SmartDashboardUtils.putButton("Start Motor", () -> {
			if (!running) {
				readNumbers();
				
				if (talonId == ERROR || p == ERROR || i == ERROR || d == ERROR || f == ERROR || izone == ERROR) {
					info("Please fill out ID, p, i, d, f, and izone text fields!");
				} else {
					talon = new StormTalon(talonId);

					ControlMode mode = modeChooser.getSelected();

					if (mode == ControlMode.Velocity && velocity == DEFAULT_SPEED) {
						info("Using default velocity of " + DEFAULT_SPEED);
					} else if (mode == ControlMode.Position && position == DEFAULT_POSITION) {
						info("Using default position of " + DEFAULT_POSITION);
					}
					info("Starting motor with " + mode.name() + " " + velocity);

					talon.setInverted(true);
					talon.setSensorPhase(true);
					talon.config_kF(0, f, TALON_FPID_TIMEOUT);
					talon.config_kP(0, p, TALON_FPID_TIMEOUT);
					talon.config_kI(0, i, TALON_FPID_TIMEOUT);
					talon.config_kD(0, d, TALON_FPID_TIMEOUT);
					talon.config_IntegralZone(0, izone, TALON_FPID_TIMEOUT);

					talon.set(mode, mode == ControlMode.Position ? position : velocity);
					running = true;
				}
			} else {
				info("Cannot start! Motor already running!");
			}
		});

		SmartDashboardUtils.putButton("Stop Motor", () -> {
			if (talon != null) {
				talon.set(ControlMode.PercentOutput, 0);
			}
			running = false;
		});

		modeChooser = new SendableChooser<>();
		modeChooser.addDefault("Velocity", ControlMode.Velocity);
		modeChooser.addObject("Position", ControlMode.Position);
		SmartDashboard.putData("Tuning Mode", modeChooser);
	}

	public void logData() {
		if (talon != null) {
			int velocity = talon.getSensorCollection().getQuadratureVelocity(),
				position = talon.getSensorCollection().getQuadraturePosition();
			SmartDashboard.putNumber("Encoder Velocity", velocity);
			SmartDashboard.putNumber("Encoder Position", position);
			SmartDashboard.putNumber("_Encoder Velocity", velocity);
			SmartDashboard.putNumber("_Encoder Position", position);
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
		velocity = (int) SmartDashboard.getNumber(VELOCITY_KEY, DEFAULT_SPEED);
		position = (int) SmartDashboard.getNumber(POSITION_KEY, DEFAULT_POSITION);
	}
}
