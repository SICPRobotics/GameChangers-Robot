package frc.robot.pi_client;

import com.fasterxml.jackson.annotation.JsonMerge;

public class VisionStatus {
    static public class VisionRect {
        public int x;
        public int y;
        public int width;
        public int height;

        public VisionRect(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    @JsonMerge
    public VisionRect bbox;
}