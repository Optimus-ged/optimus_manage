-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 22 jan. 2021 à 16:53
-- Version du serveur :  10.4.17-MariaDB
-- Version de PHP : 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `optimus_manage`
--

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_agent_del` (`nnom` VARCHAR(100))  BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM agent WHERE nom = nnom);
    DELETE FROM agent WHERE id = iid;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_agent_in` (`iid` INT, `nnom` VARCHAR(50), `pprenom` VARCHAR(50), `ssexe` VARCHAR(1), `ttelephone` VARCHAR(30), `ppost` VARCHAR(50))  BEGIN 
    IF EXISTS(SELECT id FROM agent WHERE id =iid) THEN
        UPDATE `agent` SET `nom`=nnom,`prenom`=pprenom,`telephone`=ttelephone,`sexe`=ssexe,`poste`=ppost 
        WHERE id = iid;
    ELSE
        INSERT INTO `agent`( `nom`, `prenom`, `telephone`, `sexe`, `poste`) VALUES (nnom,pprenom,ttelephone,ssexe,ppost);
    END IF;
    SELECT * FROM agent;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_approDetail_in` (IN `desiProduit_` VARCHAR(50), IN `qte_` FLOAT, IN `idEntAppro_` INT)  BEGIN 
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

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_client_del` (`nnom` VARCHAR(100))  BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM client WHERE nom = nnom);
    DELETE FROM client WHERE id = iid;
    SELECT * FROM client;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_client_in` (`iid` INT, `nnom` VARCHAR(50), `pprenom` VARCHAR(50), `ssexe` VARCHAR(1), `ttelephone` VARCHAR(50))  BEGIN 
    IF EXISTS(SELECT id from client WHERE id = iid) THEN
        UPDATE `client` SET `nom`=nnom,`prenom`=pprenom,`sexe`=ssexe,`telephone`=ttelephone WHERE id = iid;
    ELSE
        INSERT INTO `client`(`nom`, `prenom`, `sexe`, `telephone`) VALUES (nnom,pprenom,ssexe,ttelephone);
    END IF;

    SELECT * FROM client;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_enteteAppro` (IN `nnomFsseur` VARCHAR(50))  BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM fournisseur WHERE nom = nnomFsseur);
    INSERT INTO `entete_appro`( `idFournisseur`, `date_appro`) VALUES (iid, DATE_FORMAT(NOW(), "%d-%m-%Y"));
    SELECT * FROM entete_appro;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_enteteFacture` (IN `nnomClient` VARCHAR(50))  BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM client WHERE nom = nnomClient);
    INSERT INTO `entete_facture`( `idClient`, `date_facture`) VALUES (iid, DATE_FORMAT(NOW(), "%d-%m-%Y"));
    SELECT * FROM entete_facture;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_factDetail_in` (IN `desiProduit_` VARCHAR(50), IN `qte_` FLOAT, IN `idEntFacture_` INT)  BEGIN 
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

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_fournisseur_del` (`nnom` VARCHAR(100))  BEGIN 
    DECLARE iid INT;
    SET iid = (SELECT id FROM fournisseur WHERE nom = nnom);
    DELETE FROM fournisseur WHERE id = iid;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_fournisseur_in` (`iid` INT, `nnom` VARCHAR(50), `addresse` VARCHAR(50), `ttelephone` VARCHAR(50))  BEGIN
    IF EXISTS(SELECT id FROM fournisseur WHERE id=iid)THEN
        UPDATE `fournisseur` SET `nom`=nnom,`addresse`=addresse,`telephone`=ttelephone WHERE id=iid;
    ELSE
        INSERT INTO `fournisseur`(`nom`, `addresse`, `telephone`) VALUES (nnom,addresse,ttelephone);
    END IF;
    SELECT * FROM fournisseur;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_produit_del` (`ddesignation` VARCHAR(100))  BEGIN 
    DECLARE iid INT ;
    SET iid = (SELECT id FROM produit WHERE designation = ddesignation);
    DELETE FROM produit WHERE id = iid;
    SELECT * FROM produit;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_produit_in` (IN `iidproduit` INT, IN `ddesignation` VARCHAR(100), IN `ppu` FLOAT)  BEGIN
    IF EXISTS(SELECT id FROM produit WHERE id = iidproduit) THEN
        UPDATE produit SET
            designation = ddesignation,
            pu = ppu
        WHERE id = iidproduit;

    ELSE
        INSERT INTO produit(designation, pu) VALUES(ddesignation, ppu);
        INSERT INTO stock(idProduit, qte)VALUES((SELECT MAX(id) id FROM produit), 0);
    END IF;

    SELECT * FROM produit;
    SELECT * FROM stock;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `agent`
