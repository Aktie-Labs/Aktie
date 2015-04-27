package labs.syr.aktie.Activity;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import labs.syr.aktie.CubeRendering.AktieCube;
import labs.syr.aktie.CubeRendering.AktieCubeColor;
import labs.syr.aktie.CubeRendering.AktieCubeLayer;
import labs.syr.aktie.CubeRendering.AktieCubeShape;
import labs.syr.aktie.CubeRendering.AktieCubeWorld;
import labs.syr.aktie.CubeRendering.AktieCubeRender;
import labs.syr.aktie.R;

/**
 * Date: 11/28/14.
 * Author: Bharath Darapu, Pranav Vasisth
 * Purpose: Aktie project
 * File Name: AktieCubeActivity.java
 * Description: This class is implented as a splash screen for the app.
 *              modified the openGL cube to have it as a rubik's cube.
 *
 */


public class AktieCubeActivity extends Activity implements AktieCubeRender.AnimationCallback {

    /*creating the openGLworld*/
    private AktieCubeWorld makeGLWorld()
    {
        AktieCubeWorld world = new AktieCubeWorld();

        int one = 0x10000;
        int half = 0x08000;
        AktieCubeColor red = new AktieCubeColor(one, 0, 0);
        AktieCubeColor green = new AktieCubeColor(0, one, 0);
        AktieCubeColor blue = new AktieCubeColor(0, 0, one);
        AktieCubeColor yellow = new AktieCubeColor(one, one, 0);
        AktieCubeColor orange = new AktieCubeColor(one, half, 0);
        AktieCubeColor white = new AktieCubeColor(one, one, one);
        AktieCubeColor black = new AktieCubeColor(0, 0, 0);

        float c0 = -1.0f;
        float c1 = -0.38f;
        float c2 = -0.32f;
        float c3 = 0.32f;
        float c4 = 0.38f;
        float c5 = 1.0f;

        // top back, left to right
        mAktieCubes[0]  = new AktieCube(world, c0, c4, c0, c1, c5, c1);
        mAktieCubes[1]  = new AktieCube(world, c2, c4, c0, c3, c5, c1);
        mAktieCubes[2]  = new AktieCube(world, c4, c4, c0, c5, c5, c1);
        // top middle, left to right
        mAktieCubes[3]  = new AktieCube(world, c0, c4, c2, c1, c5, c3);
        mAktieCubes[4]  = new AktieCube(world, c2, c4, c2, c3, c5, c3);
        mAktieCubes[5]  = new AktieCube(world, c4, c4, c2, c5, c5, c3);
        // top front, left to right
        mAktieCubes[6]  = new AktieCube(world, c0, c4, c4, c1, c5, c5);
        mAktieCubes[7]  = new AktieCube(world, c2, c4, c4, c3, c5, c5);
        mAktieCubes[8]  = new AktieCube(world, c4, c4, c4, c5, c5, c5);
        // middle back, left to right
        mAktieCubes[9]  = new AktieCube(world, c0, c2, c0, c1, c3, c1);
        mAktieCubes[10] = new AktieCube(world, c2, c2, c0, c3, c3, c1);
        mAktieCubes[11] = new AktieCube(world, c4, c2, c0, c5, c3, c1);
        // middle middle, left to right
        mAktieCubes[12] = new AktieCube(world, c0, c2, c2, c1, c3, c3);
        mAktieCubes[13] = null;
        mAktieCubes[14] = new AktieCube(world, c4, c2, c2, c5, c3, c3);
        // middle front, left to right
        mAktieCubes[15] = new AktieCube(world, c0, c2, c4, c1, c3, c5);
        mAktieCubes[16] = new AktieCube(world, c2, c2, c4, c3, c3, c5);
        mAktieCubes[17] = new AktieCube(world, c4, c2, c4, c5, c3, c5);
        // bottom back, left to right
        mAktieCubes[18] = new AktieCube(world, c0, c0, c0, c1, c1, c1);
        mAktieCubes[19] = new AktieCube(world, c2, c0, c0, c3, c1, c1);
        mAktieCubes[20] = new AktieCube(world, c4, c0, c0, c5, c1, c1);
        // bottom middle, left to right
        mAktieCubes[21] = new AktieCube(world, c0, c0, c2, c1, c1, c3);
        mAktieCubes[22] = new AktieCube(world, c2, c0, c2, c3, c1, c3);
        mAktieCubes[23] = new AktieCube(world, c4, c0, c2, c5, c1, c3);
        // bottom front, left to right
        mAktieCubes[24] = new AktieCube(world, c0, c0, c4, c1, c1, c5);
        mAktieCubes[25] = new AktieCube(world, c2, c0, c4, c3, c1, c5);
        mAktieCubes[26] = new AktieCube(world, c4, c0, c4, c5, c1, c5);

        // paint the sides
        int i, j;
        // set all faces black by default
        for (i = 0; i < 27; i++) {
            AktieCube aktieCube = mAktieCubes[i];
            if (aktieCube != null) {
                for (j = 0; j < 6; j++)
                    aktieCube.setFaceColor(j, black);
            }
        }

        // paint top
        for (i = 0; i < 9; i++)
            mAktieCubes[i].setFaceColor(AktieCube.kTop, orange);
        // paint bottom
        for (i = 18; i < 27; i++)
            mAktieCubes[i].setFaceColor(AktieCube.kBottom, red);
        // paint left
        for (i = 0; i < 27; i += 3)
            mAktieCubes[i].setFaceColor(AktieCube.kLeft, yellow);
        // paint right
        for (i = 2; i < 27; i += 3)
            mAktieCubes[i].setFaceColor(AktieCube.kRight, white);
        // paint back
        for (i = 0; i < 27; i += 9)
            for (j = 0; j < 3; j++)
                mAktieCubes[i + j].setFaceColor(AktieCube.kBack, blue);
        // paint front
        for (i = 6; i < 27; i += 9)
            for (j = 0; j < 3; j++)
                mAktieCubes[i + j].setFaceColor(AktieCube.kFront, green);

        for (i = 0; i < 27; i++)
            if (mAktieCubes[i] != null)
                world.addShape(mAktieCubes[i]);

        // initialize our permutation to solved position
        mPermutation = new int[27];
        for (i = 0; i < mPermutation.length; i++)
            mPermutation[i] = i;

        createLayers();
        updateLayers();

        world.generate();

        return world;
    }

