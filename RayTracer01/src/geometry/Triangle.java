package geometry;

import math.Vector4;
import math.UVN4x4;
import math.Matrix4x4;
import display.Scene;

public class Triangle implements IntersectableObject {
    Vector4 v0, v1, v2; // Triangle vertices
    Vector4 transformedV0, transformedV1, transformedV2; // Transformed vertices
    int colorIndex;
    int materialIndex;

    public Triangle(Vector4 v0, Vector4 v1, Vector4 v2, int colorIndex, int materialIndex) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.colorIndex = colorIndex;
        this.materialIndex = materialIndex;
    }

    public Solution intersect(Ray ray) {
        // Moller-Trumbore algorithm for ray-triangle intersection
        Vector4 edge1 = Vector4.subtract(v1, v0);
        Vector4 edge2 = Vector4.subtract(v2, v0);
        Vector4 h = Vector4.crossProduct(ray.direction, edge2);
        double a = Vector4.dotProduct(edge1, h);

        // Check if ray is parallel to triangle
        if (Math.abs(a) < 1e-6) return null;

        double f = 1.0 / a;
        Vector4 s = Vector4.subtract(ray.origin, v0);
        double u = f * Vector4.dotProduct(s, h);
        if (u < 0.0 || u > 1.0) return null;

        Vector4 q = Vector4.crossProduct(s, edge1);
        double v = f * Vector4.dotProduct(ray.direction, q);
        if (v < 0.0 || u + v > 1.0) return null;

        double t = f * Vector4.dotProduct(edge2, q);

        // If t is negative, the intersection is behind the ray origin
        if (t < 0.0) return null;

        // Intersection point and normal
        Vector4 intersectionPoint = ray.evaluate(t);
        Vector4 normal = Vector4.crossProduct(edge1, edge2);
        normal.normalize();

        return new Solution(intersectionPoint, normal, Scene.colors.get(colorIndex), 
            Scene.materials.get(materialIndex), t);
    }

    // Transform the triangle to camera coordinates
    public void setCamera() {
        UVN4x4 uvn = Scene.camera.uvn;
        transformedV0 = Matrix4x4.times(uvn, v0);
        transformedV1 = Matrix4x4.times(uvn, v1);
        transformedV2 = Matrix4x4.times(uvn, v2);
    }

    public void reset() {
        // Reset the triangle to its original position
        transformedV0 = v0;
        transformedV1 = v1;
        transformedV2 = v2;
    }

    public static void main(String[] args) {
        Scene.readScene("escena.txt");
        // Generate tests
        Triangle t = new Triangle(new Vector4(0, 0, -10), new Vector4(1, 0, -10), new Vector4(0, 1, -10), 0, 0);
        t.reset();
        Ray r = new Ray(new Vector4(0, 0, 0), new Vector4(0, 0, -1));
        Solution sol = t.intersect(r);
        if (sol != null) {
            System.out.println(sol);
            System.out.println(sol.intersectionPoint);
        } else {
            System.out.println("No intersection.");
        }
    }
}
