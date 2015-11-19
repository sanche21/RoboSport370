package Models;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Team {
    //we use this static value to ensure that each team is assigned a unique number
    private static int uniqueNumber = 0;
    //the name of the team
    private String teamName;
    //the list of robots in the team. It's a queue so we can keep track of turn orders if we choose to
    private Queue<Robot> robotList;
    //the team's color
    private Color teamColor;
    //a number assigned to this team by the simulator for id purposes
    private int teamNumber;
    
    /**
     * Default Constructor
     * @param robots a list of robots to make up the team
     * @param name   the team's name
     * @param teamColor the team's color
     * @param uniqueNumber a number assigned to this team by the simulator for id purposes
     */
    public Team(Queue<Robot> robots, String name, Color teamColor){ 
       
        this.robotList = robots;
        this.teamName = name;
        this.teamColor = teamColor;
        this.teamNumber = uniqueNumber;
        
        //assign each robot it's id information for this game
        Iterator<Robot> robotIterator = robots.iterator();
        int i = 0;
        while(robotIterator.hasNext()){
            Robot thisRobot = robotIterator.next();
            thisRobot.setTeamIDs(uniqueNumber, i, name, teamColor);
            i++;
        }
        //increment unique number, so the next team will have a different number than this
        uniqueNumber++;
    }
    
    /**
     * Another constructor, without color requirement. If no color is passed it, it will be assigned a random one
     * @param robots a list of robots to make up the team
     * @param name   the team's name
     * @param uniqueNumber a number assigned to this team by the simulator for id purposes
     */
    public Team(Queue<Robot> robots, String name){
        this(robots, name, null);
        //create a random color to represent this team
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        this.teamColor =  new Color(r, g, b);
    }
    
    /**
     * @return an ordered list of all robots on the team
     */
    public Queue<Robot> getAllRobots(){
        return this.robotList;
    }
    
    /**
     * @return an ordered list of all robots on the team that are still alive
     */
    public Queue<Robot> getLivingRobots(){
        Queue<Robot> allRobots = this.getAllRobots();
        
        Queue<Robot> livingSet = new LinkedList<Robot>();
        Iterator<Robot> robotIterator = allRobots.iterator();
        while(robotIterator.hasNext()){
            Robot thisRobot = robotIterator.next();
            if(thisRobot.isAlive()){
                livingSet.add(thisRobot);
            }
        }
        return livingSet;
    }
    
    /**
     * @return the number of robots that are still alive on the team
     */
    public int numberOfLivingRobots(){
        Queue<Robot> livingSet = this.getLivingRobots();
        return livingSet.size();
    }
    
    /**
     * @return the team's color
     */
    public Color getColor(){
        return this.teamColor;
    }
    
    /**
     * @return the team's name
     */
    public String getName(){
        return this.teamName;
    }
    
    /**
     * @return the team's unique number
     */
    public int getTeamNumber(){
        return teamNumber;
    }
    
    public Robot getTeamMember(int memberNumber){
        Robot[] array = (Robot[]) this.robotList.toArray();
        return array[memberNumber];
    }
    
}