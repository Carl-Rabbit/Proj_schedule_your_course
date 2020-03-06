package helper;

import datamanage.ClassData;

public class Location {
	public int row, col;
	public ClassData refer;

	public Location(int row, int col, ClassData refer) {
		this.row = row;
		this.col = col;
		this.refer = refer;
	}

	@Override
	public String toString() {
		return "(" + row + "," + col + "," + refer.course.crsName + ")";
	}

	public boolean isSame(int row, int col, ClassData refer) {
		return row == this.row && col == this.col && refer == this.refer;
	}
}
