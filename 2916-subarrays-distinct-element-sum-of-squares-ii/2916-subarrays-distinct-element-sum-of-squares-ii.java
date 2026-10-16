class Solution {
    public int sumCounts(int[] nums) {
        int n = nums.length;
        long mod = 1000000007;
        long[] sum = new long[4 * n];
        long[] sq = new long[4 * n];
        long[] lazy = new long[4 * n];

        int[] last = new int[n + 1];
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int prev = map.getOrDefault(nums[i], -1);
            map.put(nums[i], i);

            update(1, 0, n - 1, prev + 1, i, sum, sq, lazy, mod);

            ans = (ans + sq[1]) % mod;
        }

        return (int) ans;
    }

    void update(int node, int l, int r, int ql, int qr,
                long[] sum, long[] sq, long[] lazy, long mod) {
        if (ql > r || qr < l) return;

        if (ql <= l && r <= qr) {
            long len = r - l + 1;
            sq[node] = (sq[node] + 2 * sum[node] + len) % mod;
            sum[node] = (sum[node] + len) % mod;
            lazy[node]++;
            return;
        }

        push(node, l, r, sum, sq, lazy, mod);

        int mid = (l + r) / 2;

        update(node * 2, l, mid, ql, qr, sum, sq, lazy, mod);
        update(node * 2 + 1, mid + 1, r, ql, qr, sum, sq, lazy, mod);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % mod;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % mod;
    }

    void push(int node, int l, int r,
              long[] sum, long[] sq, long[] lazy, long mod) {
        if (lazy[node] == 0 || l == r) return;

        int mid = (l + r) / 2;
        apply(node * 2, mid - l + 1, lazy[node], sum, sq, lazy, mod);
        apply(node * 2 + 1, r - mid, lazy[node], sum, sq, lazy, mod);

        lazy[node] = 0;
    }

    void apply(int node, long len, long v,
               long[] sum, long[] sq, long[] lazy, long mod) {
        sq[node] = (sq[node] + 2 * v * sum[node] + v * v * len) % mod;
        sum[node] = (sum[node] + v * len) % mod;
        lazy[node] = (lazy[node] + v) % mod;
    }
}