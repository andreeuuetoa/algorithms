package graphs;

/**
 * Undirected edges are represented by two Edge objects (one for both directions).
 */
class Edge {
	private final String id;
	private Vertex target;
	private Edge next;
	private int info;

	Edge(String s, Vertex v, Edge a) {
		id = s;
		target = v;
		next = a;
		info = 0;
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

	public int getInfo() {
		return info;
	}

	void setInfo(int info) {
		this.info = info;
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