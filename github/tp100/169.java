/*
169. Majority Element
DescriptionHintsSubmissionsDiscussSolution
DiscussPick One
Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

Credits:
Special thanks to @ts for adding this problem and creating all test cases.


*/
class Solution {
    public int majorityElement(int[] nums) {
        // Arrays.sort(nums);
        // return nums[nums.length/2];
        
        
        int major = nums[0],count =1;
        for(int i=1;i<nums.length;i++){
            if(count==0) {count++;major = nums[i];}
            else if(nums[i] == major) {count++;}
            else count--;
        }
        return major;
    }
}