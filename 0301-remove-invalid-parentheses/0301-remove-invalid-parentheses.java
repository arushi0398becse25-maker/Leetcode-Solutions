

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();

        int left = 0;
        int right = 0;

        // Count extra '(' and ')'
        for (char c : s.toCharArray()) {
            if (c == '(') {
                left++;
            } else if (c == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }

        remove(s, 0, left, right, ans);

        return ans;
    }

    private void remove(String s, int start, int left, int right,
                        List<String> ans) {

        if (left == 0 && right == 0) {
            if (isValid(s)) {
                ans.add(s);
            }
            return;
        }

        for (int i = start; i < s.length(); i++) {

            // Avoid duplicate results
            if (i != start && s.charAt(i) == s.charAt(i - 1)) {
                continue;
            }

            // Remove '('
            if (left > 0 && s.charAt(i) == '(') {
                String newString = s.substring(0, i) + s.substring(i + 1);
                remove(newString, i, left - 1, right, ans);
            }

            // Remove ')'
            if (right > 0 && s.charAt(i) == ')') {
                String newString = s.substring(0, i) + s.substring(i + 1);
                remove(newString, i, left, right - 1, ans);
            }
        }
    }

    private boolean isValid(String s) {
        int count = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;

                if (count < 0) {
                    return false;
                }
            }
        }

        return count == 0;
    }
}