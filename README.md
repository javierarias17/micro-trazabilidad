<br />
<div align="center">
<h3 align="center">PRAGMA POWER-UP — micro-trazabilidad</h3>
  <p align="center">
    Microservice responsible for recording and querying the order lifecycle history, and reporting restaurant operational efficiency.
  </p>
</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MongoDB](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)

### Service Dependencies

Communicates with the following microservices via Feign Client:
- **micro-plazoleta** — used for internal validation context

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* JDK 17 [https://jdk.java.net/17/](https://jdk.java.net/17/)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MongoDB [https://www.mongodb.com/try/download/community](https://www.mongodb.com/try/download/community)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

### Environment Variables

Configure the following environment variables before running:

| Variable | Description |
|---|---|
| `SERVER_PORT` | Port on which the service runs |
| `SPRING_PROFILES_ACTIVE` | Active profile (e.g. `dev`) |
| `MONGODB_URI` | MongoDB connection URI |
| `JWT_SECRET` | Secret key for JWT validation |
| `PLAZOLETA_SERVICE_URL` | Base URL of micro-plazoleta |

### Installation

1. Clone the repo
2. Change directory
   ```sh
   cd micro-trazabilidad
   ```
3. Ensure MongoDB is running and accessible
4. Set the required environment variables
5. Build and run
   ```sh
   ./gradlew bootRun
   ```

<!-- USAGE -->
## Usage

Once running, open the Swagger UI in your browser:

```
http://localhost:<SERVER_PORT>/swagger-ui/index.html
```

### API Endpoints

| Method | Path | Role | Description |
|---|---|---|---|
| `POST` | `/api/v1/order-logs` | INTERNAL | Record an order status change log |
| `GET` | `/api/v1/order-logs/{orderId}` | CUSTOMER | Get the full history of an order |
| `GET` | `/api/v1/restaurants/{restaurantId}/efficiency` | OWNER | Get average order processing time report for a restaurant |

<!-- TESTS -->
## Tests

```sh
./gradlew test jacocoTestReport
```

Or right-click the test folder in IntelliJ and choose **Run tests with coverage**.
