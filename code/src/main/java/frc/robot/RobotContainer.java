// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Autonomous.Autonomous;
import frc.robot.Commands.ElevatorCommand;
import frc.robot.Commands.IntakeCommand;
import frc.robot.Commands.PivotCommand;
import frc.robot.Commands.RotateCommand;
import frc.robot.Constants.IntakeConstats;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.PivotSubsystem;
import frc.robot.Subsystems.RotateSubsystem;

public class RobotContainer extends Autonomous {

  ElevatorSubsystem elevator;
  IntakeSubsystem intake;
  PivotSubsystem pivot;
  RotateSubsystem rotate;

  CommandXboxController xboxControllerOP = new CommandXboxController(0);
  CommandGenericHID launchpadHID = new CommandGenericHID(1);


  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    //Intake Controls (Operator)
    xboxControllerOP.rightTrigger(0.25).onTrue(new IntakeCommand(intake, IntakeCommand.Speed.FULL_IN));
    xboxControllerOP.leftTrigger(0.25).onTrue(new IntakeCommand(intake, IntakeCommand.Speed.HALF_OUT));

    //Elevator Controls (Operator)
    xboxControllerOP.povUp().onTrue(new ElevatorCommand(elevator, ElevatorCommand.Position.L4)); //L4 Elevator Scoring
    xboxControllerOP.povRight().onTrue(new ElevatorCommand(elevator, ElevatorCommand.Position.L3)); //L3 Elevator Scoring
    xboxControllerOP.povDown().onTrue(new ElevatorCommand(elevator, ElevatorCommand.Position.L2)); //L2 Elevator Scoring
    xboxControllerOP.povLeft().onTrue(new ElevatorCommand(elevator, ElevatorCommand.Position.ZERO)); //Ground Elevator Scoring

    //Intake Rotate Controls (Operator)
    xboxControllerOP.leftBumper().onTrue(new RotateCommand(rotate, RotateCommand.Position.L));
    xboxControllerOP.rightBumper().onTrue(new RotateCommand(rotate, RotateCommand.Position.R));
    xboxControllerOP.rightBumper().and(() -> xboxControllerOP.leftBumper().getAsBoolean()).onTrue(new RotateCommand(rotate, RotateCommand.Position.ZERO));

    //Intake Pivot Controls (Operator)
    xboxControllerOP.x().onTrue(new PivotCommand(pivot, PivotCommand.Positions.L1));
    xboxControllerOP.b().onTrue(new PivotCommand(pivot, PivotCommand.Positions.L23));
    xboxControllerOP.y().onTrue(new PivotCommand(pivot, PivotCommand.Positions.L4));
    xboxControllerOP.a().onTrue(new PivotCommand(pivot, PivotCommand.Positions.GROUND));
    xboxControllerOP.a().and(() -> xboxControllerOP.b().getAsBoolean()).onTrue(new PivotCommand(pivot, PivotCommand.Positions.ZERO));
  }

  public Command getAutonomousCommand() {
    return super.getAutonomousCommand();
  }
}
