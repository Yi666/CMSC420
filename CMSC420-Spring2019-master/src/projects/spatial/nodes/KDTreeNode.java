package projects.spatial.nodes;

import projects.spatial.kdpoint.KDPoint;
import projects.spatial.knnutils.BoundedPriorityQueue;
import projects.spatial.knnutils.NNData;
import projects.spatial.trees.KDTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * <p>{@link KDTreeNode} is an abstraction over nodes of a KD-Tree. It is used extensively by
 * {@link projects.spatial.trees.KDTree} to implement its functionality.</p>
 *
 * <p><b>YOU ***** MUST ***** IMPLEMENT THIS CLASS!</b></p>
 *
 * @author  Yi Liu
 *
 * @see projects.spatial.trees.KDTree
 */
public class KDTreeNode {


    /* *************************************************************************
     ************** PLACE YOUR PRIVATE METHODS AND FIELDS HERE: ****************
     ***************************************************************************/
    private ArrayList<Double> value = new ArrayList<>(100);
    private KDTreeNode left;
    private KDTreeNode right;
    private KDTreeNode parent;

    private KDPoint kdPoint;


    /* ***************************************************************************** */
    /* ******************* PUBLIC (INTERFACE) METHODS ****************************** */
    /* ***************************************************************************** */


    /**
     * 1-arg constructor. Stores the provided {@link KDPoint} inside the freshly created node.
     * @param p The {@link KDPoint} to store inside this. Just a reminder: {@link KDPoint}s are
     *          <b>mutable!!!</b>.
     */
    public KDTreeNode(KDPoint p){
        this.left = null;
        this.right = null;
        this.parent = null;
        for (int i=0;i<p.coords.length;i++){
            this.value.add(p.coords[i]);
        }
        this.kdPoint = p;

    }

    /**
     * <p>Inserts the provided {@link KDPoint} in the tree rooted at this. To select which subtree to recurse to,
     * the KD-Tree acts as a Binary Search Tree on currDim; it will examine the value of the provided {@link KDPoint}
     * at currDim and determine whether it is larger than or equal to the contained {@link KDPoint}'s relevant dimension
     * value. If so, we recurse right, like a regular BST, otherwise left.</p>
     * @param currDim The current dimension to consider
     * @param dims The total number of dimensions that the space considers.
     * @param pIn The {@link KDPoint} to insert into the node.
     * @see #delete(KDPoint, int, int)
     */
    public void insert(KDPoint pIn, int currDim, int dims){
        KDTreeNode dummy = this;

        if (pIn.coords[currDim] >= dummy.value.get(currDim)){
            if (dummy.right == null){
                dummy.right = new KDTreeNode(pIn);
                dummy.right.parent = dummy;
            }
            else{
                dummy = dummy.right;
                if (currDim + 1 == dims){
                    currDim = 0;
                }
                else{
                    currDim += 1;
                }
                dummy.insert(pIn,currDim,dims);
            }
        }
        else{
            if (dummy.left == null){
                dummy.left = new KDTreeNode(pIn);
                dummy.left.parent = dummy;

            }
            else{
                dummy = dummy.left;
                if (currDim + 1 == dims){
                    currDim = 0;
                }
                else{
                    currDim += 1;
                }
                dummy.insert(pIn,currDim,dims);
            }
        }


    }

