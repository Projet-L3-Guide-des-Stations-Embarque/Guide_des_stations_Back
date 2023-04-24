package bgfhnvbfgv;

import java.awt.Color;
import java.util.ArrayList;
import java.io.Serializable;

public class PageObject implements Serializable {
	// INSTANCE ATTRIBUTES
	private String titre;
	private Color couleur;
	private ArrayList<PageSection> elements;
				
	// CONSTRUCTORS
	public PageObject(String titre, Color couleur) {
		this.titre = titre;
		this.couleur = couleur;
		elements = new ArrayList<PageSection>();
	}
				
	// PUBLIC INSTANCE METHODS
	public String getTitre() {return titre;}	
	public void setTitre(String nouv) {titre = nouv;}
	
	public Color getCouleur() {return couleur;}	
	public void setCouleur(Color nouv) {couleur = nouv;}
			
	public void addElement(PageSection nouv) {elements.add(nouv);}
	public void removeSection(PageSection cible) {elements.remove(cible);}
}
