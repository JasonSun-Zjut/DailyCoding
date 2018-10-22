public class Solution {
    /**
     * @param A: an integer array
     * @param aux:辅助数组
     * @return: nothing
     */
    private int[] aux;
    public void sortIntegers2(int[] A) {
        // write your code here
        int length=A.length;
         aux=new int[length];
        divide(A,0,length-1);
 }
 public void divide(int[] A,int start,int end)
 {
     if(start>=end)
     {
         return ;
     }
     int mid=(start+end)>>1;
     divide(A,start,mid);
     divide(A,mid+1,end);
     merge(A,start,mid,end);
 }
 public void merge(int[]A,int start,int mid,int end)
 {
     int i=start;
     int j=mid+1;
     for(int k=start;k<=end;k++)
     {
         aux[k]=A[k];
     }
     for(int k=start;k<=end;k++)
     {
         if(i>mid)
         {
             A[k]=aux[j++];
         }
         else if(j>end)
         {
             A[k]=aux[i++];
         }
         else{
             if(aux[i]<aux[j])
             {
                 A[k]=aux[i++];
             }
             else{
                 A[k]=aux[j++];
             }
         }
     }
 }
}