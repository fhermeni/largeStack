package largeStack;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * An implementation with unsafe with stack extends having every time the same size.
 */
public class UnsafeLongStack implements LongStack {

	public static final int DEFAULT_CHUNK_SIZE = 10000;

	private long []chunks;

	private int curChunk;

	private int nextTop;

    private Unsafe unsafe;

    private int chunkSize;

    public static final int SIZEOF_LONG = 8;

    public UnsafeLongStack() throws Exception {
		this(DEFAULT_CHUNK_SIZE);
	}

	public UnsafeLongStack(int size) throws Exception {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
            this.chunkSize = size * SIZEOF_LONG;
            chunks = new long[1];
			chunks[0] = unsafe.allocateMemory(this.chunkSize);
            curChunk = 0;
            nextTop = 0;
	}

	public void push(long v) {
		if (nextTop == chunkSize) {
            curChunk++;
            int l = chunks.length;
			if (curChunk == l) {
                increase(l);
			}
		}
        unsafe.putLong(chunks[curChunk] + nextTop, v);
        nextTop += SIZEOF_LONG;
	}

    private void increase(int l) {
        long []bigger = new long[l + 1];
        System.arraycopy(chunks, 0, bigger, 0, l);
        bigger[l] = unsafe.allocateMemory(chunkSize);
        chunks = bigger;
        nextTop=0;
    }

	public long pop() {

        if (nextTop > 0) {
            nextTop -=SIZEOF_LONG;
        } else {
            nextTop = chunkSize - SIZEOF_LONG;
            --curChunk;
        }
        return unsafe.getInt(chunks[curChunk] + nextTop);
	}

    @Override
    public void free() {
        for (long l : chunks) {
            unsafe.freeMemory(l);
        }
    }

    @Override
    public long peek() {
        return unsafe.getInt(chunks[curChunk] + nextTop - SIZEOF_LONG);
    }
}