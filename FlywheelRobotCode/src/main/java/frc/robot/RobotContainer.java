
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.triggers.*;

import static frc.robot.Constants.*;

public class RobotContainer {

  // JOYSTICKS
  public final Joystick driver = new Joystick(DRIVER_CONTROLLER);
  public final Joystick operator = new Joystick(OPERATOR_CONTROLLER);

  // == BUTTONS == //

  // Drivetrain
  public final JoystickButton modeSwitchButton = new JoystickButton(driver, RIGHT_BUMPER); // stray kids going FAST or SLOW

  /**

  // Vision
  public final JoystickButton visionAlignBall = new JoystickButton(driver, BUTTON_A);
  public final JoystickButton visionAlignGoal = new JoystickButton(driver, BUTTON_Y);

  // Intake
  public final JoystickButton intakeButton = new JoystickButton(operator, BUTTON_A),
      outttakeButton = new JoystickButton(operator, BUTTON_Y),
      raiseIntakeButton = new JoystickButton(operator, BUTTON_X);

  // Storage
  public final JoystickButton storageOverrideButton = new JoystickButton(operator, START_BUTTON);

  // Shooter
  public JoystickButton toggleShooterButton = new JoystickButton(operator, RIGHT_BUMPER);

  public JoystickTrigger shootCloseTrigger = new JoystickTrigger(operator, LEFT_TRIGGER_AXIS);
  public JoystickTrigger shootFarTrigger = new JoystickTrigger(operator, RIGHT_TRIGGER_AXIS);

  */

  // SUBSYSTEMS
  public final Drivetrain DRIVETRAIN = new Drivetrain(driver);

  // COMMANDS

  // Shooter

  // Drivetrain
  public final StartEndCommand modeSwitch = new StartEndCommand(() -> DRIVETRAIN.modeSlow(),
      () -> DRIVETRAIN.modeFast(), DRIVETRAIN);

  // Intake

  // for storage trigger


  // Storage


  // STORAGE TRIGGER

  // === AUTO === //
  // private final Command moveForward = new MoveCommand(DRIVETRAIN, 20, .5);                     // TODO: FIX THIS
  // private final TestAutoCommandGroup debugAuto = new TestAutoCommandGroup(DRIVETRAIN, VISION);
  // private final FailsafeAuto failsafe = new FailsafeAuto(DRIVETRAIN, SHOOTER, STORAGE);
  // private final TrenchAuto trench = new TrenchAuto(DRIVETRAIN, SHOOTER, STORAGE, INTAKE, VISION);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Climber
    
  DRIVETRAIN.setDefaultCommand(
    new RunCommand(
      () -> DRIVETRAIN.arcadeDrive(driver.getRawAxis(FORWARD_AXIS_LEFT), -driver.getRawAxis(HORIZ_AXIS_RIGHT)),
      DRIVETRAIN
  ));

    // Shooter
    // toggleShooterButton.toggleWhenPressed(new EnableShooter(SHOOTER, STORAGE));

    // Drivetrain
    modeSwitchButton.whenHeld(modeSwitch);
  }
}