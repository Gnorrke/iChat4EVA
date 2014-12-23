package client.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
;

public class OnlineView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	private JButton button = new JButton("All Chat");

	
	public OnlineView() {
		super("iChat4EVA Online");
		initForm();
	}
	
	/*
	 * JForm wird initialisiert und alle Elemente darauf positioniert
	 */
	private void initForm() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(4, 0));
		this.setBounds(200, 200, 500, 300);
	
		
		this.add(button);
	
	}
	
	public void setStartChatListener(ActionListener l) {
		
		this.button.addActionListener(l);
	}

}
