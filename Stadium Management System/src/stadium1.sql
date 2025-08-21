-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 24, 2024 at 07:58 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stadium1`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_match` (IN `in_match_id` INT)   BEGIN
    DELETE FROM Matches
    WHERE match_id = in_match_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_user` (IN `in_user_id` INT)   BEGIN
    DELETE FROM user
    WHERE user_id = in_user_id;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `matches`
--

CREATE TABLE `matches` (
  `match_id` int(11) NOT NULL,
  `match_name` varchar(50) NOT NULL,
  `series_tournament_name` varchar(50) NOT NULL,
  `match_format` varchar(10) NOT NULL,
  `match_date` varchar(15) NOT NULL,
  `match_time` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `matches`
--

INSERT INTO `matches` (`match_id`, `match_name`, `series_tournament_name`, `match_format`, `match_date`, `match_time`) VALUES
(18, 'IND vs PAK', 'PAK tour of IND', 'T20', '02/08/2009', '07:30:00'),
(77, 'CSK vs GT', 'IPL', 'T20', '30/03/2022', '07:30:00'),
(122, 'CSK vs SRH', 'IPL', 't20', '07/08/2022', '06:12:09'),
(123, 'GT vs SRH', 'IPL', 't20', '02/02/2002', '07:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staff_id` int(11) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `password`) VALUES
(2218, '2218'),
(4433, '4433');

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `ticket_id` int(11) NOT NULL,
  `match_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `stand` varchar(1) NOT NULL,
  `ticket_price` int(11) NOT NULL,
  `no_of_tickets` int(11) NOT NULL,
  `total_payments` double NOT NULL,
  `payment_method` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`ticket_id`, `match_id`, `user_id`, `stand`, `ticket_price`, `no_of_tickets`, `total_payments`, `payment_method`) VALUES
(1, 123, 6344, 'D', 10000, 5, 50000, 'UPI'),
(2, 123, 889, 'C', 5000, 4, 20000, 'Netbanking'),
(3, 122, 889, 'A', 1000, 19, 19000, 'Debit Card'),
(4, 123, 4937, 'C', 5000, 5, 25000, 'Debit Card'),
(5, 122, 4937, 'D', 10000, 4, 40000, 'UPI'),
(6, 123, 2003, 'A', 1000, 2, 2000, 'UPI'),
(7, 114, 1122, 'D', 10000, 1, 10000, 'UPI'),
(8, 122, 1112, 'C', 5000, 3, 15000, 'Netbanking'),
(9, 77, 7733, 'D', 10000, 1, 10000, 'UPI'),
(10, 18, 4455, 'D', 10000, 5, 50000, 'UPI');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `mobile_no` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `password`, `mobile_no`) VALUES
(889, 'Urmik', 'Urmik@', '9227083370'),
(1112, 'tirth', 'tirth@', '7984792591'),
(2003, 'Raj', 'Ramani009', '8238626628'),
(4455, 'smit', 'smit@', '7698373886'),
(4937, 'bharat', 'bharat@', '9979731902'),
(6344, 'Ansh', '6433', '8849827943'),
(7733, 'Ruchit', 'Ruchit@', '1234567890');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`match_id`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`ticket_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `ticket_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
