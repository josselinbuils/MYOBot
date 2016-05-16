package myop;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SoundSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;

/**
 * Permet la gestion des capteurs du robot (initialisation et lecture des données)
 * @author Edouard
 *
 */
public class CapteursMindstorm {
	TouchSensor ts;
	UltrasonicSensor us;
	SoundSensor ss;
	LightSensor ls;

	/**
	 * Constructeur de la classe CapteursMindstorm
	 * On renseigne ici où est branché chaque capteur. L'ordre attendu est (port du capteur de pression, port du capteur ultrasion, port du capteur sonore, port du capteur de luminosité)
	 * @param portPression
	 * @param portSonar
	 * @param portSon
	 * @param portLumi
	 */
	public CapteursMindstorm(SensorPort portPression, SensorPort portSonar,
			SensorPort portSon, SensorPort portLumi) {
		ts = new TouchSensor(portPression); //capteur de pression (contact)
		us = new UltrasonicSensor(portSonar); //capteur de distance
		ss = new SoundSensor(portSon); //capteur de son
		ls = new LightSensor(portLumi); //Capteur de lumi�re

	}

	/**
	 * Fonction renvoyant true si le capteur de pression est appuyé
	 * @param port le port sur lequel est branch� le capteur de pression
	 * @return True si capteur pressé
	 */
	boolean pression() {
		return ts.isPressed();
	}

	/**
	 * Fonction donnant la distance séparant le capteur d'un obstacle
	 * 
	 * @param port sur lequel est branché le capteur ultrason
	 * @return la distance à l'obstacle (en m)
	 */
	int sonar() {
		return us.getDistance();
	}

	/**
	 * Fonction donnant la mesure du capteur sonore en %
	 * @param port sur lequel est branché le capteur
	 * @return l'intensité du son en pourcentage.
	 */
	int son() {
		ss.setDBA(true);
		return ss.readValue();
	}

	/**
	 * Donne l'intensité de la lumière en % par rapport à la valeur maximale.
	 * @return intensité de la lumière en %
	 */
	double lumi() {
		return ls.readNormalizedValue() / 10.23;
	}

}
