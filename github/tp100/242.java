/*
242. Valid Anagram
DescriptionHintsSubmissionsDiscussSolution
DiscussPick One
Given two strings s and t, write a function to determine if t is an anagram of s.

For example,
s = "anagram", t = "nagaram", return true.
s = "rat", t = "car", return false.

Note:
You may assume the string contains only lowercase alphabets.

Follow up:
What if the inputs contain unicode characters? How would you adapt your solution to such case?

*/
class Solution {
    public boolean isAnagram(String s, String t) {
        if(s.length()!=t.length()) return false;
        int freq[] = new int[26];
        for(int i =0;i<s.length();i++){
            freq[s.charAt(i)-'a']++;
            freq[t.charAt(i)-'a']--;
        }  
        for(int i =0;i<26;i++){
            
            if( freq[i]!=0) return false;
        } 
        return true;
            

    }
}