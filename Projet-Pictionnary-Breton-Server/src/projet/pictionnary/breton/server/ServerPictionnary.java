package projet.pictionnary.breton.server;

import projet.pictionnary.breton.common.model.MessageBadRequest;
import projet.pictionnary.breton.common.model.MessageGameStatus;
import projet.pictionnary.breton.common.model.MessageCreate;
import projet.pictionnary.breton.common.model.MessageProfile;
import projet.pictionnary.breton.common.model.Type;
import projet.pictionnary.breton.common.model.MessageGetWord;
import projet.pictionnary.breton.common.model.MessageInvalidLogin;
import projet.pictionnary.breton.common.model.Message;
import projet.pictionnary.breton.common.model.GameStatus;
import projet.pictionnary.breton.common.model.MessageReceptDraw;
import projet.pictionnary.breton.common.model.MessageSendDraw;
import projet.pictionnary.breton.common.model.MessageQuit;
import projet.pictionnary.breton.common.model.MessageSubmit;
import projet.pictionnary.breton.common.model.DrawEvent;
import projet.pictionnary.breton.common.model.Role;
import projet.pictionnary.breton.common.model.MessageConnected;
import projet.pictionnary.breton.common.model.MessageJoin;
import projet.pictionnary.breton.common.model.DataTable;
import projet.pictionnary.breton.common.model.MessageGetTables;
import projet.pictionnary.breton.common.model.Table;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import projet.pictionnary.breton.common.model.MessageStats;
import projet.pictionnary.breton.common.users.Members;
import projet.pictionnary.breton.server.business.AdminFacade;
import projet.pictionnary.breton.server.dto.PlayerDto;
import projet.pictionnary.breton.server.dto.WordDto;
import projet.pictionnary.breton.server.exception.PictionnaryBusinessException;
import projet.pictionnary.breton.common.users.User;
import projet.pictionnary.breton.common.util.Observer;
import projet.pictionnary.breton.server.dto.GameDto;
import projet.pictionnary.breton.server.dto.PropositionDto;

/**
 * This class is used to manage and control the Pictionnary server.
 * 
 * @author Gabriel Breton - 43397
 */
public class ServerPictionnary extends AbstractServer {

    // TODO : gerer clientDisconnected
    
    private static final int PORT = 12_345;
    static final String ID_MAPINFO = "ID";
    
    private final List<Observer> observers;    
    private final List<Table> tables;
    private final List<DataTable> dataTables;
    private int tableId;
    private int loginWaitId;
    private final Members members;
    private final List<WordDto> words;
    
