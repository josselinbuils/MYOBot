package myop;
import json.JSONArray;
import json.JSONException;
import json.JSONTokener;

/**
 * Classe permettant de réaliser les associations entre les évènements Myo et les actions robot et d'appeler les threads de gestion d'action
 * @author Edouard
 *
 */
@SuppressWarnings("deprecation")
public class LectureTampon extends Thread{
	private int cpt;
	private String ligne = "vide";
	private JSONArray tableJson = null;
	CapteursMindstorm capt;
	MouvementsMindstorm mv;
	boolean avancer;
	private String cmdDbT[] ,cmdFist[], cmdWI[],cmdWO[],cmdFS[],cmdRS[],cmdCaptSonSup[],cmdCaptSonInf[],cmdCaptSnar[],cmdCaptPress[],cmdCaptLumi[];
	private int soundSup,soundInf,distance,lumin;
	GestionAction gestAct;
	Thread threadAct = null;
	private boolean ready = false;
	
	/**
	 * Lit le fichier d'association reçus en Bluetooth, le met en forme et réalise l'association
	 * @throws InterruptedException
	 */
	public LectureTampon() throws InterruptedException {}
	public void tampon(String nomFichier) throws InterruptedException {
		mv = new MouvementsMindstorm();
		try {
			tableJson = mkJSONArray(nomFichier);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			for(cpt = 0; cpt < tableJson.length(); cpt++){
				if(tableJson.get(cpt).toString().indexOf('[') != -1){
					JSONArray myoArray = mkJSONArray(tableJson.getString(cpt).toString()); //mettre en place listener (premiere case nouvel array) avec une boucle pour attendre l'action myo puis lorsque c'est detect�: r�alise les actions jusqu'� ce que le tableau soit vide (appeler la fonction switch pour r�aliser les actions)
					assoMvmt(myoArray);
				} else {
					System.out.println("Action non myo non autorisé au premier niveau");
				}
			}
			ready=true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet le passage des conditions capteurs à EvenementCapteurs
	 * @return un tableau de valeurs contenant les conditions associés à chaque capteurs
	 */
	public int[] getParams(){
		int[] params= new int[4];
		params[0]=soundSup;
		params[1]=soundInf;
		params[2]=distance;
		params[3]=lumin;
		return params;
	}
	
	/**
	 * Permet de récupérer les informations des capteurs
	 * @param capt
	 */
	public void donneCapt(CapteursMindstorm capt){
		this.capt=capt;
	}
	
	/**
	 * Envoie les commandes associées à l'évènement Myo
	 * @param commande
	 */
	public void sendCmd(int commande){
		switch(commande){
		case 3 : //"doubleTap":
			if (cmdDbT != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdDbT,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		case 2 : //"fist":
			if (cmdFist != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdFist,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		case 5 : //"waveIn":
			if (cmdWI != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct = new GestionAction(cmdWI,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		case 6 : //"waveOut":
			if (cmdWO != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdWO,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		case 1 : //"fingerSpread":
			if (cmdFS != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdFS,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		case 7: //"rest"
			if(cmdRS != null){
				if (threadAct != null){
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdRS,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
		}
	}
	
	/**
	 * Envoie les commandes associées aux évènements capteurs
	 * @param infoCapt
	 */
	public void actionCapt(String infoCapt) {
		switch(infoCapt){
		case "soundSup" : 
			if (cmdCaptSonSup != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdCaptSonSup,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		case "soundInf":
			if (cmdCaptSonInf != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdCaptSonInf,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		case "sonar":
			if (cmdCaptSnar != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdCaptSnar,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		case "lumin":
			if (cmdCaptLumi != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdCaptLumi,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		case "pression":
			if (cmdCaptPress != null) {
				if (threadAct != null) {
					threadAct.interrupt();
					threadAct=null;
				}
				gestAct=new GestionAction(cmdCaptPress,mv,capt);
				threadAct = new Thread(gestAct);
				threadAct.start();
			}
			break;
		default:
			System.out.println("Pb de lecture commande capteur");
		}
	}
	
	/**
	 * Permet d'associer chaque évènement avec une action en lisant le fichier d'association
	 * @param myoArray
	 */
	private void assoMvmt(JSONArray myoArray) { 
		try {
			String commande = null;
			int param=-1;
			if(myoArray.getString(0).toString().indexOf('(') != -1) 
			{
				param = Integer.parseInt(myoArray.getString(0).substring(myoArray.getString(0).indexOf('(')+1,myoArray.getString(0).indexOf(')')));
				commande = myoArray.getString(0).toString().substring(0,myoArray.getString(0).indexOf('('));
			}else commande = myoArray.getString(0).toString();

			switch(commande){
			case "doubleTap":
				JSONArray dbt = mkJSONArray(myoArray.getString(1).toString());
				cmdDbT = new String[dbt.length()];
				for(int i=0; i<dbt.length();i++)cmdDbT[i]=dbt.getString(i);//à tester
				break;
			case "fist":
				JSONArray fist = mkJSONArray(myoArray.getString(1).toString());
				cmdFist = new String[fist.length()];
				for(int i=0; i<fist.length();i++)cmdFist[i]=fist.getString(i);//à tester
				break;
			case "waveIn":
				JSONArray wi = mkJSONArray(myoArray.getString(1).toString());
				cmdWI = new String[wi.length()];
				for(int i=0; i<wi.length();i++)cmdWI[i]=wi.getString(i);//à tester
				break;
			case "waveOut":
				JSONArray wo = mkJSONArray(myoArray.getString(1).toString());
				cmdWO = new String[wo.length()];
				for(int i=0; i<wo.length();i++)cmdWO[i]=wo.getString(i);//à tester
				break;
			case "fingerSpread":
				JSONArray fs = mkJSONArray(myoArray.getString(1).toString());
				cmdFS = new String[fs.length()];
				for(int i=0; i<fs.length();i++)cmdFS[i]=fs.getString(i);//à tester
				break;
			case "rest":
				JSONArray rs = mkJSONArray(myoArray.getString(1).toString());
				cmdRS = new String[rs.length()];
				for(int i=0; i<rs.length();i++)cmdRS[i]=rs.getString(i);//à tester
				break;

				//Capteurs
			case "soundSup"://noms provisoires
				if(param != 0)soundSup=param;
				JSONArray captSonS = mkJSONArray(myoArray.getString(1).toString());
				cmdCaptSonSup = new String[captSonS.length()];
				for(int i=0; i<captSonS.length();i++)cmdCaptSonSup[i]=captSonS.getString(i);//à tester
				break;
			case "soundInf"://noms provisoires
				if(param != 0)soundInf=param;
				JSONArray captSonI = mkJSONArray(myoArray.getString(1).toString());
				cmdCaptSonInf = new String[captSonI.length()];
				for(int i=0; i<captSonI.length();i++)cmdCaptSonInf[i]=captSonI.getString(i);//à tester
				break;
			case "contact":
				JSONArray captPress = mkJSONArray(myoArray.getString(1).toString());
				cmdCaptPress = new String[captPress.length()];
				for(int i=0; i<captPress.length();i++)cmdCaptPress[i]=captPress.getString(i);//à tester
				break;
			case "sonar":
				if(param != 0)distance=param;
				JSONArray captSnar = mkJSONArray(myoArray.getString(1).toString());
				cmdCaptSnar = new String[captSnar.length()];
				for(int i=0; i<captSnar.length();i++)cmdCaptSnar[i]=captSnar.getString(i);//à tester
				break;
			case "lumi":
				if(param != 0)lumin=param;
				JSONArray captLumi = mkJSONArray(myoArray.getString(1).toString());
				cmdCaptLumi = new String[captLumi.length()];
				for(int i=0; i<captLumi.length();i++)cmdCaptLumi[i]=captLumi.getString(i);//à tester
				break;


			default:
				throw new LectureException(commande);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (LectureException le){
			System.out.println(le.returnMvt());
		}

	}
	/**
	 * Permet de "parser" le texte d'entrer pour en sortir un tableau JSON formaté 
	 * @param texte brut
	 * @return tableau JSON formaté
	 * @throws JSONException
	 */
	public JSONArray mkJSONArray(String texte) throws JSONException{
		JSONTokener jtokener = new JSONTokener(texte);
		return new JSONArray(jtokener);

	}

	/**
	 * Informe sur l'état de la classe 
	 * @return true si le fichier association a bien été traité
	 */
	public boolean ready() {
		return ready;
	}

}
