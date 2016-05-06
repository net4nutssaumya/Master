import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriting {

	public static void main(String[] args) {
		File file = new File("Test.txt");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write("This is first Line");
			bw.newLine();
			bw.write("This is second Line");
		} catch (IOException e) {
			System.out.println("File not updated " + file.toString());
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				System.out.println("File Not Closed");
			}
		}
	}
}