    private static InetAddress getLocalAddress() {
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while (b.hasMoreElements()) {
                for (InterfaceAddress f : b.nextElement().getInterfaceAddresses()) {
                    if (f.getAddress().isSiteLocalAddress()) {
                        return f.getAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Logger.getLogger(ServerPictionnary.class.getName()).log(Level.SEVERE, "NetworkInterface error", e);
        }
        return null;
    }    

    /**
     * Constructs the server. Build a thread to listen connection request.
     *
     * @throws IOException if an I/O error occurs when creating the server
     * socket.
     */
    public ServerPictionnary() throws IOException, PictionnaryBusinessException {
        super(PORT);
        members = new Members();
        tableId = 0;
        tables = new ArrayList<>();
        dataTables = new ArrayList<>();
        observers = new ArrayList<>();
        words = AdminFacade.getAllWords();
        this.listen();
    }
        
    /**
     * Return the list of connected users.
     *
     * @return the list of connected users.
     */
    public Members getMembers() {
        return members;
    }
    
    /**
     * Return the server IP address.
     *
     * @return the server IP address.
     */
    public String getIP() {
        if (getLocalAddress() == null) {
            return "Unknown";
        }
        return getLocalAddress().getHostAddress();
    }

    /**
     * Return the number of connected users.
     *
     * @return the number of connected users.
     */
    public int getNbConnected() {
        return getNumberOfClients();
    }
    
    /**
     * Quits the server and closes all aspects of the connection to clients.
     *
     * @throws IOException in case of error while closing the connection.
     */
    public void quit() throws IOException {
        this.stopListening();
        this.close();
    }
    
    /**
     * Gives the nex table id.
     * 
     * @return the next table id.
     */
    final synchronized int getNextTableId() {
        tableId++;
        return tableId;
    }
    
    /**
     * Gives the next wait login id. Used when a client is connected but the 
     * login is not validated.
     * 
     * @return the next wait login id.
     */
    final synchronized int getNextLoginWaitId() {
        loginWaitId--;
        return loginWaitId;
    }
    
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Message message = (Message) msg;
        Type type = message.getType();
        int memberId = (int) client.getInfo(ID_MAPINFO);
        User author = message.getAuthor();
        
        switch (type) {
            case PROFILE:
                handleProfileRequest(author, memberId, msg);
                break;
            
            case CREATE:
                if (author.getRole() == Role.NOT_IN_GAME) {
                    handleCreateTableRequest(message, memberId);
                } else {
                    Message msgBadRequestCreate = new MessageBadRequest(User.ADMIN, 
                                author, "Can't create table. Already in game.");
                    sendToClient(msgBadRequestCreate, memberId);
                }
                break;
    
            case STATS:
                try {
                    handleStatsRequest(message, memberId);
                } catch (PictionnaryBusinessException ex) {
                    Logger.getLogger(ServerPictionnary.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                
            case GET_TABLES:
                Message msgGetTables = new MessageGetTables(User.ADMIN, author, 
                                                                dataTables);
                sendToClient(msgGetTables, author);
                break;
                
            case GET_WORD:
                handleGetWordRequest(author, memberId);
                break;
            
            case QUIT:
                try {
                    handleQuitRequest(author);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(ServerPictionnary.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PictionnaryBusinessException ex) {
                    Logger.getLogger(ServerPictionnary.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case JOIN:
                MessageJoin msgJoin = (MessageJoin) message;
                Table tableJoin = findTableFromId((Integer) msgJoin.getContent());
                handleJoinRequest(tableJoin, author);
                break;
                
            case SEND_DRAW:
                MessageSendDraw msgSendDraw = (MessageSendDraw) message;
                Table tableDrawer = findTable(memberId);
                User partner = tableDrawer.getPartner();
                DrawEvent event = (DrawEvent) msgSendDraw.getContent();
                
                Message msgReceptDraw = handleSendDrawRequest(event, partner, msgSendDraw);
                sendToClient(msgReceptDraw, partner.getId());
                break;
                
            case GAME_STATUS:
                Table tableGameStatus = findTable(memberId);
                sendMessageGameStatus(tableGameStatus);
                break;
            
            case SUBMIT:
                String proposition = (String) message.getContent();
                Table tableSubmit = findTable(message.getAuthor().getId());
                try {
                    handleSubmitRequest(tableSubmit, proposition, author);
                } catch (PictionnaryBusinessException ex) {
                    Logger.getLogger(ServerPictionnary.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }
    } 

    /**
     * Handles a profile request.
     * 
     * @param author the author of the request.
     * @param memberId the id of the member.
     * @param msg the request.
     */
    private void handleProfileRequest(User author, int memberId, Object msg) {
        try {
            PlayerDto registredPlayer = AdminFacade.getPlayerByLogin(author.getName());
            if (registredPlayer != null) {
                
                if (members.getUser(registredPlayer.getId()) != null) {
                    sendToClient(new MessageInvalidLogin(User.ADMIN, author, author.getName()), memberId);
                    
                } else {
                    members.changeId(memberId, registredPlayer.getId());
                    members.changeName(author.getName(), registredPlayer.getId());
                    author.setId(registredPlayer.getId());
                    changeClientConnectionId(memberId, registredPlayer.getId());
                    loginWaitId++;
                    
                    sendMessagesAfterProfile(author);
                    notifyObservers(msg);
                }
                
            } else {
                int idPlayer = AdminFacade.addPlayer(new PlayerDto(author.getName()));
                members.changeId(memberId, idPlayer);
                members.changeName(author.getName(), idPlayer);
                author.setId(idPlayer);
                changeClientConnectionId(memberId, idPlayer);
                loginWaitId++;
                
                sendMessagesAfterProfile(author);
                notifyObservers(msg);
                // TODO @FIX_ME : ce notify lance un exception dans le
                // foreach. OK pour l'autre au dessus.
            }
        } catch (PictionnaryBusinessException ex) {
            Logger.getLogger(ServerPictionnary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sends messages after a profile request.
     * 
     * @param author the author of the request.
     */
    private void sendMessagesAfterProfile(User author) {
        sendToClient(new MessageProfile(author.getId(), author.getName()),
                        author.getId());
        sendToClient(new MessageGetTables(User.ADMIN, author, dataTables),
                        author.getId());
    }

    /**
     * Handles a submit request.
     * 
     * @param tableSubmit the table from where the submit comes.
     * @param proposition the word submited.
     * @param author the author of the request.
     */
    private void handleSubmitRequest(Table tableSubmit, String proposition, 
                                        User author) throws PictionnaryBusinessException {
        GameStatus gameStatus;
        if (tableSubmit != null) {

            int gameId = members.getUser(author.getId()).getGameId();
            if (proposition.toLowerCase().equals(tableSubmit
                                                    .getWord()
                                                    .toLowerCase())) {
                tableSubmit.setGameStatus(GameStatus.WIN);
                updateDataTables(tableSubmit.getId(),
                                 author.getRole(),
                                 author.getName() ,
                                 "Closed",
                                GameStatus.WIN.toString());
                
                gameStatus = GameStatus.WIN;
                
                GameDto gameDto = AdminFacade.getGameById(gameId);
                
                if (gameDto !=  null) {
                    GameDto gameUpdate = new GameDto(gameDto.getId(),
                                                     gameDto.getDrawer(),
                                                     gameDto.getPartner(),
                                                     gameDto.getStartTime(),
                                                     new Timestamp(System.currentTimeMillis()),
                                                     0,
                                                     AdminFacade.getWord(proposition).getId(),
                                                     gameDto.getName());
                    AdminFacade.updateGame(gameUpdate);
                }
            } else {
                gameStatus = GameStatus.IN_GAME;
                AdminFacade.addPropostion(new PropositionDto(proposition, gameId));               
            }
            
            sendSubmitResponse(tableSubmit.getDrawer(),
                               proposition.toLowerCase(), 
                               gameStatus);
            
            sendSubmitResponse(tableSubmit.getPartner(),
                               proposition.toLowerCase(), 
                               gameStatus);
        }
    }

    /**
     * Handles a request to send the draw.
     * 
     * @param event the draw event, DRAW or CLEARPANE.
     * @param partner the partner to send to the draw.
     * @param msgSendDraw the send message.
     * @return a message to send to the partner.
     */
    private Message handleSendDrawRequest(DrawEvent event, User partner, 
                                            MessageSendDraw msgSendDraw) {
        Message msgReceptDraw;
        if (event == DrawEvent.DRAW) {
            msgReceptDraw = new MessageReceptDraw(User.ADMIN, partner,
                    event, msgSendDraw.getDrawingInfos());
        } else {
            msgReceptDraw = new MessageReceptDraw(User.ADMIN, partner,
                    event, null);
        }
        return msgReceptDraw;
    }

    /**
     * Handles a request to get the word to draw.
     * 
     * @param author the author of the request.
     * @param memberId the id of the author. 
     */
    private void handleGetWordRequest(User author, int memberId) {
        if (author.getRole() == Role.PARTNER) {
            Message msgBadRequestGetWord = new MessageBadRequest(User.ADMIN,
                    author, "Can't give the word, you are partner.");
            sendToClient(msgBadRequestGetWord, memberId);
        } else {
            Message msgGetWord = new MessageGetWord(User.ADMIN, author,
                    getWordFromTable(memberId));
            sendToClient(msgGetWord, memberId);
        }
    }

    /**
     * Send a response to a submit request.
     * 
     * @param player the player to send the response.
     * @param proposition the word that has been proposed.
     * @param gameStatus the game status to send, WIN or IN_GAME, depending if
     *                   the word is correct.
     */
    private void sendSubmitResponse(User player, String proposition, 
                                    GameStatus gameStatus) {
        MessageSubmit msgSubmit;
        if (player != null) {
            msgSubmit = new MessageSubmit(User.ADMIN, player, proposition, gameStatus);
            sendToClient(msgSubmit, player.getId());
        }
    }
    
    /**
     * Handles and applies a create table request.
     * 
     * @param message the create table request.
     * @param memberId the id of the author of the request.
     */
    private void handleCreateTableRequest(Message message, int memberId) {
        Table table = new Table((String) message.getContent(),getNextTableId(), 
                                 message.getAuthor(), getRandomWord());
        
        tables.add(table);
        message.getAuthor().setRole(Role.DRAWER);
        members.changeRole(message.getAuthor().getRole(), message.getAuthor().getId());
        Message messageCreateTable = new MessageCreate(User.ADMIN, message.getAuthor(),
                                                        table.getName());
        sendToClient(messageCreateTable, memberId);

        // update datatables.
        String namePartner = (table.getPartner() == null) ? "" : table.getPartner().getName();
        String statusTable = (table.isOpen() == true) ? "Open" : "Closed";        
        
        dataTables.add(new DataTable(table.getName(), 
                                     table.getId(),
                                     statusTable,
                                     table.getDrawer().getName(),
                                     namePartner, 
                                     table.getGameStatus().toString()));

        Message messageGetAllTables = new MessageGetTables(User.ADMIN,
                                                    User.EVERYBODY, dataTables);
        sendToAllClients(messageGetAllTables);
    }    
    
    /**
     * Handles a request to get the game statistics for a client.
     * 
     * @param message the statistics message.
     * @param memberId the id of the client.
     */
    private void handleStatsRequest(Message message, int memberId) throws PictionnaryBusinessException {
        if (memberId < 0) {
            sendToClient(new MessageBadRequest(User.ADMIN, 
                                               message.getAuthor(), 
                                               "Your login is still not validated"), 
                         message.getAuthor());
        } else {
            List<GameDto> gameInfosAsDrawer = AdminFacade.getGameInfosDrawer(memberId);
            List<GameDto> gameInfosAsPartner = AdminFacade.getGameInfosPartner(memberId);
            int [] gameStats = storeStatistics(gameInfosAsDrawer, gameInfosAsPartner);

            sendToClient(new MessageStats(User.ADMIN, message.getAuthor(), gameStats), 
                         message.getAuthor());
        }
    }

    /**
     * Stores client game statistics and return it.
     * 
     * @param gameInfosAsDrawer the informations for the client as drawer.
     * @param gameInfosAsPartner the informations for the client as partner. 
     */
    private int[] storeStatistics(List<GameDto> gameInfosAsDrawer, 
                                  List<GameDto> gameInfosAsPartner) {
        int indexTotalGame = 0;
        int indexDrawer = 1;
        int indexPartner = 2;
        // TODO bonus:
        //int indexAbandonDrawer = 3;
        //int indexAbandonPartner = 4;
        int [] gameStats = new int [3];
        int nbTotalGame = 0;
        
        if (gameInfosAsDrawer != null) {
            nbTotalGame += gameInfosAsDrawer.size();
            
            gameStats[indexTotalGame] = nbTotalGame;
            gameStats[indexDrawer] = gameInfosAsDrawer.size();
        } 
        
        if (gameInfosAsPartner != null) {
            nbTotalGame += gameInfosAsPartner.size();
            
            gameStats[indexTotalGame] = nbTotalGame;
            gameStats[indexPartner] = gameInfosAsPartner.size();
        }
        
        return gameStats;
    }
    
    /**
     * Handles and applys a quit request.
     * 
     * @param tableQuit the table to quit.
     * @param roleQuit the role of the player that want to quit.
     * @param memberId the id of the member that want to quit.
     * @param author the author of the request.
     * @throws IllegalArgumentException in case of an unknown role type.
     */
    private void handleQuitRequest(User author) throws IllegalArgumentException, PictionnaryBusinessException {        
        boolean tableDestroyed = false;
        Table tableQuit = findTable(author.getId());
        
        if (tableQuit != null && tableQuit.getPlayerCount() == 1) {
            destroyTable(tableQuit);
            author.setRole(Role.NOT_IN_GAME);
            members.changeRole(author.getRole(), author.getId());
            tableDestroyed = true;
            
        } else if (tableQuit != null && tableQuit.getPlayerCount() == 2) {
            GameDto gameDto = null;
            int gameId;
            
            switch (author.getRole()) {
                case DRAWER:
                    tableQuit.removeDrawer();
                    quitActions(author, tableQuit);
                    
                    gameId = members.getUser(author.getId()).getGameId();
                    gameDto = AdminFacade.getGameById(gameId);
                    // TODO notifier autre joueur
                    break;
        
                case PARTNER:
                    tableQuit.removePartner();
                    quitActions(author, tableQuit);
                    
                    gameId = members.getUser(author.getId()).getGameId();
                    gameDto = AdminFacade.getGameById(gameId);
                    // TODO notifier autre joueur
                    break;
                
                case NOT_IN_GAME:
                    Message msgBadRequestQuit = new MessageBadRequest(User.ADMIN,
                            author, "Can't quit, not in game.");
                    sendToClient(msgBadRequestQuit, author.getId());
                    break;
                
                default:
                    throw new IllegalArgumentException("Unknown role : " + author.getRole());
            }
            if (gameDto != null) {
                GameDto gameUpdate = new GameDto(gameDto.getId(), 
                                                 gameDto.getDrawer(), 
                                                 gameDto.getPartner(), 
                                                 gameDto.getStartTime(), 
                                                 new Timestamp(System.currentTimeMillis()),
                                                 author.getId(),
                                                 gameDto.getWord(),
                                                 gameDto.getName());
                
                AdminFacade.updateGame(gameUpdate);
            }
        }
        
        sendMessagesAfterQuit(author, tableQuit, tableDestroyed);
    }

    /**
     * Executes some actions of updates and changes after a player quit the 
     * game.
     * 
     * @param tableQuit the table that is quit.
     * @param author the user that quit the game.
     */
    private void quitActions(User author, Table tableQuit) {
        author.setRole(Role.NOT_IN_GAME);
        members.changeRole(author.getRole(), author.getId());
        updateDataTables(tableQuit.getId(), author.getRole(), "", "Closed", 
                            GameStatus.OVER.toString());
    }

    /**
     * Send several messages after a quit request, such as an update of the 
     * tables and a confirmation for the client that have emitted this request.
     * 
     * @param author the author of the message.
     * @param memberId the id of the author of the message.
     */
    private void sendMessagesAfterQuit(User author, Table tableQuit, 
                                        boolean tableDestroyed) {
        Message msgGetAllTablesRefresh = new MessageGetTables(User.ADMIN,
                                                    User.EVERYBODY, dataTables);
        sendToAllClients(msgGetAllTablesRefresh);
        
        if (!tableDestroyed) {
            sendMessageGameStatus(tableQuit);
        }
        
        Message msgQuitGame = new MessageQuit(User.ADMIN, author, Role.NOT_IN_GAME);
        sendToClient(msgQuitGame, author.getId());
    }    
    
    /**
     * Handle and apply a join request.
     * 
     * @param tableJoin the table to join.
     * @param author the author that want to join the table.
     * @param memberId the id of the user.
     * @param roleJoin the role of the user.
     */
    private void handleJoinRequest(Table tableJoin, User author) {
        if (tableJoin != null) {
            if (!tableJoin.isOpen()) {
                Message msgBadRequestClosed = new MessageBadRequest(User.ADMIN,
                                           author, "Can't join, table closed.");
                sendToClient(msgBadRequestClosed, author.getId());
                
            } else if (author.getRole() != Role.NOT_IN_GAME) {
                Message msgBadRequestInGame = new MessageBadRequest(User.ADMIN,
                                author, "Can't join, you are already in game.");
                sendToClient(msgBadRequestInGame, author.getId());
                
            } else if (tableJoin.getPlayerCount() == 1) {
                joinActions(author, tableJoin);
                sendMessagesAfterJoin(author, tableJoin);
                try {
                    int wordId = AdminFacade.getWord(tableJoin.getWord()).getId();
                    int gameId = AdminFacade.addGame(new GameDto(tableJoin.getDrawer().getId(),
                                                                 tableJoin.getPartner().getId(),
                                                                 new Timestamp(System.currentTimeMillis()),
                                                                 null,
                                                                 0,
                                                                 wordId,
                                                                 tableJoin.getName()));
                    members.changeGameId(tableJoin.getDrawer().getId(), gameId);
                    members.changeGameId(tableJoin.getPartner().getId(), gameId);
                } catch (PictionnaryBusinessException ex) {
                    Logger.getLogger(ServerPictionnary.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            Message msgBadRequestNoTable = new MessageBadRequest(User.ADMIN,
                                                    author, "Can't find table");
            sendToClient(msgBadRequestNoTable, author.getId());
        }
    }

    /**
     * Executes some actions of updates and changes after a player join the 
     * game.
     * 
     * @param tableJoin the table that is joined.
     * @param author the user that join the game.
     */
    private void joinActions(User author, Table tableJoin) {
        tableJoin.addPartner(author);
        tableJoin.setGameStatus(GameStatus.IN_GAME);
        author.setRole(Role.PARTNER);
        members.changeRole(author.getRole(), author.getId());
        updateDataTables(tableJoin.getId(), Role.PARTNER, author.getName(),
                "Closed", tableJoin.getGameStatus().toString());
    }

    /**
     * Send several messages after a join request, such as an update of the 
     * tables and a confirmation for the client that have emitted this request.
     * 
     * @param author the author of the request.
     * @param tableJoin the table to join.
     * @param memberId the id of the author of the request.
     */
    private void sendMessagesAfterJoin(User author, Table tableJoin) {
        Message msgGetAllTablesRefresh = new MessageGetTables(User.ADMIN,
                                                    User.EVERYBODY, dataTables);
        sendToAllClients(msgGetAllTablesRefresh);
        Message msgJoin = new MessageJoin(User.ADMIN, author, tableJoin.getId());
        sendToClient(msgJoin, author.getId());
    }

    /**
     * Sends a message with the status of a table to update clients informations
     * for the players of the game.
     * 
     * @param table the table to get the status.
     */
    private void sendMessageGameStatus(Table table) {
        Message msgGameStatus;
        if (table != null) {
            if (table.getDrawer() != null) {
                msgGameStatus = new MessageGameStatus(User.ADMIN, 
                                                        table.getDrawer(),
                                                        table.getGameStatus());
                sendToClient(msgGameStatus, table.getDrawer().getId());
            } 
            if (table.getPartner() != null) {
                msgGameStatus = new MessageGameStatus(User.ADMIN, 
                                                        table.getPartner(),
                                                        table.getGameStatus());
                sendToClient(msgGameStatus, table.getPartner().getId());
            }
        }
    }
    
    /**
     * Finds a table based on its id.
     * 
     * @param tableId the id of the table.
     * @return the table corresponding to the id or null is the table is not 
     * find.
     */
    private Table findTableFromId(int tableId) {
        for (Table table : tables) {
            if (table.getId() == tableId) {
                return table;
            }
        }
        return null;
    }

    /**
     * Finds a table that contains the given client.
     * 
     * @param clientId the id of the client.
     * @return the table that contains the client or null if no table contains 
     * this client.
     */
    private Table findTable(int clientId) {
        for (Table table : tables) {
            if ((table.getDrawer() != null && table.getDrawer().getId() == clientId) 
                    || (table.getPartner() != null && table.getPartner().getId() == clientId)) {
                return table;
            }
        }
        return null;
    }    
    
    @Override
    protected void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
        int memberId;
        memberId = members.add(getNextLoginWaitId(),
                                    client.getName(), 
                                    client.getInetAddress(), 
                                    Role.NOT_IN_GAME);
        client.setInfo(ID_MAPINFO, memberId);
        sendToClient(new MessageConnected(User.ADMIN, members.getUser(memberId), true), 
                            memberId);
    }
    
    /**
     * Sends a message to a client.
     * 
     * @param message the message to send.
     * @param recipient the recipient of the message.
     */
    void sendToClient(Message message, User recipient) {
        sendToClient(message, recipient.getId());
    }

    /**
     * Sends a message to a client, based on the client id.
     * 
     * @param message the message to send.
     * @param clientId the id of the recipient client.
     */
    void sendToClient(Message message, int clientId) {
        for (Thread clientThreadList : getClientConnections()) {
            int idClientThread = (Integer) ((ConnectionToClient) clientThreadList).getInfo(ID_MAPINFO);
            
            if (clientId == idClientThread) {
                try {
                    ((ConnectionToClient) clientThreadList).sendToClient(message);
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
                break;
            }
        }
    }
    
    /**
     * Destroys a table and update datatables.
     * 
     * @param table the table to destroy.
     */
    private void destroyTable(Table table) {
        System.out.println("destroyTable table");
        for(DataTable data : dataTables) {
            if (data.getId() == table.getId()) {
                dataTables.remove(data);
                break;
            }
        }
        tables.remove(table);
    }

    /**
     * Update the list of table informations when a role or the table status 
     * change.
     * 
     * @param tableId the id of the table that change.
     * @param role the role of that change.
     * @param name the name of the drawer or the partner.
     * @param status the status that change.
     */
    private void updateDataTables(int tableId, Role role, String name, 
                                    String status, String gameStatus) {
        for(DataTable data : dataTables) {
            if (data.getId() == tableId) {
                if (role == Role.DRAWER) {
                    data.setDrawer(name);
                } else if (role == Role.PARTNER) {
                    data.setPartner(name);
                }
                data.setStatus(status);
                data.setGameStatus(gameStatus);
                break;
            }
        }
    }
    
    /**
     * Gives the word to draw attributed to a table.
     * 
     * @param clientId the id of the client that needs the word to draw.
     * @return the word to draw.
     */
    private String getWordFromTable(int clientId) {
        String word = "";
        
        for (Table table : tables) {
            if (table.getDrawer().getId() == clientId) {
                word = table.getWord();
                break;
            }
        }
        return word;
    }
    
    /**
     * Gives a random word of the list of words.
     * 
     * @return a words from the list of words.
     */
    private String getRandomWord() {
        int randomValue = ThreadLocalRandom.current().nextInt(0, 43 + 1);
        return words.get(randomValue).getWord();
    }
    
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }
    
    @Override
    public void notifyObservers(Object arg) {
        observers.forEach((obs) -> {
            obs.update(arg);
        });
    }
}
