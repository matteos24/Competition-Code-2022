// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveRotaters extends SubsystemBase {
  /** These are the variables that are created for this subsytem.. */
  private TalonFX fRRotater, fLRotater, bLRotater, bRRotater;
  public final double ENCODER_PULSES_PER_ROTATION = 2048;
  public final double ROTATION_POW = 40;
  
  //Configure the turn power into the control mode pid somehow, I think there is a method for RPM or something -> check pls:)

  public final double TURN_POWER = 0.25;

  // This is the constructor where the rotater motors are created (named encoders) and are reset.
  // In addition this is where the PIDf initilization occurs for each rotater motor (again named encoders 1, 2, 3, & 4)
  // The sensor configuration is also set in this constructor.

  public SwerveRotaters() {
    fRRotater = new TalonFX(ROTATOR_PORT_1);
    fLRotater = new TalonFX(ROTATOR_PORT_2);
    bLRotater = new TalonFX(ROTATOR_PORT_3);
    bRRotater = new TalonFX(ROTATOR_PORT_4);
    fRRotater.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    fLRotater.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    bLRotater.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    bRRotater.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    resetEncoders();

    fRRotater.configFactoryDefault();
    fRRotater.set(ControlMode.Velocity,0);
    fRRotater.config_kP(0, kGains.kP);
    fRRotater.config_kI(0, kGains.kI);
    fRRotater.config_kD(0, kGains.kD);
    fRRotater.config_kF(0, kGains.kF); 
    fRRotater.setSensorPhase(true);

    fLRotater.configFactoryDefault();
    fLRotater.set(ControlMode.Velocity,0);
    fLRotater.config_kP(0, kGains.kP);
    fLRotater.config_kI(0, kGains.kI);
    fLRotater.config_kD(0, kGains.kD);
    fLRotater.config_kF(0, kGains.kF); 
    fLRotater.setSensorPhase(true);

    bLRotater.configFactoryDefault();
    bLRotater.set(ControlMode.Velocity,0);
    bLRotater.config_kP(0, kGains.kP);
    bLRotater.config_kI(0, kGains.kI);
    bLRotater.config_kD(0, kGains.kD);
    bLRotater.config_kF(0, kGains.kF); 
    bLRotater.setSensorPhase(true);
    
    bRRotater.configFactoryDefault();
    bRRotater.set(ControlMode.Velocity,0);
    bRRotater.config_kP(0, kGains.kP);
    bRRotater.config_kI(0, kGains.kI);
    bRRotater.config_kD(0, kGains.kD);
    bRRotater.config_kF(0, kGains.kF); 
    bRRotater.setSensorPhase(true);
    
  }
  //This function resets the encoders when the subsytem is initialized
  public void resetEncoders(){
    fRRotater.setSelectedSensorPosition(0);
    fLRotater.setSelectedSensorPosition(0);
    bLRotater.setSelectedSensorPosition(0);
    bRRotater.setSelectedSensorPosition(0);
  }

  //This function returns the position of the encoder that is provided
  public double getPosition(TalonFX encoder){
    return encoder.getSelectedSensorPosition();
  }

  //This function gives the current angle that the provided encoder is pointing.
  public double angleToPulse(double horizontal, double vertical, double yaw){
    return angle(horizontal, vertical, yaw)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360;
  }

  // This function provides the goal angle that is trying to be reached by the wheels.
  private double angle(double horizontal, double vertical, double yaw){
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
    // This uses the yaw of the robot in order to calculate the angle we want to turn relative to the front
    // of the robot, which is the 0 point of the encoders. 
    // yaw = Math.toDegrees(yaw);
    //System.out.println(yaw);
    if (angle>=yaw) angle -= yaw;
    else angle = 360 - (yaw-angle); 
    return (angle);
  }

  public double getAngle(double horizontal, double vertical, double yaw){
    return angle(horizontal, -vertical, yaw);
  }

  public void rotateMotors(double horizontal, double vertical, double rotationHorizontal, double yaw){
    vertical *= -1;
    // This -1 is because the vertical axis provided by the controller is reversed.
    double goal = angleToPulse(horizontal, vertical, yaw);
    double angle = angle(horizontal, vertical, yaw);
    boolean isRotating = Math.abs(rotationHorizontal)>=CONTROLLER_SENSITIVITY;
    boolean isTranslating = (Math.sqrt((Math.pow(vertical, 2) + Math.pow(horizontal, 2))) >= CONTROLLER_SENSITIVITY);
    if (isTranslating && !isRotating){
      //These are the Position Control Methods for the encoders, essentially the heart of the rotaters file
      fRRotater.set(ControlMode.Position, goal);
      fLRotater.set(ControlMode.Position, goal);
      bLRotater.set(ControlMode.Position, goal);
      bRRotater.set(ControlMode.Position, goal);
    }
    else if(isRotating && !isTranslating){
      fRRotater.set(ControlMode.Position, 45*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
      fLRotater.set(ControlMode.Position, 135*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
      bLRotater.set(ControlMode.Position, 225*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
      bRRotater.set(ControlMode.Position, 315*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
    }
    else if (isRotating && isTranslating){
      //zone 1
      if(((angle>=0)&&(45>angle))&&((360>angle)&&(angle>=315))){
        fRRotater.set(ControlMode.Position, ((angle-rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        fLRotater.set(ControlMode.Position, ((angle-rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        bLRotater.set(ControlMode.Position, ((angle+rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        bRRotater.set(ControlMode.Position, ((angle+rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
      }
      //zone 2
      else if((angle>=45)&&(135>angle)){
        bLRotater.set(ControlMode.Position, ((angle-rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        fLRotater.set(ControlMode.Position, ((angle-rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        fRRotater.set(ControlMode.Position, ((angle+rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        bRRotater.set(ControlMode.Position, ((angle+rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
      }
      //zone 3
      else if((angle>=135)&&(225>angle)){
        bLRotater.set(ControlMode.Position, ((angle-rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        bRRotater.set(ControlMode.Position, ((angle-rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        fLRotater.set(ControlMode.Position, ((angle+rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        fRRotater.set(ControlMode.Position, ((angle+rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        
      }
      //zone 4
      else if((angle>=225)&&(315>angle)){
        fRRotater.set(ControlMode.Position, ((angle-rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        bRRotater.set(ControlMode.Position, ((angle-rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        bLRotater.set(ControlMode.Position, ((angle+rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360);
        fLRotater.set(ControlMode.Position, ((angle+rotationHorizontal*ROTATION_POW)%360)*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360); 
      }
    }
    // If this statement is not true the method will not do anything
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
