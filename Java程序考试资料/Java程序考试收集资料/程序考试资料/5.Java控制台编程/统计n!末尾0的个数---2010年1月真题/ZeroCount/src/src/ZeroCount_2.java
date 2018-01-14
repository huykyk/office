package src;

import java.util.*;

public class ZeroCount_2
{
	/*
	 * ��f(x)��ʾx!ĩβ����0�ĸ��������У� ��0 < n < 5ʱ��f(n!) = 0; ��n >= 5ʱ��f(n!) = k + f(k!),
	 * ���� k = n / 5��ȡ������
	 */
	public static int countzero(int n)
	{
		if (n < 5)
			return 0;
		else
		{
			int k = n / 5;
			return k + countzero(k);
		}
	}

	public static void main(String[] args)
	{
		System.out.print("������������n=");
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();

		System.out.println(countzero(n));
	}

}
