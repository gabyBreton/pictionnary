package projet.pictionnary.breton.model;

import projet.pictionnary.breton.server.users.User;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class MessageSendDraw implements Message {

    private final User author;
    private final User recipient;
    private final DrawEvent event;
    private final DrawingInfos drawingInfos;
    
    public MessageSendDraw(User author, User recipient, DrawEvent event, 
                      DrawingInfos drawingInfos) {
        
        this.author = author;
        this.recipient = recipient;
        this.event = event;
        this.drawingInfos = drawingInfos;
    }

    @Override
    public Type getType() {
        return Type.SEND_DRAW;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public User getRecipient() {
        return recipient;
    }

    @Override
    public Object getContent() {
        return event;
    }
    
    public DrawingInfos getDrawingInfos() {
        return drawingInfos;
    }
}
