import java.util.ArrayList; // import the ArrayList class


public class Question{
    private String id;
    private String question;
    private Boolean fin;
    private ArrayList<Question> nexts;

    //INITIALISATION VIDE (PAS SUR QU'ELLE SOIT UTILISEE)
    Question(){
        this.id = "";
        this.question = "";
        this.fin = true;
        this.nexts = new ArrayList<Question>();
    }
    /////////////////////////////////////////////////////

    Question(String id,String question,Boolean fin,ArrayList<Question> nexts){
        this.id = id;
        this.question = question;
        this.fin = fin;
        this.nexts = nexts;
    }
    
    public boolean estFin(){
        if(this.fin == false) {
        	return false;
        }
        return true;
    }
    
    public void addNext(Question quest){
    		this.nexts.add(quest);
    		this.fin = false;
    }

}