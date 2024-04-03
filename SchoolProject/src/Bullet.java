import java.awt.*;

public class Bullet {
    double x;
    double y;
    double x1;
    double y1;
    double vy;
    double vx;
    double x2;
    double y2;
    double len_bullet = 10;
    double vo;
    double damage = 10;

    public Bullet(double x, double y, double x1, double y1, double v0) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.vo = v0;
        double len = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
        this.x2 = (x1 - x) / len * len_bullet + x;
        this.y2 = (y1 - y) / len * len_bullet + y;
        this.vy = (y1 - y) / len * v0;
        this.vx = (x1 - x) / len * v0;
    }

    public Bullet(double x, double y, double x1, double y1, double v0, double Len) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.y1 = y1;
        this.vo = v0;
        len_bullet = Len;
        double len = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
        this.x2 = (x1 - x) / len * len_bullet + x;
        this.y2 = (y1 - y) / len * len_bullet + y;
        this.vy = (y1 - y) / len * v0;
        this.vx = (x1 - x) / len * v0;
    }

    void update() {
        x += vx;
        y += vy;
        x2 += vx;
        y2 += vy;
    }

    void paint(Graphics g) {
        g.drawLine((int) x, (int) y, (int) x2, (int) y2);
    }
}