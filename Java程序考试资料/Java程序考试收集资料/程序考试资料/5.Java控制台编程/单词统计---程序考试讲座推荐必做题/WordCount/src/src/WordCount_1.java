package src;
//����һ���ַ�����ͳ�������ж��ٸ����ʣ�����֮���ÿո�ֿ�

import java.util.Scanner;

public class WordCount_1
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("������:");
		String sentence = input.nextLine();

		// ������ľ���ת��Ϊ��������
		String[] words = sentence.split(" ");// ���ﵥ���ÿո����������ñ����������ж���

		// ��������ĳ��Ⱦ��ǵ�����
		System.out.println("���� " + words.length + " ������.");
	}
}