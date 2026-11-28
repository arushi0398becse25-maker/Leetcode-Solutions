
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        int maxFactor = n;
        int[] freq = new int[maxFactor + 1];

        // Count frequency of each factor
        for (int[] item : items) {
            freq[item[0]]++;
        }

        // Count how many items each factor can unlock
        for (int i = 1; i <= maxFactor; i++) {
            for (int j = 2 * i; j <= maxFactor; j += i) {
                freq[i] += freq[j];
            }
        }

        // Sort items by price
        Arrays.sort(items, (a, b) ->
            Integer.compare(a[1], b[1])
        );

        int minPrice = items[0][1];
        long remaining = budget;
        long bonusItems = 0;

        // Buy items that provide free items
        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            if (price > 2L * minPrice) {
                break;
            }

            int freeCount = freq[factor] - 1;

            long take = Math.min(
                freeCount,
                remaining / price
            );

            bonusItems += take;
            remaining -= take * price;
        }

        // Spend remaining budget on cheapest item
        long answer = 2 * bonusItems
                    + remaining / minPrice;

        return (int) answer;
    }
}
