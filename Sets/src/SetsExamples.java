import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetsExamples {

	public static void main(String[] args) {
		Set<String> var1 = new HashSet<String>();

		var1.add("sam");
		var1.add("sam");
		var1.add("samuels");
		var1.add("sammy");
		var1.add("saumya");

		// can add only one null value
		//var1.add(null);
		//var1.add(null);
		var1.remove("saumya");

		System.out.println(var1.contains("sam"));
		System.out.println(var1);
		
		Set<String> var2 = new TreeSet<String>();
		var2.add("sam");
		var2.add("mouse");
		var2.add("samuels");
		var2.add("cat");
		var2.add("dog");
		System.out.println("Tree Set");
		System.out.println(var2);
		
		Set<String> var3 = new HashSet<String>(var1);
		var3.retainAll(var2);
		System.out.println(var3);
		var3.removeAll(var2);
		System.out.println(var3);
		

	}

}
