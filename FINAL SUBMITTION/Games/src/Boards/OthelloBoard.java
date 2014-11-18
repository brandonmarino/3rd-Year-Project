package Boards;

import java.util.ArrayList;

/**
 * *********************************************************************************************************************************************************
 * Board Class Sets Up the board, Checks Winning Conditions, And Updates Status of Othello
 * ***********************************************************************************************************************************************************
 *
 * * Adapted from Board source of TIC TAC TOE Authored by Lina
 * Milestone 1:
 * Adapting Author: Brandon Marino
 * Much of the code is similar however i've changed the method to use implicit enum values opposed to constants which were set to an integer value
 * Also changed the intended game to Othello, not tic tac toe
 * Milestone 2:
 * Adapting Author: Brandon Marino
 * -Made it a subclass of Board
 * -removed redundant code
 * -fixed glitches
 * -cleaned up some code
 */
public class OthelloBoard extends Board {
    private boolean blackMoved; //pay attention to if the black player made a move on their last attempt
    private boolean whiteMoved; // pay attention to if the white player made a move on their last attempt

    /**
     * Define the parameters of the othello board
     */
    public OthelloBoard() {
        super(4);    //ask for a 4x4 board
        setPlayerTiles('B', 'W');
        setCell(PLAYER.PLAYER1, DIMENSIONS / 2 - 1, DIMENSIONS / 2);
        setCell(PLAYER.PLAYER1, DIMENSIONS / 2, DIMENSIONS / 2 - 1);
        setCell(PLAYER.PLAYER2, DIMENSIONS / 2 - 1, DIMENSIONS / 2 - 1);    //place 4 tiles in middle of board
        setCell(PLAYER.PLAYER2, DIMENSIONS / 2, DIMENSIONS / 2);
        scores[0] = DIMENSIONS * DIMENSIONS / 2 - 2;
        scores[1] = DIMENSIONS * DIMENSIONS / 2 - 2;
        blackMoved = true;
        whiteMoved = true;
    }

    /*****************************************************************************************************************************************************************
     *             Getter Methods for variables and constants defined above
     * ****************************************************************************************************************************************************************/

    /**
     * get moved state of black player (Player 1)
     * @return moved state
     */
    private boolean getBlackMoved(){
        return blackMoved;
    }

    /**
     * get moved state of white player (Player w)
     * @return moved state
     */
    private boolean getWhiteMoved(){
        return whiteMoved;
    }

    /**
     * Return all legal moves that this player could make on this othello board
     * @return a list of possible moves
     */
    public ArrayList<Integer[]> getPossibleMoves(){
        ArrayList<Integer[]> legalMoves = new ArrayList<Integer[]>();
        boolean isLegal = false;
        for (Integer[] possibleMove: getEmptySpaces()){
            ArrayList<Integer[]> adjEnemies = findadjacentEnemies(possibleMove[0], possibleMove[1]);
            isLegal = false;
            for (Integer[] ret : adjEnemies){
                if (canFlank(possibleMove[0], possibleMove[1], ret[0] - possibleMove[0], ret[1] - possibleMove[1]) > 0){
                    isLegal = true;
                }

            }
            if (isLegal)
                legalMoves.add(possibleMove);
        }
        return legalMoves;
    }

    /**
     * Get the current player's disc count
     * @return the number of discs the current user has
     */
    private int getPlayerDiscs(){
        switch (getCurrentPlayer()) {
            case PLAYER1:
                return scores[0];
            case PLAYER2:
                return scores[1];
        }
        return 0;
    }

    /**
     * Create and populate a cloned of the othello board with the current board's values
     * @return a cloned othello
     */
    public Board getClone() {
        OthelloBoard clonedBoard = new OthelloBoard();
        clonedBoard = (OthelloBoard)super.getClone(clonedBoard);
        clonedBoard.setBlackMoved( this.getBlackMoved() );
        clonedBoard.setWhiteMoved( this.getWhiteMoved() );
        return clonedBoard;
    }

    /*****************************************************************************************************************************************************************
     *             Setter Methods for variables and constants defined above
     *****************************************************************************************************************************************************************/

    public void setMoved(Boolean set){
        switch(getCurrentPlayer()){
            case PLAYER1:
                blackMoved = set;
                break;
            case PLAYER2:
                whiteMoved = set;
                break;
        }
    }

    protected void setBlackMoved(boolean set){
        blackMoved = set;
    }

    protected void setWhiteMoved(boolean set){
        whiteMoved = set;
    }

    /*****************************************************************************************************************************************************************
     *              Functions to print Othello Board
     *****************************************************************************************************************************************************************/

    /**
     * Print Othello Board
     */
    public void printBoard() {
        super.printBoard();
        System.out.print("Black Discs Left: " + scores[0] + "\nWhite Discs Left: " + scores[1] + "\n\n");
    }

    /*****************************************************************************************************************************************************************
     *              Conditional Methods to test for Winning Conditions or Draw
     *****************************************************************************************************************************************************************/

    /**
     * Find if the game has been one by a player
     * @return either the winning player or null if no one has won
     */
    public PLAYER hasBeenWon() {
        if ((scores[0] <= 0 || !blackMoved) && (scores[1] <= 0 || !whiteMoved))      //both players cannot move anymore
            return countWinner();
        return null;        //equivalent to returning false
    }

