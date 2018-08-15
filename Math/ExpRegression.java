/*
Source https://github.com/drewdepriest/RegressionTesting/blob/master/src/Regression.java
 */

package Math;

public class ExpRegression {
    private Double b = 0.00;
    private Double a = 0.00;
    private Double r = 0.00;

    public ExpRegression(double x[], double y[]){
        // N = size of data set
        int N = y.length;

        // constant e:
        Double e = Math.E;

		/* sample data & equations
		 * exponential sourced from: http://www.matrixlab-examples.com/exponential-regression.html
		 * f(x) = ae^(bx) for exponential
		 * f(x) = a + bx for linear
		 */

		/* Find sum of squares:
		 * Calculate sum(X) & sum(X^2) & sum(Y) & sum(Y^2) & sum(X*Y)
		 * NOTE: Y denotes log(Y) for each case when calculating exponential regression
		 */
        Double sumX = 0.00;
        Double sumX2 = 0.00;
        Double sumY = 0.00;
        Double sumYlin = 0.00;
        Double sumY2 = 0.00;
        Double sumY2lin = 0.00;
        Double sumXY = 0.00;
        Double sumXYlin = 0.00;

        for(int i=0;i<N;i++)
        {
            sumX = sumX + x[i];
            sumX2 = sumX2 + Math.pow(x[i], 2);

            // exponential
            sumY = sumY + Math.log(y[i]);
            sumY2 = sumY2 + Math.pow(Math.log(y[i]), 2);
            sumXY = sumXY + (x[i]*(Math.log(y[i])));

        }

		/* Calculate regression coefficient 'b' */
        // exponential

        b = ((N*sumXY) - (sumX*sumY))/(N*sumX2 - (sumX*sumX));

		/* Calculate regression coefficient 'a' */
        // exponential

        a = Math.pow(e, (sumY - (b*sumX))/N);

		/* Calculate coefficient of determination (R^2) */
        // exponential
        Double c = 0.00;	// numerator
        Double d = 0.00;	// denominator
        c = (b)*(sumXY - sumX*sumY/N);
        d = sumY2 - (sumY*sumY)/N;
        r = c/d;

        // Calculate coefficient of correlation
        // exponential
        Double p = 0.00;
        if(r > 0){
            p = Math.sqrt(r);
        } else {
            p = 0.00;
        }

		/* Calculate standard error
		 * equals (total variance - y variance)/(n-2)
		 */
        // exponential
        Double std_err = 0.00;
        std_err = Math.sqrt((d-c)/(N-2));


    }

    public String getFunction(){
        double bb = Math.pow(Math.E, b);
        return String.format("%.04f*%.04f^x", a, bb);
    }

    public double getR(){
        return r;
    }
}
