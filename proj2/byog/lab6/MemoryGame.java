package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, Integer.parseInt(args[0]));
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random((seed));
    }

    public String generateRandomString(int n) {
        StringBuilder randomStringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            randomStringBuilder.append(CHARACTERS[rand.nextInt(26)]);
        }
        return randomStringBuilder.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(this.width / 2, this.height / 2, s);
        //StdDraw.text(this.width * 8, 0, );
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        char[] letterArray = letters.toCharArray();
        for (char ch : letterArray) {
            drawFrame(String.valueOf(ch));
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String input = "";
        drawFrame(input);

        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            input += String.valueOf(key);
            drawFrame(input);
        }
        StdDraw.pause(500);
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        //TODO: Establish Game loop
        while (!gameOver) {
            drawFrame("Round: " + round);
            StdDraw.pause(500);
            String expected = generateRandomString(round);
            flashSequence(expected);
            String actual = solicitNCharsInput(round);
            if (!actual.equals(expected)) {
                gameOver = true;
                break;
            }
            round += 1;
        }
        drawFrame("Game Over! You made it to round: " + round);
    }

}
