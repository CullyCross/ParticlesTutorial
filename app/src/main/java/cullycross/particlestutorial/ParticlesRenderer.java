package cullycross.particlestutorial;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cullycross.particlestutorial.objects.ParticleShooter;
import cullycross.particlestutorial.objects.ParticleSystem;
import cullycross.particlestutorial.programs.ParticleShaderProgram;
import cullycross.particlestutorial.utils.Geometry;
import cullycross.particlestutorial.utils.MatrixHelper;

/**
 * Created by cullycross on 2/14/15.
 */
public class ParticlesRenderer implements GLSurfaceView.Renderer {

    private Context mContext;

    private final float [] mProjectionMatrix = new float[16];
    private final float [] mViewMatrix = new float[16];
    private final float [] mViewProjectionMatrix = new float[16];

    private ParticleShaderProgram mParticleProgram;
    private ParticleSystem mParticleSystem;
    private ParticleShooter mRedParticleShooter;
    private ParticleShooter mGreenParticleShooter;
    private ParticleShooter mBlueParticleShooter;
    private long mGlobalStartTime;


    public ParticlesRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mParticleProgram = new ParticleShaderProgram(mContext);
        mParticleSystem = new ParticleSystem(10000);
        mGlobalStartTime = System.nanoTime();

        final Geometry.Vector particleDirection =
                new Geometry.Vector(0f, 0.5f, 0f);

        mRedParticleShooter = new ParticleShooter(
                new Geometry.Point(-1f, 0f, 0f),
                particleDirection,
                Color.rgb(255, 50, 5)
        );

        mGreenParticleShooter = new ParticleShooter(
                new Geometry.Point(0f, 0f, 0f),
                particleDirection,
                Color.rgb(25, 255, 25)
        );

        mBlueParticleShooter = new ParticleShooter(
                new Geometry.Point(1f, 0f, 0f),
                particleDirection,
                Color.rgb(5, 50, 255)
        );
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(mProjectionMatrix, 45,
                (float) width / (float) height, 1f, 10f);

        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.translateM(mViewMatrix, 0, 0f, -1.5f, -5f);
        Matrix.multiplyMM(mViewProjectionMatrix, 0,
                mProjectionMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        float currentTime = (System.nanoTime() - mGlobalStartTime) / 1000000000f;

        mRedParticleShooter.addParticles(mParticleSystem, currentTime, 5);
        mGreenParticleShooter.addParticles(mParticleSystem, currentTime, 5);
        mBlueParticleShooter.addParticles(mParticleSystem, currentTime, 5);

        mParticleProgram.useProgram();
        mParticleProgram.setUniforms(mViewProjectionMatrix, currentTime);
        mParticleSystem.bindData(mParticleProgram);
        mParticleSystem.draw();

    }
}
