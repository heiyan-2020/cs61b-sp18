package byog.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Leaf {
    public static Random rand;
    private static final int MIN_SIZE = 8;
    private static final int MAX_SIZE = 8;
    public int leftTopX;
    public int leftTopY;
    public int width;
    public int height;
    public Leaf leftChild;
    public Leaf rightChild;
    public Rectangular room;
    public List<Rectangular> halls;

    public Leaf(int x, int y, int w, int h) {
        leftTopX = x;
        leftTopY = y;
        width = w;
        height = h;
    }

    public boolean split() {
        if (leftChild != null || rightChild != null) {
            return false;
        }
        boolean splitHeight = rand.nextDouble() > 0.5;
        int maxSize = (splitHeight ? height : width) - MIN_SIZE;
        if (maxSize <= MIN_SIZE) {
            return false;
        }
        int splitPoint = rand.nextDouble() > 0.5 ? maxSize : MIN_SIZE;
        if (splitHeight) {
            leftChild = new Leaf(leftTopX, leftTopY, width, splitPoint);
            rightChild = new Leaf(leftTopX, leftTopY + splitPoint, width, height - splitPoint);
        } else {
            leftChild = new Leaf(leftTopX, leftTopY, splitPoint, height);
            rightChild = new Leaf(leftTopX + splitPoint, leftTopY, width - splitPoint, height);
        }
        return true;
    }

    public List<Leaf> createLeaves() {
        List<Leaf> leaves = new ArrayList<>();
        leaves.add(this);
        boolean continueSplit = true;

        while (continueSplit) {
            continueSplit = false;
            List<Leaf> newLeaves = new ArrayList<>();
            for (Leaf l : leaves) {
                if (l.leftChild == null && l.rightChild == null) {
                    if (l.width > MAX_SIZE || l.height > MAX_SIZE || rand.nextDouble() > 0.25) {
                        if (l.split()) {
                            newLeaves.add(l.leftChild);
                            newLeaves.add(l.rightChild);
                            continueSplit = true;
                        }
                    }
                }
            }
            leaves.addAll(newLeaves);
        }
        return leaves;
    }

    public void createRooms() {
        if (leftChild != null || rightChild != null) {
            if (leftChild != null) {
                leftChild.createRooms();
            }
            if (rightChild != null) {
                rightChild.createRooms();
            }
            if (rightChild != null && leftChild != null) {
               halls = Generator.createHall(rightChild.getRoom(), leftChild.getRoom());
            }
        } else {
            int roomWidth = rand.nextInt(width - 5) + 3;
            int roomHeight = rand.nextInt(height - 5) + 3;
            int roomLeftTopX = rand.nextInt(width - roomWidth - 2) + 1;
            int roomLeftTopY = rand.nextInt(height - roomHeight - 2) + 1;
            room = new Rectangular(leftTopX + roomLeftTopX, leftTopY + roomLeftTopY, roomWidth, roomHeight);
        }
    }

    public Rectangular getRoom() {
        if (room != null) {
            return room;
        }
        Rectangular lRoom = null;
        Rectangular rRoom = null;
        if (leftChild != null) {
            lRoom = leftChild.getRoom();
        }
        if (rightChild != null) {
            rRoom = rightChild.getRoom();
        }
        if (lRoom == null && rRoom == null) {
            return null;
        }
        if (lRoom == null) {
            return rRoom;
        }
        if (rRoom == null) {
            return lRoom;
        }
        return rand.nextDouble() > 0.5 ? lRoom : rRoom;
    }
}
