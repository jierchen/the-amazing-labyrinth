package algorithm;

import model.tiles.Tile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch implements GraphSearch {

    @Override
    public boolean reachable(Tile[][] tiles, int startingRow, int startingCol, int targetRow, int targetCol) {
        // Variable to keep track of visited tiles
        boolean[][] visited = new boolean[tiles.length][tiles.length];

        // BFS queue
        Queue<Tile> queue = new LinkedList<Tile>();

        // Visit initial tile
        visited[startingRow][startingCol] = true;
        queue.add(tiles[startingRow][startingCol]);

        // Search through queue
        while(queue.size() != 0) {

            Tile currentTile = queue.poll();

            // Tile is reachable
            if(targetRow == currentTile.getRow() && targetCol == currentTile.getCol()) {
                return true;
            }

            // Get all adjacent tiles from currentTile
            ArrayList<Tile> adjTiles = currentTile.getAdjTiles();

            // Check all adjacent tiles to see if an adjacent tile
            // has the target row and column

            for(Tile adjTile: adjTiles) {
                int row = adjTile.getRow();
                int col = adjTile.getCol();

                // Check adjacent tile only if it has not been visited already
                if(!visited[row][col]) {

                    // Mark this tile as visited and enqueue it
                    queue.add(adjTile);
                    visited[row][col] = true;
                }
            }
        }

        return false;
    }
}
