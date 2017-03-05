package com.arun.contacts;

import com.arun.HomePage;
import com.arun.ValidateString;
import com.arun.hibernate.HibernateManager;
import com.arun.model.ContactDataAccess;
import com.arun.model.HbContactsEntity;
import com.arun.model.HbUsersEntity;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ImageButton;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ILinkListener;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.UrlUtils;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.jboss.logging.Logger;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Arun on 3/1/2017.
 */
public class ContactPage extends WebPage {

    private static final Logger LOGGER = Logger.getLogger(ContactPage.class);


    private PackageResourceReference editRef = new PackageResourceReference(ContactPage.class,"edit.png");
    private PackageResourceReference deleteRef = new PackageResourceReference(ContactPage.class,"delete.png");
    private PackageResourceReference saveRef = new PackageResourceReference(ContactPage.class,"save.png");
    private PackageResourceReference addRef = new PackageResourceReference(ContactPage.class,"add.png");
    private PackageResourceReference signoutRef = new PackageResourceReference(ContactPage.class,"signout.png");

    Model<String> nameModel = Model.of(" ");
    Model<String> addressModel = Model.of(" ");
    Model<String> phoneModel = Model.of(" ");
    Model<String> emailModel = Model.of(" ");
    Model<String> nameHeadingModel = Model.of(" ");
    Model<String> contactErrorModel = Model.of("Error");
    Model editModel = Model.of(editRef);
    Model deleteModel = Model.of(deleteRef);
    Model addModel = Model.of(addRef);
    Model signoutModel = Model.of(signoutRef);

    TextField<String> nameField;
    TextField<String> phoneField;
    TextField<String> emailField;
    TextArea<String>  addressField;
    Label nameHeadingField;
    Label contactError;

    ImageButton editImage;
    ImageButton signoutImage;
    ImageButton deleteImage;
    ImageButton addImage;

    ContactDetailsForm contactDetailsForm;
    //ContactDataAccess contactDataAccess;

    int userId = -1;
    int showContactID = -1;

    boolean contactFilled = false;
    boolean save = false;



    public ContactPage(int userid) {
        //add(new Label("helloMessage", "Hello WicketWorld!"));
        userId = userid;
        //contactDataAccess = new ContactDataAccess();
        contactDetailsForm = new ContactDetailsForm("contactDetails");
        add(contactDetailsForm);
        add(new AddressNameListForm("addressNameList", userid));

        Link<Void> signoutLink = signoutLink("signOutLink");
        signoutImage = new SignOutImageButton("signoutImg",signoutModel);
        //  add(editLink("editLink").add(editImage));
        signoutImage.setDefaultFormProcessing(false);

        signoutLink.add(signoutImage);
        add(signoutLink);
        // create a list with sublists
//        List<Object> l1 = new ArrayList<>();
//        l1.add("test 1.1");
//        l1.add("test 1.2");
//        List<Object> l2 = new ArrayList<>();
//        l2.add("test 2.1");
//        l2.add("test 2.2");
//        l2.add("test 2.3");
//        l2.add("test 2.4");
//        l1.add(l2);
//        l1.add("test 1.3");
//
//        // construct the panel
//        add(new RecursivePanel("addressNameList", l1));
    }


    public final class AddressNameListForm extends Form<Void> {

        public AddressNameListForm(String id, int userid) {
            super(id);


            ContactDataAccess contactDataAccess = new ContactDataAccess();
            List<HbContactsEntity> contactList = contactDataAccess.getAllContacts(userid);
            addImage = new AddImageButton("addImg", addModel);
            addImage.setDefaultFormProcessing(false);
            add(addImage);
            RepeatingView namelistItems = new RepeatingView("namelistItems");
            if (contactList != null && !contactList.isEmpty()) {



                for (HbContactsEntity contact : contactList) {

                    //ContactLink contactLink = new ContactLink();
                    namelistItems.add(new ContactLink(namelistItems.newChildId(), contact.getContactName(), new OnContactClicked(contact)));

                    if (!contactFilled) {
                        nameHeadingModel.setObject("" + contact.getContactName());
                        contactDetailsForm.add(nameHeadingField);
                        nameModel.setObject("" + contact.getContactName());
                        contactDetailsForm.add(nameField);
                        addressModel.setObject("" + contact.getContactAddress());
                        contactDetailsForm.add(addressField);
                        phoneModel.setObject("" + contact.getContactPhone());
                        contactDetailsForm.add(phoneField);
                        emailModel.setObject("" + contact.getContactEmail());
                        contactDetailsForm.add(emailField);
                        showContactID = contact.getIdcontact();
                        contactFilled = true;
                    }


                }


            }else{
                nameHeadingModel.setObject("No Contacts available, Add new contact below");
                contactDetailsForm.add(nameHeadingField);
                editModel.setObject(saveRef);
                contactDetailsForm.add(editImage);
                nameField.setEnabled(true);
                contactDetailsForm.add(nameField);
                addressField.setEnabled(true);
                contactDetailsForm.add(addressField);
                phoneField.setEnabled(true);
                contactDetailsForm.add(phoneField);
                emailField.setEnabled(true);
                contactDetailsForm.add(emailField);
                save = true;
                contactFilled = true;
            }
            add(namelistItems);

        }


    }

