package org.stormgears.pid_tuner;

import edu.wpi.first.wpilibj.IterativeRobot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.stormgears.utils.logging.Log4jConfigurationFactory;


public class Robot extends IterativeRobot {
	static {
		ConfigurationFactory.setConfigurationFactory(new Log4jConfigurationFactory());
	}

	private static final Logger logger = LogManager.getLogger(Robot.class);

	/**
	 * This function is run when the pid_tuner is first started up and should be
	 * used for any initialization code
	 */
	@Override
	public void robotInit() {

	}

	/**
	 * Runs when autonomous mode starts
	 */
	@Override
	public void autonomousInit() {

	}

	/**
	 * Runs when operator control starts
	 */
	@Override
	public void teleopInit() {

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}

	/**
	 * This function is called whenever the pid_tuner is disabled.
	 */
	public void disabledInit() {

	}
}

