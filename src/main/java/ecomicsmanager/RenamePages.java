package ecomicsmanager;

import java.io.File;
import java.util.Arrays;
import java.util.List;

// Class: responsible for renaming comic book page files into sequential order.
// It processes every subfolder under rootFolder automatically — no user interaction needed.
public class RenamePages {

    // Supported image extensions — any file matching these will be renamed.
    // 'private static final' = constant that belongs to the class, not any instance.
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".webp", ".gif", ".bmp");

    // 'private final' = set once in the constructor, never changed after that.
    private final String rootFolder;

    // Constructor: receives the root folder path and stores it.
    public RenamePages(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public void execute() {
        // File.listFiles() returns an array of File objects inside the given path.
        // Each File object can represent either a file or a directory.
        File root = new File(rootFolder);
        File[] subFolders = root.listFiles(File::isDirectory); // Method reference: shorthand for f -> f.isDirectory()

        if (subFolders == null || subFolders.length == 0) {
            System.out.println("No subfolders found in: " + rootFolder);
            return; // Exits the method early — nothing to process.
        }

        // Enhanced for loop: iterates over each element in the array.
        for (File folder : subFolders) {
            System.out.println("\nProcessing folder: " + folder.getName());
            processFolder(folder);
        }
    }

    // Private helper method: handles the renaming logic for a single folder.
    // Breaking logic into smaller methods makes code easier to read and maintain.
    private void processFolder(File folder) {
        File[] contents = folder.listFiles();

        if (contents == null || contents.length == 0) {
            System.out.println("  WARNING: No files found in: " + folder.getName());
            return;
        }

        // Arrays.stream() + filter() — functional style to separate files by type.
        // Collecting results into sorted lists for predictable ordering.
        List<File> imageFiles = Arrays.stream(contents)
                .filter(f -> f.isFile() && isImage(f))   // keep only image files
                .sorted()                                  // sort alphabetically by name
                .toList();                                 // collect into a List (Java 16+)

        List<File> nonImageFiles = Arrays.stream(contents)
                .filter(f -> f.isFile() && !isImage(f))  // keep only non-image files
                .toList();

        List<File> nestedFolders = Arrays.stream(contents)
                .filter(File::isDirectory)                // keep only subfolders
                .toList();

        // Warn about nested subfolders — we skip them intentionally.
        for (File nested : nestedFolders) {
            System.out.println("  WARNING: Skipping nested subfolder: " + nested.getName());
        }

        // Warn about non-image files — they are left untouched.
        for (File nonImage : nonImageFiles) {
            System.out.println("  WARNING: Non-image file found and skipped: " + nonImage.getName());
        }

        if (imageFiles.isEmpty()) {
            System.out.println("  WARNING: No image files found in: " + folder.getName());
            return;
        }

        // Rename each image file sequentially: P0001.jpg, P0002.jpg, etc.
        for (int i = 0; i < imageFiles.size(); i++) {
            File original = imageFiles.get(i);
            String extension = getExtension(original); // preserve the original file extension

            // String.format() formats a string with placeholders.
            // "%04d" = integer padded with leading zeros to 4 digits (e.g. 1 → "0001").
            String newName = String.format("P%04d%s", i + 1, extension); // i+1 so numbering starts at 1
            File renamed = new File(folder, newName); // new File(parent, filename) builds the full path

            // File.renameTo() renames/moves a file. Returns true if successful, false otherwise.
            if (original.renameTo(renamed)) {
                System.out.println("  Renamed: " + original.getName() + " → " + newName);
            } else {
                System.out.println("  ERROR: Could not rename: " + original.getName());
            }
        }
    }

    // Helper method: checks if a file is an image based on its extension.
    private boolean isImage(File file) {
        return IMAGE_EXTENSIONS.contains(getExtension(file).toLowerCase());
    }

    // Helper method: extracts the extension from a file (e.g. "page1.jpg" → ".jpg").
    // String.lastIndexOf() finds the last occurrence of a character — handles names like "my.comic.page.jpg".
    private String getExtension(File file) {
        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');
        return (dotIndex == -1) ? "" : name.substring(dotIndex); // substring from dot to end
    }
}
