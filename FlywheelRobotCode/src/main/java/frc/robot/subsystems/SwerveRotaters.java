// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveRotaters extends SubsystemBase {
  /** These are the variables that are created for this subsytem.. */
  private TalonFX encoder1, encoder2, encoder3, encoder4;
  public TalonFX[] turnWheels = new TalonFX[4];
  public final double ENCODER_PULSES_PER_ROTATION = 256;
  public final double TURN_POWER = 0.2;
  //This is the constructor where the rotater motors are created (named encoders) and are reset.
  public SwerveRotaters() {
    encoder1 = new TalonFX(ROTATOR_PORT_1);
    encoder2 = new TalonFX(ROTATOR_PORT_2);
    encoder3 = new TalonFX(ROTATOR_PORT_3);
    encoder4 = new TalonFX(ROTATOR_PORT_4);
    resetEncoders();
  }
  //This function resets the encoders when the subsytem is initialized
  public void resetEncoders(){
    encoder1.setSelectedSensorPosition(0);
    encoder2.setSelectedSensorPosition(0);
    encoder3.setSelectedSensorPosition(0);
    encoder4.setSelectedSensorPosition(0);
  }

  //This function returns the position of the encoder that is provided...
  //...to the function. 
  public double getPosition(TalonFX encoder){
    return encoder.getSelectedSensorPosition();
  }
  
  //This function is for determining which direction the wheel is pointing towards (+ being Q1&2, - being Q3&4)
  public int getDirection(TalonFX encoder){
    int direction = Math.abs(getAngle(encoder)-180) > 90 ? 1:-1;
    return direction;
  }

  //This function gives the current angle that the provided encoder is pointing.
  public double getAngle(TalonFX encoder){
    double angle = (360*(getPosition(encoder) % ENCODER_PULSES_PER_ROTATION)/ENCODER_PULSES_PER_ROTATION);
    return angle;
  }

  // This fuction determines which direction it is easiest to turn in orer to match the wanted allignment.
  public int getRotationDirection(TalonFX encoder, double goal){
    double current = getAngle(encoder)%180;
    int direction = 1;
    if (current > goal){
      direction = -1;
    }
    if (Math.abs(current - goal) > 90){
      direction *= -1;
    }
    if (getAngle(encoder) == goal) {
      direction = 0;
    }

    return direction;
  }

  // This function provides the goal angle that is trying to be reached by the wheels.
  private double angle(double horizontal, double vertical){
    double angle = 0;
    if (horizontal >= 0 && vertical >=0){
      angle = Math.atan(vertical/horizontal);
    }
    else if (horizontal >= 0 && vertical < 0){
      angle = Math.atan(vertical/horizontal) + 360 ;
    }
    else if (horizontal < 0 && vertical >= 0){
      double hnew = horizontal * -1;
      angle = 180 - Math.atan(vertical/hnew);
    }
  
    else if (horizontal < 0 && vertical < 0){
      angle = 180 + Math.atan(vertical/horizontal);
    }  
    return (angle%180);
  }

  //This function is the default function for this subsystem, it rotates each individual wheel to its wanted allignment.
  public void rotateMotors(double horizontal, double vertical){

    vertical *= -1;   
    double goal = angle(horizontal, vertical);
    //code
    boolean r1Finished = getAngle(encoder1) < goal - 2 && getAngle(encoder1) > goal + 2;
    boolean r2Finished = getAngle(encoder2) < goal - 2 && getAngle(encoder2) > goal + 2;
    boolean r3Finished = getAngle(encoder3) < goal - 2 && getAngle(encoder3) > goal + 2;
    boolean r4Finished = getAngle(encoder4) < goal - 2 && getAngle(encoder4) > goal + 2;

    if (!(r1Finished && r2Finished && r3Finished && r4Finished && Math.abs(horizontal)>=0.05 && Math.abs(vertical)>=0.05)){
      encoder1.set(ControlMode.PercentOutput, getRotationDirection(encoder1, goal)*TURN_POWER);
      encoder2.set(ControlMode.PercentOutput, getRotationDirection(encoder2, goal)*TURN_POWER);
      encoder3.set(ControlMode.PercentOutput, getRotationDirection(encoder3, goal)*TURN_POWER);
      encoder4.set(ControlMode.PercentOutput, getRotationDirection(encoder4, goal)*TURN_POWER);
      
      r1Finished = getAngle(encoder1) < goal - 2 && getAngle(encoder1) > goal + 2;
      r2Finished = getAngle(encoder2) < goal - 2 && getAngle(encoder2) > goal + 2;
      r3Finished = getAngle(encoder3) < goal - 2 && getAngle(encoder3) > goal + 2;
      r4Finished = getAngle(encoder4) < goal - 2 && getAngle(encoder4) > goal + 2;
    }
  }

  //These 4 functions provide the angles that the 4 encoders within the subsytem are pointing at.
  public double getCurrentAngleE1(){
    return getAngle(encoder1);
  }
  public double getCurrentAngleE2(){
    return getAngle(encoder2);
  }
  public double getCurrentAngleE3(){
    return getAngle(encoder3);
  }
  public double getCurrentAngleE4(){
    return getAngle(encoder4);
  }

  //This returns the values obtained from the 4 functions above as one array. 
  public double [] getCurrentAngles(){
    return new double [] {getCurrentAngleE1(), getCurrentAngleE2(), getCurrentAngleE3(), getCurrentAngleE4()};
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
