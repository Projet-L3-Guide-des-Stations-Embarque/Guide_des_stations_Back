package bgfhnvbfgv;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class LoadablePageSet implements Serializable {
	// INSTANCE ATTRIBUTES
	private ArrayList<PageObject> pages;
	
	// CONSTRUCTORS
	public LoadablePageSet()  {
		pages = new ArrayList<PageObject>();
	}
	
	// PUBLIC INSTANCE METHODS
	public void addPage(PageObject nouv) {
		pages.add(nouv);
	}
	
	public void removePage(PageObject target) {
		pages.remove(target);
	}
	
	public void save(String path) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(this);
			oos.close();
		}catch (Exception e) {
			System.err.println("ERREUR | Le fichier n'a pas pu être sauvegardé.");
		}
	}
	
	public void charger(String path) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			LoadablePageSet inter = (LoadablePageSet)ois.readObject();
			
			setPages(inter.getPages());
			
			ois.close();
		}catch (Exception e) {
			System.err.println("ERREUR | Le fichier n'a pas pu être chargé.");
		}
	}
	
	// PRIVATE INSTANCE METHODS
	
	private void setPages(ArrayList<PageObject> nouv) {
		pages = nouv;
	}
	
	private ArrayList<PageObject> getPages() {
		return pages;
	}
}
