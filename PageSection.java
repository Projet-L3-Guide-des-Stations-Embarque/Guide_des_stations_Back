package bgfhnvbfgv;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class PageSection implements Serializable {
	// INSTANCE ATTRIBUTES
	private String titre;
	private Color couleur;
	private ArrayList<PageElement> elements;
			
	// CONSTRUCTORS
	public PageSection(String titre,Color couleur) {
		this.titre = titre;
		this.couleur = couleur;
		elements = new ArrayList<PageElement>();
	}
			
	// PUBLIC INSTANCE METHODS
	public String getTitre() {return titre;}
	public void setTitre(String nouv) {titre = nouv;}
	

	public Color getCouleur() {return couleur;}	
	public void setCouleur(Color nouv) {couleur = nouv;}
	
	public void addElement(PageElement nouv) {elements.add(nouv);}
	public void removeElement(PageElement cible) {elements.remove(cible);}
}
