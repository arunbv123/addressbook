package com.arun;

import com.arun.model.HbUsersEntity;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * Created by Arun on 2/28/2017.
 */
public final class SignInSession extends AuthenticatedWebSession {

    private HbUsersEntity userInSession;

    /**
     * Constructor
     *
     * @param request
     */
    protected SignInSession(Request request) {

        super(request);
    }

    @Override
    public final boolean authenticate(final String username, final String password) {

//        if (userInSession == null) {
//
//            final UserDAO userDAO = new UserDAOImpl();
//            HbUsersEntity u = userDAO.getUserByUsername(username);
//
//            if (u != null) {
//                if (u.getPassword().equalsIgnoreCase(password)) {
//                    userInSession = u;
//                }
//            }
//        }
        return true;
        //return userInSession != null;
    }


    @Override
    public Roles getRoles() {

        Roles roles = new Roles();

        //If user is signed in add the relative role:
        if(isSignedIn()) roles.add("SIGNED_IN");

        //Add the user's role:
        //if( true ){
            roles.add("ADMIN");
        //}

        return roles;
    }




}
