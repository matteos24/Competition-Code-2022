/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.BACK_LEFT_DRIVE_PORT;
import static frc.robot.Constants.BACK_RIGHT_DRIVE_PORT;
import static frc.robot.Constants.FRONT_LEFT_DRIVE_PORT;
import static frc.robot.Constants.FRONT_RIGHT_DRIVE_PORT;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * COMPETITION READY
 * 
 * Controls all wheels on frame and encoder values for auto.
 */
public class Drivetrain extends SubsystemBase {

  public static final double MM_TO_IN = 0.0393701;
  public static final double WHEEL_TO_WHEEL_DIAMETER_INCHES = 320 * MM_TO_IN;
  public static final double WHEEL_DIAMETER_INCHES = 4;

  private WPI_TalonFX frontLeft, frontRight, backLeft, backRight;

  private SpeedControllerGroup left, right;

  private double speedMultiplier = 1;

  private boolean isFast = true;

  private Joystick driver;

  public Drivetrain(Joystick drive) {
    frontLeft = new WPI_TalonFX(FRONT_LEFT_DRIVE_PORT);
    backLeft = new WPI_TalonFX(BACK_LEFT_DRIVE_PORT);
    frontRight = new WPI_TalonFX(FRONT_RIGHT_DRIVE_PORT);
    backRight = new WPI_TalonFX(BACK_RIGHT_DRIVE_PORT);

    left = new SpeedControllerGroup(frontLeft, backLeft);
    right = new SpeedControllerGroup(frontRight, backRight);

    driver = drive;

    resetEncoders();
  }

  /**
   * Resets the encoders.
   * 
   * UNSURE IF THIS ACTUALLY WORKS- SHOULD PROBABLY JUST USE START DIST JUST TO BE
   * SAFE.
   */
  public void resetEncoders() {
    frontLeft.setSelectedSensorPosition(0, 0, 0);
    backLeft.setSelectedSensorPosition(0, 0, 0);
    frontRight.setSelectedSensorPosition(0, 0, 0);
    backRight.setSelectedSensorPosition(0, 0, 0);
  }

  // SPEED MODES

  /**
   * Make the drivetrain (all axes) quarter speed.
   */
  public void modeSlow() {
    speedMultiplier = 0.25;
    isFast = false;
  }

  /**
   * Make the drivetrain (all axes) full speed.
   */
  public void modeFast() {
    speedMultiplier = 1;
    isFast = true;
  }

  // MOTOR SPEEDS

  /**
   * Takes individual speeds for the left and right side of the drivetrain.
   * 
   * @param leftSpeed  [-1.0, 1.0]
   * @param rightSpeed [-1.0, 1.0]
   */
  public void tankDrive(double leftSpeed, double rightSpeed) {
    setLeftSpeed(leftSpeed);
    setRightSpeed(-rightSpeed);
  }

  /**
   * X is vertical speed, Z is horizontal speed
   * 
   * @param x [-1.0, 1.0]
   * @param z [-1.0, 1.0]
   */
  public void arcadeDrive(double x, double z) {
    if(Math.abs(x) < 0.02) x = 0;
    if(Math.abs(z) < 0.02) z = 0;
    x *= Math.abs(x) * (isFast ? 1.0 : 0.35);
    z *= Math.abs(z) * 0.3;

    tankDrive(x + z, x - z);
  }

  /**
   * Sets the left side speed.
   * 
   * @param speed [-1.0, 1.0]
   */
  private void setLeftSpeed(double speed) {
    left.set(speed * speedMultiplier);
  }

  /**
   * Sets the right side speed.
   * 
   * @param speed [-1.0, 1.0]
   */
  private void setRightSpeed(double speed) {
    right.set(speed * speedMultiplier);
  }

  /**
   * Gets the average position of the left side of the drivetrain.
   * 
   * @return Position in units
   */
  public double getLeftDistance() {
    return (frontLeft.getSelectedSensorPosition(0) + backLeft.getSelectedSensorPosition(0)) / 2.;
  }

  /**
   * Gets the average position of the right side of the drivetrain.
   * 
   * @return Position in units
   */
  public double getRightDistance() {
    return -(frontRight.getSelectedSensorPosition(0) + backRight.getSelectedSensorPosition(0)) / 2.;
  }

  /**
   * Gets the average distance of both sides of the drivetrain.
   * 
   * Please do not use this for turning, since the wheels will be going in
   * opposite directions and therefore the average will not change.
   * 
   * @return Position in untis
   */
  public double getAverageDistance() {
    return (getLeftDistance() + getRightDistance()) / 2.;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }

}