import java.util.ArrayList;

public class mainQuestion {

	public static void main(String[] args) {
		EnsembleQuestions test = new EnsembleQuestions();
		Question ajout1 = new Question("ID","Ceci est une question");
		test.addQuestion(ajout1);
		test.confirmation();
	}

}