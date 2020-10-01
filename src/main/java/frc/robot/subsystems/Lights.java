package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.SubsystemBaseWrapper;
import frc.robot.truth.Minitrue;

public final class Lights extends SubsystemBaseWrapper {
    private final Spark lightsFakeMotor;

    public enum LightsColor {
        HOT_PINK(0.57),
        DARK_RED(0.59),
        RED(0.61),
        RED_ORANGE(0.63),
        ORANGE(0.65),
        GOLD(0.67),
        YELLOW(0.69),
        LAWN_GREEN(0.71),
        LIME(0.73),
        DARK_GREEN(0.75),
        GREEN(0.77),
        BLUE_GREEN(0.79),
        AQUA(0.81),
        SKY_BLUE(0.83),
        DARK_BLUE(0.85),
        BLUE(0.87),
        BLUE_VIOLET(0.89),
        VIOLET(0.91),
        WHITE(0.93),
        GRAY(0.95),
        DARK_GRAY(0.97),
        BLACK(0.99),
        
        COLOR_WAVES_LAVA(-0.39),
        COLOR_WAVES_OCEAN(-0.41);

        private final double value;
        private LightsColor(final double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    public Lights(Minitrue minitrue) {
        super(minitrue);
        lightsFakeMotor = new Spark(0);
    }

    public void setColor(final LightsColor color) {
        lightsFakeMotor.set(color.getValue());
        System.out.println("Set lights color to " + color.getValue() + " (" + color + ")");
    }

    public void setColor(final Alliance alliance) {
        setColor(getColorForAlliance(alliance));
    }

    public LightsColor getColorForAlliance(final Alliance alliance) {
        if (alliance == Alliance.Blue) {
            return LightsColor.COLOR_WAVES_OCEAN;
        } else if (alliance == Alliance.Red) {
            return LightsColor.COLOR_WAVES_LAVA;
        } else {
            return LightsColor.RED;
        }
    }

    /*public void allainceColor() {
        if (DriverStation.getInstance().getAlliance() == Alliance.Blue) {
            lights.set(-0.41);
        } else if (DriverStation.getInstance().getAlliance() == Alliance.Red) {
            lights.set(-0.39);
        } else {
           lights.set(0.0);
        }   
    }*/
}