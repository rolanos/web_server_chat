package models;

public class Chat {
    private int id;
    private String name;
    private int participant;
    private String participantName;

    public Chat(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Chat(int id, String name, int participant){
        this.id = id;
        this.name = name;
        this.participant = participant;
    }
    public int getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public int getParticipant(){return this.participant;}
    public String getParticipantName(){
        return  this.participantName;
    }
    public void setParticipantName(String name){
        this.participantName = name;
    }
}
