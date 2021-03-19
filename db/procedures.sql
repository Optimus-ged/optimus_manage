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
    DECLARE stock_initial_ FLOAT;
    DECLARE qte_entree_ FLOAT;
    DECLARE qte_consommee_ FLOAT;
    DECLARE stock_final_ FLOAT;

    IF EXISTS(SELECT id FROM produit WHERE id = iidproduit) THEN
        UPDATE produit SET
            designation = ddesignation,
            pu = ppu
        WHERE id = iidproduit;

    ELSE
        INSERT INTO produit(designation, pu) VALUES(ddesignation, ppu);
        INSERT INTO stock(idProduit, qte)VALUES((SELECT MAX(id) id FROM produit), 0);
    END IF;

    SET stock_initial_ = (SELECT stock_initial FROM view_stock WHERE idProduit = idProduit_);
    SET qte_entree_ = (SELECT total_entre FROM view_stock WHERE idProduit = idProduit_);
    SET qte_consommee_ =  (SELECT total_consomme FROM view_stock WHERE idProduit = idProduit_);
    SET stock_final_ = (SELECT stock_final FROM view_stock WHERE idProduit = idProduit_);

    IF NOT EXISTS(SELECT idProduit FROM fiche_de_stock WHERE idProduit = iidproduit AND date_fiche_de_stock = DATE_FORMAT(NOW(), "%d/%m/%Y"))THEN
        INSERT INTO `fiche_de_stock`(
        `date_fiche_de_stock`, 
        `idProduit`, 
        `stock_initial`, 
        `qte_entree`, 
        `qte_consommee`, 
        `stock_final`
        ) VALUES (
            DATE_FORMAT(NOW(), "%d/%m/%Y"),
            iidproduit,
            stock_initial_,
            qte_entree_,
            qte_consommee_,
            stock_final_
        );
    ELSE
        UPDATE `fiche_de_stock` SET 
            `date_fiche_de_stock`= DATE_FORMAT(NOW(), "%d/%m/%Y"),
            `stock_initial`=stock_initial_,
            `qte_entree`=qte_entree_,
            `qte_consommee`=qte_consommee_,
            `stock_final`=stock_final_
            WHERE idProduit = iidproduit AND date_fiche_de_stock = DATE_FORMAT(NOW(), "%d/%m/%Y");
    END IF;
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
    IN `idEntAppro_` INT,
    IN `pu_d_achat_` FLOAT
)
BEGIN 
	DECLARE idProduit_ INT;
    DECLARE stock_initial_ FLOAT;
    DECLARE qte_entree_ FLOAT;
    DECLARE qte_consommee_ FLOAT;
    DECLARE stock_final_ FLOAT;
    SET idProduit_ = (SELECT id FROM produit WHERE designation = desiProduit_);
       
    IF EXISTS(SELECT idProduit FROM detail_appro WHERE idProduit = idProduit_ AND idEnteteAppro = idEntAppro_ ) THEN
        UPDATE detail_appro SET qte = qte+qte_, pu_d_achat = pu_d_achat_ WHERE idEnteteAppro = idEntAppro_ AND idProduit=idProduit_;      
        UPDATE stock SET qte = qte + qte_ WHERE idProduit = idProduit_;
    ELSE
        INSERT INTO detail_appro(idProduit, qte, idEnteteAppro, pu_d_achat)
        VALUES(idProduit_, qte_, idEntAppro_, pu_d_achat_);
        UPDATE stock SET qte = qte + qte_ WHERE idProduit = idProduit_; 
    END IF;
	
    SET stock_initial_ = (SELECT stock_initial FROM view_stock WHERE idProduit = idProduit_);
    SET qte_entree_ = (SELECT total_entre FROM view_stock WHERE idProduit = idProduit_);
    SET qte_consommee_ =  (SELECT total_consomme FROM view_stock WHERE idProduit = idProduit_);
    SET stock_final_ = (SELECT stock_final FROM view_stock WHERE idProduit = idProduit_);

    -- IF NOT EXISTS(SELECT idProduit FROM fiche_de_stock WHERE idProduit = idProduit_ AND date_fiche_de_stock = DATE_FORMAT(NOW(), "%d/%m/%Y"))THEN
    --     INSERT INTO `fiche_de_stock`(
    --     `date_fiche_de_stock`, 
    --     `idProduit`, 
    --     `stock_initial`, 
    --     `qte_entree`, 
    --     `qte_consommee`, 
    --     `stock_final`
    --     ) VALUES (
    --         DATE_FORMAT(NOW(), "%d/%m/%Y"),
    --         idProduit_,
    --         stock_initial_,
    --         qte_entree_,
    --         qte_consommee_,
    --         stock_final_
    --     );
    -- ELSE
    --     UPDATE `fiche_de_stock` SET 
    --         `date_fiche_de_stock`= DATE_FORMAT(NOW(), "%d/%m/%Y"),
    --         `stock_initial`=stock_initial_,
    --         `qte_entree`=qte_entree_,
    --         `qte_consommee`=qte_consommee_,
    --         `stock_final`=stock_final_
    --         WHERE idProduit = idProduit_ AND date_fiche_de_stock = DATE_FORMAT(NOW(), "%d/%m/%Y");
    -- END IF;

