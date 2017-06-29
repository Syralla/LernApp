package core;

import java.util.ArrayList;
import java.util.List;

import xml.Aufgabe;

public class Queue implements Runnable {

	private List<List<Aufgabe>> queue = new ArrayList<List<Aufgabe>>();
	private boolean running = true;

	public Queue() {

	}

	public void handleNewTasks(List<Aufgabe> list) {

		queue.add(list);

		Thread t = new Thread(this);
		t.start();

	}

	@Override
	public void run() {

		if (queue.size() > 0) {

			List<List<Aufgabe>> tempQueue = new ArrayList<List<Aufgabe>>();

			synchronized (queue) {
				for (List<Aufgabe> list : queue) {
					tempQueue.add(list);
				}

				for (List<Aufgabe> list : tempQueue) {
					databaseKruscht(list); // in DB speichern
					queue.remove(list); // Liste aus queue löschen, in TempQueue
					// noch drin
				}
			}
		}

	}

	private void databaseKruscht(List<Aufgabe> list) {
		System.out.println("Es wird eine Aufgabenliste in der lahmen Datenbank gespeichert");
		for(Aufgabe a : list){
			System.out.println("Aufgabe[" + a.getNumber1() + "|" + a.getNumber2() + "|" + a.getOp() + "]");
		}
	}

	private void addNewTasks(List<Aufgabe> list) { // wenn zwei Threads
		synchronized (queue) {
			queue.add(list);
		}
	}

}
