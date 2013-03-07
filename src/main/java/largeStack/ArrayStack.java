package largeStack;

public class ArrayStack implements Stack {
	
	private int [] values;

	private int nb;

	public static final int DEFAULT_SIZE = 100000;

	private int inc;
	public ArrayStack(int s) {
		values = new int[s];
		nb = 0;
		inc = s;
	}
	public ArrayStack() {
		this(DEFAULT_SIZE);
	}

	public void push(int v) {
		if (nb == values.length) {
			int [] bigger = new int[values.length * 3 / 2];			
			System.arraycopy(values, 0, bigger, 0, values.length);
			values = bigger;
		}
		values[nb++] = v;
	}

	public int pop() {
		return values[--nb];
	}

	public long size() {
		return nb;
	}

    @Override
    public void free() {

    }
}