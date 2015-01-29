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
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

/**
 * Die ClientView Klasse realisiert die View Komponente des MVC-Pattern des Clienten.
 * Sie baut die grafische Benutzeroberfl�che und weist den Komponenten die Listener zu.
 * @author Max
 *
 */
public class ClientView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel labelEingabe = new JLabel("Eingabe: ");
	private JLabel labelUserliste = new JLabel("Userliste");
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
	
	/**
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
		userList.setVisibleRowCount(10);
		userList.setAlignmentX(LEFT_ALIGNMENT);
		userList.setMaximumSize(new Dimension(160, 340));
		
		leftPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
		middlePanel.setBorder(new EmptyBorder(0, 0, 0, 10));
		JScrollPane listScroller = new JScrollPane(userList);
		listScroller.setPreferredSize(new Dimension(250, 80));
		
		DefaultCaret caret = (DefaultCaret)txtChat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		// initialize leftPanel
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.add(labelUserliste);
		leftPanel.add(userList);
		leftPanel.add(buttonUserList);
		
		// initialize middlePanel
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS));
		middlePanel.add(labelEingabe);
		middlePanel.add(txtEingabe);
		middlePanel.add(buttonSenden);
		
		// initialize topPanel
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
		topPanel.setPreferredSize(new Dimension(100, 250));
		topPanel.setMaximumSize(new Dimension(100, 250));
		topPanel.add(new JScrollPane(txtChat));

		this.add(leftPanel, BorderLayout.WEST);
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(topPanel, BorderLayout.PAGE_START);
	}
	
	/**
	 * Alle ben�tigten Getter- und Setter-Methoden
	 */
	public void resetView() {
		this.txtEingabe.setText("");
	}
	
	public String getEingabe() {
		return this.txtEingabe.getText();
	}
	
	public String getSelected() {
		return userList.getSelectedValue();
	}
	
	public boolean selectionEmpty() {
		return userList.isSelectionEmpty();
	}
	
	public void addMessage(String msg) {
		this.txtChat.setText(this.txtChat.getText() + "\n" + msg);
	}
	
	public void addOwnMessage(String msg) {
		this.txtChat.setText(this.txtChat.getText() + "\n" +  "You: " + msg);
	}

	public void addUserListe(String[] userList) {
		this.userList.setListData(userList);
	}

	/**
	 * Listener werden den Buttons und dem Textfeld zugewiesen
	 * @param l
	 */
	public void setSendMessageListener(ActionListener l) {
		this.buttonSenden.addActionListener(l);
		this.txtEingabe.addActionListener(l);
	}
	
	/**
	 * Listener wird der Userliste (JList) zugewiesen
	 * @param l
	 */
	public void setUserListListener(ActionListener l) {
		this.buttonUserList.addActionListener(l);
	}

}
