package org.stormgears.utils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardUtils {
	public static void putButton(String text, SmartDashboardOnButtonClickedListener delegate) {
		SmartDashboard.putData(text, new Command() {
			@Override
			protected boolean isFinished() {
				return true;
			}

			@Override
			protected void execute() {
				System.out.println("Button on SmartDashboard '" + text + "' pressed.");
				delegate.onClicked();
			}
		});
	}
}
