package geometry;

import math.Vector4;
import math.UVN4x4;
import math.Matrix4x4;
import display.Scene;

public class Triangle implements IntersectableObject {
    Vector4 v0, v1, v2; // Vértices del triángulo
    Vector4 transformedV0, transformedV1, transformedV2; // Vértices transformados
    int colorIndex;
    int materialIndex;

    // Parámetros del plano del triángulo
    private Vector4 normal;
    private double d;

    public Triangle(Vector4 v0, Vector4 v1, Vector4 v2, int colorIndex, int materialIndex) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.colorIndex = colorIndex;
        this.materialIndex = materialIndex;
    }

    public Solution intersect(Ray ray) {
        // Algoritmo de Moller-Trumbore para la intersección rayo-triángulo
        Vector4 edge1 = Vector4.subtract(transformedV1, transformedV0);
        Vector4 edge2 = Vector4.subtract(transformedV2, transformedV0);
        Vector4 h = Vector4.crossProduct(ray.direction, edge2);
        double a = Vector4.dotProduct(edge1, h);

        // Verificar si el rayo es paralelo al triángulo
        if (Math.abs(a) < 1e-6) return null;

        double f = 1.0 / a;
        Vector4 s = Vector4.subtract(ray.origin, v0);
        double u = f * Vector4.dotProduct(s, h);
        if (u < 0.0 || u > 1.0) return null;

        Vector4 q = Vector4.crossProduct(s, edge1);
        double v = f * Vector4.dotProduct(ray.direction, q);
        if (v < 0.0 || u + v > 1.0) return null;

        double t = f * Vector4.dotProduct(edge2, q);

        // Si t es negativo, la intersección está detrás del origen del rayo
        if (t < 0.0) return null;

        // Punto de intersección y normal
        Vector4 intersectionPoint = ray.evaluate(t);
        normal = Vector4.crossProduct(edge1, edge2);
        normal.normalize();

        return new Solution(intersectionPoint, normal, Scene.colors.get(colorIndex), 
            Scene.materials.get(materialIndex), t);
    }

    // Transformación del triángulo a coordenadas de la cámara
    public void setCamera() {
        UVN4x4 uvn = Scene.camera.uvn;
    
        // Transformación de los vértices al espacio de la cámara
        transformedV0 = Matrix4x4.times(uvn, v0);
        transformedV1 = Matrix4x4.times(uvn, v1);
        transformedV2 = Matrix4x4.times(uvn, v2);
        
        // Ahora calculamos la normal en el espacio transformado
        computeNormalAndD();
    }

    // Función para calcular la normal del triángulo y el valor de d (para la ecuación del plano)
    public void computeNormalAndD() {
        // Calculamos los vectores del triángulo
        Vector4 edge1 = Vector4.subtract(transformedV1, transformedV0);
        Vector4 edge2 = Vector4.subtract(transformedV2, transformedV0);
        
        // Calculamos la normal del triángulo
        normal = Vector4.crossProduct(edge1, edge2);
        normal.normalize();
        
        // La ecuación del plano es: ax + by + cz + d = 0
        // Calculamos el valor de d usando un punto en el triángulo (transformedV0)
        d = -Vector4.dotProduct(transformedV0, normal);
    }

    // Restablecer el triángulo a su posición original
    public void reset() {
        transformedV0 = v0;
        transformedV1 = v1;
        transformedV2 = v2;
    }

    public static void main(String[] args) {
        Scene.readScene("escena.txt");
        // Generar prueba
        Triangle t = new Triangle(new Vector4(0, 0, -10), new Vector4(1, 0, -10), new Vector4(0, 1, -10), 0, 0);
        t.reset();
        Ray r = new Ray(new Vector4(0, 0, 0), new Vector4(0, 0, -1));
        Solution sol = t.intersect(r);
        if (sol != null) {
            System.out.println("Intersección encontrada: ");
            System.out.println(sol);
            System.out.println("Punto de intersección: " + sol.intersectionPoint);
        } else {
            System.out.println("Sin intersección.");
        }
    }
}
