/**
 * This file Copyright (c) 2017 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 *
 *
 * This file is dual-licensed under both the Magnolia
 * Network Agreement and the GNU General Public License.
 * You may elect to use one or the other of these licenses.
 *
 * This file is distributed in the hope that it will be
 * useful, but AS-IS and WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, or NONINFRINGEMENT.
 * Redistribution, except as permitted by whichever of the GPL
 * or MNA you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or
 * modify this file under the terms of the GNU General
 * Public License, Version 3, as published by the Free Software
 * Foundation.  You should have received a copy of the GNU
 * General Public License, Version 3 along with this program;
 * if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * 2. For the Magnolia Network Agreement (MNA), this file
 * and the accompanying materials are made available under the
 * terms of the MNA which accompanies this distribution, and
 * is available at http://www.magnolia-cms.com/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 *
 */
package info.magnolia.training.calculator;

import info.magnolia.ui.vaadin.layout.SmallAppLayout;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * info.magnolia.training.calculator.
 */
public class CalculatorImpl extends SmallAppLayout implements Calculator {

    private final TextField display = new TextField();
    private Calculator.Listener listener;

    private final static int BUTTON_SIZE = 35;

    public void build() {
        final String[][] captions = listener.getCaptions();
        VerticalLayout section = new VerticalLayout();

        Button button;

        this.display.setWidth(Integer.toString(BUTTON_SIZE * captions.length) + "px");
        this.display.setValue("0");
        section.addComponent(new HorizontalLayout(this.display));

        HorizontalLayout row;
        for (int i = 0; i < captions.length; i++) {
            row = new HorizontalLayout();
            for (int j = 0; j < captions[i].length; j++) {
                button = new Button(captions[i][j]);
                button.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        listener.keyPressed(event.getButton().getCaption());
                    }
                });
                button.setWidth(Integer.toString(BUTTON_SIZE) + "px");
                row.addComponent(button);
            }
            section.addComponent(row);
        }
        addSection(section);
    }

    @Override
    public Component asVaadinComponent() {
        return this;
    }

    @Override
    public void updateDisplay(String displayValue) {
        this.display.setValue(displayValue);
    }

    @Override
    public void setListener(Calculator.Listener listener) {
        this.listener = listener;
    }
}
