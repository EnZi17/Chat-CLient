import myUtil.Util;

public class App {
	public static void main(String[] args) {
		System.out.println(Util.postApi("http://localhost:5000/api/users/register", "{ \"username\": \"e\",\"email\": \"e\",\"password\": \"e\"}"));
	}
}
