# eComicsManager

A personal Java project built as a **"learn by doing"** exercise to get familiar with the Java language.

## About
eComicsManager is a command-line tool to help manage local comic book files. It handles common tasks like renaming pages, compressing/extracting archives, and converting between comic-specific formats (`.cbz`, `.cbr`) and their standard equivalents (`.zip`, `.rar`).

## Features
- Rename comic pages into the correct sequential order
- ZIP / Unzip folders
- RAR / Unrar folders
- Convert `.zip` ↔ `.cbz`
- Convert `.rar` ↔ `.cbr`

## Project Status
🚧 Work in progress — features are being implemented incrementally as Java concepts are learned.

## Requirements
- Java 17+
- Maven

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
- File I/O (in progress)
- try-with-resources (in progress)
