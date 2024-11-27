import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AITests {
    MiniMaxAlphaAI ai = new MiniMaxAlphaAI();

    @Test
    public void testLineEvaluate1() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(1, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 1);
        BoardUtils.print2DArray(board.board);
        for (Node node : BoardUtils.getFlattenBoard(board.nodeBoard)) {
            System.out.println(node.countNeighbors());
        }
    }

    @Test
    public void testScorePoints1() {
        System.out.println(ai.scorePoint(4,0));
        System.out.println(ai.scorePoint(4,1));
        System.out.println(ai.scorePoint(3,0));
        System.out.println(ai.scorePoint(3,1));
    }

    @Test
    public void testValidDirection() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        Node[][] myBoard = board.nodeBoard;
        Node node = myBoard[0][0];
        for (int i = 0; i < 8; i++) {
            System.out.println(BoardUtils.isValidDirection(node, i, board.board));
        }
    }

    @Test
    public void testValidDirection2() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 8);
        Node[][] myBoard = board.nodeBoard;
        Node node = myBoard[8][0];
        for (int i = 0; i < 8; i++) {
            System.out.println(BoardUtils.isValidDirection(node, i, board.board));
        }
    }

    @Test
    public void testSingleNode() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        Node[][] myBoard = board.nodeBoard;
        Node node = myBoard[0][0];
        int score = ai.evaluateSingleNodeDirections(node, board.board);
        System.out.println(score);
    }
    @Test
    public void testSingleNode2() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(0, 1);
        Node[][] myBoard = board.nodeBoard;
        BoardUtils.print2DArray(board.board);
        Node node = myBoard[0][0];
        assertNotNull(node.neighbors[5]);

        int score = ai.evaluateSingleNodeDirections(node, board.board);
        System.out.println(score);
    }

    @Test
    public void testSingleNode3() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 0);
        Node[][] myBoard = board.nodeBoard;
        BoardUtils.print2DArray(board.board);
        Node node = myBoard[0][0];
        assertNotNull(node.neighbors[5]);

        int score = ai.evaluateSingleNodeDirections(node, board.board);
        System.out.println(score);
    }

    @Test
    public void testSingleNode4() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 0);
        board.placePiece(4, 3);
        board.placePiece(2, 0);
        Node[][] myBoard = board.nodeBoard;
        BoardUtils.print2DArray(board.board);
        Node node = myBoard[0][1];
//        assertNotNull(node.neighbors[5]);

        int score = ai.evaluateSingleNodeDirections(node, board.board);
        System.out.println(score);
    }

    @Test
    public void testSingleNode5() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 0);
        board.placePiece(4, 3);
        board.placePiece(2, 0);
        Node[][] myBoard = board.nodeBoard;
        BoardUtils.print2DArray(board.board);
        Node node = myBoard[0][1];
//        assertNotNull(node.neighbors[5]);

        int score = ai.evaluateSingleNodeDirections(node, board.board);
        System.out.println(score);
    }

    @Test
    public void testSingleNode6() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 0);
        board.placePiece(4, 3);
        board.placePiece(2, 0);
        board.placePiece(4, 4);
        Node[][] myBoard = board.nodeBoard;
        BoardUtils.print2DArray(board.board);
        Node node = myBoard[4][4];

        int score = ai.evaluateSingleNodeDirections(node, board.board);
        System.out.println(score);
    }

    @Test
    public void testMoveEvaluate1() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 0);
        board.placePiece(4, 3);
        board.placePiece(2, 0);
        board.placePiece(3, 0);
        Node[][] myBoard = board.nodeBoard;
        BoardUtils.print2DArray(board.board);

        int score = ai.evaluateMove(myBoard, 2);
        System.out.println(score);
    }

    @Test
    public void testMoveEvaluate2() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 2);
        board.placePiece(4, 2);
        board.placePiece(0, 1);
        board.placePiece(3, 3);
        Node[][] myBoard = board.nodeBoard;
        BoardUtils.print2DArray(board.board);

        int score = ai.evaluateMove(myBoard, 2);
        System.out.println(score);
    }

    @Test
    public void testMoveLayer1() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 0);
        board.placePiece(4, 3);
        board.placePiece(2, 0);
