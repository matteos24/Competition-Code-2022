// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;

import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */

  //Motors
  private WPI_TalonFX shooter_motor_1;
  private WPI_TalonFX shooter_motor_2;

  //Piston
  private DoubleSolenoid shooter_piston;

  //Constant Variables
  //Speed multiplier same as the one in swerve spinners
  private static double SPEED_MULTIPLIER = 0.7;
  public Shooter(){
    shooter_motor_1 = new WPI_TalonFX(Constants.SHOOTER_MOTOR_PORT_1);
    shooter_motor_2 = new WPI_TalonFX(Constants.SHOOTER_MOTOR_PORT_2);

    shooter_piston = new DoubleSolenoid(Constants.SHOOTER_PISTON_PORT_1, Constants.SHOOTER_PISTON_PORT_2);
    limitMotorCurrent();
  }

  //Reduces the current that the TalonSRX draws in order to prevent brownouts
  public void limitMotorCurrent(){
    shooter_motor_1.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 20, 21, 1));
    shooter_motor_1.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 20, 21, 1));

    shooter_motor_2.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 20, 21, 1));
    shooter_motor_2.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 20, 21, 1));
  }
  public void setSpeed(double speed){
    speed = -speed * SPEED_MULTIPLIER;
    shooter_motor_1.set(speed);
    shooter_motor_2.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
