class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int first = -1;
        int prev = -1;

        int min = Integer.MAX_VALUE;
        int max = -1;

        int index = 1;

        ListNode previous = head;
        ListNode current = head.next;

        while (current != null && current.next != null) {

            ListNode next = current.next;

            // Check critical point
            if ((current.val > previous.val && current.val > next.val) ||
                (current.val < previous.val && current.val < next.val)) {

                // First critical point
                if (first == -1) {
                    first = index;
                }

                // If previous critical point exists
                if (prev != -1) {
                    int distance = index - prev;

                    min = Math.min(min, distance);
                    max = index - first;
                }

                prev = index;
            }

            previous = current;
            current = next;
            index++;
        }

        // Less than 2 critical points
        if (prev == -1 || first == prev) {
            return new int[]{-1, -1};
        }

        return new int[]{min, max};
    }
}