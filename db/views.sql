SELECT  entete_facture.id,designation, detail_facture.qte, produit.pu FROM `detail_facture` INNER JOIN produit ON produit.id=detail_facture.idProduit INNER JOIN entete_facture ON entete_facture.id=detail_facture.idEnteteFacture where entete_facture.id =2

-- Commentaire
-- View de sommation de qte pour details facture
CREATE VIEW sum_detail_fact AS 
SELECT idEnteteFacture, SUM(qte) AS total_qte FROM detail_facture GROUP BY idEnteteFacture

-- Commentaire
-- View de sommation prix de vente (PAT)
CREATE VIEW sum_prix_vente AS 
SELECT id_entete, SUM(prix_vente) AS prix_vente_total FROM view_facture GROUP BY id_entete

-- Commentaire
-- View principale pour facture
CREATE VIEW view_facture AS SELECT 
ent.id AS id_entete, date_facture,(CASE WHEN type_vente = 1 THEN 'cash' ELSE 'credit' END) type_vente, cli.id AS idClient, nom, prenom, sexe, telephone, 
prod.id as id_produit,designation, pu,qte, (pu*qte) AS prix_vente,
total_qte
FROM client AS cli 
INNER JOIN entete_facture AS ent ON ent.idClient = cli.id
INNER JOIN detail_facture AS det ON ent.id = det.idEnteteFacture
INNER JOIN produit as prod On det.idProduit = prod.id
INNER JOIN sum_detail_fact AS sum ON det.idEnteteFacture = sum.idEnteteFacture 
ORDER BY id_entete ASC
-- WHERE type_vente = 1

-- Commentaire
-- View definitive pour facture
CREATE VIEW view_facture_finale AS SELECT 
f.id_entete, date_facture,(CASE WHEN type_vente=1 THEN "Cash" ELSE "Credit" END) AS  type_vente, idClient, nom, prenom, sexe, telephone,
id_produit,designation, pu,qte, prix_vente, total_qte, prix_vente_total
FROM view_facture AS f
INNER JOIN sum_prix_vente AS v ON f.id_entete = v.id_entete

-- Commentaire
-- view principale pour credit facture
CREATE VIEW view_facture_credit AS SELECT 
ent.id AS id_entete, date_facture,type_vente, cli.id AS idClient, nom, prenom, sexe, telephone, 
prod.id as id_produit,designation, pu,qte, 
total_qte
FROM client AS cli 
INNER JOIN entete_facture AS ent ON ent.idClient = cli.id
INNER JOIN detail_facture AS det ON ent.id = det.idEnteteFacture
INNER JOIN produit as prod On det.idProduit = prod.id
INNER JOIN sum_detail_fact AS sum ON det.idEnteteFacture = sum.idEnteteFacture
WHERE type_vente = 2

-- Commentaire
-- View de sommation de qte pour details approvisionnement
CREATE VIEW sum_detail_appros AS 
SELECT idEnteteAppro, SUM(qte) AS total_qte FROM detail_appro GROUP BY idEnteteAppro

-- Commentaire
-- View pour sommation de qte par id pour approvisionnement
CREATE VIEW sum_appros_by_produit AS 
SELECT idProduit, SUM(qte) AS total_entre FROM detail_appro GROUP BY idProduit

-- Commentaire
-- View pour sommation de qte par id pour facture
CREATE VIEW sum_fact_by_produit AS 
SELECT idProduit, SUM(qte) AS total_consomme FROM detail_facture GROUP BY idProduit

-- Commentaire
-- View pour sommation qte pour stock
CREATE VIEW stock_by_date AS 
SELECT dateStock, SUM(qte) AS total_consomme FROM detail_facture GROUP BY idProduit

