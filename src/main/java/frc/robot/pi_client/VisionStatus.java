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
            set(x, y, width, height);
        }

        public void set(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void set(VisionRect rect) {
            this.x = rect.x;
            this.y = rect.y;
            this.width = rect.width;
            this.height = rect.height;
        }
    }

    static public class Point {
        public double x;
        public double y;

        @JsonCreator
        public Point() { }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void set(org.opencv.core.Point p) {
            this.x = p.x;
            this.y = p.y;
        }
    }

    @JsonMerge
    public boolean targetFound = false;
    @JsonMerge
    public Point target = new Point();
    @JsonMerge
    public VisionRect bbox = new VisionRect();
}