package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

//import jdk.nashorn.internal.objects.annotations.Setter;


import org.junit.Before;
import org.junit.Test;

import Boards.OthelloBoard;
import Games.Othello;
import PlayerTypes.RandomPlayerType;

/**
 * 
 * @Edited by Lina El Sadek
 *
 */
public class OthelloGameTest {
	
	Othello othello;
	
	@Before
    public void setUp() throws UnsupportedEncodingException {
		
		
		InputStream old = System.in;
		try {
			String data = "1 John 2";
			InputStream testInput = new ByteArrayInputStream(data.getBytes("UTF-8"));
		
			System.setIn(testInput);
			
			othello = new Othello();
			
		
		} finally {
			System.setIn(old);
		}
		
    }
	
	@Test
	public void constructorTest()
	{
		assertNotNull(othello);
		
	}

	@Test
	public void test() {
		 assertEquals(2, othello.getPlayers().length);
		 System.out.println(othello.getPlayers()[0]);
		 for (int i = 0; i < othello.getPlayers().length; i++) {
			 assertEquals(false, othello.getPlayers()[0] instanceof RandomPlayerType);
		 }
	}

}
