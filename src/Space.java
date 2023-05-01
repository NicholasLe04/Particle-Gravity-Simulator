import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;
public class Space extends JPanel {

    BufferedImage bi;
    int screen_width;
    int screen_height;
    boolean borders;

    float gravitational_constant;
    float dampening_coefficient;
    ArrayList<Particle> particles = new ArrayList<>();

    public Space(int screen_width, int screen_height, boolean borders, float gravitational_constant, float dampening_coefficient) {
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.gravitational_constant = gravitational_constant;
        this.dampening_coefficient = dampening_coefficient;
        this.borders = borders;

        bi = new BufferedImage(screen_width, screen_height, BufferedImage.TYPE_INT_RGB);
        ImageIcon icon = new ImageIcon(bi);
        add(new JLabel(icon));

    }

    public void drawSpace() {
        
        // Set all pixels on screen to black
        for (int y = 0; y < screen_height; y++) {
            for (int x = 0; x < screen_width; x++) {
                Color color = Color.BLACK;
                int colorValue = color.getRGB();
                bi.setRGB(x, y, colorValue);

                // If pixel lies on particle, set pixel to particle's color
                for (Particle particle : particles) {
                    if (particle.onParticle(x, y)) {
                        colorValue = particle.color.getRGB();
                        bi.setRGB(x, y, colorValue);
                        break;
                    } 
                }
            }
        }
    }


    public void start() {
        JFrame frame = new JFrame("Space");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);

        while (true) {
            updateParticles();
            drawSpace();
            frame.repaint();
        }
    }


    public void updateParticles() {

        // For each particle, compute the acceleration induced by every other particle
        for (Particle particle : particles) {
            float dx_vel = 0;
            float  dy_vel = 0;
            for (Particle particle_2 : particles) {
                if (!particle.equals(particle_2)) {
                    particle.handleCollision(particle_2);
                    
                    float d_x = particle_2.x - particle.x;
                    float d_y = particle_2.y - particle.y;
                    float distance = particle.distance(particle_2);
                    if (distance > 0) {
                        float force = gravitational_constant * ((particle_2.mass)/(distance*distance));
                        dx_vel += (force * d_x)/particle.mass;
                        dy_vel += (force * d_y)/particle.mass;
                    }
                }
            }
            particle.updateVelocity(dx_vel, dy_vel, dampening_coefficient);
            particle.updatePosition();

            // If borders are active, particles cannot leave the screen
            if (borders) {
                if (particle.x <= 0 || particle.x >= screen_width) {
                    particle.x_vel = -particle.x_vel;
                    if (particle.x <= 0) {
                        particle.x = 1;
                    }
                    else {
                        particle.x = screen_width - 1;
                    }
                }
                if (particle.y <= 0 || particle.y >= screen_height) {
                    particle.y_vel = -particle.y_vel;
                    if (particle.y <= 0) {
                        particle.y = 1;
                    }
                    else {
                        particle.y = screen_height - 1;
                    }
                }
            }
        }
    }

    public void addParticles(int count, int radius, Color color, int mass) {
        Random rn = new Random();
        for (int i = 0; i < count; i++) {
            int p_x = rn.nextInt(screen_width);
            int p_y = rn.nextInt(screen_height);
            Particle newPart = new Particle(p_x, p_y, radius, mass, color);
            particles.add(newPart);
        }
    }

    public void addParticle(int x_position, int y_position, float init_x_vel, float init_y_vel, int radius, int mass, Color color) {
        particles.add(new Particle(x_position, y_position, init_x_vel, init_y_vel, radius, mass, color));
    }

}

