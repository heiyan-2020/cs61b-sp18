public class OffByN implements CharacterComparator {
    private int offSet;

    @Override
    public boolean equalChars(char x, char y) {
        return y - x == offSet || x - y == offSet;
    }

    public OffByN(int n) {
        offSet = n;
    }
}
