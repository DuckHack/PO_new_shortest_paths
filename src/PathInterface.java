/**
 *
 *
 */
public interface PathInterface {

    /*
    metoda, która jest odpowiedzialna za odczytanie pliku graph.txt, zamiane
    informacji zapisanej w postaci binarnej na dziesietna oraz zbudowanie
    grafu na podstawie odczytanego i rozkodowanego kodu binarnego
    */
    public Graph buildGraph(String fileName);

    /*
    metoda dekodujaca i zwracajaca ciag znakow znajdujacy sie w zadanym wezle;
    */
    public String decompress(String code);

    /*
    metoda znajdująca najkrotsza ścieżkę (numry wezlow) pomiędzy dwoma wezlami
    zadanymi na wejsciu
    */
    public String findPath(Graph graph, int beginingNode, int destinationNode);

    /*
    metoda zwracajaca rozkodowany ciag znakow, które sa przechowywane przez
    poszczegolne wezly w najkrotszej sciezce pomiedzy dwoma wezlami oznaczonymi
    jako beginingNode oraz destinationNode
    */
    public String getPathString(Graph graph, int beginingNode, int destinationNode);

}