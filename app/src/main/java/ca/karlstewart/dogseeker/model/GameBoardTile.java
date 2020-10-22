package ca.karlstewart.dogseeker.model;

public class GameBoardTile {
    private enum dogVoiceType {DEEP, MEDIUM, HIGH};
    private dogVoiceType dogVoice;
    private boolean isThisDog;

    public GameBoardTile(boolean dog){
        isThisDog = dog;
        dogVoice = dogVoiceType.values()[ (int) Math.floor(Math.random()*3)];
    }

    public dogVoiceType getDogVoice(){
        return dogVoice;
    }

    public boolean isDog(){
        return isThisDog;
    }
}
