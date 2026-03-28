package models;

public class PlaylistRequest {
    private String name;
    private Object[] rules;

    public PlaylistRequest(){}

    public PlaylistRequest (String name){
        this.name=name;
        this.rules=new Object[0];
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Object[] getRules() {
        return rules;
    }
    public void setRules(Object[] rules) {
        this.rules = rules;
    }
}
