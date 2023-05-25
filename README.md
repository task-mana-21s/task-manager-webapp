# Task Manager 21S
A task manager web app is a project management tool designed to help individuals and teams manage their tasks efficiently. It allows users to create projects, break them down into tasks, assign tasks to team members, set deadlines, track progress, and collaborate with others in real-time. The app provides a visual representation of tasks and projects, making it easy to see what needs to be done and when. It can be used for personal or professional projects, and is a great way to stay organized and increase productivity.
## Setup
1. Install [Java 20 SDK](https://www.oracle.com/java/technologies/downloads/)
2. Install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Ultimate/Community Edition)
3. Install [Maven](https://maven.apache.org/download.cgi)
4. Install MySQL database from [MySQL Installer](https://dev.mysql.com/downloads/installer/) or run on Docker following guide [Docker MySQL](https://geshan.com.np/blog/2022/02/mysql-docker-compose/)
5. Check if MySQL database is running on the machine or as a Docker container
6. Clone GitHub repository 
7. Open project in IntelliJ IDEA
8. Update database connection string, username and password in [application.yml](src%2Fmain%2Fresources%2Fapplication.yml) file if needed
9. Configure Front-End by selecting "Maven->Install" in the Maven menu.
10. Run Back-End by selecting "Run" or "Shift+F10"


## Project URL's:

#### Front-End: http://localhost:8080
#### API: http://localhost:8080/api
#### API Docs: http://localhost:8080/v3/api-docs
#### API Docs in yaml format: http://localhost:8080/v3/api-docs.yaml
#### Swagger: http://localhost:8080/swagger-ui/index.html


## Troubleshooting
Describes common issues while running a project

* Check if Java is installed correctly in your machine: open terminal, run command "java --version". It should display "java 20.0.1 2023-04-18..." or similar message
* Check if Maven is installed correctly in your machine: pen terminal, run command "mvn -version". It should display "Apache Maven 3.9.1..." or similar message


## Git Flow
1. **Make Pull Request:** ALWAYS update project before starting new work. You can do this by clicking on "Git->Pull" button.
2. **Create a new branch:** Before making any changes to your code, create a new branch in IDE. This allows you to work on your code independently of the main branch. You can do this by clicking on the "Git->New Branch" button and typing in a name for your new branch.
3. **Make changes:** Once you have created a new branch, make the changes you want to your code. This could be fixing a bug, adding a new feature, or updating documentation.
4. **Commit changes:** Once you have made your changes, commit them to your branch. This creates a snapshot of your code at a specific point in time. You can do this by clicking on the "Git->Commit" button and typing in a message describing your changes.
5. **Push changes to GitHub:** After committing your changes, push them to GitHub. This uploads your changes to the repository and makes them available to others. You can do this by clicking on the "Git->Push" button in your IDE.
6. **Create a pull request:** Once you have pushed your changes, create a pull request. This notifies others that you have made changes and allows them to review and merge your code. You can do this by clicking on the "Create pull request" button in GitHub.
7. **Merge changes:** After your pull request has been reviewed and approved, merge your changes into the main branch. This incorporates your changes into the main codebase. You can do this by clicking on the "Merge pull request" button in GitHub.

## Contributors
* Vardenis
* Pavardenis
