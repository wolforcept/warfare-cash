package main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DayCyclePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Data data;

	public DayCyclePanel(Data data) {
		this.data = data;
	}

	@Override
	public void paint(Graphics graphs) {
		int w = getWidth();
		int h = getHeight();

		graphs.setColor(Color.black);
		graphs.fillRect(0, 0, w, h);

		float p = data.getDayCounter() / ((float) Data.DAY_LENGTH - 1);

		graphs.setColor(new Color(1f - (0.5f * p), 1f - (0.5f * p), p, 1f));
		graphs.fillRoundRect(0, (h - (int) (h * p)), w, 500, 10, 10);
	}
}
