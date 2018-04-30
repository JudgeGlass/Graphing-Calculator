/*
 * Copyright 2018 Hunter Wilcox
 * 
 * This file is part of GraphingCalculator.
 *
 * GraphingCalculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GraphingCalculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GraphingCalculator.  If not, see <http://www.gnu.org/licenses/>.
 */

package UI;

import Math.QuadraticFormula;
import Program.CorrectFunction;
import functions.Function;
import functions.FunctionArguments;
import functions.TokenizedFunctionFactory;

import javax.swing.*;

public class QuadraticFormulaDialog {
    private double x1;
    private double x2;

    private double a = 0;
    private double b = 0;
    private double c = 0;

    QuadraticFormulaDialog(){
        JTextField aField = new JTextField(5);
        JTextField bField = new JTextField(5);
        JTextField cField = new JTextField(5);


        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("a:"));
        myPanel.add(aField);

        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("b:"));
        myPanel.add(bField);

        myPanel.add(Box.createHorizontalStrut(15));
        myPanel.add(new JLabel("c:"));
        myPanel.add(cField);

        ImageIcon image = new ImageIcon(getClass().getResource("/Program/QF.png"));
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Quadratic Formula", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, image);
        if (result == JOptionPane.OK_OPTION) {
            a = Double.parseDouble(aField.getText());
            b = Double.parseDouble(bField.getText());
            c = Double.parseDouble(cField.getText());

            x1 = QuadraticFormula.plus(Double.parseDouble(aField.getText()), Double.parseDouble(bField.getText()), Double.parseDouble(cField.getText()));
            x2 = QuadraticFormula.minus(Double.parseDouble(aField.getText()), Double.parseDouble(bField.getText()), Double.parseDouble(cField.getText()));
        }
    }

    public String getZeros(){
        return String.format("x={%.03f,%.03f}", x1, x2);
    }
    public String getLineOfSem(){return String.format("Middle: x=%.04f", -b/(2*a));}
    public String getMinMax(){
        double lineOfSem = -b/(2*a);

        if(lineOfSem == Double.NaN)
            return "Error";

        String function = String.format("%.04fx^2%s%.04fx%s%.04f", a, (b > 0) ? "+" : "", b, (c > 0) ? "+" : "", c);

        function = function.replaceAll("x", "*(" + lineOfSem + ")");

        Function f = TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(function), null);
        double ans = f.evaluate(new FunctionArguments(null));

        if(a > 0){
            return String.format("Minimum: y=%.04f", ans);
        }
        return String.format("Maximum: y=%.04f", ans);
    }
}
