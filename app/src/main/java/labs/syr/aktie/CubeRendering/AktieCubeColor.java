package labs.syr.aktie.CubeRendering;

/**
 * Created by bharat on 11/27/14.
 */
public class AktieCubeColor {

    public final int red;
    public final int green;
    public final int blue;
    public final int alpha;

    public AktieCubeColor(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public AktieCubeColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 0x10000;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AktieCubeColor) {
            AktieCubeColor color = (AktieCubeColor)other;
            return (red == color.red &&
                    green == color.green &&
                    blue == color.blue &&
                    alpha == color.alpha);
        }
        return false;
    }
}
