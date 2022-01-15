package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Just to clarify, this is shooter
// oops - Yifei
public class Sheeesh extends SubsystemBase{
    public boolean initiateSheesh;
    public int timesSheeshed;
    public Sheeesh(){
            initiateSheesh = true;
            timesSheeshed = 0;
    }

    public void Sheeshing(boolean canSheesh){
        if (canSheesh){
            timesSheeshed++;
        }
    }
    
}
