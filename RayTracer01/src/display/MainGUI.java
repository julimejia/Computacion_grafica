package display;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

import geometry.Camera;
import math.Vector4;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainGUI extends JPanel implements KeyListener {

    public static final int WIDTH = 768;
    public static final int HEIGHT = 768;
    public static final int WIDTH2 = WIDTH / 2;
    public static final int HEIGHT2 = HEIGHT / 2;
    public static final double MINVALUE = -1000000.0;

    Graphics g;

    private boolean DEBUG = false;

    public MainGUI() {
        super();
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
  
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        Scene.zBuffer.clear();
        //to.transformObject();
        // Compute the colours before projecting the object
        //to.computeColoursAtVertices();
        // Transform the triangleobject and the lights to camera coordinates
        Scene.setCamera();    
        //to.projectObject();
        //to.draw(g);
        Scene.drawScene();
        drawPixels();
    }

    public void drawPixels() {
        // x and y are screen coordinates
        for(int x = 0; x < WIDTH; x++) {
          for(int y = 0; y < HEIGHT; y++) {
            if(Scene.zBuffer.depth[y][x] != MINVALUE) {
              g.setColor(Scene.zBuffer.color[y][x].toColor());
              g.drawLine(x, y, x, y);
            }
          }
        }
      }
  
    public void drawMyPoint(int x, int y) {
        int xs = x + WIDTH2;
        int ys = HEIGHT2 - y;
        this.g.drawLine(xs, ys, xs, ys);
    }

    public void drawMyPoint(int x, int y, double z, Colour c, Material m, Vector4 n) {
        Scene.zBuffer.setPixel(x, y, z, c);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
  
    @Override
    public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
      if(DEBUG) System.out.println(key);
      
      /*
      if(key == KeyEvent.VK_D) {
        to.ot.dx += ObjectTransformation.DELTA_TRANSL;
        repaint();
      } else if(key == KeyEvent.VK_A) {
        to.ot.dx -= ObjectTransformation.DELTA_TRANSL;
        repaint();
      } else if(key == KeyEvent.VK_W) {
        to.ot.dy += ObjectTransformation.DELTA_TRANSL;
        repaint();
      } else if(key == KeyEvent.VK_S) {
        to.ot.dy -= ObjectTransformation.DELTA_TRANSL;
        repaint();
      } else if(key == KeyEvent.VK_Q) {
        to.ot.sx += ObjectTransformation.DELTA_SCAL;
        to.ot.sy += ObjectTransformation.DELTA_SCAL;
        to.ot.sz += ObjectTransformation.DELTA_SCAL;
        repaint();
      } else if(key == KeyEvent.VK_E) {
        to.ot.sx -= ObjectTransformation.DELTA_SCAL;
        to.ot.sy -= ObjectTransformation.DELTA_SCAL;
        to.ot.sz -= ObjectTransformation.DELTA_SCAL;
        repaint();
      } else if(key == KeyEvent.VK_R) {
        to.ot.thetaX += ObjectTransformation.DELTA_ROT;
        repaint();
      } else if(key == KeyEvent.VK_F) {
        to.ot.thetaX -= ObjectTransformation.DELTA_ROT;
        repaint();
      } else if(key == KeyEvent.VK_T) {
        to.ot.thetaY += ObjectTransformation.DELTA_ROT;
        repaint();
      } else if(key == KeyEvent.VK_G) {
        to.ot.thetaY -= ObjectTransformation.DELTA_ROT;
        repaint();
      } else if(key == KeyEvent.VK_Y) {
        to.ot.thetaZ += ObjectTransformation.DELTA_ROT;
        repaint();
      } else if(key == KeyEvent.VK_H) {
        to.ot.thetaZ -= ObjectTransformation.DELTA_ROT;
        repaint();
      } else */
      if(key == KeyEvent.VK_LEFT) {
        Scene.camera.theta -= Camera.DELTA_THETA;
        repaint();
      } else if(key == KeyEvent.VK_RIGHT) {
        Scene.camera.theta += Camera.DELTA_THETA;
        repaint();
      } else if (key == KeyEvent.VK_UP) {
        if(Scene.camera.phi < Math.PI / 2 - 1.5 * Camera.DELTA_PHI) {
          Scene.camera.phi += Camera.DELTA_PHI;
          repaint();
        }
      } else if (key == KeyEvent.VK_DOWN) {
        if(Scene.camera.phi > -Math.PI / 2 + 1.5 * Camera.DELTA_PHI) {
          Scene.camera.phi -= Camera.DELTA_PHI;
          repaint();
        }
      } /* else if(key == KeyEvent.VK_Z) {
        to.resetVertices();
        repaint();
      } */
    }
  
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public static void main(String[] args) {
        // create a frame and add the panel to it
        JFrame frame = new JFrame("Ray Tracer 01");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Scene.readScene("escena.txt");
        MainGUI mainGUI = new MainGUI();
        frame.add(mainGUI);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }
}
