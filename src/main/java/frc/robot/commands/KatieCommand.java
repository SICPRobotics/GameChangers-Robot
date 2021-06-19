package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class KatieCommand extends CommandBase {

    private final Timer timer = new Timer ();
    private final DriveTrain driveTrain;

    public KatieCommand(DriveTrain driveTrain){
        this.driveTrain = driveTrain;
        addRequirements(driveTrain);

    }

    @Override
    public void initialize() {
        timer.reset();
    }

    @Override
    public void execute() {
        driveTrain.cheesyDrive(0.4, 0, 1);

    }   

    @Override
    public boolean isFinished(){
        double time = timer.get();
        if (time > 1){
            return true;    

        } else {
            return false;
        }
    }


}
    
