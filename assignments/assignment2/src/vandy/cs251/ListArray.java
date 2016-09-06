package vandy.cs251;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides a generic dynamically-(re)sized array abstraction.
 */
public class ListArray<T extends Comparable<T>>
             implements Comparable<ListArray<T>>,
                        Iterable<T> {
  
	
	private Node head;
	
	private T[] list;

	private int m;
	
	private T defaultValue;

    /**
     * Constructs an array of the given size.
     * @param size Nonnegative integer size of the desired array.
     * @throws NegativeArraySizeException if the specified size is
     *         negative.
     */
    @SuppressWarnings("unchecked")
    public ListArray(int size) throws NegativeArraySizeException {
    	this(size, null);
    }

    /**
     * Constructs an array of the given size, filled with the provided
     * default value.
     * @param size Nonnegative integer size of the desired array.
     * @param defaultValue A default value for the array.
     * @throws NegativeArraySizeException if the specified size is
     *         negative.
     */
    public ListArray(int size,
                     T defaultValue) throws NegativeArraySizeException {
    	 if(size < 0) throw new NegativeArraySizeException(Integer.toString(size));
    	 list = (T[])(new Comparable[size]);
    	 this.defaultValue = defaultValue;
    	 Arrays.fill(list, defaultValue);
    	 head = new Node();
    }

    /**
     * Copy constructor; creates a deep copy of the provided array.
     * @param s The array to be copied.
     */
    public ListArray(ListArray<T> s) {
       list = s.list.clone();
       m = s.m;
       defaultValue = s.defaultValue;
    }

    /**
     * @return The current size of the array.
     */
    public int size() {
        // TODO - you fill in here (replace 0 with proper return
        // value).
        return list.length;
    }

    /**
     * Resizes the array to the requested size.
     *
     * Changes the size of this ListArray to hold the requested number of elements.
     * @param size Nonnegative requested new size.
     */
    public void resize(int size) {
        // TODO - you fill in here.
    	if(size < 0) throw new NegativeArraySizeException(Integer.toString(size));
    	T[] resizedArray = (T[])(new Object[size]);
    	resizedArray = Arrays.copyOfRange(list, 0, size());
    	list = resizedArray;
    }

    /**
     * @return the element at the requested index.
     * @param index Nonnegative index of the requested element.
     * @throws ArrayIndexOutOfBoundsException If the requested index is outside the
     * current bounds of the array.
     */
    public T get(int index) {
    	rangeCheck(index);
        return list[index];
    }

    /**
     * Sets the element at the requested index with a provided value.
     * @param index Nonnegative index of the requested element.
     * @param value A provided value.
     * @throws ArrayIndexOutOfBoundsException If the requested index is outside the
     * current bounds of the array.
     */
    public void set(int index, T value) {
    	rangeCheck(index);
    	list[index] = value;
    	if(head.value == null) head = new Node(value, new Node());
    	else{
    		Node current = head;
    		while(current.next  != null){
    			current = current.next;
    		}
    		current.next = new Node(value, current);
    	}
    }

    private Node seek(int index) {
        // TODO - you fill in here.
    	rangeCheck(index);
    	int i = 0;
    	for(Node nd : head){
    		if(i == index) return nd;
    		i++;
    	}
    	return null;
    }

    /**
     * Removes the element at the specified position in this ListArray.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).  Returns the element that was removed from the ListArray.
     *
     * @param index the index of the element to remove
     * @return element that was removed
     * @throws ArrayIndexOutOfBoundsException if the index is out of range.
     */
    public T remove(int index) {
        // TODO - you fill in here (replace null with proper return  [ 1 ,2, 3, 4] remove(2) = [1, 3, 4]  remove(1)  remove(4) set to null
        // value).
    	rangeCheck(index);
    	T item = list[index];
    	list[index] = null;
    	for(int i = index; i < size() - 1; i++){
    		list[index] = list[index + 1];
    	}
    	if(index == 0) list[size() - 1] = null; //last item set to null
        return item;
    }

    /**
     * Compares this array with another array.
     * <p>
     * This is a requirement of the Comparable interface.  It is used to provide
     * an ordering for ListArray elements.
     * @return a negative value if the provided array is "greater than" this array,
     * zero if the arrays are identical, and a positive value if the
     * provided array is "less than" this array.
     */
    @Override
    public int compareTo(ListArray<T> s) {
       if(size() > s.size()) return 1;
       if(size() < s.size()) return -1;
       for(int i = 0; i < size(); i++){
    	   if(get(i).compareTo(s.get(i)) > 0){
    		   return 1;
    	   }else if(get(i).compareTo(s.get(i)) < 0){
    		   return -1;
    	   }
       }
       return 0;
    }

    /** 
     * Throws an exception if the index is out of bound. 
     */
    private void rangeCheck(int index) {
        if(index < 0 && index >= size()) throw new IndexOutOfBoundsException(Integer.toString(index));
    }

    /**
     * Factory method that returns an Iterator.
     */
    public Iterator<T> iterator() {
        // TODO - you fill in here (replace null with proper return value).
        return new ListIterator();
    }

    private class Node implements Iterable<Node> {
        // TODO: Fill in any fields you require.
    	
    	private T value;
    	Node next;

        /**
         * Default constructor (no op).
         */
        Node() {
           value = null;
           next = null;
        }

        /**
         * Construct a Node from a @a prev Node.
         */
        Node(Node prev) {
           this(null, prev);
        }

        /**
         * Construct a Node from a @a value and a @a prev Node.
         */
        Node(T value, Node prev) {
        	this.value = value;
        	this.next = null;
        	prev.next = this;

        }

        /**
         * Ensure all subsequent nodes are properly deallocated.
         */
        void prune() {
        	
        }

        @Override
        public Iterator<Node> iterator() {
            return new NodeIterator();
        }
    }

    private class NodeIterator implements Iterator<Node> {
        // TODO: Fill in any fields you require.

    	private Node current;
    	
    	public NodeIterator(){
    		current = head;
    	}
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Node next() {
            if(hasNext()){
            	Node node = current;
            	current = current.next;
            	return node;
            }
            throw new NoSuchElementException();
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
        	throw new UnsupportedOperationException();
        }
    }

    /**
     * @brief This class implements an iterator for the list.
     */
    private class ListIterator implements Iterator<T> {
        // TODO: Fill in any fields you require.

    	private int m;
    	public ListIterator(){
    		m = 0;
    	}
    	
    	
        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
        	if(m < 0 && m > size()) throw new NoSuchElementException();
            return list[m++];
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
        	throw new UnsupportedOperationException();
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return m < size();
        }
    }
    
}
