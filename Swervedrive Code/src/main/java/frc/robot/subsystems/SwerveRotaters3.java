// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveRotaters3 extends SubsystemBase {
  /** These are the variables that are created for this subsytem.. */
  private TalonFX encoder1, encoder2, encoder3, encoder4;
  public final double ENCODER_PULSES_PER_ROTATION = 256;
  public final double TURN_POWER = 0.3;
  //This is the constructor where the rotater motors are created (named encoders) and are reset.
  public SwerveRotaters3() {
    encoder1 = new TalonFX(ROTATOR_PORT_1);
    encoder2 = new TalonFX(ROTATOR_PORT_2);
    encoder3 = new TalonFX(ROTATOR_PORT_3);
    encoder4 = new TalonFX(ROTATOR_PORT_4);
    resetEncoders();
    encoder1.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		encoder1.config_kF(kSlotIdx, kGains.kF, kTimeoutMs);
		encoder1.config_kP(kSlotIdx, kGains.kP, kTimeoutMs);
		encoder1.config_kI(kSlotIdx, kGains.kI, kTimeoutMs);
    encoder1.config_kD(kSlotIdx, kGains.kD, kTimeoutMs);
    encoder2.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		encoder2.config_kF(kSlotIdx, kGains.kF, kTimeoutMs);
		encoder2.config_kP(kSlotIdx, kGains.kP, kTimeoutMs);
		encoder2.config_kI(kSlotIdx, kGains.kI, kTimeoutMs);
    encoder2.config_kD(kSlotIdx, kGains.kD, kTimeoutMs);
    encoder3.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		encoder3.config_kF(kSlotIdx, kGains.kF, kTimeoutMs);
		encoder3.config_kP(kSlotIdx, kGains.kP, kTimeoutMs);
		encoder3.config_kI(kSlotIdx, kGains.kI, kTimeoutMs);
    encoder3.config_kD(kSlotIdx, kGains.kD, kTimeoutMs);
    encoder4.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		encoder4.config_kF(kSlotIdx, kGains.kF, kTimeoutMs);
		encoder4.config_kP(kSlotIdx, kGains.kP, kTimeoutMs);
		encoder4.config_kI(kSlotIdx, kGains.kI, kTimeoutMs);
		encoder4.config_kD(kSlotIdx, kGains.kD, kTimeoutMs);
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

  //This function gives the current angle that the provided encoder is pointing.
  public double angleToPulse(double angle){
    return angle*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360;
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
    System.out.println(angle);
    return (angle);
  }

  public double determineRot(double goal, TalonFX encoder){
    return (angleToPulse(goal)-getPosition(encoder))/256;
  }

  public void rotateMotors(double horizontal, double vertical){
    vertical *= -1;
    double goal = angle(horizontal, vertical);

    if (Math.sqrt((Math.pow(vertical, 2) + Math.pow(horizontal, 2))) >= CONTROLLER_SENSITIVITY){
      //System.out.println(angleToPulse(goal));
      
      encoder1.set(ControlMode.MotionMagic, determineRot(goal, encoder1)* UNITS_PER_ROTATION);
      encoder2.set(ControlMode.MotionMagic, determineRot(goal, encoder2)* UNITS_PER_ROTATION, DemandType.AuxPID, 0);
      encoder3.set(ControlMode.MotionMagic, determineRot(goal, encoder3)* UNITS_PER_ROTATION);
      encoder4.set(ControlMode.MotionMagic, determineRot(goal, encoder4)* UNITS_PER_ROTATION);
    }
    else{
      encoder1.set(ControlMode.Velocity, 0);
      encoder2.set(ControlMode.Velocity, 0);
      encoder3.set(ControlMode.Velocity, 0);
      encoder4.set(ControlMode.Velocity, 0);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
