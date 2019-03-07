package projects.avlg;

import com.sun.jdi.Value;
import com.sun.source.tree.Tree;
import projects.avlg.exceptions.EmptyTreeException;
import projects.avlg.exceptions.InvalidBalanceException;

import java.security.Key;

import static java.lang.Math.abs;

/** <p>An <tt>AVL-G Tree</tt> is an AVL Tree with a relaxed balance condition. Its constructor receives a strictly
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
 * @author Yi Liu
 */
public class AVLGTree<T extends Comparable<T>> {


    private static RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");


    /**
     *  data structure
     */


    private int max_balance_AVLG;
    private Node root;




    private class Node {
        private Node left;
        private Node right;
        private int height;
        private int balance;

        private T key;

        private Node(T key, int height, int balance) {

            this.key = key;
            this.height = height;
            this.balance = balance;
        }

    }



    /**
     * Searching the position for new node to insert
     */
    private Node search_node(Node node, T key) {
        if (key.compareTo(node.key) < 0) {
            if (node.left == null) {
                return node.left;
            }
            else {
                return search_node(node.left, key);
            }
        }
        else if (key.compareTo(node.key) == 0 ) {
            return node;
        }
        else {
            if (node.right == null) {
                return node.right;
            }
            else {
                return search_node(node.right, key);
            }
        }
    }

    private void search_insert_node(Node node, T key) {
        if (key.compareTo(node.key) < 0) {
            if (node.left == null) {
                node.left = new Node(key,0,0);

            }
            else {
                search_insert_node(node.left, key);
            }
        }
        else if (key.compareTo(node.key) == 0 ) {

        }
        else {
            if (node.right == null) {
                node.right = new Node(key,0,0);

            }
            else {
                search_insert_node(node.right, key);
            }
        }
    }



    /**
     * getting the height and balance of any nodes
     */
    private int node_height(Node node) {
        if (node == null) {
            return -1;
        }
        int left_height = 0;
        int right_height = 0;
        if (node.left == null) {
            left_height = -1;
        }
        else{
            left_height = node_height(node.left);
        }
        if (node.right == null) {
            right_height = -1;
        }
        else{
            right_height = node_height(node.right);
        }


        if (left_height > right_height) {
            return 1 + left_height;
        }
        else {
            return 1 + right_height;
            }

    }
    private int node_balance(Node node) {
        if (node == null) {
            return 0;
        }
        else {
            return node_height(node.left) - node_height(node.right);
        }
    }


    /**
     * revise all heights and balances in the tree, and get the maximum balance of the tree
     */
    private void revise_height(Node node) {
        if (node == null) {
            return;
        }
        revise_height(node.left);
        node.height = node_height(node);
        revise_height(node.right);

    }
    private void revise_balance(Node node) {
        if (node == null) {
            return;
        }
        revise_balance(node.left);
        revise_balance(node.right);
        node.balance = node_balance(node);

    }
    private int max_balance(Node node) {
        if (node == null) {
            return 0;
        }
        int max_balance_of_tree = abs(node.balance);
        if (node.left != null) {
            max_balance_of_tree = Math.max(max_balance_of_tree,abs(max_balance(node.left)));
        }
        if (node.right != null) {
            max_balance_of_tree = Math.max(max_balance_of_tree,abs(max_balance(node.right)));
        }
        return max_balance_of_tree;
    }

    /**
     * get the counts of the tree with traversal
     */
    private int Tree_count(Node node) {
        int node_count = 0;
        if (node == null) {
            return 0;
        }
        else {

            node_count = 1 + Tree_count(node.left) + Tree_count(node.right);
            return node_count;
        }
    }

    /**
     * check if the tree is BST
     */
    private Boolean Tree_is_BST(Node node) {
        if (node == null) {
            return true;
        }
        else {
            if (node.left != null) {
                if (node.key.compareTo(node.left.key) < 0) {
                    return false;
                }
            }
            if (node.right != null) {
                if (node.key.compareTo(node.right.key) > 0) {
                    return false;
                }
            }
            return Tree_is_BST(node.left) && Tree_is_BST(node.right);

        }
    }

