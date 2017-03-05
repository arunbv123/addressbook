package com.arun.contacts;

import com.arun.HomePage;
import com.arun.ValidateString;
import com.arun.hibernate.HibernateManager;
import com.arun.model.ContactDataAccess;
import com.arun.model.HbContactsEntity;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ImageButton;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ILinkListener;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.jboss.logging.Logger;


import java.util.List;


/**
 * The page that renders address book page with contacts listed.
 * Created by Arun on 3/1/2017.
 */
public class ContactPage extends WebPage {

    private static final Logger LOGGER = Logger.getLogger(ContactPage.class);

    /**
     * The reference for the images
     */
    private PackageResourceReference editRef = new PackageResourceReference(ContactPage.class, "edit.png");
    private PackageResourceReference deleteRef = new PackageResourceReference(ContactPage.class, "delete.png");
    private PackageResourceReference saveRef = new PackageResourceReference(ContactPage.class, "save.png");
    private PackageResourceReference addRef = new PackageResourceReference(ContactPage.class, "add.png");
    private PackageResourceReference signoutRef = new PackageResourceReference(ContactPage.class, "signout_new.jpg");

    /**
     * Models for the components in webpage used to dynamically update the component elements example: update text for TextFields and Update images in ImageButton
     */
    private Model<String> nameModel = Model.of(" ");
    private Model<String> addressModel = Model.of(" ");
    private Model<String> phoneModel = Model.of(" ");
    private Model<String> emailModel = Model.of(" ");
    private Model<String> nameHeadingModel = Model.of(" ");
    private Model<String> contactErrorModel = Model.of("Error");
    private Model editModel = Model.of(editRef);
    private Model deleteModel = Model.of(deleteRef);
    private Model addModel = Model.of(addRef);
    private Model signoutModel = Model.of(signoutRef);

    /**
     * Components of the webpage
     */
    private TextField<String> nameField;
    private TextField<String> phoneField;
    private TextField<String> emailField;
    private TextArea<String> addressField;
    private Label nameHeadingField;
    private Label contactError;

    private ImageButton editImage;
    private ImageButton signoutImage;
    private ImageButton deleteImage;
    private ImageButton addImage;

    private ContactDetailsForm contactDetailsForm;


    /**
     * Flags to keep track of the page status
     */

    boolean contactFilled = false;
    boolean save = false;

    /**
     * User id and contact id to manipulate database when required
     */
    int userId = -1;
    int showContactID = -1;


    public ContactPage(int userid) {

        userId = userid;
        contactDetailsForm = new ContactDetailsForm("contactDetails");
        add(contactDetailsForm);
        add(new AddressNameListForm("addressNameList", userid));

        Link<Void> signoutLink = signoutLink("signOutLink");
        signoutImage = new SignOutImageButton("signoutImg", signoutModel);

        signoutImage.setDefaultFormProcessing(false);

        signoutLink.add(signoutImage);
        add(signoutLink);

    }

    /**
     * Form that list all the contacts in the AddressBook for the signed in user
     */
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


            } else {
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

    /**
     * Form that displays the Contact details of the contact selected
     */
    public final class ContactDetailsForm extends Form<Void> {


        public ContactDetailsForm(String id) {
            super(id);

            nameHeadingField = new Label("contactNameHeading", nameHeadingModel);
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

            contactError = new Label("contactError", contactErrorModel);
            contactError.setVisible(false);
            add(contactError);

            editImage = new EditImageButton("editImg", editModel);
            //  add(editLink("editLink").add(editImage));
            editImage.setDefaultFormProcessing(false);

            add(editImage);
            //editImage.add(editLink("edit"));

            deleteImage = new DeleteImageButton("deleteImg", deleteModel);
            deleteImage.setDefaultFormProcessing(false);
            add(deleteImage);
        }

        @Override
        public final void onSubmit() {
            LOGGER.info("delete clicked");

        }
    }

    /**
     * Edit Image button class overrides onSubmit() to perform action when the button is clicked
     */
    public class EditImageButton extends ImageButton {

        public EditImageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        public void onSubmit() {
            LOGGER.info("edit clicked ");
            if (!save) {
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
            } else {

                String name = nameField.getInput();
                String email = emailField.getInput();
                String phone = phoneField.getInput();
                String address = addressField.getInput();

                LOGGER.info("contactId: " + showContactID + " userID: " + userId + " name: " + nameField.getInput() + " email: " + email + " phone: " + phone + " address: " + address);
                if (name == null || name.equals("")) {
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please fill all the fields");

                    return;
                }
                if (email == null || email.equals("")) {
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please fill all the fields");
                    return;
                }
                if (phone == null || phone.equals("")) {
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please fill all the fields");
                    return;
                }
                if (address == null || address.equals("")) {
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please fill all the fields");
                    return;
                }

                if (!ValidateString.emailValidate(email)) {
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Please enter valid email");
                    return;
                }

                if (!ValidateString.phoneValidate(phone)) {
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

                if (!status) {
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



        }
    }

    /**
     * Delete Image button class overrides onSubmit() to perform action when the button is clicked
     */
    public class DeleteImageButton extends ImageButton {

        public DeleteImageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        public void onSubmit() {
            LOGGER.info("delete clicked ");
            if (showContactID == -1) {
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
            } else {
                boolean status = HibernateManager.deleteContact(showContactID);
                if (status) {
                    setResponsePage(new ContactPage(userId));
                } else {
                    contactError.setVisible(true);
                    contactErrorModel.setObject("Something went wrong please try again");
                    return;
                }
            }

        }
    }

    /**
     * Add Image button class overrides onSubmit() to perform action when the button is clicked
     */
    public class AddImageButton extends ImageButton {

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

    public class SignOutImageButton extends ImageButton {

        public SignOutImageButton(String id, IModel<String> model) {
            super(id, model);
        }

        @Override
        public void onSubmit() {

        }
    }


    /**
     * Class that implements ILinkListener to override onLickClicked() so that the contact display action can be performed
     */
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
            LOGGER.info("Contact Clicked: " + contactName);
            nameHeadingModel.setObject("" + contactName);
            contactDetailsForm.replace(nameHeadingField);

            nameModel.setObject("" + contactName);
            nameField.setEnabled(false);
            contactDetailsForm.replace(nameField);
            addressModel.setObject("" + contactAddress);
            addressField.setEnabled(false);
            contactDetailsForm.add(addressField);
            phoneModel.setObject("" + contactPh);
            phoneField.setEnabled(false);
            contactDetailsForm.add(phoneField);
            emailModel.setObject("" + contactEmail);
            emailField.setEnabled(false);
            contactDetailsForm.add(emailField);
            editModel.setObject(editRef);
            contactDetailsForm.add(editImage);
            contactFilled = true;
            showContactID = contactId;
        }
    }

    /**
     * Signout function
     * @param name - name of the component referenced by wicket:id in html
     * @return - link equivaled of <a></a> tag in HTML
     */

    public Link<Void> signoutLink(final String name) {

        return new Link<Void>(name) {
            /**
             * @see org.apache.wicket.markup.html.link.Link#onClick()
             */
            @Override
            public void onClick() {

                LOGGER.info("signout clicked ");
                getSession().invalidateNow();
                setResponsePage(HomePage.class);
            }
        };
    }


}
