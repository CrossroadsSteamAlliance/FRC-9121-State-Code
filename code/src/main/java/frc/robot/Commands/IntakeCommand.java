package frc.robot.Commands;

import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.IntakeConstats;
import frc.robot.Subsystems.IntakeSubsystem;

public class IntakeCommand extends Command{
    public static enum Speed {FULL_IN, HALF_IN, FULL_OUT, HALF_OUT}
    private Speed state;

    IntakeSubsystem subsystem;

    public IntakeCommand(IntakeSubsystem subsystem, Speed state){
        this.subsystem = subsystem;
        this.state = state;
        
        addRequirements(this.subsystem);
    }

    @Override
    public void initialize() {
       switch (state) {
        case FULL_IN:
            subsystem.intake(IntakeConstats.kIntakeVelocity);
            break;

        case HALF_IN:
            subsystem.intake(IntakeConstats.kIntakeVelocity / 2);
            break;
       
        case FULL_OUT:
            subsystem.outtake(IntakeConstats.kIntakeVelocity);
            break;
        
        case HALF_OUT:
            subsystem.outtake(IntakeConstats.kIntakeVelocity / 2);
            break;
        
        default:
            subsystem.stopIntake();
            break;
       }
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopIntake();
    }

    @Override
    public boolean isFinished() {
      return false;
    }

}