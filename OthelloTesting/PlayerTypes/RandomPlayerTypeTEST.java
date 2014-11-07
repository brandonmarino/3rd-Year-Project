package PlayerTypes;

import static org.junit.Assert.*;
import java.util.ArrayList;
import PlayerTypes.RandomPlayerType;
import org.junit.Before;
import org.junit.Test;

/**
 * *********************************************************************************************************************************************************
 * 									RandomPlayerTypeTEST 
 * ***********************************************************************************************************************************************************
 *
 * Author: Osama Buhamad 
 * -test for RandomPlayerTypeTEST Class
 * 
 */

public class RandomPlayerTypeTEST {
	private PlayerType randomPlayer; 
	
	 	@Before
	    public void setUp() {
		 randomPlayer = new RandomPlayerType();
	    }

	 	@Test
	    public void testGetMove() {
	        // test with case not enough available moves
	        ArrayList<Integer[]> availableMoves = new ArrayList<Integer[]>();
	        randomPlayer.setAvailableMoves(availableMoves);
	        assertNull(randomPlayer.getMove());
	 }

}
