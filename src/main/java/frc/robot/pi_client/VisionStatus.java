package frc.robot.pi_client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonMerge;

public class VisionStatus {
    static public class VisionRect {
        public int x;
        public int y;
        public int width;
        public int height;

        @JsonCreator
        public VisionRect() {

        }
        
        public VisionRect(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    static public class Point {
        public double x;
        public double y;

        @JsonCreator
        public Point() {

        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    @JsonCreator
    public VisionStatus() {

    }

    @JsonMerge
    public Point target;
    @JsonMerge
    public VisionRect bbox;
}