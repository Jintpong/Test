import java.lang.Math;

public class BotMove{
    private int botX;
    private int botY; 

    public BotMove(int start_X, int start_Y){
        this.botX = start_X;
        this.botY = start_Y;
    }

    public String getBotCommand(char[][] map, int playerX, int playerY){
        String[] moves = {"Move N", "Move S", "Move W", "Move E"};
        int[] change_in_x = {-1, 1, 0, 0};
        int[] change_in_y = {0, 0, -1, 1};

        double minDistance = Double.MAX_VALUE;
        String bestMove = "";

        for (int i =0; i < moves.length; i++){
            int newBotX = botX + change_in_x[i];
            int newBotY = botY + change_in_y[i];

            if (newBotX >= 0 && newBotX < map.length && newBotY >= 0 && newBotY < map[0].length && map[newBotX][newBotY] != '#'){
                double distanceToPlayer = Math.sqrt(Math.pow(playerX - newBotX, 2) + Math.pow(playerY - newBotY, 2));

                if (distanceToPlayer < minDistance){
                    minDistance = distanceToPlayer;
                    bestMove = moves[i];
                }
            }
        }
        if (!bestMove.isEmpty()){
            map[botX][botY] = '.';

            if (bestMove.equals("Move N")) {
                botX -= 1; 
            }
            else if (bestMove.equals("Move S")){
                botX += 1;
            }
            else if (bestMove.equals("Move W")){
                botY -= 1;
            }
            else if (bestMove.equals("Move E")){
                botY += 1;
            }

            map[botX][botY] = 'B';
        }
        return bestMove;

    }


}