package vn.edu.fpt.laboratory.service;

import vn.edu.fpt.laboratory.dto.common.CreateFileRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 26/10/2022 - 02:01
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface S3BucketStorageService {

    void uploadFile(CreateFileRequest request, String fileKey);
    void deleteFile(String path);

    void downloadFile(String fileKey, HttpServletResponse response);

    File downloadFile(String fileKey);
    String sharingUsingPresignedURL(String fileKey);

    String getPublicURL(String fileKey);
}
