package geometry;
import math.Vector4;

public class Ray {
    Vector4 origin;
    Vector4 direction;

    public Ray(Vector4 origin, Vector4 end) {
        this.origin = origin;
        this.direction = Vector4.subtract(end, origin);
        //this.direction.normalize();
    }

    public Vector4 evaluate(double t) {
        return Vector4.add(origin, Vector4.multiply(direction, t));
    }
}
