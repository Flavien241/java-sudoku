#  Sudoku & MultiDoku Solver – Java Project

This project is a modular and extensible framework for building, validating, and solving standard Sudoku and MultiDoku puzzles. It includes several variants of grid sizes (4x4, 5x5, 9x9), support for shared cells between grids, and multiple solving and display strategies.

## Project Highlights

- Object-oriented design with reusable core components: `Grille`, `Case`, `Bloc`, `MultiDoku`
- Support for multiple grid sizes and symbol sets
- Multiple solving strategies with backtracking
- Console-based colored display using ANSI escape codes
- CSV-style input and random generation of playable grids
- Multi-grid solver with shared zones (MultiDoku logic)

---

##  Project Structure

| File / Class | Description |
|--------------|-------------|
| `Main.java` | Entry point to run various Sudoku/MultiDoku simulations |
| `Grille.java` | Core Sudoku grid: manages blocks, validation, and solving |
| `Case.java` | Individual cell in the grid |
| `CasePartagee.java` | Special cell shared between multiple grids |
| `Bloc.java` | Logical block of 3x3 (or other sizes) within a grid |
| `MultiDoku.java` | Orchestrator for multiple synchronized Sudoku grids |
| `SudokuGenerator.java` | Tool to generate solvable random Sudoku puzzles |
| `Menu.java` | Optional CLI-based menu interface |
| `*.class` | Compiled Java bytecode |
| `test*.txt` | Java test files or main scripts testing various configurations |
| `test_generergrille.txt` | Input file of preset cell positions for a specific puzzle |

---

##  Included Tests & Features

###  test1.txt
- 4x4 Sudoku with manual initialization
- Block-by-block insertion and custom display
- Demonstrates base logic of `Grille`, `Case`, `Bloc`

###  test2.txt
- Classic 9x9 Sudoku with backtracking resolution
- Outputs result to `sudoku_solution.txt`
- Includes colorized terminal display by block

###  test3.txt
- Small 5x5 grid testing adaptability of core logic
- Highlights dynamic color rendering and block management

###  test4.txt
- Full valid grid creation then transformation into playable Sudoku (by removing values)
- Demonstrates `SudokuGenerator` usage

###  test7.txt
- MultiDoku: two linked 9x9 Sudoku grids with shared zones
- Solves across both while respecting constraints on shared cells

---

##  Display Example

Some test files use color-coded output for better readability. Example output for a 9x9 grid might look like:


---

##  How to Run

1. Compile with `javac *.java`
2. Run a specific test file using:  
   `java Main` (or other specific file if applicable)
3. For `MultiDoku` executions: launch `MultiDoku.java` directly

---

##  Files & Usage

- `README.md` – this file
- `sudoku_solution.txt` – output file for resolved grid (in some tests)
- `test_generergrille.txt` – sample formatted grid for parser

---

##  Author

**Flavien BONTEMPS**  
Polytech Lyon - Computer Science Engineering curriculum
flavienbontemps24@gmail.com  
[LinkedIn](https://www.linkedin.com/in/BONTEMPSFlavien)

---

## Scope

Academic Java project demonstrating object-oriented modelling, constraint checking and backtracking on Sudoku variants.

