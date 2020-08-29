
package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.PastaPuller;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Gate;
import frc.robot.subsystems.HangerArm;
import frc.robot.subsystems.RangeFinder;

public class AutonomusCommand extends CommandBase {
  private final DriveTrain drive;
  private Timer timer;
  // private RangeFinder ultrasonic = new RangeFinder();
  private Gate gate;
  private PastaPuller pastaPuller;
  private HangerArm hanger;
  private double delay;
  private Gyro gyro;
  public AutonomusCommand(final DriveTrain drive, final Gate gate, final PastaPuller hopper, final HangerArm hanger, final Gyro gyro) {
    this.drive = drive;
    // Use addRequirements() here to declare subsystem dependencies.
    this.gate = gate;
    this.pastaPuller = hopper;  
    this.hanger = hanger;
    this.gyro = gyro;
    addRequirements(drive, gate, hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public final void initialize() {
    gate.pistonReverse();
    pastaPuller.stopPulling();
    new Calibrate(hanger).schedule();
    timer = new Timer();
    delay = SmartDashboard.getNumber("Autonomous Delay", 0);
    timer.start();
    gyro.calibrate();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public final void execute() {
    double currentTime = timer.get() - delay;
    if(SmartDashboard.getBoolean("DriveForward", false)){
      if(currentTime < 4.0){
        drive.cheesyDrive(-0.6, 0, -1);
      }
    }
    if(SmartDashboard.getBoolean("Deposit Balls", false)){
      if(currentTime > 4.0){
        gate.pistonForward();
        pastaPuller.setMotor(1);
      }
    }
    if(SmartDashboard.getBoolean("BackUp", false)){
      if(currentTime > 8.0){
        drive.cheesyDrive(0.6, 0, -1);
      }
    }
    if(SmartDashboard.getBoolean("Turn Right", false)){
      if(currentTime > 8.0){
        if(gyro.getAngle() < 90){
          drive.cheesyDrive(0, 0.6 , -1);
        }
        else{
          drive.cheesyDrive(-0.6, 0, -1);
        }
      }
    }
    if(SmartDashboard.getBoolean("Turn Left", false)){
      if(currentTime > 8.0){
        if(gyro.getAngle() < 270){
          drive.cheesyDrive(0, -0.6 , -1);
        }
        else{
          drive.cheesyDrive(-0.6, 0, -1);
        }
      }
    }
    if(SmartDashboard.getBoolean("Score With Delay", false)){
        if(currentTime > delay){
          if(currentTime < 4 + delay){
            drive.cheesyDrive(-0.6, 0, -1);
          }
          else{
            drive.cheesyDrive(-0.4, 0, -1);
            gate.pistonForward();
            pastaPuller.setMotor(1);
          }
        }    
    }
  }


/*
    if(SmartDashboard.getNumber("Auton Chooser", 0) == 0)
    {
      if (currentTime < 2.0) {
      drive.cheesyDrive(-0.6, 0.0, -1);
      }
      else{
        drive.cheesyDrive(0, 0, 0);
      }
    }
    else if (SmartDashboard.getNumber("Auton Chooser", 0) == 1){
      if (currentTime < 4.0) {
        drive.cheesyDrive(-0.6, 0.0, -1);
      }
      else {
        drive.cheesyDrive(-0.4, 0.0, -1);
        gate.pistonForward();
        pastaPuller.setMotor(1);
      }
    }
    else if (SmartDashboard.getNumber("Auton Chooser", 0) == 2){
      // Back Up Auton 
      if (currentTime < 4.0) {
        drive.cheesyDrive(-0.6, 0.0, -1);
      }
      else if(currentTime < 8 && currentTime > 4){
        drive.cheesyDrive(-0.4, 0.0, -1);
        gate.pistonForward();
        pastaPuller.setMotor(1);
      }
      else if (currentTime > 8 && currentTime < 13){
        drive.cheesyDrive(0.6, 0.0, -1);
      }
      else{
        drive.cheesyDrive(0, 0, 0);
      }
      
    }
    else{
        System.out.println("Auton Nothing");

    }
  }
*/
  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
    timer.reset();
    System.out.println("Auton End");
    pastaPuller.setMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public final boolean isFinished() {
    return timer.get() > 15 ? true : false;
  }
}
