package com.mihas.security.service.two;

import com.mihas.security.api.ElytronServiceRemote;
import java.security.Principal;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
@Remote(ElytronServiceRemote.class)
public class ElytronServiceRemoteImpl implements ElytronServiceRemote {

    @Resource
    SessionContext sessionContext;

    @Override
    public String getPrincipal() {
        final Principal principal = sessionContext.getCallerPrincipal();
        return String.format("Name: %s, Type: %s",
                             principal.getName(),
                             principal.getClass().getCanonicalName());
    }
}
