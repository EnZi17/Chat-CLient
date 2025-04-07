import java.awt.EventQueue;

import javax.swing.UIManager;

import myUtil.Util;
import view.Index;

public class App {
	public static void main(String[] args) {
		String tmp ="{"
                + "\"participants\": [\"a\", \"b\"],"
                + "\"isGroup\": false"
                + "}";
               System.out.println(myUtil.Util.postApi("http://localhost:5000/conversations", tmp));
               
           
	}
}
