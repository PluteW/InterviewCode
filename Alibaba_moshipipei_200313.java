package Interview.InterViewSolved;

import java.util.HashMap;

public class Alibaba_moshipipei_200313 {
    public static void main(String[] args) {
        String pattern = "abba";
        String str = "北京 北京 杭州 北京";
        String[] strs = str.trim().split(" ");
        if (strs.length != pattern.length()){
            System.out.println("模式串不匹配!");
            return;
        }
        HashMap<Character,String> map = new HashMap<>();
        for (int i= 0;i<strs.length;i++){
            if (!map.containsKey(pattern.charAt(i))){
                map.put(pattern.charAt(i),strs[i]);
            }
            String s = map.get(pattern.charAt(i));
            if (!s.equals(strs[i])){
                System.out.println("模式串不匹配!");
                return;
            }
        }
        System.out.println("模式串匹配！");
        return;
    }
}
