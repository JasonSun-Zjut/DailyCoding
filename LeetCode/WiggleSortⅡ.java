public class WiggleSortⅡ{
	/**
    数组排序后，选取中间节点和尾节点，交替输入
	*/
	public void wiggleSort(int[] nums) {
        int[] temp=new int[nums.length];
        for (int i = 0; i <nums.length ; i++) {
            temp[i]=nums[i];
        }
        quickSort(temp, 0, temp.length - 1);
        int i=(nums.length+1)/2;
        int j=nums.length;
        for (int index = 0; index < nums.length; ++index) {
            nums[index] = (index & 1)==0 ? temp[--i] : temp[--j];
        }
    }

    public void quickSort(int[] nums, int start, int end) {
        if (start < end) {
            int[] index = partition(nums, start, end);
            quickSort(nums, start, index[0]);
            quickSort(nums, index[1], end);
        }
    }

    public int[] partition(int[] nums, int start, int end) {
        int lo = start - 1;
        int hi = end + 1;
        int index = start;
        int midVal = nums[start];
        while (index < hi) {
            if (nums[index] < midVal) {
                swap(nums, index++, ++lo);
            } else if (nums[index] > midVal) {
                swap(nums, index, --hi);
            } else {
                index++;
            }
        }
        return new int[]{lo, hi};
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}