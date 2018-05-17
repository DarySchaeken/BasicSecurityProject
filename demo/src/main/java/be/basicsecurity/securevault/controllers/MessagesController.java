package be.basicsecurity.securevault.controllers;

@RestController
@CrossOrigin
@RequestMapping("/api/message")
public class MessagesController {

    @Autowired
    private MessageRepository messageRepository;



}