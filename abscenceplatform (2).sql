-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 05, 2025 at 09:12 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `abscenceplatform`
--

-- --------------------------------------------------------

--
-- Table structure for table `absence`
--

CREATE TABLE `absence` (
  `abs_ratt_id` bigint(20) NOT NULL,
  `acceptee` enum('non','oui') DEFAULT NULL,
  `date_au_plus_tot` date DEFAULT NULL,
  `date_au_plus_tard` date DEFAULT NULL,
  `pinned` enum('non','oui') DEFAULT NULL,
  `seancedb` enum('S1','S2','S3','S4','S5') DEFAULT NULL,
  `seancefin` enum('S1','S2','S3','S4','S5') DEFAULT NULL,
  `enseignant_user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `absence`
--

INSERT INTO `absence` (`abs_ratt_id`, `acceptee`, `date_au_plus_tot`, `date_au_plus_tard`, `pinned`, `seancedb`, `seancefin`, `enseignant_user_id`) VALUES
(1, NULL, '2025-05-06', '2025-05-10', NULL, 'S1', 'S4', 1),
(202, 'oui', '2025-05-11', '2025-05-13', NULL, 'S1', 'S5', 25);

-- --------------------------------------------------------

--
-- Table structure for table `abs_epinglee`
--

CREATE TABLE `abs_epinglee` (
  `id_usr` bigint(20) NOT NULL,
  `id_abs` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `motdepass` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `numtel` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`user_id`, `email`, `motdepass`, `nom`, `prenom`, `numtel`) VALUES
(1, 'admin@gmail.com', '$2a$10$XKiq2/0MQcvLPSrJMyy12OYfB9/gISZvKaXLUsGUJfPOf.NkwjfCa', 'Adem', 'Weslati', '22536222');

-- --------------------------------------------------------

--
-- Table structure for table `emploi_temps`
--

CREATE TABLE `emploi_temps` (
  `classe` enum('DEUXIEME','PREMIERE','TROISIEME') NOT NULL,
  `groupe` enum('A','B','C','D','E') NOT NULL,
  `jourr` enum('JEUDI','LUNDI','MARDI','MERCREDI','SAMEDI','VENDREDI') NOT NULL,
  `specialite` enum('GSI','GSIL','INFO','MECA') NOT NULL,
  `seance1_id` int(11) DEFAULT NULL,
  `seance2_id` int(11) DEFAULT NULL,
  `seance3_id` int(11) DEFAULT NULL,
  `seance4_id` int(11) DEFAULT NULL,
  `seance5_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `emploi_temps`
--

INSERT INTO `emploi_temps` (`classe`, `groupe`, `jourr`, `specialite`, `seance1_id`, `seance2_id`, `seance3_id`, `seance4_id`, `seance5_id`) VALUES
('DEUXIEME', 'A', 'JEUDI', 'INFO', 22, 20, 11, 25, NULL),
('DEUXIEME', 'A', 'LUNDI', 'INFO', 15, 15, 16, 17, NULL),
('DEUXIEME', 'A', 'MARDI', 'INFO', 18, 19, 20, NULL, NULL),
('DEUXIEME', 'A', 'MERCREDI', 'INFO', 21, 22, 24, NULL, NULL),
('DEUXIEME', 'A', 'SAMEDI', 'INFO', NULL, NULL, 17, NULL, NULL),
('DEUXIEME', 'A', 'VENDREDI', 'INFO', 16, 12, 25, 25, 26),
('DEUXIEME', 'B', 'JEUDI', 'INFO', 20, 11, 18, 17, NULL),
('DEUXIEME', 'B', 'LUNDI', 'INFO', 28, 21, 22, 22, NULL),
('DEUXIEME', 'B', 'MARDI', 'INFO', 29, 5, 24, 7, NULL),
('DEUXIEME', 'B', 'MERCREDI', 'INFO', 20, 19, 15, 15, NULL),
('DEUXIEME', 'B', 'SAMEDI', 'INFO', 23, NULL, NULL, NULL, NULL),
('DEUXIEME', 'B', 'VENDREDI', 'INFO', 27, 28, 25, NULL, NULL),
('DEUXIEME', 'C', 'JEUDI', 'INFO', 11, 28, 24, 11, NULL),
('DEUXIEME', 'C', 'LUNDI', 'INFO', 22, 22, NULL, NULL, NULL),
('DEUXIEME', 'C', 'MARDI', 'INFO', 29, 18, 28, 17, NULL),
('DEUXIEME', 'C', 'MERCREDI', 'INFO', 27, 21, 19, 23, NULL),
('DEUXIEME', 'C', 'SAMEDI', 'INFO', 26, 16, NULL, NULL, NULL),
('DEUXIEME', 'C', 'VENDREDI', 'INFO', 11, 24, 25, 20, NULL),
('PREMIERE', 'A', 'JEUDI', 'INFO', 8, 7, 5, 10, 11),
('PREMIERE', 'A', 'LUNDI', 'INFO', 1, 2, 3, 4, 5),
('PREMIERE', 'A', 'MARDI', 'INFO', 1, 6, 2, NULL, NULL),
('PREMIERE', 'A', 'MERCREDI', 'INFO', 6, 7, 3, NULL, NULL),
('PREMIERE', 'A', 'SAMEDI', 'INFO', 12, 12, NULL, NULL, 11),
('PREMIERE', 'A', 'VENDREDI', 'INFO', 8, 9, NULL, 10, NULL),
('PREMIERE', 'B', 'JEUDI', 'INFO', 13, 12, 3, 7, NULL),
('PREMIERE', 'B', 'LUNDI', 'INFO', 12, NULL, 1, NULL, NULL),
('PREMIERE', 'B', 'MARDI', 'INFO', 6, 1, 7, NULL, NULL),
('PREMIERE', 'B', 'MERCREDI', 'INFO', 13, 6, 11, NULL, NULL),
('PREMIERE', 'B', 'SAMEDI', 'INFO', 15, 15, NULL, NULL, 11),
('PREMIERE', 'B', 'VENDREDI', 'INFO', 13, 9, 10, 14, NULL),
('PREMIERE', 'C', 'JEUDI', 'INFO', 7, 5, 8, NULL, NULL),
('PREMIERE', 'C', 'LUNDI', 'INFO', 2, 1, 9, 4, 15),
('PREMIERE', 'C', 'MARDI', 'INFO', 3, 5, NULL, 1, NULL),
('PREMIERE', 'C', 'MERCREDI', 'INFO', 7, 13, NULL, NULL, NULL),
('PREMIERE', 'C', 'SAMEDI', 'INFO', 11, 11, NULL, NULL, 9),
('PREMIERE', 'C', 'VENDREDI', 'INFO', 10, 14, 31, 15, NULL),
('PREMIERE', 'D', 'JEUDI', 'INFO', 12, 5, 9, NULL, NULL),
('PREMIERE', 'D', 'LUNDI', 'INFO', 21, 17, 16, 14, 17),
('PREMIERE', 'D', 'MARDI', 'INFO', NULL, 3, 13, 30, NULL),
('PREMIERE', 'D', 'MERCREDI', 'INFO', 13, 5, 8, NULL, NULL),
('PREMIERE', 'D', 'SAMEDI', 'INFO', 26, 26, NULL, NULL, NULL),
('PREMIERE', 'D', 'VENDREDI', 'INFO', 10, 11, 8, 15, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `enseignant`
--

CREATE TABLE `enseignant` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `motdepass` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `nb_absences` int(11) NOT NULL,
  `num_tel` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enseignant`
--

INSERT INTO `enseignant` (`user_id`, `email`, `motdepass`, `nom`, `prenom`, `grade`, `nb_absences`, `num_tel`) VALUES
(1, 'abidi@example.com', '$2a$10$nOYrxieFvMB7ZkdTsjhnE.DSn/ixDyhHaCCugRoQvp5Y7xwN7qc8a', 'Abidi', 'Ch.', 'Professor', 0, NULL),
(2, 'achour@example.com', '$2a$10$GY9Ex4swccxgL9k4LGnA4ual/g.Cwg8i7kAHXgeh5IqUHxVou7wjC', 'Achour', 'Mme', 'Associate Professor', 0, NULL),
(3, 'aloui@example.com', '$2a$10$fFA6wVNqymfumV28rTfra.06sB8nMEpJQu1zksX9VkLdprGmMOaA2', 'Aloui', 'Mr', 'Professor', 0, NULL),
(4, 'bahri@example.com', '$2a$10$1w5RgIOBhODkzYPXgFiCbOKRm.PdQmCOvz/MbOHisJd4WZwANUGQe', 'Bahri', 'Mr', 'Professor', 0, NULL),
(5, 'barhoumi@example.com', '$2a$10$5TKcdD3OuiU7xCyGGbWzCuOxMV1KvzwC4ntLDq0iz3HCP6OGBTV6a', 'Barhoumi', 'Mr', 'Assistant Professor', 0, NULL),
(6, 'bedhief@example.com', '$2a$10$1gJk39kp9N3afgWqGoDrru3IyC4ieBVgej3RuYdUpuimFX8eAt5wy', 'Bedhief', 'Mme', 'Professor', 0, NULL),
(7, 'bennasr@example.com', '$2a$10$Z7uM9lUZQXPkoB.xylwCheOdcPwa.1E7vfREivf/SWtxh4lhTlF/y', 'Ben Nasr', 'Mlle', 'Associate Professor', 0, NULL),
(8, 'bensalem@example.com', '$2a$10$m/XH8A3HSjSEpFMNaN67.e5mXYhJmUsckfMQu3C/G0bVdWebON4Qi', 'BenSalem', 'Mr', 'Professor', 0, NULL),
(9, 'benslimen@example.com', '$2a$10$AujuhJFCuDQ5w.uvZCR1huNj8vPbl3zGwwsdJkDNRdWzac2jBHuf.', 'BenSlimen', 'Mr', 'Assistant Professor', 0, NULL),
(10, 'bessouda@example.com', '$2a$10$ZbWWvtcx9HpgoNQ0wV0Bie62v5DTB03ROubqwudjJXBheowuo0s0G', 'Bessouda', 'Mme', 'Assistant Professor', 0, NULL),
(11, 'bouchima@example.com', '$2a$10$G.mYVCyUrWG758CqFEvlNejEgiw3rJFZhGJPmONhjE7iatciHhjoK', 'Bouchima', 'Mr', 'Professor', 0, NULL),
(12, 'boukhris@example.com', '$2a$10$JoYxZypY.Yj72bbMoBL6FewJIM016UXsN/U9kTSSM7JmqlNqAeZuy', 'Boukhris', 'H.', 'Associate Professor', 0, NULL),
(13, 'chaabani@example.com', '$2a$10$4x9rpn/T0Fcfk9H86PSZNeJipzL1wBlidoPa/AxyZCJ0589v12Kae', 'Chaabani', 'Mme', 'Associate Professor', 0, NULL),
(14, 'chammem@example.com', '$2a$10$w.cVas7EnWRTgfja4K1Zbu9dKycUMVn/BER.6beZC67LwK2QJc3gS', 'Chammem', 'Mme', 'Professor', 0, NULL),
(15, 'daassi@example.com', '$2a$10$D9t.9C9szLpGNlnqVm8OnuNU/MoUI3CKkKgQzu7FzwojLDHmc59ke', 'Daassi', 'Mme', 'Assistant Professor', 0, NULL),
(16, 'debbech@example.com', '$2a$10$GPETupt2gdOP4QxJ7lRxhOhoKAHk0G4h0WFKBFEk6NwAIsyKbe.nK', 'Debbech', 'Mme', 'Assistant Professor', 0, NULL),
(17, 'elbedoui@example.com', '$2a$10$rfs/oi2saqakT2x5Xei/xO5Qa1SngSK8/yJ310wLMqn8dIIwYRRse', 'Elbedoui', 'Mme', 'Associate Professor', 0, NULL),
(18, 'fkaier@example.com', '$2a$10$KW9niz2nPB1Bh/TPlt5fEuVVdGBikXQ39IrfKbdDcLLupqBFEhsFa', 'Fkaier', 'Mr', 'Associate Professor', 0, NULL),
(19, 'fourati@example.com', '$2a$10$vFdz/lyQlLwnedIKrlUrWuHm.KZChFeF4VzG.HvLLIsRKNI4z.dXW', 'Fourati', 'Mme', 'Professor', 0, NULL),
(20, 'gharbi@example.com', '$2a$10$ddhhZqj59fWfefK0EvdN8uMupIZOPbJxImVzHxNtHnjN7spNZyU1.', 'Gharbi', 'Mme', 'Associate Professor', 0, NULL),
(21, 'ghazouani@example.com', '$2a$10$xNRMhBiHDcewIkwmBnHcRuUxHx1T2rAXxjsXYNVPhMISex5uNVnaG', 'Ghazouani', 'Mr', 'Associate Professor', 0, NULL),
(22, 'gueddana@example.com', '$2a$10$oRugjvLVZezCUkYPPEvltOktB0rl6fi1reic5jye3GR9eC/ljUWQi', 'Gueddana', 'Mr', 'Associate Professor', 0, NULL),
(23, 'hamda@example.com', '$2a$10$cd4avVejxmtSroSkdygLvei4/TcMb7ilCmeHJ9tFT7HQXLT/luUdu', 'Hamda', 'H.', 'Professor', 0, NULL),
(24, 'hermassi@example.com', '$2a$10$q2mEfNoQ46BH/iLYinKBBuZB1aHAI3h/8JmrReQxCDCOo0lC6R.3e', 'Hermassi', 'Mr', 'Associate Professor', 0, NULL),
(25, 'jaidi@example.com', '$2a$10$G8eFQzMT28b08GaLZL8CyeJjsHXihW7FXptpfdDzpGDld7lfq3ZZK', 'Jaidi', 'M.', 'Associate Professor', 1, NULL),
(26, 'jandoubi@example.com', '$2a$10$iZilOqDjP9YlGfo2WSjRmOu7OmeU2KA.eWdfBnXiCNqEGyIat6k.6', 'Jandoubi', 'Mr', 'Assistant Professor', 0, NULL),
(27, 'kammoun@example.com', '$2a$10$qZT0rewtoXiAbp2.CLSWMOPIbqDNalpzDFMkS7gISmZdUwLyFFoG2', 'Kammoun', 'Mme', 'Assistant Professor', 0, NULL),
(28, 'karoui@example.com', '$2a$10$MJfQGZx9Jut29S.Tl.u62.XXLnaXLpeb1LT/Y3jCUB0DyM.ouAE2m', 'Karoui', 'S.', 'Professor', 0, NULL),
(29, 'lamouchi@example.com', '$2a$10$IVVY/qdt3OqaV8Eja8Tev.Jid8rna5R5W2gRXIDKGY0APUpnCTKXW', 'Lamouchi', 'Mme', 'Professor', 0, NULL),
(30, 'louati@example.com', '$2a$10$hnaBnPzszinurKqnCALolugG4AlWJhgk9PU7At6oO/xCyxd4IMYSm', 'Louati', 'Mme', 'Assistant Professor', 0, NULL),
(31, 'melliti@example.com', '$2a$10$RNhTFDI9gBstY.I.Vc260uhUnLHaYlfqgirFPIw0yYFa5FXrWbmi6', 'Melliti', 'Mr', 'Associate Professor', 0, NULL),
(32, 'moussa@example.com', '$2a$10$qxqW67y.QWogWl5d46jIqOsK814S/3I9I8K739rawdF2..rDu2EwG', 'Moussa', 'Mme', 'Professor', 0, NULL),
(33, 'nouira@example.com', '$2a$10$I97R5Vgl0A0dcwAviv8aaeITIHSYEeWkTJCvOnD5vn0frz1W6ChkG', 'Nouira', 'Mme', 'Assistant Professor', 0, NULL),
(34, 'said@example.com', '$2a$10$9jw72bc/h7w6RWfatQEece0A3lJ7REV.SVBUEc7ks0fDsQzGeS/RW', 'Said', 'S.', 'Professor', 0, NULL),
(35, 'simac@example.com', '$2a$10$.jh6fnqa0THhwh6Y79j3Nu02iiKY.cRTk8coi1kyhzoLpJzqMVcMi', 'Simac', 'Mme', 'Professor', 0, NULL),
(36, 'slimene@example.com', '$2a$10$wnC5pMTztTRYzkk/v9vfXuzPUcIVDU79XzzNo17UawRW/j9UFVTQ.', 'Slimène', 'Mme', 'Assistant Professor', 0, NULL),
(37, 'sl31@example.com', '$2a$10$.TXgsMSnN2l.NLFGw.gfTOFwdEwnBHcXCTMx8LTBN1s/saTOa.UJW', 'Sl31', 'Mr', 'Assistant Professor', 0, NULL),
(38, 'yaiche@example.com', '$2a$10$ZUCE7y0vR0SEcClj.3cUe.c2AJNMMrBF4PgImaUN2kf.qXExRzWv6', 'Yaiche', 'Mme', 'Associate Professor', 0, NULL),
(39, 'yahyaoui@example.com', '$2a$10$gVPZlHE5tcMuhSxWW8Cq0.dVjjPQ99XeYUMcCQGMUEqY.RA.gE5ry', 'Yahyaoui', 'Mme', 'Professor', 0, NULL),
(40, 'yousra@example.com', '$2a$10$2zApHexXMHM80BJZFDzrQ.v1UQ7BlmdGYMelvfGSj/AMP0M8Jb376', 'Yousra', 'Mme', 'Professor', 0, NULL),
(41, 'zouari@example.com', '$2a$10$2LPD854SrXNZBgnAQo0O1uF23xLSvHIz1fNcG/SINP7DphFjEsVA2', 'Zouari', 'H.', 'Associate Professor', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `etudiant`
--

CREATE TABLE `etudiant` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `motdepass` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `classe` enum('DEUXIEME','PREMIERE','TROISIEME') DEFAULT NULL,
  `groupe` enum('A','B','C','D','E') DEFAULT NULL,
  `specialite` enum('GSI','GSIL','INFO','MECA') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `etudiant`
--

INSERT INTO `etudiant` (`user_id`, `email`, `motdepass`, `nom`, `prenom`, `classe`, `groupe`, `specialite`) VALUES
(102, 'alaeddine.madani@enicar.ucar.tn', '$2a$10$zSkNaJFfaRURS8Ut6dfjVOIXrENBqEEMNfYWeaRgJzXT.8dUUYvee', 'Madani', 'Ala Eddine', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `evenement`
--

CREATE TABLE `evenement` (
  `event_id` int(11) NOT NULL,
  `isannulee` enum('non','oui') DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `date_de_debut` date DEFAULT NULL,
  `date_de_fin` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `seancedb` enum('S1','S2','S3','S4','S5') DEFAULT NULL,
  `seancefin` enum('S1','S2','S3','S4','S5') DEFAULT NULL,
  `salle_salle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequences`
--

CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) NOT NULL,
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hibernate_sequences`
--

INSERT INTO `hibernate_sequences` (`sequence_name`, `next_val`) VALUES
('default', 300);

-- --------------------------------------------------------

--
-- Table structure for table `rattrappage`
--

CREATE TABLE `rattrappage` (
  `abs_ratt_id` bigint(20) NOT NULL,
  `matiere` varchar(255) DEFAULT NULL,
  `acceptee` enum('non','oui') DEFAULT NULL,
  `classe` enum('DEUXIEME','PREMIERE','TROISIEME') DEFAULT NULL,
  `date_affectee` date DEFAULT NULL,
  `date_au_plus_tot` date DEFAULT NULL,
  `date_au_plus_tard` date DEFAULT NULL,
  `groupe` enum('A','B','C','D','E') DEFAULT NULL,
  `pinned` enum('non','oui') DEFAULT NULL,
  `seance_aff` enum('S1','S2','S3','S4','S5') DEFAULT NULL,
  `seancedb` enum('S1','S2','S3','S4','S5') DEFAULT NULL,
  `seancefin` enum('S1','S2','S3','S4','S5') DEFAULT NULL,
  `specialite` enum('GSI','GSIL','INFO','MECA') DEFAULT NULL,
  `enseignant_user_id` bigint(20) DEFAULT NULL,
  `salle_salle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rattrappage`
--

INSERT INTO `rattrappage` (`abs_ratt_id`, `matiere`, `acceptee`, `classe`, `date_affectee`, `date_au_plus_tot`, `date_au_plus_tard`, `groupe`, `pinned`, `seance_aff`, `seancedb`, `seancefin`, `specialite`, `enseignant_user_id`, `salle_salle`) VALUES
(2, 'Developpement Web', NULL, 'PREMIERE', NULL, '2025-05-11', '2025-05-17', 'A', 'non', NULL, NULL, NULL, 'INFO', 1, NULL),
(52, 'Plateforme de DEV', NULL, 'DEUXIEME', NULL, '2025-05-06', '2025-05-14', 'A', 'non', NULL, NULL, NULL, 'INFO', 25, NULL),
(152, 'Developpement Web', 'oui', 'PREMIERE', '2025-05-06', '2025-05-06', '2025-05-19', 'D', 'non', 'S4', NULL, NULL, 'INFO', 25, 'S21'),
(153, 'Developpement Web', 'oui', 'PREMIERE', '2025-05-23', '2025-05-18', '2025-05-24', 'C', 'non', 'S3', NULL, NULL, 'INFO', 25, 'SL MAC');

-- --------------------------------------------------------

--
-- Table structure for table `ratt_epinglee`
--

CREATE TABLE `ratt_epinglee` (
  `id_usr` bigint(20) NOT NULL,
  `id_ratt` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `salle`
--

CREATE TABLE `salle` (
  `salle` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `salle`
--

INSERT INTO `salle` (`salle`) VALUES
('S21'),
('S22'),
('S23'),
('S24'),
('S25'),
('S30'),
('S32'),
('S34'),
('SL MAC'),
('SL27'),
('SL31'),
('SL33'),
('SL36');

-- --------------------------------------------------------

--
-- Table structure for table `seance`
--

CREATE TABLE `seance` (
  `id` int(11) NOT NULL,
  `israttrappage` enum('non','oui') DEFAULT NULL,
  `matiere` varchar(255) DEFAULT NULL,
  `enseignant_user_id` bigint(20) DEFAULT NULL,
  `rattrapage_abs_ratt_id` bigint(20) DEFAULT NULL,
  `salle_salle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `seance`
--

INSERT INTO `seance` (`id`, `israttrappage`, `matiere`, `enseignant_user_id`, `rattrapage_abs_ratt_id`, `salle_salle`) VALUES
(1, 'non', 'Fond. RX Info', 4, NULL, 'SL36'),
(2, 'non', 'Bases de données', 32, NULL, 'S22'),
(3, 'non', 'Prob. & Stat', 12, NULL, 'S32'),
(4, 'non', 'Théor. des Organ.', 28, NULL, 'S24'),
(5, 'non', 'Archi. Sys. Microproc.', 11, NULL, 'S34'),
(6, 'non', 'Struct. de Données', 12, NULL, 'S21'),
(7, 'non', 'P.O.O', 10, NULL, 'SL33'),
(8, 'non', 'Analyse & Conception', 15, NULL, 'S23'),
(9, 'non', 'Cult. Communication', 14, NULL, 'S24'),
(10, 'non', 'Technologie Web', 26, NULL, 'SL31'),
(11, 'non', 'Analyse Numérique 2', 3, NULL, 'S30'),
(12, 'non', 'Analyse & Conception', 41, NULL, 'S25'),
(13, 'non', 'Archi. Sys. Microproc.', 22, NULL, 'S25'),
(14, 'non', 'Professional English', 36, NULL, 'SL MAC'),
(15, 'non', 'Intelli. Artificielle', 19, NULL, 'S30'),
(16, 'non', 'Prog. Sys. & Rx', 25, NULL, 'SL31'),
(17, 'non', 'Admin. Sys. et Rx', 20, NULL, 'SL33'),
(18, 'non', 'Plateformes de Dev.', 25, NULL, 'S30'),
(19, 'non', 'Routage des Rx', 30, NULL, 'SL27'),
(20, 'non', 'TLA & Compilation', 40, NULL, 'S32'),
(21, 'non', 'Business English', 36, NULL, 'SL MAC'),
(22, 'non', 'Syst. embarqués', 11, NULL, 'S34'),
(23, 'non', 'Sécur. Informatique', 24, NULL, 'SL33'),
(24, 'non', 'Analyse des Données', 27, NULL, 'S30'),
(25, 'non', 'Management de Projet', 39, NULL, 'S32'),
(26, 'non', 'Admin. Sys. et Rx', 31, NULL, 'S22'),
(27, 'non', 'Développement Mobile', 8, NULL, 'S22'),
(28, 'non', 'Prog, Sys, & Rx', 2, NULL, 'SL31'),
(29, 'non', 'Comm. en Entreprise', 6, NULL, 'S24'),
(30, 'oui', 'Developpement Web', 25, 152, 'S21'),
(31, 'oui', 'Developpement Web', 25, 153, 'SL MAC');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absence`
--
ALTER TABLE `absence`
  ADD PRIMARY KEY (`abs_ratt_id`),
  ADD KEY `FK7ankjeeqg2945fx4fulkmoif8` (`enseignant_user_id`);

--
-- Indexes for table `abs_epinglee`
--
ALTER TABLE `abs_epinglee`
  ADD KEY `FKb4ko8ykw2l8w41csf1lahmvgr` (`id_abs`),
  ADD KEY `FKk55u74vlqft8k49me7hsdwkw` (`id_usr`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Indexes for table `emploi_temps`
--
ALTER TABLE `emploi_temps`
  ADD PRIMARY KEY (`classe`,`groupe`,`jourr`,`specialite`),
  ADD KEY `FK62vl06p7bymdnhhw1tf03oo5i` (`seance1_id`),
  ADD KEY `FK9hjqwnc40k3v2qscdb9vl6wgv` (`seance2_id`),
  ADD KEY `FKi68u9l5o42nmk0dw752uhlcpo` (`seance3_id`),
  ADD KEY `FK90nplej8b5e0uc0u1fiallwa6` (`seance4_id`),
  ADD KEY `FKn2ovh1brcpia5udxb26r7ena7` (`seance5_id`);

--
-- Indexes for table `enseignant`
--
ALTER TABLE `enseignant`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Indexes for table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Indexes for table `evenement`
--
ALTER TABLE `evenement`
  ADD PRIMARY KEY (`event_id`),
  ADD KEY `FK68w8hmv66r43ow9somwuidakm` (`salle_salle`);

--
-- Indexes for table `hibernate_sequences`
--
ALTER TABLE `hibernate_sequences`
  ADD PRIMARY KEY (`sequence_name`);

--
-- Indexes for table `rattrappage`
--
ALTER TABLE `rattrappage`
  ADD PRIMARY KEY (`abs_ratt_id`),
  ADD KEY `FK7ju5belvqh9e4nx787pj5hnj6` (`enseignant_user_id`),
  ADD KEY `FKdrjy38ua9pmh0i2l4tced033c` (`salle_salle`);

--
-- Indexes for table `ratt_epinglee`
--
ALTER TABLE `ratt_epinglee`
  ADD KEY `FKlrsv6di7qihqk1lo3gisko67t` (`id_ratt`),
  ADD KEY `FKj8gkvjectmbgqqvpjvuhrq7mx` (`id_usr`);

--
-- Indexes for table `salle`
--
ALTER TABLE `salle`
  ADD PRIMARY KEY (`salle`);

--
-- Indexes for table `seance`
--
ALTER TABLE `seance`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK2l7t611jn58sck0uybnake8nx` (`rattrapage_abs_ratt_id`),
  ADD KEY `FK6d5rcwgnxkyg8cff50a6c6ine` (`enseignant_user_id`),
  ADD KEY `FKhxtlgbtpf1fqg33sghhjtfrwd` (`salle_salle`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `seance`
--
ALTER TABLE `seance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `absence`
--
ALTER TABLE `absence`
  ADD CONSTRAINT `FK7ankjeeqg2945fx4fulkmoif8` FOREIGN KEY (`enseignant_user_id`) REFERENCES `enseignant` (`user_id`);

--
-- Constraints for table `abs_epinglee`
--
ALTER TABLE `abs_epinglee`
  ADD CONSTRAINT `FKb4ko8ykw2l8w41csf1lahmvgr` FOREIGN KEY (`id_abs`) REFERENCES `absence` (`abs_ratt_id`),
  ADD CONSTRAINT `FKk55u74vlqft8k49me7hsdwkw` FOREIGN KEY (`id_usr`) REFERENCES `etudiant` (`user_id`);

--
-- Constraints for table `emploi_temps`
--
ALTER TABLE `emploi_temps`
  ADD CONSTRAINT `FK62vl06p7bymdnhhw1tf03oo5i` FOREIGN KEY (`seance1_id`) REFERENCES `seance` (`id`),
  ADD CONSTRAINT `FK90nplej8b5e0uc0u1fiallwa6` FOREIGN KEY (`seance4_id`) REFERENCES `seance` (`id`),
  ADD CONSTRAINT `FK9hjqwnc40k3v2qscdb9vl6wgv` FOREIGN KEY (`seance2_id`) REFERENCES `seance` (`id`),
  ADD CONSTRAINT `FKi68u9l5o42nmk0dw752uhlcpo` FOREIGN KEY (`seance3_id`) REFERENCES `seance` (`id`),
  ADD CONSTRAINT `FKn2ovh1brcpia5udxb26r7ena7` FOREIGN KEY (`seance5_id`) REFERENCES `seance` (`id`);

--
-- Constraints for table `evenement`
--
ALTER TABLE `evenement`
  ADD CONSTRAINT `FK68w8hmv66r43ow9somwuidakm` FOREIGN KEY (`salle_salle`) REFERENCES `salle` (`salle`);

--
-- Constraints for table `rattrappage`
--
ALTER TABLE `rattrappage`
  ADD CONSTRAINT `FK7ju5belvqh9e4nx787pj5hnj6` FOREIGN KEY (`enseignant_user_id`) REFERENCES `enseignant` (`user_id`),
  ADD CONSTRAINT `FKdrjy38ua9pmh0i2l4tced033c` FOREIGN KEY (`salle_salle`) REFERENCES `salle` (`salle`);

--
-- Constraints for table `ratt_epinglee`
--
ALTER TABLE `ratt_epinglee`
  ADD CONSTRAINT `FKj8gkvjectmbgqqvpjvuhrq7mx` FOREIGN KEY (`id_usr`) REFERENCES `etudiant` (`user_id`),
  ADD CONSTRAINT `FKlrsv6di7qihqk1lo3gisko67t` FOREIGN KEY (`id_ratt`) REFERENCES `rattrappage` (`abs_ratt_id`);

--
-- Constraints for table `seance`
--
ALTER TABLE `seance`
  ADD CONSTRAINT `FK6d5rcwgnxkyg8cff50a6c6ine` FOREIGN KEY (`enseignant_user_id`) REFERENCES `enseignant` (`user_id`),
  ADD CONSTRAINT `FKenowurun8dtd2958i0bp6yhkl` FOREIGN KEY (`rattrapage_abs_ratt_id`) REFERENCES `rattrappage` (`abs_ratt_id`),
  ADD CONSTRAINT `FKhxtlgbtpf1fqg33sghhjtfrwd` FOREIGN KEY (`salle_salle`) REFERENCES `salle` (`salle`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
