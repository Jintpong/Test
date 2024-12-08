public class Command {
    private char[][] map;
    private int goldtowin;
    private int playergold;
    private int playerX;
    private int playerY;
    private int botX;
    private int botY;
    private boolean[][] positionGold;
    private boolean[][] positionExit;
    private boolean endgame;
    private BotMove bot;

    // Constructor
    public Command(char[][] map) {
        this.map = map;
        this.playerX = 3;
        this.playerY = 3;
        this.botX = 4;
        this.botY = 2;
        this.goldtowin = 3;
        this.playergold = 0;
        this.endgame = false;

        this.bot = new BotMove(botX, botY);

        // Initialize the position of gold
        this.positionGold = new boolean[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'G') {
                    positionGold[i][j] = true;
                }
            }
        }

        // Initialize the position of exit
        this.positionExit = new boolean[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'E') {
                    positionExit[i][j] = true;
                }
            }
        }

        // Set initial player and bot positions
        map[playerX][playerY] = 'P';
        map[botX][botY] = 'B';
    }

    public void UserInput() {
        while (!endgame) {
            System.out.println("Enter a command: ");
            String command;

            if (System.console() != null) {
                command = System.console().readLine().trim().toUpperCase();

                // Check the command
                if (command.equals("HELLO")) {
                    System.out.println("Gold to win: " + goldtowin);
                } else if (command.equals("GOLD")) {
                    System.out.println("Gold owned: " + playergold);
                } else if (command.equals("PICKUP")) {
                    if (positionGold[playerX][playerY]) {
                        playergold += 1;
                        positionGold[playerX][playerY] = false;
                        System.out.println("Success. Gold owned: " + playergold);
                    } else {
                        System.out.println("Fail. Gold owned: " + playergold);
                    }
                } else if (command.startsWith("MOVE") && command.length() > 5) {
                    String direction = command.substring(5).toUpperCase().trim();
                    handlePlayerMove(direction);
                } else if (command.equals("LOOK")) {
                    displayGrid();
                } else if (command.equals("QUIT")) {
                    handleQuit();
                } else {
                    System.out.println("Unknown command");
                }

                // Bot turn after processing the player's turn
                if (!endgame) {
                    handleBotMove();
                }

            } else {
                System.out.println("Please run in a terminal.");
                break;
            }
        }
    }

    private void handlePlayerMove(String direction) {
        int new_x = playerX;
        int new_y = playerY;

        // Determine new position
        if (direction.equals("N")) {
            new_x -= 1;
        } else if (direction.equals("S")) {
            new_x += 1;
        } else if (direction.equals("E")) {
            new_y += 1;
        } else if (direction.equals("W")) {
            new_y -= 1;
        } else {
            System.out.println("Invalid Direction");
            return;
        }

        // Validate the move
        if (new_x >= 0 && new_x < map.length && new_y >= 0 && new_y < map[0].length) {
            if (map[new_x][new_y] != '#') {
                map[playerX][playerY] = '.';
                playerX = new_x;
                playerY = new_y;
                map[playerX][playerY] = 'P';
                System.out.println("Success");
                displayMap();
            } else {
                System.out.println("Fail");
            }
        } else {
            System.out.println("Fail the position is out of bound");
        }
    }

    private void handleBotMove() {
        String botCommand = bot.getBotCommand(map, playerX, playerY);
        System.out.println("Bot command: " + botCommand);

        int newBotX = bot.getBotX();
        int newBotY = bot.getBotY();

        // Check if bot catches the player
        if (newBotX == playerX && newBotY == playerY) {
            System.out.println("The bot caught you! Game over.");
            endgame = true;
        } else {
            // Update the bot's position on the map
            map[botX][botY] = '.';
            botX = newBotX;
            botY = newBotY;
            map[botX][botY] = 'B';
        }
    }

    private void handleQuit() {
        if (positionExit[playerX][playerY] || playergold >= goldtowin) {
            System.out.println("WIN");
        } else {
            System.out.println("LOSE");
        }
        endgame = true;
    }

    private void displayGrid() {
        for (int row = -2; row <= 2; row++) {
            for (int col = -2; col <= 2; col++) {
                int x = playerX + row;
                int y = playerY + col;

                if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
                    if (x == playerX && y == playerY) {
                        System.out.print("P");
                    } else if (x == botX && y == botY) {
                        System.out.print("B");
                    } else if (positionGold[x][y]) {
                        System.out.print("G");
                    } else {
                        System.out.print(map[x][y]);
                    }
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }

    private void displayMap() {
        for (char[] row : map) {
            System.out.println(new String(row));
        }
    }
}