    /**
     * Method to count the tiles on the board once either both players cannot make a move or the board is full
     * Players with the most tiles facing their colour wins the game
     * <p/>
     * Returns: Enum representation of the Players
     */
    private PLAYER countWinner() {
        int blackSpaces = countBlack();
        int whiteSpaces = countWhite();

        if (blackSpaces > whiteSpaces)
            return PLAYER.PLAYER1;
        else if (whiteSpaces > blackSpaces)
            return PLAYER.PLAYER2;
        else
            return PLAYER.EMPTY;
    }
    public int countBlack(){
        int blackSpaces = 0;
        for (int currentRow = 0; currentRow < DIMENSIONS; currentRow++)
            for (int currentColumn = 0; currentColumn < DIMENSIONS; currentColumn++)
                if (getCell(currentRow, currentColumn) == PLAYER.PLAYER1)
                    blackSpaces++;
        return blackSpaces;
    }
    public int countWhite(){
        int whiteSpaces = 0;
        for (int currentRow = 0; currentRow < DIMENSIONS; currentRow++)
            for (int currentColumn = 0; currentColumn < DIMENSIONS; currentColumn++)
                if (getCell(currentRow, currentColumn) == PLAYER.PLAYER2)
                    whiteSpaces++;
        return whiteSpaces;
    }

    /*****************************************************************************************************************************************************************
     *              Methods to Make PlayerTypes
     *****************************************************************************************************************************************************************/
    /**
     * Attempt a move in Othello
     * To make a move in othello, an empty space must be found that can be used to 'flank' the other team's players
     * @param row row of empty cell
     * @param column column of possible move
     * @return
     */
    public boolean attemptMove(int row, int column){
        if (getPlayerDiscs() <=0)
            return false;
        ArrayList<Integer[]> adjEnemies = findadjacentEnemies(row, column);
        boolean madeMove = false;
        for (Integer[] ret : adjEnemies){       //for all adjacent enemies
            int chainLength = canFlank(row, column, ret[0] - row, ret[1] - column);
            if (chainLength != 0){
                madeMove = true;                //if any chain is found, this move was made
                flankChain(chainLength, row, column, ret[0] - row, ret[1] - column);
            }
        }
        if (madeMove)
            decrementDiscs();   //remove a disc from player's pile
        return madeMove;    //if a move was successfully made, it will be reported
    }

    /**
     * Flip a chain chain of enemy pieces and place your own in the empty space
     *
     * @param originalRow    the row of the original empty space
     * @param originalColumn the column of the original empty space
     * @param slopeRow       the offset of the adjacent enemy to the empty space
     * @param slopeColumn    the offset of the adjacent enemy to the empty space
     * @return if the flip completed properly
     */
    private void flankChain(int length, int originalRow, int originalColumn, int slopeRow, int slopeColumn) {
        int currentRow;     //first element in the chain
        int currentColumn;
            //flip chain
        for (int slopeIncrement = 0; slopeIncrement < length; slopeIncrement++) {  //traverse the chain
            currentRow = originalRow + (slopeRow * slopeIncrement);          //find next item
            currentColumn = originalColumn + (slopeColumn * slopeIncrement); //find next item
            setCell(getCurrentPlayer(), currentRow, currentColumn);
        }
    }

    /**
     * Find if a chain can be made out of an empty space, adjacent enemy and then a possible chain of enemies leading
     * to a space owned by the current player
     *
     * @param originalRow    the row of the original empty space
     * @param originalColumn the column of the original empty space
     * @param slopeRow       the offset of the adjacent enemy to the empty space
     * @param slopeColumn    the offset of the adjacent enemy to the empty space
     * @return the length of the chain, null if not chain-able
     */
    public int canFlank(int originalRow, int originalColumn, int slopeRow, int slopeColumn) {
        int currentRow;  //first element in the chain return null
        int currentColumn;
        for (int incrementChain = 1; incrementChain < DIMENSIONS*2; incrementChain++) {
            currentRow = originalRow + (slopeRow * incrementChain);          //find next item
            currentColumn = originalColumn + (slopeColumn * incrementChain); //find next item

            if (isWithinBounds(currentRow, currentColumn))
                if (getCell(currentRow, currentColumn) == PLAYER.EMPTY || !isWithinBounds(currentRow, currentColumn))
                    return 0;
                 else if (getCell(currentRow, currentColumn) == getCurrentPlayer())  //found a flank-able trail
                    return incrementChain;
        }
        return 0;
    }

    /**
     * Find all adjacent enemies to a particular empty space
     * @param originalRow    the empty space row
     * @param originalColumn the empty space column
     * @return a list of the enemies around an empty space
     */
    private ArrayList<Integer[]> findadjacentEnemies(int originalRow, int originalColumn) {
        ArrayList<Integer[]> adjacentEnemies = new ArrayList<Integer[]>();
        PLAYER enemy = getEnemy();
        for (int currentRow = originalRow - 1; currentRow <= (originalRow + 1); currentRow++)                    //span the spaces connected to the move location
            for (int currentColumn = originalColumn - 1; currentColumn <= (originalColumn + 1); currentColumn++) {
                if (isWithinBounds(currentRow, currentColumn)) {                              //if it is not the center location and that it's not off the board
                    if (getCell(currentRow, currentColumn) == enemy) {                      //if this spot has the potential of being surrounded
                        Integer[] ret = {currentRow, currentColumn};
                        adjacentEnemies.add(ret);
                    }
                }
            }
        return adjacentEnemies;
    }

    /******************************************************************************************************************************************************************
     * 				Internal functions for use of Othello Logic
     ******************************************************************************************************************************************************************

    /**
     * lower the current player's disk count by 1
     */
    private void decrementDiscs() {
        if (getCurrentPlayer() == PLAYER.PLAYER1)
            scores[0]--;
        else if (getCurrentPlayer() == PLAYER.PLAYER2)
            scores[1]--;
    }
}