# eComicsManager

A personal Java project built as a **"learn by doing"** exercise to get familiar with the Java language.

## About
eComicsManager is a command-line tool to help manage local comic book files. It handles common tasks like renaming pages, compressing/extracting archives, and converting between comic-specific formats (`.cbz`, `.cbr`) and their standard equivalents (`.zip`, `.rar`).

> ⚠️ The root folder is currently hardcoded to `C:\gibis\` — the app will not run on other machines without changing this value in `ComicBookManager.java`.

## Features
- Rename comic pages into the correct sequential order
- ZIP / Unzip folders
- Unrar files (RAR creation is not supported)
- Convert `.zip` ↔ `.cbz`
- Convert `.rar` ↔ `.cbr`
- Convert `.cbr` to `.cbz` (batch, all steps automated)

## Project Status
🚧 Work in progress — features are being implemented incrementally as Java concepts are learned.

## Requirements
- Java 17+
- Maven
- [junrar](https://github.com/junrar/junrar) 7.5.5 (declared in `pom.xml`, downloaded automatically by Maven)

## How to Run
```bash
mvn compile
mvn exec:java -Dexec.mainClass="ecomicsmanager.Main"
```

## Java Concepts Practiced
- Classes and objects
- Constructors and instance fields
- Static methods and constants
- Switch expressions (Java 14+)
- File I/O
- try-with-resources
- Streams and lambdas
- Input validation with `Integer.parseInt()` and `NumberFormatException`
