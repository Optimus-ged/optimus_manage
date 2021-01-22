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
    DECLARE id_prod_stock INT;
    IF EXISTS(SELECT id FROM produit WHERE id = iidproduit) THEN
        UPDATE produit SET
            designation = ddesignation,
            pu = ppu
        WHERE id = iidproduit;

    ELSE
        SET id_prod_stock = 
        INSERT INTO produit(designation, pu) VALUES(ddesignation, ppu);
        INSERT INTO stock(idProduit, qte)VALUES((SELECT COUNT(id)+1 FROM produit), 0);
    END IF;

    SELECT * FROM produit;
    SELECT * FROM stock;
END

-- Commentaire
-- Prodedure de suppression dans la table produit
DELIMITER $
CREATE PROCEDURE sp_produit_del
(
    ddesignation VARCHAR(100)
)
BEGIN 
    DECLARE iid INT ;
    SET iid = (SELECT id FROM produit WHERE designation = ddesignation);
    DELETE FROM produit WHERE id = iid;
    SELECT * FROM produit;
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
    nnom VARCHAR(100)
)
BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM agent WHERE nom = nnom);
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
    nnom VARCHAR(100)
)
BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM fournisseur WHERE nom = nnom);
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
    nnom VARCHAR(100)
)
BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM client WHERE nom = nnom);
    DELETE FROM client WHERE id = iid;
    SELECT * FROM client;
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
    SELECT * FROM entete_appro;
END

-- Commentaire
-- Prodedure d'insertion dans la table appro details
DELIMITER $$
CREATE PROCEDURE sp_approDetail_in (
    IN `desiProduit_` VARCHAR(50), 
    IN `qte_` FLOAT, 
    IN `idEntAppro_` INT
)
BEGIN 
	DECLARE idProduit_ INT;
    SET idProduit_ = (SELECT id FROM produit WHERE designation = desiProduit_);
       
    IF EXISTS(SELECT idProduit FROM detail_appro WHERE idProduit = idProduit_ AND idEnteteAppro = idEntAppro_ ) THEN
        UPDATE detail_appro SET qte = qte_ WHERE idEnteteAppro = idEntAppro_ AND idProduit=idProduit_;
        
        UPDATE stock SET qte = qte_ WHERE idProduit = idProduit_;
    ELSE
        INSERT INTO detail_appro(idProduit, qte, idEnteteAppro)
        VALUES(idProduit_, qte_, idEntAppro_);
        
        UPDATE stock SET qte = qte + qte_ WHERE idProduit = idProduit_; 
    END IF;

    INSERT INTO `fiche_de_stock`(
        `date_fiche_de_stock`, 
        `idProduit`, 
        `stock_initial`, 
        `qte_entree`, 
        `qte_consommee`, 
        `stock_final`
    ) VALUES (
        DATE_FORMAT(NOW(), "%d-%m-%Y"),
        idProduit_,
        (SELECT stock_initial FROM view_stock WHERE idProduit = idProduit_),
        (SELECT total_entre FROM view_stock WHERE idProduit = idProduit_),
        (SELECT total_consomme FROM view_stock WHERE idProduit = idProduit_),
        (SELECT stock_final FROM view_stock WHERE idProduit = idProduit_)
    );

    SELECT * FROM entete_appro;
    SELECT * FROM detail_appro;
    SELECT * FROM stock;
END$$
DELIMITER ;


-- Commentaire
-- Prodedure d'insertion dans la table facture detail
DELIMITER $$
CREATE PROCEDURE  sp_factDetail_in(
    IN `desiProduit_` VARCHAR(50), 
    IN `qte_` FLOAT, 
    IN `idEntFacture_` INT
)
BEGIN 
	DECLARE idProduit_ INT;
    SET idProduit_ = (SELECT id FROM produit WHERE designation = desiProduit_);
       
    IF EXISTS(SELECT idProduit FROM detail_facture WHERE idProduit = idProduit_ AND idEnteteFacture = idEntFacture_) THEN
    
        UPDATE detail_facture SET qte = qte_ WHERE idProduit=idProduit_  AND idEnteteFacture = idEntFacture_;
        
UPDATE stock SET qte = qte+ qte_ WHERE idProduit = idProduit_;
    ELSE
     INSERT INTO detail_facture(idProduit, qte, idEnteteFacture)
            VALUES(idProduit_, qte_, idEntFacture_);
       UPDATE stock SET qte = qte+ qte_ WHERE idProduit = idProduit_;
    END IF;

   INSERT INTO `historique_client`(
       `date_vente`, 
       `idClient`, 
       `idProduit`, 
       `qte`, 
       `qte_total`
    ) VALUES (
        (SELECT date_facture FROM view_facture WHERE id_entete = idEntFacture_),
        (SELECT idClient FROM view_facture WHERE id_entete = idEntFacture_),
        idProduit_,
        (SELECT qte FROM view_facture WHERE id_produit = idProduit_ AND id_entete = idEntFacture_),
        (SELECT total_qte FROM view_facture WHERE id_entete = idEntFacture_)
    );

    SELECT * FROM entete_facture;
    SELECT * FROM detail_facture;
   
END$$
DELIMITER ;


-- Commentaire
-- Prodedure d'insertion dans la table entete facture
DELIMITER $
CREATE PROCEDURE sp_entete_facture(
	IN _nomClient VARCHAR(50)
)
BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM client WHERE nom = nnomClient);
    INSERT INTO `entete_facture`( `idClient`, `date_facture`) VALUES (iid, DATE_FORMAT(NOW(), "%d-%m-%Y"));
    SELECT * FROM entete_facture;
END


