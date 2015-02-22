package cullycross.particlestutorial.objects;

import android.opengl.Matrix;

import java.util.Random;

import cullycross.particlestutorial.utils.Geometry;

/**
 * Created by cullycross on 2/22/15.
 */
public class ParticleShooter {

    private final Geometry.Point mPosition;
    private final int mColor;

    private final float mAngleVariance;
    private final float mSpeedVariance;

    private final Random mRandom = new Random();

    private float [] mRotationMatrix = new float[16];
    private float [] mDirectionVector = new float[4];
    private float [] mResultVector = new float[4];

    public ParticleShooter(Geometry.Point position,
                           Geometry.Vector direction,
                           int color,
                           float angleVarianceInDegrees,
                           float speedVariance) {

        this.mPosition = position;
        this.mColor = color;
        this.mAngleVariance = angleVarianceInDegrees;
        this.mSpeedVariance = speedVariance;
        this.mDirectionVector[0] = direction.x;
        this.mDirectionVector[1] = direction.y;
        this.mDirectionVector[2] = direction.z;
    }

    public void addParticles(ParticleSystem particleSystem,
                             float currentTime, int count) {
        for(int i = 0; i < count; i++) {

            Matrix.setRotateEulerM(mRotationMatrix, 0,
                    (mRandom.nextFloat() - 0.5f) * mAngleVariance,
                    (mRandom.nextFloat() - 0.5f) * mAngleVariance,
                    (mRandom.nextFloat() - 0.5f) * mAngleVariance
                );

            Matrix.multiplyMV(mResultVector, 0,
                    mRotationMatrix, 0, mDirectionVector, 0);

            float speedAdjustment =
                    1f + mRandom.nextFloat() * mSpeedVariance;

            Geometry.Vector thisDirection = new Geometry.Vector(
                    mResultVector[0] * speedAdjustment,
                    mResultVector[1] * speedAdjustment,
                    mResultVector[2] * speedAdjustment
            );

            particleSystem.addParticle(mPosition, mColor, thisDirection, currentTime);
        }
    }

}
