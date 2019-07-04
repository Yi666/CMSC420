package projects.phonebook.hashes;
import projects.phonebook.utils.KVPair;
import projects.phonebook.utils.PrimeGenerator;

/**
 * <p>{@link LinearProbingHashTable} is an Openly Addressed {@link HashTable} implemented with <b>Linear Probing</b> as its
 * collision resolution strategy: every key collision is resolved by moving one address over. It is
 * the most famous collision resolution strategy, praised for its simplicity, theoretical properties
 * and cache locality. It <b>does</b>, however, suffer from the &quot; clustering &quot; problem:
 * collision resolutions tend to cluster collision chains locally, making it hard for new keys to be
 * inserted without collisions. {@link QuadraticProbingHashTable} is a {@link HashTable} that
 * tries to avoid this problem, albeit sacrificing cache locality.</p>
 *
 * @author YOUR NAME HERE!
 *
 * @see HashTable
 * @see SeparateChainingHashTable
 * @see QuadraticProbingHashTable
 * @see CollisionResolver
 */
public class LinearProbingHashTable implements HashTable{

    /* *******************************************************************/
    /* ***** PRIVATE FIELDS / METHODS PROVIDED TO YOU: DO NOT EDIT! ******/
    /* ****************************************************** ***********/

    private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");
    private KVPair[] table;
    private PrimeGenerator primeGenerator;
    private int count = 0;

    private int hash(String key){
        return (key.hashCode() & 0x7fffffff) % table.length;
    }

    /*  YOU SHOULD ALSO IMPLEMENT THE FOLLOWING METHOD ACCORDING TO THE SPECS
     * PROVIDED IN THE PROJECT WRITEUP, BUT KEEP IT PRIVATE!  */
    private void enlarge(){
        throw UNIMPL_METHOD; // <---- ERASE THIS LINE WHEN YOU IMPLEMENT THIS METHOD!
    }

    /* ******************/
    /*  PUBLIC METHODS: */
    /* ******************/


    /**
     *  Default constructor. Initializes the internal storage with a size equal to the default of {@link PrimeGenerator}.
     *  This constructor is <b>given to you: DO NOT EDIT IT.</b>
     */
    public LinearProbingHashTable(){
        primeGenerator = new PrimeGenerator();
        table = new KVPair[primeGenerator.getCurrPrime()];
        count = 0;
    }

    /**
     * Inserts the pair &lt;key, value&gt; into this. The container should <b>not</b> allow for null
     * keys and values, and we <b>will</b> test if you are throwing a {@link IllegalArgumentException} from your code
     * if this method is given null arguments! It is important that we establish that no null entries
     * can exist in our database because the semantics of {@link #get(String)} and {@link #remove(String)} are that they
     * return null if, and only if, their key parameter is null. This method is expected to run in <em>amortized
     * constant time</em>.
     *
     * Instances of {@link LinearProbingHashTable} will follow the writeup's guidelines about how to internally resize
     * the hash table when the capacity exceeds 50&#37;
     * @param key The record's key.
     * @param value The record's value.
     * @throws IllegalArgumentException if either argument is null.
     */
    @Override
    public void put(String key, String value) {
        throw UNIMPL_METHOD; // <---- ERASE THIS LINE WHEN YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public String get(String key) {
        throw UNIMPL_METHOD; // <---- ERASE THIS LINE WHEN YOU IMPLEMENT THIS METHOD!
    }


    /**
     * <b>Return</b> and <b>remove</b> the value associated with key in the {@link HashTable}. If key does not exist in the database
     * or if key = null, this method returns null. This method is expected to run in <em>amortized constant time</em>.
     *
     * @param key The key to search for.
     * @return The associated value if key is non-null <b>and</b> exists in our database, null
     * otherwise.
     */
    @Override
    public String remove(String key) {
        throw UNIMPL_METHOD; // <---- ERASE THIS LINE WHEN YOU IMPLEMENT THIS METHOD!
    }


    @Override
    public boolean containsKey(String key) {
        throw UNIMPL_METHOD; // <---- ERASE THIS LINE WHEN YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public boolean containsValue(String value) {
        throw UNIMPL_METHOD; // <---- ERASE THIS LINE WHEN YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public int size() {
        throw UNIMPL_METHOD; // <---- ERASE THIS LINE WHEN YOU IMPLEMENT THIS METHOD!
    }

    @Override
    public int capacity() {
        throw UNIMPL_METHOD; // <---- ERASE THIS LINE WHEN YOU IMPLEMENT THIS METHOD!
    }
}
