package game;

import javax.swing.JFrame;

public class Frame_Game
{

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Ȧ������Ϸ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PlayBoard test = new PlayBoard();

		frame.setSize(200, 220);
		frame.setLocation(100, 100);
		frame.add(test);
		frame.setVisible(true); // �����paintComponent(Graphics g)����

	}

}
