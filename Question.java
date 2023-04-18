public class Question{
    private String id;
    private String question;
    private Boolean fin;
    private Question[] nexts;

    //INITIALISATION VIDE (PAS SUR QU'ELLE SOIT UTILISEE)
    Question(){
        this.id = "";
        this.question = "";
        this.fin = true;
        this.nexts = null;
    }
    /////////////////////////////////////////////////////

    Question(String id,String question,Boolean fin,Question[] nexts){
        this.id = id;
        this.question = question;
        this.fin = fin;
        this.nexts = nexts;
    }

}