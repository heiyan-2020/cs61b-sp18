public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> chDeque = new LinkedListDeque<>();
        int length = word.length();
        for (int i = 0; i < length; i += 1) {
            chDeque.addLast(word.charAt(i));
        }
        return chDeque;
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        Deque<Character> chDeque = wordToDeque(word);
        while (chDeque.size() > 1) {
            if (chDeque.removeFirst() != chDeque.removeLast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        Deque<Character> chDeque = wordToDeque(word);
        while (chDeque.size() > 1) {
            if (!cc.equalChars(chDeque.removeFirst(), chDeque.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
