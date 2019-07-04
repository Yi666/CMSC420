package projects.spatial.nodes;

import projects.spatial.kdpoint.KDPoint;
import projects.spatial.trees.PRQuadTree;
import projects.spatial.nodes.PRQuadGrayNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/** <p>A {@link PRQuadBlackNode} is a &quot;black&quot; {@link PRQuadNode}. It maintains the following
 * invariants: </p>
 * <ul>
 *  <li>It does <b>not</b> have children.</li>
 *  <li><b>Once created</b>, it will contain at least one {@link KDPoint}. </li>
 * </ul>
 *
 * <p><b>YOU ***** MUST ***** IMPLEMENT THIS CLASS!</b></p>
 *
 * @author Yi Liu
 */
public class PRQuadBlackNode extends PRQuadNode {

    private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

    /* ****************************************************************************** */
    /* ***** YOU SHOULD LEAVE THE FOLLOWING PUBLICLY AVAILABLE CONSTANT AS IS. ****** */
    /* ****************************************************************************** */

    /**
     * The default bucket size for all of our black nodes will be 1, and this is something
     * that the interface also communicates to consumers.
     */
    public static final int DEFAULT_BUCKETSIZE = 1;


    public ArrayList<KDPoint> values;

    public double xMin;        //the quadrant area of this black node
    public double xMax;
    public double yMin;
    public double yMax;

    //private PRQuadGrayNode parent;



    /**
     * Creates a {@link PRQuadBlackNode} with the provided parameters.
     * @param centroid The {@link KDPoint} which will act as the centroid of the quadrant spanned by the current {@link PRQuadBlackNode}.
     * @param k An integer to which 2 is raised to define the side length of the quadrant spanned by the current {@link PRQuadBlackNode}.
     *          See {@link PRQuadTree#PRQuadTree(int, int)} for a full explanation of how k works.
     * @param bucketingParam The bucketing parameter provided to us {@link PRQuadTree}.
     * @see PRQuadTree#PRQuadTree(int, int)
     * @see #PRQuadBlackNode(KDPoint, int, int, KDPoint)
     */
    public PRQuadBlackNode(KDPoint centroid, int k, int bucketingParam){
        super(centroid, k, bucketingParam); // Call to the super class' protected constructor to properly initialize the object!

        this.values = new ArrayList<>();
        this.xMin = -Math.pow(2,k-1);
        this.xMax = Math.pow(2,k-1);
        this.yMin = -Math.pow(2,k-1);
        this.yMax = Math.pow(2,k-1);

        //this.parent = null;
    }

    /**
     * Creates a {@link PRQuadBlackNode} with the provided parameters.
     * @param centroid The centroid of the quadrant spanned by the current {@link PRQuadBlackNode}.
     * @param k The exponent to which 2 is raised in order to define the side of the current quadrant. Refer to {@link PRQuadTree#PRQuadTree(int, int)} for
     *          a thorough explanation of this parameter.
     * @param bucketingParam The bucketing parameter of the {@link PRQuadBlackNode}, passed to us by the {@link PRQuadTree} or {@link PRQuadGrayNode} during
     *                       object construction.
     * @param p The {@link KDPoint} with which we want to initialize this.
     * @see #DEFAULT_BUCKETSIZE
     * @see PRQuadTree#PRQuadTree(int, int)
     * @see #PRQuadBlackNode(KDPoint, int, int)
     */
    public PRQuadBlackNode(KDPoint centroid, int k, int bucketingParam, KDPoint p){
        this(centroid, k, bucketingParam); // Call to the current class' other constructor, which takes care of the base class' initialization itself.

        this.values = new ArrayList<>();
        this.values.add(p);
        this.xMin = -Math.pow(2,k-1);
        this.xMax = Math.pow(2,k-1);
        this.yMin = -Math.pow(2,k-1);
        this.yMax = Math.pow(2,k-1);

        //this.parent = null;
    }


