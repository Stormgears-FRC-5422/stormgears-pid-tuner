package org.stormgears.pid_tuner;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.command.Scheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.stormgears.utils.StormTalon;
import org.stormgears.utils.logging.Log4jConfigurationFactory;


public class Robot extends IterativeRobot {
	static {
		ConfigurationFactory.setConfigurationFactory(new Log4jConfigurationFactory());
	}

	private static final Logger logger = LogManager.getLogger(Robot.class);
	private PidTuner tuner = null;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code
	 */
	@Override
	public void robotInit() {
		logger.info("Starting PID Tuner!");

		tuner = new PidTuner();
	}

	/**
	 * Runs when operator control starts
	 */
	@Override
	public void teleopInit() {
		logger.info("Please open SmartDashboard to tune PID values.");
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		if (tuner != null) {
			tuner.logData();
		}
	}

	/**
	 * This function is called whenever the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		logger.info("Robot disabled.");
	}
}

