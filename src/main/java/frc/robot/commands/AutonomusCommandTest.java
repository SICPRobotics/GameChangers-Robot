package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutonomusCommandTest extends CommandBase {
  private final DriveTrain drive;
  public AutonomusCommandTest(final DriveTrain drive) {
    this.drive = drive;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public final void initialize() {
    //drive.resetDriveEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public final void execute() {
    if(drive.getPose().getTranslation().getX() != 2){
        drive.cheesyDrive(0.4, 0, -1);
    }
    drive.cheesyDrive(0, 0, 0);
  }
    // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public final boolean isFinished(){ 
    return false;
  }
}