package projet.pictionnary.breton.model;

import projet.pictionnary.breton.server.users.User;

public class MessageReceptDraw implements Message {

    private final User author;
    private final User recipient;
    private final DrawEvent event;
    private final DrawingInfos drawingInfos;
    
    public MessageReceptDraw(User author, User recipient, DrawEvent event, 
                      DrawingInfos drawingInfos) {
        
        this.author = author;
        this.recipient = recipient;
        this.event = event;
        this.drawingInfos = drawingInfos;
    }

    @Override
    public Type getType() {
        return Type.RECEPT_DRAW;
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
