package Math;

public class RootSimp {
    public static String simplify(final int num){
        int squares[] = new int[31];
        for(int i = 0; i < squares.length; i++){
            squares[i] = i*i;
        }

        int value1 = 0;
        int value2 = 0;

        for(int i: squares){
            if(i == 0) continue;

            if(num % i == 0){
                value1 = (int)Math.sqrt(i);
                value2 = num / i;
            }
        }

        return value1 + " * sqrt(" + value2 + ")";
    }
}
