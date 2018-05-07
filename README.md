# Pictionnary -- 2017 - 2018 
## Breton Gabriel - 43397

### Issues
* Lorsqu'un client se connecte pour la première fois et reçoit la liste des tables non vides, c'est à dire que d'autres clients sont connectés sur le serveur et en soit jeu, soit en attente, la vue la vue de ce client ne se met pas à jour.
Le client en question reçoit bien la liste de datatables non vide et l'appel de refreshTableView() - qui doit mettre à jour la vue - ne lance pas d'exception. 
