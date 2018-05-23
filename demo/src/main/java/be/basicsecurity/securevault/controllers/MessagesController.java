package be.basicsecurity.securevault.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.basicsecurity.securevault.models.Message;
import be.basicsecurity.securevault.repositories.AccountRepository;
import be.basicsecurity.securevault.repositories.MessageRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/message")
public class MessagesController {
	
	@Autowired
	private AccountRepository AccountRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/sendMessage")
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@RequestParam("file") MultipartFile file, @RequestParam("sender") String sender,
			@RequestParam("receiver") String receiver, @RequestParam("subject") String subject) {
		if (!file.isEmpty()) {
			try {
				System.out.println(sender);
				System.out.println(receiver);
				int endIndex = subject.length();
				if(subject.indexOf(".") != -1) {
					endIndex = subject.indexOf(".");
				}
				String name = String.format("Sender%sSubject%s",sender,subject.substring(0, endIndex));
				byte[] bytes = file.getBytes();
				
				String rootPath = System.getProperty("user.home");

				File dir = new File(rootPath + File.separator + "tempFiles");
				if (!dir.exists())
					dir.mkdirs();

				String locationPath = dir.getAbsolutePath() + File.separator + name;
				File serverFile = new File(locationPath);

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				Message message = new Message(AccountRepository.getOne(sender), AccountRepository.getOne(receiver), serverFile);
				messageRepository.save(message);
				return "Message sent!";
			} catch (Exception e) {
				e.printStackTrace();
				return "You failed to upload";
			}
		} else {
			return "You failed to upload because the file was empty.";
		}
	}

	@GetMapping("/{id}")
	public void get(HttpServletResponse response, @PathVariable("id") long id) {
		try {
			File file = Message.decryptMessage(messageRepository.findById(id).get());

			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				System.out.println("mimetype is not detectable, will take default");
				mimeType = "application/octet-stream";
			}

			System.out.println("mimetype : " + mimeType);

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping
	public String list() {
		try {
			return objectMapper.writeValueAsString(messageRepository.findAll());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "No Messages!";
		}
	}

}