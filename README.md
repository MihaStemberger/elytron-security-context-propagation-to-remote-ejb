# Elitron

Playground for troubleshooting security context propagation in remote EJB calls.

## Usage

**1. Start Keycloak and Wildfly**
```bash
docker compose up
```

Note:
* The `elitron` realm is automatically imported into Keycloak from [elitron-realm.json](elytron-realm.json)
* The [wildfly](wildfly/) directory is mounted inside wildfly container at _/opt/jboss/wildfly/standalone/configuration_
* Wildfly uses [standalone-elitron.xml](wildfly%2Fstandalone-elitron.xml) configuration 

**2. Deploy the application**
```bash
mvn clean wildfly:deploy
```

**3. Run [ServletTest]()**

## Credentials

* Keycloak admin (http://localhost:8180/admin/): admin / admin
* Wildfly admin (http://localhost:9990/): admin / admin
* Example user in _elitron_ realm: johndoe / johndoe
