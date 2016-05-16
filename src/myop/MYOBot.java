package myop;
import java.io.IOException;
import lejos.nxt.*;



/**
 * Ce programme permet de récupérer un fichier d'association envoyé en Bluetooth par l'application Android et de le traiter pour associer chaque évènement (mouvement de la main détécté par le Myo ou condition sur les capteurs du robot)
 * à une action définie. Il écoute ensuite les évènements (Myo ou Capteur) et lance les actions associées à cet évènement lorsqu'il est réalisé.
 * Rappel du cablage nécessaire pour un bon fonctionnement:
 * Capteur de pression: port1 - Capteur de luminosité: port 2 - Capteur sonore: port 3 - Capteur ultrason: port 4 - Moteur gauche: port C - Moteur droit: port B - Moteur aux: port A.
 * 
 * @author Edouard
 *
 */
public class MYOBot extends Thread{

	public static void main(String[] args) throws IOException,
			InterruptedException {
		
		LectureTampon tampon= new LectureTampon();
		
		CapteursMindstorm capt = new CapteursMindstorm(SensorPort.S1,SensorPort.S4,SensorPort.S3,SensorPort.S2);
		EvenementMyo eventMyo = new EvenementMyo(tampon);
		Thread tEventMyo = new Thread(eventMyo);
		tEventMyo.start();
		while(eventMyo.ready()==false){};
		EvenementCapteurs eventCapt = new EvenementCapteurs(tampon,capt);
		Thread tEventCapt = new Thread(eventCapt);
		tEventCapt.start();
		if(Button.waitForAnyPress()!=0)
			{
				System.exit(0);
			}
		
	}

}
