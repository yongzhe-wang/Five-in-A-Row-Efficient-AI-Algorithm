import java.util.ArrayList;

// This class includes many useful utils used across all game files.
public class BoardUtils {
    // Used to traverse through the nodes.
    public static int[][] DIRECTIONS = {
            {-1, -1},  // Anti-diagonal up-left
            {0, -1},   // Vertical up
            {1, -1},   // Diagonal up-right
            {1, 0},   // Horizontal right
            {1, 1},  // Diagonal down-right
            {0, 1},  // Vertical down
            {-1, 1}, // Anti-diagonal down-left
            {-1, 0}   // Horizontal left
    };

    // Given a NodeBoard, return a 2D int array SimpleBoard:
    // 1 - player 1
    // 2 - player 2
    // 0 - empty
    public static int[][] getSimpleBoard(Node[][] board) {
        int[][] simpleBoard = new int[board.length][board.length];
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                if (board[x][y] != null) {
                    simpleBoard[x][y] = board[x][y].player;
                }
            }
        }
        return simpleBoard;
    }

    // Get all the non-null nodes of a NodeBoard.
    public static Node[] getFlattenBoard(Node[][] board) {
        ArrayList<Node> nonNullNodes = new ArrayList<>();
        for (Node[] row : board) {
            for (Node node : row) {
                if (node != null) {
                    nonNullNodes.add(node);
                }
            }
        }
        return nonNullNodes.toArray(new Node[0]);
    }

    // Check if move is valid (e.g., in board and placeable)
    public static boolean isValidMove(int x, int y, int[][] board) {
        return x >= 0 && x <  board.length && y >= 0 && y < board.length && board[y][x] == 0;
    }

    // Used to check if moving over the boundary of the board.
    public static boolean isValidDirection(Node node, int i, int[][] board) {
        int x = node.x + DIRECTIONS[i][0];
        int y = node.y + DIRECTIONS[i][1];

        return x >= 0 && x <  board.length && y >= 0 && y < board.length;
    }

    // Used for AI to save computation power by only searching local points.
    // Returns a list of all the nodes' null neighbors.
    public static ArrayList<int[]> getFlattenBoardNeighbors(Node[][] board) {
        ArrayList<int[]> placeableMoves = new ArrayList<>();
        Node[] flattenBoard = BoardUtils.getFlattenBoard(board);


        for (Node node : flattenBoard) {
            int x = node.x; int y = node.y;

            for (int i = 0; i < 8; i++) {
                int[] coor = new int[]{x+DIRECTIONS[i][1], y+DIRECTIONS[i][0]};
                if (BoardUtils.isValidMove(coor[0], coor[1], BoardUtils.getSimpleBoard(board))) {
                    if (board[coor[1]][coor[0]] == null) {
                        // Check if the x, y pair is already in the list
//                        System.out.print((x + "  " + y + "->>>>" + coor[0] + "  "+ coor[1] + "  |  "));
                        boolean exists = false;
                        for (int[] move : placeableMoves) {
                            if (move[0] == coor[0] && move[1] == coor[1]) {
                                exists = true;
                                break;
                            }
                        }
                        // If not found, add to the list
                        if (!exists) {
                            placeableMoves.add(coor);
                        }
                    }
                }
            }
        }
        return placeableMoves;
    }

    // For debug purpose, print the board
    public static void print2DArray(int[][] array) {
        for (int[] row : array) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println(); // Move to the next line after printing each row
        }
    }

    //Convert a simple board to node board. Used to copy board for AI simulation.
    public static Node[][] getNodeBoard(int[][] board) {
        Node[][] nodeBoard = new Node[board.length][board.length];
        // Create the nodes.
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                int player = board[y][x];
                if (player != 0) {
                    nodeBoard[y][x]= new Node(x,y,player);
                }
            }
        }
        // Connect the nodes together.
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                Node currentNode = nodeBoard[y][x];
                if (currentNode != null) {currentNode.updateNode(nodeBoard);}
            }
        }
        return nodeBoard;
    }

    // Return a new copy of a node board.
    public static Node[][] copyNodeBoard(Node[][] original) {
        Node[][] copy = getNodeBoard(getSimpleBoard(original));
        return copy;
    }
}
