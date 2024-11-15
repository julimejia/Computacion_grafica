package geometry;
import math.Vector4;
import display.Colour;
import display.Material;

public class Solution {
    public Vector4 intersectionPoint;
    public Vector4 normal;
    public Colour colour;
    public Material material;
    public double t;

    // Constructor
    public Solution(Vector4 intersectionPoint, Vector4 normal, Colour colour, 
        Material material, double t) {
        this.intersectionPoint = intersectionPoint;
        this.normal = normal;
        this.colour = colour;
        this.material = material;
        this.t = t;
    }

    // toString
    public String toString() {
        return "Intersection point: " + intersectionPoint + "\n" +
            "Normal: " + normal + "\n" +
            "Colour: " + colour + "\n" +
            "Material: " + material + "\n" +
            "t: " + t;
    }
}
