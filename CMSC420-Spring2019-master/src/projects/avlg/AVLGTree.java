package projects.avlg;

import projects.avlg.exceptions.EmptyTreeException;
import projects.avlg.exceptions.InvalidBalanceException;

/** <p>An AVL-G Tree is an AVL Tree with a relaxed balance condition. Its constructor receives a strictly
 * positive parameter which controls the <b>maximum</b> imbalance allowed on any subtree of the tree which
 * it creates. So, for example:</p>
 *  <ul>
 *      <li>An AVL-1 tree is a classic AVL tree, which only allows for perfectly balanced binary
 *      subtrees (imbalance of 0 everywhere), or subtrees with a maximum imbalance of 1 (somewhere). </li>
 *      <li>An AVL-2 tree relaxes the criteria of AVL-1 trees, by also allowing for subtrees
 *      that have an imbalance of 2.</li>
 *      <li>AVL-3 trees allow an imbalance of 3</li>
 *      <li>...</li>
 *  </ul>
 *
 *  <p>The idea behind AVL-G trees is that rotations cost time, so maybe we would be willing to
 *  accept bad search performance now and then if it would mean less rotations.</p>
 *
 * @author <a href="https://github.com/JasonFil">Jason Filippou</a>
 */
public class AVLGTree<T extends Comparable<T>> {


    private static RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

    /* *************************************************************************
     ************** PLACE YOUR PRIVATE METHODS AND FIELDS HERE: ****************
     ***************************************************************************/




    /* *********************************************************************
     ************************* PUBLIC (INTERFACE) METHODS *******************
     **********************************************************************/

    /**
     * The class constructor provides the tree with its maximum maxImbalance allowed.
     * @param maxImbalance The maximum maxImbalance allowed by the AVL-G Tree.
     * @throws InvalidBalanceException if maxImbalance is a value smaller than 1.
     */
    public AVLGTree(int maxImbalance) throws InvalidBalanceException {
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }

    /**
     * Insert key in the tree.
     * @param key The key to insert in the tree.
     */
    public void insert(T key) {
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }

    /**
     * Delete the key from the data structure and return it to the caller.
     * @param key The key to delete from the structure.
     * @return The key that was removed, or null if the key was not found.
     * @throws EmptyTreeException if the tree is empty.
     */
    public T delete(T key) throws EmptyTreeException {
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }

    /**
     * <p>Search for key in the tree. Return a reference to it if it's in there,
     * or null otherwise.</p>
     * @param key The key to search for.
     * @return key if key is in the tree, or null otherwise.
     * @throws EmptyTreeException if the tree is empty.
     */
    public T search(T key) throws EmptyTreeException {
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }

    /**
     * Retrieves the maximum imbalance parameter.
     * @return The maximum imbalance parameter provided as a constructor parameter.
     */
    public int getMaxImbalance(){
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }


    /**
     * <p>Return the height of the tree. The height of the tree is defined as the length of the
     * longest path between the root and the leaf level. By definition of path length, a
     * stub tree has a height of 0, and we define an empty tree to have a height of -1.</p>
     * @return The height of the tree. If the tree is empty, returns -1.
     */
    public int getHeight() {
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }

    /**
     * Query the tree for emptiness. A tree is empty iff it has zero keys stored.
     * @return true if the tree is empty, false otherwise.
     */
    public boolean isEmpty() {
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }

    /**
     * Return the key at the tree's root nodes.
     * @return The key at the tree's root nodes.
     * @throws  EmptyTreeException if the tree is empty.
     */
    public T getRoot() throws EmptyTreeException{
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }


    /**
     * <p>Establishes whether the AVL-G tree <em>globally</em> satisfies the BST condition. This method is
     * <b>terrifically useful for testing!</b></p>
     * @return true if the tree satisfies the Binary Search Tree property,
     * false otherwise.
     */
    public boolean isBST() {
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }


    /**
     * <p>Establishes whether the AVL-G tree <em>globally</em> satisfies the AVL-G condition. This method is
     * <b>terrifically useful for testing!</b></p>
     * @return true if the tree satisfies the Binary Search Tree property,
     * false otherwise.
     */
    public boolean isAVLGBalanced() {
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }

    /**
     * <p>Empties the AVLGTree of all its elements. After a call to this method, the
     * tree should have <b>0</b> elements.</p>
     */
    public void clear(){
        throw  UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!
    }


    /**
     * <p>Return the number of elements in the tree.</p>
     * @return  The number of elements in the tree.
     */
    public int getCount(){
        throw UNIMPL_METHOD; // ERASE THIS LINE AFTER IMPLEMENTING THE METHOD!

    }
}
