package be.pxl.student.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

	@RequestMapping(value = "/upload" /* TO BE CHANGED */, method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file, @RequestParam("privateKeySender") MultipartFile privateKey, @RequestParam("publicKeyReceiver") MultipartFile publicKey) {
		if (!file.isEmpty() && !publicKey.isEmpty() && !privateKey.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				
				String publicKeyRead = publicKey.getInputStream().toString();
				String privateKeyRead = privateKey.getInputStream().toString();
				
				// Creating the directory to store file
				// Server (Tomcat) Location
				// String rootPath = System.getProperty("catalina.home");

				String rootPath = System.getProperty("user.home");

				File dir = new File(rootPath + File.separator + "Messages" + File.separator + publicKeyRead);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + privateKeyRead);

				System.out.println(serverFile.toString());

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				return "You sent a message to " + publicKeyRead;
			} catch (Exception e) {
				return "You failed to sent a message => " + e.getMessage();
			}
		} else {
			return "You failed to upload because the file was empty.";
		}
	}
}
