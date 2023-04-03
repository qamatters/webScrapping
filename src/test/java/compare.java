import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;

public class compare {
    public static void main(String[] args) {
//        String a = "19.0";
//        String b = "19";
//        String valueAfterPoint = StringUtils.substringAfter(a, ".");
//        System.out.println("value after point is :" + valueAfterPoint);
//        System.out.println("a value is :" + a);
//        if(!valueAfterPoint.isEmpty()) {
//            int value = Integer.parseInt(valueAfterPoint);
//            if(value == 0){
//                a = StringUtils.substringBefore(a, ".");
//            }
//        }
//
//        System.out.println("now value is :" + a);
//
//        ArrayList<String> first = new ArrayList<>();
//        first.add("[1.01, 0.36, -2.22, -12.08]");
//        first.add("[0.74, 0.59, -2.3, -14.23]");
//
//        ArrayList<String> second = new ArrayList<>();
//        first.add("[1.01, 0.36, -2.22, -12.08]");
//        first.add("[0.74, 0.59, -2.3, -14.23]");
//
//        ArrayList<String> third = new ArrayList<>();
//        first.add("[1.01, 0.36, -2.22, -12.08]");
//        first.add("[0.74, 0.59, -2.3, -14.23]");
//
//       if(Collections.disjoint(first,second)) {
//           System.out.println("All values are same");
//       } else {
//           System.out.println("All values are not same");
//       }

        String url = "https://s-www.google.com";
        char[] urlChars = url.toCharArray();
        urlChars[8] = 'u';
        url = String.valueOf(urlChars);
        System.out.println(url);


//        String str = "Hello World";
//        str = str.replace((char) 5, 'a');
//        System.out.println(str);



    }
}
