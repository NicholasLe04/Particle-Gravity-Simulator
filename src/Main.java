import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        Space space = new Space(1000, 1000, false, 1f, 0.0f);
        space.addParticle(500, 500, 0, 0, 50, 1000, Color.ORANGE);
        space.addParticle(200, 500, 0, 10, 10, 10, Color.BLUE);
        space.addParticle(180, 500, 0, 10, 5, 5, Color.GRAY);
        space.addParticle(800, 500, 0, -10, 10, 10, Color.BLUE);
        space.addParticle(820, 500, 0, -10, 5, 5, Color.GRAY);
        space.start();
    }
}

