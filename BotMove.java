import java.util.Random;
// Define the BotMove class
public class BotMove {
    private int botX;
    private int botY;
    private Random random;

    public BotMove(int start_X, int start_Y) {
        this.botX = start_X;
        this.botY = start_Y;
        this.random = new Random();
    }

    public String getBotCommand(char[][] map, int playerX, int playerY) {
        // Checking the 5x5 grid around the bot to check for the player
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                int checkX = botX + dx;
                int checkY = botY + dy;

                // Checking that the bot will not out of bound of the map
                if (checkX >= 0 && checkX < map.length && checkY >= 0 && checkY < map[0].length) {
                    if (map[checkX][checkY] == 'P') {
                        // Move towards the player
                        if (checkX < botX && botX - 1 >= 0 && map[botX - 1][botY] != '#') {
                            //Clear old position
                            updateMap(map, botX, botY); 
                            botX -= 1;
                            //Make the new position for the bot
                            map[botX][botY] = 'B'; 
                            return "Move N";
                        } 
                        else if (checkX > botX && botX + 1 < map.length && map[botX + 1][botY] != '#') {
                            updateMap(map, botX, botY);
                            botX += 1;
                            map[botX][botY] = 'B';
                            return "Move S";
                        } 
                        else if (checkY < botY && botY - 1 >= 0 && map[botX][botY - 1] != '#') {
                            updateMap(map, botX, botY);
                            botY -= 1;
                            map[botX][botY] = 'B';
                            return "Move W";
                        } 
                        else if (checkY > botY && botY + 1 < map[0].length && map[botX][botY + 1] != '#') {
                            updateMap(map, botX, botY);
                            botY += 1;
                            map[botX][botY] = 'B';
                            return "Move E";
                        }
                    }
                }
            }
        }

        // If the player is not found make the bot move randomly
        return moveRandomly(map);
    }

    private String moveRandomly(char[][] map) {
        String[] moves = {"Move N", "Move S", "Move W", "Move E"};
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        // Try random moves until a valid one is found
        for (int i = 0; i < 4; i++) {
            int direction = random.nextInt(4);
            int newBotX = botX + dx[direction];
            int newBotY = botY + dy[direction];

            if (newBotX >= 0 && newBotX < map.length && 
                newBotY >= 0 && newBotY < map[0].length && 
                map[newBotX][newBotY] != '#') {
                updateMap(map, botX, botY); 
                botX = newBotX;
                botY = newBotY;
                map[botX][botY] = 'B'; 
                return moves[direction];
            }
        }

        // Default to moving east 
        if (botY + 1 < map[0].length && map[botX][botY + 1] != '#') {
            updateMap(map, botX, botY);
            botY += 1;
            map[botX][botY] = 'B';
            return "Move E";
        }

        // Return bot remain in place if any move is not possible
        return "Bot remain in place";
    }

    private void updateMap(char[][] map, int oldBotX, int oldBotY) {
        map[oldBotX][oldBotY] = '.'; 
    }

    // Return the X coordinate of the bot
    public int getBotX() {
        return botX;
    }

    // Return the Y coordinate of the bot
    public int getBotY() {
        return botY;
    }
}
