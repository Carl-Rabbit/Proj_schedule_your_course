package datamanage;

import java.util.ArrayList;

/*
	One course of the subject
 */
public class CourseData {
	public SubjectData subject;

	public String crsName;
	public String teacher;
	public int quota;

	public ArrayList<ClassData> classList;

	public CourseData(SubjectData subject, String courseName) {
		this.subject = subject;
		this.crsName = courseName;
		classList = new ArrayList<>();
	}

	public String getAllTimeStr() {
		var sbd = new StringBuilder();
		for (ClassData c : classList) {
			if (c.week.contains("单")) {
				sbd.append("单周");
			} else if (c.week.contains("双")) {
				sbd.append("双周");
			}
			sbd.append(c.timeStr.split(" ", 2)[1]
					.replaceAll("星期", "周"));

			if (c != classList.get(classList.size()-1)) {
				sbd.append("; ");
			}
		}

		return sbd.toString();
	}

	@Override
	public String toString() {
		return String.format("Crs[%s, %s, %s, %d, N:%d]",
				subject.subName, crsName, teacher, quota, classList.size());
	}
}
