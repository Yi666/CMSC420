package projects.spatial.kdpoint;

/** <p>{@link KDPoint} is a class that represents a k-dimensional point in Euclidean
 * space, where <em>k</em> is a positive integer. It provides methods for initialization,
 * copy construction, equality checks and distanceSquared calculations. The precision of {@link KDPoint}s
 * is double.</p>
 * 
 * <p><b>YOU SHOULD ***NOT*** EDIT THIS CLASS!</b> If you do, you risk <b>not passing our tests!</b></p>
 *
 * @author <a href="https://github.com/JasonFil">Jason Filippou</a>
 */
public class KDPoint {
	
	/** To make matters simple for client code, we will allow the {@link KDPoint}'s
	 * coordinates to be publicly accessible. This makes {@link KDPoint}s <b>mutable</b>,
	 * so deep copies will be required wherever we copy {@link KDPoint}s.
	 */
	public double[] coords;
	
	
	/**
	 * Default constructor initializes this as a 2D {@link KDPoint} describing
	 * the Cartesian origin.
	 */
	public KDPoint(){
		this(2);
	}

	/**
	 * Initialize a <em>k</em>-dimensional {@link KDPoint} at the origin of the axes.
	 * @param k The dimensionality of the {@link KDPoint}.
	 * @throws RuntimeException if the provided dimensionality is &lt; 1.
	 */
	public KDPoint(int k) {
		if(k <= 0)
			throw new RuntimeException("All KDPoints need to have a positive dimensionality.");
		coords = new double[k];
	}
	
	/**
	 * Initialize a {@link KDPoint} with some values. Implicitly sets the {@link KDPoint}'s
	 * dimensionality.
	 * @param vals The values with which to initialize the {@link KDPoint}.
	 * @see System#arraycopy(Object, int, Object, int, int)
	 */
	public KDPoint(double... vals){
		coords = new double[vals.length];
		System.arraycopy(vals, 0, coords, 0, vals.length); // Java's equivalent of memcpy(), I guess.
	}
	
	/**
	 * Initialize a {@link KDPoint} based on an already existing {@link KDPoint}. Since {@link KDPoint} is a
	 * <b>mutable</b> class, <b>all new {@link KDPoint} instances</b> should be created by this copy-constructor!
	 * @param p The {@link KDPoint} on which we will base the creation of this.
	 */
	public KDPoint(KDPoint p){
		this(p.coords);
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if(o.getClass() != getClass()) // TODO: Make sure that it's ok to do a reference equality check here.
			return false;
		KDPoint oCasted = (KDPoint)o; // No ClassCastExceptions here because of above check
		if(oCasted.coords.length != coords.length)
			return false;
		for(int i = 0; i < coords.length; i++)
			if(coords[i] != oCasted.coords[i]) // TODO: Make sure that this is correct up to numerical accuracy.
				return false;
		return true;
	}
	
	/**
	 * Calculate the <b><u>squared</u> Euclidean distanceSquared</b> between this and p.
	 * @param p The {@link KDPoint} to calculate the distanceSquared to.
	 * @return The <b><u>squared</u> Euclidean distanceSquared</b> between the two {@link KDPoint}s.
	 * @throws RuntimeException if the dimensionality of the two KDPoints is different.
	 */
	public double distanceSquared(KDPoint p) throws RuntimeException{
		if(coords.length != p.coords.length)
			throw new RuntimeException("Cannot calculate the Euclidean Distance between KDPoints of different dimensionalities.");
		double sum = 0.0;
		for(int i = 0; i < coords.length; i++)
			sum += Math.pow(coords[i] - p.coords[i], 2);
		return sum;
	}
	
	/**
	 * A static version of distanceSquared calculations. Since the Squared Euclidean distanceSquared is symmetric,
	 * it's somewhat awkward to have to specify a start and end point, as {@link #distanceSquared(KDPoint) distanceSquared} does,
	 * so we provide this option as well.
	 * @param p1 One of the two {@link KDPoint}s to calculate the distanceSquared of.
	 * @param p2 One of the two {@link KDPoint}s to calculate the distanceSquared of.
	 * @return The Euclidean distanceSquared between p1 and p2.
	 */
	public static double distanceSquared(KDPoint p1, KDPoint p2){
		return p1.distanceSquared(p2);
	}
	
	@Override
	public String toString(){
		StringBuilder retVal = new StringBuilder("A KDPoint with coordinates: (");
		for(int i = 0; i < coords.length; i++){
			retVal.append(coords[i]);
			if(i < coords.length - 1) 
				retVal.append(", ");
		}
		return retVal +")";
	}
}
