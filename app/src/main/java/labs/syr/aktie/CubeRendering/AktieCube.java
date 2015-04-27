package labs.syr.aktie.CubeRendering;

/**
 * Created by bharat on 11/27/14.
 */
public class AktieCube extends AktieCubeShape {

    public AktieCube(AktieCubeWorld world, float left, float bottom, float back, float right, float top, float front) {
        super(world);
        AktieCubeVertex leftBottomBack = addVertex(left, bottom, back);
        AktieCubeVertex rightBottomBack = addVertex(right, bottom, back);
        AktieCubeVertex leftTopBack = addVertex(left, top, back);
        AktieCubeVertex rightTopBack = addVertex(right, top, back);
        AktieCubeVertex leftBottomFront = addVertex(left, bottom, front);
        AktieCubeVertex rightBottomFront = addVertex(right, bottom, front);
        AktieCubeVertex leftTopFront = addVertex(left, top, front);
        AktieCubeVertex rightTopFront = addVertex(right, top, front);
        addFace(new AktieCubeFace(leftBottomBack, leftBottomFront, rightBottomFront, rightBottomBack));
        addFace(new AktieCubeFace(leftBottomFront, leftTopFront, rightTopFront, rightBottomFront));
        addFace(new AktieCubeFace(leftBottomBack, leftTopBack, leftTopFront, leftBottomFront));
        addFace(new AktieCubeFace(rightBottomBack, rightBottomFront, rightTopFront, rightTopBack));
        addFace(new AktieCubeFace(leftBottomBack, rightBottomBack, rightTopBack, leftTopBack));
        addFace(new AktieCubeFace(leftTopBack, rightTopBack, rightTopFront, leftTopFront));

    }

    public static final int kBottom = 0;
    public static final int kFront = 1;
    public static final int kLeft = 2;
    public static final int kRight = 3;
    public static final int kBack = 4;
    public static final int kTop = 5;


}