    public final class ContactDetailsForm extends Form<Void> {



        public ContactDetailsForm(String id) {
            super(id);

            nameHeadingField = new Label("contactNameHeading",nameHeadingModel);
            add(nameHeadingField);

            nameField = new TextField<String>("contactName", nameModel);
            nameField.setRequired(true);
            nameField.setEnabled(false);

            add(nameField);

            phoneField = new TextField<String>("contactPhone", phoneModel);
            phoneField.setRequired(true);
            phoneField.setEnabled(false);
            add(phoneField);

            emailField = new TextField<String>("contactEmail", emailModel);
            emailField.setRequired(true);
            emailField.setEnabled(false);
            add(emailField);

            addressField = new TextArea<String>("contactAddress", addressModel);
            addressField.setRequired(true);
            addressField.setEnabled(false);
            add(addressField);

            contactError = new Label("contactError",contactErrorModel);
            contactError.setVisible(false);
            add(contactError);

            editImage = new EditImageButton("editImg",editModel);
         //  add(editLink("editLink").add(editImage));
            editImage.setDefaultFormProcessing(false);

            add(editImage);
            //editImage.add(editLink("edit"));

            deleteImage = new DeleteImageButton("deleteImg",deleteModel);
            deleteImage.setDefaultFormProcessing(false);
            add(deleteImage);
        }

        @Override
        public final void onSubmit() {
            LOGGER.info("delete clicked");
            //super.onSubmit();
        }
    }

    public class EditImageButton extends ImageButton{

        public EditImageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        public void onSubmit() {
            LOGGER.info("edit clicked ");
            if (!save){
                editModel.setObject(saveRef);
                contactDetailsForm.add(editImage);
                nameField.setEnabled(true);
                contactDetailsForm.add(nameField);
                addressField.setEnabled(true);
                contactDetailsForm.add(addressField);
                phoneField.setEnabled(true);
                contactDetailsForm.add(phoneField);
                emailField.setEnabled(true);
                contactDetailsForm.add(emailField);
                save = true;
            }else{
                //nameField.validate();
                //nameField.updateModel();


                String name = nameField.getInput();
                String email = emailField.getInput();
                String phone = phoneField.getInput();
                String address = addressField.getInput();

                LOGGER.info("contactId: "+showContactID+" userID: "+userId+" name: "+nameField.getInput()+" email: "+email+" phone: "+phone+" address: "+address);
                if (name == null || name.equals("")){
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please fill all the fields");

                    return;
                }
                if (email == null || email.equals("")){
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please fill all the fields");
                    return;
                }
                if (phone == null || phone.equals("")){
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please fill all the fields");
                    return;
                }
                if (address == null || address.equals("")){
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please fill all the fields");
                    return;
                }

                if (!ValidateString.emailValidate(email)){
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please enter valid email");
                    return;
                }

                if (!ValidateString.phoneValidate(phone)){
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please enter valid phone number");
                    return;
                }

                HbContactsEntity hbContactsEntity = new HbContactsEntity();
                if (showContactID != -1)
                    hbContactsEntity.setIdcontact(showContactID);
                hbContactsEntity.setIduser(userId);
                hbContactsEntity.setContactName(name);
                hbContactsEntity.setContactAddress(address);
                hbContactsEntity.setContactPhone(phone);
                hbContactsEntity.setContactEmail(email);
                ContactDataAccess contactDataAccess = new ContactDataAccess();
                boolean status = contactDataAccess.update(hbContactsEntity);

                if (!status){
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Something went wrong please try again");
                    return;
                }

                editModel.setObject(editRef);
                contactDetailsForm.add(editImage);
                nameField.setEnabled(false);
                contactDetailsForm.add(nameField);
                addressField.setEnabled(false);
                contactDetailsForm.add(addressField);
                phoneField.setEnabled(false);
                contactDetailsForm.add(phoneField);
                emailField.setEnabled(false);
                contactDetailsForm.add(emailField);
                //contactDetailsForm.add(nameField);
                save = false;
                contactError.setVisible(true);
                contactErrorModel.setObject("Saved successfully!!!");

                setResponsePage(new ContactPage(userId));
            }


            //LOGGER.info("edit clicked after");
        }
    }

