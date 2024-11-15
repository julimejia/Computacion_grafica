package display;

public class ZBuffer {
    double[][] depth = new double[MainGUI.HEIGHT][MainGUI.WIDTH];
    Colour[][] color = new Colour[MainGUI.HEIGHT][MainGUI.WIDTH];

    public ZBuffer() {
        // x and y are screen coordinates
        for (int y = 0; y < MainGUI.HEIGHT; y++) {
            for (int x = 0; x < MainGUI.WIDTH; x++) {
                depth[y][x] = MainGUI.MINVALUE;
                color[y][x] = new Colour(0, 0, 0); 
            }
        }
    }

    public void clear() {
        for (int y = 0; y < MainGUI.HEIGHT; y++) {
            for (int x = 0; x < MainGUI.WIDTH; x++) {
                depth[y][x] = MainGUI.MINVALUE;
                color[y][x] = new Colour(0, 0, 0);
            }
        }
    }

    // Xs = Xw + WIDTH / 2
    // Ys = HEIGHT / 2 - Yw
    //
    // Xw = Xs - WIDTH / 2
    // Yw = HEIGHT / 2 - Ys

    public void setPixel(int x, int y, double z, Colour c) {
        // x, y and z are world coordinates
        // xs and ys are screen coordinates
        int xs = x + MainGUI.WIDTH2;
        int ys = MainGUI.HEIGHT2 - y;
        if (xs < 0 || xs >= MainGUI.WIDTH || ys < 0 || ys >= MainGUI.HEIGHT) {
            return;
        }
        // double myDepth = depth[ys][xs];
        if (z > depth[ys][xs]) {
            depth[ys][xs] = z;
            color[ys][xs] = c;
        }
    }

}
