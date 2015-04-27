package labs.syr.aktie.CubeRendering;

/**
 * Created by bharat on 11/27/14.
 */
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Iterator;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class AktieCubeWorld {

    public void addShape(AktieCubeShape shape) {
        mShapeList.add(shape);
        mIndexCount += shape.getIndexCount();
    }

    public void generate() {
        ByteBuffer bb = ByteBuffer.allocateDirect(mVertexList.size()*4*4);
        bb.order(ByteOrder.nativeOrder());
        mColorBuffer = bb.asIntBuffer();

        bb = ByteBuffer.allocateDirect(mVertexList.size()*4*3);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asIntBuffer();

        bb = ByteBuffer.allocateDirect(mIndexCount*2);
        bb.order(ByteOrder.nativeOrder());
        mIndexBuffer = bb.asShortBuffer();

        Iterator<AktieCubeVertex> iter2 = mVertexList.iterator();
        while (iter2.hasNext()) {
            AktieCubeVertex vertex = iter2.next();
            vertex.put(mVertexBuffer, mColorBuffer);
        }

        Iterator<AktieCubeShape> iter3 = mShapeList.iterator();
        while (iter3.hasNext()) {
            AktieCubeShape shape = iter3.next();
            shape.putIndices(mIndexBuffer);
        }
    }

    public AktieCubeVertex addVertex(float x, float y, float z) {
        AktieCubeVertex vertex = new AktieCubeVertex(x, y, z, mVertexList.size());
        mVertexList.add(vertex);
        return vertex;
    }

    public void transformVertex(AktieCubeVertex vertex, AktieCubeHolder transform) {
        vertex.update(mVertexBuffer, transform);
    }

    int count = 0;
    public void draw(GL10 gl)
    {
        mColorBuffer.position(0);
        mVertexBuffer.position(0);
        mIndexBuffer.position(0);

        gl.glFrontFace(GL10.GL_CW);
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, mVertexBuffer);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, mColorBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES, mIndexCount, GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
        count++;
    }

    static public float toFloat(int x) {
        return x/43690.0f;
    }

    private ArrayList<AktieCubeShape>	mShapeList = new ArrayList<AktieCubeShape>();
    private ArrayList<AktieCubeVertex>	mVertexList = new ArrayList<AktieCubeVertex>();

    private int mIndexCount = 0;

    private IntBuffer   mVertexBuffer;
    private IntBuffer   mColorBuffer;
    private ShortBuffer mIndexBuffer;
}