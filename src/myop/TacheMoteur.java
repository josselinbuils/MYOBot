package myop;
import lejos.nxt.*;
/**
 * Classe des tâches de contrôle des moteurs
 * @author Kévin
 *
 */
public class TacheMoteur implements Runnable {
	NXTRegulatedMotor motor;
	private int go = 0;
	private boolean changeGo = false, run = true;

	/**
	 * Constructeur de la classe TacheMoteur
	 * @param motor
	 */
	public TacheMoteur(NXTRegulatedMotor motor) {
		this.motor = motor;
	}

	/**
	 * Fonction faisant tourner le moteur
	 */
	public void run() {
		while (run) {
			if (changeGo == true) {
				if (go == 1) {
					motor.forward();
				} else if (go == -1) {
					motor.backward();
				} else if (go == 0) {
					motor.stop();
				}
				changeGo = false;
			}
		}
	}

	/**
	 * Fonction annonçant le démarrage de du moteur
	 * 
	 * @param go
	 *            booléen de l'état de fonctionnemet du moteur
	 */
	public void setGo(int go) {
		this.go = go;
		changeGo = true;
	}

	/**
	 * Fonction permettant le changement de la vitesse de rotation du moteur.
	 * 
	 * @param speed
	 *            integer déterminant la vitesse de rottation en degrés par
	 *            minute.
	 */
	public void setSpeed(int speed) {
		motor.setSpeed(speed);
	}

	/**
	 * Fonction arrêtant la tâche de rotation du moteur
	 */
	public void stopThread() {
		run = false;
	}

}
