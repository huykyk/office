package src;

import java.util.*;

public class Word_Count_1
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("������һЩ���֣��ÿո�������س�������");
		String input = scanner.nextLine();

		String[] number = input.split(" ");

		int sum = 0;
		for (int i = 0; i < number.length; i++)
		{
			sum = sum + Integer.parseInt(number[i]);
		}
		double average = sum / number.length;
		System.out.println("ƽ��ֵ���ڣ�" + average);

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
