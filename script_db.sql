DELIMITER $
CREATE PROCEDURE sp_apptoDetail_in
(
    IN desiProduit_ VARCHAR(50),
    IN qte_ FLOAT,
    IN idEntAppro_ INT
)
BEGIN 
	DECLARE idProduit_ INT;
    SET idProduit_ = (SELECT id FROM produit WHERE designation = desiProduit_);
    
    IF EXISTS(SELECT idProduit FROM detailsappro WHERE idEnteteAppro = idEntAppro_) THEN
        UPDATE detailsappro SET qte = qte+qte_ WHERE idEnteteAppro = idEntAppro_ AND idProduit=idProduit_;
    ELSE
        INSERT INTO detailsappro(idProduit, qte, idEnteteAppro)
        VALUES(idProduit_, qte_, idEntAppro_);
    END IF;
   
END

DELIMITER $
CREATE PROCEDURE sp_factDetail_in
(
    IN desiProduit_ VARCHAR(50),
    IN qte_ FLOAT,
    IN idEntFacture_ INT
)
BEGIN 
	DECLARE idProduit_ INT;
    SET idProduit_ = (SELECT id FROM produit WHERE designation = desiProduit_);
    
    IF NOT EXISTS(SELECT idProduit FROM detail_facture WHERE idEnteteFacture = idEntFacture_) THEN
        INSERT INTO detail_facture(idProduit, qte, idEnteteFacture)
            VALUES(idProduit_, qte_, idEntFacture_);
    ELSE
        UPDATE detail_facture SET qte = qte+qte_ WHERE idEnteteFacture = idEntFacture_ AND idProduit=idProduit_;
    END IF;
   
END

DELIMITER $
CREATE PROCEDURE sp_entete_facture(
	IN _nomClient VARCHAR(50)
)
BEGIN 
	DECLARE _id INT;
    SET _id = (SELECT id FROM client WHERE nom = _nomClient);
    INSERT INTO entete_facture(idClient, dateFacture)
    VALUES(_id, sysdate());
END

//////////////////////////////////////////////////////////////
-- TABLES
CREATE TABLE entete_facture(
	id INT PRIMARY KEY AUTO_INCREMENT,
    idClient INT,
    date_facture VARCHAR(30)
);

ALTER TABLE entete_facture
ADD CONSTRAINT fk_entFac FOREIGN KEY (idClient) REFERENCES client(id)

-- TABLES
CREATE TABLE detail_facture(
	id INT PRIMARY KEY AUTO_INCREMENT,
    qte FLOAT,
    idProduit INT,
    idEnteteFacture INT
)

ALTER TABLE detail_facture
ADD CONSTRAINT fk_detFac1 FOREIGN KEY (idProduit) REFERENCES produit(id)

CREATE TABLE stock(
	id INT PRIMARY KEY AUTO_INCREMENT,
    idProduit INT,
    qte FLOAT
)

ALTER TABLE stock
ADD CONSTRAINT st_prod FOREIGN KEY (idProduit) REFERENCES produit(id)

CREATE TABLE fiche_de_stock(
    id INT PRIMARY KEY AUTO_INCREMENT,
    date_fiche_de_stock VARCHAR(30),
    idProduit INT,
    stock_initial FLOAT,
    qte_entree FLOAT,
    qte_consommee FLOAT,
    stock_final FLOAT
);

ALTER TABLE fiche_de_stock
ADD CONSTRAINT fk_fiche_stock FOREIGN KEY(idProduit)REFERENCES produit(id)



TRUNCATE TABLE entete_facture;
TRUNCATE TABLE detail_facture;
TRUNCATE TABLE approentete;
TRUNCATE TABLE detailsappro;
TRUNCATE TABLE fiche_de_stock;
TRUNCATE TABLE stock;
TRUNCATE TABLE produit;


SELECT  entete_facture.id,designation, detail_facture.qte, produit.pu FROM `detail_facture` INNER JOIN produit ON produit.id=detail_facture.idProduit INNER JOIN entete_facture ON entete_facture.id=detail_facture.idEnteteFacture where entete_facture.id =2

-- VIEWS
-- SOMMATION DES QUANTITES POUR LES DETAILS
CREATE VIEW sum_detail_fact AS 
SELECT idEnteteFacture, SUM(qte) AS total_qte FROM detail_facture GROUP BY idEnteteFacture

-- VIEW PRINCIPALE POUR FACTURE
CREATE VIEW view_facture AS SELECT 
ent.id AS id_entete, date_facture, nom, prenom, sexe, telephone, 
designation, pu,qte, 
total_qte
FROM client AS cli 
INNER JOIN entete_facture AS ent ON ent.idClient = cli.id
INNER JOIN detail_facture AS det ON ent.id = det.idEnteteFacture
INNER JOIN produit as prod On det.idProduit = prod.id
INNER JOIN sum_detail_fact AS sum ON det.idEnteteFacture = sum.idEnteteFacture

-- SOMMATION DES QUANTITES POUR LES APPROS
CREATE VIEW sum_detail_appros AS 
SELECT idEnteteAppro, SUM(qte) AS total_qte FROM detailsappro GROUP BY idEnteteAppro

-- SOMMATION DES QUANTITES PAR ID PRODUIT
CREATE VIEW sum_appros_by_produit AS 
SELECT idProduit, SUM(qte) AS total_entre FROM detailsappro GROUP BY idProduit

CREATE VIEW sum_fact_by_produit AS 
SELECT idProduit, SUM(qte) AS total_consomme FROM detail_facture GROUP BY idProduit

CREATE VIEW stock_by_date AS 
SELECT dateStock, SUM(qte) AS total_consomme FROM detail_facture GROUP BY idProduit


-- VIEW PRINCIPALE POUR APPROS
CREATE VIEW view_stock AS SELECT
st.id AS id_stock, dateStock as date_mvt, prod.id AS idProduit, designation, pu, qte AS stock_initial,  (CASE  WHEN total_entre IS NULL THEN 0 ELSE total_entre END) total_entre, (CASE WHEN total_consomme IS NULL THEN 0 ELSE total_consomme END) total_consomme, (CASE WHEN total_consomme IS NULL THEN qte ELSE total_entre - total_consomme END) AS stock_final FROM stock AS st
INNER JOIN produit AS prod ON st.idProduit = prod.id
LEFT JOIN sum_appros_by_produit as sApro ON st.idProduit = sApro.idProduit
LEFT join sum_fact_by_produit as sFact ON st.idProduit = sFact.idProduit
--SELECT idProduit FROM fiche_de_stock WHERE date_fiche_de_stock = DATE_FORMAT(NOW(), "%d-%m-%Y")
--st.id AS id_stock, dateStock as date_mvt, designation, pu, qte AS stock_initial,  (CASE  WHEN total_entre IS NULL THEN 0 ELSE total_entre END) total_entre, (CASE WHEN total_consomme IS NULL THEN 0 ELSE total_consomme END) total_consomme, COALESCE(qte-total_consomme, 0) AS stock_final FROM stock AS st

 INSERT INTO fiche_de_stock(
    	date_fiche_de_stock,
        idProduit,
        stock_initial,
        qte_entree,
        qte_consommee,
        stock_final
    )VALUES(
    	DATE_FORMAT(NOW(), "%d-%m-%Y"),
        (SELECT COUNT(id) id FROM produit),
        0,
        0,
        0,
        0
    );

