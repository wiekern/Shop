package problem4;

public class WaitingQueue<E> {
	private class Node {
		E element;
		Node next;
	}
	
	private Node first = null, last = null;
	private int count = 0;

	
	public boolean isEmpty() {
		return first == null;
	}
	
	// accepts no more customer.
	public boolean isFull() {
		return count == 8;
	}
	
	// opened a new cashpoint.
	public boolean isLimited() {
		return count >= 6;
	}
	
	public int size() {
		return count;
	}
	
	public void enqueue(E e) {
		if (isFull()) {
			return;
		}
		else {	
			Node oldlast = last;
			last = new Node();
			last.element = e;
			last.next = null;
			count++;
			if(isEmpty()) {
				first = last;
			} else {
				oldlast.next = last;
			}
		}
	}
	
	public E dequeue() {
		if (isEmpty()) {
			return null;
		}
	
		E e = first.element;
		first = first.next;
		count--;
		if (isEmpty()) {
			last = null;
		}
		
		return e;
	}

}