    /**
     * <p>Inserting a {@link KDPoint} into a {@link PRQuadBlackNode} can have one of two outcomes:</p>
     *
     * <ol>
     *     <li>If, after the insertion, the node's capacity is still <b>SMALLER THAN OR EQUAL TO </b> the bucketing parameter,
     *     we should simply store the {@link KDPoint} internally.</li>
     *
     *     <li>If, after the insertion, the node's capacity <b>SURPASSES</b> the bucketing parameter, we will have to
     *     <b>SPLIT</b> the current {@link PRQuadBlackNode} into a {@link PRQuadGrayNode} which will recursively insert
     *     all the available{@link KDPoint}s. This pprocess will continue until we reach a {@link PRQuadGrayNode}
     *     which successfully separates all the {@link KDPoint}s of the quadrant it represents. Programmatically speaking,
     *     this means that the method will polymorphically call itself, splitting black nodes into gray nodes as long as
     *     is required for there to be a set of 4 quadrants that separate the points between them. This is one of the major
     *     bottlenecks in PR-QuadTrees; the presence of a pair of {@link KDPoint}s with a very small {@link
     *     KDPoint#distanceSquared(KDPoint)}  distance} between them can negatively impact search in certain subplanes, because
     *     the subtrees through which those subplanes will be modeled will be &quot;unnecessarily&quot; tall.</li>
     * </ol>
     *
     * @param p A {@link KDPoint} to insert into the subtree rooted at the current node.
     * @param k The side length of the quadrant spanned by the <b>current</b> {@link PRQuadGrayNode}. It will need to be updated
     *           per recursive call to help guide the input {@link KDPoint} to the appropriate subtree.
     * @return The subtree rooted at the current node, potentially adjusted after insertion.
     */
    @Override
    public PRQuadNode insert(KDPoint p, int k) {
        if (values.size() < bucketingParam){
            values.add(p);
            return this;
        }
        else{
            PRQuadGrayNode newNode = new PRQuadGrayNode(centroid,k,bucketingParam);
            newNode.xMin = xMin;
            newNode.xMax = xMax;
            newNode.yMin = yMin;
            newNode.yMax = yMax;
            double xMean = (xMin+xMax)/2;
            double yMean = (yMin+yMax)/2;



            ArrayList<KDPoint> nodeToReinsert = values;
            newNode.values = values;
            nodeToReinsert.add(p);              //all nodes needed for reinsert
            for (int i=0;i<nodeToReinsert.size();i++){
                if (nodeToReinsert.get(i).coords[0] < xMean && nodeToReinsert.get(i).coords[1] < yMean){
                    if (newNode.third == null){
                        newNode.third = new PRQuadBlackNode(centroid,k,bucketingParam,nodeToReinsert.get(i));
                        ((PRQuadBlackNode) newNode.third).xMax = xMean;
                        ((PRQuadBlackNode) newNode.third).xMin = xMin;
                        ((PRQuadBlackNode) newNode.third).yMax = yMean;
                        ((PRQuadBlackNode) newNode.third).yMin = yMin;
                    }
                    else{
                        newNode.third = newNode.third.insert(nodeToReinsert.get(i),k);
                    }
                }
                else if (nodeToReinsert.get(i).coords[0] >= xMean && nodeToReinsert.get(i).coords[1] < yMean){
                    if (newNode.fourth == null){
                        newNode.fourth = new PRQuadBlackNode(centroid,k,bucketingParam,nodeToReinsert.get(i));
                        ((PRQuadBlackNode) newNode.fourth).xMax = xMax;
                        ((PRQuadBlackNode) newNode.fourth).xMin = xMean;
                        ((PRQuadBlackNode) newNode.fourth).yMax = yMean;
                        ((PRQuadBlackNode) newNode.fourth).yMin = yMin;
                    }
                    else{
                        newNode.fourth = newNode.fourth.insert(nodeToReinsert.get(i),k);
                    }
                }
                else if (nodeToReinsert.get(i).coords[0] < xMean && nodeToReinsert.get(i).coords[1] >= yMean){
                    if (newNode.first == null){
                        newNode.first = new PRQuadBlackNode(centroid,k,bucketingParam,nodeToReinsert.get(i));
                        ((PRQuadBlackNode) newNode.first).xMax = xMean;
                        ((PRQuadBlackNode) newNode.first).xMin = xMin;
                        ((PRQuadBlackNode) newNode.first).yMax = yMax;
                        ((PRQuadBlackNode) newNode.first).yMin = yMean;
                    }
                    else{
                        newNode.first = newNode.first.insert(nodeToReinsert.get(i),k);
                    }
                }
                else{
                    if (newNode.second == null){
                        newNode.second = new PRQuadBlackNode(centroid,k,bucketingParam,nodeToReinsert.get(i));
                        ((PRQuadBlackNode) newNode.second).xMax = xMax;
                        ((PRQuadBlackNode) newNode.second).xMin = xMean;
                        ((PRQuadBlackNode) newNode.second).yMax = yMax;
                        ((PRQuadBlackNode) newNode.second).yMin = yMean;
                    }
                    else{
                        newNode.second = newNode.second.insert(nodeToReinsert.get(i),k);
                    }
                }
            }       //finish insert
            return newNode;
        }


    }


    /**
     * <p><b>Successfully</b> deleting a {@link KDPoint} from a {@link PRQuadBlackNode} always decrements its capacity by 1. If, after
     * deletion, the capacity is at least 1, then no further changes need to be made to the node. Otherwise, it can
     * be scrapped and turned into a white node.</p>
     *
     * <p>If the provided {@link KDPoint} is <b>not</b> contained by this, no changes should be made to the internal
     * structure of this, which should be returned as is.</p>
     * @param p The {@link KDPoint} to delete from this.
     * @return Either this or null, depending on whether the node underflows.
     */
    @Override
    public PRQuadNode delete(KDPoint p) {
        if (!this.values.contains(p)){
            return this;
        }
        else{
            this.values.remove(p);
            if (this.values.size() == 0){
                return null;
            }
            else{
                return this;
            }
        }
    }

    @Override
    public boolean search(KDPoint p){
        for (int i=0;i<values.size();i++){
            if (values.get(i).equals(p)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int height(){
        return 0;
    }

    @Override
    public int count(){
        return 1;
    }

    /** Returns all the {@link KDPoint}s contained by the {@link PRQuadBlackNode}. <b>INVARIANT</b>: the returned
     * {@link Collection}'s size can only be between 1 and bucket-size inclusive.
     *
     * @return A {@link Collection} that contains all the {@link KDPoint}s that are contained by the node. It is
     * guaranteed, by the invariants, that the {@link Collection} will not be empty, and it will also <b>not</b> be
     * a null reference.
     */
    public Collection<KDPoint> getPoints(){
        Collection<KDPoint> result = new Collection<KDPoint>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<KDPoint> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(KDPoint kdPoint) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends KDPoint> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };
        for (int i=0;i<values.size();i++){
            result.add(values.get(i));
        }
        return result;
    }
}