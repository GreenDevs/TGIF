package com.crackdeveloperz.tgif.utility;

/**
 * Created by adventure on 8/15/15.
 */
public class GetExtenction {
    
    public static String a = "";

    public static  boolean isValid (String mString ) {
        boolean valid =false;
        

        if (mString.contains(".jpg")) {   a = ".jpg" ;valid =true; }
        else if (mString.contains(".png")) { a = ".png";valid =true; }
        else if (mString.contains(".apk")) { a = ".apk"; valid =true;}
        else if (mString.contains(".mp4")) { a = ".mp4"; valid =true;}
        else if (mString.contains(".jpeg")) { a = ".jpeg";valid =true;}
        else if (mString.contains(".doc")) { a = ".doc"; valid =true;}
        else if (mString.contains(".docx")) { a = ".docx"; valid =true;}
        else if (mString.contains(".ppt")) { a = ".ppt"; valid =true;}
        else if (mString.contains(".pptx")) { a = ".pptx"; valid =true;}
        else if (mString.contains(".zip")) { a = ".zip"; valid =true;}
        else if (mString.contains(".tar.gz")) { a = ".tar.gz"; valid =true;}
        else if (mString.contains(".tar")) { a = ".tar"; valid =true;}
        else if (mString.contains(".mp3")) { a = ".mp3"; valid =true;}
        else if (mString.contains(".mp4")) { a = ".mp4"; valid =true;}
        else if (mString.contains(".mkv")) { a = ".mkv"; valid =true;}
        else if (mString.contains(".flv")) { a = ".flv"; valid =true;}
        else if (mString.contains(".m4a")) { a = ".m4a"; valid =true;}
        else if (mString.contains(".pdf")) { a = ".pdf"; valid =true;}
        else if (mString.contains(".csv")) { a = ".csv"; valid =true;}
        else if (mString.contains(".xml")) { a = ".xml"; valid =true;}
        else if (mString.contains(".3gp")) { a = ".3gp"; valid =true;}
        else if (mString.contains(".avi")) { a = ".avi"; valid =true;}
        else if (mString.contains(".m4v")) { a = ".m4v"; valid =true;}
        else if (mString.contains(".mov")) { a = ".mov"; valid =true;}
        else if (mString.contains(".gif")) { a = ".gif"; valid =true;}
        else if (mString.contains(".psd")) { a = ".psd"; valid =true;}
        else if (mString.contains(".svg")) { a = ".svg"; valid =true;}
        else if (mString.contains(".xls")) { a = ".xls"; valid =true;}
        else if (mString.contains(".xlsx")) { a = ".xlsx"; valid =true;}
        else if (mString.contains(".exe")) { a = ".exe"; valid =true;}
        else if (mString.contains(".gz")) { a = ".gz"; valid =true;}
        else if (mString.contains(".torrent")) { a = ".torrent"; valid =true;}
       
        return  valid;
    }













   
}
