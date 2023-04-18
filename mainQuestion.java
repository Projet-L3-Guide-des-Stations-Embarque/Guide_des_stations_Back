import java.util.ArrayList;

public class mainQuestion {

	public static void main(String[] args) {
		Questions test = new Questions();
		Question ajout1 = new Question("ID","Ceci est une question");
		test.addQuestion(ajout1);
		test.confirmation();
	}

}