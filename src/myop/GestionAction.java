package myop;

import lejos.nxt.Button;

/**
 * Classe réalisant la gestion des actions du Mindstorm. Cette classe permet de lancer les commandes prédéfinies du Mindstorm à l'aide de la classe MouvementsMindstorm.
 * @author Kévin
 *
 */
public class GestionAction implements Runnable{

	private int cpt = 0;
	private boolean run=true;
	private String [] tabCommande;
	private MouvementsMindstorm mv;
	private CapteursMindstorm capt;

	/**
	 * Constructeur de la classe, récupère les instances nécessaires au fonctionnement de la classe
	 * @param tabCommande tableau de commande à effectuer
	 * @param mv instance de la classe MouvementsMindstorm
	 * @param capt instance de la classe CapteursMindstorm
	 */
	public GestionAction(String [] tabCommande, MouvementsMindstorm mv, CapteursMindstorm capt)
	{
		this.tabCommande=tabCommande;
		this.mv=mv;
		this.capt=capt;
	}

	@Override
	/**
	 * Fonction exécutée par le thread
	 */
	public void run() {
		//System.out.println("RUN !");

		// TODO Auto-generated method stub
		while (run)
		{
			//System.out.println("j'avance dans la vague");
			for (int i=0; i<tabCommande.length;i++)
			{
				switchInterpretation(tabCommande[i], mv, capt);
			}
			run=false;
		}

	}

	/**
	 * fonction de sélection de l'action effectuée
	 * @param commande action à effectuer
	 * @param mv instance de la classe MouvementsMindstorm
	 * @param capt instance de la classe CapteursMindstorm
	 */
	private void switchInterpretation(String commande, MouvementsMindstorm mv, CapteursMindstorm capt){


		int temp = 0;
		if(commande.indexOf('(') != -1) 
		{
			temp = Integer.parseInt(commande.substring(commande.indexOf('(')+1,commande.indexOf(')')));
			commande = commande.substring(0,commande.indexOf('('));
		}


		try
		{
			switch (commande)
			{
			//cas de la commande avancer
			case "moveForward":
				mv.avancer();
				break;

				//cas de la commande avancer temporisée
			case "timedMoveForward":
				if (temp > 0)
				{
					mv.arreter();
					Thread.sleep(temp * 10);
					mv.avancer();
					//System.out.println("Tempo de: "+temp+"s");
					Thread.sleep(temp * 1000);
					mv.arreter(); //arrêt des moteurs après la tempo
					Thread.sleep(1);
				}
				else
					System.out.println("Temporisation incorrecte !");
				break;

				// cas de la commande reculer
			case "moveBackward":
				mv.reculer();
				break;
				
				//cas de la commande reculer temporisée
			case "timedMoveBackward" :
				if (temp > 0)
				{
					mv.arreter();
					Thread.sleep(temp * 10);
					mv.reculer();
					//System.out.println("Tempo de: "+temp+"s");
					Thread.sleep(temp * 1000);
					mv.arreter(); //arrêt des moteurs après la tempo
					Thread.sleep(1);
				}
				else
					System.out.println("Temporisation incorrecte !");
				break;

				// cas de la commande accélérer
			case "accelerate" :
				mv.accelerer();
				break;

				// cas de la commande ralentir
			case "slowDown" :
				mv.ralentir();
				break;

				// cas de la commande tourner à droite
			case "veerRight" :
				mv.droite();
				break;
				
				// cas de la commande tourner à droite temporisée
			case "timedVeerRight" :
				if (temp > 0)
				{
					mv.droite();
					//System.out.println("Tempo de: "+temp+"s");
					Thread.sleep(temp * 1000);
					mv.arreter(); //arrêt des moteurs après la tempo
				}
				else
					System.out.println("Temporisation incorrecte !");
				break;

				// cas de la commande tourner à gauche
			case "veerLeft" :
				mv.gauche();
				break;
				
				// cas de la comande tourner à gauche temporisée
			case "timedVeerLeft" :
				if (temp > 0)
				{
					mv.gauche();
					//System.out.println("Tempo de: "+temp+"s");
					Thread.sleep(temp * 1000);
					mv.arreter(); //arrêt des moteurs après la tempo
				}
				else
					System.out.println("Temporisation incorrecte !");
				break;
				

				//cas de la commande tourner à droite sur place
			case "turnRight" :
				mv.droiteStop();
				break;
				
				// cas de la commande tourner à droite sur place temporisée
			case "timedTurnRight" :
				if (temp > 0)
				{
					mv.droiteStop();
					//System.out.println("Tempo de: "+temp+"s");
					Thread.sleep(temp * 1000);
					mv.arreter(); //arrêt des moteurs après la tempo
				}
				else
					System.out.println("Temporisation incorrecte !");
				break;

				//cas de la commande tourner à gauche sur place
			case "turnLeft" :
				mv.gaucheStop();
				break;
				
				//cas de la commande tourner à gauche sur place temporisée
			case "timedTurnLeft" :
				if (temp > 0)
				{
					mv.gaucheStop();
					//System.out.println("Tempo de: "+temp+"s");
					Thread.sleep(temp * 1000);
					mv.arreter(); //arrêt des moteurs après la tempo
				}
				else
					System.out.println("Temporisation incorrecte !");
				break;

				//cas de la commande arrêter
			case "stop" :
				mv.arreter();
				break;
				//cas de la commande arrêter temporisée
			case "timedStop" :
				if (temp > 0)
				{
					mv.arreter();
					//System.out.println("Tempo de: "+temp+"s");
					Thread.sleep(temp * 1000);
				}
				else
					System.out.println("Temporisation incorrecte !");
				break;

				// cas de la commande tourner à droite en reculant
			case "moveBackwardRight" :
				mv.droiteRecul();
				break;

				// cas de la commande tourner à gauche en reculant
			case "moveBackwardLeft" :
				mv.gaucheRecul();
				break;

				// cas de la commande tourner vers l'avant du moteur auxiliaire
			case "auxMotorFoward" :
				mv.avancerM3();
				if (temp != 0)
				{
					Thread.sleep(temp*1000);
					mv.stoperM3();
				}
				break;

				// cas de la commande tourner vers l'arri�re du moteur auxiliaire
			case "auxMotorBackward" :
				mv.reculerM3();
				if (temp != 0)
				{
					Thread.sleep(temp*1000);
					mv.stoperM3();
				}
				break;

				// cas de la commande accélérer du moteur auxiliaire
			case "auxMotorAccelerate" :
				mv.accelererM3();
				break;

				// cas de la commande ralentir du moteur auxiliaire
			case "auxMotorSlowDown" :
				mv.ralentirM3();
				break;

			case "auxMotorStop" :
				mv.stoperM3();
				if (temp != 0)
				{
					Thread.sleep(temp*1000);
				}
				break;

				// cas de la commande crazy
			case "crazy!":
				while (true)
				{
					System.out.println("I'm crazYyY!!");
					mv.droiteStop();
					mv.accelerer();
					mv.accelererM3();
					mv.avancerM3();
					Thread.sleep(1000);

					if ((Button.readButtons() == Button.ID_RIGHT)
							|| (Button.readButtons() == Button.ID_LEFT)
							|| (Button.readButtons() == Button.ID_ENTER)
							|| (Button.readButtons() == Button.ID_ESCAPE))
					{
						mv.stoperM3();
						mv.arreter();
						break;
					}
				}
				break;
			

			default:
				throw new LectureException(commande);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LectureException le){
			System.out.println(le.returnAct());
		}
	}
}