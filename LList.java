/*
Yikai Wang and Shamaul Dilmohamed -- Team Sum Ting Wang
APCS2 pd5
HW15 -- Generically Speaking...
2016-03-17
*/

/*****************************************************
 * class LList
 * Implements a linked list
 * Version 03 uses doubly-linked nodes
 *****************************************************/

public class LList<T> implements List<T> { //your List.java must be in same dir

    //instance vars
    private DLLNode<T> _head, _tail; //pointers to first and last nodes
    private int _size;

    // constructors -- initializes instance vars
    public LList() {
	_head = _tail = null; //at birth, a list has no elements
	_size = 0;
    }

    public LList( T s ) {
        this();
        add(s);
        _size = 1;
    }
    //--------------v  List interface methods  v--------------

    //insert a node in front of first node
    public boolean add( T newVal ) { 
	addLast( newVal );
	return true; //per Java API spec
    } 


    //insert a node containing newVal at position index
    public void add( int index, T newVal ) {

	if ( index < 0 || index > size() )
	    throw new IndexOutOfBoundsException();

	else if ( index == size() ) 
	    addLast( newVal );

	DLLNode newNode = new DLLNode( newVal, null, null );

	//if index==0, insert node before head node
	if ( index == 0 ) 
	    addFirst( newVal );
	else {
	    DLLNode tmp1 = _head; //create alias to head

	    //walk tmp1 to node before desired node
	    for( int i=0; i < index-1; i++ )
		tmp1 = tmp1.getNext();

	    //init a pointer to node at insertion index
	    DLLNode tmp2 = tmp1.getNext(); 

	    //insert new node
	    newNode.setNext( tmp2 );
	    newNode.setPrev( tmp1 );
	    tmp1.setNext( newNode );
	    tmp2.setPrev( newNode );

	    //increment size attribute
	    _size++;

	}
    }


    //remove node at pos index, return its cargo
    public T remove( int index ) {

	if ( index < 0 || index >= size() )
	    throw new IndexOutOfBoundsException();

	if ( index == 0 )
	    return removeFirst();
	else if ( index == size()-1 )
	    return removeLast();
	else {
	    DLLNode tmp1 = _head; //create alias to head

	    //walk to node before desired node
	    for( int i=0; i < index-1; i++ ) {
		tmp1 = tmp1.getNext();
		//System.out.println( "tmp1: " + tmp1.getCargo() );
	    }
	    //check target node's cargo hold
	    T retVal = (T) (tmp1.getNext().getCargo());

	    //remove target node
	    tmp1.setNext( tmp1.getNext().getNext() );
	    //System.out.println( "tmp1.getNext: " + tmp1.getNext().getCargo() );
	    tmp1.getNext().setPrev( tmp1 );

	    _size--;

	    return retVal;
	}
    }


    public T get( int index ) { 

	if ( index < 0 || index >= size() )
	    throw new IndexOutOfBoundsException();

	T retVal;
	DLLNode tmp = _head; //create alias to head

	//walk to desired node
	for( int i=0; i < index; i++ )
	    tmp = tmp.getNext();

	//check target node's cargo hold
	retVal = (T) (tmp.getCargo());
	return retVal;
    } 


    public T set( int index, T newVal ) { 

	if ( index < 0 || index >= size() )
	    throw new IndexOutOfBoundsException();

	DLLNode tmp = _head; //create alias to head

	//walk to desired node
	for( int i=0; i < index; i++ )
	    tmp = tmp.getNext();

	//store target node's cargo
	T oldVal = (T) (tmp.getCargo());
	
	//modify target node's cargo
	tmp.setCargo( newVal );
	
	return oldVal;
    } 


    //return number of nodes in list
    public int size() { return _size; } 
    //--------------^  List interface methods  ^--------------


    //--------------v  Helper methods  v--------------

    public void addFirst( T newFirstVal ) { 
	//insert new node before first node (prev=null, next=_head)
	_head = new DLLNode( newFirstVal, null, _head );

	if ( _size == 0 ) 
	    _tail = _head;
	else 
	    _head.getNext().setPrev( _head );
	_size++;
    }

    public void addLast( T newLastVal ) { 
	//insert new node before first node (prev=_last, next=null)
	_tail = new DLLNode( newLastVal, _tail, null );

	if ( _size == 0 ) 
	    _head = _tail;
	else 
	    _tail.getPrev().setNext( _tail );
	_size++;
    }

    public T getFirst() { return _head.getCargo(); }

    public T getLast() { return _tail.getCargo(); }

    public T removeFirst() { 
	T retVal = getFirst();
	if ( size() == 1 ) {
	    _head = _tail = null;
	}
	else {
	    _head = _head.getNext();
	    _head.setPrev( null );
	}
	_size--;
	return retVal;
    }

    public T removeLast() { 
	T retVal = getLast();
	if ( size() == 1 ) {
	    _head = _tail = null;
	}
	else {
	    _tail = _tail.getPrev();
	    _tail.setNext( null );
	}
	_size--;
	return retVal;
    }
    //--------------^  Helper methods  ^--------------


    // override inherited toString
    public String toString() { 
        String s = "";
        
        String temp = ""; DLLNode n = _head;

        for( int i = _size; i > 0; i-- ) {
            temp += "[" + n.getCargo() + "]"; //value
            temp += "->"; //pointer
            n = n.getNext();
        }

        s += temp + "[null]";
        return s; 
    }


    //main method for testing
    public static void main( String[] args ) {

	    LList l1 = new LList<Integer>( 3 );
        LList l2 = new LList<String>( "John" );

        System.out.println( "l1:\n" + l1 );
        System.out.println( "l2:\n" + l2 );

        System.out.println( "l1's size: " + l1.size() );
        System.out.println( "l2's size: " + l2.size() );

        l1.add( 4 );
        l1.add( 2 );

        l2.add( "Bonjour" );

        System.out.println( "l1:\n" + l1 );
        System.out.println( "l2:\n" + l2 );

        System.out.println( "l1 at index 0:\n" + l1.get(0) );
        System.out.println( "l1 at index 1:\n" + l1.get(1) );

        System.out.println( l2.set(0,"Sup") );
        System.out.println( l2.set(1,"hi") );

        System.out.println( "l2:\n" + l2 );

        System.out.println( "l1's size: " + l1.size() );
        System.out.println( "l2's size: " + l2.size() );

        l1.add( 1, 420 );
        l1.add( 2, 69 );

        System.out.println( "l1:\n" + l1 );

        System.out.println( l2.remove(1) );
       	System.out.println( "l2:\n" + l2 );

        System.out.println( l1.remove(3) );
        System.out.println( "l1:\n" + l1 );

        System.out.println( "l1's size: " + l1.size() );
        System.out.println( "l2's size: " + l2.size() );

    }

}//end class LList



