// 代码生成时间: 2025-09-19 00:12:24
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Quarkus application that organizes the folder structure.
 */
@QuarkusMain
public class FolderOrganizer implements QuarkusApplication {

    @Override
    public int run(String... args) {
        try {
            organizeFolders();
        } catch (IOException e) {
            System.err.println("An error occurred while organizing folders: " + e.getMessage());
            return 1;
        }
        return 0;
    }

    /**
     * Organizes the folder structure by moving files into appropriate subdirectories.
     * @throws IOException If an I/O error occurs.
     */
    public void organizeFolders() throws IOException {
        // Define the root directory to organize.
        String rootDirPath = "/path/to/your/directory";
        Path rootDir = Paths.get(rootDirPath);

        if (!Files.isDirectory(rootDir)) {
            throw new IOException("The specified path is not a directory: " + rootDirPath);
        }

        // List all files in the root directory.
        List<Path> files = listFilesRecursively(rootDir);

        // Create subdirectories and move files accordingly.
        for (Path file : files) {
            String fileName = file.getFileName().toString();
            Path subdir = rootDir.resolve("Type_"); // Assuming files are categorized by names starting with Type_
            if (fileName.startsWith("Type_")) {
                Path destination = subdir.resolve(fileName);
                Files.move(file, destination);
            }
        }
    }

    /**
     * Recursively lists all files in a directory.
     * @param dir The directory to list files from.
     * @return A list of paths to all files in the directory and its subdirectories.
     * @throws IOException If an I/O error occurs.
     */
    private List<Path> listFilesRecursively(Path dir) throws IOException {
        List<Path> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(dir)) {
            files = paths.filter(Files::isRegularFile).collect(Collectors.toList());
        }
        return files;
    }

    /**
     * Main method for testing the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        FolderOrganizer organizer = new FolderOrganizer();
        organizer.organizeFolders();
    }
}
