# Java Storage / Data Management Application
Myself and Shaun Conroy developed this project for one of our continuous assessments in the Object Oriented Programming module.

The Java application takes user input to find actor data on a local database, if the actor is not in the database it will check a TV API and add the actor's data to that database. Users are given many ways to manipulate the data.

A user is welcomed with a menu with the following options:

1. Search for an actor
   - Searches local database for result, if no result found then go to API.
2. Display all actors sorted by name
3. Display all actors sorted by ID
4. Display all actors sorted by rating
5. Edit person
   - Checks if actor exists, if they do then:
     - Edit actor rating
     - Edit actor comments
6. Export search results to HTML file
   - Display readable HTML page with actors details.
7. View local HTML files
   - Show list of generated local HTML files, gives user an option to open HTML file.
  
The application has validation at each stage. If a user makes a mistake they are navigated to the appropriate stage.
Some examples include:
- Input mismatch e.g. typing a letter instead of a word
- File name already exists issues
- Usual out of range issues e.g. Selecting a rating outside of the number range.
