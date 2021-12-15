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
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;



public class SwerveDrive extends SubsystemBase {

  private TalonFX bRRotater, bLRotater, fRRotater, fLRotater;
  public final double ENCODER_PULSES_PER_ROTATION = 2048;
  //Configure the turn power into the control mode pid somehow, I think there is a method for RPM or something -> check pls:)
  public final double TURN_POWER = 0.25;
  public static final double MM_TO_IN = 0.0393701;
  public static final double WHEEL_TO_WHEEL_DIAMETER_INCHES = 320 * MM_TO_IN;
  public static final double WHEE7L_DIAMETER_INCHES = 4;
  public static final double MOTOR_POWER = 0.25;

  private WPI_TalonFX bRMotor, bLMotor, fRMotor, fLMotor;
  private SpeedControllerGroup bR, bL, fR, fL;


  /** Creates a new SwerveDrive. */
  public SwerveDrive() {

    bRMotor = new WPI_TalonFX(MOTOR_PORT_4);
    bLMotor = new WPI_TalonFX(MOTOR_PORT_3);
    fRMotor = new WPI_TalonFX(MOTOR_PORT_1);
    fLMotor = new WPI_TalonFX(MOTOR_PORT_2);

    bR = new SpeedControllerGroup(bRMotor);
    bL = new SpeedControllerGroup(bLMotor);
    fR = new SpeedControllerGroup(fRMotor);
    fL = new SpeedControllerGroup(fLMotor);

    bRRotater = new TalonFX(ROTATOR_PORT_4);
    bLRotater = new TalonFX(ROTATOR_PORT_3);
    fRRotater = new TalonFX(ROTATOR_PORT_1);
    fLRotater = new TalonFX(ROTATOR_PORT_2);
    
    bRRotater.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    bLRotater.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    fRRotater.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    fLRotater.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
    resetEncoders();

    bRRotater.configFactoryDefault();
    bRRotater.set(ControlMode.Velocity,0);
    bRRotater.config_kP(0, kGains.kP);
    bRRotater.config_kI(0, kGains.kI);
    bRRotater.config_kD(0, kGains.kD);
    bRRotater.config_kF(0, kGains.kF); 
    bRRotater.setSensorPhase(true);

    bLRotater.configFactoryDefault();
    bLRotater.set(ControlMode.Velocity,0);
    bLRotater.config_kP(0, kGains.kP);
    bLRotater.config_kI(0, kGains.kI);
    bLRotater.config_kD(0, kGains.kD);
    bLRotater.config_kF(0, kGains.kF); 
    bLRotater.setSensorPhase(true);

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
  }
  public void resetEncoders(){
    bRRotater.setSelectedSensorPosition(0);
    bLRotater.setSelectedSensorPosition(0);
    fRRotater.setSelectedSensorPosition(0);
    fLRotater.setSelectedSensorPosition(0);
  }
  //This function returns the position of the encoder that is provided...
  //...to the function. 
  public void runSwerve(double horizontal, double vertical, double rotationHorizontal){
    //This -1 is due to how the vertical axis works on the controller. 
  
    horizontal = Math.pow(horizontal, 2) * (horizontal/Math.abs(horizontal));
    vertical = Math.pow(vertical, 2) * (vertical/Math.abs(vertical));
    rotationHorizontal = Math.pow(rotationHorizontal, 2) * (rotationHorizontal/Math.abs(rotationHorizontal));
    vertical *= -1;
    double r = Math.sqrt(L*L + W*W);
    double a = (horizontal-rotationHorizontal) * (L/r);
    double b = (horizontal + rotationHorizontal) * (L/r);
    double c = (vertical - rotationHorizontal) * (W/r);
    double d = (vertical + rotationHorizontal) * (W/r);
      //check L & W with CAD people, yes...
    spinMotors(a, b, c, d);
    rotateMotors(a, b, c, d);
  }

  public void spinMotors(double a, double b, double c, double d){
    double backRightSpeed = Math.sqrt(a*a + d*d);
    double backLeftSpeed = Math.sqrt(a*a + c*c);
    double frontRightSpeed = Math.sqrt(b*b + d*d);
    double frontLeftSpeed = Math.sqrt(b*b + c*c);

    bR.set(MOTOR_POWER*backRightSpeed);
    bL.set(MOTOR_POWER*backLeftSpeed);
    fR.set(MOTOR_POWER*frontRightSpeed);
    fL.set(MOTOR_POWER*frontLeftSpeed);
  }

  public double getPosition(TalonFX encoder){
    return encoder.getSelectedSensorPosition();
  }

  //This function gives the current angle that the provided encoder is pointing.
  public double angleToPulse(double angle){
    return angle*(ENCODER_PULSES_PER_ROTATION*GEAR_RATIO)/360;
  }

  public void rotateMotors(double a, double b, double c, double d){
    //System.out.println(goal); // [0, 3300]

    double backRightAngle = Math.atan2(a, d);
    if (backRightAngle<0) backRightAngle = 2*Math.PI + backRightAngle;
    double backLeftAngle = Math.atan2(a, c);
    if (backLeftAngle<0) backLeftAngle = 2*Math.PI + backLeftAngle;
    double frontRightAngle = Math.atan2(b, d);
    if (frontRightAngle<0) frontRightAngle = 2*Math.PI + frontRightAngle;
    double frontLeftAngle = Math.atan2(b, c);
    if (frontLeftAngle<0) frontLeftAngle = 2*Math.PI + frontLeftAngle;

    double bRPulse = angleToPulse(Math.toDegrees(backRightAngle));
    double bLPulse = angleToPulse(Math.toDegrees(backLeftAngle));
    double fRPulse = angleToPulse(Math.toDegrees(frontRightAngle));
    double fLPulse = angleToPulse(Math.toDegrees(frontLeftAngle));

    System.out.println("br" + bRPulse);
    System.out.println("bl" + bLPulse);
    System.out.println("fr" + fRPulse);
    System.out.println("fl" + fLPulse);

    bRRotater.set(ControlMode.Position, bRPulse);
    bLRotater.set(ControlMode.Position, bLPulse);
    fRRotater.set(ControlMode.Position, fRPulse);
    fLRotater.set(ControlMode.Position, fLPulse);
     
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

