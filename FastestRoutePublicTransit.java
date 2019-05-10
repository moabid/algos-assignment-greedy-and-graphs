/**
 * Public Transit
 * Author: Mohammed Abid and Carolyn Yao
 * Does this compile? Y
 */

/**
 * This class contains solutions to the Public, Public Transit problem in the
 * shortestTimeToTravelTo method. There is an existing implementation of a
 * shortest-paths algorithm. As it is, you can run this class and get the solutions
 * from the existing shortest-path algorithm.
 */
public class FastestRoutePublicTransit {

  /**
   * The algorithm that could solve for shortest travel time from a station S
   * to a station T given various tables of information about each edge (u,v)
   *
   * @param S the s th vertex/station in the transit map, start From
   * @param T the t th vertex/station in the transit map, end at
   * @param startTime the start time in terms of number of minutes from 5:30am
   * @param lengths lengths[u][v] The time it takes for a train to get between two adjacent stations u and v
   * @param first first[u][v] The time of the first train that stops at u on its way to v, int in minutes from 5:30am
   * @param freq freq[u][v] How frequently is the train that stops at u on its way to v
   * @return shortest travel time between S and T
   */
  public int myShortestTravelTime(
    int S,
    int T,
    int startTime,
    int[][] lengths,
    int[][] first,
    int[][] freq
  ) {
    int numVertices = lengths[0].length;

    // This is the array where we'll store all the final shortest times
    int[] times = new int[numVertices];

    // processed[i] will true if vertex i's shortest time is already finalized
    Boolean[] processed = new Boolean[numVertices];

    // Initialize all distances as INFINITE and processed[] as false
    for (int v = 0; v < numVertices; v++) {
      times[v] = Integer.MAX_VALUE;
      processed[v] = false;
    }

    // Distance of source vertex from itself is always 0
    times[S] = 0;

    // Find shortest path to all the vertices
    for (int count = 0; count < numVertices - 1 ; count++) {
      // Pick the minimum distance vertex from the set of vertices not yet processed.
      // u is always equal to source in first iteration.
      // Mark u as processed.
      int u = findNextToProcess(times, processed);
      processed[u] = true;

      // Update time value of all the adjacent vertices of the picked vertex.
      for (int v = 0; v < numVertices; v++) {
        // Update time[v] only if is not processed yet, there is an edge from u to v,
        // and total weight of path from source to v through u is smaller than current value of time[v]
        if (!processed[v] && lengths[u][v]!=0 && times[u] != Integer.MAX_VALUE && findNextTrain(first[u][v], freq[u][v], startTime) + times[u]+lengths[u][v] < times[v]) {
          times[v] = findNextTrain(first[u][v], freq[u][v], startTime) + times[u] + lengths[u][v];
          startTime+=times[v];
        }
      }
    }
    return times[T];
  }

  // return the time left to the next train
  private int findNextTrain(int first, int frequency, int startTime) {
    int i = 0;
    while (true) {
      if (first + (i * frequency) >= startTime) {
        return first + (i * frequency) - startTime;
      }
      i++;
    }
  }

  /**
   * Finds the vertex with the minimum time from the source that has not been
   * processed yet.
   * @param times The shortest times from the source
   * @param processed boolean array tells you which vertices have been fully processed
   * @return the index of the vertex that is next vertex to process
   */
  public int findNextToProcess(int[] times, Boolean[] processed) {
    int min = Integer.MAX_VALUE;
    int minIndex = -1;

    for (int i = 0; i < times.length; i++) {
      if (processed[i] == false && times[i] <= min) {
        min = times[i];
        minIndex = i;
      }
    }
    return minIndex;
  }

  public void printShortestTimes(int times[]) {
    System.out.println("Vertex Distances (time) from Source");
    for (int i = 0; i < times.length; i++)
        System.out.println(i + ": " + times[i] + " minutes");
  }

  public void printShortestTime(int start, int end, int time) {
    System.out.println("Vertex Distances (time) from " + start + " to " + end + " - " + time);
  }