    /**
     * <p>Deletes the provided {@link KDPoint} from the tree rooted at this. To select which subtree to recurse to,
     * the KD-Tree acts as a Binary Search Tree on currDim; it will examine the value of the provided {@link KDPoint}
     * at currDim and determine whether it is larger than or equal to the contained {@link KDPoint}'s relevant dimension
     * value. If so, we recurse right, like a regular BST, otherwise left. There exist two special cases of deletion,
     * depending on whether we are deleting a {@link KDPoint} from a node who either:</p>
     *
     * <ul>
     *      <li>Has a NON-null subtree as a right child.</li>
     *      <li>Has a NULL subtree as a right child.</li>
     * </ul>
     *
     * <p>You should consult the class slides, your notes, and the textbook about what you need to do in those two
     * special cases.</p>
     * @param currDim The current dimension to consider.
     * @param dims The total number of dimensions that the space considers.
     * @param pIn The {@link KDPoint} to insert into the node.
     * @see #insert(KDPoint, int, int)
     * @return A reference to this after the deletion takes place.
     */
    public KDTreeNode delete(KDPoint pIn, int currDim, int dims){

        KDTreeNode dummy = this;
        Boolean flag = false;
        if (pIn.coords[currDim] == dummy.value.get(currDim)){
            flag = true;
            for (int i=0;i<pIn.coords.length;i++){
                if (dummy.value.get(i) != pIn.coords[i]){
                    flag = false;
                }
            }
        }
        if (flag == true){

            if (dummy.right != null) {
                KDTreeNode minNode = dummy.right;


                LinkedList<KDTreeNode> stack = new LinkedList<>();
                stack.add(dummy.right);
                while (stack.size() > 0){
                    KDTreeNode temp = stack.pollLast();
                    if (minNode.value.get(currDim) > temp.value.get(currDim)){
                        minNode = temp;
                    }

                    if (temp.left != null){
                        stack.add(temp.left);
                    }
                    if (temp.right != null){
                        stack.add(temp.right);
                    }

                }
                dummy.value = minNode.value;
                dummy.kdPoint = minNode.kdPoint;

                dummy.right = dummy.right.delete(minNode.kdPoint,currDim,dims);
                return dummy;
            }
            else if (dummy.left != null){
                KDTreeNode minNode = dummy.left;

                LinkedList<KDTreeNode> stack = new LinkedList<>();
                stack.add(dummy.left);
                while (stack.size() > 0){
                    KDTreeNode temp = stack.pollLast();
                    if (minNode.value.get(currDim) > temp.value.get(currDim)){
                        minNode = temp;
                    }

                    if (temp.left != null){
                        stack.add(temp.left);
                    }
                    if (temp.right != null){
                        stack.add(temp.right);
                    }
                }

                dummy.value = minNode.value;
                dummy.kdPoint = minNode.kdPoint;

                if (dummy.left != null) {
                    dummy.left = dummy.left.delete(minNode.kdPoint, currDim, dims);
                }
                dummy.right =dummy.left;
                dummy.left = null;
                return dummy;
            }
            else{
                if (dummy.parent == null){
                    return null;
                }
                if (dummy.parent.left == dummy){
                    dummy.parent.left = null;
                    return null;
                }
                else{
                    dummy.parent.right = null;
                    return null;
                }
            }
        }

        if (pIn.coords[currDim] >= dummy.value.get(currDim)){
            dummy = dummy.right;
            if (currDim + 1 == dims){
                currDim = 0;
            }
            else{
                currDim += 1;
            }
            if (dummy != null) {
                dummy = dummy.delete(pIn, currDim, dims);
            }
            return dummy;
        }
        else{
            dummy = dummy.left;
            if (currDim + 1 == dims){
                currDim = 0;
            }
            else{
                currDim += 1;
            }
            if (dummy != null) {
                dummy = dummy.delete(pIn, currDim, dims);
            }
            return dummy;
        }


    }

