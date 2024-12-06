public class Main {
    public static void main(String[] args) {
        // Create an instance of the Map class
        Map game_map = new Map();

        // Get the map
        char[][] map = game_map.get_Map();


        Command command = new Command(map);

        for (char[] i : map) {
            System.out.println(new String(i));
        }
        
        command.UserInput();

    }
}


