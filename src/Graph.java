import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Integer, List<Integer>> nodes;
    private List<String> interiorOfNodes;


    public Graph(Map<Integer, List<Integer>> nodes, List<String> interiorOfNodes) {
        this.nodes = nodes;
        this.interiorOfNodes = interiorOfNodes;
    }

    public Map<Integer, List<Integer>> getNodes() {
        return nodes;
    }

    public List<String> getInteriorOfNodes() {
        return interiorOfNodes;
    }

//    //mapa przechowujaca liste sasiedztwa dla tworzonego grafu
//    Map<Integer, ArrayList<Integer>> nodes = new HashMap<>();
//
//    //lista zawierajaca informacje przechowywane w poszczegolnych wezlach grafu
//    ArrayList<String> interiorOfNodes = new ArrayList<>();
}
