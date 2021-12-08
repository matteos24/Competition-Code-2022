// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveRotaters extends SubsystemBase {
  /** These are the variables that are created for this subsytem.. */
  private TalonFX encoder1, encoder2, encoder3, encoder4;
  public final double ENCODER_PULSES_PER_ROTATION = 2048;
  
  //Configure the turn power into the control mode pid somehow, I think there is a method for RPM or something -> check pls:)

  public final double TURN_POWER = 0.25;

  // This is the constructor where the rotater motors are created (named encoders) and are reset.
  // In addition this is where the PIDf initilization occurs for each rotater motor (again named encoders 1, 2, 3, & 4)
  // The sensor configuration is also set in this constructor.

  public SwerveRotaters() {
    encoder1 = new TalonFX(ROTATOR_PORT_1);
    encoder2 = new TalonFX(ROTATOR_PORT_2);
    encoder3 = new TalonFX(ROTATOR_PORT_3);
    encoder4 = new TalonFX(ROTATOR_PORT_4);
    encoder1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    encoder2.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    encoder3.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    encoder4.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    resetEncoders();

    encoder1.configFactoryDefault();
    encoder1.set(ControlMode.Velocity,0);
    encoder1.config_kP(0, kGains.kP);
    encoder1.config_kI(0, kGains.kI);
    encoder1.config_kD(0, kGains.kD);
    encoder1.config_kF(0, kGains.kF); 
    encoder1.setSensorPhase(true);

    encoder2.configFactoryDefault();
    encoder2.set(ControlMode.Velocity,0);
    encoder2.config_kP(0, kGains.kP);
    encoder2.config_kI(0, kGains.kI);
    encoder2.config_kD(0, kGains.kD);
    encoder2.config_kF(0, kGains.kF); 
    encoder2.setSensorPhase(true);

    encoder3.configFactoryDefault();
    encoder3.set(ControlMode.Velocity,0);
    encoder3.config_kP(0, kGains.kP);
    encoder3.config_kI(0, kGains.kI);
    encoder3.config_kD(0, kGains.kD);
    encoder3.config_kF(0, kGains.kF); 
    encoder3.setSensorPhase(true);
    
    encoder4.configFactoryDefault();
    encoder4.set(ControlMode.Velocity,0);
    encoder4.config_kP(0, kGains.kP);
    encoder4.config_kI(0, kGains.kI);
    encoder4.config_kD(0, kGains.kD);
    encoder4.config_kF(0, kGains.kF); 
    encoder4.setSensorPhase(true);
    
  }
  //This function resets the encoders when the subsytem is initialized
  public void resetEncoders(){
    encoder1.setSelectedSensorPosition(0);
    encoder2.setSelectedSensorPosition(0);
    encoder3.setSelectedSensorPosition(0);
    encoder4.setSelectedSensorPosition(0);
  }

  //This function returns the position of the encoder that is provided
  public double getPosition(TalonFX encoder){
    return encoder.getSelectedSensorPosition();
  }

  //This function gives the current angle that the provided encoder is pointing.
  public double angleToPulse(double horizontal, double vertical){
    return angle(horizontal, vertical)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360;
  }

  // This function provides the goal angle that is trying to be reached by the wheels.
  private double angle(double horizontal, double vertical){
    double angle = 0;
   
    angle = Math.toDegrees(Math.atan(-horizontal/vertical));

    boolean horizontalIsPositive = (horizontal>0);
    boolean verticalIsPositive = (vertical>0);

    if (!horizontalIsPositive && !verticalIsPositive){
      angle = (90-(-angle)) + 90; 
    }
    else if (horizontalIsPositive && !verticalIsPositive){
      angle = (angle + 180);
    }
    else if (horizontalIsPositive && verticalIsPositive){
      angle = 360 + angle; 
    }
    //System.out.println(angle);
    return (angle);
  }

  /*
  public double determineRot(double goal, TalonFX encoder){
    return (angleToPulse(goal)-getPosition(encoder))/256;
  }
  */
  public void rotateMotors(double horizontal, double vertical){
    vertical *= -1;
    // This -1 is because the vertical axis provided by the controller is reversed.
    double goal = angleToPulse(horizontal, vertical);
    System.out.println(encoder1.getSelectedSensorPosition());
    if (Math.sqrt((Math.pow(vertical, 2) + Math.pow(horizontal, 2))) >= CONTROLLER_SENSITIVITY){
      //These are the Position Control Methods for the encoders, essentially the heart of the rotaters file
      encoder1.set(ControlMode.Position, goal);
      encoder2.set(ControlMode.Position, goal);
      encoder3.set(ControlMode.Position, goal);
      encoder4.set(ControlMode.Position, goal);
    }
    // If this statement is not true the method will not do anything
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
