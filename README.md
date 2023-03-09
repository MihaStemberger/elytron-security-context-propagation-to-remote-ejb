# Elitron

Playground for troubleshooting security context propagation in remote EJB calls.

### Credentials

* Keycloak admin (http://localhost:8180/admin/): admin / admin
* Wildfly OIDC admin (http://localhost:9990/): admin / admin
* Wildfly Keycloak Adapter admin (http://localhost:9991/): admin / admin
* Example user in _elitron_ realm: johndoe / johndoe

## Usage

**1. Build maven project**
```bash
mvn clean install
```

**2. Start docker containers**
```bash
docker compose up
```

**3. Run [ServletTest](com.mihas.security.tests.ServletTest)**
or execute tests via cli
```bash
mvn verify -DskipTests=false
```

## Deploying changes
If any changes are made to source code use: `mvn clean install wildfly:deploy-only`

If any changes are made to a DockerFile:
1. remove running containers:
    ```bash 
    docker compose down
    ```
2. rebuild the project:
    ```bash 
    mvn clean install
    ```
3. build new docker images: 
    ```bash 
   docker compose build
    ```
4. start docker containers: 
    ```bash 
   docker comose up
    ```
