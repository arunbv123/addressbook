package com.arun;

import com.arun.contacts.ContactPage;
import com.arun.hibernate.HibernateManager;
import com.arun.model.HbUsersEntity;
import com.arun.model.UserDataAccess;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {

    private static final org.jboss.logging.Logger LOGGER = org.jboss.logging.Logger.getLogger(HomePage.class);
    private static final long serialVersionUID = 1L;

    private Model<String> loginErModel = Model.of(" ");
    private Model<String> signInUpLinkModel = Model.of("Sign Up?");
    private Model<String> signInUpButtonModel = Model.of("Sign In");

    private SignInForm signInForm;


    //HomePage homePage = this;


    private PasswordTextField rePasswordField;
    private TextField<String> emailField;

    private TextField<String> usernameField;
    private PasswordTextField passwordField;
    private WebMarkupContainer rePassDiv;
    private WebMarkupContainer emailDiv;
    private Button signInUpButton;
    private Link<String> link;


    private Label errorLabel;
    private Label signInUpLinkLabel;


    private boolean signup = false;

    public HomePage(final PageParameters parameters) {
        super(parameters);


        signInForm = new SignInForm("signInForm");
        add(signInForm);




    }


    /**
     * Sign In and Sign Up form
     */
    public final class SignInForm extends Form<Void> {



        /**
         * Constructor
         */
        private SignInForm(final String id) {

            super(id);

            usernameField = new TextField<>("user", Model.of(""));
            usernameField.setRequired(true);
            add(usernameField);

            passwordField = new PasswordTextField("pass", Model.of(""));
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

            emailField = new TextField<>("signUpEmail", Model.of(""));
            emailField.setRequired(true);
            emailDiv.add(emailField);

            signInUpButton = new Button("signInUpButton", signInUpButtonModel);

            add(signInUpButton);
            setDefaultButton(signInUpButton);



           link = new Link<String>("signInUpLink") {


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

            LOGGER.info("signin submit");
            UserDataAccess userDataAccess = new UserDataAccess();
            if (!signup) {

                if (usernameField.getInput().equals("") ||passwordField.getModelObject().equals("")){
                    loginErModel.setObject("Please fill all the Fields");
                    add(errorLabel);
                    return;
                }

                HbUsersEntity user = userDataAccess.getUserByUsername(usernameField.getModelObject());
                // Sign the user in
                if (user != null && user.getUserpass().equals(passwordField.getModelObject())) {

                    loginErModel.setObject("Welcome '" + usernameField.getModelObject() + "'!");
                    add(errorLabel);
                    setResponsePage(new ContactPage(user.getIduser()));
                } else {
                    // Get the error message from the properties file associated with the Component
                    loginErModel.setObject("Login failed!");

                    // Show the error message
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




}
