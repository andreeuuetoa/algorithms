package graphs.bellmanford;

class Vertex {
	private final String id;
	private Vertex next;
	private Vertex predecessor;
	private Edge first;
	private int info;

	Vertex(String s, Vertex v, Edge e) {
		id = s;
		next = v;
		predecessor = null;
		first = e;
		info = 0;
	}

	Vertex(String s) {
		this (s, null, null);
	}

	public String getId() {
		return id;
	}

	public Vertex getNext() {
		return next;
	}

	public Edge getFirst() {
		return first;
	}

	void setFirst(Edge a) {
		first = a;
	}

	public int getInfo() {
		return info;
	}

	void setInfo(int info) {
		this.info = info;
	}

	void setNext(Vertex v) {
		next = v;
	}

	public Vertex getPredecessor() {
		return predecessor;
	}

	void setPredecessor(Vertex predecessor) {
		this.predecessor = predecessor;
	}

	@Override
	public String toString() {
		return id;
	}
}