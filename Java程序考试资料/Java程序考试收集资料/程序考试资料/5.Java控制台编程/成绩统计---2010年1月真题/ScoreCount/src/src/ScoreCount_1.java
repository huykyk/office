package src;

import java.io.*;

public class ScoreCount_1
{

	public static void main(String[] args) throws Exception
	{
		/*�����ļ����������ļ�Score.txt�е����ݶ�ȡ���ֽ�����buf��*/
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\Yuchao.Yuchao-PC\\Desktop\\��̿���\\1.Java����̨���\\�ɼ�ͳ��\\Score.txt");
		byte[] buf = new byte[100];
		int len = fis.read(buf);

		/*�����ֽ�����buf�������ַ�������score*/
		String input = new String(buf, 0, len);//������캯���ܹؼ���
		
		/*���ַ�������score���շָ������зִ�*/
		String[] score = input.split("(\\s)+");

		/*������߷���ͷֺ�ȥ����߷ֺ���ͷֵ�ƽ���ɼ�*/
		int max = Integer.parseInt(score[0]);
		int min = Integer.parseInt(score[0]);
		int sum = 0;
		for (int i = 0; i < score.length; i++)
		{
			if (Integer.parseInt(score[i]) > max)
				max = Integer.parseInt(score[i]);
			if (Integer.parseInt(score[i]) < min)
				min = Integer.parseInt(score[i]);
			sum = sum + Integer.parseInt(score[i]);
		}
		double average = (double)(sum - max - min)/(score.length-2);//ƽ���ɼ�����ǿ��ת����double�ͣ�����ʧ����		

		/*��ӡ�����*/
		System.out.println("��߷֣�"+max);
		System.out.println("��ͷ֣�"+min);
		
		System.out.println("ȥ����߷ֺ���ͷֵ�ƽ���ɼ���"+average);
		
		/*�ر��ļ�������*/
		fis.close();
	}
}
