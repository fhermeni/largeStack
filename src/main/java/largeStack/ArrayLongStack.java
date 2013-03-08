package largeStack;

public class ArrayLongStack implements LongStack {
	
	private long [] values;

	private int nb;

	public static final int DEFAULT_SIZE = 100000;

	public ArrayLongStack(int s) {
		values = new long[s];
		nb = 0;
	}

	public ArrayLongStack() {
		this(DEFAULT_SIZE);
	}

    @Override
	public void push(long v) {
		if (nb == values.length) {
			long [] bigger = new long[values.length * 3 / 2];
			System.arraycopy(values, 0, bigger, 0, values.length);
			values = bigger;
		}
		values[nb++] = v;
	}

    @Override
	public long pop() {
		return values[--nb];
	}

    @Override
    public void free() {

    }

    @Override
    public long peek() {
        return values[nb - 1];
    }
}