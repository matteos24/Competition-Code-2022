package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gyro;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;

public class Translate extends CommandBase{
    
    private double x, y, distance;

    
    public Translate(double x, double y, double d){
        this.x = x;
        this.y = y;
        this.distance = d;
    }



    @Override
    public void initialize(){
        
    }

    @Override
    public void execute(){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}   
