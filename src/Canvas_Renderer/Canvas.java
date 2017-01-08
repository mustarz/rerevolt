package Canvas_Renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;



public class Canvas {    
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int MAX_FPS = 120;
    
    public static void createCanvas() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
            Display.setTitle("Car Game");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }
    
    public static void updateCanvas() {
        Display.sync(MAX_FPS);
        Display.update();
    }
    
    public static void destroyCanvas() {
        Display.destroy();
    }
    
}
