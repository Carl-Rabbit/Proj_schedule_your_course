package Helper;

public class Pair<T> {
	public T first, second;

	public Pair(T f, T s) {
		first = f;
		second = s;
	}

	@Override
	public String toString() {
		return "(" + first + "," + second + ")";
	}

	public boolean isSame(T f, T s) {
		return first.equals(f) && second.equals(s);
	}
}
