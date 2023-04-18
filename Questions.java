Class Questions{
    private String[] id;
    private String[] question;
    private Boolean[] fin;
    private String[] idoui;
    private String[] idnon;

    Questions(){
        this.id = new String[];
        this.question = new String[];
        this.fin = new Boolean[];
        this.idoui = new String[];
        this.idnon = new String[];

    }
    Questions(String[] id,String[] question,Boolean[] fin,String[] idoui,String[] idnon){
        this.id = id;
        this.question = question;
        this.fin = fin;
        this.idoui = idoui;
        this.idnon = idnon;
    }
}