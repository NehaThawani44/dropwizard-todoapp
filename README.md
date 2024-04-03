# Dropwizard To-Do Management Service

This microservice is designed to manage To-Dos for a user. User can have types of Todo and each todo has a list of subtasks
Built with Java and Dropwizard, and leveraging the H2 database for persistence, 
it offers a RESTful API for the full lifecycle management of to-dos and their subtasks.

## Features

- **CRUD Operations**: Manage your to-dos with full create, read, update, and delete capabilities, including handling of subtasks.
- **Data Persistence**: Utilizes the PostgreSQL to ensure data is maintained across application restarts.
- **Efficient and Lightweight**: Built on Dropwizard, known for its minimal, lightweight, and rapid startup time.
- **Flexible Data Access**: Utilizes Hibernate, embracing the power of ORM over the straightforwardness of JDBC and bypassing the need for simpler frameworks like JDBI.
- **Testing Ready**:  Includes unit and integration tests, ensuring reliable operation.

## Getting Started

### Prerequisites

- Java JDK 17 
- Maven 3.8.1 
- Dropwizard 2.0.0
- Postgresql 42.2.14

### Installation
Note: Master branch has apis using Postgresql, the branch h2_config is only for reference for H2 database config.
1. Clone the repository:
    ```bash
    git clone https://github.com/NehaThawani44/dropwizard-todoapp.git
    ```

2. Navigate to the project directory:
    ```bash
    cd dropwizard-todoApp
    ```

3. Build the project with Maven:
    ```bash
    mvn clean install
    ```

4. Start the application:
    ```bash
    java -jar target/dropwizard-SNAPSHOT.jar server config.yml
    ```

Now, the service is up and running, ready to manage to-dos.

### Using the API

With your server running on the port configured(config.yml), you can perform CRUD operations on your 'to-dos'. For example, to add a new to-do:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"title": "New To-Do", "description": "Learn Dropwizard"}' http://localhost:8095/todos

curl -X POST http://localhost:8095/todos \
     -H 'Content-Type: application/json' \
     -d '{
  
  "title": "Review Code",
  "description": "Conduct code review with the team.",
  "type": "WORK",
  "status": "IN_PROGRESS",
  "dueDate": "2023-10-05",
  "subtasks": [
    {
      "id": 1,
      "title": "Review backend code",
      "description": "Go through the API service layer.",
      "status": "PENDING",
      "dueDate": "2023-10-03",
  
    },
    {
      "id": 2,
      "title": "Review frontend code",
      "description": "Check the React components for the dashboard.",
      "status": "PENDING",
      "dueDate": "2023-10-04",
    
    }
  ]
 
 
}'
		 
curl -X GET 'http://localhost:8095/todos/2'		 
```


Get all the todos: http://localhost:8095/todos/all

###Response should be somewhat like this:

{
    "id": 16,
    "title": "Review Code",
    "description": "Conduct code review with the team.",
    "status": "IN_PROGRESS",
    "dueDate": "05.10.2023",
    "subtasks": [
        {
            "id": 34,
            "title": "Review backend code",
            "description": "Go through the API service layer.",
            "version": 1712143646857
        },
        {
            "id": 35,
            "title": "Review frontend code",
            "description": "Check the React components for the dashboard.",
            "version": 1712143646859
        }
    ],
    "type": "WORK",
    "version": 1712143646833,
    "user": null
}
