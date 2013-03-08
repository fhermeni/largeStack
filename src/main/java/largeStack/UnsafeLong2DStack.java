package largeStack;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeLong2DStack implements Stack{


    public static final int DEFAULT_CHUNK_SIZE = 10000;

    private long []chunks;

    private int curChunk;

    private int nextTop;

    private Unsafe unsafe;

    private int chunkSize;

    public static final int SIZEOF_INT = 4;

    public static final int SIZEOF_LONG = 8;

    public UnsafeLong2DStack() throws Exception {
        this(DEFAULT_CHUNK_SIZE);
    }

    public UnsafeLong2DStack(int size) throws Exception {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        unsafe = (Unsafe)field.get(null);
        this.chunkSize = size * SIZEOF_INT / 2;
        chunks = new long[1];
        chunks[0] = unsafe.allocateMemory(this.chunkSize);
        curChunk = 0;
        nextTop = 0;
    }

    private long buffer;
    private boolean first = true;
    public void push(int v) {
        if (nextTop == chunkSize) {
            curChunk++;
            int l = chunks.length;
            if (curChunk == l) {
                increase(l);
            }
        }
        if (first) {
            buffer = v;
            first = !first;
        } else {
            buffer = buffer << 32;
            buffer += v;
            first = true;
            unsafe.putLong(chunks[curChunk] + nextTop, buffer);
            nextTop+=SIZEOF_LONG;
        }
    }

    private void increase(int l) {
        long []bigger = new long[l + 1];
        System.arraycopy(chunks, 0, bigger, 0, l);
        bigger[l] = unsafe.allocateMemory(chunkSize);
        chunks = bigger;
        nextTop=0;
    }

    public int pop() {
        if (nextTop > 0) {
            nextTop -=SIZEOF_INT;
        } else {
            nextTop = chunkSize - SIZEOF_INT;
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
    public int peek() {
        if (!first) {
             //want the first number
             return (int)buffer;
        } else {
            return (int)(buffer >> 32);
        }
        //return unsafe.getInt(chunks[curChunk] + nextTop - SIZEOF_INT);
    }
}