package datamanage;

import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MyFileManager extends FileLoader {

	public static void main(String[] args) {
		// inner test
		MyFileManager fm = new MyFileManager();
		fm.open();
		fm.loadFile();
	}


	private static HashMap<String, Integer> DAY = new HashMap<>(){{
		this.put("星期一", 1);
		this.put("星期二", 2);
		this.put("星期三", 3);
		this.put("星期四", 4);
		this.put("星期五", 5);
	}};

	private static HashMap<String, Integer> TIME = new HashMap<>(){{
		this.put("1-2节", 1);
		this.put("3-4节", 2);
		this.put("5-6节", 3);
		this.put("7-8节", 4);
		this.put("9-10节", 5);
		this.put("11节", 6);
	}};


	public ArrayList<SubjectData> subjectList;

	private HashSet<String> ids = new HashSet<>();

	// buffer for course that has more than one class
	private CourseData buffer;

	public MyFileManager() {
		subjectList = new ArrayList<>();
	}

	/* Make it satisfy to your own xlsx file */
	@Override
	public int loadFile() {
		if (super.file == null) { return -1; }
		if (super.file.getNumberOfSheets() == 0) { return -2; }

		var sheet = file.getSheetAt(0);

		for (int i = 1; i <= sheet.getLastRowNum(); i ++) {
			var row = sheet.getRow(i);

			if (row == null) continue;

			if (row.getCell(0) != null) {
				// new course
				analyzeAll(row);
			} else {
				// another class for last course
				analyzeExtraClass(row);
			}
		}

		return 0;
	}

	private void analyzeAll(XSSFRow row) {

		// Data for subject and course
		String id = getValue(row.getCell(0));
		String nameStr = getValue(row.getCell(1));
		int point = (int) Double.parseDouble(getValue(row.getCell(2)));
		String teacher = getValue(row.getCell(3));

		// Data for class
		String timeStr = getValue(row.getCell(4));
		int quota = (int) Double.parseDouble(getValue(row.getCell(5)));
		String place = getValue(row.getCell(6));

		// split name
		String[] tmp = nameStr.split("\\[");
		String subjectName = tmp[0];
		String courseName = tmp[1].substring(0, tmp[1].length()-1);

		SubjectData parentSub;
		CourseData newCourse;
		ClassData newClass;

		if (ids.contains(id)) {
			// new course of last subject

			parentSub = subjectList.get(subjectList.size() - 1);
			newCourse = new CourseData(parentSub, courseName);
			newClass = new ClassData(newCourse);

		} else {
			// the course of a new subject

			ids.add(id);

			parentSub = new SubjectData(id, subjectName, point);
			newCourse = new CourseData(parentSub, courseName);
			newClass = new ClassData(newCourse);

			subjectList.add(parentSub);
		}

		// init relations
		parentSub.courseList.add(newCourse);
		newCourse.classList.add(newClass);

		// set course properties
		newCourse.teacher = teacher;
		newCourse.quota = quota;

		// set class properties
		newClass.timeStr = timeStr;
		newClass.place = place;
		tmp = timeStr.split(" ");
		newClass.setTime(tmp[0], DAY.get(tmp[1]), TIME.get(tmp[2]));

		// set buffer for extra class
		buffer = newCourse;
	}

	private void analyzeExtraClass(XSSFRow row) {

		// Data for class
		String timeStr = getValue(row.getCell(4));
		String place = getValue(row.getCell(6));

		// init relations
		ClassData newClass = new ClassData(buffer);
		buffer.classList.add(newClass);

		// set class properties
		newClass.timeStr = timeStr;
		newClass.place = place;
		String[] tmp = timeStr.split(" ");
		newClass.setTime(tmp[0], DAY.get(tmp[1]), TIME.get(tmp[2]));
	}
}
