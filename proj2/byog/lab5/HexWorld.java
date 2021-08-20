package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    private static final long SEED = 2820001;
    private static final Random RANDOM = new Random(SEED);
    /**
     *
     * @param world the world to draw on.
     * @param s the size of hex.
     * @param t the Tile to fill.
     */
    private static void addHexagon(TETile[][] world, int x, int y, int s, TETile t) {
        for (int i = 0; i < 2 * s; i++) {
            addRow(world, x, y - s + i + 1, s, i + 1, t);
        }
    }

    /**
     *
     * @param s the size of hex.
     * @param num the number of row to be inquired.
     * @return the offset of given size and num.
     */
    private static int hexOffSet(int s, int num) {
        int absoluteRow = num <= s ? num : 2 * s - num + 1;
        return s - absoluteRow;
    }

    private static int hexRowWidth(int s, int num) {
        int absoluteRow = num <= s ? num : 2 * s - num + 1;
        return s + 2 * (absoluteRow - 1);
    }

    private static void addRow(TETile[][] world, int startX, int startY ,int s, int num, TETile t) {
        int width = hexRowWidth(s, num);
        int offSet = hexOffSet(s, num);
        for (int i = 0; i < offSet; i++) {
            //world[startX++ + i][startY] = Tileset.NOTHING;
            startX++;
        }
        for (int i = 0; i < width; i++) {
            world[startX + i][startY] = t;
        }
    }

    private static void generateHexWorld(int size) {
        TERenderer ter = new TERenderer();
        TETile[][] world = generateMap(size);
        ter.initialize(world.length, world[0].length);
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        for (int i = 0; i < 5; i++) {
            addColumn(world, i + 1, size);
        }
        ter.renderFrame(world);
    }

    private static TETile[][] generateMap(int size) {
        int height = 10 * size;
        int singleWidth = size + 2 * (size - 1);
        int width = 3 * singleWidth + 2 * size;
        return new TETile[width][height];
    }

    private static void addColumn(TETile[][] world, int colNum, int size) {
        int offSet = 2 * size - 1;
        int x = (colNum - 1) * offSet;
        int y = Math.abs(colNum - 3) * size + size - 1;
        int num = 5 - Math.abs(colNum - 3);
        for (int i = 0; i < num; i++) {
            addHexagon(world, x, y + i * size * 2, size, randomTile());
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(8);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.LOCKED_DOOR;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.MOUNTAIN;
            case 5: return Tileset.SAND;
            case 6: return Tileset.TREE;
            case 7: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {
        generateHexWorld(4);
//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);
//
//        // initialize tiles
//        TETile[][] world = new TETile[WIDTH][HEIGHT];
//        for (int x = 0; x < WIDTH; x += 1) {
//            for (int y = 0; y < HEIGHT; y += 1) {
//                world[x][y] = Tileset.NOTHING;
//            }
//        }
//        addHexagon(world, 10, 10, 20, randomTile());
//        ter.renderFrame(world);
    }
}
