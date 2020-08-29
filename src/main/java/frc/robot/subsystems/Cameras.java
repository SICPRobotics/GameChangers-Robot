package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.SubsystemBaseWrapper;

public class Cameras extends SubsystemBaseWrapper {
    public Cameras() {
        UsbCamera cam0 = CameraServer.getInstance().startAutomaticCapture(0);
        UsbCamera cam1 = CameraServer.getInstance().startAutomaticCapture(1);
        //UsbCamera cam2 = CameraServer.getInstance().startAutomaticCapture(2);

        VideoMode vms[] = cam0.enumerateVideoModes();
        cam0.setResolution(320, 240);
        cam1.setResolution(160, 120);
        //cam2.setResolution(320, 240);
        cam0.setFPS(15);
        cam1.setFPS(6);
        cam0.setPixelFormat(PixelFormat.kMJPEG);
        //cam2.setFPS(15);

//        System.out.println(cam0.getConfigJson());
//        System.out.println(cam1.getConfigJson());
//        for(int cnt = 0; cnt<vms.length; cnt++) {
//            printVideoMode(vms[cnt]);
//        }
    }
//    void printVideoMode(VideoMode vm) {
//        System.out.println("fps: " + vm.fps + " h: "+vm.height + " w: "+ vm.width +" pixelfmt: " + vm.pixelFormat);
//    }
}