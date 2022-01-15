package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.pheonix.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Catapault extends SubsystemBase
{
    private DoubleSolenoid piston1;
    private DoubleSolenoid piston2;

    public Catapault()
    {
        piston1 = new DoubleSolenoid(CATAPAULT_PISTON_PORT_1, CATAPAULT_PISTON_PORT_2);
        piston2 = new DoubleSolenoid(CATAPAULT_PISTON_PORT_3, CATAPAULT_PISTON_PORT_4);
    }

    public void extendAll()
    {
        piston1.set(kForward);
        piston2.set(kForward);
    }

    public void retractAll()
    {
        piston1.set(kOff);
        piston2.set(kOff);
    }

    public void togglePistons()
    {
        piston1.toggle();
        piston2.toggle();
    }


}