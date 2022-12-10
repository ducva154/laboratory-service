package vn.edu.fpt.laboratory.utils;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/12/2022 - 09:31
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public class FileUtils {

    public static String getFileSize(Long length){
        if (length < 1024) {
            return String.format("%d %s", length, "Byte");
        }
        double sizeInKb =(double) length/1024;
        if(sizeInKb < 1024){
            return String.format("%.2f %s", sizeInKb, "KB");
        }
        double sizeInMb =sizeInKb/1024;
        if(sizeInMb < 1024){
            return String.format("%.2f %s", sizeInMb, "MB");
        }
        double sizeInGb = sizeInMb / 1024;
            return String.format("%.2f %s", sizeInGb, "GB");
    }
}
