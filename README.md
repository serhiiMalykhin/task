# Task Application

This document provides instructions on how to run the Task Application.

## Prerequisites

Before you begin, ensure you have the following installed:

* **Java Development Kit (JDK):** Version 21 or higher.
* **Maven:** For building and managing dependencies.
* **Docker:** For running the PostgreSQL databases and the application container.
* **Git (Optional):** Recommended for cloning the repository.
* **Bash/Shell (Optional):** Required to run the `rebuild.sh` script on Windows. If you're using Windows, consider installing Git Bash or WSL (Windows Subsystem for Linux).

## Running the Application

1.  **Build the Application and Start Docker Containers:**

    * Execute the `rebuild.sh` script from the root directory. This script will:
        * Build the application's JAR file using Maven (`mvn clean install`).
        * Navigate to the `docker` directory and start the Docker containers using `docker-compose up --build`.
    * Open a terminal and navigate to the root directory of the project.
    * Run the script:

        ```bash
        ./rebuild.sh
        ```

    * **Note:** If you're on Windows and don't have Bash/Shell installed, you'll need to manually execute the commands within the `rebuild.sh` script.

2.  **Access the Application (Swagger UI):**

    * Open a web browser and navigate to the following URL:

        ```
        http://localhost:8080/swagger-ui/index.html
        ```

    * This will open the Swagger UI, which provides documentation for your API endpoints.

## Notes

* The application uses port 8080 by default. If you have changed the port in your application configuration, adjust the URLs accordingly.
* If you encounter any issues, check the Docker container logs using `docker-compose logs <container_name>` (e.g., `docker-compose logs task-app`).
* The `rebuild.sh` script is provided as a convenience tool. You can manually execute the Docker and Maven commands if needed.
* Ensure that Docker is running before executing the `rebuild.sh` script.
* The `docker-compose.yml` file is located in the `docker` directory.