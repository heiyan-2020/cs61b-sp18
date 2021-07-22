import static org.junit.Assert.*;

import org.junit.Test;

public class FilkTest {
    @Test
    public void TestA() {
        boolean input = Flik.isSameNumber(128, 128);
        assertTrue(input);
    }

    @Test
    public void TestB() {
        boolean input = Flik.isSameNumber(1, 1);
        assertTrue(input);
    }
}
