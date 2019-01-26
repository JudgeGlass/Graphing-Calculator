package UI;

import Program.ErrorCodes;

import java.util.ArrayList;

public class ListManager {
    public enum Lists{
        L1,
        L2,
        L3
    }

    private ArrayList<Double> _L1;
    private ArrayList<Double> _L2;
    private ArrayList<Double> _L3;

    public ListManager(){
        _L1 = new ArrayList<>();
        _L2 = new ArrayList<>();
        _L3 = new ArrayList<>();
    }

    public void addToL1(final double value){
        if(_L1 != null){
            _L1.add(value);
        }
    }

    public void addToL2(final double value){
        if(_L2 != null){
            _L2.add(value);
        }
    }

    public void addToL3(final double value){
        if(_L3 != null){
            _L3.add(value);
        }
    }

    public void clearList(final Lists list){
        switch (list){
            case L1:
                _L1.clear();
                break;
            case L2:
                _L2.clear();
                break;
            case L3:
                _L3.clear();
                break;
            default:
                ErrorCodes.printErrorCode(ErrorCodes.INVALID_DATA, "\"" + list + "\" not found");
        }
    }

    public int stringToList(final String data, final Lists list){
        String[] numbers = data.split(",");

        for(final String strNumber: numbers){
            double number;
            try {
                number = Double.parseDouble(strNumber);
            }catch (Exception e){
                e.printStackTrace();
                ErrorCodes.errorDialog(ErrorCodes.READ_ERROR, "Could not read: " + strNumber);
                return -1;
            }

            switch (list){
                case L1:
                    _L1.add(number);
                    break;
                case L2:
                    _L2.add(number);
                    break;
                case L3:
                    _L3.add(number);
                default:
                    ErrorCodes.errorDialog(ErrorCodes.INVALID_DATA, "The current list does not exist: " + list);
                    return -1;
            }
        }

        return 0;
    }

}
