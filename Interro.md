# Interro

## 1 - Modifications dans la BD
* Ca marche

## 2 - Modifications suite aux changements dans le schéma

* Les proposition se rajoutent bien dans la BD -> AdminFacade.addProposition()
* Les modifications de la table Game sont bien prises en comptes.
* Possibilité, pour un jeu donné, d'aller chercher dans la BD toutes les propositions du mot à deviner -> AdminFacade.getAllBadPropositions()
* On peut savoir combien de fois un mot a été proposé, toute parties confondues. -> AdminFacade.getNumberBadPropositions()
* On peut savoir le nombre moyen de propositions incorrectes pour un mot donné. -> AdminFacade.getAverageNumberProps()

## 3 - Modifications des affichages
* Le client du drawer affiche désormais le nombre moyen de propositions pour le mot à dessiner.
* Le client affiche un historique qui n'est pas le bon.