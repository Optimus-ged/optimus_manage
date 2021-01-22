SELECT  entete_facture.id,designation, detail_facture.qte, produit.pu FROM `detail_facture` INNER JOIN produit ON produit.id=detail_facture.idProduit INNER JOIN entete_facture ON entete_facture.id=detail_facture.idEnteteFacture where entete_facture.id =2

-- Commentaire
-- View de sommation de qte pour details facture
CREATE VIEW sum_detail_fact AS 
SELECT idEnteteFacture, SUM(qte) AS total_qte FROM detail_facture GROUP BY idEnteteFacture

-- Commentaire
-- View principale pour facture
CREATE VIEW view_facture AS SELECT 
ent.id AS id_entete, date_facture, cli.id, nom, prenom, sexe, telephone, 
designation, pu,qte, 
total_qte
FROM client AS cli 
INNER JOIN entete_facture AS ent ON ent.idClient = cli.id
INNER JOIN detail_facture AS det ON ent.id = det.idEnteteFacture
INNER JOIN produit as prod On det.idProduit = prod.id
INNER JOIN sum_detail_fact AS sum ON det.idEnteteFacture = sum.idEnteteFacture

-- Commentaire
-- View de sommation de qte pour details approvisionnement
CREATE VIEW sum_detail_appros AS 
SELECT idEnteteAppro, SUM(qte) AS total_qte FROM detailsappro GROUP BY idEnteteAppro

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
st.id AS id_stock, prod.id AS idProduit, designation, pu, qte AS stock_initial,  (CASE  WHEN total_entre IS NULL THEN 0 ELSE total_entre END) total_entre, (CASE WHEN total_consomme IS NULL THEN 0 ELSE total_consomme END) total_consomme, (CASE WHEN total_consomme IS NULL THEN qte ELSE total_entre - total_consomme END) AS stock_final FROM stock AS st
INNER JOIN produit AS prod ON st.idProduit = prod.id
LEFT JOIN sum_appros_by_produit as sApro ON st.idProduit = sApro.idProduit
LEFT join sum_fact_by_produit as sFact ON st.idProduit = sFact.idProduit
--SELECT idProduit FROM fiche_de_stock WHERE date_fiche_de_stock = DATE_FORMAT(NOW(), "%d-%m-%Y")
--st.id AS id_stock, dateStock as date_mvt, designation, pu, qte AS stock_initial,  (CASE  WHEN total_entre IS NULL THEN 0 ELSE total_entre END) total_entre, (CASE WHEN total_consomme IS NULL THEN 0 ELSE total_consomme END) total_consomme, COALESCE(qte-total_consomme, 0) AS stock_final FROM stock AS st

 SELECT  entete_facture.id,designation, detail_facture.qte, produit.pu FROM `detail_facture` INNER JOIN produit ON produit.id=detail_facture.idProduit INNER JOIN entete_facture ON entete_facture.id=detail_facture.idEnteteFacture where entete_facture.id = 1