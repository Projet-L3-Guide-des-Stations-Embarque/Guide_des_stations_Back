import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mainQuestion {

	public static EnsembleQuestions transcript(List<HashMap<String, String>> strfront) {
		EnsembleQuestions res = new EnsembleQuestions();
		for (int i=0; i < strfront.size(); i++) {
			Question ajout = new Question(Integer.toString(i), strfront.get(i).get("question"));
			ajout.addNext(strfront.get(i).get("oui"));
			ajout.addNext(strfront.get(i).get("non"));
			ajout.setfin(strfront.get(i).get("fin"));
			res.addQuestion(ajout);
		}
		return res;
		
	}
	
	public static void main(String[] args) {
		List<HashMap<String, String>>strfront;
		HashMap<String, String> test1 = new HashMap<String, String>();
		test1.put("question", "Ceci est une question");
		HashMap<String, String> test2 = new HashMap<String, String>();
		test2.put("question", "Ceci est une autre question");
		test2.put("fin", "false");
		test2.put("oui", "2");
		test2.put("non", "3");
		strfront = new ArrayList<HashMap<String, String>>();
		strfront.add(test1);
		strfront.add(test2);
		EnsembleQuestions test = transcript(strfront);
		//Question ajout1 = new Question("ID","Ceci est une question");
		//test.addQuestion(ajout1);
		test.confirmation();
	}

}