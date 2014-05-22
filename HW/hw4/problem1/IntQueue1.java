package hw4.problem1;

import java.util.LinkedList;
import java.util.List;

/**
 * IntQueue1 is our first implementation of a basic first-in, first-out queue
 * for Integers.
 * <p>
 *
 * An IntQueue can be described as [n1, n2, ..., n_k], where n1 is the
 * least-recently-added item in the queue and is the next item to be
 * removed.  n_k is the most-recently-added and will be the last of the
 * current elements to be removed.
 * <p>
 *
 * An IntQueue can also be described constructively, with the append operation,
 * ':', such that [n1, n2, ..., n_k] : n_k+1 is the result of enqueing n_k+1
 * at the end of the queue.
 *
 * @author Krysta Yousoufian
 */
public class IntQueue1 {
    // This class represents a queue as a linked list where the front of
    // the list corresponds to the front of the queue.

    // Normally, your abstraction function and representation invarant would go
    // here. For ease of grading, please place them in hw4/answers/problem1.txt
    // instead with your answers to the other written exercises.

    List<Integer> entries;

    /**
     * @effects constructs an empty queue
     */
    public IntQueue1() {
        entries = new LinkedList<Integer>();
        checkRep();
    }

    /**
     * Enqueue an item
     * @param entry item to be added to the queue
     * @modifies this
     * @effects places entry at the end of the queue
		 * @throws IllegalArgumentException if entry is null
     */
    public void enqueue(Integer entry) {
				if (entry == null) {
						throw new IllegalArgumentException("entry cannot be null");
				}
        entries.add(entry);
        checkRep();
    }

    /**
     * Dequeue an item
     * @requires size() > 0
     * @modifies this
     * @effects removes the item at the front of the queue
     * @return the item that was first in the queue
     */
    public Integer dequeue() {
        Integer front = entries.remove(0);
        checkRep();
        return front;
    }

    /**
     * See the next item without removing it
     * @requires size() > 0
     * @return the item currently first in the queue
     */
    public Integer front() {
        return entries.get(0);
    }

    /**
     *
     * @return number of elements in the queue
     */
    public int size() {
        return entries.size();
    }

    /**
     *
     * @return size() == 0
     */
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public void checkRep() {
        // If I gave this to you, you wouldn't have the fun of figuring out the
        // rep invariant for yourself :)
    }
}
