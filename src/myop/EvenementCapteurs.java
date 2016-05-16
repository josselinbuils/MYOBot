package myop;

/**
 * Ecoute les évènements capteurs et les transmets à lectureTampon quand les conditions sont remplies
 * @author Edouard
 *
 */
public class EvenementCapteurs implements Runnable{
	public CapteursMindstorm capt;
	public LectureTampon tampon;
	private int[] params;
	private int soundSup,soundInf,distance,lumin;
	private boolean ready=false;

	
	/**
	 * Initialise l'écouteur d'évènements capteurs.
	 * @param tampon
	 * @param capt
	 */
	public EvenementCapteurs(LectureTampon tampon,CapteursMindstorm capt){
		this.capt=capt;
		this.tampon=tampon;
		this.tampon.donneCapt(this.capt);
		params=tampon.getParams();
		soundSup=params[0];
		soundInf=params[1];
		distance=params[2];
		lumin=params[3];
		ready=true; //EvenementCapteur est bien initialisé, libération des threads en attente.
		
	}

	/**
	 * Fonction qui écoute si une valeur des capteurs remplis les conditions indiqué dans le fichier d'association et lance l'action associée
	 */
	@Override
	public void run() {
		while(ready==false){}
		while(true){
			if(capt.pression())tampon.actionCapt("contact");//Enclenche l'action associée si le capteur de pression est pressé.
			if(capt.son()>soundSup)tampon.actionCapt("soundSup");//Enclenche l'action associée si le son capté par le capteur de son est supérieur à la valeur renseignée.
			if(capt.son()<soundInf)tampon.actionCapt("soundInf");//Enclenche l'action associée si le son capté par le capteur de son est inférieur à la valeur renseignée.
			if(capt.sonar()<distance)tampon.actionCapt("sonar");//Enclenche l'action associée si la distance captée par le capteur ultrason est inférieur à la valeur renseignée.
			if(capt.lumi()<lumin)tampon.actionCapt("lumin");//Enclenche l'action associée si la distance captée par le capteur de luminosité est inférieur à la valeur renseignée.
		}
		
	}

}
