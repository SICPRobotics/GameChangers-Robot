package frc.robot;

import java.util.ArrayList;

public class WillowMath { 
    /**
     * 
     * @param v Initial velocity
     * @param x x coordinate of target
     * @param y y coordinate of target
     * @return returns required launch angle between 0 and 90 returns -1 if trajectory is impossible 
     */
    public static double findAngle(double v, double x, double y){
       for(int i = 0; i <= 90; i++){
            if(y == findHeight(v, x, i)){
                return i;
            }
       }
       return -1;
    }
    /**
     * 
     * @param v Initial velocity 
     * @param x x coordinate of target
     * @param theta launch angle
     * @return Height of trajectory at x coordinate
     */
    public static double findHeight(double v, double x, double theta){
        return (Math.pow(x, 2) / (2 * 9.8 * Math.cos(theta) * Math.pow(v, 2))) + Math.tan(theta);
    }
        /**
     * 
     * @param v Initial velocity
     * @param x x coordinate of target
     * @param y y coordinate of target
     * @param a effect of air
     * @return returns required launch angle between 0 and 90 returns -1 if trajectory is impossible 
     */
    public static double findAngleWithAir(double v, double x, double y, double a){
        for(double i = 0; i <= 90; i++){
             if(y == findHeightWithAir(v, x, i, a)){
                 return i;
             }
        }
        return -1;
     }
     /**
      * 
      * @param v Initial velocity 
      * @param x x coordinate of target
      * @param theta launch angle
      * @param a effect of air 
      * @return Height of trajectory at x coordinate
      */
    public static double findHeightWithAir(double v, double x, double theta, double a){
        return (Math.pow(x, 2) / (2 * 9.8 * Math.cos(theta) * Math.pow(v, 2))) + Math.tan(theta) - (Math.pow(v, 2) * a);
    }
    /**
     * 
     * @param v Initial velocity
     * @param x x coordinate of end location 
     * @param y y coordinate of end location 
     * @param theta angle at which the ball was launched 
     * @return constant a that corrects for the preense of air, not at all exact, but hoplefully good enough to to work
     */
    public static double findA(double v, double x, double y, double theta){
        double a = 0;
        for(double i = 0; i <= 1; i = i + 0.01){
            if(Math.abs(findAngleWithAir(v, x, y, i) - theta) > Math.abs(findAngleWithAir(v, x, y, a) - theta)){
                System.out.println(a);
                return a;
            }
            if(Math.abs(findAngleWithAir(v, x, y, i) - theta) < Math.abs(findAngleWithAir(v, x, y, a) - theta)){
                a = i;
            }
        }
        return a;
    }
    /**
     * 
     * @param list list of data
     * @return sum of every number in the data
     */
    public static double arraySum(ArrayList<Double> list){
        double sum = 0;
        for(int i = 0; i < list.size()-1; i++){
            sum = sum + list.get(i);
        }
        return sum;
    }
        /**
     * 
     * @param list list of data
     * @return sum of every number in the data
     */
    public static double sum(double[] list){
        double sum = 0;
        for(int i = 0; i < list.length; i++){
            sum = sum + list[i];
        }
        return sum;
    }
        /**
     * 
     * @param list list of data
     * @return sum of each number squared in the data
     */
    public static double arraySumPow(ArrayList<Double> list, double pow){
        double sum = 0;
        for(int i = 0; i < list.size()-1; i++){
            sum = sum + Math.pow(list.get(i), pow);
        }
        return sum;
    }
        /**
     * 
     * @param list list of data
     * @return sum of the each number squared in the data
     */
    public static double sumPow(double[] list, double pow){
        double sum = 0;
        for(int i = 0; i < list.length; i++){
            sum = sum + Math.pow(list[i], pow);
        }
        return sum;
    }
    /**
     * 
     * @param list list of data
     * @param list2 list of data
     * @return sum of the product of each of the coresponding entry in list and list2
     */
    public static double arraySumProduct(ArrayList<Double> list, ArrayList<Double> list2){
        double sum = 0;
        for(int i = 0; i < list.size()-1; i++){
            sum = sum + (list.get(i) * list2.get(i));
        }
        return sum;
    }
    /**
     * 
     * @param list list of data
     * @param list2 list of data
     * @return sum of the product of each coresponding entry in list and list2 
     */
    public static double sumProduct(double[] list, double[] list2){
        double sum = 0;
        for(int i = 0; i < list.length; i++){
            sum = sum + (list[i] * list2[i]);
        }
        return sum;
    }
    public static double sumPowProduct(double[] list, double[] list2, double pow){
        double sum = 0;
        for(int i = 0; i < list.length; i++){
            sum = sum + (Math.pow(list[i], pow) * list2[i]);
        }
        return sum;
    }
    /**
     * 
     * @param list list of the x values in the data
     * @param list2 list of the y values in the data 
     * @return slope of the line of best fit
     */
    public static double slopeOfBestFit(double[] list, double[] list2){
        return ((list.length * sumProduct(list, list2)) - (sum(list) * sum(list2))) / ((list.length * sumPow(list, 2)) - Math.pow(sum(list), 2)); 
    }
    /**
     * 
     * @param list list of the x values in the data
     * @param list2 list of the y values in the data
     * @param slope sploe of the line of best fit
     * @return y intercept of the line of best fit
     */
    public static double interceptOfBestFit(double[] list, double[] list2, double slope){
        return (sum(list2) - (slope * sum(list)) / list.length);
    }
    /**
     * 
     * @param list list of x values in the data
     * @param list2 list of y values in the data
     * @return slope of the line of best fit for the data
     */ 
    public static double slopeOfBestFit(ArrayList<Double> list, ArrayList<Double> list2){
        return ((list.size() * arraySumProduct(list, list2)) - (arraySum(list) * arraySum(list2)))
                / ((list.size() * arraySumPow(list, 2)) - Math.pow(arraySum(list), 2));
    }
    /**
     * 
     * @param list list of x vales in the data 
     * @param list2 list of y values in the data
     * @param slope slope of line of best fit 
     * @return y intercept of the line of best fit for the data
     */
    public static double interceptOfBestFit(ArrayList<Double> list, ArrayList<Double> list2, double slope){
        return (arraySum(list2) - (slope * arraySum(list)) / list.size());
    }
    public static double aOfBestFit(double[] list, double[] list2){
        return
         (((((square(sum(list)) * sumPowProduct(list, list2, 2)) - 
         (list.length * sumPow(list, 2) * sumPowProduct(list, list2, 2))) -
         (sum(list) * sumPow(list, 2) * sumProduct(list, list2))) + 
         (list.length * sumPow(list, 3) * sumProduct(list, list2)) + 
         (square(sumPow(list, 2)) * sum(list2))) - 
         (sum(list) * sumPow(list, 3) * sum(list2))) 
         / curveOfBestFitDivideBy(list, list2);
    }
    public static double bOfBestFit(double[] list, double[] list2){
        return 
         (((((list.length * sumPowProduct(list, list2, 2) * sumPow(list, 3)) - 
         (sum(list) * sumPow(list, 2) * sumPowProduct(list, list2, 2))) + 
         (square(sumPow(list, 2)) * sumProduct(list, list2)) + 
         (sum(list) * sumPow(list, 4) * sum(list2))) - 
         (list.length * sumPow(list, 4) * sumProduct(list, list2))) - 
         (sumPow(list, 2) * sumPow(list, 3) * sum(list2)))
         / curveOfBestFitDivideBy(list, list2);
    }
    public static double cOfBestFit(double[] list, double[] list2){
        return 
         (((((square(sumPow(list, 2)) * sumPowProduct(list, list2, 2)) - 
         (sum(list) * sumPowProduct(list, list2, 2) * sumPow(list, 3))) + 
         (square(sumPow(list, 3)) * sum(list2))) - 
         (sumPow(list, 2) * sumPow(list, 4) * sum(list2))) - 
         (sumPow(list, 2) * sumPow(list, 3) * sumProduct(list, list2))) 
         / curveOfBestFitDivideBy(list, list2);
    }
    public static double square(double d){
        return Math.pow(d, 2);
    }
    public static double cube(double d){
        return Math.pow(d, 3);
    }
    private static double curveOfBestFitDivideBy(double[] list, double[] list2){
        return
        ((((cube(sumPow(list, 2))) - 
        (2 * sum(list) * sumPow(list, 2) * sumPow(list, 3))) + 
        (list.length * square(sumPow(list, 3))) + 
        (square(sum(list)) * sumPow(list, 4))) - 
        (list.length * sumPow(list, 2) * sumPow(list, 4)));
    }
    public static double[] derivative(double[] poly){
        double derivative[] = new double[poly.length-1];
        for(int i=0;i<derivative.length;i++){
        derivative[i] = poly[i]*(poly.length - 1 -i );
        }
        return derivative;
    }
}
