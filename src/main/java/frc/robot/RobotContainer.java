/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Set;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.controllers.OperatorController;
import frc.robot.pi_client.PiClient;
import frc.robot.subsystems.DriveTrain;
import frc.robot.truth.Minitrue;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public final class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final Joystick joystick = new Joystick(0);

  private final OperatorController operatorController = new OperatorController(1);
  
  private final SubsystemContainer subs = new SubsystemContainer();
  private final Minitrue minitrue = new Minitrue();
  private final PiClient piClient = new PiClient(minitrue);
 
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    minitrue.onPoseUpdate(truth -> System.out.println("X: " + minitrue.getPose().getTranslation().getX() + "  Y: " + minitrue.getPose().getTranslation().getY() + "  Rot: " + minitrue.getPose().getRotation()));
    subs.driveTrain = new DriveTrain(minitrue);
    subs.driveTrain.setDefaultCommand(new DriveWithJoystick(subs.driveTrain, this::getJoystickY, this::getJoystickX, this::getJoystickAdjust));
    /*groundIntake = new GroundIntake();
    colorWheelSpinner = new ColorWheelSpinner();
    leftWinch = new LeftWinch();
    rightWinch = new RightWinch();
    pastaPuller = new PastaPuller();
    compressor = new Compessor();
    colorWheelPiston = new ColorWheelPiston();
    driveTrain.setDefaultCommand(new DriveWithJoystick(driveTrain, this::getJoystickY, this::getJoystickX, this::getJoystickAdjust));
    thumbButton = new JoystickButton(joystick, 2);
    gate = new Gate();
    hanger = new Hanger();
    lights = new Lights();
    //Sets lights to the alliance's color
    lights.setDefaultCommand(new SetLightsToColor(lights, lights.getColorForAlliance(DriverStation.getInstance().getAlliance())).perpetually());
    //cameras = new Cameras();
    hangerArm = new HangerArm();
    gyro = new ADXRS450_Gyro();
    SmartDashboard.putNumber("Auton Chooser", 0);*/
    
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
    JoystickButton button = new JoystickButton(joystick, 2);
    button.whileHeld(new PIDCommand(
      new PIDController(0.05, 0/*0.001*/, 0.020),
      () -> (piClient.getVisionStatus().bbox.x),
      320 / 2,
      output -> subs.driveTrain.cheesyDrive(0, Math.abs(output) < 0.1 ? 0 : (- Math.min(Math.abs(output), 0.5) * Math.signum(output)), -1),
      subs.driveTrain
    ));
    //thumbButton.toggleWhenPressed(new DriveWithJoystick(driveTrain, this::getJoystickY, this::getJoystickX, this::getJoystickAdjust, true));
    //GROUND INTAKE
    //new Trigger(() -> operatorController.triggers.right.get() > 0.1).whileActiveContinuous(new Toggle(groundIntake));
    //groundIntake.setDefaultCommand(new SetMotorContinuous(groundIntake, operatorController.sticks.left::getY));
    /*groundIntake.setDefaultCommand(new SetMotorContinuous(groundIntake, () -> 
    operatorController.sticks.left.getY() * Constants.GroundIntake.SPEED));
    
    new Trigger(() -> operatorController.triggers.right.get() > 0.1).whileActiveContinuous(
    new SetMotorContinuous(groundIntake, () -> Math.signum(operatorController.sticks.left.getY()) * Constants.GroundIntake.SNAP_SPEED));
    */

    /*//COLOR WHEEL
    //Rotate to color / rotate a number of times
    operatorController.buttons.RB.toggleWhenPressed(new SpinNumberOfTimes(colorWheelSpinner));
    operatorController.buttons.LB.toggleWhenPressed(new SpinToColor(colorWheelSpinner));
    
    //Manual left/right of color wheel
    operatorController.buttons.dPad.left
        .whileActiveContinuous(new NudgeMotor(colorWheelSpinner, -1 * Constants.ColorWheel.MANUAL_SPEED).perpetually());
    operatorController.buttons.dPad.right
        .whileActiveContinuous(new NudgeMotor(colorWheelSpinner, Constants.ColorWheel.MANUAL_SPEED).perpetually());

    //Extend up/down (toggle color wheel position)
    //operatorController.buttons.dPad.up.whenPressed(new ExtendPiston(colorWheelSpinner));
    operatorController.buttons.dPad.up.toggleWhenPressed(new ExtendPiston(colorWheelPiston));

    //HANGER ARM
    operatorController.buttons.Y.whileActiveContinuous(new StartEndCommand(() -> hangerArm.setMotor(0.4), () -> hangerArm.setMotor(0)).perpetually());
    operatorController.buttons.A.whileActiveContinuous(new StartEndCommand(() -> hangerArm.setMotor(-0.2), () -> hangerArm.setMotor(0)).perpetually());
    operatorController.buttons.X.whileActiveContinuous(new StartEndCommand(() -> hangerArm.setMotor(-0.1), () -> hangerArm.setMotor(0)).perpetually());
    
    // Calibrate 
    operatorController.buttons.B.whenPressed(new Calibrate(hangerArm));


    // WHINCH
    //operatorController.buttons.X.whileActiveContinuous(new ExtendHangerArm(hanger));

    // WINCH  INTAKE/OUTAKE SWITCH
    /*operatorController.buttons.back.whenPressed(new RunCommand(() -> { 
      groundIntake.setDefaultCommand(new DoNothing(groundIntake));
      pastaPuller.setDefaultCommand(new DoNothing(pastaPuller));
      leftWinch.setDefaultCommand(new SetMotorContinuous(leftWinch, () -> operatorController.sticks.left.getY()));
      rightWinch.setDefaultCommand(new SetMotorContinuous(rightWinch,() -> operatorController.sticks.right.getY()));
    }));

    operatorController.buttons.start.whenPressed(new RunCommand(() -> { 
      groundIntake.setDefaultCommand(new SetMotorContinuous(groundIntake, () -> 
      operatorController.sticks.left.getY() * Constants.GroundIntake.SPEED));
      new Trigger(() -> operatorController.triggers.right.get() > 0.1).whileActiveContinuous(
      new SetMotorContinuous(groundIntake, () -> Math.signum(operatorController.sticks.left.getY()) * Constants.GroundIntake.SNAP_SPEED));
      
      pastaPuller.setDefaultCommand(new SetMotorContinuous(pastaPuller, () -> 
      operatorController.sticks.right.getY() * Constants.PastaPuller.SPEED));
      new Trigger(() -> operatorController.triggers.right.get() > 0.1).whileActiveContinuous(
      new SetMotorContinuous(pastaPuller, () -> Math.signum(operatorController.sticks.right.getY()) * Constants.PastaPuller.SNAP_SPEED));
    }));
*/

    /**
     * Blockers
     * These block the subsystems to keep other commands from using them
     *//*
    Command blockIntakes = new RunCommand(() -> {}, groundIntake, pastaPuller).perpetually();
    
    /**
     * These commands run the subsystems.
     *//*
    Command runLeftWinch = new RunCommand(() -> {
      double stickValue = operatorController.sticks.left.getY();
      leftWinch.setMotor(stickValue);
      System.out.println("Left winch!");
    }).perpetually();
    Command runRightWinch = new RunCommand(() -> {
      double stickValue = operatorController.sticks.right.getY();
      rightWinch.setMotor(stickValue);
    }).perpetually();

    //Parallel command groups run different commands at a time.
    Command winchesEnabled = new ParallelCommandGroup(
      blockIntakes,
      runLeftWinch,
      runRightWinch
    );

    //Binding these to buttons
    operatorController.buttons.back.cancelWhenPressed(winchesEnabled);
    operatorController.buttons.start.whenPressed(winchesEnabled);

    //Setting defaults
    pastaPuller.setDefaultCommand(new RunCommand(() -> {
      double stickValue = operatorController.sticks.right.getY();
      if (operatorController.triggers.right.get() > 0.1) {
        pastaPuller.setMotor(Math.signum(stickValue) * Constants.PastaPuller.SNAP_SPEED);
      } else {
        pastaPuller.setMotor(stickValue);
      }
    }, pastaPuller).perpetually());

    groundIntake.setDefaultCommand(new RunCommand(() -> {
      double stickValue = operatorController.sticks.left.getY();
      if (operatorController.triggers.right.get() > 0.1) {
        groundIntake.setMotor(Math.signum(stickValue) * Constants.GroundIntake.SNAP_SPEED);
      } else {
        groundIntake.setMotor(stickValue);
      }
    }, groundIntake).perpetually());

    //PASTA PULLER
    // new Trigger(() -> operatorController.triggers.left.get() > 0.1).whileActiveContinuous(new PullPasta(pastaPuller));
    //pastaPuller.setDefaultCommand(new SetMotorContinuous(pastaPuller, operatorController.sticks.right::getY));
    //new Trigger(() -> operatorController.triggers.left.get() > 0.1).whileActiveContinuous(new PullPasta(pastaPuller));
    //pastaPuller.setDefaultCommand(new SetMotorContinuous(pastaPuller, operatorController.sticks.right::getY));
    /*
    pastaPuller.setDefaultCommand(new SetMotorContinuous(pastaPuller, () -> 
        operatorController.sticks.right.getY() * Constants.PastaPuller.SPEED));
    new Trigger(() -> operatorController.triggers.right.get() > 0.1).whileActiveContinuous(
        new SetMotorContinuous(pastaPuller, () -> Math.signum(operatorController.sticks.right.getY()) * Constants.PastaPuller.SNAP_SPEED));
    */


    //GATE
    //operatorController.buttons.dPad.down.toggleWhenPressed(new FunctionalCommand(gate::extend, () -> { }, b -> gate.retract(), () -> false, gate));
    //new Trigger(() -> operatorController.triggers.left.get() > 0.1).toggleWhenActive(new ExtendPiston(gate));
    //new Trigger(gate::isUp).whileActiveContinuous(new SetLightsToColor(lights, Lights.LightsColor.ORANGE).perpetually());
  }

  public double getJoystickX() {
    return this.joystick.getRawAxis(Constants.Joystick.X_AXIS);
  }

  public double getJoystickY() {
    return this.joystick.getRawAxis(Constants.Joystick.Y_AXIS);
  }
  
  public double getJoystickZ() {
    return this.joystick.getRawAxis(2);
  }

  public double getJoystickAdjust() {
    return this.joystick.getRawAxis(Constants.Joystick.ADJUST_AXIS);
  }

  
  // * @return the command to run in autonomous
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return new AutonomusCommand(driveTrain, gate, pastaPuller, hangerArm, gyro);
    return new Command(){
    
      @Override
      public Set<Subsystem> getRequirements() {
        // TODO Auto-generated method stub
        return null;
      }
    };
  }
  
}