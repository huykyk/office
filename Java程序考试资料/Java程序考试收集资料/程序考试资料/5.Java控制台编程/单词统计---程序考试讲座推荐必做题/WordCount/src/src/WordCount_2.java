package src;
import java.util.*;

public class WordCount_2
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);
		System.out.print("������:");
		String text = s.nextLine();

		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		String[] words = text.split("[ .!?]");//��ǿ��ķִ�
		
		for (int i = 0; i < words.length; i++)
		{
				if (hashMap.get(words[i]) != null)//��if (keys.contains(arr[i]))Ч��һ��
				{
					int value = hashMap.get(words[i]).intValue();
					value++;
					hashMap.put(words[i], value);//����ֱ��result.put(arr[i], result.get(arr[i]) + 1);
				}
				else
					hashMap.put(words[i], 1);
		}

		/*System.out.println("����������ʺ��������");
		System.out.println(hashMap);
		
		System.out.println("�����ֵ���TreeMap����key����������ʺ��������");
		Map<String, Integer> treeMap = new TreeMap<String, Integer>(hashMap);//��HashMap����TreeMap
		System.out.println(treeMap);*/	
		
		ArrayList arrayList = new ArrayList(hashMap.entrySet());//����һ��ArrayList��������ż�ֵ��entrySet()		
		
		Collections.sort(arrayList, new Comparator()//����new��һ���Ƚ������󣬸ö�������������
		{
			public int compare(Object o1, Object o2)
			{
				Map.Entry obj1 = (Map.Entry) o1;
				Map.Entry obj2 = (Map.Entry) o2;
				return ((Integer) obj2.getValue()).compareTo((Integer) obj1.getValue());
				//String��Integer���Ͷ�ʵ����Comparable�ӿڣ�������һ��compareTo��������ֱ��ʹ��
			}
		});

		System.out.println("���մӴ�С��˳��������ʺ��������");
		System.out.println(arrayList);

	}
}