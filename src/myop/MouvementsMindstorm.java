package myop;
import lejos.nxt.*;

/**
 * Gere les threads des moteurs du robot
 * Les moteurs doivent être connecté aux ports suivant:
 * Le moteur de la roue gauche: port C
 * Le moteur de la roue droite: port B
 * Le moteur auxiliaire: port A
 * @author Edouard
 *
 */
public class MouvementsMindstorm extends Thread{
	// /!\ Le moteur droit doit être connecté sur le port B et le moteur gauche sur le port C.
	int speed=360, speedA=360, speedMax=720, speedMin= 180; // vitesse max théorique 900
	TacheMoteur moteurC = new TacheMoteur(Motor.C); // création d'un moteur contrôlable par thread
	TacheMoteur moteurB = new TacheMoteur(Motor.B);
	TacheMoteur moteurA = new TacheMoteur(Motor.A);
	Thread tC = new Thread(moteurC); // Création d'un thread correspondant à un moteur.
	Thread tB = new Thread(moteurB);
	Thread tA = new Thread(moteurA);
	
	/**
	 * Constructeur d�marrant les t�ches de mouvements.
	 */
	MouvementsMindstorm()
	{
		demarrer();
	}
	
	void demarrer()
	{
		tB.start(); // d�marrage des threads
		tC.start();
		tA.start();
	}
	
	/**
	 * Fonction permettant au robot d'avancer en ligne droite suivant la vitesse d�termin�e
	 */
	void finir()
	{	
		moteurA.stopThread();
		moteurB.stopThread();
		moteurC.stopThread();
		tC.interrupt();
		tB.interrupt();
		tA.interrupt();
	}

	/**
	 * Fonction permettant au robot d'avancer en ligne droite suivant la vitesse d�termin�e
	 */
	void avancer()
	{	
		moteurC.setSpeed(speed);
		moteurB.setSpeed(speed);
		moteurC.setGo(1);
		moteurB.setGo(1);
	}

	/**
	 * Fonction permettant au robot de reculer en ligne droite suivant la vitesse d�termin�e
	 */
	void reculer()
	{
		moteurC.setSpeed(speed);
		moteurB.setSpeed(speed);
		moteurC.setGo(-1);
		moteurB.setGo(-1);
	}

	/**
	 * Fonction permettant au robot de tourner � gauche en �tant � l'arr�t
	 */
	void gaucheStop()
	{
		moteurB.setSpeed(speed/2); //r�duit la vitesse des motors par 2 par rapport � la ligne droite. Attention mettre que des vitesses paires.
		moteurC.setSpeed(speed/2);
		moteurB.setGo(1); //avance le moteur droit
		moteurC.setGo(-1); //recule le moteur gauche
	}

	/**
	 * Fonction permettant au robot de tourner � droite en �tant � l'arr�t
	 */
	void droiteStop()
	{
		moteurB.setSpeed(speed/2); //r�duit la vitesse des motors par 2 par rapport � la ligne droite. Attention mettre que des vitesses paires.
		moteurC.setSpeed(speed/2);
		moteurC.setGo(1); //avance le moteur gauche
		moteurB.setGo(-1); //recule le moteur droit
	}
	
	/**
	 * Fonction permettant au robot de tourner � gauche en avan�ant
	 */
	void gauche()
	{
		moteurB.setSpeed(speed); // Conserve la vitesse du moteur droit
		moteurC.setSpeed(speed/2); // R�duit la vitesse du moteur gauche par 2
		moteurB.setGo(1);
		moteurC.setGo(1);
	}

	/**
	 * Fonction permettant au robot de tourner � droite en avan�ant
	 */
	void droite()
	{
		moteurC.setSpeed(speed); // Conserve la vitesse du moteur gauche
		moteurB.setSpeed(speed/2); // R�duit la vitesse du moteur droit par 2
		moteurC.setGo(1);
		moteurB.setGo(1);
	}

	/**
	 * Fonction permettant au robot d'acc�l�rer : vitesse max � 120  rpm
	 */
	void accelerer()
	{
		if (speed<700)
		{
			speed=speed+100;
		}
		else
		{
			speed=speedMax;
		}
		moteurC.setSpeed(speed); //mise � jour des vitesses directement dans la t�che
		moteurB.setSpeed(speed);
	}

	/**
	 * Fonction permettant au robot de ralentir : vitesse min � 30 rpm
	 */
	void ralentir()
	{
		if (speed>280)
		{
			speed=speed-100;
		}
		else
		{
			speed=speedMin;
		}
		moteurC.setSpeed(speed); //mise � jour des vitesses directement dans la t�che
		moteurB.setSpeed(speed);
	}

	/**
	 * Fonction arr�tant les mouvements du robot par rapport au sol 
	 */
	void arreter()
	{
		moteurC.setGo(0);
		moteurB.setGo(0);
	}

	/**
	 * Fonction permettant au robot de reculer en tournant � gauche
	 */
	void gaucheRecul()
	{
		moteurB.setSpeed(speed); // Conserve la vitesse du moteur droit
		moteurC.setSpeed(speed/2); // R�duit la vitesse du moteur gauche par 2
		moteurB.setGo(-1);
		moteurC.setGo(-1);
	}

	/**
	 * Fonction permettant au robot de reculer en tournant � droite
	 */
	void droiteRecul()
	{
		moteurC.setSpeed(speed); // Conserve la vitesse du moteur gauche
		moteurB.setSpeed(speed/2); // R�duit la vitesse du moteur droit par 2
		moteurB.setGo(-1);
		moteurC.setGo(-1);
	}

	/**
	 * Fonction permettant de mettre en marche avant le moteur auxiliaire
	 */
	void avancerM3()
	{
		moteurA.setSpeed(speedA);
		moteurA.setGo(1);
	}

	/**
	 * Fonction permettant de mettre en marche arri�re le moteur auxiliaire
	 */
	void reculerM3()
	{
		moteurA.setSpeed(speedA);
		moteurA.setGo(-1);
	}

	/**
	 * Fonction permettant d'acc�l�rer la vitesse de rotation du moteur auxiliaire : vitesse max = 120 rpm
	 */
	void accelererM3()
	{
		if (speedA<1340)
		{
			speedA=speedA+100;
		}
		else
		{
			speedA=speedMax;
		}
		moteurA.setSpeed(speedA);
	}

	/**
	 * Fonction permettant de ralentir la vitesse de rotation du moteur auxiliaire : vitesse min = 30 rpm
	 */
	void ralentirM3()
	{
		if (speedA>280)
		{
			speedA=speedA-100;
		}
		else
		{
			speedA=speedMin;
		}
		moteurA.setSpeed(speedA);
	}

	/**
	 * Fonction permettant d'arr�ter le moteur auxiliaire
	 */
	void stoperM3()
	{
		moteurA.setGo(0);
	}
}
