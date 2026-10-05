# agents

Spring Boot 3 (Java 21) REST service for managing "agents". This is a learning project for Java backend development and Git workflow.

## Run it

```bash
mvn spring-boot:run          # starts on http://localhost:8080
mvn test                     # runs unit + MockMvc tests
```

## Try the API (curl)

```bash
# create
curl -X POST localhost:8080/api/agents -H "Content-Type: application/json" \
     -d '{"name":"Ravi","role":"support"}'
# list
curl localhost:8080/api/agents
# get one
curl localhost:8080/api/agents/1
# update
curl -X PUT localhost:8080/api/agents/1 -H "Content-Type: application/json" \
     -d '{"name":"Ravi","role":"billing","status":"INACTIVE"}'
# delete
curl -X DELETE localhost:8080/api/agents/1
# health check
curl localhost:8080/actuator/health
```

## Project layout

```
src/main/java/com/ashish/agents
├── AgentsApplication.java      # Spring Boot entry point
├── controller/AgentController  # HTTP layer (REST endpoints)
├── service/AgentService        # business logic + in-memory store
├── model/                      # Agent, AgentRequest, AgentStatus (JSON shapes)
└── exception/                  # 404 handling via @RestControllerAdvice
```

## Git branching workflow

| Branch       | Purpose                                                        |
|--------------|----------------------------------------------------------------|
| `main`       | Stable, released code only. Every merge here is tagged.        |
| `dev`        | Integration branch. All features merge here first.             |
| `feature/*`  | One branch per change, created from `dev`.                     |
| `hotfix/*`   | Urgent fixes created from `main`, merged to `main` and `dev`.  |

### Day-to-day

```bash
git checkout dev && git pull origin dev           # 1. start from latest dev
git checkout -b feature/add-database              # 2. new feature branch
# ...edit code...
mvn test                                          # 3. make sure it works
git add . && git commit -m "Add H2 database"      # 4. commit
git push -u origin feature/add-database           # 5. push, open PR -> dev
```

### Releasing

```bash
git checkout main && git pull origin main
git merge --no-ff dev -m "Release v0.2.0"
git tag v0.2.0
git push origin main --tags
```