    /**
     * Searches the subtree rooted at the current node for the provided {@link KDPoint}.
     * @param pIn The {@link KDPoint} to search for.
     * @param currDim The current dimension considered.
     * @param dims The total number of dimensions considered.
     * @return true iff pIn was found in the subtree rooted at this, false otherwise.
     */
    public  boolean search(KDPoint pIn, int currDim, int dims){
        KDTreeNode dummy = this;
        Boolean flag = false;
        if (pIn.coords[currDim] == dummy.value.get(currDim)){
            flag = true;
            for (int i=0;i<pIn.coords.length;i++){
                if (dummy.value.get(i) != pIn.coords[i]){
                    flag = false;
                }
            }
        }
        if (flag == true){
            return true;
        }

        if (pIn.coords[currDim] >= dummy.value.get(currDim)){
            if (dummy.right == null){
                return false;
            }
            else{
                dummy = dummy.right;
                if (currDim + 1 == dims){
                    currDim = 0;
                }
                else{
                    currDim += 1;
                }
                return dummy.search(pIn,currDim,dims);
            }
        }
        else{
            if (dummy.left == null){
                return false;
            }
            else{
                dummy = dummy.left;
                if (currDim + 1 == dims){
                    currDim = 0;
                }
                else{
                    currDim += 1;
                }
                return dummy.search(pIn,currDim,dims);
            }
        }


    }

    /**
     * <p>Executes a range query in the given {@link KDTreeNode}. Given an &quot;anchor&quot; {@link KDPoint},
     * all {@link KDPoint}s that have a {@link KDPoint#distanceSquared(KDPoint) distance} of <b>at most</b> range
     * <b>INCLUSIVE</b> from the anchor point <b>except</b> for the anchor itself should be inserted into the {@link Collection}
     * that is passed.</p>
     *
     * <p>Remember: range queries behave <em>greedily</em> as we go down (approaching the anchor as &quot;fast&quot;
     * as our currDim allows and <em>prune subtrees</em> that we <b>don't</b> have to visit as we backtrack. Consult
     * all of our resources if you need a reminder of how these should work.</p>
     * @param anchor The centroid of the hypersphere that the range query implicitly creates.
     * @param results A {@link Collection} that accumulates all the {@link KDPoint}s which satisfy the range query.
     * @param currDim The current dimension examined by the {@link KDTreeNode}.
     * @param dims The total number of dimensions of our {@link KDPoint}s.
     * @param range The <b>INCLUSIVE</b> range from the &quot;anchor&quot; {@link KDPoint}, within which all the
     *              {@link KDPoint}s that satisfy our query will fall. The distance metric used} is defined by
     *              {@link KDPoint#distanceSquared(KDPoint)}.
     */
    public void range(KDPoint anchor, Collection<KDPoint> results,
                      double range, int currDim , int dims){
        KDTreeNode dummy = this;
        if (dummy == null){
            return;
        }
/*        System.out.println("           "+dummy.kdPoint.distanceSquared(anchor));
        System.out.println("              "+range);*/
        if (dummy.kdPoint.distanceSquared(anchor) <= Math.pow(range + 0.000001,2)) {
            results.add(dummy.kdPoint);
        }
        if (anchor.coords[currDim] >= dummy.kdPoint.coords[currDim]) {
            if (currDim + 1 ==dims){
                currDim = 0;
            }
            else{
                currDim += 1;
            }
            if (dummy.right != null) {
                dummy.right.range(anchor, results, range, currDim, dims);
            }
            if (Math.abs(  Math.pow(anchor.coords[currDim] - dummy.kdPoint.coords[currDim],2)   )<= range){
                if (dummy.left != null) {
                    dummy.left.range(anchor,results,range,currDim,dims);
                }
            }
        }
        else{
            if (currDim + 1 ==dims){
                currDim = 0;
            }
            else{
                currDim += 1;
            }
            if (dummy.left != null) {
                dummy.left.range(anchor, results, range, currDim, dims);
            }
            if (Math.abs(  Math.pow(anchor.coords[currDim] - dummy.kdPoint.coords[currDim],2) )<= range){
                if (dummy.right != null) {
                    dummy.right.range(anchor,results,range,currDim,dims);
                }
            }

        }


    }


