
import java.util.*;

class Solution {
    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        List<Integer>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        int[][] up = new int[LOG][n];
        int[] depth = new int[n];
        int[] tin = new int[n];
        int[] tout = new int[n];
        int[] prefix = new int[n];
        int[] order = new int[n];
        int[] parent = new int[n];
        int[] iter = new int[n];

        Arrays.fill(parent, -1);

        char[] chars = s.toCharArray();
        int timer = 0;

        // Iterative DFS: avoids stack overflow
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        parent[0] = 0;
        prefix[0] = 1 << (chars[0] - 'a');

        while (top > 0) {
            int u = stack[top - 1];

            if (iter[u] == 0) {
                tin[u] = timer;
                order[timer++] = u;

                up[0][u] = parent[u];
                for (int j = 1; j < LOG; j++) {
                    up[j][u] = up[j - 1][up[j - 1][u]];
                }
            }

            if (iter[u] < adj[u].size()) {
                int v = adj[u].get(iter[u]++);
                if (v == parent[u]) continue;

                parent[v] = u;
                depth[v] = depth[u] + 1;
                prefix[v] = prefix[u] ^ (1 << (chars[v] - 'a'));
                stack[top++] = v;
            } else {
                tout[u] = timer - 1;
                top--;
            }
        }

        // Fenwick tree for range XOR updates and point queries
        int[] bit = new int[n + 2];

        class Fenwick {
            void add(int idx, int val) {
                for (idx++; idx < bit.length; idx += idx & -idx) {
                    bit[idx] ^= val;
                }
            }

            int pointQuery(int idx) {
                int res = 0;
                for (idx++; idx > 0; idx -= idx & -idx) {
                    res ^= bit[idx];
                }
                return res;
            }

            void rangeXor(int l, int r, int val) {
                add(l, val);
                if (r + 1 < n) add(r + 1, val);
            }
        }

        Fenwick fw = new Fenwick();

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');
                int delta = oldMask ^ newMask;

                fw.rangeXor(tin[u], tout[u], delta);
                chars[u] = c;
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int a = u;
                int b = v;

                if (depth[a] < depth[b]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }

                int diff = depth[a] - depth[b];

                for (int j = 0; j < LOG; j++) {
                    if ((diff & (1 << j)) != 0) {
                        a = up[j][a];
                    }
                }

                if (a != b) {
                    for (int j = LOG - 1; j >= 0; j--) {
                        if (up[j][a] != up[j][b]) {
                            a = up[j][a];
                            b = up[j][b];
                        }
                    }
                    a = parent[a];
                }

                int lca = a;

                int pu = prefix[u] ^ fw.pointQuery(tin[u]);
                int pv = prefix[v] ^ fw.pointQuery(tin[v]);
                int mask = pu ^ pv
                        ^ (1 << (chars[lca] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }
}
