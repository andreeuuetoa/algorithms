package graphs.bellmanford;

/**
 * Undirected edges are represented by two Edge objects (one for both directions).
 */
class Edge {
	private final String id;
	private Vertex target;
	private Edge next;
	private int length;

	Edge(String s, Vertex v, Edge a) {
		id = s;
		target = v;
		next = a;
		length = 0;
	}

	Edge(String s) {
		this (s, null, null);
	}

	public String getId() {
		return id;
	}

	public Vertex getTarget() {
		return target;
	}

	public Edge getNext() {
		return next;
	}

	public int getLength() {
		return length;
	}

	void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return id;
	}

	void setNext(Edge a) {
		next = a;
	}

	void setTarget(Vertex v) {
		target = v;
	}
}