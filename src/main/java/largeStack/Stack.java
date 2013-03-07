package largeStack;

public interface Stack {
	
	void push(int v);

	int pop();

	long size();

    void free();
}