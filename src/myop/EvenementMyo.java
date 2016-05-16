package myop;
import java.io.DataInputStream;
import java.io.IOException;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 * Classe qui se charge de récupérer toutes les informations Bluetooth (Fichier d'association et évènements Myo) et de les exploiter.
 * @author Edouard
 */
public class EvenementMyo implements Runnable{

	BTConnection bt = null;
	DataInputStream in = null;
	LectureTampon tampon;
	private boolean ready = false;

	/**
	 * Initialise le gestionnaire d'évènement Myo 
	 * @param tampon l'instance de LectureTampon qui fera le lien entre les évènements Myo et les actions du robot
	 */
	public EvenementMyo(LectureTampon tampon){
		this.tampon=tampon;
	}

	
	
	/**
	 * Thread permettant la synchonisation Bluetooth du robot avec l'application Android, récupère le fichier d'association mouvement Myo et actions robot puis écoute les mouvements Myo pour déclencher les actions associées.
	 */
	@Override
	public void run() {
		System.out.println("j'attend la connexion");
		bt = Bluetooth.waitForConnection(); //Attend la connexion avec l'application Android préalablement connectée au Myo
		if (bt == null) {
			try {
				throw new IOException("BT connection fail");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Myo connecté"); //Connexion réussie

		in = bt.openDataInputStream();

		byte[] buffer = new byte[2048];
		int nbBytes;
		String programme;
		try { //Récupération du fichier d'association
			nbBytes = in.read(buffer);
			programme = new String(buffer, 0, nbBytes);
			String bla = programme.substring(programme.indexOf('['),programme.lastIndexOf(']')+1);
			tampon.tampon(bla); //passage du fichier à lectureTampon qui se chargera de réaliser l'association
			ready=tampon.ready(); //Sémaphore indiquant que le fichier d'association a bien été passé .
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while(true)
		{
			try {
				if (in.available() > 0) {
					readMessage();

				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Fonction envoyant la commande Bluetooth reçue à lectureTampon afin qu'une action soit réalisée
	 */
	public void readMessage()  {

		int tmp = -1;
		try {
			tmp = in.read();

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (tmp!=-1)
		{
			tampon.sendCmd(tmp);
		}

	}

	/**
	 * Fonction permettant de débloquer une autre classe en attente (Sémaphore)
	 * @return l'état de l'instance évènementMyo
	 */
	public boolean ready() {
		return ready;
	}



}
