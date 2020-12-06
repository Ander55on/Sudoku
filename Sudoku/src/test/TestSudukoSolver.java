package test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import solver.Sudoku;
import solver.SudokuSolver;

class TestSudukoSolver {
	SudokuSolver solver;


	@BeforeEach
	void setUp() throws Exception {
		solver = new Sudoku();
	}

	@AfterEach
	void tearDown() throws Exception {
		solver = null;
	}
	

	/*
	 *  
	 *  TESTCASE ONE
	 *
	 */

	//Test solve Empty Sudoku
	@Test
	void testSolveEmptySudoku() {
		assertTrue(solver.solve());
	}
	
	//Test solve a Sudoku
	@Test
	void testSolveASudoku() {
		solver.setCell(0, 2, 8);
		solver.setCell(0, 5, 9);
		solver.setCell(0, 7, 6);
		solver.setCell(0, 8, 2);
		solver.setCell(1, 8, 5);
		solver.setCell(2, 0, 1);
		solver.setCell(2, 2, 2);
		solver.setCell(2, 3, 5);
		solver.setCell(3, 3, 2);
		solver.setCell(3, 4, 1);
		solver.setCell(3, 7, 9);
		solver.setCell(4, 1, 5);
		solver.setCell(4, 6, 6);
		solver.setCell(5, 0, 6);
		solver.setCell(5, 7, 2);
		solver.setCell(5, 8, 8);
		solver.setCell(6, 0, 4);
		solver.setCell(6, 1, 1);
		solver.setCell(6, 3, 6);
		solver.setCell(6, 5, 8);
		solver.setCell(7, 0, 8);
		solver.setCell(7, 1, 6);
		solver.setCell(7, 4, 3);
		solver.setCell(7, 6, 1);
		solver.setCell(8, 6, 4);
		
		assertTrue(solver.solve());
	} 
	
	
	/*
	 *  
	 *  TESTCASE TWO
	 *
	 */
	
	//Test solve impossible Sudoku with 2 fives in in the first row
	@Test
	void testImpossibleSudokuWithFivesInRow() {
		solver.setCell(0, 0, 5);
		solver.setCell(0, 3, 5);
		assertFalse(solver.solve());
	}
	
	//Test solve impossible Sudoku with 2 fives in the first col but not in the same region
	@Test
	void testImpossibleSudokuWithFivesInTheFirstCol() {
		solver.setCell(0, 0, 5);
		solver.setCell(5, 0, 5);
		assertFalse(solver.solve());
	}
	
	//Test solve impossible Sudoku with 2 fives in the the same region
	@Test
	void testImpossibleSudokuWithFivesInTheSameRegion() {
		solver.setCell(0, 0, 5);
		solver.setCell(1, 2, 5);
		assertFalse(solver.solve());
	} 
	
	/*
	 *  
	 *  TESTCASE THREE
	 *
	 */
	 
	//Test impissobleSudoku and Clear Wrong 
	@Test
	void testImpossibleSudokuAndClear() {
		solver.setCell(0, 0, 1);
		solver.setCell(0, 1, 2);
		solver.setCell(0, 2, 3);
		solver.setCell(1, 0, 4);
		solver.setCell(1, 1, 5);
		solver.setCell(1, 2, 6);
		solver.setCell(2, 3, 7);
		assertFalse(solver.solve());
		
		solver.setCell(2, 3, 0);
		assertTrue(solver.solve());
		
	}  
	
	/*
	 *  
	 *  TESTCASE FOUR
	 *
	 */
	//Test clear and solve
	@Test
	void testClearAndSolve() {
		solver.setCell(0, 0, 5);
		solver.setCell(0, 7, 5);
		assertFalse(solver.solve());
		solver.clear();
		assertTrue(solver.solve());
	} 
	
	/*
	*
	* TESTCASE SIX
	*/
	//Test invalid input
	@Test
	void test6() {
		assertThrows(IllegalArgumentException.class, new Executable() {           
			@Override
            public void execute() throws Throwable {
            	solver.setCell(3, 3, -1);
            }
        });
	} 
	

	
	
	
	
	
	
	
	

}
