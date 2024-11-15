package display;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import geometry.Camera;
//import geometry.TriangleObject;
//import geometry.Vertex;
import math.Vector4;
import geometry.IntersectableObject;
//import geometry.Triangle;
import geometry.Sphere;
import geometry.Plane;
import geometry.Ray;
import geometry.Solution;

public class Scene {
    public static ArrayList<Colour> colors = new ArrayList<>();
    public static ArrayList<Material> materials = new ArrayList<>();
    public static ArrayList<Light> lights = new ArrayList<>();
    public static int ambientLight; // color index of ambient light
    public static Camera camera = new Camera();
    // public static TriangleObject to;
    public static ArrayList<IntersectableObject> io = new ArrayList<>();
    public static double centerX;
    public static double centerY;
    public static double centerZ;
    public static int projectionDistance;
    public static ZBuffer zBuffer = new ZBuffer();
    public static Colour backgroundColour = new Colour(0, 0, 0);

    public static void setCamera() {
        camera.createUVN();
        // transform de intersectable objects to camera coordinates
        for (IntersectableObject object : io) {
            object.setCamera();
        }
        // Transform the triangleobject
        // to.setCamera();
        // Transform the lights
        // for(Light light : lights) {
        // light.setCamera();
        // }
    }

    public static void readScene(String fileName) {
        try {
            Scanner in = new Scanner(new File(fileName));
            /*
             * // Number of vertices and vertices
             * int n = in.nextInt();
             * for (int i = 0; i < n; i++) {
             * double x = in.nextDouble();
             * double y = in.nextDouble();
             * double z = in.nextDouble();
             * vertices.add(new Vertex(new Vector4(x, y, z)));
             * }
             */
            // read the number of colors and then the colors
            int n = in.nextInt();
            for (int i = 0; i < n; i++) {
                double r = in.nextDouble();
                double g = in.nextDouble();
                double b = in.nextDouble();
                Scene.colors.add(new Colour(r, g, b));
            }
            // read the number of materials and then the materials
            n = in.nextInt();
            for (int i = 0; i < n; i++) {
                double kA = in.nextDouble();
                double kD = in.nextDouble();
                double kS = in.nextDouble();
                int nExponent = in.nextInt();
                Scene.materials.add(new Material(kA, kD, kS, nExponent));
            }
            // read the color index of the ambient light
            Scene.ambientLight = in.nextInt();
            // read the number of lights and then the lights
            n = in.nextInt();
            for (int i = 0; i < n; i++) {
                double x = in.nextDouble();
                double y = in.nextDouble();
                double z = in.nextDouble();
                int colorIndex = in.nextInt();
                Scene.lights.add(new Light(new Vector4(x, y, z), colorIndex));
            }

            // read the number of triangles, vertices indices, color indices and
            // materia indices
            /*
             * n = in.nextInt();
             * for (int i = 0; i < n; i++) {
             * int v1 = in.nextInt();
             * int v2 = in.nextInt();
             * int v3 = in.nextInt();
             * int c = in.nextInt();
             * int m = in.nextInt();
             * Triangle t = new Triangle(v1, v2, v3, c, m, canvas);
             * triangles.add(t);
             * // System.out.println("TriangleObject: ");
             * //t.print(vertices);
             * }
             */
            // read the number of spheres and then the spheres:
            // center, radious, color index, material index
            n = in.nextInt();
            for (int i = 0; i < n; i++) {
                double x = in.nextDouble();
                double y = in.nextDouble();
                double z = in.nextDouble();
                double radious = in.nextDouble();
                int colorIndex = in.nextInt();
                int materialIndex = in.nextInt();
                Sphere s = new Sphere(new Vector4(x, y, z), radious, colorIndex, materialIndex);
                Scene.io.add(s);
            }
            // Read the number of planes and the points that define the planes
            n = in.nextInt();
            for (int i = 0; i < n; i++) {
                double x1 = in.nextDouble();
                double y1 = in.nextDouble();
                double z1 = in.nextDouble();
                double x2 = in.nextDouble();
                double y2 = in.nextDouble();
                double z2 = in.nextDouble();
                double x3 = in.nextDouble();
                double y3 = in.nextDouble();
                double z3 = in.nextDouble();
                Vector4 p1 = new Vector4(x1, y1, z1);
                Vector4 p2 = new Vector4(x2, y2, z2);
                Vector4 p3 = new Vector4(x3, y3, z3);
                int colorIndex = in.nextInt();
                int materialIndex = in.nextInt();
                Plane plane = new Plane(p1, p2, p3, colorIndex, materialIndex);
                Scene.io.add(plane);
            }
            // read the center of the Scene
            Scene.centerX = in.nextInt();
            Scene.centerY = in.nextInt();
            Scene.centerZ = in.nextInt();
            // read the projection distance
            Scene.projectionDistance = in.nextInt();
            in.close();
        } catch (Exception e) {
            System.out.println("Error reading file");
        }

    }

    public static Colour intersectRay(Ray ray) {
        Colour pixelColor = backgroundColour;
        // Find the closest intersection
        Solution solution = null;
        for (IntersectableObject object : io) {
            Solution s = object.intersect(ray);
            if (s != null) {
                if (solution == null) {
                    // First solution found
                    solution = s;
                } else {
                    // Take into account that the objects have been
                    // transformed to camera coordinates, that is to say,
                    // the camera is at the origin and looking at the
                    // negative z axis
                    // Keep the solution with the smallest z value
                    if (s.intersectionPoint.vector[2] > solution.intersectionPoint.vector[2]) {
                        solution = s;
                    }
                }
            }
        }
        // If there is an intersection, paint the pixel
        if (solution != null) {
            // x and y are world coordinates
            // zBuffer translates them to screen coordinates
            Vector4 point = solution.intersectionPoint;
            Vector4 normal = solution.normal;
            Colour colour = solution.colour;
            Material material = solution.material;
            // See if the point is in the shadow of an object
            pixelColor = Shader.computeLocalIllumination(point, normal, colour, material);
        }
        return pixelColor;
    }

    public static Solution intersectForShadow(Ray ray) {
        // Find the closest intersection
        // Find the smallest intersection with a t value that is both
        // Less than 1 and greater than epsilon
        double minT = 1;
        Solution solution = null;
        for (IntersectableObject object : io) {
            Solution s = object.intersect(ray);
            if(s != null && s.t > 0.0001) {
                if(s.t < minT) {
                    minT = s.t;
                    solution = s;
                }
            }
        }
        return solution;
    }

    public static void drawScene() {
        // Trhow rays for each pixel
        Ray ray;
        Vector4 origin = new Vector4(0, 0, 0);  // Camera is at the origin
        for (int x = -MainGUI.WIDTH2; x < MainGUI.WIDTH2; x++) {
            for (int y = -MainGUI.HEIGHT2; y < MainGUI.HEIGHT2; y++) {
                // Create the ray
                Vector4 end = new Vector4(x, y, projectionDistance); // Each pixel on the projection plane
                ray = new Ray(origin, end);
                Colour colour = Scene.intersectRay(ray);
                zBuffer.setPixel(x, y, 0, colour);
            }
        }
    }

}
