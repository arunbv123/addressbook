package com.arun;

import com.arun.contacts.ContactPage;
import com.arun.hibernate.HibernateManager;
import com.arun.model.HbUsersEntity;
import com.arun.model.UserDataAccess;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

import java.util.logging.Logger;

public class HomePage extends WebPage {

    private static final org.jboss.logging.Logger LOGGER = org.jboss.logging.Logger.getLogger(HomePage.class);
    private static final long serialVersionUID = 1L;
    //TextField<String> usernameField;
    // PasswordTextField passwordField;
    // Label errorLabel;
    Model<String> loginErModel = Model.of(" ");
    Model<String> signInUpLinkModel = Model.of("Sign Up?");
    Model<String> signInUpButtonModel = Model.of("Sign In");

    SignInForm signInForm;
//    SignInSession session = getMySession();
//
//    /**
//     * @return Session
//     */
//    private SignInSession getMySession() {
//        return (SignInSession)getSession();
//    }

    HomePage homePage = this;

//    TextField<String> usernameField;
//    PasswordTextField passwordField;
    PasswordTextField rePasswordField;
    TextField<String> emailField;
    //Label errorLabel;
    TextField<String> usernameField;
    PasswordTextField passwordField;
    WebMarkupContainer rePassDiv;
    WebMarkupContainer emailDiv;
    Button signInUpButton;
    Link<String> link;
    //SignInUpLink signInUpLink;

    Label errorLabel;
    Label signInUpLinkLabel;

    //SignUpForm signUpForm;

    boolean signup = false;

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

        signInForm = new SignInForm("signInForm");
        add(signInForm);
        //signUpForm = new SignUpForm("signUpForm");
        //signUpForm.setEnabled(false);
        //signUpForm.onFormSubmitted(new );

        //add(signUpForm);

//        usernameField = new TextField<String>("signUpUser", Model.of(""));
//        usernameField.setRequired(true);
//        add(usernameField);
//
//        passwordField = new PasswordTextField("signUpPass", Model.of(""));
//        passwordField.setRequired(true);
//        add(passwordField);


//        errorLabel = new Label("signUpError", signupErModel);
//        errorLabel.setOutputMarkupId(true);
//        add(errorLabel);
//
//        Link<String> link = new Link<String>("signUpButton") {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public void onClick() {
//                LOGGER.info("inside click: ");
//
//            }
//
//
//        };

//        link.setOutputMarkupId(true);
//
//        add(link);


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

            errorLabel = new Label("labelError", loginErModel);
            errorLabel.setOutputMarkupId(true);
            add(errorLabel);

            rePassDiv = new WebMarkupContainer("passDiv");
            rePassDiv.setVisible(false);
            add(rePassDiv);

            rePasswordField = new PasswordTextField("signUpRePass", Model.of(""));
            rePasswordField.setRequired(true);
            rePassDiv.add(rePasswordField);

            emailDiv = new WebMarkupContainer("emailDiv");
            emailDiv.setVisible(false);
            add(emailDiv);

            emailField = new TextField<String>("signUpEmail", Model.of(""));
            emailField.setRequired(true);
            emailDiv.add(emailField);

            signInUpButton = new Button("signInUpButton", signInUpButtonModel);

            add(signInUpButton);
            setDefaultButton(signInUpButton);
            //signInUpLink = new SignInUpLink("signInUpLink",signInUpLinkModel);


