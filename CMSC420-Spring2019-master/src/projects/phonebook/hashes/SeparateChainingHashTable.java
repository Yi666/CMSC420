package projects.phonebook.hashes;

import projects.phonebook.utils.*;

/**<p>{@link SeparateChainingHashTable} is a {@link HashTable} that implements <b>Separate Chaining</b>
 * as its collision resolution strategy, i.e the collision chains are implemented as actual
 * Linked Lists. These Linked Lists are <b>not assumed ordered</b>. It is the easiest and most &quot; natural &quot; way to
 * implement a hash table and is useful for estimating hash function quality. In practice, it would
 * <b>not</b> be the best way to implement a hash table, because of the wasted space for the heads of the lists.
 * Open Addressing methods, like those implemented in {@link LinearProbingHashTable} and {@link QuadraticProbingHashTable}
 * are more desirable in practice, since they use the original space of the table for the collision chains themselves.</p>
 *
 * @author YOUR NAME HERE!
 * @see HashTable
 * @see SeparateChainingHashTable
 * @see LinearProbingHashTable
 * @see CollisionResolver
 */
public class SeparateChainingHashTable implements HashTable{


    /* *******************************************************************/
    /* ***** PRIVATE FIELDS / METHODS PROVIDED TO YOU: DO NOT EDIT! ******/
    /* ****************************************************** ***********/
    private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");
    private KVPairList[] table;
    private int count;
    private PrimeGenerator primeGenerator;

    private int hash(String key){
        return (key.hashCode() & 0x7fffffff) % table.length;
    }

    /* ******************/
    /*  PUBLIC METHODS: */
    /* ******************/
    /**
     *  Default constructor. Initializes the internal storage with a size equal to the default of {@link PrimeGenerator}.
     *  This constructor is <b>GIVEN TO YOU; DO NOT EDIT IT!</b>
     */
    public SeparateChainingHashTable(){
        primeGenerator = new PrimeGenerator();
        table = new KVPairList[primeGenerator.getCurrPrime()];
        for(int i = 0; i < table.length; i++){
            table[i] = new KVPairList();
        }
        count = 0;
    }

    @Override
    public void put(String key, String value) {
        throw UNIMPL_METHOD; // < ---- ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public String get(String key) {
        throw UNIMPL_METHOD; // < ---- ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public String remove(String key) {
        throw UNIMPL_METHOD; // < ---- ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public boolean containsKey(String key) {
        throw UNIMPL_METHOD; // < ---- ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public boolean containsValue(String value) {
        throw UNIMPL_METHOD; // < ---- ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public int size() {
        throw UNIMPL_METHOD; // < ---- ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public int capacity() {
        throw UNIMPL_METHOD; // < ---- ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }
    /**
     * Enlarges this hash table. At the very minimum, this method should increase the <b>capacity</b> of the hash table and ensure
     * that the new size is prime. The class {@link PrimeGenerator} implements the enlargement heuristic that
     * we have talked about in class and can be used as a black box if you wish.
     * @see PrimeGenerator#getNextPrime()
     */
    public void enlarge() {
        throw UNIMPL_METHOD; // < ---- ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }

    /**
     * Shrinks this hash table. At the very minimum, this method should decrease the size of the hash table and ensure
     * that the new size is prime. The class {@link PrimeGenerator} implements the shrinking heuristic that
     * we have talked about in class and can be used as a black box if you wish.
     *
     * @see PrimeGenerator#getPreviousPrime()
     */
    public void shrink(){
        throw UNIMPL_METHOD; // < ---- ERASE THIS LINE AFTER YOU IMPLEMENT THIS METHOD!
    }
}
