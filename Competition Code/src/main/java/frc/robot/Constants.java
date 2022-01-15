/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public final class Constants {

    // === SWERVEDRIVE === //

    // SwerveDrive MOTOR Ports
  
    public static final int MOTOR_PORT_1 = 5;

    public static final int MOTOR_PORT_2 = 8;

    public static final int MOTOR_PORT_3 = 3;

    public static final int MOTOR_PORT_4 = 7; 

    public static final int ROTATOR_PORT_1 = 4;

    public static final int ROTATOR_PORT_2 = 2;
   
    public static final int ROTATOR_PORT_3 = 1;
 
    public static final int ROTATOR_PORT_4 = 6;
  

    public static final int CATAPAULT_PISTON_PORT_1 = 0;
    public static final int CATAPAULT_PISTON_PORT_2 = 0;
    public static final int CATAPAULT_PISTON_PORT_3 = 0;
    public static final int CATAPAULT_PISTON_PORT_4 = 0;
    //Gyro
    public static final int GYRO_PORT = 11;

    //Controller Constants
    public static final int CONTROLLER_0 = 0;
    public static final int TRANSLATIONAL_HORIZONTAL_AXIS = 0;
    public static final int TRANSLATIONAL_VERTICAL_AXIS = 1;
    public static final int ROTATIONAL_HORIZONTAL_AXIS = 2;
    public static final double CONTROLLER_SENSITIVITY = 0.1; // The value from the center of an axis to where it is, after which the code will actually run for the rotaters
    public static final int DRIVESWITCHBUTTON = 9; // Check this
    public static final int CATAPAULT_BUTTON = 2;

    // Swervedrive Constants
    public static final double ANGLE_RANGE = 2;
    public static final double P_CONSTANT = 1.0/360.0;
    public static final double GEAR_RATIO = 12.8;
    public static final double UNITS_PER_ROTATION = 2048;
    public static final double L = 24.174;
    public static final double W = 24.183;

    //Swervedrive PID Stuff
    public static final int kSlotIdx = 0;
	public static final int kPIDLoopIdx = 0;
    public static final int kTimeoutMs = 30;
    public static final Gains kGains = new Gains(0.1, 0.0, 0.0, 0.0, 0, 0.0);
}