/**
 * 合并排序
 * @author sinllychen
 *
 */
public class MergeSort {
	public static void merge(int[] k,int i,int q,int j)
	{
		int n1=q-i+1;
		int n2=j-q;
		int[] l=new int[n1+1];
		int[] r=new int[n2+1];
		int m,n;
		for(m=0;m<n1;m++)
		{
			l[m]=k[i+m];
		}
		for(n=0;n<n2;n++)
		{
			r[n]=k[q+n+1];
		}
		l[n1]=1000000;
		r[n2]=1000000;
		m=0;n=0;
		for(int p=i;p<=j;p++)
		{
			if(l[m]<=r[n])
			{
				k[p]=l[m];
				m++;
			}
			else 
				{
				k[p]=r[n];
				n++;
				}
		}
		
	}
	public static void mergeSort(int[] k,int i,int j)
	{
		int q;
		if(i<j)
		{
			q=(int) Math.floor((i+j)/2);
			System.out.println("q="+q);
			mergeSort(k,i,q);
			mergeSort(k,q+1,j);
			merge(k,i,q,j);
		}
	}
    public static void main(String[] args)
    {
    	int a[]={5,2,6,7,8,9,10,1,0,2,13,17,19,20,33};
    	mergeSort(a,0,a.length-1);
    	for(int i=0;i<a.length;i++)
    		System.out.print(a[i]+" ");
    }
}
