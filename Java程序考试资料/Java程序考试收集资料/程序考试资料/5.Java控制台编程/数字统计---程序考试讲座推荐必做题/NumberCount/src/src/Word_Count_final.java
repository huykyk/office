package src;

import java.util.*;

public class Word_Count_final
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		/*��������*/
		Scanner scanner = new Scanner(System.in);
		System.out.println("������һЩ���֣��ÿո�������س�������");
		String input = scanner.nextLine();

		/*���зִ�*/
		String[] number = input.split(" ");

		/*���������Сֵ��ƽ��ֵ*/
		int max = Integer.parseInt(number[0]);
		int min = Integer.parseInt(number[0]);
		int sum = 0;
		for (int i = 0; i < number.length; i++)
		{
			sum = sum + Integer.parseInt(number[i]);
			if (Integer.parseInt(number[i]) > max)
				max = Integer.parseInt(number[i]);
			if (Integer.parseInt(number[i]) < min)
				min = Integer.parseInt(number[i]);
		}
		double average = sum / number.length;		
		System.out.println("���ֵ���ڣ�" + max);
		System.out.println("��Сֵ���ڣ�" + min);
		System.out.println("ƽ��ֵ���ڣ�" + average);

		
		/*���㷽��*/
		double total = 0;
		for (int j = 0; j < number.length; j++)
		{
			total = total + (Integer.parseInt(number[j]) - average)
					* (Integer.parseInt(number[j]) - average);
		}
		double deviation = total / number.length;
		System.out.println("������ڣ�"+ deviation);
	}

}
