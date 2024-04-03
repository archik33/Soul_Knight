import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
    double x;
    double y;
    double vx;
    double vy;
    double raz_x;
    double raz_y;
    double razbor = 50;
    Random r = new Random();
    BufferedImage image;
    int lifeMax = 100;
    int lifes = lifeMax;
    boolean death = true;
    boolean getHitSword = false;

    ArrayList<Bullet> bullets = new ArrayList<>();
    int n = 0;
    int[] time_shot = {0, 250, 400, 600};
    int timer = 0;
    int speed = 1;
    int score = 1;
    int l;
    int weaponType;
    // 1 - pistol ; 2 - shotgun ; 3 - sniperka;
    double h, W;

    public Enemy(double x, double y, double vx, double vy, int weaponType) throws IOException {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
//        this.image = ImageIO.read(new File("data\\enemy_shot_picture1.png"));
        this.image = ImageIO.read(new File("data\\player_picture1.png"));
        this.h = image.getHeight();
        this.W = image.getWidth();
        this.weaponType = weaponType;
        choose();
    }

    void paint(Graphics g, int height, int wight, int player_x, int player_y, boolean pause) {
        if (death) {

        } else {
            g.drawImage(image, (int) x, (int) y, 50, 50, null);
            if (!pause) {
                if (timer >= time_shot[weaponType]) {
                    shot(player_x, player_y);
                    timer = 0;
                } else {
                    ++timer;
                }
                update_random();
                if (x < 50 || x + 50 > wight - 50 || y < 62 || y + 50 > height - 55) {
                    update(height, wight);
                }
            }
        }
        for (int i = 0; i <= n - 1; ++i) {
            Color q = new Color(210, 1, 27, 255);
            g.setColor(q);
            bullets.get(i).paint(g);
            if (!pause) {
                bullets.get(i).update();
            }
            if (bullets.get(i).x >= 2000 || bullets.get(i).y >= 1000 || bullets.get(i).x <= -100 || bullets.get(i).y <= -100) {
                remove(i);
            }
        }
    }

    void kill() {
        lifes = 0;
        death = true;
    }

    void shot(int player_x, int player_y) {
        if (weaponType == 1) {
            shot_pistol(player_x, player_y);
        }
        if (weaponType == 2) {
            shot_shotgun(player_x,player_y);
        }
    }

    void shot_pistol(int player_x, int player_y) {
        raz_x = razbor * r.nextDouble() - razbor / 2;
        raz_y = razbor * r.nextDouble() - razbor / 2;
        Bullet bullet = new Bullet(x + 25, y + 25, player_x + 25 + raz_x, player_y + 25 + raz_y, 3);
        bullets.add(bullet);
        ++n;
    }

    void shot_shotgun(int player_x, int player_y) {
        double a = Math.atan2((player_x - x - 25), -(player_y - y - 25));
        for (double j = 0; j < 10; j++) {
            double a1 = a + (j - 5) / 30;
            Bullet bullet = new Bullet(x + 25, y + 25, x + 25 + 5 * Math.sin(a1), y + 25 - 5 * Math.cos(a1), 5);
            bullets.add(bullet);
            ++n;
        }
    }

    void choose() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        double len = (x * x + y * y);
        len = Math.sqrt(len);
        vx = x / (len * speed);
        vy = y / (len * speed);
    }

    void update_block(int a) {
        if (a == 1) {
            l = 1;
            double x = r.nextDouble();
            double y = r.nextDouble();
            int znak = r.nextInt(0, 1);
            if (znak == 0) {
                znak--;
            }
            double len = (x * x + y * y);
            len = Math.sqrt(len);
            vx = Math.abs(x) * l / (len * speed);
            vy = znak * y / (len * speed);
        }
        if (a == 2) {
            l = -1;
            double x = r.nextDouble();
            double y = r.nextDouble();
            int znak = r.nextInt(0, 1);
            if (znak == 0) {
                znak--;
            }
            double len = (x * x + y * y);
            len = Math.sqrt(len);
            vx = Math.abs(x) * l / (len * speed);
            vy = znak * y / (len * speed);
        }
        if (a == 3) {
            l = 1;
            double x = r.nextDouble();
            double y = r.nextDouble();
            double len = (x * x + y * y);
            len = Math.sqrt(len);
            vx = x / (len * speed);
            vy = Math.abs(y) * l / (len * speed);
        }
        if (a == 4) {
            l = -1;
            double x = r.nextDouble();
            double y = r.nextDouble();
            double len = (x * x + y * y);
            len = Math.sqrt(len);
            vx = x / (len * speed);
            vy = Math.abs(y) * l / (len * speed);
        }
    }

    void update(int height, int wight) {
        if (x < 50 || x + 50 > wight - 50) {
            if (x < 50) {
                l = 1;
            } else {
                l = -1;
            }
            double x = r.nextDouble();
            double y = r.nextDouble();
            int znak = r.nextInt(0, 2);
            if (znak == 0) {
                znak--;
            }
            double len = (x * x + y * y);
            len = Math.sqrt(len);
            vx = Math.abs(x) * l / (len * speed);
            vy = znak * y / (len * speed);
        }
        if (y < 62 || y + 50 > height - 55) {
            if (y < 62) {
                l = 1;
            } else {
                l = -1;
            }
            double x = r.nextDouble();
            double y = r.nextDouble();
            double len = (x * x + y * y);
            len = Math.sqrt(len);
            vx = x / (len * speed);
            vy = Math.abs(y) * l / (len * speed);
        }
    }

    void update_random() {
        x += vx;
        y += vy;
    }

    void remove(int i) {
        bullets.remove(i);
        n--;
    }

    void is_death() {
        if (lifes <= 0) {
            death = true;
            MyFrame.score += score;
            MyFrame.allScore += score;
        }
    }

    void respawn() {
        lifes = lifeMax;
        death = false;
    }

    void hit(double damage) {
        lifes -= (int) damage;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
        this.lifeMax = lifes;
    }
}
