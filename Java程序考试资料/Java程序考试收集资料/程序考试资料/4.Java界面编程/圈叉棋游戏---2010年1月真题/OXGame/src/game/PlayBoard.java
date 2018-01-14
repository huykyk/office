package game;

import javax.swing.JPanel; //import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class PlayBoard extends JPanel
{
	public final int SizeOfGrid = 50; // �����С
	public final int NumberOfGrid = 3; // ���з��������
	public byte stateOfGrid[][]; // �����״̬�� 0 �գ� 1 Ȧ ��2 ��
	public byte process; // ��Ϸ���̣� 1 Ȧ�ߣ� 2 ����

	public boolean BoradAlreadPaint;

	public PlayBoard()
	{
		mb_initData(); // ��ʼ�����̣�ǰ׺mb��ʾ��Ա��������˼

		addMouseListener(new MouseAdapter() // �����������
		{
			public void mousePressed(MouseEvent e) // �������
			{
				int x = e.getX(); // ���x����
				int y = e.getY(); // ���y����

				if (x >= 0 && x <= SizeOfGrid * NumberOfGrid && y >= 0 && y <= SizeOfGrid * NumberOfGrid)
				{
					int c = x / SizeOfGrid; // ����к�
					int r = y / SizeOfGrid; // ����к�

					if (mb_isEmptyGrid(r, c)) // �����Ƿ��������
					{
						if (process == 1)// �����Ȧ��
						{
							stateOfGrid[r][c] = 1; // ��Ȧ

							if (mb_isGameOver(r, c))// �ж���Ϸ�Ƿ����
							{
								int temp = JOptionPane.showConfirmDialog(null, "Ȧʤ���Ƿ������\n�ǣ�������\n���˳���");
								if (temp == 0)
								{
									mb_initData();
								}
								else
								{
									System.exit(0);
								}
							}
							else
							{
								process = 2; // ��һ������
							}
						}

						else if (process == 2)// ����ò���
						{
							stateOfGrid[r][c] = 2; // ����

							if (mb_isGameOver(r, c))
							{
								int temp = JOptionPane.showConfirmDialog(null, "��ʤ���Ƿ������\n�ǣ�������\n���˳���");
								if (temp == 0)
								{
									mb_initData();
								}
								else
								{
									System.exit(0);
								}
							}
							else
							{
								process = 1; // ��һ����Ȧ
							}
						}

						if (mb_isFull())// ���������˻�δ�ֳ�ʤ�������
						{
							int temp = JOptionPane.showConfirmDialog(null, "���壡�Ƿ������\n�ǣ�������\n���˳���");
							if (temp == 0)
							{
								mb_initData();
							}
							else
							{
								System.exit(0);
							}
						}

						repaint(); // ���ٴε���paintComponent(Graphics g)����

					} // if ����
				} // if ����
			} // ��������
		} // ʵ���ڲ���
		); // �������ý���
	} // ���췽������

	/** ��ʼ����ر��� */
	public void mb_initData()
	{
		process = 1; // ��ȦΪ����
		stateOfGrid = new byte[NumberOfGrid][NumberOfGrid]; // �����ڴ�

		// ��ʼ�����̷����״̬
		for (int i = 0; i < NumberOfGrid; i++)
			for (int j = 0; j < NumberOfGrid; j++)
			{
				stateOfGrid[i][j] = 0; // ��ʼ��Ϊ0�������и���Ϊ��
			}
	}

	/** ��ͼ */
	protected void paintComponent(Graphics g)
	{
		setBounds(0, 0, SizeOfGrid * NumberOfGrid + 2, SizeOfGrid * NumberOfGrid + 2); // ���û�������

		// ��������������
		for (int i = 0; i <= NumberOfGrid; i++)
		{
			g.drawLine(i * SizeOfGrid, 0, i * SizeOfGrid, NumberOfGrid * SizeOfGrid);// ��3������
			g.drawLine(0, i * SizeOfGrid, NumberOfGrid * SizeOfGrid, i * SizeOfGrid);// ��3������
		}

		BoradAlreadPaint = true;

		for (int i = 0; i < NumberOfGrid; i++)
			for (int j = 0; j < NumberOfGrid; j++)
			{
				if (stateOfGrid[i][j] == 1) // ��Ȧ
				{
					g.setColor(Color.blue);
					g.drawOval(j * SizeOfGrid + 10, i * SizeOfGrid + 10, SizeOfGrid - 20, SizeOfGrid - 20);
				}
				else if (stateOfGrid[i][j] == 2) // ����
				{
					g.setColor(Color.red);
					g.drawLine(j * SizeOfGrid + 10, i * SizeOfGrid + 10, j * SizeOfGrid + SizeOfGrid - 10, i
							* SizeOfGrid + SizeOfGrid - 10);
					g.drawLine(j * SizeOfGrid + SizeOfGrid - 10, i * SizeOfGrid + 10, j * SizeOfGrid + 10, i
							* SizeOfGrid + SizeOfGrid - 10);
				}
			}
	}

	/** �ж�ĳһ�������Ƿ�Ϊ�� */
	public boolean mb_isEmptyGrid(int r, int c)
	{
		if (stateOfGrid[r][c] != 0) // �ѱ�ռ �����ٷ���
			return false;

		else
			return true; // 
	}

	/** �ж���Ϸ�Ƿ���� */
	public boolean mb_isGameOver(int r, int c)
	{
		int i, j;
		int state = stateOfGrid[r][c]; // ��õ�ǰ��״̬

		// ��r���䣬�жϵ�ǰ������
		for (i = 0; i < NumberOfGrid; i++)
		{
			if (stateOfGrid[r][i] != state)
			{
				break;
			}
			if (i == NumberOfGrid - 1)// ˵��һ�г�����������Ȧ�����
				return true;
		}

		// ��c���䣬�жϵ�ǰ������
		for (i = 0; i < NumberOfGrid; i++)
		{
			if (stateOfGrid[i][c] != state)
			{
				break;
			}
			if (i == NumberOfGrid - 1)// ˵��һ�г�����������Ȧ�����
				return true;
		}

		// ���ҶԽ���
		if (r == c)// ���Խ���
		{
			for (i = 0, j = 0; i < NumberOfGrid && j < NumberOfGrid; i++, j++)
			{
				if (stateOfGrid[i][j] != state)
				{
					break;
				}
				if (j == NumberOfGrid - 1)
					return true;
			}
		}

		if (r + c == NumberOfGrid - 1)// �ζԽ���
		{
			for (i = 0, j = NumberOfGrid - 1; i < NumberOfGrid && j >= 0; i++, j--)
			{
				if (stateOfGrid[i][j] != process)
				{
					break;
				}
				if (j == 0)
					return true;
			}
		}

		return false;
	}

	/** �ж������Ƿ����������Ƿ���� */
	public boolean mb_isFull()
	{
		for (int i = 0; i < NumberOfGrid; i++)
			for (int j = 0; j < NumberOfGrid; j++)
			{
				if (stateOfGrid[i][j] == 0)
				{
					return false;
				}
			}
		return true;
	}
}
