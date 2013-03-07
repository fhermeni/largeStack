package largeStack;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.IntBuffer;

public class IntBuffer2DStack implements Stack{

	public static final int DEFAULT_CHUNK_SIZE = 10000;

    public static final int SIZEOF_INT = 4;
	private IntBuffer[]chunks;

	private int curChunk;

	private int nextTop;

	private long capacity;

    private long size;

    private Unsafe unsafe;

    private int lastChunkCapacity;

    public IntBuffer2DStack(){
		this(DEFAULT_CHUNK_SIZE);
	}

	public IntBuffer2DStack(int chunkSize){
            chunks = new IntBuffer[1];
			chunks[0] = IntBuffer.allocate(chunkSize);
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
				IntBuffer []bigger = new IntBuffer[l + 1];
				System.arraycopy(chunks, 0, bigger, 0, l);
				lastChunkCapacity  = (int) capacity / 2;
				bigger[l] = IntBuffer.allocate(lastChunkCapacity);
				chunks = bigger;						
				capacity += lastChunkCapacity;
			}
			nextTop=0;
		}
        chunks[curChunk].put(nextTop++, v);
	}

	public int pop() {
        throw new UnsupportedOperationException();
	}

    @Override
    public void free() {
    }

    @Override
    public int peek() {
        //System.err.println("peek");
        return chunks[curChunk].get((nextTop - 1));
    }
}