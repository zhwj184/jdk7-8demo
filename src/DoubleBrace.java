import java.util.HashSet;
 
public class DoubleBrace {
    public static void main(String[] args) {
 
        useParamsInSomeMethod(new HashSet<String>() {
            {
                add("param one");
                add("param two");
                add("param three");
                add("param four");
            }
        });
    }
    private static void useParamsInSomeMethod(HashSet<String> params) {
    	System.out.println(params);
    }
}