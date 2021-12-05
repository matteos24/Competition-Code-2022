// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveRotaters3 extends SubsystemBase {
  /** These are the variables that are created for this subsytem.. */
  private TalonFX encoder1, encoder2, encoder3, encoder4;
  public final double ENCODER_PULSES_PER_ROTATION = 256;
  public final double TURN_POWER = 0.1;
  //This is the constructor where the rotater motors are created (named encoders) and are reset.
  public SwerveRotaters3() {
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
    //System.out.println(angle);
    return (angle);
  }


  public void rotateMotors(double horizontal, double vertical){
    vertical *= -1;
    double goal = angle(horizontal, vertical);

    if (Math.sqrt((Math.pow(vertical, 2) + Math.pow(horizontal, 2))) >= CONTROLLER_SENSITIVITY){
      System.out.println(angleToPulse(goal));
      encoder1.set(ControlMode.MotionMagic, angleToPulse(goal));
      encoder2.set(ControlMode.MotionMagic, angleToPulse(goal));
      encoder3.set(ControlMode.MotionMagic, angleToPulse(goal));
      // encoder4.set(ControlMode.MotionMagic, angleToPulse(goal)); 
    }
    else{
      encoder1.set(ControlMode.Position, getPosition(encoder1));
      encoder2.set(ControlMode.Position, getPosition(encoder2));
      encoder3.set(ControlMode.Position, getPosition(encoder3));
      encoder4.set(ControlMode.Position, getPosition(encoder4));
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
