/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alvaro.cascante
 */
@Repository(value = "archivoFtpDao")
public class ArchivoFtpDao {
    public void downloadFile(String user, String host, Integer port, String password, String localPath, String fileName) throws JSchException, SftpException {
        JSch jsch = new JSch();
        Session session = null;
        session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        channelSftp.get(fileName, localPath);
        channelSftp.exit();
        session.disconnect();
    }
    
    public void uploadFile(String user, String host, Integer port, String password, String localPath, String uploadPath) throws JSchException, SftpException {
        JSch jsch = new JSch();
        Session session = null;
        session = jsch.getSession(user, host, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(password);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        channelSftp.put(localPath, uploadPath);
        channelSftp.exit();
        session.disconnect();
    }
}
