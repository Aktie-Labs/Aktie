package labs.syr.aktie.CubeRendering;

/**
 * Created by bharat on 11/27/14.
 */
import android.util.Log;

import java.nio.ShortBuffer;
import java.util.ArrayList;

public class AktieCubeFace {

    public AktieCubeFace() {

    }

    // for triangles
    public AktieCubeFace(AktieCubeVertex v1, AktieCubeVertex v2, AktieCubeVertex v3) {
        addVertex(v1);
        addVertex(v2);
        addVertex(v3);
    }
    // for quadrilaterals
    public AktieCubeFace(AktieCubeVertex v1, AktieCubeVertex v2, AktieCubeVertex v3, AktieCubeVertex v4) {
        addVertex(v1);
        addVertex(v2);
        addVertex(v3);
        addVertex(v4);
    }

    public void addVertex(AktieCubeVertex v) {
        mVertexList.add(v);
    }

    // must be called after all vertices are added
    public void setColor(AktieCubeColor c) {

        int last = mVertexList.size() - 1;
        if (last < 2) {
            Log.e("AktieCubeFace", "not enough vertices in setColor()");
        } else {
            AktieCubeVertex vertex = mVertexList.get(last);

            // only need to do this if the color has never been set
            if (mColor == null) {
                while (vertex.color != null) {
                    mVertexList.add(0, vertex);
                    mVertexList.remove(last + 1);
                    vertex = mVertexList.get(last);
                }
            }

            vertex.color = c;
        }

        mColor = c;
    }

    public int getIndexCount() {
        return (mVertexList.size() - 2) * 3;
    }

    public void putIndices(ShortBuffer buffer) {
        int last = mVertexList.size() - 1;

        AktieCubeVertex v0 = mVertexList.get(0);
        AktieCubeVertex vn = mVertexList.get(last);

        // push triangles into the buffer
        for (int i = 1; i < last; i++) {
            AktieCubeVertex v1 = mVertexList.get(i);
            buffer.put(v0.index);
            buffer.put(v1.index);
            buffer.put(vn.index);
            v0 = v1;
        }
    }

    private ArrayList<AktieCubeVertex> mVertexList = new ArrayList<AktieCubeVertex>();
    private AktieCubeColor mColor;
}