
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;
import frc.robot.truth.Minitrue;

public class Compessor extends SubsystemBaseWrapper {
    private final Compressor compressor;
    
    public Compessor(Minitrue minitrue) {
        super(minitrue);
        compressor = new Compressor(Constants.Compressor.COMPRESSOR_ID);
       
        compressor.setClosedLoopControl(true);
    }
}