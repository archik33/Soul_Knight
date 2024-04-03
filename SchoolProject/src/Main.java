import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        int room_counter = 2;
        Random r = new Random();
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
        for (int i = 1; i < room_counter; ++i) {
            if (!rooms[0][i].is_empty) {
                rooms[0][i].top = true;
            }
        }
        for (int i = 1; i < room_counter; ++i) {
            if (!rooms[i][0].is_empty) {
                rooms[i][0].left = true;
            }
        }
        if (!rooms[0][1].is_empty) {
            rooms[0][1].top = true;
        }
        if (!rooms[1][0].is_empty) {
            rooms[1][0].left = true;
        }
        rooms[room_counter - 1][room_counter - 2].bottom = true;
        rooms[room_counter - 2][room_counter - 1].right = true;
        int enemy_counter = 10;
        int weaponType = 0;
        for (int i = 0; i <= enemy_counter - 1; ++i) {
            int x = r.nextInt();
            int y = r.nextInt();
            x = Math.abs(x) % 600 + 150;
            y = Math.abs(y) % 600 + 100;
            if (i <= 1) {
                weaponType = 1;
            } else if (i <= 2) {
                weaponType = 2;
            } else if (i == 3) {
                weaponType = 3;
            }
            Enemy enemy = new Enemy(x, y, 0, 0, weaponType);
            enemies.add(enemy);
        }
        ArrayList<Block> blocks = new ArrayList<Block>();
        int blocks_counter = 3;
        for (int i = 0; i <= blocks_counter - 1; ++i) {
            int x = r.nextInt();
            int y = r.nextInt();
            x = Math.abs(x) % 600 + 150;
            y = Math.abs(y) % 600 + 100;
            Block block = new Block(x, y, 50, 50, 100, 1);
            blocks.add(block);
        }
        MyPanel panel = new MyPanel();
        player player = new player(100, 100, 0, 0, 960, 1280);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(player);
        MyFrame frame = new MyFrame(player, enemies, enemy_counter, rooms, room_counter, blocks, blocks_counter);
        frame.add(panel);
        while (true) {
            frame.repaint();
        }
    }
}