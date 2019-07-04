package projects.spatial.knnutils;

import projects.spatial.kdpoint.KDPoint;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>{@link BoundedPriorityQueue} is an {@link Iterable} priority queue whose number of elements
 * is bounded above. Insertions are such that if the queue's provided capacity is surpassed,
 * its length is not expanded, but rather the maximum priority element is ejected
 * (which could be the element just attempted to be enqueued).</p>
 *
 * <p><b>YOU ***** MUST ***** IMPLEMENT THIS CLASS!</b></p>
 *
 * @author  Yi Liu
 *
 */
public class BoundedPriorityQueue<T> implements PriorityQueue<T>{

	private static RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");
	private static RuntimeException SIZE_WRONG = new RuntimeException("the size should be larger than 0");

	/* *************************************************************************
	 ************** PLACE YOUR PRIVATE METHODS AND FIELDS HERE: ****************
	 ***************************************************************************/

	private Integer allowedSize;
	private ArrayList<PriorityQueueNode> queue;
	private Integer insertionOrder;



	/* ***************************************************************************** */
	/* ******************* PUBLIC (INTERFACE) METHODS ****************************** */
	/* ***************************************************************************** */


	/**
	 * Standard constructor. Creates a {@link BoundedPriorityQueue} of the provided size.
	 * @param size The number of elements that the {@link BoundedPriorityQueue} instance is allowed to store.
	 * @throws RuntimeException if size &lt; 1.
	 */
	public BoundedPriorityQueue(int size){
		if (size < 1){
			throw SIZE_WRONG;
		}

		this.allowedSize = size;
		this.queue = new ArrayList<>(allowedSize);
		this.insertionOrder = 0;			//increment this number every insertion


	}

	/**
	 * <p>Insert element in the Priority Queue, according to its priority.
	 * <b>Lower is better.</b> We allow for <b>non-integer priorities</b> such that the Priority Queue
	 * can be used for orderings where the prioritization is <b>not</b> rounded to integer quantities, such as
	 * Euclidean Distances in KNN queries. </p>
	 *
	 * @param element The element to insert in the queue.
	 * @param priority The priority of the element.
	 *
	 * @see projects.spatial.kdpoint.KDPoint#distanceSquared(KDPoint)
	 */
	public void enqueue(T element, double priority) {
		this.insertionOrder += 1;
		PriorityQueueNode node = new PriorityQueueNode(element,priority,insertionOrder);


		Boolean added = false;
		if (this.queue.size() == 0){
			this.queue.add(0, node);
			added = true;
		}
		for (int i=0;i<this.queue.size();i++){

			if(!added && this.queue.get(i).compareTo(node) > 0){
				this.queue.add(i,node);
				added = true;
			}
		}

		if (!added){
			this.queue.add(node);
		}

		if (this.allowedSize < this.queue.size()){
			this.queue.remove(this.queue.size()-1);
		}
	}

	/**
	 * Return the <b>minimum priority element</b> in the queue, <b>simultaneously removing it</b> from the structure.
	 * @return The minimum priority element in the queue, or null if the queue is empty.
	 */
	public T dequeue() {
		if (this.queue.size() == 0){
			return null;
		}
		PriorityQueueNode node = this.queue.get(0);
		this.queue.remove(0);
		return (T) node.getData();
	}

	/**
	 * Return, <b>but don't remove</b>, the <b>minimum priority element</b> from the queue.
	 * @return The minimum priority element of the queue, or null if the queue is empty.
	 */
	public T first() {
		if (this.queue.size() == 0){
			return null;
		}
		PriorityQueueNode node = this.queue.get(0);
		return (T) node.getData();
	}


	/**
	 * <p>Return, <b>but don't remove</b>, the <b>maximum priority element</b> from the queue. This operation is inefficient
	 * in MinHeap - based Priority Queues. That's fine for the purposes of our project; you should feel free to
	 * implement your priority queue in any way provides correctness and elementary efficiency of operations.</p>
	 * @return The maximum priority element of the queue, or null if the queue is empty.
	 */
	public T last() {
		if (this.queue.size() == 0){
			return null;
		}
		int pos = 0;
		PriorityQueueNode node = this.queue.get(this.queue.size()-1);

		return (T) node.getData();
	}

	/**
	 * Query the queue about its size. <b>Empty queues have a size of 0.</b>
	 * @return The size of the queue. Returns 0 if the queue is empty.
	 */
	public int size() {
		return this.queue.size();
	}

	/**
	 * Query the queue about emptiness. A queue is empty <b>iff</b> it contains <b>0 (zero)</b> elements.
	 * @return true iff the queue contains <b>0 (zero)</b> elements.
	 */
	public boolean isEmpty() {
		if (this.queue.size() == 0){
			return true;
		}
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int currentPos;
			ArrayList<PriorityQueueNode> iterQueue = queue;

			public boolean hasNext() {
				return this.iterQueue.get(currentPos) != null;
			}

			public T next(){
				T data = (T) this.iterQueue.get(currentPos);
				currentPos += 1;
				return data;
			}
		};
	}


}