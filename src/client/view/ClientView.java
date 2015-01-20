package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultCaret;

public class ClientView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelEingabe = new JLabel("Eingabe: ");
	private JTextField txtEingabe = new JTextField();
	private JTextArea txtChat = new JTextArea();
	private JButton buttonSenden = new JButton("Senden");
	private JButton buttonUserList = new JButton("Aktualisieren");
	private String Data[] = {};
	private JList<String> userList = new JList<String>(Data);
	
	private JPanel leftPanel = new JPanel();
	private JPanel middlePanel = new JPanel();
	private JPanel topPanel = new JPanel();

	
	public ClientView() {
		super("iChat4EVA Client");
		initForm();
	}
	
	/*
	 * JForm wird initialisiert und alle Elemente darauf positioniert
	 */
	private void initForm() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(0, 5));
		this.setResizable(false);
		this.setBounds(550, 200, 600, 650);
		this.txtChat.setEditable(false);
		
		// initialize components
		userList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		userList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		userList.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(userList);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		DefaultCaret caret = (DefaultCaret)txtChat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		// initialize leftPanel
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.add(buttonUserList);
		leftPanel.add(userList);
		
		// initialize middlePanel
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS));
		middlePanel.add(txtEingabe);
		middlePanel.add(buttonSenden);
		
		// initialize topPanel
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
		topPanel.setPreferredSize(new Dimension(100, 250));
		topPanel.setMaximumSize(new Dimension(100, 250));
		topPanel.add(new JScrollPane(txtChat));
		topPanel.add(labelEingabe);

		this.add(leftPanel, BorderLayout.WEST);
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(topPanel, BorderLayout.PAGE_START);
	}
	
	public void resetView() {
		this.txtEingabe.setText("");
	}
	
	public String getEingabe() {
		return this.txtEingabe.getText();
	}
	
	public void addMessage(String msg) {
		this.txtChat.setText(this.txtChat.getText() + "\n" + msg);
	}
	
	public void addUserListe(String[] userList) {
		this.userList.setListData(userList);
	}

	public void setSendMessageListener(ActionListener l) {
		this.buttonSenden.addActionListener(l);
	}
	
	public void setUserListListener(ActionListener l) {
		this.buttonUserList.addActionListener(l);
	}

}
