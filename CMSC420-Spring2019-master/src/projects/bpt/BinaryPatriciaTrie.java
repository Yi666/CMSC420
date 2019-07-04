package projects.bpt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>{@link BinaryPatriciaTrie} is a Patricia Trie over the binary alphabet &#123;	 0, 1 &#125;. By restricting themselves
 * to this small but terrifically useful alphabet, Binary Patricia Tries combine all the positive
 * aspects of Patricia Tries while shedding the storage cost typically associated with Tries that
 * deal with huge alphabets.</p>
 *
 * @author ---- YOUR NAME HERE! -----
 */
public class BinaryPatriciaTrie {

    /* **************************************************************************************************  */
    /* ********************* PLACE YOUR PRIVATE MEMBERS AND METHODS BELOW: ******************************  */
    /*  ************************************************************************************************** */

    private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

    /* ************************************************************************************************** */
    /* ***********************   IMPLEMENT THE FOLLOWING PUBLIC METHODS:   ******************************** */
    /* ************************************************************************************************** */

    /**
     * Simple constructor that will initialize the internals of this.
     */
    public BinaryPatriciaTrie() {
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THIS METHOD!
    }

    /**
     * Searches the trie for a given key.
     *
     * @param key The input String key.
     * @return true if and only if key is in the trie, false otherwise.
     */
    public boolean search(String key) {
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THIS METHOD!
    }


    /**
     * Inserts key into the trie.
     *
     * @param key The input String key.
     * @return true if and only if the key was not already in the trie, false otherwise.
     */
    public boolean insert(String key) {
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THIS METHOD!
    }

    /**
     * Deletes key from the trie.
     *
     * @param key The String key to be deleted.
     * @return True if and only if key was contained by the trie before we attempted deletion, false otherwise.
     */
    public boolean delete(String key) {
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THIS METHOD!
    }

    /**
     * Queries the trie for emptiness.
     *
     * @return true if and only if {@link #getSize()} == 0, false otherwise.
     */
    public boolean isEmpty() {
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THIS METHOD!
    }

    /**
     * Returns the number of keys in the tree.
     *
     * @return The number of keys in the tree.
     */
    public int getSize() {
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THIS METHOD!
    }

    /**
     * <p>Performs an <i>inorder (symmetric) traversal</i> of the Binary Patricia Trie. Remember from lecture that inorder
     * traversal in tries is NOT sorted traversal, unless all the stored keys have the same length. This
     * is of course not required by your implementation, so you should make sure that in your tests you
     * are not expecting this method to return keys in lexicographic order. We put this method in the
     * interface because it helps us test your submission thoroughly and it helps you debug your code! </p>
     *
     * <p>We <b>neither require nor test </b> whether the {@link Iterator} returned by this method is fail-safe or fail-fast.
     * This means that you  do <b>not</b> need to test for thrown {@link java.util.ConcurrentModificationException}s and we do
     * <b>not</b> test your code for the possible occurrence of concurrent modifications.</p>
     *
     * <p>We also assume that the {@link Iterator} is <em>immutable</em>, i,e we do <b>not</b> test for the behavior
     * of {@link Iterator#remove()}. You can handle it any way you want for your own application, yet <b>we</b> will
     * <b>not</b> test for it.</p>
     *
     * @return An {@link Iterator} over the {@link String} keys stored in the trie, exposing the elements in <i>symmetric
     * order</i>.
     */
    public Iterator<String> inorderTraversal() {
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THIS METHOD!
    }


    /**
     * Finds the longest {@link String} stored in the Binary Patricia Trie.
     *
     * @return <p>The longest {@link String} stored in this. If the trie is empty, the empty string &quot;&quot; should be
     * returned. Careful: the empty string &quot;&quot;is <b>not</b> the same string as &quot; &quot;; the latter is a string
     * consisting of a single <b>space character</b>! It is also <b>not the same as the</b> null <b>reference</b>!</p>
     *
     * <p>Ties should be broken in terms of <b>value</b> of the bit string. For example, if our trie contained
     * only the binary strings 01 and 11, <b>11</b> would be the longest string. If our trie contained
     * only 001 and 010, <b>010</b> would be the longest string.</p>
     */
    public String getLongest() {
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THIS METHOD!
    }

    /**
     * Makes sure that your trie doesn't have splitter nodes with a single child. In a Patricia trie, those nodes should
     * be pruned. Be careful with the implementation of this method, since our tests call it to make sure your deletions work
     * correctly! That is to say, if your deletions work well, but you have made an error in this (far easier) method,
     * you will <b>still</b> not be passing our tests!
     *
     * @return true iff all nodes in the trie either denote stored strings or split into two subtrees, false otherwise.
     */
    public boolean isJunkFree(){
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THIS METHOD!
    }
}