    /*here we create the layers (x,y,z) for the cube[s]*/
    private void createLayers() {
        mAktieCubeLayers[kUp] = new AktieCubeLayer(AktieCubeLayer.kAxisY);
        mAktieCubeLayers[kDown] = new AktieCubeLayer(AktieCubeLayer.kAxisY);
        mAktieCubeLayers[kLeft] = new AktieCubeLayer(AktieCubeLayer.kAxisX);
        mAktieCubeLayers[kRight] = new AktieCubeLayer(AktieCubeLayer.kAxisX);
        mAktieCubeLayers[kFront] = new AktieCubeLayer(AktieCubeLayer.kAxisZ);
        mAktieCubeLayers[kBack] = new AktieCubeLayer(AktieCubeLayer.kAxisZ);
        mAktieCubeLayers[kMiddle] = new AktieCubeLayer(AktieCubeLayer.kAxisX);
        mAktieCubeLayers[kEquator] = new AktieCubeLayer(AktieCubeLayer.kAxisY);
        mAktieCubeLayers[kSide] = new AktieCubeLayer(AktieCubeLayer.kAxisZ);
    }

    /*updating the layers as the cube moves/revolves*/
    private void updateLayers() {
        AktieCubeLayer aktieCubeLayer;
        AktieCubeShape[] shapes;
        int i, j, k;

        // up layer
        aktieCubeLayer = mAktieCubeLayers[kUp];
        shapes = aktieCubeLayer.mShapes;
        for (i = 0; i < 9; i++)
            shapes[i] = mAktieCubes[mPermutation[i]];

        // down layer
        aktieCubeLayer = mAktieCubeLayers[kDown];
        shapes = aktieCubeLayer.mShapes;
        for (i = 18, k = 0; i < 27; i++)
            shapes[k++] = mAktieCubes[mPermutation[i]];

        // left layer
        aktieCubeLayer = mAktieCubeLayers[kLeft];
        shapes = aktieCubeLayer.mShapes;
        for (i = 0, k = 0; i < 27; i += 9)
            for (j = 0; j < 9; j += 3)
                shapes[k++] = mAktieCubes[mPermutation[i + j]];

        // right layer
        aktieCubeLayer = mAktieCubeLayers[kRight];
        shapes = aktieCubeLayer.mShapes;
        for (i = 2, k = 0; i < 27; i += 9)
            for (j = 0; j < 9; j += 3)
                shapes[k++] = mAktieCubes[mPermutation[i + j]];

        // front layer
        aktieCubeLayer = mAktieCubeLayers[kFront];
        shapes = aktieCubeLayer.mShapes;
        for (i = 6, k = 0; i < 27; i += 9)
            for (j = 0; j < 3; j++)
                shapes[k++] = mAktieCubes[mPermutation[i + j]];

        // back layer
        aktieCubeLayer = mAktieCubeLayers[kBack];
        shapes = aktieCubeLayer.mShapes;
        for (i = 0, k = 0; i < 27; i += 9)
            for (j = 0; j < 3; j++)
                shapes[k++] = mAktieCubes[mPermutation[i + j]];

        // middle layer
        aktieCubeLayer = mAktieCubeLayers[kMiddle];
        shapes = aktieCubeLayer.mShapes;
        for (i = 1, k = 0; i < 27; i += 9)
            for (j = 0; j < 9; j += 3)
                shapes[k++] = mAktieCubes[mPermutation[i + j]];

        // equator layer
        aktieCubeLayer = mAktieCubeLayers[kEquator];
        shapes = aktieCubeLayer.mShapes;
        for (i = 9, k = 0; i < 18; i++)
            shapes[k++] = mAktieCubes[mPermutation[i]];

        // side layer
        aktieCubeLayer = mAktieCubeLayers[kSide];
        shapes = aktieCubeLayer.mShapes;
        for (i = 3, k = 0; i < 27; i += 9)
            for (j = 0; j < 3; j++)
                shapes[k++] = mAktieCubes[mPermutation[i + j]];
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //no title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);


        LinearLayout cubecontainer = (LinearLayout) findViewById(R.id.cube_container);
        mView = new GLSurfaceView(getApplication());
        mRenderer = new AktieCubeRender(makeGLWorld(), this);
        mView.setRenderer(mRenderer);

        mView = new GLSurfaceView(getApplication());
        mRenderer = new AktieCubeRender(makeGLWorld(), this);

        /*to make the background transparent*/
        mView.setZOrderOnTop(true);
        mView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mView.getHolder().setFormat(PixelFormat.RGBA_8888);
        mView.setRenderer(mRenderer);

        //adding the rendered cube to the dynamic layout*/
        cubecontainer.addView(mView);

        /*proceed button to move to next screen, actually activity*/
        Button proceedButton = (Button) findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*new activity is started here*/
                Intent intent = new Intent(AktieCubeActivity.this, AktiePagerActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mView.onPause();
    }

    /*animating the cube [movement/rotation*/
    public void animate() {
        // change our angle of view
        mRenderer.setAngle(mRenderer.getAngle() + 1.2f);

        if (mCurrentAktieCubeLayer == null) {
            int layerID = mRandom.nextInt(9);
            mCurrentAktieCubeLayer = mAktieCubeLayers[layerID];
            mCurrentLayerPermutation = mLayerPermutations[layerID];
            mCurrentAktieCubeLayer.startAnimation();
            boolean direction = mRandom.nextBoolean();
            int count = mRandom.nextInt(3) + 1;

            count = 1;
            direction = false;
            mCurrentAngle = 0;
            if (direction) {
                mAngleIncrement = (float)Math.PI / 50;
                mEndAngle = mCurrentAngle + ((float)Math.PI * count) / 2f;
            } else {
                mAngleIncrement = -(float)Math.PI / 50;
                mEndAngle = mCurrentAngle - ((float)Math.PI * count) / 2f;
            }
        }

        mCurrentAngle += mAngleIncrement;

        if ((mAngleIncrement > 0f && mCurrentAngle >= mEndAngle) ||
                (mAngleIncrement < 0f && mCurrentAngle <= mEndAngle)) {
            mCurrentAktieCubeLayer.setAngle(mEndAngle);
            mCurrentAktieCubeLayer.endAnimation();
            mCurrentAktieCubeLayer = null;

            // adjust mPermutation based on the completed layer rotation
            int[] newPermutation = new int[27];
            for (int i = 0; i < 27; i++) {
                newPermutation[i] = mPermutation[mCurrentLayerPermutation[i]];
            }
            mPermutation = newPermutation;
            updateLayers();

        } else {
            mCurrentAktieCubeLayer.setAngle(mCurrentAngle);
        }
    }

    GLSurfaceView mView;
    AktieCubeRender mRenderer;
    AktieCube[] mAktieCubes = new AktieCube[27];
    // a Layer for each possible move
    AktieCubeLayer[] mAktieCubeLayers = new AktieCubeLayer[9];
    // permutations corresponding to a pi/2 rotation of each layer about its axis
    //taken from the internet[stackoverflow quesiton]
    static int[][] mLayerPermutations = {
            // permutation for UP layer
            { 2, 5, 8, 1, 4, 7, 0, 3, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 },
            // permutation for DOWN layer
            { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 20, 23, 26, 19, 22, 25, 18, 21, 24 },
            // permutation for LEFT layer
            { 6, 1, 2, 15, 4, 5, 24, 7, 8, 3, 10, 11, 12, 13, 14, 21, 16, 17, 0, 19, 20, 9, 22, 23, 18, 25, 26 },
            // permutation for RIGHT layer
            { 0, 1, 8, 3, 4, 17, 6, 7, 26, 9, 10, 5, 12, 13, 14, 15, 16, 23, 18, 19, 2, 21, 22, 11, 24, 25, 20 },
            // permutation for FRONT layer
            { 0, 1, 2, 3, 4, 5, 24, 15, 6, 9, 10, 11, 12, 13, 14, 25, 16, 7, 18, 19, 20, 21, 22, 23, 26, 17, 8 },
            // permutation for BACK layer
            { 18, 9, 0, 3, 4, 5, 6, 7, 8, 19, 10, 1, 12, 13, 14, 15, 16, 17, 20, 11, 2, 21, 22, 23, 24, 25, 26 },
            // permutation for MIDDLE layer
            { 0, 7, 2, 3, 16, 5, 6, 25, 8, 9, 4, 11, 12, 13, 14, 15, 22, 17, 18, 1, 20, 21, 10, 23, 24, 19, 26 },
            // permutation for EQUATOR layer
            { 0, 1, 2, 3, 4, 5, 6, 7, 8, 11, 14, 17, 10, 13, 16, 9, 12, 15, 18, 19, 20, 21, 22, 23, 24, 25, 26 },
            // permutation for SIDE layer
            { 0, 1, 2, 21, 12, 3, 6, 7, 8, 9, 10, 11, 22, 13, 4, 15, 16, 17, 18, 19, 20, 23, 14, 5, 24, 25, 26 }
    };



    // current permutation of starting position
    int[] mPermutation;

    // for random cube movements
    Random mRandom = new Random(System.currentTimeMillis());
    // currently turning layer
    AktieCubeLayer mCurrentAktieCubeLayer = null;
    // current and final angle for current Layer animation
    float mCurrentAngle, mEndAngle;
    // amount to increment angle
    float mAngleIncrement;
    int[] mCurrentLayerPermutation;

    // names for our 9 layers (based on notation from http://www.cubefreak.net/notation.html)
    static final int kUp = 0;
    static final int kDown = 1;
    static final int kLeft = 2;
    static final int kRight = 3;
    static final int kFront = 4;
    static final int kBack = 5;
    static final int kMiddle = 6;
    static final int kEquator = 7;
    static final int kSide = 8;

}
