/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 * COMPETITION READY
 * 
 * Controls all wheels on frame and encoder values for auto.
 */
public class SwerveSpinners extends SubsystemBase {

  public static final double MM_TO_IN = 0.0393701;
  public static final double WHEEL_TO_WHEEL_DIAMETER_INCHES = 320 * MM_TO_IN;
  public static final double WHEE7L_DIAMETER_INCHES = 4;
  public static final double MOTOR_POWER = 0.45;

  private WPI_TalonFX motor1, motor2, motor3, motor4;
  //private SpeedControllerGroup spinSpeedController;
  
  public SwerveSpinners() {
    motor1 = new WPI_TalonFX(MOTOR_PORT_1);
    motor2 = new WPI_TalonFX(MOTOR_PORT_2);
    motor3 = new WPI_TalonFX(MOTOR_PORT_3);
    motor4 = new WPI_TalonFX(MOTOR_PORT_4);
  }

  // MOTORS

  //So this is the default command for SwerveDrive
  public void spinMotors(double horizontal, double vertical, double [] currentAngles){
    vertical *= -1;
    if (horizontal>= 0.05 && vertical >= 0.05){
      double trueSpinSpeed = (((Math.sqrt(Math.pow(horizontal, 2)+Math.pow(vertical,2)))/(Math.sqrt(2)))*MOTOR_POWER);
      motor1.set(ControlMode.PercentOutput, trueSpinSpeed*(getSpinDirection(vertical, currentAngles[0])));
      motor2.set(ControlMode.PercentOutput, trueSpinSpeed*(getSpinDirection(vertical, currentAngles[1])));
      motor3.set(ControlMode.PercentOutput, trueSpinSpeed*(getSpinDirection(vertical, currentAngles[2])));
      motor4.set(ControlMode.PercentOutput, trueSpinSpeed*(getSpinDirection(vertical, currentAngles[3])));
    }
  }

  private double getSpinDirection(double vertical, double currentAngle){
    // here I do not multiply vertical by -1 at start: just switched > to <. 
    if (vertical <= 0) return 1;
    else return -1;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }

}