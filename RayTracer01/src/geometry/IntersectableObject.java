package geometry;

public interface IntersectableObject {
    public Solution intersect(Ray ray);
    public void setCamera();
}
