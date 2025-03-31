package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.PivotSubsystem;

public class PivotCommand  extends Command{
    public static enum Positions{ZERO, L1, L23, L4, GROUND}
    private Positions pose;
    PivotSubsystem subsystem;

    public PivotCommand(PivotSubsystem subsystem, Positions pose){
        this.subsystem = subsystem;
        this.pose = pose;
        addRequirements(subsystem);
    }

     @Override
    public void initialize() {
       switch (pose) {
        
        default:
            subsystem.stopPivot();
            break;
       }
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stopPivot();
    }

    @Override
    public boolean isFinished() {
      return false;
    }

}
