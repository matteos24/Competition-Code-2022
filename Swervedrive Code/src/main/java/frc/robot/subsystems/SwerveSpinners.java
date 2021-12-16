/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
//import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.ctre.phoenix.sensors.CANCoder;

//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
//import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;


public class SwerveSpinners extends SubsystemBase {

  /** These are the variables for the SwerveSpinners subsytem. */
  public static final double MM_TO_IN = 0.0393701;
  public static final double WHEEL_TO_WHEEL_DIAMETER_INCHES = 320 * MM_TO_IN;
  public static final double WHEEL_DIAMETER_INCHES = 4;
  public static final double MOTOR_POWER = 0.5;
  public static final double SPEED_DIVIDER = 1.2;
  public static final double ROTATION_COEFFICIENT= 0.16; //This can take a max value of 1-(1/SPEED_DIVIDER)

  private WPI_TalonFX bRMotor, bLMotor, fRMotor, fLMotor;
  private SpeedControllerGroup bR, bL, fR, fL;
  
  //This is the constructor for this subsytem.
  public SwerveSpinners() {
    bRMotor = new WPI_TalonFX(MOTOR_PORT_4);
    bLMotor = new WPI_TalonFX(MOTOR_PORT_3);
    fRMotor = new WPI_TalonFX(MOTOR_PORT_1);
    fLMotor = new WPI_TalonFX(MOTOR_PORT_2);

    bR = new SpeedControllerGroup(bRMotor);
    bL = new SpeedControllerGroup(bLMotor);
    fR = new SpeedControllerGroup(fRMotor);
    fL = new SpeedControllerGroup(fLMotor);
  }


  //This function is the default command for the swervedrive motor spinners.
  public void spinMotors(double horizontal, double vertical, double rotationHorizontal, double angle){
    //This -1 is due to how the vertical axis works on the controller. 
    vertical *= -1;
    double r = (Math.sqrt(horizontal*horizontal + vertical*vertical)/SPEED_DIVIDER);
    //This makes the maximum power 1/Speed Divider. So, we can essentially add to some of the motors and
    // get it to rotate without going above 1 by accident, which would just turn to 1. (Probably)

    //Here the initial speeds are set to the value r - calculated above -
    double backRightSpeed = r;
    double backLeftSpeed = r;
    double frontRightSpeed = r;
    double frontLeftSpeed = r;

    //If the horizontal and vertical are enough to make it move translationally then this will compute.
    // I commented out the decrease operations in order to decrease skid. (NEED TO TEST)
    if (Math.sqrt(horizontal*horizontal + vertical*vertical)>=CONTROLLER_SENSITIVITY){
      if ((45>angle && angle>=0)||(angle>315)){
      //left motors are increased, right are decreased
      backLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
      frontLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
      //backRightSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
      //frontRightSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;

      }
      
      if (angle == 45){
        //only backLeft is increased
        backLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //frontRightSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
      }

      if (135>angle && angle>45){
        //back motors are increased, front are decreased
        backLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //frontLeftSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
        backRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //frontRightSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
      }

      if (angle == 135){
        //backRight is increased
        backRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //frontLeftSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
      }

      if (225>angle && angle>135){
        //right motors are increased, left are decreased
        //backLeftSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
        //frontLeftSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
        backRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        frontRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
      }

      if (angle == 225){
        //frontRight is increased
        frontRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //backLeftSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
      }

      if (315>angle && angle>225){
        //front motors are increased, back are decreased
        //backLeftSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
        frontLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //backRightSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
        frontRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
      }

      if (angle == 315) {
        //frontLeft is increased, rightBack is decreased
        frontLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //backRightSpeed -= ROTATION_COEFFICIENT*rotationHorizontal;
      }
    }
    //This part is for no translation. There are always opposite speeds but other 2 speeds are 0 in order
    // to just rotate without translation.
    else{
      if ((45>angle && angle>=0)||(angle>315)){
      //left motors are increased, right are decreased
      backLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
      frontLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
      backRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
      frontRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;

      }
      
      if (angle == 45){
        //only backLeft is increased
        backLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        frontRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        //other two are 0
        backRightSpeed = 0;
        frontLeftSpeed = 0;
      }

      if (135>angle && angle>45){
        //back motors are increased, front are decreased
        backLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        frontLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        backRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        frontRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
      }

      if (angle == 135){
        //backRight is increased
        backRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        frontLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        //other two are 0
        backLeftSpeed = 0;
        frontRightSpeed = 0;
      }

      if (225>angle && angle>135){
        //right motors are increased, left are decreased
        backLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        frontLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        backRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        frontRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
      }

      if (angle == 225){
        //frontRight is increased
        frontRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        backLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        //other two are 0
        frontLeftSpeed = 0;
        backRightSpeed = 0;
      }

      if (315>angle && angle>225){
        //front motors are increased, back are decreased
        backLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        frontLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        backRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        frontRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
      }

      if (angle == 315) {
        //frontLeft is increased, rightBack is decreased
        frontLeftSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        backRightSpeed = ROTATION_COEFFICIENT*rotationHorizontal;
        //other 2 are 0
        frontRightSpeed = 0;
        backLeftSpeed = 0;
      }
    }
    bR.set(MOTOR_POWER*backRightSpeed);
    bL.set(MOTOR_POWER*backLeftSpeed);
    fR.set(MOTOR_POWER*frontRightSpeed);
    fL.set(MOTOR_POWER*frontLeftSpeed);

    //Super idol de xiao rong
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }

}