END
DELIMITER ;


-- Commentaire
-- Prodedure d'insertion dans la table facture detail
DELIMITER $$
CREATE PROCEDURE  sp_factDetail_in(
    IN `desiProduit_` VARCHAR(50), 
    IN `qte_` FLOAT, 
    IN `pu_de_vente_` FLOAT, 
    IN `idEntFacture_` INT
)
BEGIN 
	DECLARE idProduit_ INT;
    -- DECLARE dateVente_ VARCHAR(30);
    -- DECLARE idClient_ INT;
    -- DECLARE qte2_ FLOAT;
    -- DECLARE total_qte_ FLOAT;
    -- DECLARE prix_vente_ FLOAT;
    -- DECLARE prix_vente_total_ FLOAT;
    -- DECLARE type_vente_ VARCHAR(30);
    -- DECLARE id_entete_ INT;
    -- DECLARE stock_initial_ FLOAT;
    -- DECLARE qte_entree_ FLOAT;
    -- DECLARE qte_consommee_ FLOAT;
    -- DECLARE stock_final_ FLOAT;
    SET idProduit_ = (SELECT id FROM produit WHERE designation = desiProduit_);

    IF EXISTS(SELECT idProduit FROM detail_facture WHERE idProduit = idProduit_ AND idEnteteFacture = idEntFacture_) THEN
        UPDATE detail_facture SET qte = qte + qte_, pu_d_vente = pu_de_vente_ WHERE idProduit=idProduit_  AND idEnteteFacture = idEntFacture_;
        UPDATE stock SET qte = qte - qte_ WHERE idProduit = idProduit_;
    ELSE
        INSERT INTO detail_facture(idProduit, qte, idEnteteFacture, pu_d_vente)
        VALUES(idProduit_, qte_, idEntFacture_, pu_de_vente_);
        UPDATE stock SET qte = qte - qte_ WHERE idProduit = idProduit_;
    END IF;
    
    -- SET dateVente_ = (DATE_FORMAT(NOW(), "%d/%m/%Y"));
    -- SET idClient_ = (SELECT idClient FROM view_facture_finale WHERE id_entete = idEntFacture_ AND id_produit = idProduit_);
    -- SET qte2_ = (SELECT qte FROM view_facture_finale WHERE id_entete = idEntFacture_ AND id_produit = idProduit_);
    -- SET total_qte_ = (SELECT total_qte FROM view_facture_finale WHERE id_entete = idEntFacture_ AND id_produit = idProduit_);
    -- SET prix_vente_ = (SELECT prix_vente FROM view_facture_finale WHERE id_entete = idEntFacture_ AND id_produit = idProduit_);
    -- SET prix_vente_total_ = (SELECT prix_vente_total FROM view_facture_finale WHERE id_entete = idEntFacture_ AND id_produit = idProduit_); 
    -- SET type_vente_ = (SELECT type_vente FROM view_facture_finale WHERE id_entete = idEntFacture_ AND id_produit = idProduit_);
    -- SET id_entete_ = (SELECT id_entete FROM view_facture_finale WHERE id_entete = idEntFacture_ AND id_produit = idProduit_);  
    -- SET stock_initial_ = (SELECT stock_initial FROM view_stock WHERE idProduit = idProduit_);
    -- SET qte_entree_ = (SELECT total_entre FROM view_stock WHERE idProduit = idProduit_);
    -- SET qte_consommee_ =  (SELECT total_consomme FROM view_stock WHERE idProduit = idProduit_);
    -- SET stock_final_ = (SELECT stock_final FROM view_stock WHERE idProduit = idProduit_);

    -- IF NOT EXISTS(SELECT idProduit FROM fiche_de_stock WHERE idProduit = idProduit_ AND date_fiche_de_stock = DATE_FORMAT(NOW(), "%d/%m/%Y"))THEN
    --     INSERT INTO `fiche_de_stock`(
    --     `date_fiche_de_stock`, 
    --     `idProduit`, 
    --     `stock_initial`, 
    --     `qte_entree`, 
    --     `qte_consommee`, 
    --     `stock_final`
    --     ) VALUES (
    --         DATE_FORMAT(NOW(), "%d/%m/%Y"),
    --         idProduit_,
    --         stock_initial_,
    --         qte_entree_,
    --         qte_consommee_,
    --         stock_final_
    --     );
    -- ELSE
    --     UPDATE `fiche_de_stock` SET 
    --         `date_fiche_de_stock`= DATE_FORMAT(NOW(), "%d/%m/%Y"),
    --         `stock_initial`=stock_initial_,
    --         `qte_entree`=qte_entree_,
    --         `qte_consommee`=qte_consommee_,
    --         `stock_final`=stock_final_
    --         WHERE idProduit = idProduit_ AND date_fiche_de_stock = DATE_FORMAT(NOW(), "%d/%m/%Y");
    -- END IF;
    
    -- IF EXISTS(SELECT idProduit FROM historique_client WHERE idProduit =idProduit_  AND id_entete = idEntFacture_  )THEN
    --     UPDATE `historique_client` SET 
    --     `date_vente`= dateVente_,
    --     `idClient`=  idClient_,
    --     `idProduit`= idProduit_,
    --     `qte`= qte2_,
    --     `qte_total`= total_qte_,
    --     `prix_vente`= prix_vente_,
    --     `prix_vente_total`= prix_vente_total_,
    --     `type_vente`= type_vente_,
    --     `id_entete`= id_entete_ 
    --     WHERE idProduit =idProduit_  AND id_entete = idEntFacture_ ;
            
    --     ELSE
    --     	INSERT INTO `historique_client`(
    --         `date_vente`, 
    --         `idClient`, 
    --         `idProduit`, 
    --         `qte`, 
    --         `qte_total`,
    --         `prix_vente`, 
    --         `prix_vente_total`,
    --         `type_vente`,
    --         `id_entete`
    --         ) VALUES (
    --         dateVente_,
    --         idClient_,
    --         idProduit_,
    --         qte2_,
    --         total_qte_,
    --         prix_vente_,
    --         prix_vente_total_,
    --         type_vente_,
    --         id_entete_
    --         );
    --   END IF;

    IF EXISTS (SELECT id FROM entete_facture WHERE id = idEntFacture_ AND type_vente = 2) THEN
        IF NOT EXISTS(SELECT id FROM paiement WHERE idEnteteFacture = idEntFacture_)THEN 
            INSERT INTO `paiement`(
                `idEnteteFacture`, 
                `datePaiement`, 
                `montantPaye`
            ) VALUES (
                idEntFacture_,
                DATE_FORMAT(NOW(), "%d/%m/%Y"),
                0
            );
        END IF;
    END IF;
END
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

-- Commentaire
-- procedure d'enregistrement paiement
DELIMITER $
CREATE PROCEDURE sp_paiement_in(
    iidEnteteFacture INT,
    mmontantPayt FLOAT
)
BEGIN 
    IF EXISTS(SELECT id FROM paiement WHERE idEnteteFacture = iidEnteteFacture) THEN 
        UPDATE paiement SET 
            datePaiement = DATE_FORMAT(NOW(), "%d/%m/%Y"),
            montantPaye =montantPaye + mmontantPayt
        WHERE idEnteteFacture = iidEnteteFacture;
    END IF;
END


