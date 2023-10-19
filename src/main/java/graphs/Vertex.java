package graphs;

class Vertex {
	private final String id;
	private Vertex next;
	private Arc first;
	private int info;

	Vertex(String s, Vertex v, Arc e) {
		id = s;
		next = v;
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

	public Arc getFirst() {
		return first;
	}

	void setFirst(Arc a) {
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

	@Override
	public String toString() {
		return id;
	}
}