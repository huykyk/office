package src;

import java.math.*;
import java.util.*;

public class NPower
{

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("������������n��");
		Integer n = input.nextInt();

		BigInteger result = new BigInteger(n.toString());

		for (int i = 0; i < n; i++)
		{
			result = result.multiply(new BigInteger(n.toString()));// �����˷�
		}
		
		 BigInteger temp =  result.mod(new BigInteger("1000")); 
         /*Returns a BigInteger whose value is (this mod m).*/ 
		
		System.out.println(result);//N��N�η�
		System.out.println(temp);//N��N�η���ĩ��λ

	}

}
