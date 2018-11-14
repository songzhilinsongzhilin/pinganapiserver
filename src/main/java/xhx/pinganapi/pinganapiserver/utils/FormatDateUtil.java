package xhx.pinganapi.pinganapiserver.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatDateUtil {
    /**
     * @author johnny
     * 通用字符串转换提取年月日
     * @param s$
     * **/
    public static String isConversionDate(String s$,String end,String too){
        try {
            String pattern=null;
            SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
            if(s$==null||s$==""){
                return null;
            }
            if(s$.contains("-") && s$.indexOf("-")>0){
                if(s$.length() == 10){
                    return s$;
                }else if(s$.length() >= 8 && s$.length()<10){
                    pattern = "yyyy-M-d";
                }else{
                    pattern = "yyyy-MM-dd";
                    s$=s$+"-"+too;
                }
            }else if(s$.contains("/")){
                if(s$.length() == 10){
                    pattern = "yyyy/MM/dd";
                }else if(s$.length() >= 8){
                    pattern = "yyyy/M/dd";
                }else{
                    pattern = "yyyy/MM/dd";
                    s$=s$+"/"+too;
                }
            }else{
                if(s$.matches("[0-9]*")){
                    if(s$.length()>8) {
                        Date date = new Date();
                        date.setTime(Long.valueOf(s$));
                        s$ = simple.format(date);
                        return s$;
                    }else {
                        if(s$.length() == 8){
                            pattern = "yyyyMMdd";
                        }else if(s$.length() == 6){
                            pattern = "yyyyMMdd";
                            s$+=too;
                        }else if(s$.length()==4){
                            s$+="-"+end+"-"+too;
                            return s$;
                        }
                    }
                }else {
                    if(s$.indexOf("-")==0){
                        Date date = new Date();
                        date.setTime(Long.valueOf(s$));
                        s$ = simple.format(date);
                        return s$;
                    }
                    String patternT = "EEE MMM dd HH:mm:ss zzz yyyy";
                    SimpleDateFormat df = new SimpleDateFormat(patternT, Locale.US);
                    s$=simple.format(df.parse(s$));
                    return s$;
                }
            }
            if(pattern!=null){
                SimpleDateFormat df = new SimpleDateFormat(pattern,Locale.US);
                s$=simple.format(df.parse(s$));
            }
        }catch (ParseException e1) {
            e1.printStackTrace();
        }
        return s$;
    }
}
