package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.controller.LinearQuadraticRegulator;
import edu.wpi.first.wpilibj.estimator.KalmanFilter;
import edu.wpi.first.wpilibj.system.LinearSystem;
import edu.wpi.first.wpilibj.system.LinearSystemLoop;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpilibj.system.plant.LinearSystemId;
import edu.wpi.first.wpiutil.math.Nat;
import edu.wpi.first.wpiutil.math.VecBuilder;
import edu.wpi.first.wpiutil.math.numbers.N1;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class FlyWheel extends SubsystemBaseWrapper implements MotorSubsystem {
    private final TalonFX motor;
    private final double kMomentOfInertia = 0.0028125; // very very loose estimate
    private final double kGearing = 1;
    private final double kV = 0.023; //very very loose estimate
    private final double kA = 0.001; // alsp very very loose estimate
    private final double kMaxRadsPerSec = 600; //6380 rpm * 2Pi / 60sec

      // The plant holds a state-space model of our flywheel. This system has the following properties:
  //
  // States: [velocity], in radians per second.
  // Inputs (what we can "put in"): [voltage], in volts.
  // Outputs (what we can measure): [velocity], in radians per second.
    private final LinearSystem<N1, N1, N1> plant = LinearSystemId.identifyVelocitySystem(kV, kA);
    //private final LinearSystem<N1,N1,N1> plant = LinearSystemId.createFlywheelSystem(DCMotor.getNEO(1), kMomentOfInertia, kGearing);
    
      // The observer fuses our encoder data and voltage inputs to reject noise.
    private final KalmanFilter<N1, N1, N1> observer = new KalmanFilter<>(Nat.N1(), Nat.N1(), plant, 
    VecBuilder.fill(3.0), /* How accurate we think our model is */ 
        VecBuilder.fill(0.01), /* How accurate we think our encoder data is */ 
            0.020);
      // A LQR uses feedback to create voltage commands.
    private final LinearQuadraticRegulator<N1, N1, N1> controller = new LinearQuadraticRegulator<>( plant,
      VecBuilder.fill(8.0), // qelms. Velocity error tolerance, in radians per second. Decrease this to more heavily penalize state excursion, or make the controller behave more aggressively.
        VecBuilder.fill(12.0), // relms. Control effort (voltage) tolerance. Decrease this to more heavily penalize control effort, or make the controller less aggressive. 12 is a good starting point because that is the (approximate) maximum voltage of a battery.
            0.020); // Nominal time between loops. 0.020 for TimedRobot, but can be lower if using notifiers.
    
    private final LinearSystemLoop<N1, N1, N1> loop =
            new LinearSystemLoop<>(plant, controller, observer, 12.0, 0.020);
      
    public FlyWheel() {
        super();
        motor = new TalonFX(Constants.MotorSubsystems.FLYWHEEL_ID);
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
    }
    public double getOmega(){
        return (motor.getSelectedSensorVelocity() * 2 * Math.PI * (1 / 4096));
    }
    public void reset(){
        loop.reset(VecBuilder.fill(getOmega()));
    }
    
    public void turnOn(double velocity) {
        setMotor(velocity);
        System.out.println("SetFly");
    }

    public void turnOff() {
        setMotor(0);
    }
    public void setOff(){
        loop.setNextR(VecBuilder.fill(0));
    }
    public void setMotorOmega(double omega){
        loop.setNextR(omega);
        loop.correct(VecBuilder.fill(getOmega()));
        loop.predict(0.02);
        motor.set(ControlMode.Current, loop.getU(0));
    }
    public void setMotorMaxOmega(){
        System.out.println("MAX OMEGA");
        loop.setNextR(10);
        loop.correct(VecBuilder.fill(getOmega()));
        loop.predict(0.02);
        System.out.println(loop.getU(0));
        motor.set(ControlMode.PercentOutput, loop.getU(0) * 12);
        System.out.println(motor.getMotorOutputVoltage());
    }
    public void setMotor(final double value) {
        motor.set(ControlMode.PercentOutput, value);
        
    }
}
