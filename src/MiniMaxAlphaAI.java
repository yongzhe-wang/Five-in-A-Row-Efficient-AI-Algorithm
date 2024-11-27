import java.util.ArrayList;

// The score and decision algorithm of the AI.
public class MiniMaxAlphaAI {

    public MiniMaxAlphaAI() {}

    // recurseScore calculates the score of depth n of a board, following the rule:
    // 1. Alternating between human (player 1) and AI (player 2).
    // 2. Positive score for AI and negative score for human.
    // 3. Score = Depth_Score_N + Depth_Score_(N-1) + ... + Depth_Score_1

    public int recurseScore(int depth, Node[][] nodeBoard, int player) {
        // Setup Recursion
        depth--;
        if (depth > 0) {

            // Get a list of coordinates that the AI should consider.
            ArrayList<int[]> canPlaceList = BoardUtils.getFlattenBoardNeighbors(nodeBoard);
            for (int[] coor : canPlaceList) {
                // Place a new stone at the coor location.
                Node[][] copyBoard = BoardUtils.copyNodeBoard(nodeBoard);
                Node newNode = new Node(coor[0], coor[1], player);
                copyBoard[coor[1]][coor[0]] = newNode;
                newNode.updateNode(copyBoard);

                // Calculate the score.
                int moveScore = evaluateMove(copyBoard, player);
                if (player == 1) {
                    moveScore = -moveScore;
                }
                return moveScore + recurseScore(depth - 1, copyBoard, 3 - player);
            }
        }
        else {
            return 0;
        }
        return 0;
    }

    // evaluateMultipleLayers loops through "local" placeable points and do:
    // 1. Evaluate corresponding score until depth is reached for each possible AI move.
    // 2. Calculate the final score: moveScore = Layer0 * (depth) * 10 + AllRestLayers
    // Then, it outputs the coordinates of the best move.

    public int[] evaluateMultipleLayers(Node[][] nodeBoard, int player, int depth) {

        // Get a list of coordinates that the AI should consider.
        nodeBoard = BoardUtils.copyNodeBoard(nodeBoard);
        ArrayList<int[]> canPlaceList = BoardUtils.getFlattenBoardNeighbors(nodeBoard);

        int bestScore = Integer.MIN_VALUE;
        int[] bestCoor = new int[2];
        for (int[] coor : canPlaceList) {
            // Create a separate board for each possible move
            Node[][] copyBoard = BoardUtils.copyNodeBoard(nodeBoard);
            Node newNode = new Node(coor[0], coor[1], player);
            copyBoard[coor[1]][coor[0]] = newNode;

            newNode.updateNode(copyBoard);

            // Calculate score based on the rule
            int score2 = recurseScore(depth, copyBoard, 3 - player);
            int moveScore = evaluateMove(copyBoard, player) * (depth)*10 + score2;
            System.out.println(coor[0]+" "+coor[1]+"-》"+moveScore);
            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestCoor = coor;

            }
        }
        System.out.println("This is best Score" + bestScore);
        return bestCoor;
    }


    // Given a nodeBoard, evaluateMove calculates the score for that board summing each point up based on:
    // 1. Human placed and AI placed points are summed up separately.
    // 2. Final score = HumanScore - AIScore * 50
    // Note: A weight of 50 is added here to prioritize defense of the AI, given that human player start first.
    public int evaluateMove(Node[][] nodeBoard, int playerMe) {
        int scoreMe = 0; int scoreHim = 0;

        // Get all the non-null nodes of the board.
        Node[] board = BoardUtils.getFlattenBoard(nodeBoard);
        for (Node node : board) {

            // Calculate the score and accumulate it.
            int nodeScore = evaluateSingleNodeDirections(node,BoardUtils.getSimpleBoard(nodeBoard));
            if (node.player == playerMe) {
                scoreMe += nodeScore;
            }  else {
                scoreHim += nodeScore*50;
            }
        }
        return scoreMe-scoreHim;
    }

    // This function evaluates one node and calculate score based on the following rules:
    // 1. Loop through all 4 directions and calculate score = 10^ (connectCount - blockCount)
    // 2. Sum up all 4 directions.
    public int evaluateSingleNodeDirections(Node node, int[][] board){
        int score = 0;
        int nodePlayer = node.player;

        // Here, to ensure that the node put one direction's count and block together,
        // I pair up the 8 neighbors into group of 2 based on their spatial location.
        for (int i = 0; i < 4; i++) {
            int count = 1; int block = 0;
            for (int j : new int[]{i, Math.floorMod(i-4,8)}) {

                // Some boring logic to ensure everything is as described in the rule.
                if (BoardUtils.isValidDirection(node, j, board)) {
                    if (node.neighbors[j]!=null) {
                        Node current = node.neighbors[j];
                        while (current != null && current.player == nodePlayer) {
                            count++;
                            if (BoardUtils.isValidDirection(current, j, board)) {
                                current = current.neighbors[j];
                            } else {
                                block++;
                                current = null;
                                break;
                            }
                        }
                        if (current != null) {
                            if (current.player == (3 - nodePlayer)) {
                                block++;
                            } else if (!BoardUtils.isValidDirection(current, j, board)) {
                                block++;
                            }
                        }
                    }
                } else {
                    block++;
                }
            }
            score+= scorePoint(count, (block));
        }
        return score;
    }

    // Simple function to calculate the score.
    public int scorePoint(int countMe, int blockMe) {
        return (int) (Math.pow(10,countMe-blockMe));
    }

    // Only for test purpose. evaluateMultipleLayers is a depth 1 version of above evaluateMultipleLayers.
    public int[] evaluateLayer(Node[][] nodeBoard, int player) {
        ArrayList<int[]> canPlaceList = BoardUtils.getFlattenBoardNeighbors(nodeBoard);
        int[] bestCoor = null;
        int bestScore = Integer.MIN_VALUE;
        for (int[] coor :canPlaceList) {
            Node[][] copyBoard = BoardUtils.copyNodeBoard(nodeBoard);
            Node newNode = new Node(coor[0],coor[1], player);
            copyBoard[coor[1]][coor[0]] = newNode;

            newNode.updateNode(copyBoard);

            int moveScore = evaluateMove(copyBoard, player);
            System.out.println(coor[0]+" "+coor[1]+"-》"+moveScore);
            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestCoor = coor;
            }

        }
        return bestCoor;
    }


}

