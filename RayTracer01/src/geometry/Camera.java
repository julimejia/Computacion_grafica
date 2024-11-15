package geometry;

import math.UVN4x4;
import math.Vector4;
import display.Scene;

public class Camera {
    Vector4 lookAt;
    public Vector4 cameraPosition;
    Vector4 up;
    //ObjectTransformation ot;
    public double theta;
    public double phi;
    public double radius = 1000;
    public UVN4x4 uvn;
    public static double DELTA_THETA = 10 * Math.PI / 180;
    public static double DELTA_PHI = 10 * Math.PI / 180;
    public static double DELTA_RADIUS = 25;
    public static boolean DEBUG = false;

    /*
    public Camera(Vector4 lookAt, Vector4 cameraPosition, Vector4 up) {
        this.lookAt = lookAt;
        this.cameraPosition = cameraPosition;
        this.up = up;
    }
    */
    /*
    public Camera(ObjectTransformation ot) {
        this.ot = ot;
        this.theta = 0;
        this.phi = 0;    
        double projectdR = Math.cos(phi) * radius;
        double y = Math.sin(phi) * radius + ot.centerY;
        double x = Math.sin(theta) * projectdR + ot.centerX;
        double z = Math.cos(theta) * projectdR + ot.centerZ;
        cameraPosition = new Vector4(x, y, z);
    }
    */

    public Camera() {
        this.theta = 0;
        this.phi = 0;    
        double projectdR = Math.cos(phi) * radius;
        double y = Math.sin(phi) * radius + Scene.centerY;
        double x = Math.sin(theta) * projectdR + Scene.centerX;
        double z = Math.cos(theta) * projectdR + Scene.centerZ;
        cameraPosition = new Vector4(x, y, z);
    }

    public UVN4x4 createUVN() {
        if(DEBUG) System.out.println("theta: " + theta);
        this.lookAt = new Vector4(Scene.centerX, Scene.centerY, Scene.centerZ);
        double projectdR = Math.cos(phi) * radius;
        double y = Math.sin(phi) * radius + Scene.centerY;
        double x = Math.sin(theta) * projectdR + Scene.centerX;
        double z = Math.cos(theta) * projectdR + Scene.centerZ;
        cameraPosition = new Vector4(x, y, z);
        this.up = new Vector4(0, 1, 0);
        //UVN4x4 uvn = new UVN4x4(cameraPosition, lookAt, up);
        this.uvn = new UVN4x4(cameraPosition, lookAt, up);
        if(DEBUG) System.out.println("UVN: " + uvn);
        return uvn;
    }   

    public Vector4 cameraDirection() {
        return Vector4.subtract(lookAt, cameraPosition);
    }
}
