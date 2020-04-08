/** CS 61B Homework 1:
 * @author Aniruddh Khanwale
 * Includes method that calculates the maximum value in an array, as well as 3SUM.
 */
public class hw0{
    public static void main(String[] args) {
        int[] one = new int[]{0, 1, 2 ,3};
        int[] two = new int[]{-6, 80, -7, -8};
        int[] three = new int[]{-2,-1,-3,-4};
        System.out.println(max(one));
        System.out.println(max(two));
        System.out.println(max(three));
        System.out.println(threeSum(new int[]{-6, 2, 4}));
        System.out.println(threeSum(new int[]{-6, 2, 5}));
        System.out.println(threeSum(new int[]{-6, 3, 10, 200}));
        System.out.println(threeSum(new int[]{8, 2, -1, 15}));
        System.out.println(threeSum(new int[]{8, 2, -1, -1, 15}));
        System.out.println(threeSum(new int[]{5, 1, 0, 3, 6}));

    }
    public static int max(int[] a){
        int max_val = Integer.MIN_VALUE;
        for(int i = 0; i < a.length; i+=1){
            if(a[i] > max_val){
                max_val = a[i];
            }
        }
        return max_val;
    }
    public static boolean threeSum(int[] a){
        for(int i = 0; i < a.length; i+= 1){
            for(int j = 0; j < a.length; j+= 1){
                for(int k = 0; k < a.length; k+= 1){
                    if ((a[k] == 0) || (a[i]+a[j]+a[k] == 0)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean threeSumDistinct(int[] a){
        for(int i = 0; i < a.length; i+= 1){
            for(int j = i+1; j < a.length; j+= 1){
                for(int k = j+1; k < a.length; k+= 1){
                    if(a[i]+a[j]+a[k] == 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}