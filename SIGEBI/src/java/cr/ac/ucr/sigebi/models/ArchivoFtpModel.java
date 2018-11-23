/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import cr.ac.ucr.sigebi.daos.ArchivoFtpDao;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service("archivoFtpModel")
public class ArchivoFtpModel {

    @Value("${sigebi.ftp.url}")
    private String ftpUrl;

    @Value("${sigebi.ftp.host}")
    private String ftpHost;

    @Value("${sigebi.ftp.user}")
    private String ftpUser;

    @Value("${sigebi.ftp.password}")
    private String ftpPassword;

    @Value("${sigebi.ftp.path}")
    private String ftpUploadPath;
    
    @Value("${sigebi.ftp.port}")
    private Integer ftpPort;
    
    @Resource
    private ArchivoFtpDao archivoFtpDao;

    public void downloadFile(String filePath, String fileName) throws JSchException, SftpException {

        archivoFtpDao.downloadFile(ftpUser, ftpHost, ftpPort, ftpPassword, filePath, fileName);        
    }

    public void uploadFile(String filePath, String fileName) throws JSchException, SftpException {
        archivoFtpDao.uploadFile(ftpUser, ftpHost, ftpPort, ftpPassword, filePath, fileName);        
    }
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getFtpUrl() {
        return ftpUrl;
    }

    public void setFtpUrl(String ftpUrl) {
        this.ftpUrl = ftpUrl;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpUploadPath() {
        return ftpUploadPath;
    }

    public void setFtpUploadPath(String ftpUploadPath) {
        this.ftpUploadPath = ftpUploadPath;
    }
    
    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }
    //</editor-fold>
}