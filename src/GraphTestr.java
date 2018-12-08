import java.util.*;

public class GraphTestr {
    private Graph graph;
    private Path path;
    private Integer numberOfNodes = 1;

    public GraphTestr() {
        this.path = new Path();
//        this.graph = new Graph();
    }

    public void perfromTests() {
        decompressionTest();
        graphBuildingTest();
        searchPathTest();
    }

    private void decompressionTest() {
        String stringForDecompress = "t(1x3)ek(2x4)st";
        String expectedResult = "tttekekekekst";
        String secondStringForDecompress = "przyk(3x3)la(1x2)d(2x4)";
        String secondExpectedResult = "przykzykzyklaaadadadad";

//        String secondStringForDecompress = "rez(3x5)erw(2x10)ac(3x3)ja(1x2)";
//        String secondExpectedResult = "rezrezrezrezrezerwrwrwrwrwrwrwrwrwrwwacwacwacjaa";
        String result = path.decompress(stringForDecompress);
        System.out.println("stringForDecompress -> " + stringForDecompress);
        System.out.println("Rsult -> " + result);
        System.out.println("expectedResult -> " + expectedResult);
        if (result.equals(expectedResult)) {
            System.out.println("Good damn right");
        }
        result = path.decompress(secondStringForDecompress);
        System.out.println("Rsult -> " + result);
        System.out.println("Rsult -> " + secondExpectedResult);
        if (result.equals(secondExpectedResult)) {
            System.out.println("Good damn right");
        }

    }

    private void graphBuildingTest() {
        path.buildGraph("graphData.txt");
    }

    private void searchPathTest() {
        Map<Integer, List<Integer>> nodes = new HashMap<>();
        nodes.put(1, Arrays.asList(2, 3));
        nodes.put(2, Arrays.asList(4));
        nodes.put(3, Arrays.asList(5, 6));
        nodes.put(4, Arrays.asList(6));
        nodes.put(5, Collections.emptyList());
        nodes.put(6, Collections.emptyList());

        List<String> interiorOfNodes = new ArrayList<>();
        interiorOfNodes.add("t(1x4)eks(2x2)t");
        interiorOfNodes.add("t(1x3)e(1x2)ks(3x3)t(1x2)");
        interiorOfNodes.add("przy(2x6)kl(1x10)ad");
        interiorOfNodes.add("ksia(1x2)zka");
        interiorOfNodes.add("rez(3x5)erw(2x10)ac(3x3)ja(1x2)");
        interiorOfNodes.add("dow(2x2)od(1x2)");

        Graph graph = new Graph(nodes, interiorOfNodes);

        String shortestPath = path.findPath(graph, 1, 6);
        System.out.println("shortestPath -> " + shortestPath);
        System.out.println("path.getPathString -> " + path.getPathString(graph, 1, 6));
    }
}
