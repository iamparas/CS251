package vandy.cs251;

import java.util.Arrays;

/**
 * Provides a wrapper facade around primitive char lists, allowing for dynamic
 * resizing.
 */
public class CharList implements Comparable<CharList>, Cloneable {
    
   
    private Node head;
   
    private int size = 20;

    private char defaultValue;

    private char[] charlist;

    public CharList(int size) {
        this(size, '\0');
    }


    public CharList(int size, char defaultValue) {
        if (size < 0)
            throw new IndexOutOfBoundsException(String.format("Invalid size %s" , size));
        this.size = size;
        this.defaultValue = defaultValue;
        charlist = new char[this.size];
        Arrays.fill(charlist, defaultValue);
    }

    /**
     * Copy constructor; creates a deep copy of the provided CharList.
     *
     * @param s
     *            The CharList to be copied.
     */
    public CharList(CharList s) {
        this.size = s.size();
        this.charlist = s.charlist;
        this.defaultValue = s.defaultValue;
    }

    /**
     * Creates a deep copy of this CharList.  Implements the
     * Prototype pattern.
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        // TODO - you fill in here (replace return null with right
        // implementation).
        CharList clonedCharList = (CharList)super.clone();
        clonedCharList.charlist = this.charlist.clone();
        return clonedCharList;
    }

    public void printElems(){
    	System.out.printf("Size : %s , Default value %s", this.size, this.defaultValue);
    	System.out.println(Arrays.toString(this.charlist));
    }
    /**
     * @return The current size of the list.
     */
    public int size() {
        // TODO - you fill in here (replace return 0 with right
        // implementation).
        return this.size;
    }

    /**
     * Resizes the list to the requested size.
     *
     * Changes the capacity of this list to hold the requested number of elements.
     * Note the following optimizations/implementation details:
     * <ul>
     *   <li> If the requests size is smaller than the current maximum capacity, new memory
     *   is not allocated.
     *   <li> If the list was constructed with a aault value, it is used to populate
     *   uninitialized fields in the list.
     * </ul>
     * @param size Nonnegative requested new size.
     */
    public void resize(int size) {
        
        if(size < 0) throw new IllegalArgumentException("Invalid size");
        char[] newCharList = new char[size];
        
        for(int i = 0; i < size; i++){
            if(i >= size()) {
                newCharList[i] = this.defaultValue;
                continue;
            }
            newCharList[i] = this.charlist[i];
        }
        this.charlist = newCharList;
        this.size = size;
    }

    /**
     * @return the element at the requested index.
     * @param index
     *            Nonnegative index of the requested element.
     * @throws IndexOutOfBoundsException
     *             If the requested index is outside the current bounds of the
     *             list.
     */
    public char get(int index) {
        rangeCheck(index);
        return charlist[index];
    }

    /**
     * Sets the element at the requested index with a provided value.
     * 
     * @param index
     *            Nonnegative index of the requested element.
     * @param value
     *            A provided value.
     * @throws IndexOutOfBoundsException
     *             If the requested index is outside the current bounds of the
     *             list.
     */
    public void set(int index, char value) {
        rangeCheck(index);
        charlist[index] = value;
    }

    /**
     * Locate and return the @a Node at the @a index location.
     */
    public Node seek(int index) {
        // TODO - you fill in here
    	if(index < 0) return new Node();
    	if(index == 0) return new Node(charlist[0], new Node());
    	return new Node(charlist[index], seek(index - 1));
    }

    /**
     * Compares this list with another list.
     * <p>
     * This is a requirement of the Comparable interface. It is used to provide
     * an ordering for CharList elements.
     * 
     * @return a negative value if the provided list is "greater than" this
     *         list, zero if the lists are identical, and a positive value if
     *         the provided list is "less than" this list. These lists should be
     *         compared lexicographically.
     */
    @Override
    public int compareTo(CharList s) {
        // TODO - you fill in here (replace return 0 with right
        // implementation).
    	if(this.size() < s.size()) return -1;
    	if(this.size() > s.size()) return 1;
 
    	for(int i = 0; i < this.size(); i++){
    		if(this.get(i) > s.get(i)) return 1;
    		if(this.get(i) < s.get(i)) return -1;
    	}
        return 0;
    }

    /**
     * Throws an exception if the index is out of bound.
     */
    private void rangeCheck(int index) {
       if(index < 0 || index >= this.size()) throw new IndexOutOfBoundsException(Integer.toString(index));
    }

    /**
     * A Node in the Linked List.
     */
    private class Node {
        
    	private char val;
    	
    	private Node  next;
        
        /**
         * Default constructor (no op).
         */
        Node() {
        	val = '\0';
        	next = null;
        }

        /**
         * Construct a Node from a @a prev Node.
         */
        Node(Node prev) {
        	this(prev.val, prev);
        }

        /**
         * Construct a Node from a @a value and a @a prev Node.
         */
        Node(char value, Node prev) {
           	this.val = value;
           	this.next = prev.next;
           	prev.next = this;
        }

        /**
         * Ensure all subsequent nodes are properly deallocated.
         */
        void prune() {
            // TODO - you fill in here
            // Leaving the list fully linked could *potentially* cause
            // a pathological performance issue for the garbage
            // collector.
        	
        	
        }
    }
    
    public static void main(String[] args){
    	CharList chl = new CharList(1);
    	System.out.println(chl.get(0));
    }
}
