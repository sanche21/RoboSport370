package Views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import Controllers.EditTeamController;
import Models.Robot;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class EditTeamView extends ScreenAdapter implements EventListener {
    
    private final Stage stage;
    private static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("assets/ui_atlas/ui-blue.atlas"));
    private static final TextureAtlas commonAtlas = new TextureAtlas(Gdx.files.internal("assets/ui_atlas/ui-commons.atlas"));
    

    private TextButton confirmButton;
    private TextButton searchButton;
    private TextButton backButton;
    
    private Table resultsTable;
    private Table rosterTable;

    private CheckBoxStyle checkboxStyle;
    
    private Queue<Robot> robotList;
    private Queue<Robot> rosterList;

    private EditTeamController controller;
    
    /**
     * Set up the controller
     * @param controller the controller we are setting up
     */
    public EditTeamView(EditTeamController controller) {
        robotList = new LinkedList<Robot>();
        rosterList = new LinkedList<Robot>();
        
        this.controller = controller;
        
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        
        //set up the stage
        stage = new Stage();
        
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.addRegions(atlas);
        
        Skin selectionSkin = new Skin();
        selectionSkin.addRegions(commonAtlas);
        
        
        //set up buttons
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button_02");
        textButtonStyle.down = skin.getDrawable("button_01");
        
        //set up text fields
        TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.background= skin.getDrawable("textbox_01");
        textFieldStyle.font=font;
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.cursor=skin.getDrawable("textbox_cursor_02");
        textFieldStyle.selection = selectionSkin.getDrawable("transparent-black-30");
        
        
        //set up labels
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.fontColor = Color.BLACK;
        labelStyle.font = font;   
        
        //set up checkboxes
        checkboxStyle = new CheckBoxStyle();
        checkboxStyle.checkboxOn = skin.getDrawable("checkbox_on");
        checkboxStyle.checkboxOff = skin.getDrawable("checkbox_off");
        checkboxStyle.fontColor = Color.BLACK;
        checkboxStyle.font = font;
        
        

        
        //set up the title
        Label titleLabel = new Label("Search for Robots", labelStyle);
        titleLabel.setPosition(width/2 - 100, height-50);
        titleLabel.setFontScale(2);
        
        //set up buttons on the bottom
        backButton = new TextButton("Cancel", textButtonStyle);
        backButton.setPosition(100, 50);
        backButton.setSize(500, 50);
        backButton.addListener(this);
        
        confirmButton = new TextButton("Confirm", textButtonStyle);
        confirmButton.setPosition(width-600,  50);
        confirmButton.setSize(500, 50);
        confirmButton.addListener(this);
        
        searchButton = new TextButton("Search", textButtonStyle);
        searchButton.setPosition(width-200,  50);
        searchButton.setSize(500, 50);
        searchButton.addListener(this);
        
        Table masterTable = new Table();
        masterTable.setFillParent(true);
        
        Table searchTable = new Table();
        searchTable.setColor(Color.BLUE);
        resultsTable = new Table();
        rosterTable = new Table();
        Table robotInfoTable = new Table();
        robotInfoTable.setColor(Color.RED);
        
        masterTable.add(searchTable).width(200).padRight(50);
        masterTable.add(resultsTable).width(200);
        masterTable.add(rosterTable).width(200);
        masterTable.add(robotInfoTable).width(200);
        
        
        Label searchTitle = new Label("Search", labelStyle);
        Label resultsTitle = new Label("Robot List", labelStyle);
        Label rosterTitle = new Label("Current Roster", labelStyle);
        Label infoTitle = new Label("Robot Info", labelStyle);
        searchTable.add(searchTitle);
        resultsTable.add(resultsTitle);
        rosterTable.add(rosterTitle);
        robotInfoTable.add(infoTitle);
        
        
        
        Label nameSearchLabel = new Label("Robot Name:", labelStyle);
        Label teamSearchLabel = new Label("Team Name:", labelStyle); 
        Label minWinsSearchLabel = new Label("Min Wins:", labelStyle); 
        Label maxWinsSearchLabel = new Label("Max Wins:", labelStyle); 
        Label minLossesSearchLabel = new Label("Min Losses:", labelStyle); 
        Label maxLossesSearchLabel = new Label("Max Losees:", labelStyle); 
        Label gamesPlayedSearchLabel = new Label("Games Played:", labelStyle); 
        Label allVersionsSearchLabel = new Label("All Versions:", labelStyle); 
        TextField nameSearchField = new TextField("", textFieldStyle);
        TextField teamSearchField = new TextField("", textFieldStyle);
        TextField minWinsSearchField = new TextField("", textFieldStyle);
        TextField maxWinsSearchField = new TextField("", textFieldStyle);
        TextField minLossesSearchField = new TextField("", textFieldStyle);
        TextField maxLossesSearchField = new TextField("", textFieldStyle);
        TextField gamesPlayedSearchField = new TextField("", textFieldStyle);
        CheckBox versionsSearchBox = new CheckBox("", checkboxStyle);
        searchTable.row();
        searchTable.add(nameSearchLabel).padBottom(5);
        searchTable.add(nameSearchField).padBottom(5);
        searchTable.row();
        searchTable.add(teamSearchLabel).padBottom(5);
        searchTable.add(teamSearchField).padBottom(5);
        searchTable.row();
        searchTable.add(minWinsSearchLabel).padBottom(5);
        searchTable.add(minWinsSearchField).padBottom(5);
        searchTable.row();
        searchTable.add(maxWinsSearchLabel).padBottom(5);
        searchTable.add(maxWinsSearchField).padBottom(5);
        searchTable.row();
        searchTable.add(minLossesSearchLabel).padBottom(5);
        searchTable.add(minLossesSearchField).padBottom(5);
        searchTable.row();
        searchTable.add(maxLossesSearchLabel).padBottom(5);
        searchTable.add(maxLossesSearchField).padBottom(5);
        searchTable.row();
        searchTable.add(gamesPlayedSearchLabel).padBottom(5);
        searchTable.add(gamesPlayedSearchField).padBottom(5);
        searchTable.row();
        searchTable.add(allVersionsSearchLabel).padBottom(5);
        searchTable.add(versionsSearchBox).padBottom(5);
        searchTable.row();
        searchTable.add(searchButton);
        
        
        
        //add actors to stage
        stage.addActor(titleLabel);
        stage.addActor(backButton);
        stage.addActor(confirmButton);
        stage.addActor(masterTable);
        
    }

    
    /**
     * render the scene
     */
    public void render(float delta) {   
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    /**
     * We use this to handle button presses
     */
    public boolean handle(Event arg0) {

        if(arg0.getTarget() instanceof CheckBox){
            CheckBox checked = (CheckBox)arg0.getTarget();
            boolean isChecked = checked.isChecked();
            
            Robot selectedRobot = (Robot)arg0.getTarget().getUserObject();
            if(checked.getParent() == this.resultsTable){
                if(isChecked){
                    rosterList.add(selectedRobot);
                } else {
                    rosterList.remove(selectedRobot);
                }
            } else {
                rosterList.remove(selectedRobot);
            }
            refreshRosterList();
            refreshResultsList();

        } else  if(arg0.getTarget() instanceof TextButton &&  ((TextButton)arg0.getTarget()).isPressed()){
            TextButton sender = (TextButton)arg0.getTarget();
            if(sender == this.confirmButton){

                   
            } else if (sender == this.backButton){
                    this.controller.notifyCancel();
            } else if (sender == this.searchButton){
                this.robotList = this.controller.notifySearch();
                refreshResultsList();
            }
        }
        return false;
    }
    
    public void refreshResultsList(){
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.fontColor = Color.BLACK;
        labelStyle.font = new BitmapFont();   
        
        this.resultsTable.clear();
        Iterator<Robot> it = this.robotList.iterator();
        while(it.hasNext()){
            Robot next = it.next();
            Label nameLabel = new Label(next.getName(), labelStyle);
            CheckBox box = new CheckBox("", checkboxStyle);
            if(this.rosterList.contains(next)){
                box.setChecked(true);
            }
            box.setUserObject(next);
            box.addListener(this);
            this.resultsTable.add(nameLabel).padBottom(10).padRight(50);
            this.resultsTable.add(box).padBottom(10);
            this.resultsTable.row();
        }
    }
    
    public void refreshRosterList(){
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.fontColor = Color.BLACK;
        labelStyle.font = new BitmapFont();   
        
        this.rosterTable.clear();
        Iterator<Robot> it = this.rosterList.iterator();
        while(it.hasNext()){
            Robot next = it.next();
            System.out.println(next);
            Label nameLabel = new Label(next.getName(), labelStyle);
            CheckBox box = new CheckBox("", checkboxStyle);
            box.setUserObject(next);
            box.setChecked(true);
            box.addListener(this);
            this.rosterTable.add(nameLabel).padBottom(10).padRight(50);
            this.rosterTable.add(box).padBottom(10);
            this.rosterTable.row();
        }
    }

    /**
     * set this screen to receive buttons whenever it becomes active
     */
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    
    /**
     * Called to do garbage collection
     */
    @Override
    public void dispose() {
        this.stage.dispose();
    }
 


}