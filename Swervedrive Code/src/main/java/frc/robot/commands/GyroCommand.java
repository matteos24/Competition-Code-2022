package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gyro;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;

public class GyroCommand extends CommandBase{
    
    private Gyro gyro;
    private double[] ypr;
    private double yaw;

    public GyroCommand(Gyro Gyro){
        addRequirements(Gyro);
        this.gyro = Gyro;
    }

    @Override
    public void initialize(){
        gyro.resetValues();
        gyro.calibrateGyro();
        gyro.getState();
    }

    @Override
    public void execute(){
        System.out.println("Yaw: " + gyro.getYaw());
        yaw = (gyro.getYaw() % 360);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}   