  /**
   * Given an adjacency matrix of a graph, implements
   * @param graph The connected, directed graph in an adjacency matrix where
   *              if graph[i][j] != 0 there is an edge with the weight graph[i][j]
   * @param source The starting vertex
   */
  public void shortestTime(int graph[][], int source) {
    int numVertices = graph[0].length;

    // This is the array where we'll store all the final shortest times
    int[] times = new int[numVertices];

    // processed[i] will true if vertex i's shortest time is already finalized
    Boolean[] processed = new Boolean[numVertices];

    // Initialize all distances as INFINITE and processed[] as false
    for (int v = 0; v < numVertices; v++) {
      times[v] = Integer.MAX_VALUE;
      processed[v] = false;
    }

    // Distance of source vertex from itself is always 0
    times[source] = 0;

    // Find shortest path to all the vertices
    for (int count = 0; count < numVertices - 1 ; count++) {
      // Pick the minimum distance vertex from the set of vertices not yet processed.
      // u is always equal to source in first iteration.
      // Mark u as processed.
      int u = findNextToProcess(times, processed);
      processed[u] = true;

      // Update time value of all the adjacent vertices of the picked vertex.
      for (int v = 0; v < numVertices; v++) {
        // Update time[v] only if is not processed yet, there is an edge from u to v,
        // and total weight of path from source to v through u is smaller than current value of time[v]
        if (!processed[v] && graph[u][v]!=0 && times[u] != Integer.MAX_VALUE && times[u]+graph[u][v] < times[v]) {
          times[v] = times[u] + graph[u][v];
        }
      }
    }

    printShortestTimes(times);
  }

  public static void main (String[] args) {
    /* length(e) */
    int lengthTimeGraph[][] = new int[][]{
      {0, 4, 0, 0, 0, 0, 0, 8, 0},
      {4, 0, 8, 0, 0, 0, 0, 11, 0},
      {0, 8, 0, 7, 0, 4, 0, 0, 2},
      {0, 0, 7, 0, 9, 14, 0, 0, 0},
      {0, 0, 0, 9, 0, 10, 0, 0, 0},
      {0, 0, 4, 14, 10, 0, 2, 0, 0},
      {0, 0, 0, 0, 0, 2, 0, 1, 6},
      {8, 11, 0, 0, 0, 0, 1, 0, 7},
      {0, 0, 2, 0, 0, 0, 6, 7, 0}
    };
    /* first(e) */
    int firstGraph[][] = new int[][]{
      {0, 4, 0, 0, 0, 0, 0, 18, 0},
      {14, 0, 18, 0, 0, 0, 10, 11, 0},
      {0, 18, 10, 17, 10, 14, 0, 0, 12},
      {0, 0, 17, 0, 19, 14, 0, 0, 0},
      {0, 0, 0, 9, 0, 10, 0, 0, 0},
      {0, 0, 14, 14, 10, 0, 12, 0, 0},
      {0, 0, 0, 0, 0, 12, 0, 11, 16},
      {18, 11, 0, 0, 0, 0, 11, 0, 17},
      {0, 0, 12, 0, 0, 0, 16, 17, 0}
    };
    /* freq(e) */
    int freqGraph[][] = new int[][]{
      {0, 5, 0, 0, 0, 0, 0, 10, 0},
      {12, 0, 20, 0, 0, 0, 10, 15, 0},
      {0, 18, 10, 25, 10, 30, 0, 0, 14},
      {0, 0, 15, 0, 11, 14, 0, 0, 0},
      {0, 0, 0, 19, 0, 10, 0, 0, 0},
      {0, 0, 15, 14, 13, 0, 8, 0, 0},
      {0, 0, 0, 0, 0, 10, 0, 14, 16},
      {20, 11, 0, 0, 0, 0, 10, 0, 14},
      {0, 0, 12, 0, 0, 0, 15, 18, 0}
    };
    FastestRoutePublicTransit t = new FastestRoutePublicTransit();
    t.shortestTime(lengthTimeGraph, 0);

    // You can create a test case for your implemented method below
    t.printShortestTime(0,2,t.myShortestTravelTime(0, 2, 30, lengthTimeGraph,  firstGraph, freqGraph));
    t.printShortestTime(3,7,t.myShortestTravelTime(3, 7, 20, lengthTimeGraph,  firstGraph, freqGraph));
    t.printShortestTime(1,5,t.myShortestTravelTime(1, 5, 55, lengthTimeGraph,  firstGraph, freqGraph));
    t.printShortestTime(8,6,t.myShortestTravelTime(8, 6, 200, lengthTimeGraph,  firstGraph, freqGraph));
  }
}
