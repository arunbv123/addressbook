package com.arun.contacts;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ILinkListener;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * Created by Arun on 3/2/2017.
 */
public class ContactLink extends Panel {


    public ContactLink(String id,String linkName,
                       ILinkListener linkListener) {
        super(id);
        setRenderBodyOnly(true);
        add(generateLink(linkListener, linkName));
    }

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
