
class Solution {
    public int numOfSubarrays(int[] arr) {
        int MOD = 1000000007;

        long even = 1;
        long odd = 0;
        long prefixSum = 0;
        long ans = 0;

        for (int num : arr) {
            prefixSum += num;

            if (prefixSum % 2 == 0) {
                ans += odd;
                even++;
            } else {
                ans += even;
                odd++;
            }

            ans %= MOD;
        }

        return (int) ans;
    }
}
