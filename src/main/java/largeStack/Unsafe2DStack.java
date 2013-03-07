package largeStack;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Unsafe2DStack implements Stack{

	public static final int DEFAULT_CHUNK_SIZE = 10000;

	private int chunkSize;

	private long []chunks;

	private int curChunk;

	private int nextTop;

	private long capacity;

    private long size;

    private Unsafe unsafe;

    private long lastChunkCapacity;
    public Unsafe2DStack() throws Exception {
		this(DEFAULT_CHUNK_SIZE);
	}

	public Unsafe2DStack(int chunkSize) throws Exception {

            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);

            this.chunkSize = chunkSize;                       
            chunks = new long[1];
			chunks[0] = unsafe.allocateMemory(chunkSize);
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
				int inc = (int) capacity / 2;
				bigger[l] = unsafe.allocateMemory(inc);
				chunks = bigger;						
				capacity += inc;
			}
			nextTop=0;
		}
        size++;
        unsafe.putInt(chunks[curChunk] + nextTop, v);
	}

	public int pop() {
        throw new UnsupportedOperationException();
	}

	public long size() {
		return size;
	}

    @Override
    public void free() {
        for (long l : chunks) {
            unsafe.freeMemory(l);
        }
    }
}