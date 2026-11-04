import java.util.*;

class Solution {

    class DualHeap {
        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        int leftSize = 0, rightSize = 0;
        long leftSum = 0, rightSum = 0;

        void add(TreeMap<Integer, Integer> map, int val) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

        void remove(TreeMap<Integer, Integer> map, int val) {
            int count = map.get(val);

            if (count == 1) map.remove(val);
            else map.put(val, count - 1);
        }

        void insert(int val) {
            if (left.isEmpty() || val <= left.lastKey()) {
                add(left, val);
                leftSize++;
                leftSum += val;
            } else {
                add(right, val);
                rightSize++;
                rightSum += val;
            }
            balance();
        }

        void erase(int val) {
            if (left.containsKey(val)) {
                remove(left, val);
                leftSize--;
                leftSum -= val;
            } else {
                remove(right, val);
                rightSize--;
                rightSum -= val;
            }
            balance();
        }

        void balance() {
            while (leftSize > rightSize + 1) {
                int val = left.lastKey();
                remove(left, val);
                leftSize--;
                leftSum -= val;

                add(right, val);
                rightSize++;
                rightSum += val;
            }

            while (leftSize < rightSize) {
                int val = right.firstKey();
                remove(right, val);
                rightSize--;
                rightSum -= val;

                add(left, val);
                leftSize++;
                leftSum += val;
            }
        }

        long getCost() {
            long median = left.lastKey();

            return median * leftSize - leftSum
                    + rightSum - median * rightSize;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Step 1: Calculate cost of every window
        long[] cost = new long[n - x + 1];

        DualHeap dh = new DualHeap();

        for (int i = 0; i < x; i++) {
            dh.insert(nums[i]);
        }

        cost[0] = dh.getCost();

        for (int i = x; i < n; i++) {

            dh.erase(nums[i - x]);
            dh.insert(nums[i]);

            cost[i - x + 1] = dh.getCost();
        }

        // Step 2: Dynamic Programming
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        Arrays.fill(prev, 0);

        for (int t = 1; t <= k; t++) {

            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Skip current element
                curr[i] = curr[i - 1];

                // Select a window of size x
                if (i >= x) {

                    curr[i] = Math.min(
                        curr[i],
                        prev[i - x] + cost[i - x]
                    );
                }
            }

            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}