-- Commentaire
-- View principale pour approvisionnement
CREATE VIEW view_stock AS SELECT
st.id AS id_stock,ent.date_appro, prod.id AS idProduit, designation, pu, qte AS stock_initial,  (CASE  WHEN total_entre IS NULL THEN 0 ELSE total_entre END) total_entre, (CASE WHEN total_consomme IS NULL THEN 0 ELSE total_consomme END) total_consomme, (CASE WHEN total_consomme IS NULL THEN qte ELSE total_entre - total_consomme END) AS stock_final FROM stock AS st
INNER JOIN produit AS prod ON st.idProduit = prod.id
INNER JOIN entete_appro AS ent ON ent.
LEFT JOIN sum_appros_by_produit as sApro ON st.idProduit = sApro.idProduit
LEFT join sum_fact_by_produit as sFact ON st.idProduit = sFact.idProduit
--SELECT idProduit FROM fiche_de_stock WHERE date_fiche_de_stock = DATE_FORMAT(NOW(), "%d-%m-%Y")
--st.id AS id_stock, dateStock as date_mvt, designation, pu, qte AS stock_initial,  (CASE  WHEN total_entre IS NULL THEN 0 ELSE total_entre END) total_entre, (CASE WHEN total_consomme IS NULL THEN 0 ELSE total_consomme END) total_consomme, COALESCE(qte-total_consomme, 0) AS stock_final FROM stock AS st

 SELECT  entete_facture.id,designation, detail_facture.qte, produit.pu FROM `detail_facture` INNER JOIN produit ON produit.id=detail_facture.idProduit INNER JOIN entete_facture ON entete_facture.id=detail_facture.idEnteteFacture where entete_facture.id = 1

 SELECT ent.id, nom, telephone, addresse FROM entete_appro AS ent
INNER JOIN fournisseur AS f ON f.id = ent.idFournisseur

-- Commentaire
-- View pour paiement
SELECT `id_entete`, datePaiement, `type_vente`, `idClient`, `nom`, `prenom`, `sexe`, 
`telephone`, `id_produit`, `designation`, `pu`, `qte`, `prix_vente`, `total_qte`, 
`prix_vente_total` AS montant_a_paye, montantPaye AS montant_deja_paye, (prix_vente_total-montantPaye) AS reste
FROM `view_facture_finale` as fi
INNER JOIN paiement AS p ON fi.id_entete = p.idEnteteFacture

SELECT 
e.id, idProduit,pu, qte, designation, (pu*qte) AS total_a_payer
FROM detail_facture AS d
INNER JOIN produit AS p ON d.idProduit = p.id
INNER JOIN entete_facture AS e ON d.idEnteteFacture = e.id
WHERE type_vente = 2

CREATE VIEW view_detail_pyt_2 AS 
SELECT id, SUM(total_a_payer) AS A_payer FROM view_detail_pyt_1 GROUP BY id

CREATE VIEW view_detail_pyt_3 AS
SELECT p.id, idEnteteFacture, datePaiement, A_payer, montantPaye AS deja_paye, (A_payer - montantPaye) AS reste
FROM view_detail_pyt_2 AS v 
INNER JOIN paiement AS p ON v.id = p.idEnteteFacture

-- Commentaire
-- view facture credit finale
CREATE VIEW facture_credit_finale SELECT 
f.id_entete, datepaiement,(CASE WHEN type_vente=1 THEN "Cash" ELSE "Credit" END) AS  type_vente, idClient, nom, prenom, sexe, telephone,
id_produit,designation, pu,qte, prix_vente, total_qte, prix_vente_total AS total_a_payer,
montantpaye, (prix_vente_total - montantpaye) AS reste
FROM view_facture AS f
INNER JOIN sum_prix_vente AS v ON f.id_entete = v.id_entete
INNER JOIN paiement AS p ON f.id_entete = p.idEnteteFacture

-- Commentaire
-- View de liaison entre entete facture et client
CREATE VIEW view_entete_facture AS SELECT
e.id, e.idClient, type_vente, date_facture,
nom, prenom, sexe,  telephone
FROM entete_facture AS e
INNER JOIN client AS c ON c.id = e.idClient

-- Commentaire
-- View de selection de l

-- Commntaire
-- Une autre view de stock
SELECT p.id, date_fiche_de_stock, idProduit, designation, pu, stock_initial, 
qte_entree, qte_consommee, stock_final
FROM produit as p 
INNER JOIN fiche_de_stock AS f WHERE f.idProduit = p.id

