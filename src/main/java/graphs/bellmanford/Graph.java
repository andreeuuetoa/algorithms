package graphs.bellmanford;

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
        int newEdgeInfo = new Random().nextInt(11);
        newEdge.setLength(newEdgeInfo);
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
    public void bellmanFordForOneVertex(String vertexId) {
        Vertex v = getFirst();
        while (!v.getId().equals(vertexId)) {
            v = v.getNext();
            if (v == null) {
                throw new RuntimeException(String.format("No vertex with ID '%s' was found!", vertexId));
            }
        }
        initializeDistancesFromVertex(v);
        relaxAllEdgesOnce(v);
        if (graphHasNegativeWeightCycles(v)) {
            System.out.println("OOPS! This graph has a negative weight cycle!");
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

    private boolean graphHasNegativeWeightCycles(Vertex v) {
        Edge out = v.getFirst();
        while (out != null) {
            int newDist = v.getInfo() + out.getLength();
            if (newDist < out.getTarget().getInfo()) {
                return true;
            }
            out = out.getNext();
        }
        return false;
    }
}
