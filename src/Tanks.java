import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Tanks extends JPanel {

    final int UP = 1;
    final int DOWN = 2;
    final int LEFT = 3;
    final int RIGHT = 4;
    int direction = UP;
    final int tanKSize = 62;
    int tankX = tanKSize * 4;
    int tankY = tanKSize * 4;





    int bulletX = -100;
    int bulletY = -100;
    final int bulletSize = 12;
    static final int BF_SIZE = 558;
    final int BF_TOP_X = BF_SIZE - tanKSize;
    final int BF_TOP_Y = BF_SIZE - tanKSize;


    final int objectSize = 62;

    String[][] obj = {
            {"G", "B", "W", "W", "G", "G", "G", "G", "B"},
            {"G", "B", "S", "S", "G", "G", "G", "G", "W"},
            {"G", "G", "B", "S", "B", "B", "B", "G", "W"},
            {"G", "S", "S", "S", "S", "S", "S", "B", "W"},
            {"G", "I", "B", "S", "S", "S", "S", "B", "I"},
            {"G", "B", "S", "B", "S", "S", "S", "G", "S"},
            {"G", "B", "S", "S", "G", "B", "B", "I", "W"},
            {"W", "G", "G", "I", "I", "I", "G", "B", "W"},
            {"W", "G", "G", "I", "H", "I", "G", "B", "S"},

    };

    //TODO: 1 = вверх 2 = вниз 3 = влево 4 = вправо
    void move(int direction) throws Exception {
        this.direction = direction;

        if (cantMove()){
            System.out.println("Can't move!");
            fire();
            return;
        }

        for (int i = 0; i < 62; i++) {

            if (direction == 1) {
                tankY--;
                repaint();
            } else if (direction == 2) {
                tankY++;
                repaint();
            } else if (direction == 3) {
                tankX--;
                repaint();
            } else if (direction == 4) {
                tankX++;
                repaint();
            }
            Thread.sleep(11);
            repaint();
        }
    }

    void moveRandom() throws Exception{

        Random random = new Random();
        int dir = random.nextInt(4) + 1;
        move(dir);
    }


    void runTheGame() throws Exception {
        while (true) {
            moveRandom();
        }
    }

    boolean destroyWall() {

        int y = bulletY / 64;
        int x = bulletX / 64;

        if (obj[y][x].equals("B")) {
            obj[y][x] = "S";
            return true;
        } else if (obj[y][x].equals("I")){
            obj[y][x] = "S";
            return true;
        }
        return false;
    }

    boolean cantMove() {
        return (direction == UP && tankY == 0) || (direction == DOWN && tankY == BF_TOP_Y)
                || (direction == LEFT && tankX == 0)|| (direction == RIGHT && tankX == BF_TOP_X)
                || (nextObject(direction).equals("B")) || (nextObject(direction).equals("I"));
    }


    String nextObject(int direction) {
        int y = tankY;
        int x = tankX;

        switch (direction) {
            case UP :
                y-=tanKSize;
                break;
            case DOWN :
                y+=tanKSize;
                break;
            case LEFT :
                x-=tanKSize;
                break;
            case RIGHT :
                x+=tanKSize;
        }
        return obj[y/tanKSize][x/tanKSize];
    }

    //стрельба
    void fire() throws Exception {
        System.out.println("FIRE!");
        bulletX = tankX + 25;
        bulletY = tankY + 25;
        while (bulletX > 0 && bulletX < BF_SIZE && bulletY > 0 && bulletY < BF_SIZE) {
            switch (direction) {

                case 1:
                    bulletY--;
                    break;
                case 2:
                    bulletY++;
                    break;
                case 3:
                    bulletX--;
                    break;
                case 4:
                    bulletX++;
                    break;
            }
            if (destroyWall()) {
                destroyBullet();
            }
            Thread.sleep(2);
            repaint();
        }
        destroyBullet();
    }

    //уничтожение пули
    void destroyBullet() {
        bulletY = -100;
        bulletX = -100;
        repaint();
    }

    public static void main(String[] args) throws Exception {
        //TODO: процесс создания окна
        JFrame frame = new JFrame("Dendy objects.Tanks");
        frame.setMinimumSize(new Dimension(BF_SIZE + 16, BF_SIZE + 38));
        frame.setLocation(0, 0);
        frame.setVisible(true);
        Tanks tanks = new Tanks();
        frame.getContentPane().add(tanks);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        KeyboardFocusManager keyboard = new DefaultFocusManager();
//        if (keyboard.dispatchKeyEvent())
         tanks.runTheGame();
        tanks.fire();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        for (int y = 0; y < obj.length; y++) {
            for (int x = 0; x < obj.length; x++) {
                switch (obj[y][x]) {
                    case "B":
                        g.setColor(new Color(133, 40, 0));
                        break;

                    case "W":
                        g.setColor(new Color(99, 201, 238));

                        break;

                    case "G":
                        g.setColor(new Color(52, 119, 32));

                        break;
                    case "S":
                        g.setColor(new Color(220, 185, 136));
                        break;
                    case "I":
                        g.setColor(new Color(141, 141, 141));
                        break;
                    case "H":
                        g.setColor(new Color(9, 10, 6));
                        break;

                }
                g.fillRect(x * objectSize, y * objectSize, tanKSize, tanKSize);
            }

        }


        //draw tank
        g.setColor(new Color(28, 103, 89));
        g.fillRect(tankX, tankY, objectSize, objectSize);
        g.setColor(new Color(8, 138, 108));

        if (direction == 1) {
            g.fillRect(tankX + 21, tankY, 20, 33);

        } else if (direction == 2) {
            g.fillRect(tankX + 21, tankY + 30, 20, 33);

        } else if (direction == 4) {
            g.fillRect(tankX + 31, tankY + 21, 33, 20);

        } else if (direction == 3) {
            g.fillRect(tankX, tankY + 21, 33, 20);

        }

        //draw bullet
        g.setColor(new Color(241, 7, 39));
        g.fillOval(bulletX, bulletY, bulletSize, bulletSize);

        //draw BF


    }
}
