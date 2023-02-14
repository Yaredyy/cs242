
public class DFS
{
  Vertex[] verts = new Vertex[0];
  boolean undirected = false;
  int ts;  // timestamp
  DFS() {

}
void expandVerts() {
  Vertex[] tmp;
  tmp = new Vertex[verts.length + 1];
  System.arraycopy(verts, 0, tmp, 0, verts.length);
  verts = tmp;
}
Vertex findVertex(String nm) {
  int ix;
  for (ix = 0; ix < verts.length; ++ix)
  {
    if (nm.equals(verts[ix].name))
    {
      return verts[ix];
    }
  }
  return null;
}
Vertex addVertex(String nam) {
  int jx;
  Vertex vx;
  if (null == (vx = findVertex(nam)))
  {
    expandVerts();
    jx = verts.length - 1;
    verts[jx] = vx = new Vertex(nam);
    vx.sub = jx;
  }
  return vx;
}
void addAdjEdge(Vertex fm, Vertex to, String wgt) {
  Edge gn;
  if (null == fm.adj)
  {
    fm.adj = this.new Edge(fm, to, wgt);
    fm.adj.setFrom(fm);
    return;
  }
  gn = fm.adj;
  while (null != gn.link)
  {
    if (to.name.equals(gn.to.name))
    {
      System.out.printf("vertex %s already adjacent to vertex %s\n", to.name, fm.name);
      return;
    }
    gn = gn.link;
  }
  gn.link = this.new Edge(fm, to, wgt);
}
static DFS factory(String[][] data, boolean undirected) {
  int ix;
  Vertex fm, to;
  DFS gr = new DFS();
  for (ix = 0; ix < data.length; ++ix)
  {
    fm = gr.addVertex(data[ix][0]);
    if (null == data[ix][1])
      continue;
    to = gr.addVertex(data[ix][1]);
    gr.addAdjEdge(fm, to, data[ix][2]);
    if (undirected)
      gr.addAdjEdge(to, fm, data[ix][2]);
  }
  gr.undirected = undirected;
  return gr;
}
void reset() {
  int ix;
  Vertex vtx;
  Edge edg;
  for (ix = 0; ix < verts.length; ++ix)
  {
    vtx = verts[ix];
    vtx.mark = 0;
    vtx.pr = null;
    vtx.st = 0;
    vtx.ft = 0;
    vtx.dist = 0;
    edg = verts[ix].adj;
    while (null != edg)
    {
      edg.mark = 0;
      edg = edg.link;
    }
  }
}
void allEdges(Callback<Edge> cb) {
  Edge edg;
  for (int ix = 0; ix < verts.length; ++ix)
  {
    edg = verts[ix].adj;
    while (null != edg)
    {
      cb.call(edg);
      edg = edg.link;
    }
  }
}
void dfs_visit(Vertex cur) {
  Edge edg;
  /* implement dfs_visit() code here */
  cur.mark = -1;
  cur.st = ++ts;
  edg = cur.adj;
  while (null != edg)
  {
    if (0 == edg.to.mark)
    {
      edg.to.pr = cur;
      edg.mark = -1;
      dfs_visit(edg.to);
    }
    edg = edg.link;
  }
  cur.mark = 1;
  cur.ft = ++ts;
}
void dfs(int s) {
  reset();
  ts = 0;
  /* implement dfs() code here */
  for (Vertex v:verts){
    if(v.mark==0){
      dfs_visit(v);
    }
  }
}
boolean verifyVertices() {
  boolean verified = true;
  for (Vertex v : verts)
  {
    if (0 == v.mark)
    {
      verified = false;
      System.out.printf("vertex %s not visited\n", v.name);
    }
  }
  return verified;
}
public String toString() {
  int ix;
  Edge seq;
  StringBuilder sb;
  if (0 == verts.length)
  {
    return "[]";
  }
  sb = new StringBuilder();
  sb.append("[");
  for (ix = 0; ix < verts.length; ++ix)
  {
    if (0 != ix)
      sb.append(",\n");
    sb.append(verts[ix].name);
    seq = verts[ix].adj;
    while (null != seq)
    {
      sb.append(String.format("->(%.1f)", seq.getWeight()));
      sb.append(seq.to.name);
      seq = seq.link;
    }
  }
  sb.append("]");
  return sb.toString();
}
void assignWeek9() {
  Callback<Edge> cb = new F1();
  dfs(0);
  verifyVertices();
  System.out.println();
  allEdges(cb);
}
public static void main(String[] args)
{
  // String[][] gr1 = {
  //     {"A","B","2"}, {"A","C","3"}, {"B","D","1"}, {"C","D","1"}, {"E","A","2"}, {"E","F","5"}, {"E","G","4"},
  //     {"E","H","2"}, {"F","G","3"}, {"F","B","2"}, {"G","D","3"}, {"G","H","5"}, {"H","C","1"}, {"I","E","3"},
  //     {"I","H","1"}, {"J","F","1"}, {"J","G","2"},
  //   };
  // String[][] gr2 = {   // Dijkstra nfrom slides
  //     {"A", "B", "2"}, {"A", "G", "6"}, {"B", "C", "6"}, {"B", "E", "2"}, {"E", "G", "1"}, {"E", "F", "2"},
  //     {"F", "C", "3"}, {"F", "H", "2"}, {"G", "H", "4"}, {"H", "D", "2"}, {"C", "D", "3"},
  // };
  // String[][] gr3 = {  /* directed from slides */
  //     {"1", "2", "1"}, {"2", "2", "1"}, {"3", null, null}, {"2", "4", "1"}, {"4", "1", "1"}, {"4", "5", "1"},
  //     {"5", "4", "1"}, {"6", "3", "1"}
  // };
  // String[][] gr4 = {  /* undirected from slides */
  //     {"a", "b", "1"}, {"a", "c", "1"}, {"b", "c", "1"}, {"d", null, null}, {"e", "f", "1"}
  // };
  String[][] gr5 = {  /* week 9 */
      {"s", "z", "1"}, {"z", "y", "1"}, {"y", "x", "1"}, {"x", "z", "1"}, {"z", "w", "1"}, {"s", "w", "1"},
      {"w", "x", "1"}, {"t", "v", "1"}, {"t", "u", "1"}, {"u", "t", "1"}, {"u", "v", "1"}, {"v", "w", "1"},
      {"v", "s", "1"},
  };
  DFS gr;
  gr = factory(gr5, false);
  System.out.println(gr);
  gr.assignWeek9();
}
class Edge implements Comparable<Edge>{
    Vertex to;
    Vertex from;
    Edge link;
    int mark;
    float weight;
  public Edge(final Vertex fm, final Vertex to, final String wgt) {
    from = fm;
    this.to = to;
    setWeight(wgt);
  }
  public float getWeight() {
    return weight;
  }
  public void setWeight(final float weight) {
    this.weight = weight;
  }
  public void setWeight(final String weight) {
    try
    {
      this.weight = Float.parseFloat(weight);
    }
    catch (NumberFormatException nfx)
    {
      this.weight = 1.0f;
    }
  }
  public Vertex getFrom() {
    return from;
  }
  public void setFrom(Vertex from) {
    this.from = from;
  }
  public int compareTo(Edge nod) {
      if (weight < nod.getWeight())
        return -1;
      else if (weight > nod.getWeight())
        return 1;
      return 0;
    }
}
class Vertex {
  String name;
  Edge adj;
  int sub;   // subscript of this vertex in the adjacency list
  int mark;  // general purpose mark
  int dist;  // distance
  int st;  // DFS start time
  int ft;  // DFS finish time
  Vertex pr;  // predecessor, BFS or DFS, parent MST
Vertex(String... attr) {
  if (attr.length >= 1)
    name = attr[0];
}
public String toString() {
  return String.format("[name=%s,mark=%d,adj=%s]", name, mark, (null != adj) ? "nn" : "nl");
}
}
interface Callback<T> {
  void call(T prm);
}
class F1 implements Callback<Edge> {
  @Override
  public void call(Edge edg) {
    Vertex u, v;
    /* add edge classification and output code here */
    u=edg.from;
    System.out.print(u.name+"->");
    v=edg.to;
    System.out.print(v.name+" ");
    if(edg.mark==1){
      System.out.println("is a tree edge");
    }
    else {
      if (u.st < v.st && u.ft > v.ft) {
        System.out.println("is a forward edge");
      } else if (v.st < u.st && u.ft < v.ft) {
        System.out.println("is a back edge");
      } else {
        System.out.println("is a cross edges");
      }
    }

  }
}
}
