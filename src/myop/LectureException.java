package myop;
/**
 * Permet la gestions des erreurs qui peuvent survenir lors des associations
 * @author Edouard
 *
 */
public class LectureException extends Exception {

	String nonReconnu = null;
	public LectureException(String nonReconnu) {
		this.nonReconnu = nonReconnu;
	}
	/**
	 * Appelé lorsqu'un mouvement du fichier d'association n'est pas reconnu
	 * @return un texte disant quel mouvement n'a pas été reconnu
	 */
	public String returnMvt(){
		return "Mouvement: "+nonReconnu+" non reconnu";
	}
	/**
	 * Appelé lorsqu'une action du fichier d'association n'est pas reconnu
	 * @return un texte disant quelle action n'a pas été reconnu
	 */
	public String returnAct(){
		return "Action: "+nonReconnu+" non reconnu";
	}
}
