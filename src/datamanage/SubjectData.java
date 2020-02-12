package datamanage;

import java.util.ArrayList;

/*
	A abstract subject, with multi course to choose
 */
public class SubjectData {

	public String id;
	public String subName;
	public int point;

	public ArrayList<CourseData> courseList;

	public CourseData selectedCourse;

	public SubjectData(String id, String subName, int point) {
		this.id = id;
		this.subName = subName;
		this.point = point;
		courseList = new ArrayList<>();
	}

	@Override
	public String toString() {
		return String.format("Sub[%s, %s, %d, N:%d]",
				id, subName, point, courseList.size());
	}
}