    /**
     * rotate right/left/left-right/right-left
     */

    private Node rotateRight(Node node) {

        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        return temp;
    }
    private Node rotateLeft(Node node) {

        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        return  temp;
    }
    private Node rotateLR(Node node) {

        node.left = rotateLeft(node.left);
        node = rotateRight(node);
        return node;
    }
    private Node rotateRL(Node node) {

        node.right = rotateRight(node.right);
        node = rotateLeft(node);
        return node;
    }

    /**
    find the way to rotate
     */
    private Node Node_Rotation(Node node) {
        if (node.balance < 0) {
            if (node.right.balance <= 0) {
                node = rotateLeft(node);
                return node;
            }
            else {
                node = rotateRL(node);
                return node;
            }
        }
        else {
            if (node.left.balance >= 0) {
                node = rotateRight(node);
                return node;
            }
            else {
                node = rotateLR(node);
                return node;
            }
        }
    }


    private Node checkRotation(Node node) {
        if (node == null) {
            return node;
        }
        node.left = checkRotation(node.left);
        node.right = checkRotation(node.right);


        if (abs(node.balance) > max_balance_AVLG) {
            node = Node_Rotation(node);
            revise_height(root);
            revise_balance(root);
            return node;
        }

        else {
            return node;
        }

    }
    private void check_balance() {
            root = checkRotation(root);
            revise_height(root);
            revise_balance(root);

    }


    /**
     * Delete the node
     */
    private Node Node_delete(Node node, T key) {
        if (node == null) {
            return node;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = Node_delete(node.left,key);
            return node;
        }
        else if (key.compareTo(node.key) > 0) {
            node.right = Node_delete(node.right,key);
            return node;
        }
        else {                                                      //this node is the node to be deleted
            if (node.left == null && node.right == null) {
                return null;
            }
            else if (node.left != null && node.right == null) {
                return node.left;
            }
            else if ( node.left == null) {
                return node.right;
            }
            else {
                node.key = minValue(node.right);
                node.right = Node_delete(node.right,minValue(node.right));
                return node;

            }
        }
    }
    private T minValue(Node node){
        T min_key = node.key;
        while (node.left != null) {
            min_key = node.left.key;
            node = node.left;

            System.out.println(min_key);
        }

        return min_key;
    }

    private void traversal(Node node){
        if (node == null) {
            return;
        }
        System.out.println(node.key);
        traversal(node.left);
        traversal(node.right);
    }


    /**
         * The class constructor provides the tree with its maximum maxImbalance allowed.
         * @param maxImbalance The maximum maxImbalance allowed by the AVL-G Tree.
         * @throws InvalidBalanceException if <tt>maxImbalance</tt> is a value smaller than 1.
         */
    public AVLGTree(int maxImbalance) throws InvalidBalanceException {
        if (maxImbalance < 1) {
            throw new InvalidBalanceException("Invalid Balance");
        }
        else {
            this.max_balance_AVLG = maxImbalance;

        }

    }

    /**
     * Insert <tt>key</tt> in the tree.
     * @param key The key to insert in the tree.
     */
    public void insert(T key) {
        if (this.isEmpty()) {
            root = new Node(key,0,0);
        }
        else {
            search_insert_node(root,key);
        }

        revise_height(root);
        revise_balance(root);

        // check the balance of the tree
        if (abs(max_balance(root)) > max_balance_AVLG) {
            check_balance();

        }








    }

