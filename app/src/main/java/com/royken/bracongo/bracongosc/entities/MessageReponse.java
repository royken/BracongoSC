package com.royken.bracongo.bracongosc.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vr.kenfack on 06/09/2017.
 */

public class MessageReponse {

    @Expose(serialize = true, deserialize = true)
    @SerializedName("payload")
    private List<Message> messages;

    public MessageReponse() {
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessageReponse{" +
                "messages=" + messages +
                '}';
    }
}
