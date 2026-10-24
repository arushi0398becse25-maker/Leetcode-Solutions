class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice,
                                 String arriveBob, String leaveBob) {

        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int start = Math.max(convert(arriveAlice, days),
                             convert(arriveBob, days));

        int end = Math.min(convert(leaveAlice, days),
                           convert(leaveBob, days));

        return Math.max(0, end - start + 1);
    }

    private int convert(String date, int[] days) {
        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(3, 5));

        for (int i = 0; i < month - 1; i++) {
            day += days[i];
        }

        return day;
    }
}