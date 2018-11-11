/**
 * @author
 * @date /11/11
 *
 *
 *
 * The Pigeonhole Principle
 * 如果存在n个element和m个桶(m<n),那么必定存在某个桶，该桶中含有的element大于1.
 *
 * bucketSize:每个桶中数的容量
 * bucketNum：桶的个数
 * DistributeNums:将数组中的数分配到各个桶中
 */
public class Bucket {
    public int maximumGap(int[] nums) {
        if (nums.length == 0 || nums.length < 2) {
            return 0;
        }
        int[] maxAndmin = MaxAndMin(nums);
        if (maxAndmin[0] == maxAndmin[1]) {
            return 0;
        }
        int bucketSize =Integer.max(1,(maxAndmin[1]-maxAndmin[0])/(nums.length-1));
        int bucketNum = (maxAndmin[1] - maxAndmin[0]) / bucketSize + 1;
        int[] bucketMax = new int[bucketNum];
        int[] bucketMin = new int[bucketNum];
        boolean[] booleans = new boolean[bucketNum];
        DistributeNums(bucketMin, bucketMax, booleans, nums, maxAndmin[0], bucketSize);
        int maxGap = 0;
        for (int i = 0; i < bucketNum - 1; i++) {
            if (booleans[i]) {
                for (int j = i + 1; j < bucketNum; j++) {
                    if (booleans[j]) {
                        maxGap = (bucketMin[j] - bucketMax[i]) > maxGap ? bucketMin[j] - bucketMax[i] : maxGap;
                        break;
                    }
                }
            }
        }
        return maxGap;
    }

    private int[] MaxAndMin(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            min = nums[i] < min ? nums[i] : min;
            max = nums[i] > max ? nums[i] : max;
        }
        return new int[]{min, max};
    }

    private void DistributeNums(int[] bucketMin, int[] bucketMax, boolean[] booleans, int[] nums, int min, int bucketsize) {
        for (int i = 0; i < nums.length; i++) {
            int index = (nums[i] - min) / bucketsize;
            if (!booleans[index]) {
                booleans[index] = true;
                bucketMin[index] = nums[i];
                bucketMax[index] = nums[i];
            } else {
                bucketMin[index] = nums[i] < bucketMin[index] ? nums[i] : bucketMin[index];
                bucketMax[index] = nums[i] > bucketMax[index] ? nums[i] : bucketMax[index];
            }
        }
    }
}
