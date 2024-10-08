# Football World Cup Score Board

## Application Description
This application implements a Football World Cup Score Board system that allows users to manage live football matches and track scores. 
The system supports four key functionalities:

### Start a Game: 
Allows a new match to be started between two teams, initializing the score at 0-0.
### Finish a Game: 
Removes a match from the scoreboard once the game is completed.
### Update Score: 
Updates the score of an ongoing match with the provided values for the home and away teams.
### Get Summary: 
Generates a summary of the matches, sorted by total score (highest to lowest), and when scores are equal, by the most recently added match.

## Design Decisions
### No Collection Operations Support:
At this stage, the design does not support operations on collections of matches (e.g., adding multiple matches at once). In case of errors in sequential method calls, any subsequent method calls after an exception occurs will not be recorded or saved.

### Spring Boot Over Plain Maven:
The project was set up as a Spring Boot application instead of a simple Maven project to allow for easier future expansion. Spring Boot provides a robust framework for adding new features such as REST APIs or additional services without needing a complete redesign.

### Command Objects as Domain Events:
The use of command objects (StartGameCommand, UpdateGameCommand, etc.) was introduced to model domain events. These commands simplify future functionality extensions or parameter changes by encapsulating input data in a structured way. This also adheres to good domain-driven design principles, making the system more flexible for future modifications.