import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Durman {
    double x;
    double y;
    BufferedImage image;
    int lifes = 1000;
    int lifeMax = 1000;
    boolean death = false;
    boolean damage_sunstike = false;
    int count_sunstrikeMax = 2;
    int count_sunstrike = 0;
    Random r = new Random();
    ArrayList<Bullet> bullets = new ArrayList<>();
    double[] AllReloads = {500, 800, 800};
    int timer = 0;
    int l = 0;
    int timer_phase_3 = 0;
    int[] time_sun_strike = {300, 500};
    Color sunstike_1 = new Color(250, 37, 103, 220);
    Color sunstike_2 = new Color(232, 2, 2, 223);
    ArrayList<pair> sunstrikes = new ArrayList<>();
    int sunstrike = 15;
    int phase = 1;
    int bullet_counter = 0;
    double h;

    public Durman(double x, double y) throws IOException {
        this.x = x;
        this.y = y;
        this.image = ImageIO.read(new File("data\\Invoker2.png"));
        this.h = image.getHeight();
    }

    void paint(Graphics g, double player_x, double player_y, boolean pause) {
        ++timer;
        if (!death) {
            Color q2 = new Color(147, 43, 43, 181);
            g.setColor(q2);
            g.fillRect(400, 60, 500, 20);
            Color q1 = new Color(224, 14, 14, 250);
            g.setColor(q1);
            g.fillRect(400, 60, lifes / 2, 20);
            g.drawImage(image, (int) x, (int) y, null);
            if(!pause){
                if (phase == 1) {
                    if (timer >= AllReloads[phase - 1]) {
                        phase_1(player_x, player_y);
                        timer = 0;
                    } else {
                        ++timer;
                    }
                } else if (phase == 2) {
                    if (timer >= AllReloads[phase - 1]) {
                        phase_2(player_x, player_y);
                        timer = 0;
                    } else {
                        ++timer;
                    }
                } else if (phase == 3) {
                    phase_3(player_x, player_y, g);
                } else if (phase == 4) {
                    phase_3(player_x, player_y, g);
                    if (timer >= AllReloads[1]) {
                        phase_2(player_x, player_y);
                        timer = 0;
                    } else {
                        ++timer;
                    }
                }
            }
            for (int i = 0; i <= bullet_counter - 1; ++i) {
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
    }

    void phase_1(double player_x, double player_y) {
        for (int j = 0; j < 8; j++) {
            Bullet bullet = new Bullet(x + 25, y + 25, player_x + 25, player_y + 25, j + 1);
            bullet.damage = 10 - j;
            bullets.add(bullet);
            ++bullet_counter;
        }
    }

    void phase_2(double player_x, double player_y) {
        Bullet bullet = new Bullet(x + h / 2, y + h / 2, player_x + 25, player_y + 25, 10, 70);
        bullet.damage = 100;
        bullets.add(bullet);
        ++bullet_counter;
        double a = Math.atan2(player_x - x - h / 2, player_y - y - h / 2);
        for (int j = 0; j < 19; j++) {
            double a1 = a + (j - 10);
            bullet = new Bullet(x + h / 2, y + h / 2, x + 25 + 100 * Math.cos(a1), y + 25 + 100 * Math.sin(a1), 1);
            bullets.add(bullet);
            ++bullet_counter;
        }
    }

    void phase_3(double player_x, double player_y, Graphics g) {
        if (l == 0) {
            for (int i = 0; i < sunstrike; ++i) {
                int x = r.nextInt(100, 1000);
                int y = r.nextInt(100, 700);
                pair pair = new pair(x, y);
                sunstrikes.add(pair);
            }
            l = 1;
        }
        if (timer_phase_3 > time_sun_strike[0] && timer_phase_3 < time_sun_strike[1]) {
            ++timer_phase_3;
            g.setColor(sunstike_2);
            for (int i = 0; i < sunstrike; ++i) {
                double x = sunstrikes.get(i).getX();
                double y = sunstrikes.get(i).getY();
                if (chek_two_rect(player_x, player_y, 50, 50, x, y, 50, 50) || chek_two_rect(x, y, 50, 50, player_x, player_y, 50, 50)) {
                    if (count_sunstrike <= count_sunstrikeMax) {
                        damage_sunstike = true;
                        count_sunstrike++;
                    }
                }
                g.fillRect((int) x, (int) y, 50, 50);
            }
        } else if (timer_phase_3 > time_sun_strike[1]) {
            l = 0;
            timer_phase_3 = 0;
            sunstrikes.clear();
            count_sunstrike = 0;
        } else {
            ++timer_phase_3;
            g.setColor(sunstike_1);
            for (int i = 0; i < sunstrike; ++i) {
                double x = sunstrikes.get(i).getX();
                double y = sunstrikes.get(i).getY();
                g.fillRect((int) x, (int) y, 50, 50);
            }
        }
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

    boolean player_hit() {
        if (damage_sunstike) {
            damage_sunstike = false;
            return true;
        }
        return false;
    }

    double chek_player(double player_x, double player_y) {
        double ans = 0;
        for (int i = 0; i < bullet_counter; ++i) {
            if (i > bullets.size()) {
                continue;
            }
            if ((bullets.get(i).x >= player_x && bullets.get(i).y > player_y) && bullets.get(i).x <= player_x + 50 && bullets.get(i).y <= player_y + 50) {
                double damage = bullets.get(i).damage;
                remove(i);
                ans += damage;
            }
        }
        return ans;
    }

    void kill() {
        lifes = 0;
        death = true;
    }

    void shot(int player_x, int player_y) {
        Bullet bullet = new Bullet(x + 25, y + 25, player_x + 25, player_y + 25, 3);
        bullets.add(bullet);
        ++bullet_counter;
    }

    void remove(int i) {
        bullets.remove(i);
        bullet_counter--;
    }

    void is_death() {
        if (lifes <= 0) {
            death = true;
        } else if (lifes < 750 && lifes >= 500 && phase == 1) {
            phase = 2;
            bullet_counter = 0;
            bullets.clear();
        } else if (lifes < 500 && lifes >= 250 && phase == 2) {
            phase = 3;
            bullet_counter = 0;
            bullets.clear();
        } else if (lifes < 250 && phase == 3) {
            phase = 4;
            bullet_counter = 0;
            bullets.clear();
        } else if (lifes > 750 && lifes <= 1000) {
            phase = 1;
        }
    }

    void respawn() {
        lifes = lifeMax;
        death = false;
    }

    void hit(double damage) {
        lifes -= (int) damage;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }
}
