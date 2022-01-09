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
  public static final double MOTOR_POWER = 0.8;
  // It may be more logical to use no SPEED MULTIPLIER and rather just depend on the controller input(investigate)
  public static final double SPEED_MULTIPLIER = 0.5;
  public static final double ROTATION_COEFFICIENT= 1-(SPEED_MULTIPLIER); //This can take a max value of 1-(1/SPEED_DIVIDER)
  public static final double ONLY_ROTATION_MULTIPLIER = ROTATION_COEFFICIENT; // For smooth transition between only rotation to rotation & trans.
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
    double r = (Math.sqrt(horizontal*horizontal + vertical*vertical)*SPEED_MULTIPLIER);
    //This makes the maximum power 1/Speed Divider. So, we can essentially add to some of the motors and
    // get it to rotate without going above 1 by accident, which would just turn to 1. (Probably)

    //Here the initial speeds are set to the value r - calculated above -
    double backRightSpeed = r;
    double backLeftSpeed = r;
    double frontRightSpeed = r;
    double frontLeftSpeed = r;

    //If the horizontal and vertical are enough to make it move translationally then this will compute.
    // I commented out the decrease operations in order to decrease skid. (NEED TO TEST)

    /*
    Physically an object will mantain its energy or state if there is sufficient force to sustain it.
    If the net force changes, the translational state of the object might change. Therefore, when doing rotation,
    it is important that to decrease the power on two wheels, while increasing the power of the other two. 
    
    Notice also that the operation below account for both + and - rotationalHorizontal.
     */
    if (Math.sqrt(horizontal*horizontal + vertical*vertical)>=CONTROLLER_SENSITIVITY){
      if ((45>angle && angle>0)||(angle>315)){
        backLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        frontLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //
        backRightSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
        frontRightSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
      }
      
      if (angle == 45){
        backLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //
        frontRightSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
      }

      if (135>angle && angle>45){
        backRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        backLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //
        frontLeftSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
        frontRightSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
      }

      if (angle == 135){
        backRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //
        frontLeftSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
      }

      if (225>angle && angle>135){
        backRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        frontRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //
        backLeftSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
        frontLeftSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
      }

      if (angle == 225){
          frontRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
          //
          backLeftSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
      }

      if (315>angle && angle>225){
        frontLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        frontRightSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
        //
        backLeftSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
        backRightSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
      }

      if (angle == 315) {
          frontLeftSpeed += ROTATION_COEFFICIENT*rotationHorizontal;
          //
          backRightSpeed -= ROTATION_COEFFICIENT*(rotationHorizontal);
      }
    }
    //This part is for no translation. There are always opposite speeds but other 2 speeds are 0 in order
    // to just rotate without translation.
    else if(Math.abs(rotationHorizontal)>=CONTROLLER_SENSITIVITY){
      //set the motor powers to 
      backRightSpeed = -rotationHorizontal*ONLY_ROTATION_MULTIPLIER;
      frontRightSpeed = -rotationHorizontal*ONLY_ROTATION_MULTIPLIER;
      backLeftSpeed = -rotationHorizontal*ONLY_ROTATION_MULTIPLIER;
      frontLeftSpeed = -rotationHorizontal*ONLY_ROTATION_MULTIPLIER;
      
    }
    bR.set(MOTOR_POWER*backRightSpeed);
    bL.set(MOTOR_POWER*backLeftSpeed);
    fR.set(MOTOR_POWER*frontRightSpeed);
    fL.set(MOTOR_POWER*frontLeftSpeed);

    //amogus
  }

  public void autoTranslational(double x, double y, double totalDistance){
    double initialPosition = bRMotor.getSelectedSensorPosition();
    while((Math.PI*WHEEL_DIAMETER_INCHES*360*(bRMotor.getSelectedSensorPosition()-initialPosition)/2048)<totalDistance){
      spinMotors(x, -y, 0, 0);
    }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }

}