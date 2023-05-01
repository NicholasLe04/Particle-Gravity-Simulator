import java.awt.Color;
import java.lang.Math;

public class Particle {
    float x; 
    float y;
    float x_vel = 0;
    float y_vel = 0;
    int radius;
    float mass;
    Color color;

    public Particle(int x, int y, int radius, int mass, Color color) {
        this.x = (float)x;
        this.y = (float)y;
        this.radius = radius;
        this.mass = (float)mass;
        this.color = color;
    }

    
    public Particle(int x, int y, float x_vel, float y_vel, int radius, int mass, Color color) {
        this.x = (float)x;
        this.y = (float)y;
        this.x_vel = x_vel;
        this.y_vel = y_vel;
        this.radius = radius;
        this.mass = (float)mass;
        this.color = color;
    }

    public boolean onParticle(int x, int y) {
        if (((x - this.x) * (x - this.x)) + ((y - this.y) * (y - this.y)) < (radius*radius)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void updatePosition() {
        x += x_vel;
        y += y_vel;
    }

    public void updateVelocity(float dx_vel, float dy_vel, float damping_coefficient) {
        x_vel += (1-damping_coefficient) * dx_vel;
        y_vel += (1-damping_coefficient) * dy_vel;
    }

    public float distance(Particle particle) {
        return (float)Math.sqrt(((particle.x-x)*(particle.x-x)) + ((particle.y-y)*((particle.y-y))));
    }


    public void handleCollision(Particle particle) {
        float dist = distance(particle);
        float min_dist = particle.radius + radius;

        if (dist < (radius + particle.radius)) {
            float dx = 0;
            float dy = 0;
            dx = (((particle.x - x)*min_dist)/dist) - (particle.x - x);
            dy = (((particle.y - y)*min_dist)/dist) - (particle.y - y);
    
            particle.x += dx;
            particle.y += dy;
        }
    }
}
