package ecomicsmanager;

import java.io.File;

// Class: responsible for renaming comic file extensions (.zip <-> .cbz, .rar <-> .cbr).
// Separating this responsibility into its own class follows the Single Responsibility Principle (SRP):
// each class should do one thing and do it well.
// Only scans rootFolder directly — does not go into subfolders.
public class FileRenamer {

    // 'private final' = set once in the constructor, never changed after that.
    private final String rootFolder;

    // Constructor: called when creating a new FileRenamer object with 'new FileRenamer(path)'.
    // The parameter 'rootFolder' shadows the field name, so 'this.rootFolder' refers to the field,
    // while 'rootFolder' alone refers to the parameter.
    public FileRenamer(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    // Each public method delegates to the private helper with the appropriate extension pair.
    // This avoids repeating the same logic 4 times — DRY principle (Don't Repeat Yourself).
    public void renameZipToCbz() {
        renameExtension(".zip", ".cbz");
    }

    public void renameCbzToZip() {
        renameExtension(".cbz", ".zip");
    }

    public void renameRarToCbr() {
        renameExtension(".rar", ".cbr");
    }

    public void renameCbrToRar() {
        renameExtension(".cbr", ".rar");
    }

    // Private helper method: contains the actual renaming logic reused by all 4 public methods.
    // 'fromExt' = the extension to look for, 'toExt' = the extension to rename to.
    private void renameExtension(String fromExt, String toExt) {
        File root = new File(rootFolder);

        // listFiles() with a filter: only returns files (not folders) matching the source extension.
        // The lambda 'f -> ...' is an anonymous function passed as a FileFilter.
        // String.endsWith() checks if the filename ends with the given extension (case-insensitive via toLowerCase()).
        File[] files = root.listFiles(f -> f.isFile() && f.getName().toLowerCase().endsWith(fromExt));

        if (files == null || files.length == 0) {
            System.out.println("No " + fromExt + " files found in: " + rootFolder);
            return; // Exits the method early — nothing to process.
        }

        // Enhanced for loop: iterates over each matched file.
        for (File file : files) {
            String nameWithoutExt = file.getName().substring(0, file.getName().length() - fromExt.length());
            // String.substring(0, n) returns the first n characters of the string.
            // Subtracting the extension length strips it from the end, leaving just the base name.

            File renamed = new File(rootFolder, nameWithoutExt + toExt);
            // new File(parent, child) builds a full path from a folder and a filename.

            // Check if a file with the target name already exists — skip and warn if so.
            if (renamed.exists()) {
                System.out.println("  WARNING: Skipping " + file.getName() + " — target already exists: " + renamed.getName());
                continue; // 'continue' skips the rest of this loop iteration and moves to the next file.
            }

            // File.renameTo() renames the file on disk. Returns true if successful, false otherwise.
            if (file.renameTo(renamed)) {
                System.out.println("  Renamed: " + file.getName() + " → " + renamed.getName());
            } else {
                System.out.println("  ERROR: Could not rename: " + file.getName());
            }
        }
    }
}
