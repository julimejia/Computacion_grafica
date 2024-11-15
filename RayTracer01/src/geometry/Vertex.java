package geometry;

import math.Vector4;

public class Vertex {
    Vector4 vector;
    // ArrayList of triangles adjacent to this vertex
    // Normal vector of this vertex

    public Vertex(Vector4 vector) {
        this.vector = vector;
    }

    /*
    public void makeXYInteger() {
        //int x = (int) vector.vector[0];
        //int y = (int) vector.vector[1];
        //vector.vector[0] = (int) vector.vector[0];
        vector.vector[0] = Math.round( vector.vector[0]);
        //vector.vector[1] = (int) vector.vector[1];
        vector.vector[1] = Math.round( vector.vector[1]);
        //return new Vector4(x , y, vector.vector[2]);
    }
    */
}
