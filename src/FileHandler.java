import java.io.*;

public class FileHandler {

    private static final String FILE_PATH = "directory.txt";
    public static void writeWord(String word) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(word);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readWord() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void replaceWord(String newWord) {
        writeWord(newWord);
    }

}
