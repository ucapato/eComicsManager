package ecomicsmanager;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

// Class: responsible for RAR extraction only — creating RAR files is not supported.
// Uses the 'junrar' open-source library for pure Java RAR extraction.
// No external tools (like WinRAR) need to be installed on the machine.
// junrar is declared as a dependency in pom.xml and downloaded automatically by Maven.
public class RarHandler {

    // 'private final' = set once in the constructor, never changed after that.
    private final String rootFolder;

    // Constructor: initializes the RarHandler with the folder it will operate on.
    public RarHandler(String rootFolder) {
        this.rootFolder = rootFolder; // 'this' distinguishes the field from the constructor parameter.
    }

    // Scans rootFolder for all .rar files and extracts each one into a subfolder of the same name.
    // e.g. "mycomic.rar" → "mycomic/" folder inside rootFolder.
    // Skips and warns if the target folder already exists.
    public void unrarFile() {
        File root = new File(rootFolder);

        // listFiles() with a lambda filter: only returns .rar files directly in rootFolder.
        File[] rarFiles = root.listFiles(f -> f.isFile() && f.getName().toLowerCase().endsWith(".rar"));

        if (rarFiles == null || rarFiles.length == 0) {
            System.out.println("No .rar files found in: " + rootFolder);
            return; // Exits the method early — nothing to process.
        }

        // Enhanced for loop: iterates over each matched .rar file.
        for (File rarFile : rarFiles) {
            // Strip the ".rar" extension (4 characters) to get the target folder name.
            String folderName = rarFile.getName().substring(0, rarFile.getName().length() - 4);
            File targetFolder = new File(rootFolder, folderName);

            if (targetFolder.exists()) {
                System.out.println("WARNING: Skipping " + rarFile.getName() + " — folder already exists: " + folderName);
                continue; // 'continue' skips to the next iteration of the loop.
            }

            // mkdir() creates the target folder on disk. Returns false if it could not be created.
            if (!targetFolder.mkdir()) {
                System.out.println("  ERROR: Could not create folder: " + folderName);
                continue;
            }

            System.out.println("Unraring: " + rarFile.getName() + " → " + folderName + "/");
            unrarSingleFile(rarFile, targetFolder);
        }
    }

    // Private helper: extracts a single .rar file into the target folder using junrar.
    private void unrarSingleFile(File rarFile, File targetFolder) {
        // Archive is junrar's main class for opening and reading a .rar file.
        // try-with-resources ensures the Archive is closed after extraction, even if an error occurs.
        try (Archive archive = new Archive(rarFile)) {

            // FileHeader represents a single entry (file) inside the RAR archive.
            // nextFileHeader() moves to the next entry — returns null when there are no more.
            FileHeader fileHeader;
            while ((fileHeader = archive.nextFileHeader()) != null) {

                // getFileName() returns the name of the file inside the archive.
                File outFile = new File(targetFolder, fileHeader.getFileName());

                // FileOutputStream writes the extracted bytes to a file on disk.
                // try-with-resources ensures it is closed after writing.
                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    // archive.extractFile() does the actual extraction of this entry into the output stream.
                    archive.extractFile(fileHeader, fos);
                    System.out.println("  Extracted: " + fileHeader.getFileName());
                }
            }
        } catch (RarException e) {
            // RarException: thrown by junrar when the .rar file is corrupt or unsupported.
            System.out.println("  ERROR: Failed to read RAR file: " + rarFile.getName() + " — " + e.getMessage());
        } catch (IOException e) {
            // IOException: thrown when a file cannot be read from or written to disk.
            System.out.println("  ERROR: File I/O error during extraction: " + e.getMessage());
        }
    }
}
