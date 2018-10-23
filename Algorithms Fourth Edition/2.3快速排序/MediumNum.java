public class MediumNum {
    /**
     * @param nums: A list of integers
     * @return: An integer denotes the middle number of the array
     * 

       整体思路:
       1.对数组进行划分，参照荷兰国旗。  
       2.数组被划分成三部分 （<v)(=v)(>v) 计算每部分的个数
       3.
        3.1如果(<v）部分的个数大于 N/2那么说明中位数还在这部分中，对这部分进行递归调用partition
        3.2如果(<v)+(=v)两部分的个数刚好大于等于N/2,由于(<v)部分的个数<N/2,那么中位数只会在(=v)这部分中
        3.3如果(<v)+(=v)两部分的个数小于N/2，那么中位数只会在(>v)这部分中，对这部分进行递归调用 partition
     */
    public int median(int[] nums) {
        return partition(nums, 0, nums.length - 1, (nums.length + 1)/2);
    }
    public int partition(int[] nums,int start,int end,int size)
    {
        int lo=start-1;
        int hi=end+1;
        int v=nums[start];
        int index=start;
        //划分数组
        while(index<hi)
        {
            if(nums[index]<v)
            {
                int temp=nums[++lo];
                nums[lo]=nums[index];
                nums[index]=temp;
                index++;
            }
            else if(nums[index]>v)
            {
                int temp=nums[--hi];
                nums[hi]=nums[index];
                nums[index]=temp;
            }
            else{
                index++;
            }
        }
        //中位数位置的判断
        if(lo-start+1>=size)
        {
            return partition(nums,start,lo,size);
        }else if(hi-start>=size)
        {
            return nums[hi-1];
        }
        else {
            return partition(nums,hi,end,size-(hi-start));
        }
    }
}