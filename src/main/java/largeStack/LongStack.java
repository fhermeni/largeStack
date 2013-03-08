package largeStack;

/**
 * A stack for long values.
 *
 * @author Fabien Hermenier
 */
public interface LongStack {

    /**
     * Push a value on the stack.
     * @param v the value to stack
     */
	void push(long v);

    /**
     * Pop a value from the stack.
     * @return the poped value.
     */
	long pop();

    /**
     * free the memory used by the stack
     */
    void free();

    /**
     * Get the value on the top of the stack.
     * @return the last pushed value
     */
    long peek();
}