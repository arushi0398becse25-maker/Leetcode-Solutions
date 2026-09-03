class Solution {
    public boolean uniformArray(int[] nums1) {
        
        int min = nums1[0];

        
        for (int x : nums1) {
            if (x < min) {
                min = x;
            }
        }

       
        if (min % 2 != 0) {
            return true;
        }

        
        for (int x : nums1) {
            if (x % 2 != 0) {
                return false;
            }
        }

        return true;
    }
}