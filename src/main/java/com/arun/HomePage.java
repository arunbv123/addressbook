package com.arun;

import com.arun.model.HbUsersEntity;
import com.arun.model.UserDataAccess;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;
    //TextField<String> usernameField;
   // PasswordTextField passwordField;
   // Label errorLabel;
    Model<String> strMdl = Model.of(" ");
//    SignInSession session = getMySession();
//
//    /**
//     * @return Session
//     */
//    private SignInSession getMySession() {
//        return (SignInSession)getSession();
//    }

    HomePage homePage = this;

    public HomePage(final PageParameters parameters) {
        super(parameters);
//        usernameField = new TextField<String>("user", Model.of(" "));
//        usernameField.setRequired(true);
//        add(usernameField);
//
//        passwordField = new PasswordTextField("pass", Model.of(" "));
//        passwordField.setRequired(true);
//        add(passwordField);
//


        add(new SignInForm("signInForm"));

        //Check if there already is a session:
//        if (session.isSignedIn()) {
//            // set feedback message and go to HomePage:
//            getSession().info("Welcome back '"+session.getUser().getUsername()+"'!");
//            setResponsePage(new HomePage());
//        }


        //add(new Label("version", getApplication().getFrameworkSettings().getVersion()));

        // TODO Add your page's components here

    }



//    public Link<Void> LoginUserLink(final String name){
//
//        return new Link<Void>(name)
//        {
//            /**
//             * @see org.apache.wicket.markup.html.link.Link#onClick()
//             */
//            @Override
//            public void onClick() {
//                strMdl.setObject("Username: "+usernameField.getModelObject()+" password: "+passwordField.getModelObject());
//                homePage.add(errorLabel);
//               // errorLabel = new Label("labelError","user "+usernameField.getValue());
//                //errorLabel
//                //setResponsePage(new RegisterUser());
//            }
//        };
//
//    }

    public final class SignInForm extends Form<Void> {

        TextField<String> usernameField;
        PasswordTextField passwordField;
        Label errorLabel;
        /**
         * Constructor
         */
        public SignInForm(final String id) {

            super(id);

            usernameField = new TextField<String>("user", Model.of(" "));
            usernameField.setRequired(true);
            add(usernameField);

            passwordField = new PasswordTextField("pass", Model.of(" "));
            passwordField.setRequired(true);
            add(passwordField);

            errorLabel = new Label("labelError",strMdl);
            errorLabel.setOutputMarkupId(true);
            add(errorLabel);
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit() {
//           strMdl.setObject("Username: "+usernameField.getModelObject()+" password: "+passwordField.getModelObject());
//            add(errorLabel);
            UserDataAccess userDataAccess = new UserDataAccess();
            HbUsersEntity user = userDataAccess.getUserByUsername(usernameField.getModelObject());
            // Sign the user in
            if (user != null && user.getUserpass().equals(passwordField.getModelObject())) {

                strMdl.setObject("Welcome '"+usernameField.getModelObject()+"'!");
                add(errorLabel);
                //setResponsePage(new HomePage());
            }
            else {
                // Get the error message from the properties file associated with the Component
                strMdl.setObject("Login failed!");

                // Register the error message with the feedback panel
                add(errorLabel);
            }
        }

    }
}
