package roguemaker.game;

/**
 * @author Quinn Tucker
 */
public interface LevelGenerator {
    
    Level generate();
    
    static void generateCaves(Level level, int floorTile, int wallTile, double density) {
        generateCaves(level, floorTile, wallTile, density, 5);
    }
    
    static void generateCaves(Level level, int floorTile, int wallTile, double density, int smoothness) {
        boolean[][] grid = generateCaves(level.getWidth(), level.getHeight(), density, smoothness);
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                level.setTile(x, y, grid[x][y]? wallTile : floorTile);
            }
        }
    }
    
    static boolean[][] generateCaves(int width, int height, double density) {
        return generateCaves(width, height, density, 5);
    }
    
    static boolean[][] generateCaves(int width, int height, double density, int smoothness) {
        // setup grids
        boolean[][] grid1 = new boolean[width][height];
        boolean[][] grid2 = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean isBorder = x==0 || y==0 || x==width-1 || y==height-1;
                grid1[x][y] = isBorder || Math.random() < density;
            }
        }
        
        // apply cellular automaton
        for (int n = 0; n < smoothness*2; n++) {
            // compute the next step
            for (int x = 1; x < width-1; x++) {
                for (int y = 1; y < height-1; y++) {
                    int count = 0;
                    if (grid1[x+1][y]) count++;
                    if (grid1[x-1][y]) count++;
                    if (grid1[x][y+1]) count++;
                    if (grid1[x][y-1]) count++;
                    if (grid1[x+1][y+1]) count++;
                    if (grid1[x-1][y+1]) count++;
                    if (grid1[x+1][y-1]) count++;
                    if (grid1[x-1][y-1]) count++;
                    grid2[x][y] = grid1[x][y]? count >= 4 : count >= 5;
                }
            }
            
            // swap grids
            boolean[][] tmp = grid1;
            grid1 = grid2;
            grid2 = tmp;
        }
        
        // return
        return grid1;
    }
    
}
