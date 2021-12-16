/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import static frc.robot.Constants.*;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;

/**
 *
 * This is the subsytem for our gyroscope
 * 
 */

public class Gyro extends SubsystemBase {

    // Initialize fields
    private PigeonIMU gyro;
    private double[] ypr = new double[3];

    public enum PigeonState {
        NoComm, Initializing, Ready, UserCalibration,
    };

    // Constructer
    public Gyro() {
        gyro = new PigeonIMU(11);
        PigeonIMU.GeneralStatus genStatus = new PigeonIMU.GeneralStatus();
        resetValues();
        calibrateGyro();
        getState();
    }

    public ErrorCode getStatus(PigeonIMU.GeneralStatus genStatus){
        return gyro.getGeneralStatus(genStatus);
    }

    //It caps at about 60-65 full rotations. Check this: https://www.ctr-electronics.com/downloads/pdf/Pigeon%20IMU%20User's%20Guide.pdf
    public double getYaw(){
        gyro.getYawPitchRoll(ypr);
        return (ypr[0]%360);
    }

    public void calibrateGyro()
    {
        gyro.enterCalibrationMode(CalibrationMode.BootTareGyroAccel);
    }

    public void resetValues()
    {
        gyro.configFactoryDefault();
    }

    public void getState()
    {
        if (gyro.getState() == PigeonIMU.PigeonState.Ready)
        {
            System.out.println ("Ready");
        }
        else if (gyro.getState() == PigeonIMU.PigeonState.UserCalibration)
        {
            System.out.println ("Calibrating");
        }
        else if (gyro.getState() == PigeonIMU.PigeonState.Initializing)
        {
            System.out.println ("Initializing");
        }
        else if (gyro.getState() == PigeonIMU.PigeonState.NoComm)
        {
            System.out.println ("No communication with pigeon gyro");
        }
    }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

}