           link = new Link<String>("signInUpLink") {
                //private static final long serialVersionUID = 1L;

                @Override
                public void onClick() {

                    LOGGER.info("inside click: ");
                    if (!signup) {
                        signInUpButtonModel.setObject("Sign Up");
                        signInForm.add(signInUpButton);
                        signInForm.setDefaultButton(signInUpButton);
                        signInUpLinkModel.setObject("Sign In?");
                        link.add(signInUpLinkLabel);
                        emailDiv.setVisible(true);
                        rePassDiv.setVisible(true);
                        signup = true;

                    }else{
                        signInUpButtonModel.setObject("Sign In");
                        signInForm.add(signInUpButton);
                        signInForm.setDefaultButton(signInUpButton);
                        signInUpLinkModel.setObject("Sign Up?");
                        link.add(signInUpLinkLabel);
                        emailDiv.setVisible(false);
                        rePassDiv.setVisible(false);
                        signup = false;

                    }

                }


            };
           signInUpLinkLabel = new Label("signInUpLinkLabel",signInUpLinkModel);
            link.add(signInUpLinkLabel);
            //link.setModel(signInUpLinkModel);
            link.setOutputMarkupId(true);

            add(link);
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        public final void onSubmit() {
//           strMdl.setObject("Username: "+usernameField.getModelObject()+" password: "+passwordField.getModelObject());
//            add(errorLabel);
            LOGGER.info("signin submit");
            UserDataAccess userDataAccess = new UserDataAccess();
            if (!signup) {
                HbUsersEntity user = userDataAccess.getUserByUsername(usernameField.getModelObject());
                // Sign the user in
                if (user != null && user.getUserpass().equals(passwordField.getModelObject())) {

                    loginErModel.setObject("Welcome '" + usernameField.getModelObject() + "'!");
                    add(errorLabel);
                    setResponsePage(new ContactPage(user.getIduser()));
                } else {
                    // Get the error message from the properties file associated with the Component
                    loginErModel.setObject("Login failed!");

                    // Register the error message with the feedback panel
                    add(errorLabel);
                }
            }else{

                LOGGER.info("inside signup submit: "+usernameField.getInput());


                if (usernameField.getInput().equals("") ||passwordField.getModelObject().equals("") ||rePasswordField.getModelObject().equals("") ||emailField.getModelObject().equals("") ){
                    loginErModel.setObject("Please fill all the Fields");
                    add(errorLabel);
                    return;
                }

                if (!ValidateString.emailValidate(emailField.getModelObject())){
                    loginErModel.setObject("Please enter a valid email");
                    add(errorLabel);
                    return;
                }

                if (!passwordField.getModelObject().equals(rePasswordField.getModelObject())){
                    loginErModel.setObject("Passwords dont match!");
                    add(errorLabel);
                    return;
                }

                HbUsersEntity user = new HbUsersEntity();
                user.setUsername(usernameField.getModelObject());
                user.setUserpass(passwordField.getModelObject());
                user.setUseremail(emailField.getModelObject());

                boolean status = HibernateManager.createUser(user);
                if (status){
                    signInUpButtonModel.setObject("Sign In");
                    signInForm.add(signInUpButton);
                    signInForm.setDefaultButton(signInUpButton);
                    signInUpLinkModel.setObject("Sign Up?");
                    link.add(signInUpLinkLabel);
                    emailDiv.setVisible(false);
                    rePassDiv.setVisible(false);
                    signup = false;
                    loginErModel.setObject("Sign Up success, Login now");
                    add(errorLabel);
                }else{
                    loginErModel.setObject("Something went wrong , try again");
                    add(errorLabel);
                }
            }
        }

    }

    public final class SignUpForm extends Form<Void> {

        SubmitLink submitLink;
        //Button signUp;

        /**
         * Constructor
         */
        public SignUpForm(final String id) {

            super(id);


            //signUp.setDefaultFormProcessing(false);
            //delegateSubmit(signUp);

            // add(signUp);


        }


    }

//    public class SignInUpLink extends Label implements Link<Void>{
//
//        public SignInUpLink(String id, IModel<?> model) {
//            super(id, model);
//        }
//
//        @Override
//        public void onClick() {
//
//        }
//    }

    public static Link<Void> SignOutLink(final String name) {

        return new Link<Void>(name) {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick() {


                // Log out:
                // getSession().invalidate();
                // setResponsePage(SignIn.class);
            }
        };
    }

}
