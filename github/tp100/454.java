/*
454. 4Sum II
DescriptionHintsSubmissionsDiscussSolution
DiscussPick One
Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are such that A[i] + B[j] + C[k] + D[l] is zero.

To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500. All integers are in the range of -228 to 228 - 1 and the result is guaranteed to be at most 231 - 1.

Example:

Input:
A = [ 1, 2]
B = [-2,-1]
C = [-1, 2]
D = [ 0, 2]

Output:
2

Explanation:
The two tuples are:
1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0

*/
class Solution {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer,Integer> sums = new HashMap<>();
        int count = 0;
        for(int a =0; a < A.length;a++){
            for(int b = 0;b< B.length;b++){
                int sum = A[a] + B[b];
                if(sums.containKey(sum)){
                    sums.put(sum,sums.get(sum)+1);
                }
                else sums.put(sum,1);
            }
     }
        for(int c = 0;c<C.length;c++){
            for(int d = 0;d<D.length;d++){
                int sum = -(C[c] + D[d]);
                if(sums.containKey(sum)){
                    count+=sums.get(sum);
                }
            }
            
        }
        return count;
    }
}