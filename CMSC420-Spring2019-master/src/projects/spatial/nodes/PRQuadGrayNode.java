package projects.spatial.nodes;

import projects.spatial.kdpoint.KDPoint;
import projects.spatial.trees.PRQuadTree;
import projects.spatial.nodes.PRQuadGrayNode;

import java.util.ArrayList;

/** <p>A {@link PRQuadGrayNode} is a gray (&quot;mixed&quot;) {@link PRQuadNode}. It
 * maintains the following invariants: </p>
 * <ul>
 *      <li>Its children pointer buffer is non-null and has a length of 4.</li>
 *      <li>If there is at least one black node child, the total number of {@link KDPoint}s stored
 *      by <b>all</b> of the children is greater than the bucketing parameter (because if it is equal to it
 *      or smaller, we can prune the node.</li>
 * </ul>
 *
 * <p><b>YOU ***** MUST ***** IMPLEMENT THIS CLASS!</b></p>
 *
 *  @author Yi Liu
 */
public class PRQuadGrayNode extends PRQuadNode{

    private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");


    public PRQuadNode first;
    public PRQuadNode second;
    public PRQuadNode third;
    public PRQuadNode fourth;

    public double xMin;
    public double xMax;
    public double yMin;
    public double yMax;

    public ArrayList<KDPoint> values;


    /**
     * Creates a {@link PRQuadGrayNode}  with the provided {@link KDPoint} as a centroid;
     * @param centroid A {@link KDPoint} that will act as the centroid of the space spanned by the current
     *                 node.
     * @param k The See {@link PRQuadTree#PRQuadTree(int, int)} for more information on how this parameter works.
     * @param bucketingParam The bucketing parameter fed to this by {@link PRQuadTree}.
     * @see PRQuadTree#PRQuadTree(int, int)
     */
    public PRQuadGrayNode(KDPoint centroid, int k, int bucketingParam){
        super(centroid, k, bucketingParam); // Call to the super class' protected constructor to properly initialize the object!
        this.first = null;
        this.second = null;
        this.third = null;
        this.fourth = null;
        this.xMin = -Math.pow(2,k-1);
        this.xMin = Math.pow(2,k-1);
        this.yMin = -Math.pow(2,k-1);
        this.yMax = Math.pow(2,k-1);
        this.values = new ArrayList<>();
    }


    /**
     * <p>Insertion into a {@link PRQuadGrayNode} consists of navigating to the appropriate child
     * and recursively inserting elements into it. If the child is a white node, memory should be allocated for a
     * {@link PRQuadBlackNode} which will contain the provided {@link KDPoint} If it's a {@link PRQuadBlackNode},
     * refer to {@link PRQuadBlackNode#insert(KDPoint, int)} for details on how the insertion is performed. If it's a {@link PRQuadGrayNode},
     * the current method would be called recursively. Polymorphism will allow for the appropriate insert to be called
     * based on the child object's runtime object.</p>
     * @param p A {@link KDPoint} to insert into the subtree rooted at the current {@link PRQuadGrayNode}.
     * @param k The side length of the quadrant spanned by the <b>current</b> {@link PRQuadGrayNode}. It will need to be updated
     *          per recursive call to help guide the input {@link KDPoint}  to the appropriate subtree.
     * @return The subtree rooted at the current node, potentially adjusted after insertion.
     * @see PRQuadBlackNode#insert(KDPoint, int)
     */
    @Override
    public PRQuadNode insert(KDPoint p, int k) {
        double xMean = (xMax+xMin)/2;
        double yMean = (yMax+yMin)/2;
        if (p.coords[0] < xMean && p.coords[1] < yMean){
            if (third == null){
                third = new PRQuadBlackNode(centroid,k,bucketingParam,p);
                ((PRQuadBlackNode) third).xMax = xMean;
                ((PRQuadBlackNode) third).xMin = xMin;
                ((PRQuadBlackNode) third).yMax = yMean;
                ((PRQuadBlackNode) third).yMin = yMin;
            }
            else{
                third = third.insert(p,k);
            }
        }
        else if (p.coords[0] >= xMean && p.coords[1] < yMean){
            if (fourth == null){
                fourth = new PRQuadBlackNode(centroid,k,bucketingParam,p);
                ((PRQuadBlackNode) fourth).xMax = xMax;
                ((PRQuadBlackNode) fourth).xMin = xMean;
                ((PRQuadBlackNode) fourth).yMax = yMean;
                ((PRQuadBlackNode) fourth).yMin = yMin;
            }
            else{
                fourth = fourth.insert(p,k);
            }
        }
        else if (p.coords[0] < xMean && p.coords[1] >= yMean){
            if (first == null){
                first = new PRQuadBlackNode(centroid,k,bucketingParam,p);
                ((PRQuadBlackNode) first).xMax = xMean;
                ((PRQuadBlackNode) first).xMin = xMin;
                ((PRQuadBlackNode) first).yMax = yMax;
                ((PRQuadBlackNode) first).yMin = yMean;
            }
            else{
                first = first.insert(p,k);
            }
        }
        else{
            if (second == null){
                second = new PRQuadBlackNode(centroid,k,bucketingParam,p);
                ((PRQuadBlackNode) second).xMax = xMax;
                ((PRQuadBlackNode) second).xMin = xMean;
                ((PRQuadBlackNode) second).yMax = yMax;
                ((PRQuadBlackNode) second).yMin = yMean;
            }
            else{
                second = second.insert(p,k);
            }
        }
        this.values.add(p);
        return this;
    }


