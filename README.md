## NetNav: Network Path Optimizer

Welcome to NetNav, a network path optimizer designed to find the best routes between different routers based on parameters like latency and bandwidth. This project uses Spring for the backend and React.js for the frontend. It is a collaborative effort led by GitHub user [`@octagonemusic`](https://github.com/octagonemusic).

## Prerequisites

Before getting started with NetNav, ensure you have the following prerequisites:

- **PostgreSQL database**: NetNav requires a PostgreSQL database to store router route data. Ensure you have PostgreSQL installed on your system.
- **Maven**: Maven is required to build and manage the backend project dependencies. You can download and install Maven from [here](https://maven.apache.org/install.html).
- **Spring Framework**: Spring is used for the backend REST APIs. Ensure you have a basic understanding of Spring and its configuration.
- **Node.js and npm**: Node.js and npm are required to run the frontend application. You can download and install them from [here](https://nodejs.org/).

## Usage Guide

Clone this repository to your local machine: `git clone https://github.com/octagonemusic/NetNav.git`.

### Backend Setup

1. Navigate to the backend directory: `cd netnav-backend`.
2. Modify the `application.properties` file located in the `src/main/resources` directory to set your PostgreSQL database URL.

   - Example:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
     spring.datasource.username=${DB_USERNAME}
     spring.datasource.password=${DB_PASSWORD}
     ```

3. Set up environment variables `DB_USERNAME` and `DB_PASSWORD` for the PostgreSQL database user and password.

   - **For Unix-based Systems (Linux/MacOS)**:

     - Example:
       ```bash
       export DB_USERNAME=your_username
       export DB_PASSWORD=your_password
       ```

   - **For Windows**:
     - Example (PowerShell):
       ```powershell
       $env:DB_USERNAME="your_username"
       $env:DB_PASSWORD="your_password"
       ```

4. Execute `mvn clean install` to build the project.
5. Run `mvn spring-boot:run` to launch the server.

### Frontend Setup

1. Navigate to the frontend directory: `cd netnav-frontend`.
2. Execute `npm install` to install dependencies.
3. Run `npm run dev` to initialize the React.js application.

To utilize the application, open your browser and visit `http://localhost:3000`.

## Feedback and Support

For any inquiries or feedback concerning NetNav, please reach out to [`@octagonemusic`](https://github.com/octagonemusic) on GitHub.
