package view;

public interface TableChangeListener {

	public void onTableChanged();
	
	public void onCellChanged(int row, int col);
}
