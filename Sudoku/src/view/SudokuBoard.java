package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import solver.Sudoku;
import solver.SudokuSolver;

public class SudokuBoard implements TableChangeListener {
	private JFrame frame;
	private JPanel board;
	private JPanel buttonPanel;
	private Container content;
	private Color subBoardColor;

	private final int hgap = 5;
	private final int vgap = 5;
	private final int SIZE = 9;

	private int width = 500;
	private int height = 500;

	private SudokuSolver sudoku;
	private JTextField[][] textCells;

	public SudokuBoard() {
		sudoku = new Sudoku(this);
		subBoardColor = new Color(255, 116, 0);
		textCells = new JTextField[SIZE][SIZE];

		// Create a new thread for GUI to run on
		SwingUtilities.invokeLater(() -> createWindow());

	}

	private void createWindow() {
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub

				for (int row = 0; row < SIZE; row++)
					for (int col = 0; col < SIZE; col++) {
						Font bigFont = textCells[row][col].getFont().deriveFont(Font.PLAIN,
								(frame.getWidth() + frame.getHeight()) / 30);
						textCells[row][col].setFont(bigFont);
					}

				frame.getContentPane().revalidate();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

		});

		content = frame.getContentPane();

		board = new JPanel();
		board.setLayout(new GridLayout(9, 9, hgap, vgap));
		board.setBorder(new EmptyBorder(5, 5, 5, 5));
		addCells();

		buttonPanel = new JPanel();
		createButtons();

		content.add(board, BorderLayout.CENTER);
		content.add(buttonPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
		frame.pack();
	}

	private void addCells() {

		for (int row = 0; row < 9; row++)
			for (int col = 0; col < 9; col++) {
				JTextField textField = new JTextField();
				textField.setHorizontalAlignment(JTextField.CENTER);
				textField.setBorder(new EmptyBorder(0, 0, 0, 0));
				if (((col < 3 || col > 5) && (row < 3 || row > 5)))
					textField.setBackground(subBoardColor);

				if ((col > 2 && col < 6) && (row > 2 && row < 6))
					textField.setBackground(subBoardColor);

				Font bigFont = textField.getFont().deriveFont(Font.PLAIN, (width + height) / 30);
				textField.setFont(bigFont);
				textField.setDocument(new JTextFieldLimit(1));
				textCells[row][col] = textField;
				board.add(textField);
			}
	}

	private void createButtons() {
		JButton solveButton = new JButton("Solve");
		JButton clearButton = new JButton("Clear");

		solveButton.addActionListener((e) -> {
			for(int row = 0; row < SIZE; row++) {
				for(int col = 0; col < SIZE; col++) {
					String text = textCells[row][col].getText();
					int value = 0;
					if(!text.isEmpty()) {
						value = Integer.parseInt(text);
					}
					sudoku.setCell(row, col, value);
				}
			}
				sudoku.solve();		
		});

		clearButton.addActionListener((e) -> {
			sudoku.clear();

		});

		buttonPanel.add(solveButton);
		buttonPanel.add(clearButton);
	}

	@SuppressWarnings("serial")
	public class JTextFieldLimit extends PlainDocument {
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit && isStringInt(str)) {
				super.insertString(offset, str, attr);
			}

		}	

	}
	
	private boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	@Override
	public void onTableChanged() {
		for(int row = 0; row < SIZE; row++)
			for(int col = 0; col < SIZE; col++) {
				String valueStringRepresentation = "";
				Integer value = sudoku.getCell(row, col);
				if(value != 0) {
					valueStringRepresentation = value.toString();
				}
				textCells[row][col].setText(valueStringRepresentation);
			}
	}

	@Override
	public void onCellChanged(int row, int col) {
		Integer value = sudoku.getCell(row, col);
		textCells[row][col].setText(value.toString());
		textCells[row][col].repaint();
	}
	
	

}
