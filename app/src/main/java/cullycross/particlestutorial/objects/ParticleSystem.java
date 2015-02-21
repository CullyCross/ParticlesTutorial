package cullycross.particlestutorial.objects;

import android.graphics.Color;
import android.opengl.GLES20;

import cullycross.particlestutorial.data.VertexArray;
import cullycross.particlestutorial.programs.ParticleShaderProgram;
import cullycross.particlestutorial.utils.Geometry;

import static cullycross.particlestutorial.Constants.*;

/**
 * Created by cullycross on 2/21/15.
 */
public class ParticleSystem {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int VECTOR_COMPONENT_COUNT = 3;
    private static final int PARTICLE_START_TIME_COMPONENT_COUNT = 3;

    private static final int TOTAL_COMPONENT_COUNT =
                    POSITION_COMPONENT_COUNT +
                    COLOR_COMPONENT_COUNT +
                    VECTOR_COMPONENT_COUNT +
                    PARTICLE_START_TIME_COMPONENT_COUNT;

    private static final int STRIDE = TOTAL_COMPONENT_COUNT * BYTES_PER_FLOAT;

    private final float[] mParticles;
    private final VertexArray mVertexArray;
    private final int mMaxParticleCount;

    private int mCurrentParticleCount;
    private int mNextParticle;

    public ParticleSystem(int maxParticleCount) {
        mParticles = new float[maxParticleCount * TOTAL_COMPONENT_COUNT];
        mVertexArray = new VertexArray(mParticles);
        this.mMaxParticleCount = maxParticleCount;
    }

    public void addParticle(Geometry.Point position,
                            int color, Geometry.Vector direction,
                            float particleStartTime) {
        final int particleOffset = mNextParticle * TOTAL_COMPONENT_COUNT;

        int currentOffset = particleOffset;
        mNextParticle++;

        if(mCurrentParticleCount < mMaxParticleCount) {
            mCurrentParticleCount++;
        }

        if(mNextParticle == mMaxParticleCount) {
            mNextParticle = 0;
        }

        mParticles[currentOffset++] = position.x;
        mParticles[currentOffset++] = position.y;
        mParticles[currentOffset++] = position.z;

        mParticles[currentOffset++] = Color.red(color) / 255f;
        mParticles[currentOffset++] = Color.green(color) / 255f;
        mParticles[currentOffset++] = Color.blue(color) / 255f;

        mParticles[currentOffset++] = direction.x;
        mParticles[currentOffset++] = direction.y;
        mParticles[currentOffset++] = direction.z;

        mParticles[currentOffset] = particleStartTime;

        mVertexArray.updateBuffer(mParticles,
                particleOffset, TOTAL_COMPONENT_COUNT);
    }

    public void bindData(ParticleShaderProgram particleProgram) {
        int dataOffset = 0;
        mVertexArray.setVertexAttribPointer(dataOffset,
                particleProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, STRIDE);

        dataOffset += POSITION_COMPONENT_COUNT;

        mVertexArray.setVertexAttribPointer(dataOffset,
                particleProgram.getColorAttributeLocation(),
                COLOR_COMPONENT_COUNT, STRIDE);

        dataOffset += COLOR_COMPONENT_COUNT;

        mVertexArray.setVertexAttribPointer(dataOffset,
                particleProgram.getDirectionVectorAttributeLocation(),
                VECTOR_COMPONENT_COUNT, STRIDE);

        dataOffset += VECTOR_COMPONENT_COUNT;

        mVertexArray.setVertexAttribPointer(dataOffset,
                particleProgram.getParticleStartTimeAttributeLocation(),
                PARTICLE_START_TIME_COMPONENT_COUNT, STRIDE);
    }

    public void draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, mCurrentParticleCount);
    }
}
