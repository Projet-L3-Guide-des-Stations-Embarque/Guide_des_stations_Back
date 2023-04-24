package bgfhnvbfgv;

import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
	
	public boolean save(String path) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(this);
			oos.close();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean load(String path) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			LoadablePageSet inter = (LoadablePageSet)ois.readObject();
			
			setPages(inter.getPages());
			
			ois.close();
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public ArrayList<PageObject> getPages() {
		return pages;
	}
	
	// PRIVATE INSTANCE METHODS
	
	private void setPages(ArrayList<PageObject> nouv) {
		pages = nouv;
	}
}
