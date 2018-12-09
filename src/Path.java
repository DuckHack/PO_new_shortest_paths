import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

public class Path implements PathInterface {
    @Override
    public Graph buildGraph(String fileName) {
        return getBuildedGraph(fileName);
    }

    @Override
    public String decompress(String code) {
        return getDecompressedNode(code);
    }

    @Override
    public String findPath(Graph graph, int beginingNode, int destinationNode) {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        dijkstraAlgorithm.execute(beginingNode);
        return dijkstraAlgorithm.getPath(destinationNode).toString();
    }

    @Override
    public String getPathString(Graph graph, int beginingNode, int destinationNode) {
        StringBuilder pathString = new StringBuilder();
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        dijkstraAlgorithm.execute(beginingNode);
        for (Integer code : dijkstraAlgorithm.getPath(destinationNode)) {
            pathString.append(decompress(graph.getInteriorOfNodes().get(code - 1)) + " ");
        }
        return pathString.toString();
    }

    private Graph getBuildedGraph(String fileName) {
        List<String> extractedStrings = new ArrayList<>(100);
        File file = new File(System.getProperty("user.dir") + "/" + fileName);
        try (BufferedReader fileBuffer = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileBuffer.readLine()) != null) {
                extractedStrings.add(line);
            }
        } catch (IOException e) {
            System.out.println("Reading file problem: " + e.getMessage());
        }

        List<String> interiorOfNodes = getCodes(extractedStrings);
        Map<Integer, ArrayList<Integer>> nodes = getNodes(extractedStrings);

        System.out.println(nodes);
        System.out.println(interiorOfNodes);
        return null;
    }

    private List<String> getCodes(List<String> readeFile) {
        List<String> codesList = new ArrayList<>(readeFile.size());
        for (String line : readeFile) {
            for (int i = 0; i < line.length(); i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != ',' && line.charAt(i) != ' ') {
                    codesList.add(line);
                    break;
                }
            }
        }
        return codesList;
    }

    private static int integerfrmbinary(String str) {
        double j = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                j = j + Math.pow(2, str.length() - 1 - i);
            }
        }
        return (int) j;
    }

    private Map<Integer, ArrayList<Integer>> getNodes(List<String> readeFile) {
        Map<Integer, ArrayList<Integer>> nodeMap = new HashMap<>();
        int index = 0;
        for (String line : readeFile) {
            if (line.matches("[01\\s]+")) {
                ArrayList<Integer> tab = new ArrayList<>();
                for (String slit : line.split("\\s")) {
                    tab.add(integerfrmbinary(slit));
                }
                if (index > 0 && tab.size() >= 2) {
                    tab.remove(0);
                    tab.remove(0);
                }
                nodeMap.put(index++, tab);
            }
        }
        return nodeMap;
    }

    // Decompression code
    private String getDecompressedNode(String code) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = code.length() - 1; i >= 0; i--) {
            if (Character.isDigit(code.charAt(i))) {
                int escapeSize = 0;
                int multi = Integer.parseInt(getNumbers(code, i, true));
                int numOfChars = Integer.parseInt(getNumbers(code, i, false));
                String strToAppending = getSubstring(numOfChars, i, code);

                for (int j = 0; j < multi; j++) {
                    stringBuilder.append(strToAppending);
                }

                i -= ("" + multi + numOfChars + 1).length();

                for (int k = i; k > 0; k--) {
                    if (code.charAt(k) == ')')
                        break;
                    escapeSize += 1;
                }

                if (escapeSize < strToAppending.length()) {
                    i -= escapeSize;
                } else {
                    i -= strToAppending.length();
                }

            } else {
                if (code.charAt(i) == '(' || code.charAt(i) == ')') {
                    continue;
                }
                stringBuilder.append(code.charAt(i));
            }
        }
        return stringBuilder.reverse().toString();

    }

    private String getNumbers(String searchString, Integer i, boolean isMulti) {
        StringBuilder retrievedNumbers = new StringBuilder();
        if (isMulti) {
            for (int j = i; j > 0; j--) {
                char retrievedCharacter = searchString.charAt(j);
                if (retrievedCharacter == 'x')
                    break;
                retrievedNumbers.append(retrievedCharacter);
            }
        } else {
            boolean isTimeToRead = false;
            for (int j = i; j > 0; j--) {
                char retrievedCharacter = searchString.charAt(j);
                if (retrievedCharacter == 'x') {
                    isTimeToRead = true;
                    continue;
                }
                if (retrievedCharacter == '(')
                    break;
                if (isTimeToRead)
                    retrievedNumbers.append(retrievedCharacter);
            }
        }
        return retrievedNumbers.reverse().toString();

    }

    private String getSubstring(Integer numOfChars, Integer i, String code) {
        List<Integer> jumpList = new LinkedList<>();
        StringBuilder stringBuilder = new StringBuilder();
        do {
            i--;
        } while (code.charAt(i) != '(');

        int iForFirst = i;
        int iForSecond = i;

        for (int j = 0; j <= numOfChars; j++) {
            int checkIndex = iForFirst - j;
            if (code.charAt(checkIndex) == ')') {
                int jumpSize = 1;
                do {
                    jumpSize += 1;
                    iForFirst -= 1;
                    checkIndex -= 1;
                } while (code.charAt(checkIndex) != '(');
                iForFirst -= 2;
                jumpList.add(jumpSize);
            }
        }

        if (jumpList.size() == 0) {
            for (int k = numOfChars; k > 0; k--) {
                stringBuilder.append(code.charAt(i - k));
            }
        } else {
            int totalJumpSize = jumpList.stream().reduce(0, (sum, p) -> sum += p, (sum1, sum2) -> sum1 + sum2);
            for (int k = numOfChars + totalJumpSize; k > 0; k--) {
                int currentIndex = iForSecond - k;
                if (code.charAt(currentIndex) == '(') {
                    int jumpSize = ((LinkedList<Integer>) jumpList).removeLast();
                    currentIndex = iForSecond - k + jumpSize;
                    k -= jumpSize;
                }
                stringBuilder.append(code.charAt(currentIndex));
            }
        }
        return stringBuilder.reverse().toString();
    }
}
