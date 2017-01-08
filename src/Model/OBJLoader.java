package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

public class OBJLoader {
    public static Model loadObj(String name) throws IOException {
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader("obj/" + name + ".obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model model = new Model();
        String line;
        while((line = bf.readLine()) != null) {
            if(line.startsWith("v ")) {
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[2]);
                float z = Float.valueOf(line.split(" ")[3]);
                model.vertices.add(new Vector3f(x, y, z));
            } 
            else if(line.startsWith("vn ")) {
                float x = Float.valueOf(line.split(" ")[1]);
                float y = Float.valueOf(line.split(" ")[2]);
                float z = Float.valueOf(line.split(" ")[3]);
                model.normals.add(new Vector3f(x, y, z));
            }
            else if(line.startsWith("f ")) {
                Vector3f vertexIn = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[0]), Float.valueOf(line.split(" ")[2].split("/")[0]), Float.valueOf(line.split(" ")[3].split("/")[0]));
                Vector3f normalIn = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[2]), Float.valueOf(line.split(" ")[2].split("/")[2]), Float.valueOf(line.split(" ")[3].split("/")[2]));
                model.faces.add(new Face(vertexIn, normalIn));
            }
        }
        bf.close();
        return model;
    } 
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    public Model loadtoVAO(float[] positions){
        int vaoID = createVAO();
        storeDataInAttributeList(0,positions);
        unbindVAO();
        return new Model(vaoID,positions.length/3);
    }
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
    private void storeDataInAttributeList(int attributeNumber, float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber,3,GL11.GL_FLOAT,false,0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
    private void unbindVAO(){
        GL30.glBindVertexArray(0);
    }
    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void cleanup() {
        for(int vao:vaos)
        {
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo:vbos)
        {
            GL15.glDeleteBuffers(vbo);
        }
    }
}
