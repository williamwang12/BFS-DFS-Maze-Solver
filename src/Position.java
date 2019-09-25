public class Position {

	private Cell cell;
	private Position previous;

	Position(Cell cell, Position previous){
		this.cell = cell;
		this.previous = previous;
	}

	Cell getCell() { return cell; }

	Position getPrevious() { return previous; }
}
