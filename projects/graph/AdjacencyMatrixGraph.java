package projects.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>{@link AdjacencyMatrixGraph} is a {@link Graph} implemented as an <b>adjacency matrix</b>. An adjacency matrix
 * is a V x V matrix where M(i, j) is the weight of the edge from i to j. If there is no edge between i and j,
 * the weight should be zero. </p>
 *
 * <p>Adjacency matrices answer {@link #edgeBetween(int, int)} in O(1) time. Insertion and deletion of edges, as well
 * as retrieval of the weight of a given edge  are all O(1) operations as well. Retrieval of all neighbors of a given node
 * happens in O(V) time. </p>
 *
 * <p>The main drawbacks of adjacency matrices are: </p>
 *  <ol>
 *      <li>They occupy O(V^2) <b>contiguous</b> memory space, which for sparse graphs can be a significant memory footprint. </li>
 *      <li>addNode() runs in O(V^2) time, since new array storage needs to be allocated for the extra row and column,
 *      and the old data need be copied to the new array. </li>
 *  </ol>
 *
 * @author Yi Liu
 * @see Graph
 * @see SparseAdjacencyMatrixGraph
 * @see AdjacencyListGraph
 */
public class AdjacencyMatrixGraph extends Graph {

    /* ****************************************************** */
    /* THE FOLLOWING DATA FIELD IS THE INNER REPRESENTATION */
    /* OF YOUR GRAPH. YOU SHOULD NOT CHANGE THIS DATA FIELD! */
    /* ******************************************************  */

    private int[][] matrix;

    /* ***************************************************** */
    /* PLACE ANY EXTRA PRIVATE DATA MEMBERS OR METHODS HERE: */
    /* ***************************************************** */

    /* ************************************************** */
    /* IMPLEMENT THE FOLLOWING PUBLIC METHODS. MAKE SURE  */
    /* YOU ERASE THE LINES THAT THROW EXCEPTIONS.         */
    /* ************************************************** */

    /**
     * A default (no-arg) constructor for {@link AdjacencyMatrixGraph} <b>should</b> exist,
     * even if you don't do anything with it.
     */
    public AdjacencyMatrixGraph(){
        matrix = new int[0][0];

    }

    @Override
    public void addNode() {
        if (matrix.length == 0) {
            matrix = new int[1][1];
        }
        else{
            int [][] new_matrix = new int[matrix.length + 1][matrix.length + 1];
            for (int i = 0;i<matrix.length;i++) {
                for (int j = 0;j<matrix.length;j++)
                new_matrix[i][j] = matrix[i][j];
            }
            matrix = new_matrix;

        }
        // System.out.println(matrix.length);

    }

    @Override
    public void addEdge(int source, int dest, int weight) {
        if (weight<0 || weight > INFINITY) {
            throw Negative_edge;
        }
        else if(weight == 0){
            ;
        }
        else {
            // I throw an AssertionError if either node isn't within parameters. Behavior open to implementation according to docs.
            if (source < matrix.length && dest < matrix.length) {
                matrix[source][dest] = weight;
            }
        }
    }


    @Override
    public void deleteEdge(int source, int dest) {
        if (source < matrix.length && dest < matrix.length) {
            matrix[source][dest] = 0;
        }
    }

    @Override
    public boolean edgeBetween(int source, int dest) {
        if (source < matrix.length && dest < matrix.length && matrix[source][dest] != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int getEdgeWeight(int source, int dest) {
        if (source < matrix.length && dest < matrix.length) {
            return matrix[source][dest];
        }
        else {
            return 0;
        }
    }

    @Override
    public Set<Integer> getNeighbors(int node) {
        Set<Integer> a = new HashSet<Integer>();
        if (node < matrix.length) {
            for (int i=0;i<matrix.length;i++) {
                if (matrix[node][i] !=0) {
                    a.add(i);
                }
            }
            return a;
        }
        else{
            return null;
        }
    }

    @Override
    public int getNumNodes() {
        // System.out.println("num of Nodes" + matrix.length);
        return matrix.length;
    }

    @Override
    public int getNumEdges() {
        int count = 0;
        for (int i = 0;i<matrix.length;i++) {
            for (int j = 0;j<matrix.length;j++) {
                if (matrix[i][j] != 0) {
                    count++;
                }
            }
        }
        // System.out.println("num of Edges" + count);
        return count;
    }

    @Override
    public void clear() {
        matrix = new int [0][0];
    }


    /* Methods specific to this class follow. */

    /**
     * Returns a sparsified representation of the adjacency matrix, i.e a linked list of its non-null elements (non-zero,
     * in this case) and their coordinates. The matrix should be scanned in <b>row-major order</b> to populate
     * the elements of the list (<b>and we test for proper element insertion order!</b>).
     *
     * You are <b>not</b> allowed to implement this method by using other transformation methods. For example, you
     * <b>cannot</b> implement it with the line of code toAdjacencyListGraph().toSparseAdjacencyMatrixGraph().
     *
     * @return A {@link SparseAdjacencyMatrixGraph} instance.
     */
    public SparseAdjacencyMatrixGraph toSparseAdjacencyMatrixGraph(){
        SparseAdjacencyMatrixGraph graph = new SparseAdjacencyMatrixGraph();
        if (matrix.length>0) {
            for (int i=0;i<matrix.length;i++){
                graph.addNode();
            }
            for (int i=0;i<matrix.length;i++){
                for (int j=0;j<matrix.length;j++){
                    if (matrix[i][j] !=0){
                        graph.addEdge(i,j,matrix[i][j]);
                    }
                }
            }
        }
        return graph;
    }

    /**
     * Returns a representation of the {@link Graph} as an {@link AdjacencyListGraph}. Remember that an {@link AdjacencyListGraph}
     * is implemented as an array of linked lists, where A(i) is a linked list containing the neighbors of node i.  Remember that an
     * {@link AdjacencyListGraph} is implemented as an array of linked lists, where A(i) is a linked list containing the neighbors of node i.
     *
     * You are <b>not</b> allowed to implement this method by using other transformation methods. For example, you
     *    <b>cannot</b> implement it with the line of code toSparseAdjacencyMatrixGraph().toAdjacencyListGraph().
     *
     * @return  An {@link AdjacencyListGraph} instance.
     */
    public AdjacencyListGraph toAdjacencyListGraph(){
        AdjacencyListGraph graph = new AdjacencyListGraph();
        if (matrix.length>0) {
            for (int i=0;i<matrix.length;i++){
                graph.addNode();

            }
            for (int i=0;i<matrix.length;i++){
                for (int j=0;j<matrix.length;j++){
                    if (matrix[i][j] >0){
                        graph.addEdge(i,j,matrix[i][j]);
                    }
                }
            }
        }
        return graph;
    }
}