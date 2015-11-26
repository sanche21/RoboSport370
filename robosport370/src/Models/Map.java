package Models;
import Controllers.gameVariables;
import Models.Map.DIRECTION;

public class Map {
    boolean test = false;
    private int mapDiameter = gameVariables.mapSize *2 -1;
    private int height = gameVariables.mapSize;
    private int mapSize = gameVariables.mapSize;
    private int xPosMax, xPosMin, yPosMax, yPosMin;
    
    Tile[][] tiles = new Tile[mapDiameter][mapDiameter];
    
    public Map(){
        
        int inity = 0;
        int blwHalfDepth = 0;
        int abvHalfDepth = mapSize-1;
        xPosMax = this.mapSize;
        yPosMax = this.mapSize;
        xPosMin = -this.mapSize;
        yPosMin = -this.mapSize;
        
        int xPos = -(mapSize-1);
        int yPos = inity;
        
        int count = 0;

        for(int left = 0; left < mapDiameter; left++){
            for(int y = 0; y < height; y++) {
                if(left > ((mapDiameter-1)/2)+1){
                   
                    tiles[left][y] = new Tile(xPos, yPos);    
                    yPos--;
                }else{
                    if(height > mapDiameter) height--;
                    tiles[left][y] = new Tile(xPos, yPos);
                    
                    yPos--;
                }
                if(test){
                    System.out.print("( " + tiles[left][y].getXCoord() +
                            "," + tiles[left][y].getYCoord() + ") ");                    
                }
                
                count++;
            }          
            if(left >= ((mapDiameter-1)/2)){
                xPos++;
                yPos = mapSize-1;
                height--;
            }else{
                blwHalfDepth++;
                xPos++;
                yPos = blwHalfDepth;
                height++;    
            }
            if(test)System.out.println();
        }
        
        if(test)System.out.println(count);
    }
    
   
    
    /**
     * Calculates distance between two tiles
     * @param cur the tile to start on 
     * @param dest the tile that is being travelled to
     * @return the distance as an integer
     */
    public int calcDistance(Tile cur, Tile dest){
        return (int) Math.sqrt( (Math.pow(dest.getXCoord()- cur.getXCoord(),2)) + 
        		(Math.pow(dest.getYCoord()- cur.getYCoord(),2)));
    }
    
    public enum DIRECTION{
        NORTH(0,1),
        SOUTH(0,-1),
        NORTH_WEST(-1,0),
        NORTH_EAST(1,1),
        SOUTH_WEST(-1,-1),
        SOUTH_EAST(1,0);
        
        private final int xCoordinate;
        private final int yCoordinate;
        
        DIRECTION(int xCoordinate, int yCoordinate){
            this.xCoordinate = xCoordinate;
            this.yCoordinate = yCoordinate;
        }
        
        public int getXCoordinate(){
           
            return xCoordinate;
        }
        public int getYCoordinate(){
            return yCoordinate;            
        }
    }
    
    /**
     * returns the direction coordinates given the game direction (0,1,2,3...)
     * @param Direction the direction in game format (0,1,2,3...)
     * @return returns the type DIrection with x and y coordinates
     */
    public DIRECTION getDirection(int Direction){
        
        if(Direction == 0){
            return DIRECTION.NORTH;
           
        }
        else if(Direction == 1){
            return DIRECTION.NORTH_EAST;
        }
        else if(Direction == 2){
            return DIRECTION.SOUTH_EAST;
        }
        else if(Direction == 3){
            return DIRECTION.SOUTH;
        }
        else if(Direction == 4){
            return DIRECTION.SOUTH_WEST;
        }
        else if(Direction == 5){
            return DIRECTION.NORTH_WEST;
        }
        else{
            throw new RuntimeException("The provided direction is not valid");
        }
        
       
    }
    
    /**
     * returns a direction given a current and destination tile
     * @param current the tile that the direction is going from
     * @param destination the destination tile to calculate direction
     * @return returns the direction as an enum DIRECTION.
     */
    public DIRECTION findDirection(Tile current, Tile destination){
    	int xCoord = destination.getXCoord()- current.getXCoord();
    	int yCoord = destination.getYCoord()- current.getYCoord();
    	
    	DIRECTION toRet = null;
    	for(DIRECTION dir: DIRECTION.values()){
    		if(dir.getXCoordinate() == xCoord && dir.getYCoordinate() == yCoord){
    			toRet = dir;
    		}
    	}
    	return toRet;
    }
    
    
    public Tile[][] getTiles(){
        return this.tiles;
    }
    
    public int getMapSize(){
        return this.mapSize;
    }
    
    public int getMapDiameter(){
        return this.mapDiameter;
    }

    public int getMaxY(){
        return yPosMax;
    }
    
    public int getMaxX(){
        return xPosMax;
    }
    
    public int getMinY(){
        return yPosMin;
    }
    
    public int getMinX(){
        return xPosMin;
    }
    
    public static void main(String[] args){
        new Map();   
    }
} 
