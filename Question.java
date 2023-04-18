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
    
    Question(String id,String question){
        this.id = id;
        this.question = question;
        this.fin = true;
        this.nexts = new ArrayList<Question>();
    }
    
    
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
    
    
    
    public String nextsToString() {
    	String str = "";
    	if (nexts.size() == 0) {
    		str = str + "\"idoui\" : \"\",\n";
    		str = str + "\"idnon\" : \"\"\n";
    	} else {
    		str = str + "\"idoui\" : \""+ this.nexts.get(0).id +"\",\n";
    		if (nexts.size() > 1) {
    			str = str + "\"idnon\" : \""+ this.nexts.get(1).id +"\"\n";
        	} else {
        		str = str + "\"idnon\" : \"\"\n";
        	}
    	}
    	return str;
    }
    
    
    @Override
    public String toString(){
    	String str = "{\n";
    	str = str + "\"id\" : \"" + this.id + "\",\n";
    	str = str + "\"question\" : \"" + this.question + "\",\n";
    	str = str + "\"fin\" : " + this.fin.toString() + ",\n";
    	str = str + this.nextsToString();
    	str = str + "}\n";
        return str;
    }
    
}