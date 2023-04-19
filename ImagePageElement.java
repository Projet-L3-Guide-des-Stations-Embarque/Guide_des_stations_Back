public class ImagePageElement implements PageElement {
	// INSTANCE ATTRIBUTES
	private String base64;
		
	// CONSTRUCTORS
	public ImagePageElement(String base64) {
		this.base64 = base64;
	}
		
	// PUBLIC INSTANCE METHODS
	public String getBase64() {return base64;}
	public void setBase64(String nouv) {base64 = nouv;}
}