package src;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class SortGame_Practice
{
	/** �����Ա���� */
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JButton[] button;
	private int[] butNum;
	private Vector<String> optionalNum;

	/** ���캯��---�����Գ�Ա�������г�ʼ�� */
	public SortGame_Practice()
	{
		mainFrame = new JFrame("16����������Ϸ");
		mainPanel = new JPanel();
		button = new JButton[16];
		butNum = new int[16];
		for (int i = 0; i < 16; i++)
		{
			button[i] = new JButton();
		}
		optionalNum = new Vector<String>();
		initButtonNum();// �漴���а�ť��ź���

	}

	/** ��Ϸ�������������---�������ô��ڴ�С����Ӱ�ť��ע��������������ʾ���ڵ� */
	public void launchFrame()
	{

		mainFrame.setSize(300, 300);// ���ô��ڴ�С
		mainFrame.setDefaultCloseOperation(3);// ����Ĭ�Ϲرղ���

		mainPanel.setLayout(new GridLayout(4, 4, 3, 3));// ������岼�֣������Layout�õ���awt���е�����
		mainFrame.getContentPane().add(mainPanel);// �����Ȼ�ȡJFrame������壬Ȼ����������������Ǻ�awt����ͬ�ĵط�

		for (int i = 0; i < 16; i++)
		{
			button[i].addActionListener(new SetOperator_ActionListener());// ��ÿ����ťע���¼�������
			mainPanel.add(button[i]);
		}

		mainFrame.setVisible(true);// ��ʾ����
	}

	/** �漴���а�ť���---�ؼ���Vector��get��remove�����ã����ɱ�֤ÿ����ť�ϵ����ֶ���ͬ */
	private void initButtonNum()
	{
		for (int i = 0; i < 16; i++)
		{
			optionalNum.add(String.valueOf(i));// ��������˳�����Ԫ��0~15
		}
		int index = -1;
		String str = null;
		for (int i = 0; i < 16; i++)
		{
			index = (int) (Math.random() * optionalNum.size());// �漴����0~optionalNum.size֮������ָ���index
			str = optionalNum.get(index);// Ȼ��������а��漴��ȥȡԪ�أ�Ȼ����ʾ��button�ϣ����洢��butNum[]��
			if (str.equals("0"))
			{
				button[i].setText("");
				butNum[i] = 0;
			}
			else
			{
				button[i].setText(str);
				butNum[i] = Integer.parseInt(str);
			}
			optionalNum.remove(index);// ɾ��index��Ӧλ�õ�Ԫ��
		}
	}

	/** ��麯��---���ð�ť�Ƿ�Ϊ0�Ű�ť */
	private boolean check(int index)
	{
		if (index >= 0 && index < 16 && butNum[index] == 0)
			return true;
		else
			return false;
	}

	/** �ж��Ƿ���ɹ��� */
	private boolean evaluate()
	{
		int i, j;
		for (i = 0; i < 16; i++)
		{
			if (butNum[i] != i)
				break;
		}
		if (i == 16)
			return true;// ����ɹ����ո������Ͻ�

		for (i = 0; i < 15; i++)
		{
			if (butNum[i] != i + 1)
				break;
		}
		if (i == 15)
			return true;// ����ɹ����ո������½�

		for (i = 0, j = 15; j >= 0; i++, j--)
		{
			if (butNum[j] != i)
				break;
		}
		if (i == 16)
			return true;// ����ɹ����ո������Ͻ�

		for (i = 0, j = 15; j > 0; i++, j--)
		{
			if (butNum[j] != i + 1)
				break;
		}
		if (i == 15)
			return true;// ����ɹ����ո������½�

		return false;
	}

	/** SetOperator_ActionListener��---ʵ�ְ�ť����¼��Ĵ��� */
	class SetOperator_ActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int num = -1, location = -1;
			int aim = -1;
			int i = 0;

			if (e.getActionCommand().length() == 0)// ����������0�Ű�ť����Ϊ0�Ű�ť��String����Ϊ0������ʲôҲ����
				return;

			num = Integer.parseInt(e.getActionCommand());// ��ȡ����İ�ť�����ֱ��

			while (i < 16)
			{
				if (num == butNum[i++])
				{
					location = i - 1;// ���ѭ��������ʵ����ȷ��location��λ��
					//System.out.println(location);
					break;
				}
			}

			int candidates[] = { location - 1, location + 1, location - 4,
					location + 4 }; // candidates[]���������������ĸ�λ��
			for (int j = 0; j < 4; j++)
			{
				if (check(candidates[j]))// ������������ĸ�λ���ĸ���0��ť
					aim = candidates[j];// aim���Ǳ��Ϊ0�Ŀհװ�ť
			}
			if (aim >= 0 && aim < 16)// Ϊ�˱�����������������������ж�һ��aim�ķ�Χ
			{
				String tempStr = button[location].getText();
				int tempNum = butNum[location];
				button[location].setText("");// ����İ�ť��Ϊ�հ�
				butNum[location] = 0;// ����İ�ť�����Ϊ0
				button[aim].setText(tempStr);// Ŀ�갴ť��Ϊ���㰴ť���ı�
				butNum[aim] = tempNum;// Ŀ�갴ť�ı�ű�Ϊ���㰴ť�ı��
			}
			if (evaluate())// �ж��Ƿ���ɹ���
			{
				int choice = JOptionPane.showConfirmDialog(mainFrame,
						"�ɹ�����ף���㣡����һ����", null, JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION)
					initButtonNum();
				else
					System.exit(0);
			}
		}
	}

	/** ������ */
	public static void main(String[] args)
	{
		SortGame_Practice game = new SortGame_Practice();
		game.launchFrame();

	}

}
