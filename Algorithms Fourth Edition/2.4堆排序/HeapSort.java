/**
 * @author
 * @date /10/24
 */
public class HeapSort {
    /**
     * 堆排序
     * */
    public static void heapSort(int[] nums)
    {
        heapinsert(nums);
        for(int heapsize=nums.length;heapsize>0;heapsize--)
        {
            heapTop(nums,heapsize);
        }
    }
    /**
     * 根据给定的数组构造一个大根堆
     * */
    private static void heapinsert(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            heapinsert(i, nums);
        }
    }
    /**
     * 插入大根堆后判断是否需要进行调整
     * */
    private static void heapinsert(int index, int nums[]) {
        if (index == 0) {
            return;
        }
        int parent = (index - 1) / 2;
        if (nums[parent] < nums[index]) {
            int temp = nums[parent];
            nums[parent] = nums[index];
            nums[index] = temp;
        }
        heapinsert(parent, nums);
    }
    /**
     * 大根堆内部节点的值发生改变，进行堆的重构
     * @param nums:数组
     * @param index:节点的下标
     * @param heapsize:堆的大小
     * */
    private static void heapify(int nums[], int index, int heapsize) {
        //进行向下调整大根堆
        int left = index * 2 + 1;
        if (left < heapsize) {
            int largest = left + 1 < heapsize && nums[left + 1] > nums[left] ? left + 1 : left;
            largest = nums[largest] > nums[index] ? largest : index;
            //修改的节点仍然是最大值，最return
            if (largest == index) {
                return;
            }
            //与孩子节点中最大的节点进行交换
            int temp = nums[largest];
            nums[largest] = nums[index];
            nums[index] = temp;
            //递归的向下调整
            heapify(nums, largest, heapsize);
        }
    }
  /**
   * 返回大根堆的堆顶
   * @param nums:数组
   * @param heapsize:堆的大小
   * */
  private static int heapTop(int nums[],int heapsize) {
        int tail=heapsize-1;
        int top = nums[0];
        nums[0] = nums[tail];
        nums[tail] = top;
        //向下调整
        heapify(nums, 0, heapsize-1);
        return top;
    }

    public static void main(String[] args) {
        int [] nums={1,9,2,8,3,7,4,6,5};
        heapSort(nums);
    }
}
