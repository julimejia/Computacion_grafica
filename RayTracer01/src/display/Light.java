package display;
import math.Vector4;

public class Light {
    Vector4 position;
    Vector4 transformedPosition;
    int colorIndex;

    public Light(Vector4 position, int colorIndex) {
        this.position = position;
        this.transformedPosition = new Vector4();
        this.colorIndex = colorIndex;
    }

}
