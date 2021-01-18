DELIMITER $
CREATE PROCEDURE sp_approDetail_in
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


