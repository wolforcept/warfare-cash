package swing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import data.Data;

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

		float p = (float) (data.getDayCounter() / ((float) Data.DAY_LENGTH - 1));

		float r = Math.max(0, 1f - (0.5f * p));
		float g = Math.max(0, 1f - (0.5f * p));
		float b = Math.max(0, p);

		graphs.setColor(new Color(//
				r > 1f ? 1f : r, //
				g > 1f ? 1f : g, //
				b > 1f ? 1f : b, //
				1f));
		graphs.fillRoundRect(0, (h - (int) (h * p)), w, 500, 10, 10);
	}
}
