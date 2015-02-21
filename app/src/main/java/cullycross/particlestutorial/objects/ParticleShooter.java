package cullycross.particlestutorial.objects;

import cullycross.particlestutorial.utils.Geometry;

/**
 * Created by cullycross on 2/22/15.
 */
public class ParticleShooter {

    private final Geometry.Point mPosition;
    private final Geometry.Vector mDirection;
    private final int mColor;

    public ParticleShooter(Geometry.Point position,
                           Geometry.Vector direction,
                           int color) {

        this.mPosition = position;
        this.mDirection = direction;
        this.mColor = color;
    }

    public void addParticles(ParticleSystem particleSystem,
                             float currentTime, int count) {
        for(int i = 0; i < count; i++) {
            particleSystem.addParticle(mPosition, mColor, mDirection, currentTime);
        }
    }

}
