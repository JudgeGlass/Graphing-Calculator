package UI;

import Program.ErrorCodes;

import java.util.ArrayList;

public class ListManager {
    public enum Lists{
        L1,
        L2,
        L3,
        ALL
    }

    private ArrayList<Double> _L1;
    private ArrayList<Double> _L2;
    private ArrayList<Double> _L3;

    public Lists xList = Lists.L1;
    public Lists yList = Lists.L2;

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

    public void removeFromList(final Lists list, final int index){
        switch (list){
            case L1:
                _L1.remove(index);
                break;
            case L2:
                _L2.remove(index);
                break;
            case L3:
                _L3.remove(index);
                break;
            case ALL:
                _L1.remove(index);
                _L2.remove(index);
                _L3.remove(index);
                break;
            default:
                ErrorCodes.printErrorCode(ErrorCodes.INVALID_DATA, "The current list does not exist: " + list);
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
            case ALL:
                _L1.clear();
                _L2.clear();
                _L3.clear();
                break;
            default:
                ErrorCodes.printErrorCode(ErrorCodes.INVALID_DATA, "\"" + list + "\" not found");
        }
    }

    public int getLargestListCount(){
        int a = _L1.size();
        int b = _L2.size();
        int c = _L3.size();

        if(a >= b && a >= c){
            return a;
        }else if(b >= a && b >= c){
            return b;
        }else if(c >= a && c >= b){
            return c;
        }

        return 0;
    }

    public int getLargestDataList(){
        if(getXList().size() >= getYList().size()){
            return getXList().size();
        }else if(getYList().size() >= getXList().size()){
            return getYList().size();
        }

        return 0;
    }

    public ArrayList<Double> getL1(){
        return _L1;
    }

    public ArrayList<Double> getL2(){
        return _L2;
    }

    public ArrayList<Double> getL3(){
        return _L3;
    }

    public ArrayList<Double> getXList(){
        switch (xList){
            case L1:
                return _L1;
            case L2:
                return _L2;
            case L3:
                return _L3;
        }

        return null;
    }

    public ArrayList<Double> getYList(){
        switch (yList){
            case L1:
                return _L1;
            case L2:
                return _L2;
            case L3:
                return _L3;
        }

        return null;
    }

    public String toString(final Lists list){
        ArrayList<Double> data = null;
        switch (list){
            case L1:
                data = _L1;
                break;
            case L2:
                data = _L2;
                break;
            case L3:
                data = _L3;
                break;
        }

        if(data == null){
            return "";
        }

        String strData = "";
        for(int i = 0; i < data.size(); i++){
            if(i == data.size() - 1){
                strData += data.get(i);
            }else{
                strData += data.get(i) + ",";
            }
        }

        return strData;
    }

    public int stringToList(final String data, final Lists list){
        if(data.isEmpty()){
            return 0;
        }
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
                    break;
                default:
                    ErrorCodes.errorDialog(ErrorCodes.INVALID_DATA, "(stringToList) The current list does not exist: " + list);
                    return -1;
            }
        }

        return 0;
    }

}
