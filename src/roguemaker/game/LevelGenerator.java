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
        // setup grids
        boolean[][] grid1 = new boolean[level.getWidth()][level.getHeight()];
        boolean[][] grid2 = new boolean[level.getWidth()][level.getHeight()];
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                boolean isBorder = x==0 || y==0 || x==level.getWidth()-1 || y==level.getHeight()-1;
                grid1[x][y] = isBorder || Math.random() < density;
            }
        }
        
        // apply cellular automaton
        for (int n = 0; n < smoothness*2; n++) {
            // compute the next step
            for (int x = 1; x < level.getWidth()-1; x++) {
                for (int y = 1; y < level.getHeight()-1; y++) {
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
        
        // fill the Level
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                level.setTile(x, y, grid1[x][y]? wallTile : floorTile);
            }
        }
    }
    
}
