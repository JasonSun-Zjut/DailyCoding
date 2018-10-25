public class Solution {
    /**
     * @param nums: A list of integers
     * @return: the median of numbers
     */
    public int[] medianII(int[] nums) {
        // write your code here
        int len = nums.length;
        int[] res = new int[len];
        /**
          构造大根堆和小根堆
        */
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(new SmallComparator());
        PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new BigComparator());
        
        maxHeap.offer(nums[0]);
        res[0] = nums[0];
        /**
        大根堆中的数均小于小根堆的堆顶，同时大根堆的size保持比小根堆的size大1
        */
        for (int i = 1; i < len; i++) {
            if (nums[i] >= maxHeap.peek()) {
                minHeap.offer(nums[i]);
            } else {
                maxHeap.offer(nums[i]);
            }
            while (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
            while (maxHeap.size() - 1 > minHeap.size()) {
                minHeap.offer(maxHeap.poll());
            }
            res[i] = maxHeap.peek();
        }
        
        return res;
    }
    static class SmallComparator implements Comparator<Integer>
    {
        @Override
		public int compare(Integer i1, Integer i2) {
			return i1-i2;
		}
    }
     static class BigComparator implements Comparator<Integer>
    {
        @Override
		public int compare(Integer i1, Integer i2) {
			return i2-i1;
		}
    }
}