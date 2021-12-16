
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
//import edu.wpi.first.wpilibj2.command.StartEndCommand;
//import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.*;
//import frc.robot.triggers.*;

import static frc.robot.Constants.*;

public class RobotContainer {

  // JOYSTICKS
  public final Joystick shopper = new Joystick(CONTROLLER_0);

  // SUBSYSTEMS
  // public final SwerveDrive SHOPPINGCART = new SwerveDrive();
  public final SwerveSpinners SWERVESPINNERS = new SwerveSpinners();
  public final SwerveRotaters SWERVEROTATERS = new SwerveRotaters();
  public final Gyro GYRO = new Gyro();

  
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    //Swervedrive.exe
    SWERVEROTATERS.setDefaultCommand(
      new RunCommand(
        () -> SWERVEROTATERS.rotateMotors(shopper.getRawAxis(TRANSLATIONAL_HORIZONTAL_AXIS),
        shopper.getRawAxis(TRANSLATIONAL_VERTICAL_AXIS), GYRO.getYaw()),
        SWERVEROTATERS
    ));
    SWERVESPINNERS.setDefaultCommand(
      new RunCommand(
        () -> SWERVESPINNERS.spinMotors(shopper.getRawAxis(TRANSLATIONAL_HORIZONTAL_AXIS),
        shopper.getRawAxis(TRANSLATIONAL_VERTICAL_AXIS),
        shopper.getRawAxis(ROTATIONAL_HORIZONTAL_AXIS),
        SWERVEROTATERS.getAngle(shopper.getRawAxis(TRANSLATIONAL_HORIZONTAL_AXIS), shopper.getRawAxis(TRANSLATIONAL_VERTICAL_AXIS), GYRO.getYaw())),
        SWERVESPINNERS
    ));
  }
}