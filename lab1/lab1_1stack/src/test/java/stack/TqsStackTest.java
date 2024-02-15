package stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TqsStackTest {
    TqsStack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new TqsStack<>();
    }

    @DisplayName("A stack is empty on construction. ")
    @Test
    void testEmptyStack() {
        // Arrange (required objects, initial state...)
        // beforeEach already created the stack

        // act
        // nothing to do

        // assess
        assertTrue(stack.isEmpty());
    }

    @DisplayName("A stack has size 0 on construction.")
    @Test
    void testStackSize() {
        assertEquals(0, stack.size());
    }

    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n.")
    @Test
    void testPush() {

        int n = 10;

        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        assertFalse(stack.isEmpty()); // or assertTrue(!stack.isEmpty())
        assertEquals(10, stack.size());
    }

    @DisplayName("If one pushes x then pops, the value popped is x.")
    @Test
    void testPop() {
        int x = 10;

        stack.push(x);
        int popped_val = stack.pop();

        assertEquals(x, popped_val);
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same.")
    @Test
    void testPeek() {
        int x = 10;

        stack.push(x);
        int peeked_val = stack.peek();

        assertEquals(x, peeked_val);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
    }

    @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0.")
    @Test
    void testPopUntilEmpty() {
        int n = 10;

        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        assertEquals(n, stack.size());

        for (int i = 0; i < n; i++) {
            stack.pop();
        }

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @DisplayName("Popping from an empty stack does throw a NoSuchElementException.")
    @Test
    void testPopEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @DisplayName("Peeking into an empty stack does throw a NoSuchElementException.")
    @Test
    void testPeekEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        });
    }

    @DisplayName("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException")
    @Test
    void testPushFullStack() {
        int limit = 5;

        TqsStack<Integer> bounded_stack = new TqsStack<>(limit);

        for (int i = 0; i < limit; i++) {
            bounded_stack.push(i);
        }

        assertThrows(IllegalStateException.class, () -> {
            bounded_stack.push(5);
        });
    }
}
