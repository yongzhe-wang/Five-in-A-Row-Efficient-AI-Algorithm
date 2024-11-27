// Node satisfy the following requirements:
// 1. Node can be null or has certain coordinates, neighbors, and player.
// 2. Node has 8 neighbors, corresponding to a 2D board 8 directions.
// 2. If Node != null,
//   2.1 Node.neighbor[i] = Node.neighbor.neighbor[(4-i) mod 8)]
//   Note: Simply means that node and its neighbors should be interconnected, regardless of player.

public class Node {
    int x, y;
    int player;
    // [horizontal, vertical, diagonal, anti-diagonal]
    Node[] neighbors;

    public Node(int x, int y, int player) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.neighbors = new Node[8];
    }

    // Count the number of non-null neighbors.
    public int countNeighbors() {
        int count = 0;
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] != null) {
                count++;
            }
        }
        return count;
    }

    // When placing a new node into one board, connect it to the existing neighbor nodes and the opposite.
    public void updateNode(Node[][] board) {
        // Update neighbors in all four directions
        int[][] directions = BoardUtils.DIRECTIONS;
        for (int i = 0; i < 8; i++) {

            int dx = directions[i][0], dy = directions[i][1];
            int nx = x + dx;
            int ny = y + dy;

            int GRID_SIZE = board.length;

            if (nx >= 0 && nx < GRID_SIZE && ny >= 0 && ny < GRID_SIZE && board[ny][nx] != null) {
                neighbors[i] = board[ny][nx];
            }

            // Connect the neighbors
            if (neighbors[i] != null) {
                neighbors[i].neighbors[Math.floorMod(i - 4, 8)] = this;
            }
        }
    }

    // Check if win
    public boolean checkWin() {
        for (int i = 0; i < 8; i++) {
            int count = 1;
            for (int j : new int[]{i, Math.floorMod(i-4,8)}) {

                // Each direction
                Node current = neighbors[j];
                while (current != null && current.player == player) {
                    count++;
                    current = current.neighbors[j];
                }
            }
            if (count >= 5) {
                return true;
            }
        }
        return false;
    }
}
