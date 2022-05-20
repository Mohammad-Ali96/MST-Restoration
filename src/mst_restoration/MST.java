package mst_restoration;

import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MST extends JFrame {

    int n;
    int m;
    int[][] adjMatrix;
    int vis[];
    int parent[];
    int distance[];
    ArrayList<ArrayList<Integer>> MST_AdjList;
    int costMST_Again;
    int costMST_minCut;
    String msg_Min_Cut = "";
    String time_min = "";
    String time_again = "";

    MST(int n, int m) {
        this.n = n;
        this.m = m;
        adjMatrix = new int[n][n];
    }

    MST(int n, int m, int[][] adjMatrix) {
        this.n = n;
        this.m = m;
        this.adjMatrix = adjMatrix;
    }

    public void inputMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjMatrix[i][j] = 0;
            }
        }
        System.out.println("Please input u, v, weight");
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            adjMatrix[u][v] = w;
            adjMatrix[v][u] = w;
        }
    }

    public void addEdge(int u, int v, int w) {
        this.adjMatrix[u][v] = w;
        this.adjMatrix[v][u] = w;
    }

    public void addEdgeToMST(int u, int v, int w) {
        this.MST_AdjList.get(u).add(new Integer(v));
        this.MST_AdjList.get(v).add(new Integer(u));

    }

    public int weightMST() {
        int sum = 0;
        for (int i = 0; i < this.n; i++) {
            sum += this.adjMatrix[i][this.parent[i]];
        }
        return sum;
    }

    public void generateRandomAdjMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjMatrix[i][j] = 0;
            }
        }
        Random r = new Random();
        for (int i = 0; i < m;) {
            int u, v, w;
            u = r.nextInt(n);
            v = r.nextInt(n);
            if (u == v) {
                continue;
            }
            if (adjMatrix[u][v] == 0) {
                w = r.nextInt(100);
                adjMatrix[u][v] = w;
                adjMatrix[v][u] = w;
                i++;
            }
        }
    }

    public void init() {
        vis = new int[n];
        parent = new int[n];
        distance = new int[n];
        MST_AdjList = new ArrayList<>();
        for (int i = 0; i < this.n; i++) {
            MST_AdjList.add(new ArrayList<>());
            parent[i] = -4;
            distance[i] = 1000000;
            vis[i] = 0;
        }
        parent[0] = 0;
        distance[0] = 0;
    }

    public int findVertexWithMinKey() {
        int mn = 1000000;
        int node = -1;
        for (int i = 0; i < n; i++) {
            if (vis[i] == 0 && distance[i] < mn) {
                mn = distance[i];
                node = i;
            }
        }
        return node;
    }

    public void runMST() {
        init();

        for (int i = 0; i < n; i++) {
            int node = findVertexWithMinKey();
            vis[node] = 1;
            for (int j = 0; j < n; j++) {
                if (adjMatrix[node][j] > 0 && vis[j] == 0 && adjMatrix[node][j] < distance[j]) {
                    distance[j] = adjMatrix[node][j];
                    parent[j] = node;
                }
            }
        }
        // for saving Tree of MST in adjacencey List
        saveMST();
    }

    public void saveMST() {
        for (int i = 0; i < n; i++) {
            if (i != parent[i]) {
                MST_AdjList.get(i).add(parent[i]);
                MST_AdjList.get(parent[i]).add(i);
            }
        }
    }

    public void dfs(int u) {
        vis[u] = 1;
//        System.out.println(u);
        for (int i = 0; i < n; i++) {
            if (adjMatrix[u][i] > 0 && vis[i] == 0) {
                dfs(i);
            }
        }
    }

    public boolean isGrraphConnected() {
        clearVis();

        dfs(0);

        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {

                return false;
            }
        }

        return true;

    }

    public void dfs_MST(int u) {
        vis[u] = 1;
        System.out.println(u);
        for (int i = 0; i < MST_AdjList.get(u).size(); i++) {
            if (vis[MST_AdjList.get(u).get(i)] == 0) {
                dfs_MST(MST_AdjList.get(u).get(i));
            }
        }
    }

    public void deleteEdge(int u, int v) {

        int weight = adjMatrix[u][v];
        adjMatrix[u][v] = 0;
        adjMatrix[v][u] = 0;

        boolean isConnected = isGrraphConnected();
        System.out.println(isConnected);
        if (!isConnected) {
            JOptionPane.showMessageDialog(this, "The Graph became disconnected");
        } else {
            JOptionPane.showMessageDialog(this, "The Graph still connected after deleting (u,v) = (" + u + ", " + v + ")");
            clearVis();
            for (int i = 0; i < MST_AdjList.get(u).size(); i++) {
                if (MST_AdjList.get(u).get(i) == v) {
                    MST_AdjList.get(u).remove(i);
                    break;
                }
            }

            for (int i = 0; i < MST_AdjList.get(v).size(); i++) {
                if (MST_AdjList.get(v).get(i) == u) {
                    MST_AdjList.get(v).remove(i);
                    break;
                }
            }

            dfs_MST(0);
            boolean isMstConnected = true;
            for (int i = 0; i < n; i++) {
                if (vis[i] == 0) {
                    isMstConnected = false;
                    break;
                }
            }
            if (isMstConnected) {
                JOptionPane.showMessageDialog(this, "The Tree of MST still connected after remove edge (u,v) = (" + u + ", " + v + ")");
            } else {
                JOptionPane.showMessageDialog(this, "The Tree of MST became T1, T2");

                double start = System.currentTimeMillis();

                ArrayList<Integer> T1 = new ArrayList<>();
                ArrayList<Integer> T2 = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if (vis[i] == 1) {
                        T1.add(new Integer(i));
                    } else {
                        T2.add(new Integer(i));
                    }
                }
                System.out.println("size of cut");
                System.out.println(T1.size());
                System.out.println(T2.size());

                int minU = -1, minV = -1;
                int minDistance = Integer.MAX_VALUE;
                for (int i = 0; i < T1.size(); i++) {
                    for (int j = 0; j < T2.size(); j++) {
                        if (adjMatrix[T1.get(i)][T2.get(j)] > 0 && adjMatrix[T1.get(i)][T2.get(j)] < minDistance) {
                            minDistance = adjMatrix[T1.get(i)][T2.get(j)];
                            minU = T1.get(i);
                            minV = T2.get(j);
                        }
                    }
                }

                System.out.println(minU + ", " + minV + " = " + minDistance);
                msg_Min_Cut += minU + " ---> " + minV + "\n";
                System.out.println("Add minmum weight edge to MST");
                addEdgeToMST(minU, minV, minDistance);
                addEdgeToMST(minV, minU, minDistance);

                double end = System.currentTimeMillis();
                double duration = (end - start) * 0.001;
                this.time_min = "" + duration;

                // return the edge deleted
                adjMatrix[u][v] = weight;
                adjMatrix[v][u] = weight;

                System.out.println("The Weiht Of MST by min edge:");

                costMST_minCut = weightMST() - weight + minDistance;
                System.out.println(costMST_minCut);

                adjMatrix[u][v] = 0;
                adjMatrix[v][u] = 0;
                // the diffrent way implement again prims
                start = System.currentTimeMillis();

                this.runMST();
                System.out.println("The Weight of MST after implement MST again");
                costMST_Again = weightMST();
                System.out.println(costMST_Again);
                end = System.currentTimeMillis();
                duration = (end - start) * 0.001;
                this.time_again = "" + duration;

                adjMatrix[u][v] = weight;
                adjMatrix[v][u] = weight;

            }

        }

    }

    public void printAdjMatrix() {
        System.out.println("The Adjacency Matrix");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public void printMST() {
        System.out.println("Minimum Spaning Tree is: ");
        for (int i = 0; i < n; i++) {
            System.out.println(i + "  " + parent[i]);
        }
    }

    public void clearVis() {
        vis = new int[this.n];
        for (int i = 0; i < n; i++) {
            vis[i] = 0;
        }
    }

}
