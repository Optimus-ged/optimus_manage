-- Commentaire
-- Creation de la table client
CREATE TABLE client(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    sexe VARCHAR(1),
    telephone VARCHAR(30)
);

-- Commentaire
-- Creation de la table agent
CREATE TABLE agent(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    telephone VARCHAR(30)
    sexe VARCHAR(1),
    poste VARCHAR(30)
);

-- Commentaire
-- Creation de la table fournisseur
CREATE TABLE fournisseur(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100),
    addresse VARCHAR(100),
    telephone VARCHAR(30)
);

-- Commentaire
-- Creation de la table produit
CREATE TABLE produit(
    id INT PRIMARY KEY AUTO_INCREMENT,
    designation VARCHAR(100),
    pu FLOAT,
);

-- Commentaire
-- Creation de la table enete facture
CREATE TABLE entete_facture(
	id INT PRIMARY KEY AUTO_INCREMENT,
    idClient INT,
    type_vente INT,
    date_facture VARCHAR(30),
    CONSTRAINT fk_entFac FOREIGN KEY (idClient) REFERENCES client(id)
);


-- Commentaire
-- Creation de la table details facture
CREATE TABLE detail_facture(
	id INT PRIMARY KEY AUTO_INCREMENT,
    qte FLOAT,
    idProduit INT,
    pu_d_vente FLOAT,
    idEnteteFacture INT,
    CONSTRAINT fk_detFac1 FOREIGN KEY (idProduit) REFERENCES produit(id),
    CONSTRAINT fk_detFac2 FOREIGN KEY (idEnteteFacture) REFERENCES entete_facture(id)
)

ALTER TABLE detail_appro
ADD pu_d_achat FLOAT,


-- Commentaire
-- Creation de la table entete approvisionnement
CREATE TABLE entete_appro(
	id INT PRIMARY KEY AUTO_INCREMENT,
    idFournisseur INT,
    date_appro VARCHAR(30),
    CONSTRAINT fk_entAppro FOREIGN KEY (idFournisseur) REFERENCES fournisseur(id)
);

-- Commentaire
-- Creation de la table details approvisionnement
CREATE TABLE detail_appro(
	id INT PRIMARY KEY AUTO_INCREMENT,
    qte FLOAT,
    idProduit INT,
    idEnteteAppro INT,
    CONSTRAINT fk_detAppro FOREIGN KEY (idProduit) REFERENCES produit(id),
    CONSTRAINT fk_detAppro1 FOREIGN KEY (idEnteteAppro) REFERENCES entete_appro(id)
)

-- Commentaire
-- CReation de la table paiement
CREATE TABLE paiement(
    id INT PRIMARY KEY AUTO_INCREMENT,
    idEnteteFacture INT,
    datePaiement VARCHAR(30),
    montantPaye FLOAT
);

ALTER TABLE paiement
ADD CONSTRAINT fk_pyt FOREIGN KEY (idEnteteFacture) REFERENCES entete_facture(id);

-- Commentaire
-- Creation de la table entete credit
CREATE TABLE entete_credit(
	id INT PRIMARY KEY AUTO_INCREMENT,
    idClient INT,
    date_credit VARCHAR(30),
    CONSTRAINT fk_entCre FOREIGN KEY (idClient) REFERENCES client(id)
); 

-- Commentaire
-- Creation de la table details credit
CREATE TABLE detail_credit(
	id INT PRIMARY KEY AUTO_INCREMENT,
    qte FLOAT,
    idProduit INT,
    idEnteteCredit INT,
    CONSTRAINT fk_detCre1 FOREIGN KEY (idProduit) REFERENCES produit(id),
    CONSTRAINT fk_detailCredit FOREIGN KEY (idEnteteCredit) REFERENCES entete_credit(id)
);

-- Commentaire
-- Creation de la table Stock
CREATE TABLE stock(
	id INT PRIMARY KEY AUTO_INCREMENT,
    idProduit INT,
    qte FLOAT,
    CONSTRAINT st_prod FOREIGN KEY (idProduit) REFERENCES produit(id)
)

-- Commentaire
-- Creation de la table fiche de stock
CREATE TABLE fiche_de_stock(
    id INT PRIMARY KEY AUTO_INCREMENT,
    date_fiche_de_stock VARCHAR(30),
    idProduit INT,
    stock_initial FLOAT,
    qte_entree FLOAT,
    qte_consommee FLOAT,
    stock_final FLOAT,
    CONSTRAINT fk_fiche_stock FOREIGN KEY(idProduit)REFERENCES produit(id)
);




CREATE TABLE historique_client(
    id INT PRIMARY KEY AUTO_INCREMENT,
    date_vente VARCHAR(30),
    idClient INT
    idProduit INT,
    qte FLOAT,
    qte_total FLOAT,
    prix_vente FLOAT,
    prix_vente_total FLOAT,
    type_vente VARCHAR(30),
    id_entete INT,
    CONSTRAINT fk_hisclient FOREIGN KEY(idProduit)REFERENCES produit(id),
    CONSTRAINT fk_hisclient2 FOREIGN KEY(idClient)REFERENCES client(id)
);

ALTER TABLE historique_client
ADD CONSTRAINT fk_hisclient FOREIGN KEY(idProduit)REFERENCES produit(id);

ALTER TABLE historique_client
ADD CONSTRAINT fk_hisclient2 FOREIGN KEY(idClient)REFERENCES client(id);

ALTER TABLE historique_client
ADD prix_vente FLOAT;

ALTER TABLE historique_client
ADD prix_vente_total FLOAT;

ALTER TABLE historique_client
ADD type_vente VARCHAR(30);

ALTER TABLE historique_client
ADD id_entete INT;

TRUNCATE TABLE entete_facture;
TRUNCATE TABLE detail_facture;
TRUNCATE TABLE approentete;
TRUNCATE TABLE detailsappro;
TRUNCATE TABLE fiche_de_stock;
TRUNCATE TABLE stock;
TRUNCATE TABLE produit;


TRUNCATE detail_facture;
TRUNCATE paiement;
TRUNCATE entete_facture;
TRUNCATE detail_appro;
TRUNCATE entete_appro;
TRUNCATE stock;
TRUNCATE fiche_de_stock;
TRUNCATE historique_client;
TRUNCATE produit;
TRUNCATE fournisseur;
TRUNCATE client;