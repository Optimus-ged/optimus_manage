-- Commentaire
-- Prodedure d'insertion dans la table client
DELIMITER $
CREATE PROCEDURE sp_produit_in
(
    iidproduit INT,
    ddesignation VARCHAR(100),
    ppu FLOAT
)
BEGIN
    IF EXISTS(SELECT id FROM produit WHERE id = iidproduit) THEN
        UPDATE produit SET
            designation = ddesignation,
            pu = ppu
        WHERE id = iidproduit;

    ELSE
        INSERT INTO produit(designation, pu) VALUES(ddesignation, ppu);
        INSERT INTO stock(idProduit, qte)VALUES(iidproduit, 0);
    END IF;

    SELECT * FROM produit;
    SELECT * FROM stock;
END

-- Commentaire
-- Prodedure de suppression dans la table produit
DELIMITER $
CREATE PROCEDURE sp_produit_del
(
    iid INT
)
BEGIN 
    DELETE FROM produit WHERE id = iid;
END

-- Commentaire
-- Prodedure d'insertion dans la table agent
DELIMITER $
CREATE PROCEDURE sp_agent_in
(
    iid INT,
    nnom VARCHAR(50),
    pprenom VARCHAR(50),
    ssexe VARCHAR(1),
    ttelephone VARCHAR(30),
    ppost VARCHAR(50),
)
BEGIN 
    IF EXISTS(SELECT id FROM agent WHERE id =iid) THEN
        UPDATE `agent` SET `nom`=nnom,`prenom`=pprenom,`telephone`=ttelephone,`sexe`=ssexe,`poste`=ppost 
        WHERE id = iid;
    ELSE
        INSERT INTO `agent`( `nom`, `prenom`, `telephone`, `sexe`, `poste`) VALUES (nnom,pprenom,ttelephone,ssexe,ppost);
    END IF;
    SELECT * FROM agent;
END

-- Commentaire
-- Prodedure de suppression dans la table agent
DELIMITER $
CREATE PROCEDURE sp_agent_del
(
    iid INT
)
BEGIN 
    DELETE FROM agent WHERE id = iid;
END

-- Commentaire
-- Prodedure d'insertion dans la table fournisseur
DELIMITER $
CREATE PROCEDURE sp_fournisseur_in
(
    iid INT,
    nnom VARCHAR(50),
    addresse VARCHAR(50),
    ttelephone VARCHAR(50),
)
BEGIN
    IF EXISTS(SELECT id FROM fournisseur WHERE id=iid)THEN
        UPDATE `fournisseur` SET `nom`=nnom,`addresse`=addresse,`telephone`=ttelephone WHERE id=iid;
    ELSE
        INSERT INTO `fournisseur`(`nom`, `addresse`, `telephone`) VALUES (nnom,addresse,ttelephone);
    END IF;
    SELECT * FROM fournisseur;
END

-- Commentaire
-- Prodedure de suppression dans la table fournisseur
DELIMITER $
CREATE PROCEDURE sp_fournisseur_del
(
    iid INT
)
BEGIN 
    DELETE FROM fournisseur WHERE id = iid;
END

-- Commentaire
-- Prodedure d'insertion dans la table client
DELIMITER $
CREATE PROCEDURE sp_client_in
(
    iid INT,
    nnom VARCHAR(50),
    pprenom VARCHAR(50),
    ssexe VARCHAR(1),
    ttelephone VARCHAR(50),
)
BEGIN 
    IF EXISTS(SELECT id from client WHERE id = iid) THEN
        UPDATE `client` SET `nom`=nnom,`prenom`=pprenom,`sexe`=ssexe,`telephone`=ttelephone WHERE id = iid;
    ELSE
        INSERT INTO `client`(`nom`, `prenom`, `sexe`, `telephone`) VALUES (nnom,pprenom,ssexe,ttelephone);
    END IF;

    SELECT * FROM client;
END

-- Commentaire
-- Prodedure de suppression dans la table client
DELIMITER $
CREATE PROCEDURE sp_client_del
(
    iid INT
)
BEGIN 
    DELETE FROM client WHERE id = iid;
END

-- Commentaire
-- Prodedure d'insertion dans la table entete appro
DELIMITER $
CREATE PROCEDURE sp_enteteAppro
(
    nnomFsseur VARCHAR(50)
)
BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM fournisseur WHERE nom = nnomFsseur);
    INSERT INTO `entete_appro`( `idFournisseur`, `date_appro`) VALUES (iid, DATE_FORMAT(NOW(), "%d-%m-%Y"));
END


-- Commentaire
-- Prodedure d'insertion dans la table entete facture
DELIMITER $
CREATE PROCEDURE sp_enteteFacture
(
    nnomClient VARCHAR(50)
)
BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM client WHERE nom = nnomClient);
    INSERT INTO `entete_facture`( `idClient`, `date_facture`) VALUES (iid, DATE_FORMAT(NOW(), "%d-%m-%Y"));
END


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


