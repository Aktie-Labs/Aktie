package labs.syr.aktie.CubeRendering;

/**
 * Created by bharat on 11/27/14.
 */
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;

public class AktieCubeShape {

    public AktieCubeShape(AktieCubeWorld world) {
        mWorld = world;
    }

    public void addFace(AktieCubeFace face) {
        mFaceList.add(face);
    }

    public void setFaceColor(int face, AktieCubeColor color) {
        mFaceList.get(face).setColor(color);
    }

    public void putIndices(ShortBuffer buffer) {
        Iterator<AktieCubeFace> iter = mFaceList.iterator();
        while (iter.hasNext()) {
            AktieCubeFace face = iter.next();
            face.putIndices(buffer);
        }
    }

    public int getIndexCount() {
        int count = 0;
        Iterator<AktieCubeFace> iter = mFaceList.iterator();
        while (iter.hasNext()) {
            AktieCubeFace face = iter.next();
            count += face.getIndexCount();
        }
        return count;
    }

    public AktieCubeVertex addVertex(float x, float y, float z) {

        // look for an existing AktieCubeVertex first
        Iterator<AktieCubeVertex> iter = mVertexList.iterator();
        while (iter.hasNext()) {
            AktieCubeVertex vertex = iter.next();
            if (vertex.x == x && vertex.y == y && vertex.z == z) {
                return vertex;
            }
        }

        // doesn't exist, so create new vertex
        AktieCubeVertex vertex = mWorld.addVertex(x, y, z);
        mVertexList.add(vertex);
        return vertex;
    }

    public void animateTransform(AktieCubeHolder transform) {
        mAnimateTransform = transform;

        if (mTransform != null)
            transform = mTransform.multiply(transform);

        Iterator<AktieCubeVertex> iter = mVertexList.iterator();
        while (iter.hasNext()) {
            AktieCubeVertex vertex = iter.next();
            mWorld.transformVertex(vertex, transform);
        }
    }

    public void startAnimation() {
    }

    public void endAnimation() {
        if (mTransform == null) {
            mTransform = new AktieCubeHolder(mAnimateTransform);
        } else {
            mTransform = mTransform.multiply(mAnimateTransform);
        }
    }

    public AktieCubeHolder mTransform;
    public AktieCubeHolder mAnimateTransform;
    protected ArrayList<AktieCubeFace>		mFaceList = new ArrayList<AktieCubeFace>();
    protected ArrayList<AktieCubeVertex>	mVertexList = new ArrayList<AktieCubeVertex>();
    protected ArrayList<Integer>	mIndexList = new ArrayList<Integer>();	// make more efficient?
    protected AktieCubeWorld mWorld;
}
