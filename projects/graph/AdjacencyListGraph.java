package projects.graph;

import projects.graph.utils.Neighbor;
import projects.graph.utils.NeighborList;

import java.util.HashSet;
import java.util.Set;
/**
 * <p>{@link AdjacencyListGraph} is a {@link Graph} implemented as an adjacency list, i.e a one-dimensional array of linked lists,
 * where A(i) is a linked list containing the neighbors of node i and the corresponding edges' weights. <b>The neighbors of a given node are defined as the nodes it  points to</b> (if any). </p>
 *
 * <p>Other implementations besides linked lists are possible (e.g BSTs over the weight of the edge), yet for this project we
 * will keep it simple and stick to that basic implementation. One of its advantages is that, because the lists do not need
 * to be sorted in any way, the insertion of a new edge is a O(1) operation (find the list corresponding to the source node in O(1)
 * and add the new list node up front.</p>
 *
 * @author Yi Liu
 *
 * @see Graph
 * @see AdjacencyMatrixGraph
 * @see SparseAdjacencyMatrixGraph
 * @see NeighborList
 */
public class AdjacencyListGraph extends Graph {

    /* *********************************************************** */
    /* THE FOLLOWING DATA FIELD MAKES UP THE INNER REPRESENTATION */
    /* OF YOUR GRAPH. YOU SHOULD NOT CHANGE THIS DATA FIELD!      */
    /* *********************************************************  */

    private NeighborList[] list;

    /* ***************************************************** */
    /* PLACE ANY EXTRA PRIVATE DATA MEMBERS OR METHODS HERE: */
    /* ***************************************************** */

    /* ************************************************** */
    /* IMPLEMENT THE FOLLOWING PUBLIC METHODS. MAKE SURE  */
    /* YOU ERASE THE LINES THAT THROW EXCEPTIONS.         */
    /* ************************************************** */

    /**
     * A default (no-arg) constructor for {@link AdjacencyListGraph} <b>should</b> exist,
     * even if you don't do anything with it.
     */
    public AdjacencyListGraph(){
        list = new NeighborList[0];

    }

    @Override
    public void addNode() {
        if (list.length == 0) {
            list = new NeighborList[1];

        }
        else {
            NeighborList[] new_list = new NeighborList[list.length + 1];
            for (int i = 0;i<list.length;i++) {
                new_list[i] = list[i];
            }
            list = new_list;
        }

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
            if (source < list.length && dest < list.length) {
                if (list[source] == null) {
                    list[source] = new NeighborList(dest, weight);
                } else {
                    list[source].addBack(dest, weight);
                }
            }
        }



    }


    @Override
    public void deleteEdge(int source, int dest) {
        if (source < list.length && dest < list.length && list[source]!=null) {
            list[source].remove(dest);
        }



    }

    @Override
    public boolean edgeBetween(int source, int dest) {
        if (source < list.length && dest < list.length && list[source]!=null) {
            return list[source].containsNeighbor(dest);
        }
        else{
            return false;
        }


    }

    @Override
    public int getEdgeWeight(int source, int dest) {
        if (source < list.length && dest < list.length && list[source]!=null) {
            return list[source].getWeight(dest);
        }
        else{
            return 0;
        }
    }

    @Override
    public Set<Integer> getNeighbors(int node) {
        Set<Integer> a = new HashSet<Integer>();
        if (node < list.length && list[node]!=null) {
            for (int i=0;i<list.length;i++) {
                if (list[node].containsNeighbor(i)) {
                    a.add(i);
                }

            }

        }


        return a;



    }

    @Override
    public int getNumNodes() {
        return list.length;
    }

    @Override
    public int getNumEdges() {
        int count = 0;

        for (int i = 0;i<list.length;i++) {
            for (int j = 0;j<list.length;j++) {
                if (list[i]!=null) {
                    if (list[i].containsNeighbor(j)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public void clear() {
        list = new NeighborList[0];
    }

    /* Methods specific to this class follow. */

    /**
     * Transforms this into an instance of {@link AdjacencyMatrixGraph}. This is an O(E) operation.
     *
     * You are <b>not</b> allowed to implement this method by using other transformation methods. For example, you
     *   <b>cannot</b> implement it with the line of code toSparseAdjacencyMatrixGraph().toAdjacencyMatrixGraph().
     *
     * @return An instance of {@link AdjacencyMatrixGraph}.
     */
    public AdjacencyMatrixGraph toAdjacencyMatrixGraph(){
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph();
        if (list.length>0) {
            for(int i=0;i<list.length;i++){
                graph.addNode();
            }
            for(int i=0;i<list.length;i++){
                for(int j=0;j<list.length;j++){
                    if (list[i]!=null) {
                        if (list[i].containsNeighbor(j)) {
                            graph.addEdge(i, j, list[i].getWeight(j));
                        }
                    }
                }
            }
        }
        return graph;


    }

    /**
     * Transforms this into an instance of {@link SparseAdjacencyMatrixGraph}. This is an O(E) operation.
     *
     * You are <b>not</b> allowed to implement this method by using other transformation methods. For example, you
     * <b>cannot</b> implement it with the line of code toAdjacencyMatrixGraph().toSparseAdjacencyMatrixGraph().
     *
     * @return An instance of {@link SparseAdjacencyMatrixGraph}.
     */
    public SparseAdjacencyMatrixGraph toSparseAdjacencyMatrixGraph(){
        SparseAdjacencyMatrixGraph graph = new SparseAdjacencyMatrixGraph();
        if (list.length>0) {
            for(int i=0;i<list.length;i++){
                graph.addNode();
            }
            for(int i=0;i<list.length;i++){
                for(int j=0;j<list.length;j++){
                    if (list[i]!=null) {
                        if (list[i].containsNeighbor(j)) {

                            graph.addEdge(i, j, list[i].getWeight(j));
                        }
                    }
                }
            }
        }
        return graph;
    }
}