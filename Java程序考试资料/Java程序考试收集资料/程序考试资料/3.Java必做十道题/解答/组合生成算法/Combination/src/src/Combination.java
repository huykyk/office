package src;

public class Combination
{
	/**
	 * ʹ�÷ǵݹ����n�Ľ׳�
	 * 
	 * @param n ������Ľ׳�
	 * @return ����n�Ľ׳˼�������Ľ��
	 */
	public static int doFactorial(int n)
	{
		int result = 1;// ���
		if (n < 0)
		{// �����n���Ϸ�
			return -1;// ����-1��˵���������Ϸ�
		}
		if (n == 0)
		{// 0!=1
			return 1;
		}

		for (int i = 1; i <= n; i++)
		{// ��1~n���
			result *= i;
		}
		return result;// ���ؽ��
	}

	/**
	 * ��������㷨C(n,r)
	 * 
	 * @param n
	 * @param r
	 */
	public static void combination_n_r(int n, int r)
	{
		int c_n_r = doFactorial(n) / (doFactorial(r) * doFactorial(n - r));// ������ϵĸ���
																			// C(n,r)=n!/(r!*(n-r)!)
		System.out.println("����" + c_n_r + "����ϣ�");

		if (c_n_r != 0)// ������Ϸ�����ϴ���ʱ
		{

			int[] s = new int[r + 1];
			for (int i = 1; i <= r; i++)
			{
				s[i] = i;
			}

			for (int i = 1; i <= r; i++)
			{
				System.out.print(s[i]);// ��ӡ��һ�����

			}
			System.out.println();// ����

			for (int i = 2; i <= c_n_r; i++)
			{
				int m = r;// ////////////
				int max_val = n;// ////////
				while (s[m] == max_val)
				{
					// ���������ҵ���һ�������ֵ��Ԫ��
					m--;
					max_val--;
				}

				if (m == 0)
				{
					System.out.println("!!!!!!!!!!!!!!!");
					return;

				}

				// �����������һ�������ֵ��Ԫ�ؼ�1
				s[m] = s[m] + 1;

				// s[m]֮���Ԫ�����ε���
				for (int j = m + 1; j <= r; j++)
					s[j] = s[j - 1] + 1;

				for (int k = 1; k <= r; k++)
					System.out.print(s[k]);// ��ӡ�ڸ����
				System.out.println();// ����
			}
		}

	}

	public static void main(String[] args)
	{
		combination_n_r(4, 2);

	}
}
