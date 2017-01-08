package Game;


import Canvas_Renderer.Canvas;
import Model.Model;
import Model.OBJLoader;
import Model.Render;
import java.io.IOException;
import org.lwjgl.opengl.Display;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.DisplayMode;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class CarGame {
    
    public static void main(String[] args) throws IOException {
        Canvas.createCanvas();
        //Model tire = OBJLoader.loadObj("WheelFR");     
        OBJLoader Loader = new OBJLoader();
        Render rend = new Render();
        
        
        
        while(!Display.isCloseRequested()) {
            rend.pripravi();
            
            rend.render(model);
            Canvas.updateCanvas();
        }
        Loader.cleanup();
        Canvas.destroyCanvas();
    }    
}
