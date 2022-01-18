package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Catapault;

public class CatapaultCommand extends CommandBase
{
   private Catapault catapault;
   
    public CatapaultCommand(Catapault catapault) {
        addRequirements(catapault);
        this.catapault = catapault;
        catapault.retractAll();
    }

    @Override
    public void initialize(){
        catapault.togglePistons();
    }

    @Override
    public void execute()
    {

    }

    @Override
    public boolean isFinished(){
        return false;
    }

    
}