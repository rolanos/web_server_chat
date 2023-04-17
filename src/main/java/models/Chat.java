package models;

public class Chat {
    private int id;
    private String name;
    private int participantID;
    public Chat(int id, String name, int participantID){
        this.id = id;
        this.name = name;
        this.participantID = participantID;
    }
    public int getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public int getParticipantID(){
        return this.participantID;
    }
}
