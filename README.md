# Dropwizard To-Do Management Service

This microservice is designed to manage To-Dos similar to applications like Todoist but with a simple, streamlined interface. Built with Java and Dropwizard, and leveraging the H2 database for persistence, it offers a RESTful API for the full lifecycle management of to-dos and their subtasks.

## Features

- **CRUD Operations**: Manage your to-dos with full create, read, update, and delete capabilities, including handling of subtasks.
- **Data Persistence**: Utilizes the H2 in-memory database to ensure data is maintained across application restarts.
- **Efficient and Lightweight**: Built on Dropwizard, known for its minimal, lightweight, and rapid startup time.
- **Flexible Data Access**: Incorporates JDBI for straightforward JDBC operations, avoiding complex ORM frameworks.
- **Testing Ready**: Includes unit and integration tests, ensuring reliable operation.

## Getting Started

### Prerequisites

- Java JDK 8 or newer
- Maven 3.6.0 or newer

### Installation

1. Clone the repository:
    ```bash
    git clone https://your-repository-url.git
    ```

2. Navigate to the project directory:
    ```bash
    cd your-project-directory
    ```

3. Build the project with Maven:
    ```bash
    mvn clean install
    ```

4. Start the application:
    ```bash
    java -jar target/dropwizard-todo-1.0-SNAPSHOT.jar server config.yml
    ```

Now, the service is up and running, ready to manage to-dos.

### Using the API

With your server running, you can perform CRUD operations on your to-dos. For example, to add a new to-do:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"title": "New To-Do", "description": "Learn Dropwizard"}' http://localhost:8095/todos

curl -X POST http://localhost:8095/todos \
     -H 'Content-Type: application/json' \
     -d '{
           "title": "Complete Project",
           "description": "Finalize the coding tasks for Project X.",
           "subtasks": [
             {
               "id": 4,
               "title": "Write Unit Tests",
               "description": "Create unit tests for all new methods."
             },
             {
               "id": 6,
               "title": "Review Code",
               "description": "Conduct code review with the team."
             }
           ]
         }'
		 
curl -X GET 'http://localhost:8095/todos/2'		 
```
http://localhost:8095/

Get all the todos: http://localhost:8095/todos/all

###Response should be somewhat like this:

[
    {
        "id": 1,
        "title": "Complete Project",
        "description": "Finalize the coding tasks for Project X.",
        "status": null,
        "dueDate": null,
        "subtasks": [],
        "version": 1711991464422
    },
    {
        "id": 2,
        "title": "Fix Bugs",
        "description": "Resolve all issues found during testing.",
        "status": null,
        "dueDate": null,
        "subtasks": [],
        "version": 1711991644243
    },
    {
        "id": 3,
        "title": "Complete Project",
        "description": "Finalize the coding tasks for Project X.",
        "status": null,
        "dueDate": null,
        "subtasks": [],
        "version": 1711991682651
    },
    {
        "id": 4,
        "title": "Complete Project",
        "description": "Finalize the coding tasks for Project X.",
        "status": null,
        "dueDate": null,
        "subtasks": [
            {
                "id": 12,
                "title": "Write Unit Tests",
                "description": "Create unit tests for all new methods.",
                "version": 1711992635411
            },
            {
                "id": 13,
                "title": "Review Code",
                "description": "Conduct code review with the team.",
                "version": 1711992635412
            }
        ],
        "version": 1711992635402
    }
]
