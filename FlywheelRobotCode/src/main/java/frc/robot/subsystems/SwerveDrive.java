package frc.robot.subsystems;

import java.lang.reflect.Array;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrive extends SubsystemBase{

/** 
 * 1. We assume that we have the angle
 * 2. Get the translational velocity
 * 3. We get the rotational difference
 */

    public SwerveDrive(){

    } 
    
    public double[] translationalRotation(){
        
       return new double[0]; 
    }

    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }  
}
