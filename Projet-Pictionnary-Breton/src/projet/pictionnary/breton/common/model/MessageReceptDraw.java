package projet.pictionnary.breton.common.model;

import projet.pictionnary.breton.common.users.User;

/**
 * This class is used to transfer a draw to a partner.
 * 
 * @author Gabriel Breton - 43397
 */
public class MessageReceptDraw implements Message {

    private final User author;
    private final User recipient;
    private final DrawEvent event;
    private final DrawingInfos drawingInfos;
   
    /**
     * Constructs a <code> MessageReceptDraw </code>.
     * 
     * @param author the author of the message.
     * @param recipient the recipient of the message.
     * @param event the draw event, DRAW or CLEARPANE.
     * @param drawingInfos the drawing infos to reconstruct the draw.
     */
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
    
    /**
     * Gives the drawing infos to reconstruct the draw.
     * 
     * @return the drawing infos.
     */
    public DrawingInfos getDrawingInfos() {
        return drawingInfos;
    }
}
