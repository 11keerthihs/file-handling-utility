import java.io.*;
import java.util.*;

/**
 * FileHandlingUtility
 * A comprehensive utility class to demonstrate core file operations in Java.
 * Includes Create, Read, Update (Append/Modify), and Delete operations.
 */
public class FileHandlingUtility {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        System.out.println("=== File Handling Utility ===");

        while (!exit) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createFile();
                    break;
                case "2":
                    displayFileContents();
                    break;
                case "3":
                    appendToFile();
                    break;
                case "4":
                    editFileContent();
                    break;
                case "5":
                    deleteFile();
                    break;
                case "6":
                    exit = true;
                    System.out.println("Exiting Utility. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * Prints the main menu options to the console.
     */
    private static void printMenu() {
        System.out.println("\nSelect an operation:");
        System.out.println("1. Create a new file");
        System.out.println("2. Display file contents");
        System.out.println("3. Append new content");
        System.out.println("4. Edit existing content (Replace words/lines)");
        System.out.println("5. Delete a file");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
    }

    /**
     * Creates a new file if it doesn't already exist.
     */
    private static void createFile() {
        System.out.print("Enter file name to create (e.g., test.txt): ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("File created successfully: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    /**
     * Reads and displays the contents of a specified file.
     */
    private static void displayFileContents() {
        System.out.print("Enter file name to read: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Error: File not found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("\n--- File Contents: " + fileName + " ---");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("-----------------------------------");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Appends user-provided text to the end of a file.
     */
    private static void appendToFile() {
        System.out.print("Enter file name to append to: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Error: File not found.");
            return;
        }

        System.out.println("Enter content to append (Type 'END' on a new line to finish):");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String line;
            while (!(line = scanner.nextLine()).equalsIgnoreCase("END")) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Content appended successfully.");
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    /**
     * Edits existing content by replacing a specific word or line.
     * This method reads the entire file into memory, performs replacement, and writes back.
     */
    private static void editFileContent() {
        System.out.print("Enter file name to edit: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Error: File not found.");
            return;
        }

        System.out.print("Enter the word/line to find: ");
        String find = scanner.nextLine();
        System.out.print("Enter the replacement word/line: ");
        String replace = scanner.nextLine();

        List<String> lines = new ArrayList<>();
        boolean modified = false;

        // Read file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(find)) {
                    line = line.replace(find, replace);
                    modified = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file for edit: " + e.getMessage());
            return;
        }

        if (!modified) {
            System.out.println("No occurrences of '" + find + "' found.");
            return;
        }

        // Write back
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("File updated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing back to file: " + e.getMessage());
        }
    }

    /**
     * Deletes the specified file.
     */
    private static void deleteFile() {
        System.out.print("Enter file name to delete: ");
        String fileName = scanner.nextLine();
        File file = new File(fileName);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("Error: File not found.");
        }
    }
}