    /**
     * Delete the key from the data structure and return it to the caller.
     * @param key The key to delete from the structure.
     * @return The key that was removed, or <tt>null</tt> if the key was not found.
     * @throws EmptyTreeException if the tree is empty.
     */
    public T delete(T key) throws EmptyTreeException {
        if (root == null) {
            throw new EmptyTreeException("Tree is empty");
        }

        else {
            if (search_node(root, key) == null) {
                return null;
            } else {
                root = Node_delete(root,key);

                revise_height(root);
                revise_balance(root);


                /*if (root.right != null){
                    if (root.right.right != null) {
                        System.out.println("40 height " + root.right.right.height);
                        System.out.println("40 balance" + root.right.right.balance);
                    }
                }
                System.out.println("max balance " + max_balance(root));
                */

                // check the balance of the tree
                if (abs(max_balance(root)) > max_balance_AVLG) {
                    check_balance();
                }

                /*
                if (root.right != null){
                    if (root.right.right != null) {
                        System.out.println("40 height " + root.right.right.height);
                        System.out.println("40 balance" + root.right.right.balance);
                    }
                }
                */

                return key;
            }
        }
    }

    /**
     * <p>Search for <tt>key</tt> in the tree. Return a reference to it if it's in there,
     * or <tt>null</tt> otherwise.</p>
     * @param key The key to search for.
     * @return <tt>key</tt> if <tt>key</tt> is in the tree, or <tt>null</tt> otherwise.
     * @throws EmptyTreeException if the tree is empty.
     */
    public T search(T key) throws EmptyTreeException {
        if (root == null) {
            throw new EmptyTreeException("Tree is empty");
        }
        else {
            Node location_of_node = this.search_node(root,key);
            if (location_of_node == null) {
                return null;
            }
            else {
                return location_of_node.key;
            }
        }
    }

    /**
     * Retrieves the maximum imbalance parameter.
     * @return The maximum imbalance parameter provided as a constructor parameter.
     */
    public int getMaxImbalance(){
        if (root == null) {
            return 0;
        }
        else {
            return max_balance(root);
        }

    }


    /**
     * <p>Return the height of the tree. The height of the tree is defined as the length of the
     * longest path between the root and the leaf level. By definition of path length, a
     * stub tree has a height of 0, and we define an empty tree to have a height of -1.</p>
     * @return The height of the tree. If the tree is empty, returns -1.
     */
    public int getHeight() {
        if (root == null) {
            return -1;
        }
        else {
            revise_height(root);
            revise_balance(root);
            return root.height;
        }
    }

    /**
     * Query the tree for emptiness. A tree is empty iff it has zero keys stored.
     * @return <tt>true</tt> if the tree is empty, <tt>false</tt> otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Return the key at the tree's root nodes.
     * @return The key at the tree's root nodes.
     * @throws  EmptyTreeException if the tree is empty.
     */
    public T getRoot() throws EmptyTreeException{
        if (root == null) {
            throw new EmptyTreeException("Tree is empty");
        }
        else {
            return root.key;
        }
    }


    /**
     * <p>Establishes whether the AVL-G tree <em>globally</em> satisfies the BST condition. This method is
     * <b>terrifically useful for testing!</b></p>
     * @return <tt>true</tt> if the tree satisfies the Binary Search Tree property,
     * <tt>false</tt> otherwise.
     */
    public boolean isBST() {
        if (root == null) {
            return true;
        }
        else {
            return Tree_is_BST(root);
        }
    }


    /**
     * <p>Establishes whether the AVL-G tree <em>globally</em> satisfies the AVL-G condition. This method is
     * <b>terrifically useful for testing!</b></p>
     * @return <tt>true</tt> if the tree satisfies the Binary Search Tree property,
     * <tt>false</tt> otherwise.
     */
    public boolean isAVLGBalanced() {
        if (root == null) {
            return true;
        }
        else {
            revise_height(root);
            revise_balance(root);

            // check the balance of the tree
            if (abs(max_balance(root)) > max_balance_AVLG) {
                check_balance();
            }

            return max_balance(root) <= max_balance_AVLG;
        }
    }

    /**
     * <p>Empties the <tt>AVLGTree</tt> of all its elements. After a call to this method, the
     * tree should have <b>0</b> elements.</p>
     */
    public void clear(){
        root = null;
    }


    /**
     * <p>Return the number of elements in the tree.</p>
     * @return  The number of elements in the tree.
     */
    public int getCount(){
        return Tree_count(root);

    }
}
