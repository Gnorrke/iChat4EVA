package client.model;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import network.TCPConnection;

/**
 * Die Klasse ClientModel realisiert das Model im MVC-Pattern des Clienten.
 * Sie bietet die Methoden, um die Verbindung mit dem Server herzustellen, Nachrichten zu senden,
 * Nachrichten zu empfangen und das Empfangen der Userliste 
 *
 * @see ClientController
 * @see ClientView
 * @see TCPConnection
 * 
 * @author Max Niederauer, Fabian Kalweit
 */

public class ClientModel {

	private static TCPConnection connection = null;
	private String letzteNachricht;
	private String result;
	private String lastSender;
	private String[] userList;
	private static String id;

	/**
	 * Definition der regulären Ausdrücke, um IP-Adresse und Port zu überprüfen
	 */
	private static final String PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static final String PATTERNPORT = "([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])";
	private static final String urlRegex = "^[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	public ClientModel() {

		this.letzteNachricht = "";
		this.lastSender = "";
		this.result = "";
		this.userList = new String[100];
		ClientModel.setId("");

		connect();
	}
	
	/**
	 * Frägt vom Nutzer mittels JOptionPane die IP-Adresse und den Port des Servers ab.
	 * Bei erfolgreicher Eingabe wird eine TCPConnection zum Server aufgebaut.
	 * Zudem erfolgt eine Art "Handshake", der die uniqueID des Clienten vom Server holt und die Userliste aktualisiert
	 */

	public void connect() {

		try {
			Pattern pattern = Pattern.compile(PATTERN);
			Pattern pattern2 = Pattern.compile(PATTERNPORT);
			Matcher matcher;
			String inputIP, inputPort;
			
			
			do {
				inputIP = JOptionPane
						.showInputDialog("Bitte geben Sie eine IP Adresse ein!");
				matcher = pattern.matcher(inputIP);

			} while (!(inputIP.matches(urlRegex) || matcher.matches()));
			
			do {
				inputPort = JOptionPane
						.showInputDialog("Bitte geben Sie eine gültige Portnummer ein!");
				matcher = pattern2.matcher(inputPort);

			} while (!matcher.matches());

			System.out.println("Verbindungsaufbau ...");

			connection = new TCPConnection(inputIP, Integer.parseInt(inputPort));

			// Handshake
			connection.sendLine("00000000000000000000%GID%");
			setId(connection.receiveLine().substring(25));

			connection.sendLine(getId() + "%GUL%");
			System.out.println(getId());
			result = connection.receiveLine();
			this.listUsers(result.substring(25));

			System.out.println("Die Verbindung wurde erfolgreich aufgebaut!");

		} catch (Exception e) {
			System.out.println(e + " - Keine Verbindung zum Server möglich");
			System.exit(-1);
		}
	}
	
	/**
	 * Die Verbindung zum Server wird getrennt. Dies geschieht mit dem FLAG %DSC%
	 */

	public static void disconnect() {

		try {
			connection.sendLine(getId() + "%DSC%");
			connection.close();

		} catch (Exception e) {
			System.out.println(e);
			System.exit(-1);
		}
	}

	/**
	 * Mit Hilfe der send()-Methode wird ein String (msg) an den Server geschickt
	 * @param msg - Nachricht, die an den Server geschickt wird
	 * @throws IOException
	 */
	public void send(String msg) throws IOException {

		try {
			connection.sendLine(msg);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Der Server hat die Verbindung abgebrochen oder wurde beendet!",
							"Fehler!", JOptionPane.WARNING_MESSAGE);
			System.exit(-1);
			e.printStackTrace();
		}
	}

	/**
	 * Die receive()-Methode empfängt Nachrichten vom Server und parst diese entprechend.
	 *  Dies wird mit den Flags, die in der Nachricht enthalten sind, realisiert.
	 * @return String - Die Nachricht, welche der Server geschickt hat
	 * @throws IOException
	 */
	public String receive() throws IOException {

		result = connection.receiveLine();
		if (result != null && result.contains("%GUL%")) {
			listUsers(result.substring(25));
			return "System: Userliste aktualisiert";
		}

		else if (result != null && result.contains("%MSG%")) {
			lastSender = result.substring(0, 20);
			System.out.println(lastSender);
			return result.substring(45);
		}

		return result;
	}

	/**
	 * Die listUsers(String msg)-Methode parst den vom Server gesendeten String in die einzelnen UserIDs
	 * @param msg - Die zusammenhängenden UserIDs (Userliste)
	 * @return String[] mit den einzelnen UserIDs
	 * @throws IOException
	 */
	public String[] listUsers(String msg) throws IOException {

		try {
			userList = new String[(msg.length() / 20)];

			for (int i = 0; i < (msg.length() / 20); i++) {
				if (!id.equals(msg.substring(i * 20, (i + 1) * 20)))
					userList[i] = msg.substring(i * 20, (i + 1) * 20);
				if (userList != null)
					System.out.println(userList[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	/**
	 * Die benötigten Getter- und Setter Methoden
	 */
	public String getLetzteNachricht() {
		return letzteNachricht;
	}

	public void setID(String id) {
		ClientModel.setId(id);
	}

	public String getID() {
		return getId();
	}

	public String getLastSender() {
		return lastSender;
	}

	public String[] getUserList() {
		return userList;
	}

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		ClientModel.id = id;
	}
}
