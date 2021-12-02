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


public class SwerveSpinners extends SubsystemBase {

  /** These are the variables for the SwerveSpinners subsytem. */
  public static final double MM_TO_IN = 0.0393701;
  public static final double WHEEL_TO_WHEEL_DIAMETER_INCHES = 320 * MM_TO_IN;
  public static final double WHEE7L_DIAMETER_INCHES = 4;
  public static final double MOTOR_POWER = 0.45;

  private WPI_TalonFX motor1, motor2, motor3, motor4;
  
  //This is the constructor for this subsytem.
  public SwerveSpinners() {
    motor1 = new WPI_TalonFX(MOTOR_PORT_1);
    motor2 = new WPI_TalonFX(MOTOR_PORT_2);
    motor3 = new WPI_TalonFX(MOTOR_PORT_3);
    motor4 = new WPI_TalonFX(MOTOR_PORT_4);
  }


  //This function is the default command for the swervedrive motor spinners.
  public void spinMotors(double horizontal, double vertical, double [] currentAngles){
    //This -1 is due to how the vertical axis works on the controller. 
    vertical *= -1;
    if (horizontal>= CONTROLLER_SENSITIVITY && vertical >= CONTROLLER_SENSITIVITY){
      double trueSpinSpeed = (((Math.sqrt(Math.pow(horizontal, 2)+Math.pow(vertical,2)))/(Math.sqrt(2)))*MOTOR_POWER);
      motor1.set(ControlMode.PercentOutput, trueSpinSpeed*(getSpinDirection(vertical, currentAngles[0])));
      motor2.set(ControlMode.PercentOutput, trueSpinSpeed*(getSpinDirection(vertical, currentAngles[1])));
      motor3.set(ControlMode.PercentOutput, trueSpinSpeed*(getSpinDirection(vertical, currentAngles[2])));
      motor4.set(ControlMode.PercentOutput, trueSpinSpeed*(getSpinDirection(vertical, currentAngles[3])));
    }
  }

  /**
   * This function is for getting the direction for the spin of a wheel. The current angles are useless right now.
   * However, it may be of a use if my thinking is wrong, so I did not erase it. 
  */
  private double getSpinDirection(double vertical, double currentAngle){
    if (vertical >= 0) return 1;
    else return -1;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }

}