import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Block {
    double x;
    double y;
    double height;
    double width;
    double lifes;
    double lifesMax;
    boolean death = false;
    Color color;
    BufferedImage image;
    boolean isPicture = false;

    public Block(double x, double y, double height, double width, double lifes, Color color) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.lifes = lifes;
        this.lifesMax = lifes;
        this.color = color;
    }

    public Block(double x, double y, double height, double width, double lifes, int picture) throws IOException {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.lifes = lifes;
        this.lifesMax = lifes;
        this.isPicture = true;
        if (picture == 1) {
            this.image = ImageIO.read(new File("data\\Wood_Box.png"));
        }
    }

    void paint(Graphics g) {
        if (!death) {
            if (isPicture) {
                g.drawImage(image, (int) x, (int) y, null);
            } else {
                g.setColor(color);
                g.fillRect((int) x, (int) y, (int) width, (int) height);
            }
        }
    }

    void hit(double damage) {
        lifes -= damage;
        if (lifes <= 0 && lifes > -1000) {
            death = true;
        }
    }

    void respawn() {
        lifes = lifesMax;
        death = false;
    }


}
