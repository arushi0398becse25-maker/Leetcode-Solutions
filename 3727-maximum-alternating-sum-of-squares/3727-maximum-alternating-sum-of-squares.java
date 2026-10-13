

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;

        // Square every element
        for (int i = 0; i < n; i++) {
            nums[i] = nums[i] * nums[i];
        }

        // Sort squared values
        Arrays.sort(nums);

        long ans = 0;
        int mid = n / 2;

        // Subtract the smaller half
        for (int i = 0; i < mid; i++) {
            ans -= nums[i];
        }

        // Add the larger half
        for (int i = mid; i < n; i++) {
            ans += nums[i];
        }

        return ans;
    }
}