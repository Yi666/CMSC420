package projects.bpt;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <p>{@link BinaryPatriciaTrie} is a Patricia Trie over the binary alphabet &#123;	 0, 1 &#125;. By restricting themselves
 * to this small but terrifically useful alphabet, Binary Patricia Tries combine all the positive
 * aspects of Patricia Tries while shedding the storage cost typically associated with Tries that
 * deal with huge alphabets.</p>
 *
 * @author Yi Liu
 */
public class BinaryPatriciaTrie {




    /**
     * Simple constructor that will initialize the internals of this.
     */

    private class Node {
        Boolean isEnd;
        String leftVal;
        String rightVal;
        Node left;
        Node right;
        Node parent;

        Node(Boolean isEnd,String leftVal,String rightVal,Node left,Node right,Node parent){
            this.isEnd = isEnd;
            this.leftVal = leftVal;
            this.rightVal = rightVal;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }


    }

    private Node root;

    private Integer size;

    public BinaryPatriciaTrie() {

        root = new Node(false,"","",null,null,null);
        size = 0;
    }

    public boolean search_with_root(Node root, String key) {
        if (root == null){
            return false;
        }

        if (key == ""){
            if (root.isEnd == true) {
                return true;
            }
            else{
                return false;
            }
        }


        if (root.leftVal!= "" && root.leftVal.length() <= key.length() && root.leftVal.charAt(0) == key.charAt(0)){

            if (root.leftVal.equals(key.substring(0,root.leftVal.length()))  ){
                if (root.leftVal.length() == key.length()){
                    if (root.left.isEnd == true){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else {
                    return search_with_root(root.left, key.substring(root.leftVal.length()));
                }
            }
            else{
                return false;
            }
        }
        else if (root.rightVal!="" && root.rightVal.length() <= key.length() && root.rightVal.charAt(0) == key.charAt(0)){

            if (root.rightVal.equals(key.substring(0,root.rightVal.length()))  ){
                if (root.rightVal.length() == key.length()){
                    if (root.right.isEnd == true){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else {
                    return search_with_root(root.right, key.substring(root.rightVal.length()));
                }
             }
            else{
                return  false;
            }
        }
        else{
            return false;
        }
    }


    public boolean insert_with_root(Node root, String key){

        boolean result = true;

        int locate = 0;
        Boolean PutANode = false;


        // confirm where to insert



        // insertion
        if ((int)key.charAt(0)==48){
            while (locate<key.length()){
                if (root.leftVal == ""){        //leftVal has no value
                    root.leftVal = key;
                    root.left = new Node(true,"","",null,null,root);
                    locate = key.length();
                    PutANode = true;
                    break;
                }
                else if (root.leftVal.length() <= locate){
                    return insert_with_root(root.left,key.substring(locate));
                }
                else if (root.leftVal.charAt(locate) == key.charAt(locate)){
                    locate += 1;
                }
                else{ //trie string and key not the same
                    String temp = root.leftVal.substring(locate);
                    Node tempNode = root.left;
                    root.leftVal = root.leftVal.substring(0,locate);
                    if ((int)key.charAt(locate) == 48) {

                        Node newNode = new Node(true,"","",null,null,root);
                        root.left = new Node(false,key.substring(locate),temp,newNode,tempNode,root);
                        root.left.left.parent = root.left;
                        root.left.right.parent = root.left;
                        PutANode = true;
                    }
                    else{
                        Node newNode = new Node(true,"","",null,null,root);
                        root.left = new Node(false,temp,key.substring(locate),tempNode,newNode,root);
                        root.left.left.parent = root.left;
                        root.left.right.parent = root.left;
                        PutANode = true;
                    }

                    break;

                }

            }


            if (root.leftVal.length()==locate){
                if (!PutANode && root.left.isEnd == true){
                    result = false;
                }
                else if (!PutANode && root.left.isEnd == false) {
                    root.left.isEnd = true;
                }
            }
            else{  //trie string is longer than the inserted key
                if ((int)root.leftVal.charAt(locate) == 48){
                    Node tempNode = root.left;

                    root.left = new Node(true,root.leftVal.substring(locate),"",tempNode,null,root);
                    root.left.left.parent = root.left;
                    root.leftVal = root.leftVal.substring(0,locate);
                }
                else{
                    Node tempNode = root.left;
                    tempNode.parent = root.left;
                    root.left = new Node(true,"",root.leftVal.substring(locate),null,tempNode,root);
                    root.left.right.parent = root.left;
                    root.leftVal = root.leftVal.substring(0,locate);
                }

            }
        }

        else {
            while (locate<key.length()){
                if (root.rightVal == ""){
                    root.rightVal = key;
                    root.right = new Node(true,"","",null,null,root);
                    locate = key.length();
                    PutANode = true;
                    break;
                }
                else if (root.rightVal.length() <= locate){
                    return insert_with_root(root.right,key.substring(locate));
                }
                else if (root.rightVal.charAt(locate) == key.charAt(locate)){
                    locate += 1;
                }
                else{ //trie string and key not the same
                    String temp = root.rightVal.substring(locate);
                    Node tempNode = root.right;
                    root.rightVal = root.rightVal.substring(0,locate);
                    if ((int)key.charAt(locate) == 48) {

                        Node newNode = new Node(true,"","",null,null,root);
                        root.right = new Node(false,key.substring(locate),temp,newNode,tempNode,root);
                        root.right.left.parent = root.right;
                        root.right.right.parent = root.right;
                        PutANode = true;

                    }
                    else{
                        Node newNode = new Node(true,"","",null,null,root);
                        root.right = new Node(false,temp,key.substring(locate),tempNode,newNode,root);
                        root.right.left.parent = root.right;
                        root.right.right.parent = root.right;
                        PutANode = true;
                    }
                    break;
                }

            }
            if (root.rightVal.length()==locate){
                if (!PutANode && root.right.isEnd == true){
                    result = false;
                }
                else if (!PutANode && root.right.isEnd == false) {
                    root.right.isEnd = true;
                }
            }
            else{
                //System.out.println((int)root.rightVal.charAt(locate));
                if ((int)root.rightVal.charAt(locate) == 48){
                    Node tempNode = root.right;


                    root.right = new Node(true,root.rightVal.substring(locate),"",tempNode,null,root);
                    root.right.left.parent = root.right;
                    root.rightVal = root.rightVal.substring(0,locate);
                }
                else{
                    Node tempNode = root.right;

                    root.right = new Node(true,"",root.rightVal.substring(locate),null,tempNode,root);
                    root.right.right.parent = root.right;
                    root.rightVal = root.rightVal.substring(0,locate);

                }

            }

        }
    return result;
    }


    public boolean delete_with_root(Node root, String key) {
        //boolean result = false;
        int locate = 0;

        if ((int)key.charAt(0) == 48) {
            while (locate < key.length()) {
                if (root.leftVal == "") {
                    return false;
                } else if (root.leftVal.length() <= locate) {
                    return delete_with_root(root.left, key.substring(locate));
                } else if (root.leftVal.charAt(locate) == key.charAt(locate)) {
                    locate += 1;
                } else { //string not match
                    return false;
                }
            }
            if (root.leftVal.length() == key.length()) {
                if (root.left.isEnd == false) {
                    return false;
                }
                else{
                        if (root.left.left != null && root.left.right != null) {
                            root.left.isEnd = false;
                        } else if (root.left.left != null && root.left.right == null) {
                            String temp = root.left.leftVal;
                            root.left.left = null;
                            root.left.isEnd = true;
                            root.leftVal = root.leftVal + temp;
                        } else if (root.left.left == null && root.left.right != null) {
                            String temp = root.left.rightVal;
                            root.left.right = null;
                            root.left.isEnd = true;
                            root.leftVal = root.leftVal + temp;
                        } else {   //left node has no children, directly delete this node

                            String temp = root.rightVal;
                            if (root.isEnd){
                                root.left = null;
                                root.leftVal = "";
                                return true;
                            }
                            if (root.parent != null) {
                                if (root.parent.left == root) {
                                    Node rootParent = root.parent;
                                    root.parent.leftVal = root.parent.leftVal + temp;
                                    root = root.right;
                                    root.parent = rootParent;
                                    root.parent.left = root;

                                } else {  //root is the right node of its parent
                                    Node rootParent = root.parent;
                                    root.parent.rightVal = root.parent.rightVal + temp;
                                    root = root.right;
                                    root.parent = rootParent;
                                    root.parent.right = root;

                                }
                            } else {
                                root.leftVal = "";
                                root.left = null;
                            }

                        }
                        return true;
                    }

            } else { //node string longer than key
                return false;
            }


        } else { //delete from the right
            while (locate < key.length()) {
                if (root.rightVal == "") {
                    return false;
                } else if (root.rightVal.length() <= locate) {
                    return delete_with_root(root.right, key.substring(locate));
                } else if (root.rightVal.charAt(locate) == key.charAt(locate)) {
                    locate += 1;
                } else { //string not match
                    return false;
                }
            }
            if (root.rightVal.length() == key.length()) {
                if (root.right.isEnd == false) {
                    return false;
                }
                else {
                        if (root.right.left != null && root.right.right != null) {
                            root.right.isEnd = false;
                        } else if (root.right.left != null && root.right.right == null) {
                            String temp = root.right.leftVal;
                            root.right.left = null;
                            root.right.isEnd = true;
                            root.rightVal = root.rightVal + temp;
                        } else if (root.right.left == null && root.right.right != null) {
                            String temp = root.right.rightVal;
                            root.right.right = null;
                            root.right.isEnd = true;
                            root.rightVal = root.rightVal + temp;
                        } else {

                            String temp = root.leftVal;
                            if (root.isEnd){
                                root.right= null;
                                root.rightVal = "";
                                return true;
                            }
                            if (root.parent != null) {
                                if (root.parent.left == root) {
                                    Node rootParent = root.parent;
                                    root.parent.leftVal = root.parent.leftVal + temp;
                                    root = root.left;
                                    root.parent = rootParent;
                                    root.parent.left = root;

                                } else {
                                    Node rootParent = root.parent;
                                    root.parent.rightVal = root.parent.rightVal + temp;
                                    root = root.left;
                                    root.parent = rootParent;
                                    root.parent.right = root;

                                }
                            } else {
                                root.rightVal = "";
                                root.right = null;
                            }

                        }
                        return true;
                    }

            } else {    //node string longer than key
                return false;
            }


        }
    }

    /*private void modify(Node node){
        if (node == null){
            return;
        }
        modify(node.left);
        modify(node.right);
        if (node == this.root){
            return;
        }
        if (node.isEnd == false){
            if (node.left == null && node.right == null){
                if (node.parent.left == node){
                    node.parent.leftVal = "";
                    node = null;
                }
                else{
                    node.parent.rightVal = "";
                    node = null;
                }
            }
            else if (node.left!=null && node.right == null){
                if (node.parent.left == node){
                    node.parent.leftVal += node.leftVal;
                    node = node.left;
                }
                else{
                    node.parent.rightVal += node.leftVal;
                    node = node.left;
                }
            }
            else if (node.left==null && node.right != null){
                if (node.parent.left==node){
                    node.parent.leftVal += node.rightVal;
                    node = node.right;
                }
                else{
                    node.parent.rightVal += node.rightVal;
                    node = node.right;
                }
            }
        }
        return;
    }*/


    public Boolean isJunkFree_with_root(Node root){
        if (root == null){
            return true;
        }
        Boolean temp = true;


        if(root.isEnd == false){
            if (root.left == null && root.right == null){
                temp = false;
            }
            else if (root.left != null && root.right == null){
                if (root.left.isEnd == false){
                    temp = false;
                }
            }
            else if (root.right != null && root.left == null){
                if (root.right.isEnd == false){
                    temp = false;
                }
            }
        }

        return temp && isJunkFree_with_root(root.left) && isJunkFree_with_root(root.right);
    }

    /**
     * Searches the trie for a given key.
     *
     * @param key The input String key.
     * @return true if and only if key is in the trie, false otherwise.
     */
    public boolean search(String key) {
        return search_with_root(root,key);
    }


    /**
     * Inserts key into the trie.
     *
     * @param key The input String key.
     * @return true if and only if the key was not already in the trie, false otherwise.
     */
    public boolean insert(String key) {
        if (key == "" && root.isEnd == true){
            return false;
        }
        else if (key == "" && root.isEnd == false){
            root.isEnd = true;
            size += 1;
            return true;
        }

        Boolean result = insert_with_root(root,key);
        if (result){
            size += 1;
        }
        return result;
    }

    /**
     * Deletes key from the trie.
     *
     * @param key The String key to be deleted.
     * @return True if and only if key was contained by the trie before we attempted deletion, false otherwise.
     */
    public boolean delete(String key) {
        if (key == "" && root.isEnd == true){
            root.isEnd = false;
            size -= 1;
            return true;
        }
        else if (key == "" && root.isEnd == false){
            return false;
        }

        Boolean result = delete_with_root(root,key);
        if (result){
            size -= 1;
        }
        return result;
    }

    /**
     * Queries the trie for emptiness.
     *
     * @return true if and only if {@link #getSize()} == 0, false otherwise.
     */
    public boolean isEmpty() {
        if (root.left == null && root.right == null){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Returns the number of keys in the tree.
     *
     * @return The number of keys in the tree.
     */
    public int getSize() {
        return size;
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


        return new Iterator<String>() {
            private Boolean Start = true;
            private Node curr = root;
            private String currString = "";
            String temp = "";



            @Override
            public boolean hasNext() {
                if (Start && curr == root && curr.left == null && curr.right== null && !curr.isEnd){
                    curr = null;
                    return false;
                }
                return curr != null;

            }

            @Override
            public String next() {

                if (Start){
                    while(true) {
                        while (curr.left != null) {
                            temp += curr.leftVal;
                            curr = curr.left;
                        }
                        if (!curr.isEnd) {
                            temp += curr.rightVal;
                            curr = curr.right;
                        } else {
                            this.currString = temp;
                            this.Start = false;
                            break;
                        }
                    }
                }
                String tempString = this.currString;
                while (true) {
                    if (curr.right != null) {
                        this.currString += curr.rightVal;
                        curr = curr.right;
                        while (curr.left != null) {
                            this.currString += curr.leftVal;
                            curr = curr.left;
                        }
                    } else {
                        while (true) {
                            if (curr.parent == null) {
                                curr = null;
                                break;
                            }
                            else if (findLast(curr)){             //find the last node
                                curr = null;
                                break;
                            }


                            if (curr.parent.left == curr) {
                                this.currString = this.currString.substring(0, this.currString.length() - curr.parent.leftVal.length());
                                curr = curr.parent;
                                break;
                            }
                            this.currString = this.currString.substring(0, this.currString.length() - curr.parent.rightVal.length());
                            curr = curr.parent;
                        }
                    }
                    if (curr == null){
                        break;
                    }
                    if(curr.isEnd){
                        break;
                    }
                }
                return tempString;
            }

        };
    }

    private Boolean findLast(Node node){
        while (node.parent != null){
            if(node.parent.left == node){
                return false;
            }
            node = node.parent;
        }
        return true;
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
        String result = "";
        Integer Longest = -1;
        if (root.left == null && root.right == null){
            return result;
        }
        Iterator<String> iter = this.inorderTraversal();
        while (iter.hasNext()){
            String temp = iter.next();
            if (temp.length()>Longest){
                Longest = temp.length();
                result = temp;
            }
            else if(temp.length() == Longest){
                for (int i=0;i<temp.length();i++){
                    if ((int)temp.charAt(i) > (int)result.charAt(i)){
                        result = temp;
                        break;
                    }
                    else if ((int)temp.charAt(i) < (int)result.charAt(i)){
                        break;
                    }
                }
            }
        }
        return result;


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

/*        Boolean result = isJunkFree_with_root(root.left) && isJunkFree_with_root(root.right);
        if (!result){
            modify(root);
        }*/
        Boolean result = isJunkFree_with_root(root.left) && isJunkFree_with_root(root.right);
        return result;
    }
}
