## Bookmarks manager

This is a simple bookmarks manager with command line interface. It is a client-server application, both written in Java using the latest features of Java 19.

Made as a course project for the [Modern Java Technologies course](<https://github.com/fmi/java-course>) at FMI, Sofia University. The rest of the task in the course can be found [here](<https://github.com/Tsvetilin/MJT-2022>).

### Server
- Uses a file storage as database
- Uses a socket connection for communication with the client
- Uses Bitly API for shortening URLs
- Has a simple command line interface
- Has a simple logging system - in file and console
- Implements various design patterns, such as:
    - Singleton
    - Factory
    - Builder
    - Observer
    - Strategy
    - Command

### Client
- Sends requests to the server and recieves responses as messages
- Has a simple command line interface
- Extracts the bookmarks saved in Chrome browser

### Run
- Configure the server when creating an instance of `ServerOptions` class
- Environment variable must be set with an API Key to use the Bitly API

### Test
- Written in JUnit 5
- 50% of the code is covered by unit tests

### License
This project is licensed under the GNU General Public License GPLv3 License - see the [LICENSE](LICENSE) file for details

