package bgfhnvbfgv;

import java.io.Serializable;

public class TextePageElement implements PageElement, Serializable {
	// INSTANCE ATTRIBUTES
	private String contenu;
	
	// CONSTRUCTORS
	public TextePageElement(String contenu) {
		this.contenu = contenu;
	}
	
	// PUBLIC INSTANCE METHODS
	public String getContenu() {return contenu;}
	public void setContenu(String nouv) {contenu = nouv;}
}
