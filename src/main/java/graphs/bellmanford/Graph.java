package graphs.bellmanford;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Graph {
    public static final int INFINITY = Integer.MAX_VALUE / 2;
    private String id;
    private Vertex first;
    private int info;

    public Graph(String s, Vertex v) {
        id = s;
        first = v;
    }

    public Graph(String s) {
        this(s, null);
    }

    public String getId() {
        return id;
    }

    public Vertex getFirst() {
        return first;
    }

    @Override
    public String toString() {
        String nl = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder(nl);
        sb.append(id);
        sb.append("\n");
        sb.append(nl);
        Vertex v = first;
        while (v != null) {
            sb.append(v);
            sb.append(" \"");
            sb.append(v.getInfo() <= INFINITY + 1000 && v.getInfo() >= INFINITY - 1000 ? "INFINITY" : v.getInfo());
            sb.append("\"");
            sb.append(" -->");
            Edge a = v.getFirst();
            while (a != null) {
                sb.append(" ");
                sb.append(a);
                sb.append(" (");
                sb.append(v);
                sb.append("->");
                sb.append(a.getTarget());
                sb.append(")");
                sb.append(" \"");
                sb.append(a.getLength());
                sb.append("\"");
                a = a.getNext();
            }
            sb.append(nl);
            v = v.getNext();
        }
        return sb.toString();
    }

    public Vertex createVertex(String vid) {
        Vertex res = new Vertex(vid);
        res.setNext(first);
        first = res;
        return res;
    }

    public Edge createEdge(String aid, Vertex from, Vertex to) {
        Edge res = new Edge(aid);
        res.setNext(from.getFirst());
        from.setFirst(res);
        res.setTarget(to);
        return res;
    }

    /**
     * Create a connected undirected random tree with n vertices.
     * Each new vertex is connected to some random existing vertex.
     *
     * @param n number of vertices added to this graph
     */
    public void createRandomTree(int n) {
        if (n <= 0)
            return;
        Vertex[] varray = new Vertex[n];
        for (int i = 0; i < n; i++) {
            varray[i] = createVertex("v" + (n - i));
            if (i > 0) {
                int vnr = (int) (Math.random() * i);
                Edge newEdge = createEdge("a" + varray[vnr].toString() + "_"
                        + varray[i].toString(), varray[vnr], varray[i]);
                setRandomLengthOnEdge(newEdge);
            }
        }
    }

    /**
     * Create an adjacency matrix of this graph.
     * Side effect: corrupts info fields in the graph
     *
     * @return adjacency matrix
     */
    public int[][] createAdjMatrix() {
        info = 0;
        Vertex v = first;
        while (v != null) {
            v.setInfo(info++);
            v = v.getNext();
        }
        int[][] res = new int[info][info];
        v = first;
        while (v != null) {
            int i = v.getInfo();
            Edge a = v.getFirst();
            while (a != null) {
                int j = a.getTarget().getInfo();
                res[i][j]++;
                a = a.getNext();
            }
            v = v.getNext();
        }
        return res;
    }

    /**
     * Create a connected simple (undirected, no loops, no multiple
     * arcs) random graph with n vertices and m edges.
     *
     * @param n number of vertices
     * @param m number of edges
     */
    public void createRandomSimpleGraph(int n, int m) {
        if (n <= 0)
            return;
        if (n > 2500)
            throw new IllegalArgumentException("Too many vertices: " + n);
        if (m < n - 1 || m > n * (n - 1) / 2)
            throw new IllegalArgumentException
                    ("Impossible number of edges: " + m);
        first = null;
        createRandomTree(n);       // n-1 edges created here
        Vertex[] vert = new Vertex[n];
        Vertex v = first;
        int c = 0;
        while (v != null) {
            vert[c++] = v;
            v = v.getNext();
        }
        int[][] connected = createAdjMatrix();
        int edgeCount = m - n + 1;  // remaining edges
        while (edgeCount > 0) {
            int i = (int) (Math.random() * n);  // random source
            int j = (int) (Math.random() * n);  // random target
            if (i == j)
                continue;  // no loops
            if (connected[i][j] != 0 || connected[j][i] != 0)
                continue;  // no multiple edges
            Vertex vi = vert[i];
            Vertex vj = vert[j];
            Edge newEdge = createEdge("e" + vi.toString() + "_" + vj.toString(), vi, vj);
            connected[i][j] = 1;
            setRandomLengthOnEdge(newEdge);
            edgeCount--;  // a new edge happily created
        }
    }

    private void setRandomLengthOnEdge(Edge newEdge) {
        int newArcInfo = new Random().nextInt(11);
        newEdge.setLength(newArcInfo);
    }

    public Graph clone() {
        initializeGraphInfo();
        Vertex ptr = getFirst();
        Graph clonedGraph = new Graph(getId());
        while (ptr != null) {
            clonedGraph.newVertexDepthFirst(ptr);
            ptr = ptr.getNext();
        }
        return clonedGraph;
    }

    /**
     * Säti graafi koopia tegemise alguses iga tipu väärtuseks null.
     */
    private void initializeGraphInfo() {
        Vertex ptr = getFirst();
        while (ptr != null) {
            ptr.setInfo(0);
            ptr = ptr.getNext();
        }
    }

    /**
     * Graafi laiuti läbides tee koopia-graafile uus tipp,
     * mille id on sama, mis päris graafilt antud tipp.
     * Kui selline tipp on koopial juba olemas,
     * siis tagasta see ja ära uut tippu tee.
     *
     * @return kloonile loodud uus tipp
     */
    private Vertex newVertexDepthFirst(Vertex vertex) {
        if (vertex.getInfo() != 0) {
            return vertex;
        }
        vertex.setInfo(1);
        Edge arcFromVertex = vertex.getFirst();
        Vertex newVertex;
        while (arcFromVertex != null) {
            Vertex target = arcFromVertex.getTarget();
            if (target.getInfo() == 0) {
                newVertex = newVertexDepthFirst(target);
            } else {
                newVertex = getMatchingVertexFromGraph(target);
            }
            Vertex sourceVertex = getMatchingVertexFromGraph(vertex);
            if (sourceVertex == null) {
                sourceVertex = createVertex(vertex.getId());
            }
            Edge newArc = createEdge(arcFromVertex.getId(), sourceVertex, newVertex);
            newArc.setLength(arcFromVertex.getLength());
            arcFromVertex = arcFromVertex.getNext();
        }
        newVertex = getMatchingVertexFromGraph(vertex);
        newVertex.setInfo(2);
        return newVertex;
    }

    /**
     * Leia kloonitud graafis olev sama id-ga tipp.
     *
     * @param vertex päris graafis olev tipp
     * @return kloonile vastav tipp
     */
    private Vertex getMatchingVertexFromGraph(Vertex vertex) {
        Vertex ptr = getFirst();
        while (ptr != null) {
            if (ptr.getId().equals(vertex.getId())) {
                return ptr;
            }
            ptr = ptr.getNext();
        }
        return createVertex(vertex.getId());
    }

    /**
     * Implementeeri graafil Bellman-Fordi algoritm, mis leiab igast
     * tipust lühima kauguse igasse teise tippu.
     */
    public List<Graph> relaxAllEdges() {
        List<Graph> bellmanFordFinishedGraphs = new ArrayList<>();
        Vertex vertex = getFirst();
        while (vertex != null) {
            Graph g = this.clone();
            g.id = '\'' + g.id + "' graafi lühimad kaugused kõikidesse tippudesse tipust " + vertex;
            Vertex cloneVertex = getVertexOnClonedGraph(g, vertex);
            g.initializeGraphInfo();
            g.bellmanFordForOneVertex(cloneVertex);
            bellmanFordFinishedGraphs.add(g);
            vertex = vertex.getNext();
        }
        return bellmanFordFinishedGraphs;
    }

    private Vertex getVertexOnClonedGraph(Graph g, Vertex v) {
        Vertex cloneVertex = g.getFirst();
        while (!cloneVertex.getId().equals(v.getId())) {
            cloneVertex = cloneVertex.getNext();
        }
        return cloneVertex;
    }

    /**
     * Kasutades Bellman-Fordi algoritmi, etteantud lähtetipust
     * lühimad teepikkused kõikidesse teistesse tippudesse.
     * See meetod käib läbi kõikide graafi kaarte n - 1 korda,
     * kus n on graafis olevate tippude arv.
     * Kui graaf sisaldab negatiivse kaaluga kaart,
     * võib kahtlustada graafis olevat negatiivse kaaluga tsüklit
     * ja kontrollimiseks proovitakse kõik kaared veel kord läbi käia.
     *
     * @param v lähtetipp
     */
    public void bellmanFordForOneVertex(Vertex v) {
        initializeDistancesFromVertex(v);
        Vertex x = first;
        while (x != null) {
            relaxAllEdgesOnce(v);
            x = x.getNext();
        }
    }

    /**
     * Algselt on lähtetipust kõik teised tipud otseselt ligipääsmatud.
     * Seetõttu sätib meetod algselt teepikkused lähtetipust kõikidesse teistesse tippudesse lõpmatuks.
     *
     * @param v lähtetipp
     */
    private void initializeDistancesFromVertex(Vertex v) {
        Vertex vertex = first;
        while (vertex != null) {
            if (vertex.equals(v)) {
                vertex.setInfo(0);
            } else {
                vertex.setInfo(INFINITY);
            }
            vertex = vertex.getNext();
        }
    }

    /**
     * Käib läbi kõik graafis olevad kaared, et leida lähtetipu ja
     * kõikide teiste tippude vahel vähimad teepikkused.
     * Kui kõik kaared on läbi käidud n - 1 korda,
     * kus n on graafis olevate tippude arv,
     * proovi veel üks kord leida lähtetipu ja kõikide teiste tippude vahel väikseim teepikkus.
     * Kui siis leitakse veel väiksem teepikkus,
     * leidub graafis negatiivse kaaluga tsükkel ja vähimat teepikkust ei ole võimalik leida.
     * Selline olukord võib tekkida siis, kui antud graafis on negatiivsete kaaludega kaari.
     */
    private void relaxAllEdgesOnce(Vertex v) {
        Vertex vertex = first;
        while (vertex != null) {
            Edge out = vertex.getFirst();
            while (out != null) {
                int newDist = vertex.getInfo() + out.getLength();
                if (newDist < out.getTarget().getInfo()) {
                    out.getTarget().setInfo(newDist);
                }
                out = out.getNext();
            }
            vertex = vertex.getNext();
        }
    }
}
