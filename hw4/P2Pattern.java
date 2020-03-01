/** P2Pattern class.
 *  @author Josh Hug & Vivant Sakore
 */

public class P2Pattern {
    /** Pattern to match a valid date of the form MM/DD/YYYY. Eg: 9/22/2019 */
    public static String p1 =
            "(0{0,1}\\d|1[0-2])/(\\d|[0-2]\\d|3[01])/[0-9]{4}";

    /** Pattern to match 61b notation for literal IntLists. */
    public static String p2 =
            "\\((\\d+(?:[ \\t]*,[ \\t]*\\d+)+)\\)";

    /** Pattern to match a valid domain name. Eg: www.support.facebook-login.com */
    public static String p3 =
            "(([a-zA-Z\\d]{1,2}\\.)|([a-zA-Z\\d][a-zA-Z\\d-]"
                    + "+[a-zA-Z\\d]\\.))+[a-zA-Z]{2,6}";

    /** Pattern to match a valid java variable name. Eg: _child13$ */
    public static String p4 = "^[a-zA-Z_$][a-zA-Z_$0-9]*";

    /** Pattern to match a valid IPv4 address. Eg: 127.0.0.1 */
    public static String p5 = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";

    public static String getP1() {
        return p1;
    }

    public static String getP2() {
        return p2;
    }

    public static String getP3() {
        return p3;
    }

    public static String getP4() {
        return p4;
    }

    public static String getP5() {
        return p5;
    }
}
