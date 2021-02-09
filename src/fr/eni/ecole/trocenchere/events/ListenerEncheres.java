package fr.eni.ecole.trocenchere.events;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ListenerEncheres
 *
 */

// class checking regularly which auctions are over and applying appropriate processes (marking the article status as sold, crediting seller, debiting buyer, etc.) via stored procedure
@WebListener
public class ListenerEncheres implements ServletContextListener {

	private boolean start = true;
	private Thread asyncTask = null;

	/**
	 * Default constructor.
	 */
	public ListenerEncheres() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce)  {
		System.out.println("l'application s'est arrêtée");
		start = false;
		asyncTask.stop(); // TODO find non deprecated replacement
		System.out.println("... le traitement asynchrone s'arrête");
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce)  {
		System.out.println("l'application a démarré");

		// definition of asynchronous task
		asyncTask = new Thread(new Runnable() {

			@Override
			public void run() {
				int cpt = 0;
				System.out.println("le traitement asynchrone commence...");
				while (start) {
					try {
						cpt++; // TODO placer ici l'appel à un manager qui appelle la couche DAL pour
						// déclencher la
						// procstock
						System.out.println("... le traitement asynchrone a été exécuté " + cpt + " fois...");
						Thread.sleep(5000000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		// launch task
		asyncTask.start();
	}

}
