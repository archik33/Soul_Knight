import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class Button implements MouseListener, MouseMotionListener {
    int x;
    int y;
    String name;
    int height;
    int weight;
    ButtonListener listener;
    boolean click = false;
    boolean pressed = false;
    BufferedImage image;
    boolean picture = false;

    public Button(int x, int y, int height, int weight, BufferedImage image, ButtonListener listener) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.height = height;
        this.weight = weight;
        this.listener = listener;
        picture = true;
    }

    public Button(int x, int y, int height, int weight, String name, ButtonListener listener) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.listener = listener;
    }

    void paint(Graphics g) {
        if (picture) {
            g.drawImage(image, x, y, null);
        } else {
            if (!click) {
                g.drawRect(x, y, weight, height);
                g.drawString(name, x + 10, y + height / 2);
            } else if (pressed) {
                g.drawRect(x + 10, y + 10, weight - 20, height - 20);
                g.drawString(name, x + 10, y + height / 2);
            } else if (click) {
                g.drawRect(x - 20, y - 20, weight + 40, height + 40);
                g.drawString(name, x + 10, y + height / 2);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getY() > y && e.getY() < y + height && e.getX() > x && e.getX() < x + weight) {
            listener.Something();
            pressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getY() > y && e.getY() < y + height && e.getX() > x && e.getX() < x + weight) {
            pressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getY() > y && e.getY() < y + height && e.getX() > x && e.getX() < x + weight) {
            click = true;
        } else {
            click = false;
        }
    }
}