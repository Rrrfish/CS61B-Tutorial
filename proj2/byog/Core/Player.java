package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;

public class Player implements Serializable
{
    private final String filepath;
    private int playerX;
    private int playerY;


    public Player(String filepath, int X, int Y, TETile[][] world)
    {
        this.filepath = filepath;
        this.playerX = X;
        this.playerY = Y;
        setPlayerX(X, world);
    }


    public boolean userMoveCommand(char direction, TETile[][] world)
    {
        switch (direction)
        {
            case 'W':
                if(world[getPlayerX()][getPlayerY()+1] == Tileset.WALL)
                {
                    return false;
                }
                setPlayerY(getPlayerY()+1, world);
                break;
            case 'S':
                if(world[getPlayerX()][getPlayerY()-1] == Tileset.WALL)
                {
                    return false;
                }
                setPlayerY(getPlayerY()-1, world);
                break;
            case 'A':
                if(world[getPlayerX()-1][getPlayerY()] == Tileset.WALL)
                {
                    return false;
                }
                setPlayerX(getPlayerX()-1, world);
                break;
            case 'D':
                if(world[getPlayerX()+1][getPlayerY()] == Tileset.WALL)
                {
                    return false;
                }
                setPlayerX(getPlayerX()+1, world);
                break;
        }

        return true;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX, TETile[][] world) {
        movePlayer(getPlayerX(),getPlayerY(),playerX, getPlayerY(), world);
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY, TETile[][] world) {
        movePlayer(getPlayerX(),getPlayerY(),getPlayerX(), playerY, world);
        this.playerY = playerY;
    }

    private void movePlayer(int oldX, int oldY,int newX, int newY, TETile[][] world)
    {
        world[oldX][oldY].draw(oldX, oldY);
        StdDraw.picture(newX+0.5, newY+0.72, filepath);
        StdDraw.show();
        if (world[newX][newY] == Tileset.LOCKED_DOOR)
        {
            Game.endGame();
        }
    }
}
