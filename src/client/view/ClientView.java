package client.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClientView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelEingabe = new JLabel("Eingabe: ");
	private JTextField txtEingabe = new JTextField();
	private JLabel txtChat = new JLabel();
	private JButton buttonSenden = new JButton("Senden");
	
	public ClientView() {
		super("iChat4EVA Client");
		initForm();
	}
	
	/*
	 * JForm wird initialisiert und alle Elemente darauf positioniert
	 */
	private void initForm() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(4, 0));
		this.setBounds(200, 200, 500, 300);
		
		this.add(labelEingabe);
		this.add(txtChat);
		this.add(txtEingabe);
		this.add(buttonSenden);
	}
	
	public void resetView() {
		this.txtEingabe.setText("");
	}
	
	public String getEingabe() {
		return this.txtEingabe.getText();
	}
	
	public void addMessage(String msg) {
		this.txtChat.setText(msg);
	}

	public void setSendMessageListener(ActionListener l) {
		this.buttonSenden.addActionListener(l);
	}

}
