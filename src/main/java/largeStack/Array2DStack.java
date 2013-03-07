package largeStack;
public class Array2DStack implements Stack{
	
	public static final int DEFAULT_CHUNK_SIZE = 10000;

	private int chunkSize;

	private int [][]chunks;

	private int curChunk;

	private int nextTop;
	
	private long capacity;

    private long size;
    public Array2DStack() {
		this(DEFAULT_CHUNK_SIZE);
	}

	public Array2DStack(int chunkSize) {    
            this.chunkSize = chunkSize;                       
            chunks = new int[1][];            
			chunks[0] = new int[chunkSize];            
            curChunk = 0;
            nextTop = 0;
            capacity = chunkSize;
	}

	public void push(int v) {						
		if (nextTop == chunks[curChunk].length) {
			int l = chunks.length;
            curChunk++;
			if (curChunk == l) {
				int [][]bigger = new int[l + 1][];
				System.arraycopy(chunks, 0, bigger, 0, l);				
				int inc = (int) capacity / 2;
				bigger[l] = new int[inc];				
				chunks = bigger;						
				capacity += inc;
                //System.out.println(prettyChunks());
			}
			nextTop=0;
		}
        size++;
		chunks[curChunk][nextTop++] = v;
	}

	public int pop() {		
		if (nextTop == 0) {
			nextTop = chunkSize - 1;
			curChunk--;
		}
		if (curChunk == -1) {
			System.err.println("error");
		}
		return chunks[curChunk][--nextTop];
	}

	public long size() {
		return size;
	}

    private String prettyChunks() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < chunks.length; i++) {
            int [] chunk = chunks[i];
            b.append("chunk[" + i + "]:" + (chunk == null ? "null" : chunk.length) +  " ");
        }
        return b.toString();
    }

    @Override
    public void free() {
    }

    @Override
    public int peek() {
        return chunks[curChunk][nextTop - 1];
    }
}