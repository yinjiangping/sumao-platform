package team.yqby.platform.interceptor;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsFileUploadSupport;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class I2fCommonsMultipartResolver extends CommonsMultipartResolver {

    protected CommonsFileUploadSupport.MultipartParsingResult parseRequest(HttpServletRequest request)throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = this.prepareFileUpload(encoding,request);
        try {
            List fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);

        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(),
                    ex);
        } catch (FileUploadException ex) {
            throw new MultipartException(
                    "Could not parse multipart servlet request", ex);
        }

    }

    protected FileUpload prepareFileUpload(String encoding,HttpServletRequest request) {
        FileUpload fileUpload = getFileUpload();
        FileUpload actualFileUpload = fileUpload;
        if (encoding != null && !encoding.equals(fileUpload.getHeaderEncoding())) {
            actualFileUpload = newFileUpload(getFileItemFactory());
            actualFileUpload.setHeaderEncoding(encoding);
            boolean isAddProduct = request.getRequestURI().contains("/uploadMultiplePic")
                    ||request.getRequestURI().contains("/uploadPic");
            if(isAddProduct){
                actualFileUpload.setSizeMax(1024 * 1024 * 14);//重新设置文件限制14M
            }else{
                actualFileUpload.setSizeMax(fileUpload.getSizeMax());
            }
        }
        return actualFileUpload;
    }
}
