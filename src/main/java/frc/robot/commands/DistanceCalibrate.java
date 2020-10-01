
public final class DistanceCalibrate extends CommandBase {
    private DriveTrain drive;
    private Rangefinder ultrasonic;
    private double initalDistance;
    
    public DistanceCalibrate(final DriveTrain drive, final RangeFinder ultrasonic) {
        this.drive = drive;
        this.ultrasonic = ultrasonic;
        System.out.println("DistanceCalibrate");
        addRequirements(drive, ultrasonic);
    }

    @Override
    public void initialize() {
        initalDistance = ultrasonic.getCmDistance();
    }

    @Override
    public void execute() {
        drive.cheesyDrive(1, 0, 1);

    }
  
    @Override
    public boolean isFinished() 
    {
        return (ultrasonic.getCmDistance() > 100);
    }
    @Override
    public void end(final boolean interrupted) {
        drive.cheesyDrive(0,0,0);
        System.out.println("Right " + drive.getRightDistance() + " Left " + drive.getLeftDistance());
    }
}