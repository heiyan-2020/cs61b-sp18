import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String testA = "abba";
        String testB = "aacbc";
        String testC = "aaaa";
        String testD = null;
        String testE = "";

        assertTrue(palindrome.isPalindrome(testA));
        assertFalse(palindrome.isPalindrome(testB));
        assertTrue(palindrome.isPalindrome(testC));
        assertFalse(palindrome.isPalindrome(testD));
        assertTrue(palindrome.isPalindrome(testE));
    }

    @Test
    public void testIsPalindromeWithCC() {
        String testA = "abba";
        String testB = "flake";
        String testC = "abab";
        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome(testA, cc));
        assertTrue(palindrome.isPalindrome(testB, cc));
        assertTrue(palindrome.isPalindrome(testC, cc));

    }
}
