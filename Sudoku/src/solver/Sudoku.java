package solver;



import view.TableChangeListener;

public class Sudoku implements SudokuSolver {

	private final int ROWS = 9;
	private final int COLS = 9;

	private int[][] board;
	TableChangeListener listener;

	public Sudoku(TableChangeListener listener) {
		this.board = new int[ROWS][COLS];
		this.listener = listener;

	}

	@Override
	public void clear() {
		for (int i = 0; i < 9; i++) {
			for (int k = 0; k < 9; k++) {
				setCell(i, k, 0);
			}
		}
		fireTableChanged();
	}

	@Override
	public boolean solve() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (!isPossible(row, col, board[row][col])) { // Checks if it breaks the soduko rules
					return false;
				}
			}
		}
		
			return solve(0, 0);
	}

	private boolean solve(int row, int column) {
		// base case no more empty cells
		if (row > 8) {
			fireTableChanged();
			return true;
		}

		int nextRow = column == 8 ? (row + 1) : row;
		int nextColumn = column == 8 ? 0 : (column + 1);

		if (getCell(row, column) != 0) {
			return solve(nextRow, nextColumn);
		}
		// try all possible solutions
		for (int value = 1; value <= 9; value++) {
			if (isPossible(row, column, value)) {
				setCell(row, column, value);

				if (solve(nextRow, nextColumn)) {
					return true; // If we find a solution with the given value return true
				}
			}
			setCell(row, column, 0);

		}

		return false;
	}

	private void fireTableChanged() {
		listener.onTableChanged();
	}

	private void fireCellChanged(int row, int col) {
		listener.onCellChanged(row, col);
	}

	// Helper method
	private boolean isPossible(int row, int col, int value) {

		if (value == 0) {
			setCell(row, col, value);
			return true;
		}

		setCell(row, col, 0);

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

		setCell(row, col, value);
		return true;

	}

	public void print() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				System.out.print(board[row][col] + " ");
				if (col == 8) {
					System.out.println();
				}
			}
		}
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
