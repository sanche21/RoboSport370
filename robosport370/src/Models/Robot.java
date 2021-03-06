package Models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import Exceptions.ForthRunTimeException;
import Interfaces.ForthWord;

/**
 * 
 * @author Levi Paradis
 * 
 *         Notes for report: Replaced getWinsLossStats with getters for each win
 *         loss, and tie value
 */
public class Robot implements Cloneable {

    private long serialNumber;
    private String name;
    private long baseHealth, currentHealth, movesPerTurn;
    private int strength;
    private int xPosition;
    private int yPosition;
    private long simTeamNumber, simMemberNumber;
    private RobotGameStats stats;
    private HashMap<String, String> forthVariables, forthWords;
    private HashMap<Integer, Queue<ForthWord>> mailBox;

    /**
     * this constructor will be called to create a robot. A robot can only be
     * created if you know all of the information needed for the robot to run a
     * match
     * 
     * @param robotName
     *            Name of the robot
     * @param serial
     *            The robot's serial number
     * @param health
     *            The base health the robot starts with
     * @param strength
     *            The amount of damage the robot will do
     * @param moves
     *            The number of moves the robot can move in one turn
     * @param vars
     *            The list of forth variables
     * @param words
     *            The list of forth commands
     * @param stats
     *            An object that holds the stats for this robot. If it w
     */
    public Robot(String robotName, long serial, long health, int strength, long moves, HashMap<String, String> vars,
            HashMap<String, String> words, RobotGameStats stats) {
        this.name = robotName;
        this.serialNumber = serial;
        this.forthVariables = vars;
        this.forthWords = words;
        this.baseHealth = health;
        this.currentHealth = health;
        this.strength = strength;
        this.movesPerTurn = moves;
        this.mailBox = new HashMap<Integer, Queue<ForthWord>>();
        this.stats = stats;
    }

    /**
     * Another constructor for if you know all of the base information for a
     * robot, but are missing stats
     * 
     * @param robotName
     *            Name of the robot
     * @param serial
     *            The robot's serial number
     * @param health
     *            The base health the robot starts with
     * @param strength
     *            The amount of damage the robot will do
     * @param moves
     *            The number of moves the robot can move in one turn
     * @param vars
     *            The list of forth variables
     * @param words
     *            The list of forth commands
     */
    public Robot(String robotName, long serial, long health, int strength, long moves, HashMap<String, String> vars,
            HashMap<String, String> words) {
        this(robotName, serial, health, strength, moves, vars, words, new RobotGameStats());
    }

    /**
     * The simulator will assign each robot a unique team number and member
     * number. When a robot is assigned to a team, it will recieve these
     * attributes
     * 
     * @param teamNumber the current team number
     * @param memberNumber the current member number
     */
    public void setTeamIDs(int teamNumber, int memberNumber) {
        this.simMemberNumber = memberNumber;
        this.simTeamNumber = teamNumber;
    }

    /**
     * @return robot's serial number
     */
    public long getSerialNumber() {
        return this.serialNumber;
    }

