package src;

import java.util.*;

public class Word_Count_2
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

		int max = 0;
		int min = 0;

		for (int i = 0; i < number.length; i++)
		{
			if (Integer.parseInt(number[i]) > max)
				max = Integer.parseInt(number[i]);
			if (Integer.parseInt(number[i]) < min)
				min = Integer.parseInt(number[i]);

		}

		System.out.println("���ֵ���ڣ�" + max);
		System.out.println("��Сֵ���ڣ�" + min);
	}

}
