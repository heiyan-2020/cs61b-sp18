package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Player {
    private final TETile ENTITY = Tileset.PLAYER;
    private TETile[][] world;
    private int x_coordinate;
    private int y_coordinate;

    Player(TETile[][] world) {
        this.world = world;
    }

    public void setPosition(int x, int y) {
        x_coordinate = x;
        y_coordinate = y;
        world[x_coordinate][y_coordinate] = ENTITY;
    }

    public void makeMove(char direction) {
        int tempX = x_coordinate;
        int tempY = y_coordinate;
        switch (direction) {
            case 'W':
            case 'w': tempY = y_coordinate + 1;break;
            case 'A':
            case 'a': tempX = x_coordinate - 1;break;
            case 'S':
            case 's': tempY = y_coordinate - 1;break;
            case 'D':
            case 'd': tempX = x_coordinate + 1;break;
            default: break;
        }
        if (tempX < 0 || tempX >= Generator.WIDTH || tempY < 0 || tempY >= Generator.HEIGHT) {
            return;
        } else if (world[tempX][tempY] != Tileset.FLOOR) {
            return;
        } else {
            world[x_coordinate][y_coordinate] = Tileset.FLOOR;
            world[tempX][tempY] = ENTITY;
            x_coordinate = tempX;
            y_coordinate = tempY;
        }
    }
}
