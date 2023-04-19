
import java.util.ArrayList;

public class SubSectionPageElement implements PageElement {
	// INSTANCE ATTRIBUTES
	private String titre;
	private ArrayList<PageElement> elements;
		
	// CONSTRUCTORS
	public SubSectionPageElement(String titre) {
		this.titre = titre;
		elements = new ArrayList<PageElement>();
	}
		
	// PUBLIC INSTANCE METHODS
	public String getTitre() {return titre;}
	public void setTitre(String nouv) {titre = nouv;}
	
	public void addElement(PageElement nouv) {elements.add(nouv);}
	public void removeElement(PageElement cible) {elements.remove(cible);}
}
