import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testArrayDeque() {
        StudentArrayDeque<Integer> input = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> std = new ArrayDequeSolution<>();
        while (true) {
            StringBuilder invokeTrace = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                double random = StdRandom.uniform();

                if (random < 0.5) {
                    input.addFirst(i);
                    std.addFirst(i);
                    invokeTrace.append("addFirst(" + i + ")" + System.lineSeparator());
                } else {
                    input.addLast(i);
                    std.addLast(i);
                    invokeTrace.append("removeLast(" + i + ")" + System.lineSeparator());
                }
                assertEquals(invokeTrace.toString(), std.get(std.size() - 1), input.get(input.size() - 1));
            }

            int removeLimit = (int) (10 * StdRandom.uniform());
            for (int i = 0; i < removeLimit; i++) {
                double random = StdRandom.uniform();

                if (random < 0.5) {
                    invokeTrace.append("removeFirst(" + ")" + System.lineSeparator());
                    assertEquals(invokeTrace.toString(), std.removeFirst(), input.removeFirst());
                } else {
                    invokeTrace.append("removeLast(" + ")" + System.lineSeparator());
                    assertEquals(invokeTrace.toString(), std.removeLast(), input.removeLast());
                }
            }
        }
    }
}
