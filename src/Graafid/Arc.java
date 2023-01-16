package Graafid;

/**
 * Arc (kaar) kujutab ühte noolt graafis.
 * Orienteerimata graafis olevaid servu kujutatakse kahe kaare objektiga
 * (kummagis suunas üks).
 * Kaarele on omistatud unikaalne identifikaator, sihtpunkt,
 * viide järgmisele kaarele ja infoväli.
 */
class Arc {

	private final String id;
	private Vertex target;
	private Arc next;
	private int info;

	Arc(String s, Vertex v, Arc a) {
		id = s;
		target = v;
		next = a;
		info = 0;
	}

	Arc(String s) {
		this (s, null, null);
	}

	public String getId() {
		return id;
	}

	public Vertex getTarget() {
		return target;
	}

	public Arc getNext() {
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

	void setNext(Arc a) {
		next = a;
	}

	void setTarget(Vertex v) {
		target = v;
	}
}