package display;

import java.awt.Color;

public class Colour {
    double r;
    double g;
    double b;

    public Colour(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void acumColor(Colour c) {
        this.r += c.r;
        this.g += c.g;
        this.b += c.b;
    }

    public Colour timesColour(Colour c) {
        return new Colour(r * c.r, g * c.g, b * c.b);
    }

    public Colour timesScalar(double s) {
        return new Colour(r * s, g * s, b * s);
    }

    public static Colour subtract(Colour c1, Colour c2) {
        return new Colour(c1.r - c2.r, c1.g - c2.g, c1.b - c2.b);
    }

    public Colour add(Colour c) {
        return new Colour(r + c.r, g + c.g, b + c.b);
    }

    public Color toColor() {
        int red = (int) (r * 255);
        int green = (int) (g * 255);
        int blue = (int) (b * 255);
        if(red > 255) red = 255;
        if(red < 0) red = 0;
        if(green > 255) green = 255; 
        if(green < 0) green = 0;
        if(blue > 255) blue = 255;
        if(blue < 0) blue = 0;
        return new Color(red, green, blue);
    };

    public String toString() {
        return "Colour(" + r + ", " + g + ", " + b + ")";
    }
    
}
