import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class player implements KeyEventDispatcher, MouseListener, MouseMotionListener {
    double x;
    double y;
    double h;
    double W;
    BufferedImage image;
    double vx;
    double vy;
    boolean pressed = false;
    double razbor = 50;
    Random r = new Random();
    boolean w, a, s, d;
    int timer = 0;
    ArrayList<Bullet> bullets = new ArrayList<>();
    double raz_x;
    double raz_y;
    int bullet_counter = 0;
    int time_blink = 500;
    int Frame_Heigth;
    int Frame_Width;
    double lifesMax = 1000;
    double lifes = lifesMax;
    boolean death;
    int typeWeapon = 0;
    int Weapon_max = 5;
    boolean hand_punch;
    boolean pause = false;
    boolean esc_pressed = false;
    boolean respawn = false;
    double[] AllReloads = {1, 20, 100, 1, 10, 100, 150, 100};
    String[] AllNames_start = {"hand", "pistolet", "drobash", "MegaGun", "sniperka", "zvezda", "sloopy", "drob`"};
    String[] AllNames = {"hand", "pistolet", "drobash", "MegaGun", "sniperka", "zvezda", "sloopy", "drob`"};
    double timer_reload = 0;
    double speed = 1;
    double damage = 1;

    public player(double x, double y, double vx, double vy, int heignt, int width) throws IOException {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.Frame_Heigth = heignt - 210;
        this.Frame_Width = width - 100;
        this.image = ImageIO.read(new File("data\\player_picture1.png"));
        this.W = image.getWidth();
        this.h = image.getHeight();

    }

    void update(int heignt, int weght) {
        this.x += vx;
        this.y += vy;
        if (w) {
            vy = -2 * speed;
        }
        if (a) {
            vx = -2 * speed;
        }
        if (d) {
            vx = 2 * speed;
        }
        if (s) {
            vy = 2 * speed;
        }
        timer_reload++;
        ++timer;
        if (x + 50 >= weght) {
            vx = 0;
            x = weght - 50;
        } else if (y + 50 >= heignt) {
            vy = 0;
            y = heignt - 50;
        } else if (x < 0) {
            vx = 0;
            x = 0;
        } else if (y < 0) {
            vy = 0;
            y = 0;
        }

    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString(AllNames[typeWeapon], 60, 700);
        g.drawImage(image, (int) x, (int) y, null);
        Color q2 = new Color(147, 43, 43, 181);
        g.setColor(q2);
        g.fillRect(20, 750, 300, 20);
        g.setColor(new Color(224, 14, 14, 250));
        g.fillRect(20, 750, (int) (lifes / lifesMax * 300), 20);
        Color t2 = new Color(77, 154, 51, 181);
        g.setColor(t2);
        g.fillRect(20, 740, 300, 10);
        Color t1 = new Color(84, 229, 51, 250);
        g.setColor(t1);
        g.fillRect(20, 740, (int) (Math.min(timer_reload, AllReloads[typeWeapon]) / (AllReloads[typeWeapon]) * 300), 10);
        for (int i = 0; i <= bullet_counter - 1; ++i) {
            Color q = new Color(0, 0, 0);
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
        death = true;
    }

    void remove(int i) {
        bullets.remove(i);
        bullet_counter--;
    }

    void is_death() {
        if (lifes <= 0) {
            pause = true;
            death = true;
        }
    }

    void respawn() {
        x = 100;
        y = 100;
        lifes = lifesMax;
        death = false;
        pause = false;
    }

    void NewWeapon() {
        Weapon_max++;
        Weapon_max = Math.min(Weapon_max, AllNames.length);
    }

    void hit(double damage) {
        lifes -= (int) damage;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_SPACE && !pause) {
            if (timer > time_blink) {
                timer = 0;
                if (a && w) {
                    x = Math.max(x - 100, 50);
                    y = Math.max(y - 100, 70);
                } else if (a && s) {
                    y = Math.min(y + 100, Frame_Heigth);
                    x = Math.max(x - 100, 50);
                } else if (d && w) {
                    x = Math.min(x + 100, Frame_Width);
                    y = Math.max(y - 100, 70);
                } else if (d && s) {
                    x = Math.min(x + 100, Frame_Width);
                    y = Math.min(y + 100, Frame_Heigth);
                } else if (a) {
                    x = Math.max(x - 200, 50);
                } else if (w) {
                    y = Math.max(y - 200, 70);
                } else if (s) {
                    y = Math.min(y + 200, Frame_Heigth);
                } else {
                    x = Math.min(x + 200, Frame_Width);
                }
            }
        }
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_W) {
            w = true;
        }
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_S) {
            s = true;
        }
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_D) {
            d = true;
        }
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_A) {
            a = true;
        }
        if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_W) {
            w = false;
            vx = 0;
            vy = 0;
        }
        if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_A) {
            a = false;
            vx = 0;
            vy = 0;
        }
        if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_S) {
            s = false;
            vx = 0;
            vy = 0;
        }
        if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_D) {
            d = false;
            vx = 0;
            vy = 0;
        }
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_E) {
            typeWeapon = ((typeWeapon + 1) % Weapon_max);
        }
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE && esc_pressed) {
            pause = false;
            esc_pressed = false;
        } else if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE && !esc_pressed) {
            pause = true;
            esc_pressed = true;
        }
        return false;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setRespawn(boolean respawn) {
        this.respawn = respawn;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!pause) {
            if (timer_reload >= AllReloads[typeWeapon]) {
                shot(e);
                timer_reload = 0;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!pause) {
            if (timer_reload >= AllReloads[typeWeapon]) {
                shot(e);
                timer_reload = 0;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    boolean chek_two_rect(double x, double y, double w, double h, double x1, double y1, double w1, double h1) {
        if (chek_point_rect(x, y, x1, y1, w1, h1) || chek_point_rect(x, y + h, x1, y1, w1, h1) || chek_point_rect(x + w, y, x1, y1, w1, h1) || chek_point_rect(x + w, y + h, x1, y1, w1, h1)) {
            return true;
        } else {
            return false;
        }

    }

    boolean chek_point_rect(double x, double y, double x1, double y1, double w1, double h1) {
        if (x > x1 && x < x1 + w1 && y > y1 && y < y1 + h1) {
            return true;
        } else {
            return false;
        }
    }


    void shot(MouseEvent e) {
        if (typeWeapon == 0) {
            if (!hand_punch) {
                hand_punch = true;
            }
        }
        if (typeWeapon == 1) {
            raz_x = razbor * r.nextDouble() - razbor / 2;
            raz_y = razbor * r.nextDouble() - razbor / 2;
            Bullet bullet = new Bullet(x + 25, y + 25, e.getX() + raz_x, e.getY() + raz_y, 4);
            bullets.add(bullet);
            ++bullet_counter;
            bullet.damage *= damage;
            pressed = true;
        } else if (typeWeapon == 2) {
            double a = Math.atan2((e.getX() - x - 25), -(e.getY() - y - 25));
            raz_x = razbor * r.nextDouble() - razbor / 2;
            raz_y = razbor * r.nextDouble() - razbor / 2;
            for (double j = 0; j < 10; j++) {
                double a1 = a + (j - 5) / 30;
                Bullet bullet = new Bullet(x + 25, y + 25, x + 25 + 5 * Math.sin(a1), y + 25 - 5 * Math.cos(a1), 5);
                bullets.add(bullet);
                ++bullet_counter;
                bullet.damage *= damage;
            }
            pressed = true;
        } else if (typeWeapon == 3) {
            for (int j = 0; j < 100; j++) {
                Bullet bullet = new Bullet(x + 25, y + 25, e.getX() + (j - 3) * 100, e.getY() + (j - 3) * 100, 3);
                bullets.add(bullet);
                ++bullet_counter;
                bullet.damage *= damage;
            }
            pressed = true;
        } else if (typeWeapon == 4) {
            raz_x = razbor * r.nextDouble() - razbor / 2;
            raz_y = razbor * r.nextDouble() - razbor / 2;
            Bullet bullet = new Bullet(x + 25, y + 25, e.getX() + raz_x, e.getY() + raz_y, 10, 70);
            bullet.damage = 100;
            bullets.add(bullet);
            ++bullet_counter;
            pressed = true;
            bullet.damage *= damage;
        } else if (typeWeapon == 5) {
            double a = Math.atan2(e.getX() - x - 25, e.getY() - y - 25);
            for (int j = 0; j < 19; j++) {
                double a1 = a + (j - 10);
                Bullet bullet = new Bullet(x + 25, y + 25, x + 25 + 100 * Math.cos(a1), y + 25 + 100 * Math.sin(a1), 1);
                bullets.add(bullet);
                ++bullet_counter;
                bullet.damage *= damage;
            }
        } else if (typeWeapon == 6) {
            raz_x = razbor * r.nextDouble() - razbor / 2;
            raz_y = razbor * r.nextDouble() - razbor / 2;
            for (int j = 0; j < 8; j++) {
                Bullet bullet = new Bullet(x + 25, y + 25, e.getX() + raz_x, e.getY() + raz_y, j + 1);
                bullet.damage = 10 - j;
                bullets.add(bullet);
                ++bullet_counter;
                bullet.damage *= damage;
            }
            pressed = true;
        } else if (typeWeapon == 7) {
            double a = Math.atan2((e.getX() - x - 25), -(e.getY() - y - 25));
            for (double j = 0; j < 30; j++) {
                double a1 = a + (Math.random() - 0.5) / 2;
                Bullet bullet = new Bullet(x + 25, y + 25, x + 25 + 5 * Math.sin(a1), y + 25 - 5 * Math.cos(a1), (Math.random() + 1) * 4, 4);
                bullet.damage = 1;
                bullets.add(bullet);
                ++bullet_counter;
                bullet.damage *= damage;
            }
            pressed = true;
        }
    }

    public void setLifes(double lifes) {
        this.lifes = lifes;
        lifesMax = lifes;
    }

    double update_swordX(double x) {
        return x + vx;
    }

    double update_swordY(double y) {
        return y + vy;
    }
}