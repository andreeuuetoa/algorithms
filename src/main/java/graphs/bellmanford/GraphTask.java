package graphs.bellmanford;

public class GraphTask {

	/*
	  ÜLESANNE:
	  Kasutades Bellman-Fordi algoritmi,
	  leia loodud kaalutud orienteeritud graafis
	  lühimad kaugused igast tipust igasse teise tippu.
	  Graafi kaarte kaalud võivad olla negatiivsed.
	 */

	/** Põhimeetod. */
	public static void main(String[] args) {
		GraphTask a = new GraphTask();
		a.run();
	}

	/** Tegelik jooksumeetod, mis käitab näiteid ja kõike muud. */
	public void run() {
		Graph g = new Graph("Näidisgraaf");
		g.createRandomSimpleGraph(10, 15);
		System.out.println(g);

		String start = "v5";
		System.out.println();
		System.out.println("Lühimad teed igasse punkti punktist " + start);
		g.bellmanFordForOneVertex(start);
		System.out.println(g);
	}
} 