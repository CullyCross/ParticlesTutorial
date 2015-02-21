package cullycross.particlestutorial.programs;

import android.content.Context;
import android.opengl.GLES20;

import cullycross.particlestutorial.R;

/**
 * Created by cullycross on 2/21/15.
 */
public class ParticleShaderProgram extends ShaderProgram {

    //Uniforms
    private final int uMatrixLocation;
    private final int uTimeLocation;

    //Attributes
    private final int aPositionLocation;
    private final int aColorLocation;
    private final int aDirectionVectorLocation;
    private final int aParticleStartTimeLocation;

    public ParticleShaderProgram(Context context) {
        super(context, R.raw.particle_vertex_shader,
                R.raw.particle_fragment_shader);

        //uniforms
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTimeLocation = GLES20.glGetUniformLocation(program, U_TIME);

        //attributes
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        aDirectionVectorLocation =
                GLES20.glGetAttribLocation(program, A_DIRECTION_VECTOR);
        aParticleStartTimeLocation =
                GLES20.glGetAttribLocation(program, A_PARTICLE_START_TIME);
    }

    public void setUniforms(float[] matrix, float elapsedTime) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        GLES20.glUniform1f(uTimeLocation, elapsedTime);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getColorAttributeLocation() {
        return aColorLocation;
    }

    public int getDirectionVectorAttributeLocation() {
        return aDirectionVectorLocation;
    }

    public int getParticleStartTimeAttributeLocation() {
        return aParticleStartTimeLocation;
    }


}
