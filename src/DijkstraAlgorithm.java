import java.util.*;

public class DijkstraAlgorithm {
    private Set<Integer> settledNodes;
    private Set<Integer> unSettledNodes;
    private Map<Integer, Integer> predecessors;
    private Map<Integer, Integer> distance;
    private List<String> interiorOfNodes;
    private Map<Integer, List<Integer>> nodes;


    public DijkstraAlgorithm(Graph graph) {
        this.interiorOfNodes = graph.getInteriorOfNodes();
        this.nodes = graph.getNodes();
    }

    public void execute(Integer source) {

        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();


        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Integer node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Integer node) {
        List<Integer> adjacentNodes = getNeighbors(node);
        for (Integer target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private Integer getDistance(Integer node) {
        return interiorOfNodes.get(node - 1).length();
    }

    private List<Integer> getNeighbors(Integer node) {
        List<Integer> neighbors = new ArrayList<>();
        for (Integer neigbh : nodes.get(node)) {
            if (!isSettled(neigbh)) {
                neighbors.add(neigbh);
            }
        }
        return neighbors;
    }

    //
    private Integer getMinimum(Set<Integer> vertexes) {
        Integer minimum = null;
        for (Integer vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    //
    private boolean isSettled(Integer vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Integer destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<Integer> getPath(Integer target) {
        LinkedList<Integer> path = new LinkedList<>();
        Integer step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
}
