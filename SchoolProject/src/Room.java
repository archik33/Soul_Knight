import java.awt.*;

public class Room {
    boolean is_empty;
    boolean is_fight = false;
    int x, y;
    boolean player_here = false;
    boolean right = false;
    boolean top = false;
    boolean bottom = false;
    boolean left = false;

    public Room() {

    }

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setIs_empty(boolean is_empty) {
        this.is_empty = is_empty;
    }

    public void setIs_fight(boolean is_fight) {
        this.is_fight = is_fight;
    }

    void paint(Graphics g) {
        if (!is_empty) {
            if (player_here) {
                g.fillRect(1140 + x * 30, 80 + y * 30, 20, 20);
            } else {
                g.drawRect(1140 + x * 30, 80 + y * 30, 20, 20);
            }
        }
    }

    public void setPlayer_here(boolean player_here) {
        this.player_here = player_here;
    }
}

