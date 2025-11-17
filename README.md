# GO HOME  ROBOT!
A JavaFX grid-based game utilizing data structures, and search and sorting algorithms. 

## Overview
Go Home Robot! is a game set on an `n × n` grid where the player controls a robot that must reach its house while:
- Avoiding monsters
- Collecting power-ups
- Minimizing distance traveled
- Managing score and travel cost
- Undoing previous moves
- Tracking and sorting high sco

## How To Run
1. Clone repository 
git clone https://github.com/CameronA001/dataStructuresFinalProject.git/

2. Compile
javac -d bin src/robotgohome/*.java?

3.Run
java -cp bin robotgohome.Driver


## Data Structures Used
- **HashTable** – stores monsters for quick lookup and removal
- **ArrayList<Integer>** – stores high scores
- **Stack<Move>** – tracks moves for undo functionality

## Algorithms Used
- **Selection Sort** – sorts high scores before saving to file
- **DFS-inspired logic** – used for monsters’ path tracking

## Technologies
- Java
- JavaFX
- File I/O


