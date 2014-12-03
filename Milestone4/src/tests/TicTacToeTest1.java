package tests;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Boards.Board;
import Boards.Board.GAME_STATE;
import Boards.Board.PLAYER;
import Games.Othello;
import Games.TicTacToe;

public class TicTacToeTest1 {
	@Before
    public void setUp() throws UnsupportedEncodingException{
		TicTacToe ticTacToe;
		
		InputStream old = System.in;
		try {
			String data = "1 John 1 Joe 1 2 3 4 5";
			InputStream testInput = new ByteArrayInputStream(data.getBytes("UTF-8"));
		
			System.setIn(testInput);
			ticTacToe = new TicTacToe();
			
			ticTacToe.play();
			Board board = ticTacToe.getBoard();
			assertEquals(board.getCurrentState(), GAME_STATE.PLAYER1_WON);
			assertFalse(board.getCurrentState() == GAME_STATE.PLAYER2_WON);
		} finally {
			System.setIn(old);
		}
	}
	
	@Test
	public void test() {
		
	}
}