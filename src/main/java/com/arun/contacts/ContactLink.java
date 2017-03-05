package com.arun.contacts;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ILinkListener;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * Class that creates individual contact item in the contact list. This also has the link that can be accessed by overriding onClick() in ILinkListener of the constructor
 * Created by Arun on 3/2/2017.
 */
class ContactLink extends Panel {


    /**
     *
     * @param id - component id
     * @param linkName - Name of the contact
     * @param linkListener - Listener to perform action when the contact is clicked
     */
     ContactLink(String id, String linkName,
                       ILinkListener linkListener) {
        super(id);
        setRenderBodyOnly(true);
        add(generateLink(linkListener, linkName));
    }

    /**
     * Function creates th contact label and link
     * @param linkListener - contact onClick listener
     * @param linkName - contact lable name
     * @return
     */
    private Link<?> generateLink(final ILinkListener linkListener,
                                 String linkName) {
        Label linkLabel = new Label("linkLabel", linkName);
        Link<String> link = new Link<String>("link") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                linkListener.onLinkClicked();
            }
        };
        link.add(linkLabel);
        return link;
    }


}
