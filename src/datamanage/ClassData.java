package datamanage;

/*
	One specific class for the course, at specific time
 */
public class ClassData {
	public CourseData course;

	public String week;
	public int day;
	public int time;
	public String timeStr;

	public String place;

	public ClassData(CourseData course) {
		this.course = course;
	}

	public void setTime(String week, int day, int time) {
		this.week = week;
		this.day = day;
		this.time = time;
	}

	@Override
	public String toString() {
		return String.format("Cls[%s, %s, %d, %d]",
				course.crsName, timeStr, day, time);
	}
}
