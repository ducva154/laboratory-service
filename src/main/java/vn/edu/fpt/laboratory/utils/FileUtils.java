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
        double sizeInBytes = 4 * Math.ceil((length / 3))*0.5624896334383812;
        if(sizeInBytes < 1000){
            return String.format("%f %s", sizeInBytes, "Byte");
        }
        double sizeInKb =sizeInBytes/1000;
        if(sizeInKb < 1000){
            return String.format("%f %s", sizeInKb, "KB");
        }
        double sizeInMb = sizeInKb / 1000;
        if(sizeInMb < 1000){
            return String.format("%f %s", sizeInMb, "MB");
        }
        double sizeInGb = sizeInMb / 1000;
        return String.format("%f %s", sizeInGb, "GB");
    }
}
