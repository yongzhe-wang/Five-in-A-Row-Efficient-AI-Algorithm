**Five in A Row Game Project**

This is a simple project that implements a local search algorithm for the game Five in A Row (Gomoku) and the game itself in Java.
This algorithm can win ~95% of the time against human players and requires less than 200 lines of code.

**Key Ideas**
Given that the board is 19 by 19, it is impractical to search every slot, especially as the depth increases.  Thus, the algorithm only search locally, the neighbors of existing pieces. The score calculation is based on two factors:
1. Number of connected pieces at each direction. (increase the score)
2. Number of blocks on both ends. (decrease the score)
Using this scoring function, the algorithm evaluates all possible local moves and recurses for a few iterations.


**Description of Files**

 **BoardUtils.java** - a static class that implements a series of useful static functions (maniuplate
 arrays, getters, calculate directions) that is used throughout all other parts of the project.
 
 **GomokuBoard.java** - class that act as the board and implements the main function in recording
 player's moves and determining who wins. 
 
 **MainFrame.java** - class that sets up the GUI, connecting the GomokuBoard and StartScreen classes.
 
 **MiniMaxAlphaAI.java** - class that implements a search algorithm, acting as AI.
 
 **Node.java**- linked structure.java used by both the AI and GomokuBoard to determine wining or calculate score.
 
 **StartScreen.java** - GUI class that sets up the user interface for the start screen.

