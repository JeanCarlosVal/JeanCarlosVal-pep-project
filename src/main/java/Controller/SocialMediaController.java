package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {

    SocialMediaService socialMediaService;

    public SocialMediaController() {
        socialMediaService = new SocialMediaService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getOneMessageHandler);
        app.delete("/messages/{message_id}", this::deleteOneMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        return app;
    }

    private void registerUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = socialMediaService.registerAccount(account);

        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }

    private void loginUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account logInAccount = socialMediaService.logInUser(account);

        if (logInAccount == null) {
            ctx.status(401);
        } else {
            ctx.json(mapper.writeValueAsString(logInAccount));
        }
    }

    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = socialMediaService.createMessage(message);

        if (newMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(newMessage));
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> allMessages = socialMediaService.getAllMessages();

        ctx.json(allMessages);
    }

    private void getOneMessageHandler(Context ctx) {
        Message message = socialMediaService.getOneMessage(Integer.parseInt(ctx.pathParam("message_id")));

        if (message.message_text != null) {
            ctx.json(message);
        }
    }

    private void deleteOneMessageHandler(Context ctx) {
        Message message = socialMediaService.deleteMessage(Integer.parseInt(ctx.pathParam("message_id")));

        if (message.message_text != null) {
            ctx.json(message);
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = socialMediaService.updatMessage(Integer.parseInt(ctx.pathParam("message_id")), message);

        if (newMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(newMessage));
        }

    }

    private void getAllMessagesFromUserHandler(Context ctx) {

        List<Message> allMessagesFromUser = socialMediaService.getAllMessagesFromUser(Integer.parseInt(ctx.pathParam("account_id")));

        ctx.json(allMessagesFromUser);

    }
}