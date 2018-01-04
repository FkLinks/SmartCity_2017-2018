package com.henallux.smartcity;

public class Constants {
    /*DAO*/
    /*Convert a stream into a string Json*/
    public static String convertStreamToString(java.io.InputStream inputStream){
        java.util.Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext()? scanner.next():"";
    }
}
