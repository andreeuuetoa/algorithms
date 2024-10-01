package graphs.bellmanford;

public class GraphTask {

	public static void main(String[] args) {
		GraphTask a = new GraphTask();
		a.run();
	}

	public void run() {
		Graph g = new Graph("Example graph");
		g.createRandomSimpleGraph(10, 15);
		System.out.println(g);

		String start = "v5";
		System.out.println();
		System.out.println("Shortest paths to each vertex from vertex " + start + ":");
		g.bellmanFordForOneVertex(start);
		System.out.println(g);
	}
} 