package cullycross.particlestutorial.programs;

import android.content.Context;

import cullycross.particlestutorial.utils.ShaderHelper;
import cullycross.particlestutorial.utils.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by cullycross on 2/17/15.
 */
public abstract class ShaderProgram {

    //Uniforms
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";
    protected static final String U_TIME = "u_Time";

    //Attributes
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    protected static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";

    //Shader
    protected final int program;
    protected ShaderProgram(Context context, int vertexShaderResourceId,
                            int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(
                        context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(
                        context, fragmentShaderResourceId));
    }

    public void useProgram() {
        glUseProgram(program);
    }
}
