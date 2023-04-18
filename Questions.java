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
	
	public void ecrit(File filename) throws IOException{
		String str = "Hello";
	    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
	    writer.write(str);
	    writer.close();
	}
	
	public void confirmation(){
		try {
			File myObj = new File("question.json");
		    if (myObj.createNewFile()) {
		    	System.out.println("File created: " + myObj.getName());
		    	ecrit(myObj);
		    } else {
		        System.out.println("File already exists.");
		    }
		} catch (IOException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
}
