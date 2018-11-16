package com.example.dong.testcpp;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Environment;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRender implements GLSurfaceView.Renderer {
    private Context context;
    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] viewProjectionMatrix = new float[16];

    MyRender(Context context) {
        this.context = context;
    }

    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GL2JNILib.onDrawFrame();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix.setIdentityM(projectionMatrix, 0);
        Matrix.perspectiveM(projectionMatrix, 0, 45, (float)width / (float)height, 1, 5f);
        Matrix.setLookAtM(viewMatrix, 0, 0, 2, 3, 0, 0, 0, 0, 1, 0);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        GL2JNILib.onProjectionChanged(viewProjectionMatrix);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Do nothing.
        String gltfFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/box01.glb";
        GL2JNILib.init(gltfFile);
        GLES20.glClearColor(1, 1, 1, 0);
    }
}
