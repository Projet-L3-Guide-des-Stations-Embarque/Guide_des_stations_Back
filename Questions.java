import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Questions {
	private ArrayList<Question> liste;
	
	Questions(){
		this.liste = new ArrayList<Question>();
	}
	Questions( ArrayList<Question> liste){
		this.liste = liste;
	}
	
	public void addQuestion(Question quest){
		this.liste.add(quest);
	}
	
	
	public void ecrit(File filename) throws IOException{
		String str = "[\n";
		for (int i=0; i < this.liste.size(); i++) {
			str = str + liste.get(i).toString();
		}
		str = str + "]";
	    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
	    writer.write(str);
	    writer.close();
	}
	
	public void confirmation(){
		if(this.liste.size() > 0) {
			try {
				File myObj = new File("station.json");
				if (myObj.createNewFile()) {
					System.out.println("Fichier crée: " + myObj.getName());
					ecrit(myObj);
				} else {
					System.out.println("Fichier déja existant");
				}
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		} else {
			System.out.println("Liste de question vide");
		}
	}
	
}
