/**
 * @author
 * @date /10/21
 */
public class MergeSort {

    public static void MergeSelf(int[] array,int lo,int mid,int hi,int[] aux)
    {

        for(int k=lo;k<=hi;k++)
        {
            aux[k]=array[k];
        }
        int i=lo;
        int j=mid+1;
        for(int k=lo;k<=hi;k++)
        {
            if(i>mid)
            {
                array[k]=aux[j++];
            }
            else if(j>hi)
            {
                array[k]=aux[i++];
            }
            else {
                if(aux[i]>aux[j])
                {
                 array[k]=aux[j++];
                }
                else{
                    array[k]=aux[i++];
                }
            }
        }
    }
    public static void sort(int[] array)
    {
        int length=array.length;
        int[]aux=new int[length];
        divideSort(array,0,length-1,aux);

    }
    private static void divideSort(int[] array,int start,int end,int[] aux)
    {
        if(start==end)
        {
            return ;
        }
        int mid=(start+end)>>1;
        divideSort(array,start,mid,aux);
        divideSort(array,mid+1,end,aux);
        MergeSelf(array,start,mid,end,aux);
    }

    public static void main(String[] args) {
        int [] array={7,8,9,1,2,3,4,5,6};
        sort(array);
        System.out.println(array);
    }
}
