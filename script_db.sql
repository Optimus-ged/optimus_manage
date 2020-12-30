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

TRUNCATE TABLE entete_facture;
TRUNCATE TABLE detail_facture;
TRUNCATE TABLE approentete;
TRUNCATE TABLE detailsappro
