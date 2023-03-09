package com.mihas.security.service.one;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.EJBClientInterceptor;
import org.jboss.ejb.client.EJBClientInvocationContext;
import org.wildfly.security.auth.server.SecurityDomain;
import org.wildfly.security.auth.server.SecurityIdentity;

@Startup
@Singleton
@Lock(LockType.READ)
public class EjbClientRunAsInterceptor implements EJBClientInterceptor {

    @PostConstruct
    public void init() {
        // todo: register it via org.jboss.ejb.client.EJBClientInterceptor services file
        EJBClientContext.getCurrent().registerInterceptor(0, this);
    }

    @Override
    public void handleInvocation(final EJBClientInvocationContext context) throws Exception {
        final SecurityDomain securityDomain = SecurityDomain.getCurrent();

        if (securityDomain == null) {
            System.out.println("No security domain found, proceeding unauthenticated!");
            context.sendRequest();
            return;
        }

        final SecurityIdentity identity = securityDomain.getCurrentSecurityIdentity();
        System.out.println("Found security domain, proceeding as " + identity);
        identity.runAsExceptionAction(() -> {
            context.sendRequest();
            return null;
        });
    }

    @Override
    public Object handleInvocationResult(final EJBClientInvocationContext context) throws Exception {
        return context.getResult();
    }

}
