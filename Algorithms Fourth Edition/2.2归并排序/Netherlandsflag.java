package test;

/**
 * @author
 * @date /10/22
 */
public class Netherlandsflag {
    /***
     * @param A 排序的数组
     * @param midValue 中间的值,左侧的所有数都小于midValue,右侧的所有值都大于midValue
     */

    public static  void sort(int[] A,int midValue)
    {
        int lo=-1;
        int hi=A.length;
        int current=0;
        while(current<hi)
        {
            if(A[current]<midValue)
            {
                  /**把current指向的元素与lo后一位交换,完成后current继续向前移动*/
                  int temp=A[++lo];
                  A[lo]=A[current];
                  A[current]=temp;
                  current++;
            }
            else if(A[current]>midValue)
            {   /**把current指向的元素与hi前一位交换,完成后current保持不变*/
                int temp=A[--hi];
                A[hi]=A[current];
                A[current]=temp;
            }
            else{
                current++;
            }
        }
    }
}
