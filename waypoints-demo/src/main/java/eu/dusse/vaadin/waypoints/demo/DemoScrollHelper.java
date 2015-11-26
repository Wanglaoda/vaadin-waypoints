package eu.dusse.vaadin.waypoints.demo;

import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import eu.dusse.vaadin.waypoints.ScrollHelper;

public class DemoScrollHelper extends VerticalLayout implements View
{

    public DemoScrollHelper()
    {
        setSpacing(true);
    }

    @Override
    public void enter(ViewChangeEvent event)
    {
        createExample1();
    }

    private void createExample1()
    {
        CssLayout spacerTop = new CssLayout();
        spacerTop.setHeight(200, Unit.PIXELS);

        CssLayout spacerLeft = new CssLayout();
        spacerLeft.setWidth(400, Unit.PIXELS);

        final Button target = ButtonFactory.createButton();

        CssLayout spacerRight = new CssLayout();
        spacerRight.setWidth(2000, Unit.PIXELS);

        HorizontalLayout targetWrapper = new HorizontalLayout();
        targetWrapper.addComponents(spacerLeft, target, spacerRight);
        targetWrapper.setSpacing(true);

        CssLayout spacerBottom = new CssLayout();
        spacerBottom.setHeight(2000, Unit.PIXELS);

        VerticalLayout content = new VerticalLayout();
        content.addComponents(spacerTop, targetWrapper, spacerBottom);
        content.setSizeUndefined();

        final Panel contentPanel = new Panel();
        contentPanel.setCaption("Panel");
        contentPanel.setContent(content);
        contentPanel.setWidth(800, Unit.PIXELS);
        contentPanel.setHeight(640, Unit.PIXELS);

        final TextField offsetField = new TextField();
        offsetField.setCaption("Offset");
        offsetField.setConverter(new StringToIntegerConverter());
        offsetField.setConvertedValue(0);
        offsetField.setRequired(true);

        final TextField speedField = new TextField();
        speedField.setCaption("Speed");
        speedField.setConverter(new StringToIntegerConverter());
        speedField.setConvertedValue(400);
        speedField.setRequired(true);
        speedField.addValidator(new IntegerRangeValidator("Out of bounds!", 0, 10000));

        final CheckBox horizontalField = new CheckBox();
        horizontalField.setCaption("Horizontal");
        horizontalField.setValue(false);

        Button scrollToButton = new Button();
        scrollToButton.setCaption("Scroll to");
        scrollToButton.addClickListener(new ClickListener()
        {
            @Override
            public void buttonClick(ClickEvent event)
            {
                if (offsetField.isValid() && speedField.isValid())
                {
                    AbstractComponent context = contentPanel;
                    boolean horizontal = horizontalField.getValue();
                    int offset = (Integer) offsetField.getConvertedValue();
                    int speed = (Integer) speedField.getConvertedValue();
                    ScrollHelper.getCurrent().scrollTo(target, context, horizontal, offset, speed);
                }
            }
        });

        HorizontalLayout actionLayout = new HorizontalLayout();
        actionLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        actionLayout.addComponents(offsetField, speedField, horizontalField, scrollToButton);
        actionLayout.setSpacing(true);

        addComponents(actionLayout, contentPanel);
    }

}
