import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EnsembleQuestions {
	private ArrayList<Question> liste;
	
	EnsembleQuestions(){
		this.liste = new ArrayList<Question>();
	}
	EnsembleQuestions( ArrayList<Question> liste){
		this.liste = liste;
	}
	
	public void addQuestion(Question quest){
		this.liste.add(quest);
	}
	
	public void ecrit(File filename) throws IOException{
		if(liste.size()>0) {
			String str = "[\n";
			int i = 0;
			while(i < this.liste.size() -1) {
				str = str + liste.get(i).toString();
				str = str + ",";
				i++;
			}	
			str = str + liste.get(i).toString();	
			str = str + "]";
		    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		    writer.write(str);
		    writer.close();
		}
	}
	
	public void confirmation(){
		if(this.liste.size() > 0) {
			try {
				File myObj = new File("stationq.json");
				if (myObj.createNewFile()) {
					System.out.println("Fichier crée: " + myObj.getName());
					ecrit(myObj);
				} else {
					ecrit(myObj);
					System.out.println("Fichier déja existant, réécriture");
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
