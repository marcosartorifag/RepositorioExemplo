import java.util.*;

class CityGraph {
    private Map<String, Map<String, Integer>> graph = new HashMap<>();

    public void addEdge(String city1, String city2, int distance) {
        graph.computeIfAbsent(city1, k -> new HashMap<>()).put(city2, distance);
        graph.computeIfAbsent(city2, k -> new HashMap<>()).put(city1, distance);
    }

    public int getDistance(String city1, String city2) {
        return graph.getOrDefault(city1, Collections.emptyMap()).getOrDefault(city2, Integer.MAX_VALUE);
    }
}

public class ShortestDistanceFinder {
    public static void main(String[] args) {
        CityGraph cityGraph = new CityGraph();

        // Adicionar cidades e distâncias entre elas ao grafo
        cityGraph.addEdge("A", "B", 5);
        cityGraph.addEdge("A", "C", 3);
        cityGraph.addEdge("B", "C", 1);
        cityGraph.addEdge("B", "D", 4);
        cityGraph.addEdge("C", "D", 2);
        cityGraph.addEdge("D", "E", 7);

        String startCity = "A"; // Adição da cidade de origem
        String endCity = "E"; // Adição da cidade de destino

        int shortestDistance = dijkstra(cityGraph, startCity, endCity);
        System.out.println("A menor distância entre " + startCity + " e " + endCity + " é: " + shortestDistance);
    }

    public static int dijkstra(CityGraph graph, String startCity, String endCity) {
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        Map<String, Integer> distances = new HashMap<>();

        minHeap.offer(new Node(startCity, 0));
        distances.put(startCity, 0);

        while (!minHeap.isEmpty()) {
            Node currentNode = minHeap.poll();
            if (currentNode.city.equals(endCity)) {
                return currentNode.distance;
            }

            for (Map.Entry<String, Integer> neighborEntry : graph.graph
                    .getOrDefault(currentNode.city, Collections.emptyMap()).entrySet()) {
                String neighborCity = neighborEntry.getKey();
                int newDistance = currentNode.distance + neighborEntry.getValue();

                if (newDistance < distances.getOrDefault(neighborCity, Integer.MAX_VALUE)) {
                    distances.put(neighborCity, newDistance);
                    minHeap.offer(new Node(neighborCity, newDistance));
                }
            }
        }

        return Integer.MAX_VALUE; // Caso não haja um caminho entre as cidades
    }

    static class Node {
        String city;
        int distance;

        public Node(String city, int distance) {
            this.city = city;
            this.distance = distance;
        }
    }
}
