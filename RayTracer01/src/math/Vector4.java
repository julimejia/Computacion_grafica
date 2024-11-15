package math;

public class Vector4 {
    public double [] vector = new double[4];

    public Vector4(double x, double y, double z) {
        vector[0] = x;
        vector[1] = y;
        vector[2] = z;
        vector[3] = 1;
    }    

    public Vector4() {
        vector[0] = 0;
        vector[1] = 0;
        vector[2] = 0;
        vector[3] = 1;
    }

    public void normalizeW() {
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
        vector[3] = 1;
    }   

    public Vector4 copy() {
        Vector4 v = new Vector4();
        v.vector[0] = vector[0];
        v.vector[1] = vector[1];
        v.vector[2] = vector[2];
        v.vector[3] = vector[3];
        return v;
    }

    // Returns the subtraction of two vectors
    public static Vector4 subtract (Vector4 v1, Vector4 v2) {
        Vector4 v3 = new Vector4();
        v3.vector[0] = v1.vector[0] - v2.vector[0];
        v3.vector[1] = v1.vector[1] - v2.vector[1];
        v3.vector[2] = v1.vector[2] - v2.vector[2];
        v3.vector[3] = 1;
        return v3;
    }

    // Returns the subtraction of this vector minus v1 (parameter)
    public Vector4 subtract(Vector4 v1) {
        Vector4 v2 = new Vector4();
        v2.vector[0] = vector[0] - v1.vector[0];
        v2.vector[1] = vector[1] - v1.vector[1];
        v2.vector[2] = vector[2] - v1.vector[2];
        v2.vector[3] = 1;
        return v2;
    }

    // Returns the dot product of two vectors
    public static double dotProduct(Vector4 v1, Vector4 v2) {
        double acum = 0;
        for(int i = 0; i < 3; i++) {
            acum += v1.vector[i] * v2.vector[i];
        }
        return acum;
    }

    // Returns the cross product of two vectors
    public static Vector4 crossProduct(Vector4 v1, Vector4 v2) {
        Vector4 v3 = new Vector4();
        v3.vector[0] = v1.vector[1] * v2.vector[2] - v1.vector[2] * v2.vector[1];
        v3.vector[1] = v1.vector[2] * v2.vector[0] - v1.vector[0] * v2.vector[2];
        v3.vector[2] = v1.vector[0] * v2.vector[1] - v1.vector[1] * v2.vector[0];
        v3.vector[3] = 1;
        return v3;
    }

    public Vector4 timesScalar(double s) {
        Vector4 v = new Vector4();
        v.vector[0] = vector[0] * s;
        v.vector[1] = vector[1] * s;
        v.vector[2] = vector[2] * s;
        v.vector[3] = 1;
        return v;
    }

    // Normalize a vector
    public void normalize() {
        double length = Math.sqrt(vector[0] * vector[0] + 
            vector[1] * vector[1] + vector[2] * vector[2]);
        vector[0] /= length;
        vector[1] /= length;
        vector[2] /= length;
        vector[3] = 1;
    }

    public double length() {
        return Math.sqrt(vector[0] * vector[0] + 
            vector[1] * vector[1] + vector[2] * vector[2]);
    }

    public static Vector4 minus(Vector4 v1) {
        Vector4 v2 = new Vector4();
        v2.vector[0] = -v1.vector[0];
        v2.vector[1] = -v1.vector[1];
        v2.vector[2] = -v1.vector[2];
        v2.vector[3] = 1;
        return v2;
    }

    public static Vector4 multiply(Vector4 v2, double scalar) {
        Vector4 v3 = new Vector4();
        v3.vector[0] = v2.vector[0] * scalar;
        v3.vector[1] = v2.vector[1] * scalar;
        v3.vector[2] = v2.vector[2] * scalar;
        v3.vector[3] = 1;
        return v3;
    }

    public static Vector4 add(Vector4 v1, Vector4 v2) {
        Vector4 v3 = new Vector4();
        v3.vector[0] = v1.vector[0] + v2.vector[0];
        v3.vector[1] = v1.vector[1] + v2.vector[1];
        v3.vector[2] = v1.vector[2] + v2.vector[2];
        v3.vector[3] = 1;
        return v3;  
    }

    public static double distance(Vector4 p1, Vector4 p2) {
        double dx = p1.vector[0] - p2.vector[0];
        double dy = p1.vector[1] - p2.vector[1];
        double dz = p1.vector[2] - p2.vector[2];
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public String toString() {
        return "(" + vector[0] + ", " + vector[1] + ", " + vector[2] + ")";
    }

}
