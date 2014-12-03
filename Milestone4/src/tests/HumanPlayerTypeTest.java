package tests;


import static org.junit.Assert.*;

import java.util.ArrayList;

import Boards.Board;
import Boards.OthelloBoard;
import PlayerTypes.HumanPlayerType;
import PlayerTypes.PlayerType;

import org.junit.Before;
import org.junit.Test;

import common.Move;

/**
 * *********************************************************************************************************************************************************
 * 									HumanPlayerTypeTEST 
 * ***********************************************************************************************************************************************************
 *
 * Author: Osama Buhamad 
 * -test for HumanPlayerTypeTEST Class
 * 
 * Edited by: Lina El Sadek
 * for Milestone 3
 */

public class HumanPlayerTypeTest {
	 private PlayerType humanPlayerMove;

	    @Before
	    public void setUp() {
	        humanPlayerMove = new HumanPlayerType(new OthelloBoard(), 0);
	    }

	    @Test
	    public void testGetMove() {
	        // test with case not enough available moves
	        ArrayList<Move> availableMoves = new ArrayList<Move>();
	        humanPlayerMove.setAvailableMoves(availableMoves);
	        assertNotNull(humanPlayerMove);
	        
	    }

}