    /**
     * <p>Deleting a {@link KDPoint} from a {@link PRQuadGrayNode} consists of recursing to the appropriate
     * {@link PRQuadBlackNode} child to find the provided {@link KDPoint}. If no such child exists, the search has
     * <b>necessarily failed</b>; <b>no changes should then be made to the subtree rooted at the current node!</b></p>
     *
     * <p>Polymorphism will allow for the recursive call to be made into the appropriate delete method.
     * Importantly, after the recursive deletion call, it needs to be determined if the current {@link PRQuadGrayNode}
     * needs to be collapsed into a {@link PRQuadBlackNode}. This can only happen if it has no gray children, and one of the
     * following two conditions are satisfied:</p>
     *
     * <ol>
     *     <li>The deletion left it with a single black child. Then, there is no reason to further subdivide the quadrant,
     *     and we can replace this with a {@link PRQuadBlackNode} that contains the {@link KDPoint}s that the single
     *     black child contains.</li>
     *     <li>After the deletion, the <b>total</b> number of {@link KDPoint}s contained by <b>all</b> the black children
     *     is <b>equal to or smaller than</b> the bucketing parameter. We can then similarly replace this with a
     *     {@link PRQuadBlackNode} over the {@link KDPoint}s contained by the black children.</li>
     *  </ol>
     *
     * @param p A {@link KDPoint} to delete from the tree rooted at the current node.
     * @return The subtree rooted at the current node, potentially adjusted after deletion.
     */
    @Override
    public PRQuadNode delete(KDPoint p) {
        double xMean = (xMax+xMin)/2;
        double yMean = (yMax+yMin)/2;

        ArrayList<KDPoint> kdPoints = new ArrayList<>();

        if (p.coords[0] < xMean && p.coords[1] < yMean){
            if (third == null){
                return this;
            }
            else{
                PRQuadNode temp = third;
                third = third.delete(p);
                if (temp != third){
                    this.values.remove(p);
                }


            }
        }
        else if (p.coords[0] >= xMean && p.coords[1] < yMean){
            if (fourth == null){
                return this;
            }
            else{
                PRQuadNode temp = fourth;
                fourth = fourth.delete(p);
                if (temp != fourth){
                    this.values.remove(p);
                }
            }
        }
        else if (p.coords[0] < xMean && p.coords[1] >= yMean){
            if (first == null){
                return this;
            }
            else{
                PRQuadNode temp = first;
                first = first.delete(p);
                if (temp != first){
                    this.values.remove(p);
                }
            }
        }
        else{
            if (second == null){
                return this;
            }
            else{
                PRQuadNode temp = second;
                second = second.delete(p);
                if (temp != second){
                    this.values.remove(p);
                }
            }
        }


        if (this.count() <= bucketingParam){
            PRQuadBlackNode newNode = new PRQuadBlackNode(centroid,k,bucketingParam);
            if (first != null){

                for (int i=0;i<first.values.size();i++){
                    newNode.values.add(first.values.get(i));
                }
            }
            if (second != null){
                for (int i=0;i<second.values.size();i++){
                    newNode.values.add(second.values.get(i));
                }
            }
            if (third != null){
                for (int i=0;i<third.values.size();i++){
                    newNode.values.add(third.values.get(i));
                }
            }
            if (fourth != null){
                for (int i=0;i<fourth.values.size();i++){
                    newNode.values.add(fourth.values.get(i));
                }
            }
            return newNode;
        }
        return this;
    }

    @Override
    public boolean search(KDPoint p){
        double xMean = (xMax+xMin)/2;
        double yMean = (yMax+yMin)/2;
        if (p.coords[0] < xMean && p.coords[1] < yMean){
            if (third == null){
                return false;
            }
            else{
                return third.search(p);
            }
        }
        else if (p.coords[0] >= xMean && p.coords[1] < yMean){
            if (fourth == null){
                return false;
            }
            else{
                return fourth.search(p);
            }
        }
        else if (p.coords[0] < xMean && p.coords[1] >= yMean){
            if (first == null){
                return false;
            }
            else{
                return first.search(p);
            }
        }
        else{
            if (second == null){
                return false;
            }
            else{
                return second.search(p);
            }
        }

    }

    @Override
    public int height(){
        int firstHeight = -1;
        int secondHeight = -1;
        int thirdHeight = -1;
        int fourthHeight = -1;
        if (first != null){
            firstHeight = first.height();
        }
        if (second != null){
            secondHeight = second.height();
        }
        if (third != null){
            thirdHeight = third.height();
        }
        if (fourth != null){
            firstHeight = fourth.height();
        }
        return Math.max(Math.max(firstHeight,secondHeight),Math.max(thirdHeight,fourthHeight))+1;

    }

    @Override
    public int count(){
        int firstCount = 0;
        int secondCount = 0;
        int thirdCount = 0;
        int fourthCount = 0;
        if (first != null){
            firstCount = first.count();
        }
        if (second != null){
            secondCount = second.count();
        }
        if (third != null){
            thirdCount = third.count();
        }
        if (fourth != null){
            fourthCount = fourth.count();
        }
        return firstCount+secondCount+thirdCount+fourthCount;
    }
}