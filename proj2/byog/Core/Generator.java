package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Generator {
    public static final int WIDTH = Game.WIDTH;
    public static final int HEIGHT = Game.HEIGHT;
    public TETile[][] world;
    private Random rand;
    public Player player;
    private static final double RATE = 0.1;
    private static final int LIMIT = 8;
    public ArrayList<Character> keys;
    private boolean readyToSave = false;
    private long seed;

    public Generator(long seed) {
        this.seed = seed;
        rand = new Random(seed);
        world = new TETile[WIDTH][HEIGHT];
        keys = new ArrayList<>();
        generateWorld();
        generatePlayer();
    }

    public void generateWorld() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        Leaf wholeLeaf = new Leaf(3, 3, WIDTH - 5, HEIGHT - 5);
        Leaf.rand = rand;
        generateRooms(wholeLeaf);
        this.world = world;
    }

    private void addRoom(Rectangular r) {

        for (int i = -1; i < r.width + 1; i++) {
            for (int j = -1; j < r.height + 1; j++) {
                if (i > -1 && j > -1 && i < r.width && j < r.height) {
                    world[r.leftTopX + i][r.leftTopY + j] = Tileset.FLOOR;
                } else {
                    if (world[r.leftTopX + i][r.leftTopY + j] != Tileset.FLOOR) {
                        world[r.leftTopX + i][r.leftTopY + j] = Tileset.WALL;
                    }
                }
            }
        }
    }

    private void generateRooms(Leaf l) {
        List<Leaf> leaves = l.createLeaves();
        l.createRooms();
        for (Leaf le : leaves) {
            if (le.room != null) {
                addRoom(le.room);
            }
        }
        for (Leaf le : leaves) {
            if (le.halls != null) {
                for (Rectangular r : le.halls) {
                    addRoom(r);
                }
            }
        }
    }

    public static List<Rectangular> createHall(Rectangular r1, Rectangular r2) {
        int p1_x = Leaf.rand.nextInt(r1.width) + r1.leftTopX;
        int p1_y = Leaf.rand.nextInt(r1.height) + r1.leftTopY;
        int p2_x = Leaf.rand.nextInt(r2.width) + r2.leftTopX;
        int p2_y = Leaf.rand.nextInt(r2.height) + r2.leftTopY;

        int deltaW = p1_x - p2_x;
        int deltaH = p1_y - p2_y;

        List<Rectangular> hallways = new ArrayList<>();
        if (deltaW < 0) {
            if (deltaH < 0) {
                hallways.add(new Rectangular(p1_x, p1_y, Math.abs(deltaW), 1));
                hallways.add(new Rectangular(p2_x, p1_y, 1, Math.abs(deltaH)));
            } else if (deltaH > 0) {
                hallways.add(new Rectangular(p1_x, p1_y, Math.abs(deltaW), 1));
                hallways.add(new Rectangular(p2_x, p2_y, 1, Math.abs(deltaH)));
            } else {
                hallways.add(new Rectangular(p1_x, p1_y, Math.abs(deltaW), 1));
            }
        } else if (deltaW > 0) {
            if (deltaH < 0) {
                hallways.add(new Rectangular(p2_x, p2_y, Math.abs(deltaW), 1));
                hallways.add(new Rectangular(p1_x, p1_y, 1, Math.abs(deltaH)));
            } else if (deltaH > 0) {
                hallways.add(new Rectangular(p2_x, p2_y, Math.abs(deltaW), 1));
                hallways.add(new Rectangular(p1_x, p2_y, 1, Math.abs(deltaH)));
            } else {
                hallways.add(new Rectangular(p2_x, p2_y, Math.abs(deltaW), 1));
            }
        } else {
            if (deltaH < 0) {
                hallways.add(new Rectangular(p1_x, p1_y, 1, Math.abs(deltaH)));
            } else {
                hallways.add(new Rectangular(p2_x, p2_y, 1, Math.abs(deltaH)));
            }
        }
        return hallways;
    }

    public boolean getKeyBoardInput() {
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            switch (key) {
                case 'w':
                case 'W':
                case 'a':
                case 'A':
                case 's':
                case 'S':
                case 'd':
                case 'D': {
                    player.makeMove(key);
                    keys.add(key);
                    break;
                }
                case ':': {
                    readyToSave = true;
                    break;
                }
                case 'Q': {
                    if (readyToSave) {
                        saveWorld();
                        return true;
                    }
                    break;
                }
                default: break;
            }
        }
        return false;
    }

    public static void showDescription(String s) {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(5, WIDTH, s);
        StdDraw.text(0, WIDTH - 2, String.join("", Collections.nCopies(40*16, "*")));
        StdDraw.show();
    }

    public void generatePlayer() {
        player = new Player(world);
        while (true) {
            int x = rand.nextInt(WIDTH);
            int y = rand.nextInt(HEIGHT);
            if (world[x][y] == Tileset.FLOOR) {
                player.setPosition(x, y);
                break;
            }
        }
    }

    public void saveWorld() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("prevWorld.txt"));
            out.write(String.valueOf(seed) + System.lineSeparator());
            for (Character ch : keys) {
                out.write(ch);
            }
            out.close();
        } catch (IOException e) {
            System.out.println("IOException" + e);
        }
//        StdDraw.clear();
//        StdDraw.text(WIDTH / 2, HEIGHT / 2, "saved successfully");
//        StdDraw.show();
//        StdDraw.pause(1000);
    }

    public static Generator startPage() {
        StdDraw.setCanvasSize(WIDTH * 16, (HEIGHT + 2) * 16);
        Font font = new Font("Monaco", Font.BOLD, 14);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, (HEIGHT + 2));
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "THE GAME");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 1, "New Game(N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "Load Game(L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 3, "Quit(Q)");

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char ch = StdDraw.nextKeyTyped();
                switch (ch) {
                    case 'Q': System.exit(0);
                    case 'L': return loadWorld();
                    case 'N': {
                        StdDraw.clear(Color.black);
                        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Type a new seed, end with 'S'");
                        StdDraw.show();
                        StringBuilder seed = new StringBuilder();
                        boolean notEnd = true;
                        while(notEnd) {
                            if (StdDraw.hasNextKeyTyped()) {
                                char cc = StdDraw.nextKeyTyped();
                                switch (cc) {
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                    case '4':
                                    case '5':
                                    case '6':
                                    case '7':
                                    case '8':
                                    case '9': seed.append(cc);break;
                                    case 'S': return new Generator(Long.parseLong(seed.toString()));
                                    default: System.exit(0);break;
                                }
                            }
                        }
                        break;
                    }
                    default: break;
                }
            }
        }
    }

    public static Generator loadWorld(){
        Generator ge = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader("prevWorld.txt"));
            long seed = Long.parseLong(in.readLine());
            ge = new Generator(seed);
            ge.reloadPrevWorld(in.readLine().toCharArray());
        } catch (IOException e) {
            System.out.println("IOException" + e);
        }
        return ge;
    }

    public void reloadPrevWorld(char[] keys) {
        for (Character ch : keys) {
            player.makeMove(ch);
            this.keys.add(ch);
        }
    }
}
