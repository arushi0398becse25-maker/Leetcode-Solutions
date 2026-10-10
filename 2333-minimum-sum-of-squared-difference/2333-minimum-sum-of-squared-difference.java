class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];
        int max = 0;

        long k = (long) k1 + k2;
        long sum = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
            sum += diff[i];
        }

        if (sum <= k) {
            return 0;
        }

        int low = 0, high = max;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long need = 0;

            for (int d : diff) {
                if (d > mid) {
                    need += d - mid;
                }
            }

            if (need <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int target = low;

        for (int i = 0; i < n; i++) {
            if (diff[i] > target) {
                k -= diff[i] - target;
                diff[i] = target;
            }
        }

        for (int i = 0; i < n && k > 0; i++) {
            if (diff[i] == target && target > 0) {
                diff[i]--;
                k--;
            }
        }

        long ans = 0;

        for (int d : diff) {
            ans += (long) d * d;
        }

        return ans;
    }
}