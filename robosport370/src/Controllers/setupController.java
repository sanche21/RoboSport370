package Controllers;

import java.util.Queue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import Interpreters.JsonInterpreter;
import Models.Robot;
import Models.Team;
import Views.mainMenuView;
import Views.setupView;
import Views.mapView;

/**
 * @author Corey
 * @author Levi
 * setupController handles the main menu and setup screens while interfacing with the models
 *
 */
public class setupController extends Game {
	private Music introMusic;
	public int mapSize;
	public boolean isTournament,isSimulation;
	
	/**
	 * Called on initilization
	 */
	public void create() {
		// Getting the music intialized
		introMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/Bit Quest.mp3"));
        introMusic.setLooping(true);
        introMusic.setVolume(0.6f);
        introMusic.play();
        mapSize = 5;
        isTournament = false;
        isSimulation = false;
		
        //This is for testing my first screen
        this.setScreen(new mainMenuView(this));
		
		//This is for testing my second screen
		//this.setScreen(new setupView(this));
    }
	/**
	 * Gets called when the Main Menu view selects tournament
	 * 
	 */
	public void notifyTournament(){
	    this.setScreen(new setupView(this));
	    gameVariables.isSim = false;
	}
	
	/**
	 * Gets called when the Main Menu view selects a simulation
	 */
	public void notifySim(){
	      this.setScreen(new setupView(this));
	      gameVariables.isSim = true;
	}
	public void notifyDebug(){
	    gameVariables.isDebug = true;
	}
	/**
	 * gets called when the Main Menu view selects exit
	 */
	public void notifyExit(){
	    System.exit(0);
	}
	/**
	 * gets called when Setup view selects return
	 */
	public void notifyReturn(){
	    this.setScreen(new mainMenuView(this));
	}
	/**
     * gets called when Setup view selects return
     */
	/**
	 * changes the screen when continue is pressed
	 */
	public void notifyAddTeam(){
	    //TODO: Create a new add/edit team view to open here
	    System.out.println(JsonInterpreter.listRobots(true, null, null, null, null, null, null, null, null));
	    
	    for(int i = 0; i <= 6; i++)
	    {
	        Queue<Robot> robotList = JsonInterpreter.listRobots(true, null, null, null, null, null, null, null, null);
	        Team newTeam = new Team(robotList, "teamName");
	        gameVariables.allTeams[i] = newTeam;
	    }
	}
    public void notifyContinue(){
          GameController gameController = new GameController();
          if (gameVariables.isSim == false){    
             this.setScreen(new mapView(gameController));
          }
   }
	/**
	 * Handles storing the mapsize data
	 */
	public void notifyMapSize(){
	    if(this.mapSize < 11) {
            this.mapSize = this.mapSize + 2;
        }
        else if (this.mapSize >= 11) {
            this.mapSize = this.mapSize - 6;
        }   
	    gameVariables.mapSize = this.mapSize;
	}
	/**
	 * Called every frame
	 */
	public void render() {
        super.render();
    }
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RobotSport370";
		config.height = 800;
		config.width = 1280;
		new LwjglApplication(new setupController(), config);
		

	}
}
