package frc.robot.Autonomous;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Commands.ElevatorCommand;
import frc.robot.Commands.IntakeCommand;
import frc.robot.Commands.PivotCommand;
import frc.robot.Commands.RotateCommand;
import frc.robot.Subsystems.CommandSwerveDrivetrain;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.PivotSubsystem;
import frc.robot.Subsystems.RotateSubsystem;

public class Autonomous extends Command {

    private SendableChooser<Command> autoChooser;
    
    public Autonomous(ElevatorSubsystem elevator, IntakeSubsystem intake, RotateSubsystem rotate, PivotSubsystem pivot){
        autoChooser = AutoBuilder.buildAutoChooser();
        SmartDashboard.putData(autoChooser);
        configNamedCommands(elevator, intake, rotate, pivot);
    }

    private void configNamedCommands(ElevatorSubsystem elevator, IntakeSubsystem intake, RotateSubsystem rotate, PivotSubsystem pivot){
        NamedCommands.registerCommand("Elevator L4 Score", new ElevatorCommand(elevator, ElevatorCommand.Position.L4)
            .withTimeout(0.5)
            .andThen(new RotateCommand(rotate, RotateCommand.Position.L))
            .alongWith(new PivotCommand(pivot, PivotCommand.Positions.L4))
            .withTimeout(1)
            .andThen(new ElevatorCommand(elevator, ElevatorCommand.Position.ZERO))
            .andThen(new PivotCommand(pivot, PivotCommand.Positions.ZERO)));

        NamedCommands.registerCommand("Substation Pickup", new ElevatorCommand(elevator, ElevatorCommand.Position.SUBSTATION)
        .alongWith(new PivotCommand(pivot, PivotCommand.Positions.SUBSTATION))
        .alongWith(new IntakeCommand(intake, IntakeCommand.Speed.FULL_IN))
        .withTimeout(2)
        .andThen(new IntakeCommand(intake, IntakeCommand.Speed.STOP)));

    }
    

    public Command getAutonomousCommand(){
        return autoChooser.getSelected();
    }
}
