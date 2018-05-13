# Pictionnary -- 2017 / 2018 
## Breton Gabriel - 43397

### TODO:
#### Client:
 * Afficher proprement une erreur en GUI si l'utilisateur tente de se connecter sur un mauvais port ou un mauvais host, ou avec un nom vide.
 * Nettoyer le stockage du mot à dessiner lors de la destruction de la fenetre de Drawer.
 * Bonus, afficher les stats pour les abandons.
 
#### Serveur:
 * Le serveur en console n'affiche que très peu d'informations, il pourrait en afficher plus, notamment dans le cas de changements importants.
 * Gérer la deconnexion d'un client : une incohérence survient lorsqu'un client ferme ses fênetres et que le processus tourne toujours. Le serveur le considère toujours comme connecté car le processus tourne dans Netbeans et du coup refuse une nouvelle connexion avec le même login (logique, c'est ce qui est attendu).
 * Lors de la gestion des requetes reçues, nettoyer la redondance qu'il peut y avoir avec le passage en paramètre de memberId et de author (si l'id est le même).
 * Notifier l'autre joueur d'une partie si l'un quitte la partie (en plus de l'affichage du status de la partie qui lui passe déjà à Game over).


