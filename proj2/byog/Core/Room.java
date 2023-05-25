package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room
{
    private int Width;
    private int Height;
    private Position entrance;
    private Position leftBottom;
    private Position rightTop;

    private TETile[][] world;
    private Random RANDOM;

    public Room ( int Width, int Height, Position entrance,
                  Position leftBottom, TETile[][] world, Random RANDOM)
    {
        this.Width = Width;
        this.Height = Height;
        this.entrance = entrance;
        this.leftBottom = leftBottom;
        this.rightTop = new Position(leftBottom.x + Width, leftBottom.y + Height );
        this.world = world;
        this.RANDOM = RANDOM;
    }

    public void generateRoom ()
    {

        if (canGenerateRoom())
        {
            for ( int i = leftBottom.x ; i <= rightTop.x ; i++)
            {
                for ( int j = leftBottom.y ; j <= rightTop.y ; j++)
                {
                    if ( i == leftBottom.x || i == rightTop.x || j == leftBottom.y || j == rightTop.y)
                    {
                        world[i][j] = Tileset.WALL;
                        continue;
                    }
                    world[i][j] = Tileset.FLOOR;
                }
            }

            world[entrance.x][entrance.y] = Tileset.FLOOR;

            generateNextRoom();

            return;
        }

        return;
    }

    public boolean canGenerateRoom()
    {
        if ( leftBottom.x < 0 || rightTop.x > Game.WIDTH-1 ||
            leftBottom.y < 0 || rightTop.y > Game.HEIGHT-2) return false;

        for ( int i = leftBottom.x ; i <= rightTop.x ; i++)
        {
            for ( int j = leftBottom.y ; j <= rightTop.y ; j++)
            {
                if ( world[i][j] != null  ) return false;
            }
        }

        return true;
    }

    public void generateNextRoom()
    {

        Room nextRoom;
        Position nextRightTop, nextEntrance;
        Position Exit;
        int nextWidth = generateLen();
        int nextHeight = generateLen();
        Position nextLeftBottom;

        int[] order;

        double orderRandom = RANDOM.nextDouble();

        if(orderRandom < 0.25) order = new int[] {1,2,3,4};
        else if(orderRandom < 0.5) order = new int[] {3,1,4,2};
        else if(orderRandom < 0.75) order = new int[] {2,3,1,4};
        else order = new int[] {4,2,3,1};

        for(int k = 0 ; k < 4 ; k++)
        {
            switch (order[k])
            {
                case 1:
                    //左边
                    Exit = new Position(leftBottom.x, RANDOM.nextInt( rightTop.y-leftBottom.y-1)+leftBottom.y+1);

                    nextEntrance = new Position(Exit.x - 1, Exit.y);

                    nextRightTop = new Position(leftBottom.x-1,
                            RANDOM.nextInt( Math.min(nextEntrance.y + nextHeight, rightTop.y+1)-(nextEntrance.y+1))+nextEntrance.y+1);
                    nextLeftBottom = new Position(nextRightTop.x - nextWidth, nextRightTop.y - nextHeight);
                    nextRoom = new Room( nextWidth, nextHeight, nextEntrance, nextLeftBottom, world, RANDOM);
                    if ( nextRoom.canGenerateRoom() )
                    {
                        world[Exit.x][Exit.y] = Tileset.FLOOR;
                        nextRoom.generateRoom();
                    }
                    if( RANDOM.nextDouble() < 0.2) return;
                    break;
                case 2:
                    //上边
                    Exit = new Position(RANDOM.nextInt( rightTop.x - (leftBottom.x+1))+leftBottom.x+1, rightTop.y);
                    nextEntrance = new Position(Exit.x, Exit.y+1);
                    nextLeftBottom = new Position( RANDOM.nextInt(Math.min(nextEntrance.x + nextWidth, rightTop.x+1)
                            -(nextEntrance.x + 1)) -(nextEntrance.x + 1) - nextWidth, nextEntrance.y);
                    Room nextRoom2 = new Room( nextWidth, nextHeight, nextEntrance, nextLeftBottom, world, RANDOM);
                    if ( nextRoom2.canGenerateRoom() )
                    {
                        world[Exit.x][Exit.y] = Tileset.FLOOR;
                        nextRoom2.generateRoom();
                    }
                    if( RANDOM.nextDouble() < 0.2) return;
                    break;
                case 3:
                    //下边
                    Exit = new Position(RANDOM.nextInt(rightTop.x-(leftBottom.x+1))+(leftBottom.x+1), leftBottom.y);
                    nextEntrance = new Position(Exit.x, Exit.y-1);
                    nextRightTop = new Position( RANDOM.nextInt(Math.min(nextEntrance.x + nextWidth, rightTop.x+1)
                            -nextEntrance.x + 1)+nextEntrance.x + 1, nextEntrance.y);
                    nextLeftBottom = new Position(nextRightTop.x-nextWidth, nextRightTop.y - nextHeight);
                    Room nextRoom3 = new Room( nextWidth, nextHeight, nextEntrance, nextLeftBottom, world, RANDOM);
                    if ( nextRoom3.canGenerateRoom() )
                    {
                        world[Exit.x][Exit.y] = Tileset.FLOOR;
                        nextRoom3.generateRoom();
                    }
                    if( RANDOM.nextDouble() < 0.2) return;
                    break;
                case 4:
                    //右边
                    Exit = new Position(rightTop.x, RANDOM.nextInt( rightTop.y-(leftBottom.y+1))+leftBottom.y+1);
                    nextEntrance = new Position(Exit.x+1, Exit.y);
                    nextLeftBottom = new Position( nextEntrance.x, RANDOM.nextInt(nextEntrance.y-(nextEntrance.y-nextHeight+1))
                            +nextEntrance.y-nextHeight+1);
                    Room nextRoom4 = new Room( nextWidth, nextHeight, nextEntrance, nextLeftBottom, world, RANDOM);
                    if ( nextRoom4.canGenerateRoom() )
                    {
                        world[Exit.x][Exit.y] = Tileset.FLOOR;
                        nextRoom4.generateRoom();
                    }
                    if( RANDOM.nextDouble() < 0.2) return;
                    break;


            }
        }

        return;
    }

    public int generateLen()
    {
        double random = RANDOM.nextDouble();

        if (random < 0.1)
        {
            return 2;
        }
        else if (random < 0.3)
        {
            return 5;
        }
        else if (random < 0.5)
        {
            return 7;
        }
        else if (random < 0.7)
        {
            return 8;
        }
        else if (random < 0.9)
        {
            return 9;
        }
        else
        {
            return 10;
        }
    }
}