    /**
     * <p>Executes a nearest neighbor query, which returns the nearest neighbor, in terms of
     * {@link KDPoint#distanceSquared(KDPoint)}, from the &quot;anchor&quot; point.</p>
     *
     * <p>Recall that, in the descending phase, a NN query behaves <em>greedily</em>, approaching our
     * &quot;anchor&quot; point as fast as currDim allows. While doing so, it implicitly
     * <b>bounds</b> the acceptable solutions under the current <b>best solution</b>, which is passed as
     * an argument. This approach is known in Computer Science as &quot;branch-and-bound&quot; and it helps us solve an
     * otherwise exponential complexity problem (nearest neighbors) efficiently. Remember that when we want to determine
     * if we need to recurse to a different subtree, it is <b>necessary</b> to compare the distance reported by
     * {@link KDPoint#distanceSquared(KDPoint)} and coordinate differences! Those are comparable with each other because they
     * are the same data type ({@link Double}).</p>
     *
     * @return An object of type {@link NNData}, which exposes the pair (distance_of_NN_from_anchor, NN),
     * where NN is the nearest {@link KDPoint} to the anchor {@link KDPoint} that we found.
     *
     * @param anchor The &quot;anchor&quot; {@link KDPoint}of the nearest neighbor query.
     * @param currDim The current dimension considered.
     * @param dims The total number of dimensions considered.
     * @param n An object of type {@link NNData}, which will define a nearest neighbor as a pair (distance_of_NN_from_anchor, NN),
     *      * where NN is the nearest neighbor found.
     *
     * @see NNData
     * @see #kNearestNeighbors(int, KDPoint, BoundedPriorityQueue, int, int)
     */
    public  NNData<KDPoint> nearestHelper(KDPoint anchor, int currDim,
                                            NNData<KDPoint> n, int dims, KDTreeNode node, double range){
        if (node == null){
            return n;
        }

        double distance = Integer.MAX_VALUE;
        for (int i=0;i<dims;i++){
            distance += Math.pow(  (anchor.coords[i] - node.kdPoint.coords[i])  ,2);
        }
        if (distance <= range) {
            n = new NNData<KDPoint>(node.kdPoint, distance);
        }


        KDTreeNode dummy = node;

        if (anchor.coords[currDim] >= dummy.kdPoint.coords[currDim]) {
            if (currDim + 1 ==dims){
                currDim = 0;
            }
            else{
                currDim += 1;
            }
            if (dummy.right != null) {
                n = nearestHelper(anchor,currDim,n,dims,dummy.right,distance);
            }
            if (Math.abs(  Math.pow(anchor.coords[currDim] - dummy.kdPoint.coords[currDim],2)   )<= range){
                if (dummy.left != null) {
                    n = nearestHelper(anchor,currDim,n,dims,dummy.left,distance);
                }
            }
        }
        else{
            if (currDim + 1 ==dims){
                currDim = 0;
            }
            else{
                currDim += 1;
            }
            if (dummy.left != null) {
                n = nearestHelper(anchor,currDim,n,dims,dummy.left,distance);
            }
            if (Math.abs(  Math.pow(anchor.coords[currDim] - dummy.kdPoint.coords[currDim],2) )<= range){
                if (dummy.right != null) {
                    n = nearestHelper(anchor,currDim,n,dims,dummy.right,distance);
                }
            }

        }

        return n;
    }


    public  NNData<KDPoint> nearestNeighbor(KDPoint anchor, int currDim,
                                            NNData<KDPoint> n, int dims){
        return nearestHelper(anchor,currDim,n,dims,this,Integer.MAX_VALUE);
    }

