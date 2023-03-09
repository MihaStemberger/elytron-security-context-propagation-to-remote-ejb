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

**3. Deploy artifacts**
```bash
mvn wildfly:deploy-only
```

**4. Run [ServletTest](code/tests/src/test/java/com/mihas/security/tests/ServletTest.java)**
or execute tests via cli
```bash
mvn verify -DskipTests=false
```

The result of the tests will show, that with usage of Keycloak Adapter, the Remote EJB gets a resolved principal.
```text
Sending request to: http://localhost:8081/service-one/
HTTP Status: 200
HTTP principal: Name: 9302f4a1-5f37-4699-9971-ca6ff56bebc6, Type: org.keycloak.KeycloakPrincipal
Local EJB principal: Name: 9302f4a1-5f37-4699-9971-ca6ff56bebc6, Type: org.keycloak.KeycloakPrincipal
Remote EJB principal: Name: 9302f4a1-5f37-4699-9971-ca6ff56bebc6, Type: org.keycloak.KeycloakPrincipal

```
With out of the box standalone-full.xml, the Remote EJB principal remains anonymous.
```text
Sending request to: http://localhost:8080/service-one/
HTTP Status: 200
HTTP principal: Name: 9302f4a1-5f37-4699-9971-ca6ff56bebc6, Type: org.wildfly.security.http.oidc.OidcPrincipal
Local EJB principal: Name: 9302f4a1-5f37-4699-9971-ca6ff56bebc6, Type: org.wildfly.security.http.oidc.OidcPrincipal
Remote EJB principal: Name: anonymous, Type: org.wildfly.security.auth.principal.AnonymousPrincipal
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
