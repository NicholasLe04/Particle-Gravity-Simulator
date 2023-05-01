import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        Space space = new Space(1000, 1000, false, 1f, 0.0f);
        space.addParticle(500, 500, 0, 0, 50, 1000, Color.ORANGE);
        space.addParticle(250, 500, 0, 10, 10, 10, Color.BLUE);
        space.addParticle(750, 500, 0, -10, 10, 10, Color.BLUE);
        space.start();
    }
}