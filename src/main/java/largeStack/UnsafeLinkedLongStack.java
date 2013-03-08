package largeStack;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * An implementation with unsafe with stack extends implemented using a linked list.
 * No more array copies !
 * Stack extends have always the same size, contrary to the principles of ArrayList.
 */
public class UnsafeLinkedLongStack implements LongStack {

	public static final int DEFAULT_CHUNK_SIZE = 10000;

	private IndexCell curChunk;

    private Unsafe unsafe;

    private int chunkSize;

    private IndexCell tail;

    private long sp;

    private long endOfChunk;

    public static final int SIZEOF_LONG = 8;

    public UnsafeLinkedLongStack() throws Exception {
		this(DEFAULT_CHUNK_SIZE);
	}

	public UnsafeLinkedLongStack(int size) throws Exception {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
            this.chunkSize = size * SIZEOF_LONG;
            tail = new IndexCell(unsafe.allocateMemory(this.chunkSize));
            curChunk = tail;
            sp = curChunk.base;
            endOfChunk = sp + chunkSize;
	}

	public void push(long v) {
		if (sp == endOfChunk) {
            if (curChunk.next == null) {
                increase();
            } else {
                curChunk = curChunk.next;
                sp = curChunk.base;
                endOfChunk = sp + chunkSize;
            }
		}
        unsafe.putLong(sp, v);
        sp += SIZEOF_LONG;
	}

    private void increase() {
        curChunk = new IndexCell(unsafe.allocateMemory(chunkSize), tail);
        tail.next = curChunk;
        tail = curChunk;
        sp = curChunk.base;
        endOfChunk = curChunk.base + chunkSize;
    }

	public long pop() {
        if (sp > curChunk.base) {
            sp -= SIZEOF_LONG;
        } else {
            curChunk = curChunk.prev;
            sp = curChunk.base + chunkSize - SIZEOF_LONG;
        }
        return unsafe.getInt(sp);
	}

    @Override
    public void free() {
        IndexCell c = tail;
        while (c != null) {
            unsafe.freeMemory(c.base);
            c = c.prev;
        }
    }

    @Override
    public long peek() {
        return unsafe.getInt(sp - SIZEOF_LONG);
    }

    static class IndexCell {

        long base;

        IndexCell next;

        IndexCell prev;

        public IndexCell(long v, IndexCell p) {
            this.base = v;
            this.next = null;
            this.prev = p;
        }

        public IndexCell(long v) {
            base = v;
            next = null;
            prev = null;
        }
    }
}