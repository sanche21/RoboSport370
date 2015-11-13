package Models;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;


/**
 * 
 * @author Levi Paradis
 * 
 * Notes for report: Replaced getWinsLossStats with getters for each win loss, and tie 
 * value
 */
public class Robot {
    
    private long serialNumber;
    private String name, teamName;
    private long baseHealth, currentHealth, strength, movesPerTurn, hexPosition;
    private long wins,losses, matches; 
    private HashMap<String,String> forthVariables,forthWords;
    private Stack<String> mailBox;
    
    /**
    this constructor will be called to create a robot. A robot can only be created if you know all of the information
    needed for the robot to run a match
     * 
     * @param robotName   Name of the robot
     * @param team        Name of the team
     * @param serial      The robot's serial number
     * @param health      The base health the robot starts with
     * @param strength    The amount of damage the robot will do
     * @param moves       The number of moves the robot can move in one turn
     * @param vars        The list of forth variables
     * @param words       The list of forth commands
     * @param winCount   The number of times this robot has won a match
     * @param lossCount  The number of times this robot has lost a match
     * @param matchCount The total number of matches this robot has played
     */
    public Robot(String robotName, String team, long serial,
            long health, long strength, long moves,
            HashMap<String,String> vars, HashMap<String,String> words,
            long winCount, long lossCount, long matchCount) {
        this.name = robotName;
        this.serialNumber = serial;
        this.teamName = team;
        this.forthVariables = vars;
        this.forthWords = words;
        this.baseHealth = health;
        this.currentHealth = health;
        this.strength = strength;
        this.movesPerTurn = moves;
        this.wins = winCount;
        this.losses = lossCount;
        this.matches = matchCount;
        this.mailBox = new Stack<String>();
    }
    
    /**
     * Another constructor for if you know all of the base information for a robot, but are missing stats
     * @param robotName   Name of the robot
     * @param team        Name of the team
     * @param serial      The robot's serial number
     * @param health      The base health the robot starts with
     * @param strength    The amount of damage the robot will do
     * @param moves       The number of moves the robot can move in one turn
     * @param vars        The list of forth variables
     * @param words       The list of forth commands
     */
    public Robot(String robotName, String team, long serial,
            long health, long strength, long moves,
            HashMap<String,String> vars, HashMap<String,String> words) {
        this(robotName, team, serial, health, strength, moves, vars, words, 0, 0, 0);
    }
    
    /**
     * @return robot's serial number
     */
    public long getSerialNumber(){
        return this.serialNumber;
    }
    
    /**
     * @return robot's name
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * 
     * @return robot's team's name
     */
    public String getTeamName(){
        return this.teamName;
    }
    
    /**
     * @return robot's current health
     */
    public long getHealth() {
        return this.currentHealth;
	    }
    
    /**
     * @return the health the robot starts a match with
     */
    public long getBaseHealth() {
        return this.baseHealth;
    }
    
    /**
     * causes damage to the robot
     * @param damage the amount to subtract from the robot's health
     */
	    public void inflictDamage(int damage) {
	        this.currentHealth = this.currentHealth - damage;
	        if(this.currentHealth <= 0){
	            this.destroy();
	        }
	    }
	
	    /**
	     * @return the amount of damage the robot can cause to others
	     */
	    public long getStrength() {
	        return strength;
	    }
	    
	    /**
	     * @return the number of moves the robot can do each turn
	     */
    public long getMovesPerTurn(){
        return movesPerTurn;
    }

    /**
     * @return a number representing the robot's position on the board
     */
	    public long getPosition() {
	        return hexPosition;
	    }

	    /**
	     * @param newHexPosition the hex number to move this robot to
	     */
	    public void setPosition(long newHexPosition) {
	        this.hexPosition = newHexPosition;
	    }

	    /**
	     * @return the number of games this robot has won
	     */
	    public long getWins() {
	       return wins;
	    }
	    
	    /**
	     * @return the number of games this robot has lost
	     */
	    public long getLosses() {
	        return losses;
	    }
	    
	    /**
     * @return the total number of games this robot has played
     */
    public long getTotalNumberOfMatches() {
        return this.matches;
    }
    
    /**
     * @return the value stored in the forth variable "variableName"
     * @param variableName the name of the variable we are checking
     */
    public String getForthVariable(String variableName){
        return this.forthVariables.get(variableName);
    }
    
    /**
     * @return the names of all forth variables the robot is saving
     */
    public Set<String> getAllForthVariables(){
        return this.forthVariables.keySet();
    }
    
   /**
    * Sets a new value to the saved variable passed in
    * @param variableName the name of the variable to set
    * @param newValue the value to set to that variable
    */
    public void setForthVariable(String variableName, String newValue){
        this.forthVariables.put(variableName, newValue);
    }
    
    /**
     * gets a string representing all the logic stored in a forth word
     * @param wordName the word we are looking for
     * @return the forth logic stored as that word
     */
    public String getForthWord(String wordName){
        return this.forthWords.get(wordName);
    }
    
    /**
     * @return the names of all forth words the robot has
     */
    public Set<String> getAllForthWords(){
        return this.forthWords.keySet();
    }
    
  
	    /**
	     * Destroys this robot
	     * Removes it's health, and animates it's destruction
	     */
	    public void destroy(){
	        this.currentHealth = 0;
	        //TODO: should also animate destruction, and remove the robot from the game
	    }
	
	    /**
	     * @returns true if robot's health is above 0
	     */
	    public boolean isAlive()	{
	        return (this.currentHealth >= 0);
	    }
	
	    /**
	     * Saves a new value into this robot's mailbox
	     * @param objectToPush the new value to save to the mailbox
	     */
	    public void pushMailbox(String objectToPush){
	        this.mailBox.push(objectToPush);
	    }
	    /**
	     * Pops a value off the robot's mailbox 
	     * @returns the top value stored in the mailbox stack
	     */
	    public String popMailbox(){
	        return this.mailBox.pop();
	    }
	    
	    /**
	     * @return a string representation of the robot
	     */
	    public String toString(){
        return name + " - " + teamName + " - " + serialNumber; 
	    }

}
