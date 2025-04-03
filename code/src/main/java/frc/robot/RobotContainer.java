// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import static edu.wpi.first.units.Units.*;

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
import frc.robot.Subsystems.CommandSwerveDrivetrain;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.PivotSubsystem;
import frc.robot.Subsystems.RotateSubsystem;
import frc.robot.generated.TunerConstants;

public class RobotContainer {
  private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
  private double MaxAngularRate = RotationsPerSecond.of(1.5).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

  public final ElevatorSubsystem elevator = new ElevatorSubsystem();
  public final IntakeSubsystem intake = new IntakeSubsystem();
  public final PivotSubsystem pivot = new PivotSubsystem();
  public final RotateSubsystem rotate = new RotateSubsystem();
  private final Autonomous auto = new Autonomous(elevator, intake, rotate, pivot);
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain(); 

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.25).withRotationalDeadband(MaxAngularRate * 0.2) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

  CommandXboxController xboxControllerD = new CommandXboxController(0);
  CommandXboxController xboxControllerOP = new CommandXboxController(1);


  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    drivetrain.setDefaultCommand(
      // Drivetrain will execute this command periodically
      drivetrain.applyRequest(() ->
          drive.withVelocityX(xboxControllerD.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
              .withVelocityY(xboxControllerD.getLeftX() * MaxSpeed) // Drive left with negative X (left)
              .withRotationalRate(xboxControllerD.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
      )
  );

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
    return auto.getAutonomousCommand();
  }
}