//        board.placePiece(3, 0);
        Node[][] myBoard = board.nodeBoard;
        BoardUtils.print2DArray(board.board);

        int[] coord = ai.evaluateLayer(myBoard, 2);
        System.out.println("Shoudl Place: "+ coord[0]+ "  "+ coord[1]);
    }

    @Test
    public void testEvaluateSingle() {
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 0);
        board.placePiece(4, 3);
        board.placePiece(2, 0);
//        board.placePiece(3, 0);
        Node[][] myBoard = board.nodeBoard;
        BoardUtils.print2DArray(board.board);


        ArrayList<int[]> canPlaceList = BoardUtils.getFlattenBoardNeighbors(myBoard);
        int[] bestCoor = null;
        int bestScore = -999999;
        int[] coor = new int[]{5,3};
        Node[][] copyBoard = BoardUtils.copyNodeBoard(myBoard);
        Node newNode = new Node(coor[0],coor[1], 2);
        copyBoard[coor[1]][coor[0]] = newNode;
        newNode.updateNode(copyBoard);
        BoardUtils.print2DArray(BoardUtils.getSimpleBoard(copyBoard));

        int moveScore = ai.evaluateMove(copyBoard, 2);
        System.out.println(coor[0]+" "+coor[1]+"-》"+moveScore);
        System.out.println("_________________");


    }

    @Test
    public void testConnections() {
        MiniMaxAlphaAI ai = new MiniMaxAlphaAI();
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(1, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 1);
        board.placePiece(0, 2);
        board.placePiece(1, 2);
        board.placePiece(0, 3);
//        board.placePiece(1, 4);
        BoardUtils.print2DArray(board.board);
//        [3, 5, 5, 8, 5, 8, 4, 1]
//        1 4-》2141253396
        Node[] nodes = BoardUtils.getFlattenBoard(board.nodeBoard);
        int score = ai.evaluateSingleNodeDirections(nodes[0],board.board);
        System.out.println(score);
        System.out.println(ai.evaluateMove(board.nodeBoard, 2));
        System.out.println("This is the move score if chosen: " + score);
        ArrayList<int[]> canPlaceList = BoardUtils.getFlattenBoardNeighbors(board.nodeBoard);
        int[] bestCoor = null;
        int bestScore = -999999;
        System.out.println("------------------------------------");
        for (int[] coor : canPlaceList) {
//            coor = new int[]{1,4};
            Node[][] copyBoard = BoardUtils.copyNodeBoard(board.nodeBoard);
            System.out.println("Coordinate of move to add: "+coor[0]+"   "+ coor[1]);
            Node newNode = new Node(coor[0], coor[1], 2);
            copyBoard[coor[1]][coor[0]] = newNode;
            newNode.updateNode(copyBoard);
            BoardUtils.print2DArray(BoardUtils.getSimpleBoard(copyBoard));

            int moveScore = ai.evaluateMove(copyBoard, 2);
            System.out.println(coor[0] + " " + coor[1] + "-》" + moveScore);
//            break;
        }
    }


    @Test
    public void testConnections2() {
        MiniMaxAlphaAI ai = new MiniMaxAlphaAI();
        GomokuBoard board = new GomokuBoard(true);
        board.placePiece(0, 0);
        board.placePiece(0, 1);
        board.placePiece(1, 0);
        board.placePiece(1, 2);
        board.placePiece(2, 0);
        board.placePiece(0, 3);
        board.placePiece(3, 0);
//        board.placePiece(1, 4);
        BoardUtils.print2DArray(board.board);
        System.out.println(ai.evaluateMove(board.nodeBoard, 2));
        ArrayList<int[]> canPlaceList = BoardUtils.getFlattenBoardNeighbors(board.nodeBoard);
        int[] bestCoor = null;
        int bestScore = -999999;
        System.out.println("------------------------------------");
        for (int[] coor : canPlaceList) {
//            coor = new int[]{1,4};
            Node[][] copyBoard = BoardUtils.copyNodeBoard(board.nodeBoard);
            System.out.println("Coordinate of move to add: "+coor[0]+"   "+ coor[1]);
            Node newNode = new Node(coor[0], coor[1], 2);
            copyBoard[coor[1]][coor[0]] = newNode;
            newNode.updateNode(copyBoard);
            BoardUtils.print2DArray(BoardUtils.getSimpleBoard(copyBoard));

            int moveScore = ai.evaluateMove(copyBoard, 2);
            System.out.println(coor[0] + " " + coor[1] + "-》" + moveScore);
//            break;
        }
    }
}