    /**
     * @return robot's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return robot's member number for this match, assigned by the simulator
     */
    public long getMemberNumber() {
        return this.simMemberNumber;
    }

    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }

    public void setXPosition(int newX) {
        this.xPosition = newX;
    }

    public void setYPosition(int newY) {
        this.yPosition = newY;
    }

    /**
     * @return robot's team number for this match, assigned by the simulator
     */
    public long getTeamNumber() {
        return this.simTeamNumber;
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
     * 
     * @param damage
     *            the amount to subtract from the robot's health
     */
    public void inflictDamage(int damage) {
        this.stats.incrementDamageReceived(damage);
        this.currentHealth = this.currentHealth - damage;
        if (this.currentHealth <= 0) {
            this.destroy();
        }
    }

    /**
     * @return the amount of damage the robot can cause to others
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @return the number of moves the robot can do each turn
     */
    public long getMovesPerTurn() {
        return movesPerTurn;
    }

    /**
     * @return the value stored in the forth variable "variableName"
     * @param variableName
     *            the name of the variable we are checking
     */
    public String getForthVariable(String variableName) {
        return this.forthVariables.get(variableName);
    }

    /**
     * @return the names of all forth variables the robot is saving
     */
    public Set<String> getAllForthVariables() {
        return this.forthVariables.keySet();
    }

    /**
     * Sets a new value to the saved variable passed in
     * 
     * @param variableName
     *            the name of the variable to set
     * @param newValue
     *            the value to set to that variable
     */
    public void setForthVariable(String variableName, String newValue) {
        this.forthVariables.put(variableName, newValue);
    }

    /**
     * gets a string representing all the logic stored in a forth word
     * 
     * @param wordName
     *            the word we are looking for
     * @return the forth logic stored as that word
     */
    public String getForthWord(String wordName) {
        return this.forthWords.get(wordName);
    }

    /**
     * @return the names of all forth words the robot has
     */
    public Set<String> getAllForthWords() {
        return this.forthWords.keySet();
    }

    /**
     * Destroys this robot Removes it's health, and animates it's destruction
     */
    public void destroy() {
        this.currentHealth = 0;
        this.stats.markAsDied();
        // TODO: should also animate destruction, and remove the robot from the
        // game
    }

    /**
     * @return boolean that is true if robot's health is above 0
     */
    public boolean isAlive() {
        return (this.currentHealth > 0);
    }

    /**
     * Saves a new value into this robot's mailbox the mailbox has a capacity of
     * 6 If the mailbox is full or the robot is destroyed, it will return false
     * 
     * @param sender
     *            the new value to save to the mailbox
     * @param newMail the new mail to send from the member
     * @return whether the action succeeded or failed
     */
    public boolean addMailFromMember(int sender, ForthWord newMail) {
        int totalMail = this.totalMailAmount();
        Integer memberNumber = new Integer(sender);

        if (this.isAlive() && totalMail < 6) {
            Queue<ForthWord> memberMessages = this.mailBox.get(memberNumber);
            if (memberMessages == null) {
                memberMessages = new LinkedList<ForthWord>();
            }
            memberMessages.add(newMail);
            this.mailBox.put(memberNumber, memberMessages);
            return true;
        } else {
            return false;
        }
    }

    /**
     * tells us the amount of mail this robot has stored it should always be a
     * number between 0 and 6
     * 
     * @return the total amount of mail this robot has saved
     */
    private int totalMailAmount() {
        int count = 0;
        Set<Integer> allKeys = this.mailBox.keySet();
        Iterator<Integer> boxIterator = allKeys.iterator();
        while (boxIterator.hasNext()) {
            Integer key = boxIterator.next();
            Queue<ForthWord> thisBox = this.mailBox.get(key);
            count = count + thisBox.size();
        }
        return count;
    }

    /**
     * Pops a value off the robot's mailbox
     * @param sender the sender of the mail
     * @return ForthWord the top value stored in the mailbox stack
     * @throws ForthRunTimeException an exception that occurs when an unknown forth command is parsed
     */
    public ForthWord popMailFromMember(int sender) throws ForthRunTimeException {

        Integer member = new Integer(sender);
        Queue<ForthWord> memberMessages = this.mailBox.get(new Integer(member));

        if (memberMessages != null && !memberMessages.isEmpty()) {
            ForthWord message = memberMessages.poll();
            this.mailBox.put(member, memberMessages);
            return message;
        } else {
            String errorMessage = "no mail from teammate " + sender;
            throw new ForthRunTimeException(errorMessage);
        }
    }

    /**
     * lets us know whether we have mail waiting from a specific sender
     * 
     * @param sender
     *            the member number of the sender we are checking for
     * @return a bool representing whether we have mail waiting
     */
    public boolean hasMailFromMember(int sender) {
        Integer memberNumber = new Integer(sender);
        Queue<ForthWord> memberMessages = this.mailBox.get(memberNumber);
        return (memberMessages != null && !memberMessages.isEmpty());
    }

    /**
     * @return a string representation of the robot
     */
    public String toString() {
        return name + " - " + this.simTeamNumber;
    }

    /**
     * used to make a copy of this robot that has all the same attributes, but
     * shares the same stats object this is so that we can have multiple copies
     * of the robot on different teams, but they all update the same stats
     * 
     * @return a copy of the robot with the same stats object
     */
    public Robot clone() {
        Robot result = new Robot(this.name, this.serialNumber, this.baseHealth, this.strength, this.movesPerTurn,
                this.forthVariables, this.forthWords, this.stats);
        return result;

    }

    public RobotGameStats getStats() {
        return this.stats;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Robot) {
            Robot other = (Robot) obj;
            long sn = other.getSerialNumber();
            return (sn == this.getSerialNumber() && other.getTeamNumber() == this.getTeamNumber());
        }
        return false;
    }
}
