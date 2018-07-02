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

import FileIO.SaveSettings;
import functions.FunctionStore;

import javax.swing.*;

public class DeleteFunction {
    public static void showDeleteWindow(final SaveSettings save){
        if(FunctionStore.getStore().getRawFunctions().size() == 0){
            JOptionPane.showMessageDialog(null, "No functions to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String name = JOptionPane.showInputDialog(null, functionStoreToString(), "Functions", JOptionPane.INFORMATION_MESSAGE);

        if(name == null)return;

        FunctionStore.getStore().removeFunction(name);
        save.update();
    }

    private static String functionStoreToString(){
        String functions = "";
        functions += "Functions names:\n";
        for(int i = 0; i < FunctionStore.getStore().getRawFunctions().size(); i++){
            functions += (i+1) + ". " + FunctionStore.getStore().getRawFunctions().get(i) + "\n";
        }

        return functions;
    }
}
