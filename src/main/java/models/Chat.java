package models;

public class Chat {
    private int id;
    private String name;

    public Chat(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
}
