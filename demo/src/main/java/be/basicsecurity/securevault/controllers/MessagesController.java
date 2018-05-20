package be.basicsecurity.securevault.controllers;

@RestController
@CrossOrigin
@RequestMapping("/api/message")
public class MessagesController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/sentmessage")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody Map<String, Object> json) {
        File encyptedMessage = json.get("encryptedMessage").toString();
        Account sender = json.get("username");
        Accound receiver = json.get("receiver");
        Message message = new Message(sender, receiver, message);
        messageRepository.save(message);
        return "Message sent!";
    }

    @GetMapping("/{getmessage}")
    public String get(@PathVariable("id") String id) {
        try {
            return messageRepository.findById(id).get().get().getEncryptedMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Message not found!";
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