    public class DeleteImageButton extends ImageButton{

        public DeleteImageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        public void onSubmit() {
            LOGGER.info("delete clicked ");
           if (showContactID == -1){
               nameModel.setObject("");
               nameField.setEnabled(true);
               contactDetailsForm.add(nameField);
               addressModel.setObject("");
               addressField.setEnabled(true);
               contactDetailsForm.add(addressField);
               phoneModel.setObject("");
               phoneField.setEnabled(true);
               contactDetailsForm.add(phoneField);
               emailModel.setObject("");
               emailField.setEnabled(true);
               contactDetailsForm.add(emailField);
           }else{
               boolean status = HibernateManager.deleteContact(showContactID);
               if (status) {
                   setResponsePage(new ContactPage(userId));
               }else{
                   contactError.setVisible(true);
                   contactErrorModel.setObject("Something went wrong please try again");
                   return;
               }
           }
            //LOGGER.info("edit clicked after");
        }
    }

    public class AddImageButton extends ImageButton{

        public AddImageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        public void onSubmit() {
            LOGGER.info("add clicked ");
            nameHeadingModel.setObject("Add new contact below");
            contactDetailsForm.add(nameHeadingField);
            editModel.setObject(saveRef);
            contactDetailsForm.add(editImage);
            nameModel.setObject("");
            nameField.setEnabled(true);
            contactDetailsForm.add(nameField);
            addressModel.setObject("");
            addressField.setEnabled(true);
            contactDetailsForm.add(addressField);
            phoneModel.setObject("");
            phoneField.setEnabled(true);
            contactDetailsForm.add(phoneField);
            emailModel.setObject("");
            emailField.setEnabled(true);
            contactDetailsForm.add(emailField);

            save = true;
            contactFilled = true;
            showContactID = -1;
        }
    }

    public class SignOutImageButton extends ImageButton{

        public SignOutImageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        public void onSubmit() {

        }
    }


    public class OnContactClicked implements ILinkListener {

        int contactId;
        String contactName;
        String contactAddress;
        String contactPh;
        String contactEmail;

        OnContactClicked(HbContactsEntity contact) {
            contactName = contact.getContactName();
            contactAddress = contact.getContactAddress();
            contactEmail = contact.getContactEmail();
            contactPh = contact.getContactPhone();
            contactId = contact.getIdcontact();
        }

        @Override
        public void onLinkClicked() {
            contactError.setVisible(false);
            LOGGER.info("Contact Clicked: "+contactName);
            nameHeadingModel.setObject(""+contactName);
            contactDetailsForm.replace(nameHeadingField);

            nameModel.setObject(""+contactName);
            nameField.setEnabled(false);
            contactDetailsForm.replace(nameField);
            addressModel.setObject(""+contactAddress);
            addressField.setEnabled(false);
            contactDetailsForm. add(addressField);
            phoneModel.setObject(""+contactPh);
            phoneField.setEnabled(false);
            contactDetailsForm.add(phoneField);
            emailModel.setObject(""+contactEmail);
            emailField.setEnabled(false);
            contactDetailsForm.add(emailField);
            editModel.setObject(editRef);
            contactDetailsForm.add(editImage);
            contactFilled = true;
            showContactID = contactId;
        }
    }

    public Link<Void> signoutLink(final String name) {

        return new Link<Void>(name) {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick() {
//                strMdl.setObject("Username: "+usernameField.getModelObject()+" password: "+passwordField.getModelObject());
//                homePage.add(errorLabel);
                // errorLabel = new Label("labelError","user "+usernameField.getValue());
                //errorLabel
                //setResponsePage(new RegisterUser());
                LOGGER.info("signout clicked ");
                getSession().invalidateNow();
                setResponsePage(HomePage.class);
            }
        };
    }


}
