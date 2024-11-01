package server.repository;

import java.io.*;

/**
 * The FileIO class implements the Repository interface for managing chat history.
 * It provides methods to save and load chat messages to and from a file,
 * as well as to check the existence of the chat history file.
 */
public class FileIO implements Repository<String> {
    private static final String PATH_TO_CHAT_HISTORY_FILE = "src/server/repository/chat_history.txt";

    /**
     * Saves the provided text to the chat history file.
     *
     * @param text the chat message to save in the history file.
     */
    @Override
    public void save(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_CHAT_HISTORY_FILE, true))) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the entire content of the chat history file and returns it as a single String.
     * If an error occurs during reading, null is returned.
     *
     * @return the content of the chat history file as a String, or null if an error occurs.
     */
    @Override
    public String load() {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_CHAT_HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks for the existence of the chat history file.
     * If the file does not exist, it attempts to create a new one.
     * Logs messages to the console indicating the file's status.
     */
    @Override
    public void checkHistoryFile() {
        File file = new File(PATH_TO_CHAT_HISTORY_FILE);
        if (file.exists()) {
            System.out.println("The chat history file already exists.");
        } else {
            try {
                if (!file.createNewFile()) {
                    System.err.println("Failed to create chat history file.");
                }
                System.out.println("A new file has been created to store chat history." + System.lineSeparator() + "Path to the file: " + file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
