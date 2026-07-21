package ecomicsmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

// Class: responsible for ZIP-related operations (compressing and extracting .zip files).
// Java has built-in support for ZIP via java.util.zip — no external library needed.
public class ZipHandler {

    // 'private final' = set once in the constructor, never changed after that.
    private final String rootFolder;

    // Constructor: sets the folder this handler will work with.
    public ZipHandler(String rootFolder) {
        this.rootFolder = rootFolder; // 'this.rootFolder' = the field; 'rootFolder' = the parameter.
    }

    // Scans rootFolder for all subfolders and zips each one into a .zip file saved in rootFolder.
    // The .zip file gets the same name as the subfolder (e.g. "mycomic/" → "mycomic.zip").
    public void zipFolder() {
        File root = new File(rootFolder);

        // listFiles(File::isDirectory) returns only subfolders, ignoring files.
        File[] subFolders = root.listFiles(File::isDirectory);

        if (subFolders == null || subFolders.length == 0) {
            System.out.println("No subfolders found in: " + rootFolder);
            return; // Exits the method early — nothing to process.
        }

        for (File folder : subFolders) {
            File zipFile = new File(rootFolder, folder.getName() + ".zip");
            // new File(parent, child) builds a full path from a folder and a filename.

            if (zipFile.exists()) {
                System.out.println("WARNING: Skipping " + folder.getName() + " — zip already exists: " + zipFile.getName());
                continue; // 'continue' skips to the next iteration of the loop.
            }

            System.out.println("Zipping: " + folder.getName() + " → " + zipFile.getName());
            zipSingleFolder(folder, zipFile);
        }
    }

    // Private helper: compresses a single folder into a .zip file.
    private void zipSingleFolder(File folder, File zipFile) {
        // try-with-resources: automatically closes ZipOutputStream when the block ends,
        // even if an exception occurs — prevents resource leaks.
        // FileOutputStream writes raw bytes to a file on disk.
        // ZipOutputStream wraps it to write in ZIP format.
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            File[] files = folder.listFiles();
            // listFiles() can return null if the folder is not readable (e.g. permission denied).
            // Always null-check before iterating to avoid NullPointerException.
            if (files == null) {
                System.out.println("  ERROR: Could not read folder: " + folder.getName());
                return;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("  WARNING: Skipping nested subfolder: " + file.getName());
                    continue;
                }

                // ZipEntry represents a single file inside the ZIP archive.
                // We use just the filename (not the full path) so the ZIP stays clean.
                zos.putNextEntry(new ZipEntry(file.getName()));

                // FileInputStream reads raw bytes from a file on disk.
                // try-with-resources ensures it is closed after reading.
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[1024]; // buffer: temporary storage for chunks of bytes while reading/writing.
                    int length;
                    // Read chunks of bytes until the end of the file (read() returns -1 when done).
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length); // write only the bytes actually read, not the whole buffer.
                    }
                }

                zos.closeEntry(); // Signals that we are done writing this entry into the ZIP.
                System.out.println("  Added: " + file.getName());
            }
        } catch (IOException e) {
            // IOException is a checked exception — Java forces you to handle it when doing file I/O.
            // e.getMessage() returns a human-readable description of what went wrong.
            System.out.println("  ERROR: Failed to zip " + folder.getName() + ": " + e.getMessage());
        }
    }

    // Scans rootFolder for all .zip files and extracts each one into a subfolder of the same name.
    // e.g. "mycomic.zip" → "mycomic/" folder inside rootFolder.
    public void unzipFile() {
        File root = new File(rootFolder);

        // listFiles() with a lambda filter: only returns .zip files directly in rootFolder.
        File[] zipFiles = root.listFiles(f -> f.isFile() && f.getName().toLowerCase().endsWith(".zip"));

        if (zipFiles == null || zipFiles.length == 0) {
            System.out.println("No .zip files found in: " + rootFolder);
            return;
        }

        for (File zipFile : zipFiles) {
            // Strip the ".zip" extension to get the target folder name.
            String folderName = zipFile.getName().substring(0, zipFile.getName().length() - 4);
            File targetFolder = new File(rootFolder, folderName);

            if (targetFolder.exists()) {
                System.out.println("WARNING: Skipping " + zipFile.getName() + " — folder already exists: " + folderName);
                continue;
            }

            // mkdir() creates the target folder on disk. Returns false if it could not be created.
            if (!targetFolder.mkdir()) {
                System.out.println("  ERROR: Could not create folder: " + folderName);
                continue;
            }

            System.out.println("Unzipping: " + zipFile.getName() + " → " + folderName + "/");
            unzipSingleFile(zipFile, targetFolder);
        }
    }

    // Private helper: extracts all entries from a single .zip file into the target folder.
    // Strips the top-level folder prefix if present (e.g. "mycomic/extras/P0001.jpg" → "extras/P0001.jpg"),
    // but preserves any nested subfolder structure underneath it.
    private void unzipSingleFile(File zipFile, File targetFolder) {
        // ZipInputStream reads a ZIP archive entry by entry.
        // FileInputStream provides the raw bytes of the .zip file to ZipInputStream.
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;

            // getNextEntry() moves to the next file inside the ZIP. Returns null when there are no more entries.
            while ((entry = zis.getNextEntry()) != null) {

                // Skip directory entries — we only extract files.
                // Some ZIPs include explicit folder entries (e.g. "mycomic/") that would crash FileOutputStream.
                if (entry.isDirectory()) {
                    zis.closeEntry();
                    continue;
                }

                // entry.getName() may include a top-level folder prefix (e.g. "mycomic/extras/P0001.jpg").
                // indexOf('/') finds the first '/' — if present, substring() strips everything up to and including it,
                // leaving "extras/P0001.jpg". If there is no '/', the name is used as-is.
                String entryName = entry.getName();
                int firstSlash = entryName.indexOf('/');
                String relativePath = (firstSlash != -1) ? entryName.substring(firstSlash + 1) : entryName;

                // Skip empty paths — this happens when the entry IS the top-level folder itself (e.g. "mycomic/").
                if (relativePath.isEmpty()) {
                    zis.closeEntry();
                    continue;
                }

                File outFile = new File(targetFolder, relativePath);

                // getParentFile() returns the folder that should contain this file.
                // mkdirs() creates it and any missing intermediate folders (e.g. "extras/").
                // This preserves nested subfolder structure from inside the ZIP.
                File parentDir = outFile.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }

                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }

                zis.closeEntry(); // Signals that we are done reading this entry from the ZIP.
                System.out.println("  Extracted: " + relativePath);
            }
        } catch (IOException e) {
            System.out.println("  ERROR: Failed to unzip " + zipFile.getName() + ": " + e.getMessage());
        }
    }
}
