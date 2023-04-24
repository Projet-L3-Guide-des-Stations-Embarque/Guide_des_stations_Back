import java.util.ArrayList; // import the ArrayList class


public class Question{
    private String id;
    private String question;
    private Boolean fin;
    private ArrayList<String> nexts;

    //INITIALISATION VIDE (PAS SUR QU'ELLE SOIT UTILISEE)
    Question(){
        this.id = "";
        this.question = "";
        this.fin = true;
        this.nexts = new ArrayList<String>();
    }
    /////////////////////////////////////////////////////
    
    Question(String id,String question){
        this.id = id;
        this.question = question;
        this.fin = true;
        this.nexts = new ArrayList<String>();
    }
    
    
    Question(String id,String question,Boolean fin,ArrayList<String> nexts){
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
    
    public void addNext(String id){
    		this.nexts.add(id);
    }
    
    public void setfin(String str) {
    	if (str == "false") {
    		this.fin = false;
    	}
    	else {
    		this.fin = true;
    	}
    }
    
    
    public String nextsToString() {
    	String str = "";
    	if (nexts.get(0) == null) {
    		str = str + "\"idoui\" : \"\",\n";
    		str = str + "\"idnon\" : \"\"\n";
    	} else {
    		str = str + "\"idoui\" : \""+ this.nexts.get(0) +"\",\n";
    		if (nexts.get(1) != null) {
    			str = str + "\"idnon\" : \""+ this.nexts.get(1) +"\"\n";
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