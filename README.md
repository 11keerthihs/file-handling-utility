# File Handling Utility (Java)

A robust Java-based command-line utility for performing core file operations.

## Features
- **Create**: Create new text files.
- **Read**: Display contents of existing files.
- **Append**: Add new content to the end of a file.
- **Edit**: Search and replace specific text within a file.
- **Delete**: Remove files from the system.
- **Exception Handling**: Robust error management for IO and file-not-found scenarios.

## How It Works
The program uses a menu-driven interface. Users select an operation by entering a number (1-6). The utility uses standard Java IO classes:
- `java.io.File` for file metadata and existence checks.
- `java.io.BufferedReader` and `java.io.FileReader` for efficient reading.
- `java.io.BufferedWriter` and `java.io.FileWriter` for efficient writing and appending.

## How to Compile and Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher installed.

### Compilation
Open your terminal or command prompt and navigate to the project directory:
```bash
javac FileHandlingUtility.java
```

### Execution
Run the compiled class:
```bash
java FileHandlingUtility
```

## Sample Input/Output

### Creating a File
**Input:**
```
Select an operation:
1. Create a new file
...
Enter choice: 1
Enter file name to create: notes.txt
```
**Output:**
```
File created successfully: notes.txt
```

### Appending Content
**Input:**
```
Enter choice: 3
Enter file name to append to: notes.txt
Enter content to append:
Hello World
This is a test.
END
```
**Output:**
```
Content appended successfully.
```

### Displaying Content
**Input:**
```
Enter choice: 2
Enter file name to read: notes.txt
```
**Output:**
```
--- File Contents: notes.txt ---
Hello World
This is a test.
-----------------------------------
```
