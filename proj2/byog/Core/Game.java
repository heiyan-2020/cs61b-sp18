package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT + 2, 0, -2);
        Generator ge = Generator.startPage();
        ge.generateWorld();
        ge.generatePlayer();
        String des = null;
        while (true) {
            ter.renderFrame(ge.world);
            int x_coordinate = (int) StdDraw.mouseX();
            int y_coordinate = (int) StdDraw.mouseY() + 2;
            if (x_coordinate < WIDTH && y_coordinate < HEIGHT) {
                des = ge.world[x_coordinate][y_coordinate].description();
            } else {
                des = "";
            }
            ge.getKeyBoardInput();
            Generator.showDescription(des);
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        int index = 0;
        char mode = input.charAt(0);
        input = input.substring(1);
        Generator ge = null;
        switch (mode) {
            case 'Q': return null;
            case 'L': ge = Generator.loadWorld();break;
            case 'N': {
                System.err.println(input);
//                boolean notEnd = true;
//                StringBuilder seed = new StringBuilder();
//                while (input.length() > 0 && notEnd) {
//                    char ch = input.charAt(0);
//                    input = input.substring(1);
//                    switch (ch) {
//                        case '0':
//                        case '1':
//                        case '2':
//                        case '3':
//                        case '4':
//                        case '5':
//                        case '6':
//                        case '7':
//                        case '8':
//                        case '9': seed.append(ch);break;
//                        case 'S': ge = new Generator(Long.parseLong(seed.toString()));notEnd = false;break;
//                        default: System.exit(0);break;
//                    }
//                }
//                break;
            }
            default: break;
        }
        ge.generateWorld();
        ge.generatePlayer();
        boolean readyToSave = false;
        while (input.length() > 0) {
            char ch = input.charAt(0);
            input = input.substring(1);
            switch (ch) {
                case 'W':
                case 'w':
                case 'A':
                case 'a':
                case 'S':
                case 's':
                case 'D':
                case 'd': ge.player.makeMove(ch);ge.keys.add(ch);break;
                case ':': readyToSave = true;break;
                case 'Q': {
                    if (readyToSave) {
                        ge.saveWorld();
                    }
                    break;
                }
                default: break;
            }
        }
        return ge.world;
    }
}
