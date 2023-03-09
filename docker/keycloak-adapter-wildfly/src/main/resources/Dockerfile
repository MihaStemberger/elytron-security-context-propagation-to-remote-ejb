FROM quay.io/wildfly/wildfly:26.1.3.Final-jdk11
RUN ${JBOSS_HOME}/bin/add-user.sh admin admin --silent
ENV  JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8787"
COPY --chown=jboss data/wildfy ${JBOSS_HOME}
COPY --chown=jboss dependency/keycloak-adapter/modules ${JBOSS_HOME}/modules
COPY --chown=jboss dependency/deployables ${JBOSS_HOME}/standalone/deployments
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "--server-config=standalone-keycloak-adapter.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
