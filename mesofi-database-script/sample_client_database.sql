-- phpMyAdmin SQL Dump
-- version 4.2.10.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 10, 2016 at 10:20 PM
-- Server version: 5.6.21
-- PHP Version: 5.5.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sample_client_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `sample_table_one`
--

CREATE TABLE IF NOT EXISTS `sample_table_one` (
`int_column_key` int(11) NOT NULL,
  `varchar_column` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sample_table_two`
--

CREATE TABLE IF NOT EXISTS `sample_table_two` (
`id` int(11) NOT NULL,
  `description` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sample_table_one`
--
ALTER TABLE `sample_table_one`
 ADD PRIMARY KEY (`int_column_key`);

--
-- Indexes for table `sample_table_two`
--
ALTER TABLE `sample_table_two`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `sample_table_one`
--
ALTER TABLE `sample_table_one`
MODIFY `int_column_key` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `sample_table_two`
--
ALTER TABLE `sample_table_two`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
