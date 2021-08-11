package synthesizer;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        assertTrue(arb.isEmpty());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals((Integer) 1, arb.dequeue());
        assertEquals((Integer) 2, arb.dequeue());
        assertEquals((Integer) 3, arb.dequeue());
        assertTrue(arb.isEmpty());

    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        ArrayList<Integer> actual = new ArrayList<>();
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        for (Integer item : arb) {
            actual.add(item);
        }
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        assertEquals(expected, actual);
    }
    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
