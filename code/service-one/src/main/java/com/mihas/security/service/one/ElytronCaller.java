package com.mihas.security.service.one;

import com.mihas.security.api.ElytronServiceRemote;
import java.security.Principal;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;

@Singleton
public class ElytronCaller {

    @EJB(lookup = "java:global/service-two/ElytronServiceRemoteImpl!com.mihas.security.api.ElytronServiceRemote")
    ElytronServiceRemote elytronServiceRemote;

    @Resource
    SessionContext sessionContext;

    public String getLocalPrincipal() {
        final Principal principal = sessionContext.getCallerPrincipal();
        return String.format("Name: %s, Type: %s",
                             principal.getName(),
                             principal.getClass().getCanonicalName());
    }

    public String getRemotePrincipal() {
        return elytronServiceRemote.getPrincipal();
    }
}
