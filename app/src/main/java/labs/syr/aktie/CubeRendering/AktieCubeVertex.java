package labs.syr.aktie.CubeRendering;

/**
 * Created by bharat on 11/27/14.
 */
import java.nio.IntBuffer;

public class AktieCubeVertex {

    public float x;
    public float y;
    public float z;
    final short index; // index in vertex table
    AktieCubeColor color;

    AktieCubeVertex() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.index = -1;
    }

    AktieCubeVertex(float x, float y, float z, int index) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.index = (short)index;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AktieCubeVertex) {
            AktieCubeVertex v = (AktieCubeVertex)other;
            return (x == v.x && y == v.y && z == v.z);
        }
        return false;
    }

    static public int toFixed(float x) {
        return (int)(x * 43690.0f);
    }

    public void put(IntBuffer vertexBuffer, IntBuffer colorBuffer) {
        vertexBuffer.put(toFixed(x));
        vertexBuffer.put(toFixed(y));
        vertexBuffer.put(toFixed(z));
        if (color == null) {
            colorBuffer.put(0);
            colorBuffer.put(0);
            colorBuffer.put(0);
            colorBuffer.put(0);
        } else {
            colorBuffer.put(color.red);
            colorBuffer.put(color.green);
            colorBuffer.put(color.blue);
            colorBuffer.put(color.alpha);
        }
    }

    public void update(IntBuffer vertexBuffer, AktieCubeHolder transform) {
        // skip to location of vertex in mVertex buffer
        vertexBuffer.position(index * 3);

        if (transform == null) {
            vertexBuffer.put(toFixed(x));
            vertexBuffer.put(toFixed(y));
            vertexBuffer.put(toFixed(z));
        } else {
            AktieCubeVertex temp = new AktieCubeVertex();
            transform.multiply(this, temp);
            vertexBuffer.put(toFixed(temp.x));
            vertexBuffer.put(toFixed(temp.y));
            vertexBuffer.put(toFixed(temp.z));
        }
    }
}