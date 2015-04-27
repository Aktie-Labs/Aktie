package labs.syr.aktie.CubeRendering;

/**
 * Created by bharat on 11/27/14.
 */
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AktieCubeRender implements GLSurfaceView.Renderer {
    public interface AnimationCallback {
        void animate();
    }

    public AktieCubeRender(AktieCubeWorld world, AnimationCallback callback) {
        mWorld = world;
        mCallback = callback;
    }

    public void onDrawFrame(GL10 gl) {
        if (mCallback != null) {
            mCallback.animate();
        }

        gl.glClearColor(0,0,0,0);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -3.0f);
        gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glRotatef(mAngle,        0, 1, 0);
        gl.glRotatef(mAngle*0.25f,  1, 0, 0);

        gl.glColor4f(0.0f, 0.0f, 0.0f, 0.2f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        mWorld.draw(gl);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float ratio = (float)width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 2, 12);
        gl.glDisable(GL10.GL_DITHER);
        gl.glActiveTexture(GL10.GL_TEXTURE0);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public float getAngle() {
        return mAngle;
    }

    private AktieCubeWorld mWorld;
    private AnimationCallback mCallback;
    private float mAngle;
}