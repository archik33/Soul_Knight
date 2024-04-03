import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MyFrame extends JFrame {


    BufferedImage picture_escape;
    BufferedImage picture_healing;
    BufferedImage picture_chest;
    Random r = new Random();
    boolean is_start = false;
    boolean is_wave = false;
    boolean choosenPortal = false;
    Button healing;
    Button DamageUP;
    Button respawn;
    player player;
    Button to_main;
    Button to_respawn;
    Button afterBoss;
    Button afterBoss2;
    boolean boss = false;
    Button easy;
    Button normal;
    Button hard;
    Durman durman = new Durman(600, 400);
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Bullet> enemy_bullets = new ArrayList<>();
    ArrayList<Bullet> boss_bullets = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    boolean firstSecond = true;
    double damage_buster = 1.1;
    double speed_buster = 1.1;
    int enemy_counter = 0;
    int start_enemy_counter;
    Room[][] rooms;
    double sword_damage = 50;
    double player_centerX;
    double player_centerY;
    double x_sword;
    double y_sword;
    double r_sword;
    double sword_size = 50;
    double vx_sword = 1;
    int room_counter;
    int room_counter_start;
    int portalChosen = 1;
    int x_room = 0;
    int y_room = 0;
    static int score = 1;
    static int allScore = 0;
    int wave = 1;
    int max_wave = 4;
    double enemy_lives = 0;
    int[] enemy_waves = {0, 1, 3, 3, 3, 4, 4, 5, 7, 7, 7, 7};
    int[] enemy_to_kill = {0, 1, 4, 7, 10, 14, 18, 23, 32, 39, 46, 53};
    boolean is_Bonus = false;
    ArrayList<Block> blocks;
    int block_counter;
    ArrayList<Block> Room_fight = new ArrayList<>();
    ArrayList<Block> Room_top = new ArrayList<>();
    ArrayList<Block> Room_left = new ArrayList<>();
    ArrayList<Block> Room_right = new ArrayList<>();
    ArrayList<Block> Room_bottom = new ArrayList<>();
    ArrayList<Block> Room_top1 = new ArrayList<>();
    ArrayList<Block> Room_left1 = new ArrayList<>();
    ArrayList<Block> Room_right1 = new ArrayList<>();
    ArrayList<Block> Room_bottom1 = new ArrayList<>();

    public MyFrame(player player, ArrayList<Enemy> enemies, int enemy_counte, Room[][] rooms, int roomcount, ArrayList<Block> blocks, int block_counter) throws IOException {
        setSize(1280, 850);
        picture_escape = ImageIO.read(new File("data\\Portal.png"));
        this.afterBoss = new Button(550, 200, 157, 150, picture_escape, () -> {
            if (portalChosen == 1) {
                restart_afterBoss();
            }
        });
        this.afterBoss2 = new Button(550, 600, 157, 150, picture_escape, () -> {
            if (portalChosen == 2) {
                restart_afterBoss();
            }
        });
        this.to_main = new Button(400, 100, 50, 150, "Back", () -> {
            if (player.death) {
                is_start = false;
                player.respawn();
                player.setX(100);
                player.setY(100);
                score = 0;
                wave = 0;
            }
        });
        picture_healing = ImageIO.read(new File("data\\Healing.png"));
        picture_chest = ImageIO.read(new File("data\\Chest.jpg"));
        this.DamageUP = new Button(200, 500, 100, 100, picture_chest, this::DamageUp);
        this.healing = new Button(1000, 500, 100, 100, picture_healing, this::Healing);
        this.easy = new Button(400, 300, 50, 150, "Easy", () -> {
            if (!is_start) {
                is_start = true;
                Easy();
            }
        });
        this.normal = new Button(600, 300, 50, 150, "Normal", () -> {
            if (!is_start) {
                is_start = true;
                Normal();
            }
        });
        this.hard = new Button(800, 300, 50, 150, "Hard", () -> {
            if (!is_start) {
                is_start = true;
                Hard();
            }
        });
        this.to_respawn = new Button(400, 200, 50, 150, "Try again", () -> {
            if (player.death) {
                player.respawn();
                player.setX(100);
                player.setY(100);
                score = 0;
                for (int i = 0; i < enemy_counter; ++i) {
                    enemies.get(i).respawn();
                }
                wave = 0;
                enemy_counter = start_enemy_counter;
            }
        });
        this.rooms = rooms;
        rooms[0][0].setIs_fight(true);
        rooms[0][0].setPlayer_here(true);
        this.room_counter = roomcount;
        this.room_counter_start = roomcount;
        this.enemies = enemies;
        this.enemy_counter = enemy_waves[wave];
        this.start_enemy_counter = enemy_counte;
        this.blocks = blocks;
        this.block_counter = block_counter;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addMouseListener(to_main);
        addMouseListener(to_respawn);
        addMouseMotionListener(to_main);
        addMouseMotionListener(to_respawn);
        addMouseMotionListener(healing);
        addMouseMotionListener(DamageUP);
        addMouseMotionListener(easy);
        addMouseListener(DamageUP);
        addMouseListener(healing);
        addMouseListener(easy);
        addMouseMotionListener(normal);
        addMouseListener(normal);
        addMouseMotionListener(hard);
        addMouseListener(hard);
        addMouseListener(player);
        addMouseMotionListener(player);
        addMouseListener(respawn);
        addMouseMotionListener(respawn);
        addMouseListener(afterBoss);
        addMouseMotionListener(afterBoss);
        addMouseListener(afterBoss2);
        addMouseMotionListener(afterBoss2);
        this.player = player;
        setVisible(true);
        for (int i = 0; i < 26; ++i) {
            Block block_top = new Block(50 * i, 20, 50, 50, -2000, 1);
            Block block_bottom = new Block(50 * i, 800, 50, 50, -2000, 1);
            Room_bottom.add(block_bottom);
            Room_top.add(block_top);
            Room_fight.add(block_bottom);
            Room_fight.add(block_top);
            if (i <= 10 || i >= 16) {
                Room_top1.add(block_top);
                Room_bottom1.add(block_bottom);
            }
        }
        for (int i = 0; i < 20; ++i) {
            Block block_left = new Block(0, 20 + 50 * i, 50, 50, -2000, 1);
            Block block_right = new Block(1230, 20 + 50 * i, 50, 50, -2000, 1);
            Room_left.add(block_left);
            Room_right.add(block_right);
            Room_fight.add(block_left);
            Room_fight.add(block_right);
            if (i <= 7 || i >= 11) {
                Room_left1.add(block_left);
                Room_right1.add(block_right);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }
        g = bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("Default", Font.ROMAN_BASELINE, 20));
        if (player.pause && !player.death) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Default", Font.ROMAN_BASELINE, 60));
            String s = "Pause, press 'esc' to continue";
            g.drawString(s, 300, 480);
            g.setFont(new Font("Default", Font.ROMAN_BASELINE, 20));
        }
        if (player.death) {
            to_respawn.paint(g);
            to_main.paint(g);
            String s = "You Lose! Your score is " + allScore;
            g.drawString(s, 500, 400);
        }
        if (is_start) {
            if (enemy_waves[wave] == 0) {
                is_wave = false;
            } else {
                is_wave = true;
            }
            if (is_wave || boss) {
                if_wave(g);
            } else {
                if (!rooms[x_room][y_room].right) {
                    Room_right = check_bullet_block(g, Room_right);
                    check_block_player(Room_right);
                } else {
                    Room_right1 = check_bullet_block(g, Room_right1);
                    check_block_player(Room_right1);
                }
                if (!rooms[x_room][y_room].left) {
                    Room_left = check_bullet_block(g, Room_left);
                    check_block_player(Room_left);
                } else {
                    Room_left1 = check_bullet_block(g, Room_left1);
                    check_block_player(Room_left1);
                }
                if (!rooms[x_room][y_room].top) {
                    Room_top = check_bullet_block(g, Room_top);
                    check_block_player(Room_top);
                } else {
                    Room_top1 = check_bullet_block(g, Room_top1);
                    check_block_player(Room_top1);
                }
                if (!rooms[x_room][y_room].bottom) {
                    Room_bottom = check_bullet_block(g, Room_bottom);
                    check_block_player(Room_bottom);
                } else {
                    Room_bottom1 = check_bullet_block(g, Room_bottom1);
                    check_block_player(Room_bottom1);
                }
                if_not_wave(g);
            }
            if ((wave == max_wave)) {
                if (player.y >= 360 && player.y <= 600 && player.x < 100 && x_room != 0 && !rooms[x_room - 1][y_room].is_empty) {
                    player.setX(1200);
                    player.setY(480);
                    rooms[x_room][y_room].setPlayer_here(false);
                    x_room--;
                    rooms[x_room][y_room].setPlayer_here(true);
                    if (!rooms[x_room][y_room].is_fight) {
                        wave = 0;
                        score = 0;
                    }
                    rooms[x_room][y_room].setIs_fight(true);
                    boss = false;
                }
                if (player.x >= 540 && player.x <= 740 && player.y < 70 && y_room != 0 && !rooms[x_room][y_room - 1].is_empty) {
                    player.setX(640);
                    player.setY(400);
                    rooms[x_room][y_room].setPlayer_here(false);
                    y_room--;
                    rooms[x_room][y_room].setPlayer_here(true);
                    if (!rooms[x_room][y_room].is_fight) {
                        wave = 0;
                        score = 0;
                    }
                    rooms[x_room][y_room].setIs_fight(true);
                    boss = false;
                }
                if (player.y >= 360 && player.y <= 600 && player.x > 1200 && x_room != room_counter - 1 && !rooms[x_room + 1][y_room].is_empty) {
                    player.setX(100);
                    player.setY(480);
                    rooms[x_room][y_room].setPlayer_here(false);
                    x_room++;
                    rooms[x_room][y_room].setPlayer_here(true);
                    if (!rooms[x_room][y_room].is_fight) {
                        wave = 0;
                        score = 0;
                    }
                    rooms[x_room][y_room].setIs_fight(true);
                    boss = false;
                }
                if (player.x >= 540 && player.x <= 740 && player.y >= 800 && y_room != room_counter - 1 && !rooms[x_room][y_room + 1].is_empty) {
                    player.setX(600);
                    player.setY(400);
                    rooms[x_room][y_room].setPlayer_here(false);
                    y_room++;
                    rooms[x_room][y_room].setPlayer_here(true);
                    if (!rooms[x_room][y_room].is_fight) {
                        wave = 0;
                        score = 0;
                    }
                    rooms[x_room][y_room].setIs_fight(true);
                    boss = false;
                }
                if (x_room == room_counter - 1 && y_room == room_counter - 1) {
                    wave = 5;
                    boss = true;
                }
            }
            if (score == enemy_to_kill[wave]) {
                wave++;
                enemy_counter = enemy_waves[wave];
                for (int i = 0; i < start_enemy_counter; ++i) {
                    if (enemy_waves[wave] == 3 && (i == 0)) {
                        enemies.get(i).setX(100);
                        enemies.get(i).setY(100);
                    }
                    if (enemy_waves[wave] == 3 && (i == 1)) {
                        enemies.get(i).setX(900);
                        enemies.get(i).setY(700);
                    }
                    if (enemy_waves[wave] == 3 && (i == 2)) {
                        enemies.get(i).setX(1000);
                        enemies.get(i).setY(100);
                    }
                    enemies.get(i).respawn();
                }

            }
            if (!player.death) {
                player.paint(g);
            }
            int bullets_counter = bullets.size();
            if (boss) {
                durman.paint(g, player.x, player.y, player.pause);
                if (durman.player_hit()) {
                    player.hit(50);
                }
                for (int i = 0; i <= bullets_counter - 1; ++i) {
                    if (durman.death) {
                        continue;
                    }
                    if (i >= bullets.size()) {
                        continue;
                    }
                    if ((bullets.get(i).x >= durman.x && bullets.get(i).y >= durman.y) && bullets.get(i).x <= durman.x + durman.h && bullets.get(i).y <= durman.y + durman.h) {
                        double damage = bullets.get(i).damage;
                        durman.hit(damage);
                        player.remove(i);
                        durman.is_death();
                    }
                }
                player.hit(durman.chek_player(player.x, player.y));
                player.is_death();
                boss_bullets = durman.getBullets();
            } else {
                for (int i = 0; i < enemy_counter; ++i) {
                    enemies.get(i).paint(g, this.getHeight(), getWidth(), (int) player.x, (int) player.y, player.pause);
                    if (!enemies.get(i).death) {
                        enemy_bullets = enemies.get(i).bullets;
                        for (int j = 0; j < enemy_bullets.size(); ++j) {
                            if (enemies.get(i).death) {
                                continue;
                            }
                            if (j >= enemy_bullets.size()) {
                                continue;
                            }
                            if ((enemy_bullets.get(j).x >= player.x && enemy_bullets.get(j).y >= player.y) && enemy_bullets.get(j).x <= player.x + 50 && enemy_bullets.get(j).y <= player.y + 50) {
                                double damage = enemy_bullets.get(j).damage;
                                player.hit(damage);
                                player.is_death();
                                enemies.get(i).remove(j);

                            }
                        }
                    }
                }
                bullets = player.getBullets();
                for (int i = 0; i <= bullets_counter - 1; ++i) {
                    for (int j = 0; j < enemy_counter; ++j) {
                        if (enemies.get(j).death) {
                            continue;
                        }
                        double enemy_x = enemies.get(j).x;
                        double enemy_y = enemies.get(j).y;
                        if (i >= bullets.size()) {
                            continue;
                        }
                        if ((bullets.get(i).x >= enemy_x && bullets.get(i).y >= enemy_y) && bullets.get(i).x <= enemy_x + 50 && bullets.get(i).y <= enemy_y + 50) {
                            double damage = bullets.get(i).damage;
                            enemies.get(j).hit(damage);
                            enemies.get(j).is_death();
                            player.remove(i);
                        }
                    }
                }
            }
            if (player.hand_punch) {
                if (firstSecond) {
                    x_sword = 0;
                    y_sword = -sword_size - player.h / 2;
                    r_sword = player.h / 2 + sword_size;
                    firstSecond = false;
                }
                player_centerX = player.x + player.h / 2;
                player_centerY = player.y + player.h / 2;
                Color q = new Color(231, 7, 7, 228);
                g.setColor(q);
                g.drawLine((int) player_centerX, (int) player_centerY, (int) (player_centerX + x_sword), (int) (player_centerY + y_sword));
                x_sword += vx_sword;
                y_sword = -Math.sqrt(r_sword * r_sword - x_sword * x_sword);
                for (int j = 0; j < enemy_counter; ++j) {
                    if (enemies.get(j).death) {
                        continue;
                    }
                    double enemy_x = enemies.get(j).x;
                    double enemy_y = enemies.get(j).y;
                    if ((check_two_rect(player_centerX, player_centerY, 1, 1, enemy_x, enemy_y, 50, 50) || check_two_rect(enemy_x, enemy_y, 50, 50, player_centerX, player_centerY, 1, 1)) && (!enemies.get(j).getHitSword)) {
                        enemies.get(j).hit(sword_damage);
                        enemies.get(j).is_death();
                        enemies.get(j).getHitSword = true;
                    }
                }
                if (x_sword >= player.h / 2 + sword_size) {
                    player.hand_punch = false;
                    firstSecond = true;
                    for(int i = 0; i < enemy_counter; ++i){
                        enemies.get(i).getHitSword = false;
                    }
                }
            }
            if (allScore == 6 && player.x < 10 && player.y < 10) {
                for (int i = 0; i < enemy_counter; ++i) {
                    enemies.get(i).kill();
                }
                String s = "THIS IS MY KINGDOM CUM";
                g.drawString(s, 400, 400);
            }
            if (!player.death && !player.pause) {
                player.update(getHeight(), getWidth());
            }
            g.setColor(Color.BLACK);
            String s1 = "Your Score " + allScore;
            g.drawString(s1, 1100, 60);
            if (player.respawn) {
                for (int i = 0; i < start_enemy_counter; ++i) {
                    enemies.get(i).kill();
                }
                wave++;
                score = enemy_to_kill[max_wave];
                player.setRespawn(false);
            }
            for (int i = 0; i < enemy_counter; ++i) {
                if (enemies.get(i).lifes < 0) {
                    continue;
                }
                enemy_lives += enemies.get(i).lifes;
            }
            enemy_lives = enemy_lives / ((double) enemies.get(0).lifeMax * enemy_counter);
            enemy_lives *= 400;
            for (int i = 0; i < room_counter; ++i) {
                for (int j = 0; j < room_counter; ++j) {
                    rooms[i][j].paint(g);
                }
            }
            if (!boss) {
                Color q2 = new Color(147, 43, 43, 181);
                g.setColor(q2);
                g.fillRect(400, 60, 400, 20);
                Color q1 = new Color(224, 14, 14, 250);
                g.setColor(q1);
                g.fillRect(400, 60, (int) enemy_lives, 20);
            }
            enemy_lives = 0;
            if (durman.death) {
                if (!is_Bonus) {
                    healing.paint(g);
                    DamageUP.paint(g);
                }
                if ((!choosenPortal) && (check_two_rect(player.x, player.y, player.h, player.h, 550, 200, 150, 157) || check_two_rect(550, 200, 150, 157, player.x, player.y, player.h, player.h))) {
                    choosenPortal = true;
                    portalChosen = 2;
                } else {
                    choosenPortal = true;
                }
                if (portalChosen == 1) {
                    afterBoss.paint(g);
                } else {
                    afterBoss2.paint(g);
                }

            }
        } else if (!is_start) {
            easy.paint(g);
            normal.paint(g);
            hard.paint(g);
            player.paint(g);
            player.update(getHeight(), getWidth());
        }
        if (player.pause && !player.death) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Default", Font.ROMAN_BASELINE, 60));
            String s = "Pause, press 'esc' to continue";
            g.drawString(s, 300, 480);
            g.setFont(new Font("Default", Font.ROMAN_BASELINE, 20));
        }
        g.dispose();
        bufferStrategy.show();
    }

    ArrayList<Block> check_bullet_block(Graphics g, ArrayList<Block> blocks) {
        int block_counter = blocks.size();
        for (int i = 0; i < block_counter; ++i) {
            if (blocks.get(i).death) {
                continue;
            }
            blocks.get(i).paint(g);
            double block_x = blocks.get(i).x;
            double block_y = blocks.get(i).y;
            if (!bullets.isEmpty()) {
                int bullets_counter = bullets.size();
                for (int j = 0; j < bullets_counter; ++j) {
                    if (j >= bullets.size()) {
                        continue;
                    }
                    if (((bullets.get(j).x >= block_x && bullets.get(j).y >= block_y) && bullets.get(j).x <= block_x + 50 && bullets.get(j).y <= block_y + 50)) {
                        double damage = bullets.get(j).damage;
                        blocks.get(i).hit(damage);
                        player.remove(j);
                    }
                }
            }
            for (int k = 0; k < enemy_counter; ++k) {
                if (!enemies.get(k).death) {
                    enemy_bullets = enemies.get(k).bullets;
                    int bullets_counter = enemy_bullets.size();
                    for (int j = 0; j < bullets_counter; ++j) {
                        if (enemies.get(k).death) {
                            continue;
                        }
                        if (j >= enemy_bullets.size()) {
                            continue;
                        }
                        if ((enemy_bullets.get(j).x >= block_x && enemy_bullets.get(j).y >= block_y) && enemy_bullets.get(j).x <= block_x + 50 && enemy_bullets.get(j).y <= block_y + 50) {
                            double damage = enemy_bullets.get(j).damage;
                            blocks.get(i).hit(damage);
                            enemies.get(k).remove(j);

                        }
                    }
                }
            }
            if (boss) {
                int bullets_counter = boss_bullets.size();
                for (int j = 0; j < bullets_counter; ++j) {
                    if (j >= boss_bullets.size()) {
                        continue;
                    }
                    if ((boss_bullets.get(j).x >= block_x && boss_bullets.get(j).y >= block_y) && boss_bullets.get(j).x <= block_x + 50 && boss_bullets.get(j).y <= block_y + 50) {
                        double damage = boss_bullets.get(j).damage;
                        blocks.get(i).hit(damage);
                        durman.remove(j);

                    }
                }
            }
        }
        return blocks;
    }

    void check_block_player(ArrayList<Block> blocks) {
        int counter = blocks.size();
        for (int i = 0; i < counter; ++i) {
            if (blocks.get(i).death) {
                continue;
            }
            if (player.x <= blocks.get(i).x + blocks.get(i).width && player.x + player.h >= blocks.get(i).x + blocks.get(i).width && player.y < blocks.get(i).y + blocks.get(i).height && player.y + player.h > blocks.get(i).y && player.vx < 0) {
                player.vx = Math.max(0, player.vx);
            } else if (player.x + player.h >= blocks.get(i).x && player.x <= blocks.get(i).x && player.y < blocks.get(i).y + blocks.get(i).height && player.y + player.h > blocks.get(i).y && player.vx > 0) {
                player.vx = -Math.max(0, -player.vx);
            }
            if (player.y <= blocks.get(i).y + blocks.get(i).height && player.y >= blocks.get(i).y && player.x < blocks.get(i).x + blocks.get(i).width && player.x + player.h > blocks.get(i).x) {
                player.vy = Math.max(0, player.vy);
            } else if (player.y + player.h >= blocks.get(i).y && player.y + player.h <= blocks.get(i).y + blocks.get(i).height && player.x < blocks.get(i).x + blocks.get(i).width && player.x + player.h > blocks.get(i).x) {
                player.vy = -Math.max(0, -player.vy);
            }
        }
    }

    void check_block_enemy(ArrayList<Block> blocks, ArrayList<Enemy> enemy) {
        int counter = blocks.size();
        for (int j = 0; j < enemy.size(); ++j) {
            if (enemy.get(j).death) {
                continue;
            }
            for (int i = 0; i < counter; ++i) {
                if (blocks.get(i).death) {
                    continue;
                }
                if (enemy.get(j).x <= blocks.get(i).x + blocks.get(i).width && enemy.get(j).x + enemy.get(j).W >= blocks.get(i).x + blocks.get(i).width && enemy.get(j).y < blocks.get(i).y + blocks.get(i).height && enemy.get(j).y + enemy.get(j).h > blocks.get(i).y && enemy.get(j).vx < 0) {
                    enemy.get(j).update_block(1);
                } else if (enemy.get(j).x + enemy.get(j).h >= blocks.get(i).x && enemy.get(j).x <= blocks.get(i).x && enemy.get(j).y < blocks.get(i).y + blocks.get(i).height && enemy.get(j).y + enemy.get(j).h > blocks.get(i).y && enemy.get(j).vx > 0) {
                    enemy.get(j).update_block(2);
                }
                if (enemy.get(j).y <= blocks.get(i).y + blocks.get(i).height && enemy.get(j).y >= blocks.get(i).y && enemy.get(j).x < blocks.get(i).x + blocks.get(i).width && enemy.get(j).x + enemy.get(j).W > blocks.get(i).x) {
                    enemy.get(j).update_block(3);
                } else if (enemy.get(j).y + enemy.get(j).h >= blocks.get(i).y && enemy.get(j).y + enemy.get(j).h <= blocks.get(i).y + blocks.get(i).height && enemy.get(j).x < blocks.get(i).x + blocks.get(i).width && enemy.get(j).x + enemy.get(j).W > blocks.get(i).x) {
                    enemy.get(j).update_block(4);
                }
            }
        }
    }

    void if_not_wave(Graphics g) {
        blocks = check_bullet_block(g, blocks);
        check_block_player(blocks);
        check_block_enemy(blocks, enemies);
    }

    void if_wave(Graphics g) {
        Room_fight = check_bullet_block(g, Room_fight);
        check_block_player(Room_fight);
        blocks = check_bullet_block(g, blocks);
        check_block_player(Room_fight);
        check_block_player(blocks);
        check_block_enemy(blocks, enemies);
    }

    void setRoom_counter(int room_counter) {
        this.room_counter = room_counter;
    }

    Room[][] NewRooms(int room_counter) {
        int i1 = 0;
        int j1 = 0;
        Room[][] rooms = new Room[room_counter][room_counter];
        for (int i = 0; i < room_counter; ++i) {
            for (int j = 0; j < room_counter; ++j) {
                Room r1 = new Room(i, j);
                rooms[i][j] = r1;
                rooms[i][j].setIs_empty(true);
            }
        }
        rooms[room_counter - 1][room_counter - 1].setIs_empty(false);
        while ((i1 <= room_counter - 1) && (j1 <= room_counter - 1)) {
            int q = r.nextInt(0, 2);
            rooms[i1][j1].setIs_empty(false);
            if (q == 1) {
                i1++;
            } else {
                j1++;
            }
        }
        for (int i = 0; i < room_counter - 1; ++i) {
            if (!rooms[i][room_counter - 1].is_empty) {
                rooms[i + 1][room_counter - 1].setIs_empty(false);
            }
        }
        for (int i = 0; i < room_counter - 1; ++i) {
            if (!rooms[room_counter - 1][i].is_empty) {
                rooms[room_counter - 1][i + 1].setIs_empty(false);
            }
        }
        for (int i = 0; i < room_counter; ++i) {
            for (int j = 0; j < room_counter; ++j) {
                if (i > 0 && rooms[i - 1][j].is_empty) {
                    rooms[i][j].top = true;
                }
                if (i < room_counter - 1 && rooms[i + 1][j].is_empty) {
                    rooms[i][j].bottom = true;
                }
                if (j < room_counter - 1 && rooms[i][j + 1].is_empty) {
                    rooms[i][j].right = true;
                }
                if (j > 0 && rooms[i][j - 1].is_empty) {
                    rooms[i][j].left = true;
                }
            }
        }
        if (!rooms[0][1].is_empty) {
            rooms[0][1].top = true;
        }
        if (!rooms[1][0].is_empty) {
            rooms[1][0].left = true;
        }
        for (int i = 1; i < room_counter; ++i) {
            if (!rooms[0][i].is_empty) {
                rooms[0][i].top = true;
            }
        }
        for (int i = 1; i < room_counter; ++i) {
            if (!rooms[i][room_counter - 1].is_empty && !rooms[i - 1][room_counter - 1].is_empty) {
                rooms[i][room_counter - 1].right = true;
            }
        }
        for (int i = 1; i < room_counter; ++i) {
            if (!rooms[room_counter - 1][1].is_empty && !rooms[room_counter - 1][i - 1].is_empty) {
                rooms[room_counter - 1][1].bottom = true;
            }
        }
        for (int i = 1; i < room_counter; ++i) {
            if (!rooms[i][0].is_empty) {
                rooms[i][0].left = true;
            }
        }
        rooms[room_counter - 1][room_counter - 2].bottom = true;
        rooms[room_counter - 2][room_counter - 1].right = true;
        return rooms;
    }

    void restart_afterBoss() {
        if (durman.death && !player.pause) {
            choosenPortal = false;
            portalChosen = 1;
            is_Bonus = false;
            room_counter_start = Math.min(room_counter_start + 1, 5);
            setRoom_counter(room_counter_start);
            durman.respawn();
            durman.setPhase(1);
            rooms = NewRooms(room_counter_start);
            rooms[0][0].setIs_fight(true);
            rooms[0][0].setPlayer_here(true);
            x_room = 0;
            y_room = 0;
            score = 1;
            wave = 1;
            player.setX(200);
            player.setY(200);
            boss = false;
            player.NewWeapon();
            allScore += 10;
        }
    }

    void Easy() {
        player.setLifes(1000);
        enemy_waves[3] = 0;
        max_wave = 3;
        for (int i = 0; i < start_enemy_counter; ++i) {
            enemies.get(i).setLifes(75);
        }
    }

    void Normal() {
        player.setLifes(500);
        enemy_waves[4] = 0;
        max_wave = 4;
        for (int i = 0; i < start_enemy_counter; ++i) {
            enemies.get(i).setLifes(100);
        }
    }

    void Hard() {
        enemy_waves[6] = 0;
        max_wave = 6;
        player.setLifes(350);
        for (int i = 0; i < start_enemy_counter; ++i) {
            enemies.get(i).setLifes(120);
        }
    }

    void Healing() {
        if (durman.death && !is_Bonus && !player.pause) {
            player.lifes = player.lifesMax;
            is_Bonus = true;
        }
    }

    void DamageUp() {
        if (durman.death && !is_Bonus && !player.pause) {
            player.damage *= damage_buster;
//            player.speed *= speed_buster;
            is_Bonus = true;
        }
    }

    boolean check_two_rect(double x, double y, double w, double h, double x1, double y1, double w1, double h1) {
        if (check_point_rect(x, y, x1, y1, w1, h1) || check_point_rect(x, y + h, x1, y1, w1, h1) || check_point_rect(x + w, y, x1, y1, w1, h1) || check_point_rect(x + w, y + h, x1, y1, w1, h1)) {
            return true;
        } else {
            return false;
        }

    }

    boolean check_point_rect(double x, double y, double x1, double y1, double w1, double h1) {
        if (x > x1 && x < x1 + w1 && y > y1 && y < y1 + h1) {
            return true;
        } else {
            return false;
        }
    }

    void punch() {

    }
}