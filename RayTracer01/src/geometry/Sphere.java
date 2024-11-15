package geometry;

import math.UVN4x4;
import math.Matrix4x4;
import math.Vector4;
import display.Scene;

public class Sphere implements IntersectableObject {
    Vector4 center;
    Vector4 transformedCenter;
    double radious;
    int colorIndex;
    int materialIndex;

    public Sphere(Vector4 center, double radious, int colorIndex, int materialIndex) {
        this.center = center;
        this.radious = radious;
        this.colorIndex = colorIndex;
        this.materialIndex = materialIndex;
    }

    public Solution intersect(Ray ray) {
        // Compute intersection of this sphere with a ray, if any
        double A = Vector4.dotProduct(ray.direction, ray.direction);
        //Vector4 centerOrigin = Vector4.subtract(ray.origin, center);
        Vector4 centerOrigin = Vector4.subtract(ray.origin, transformedCenter);
        double B = 2 * Vector4.dotProduct(ray.direction, centerOrigin);
        double C = Vector4.dotProduct(centerOrigin, centerOrigin) - radious * radious;
        double det = B * B - 4 * A * C;
        if(det < 0) {
            // No intersections with this ray
            return null;
        } else {
            double t1 = (-B - Math.sqrt(det)) / (2 * A);
            double t2 = (-B + Math.sqrt(det)) / (2 * A);
            Vector4 n1 = computeNormal(ray.evaluate(t1));
            Vector4 n2 = computeNormal(ray.evaluate(t2));
            Vector4 p1 = ray.evaluate(t1);
            Vector4 p2 = ray.evaluate(t2);
            if(t1 < 0 && t2 < 0) {
                // Both intersections are behind the ray origin
                return null;
            } else if(t1 < 0) {
                // t1 is behind the ray origin, but t2 is not
                return new Solution(p2, n2, Scene.colors.get(colorIndex), 
                    Scene.materials.get(materialIndex), t2);
            } else if(t2 < 0) {
                // t2 is behind the ray origin, but t1 is not
                return new Solution(p1, n1, Scene.colors.get(colorIndex), 
                    Scene.materials.get(materialIndex), t1);
            } else {
                // Both intersections are in front of the ray origin
                if(t1 < t2) {
                    return new Solution(p1, n1, Scene.colors.get(colorIndex), 
                        Scene.materials.get(materialIndex), t1);
                } else {
                    return new Solution(p2, n2, Scene.colors.get(colorIndex), 
                        Scene.materials.get(materialIndex), t2);
                }
            }
        }
    }

    public Vector4 computeNormal(Vector4 point) {
        // Compute the normal vector at point
        //Vector4 normal = Vector4.subtract(point, center);
        Vector4 normal = Vector4.subtract(point, transformedCenter);
        normal.normalize();
        return normal;
    }

    // Transform the sphere to camera coordinates
    public void setCamera() {
        UVN4x4 uvn = Scene.camera.uvn;
        transformedCenter = Matrix4x4.times(uvn, center);
        //transformedCenter.normalizeW();
    }

    public void reset() {
        // Reset the sphere to its original position
        transformedCenter = center;
    }

    
    public static void main(String [] args) {
        Scene.readScene("escena.txt");
        // Generate tests
        Sphere s = new Sphere(new Vector4(0, 0, -10), 1, 0, 0);
        s.reset();
        Ray r = new Ray(new Vector4(0, 0, 0), new Vector4(0, 0, -20));
        Solution sol = s.intersect(r);
        System.out.println(sol);
        System.out.println(sol.intersectionPoint);
    }
    
}
