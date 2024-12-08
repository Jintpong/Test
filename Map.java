public class Map {
    private final char[][] map = {
        "####################".toCharArray(),
        "#...............E..#".toCharArray(),
        "#...G..............#".toCharArray(),
        "#.........G........#".toCharArray(),
        "#..................#".toCharArray(),
        "#..................#".toCharArray(),
        "#...G..............#".toCharArray(),
        "#.............G....#".toCharArray(),
        "####################".toCharArray()
    };

    // Return the map
    public char[][] getMap() {
        return map;
    }

}