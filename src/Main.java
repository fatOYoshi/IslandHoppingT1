import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Locale;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(
                new File("dados/entradas_do_problema.txt")
        ).useLocale(Locale.US);

        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        for (int t = 0; t < n; t++) {
            int m = scanner.nextInt();

            double[] x = new double[m];
            double[] y = new double[m];

            for (int i = 0; i < m; i++) {
                x[i] = scanner.nextDouble();
                y[i] = scanner.nextDouble();
            }


            MinPQ<Edge> pq = new MinPQ<>();
            for (int i = 0; i < m; i++) {
                for (int j = i + 1; j < m; j++) {
                    double dx = x[i] - x[j];
                    double dy = y[i] - y[j];
                    double weight = Math.sqrt(dx * dx + dy * dy);
                    pq.insert(new Edge(i, j, weight));
                }
            }


            UF uf = new UF(m);
            double totalWeight = 0.0;
            int edgesCount = 0;

            while (!pq.isEmpty() && edgesCount < m - 1) {
                Edge e = pq.delMin();
                int v = e.either();
                int w = e.other(v);

                if (!uf.connected(v, w)) {
                    uf.union(v, w);

                    System.out.println(v + " - " + w + " : " + e.weight());

                    totalWeight += e.weight();
                    edgesCount++;
                }
            }

            System.out.println("Custo usando MST:");
            System.out.printf(Locale.US, "%.12f\n", totalWeight);
        }

        scanner.close();
    }


    public static class Edge implements Comparable<Edge> {
        private final int v;
        private final int w;
        private final double weight;

        public Edge(int v, int w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        public double weight() {
            return weight;
        }

        public int either() {
            return v;
        }

        public int other(int vertex) {
            if (vertex == v) return w;
            else if (vertex == w) return v;
            else throw new IllegalArgumentException("Vértice inválido");
        }

        @Override
        public int compareTo(Edge that) {
            return Double.compare(this.weight, that.weight);
        }
    }


    public static class UF {
        private int[] parent;
        private byte[] rank;
        private int count;

        public UF(int n) {
            if (n < 0) throw new IllegalArgumentException();
            count = n;
            parent = new int[n];
            rank = new byte[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }

        public int count() {
            return count;
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;


            if (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
            else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
            else {
                parent[rootQ] = rootP;
                rank[rootP]++;
            }
            count--;
        }
    }


    public static class MinPQ<Key extends Comparable<Key>> {
        private Key[] pq;
        private int n;

        @SuppressWarnings("unchecked")
        public MinPQ(int initCapacity) {
            pq = (Key[]) new Comparable[initCapacity + 1];
            n = 0;
        }

        public MinPQ() {
            this(1);
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public void insert(Key x) {

            if (n == pq.length - 1) resize(2 * pq.length);
            pq[++n] = x;
            swim(n);
        }

        public Key delMin() {
            if (isEmpty()) throw new java.util.NoSuchElementException("Fila vazia");
            Key min = pq[1];
            exch(1, n--);
            sink(1);
            pq[n + 1] = null;
            if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);
            return min;
        }


        private void swim(int k) {
            while (k > 1 && greater(k / 2, k)) {
                exch(k / 2, k);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= n) {
                int j = 2 * k;
                if (j < n && greater(j, j + 1)) j++;
                if (!greater(k, j)) break;
                exch(k, j);
                k = j;
            }
        }

        private boolean greater(int i, int j) {
            return pq[i].compareTo(pq[j]) > 0;
        }

        private void exch(int i, int j) {
            Key swap = pq[i];
            pq[i] = pq[j];
            pq[j] = swap;
        }

        @SuppressWarnings("unchecked")
        private void resize(int capacity) {
            Key[] temp = (Key[]) new Comparable[capacity];
            for (int i = 1; i <= n; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
        }
    }
}