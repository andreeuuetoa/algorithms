package graphs.bellmanford;

import java.util.List;

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

		System.out.println();
		List<Graph> graphsAfterBellmanFord = g.relaxAllEdges();
		System.out.println(graphsAfterBellmanFord);
	}
} 