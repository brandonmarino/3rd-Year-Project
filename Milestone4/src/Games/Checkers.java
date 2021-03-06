package Games;

import Boards.CheckersBoard;
import Boards.OthelloBoard;
import PlayerTypes.PlayerType;
import Strategies.Alternative.CheckersObstruct;
import Strategies.Alternative.ObstructPlayerType;
import Strategies.Minimax.CheckersMinimax;
import Strategies.Minimax.MinimaxPlayerType;

public class Checkers extends Game {
	
	public static void main(String[] args) {
        for(int i = 0; i < 10; i++ ) {
            Checkers game = new Checkers();
            game.play();
        }
    }
	
	public Checkers()
	{
		super(new CheckersBoard());
	}
	
	/**
     * Take a turn
     * @return if a turn was successfully completed
     */
    protected boolean takeTurn(PlayerType move) throws common.GameTerminatedException{
        boolean takeTurn = super.takeTurn(move);
        ((CheckersBoard)boardGame).setMoved(takeTurn);
        return takeTurn;
    }
    /**
     * Will specify the specifics of each strategy, avoid specialization in superclass
     */
    protected void getPlayerInfo(){
        super.getPlayerInfo();
        PlayerType[] players = getPlayers();
        for(int i = 0; i< players.length; i++)
            if (players[i] instanceof ObstructPlayerType)
                players[i] = new CheckersObstruct((ObstructPlayerType)players[i]);
            else if (players[i] instanceof MinimaxPlayerType)
                players[i] = new CheckersMinimax((MinimaxPlayerType)players[i]);
            else if (players[i] instanceof ObstructPlayerType)
                players[i] = new CheckersObstruct((ObstructPlayerType)players[i]);
    }
	
}
