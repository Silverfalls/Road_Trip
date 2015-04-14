/**
 * Created by Alexander on 14.04.2015.
 */
public interface Graph {
    //TODO: implement the real Graph Interface
    public void initGraph();
    public void getRandomNeighbor();
    public void getDistance(Node n1, Node n2);
    public void getNodeById(long id);
    public void getNodeByName(String name);

}
