package ecomicsmanager;

// Class: responsible for renaming comic file extensions (.zip <-> .cbz, .rar <-> .cbr).
// Separating this responsibility into its own class follows the Single Responsibility Principle (SRP):
// each class should do one thing and do it well.
public class FileRenamer {

    // Instance field: stores the root folder path for this object.
    // 'private' = only accessible within this class (encapsulation — hiding internal data).
    // NOTE: since rootFolder is set once in the constructor and never changed, it should be 'final'.
    // 'private final String rootFolder' would make that intent explicit.
    private String rootFolder;

    // Constructor: called when creating a new FileRenamer object with 'new FileRenamer(path)'.
    // The parameter 'rootFolder' shadows the field name, so 'this.rootFolder' refers to the field,
    // while 'rootFolder' alone refers to the parameter.
    public FileRenamer(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    // Public method: accessible from other classes (e.g. ComicBookManager calls this).
    // 'void' means it does not return any value.
    // NOTE: this is a stub — it only prints a message. Real implementation will rename files on disk.
    public void renameZipToCbz() {
        System.out.println("Renaming .zip to .cbz in " + rootFolder);
    }

    // String concatenation: the '+' operator joins strings together.
    // 'rootFolder' here refers to the instance field set in the constructor.
    public void renameCbzToZip() {
        System.out.println("Renaming .cbz to .zip in " + rootFolder);
    }

    public void renameRarToCbr() {
        System.out.println("Renaming .rar to .cbr in " + rootFolder);
    }

    public void renameCbrToRar() {
        System.out.println("Renaming .cbr to .rar in " + rootFolder);
    }
}