--

CREATE TABLE `agent` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `telephone` varchar(30) DEFAULT NULL,
  `sexe` varchar(1) DEFAULT NULL,
  `poste` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `agent`
--

INSERT INTO `agent` (`id`, `nom`, `prenom`, `telephone`, `sexe`, `poste`) VALUES
(1, 'samy', 'vukungu', '0987777665', 'm', 'Vendeur');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `sexe` varchar(1) DEFAULT NULL,
  `telephone` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `prenom`, `sexe`, `telephone`) VALUES
(6, 'Optimus', 'ged', 'M', '0000000000'),
(7, 'Isaac', 'matabaro', 'M', '0986765443'),
(8, 'kafene', 'yala', 'F', '0987777665'),
(9, 'salem', 'nzolani', 'F', '0997766554');

-- --------------------------------------------------------

--
-- Structure de la table `detail_appro`
--

CREATE TABLE `detail_appro` (
  `id` int(11) NOT NULL,
  `qte` float DEFAULT NULL,
  `idProduit` int(11) DEFAULT NULL,
  `idEnteteAppro` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `detail_appro`
--

INSERT INTO `detail_appro` (`id`, `qte`, `idProduit`, `idEnteteAppro`) VALUES
(19, 1000, 17, 23),
(20, 500, 17, 24),
(21, 60, 17, 25),
(22, 100, 17, 26);

-- --------------------------------------------------------

--
-- Structure de la table `detail_credit`
--

CREATE TABLE `detail_credit` (
  `id` int(11) NOT NULL,
  `qte` float DEFAULT NULL,
  `idProduit` int(11) DEFAULT NULL,
  `idEnteteCredit` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `detail_facture`
--

CREATE TABLE `detail_facture` (
  `id` int(11) NOT NULL,
  `qte` float DEFAULT NULL,
  `idProduit` int(11) DEFAULT NULL,
  `idEnteteFacture` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `detail_facture`
--

INSERT INTO `detail_facture` (`id`, `qte`, `idProduit`, `idEnteteFacture`) VALUES
(43, 10, 17, 30),
(44, 5, 20, 30),
(45, 15, 19, 30),
(46, 5, 18, 31),
(47, 15, 17, 31),
(48, 2, 19, 31),
(49, 50, 17, 32),
(50, 5, 20, 32),
(51, 5, 17, 33),
(52, 2, 17, 34),
(53, 10, 19, 34),
(54, 10, 17, 35);

-- --------------------------------------------------------

--
-- Structure de la table `entete_appro`
--

CREATE TABLE `entete_appro` (
  `id` int(11) NOT NULL,
  `idFournisseur` int(11) DEFAULT NULL,
  `date_appro` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `entete_appro`
--

INSERT INTO `entete_appro` (`id`, `idFournisseur`, `date_appro`) VALUES
(23, 1, '22-01-2021'),
(24, 1, '22-01-2021'),
(25, 3, '22-01-2021'),
(26, 1, '22-01-2021');

-- --------------------------------------------------------

--
-- Structure de la table `entete_credit`
--

CREATE TABLE `entete_credit` (
  `id` int(11) NOT NULL,
  `idClient` int(11) DEFAULT NULL,
  `date_credit` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `entete_credit`
--

INSERT INTO `entete_credit` (`id`, `idClient`, `date_credit`) VALUES
(1, 6, '01-01-2020');

-- --------------------------------------------------------

--
-- Structure de la table `entete_facture`
--

CREATE TABLE `entete_facture` (
  `id` int(11) NOT NULL,
  `idClient` int(11) DEFAULT NULL,
  `date_facture` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `entete_facture`
--

INSERT INTO `entete_facture` (`id`, `idClient`, `date_facture`) VALUES
(30, 6, '22-01-2021'),
(31, 9, '22-01-2021'),
(32, 7, '22-01-2021'),
(33, 7, '22-01-2021'),
(34, 8, '22-01-2021'),
(35, 7, '22-01-2021');

-- --------------------------------------------------------

--
-- Structure de la table `fiche_de_stock`
--

CREATE TABLE `fiche_de_stock` (
  `id` int(11) NOT NULL,
  `date_fiche_de_stock` varchar(30) DEFAULT NULL,
  `idProduit` int(11) DEFAULT NULL,
  `stock_initial` float DEFAULT NULL,
  `qte_entree` float DEFAULT NULL,
  `qte_consommee` float DEFAULT NULL,
  `stock_final` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `fiche_de_stock`
--

INSERT INTO `fiche_de_stock` (`id`, `date_fiche_de_stock`, `idProduit`, `stock_initial`, `qte_entree`, `qte_consommee`, `stock_final`) VALUES
(1, '22-01-2021', 17, 685, 1660, 25, 1635);

-- --------------------------------------------------------

--
-- Structure de la table `fournisseur`
--

CREATE TABLE `fournisseur` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `addresse` varchar(100) DEFAULT NULL,
  `telephone` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `fournisseur`
--

INSERT INTO `fournisseur` (`id`, `nom`, `addresse`, `telephone`) VALUES
(1, 'connectis', 'centre ville', '0987777665'),
(2, 'ww', 'ww', 'www'),
(3, 'ISIG', 'office', '0812233334');

-- --------------------------------------------------------

--
-- Structure de la table `historique_client`
--

CREATE TABLE `historique_client` (
  `id` int(11) NOT NULL,
  `date_vente` varchar(30) DEFAULT NULL,
  `idClient` int(11) DEFAULT NULL,
  `idProduit` int(11) DEFAULT NULL,
  `qte` float DEFAULT NULL,
  `qte_total` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `historique_client`
--

INSERT INTO `historique_client` (`id`, `date_vente`, `idClient`, `idProduit`, `qte`, `qte_total`) VALUES
(1, '22-01-2021', 7, 17, NULL, 50),
(2, '22-01-2021', 8, 17, NULL, 2),
(3, '22-01-2021', 7, 17, 10, 10);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `designation` varchar(100) DEFAULT NULL,
  `pu` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `designation`, `pu`) VALUES
(17, 'jus', 100),
(18, 'ananas', 300),
(19, 'planche', 2000),
(20, 'maddrier', 3000),
(21, 'clous', 1000);

-- --------------------------------------------------------

--
-- Structure de la table `stock`
--

CREATE TABLE `stock` (
  `id` int(11) NOT NULL,
  `idProduit` int(11) DEFAULT NULL,
  `qte` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `stock`
--

INSERT INTO `stock` (`id`, `idProduit`, `qte`) VALUES
(18, 17, 752),
(19, 18, 5),
(20, 19, 47),
(21, 20, 15),
(22, 21, 0);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `sum_appros_by_produit`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `sum_appros_by_produit` (
`idProduit` int(11)
,`total_entre` double
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `sum_detail_fact`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `sum_detail_fact` (
`idEnteteFacture` int(11)
,`total_qte` double
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `sum_fact_by_produit`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `sum_fact_by_produit` (
`idProduit` int(11)
,`total_consomme` double
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `view_facture`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `view_facture` (
`id_entete` int(11)
,`date_facture` varchar(30)
,`idClient` int(11)
,`nom` varchar(50)
,`prenom` varchar(50)
,`sexe` varchar(1)
,`telephone` varchar(30)
,`id_produit` int(11)
,`designation` varchar(100)
,`pu` float
,`qte` float
,`total_qte` double
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `view_stock`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `view_stock` (
`id_stock` int(11)
,`idProduit` int(11)
,`designation` varchar(100)
,`pu` float
,`stock_initial` float
,`total_entre` double
,`total_consomme` double
,`stock_final` double
);

-- --------------------------------------------------------

--
-- Structure de la vue `sum_appros_by_produit`
--
DROP TABLE IF EXISTS `sum_appros_by_produit`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `sum_appros_by_produit`  AS SELECT `detail_appro`.`idProduit` AS `idProduit`, sum(`detail_appro`.`qte`) AS `total_entre` FROM `detail_appro` GROUP BY `detail_appro`.`idProduit` ;

-- --------------------------------------------------------

--
-- Structure de la vue `sum_detail_fact`
--
DROP TABLE IF EXISTS `sum_detail_fact`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `sum_detail_fact`  AS SELECT `detail_facture`.`idEnteteFacture` AS `idEnteteFacture`, sum(`detail_facture`.`qte`) AS `total_qte` FROM `detail_facture` GROUP BY `detail_facture`.`idEnteteFacture` ;

-- --------------------------------------------------------

--
-- Structure de la vue `sum_fact_by_produit`
--
DROP TABLE IF EXISTS `sum_fact_by_produit`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `sum_fact_by_produit`  AS SELECT `detail_facture`.`idProduit` AS `idProduit`, sum(`detail_facture`.`qte`) AS `total_consomme` FROM `detail_facture` GROUP BY `detail_facture`.`idProduit` ;

-- --------------------------------------------------------

--
-- Structure de la vue `view_facture`
--
DROP TABLE IF EXISTS `view_facture`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_facture`  AS SELECT `ent`.`id` AS `id_entete`, `ent`.`date_facture` AS `date_facture`, `cli`.`id` AS `idClient`, `cli`.`nom` AS `nom`, `cli`.`prenom` AS `prenom`, `cli`.`sexe` AS `sexe`, `cli`.`telephone` AS `telephone`, `prod`.`id` AS `id_produit`, `prod`.`designation` AS `designation`, `prod`.`pu` AS `pu`, `det`.`qte` AS `qte`, `sum`.`total_qte` AS `total_qte` FROM ((((`client` `cli` join `entete_facture` `ent` on(`ent`.`idClient` = `cli`.`id`)) join `detail_facture` `det` on(`ent`.`id` = `det`.`idEnteteFacture`)) join `produit` `prod` on(`det`.`idProduit` = `prod`.`id`)) join `sum_detail_fact` `sum` on(`det`.`idEnteteFacture` = `sum`.`idEnteteFacture`)) ;

-- --------------------------------------------------------

--
-- Structure de la vue `view_stock`
--
DROP TABLE IF EXISTS `view_stock`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_stock`  AS SELECT `st`.`id` AS `id_stock`, `prod`.`id` AS `idProduit`, `prod`.`designation` AS `designation`, `prod`.`pu` AS `pu`, `st`.`qte` AS `stock_initial`, CASE WHEN `sapro`.`total_entre` is null THEN 0 ELSE `sapro`.`total_entre` END AS `total_entre`, CASE WHEN `sfact`.`total_consomme` is null THEN 0 ELSE `sfact`.`total_consomme` END AS `total_consomme`, CASE WHEN `sfact`.`total_consomme` is null THEN `st`.`qte` ELSE `sapro`.`total_entre`- `sfact`.`total_consomme` END AS `stock_final` FROM (((`stock` `st` join `produit` `prod` on(`st`.`idProduit` = `prod`.`id`)) left join `sum_appros_by_produit` `sapro` on(`st`.`idProduit` = `sapro`.`idProduit`)) left join `sum_fact_by_produit` `sfact` on(`st`.`idProduit` = `sfact`.`idProduit`)) ;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `agent`
--
ALTER TABLE `agent`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `detail_appro`
--
ALTER TABLE `detail_appro`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_detAppro` (`idProduit`),
  ADD KEY `fk_detAppro1` (`idEnteteAppro`);

--
-- Index pour la table `detail_credit`
--
ALTER TABLE `detail_credit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_detCre1` (`idProduit`),
  ADD KEY `fk_detCre2` (`idEnteteCredit`);

--
-- Index pour la table `detail_facture`
--
ALTER TABLE `detail_facture`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_detFac1` (`idProduit`),
  ADD KEY `fk_detFac2` (`idEnteteFacture`);

--
-- Index pour la table `entete_appro`
--
ALTER TABLE `entete_appro`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_entAppro` (`idFournisseur`);

--
-- Index pour la table `entete_credit`
--
ALTER TABLE `entete_credit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_entCre` (`idClient`);

--
-- Index pour la table `entete_facture`
--
ALTER TABLE `entete_facture`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_entFac` (`idClient`);

--
-- Index pour la table `fiche_de_stock`
--
ALTER TABLE `fiche_de_stock`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_fiche_stock` (`idProduit`);

--
-- Index pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `historique_client`
--
ALTER TABLE `historique_client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_hisclient` (`idProduit`),
  ADD KEY `fk_hisclient2` (`idClient`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`id`),
  ADD KEY `st_prod` (`idProduit`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `agent`
--
ALTER TABLE `agent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `detail_appro`
--
ALTER TABLE `detail_appro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT pour la table `detail_credit`
--
ALTER TABLE `detail_credit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `detail_facture`
--
ALTER TABLE `detail_facture`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT pour la table `entete_appro`
--
ALTER TABLE `entete_appro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT pour la table `entete_credit`
--
ALTER TABLE `entete_credit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `entete_facture`
--
ALTER TABLE `entete_facture`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT pour la table `fiche_de_stock`
--
ALTER TABLE `fiche_de_stock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `historique_client`
--
ALTER TABLE `historique_client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `stock`
--
ALTER TABLE `stock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `detail_appro`
--
ALTER TABLE `detail_appro`
  ADD CONSTRAINT `fk_detAppro` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`id`),
  ADD CONSTRAINT `fk_detAppro1` FOREIGN KEY (`idEnteteAppro`) REFERENCES `entete_appro` (`id`);

--
-- Contraintes pour la table `detail_credit`
--
ALTER TABLE `detail_credit`
  ADD CONSTRAINT `fk_detCre1` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`id`),
  ADD CONSTRAINT `fk_detCre2` FOREIGN KEY (`idEnteteCredit`) REFERENCES `entete_credit` (`id`);

--
-- Contraintes pour la table `detail_facture`
--
ALTER TABLE `detail_facture`
  ADD CONSTRAINT `fk_detFac1` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`id`),
  ADD CONSTRAINT `fk_detFac2` FOREIGN KEY (`idEnteteFacture`) REFERENCES `entete_facture` (`id`);

--
-- Contraintes pour la table `entete_appro`
--
ALTER TABLE `entete_appro`
  ADD CONSTRAINT `fk_entAppro` FOREIGN KEY (`idFournisseur`) REFERENCES `fournisseur` (`id`);

--
-- Contraintes pour la table `entete_credit`
--
ALTER TABLE `entete_credit`
  ADD CONSTRAINT `fk_entCre` FOREIGN KEY (`idClient`) REFERENCES `client` (`id`);

--
-- Contraintes pour la table `entete_facture`
--
ALTER TABLE `entete_facture`
  ADD CONSTRAINT `fk_entFac` FOREIGN KEY (`idClient`) REFERENCES `client` (`id`);

--
-- Contraintes pour la table `fiche_de_stock`
--
ALTER TABLE `fiche_de_stock`
  ADD CONSTRAINT `fk_fiche_stock` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`id`);

--
-- Contraintes pour la table `historique_client`
--
ALTER TABLE `historique_client`
  ADD CONSTRAINT `fk_hisclient` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`id`),
  ADD CONSTRAINT `fk_hisclient2` FOREIGN KEY (`idClient`) REFERENCES `client` (`id`);

--
-- Contraintes pour la table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `st_prod` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
