package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class MyButton extends JComponent implements MouseListener {

	private static final long serialVersionUID = 1L;

	private String text = "null";
	private int width, height;

	private MyAction action;

	private boolean mousein;

	public MyButton(String s, int w, int h) {
		super();
		text = s;
		width = w;
		height = h;
		mousein = false;

		enableInputMethods(true);
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {
		g.setFont(new Font(null, Font.BOLD, 12));
		if (mousein)
			g.setColor(new Color(0f, 0f, 0f, 0.8f));
		else
			g.setColor(new Color(0.5f, 0.5f, 0.5f, 1f));

		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

		g.drawString(text, 5, 14);
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

	}

	public void setText(String text) {
		this.text = text;
	}

	public void addAction(MyAction action) {
		this.action = action;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(width, height);
	}

	@Override
	public Dimension getMaximumSize() {
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mousein = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		mousein = false;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (action != null)
			action.perform();
	}

	public interface MyAction {
		public void perform();
	}

}
