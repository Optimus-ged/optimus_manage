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
    date_facture VARCHAR(30)
);

ALTER TABLE entete_facture
ADD CONSTRAINT fk_entFac FOREIGN KEY (idClient) REFERENCES client(id)

-- Commentaire
-- Creation de la table entete approvisionnement
CREATE TABLE entete_appro(
	id INT PRIMARY KEY AUTO_INCREMENT,
    idFournisseur INT,
    date_appro VARCHAR(30)
);

ALTER TABLE entete_appro
ADD CONSTRAINT fk_entAppro FOREIGN KEY (idFournisseur) REFERENCES fournisseur(id)


-- Commentaire
-- Creation de la table entete credit
CREATE TABLE entete_credit(
	id INT PRIMARY KEY AUTO_INCREMENT,
    idClient INT,
    date_credit VARCHAR(30)
);

ALTER TABLE entete_credit
ADD CONSTRAINT fk_entCre FOREIGN KEY (idClient) REFERENCES client(id)

-- Commentaire
-- Creation de la table details approvisionnement
CREATE TABLE detail_appro(
	id INT PRIMARY KEY AUTO_INCREMENT,
    qte FLOAT,
    idProduit INT,
    idEnteteAppro INT
)

ALTER TABLE detail_appro
ADD CONSTRAINT fk_detAppro FOREIGN KEY (idProduit) REFERENCES produit(id)

ALTER TABLE detail_appro
ADD CONSTRAINT fk_detAppro1 FOREIGN KEY (idEnteteAppro) REFERENCES entete_appro(id)

-- Commentaire
-- Creation de la table details facture
CREATE TABLE detail_facture(
	id INT PRIMARY KEY AUTO_INCREMENT,
    qte FLOAT,
    idProduit INT,
    idEnteteFacture INT
)

ALTER TABLE detail_facture
ADD CONSTRAINT fk_detFac1 FOREIGN KEY (idProduit) REFERENCES produit(id);

ALTER TABLE detail_facture
ADD CONSTRAINT fk_detFac2 FOREIGN KEY (idEnteteFacture) REFERENCES entete_facture(id)


-- Commentaire
-- Creation de la table details credit
CREATE TABLE detail_credit(
	id INT PRIMARY KEY AUTO_INCREMENT,
    qte FLOAT,
    idProduit INT,
    idEnteteCredit INT
)

ALTER TABLE detail_credit
ADD CONSTRAINT fk_detCre1 FOREIGN KEY (idProduit) REFERENCES produit(id);

ALTER TABLE detail_credit
ADD CONSTRAINT fk_detCre2 FOREIGN KEY (idEnteteCredit) REFERENCES entete_credit(id)

-- Commentaire
-- Creation de la table Stock
CREATE TABLE stock(
	id INT PRIMARY KEY AUTO_INCREMENT,
    idProduit INT,
    qte FLOAT
)

ALTER TABLE stock
ADD CONSTRAINT st_prod FOREIGN KEY (idProduit) REFERENCES produit(id)

-- Commentaire
-- Creation de la table fiche de stock
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