    /**
     * <p>Executes a nearest neighbor query, which returns the k nearest neighbors, in terms of
     * {@link KDPoint#distanceSquared(KDPoint)}, from the &quot;anchor&quot; point.</p>
     *
     * <p>Recall that, in the descending phase, a NN query behaves <em>greedily</em>, approaching our
     * &quot;anchor&quot; point as fast as currDim allows. While doing so, it implicitly
     * <b>bounds</b> the acceptable solutions under the current <b>worst solution</b>, which is maintained as the
     * last element of the provided {@link BoundedPriorityQueue}. This is another instance of &quot;branch-and-bound&quot;
     * Remember that when we want to determine if we need to recurse to a different subtree, it is <b>necessary</b>
     * to compare the distance reported by* {@link KDPoint#distanceSquared(KDPoint)} and coordinate differences!
     * Those are comparable with each other because they are the same data type ({@link Double}).</p>
     *
     * <p>The main difference of the implementation of this method and the implementation of
     * {@link #nearestNeighbor(KDPoint, int, NNData, int)} is the necessity of using the class
     * {@link BoundedPriorityQueue} effectively. Consult your various resources
     * to understand how you should be using this class.</p>
     *
     * @param k The total number of neighbors to retrieve. It is better if this quantity is an odd number, to
     *          avoid ties in Binary Classification tasks.
     * @param anchor The &quot;anchor&quot; {@link KDPoint} of the nearest neighbor query.
     * @param currDim The current dimension considered.
     * @param dims The total number of dimensions considered.
     * @param queue A {@link BoundedPriorityQueue} that will maintain at most k nearest neighbors of
     *              the anchor point at all times, sorted by distance to the point.
     *
     * @see BoundedPriorityQueue
     */
    public void kNearestHelper(int k, KDPoint anchor, BoundedPriorityQueue<KDPoint> queue, int currDim, int dims, KDTreeNode node){

        if (node == null){
            return;
        }
        double priority = node.kdPoint.distanceSquared(anchor);
        queue.enqueue(node.kdPoint,priority);

        if (anchor.coords[currDim] >= node.kdPoint.coords[currDim]) {
            if (currDim + 1 ==dims){
                currDim = 0;
            }
            else{
                currDim += 1;
            }
            if (node.right != null) {
                kNearestHelper(k,anchor,queue,currDim,dims,node.right);
            }
            if (Math.abs(  Math.pow(anchor.coords[currDim] - node.kdPoint.coords[currDim],2)   )<= queue.last().distanceSquared(anchor)  ){
                if (node.left != null) {
                    kNearestHelper(k,anchor,queue,currDim,dims,node.left);
                }
            }
        }
        else{
            if (currDim + 1 ==dims){
                currDim = 0;
            }
            else{
                currDim += 1;
            }
            if (node.left != null) {
                kNearestHelper(k,anchor,queue,currDim,dims,node.left);
            }
            if (Math.abs(  Math.pow(anchor.coords[currDim] - node.kdPoint.coords[currDim],2) )<= queue.last().distanceSquared(anchor)  ){
                if (node.right != null) {
                    kNearestHelper(k,anchor,queue,currDim,dims,node.right);
                }
            }

        }

    }

    public void kNearestNeighbors(int k, KDPoint anchor, BoundedPriorityQueue<KDPoint> queue, int currDim, int dims){
        kNearestHelper(k,anchor,queue,currDim,dims,this);
    }

    /**
     * +
     * Returns the height of the subtree rooted at the current node. Recall our definition of height for binary trees:
     * <ol>
     *     <li>A null tree has a height of -1.</li>
     *     <li>A non-null tree has a height equal to max(height(left_subtree), height(right_subtree)) + 1 </li>
     * </ol>
     * @return the height of the subtree rooted at the current node.
     */
    public int height(){
       if (this == null){
           return -1;
       }
       int leftHeight = -1;
       int rightHeight = -1;
       if (this.left != null){
           leftHeight = this.left.height();
       }
       if (this.right != null) {
           rightHeight = this.right.height();
       }
       return Math.max(leftHeight,rightHeight) + 1;
    }


    /**
     * A simple getter for the {@link KDPoint} held by the current node. Remember: {@link KDPoint}s ARE
     * IMMUTABLE, SO WE NEED TO DO DEEP COPIES!!!
     * @return The {@link KDPoint} held inside this.
     */
    public KDPoint getPoint(){
        return this.kdPoint;
    }
}