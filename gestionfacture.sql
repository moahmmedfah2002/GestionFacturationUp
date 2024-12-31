-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 28, 2024 at 06:26 PM
-- Server version: 8.3.0
-- PHP Version: 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestionfacture`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telephone` int NOT NULL,
  `idUser` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `telephone` (`telephone`) USING BTREE,
  UNIQUE KEY `email` (`email`(250)) USING BTREE,
  KEY `ClientUser` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `id` int NOT NULL AUTO_INCREMENT,
  `commandedate` date NOT NULL,
  `totalamount` float NOT NULL,
  `idClient` int NOT NULL,
  `statuePaiement` tinyint(1) NOT NULL,
  `idPaiement` int DEFAULT NULL,
  `idUser` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CommandeClient` (`idClient`),
  KEY `CommandePaiement` (`idPaiement`),
  KEY `CommandeUser` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `detailcommande`
--

DROP TABLE IF EXISTS `detailcommande`;
CREATE TABLE IF NOT EXISTS `detailcommande` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantite` int NOT NULL,
  `idProduit` int NOT NULL,
  `idCommande` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `DetailCommandeProduit` (`idProduit`),
  KEY `DetailCommandeCommande` (`idCommande`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `facture`
--

DROP TABLE IF EXISTS `facture`;
CREATE TABLE IF NOT EXISTS `facture` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idCommande` int NOT NULL,
  `idUser` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FactureCommande` (`idCommande`),
  KEY `FactureUser` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `typenotification` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `datemessage` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `paiement`
--

DROP TABLE IF EXISTS `paiement`;
CREATE TABLE IF NOT EXISTS `paiement` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `idCommande` int NOT NULL,
  `idUser` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `PaiementCommand` (`idCommande`),
  KEY `PaiementUser` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
CREATE TABLE IF NOT EXISTS `permissions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUser` int NOT NULL,
  `permission` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `PermissionUser` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `prix` float NOT NULL,
  `quantitéDisponible` int NOT NULL DEFAULT '0',
  `idUser` int NOT NULL,
  `tva` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nom` (`nom`(250)) USING BTREE,
  KEY `ProduitUser` (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` int NOT NULL,
  `password` int NOT NULL,
  `role` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `password` (`password`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `ClientUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `CommandeClient` FOREIGN KEY (`idClient`) REFERENCES `client` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `CommandePaiement` FOREIGN KEY (`idPaiement`) REFERENCES `paiement` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `CommandeUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `detailcommande`
--
ALTER TABLE `detailcommande`
  ADD CONSTRAINT `DetailCommandeCommande` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `DetailCommandeProduit` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `FactureCommande` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `FactureUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `paiement`
--
ALTER TABLE `paiement`
  ADD CONSTRAINT `PaiementCommand` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `PaiementUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `permissions`
--
ALTER TABLE `permissions`
  ADD CONSTRAINT `PermissionUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `ProduitUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
