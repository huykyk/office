import java.util.ArrayList;


public class CountSort {
      public  static void  countSort(ArrayList<Integer> datas,int dataRange)
      {
    	 ArrayList<Integer> output=new ArrayList<Integer>();
         for(int i=0;i<=datas.size();i++)
           	output.add(0);
    	  int[] array=new int[dataRange];
    	  int i=0;int j=0;
    	  for(;i<dataRange;i++)
    		  array[i]=0;
    	  for(;j<datas.size();j++)//统计数组中每个数的个数
    		  array[datas.get(j)]=array[datas.get(j)]+1;
    	  for(i=1;i<dataRange;i++)//统计数组中放在其前面的数的个数
    		  array[i]=array[i]+array[i-1];
    	  for(j=datas.size()-1;j>=0;j--)
    	  {
    		  output.set(array[datas.get(j)], datas.get(j));
    		  array[datas.get(j)]=array[datas.get(j)]-1;
    	  }
    	  for(i=0;i<datas.size();i++)
    	  {
    		  System.out.println(output.get(i+1));
    		  datas.set(i, output.get(i+1));
    	  }
    	  System.out.println("");
      }
      public static void main(String[] args)
      {
    	  ArrayList<Integer> datas=new ArrayList<Integer>();
       	datas.add(6);
       	datas.add(5);
       	datas.add(5);
       	datas.add(7);
       	datas.add(2);
       	datas.add(1);
       	datas.add(9);
       	datas.add(1);
       	countSort(datas,10);
       	for(int i=1;i<datas.size();i++)
     		System.out.print(datas.get(i)+"   ");
      }
      
}
