package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import javax.swing.plaf.synth.SynthLookAndFeel;
import java.awt.*;
import java.io.*;
import java.lang.String;
import java.util.*;

public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public static Position lockedDoorPos;
    public int worldSeed;

    public static void endGame()
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle( WIDTH/2.0, HEIGHT+3, WIDTH/2.0, 50);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("large", Font.BOLD, 50));
        StdDraw.text(WIDTH/2.0, HEIGHT*(3.0/4), "YOU WIN!");
        StdDraw.show();
        return;
    }
    public static boolean save(String worldSeed, Game game, Player player)
    {
        if (saveWorld(worldSeed) && saveGame(game) && savePlayer(player)) return true;
        return false;
    }

    public static Player openPlayer()
    {
        Player player;
        try
        {
            FileInputStream iis = new FileInputStream("D:\\cs61b\\proj2\\byog\\Core\\savePlayer");
            ObjectInputStream ois = new ObjectInputStream(iis);

            player = (Player)ois.readObject();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Oops,openGame can't open the game!");
            return null;
        }
        catch (IOException e)
        {
            System.out.println("Oops,openGame occur IOException!");
            return null;
        } catch (ClassNotFoundException e)
        {
            System.out.println("Oops,openGame class not found!");
            throw null;
        }

        return player;
    }
    public  static boolean savePlayer(Player player)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("D:\\cs61b\\proj2\\byog\\Core\\savePlayer");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(player);

            oos.flush();
            oos.close();
            fos.close();
        }
        catch (IOException e)
        {
            System.out.println("Oops,savePlayer can't save the player!");
            return false;
        }

        return true;
    }


    public static String openWorld()
    {
        //TETile[][] world;
        String seed;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("D:\\cs61b\\proj2\\byog\\Core\\saveWorld"));
            seed = br.readLine();
            br.close();
            /* iis = new FileReader("D:\\cs61b\\proj2\\byog\\Core\\saveWorld");
            *//*ObjectInputStream ois = new ObjectInputStream(iis);

            world = (TETile[][])ois.readObject();*//*
            iis.readLine();*/

        }
        catch (Exception e)
        {
            System.out.println("Oops,openWorld can't open the world!");
            return null;
        }

        return seed;
    }

    public static Boolean saveWorld(String worldSeed)
    {
        try
        {
            FileWriter writer = new FileWriter(new File("D:\\cs61b\\proj2\\byog\\Core\\saveWorld"));
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(worldSeed);
            bw.flush();
            bw.close();
        }
        catch (IOException e)
        {
            System.out.println("Oops,saveWorld can't save world!");
            return false;
        }

        return true;
    }

    public static Game openGame()
    {
        Game game;
        try
        {
            FileInputStream iis = new FileInputStream("D:\\cs61b\\proj2\\byog\\Core\\saveFile");
            ObjectInputStream ois = new ObjectInputStream(iis);

            game = (Game)ois.readObject();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Oops,openGame can't open the game!");
            return null;
        }
        catch (IOException e)
        {
            System.out.println("Oops,openGame occur IOException!");
            return null;
        } catch (ClassNotFoundException e)
        {
            System.out.println("Oops,openGame class not found!");
            throw null;
        }

        return game;
    }

    public static Boolean saveGame(Game game)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream("D:\\cs61b\\proj2\\byog\\Core\\saveFile");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(game);

            oos.flush();
            oos.close();
            fos.close();
        }
        catch (NotSerializableException e)
        {
            System.out.println("Oops,saveGame occur NotSerializableException!");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Oops,saveGame can't save game!");
            return false;
        }
        catch (IOException e) {
            System.out.println("Oops,saveGame occur IOException!");
            return false;
        }

        return true;
    }
    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.text(WIDTH/2.0,HEIGHT,"Type your seed");
        StdDraw.text(WIDTH/2.0,HEIGHT*(2.0/3),s);
        StdDraw.show();
    }
    public TETile[][] generateRandomWorld(String seed, String pattern)
    {
        /**
         * 第四版想法
         * 1.构建Room类，method：判断是否能生成特定规格的Room；生成Room；
         * 2.构建Hallway类，method：判断是否能生成特定规格的Hallway；生成Hallway；
         * 3.生成区域的方法，将”入口“的wall换成floor，生成除入口外由wall封闭的区域，然后递归进行
         */
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];

        long randomNumber = 0;

        if(pattern.equals("String"))
        {
            char[] inputArr = seed.toCharArray();

            char[] randomNum = new char[seed.length()-1];
            for(int i = 0 ; i < randomNum.length ; i++)
            {
                randomNum[i] = inputArr[i+1];
            }
            randomNumber = Long.parseLong(new String(randomNum));
        }
        else
        {
            randomNumber = Integer.parseInt(seed);
        }
        final Random RANDOM = new Random(randomNumber);


        Position startPos = new Position(RANDOM.nextInt(20)+30, RANDOM.nextInt(5)+10);

        Room firstRoom = new Room(RANDOM.nextInt(5)+3, RANDOM.nextInt(5)+3, startPos,
                new Position(startPos.x-1, startPos.y), finalWorldFrame, RANDOM);
        firstRoom.generateRoom();


        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                if(finalWorldFrame[x][y] == null)
                {
                    finalWorldFrame[x][y] = Tileset.NOTHING;
                }
            }
        }

        finalWorldFrame[startPos.x][startPos.y] = Tileset.LOCKED_DOOR;
        Game.lockedDoorPos = new Position(startPos.x, startPos.y);
        return finalWorldFrame;
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard()
    {
        ter.initialize(WIDTH, HEIGHT+6, 0, 0);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("large", Font.BOLD, 50));
        StdDraw.text(WIDTH/2.0, HEIGHT*(3.0/4), "CS61B The Game");
        StdDraw.setFont(new Font("large", Font.BOLD, 30));
        StdDraw.text(WIDTH/2.0, HEIGHT/2.0, "New Game(N)");
        StdDraw.text(WIDTH/2.0, HEIGHT/2.0-5, "Load Game(L)");
        StdDraw.text(WIDTH/2.0, HEIGHT/2.0-10, "Quit(Q)");
        StdDraw.show();

        TETile[][] finalWorldFrame = null;
        Player player = null;
        String Seed = "";
        /*TETile[][] finalWorldFrame = generateRandomWorld("1891", "KeyBoard");

        ter.renderFrame(finalWorldFrame);
        Player player = new Player("D:\\cs61b\\proj2\\playerImage2\\idle_1.png", Game.lockedDoorPos.x, Game.lockedDoorPos.y+1, finalWorldFrame);
*/
        while(true)
        {
            if (StdDraw.hasNextKeyTyped())
            {
                char command = StdDraw.nextKeyTyped();
                switch (command)
                {
                    case 'N':
                        StringBuilder seed = new StringBuilder();
                        StdDraw.clear(StdDraw.BLACK);
                        StdDraw.setFont(new Font("large", Font.BOLD, 50));
                        StdDraw.setPenColor(StdDraw.WHITE);
                        StdDraw.text(WIDTH/2.0,HEIGHT,"Type your seed");
                        StdDraw.show();
                        while(true)
                        {
                            if (StdDraw.hasNextKeyTyped())
                            {
                                char c = StdDraw.nextKeyTyped();
                                if (c == 'S') break;
                                seed.append(c);
                                drawFrame(seed.toString());

                            }
                        }
                        ter.initialize(WIDTH, HEIGHT+6, 0, 0);
                        finalWorldFrame = generateRandomWorld(seed.toString(), "KeyBoard");
                        Seed = seed.toString();
                        player = new Player("D:\\cs61b\\proj2\\playerImage2\\idle_1.png",
                                Game.lockedDoorPos.x, Game.lockedDoorPos.y+1, finalWorldFrame);
                        //ter.renderFrame(finalWorldFrame);
                        break;
                    case 'L':
                        ter.initialize(WIDTH, HEIGHT+6, 0, 0);
                        finalWorldFrame = generateRandomWorld(openWorld(), "KeyBoard");
                        player = openPlayer();
                        //player.setPlayerX(player.getPlayerX(), finalWorldFrame);
                        //ter.renderFrame(finalWorldFrame);
                        Seed = openWorld();
                        break;
                    case 'Q':
                        System.exit(0);
                        break;
                }
                break;
            }
        }


        ter.renderFrame(finalWorldFrame);
        player.canDraw = true;
        player.setPlayerX(player.getPlayerX(), finalWorldFrame);
        while(true)
        {
            if (StdDraw.hasNextKeyTyped())
            {
                char command = StdDraw.nextKeyTyped();
                System.out.print(command);
                if (command == ':')
                {
                    while(true)
                    {
                        if(StdDraw.hasNextKeyTyped())
                        {
                            command = StdDraw.nextKeyTyped();
                            System.out.print(command);
                            if (command == 'Q')
                            {
                                Game.save(Seed, this, player);
                                System.exit(0);
                                return;
                            }
                            break;
                        }
                    }
                }
                player.userMoveCommand(command, finalWorldFrame);
            }
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        //TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];

        //ter.initialize(WIDTH, HEIGHT+6, 0, 0);
        String[] inputs;

        if( input.contains("s"))
        {
            inputs = input.split("s");
        }
        else
        {
            inputs = input.split("S");
        }

        System.out.println(inputs[0]);
        TETile[][] finalWorldFrame = generateRandomWorld(inputs[0], "String");
        Player player = new Player("D:\\cs61b\\proj2\\playerImage2\\idle_1.png", Game.lockedDoorPos.x, Game.lockedDoorPos.y+1, finalWorldFrame);
        player.canDraw = false;
        if(inputs.length > 1)
        {
            class CharIterator implements Iterator<Character> {
                private char[] array;
                private int currentIndex;

                public CharIterator(char[] array) {
                    this.array = array;
                    this.currentIndex = 0;
                }

                @Override
                public boolean hasNext() {
                    return currentIndex < array.length;
                }

                @Override
                public Character next() {
                    return array[currentIndex++];
                }
            }

            CharIterator iter = new CharIterator(inputs[1].toCharArray());

            while (iter.hasNext()) {
                if (iter.hasNext()) {
                    char command = iter.next();
                    while (true) {
                        if (iter.hasNext()) {
                            command = iter.next();
                            if (command == 'Q') {
                                Game.save(inputs[1], this, player);
                                return finalWorldFrame;
                            }
                            break;
                        }
                    }
                    player.userMoveCommand(command, finalWorldFrame);
                }
            }
        }
        Game.save(inputs[1], this, player);
        return finalWorldFrame;
    }

    public TETile showCurrentTileUnderMouse(TETile[][] world)
    {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("large", Font.BOLD, 30));
        int Xx = (int)StdDraw.mouseX();
        int Yy = (int)StdDraw.mouseY();
        if (Xx < 0 || Xx > WIDTH-1 || Yy < 0 || Yy > HEIGHT-1)
        {
            return Tileset.NOTHING;
        }

        TETile curTile = world[Xx][Yy];
        String depict = "";
        if (curTile != null)
        {
            if (curTile == Tileset.FLOOR) depict = "A plain and ordinary floor, smooth and sturdy, covered with light yellow wooden boards. It exudes a natural fragrance, providing a warm and comfortable ambiance.";
            else if (curTile == Tileset.WALL) depict = "Taken from the walls of a lost kingdom, bearing the marks of time with a weathered yet resolute appearance.";
            else if (curTile == Tileset.LOCKED_DOOR) depict = "A locked door made of sturdy wood, adorned with intricate patterns and ancient locks. It exudes a mysterious aura.";
        }
        else
        {
            return Tileset.NOTHING;
        }


        int lineWidth = (WIDTH);  //行宽
        int lineHeight = 2;  //行间距
        int heightY = HEIGHT+6-2; //文本开始的高度
        String[] depictText = wrapText(depict, lineWidth);


        for(String line : depictText)
        {
            StdDraw.text(WIDTH/2, heightY, line);
            heightY -= lineHeight;
        }


        StdDraw.show();
        return curTile;
    }


    private void myClearText()
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle( WIDTH/2, HEIGHT+3, WIDTH/2, 3.5);
        StdDraw.show();
    }

    private String[] wrapText(String text, int lineWidth)
    {
        StringBuilder returnString = new StringBuilder();
        String words[] = text.split("\\s+");
        int StringLineLen = 0;

        for(String word : words)
        {
            if (StringLineLen + word.length() <= lineWidth)
            {
                returnString.append(word).append(" ");
                StringLineLen = StringLineLen + word.length() + 1;
            }
            else
            {
                returnString.append("\n").append(word).append(" ");
                StringLineLen = word.length() + 1;
            }
        }

        return returnString.toString().split("\n");
    }


}


