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

import javax.inject.Inject;

/**
 * info.magnolia.training.calculator.
 */
public class Processor implements Calculator.Listener {

    private Calculator view;
    private String displayValue = "0";
    private Function function = null;
    private int register = 0;

    private enum Function { EQUALS, CLEAR, ADD, SUBTRACT, MULTIPLY, DIVIDE }

    public String[][] getCaptions() {
        return new String[][]
                {{ "7", "8", "9", "+" },
                        { "4", "5", "6", "-" },
                        { "3", "2", "1", "x" },
                        { "c", "0", "=", "/" }};
    }

    @Inject
    public Processor(Calculator view) {
        this.view = view;
    }

    public Calculator start() {
        view.setListener(this);
        view.build();
        return view;
    }

    @Override
    public void keyPressed(String key) {

        // Numeric key pressed
        if (Character.isDigit(key.charAt(0)))
            this.displayValue = "0".equals(this.displayValue) ? key : this.displayValue + key;

        else // Function key pressed
            switch (this.getFunction(key)) {
            case EQUALS:
                this.displayValue = Integer.toString(this.calculate());
                this.register = 0;
                this.function = null;
                break;
            case CLEAR:
                this.displayValue = "0";
                this.register = 0;
                this.function = null;
                break;
            default:
                this.register = Integer.parseInt(this.displayValue);
                this.displayValue = "0";
                this.function = this.getFunction(key);
                return; // don't update display
            }

        view.updateDisplay(this.displayValue);
    }

    protected final Calculator getView() {
        return this.view;
    }

    protected final String getDisplayValue() {
        return this.displayValue;
    }

    private Function getFunction(String key) {
        switch (key.charAt(0)) {
        case '=': return Function.EQUALS;
        case 'c': return Function.CLEAR;
        case '+': return Function.ADD;
        case '-': return Function.SUBTRACT;
        case 'x': return Function.MULTIPLY;
        case '/': return Function.DIVIDE;
        default : return null;
        }
    }

    private int calculate() {
        switch (this.function) {
        case ADD:      this.register += Integer.parseInt(this.displayValue); break;
        case SUBTRACT: this.register -= Integer.parseInt(this.displayValue); break;
        case MULTIPLY: this.register *= Integer.parseInt(this.displayValue); break;
        case DIVIDE:   this.register /= Integer.parseInt(this.displayValue); break;
        default:                                                             break;
        }

        return this.register;
    }
}
