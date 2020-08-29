package frc.robot.commands.color_wheel.by_color;

import frc.robot.subsystems.ColorWheelSpinner;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class SpinToColorByColor extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ColorWheelSpinner spinner;

  public SpinToColorByColor(ColorWheelSpinner spinner) {
    this.spinner = spinner;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Got target color " + spinner.getTargetColor() + " from FMS");
    System.out.println("Currently on color " + spinner.getCurrentColor());
    spinner.setMotor(spinner.getSlicesTo(spinner.getTargetColor()) * 0.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    spinner.setMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return spinner.getTargetColor() != spinner.getCurrentColor();
  }
}
