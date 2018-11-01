public class HIndex{
	/**
    将数组递减的排序后，index>citations[index]时，index便是所求值
	*/
	public int hIndex(int[] citations) {
       quickSort(citations,0,citations.length-1); 
       int index=0;
        while(index<citations.length)
        {
            if(index>=citations[index])
            {
                return index;
            }
            index++;
        }
        return citations.length;
    }
    public void quickSort(int [] citations,int start,int end)
    {
        if(start<end)
        {
            int[] index=partition(citations,start,end);
            quickSort(citations,start,index[0]);
            quickSort(citations,index[1],end);
        }
    }
    public int[] partition(int[] citations,int start,int end)
    {
        int lo=start-1;
        int hi=end+1;
        int midVal=citations[start];
        int index=start;
        while(index<hi)
        {
            if(citations[index]>midVal)
            {
                swap(citations,index,++lo);
                index++;
            }else if(citations[index]<midVal)
            {
                swap(citations,index,--hi);
            }else{
                index++;
            }
        }
        return new int[]{lo,hi};
    }
    public void swap(int[] citations,int i,int j)
    {
        int temp=citations[i];
        citations[i]=citations[j];
        citations[j]=temp;
    }
}