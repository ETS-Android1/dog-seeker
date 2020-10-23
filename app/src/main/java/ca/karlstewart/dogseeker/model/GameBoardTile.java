/*
    Tile object used in GameBoard objects. Contains a dog voice property, and a count of nearby dogs.

*/
package ca.karlstewart.dogseeker.model;

public class GameBoardTile {
    public enum dogVoiceType {DEEP, MEDIUM, HIGH}
    private dogVoiceType dogVoice;
    private boolean isThisDog;
    private boolean found;
    private int dogsNearby;

    public GameBoardTile(){
        isThisDog = false;
        found = false;
        dogVoice = dogVoiceType.values()[ (int) Math.floor(Math.random()*3)];
    }

    public dogVoiceType getDogVoice(){
        return dogVoice;
    }

    public boolean isDog(){
        return isThisDog;
    }

    public void setIsDog(boolean isDog) {
        isThisDog = isDog;
    }

    public int getDogsNearby() {
        return dogsNearby;
    }

    public void setDogsNearby(int dogsNearby) {
        this.dogsNearby = dogsNearby;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

}
