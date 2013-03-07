package largeStack;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Unsafe2DStack implements Stack{

	public static final int DEFAULT_CHUNK_SIZE = 10000;

    public static final int SIZEOF_INT = 4;
	private long []chunks;

	private int curChunk;

	private int nextTop;

	private long capacity;

    private Unsafe unsafe;

    private long lastChunkCapacity;

    public Unsafe2DStack() throws Exception {
		this(DEFAULT_CHUNK_SIZE);
	}

	public Unsafe2DStack(int chunkSize) throws Exception {
            //System.out.println("new");
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);

            chunks = new long[1];
			chunks[0] = unsafe.allocateMemory(chunkSize * SIZEOF_INT);
            //System.err.println("allocate " + chunkSize + " on " + chunks[0]);
            curChunk = 0;
            nextTop = 0;
            capacity = chunkSize;
            lastChunkCapacity = chunkSize;
	}

	public void push(int v) {						
		if (nextTop == lastChunkCapacity) {
			int l = chunks.length;
            curChunk++;
			if (curChunk == l) {
				long []bigger = new long[l + 1];
				System.arraycopy(chunks, 0, bigger, 0, l);
                lastChunkCapacity = (int) capacity / 2;
				bigger[l] = unsafe.allocateMemory(lastChunkCapacity * SIZEOF_INT);
				chunks = bigger;						
				capacity += lastChunkCapacity;
			}
			nextTop=0;
		}
        unsafe.putInt(chunks[curChunk] + (nextTop++ * SIZEOF_INT), v);
	}

	public int pop() {
        throw new UnsupportedOperationException();
	}

    @Override
    public void free() {
        for (long l : chunks) {
            unsafe.freeMemory(l);
        }
    }

    @Override
    public int peek() {
        return unsafe.getInt(chunks[curChunk] + ((nextTop - 1) * SIZEOF_INT));
    }
}