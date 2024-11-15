package math;

public class UVN4x4 extends Matrix4x4 {

    // Constructor
    public UVN4x4() {
        super();
    }

    // Another constructor, receiving the from, lookAt, and up vectors
    public UVN4x4(Vector4 from, Vector4 lookAt, Vector4 up) {
        super();
        // N = (from - lookAt)
        Vector4 n = Vector4.subtract(from, lookAt);
        // n = normalize(N)
        n.normalize();
        // U = up x n
        Vector4 u = Vector4.crossProduct(up, n);
        // u = normalize(U)
        u.normalize();
        // v = n x u
        Vector4 v = Vector4.crossProduct(n, u);
        // insert elements of u, v, n in the matrix
        matrix[0][0] = u.vector[0];
        matrix[0][1] = u.vector[1];
        matrix[0][2] = u.vector[2];
        matrix[1][0] = v.vector[0];
        matrix[1][1] = v.vector[1];
        matrix[1][2] = v.vector[2];
        matrix[2][0] = n.vector[0];
        matrix[2][1] = n.vector[1];
        matrix[2][2] = n.vector[2];
        // compute and insert the elements in the last column of the UVN matrix: 
        // -u . from
        // -v . from
        // -n . from
        matrix[0][3] = Vector4.dotProduct(Vector4.minus(u), from);
        matrix[1][3] = Vector4.dotProduct(Vector4.minus(v), from);
        matrix[2][3] = Vector4.dotProduct(Vector4.minus(n), from);
        
    }
    
    public static Vector4 minus(Vector4 v1) {
        Vector4 v2 = new Vector4();
        v2.vector[0] = -v1.vector[0];
        v2.vector[1] = -v1.vector[1];
        v2.vector[2] = -v1.vector[2];
        v2.vector[3] = 1;
        return v2;
    }
}
