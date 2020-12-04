package sudoku;

import solver.SudokuSolver;

public class Sudoku implements SudokuSolver {

	private final int ROWS = 9;
	private final int COLS = 9;

	private int[][] board;

	public Sudoku() {
		this.board = new int[ROWS][COLS];
		clear();
	}

	@Override
	public void clear() {
		for (int i = 0; i < 9; i++) {
			for (int k = 0; k < 9; k++) {
				setCell(i, k, 0);
			}
		}
	}

	@Override
	public boolean solve() {

		return solve(0, 0);
	}

	/*
	 * private boolean solve(int row, int col) {
	 * 
	 * for(int i = row; i < ROWS; i++) {
	 * 
	 * for(int k = col; k < COLS; k++) {
	 * 
	 * int value = getCell(i,k);
	 * 
	 * if(value == 0) { //If the cell is empty for(int j = 1; j < 10; j++) {
	 * 
	 * if(isPossible(i,k,j)) { //Try to populate the cell setCell(i,k,j);
	 * 
	 * if(k == 8) { if(solve(row + 1, 0)) { return true; } } else { if(solve(row,
	 * col + 1)) { return true; } } } } setCell(i,k, 0); return false; } else {
	 * setCell(i,k,0); if(isPossible(i,k, value)) { setCell(i,k,0); if(k == 8) {
	 * return solve(row + 1, 0); } else { return solve(row, col + 1); } } else {
	 * return false; } }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * return true; }
	 */

	/*
	 * private boolean solve(int row, int col) {
	 * 
	 * if (row > 8) { return true; }
	 * 
	 * int value = getCell(row, col);
	 * 
	 * if (value == 0) { // empty value value = 1;
	 * 
	 * for (int i = value; i < 10; i++) {
	 * 
	 * if (isPossible(row, col, i)) { // Try to set a value according to the rules
	 * of sudoku setCell(row, col, i); if (col == 8) { if(solve(row + 1, 0)) { //If
	 * the recursive method return true we found a solution return true; } } else {
	 * if(solve(row, col + 1)) { //If the recursive method return true we found a
	 * solution return true; } } } }
	 * 
	 * return false; //No solution was found
	 * 
	 * } else { // empty cell first setCell(row, col, 0); // try if the insertion is
	 * possible if (isPossible(row, col, value)) { setCell(row, col, value); if (col
	 * == 8) return solve(row + 1, 0); else return solve(row, col + 1); } else {
	 * return false; }
	 * 
	 * }
	 * 
	 * }
	 */

	private boolean solve(int row, int col) {
		
		if(row > 8) {
			return true; //No more empty cells 
		}

		if (getCell(row, col) == 0) { 	// Cell is empty
			for (int value = 1; value < 10; value++) {
				// Try to set a value and see if it is possible
				// If possible set the value see if it can be solved with that value
				if (isPossible(row, col, value)) {
					setCell(row, col, value); 
					if (col == 8) {
						if(solve(row + 1, 0)) { //if we find a solution return true
							return true;		
						}
					} else {
						if(solve(row, col + 1)) {
							return true;
						}
					}
				} 
			}
			setCell(row, col, 0); //If we didnt find a value that was possible we set the cell to 0 and pop back
			return false;

		} else {
			int value = getCell(row, col);
			setCell(row, col,0);
			if(isPossible(row, col, value)) {
				setCell(row, col, value);				
				if(col == 8) {
					return solve(row + 1, 0); //If we find a solution with the given value return true
				} else {
					return solve(row, col + 1); //If we find a solution with the given value return true
				}
			} else {
				return false;
			}
		}
	
	
	}

	// Helper method
	private boolean isPossible(int row, int col, int value) {

		for (int i = 0; i < ROWS; i++) {
			if (getCell(i, col) == value) {
				return false;
			}
		}

		for (int i = 0; i < COLS; i++) {
			if (getCell(row, i) == value) {
				return false;
			}
		}

		int startRow = row - (row % 3);
		int startCol = col - (col % 3);

		for (int i = startRow; i < startRow + 3; i++) {

			for (int k = startCol; k < startCol + 3; k++) {

				if (getCell(i, k) == value) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void setCell(int row, int col, int val) throws IllegalArgumentException {
		if (val < 0 || val > 9) {
			throw new IllegalArgumentException("Value is less than 0 or greater than 9");
		}
		this.board[row][col] = val;
	}

	@Override
	public int getCell(int row, int col) throws IllegalArgumentException {
		return this.board[row][col];
	}

}
