-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 03, 2019 at 07:23 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `treehouseschool`
--

-- --------------------------------------------------------

--
-- Table structure for table `class`
--

CREATE TABLE IF NOT EXISTS `class` (
  `class_id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `plase` varchar(20) NOT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `course_id` int(4) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(20) DEFAULT NULL,
  `coures_des` text,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`course_id`, `course_name`, `coures_des`) VALUES
(1, 'Arts', 'is for art'),
(2, 'Maths', 'is for Numbers'),
(3, 'Arabic', 'is for reading');

-- --------------------------------------------------------

--
-- Table structure for table `marks`
--

CREATE TABLE IF NOT EXISTS `marks` (
  `course_id` int(11) NOT NULL DEFAULT '0',
  `std_id` int(11) NOT NULL DEFAULT '0',
  `prof_id` int(11) NOT NULL DEFAULT '0',
  `mark` int(11) DEFAULT NULL,
  PRIMARY KEY (`course_id`,`std_id`,`prof_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `marks`
--

INSERT INTO `marks` (`course_id`, `std_id`, `prof_id`, `mark`) VALUES
(1, 1, 3, 30),
(1, 3, 3, 12),
(1, 7, 3, 34),
(1, 8, 3, 23),
(1, 9, 3, 0),
(2, 1, 4, 22),
(2, 4, 4, 30),
(2, 6, 4, 11),
(2, 7, 4, 22),
(2, 9, 4, 17),
(3, 3, 5, 28),
(3, 4, 5, 15),
(3, 6, 5, 7),
(3, 8, 5, 19),
(3, 9, 5, 25);

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE IF NOT EXISTS `schedule` (
  `coures_id` int(4) NOT NULL DEFAULT '0',
  `class_id` int(4) NOT NULL DEFAULT '0',
  `teacher_id` int(4) NOT NULL DEFAULT '0',
  `day` enum('sat','sun','mon','tue','wed','thu') DEFAULT NULL,
  `time` time DEFAULT NULL,
  PRIMARY KEY (`coures_id`,`class_id`,`teacher_id`),
  KEY `schedule_fk_class` (`class_id`),
  KEY `schedule_fk_teacher` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `student_id` int(4) NOT NULL AUTO_INCREMENT,
  `fname` varchar(15) DEFAULT NULL,
  `sname` varchar(15) NOT NULL,
  `lname` varchar(15) DEFAULT NULL,
  `mname` varchar(15) NOT NULL,
  `addres` varchar(20) NOT NULL,
  `ssn` int(12) DEFAULT NULL,
  `birthdate` date NOT NULL,
  `birthplase` varchar(10) NOT NULL,
  `storydate` date NOT NULL,
  `father_phone_num` int(10) NOT NULL,
  `class_id` int(4) NOT NULL,
  `gender` enum('male','female','','') NOT NULL,
  `img` text NOT NULL,
  `nation` varchar(10) NOT NULL,
  PRIMARY KEY (`student_id`),
  KEY `student_fk_class` (`class_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`student_id`, `fname`, `sname`, `lname`, `mname`, `addres`, `ssn`, `birthdate`, `birthplase`, `storydate`, `father_phone_num`, `class_id`, `gender`, `img`, `nation`) VALUES
(1, 'Moez', 'Salem', 'Salama', 'Hana', 'Zawiya', 22000, '2000-03-03', 'Libya', '2019-02-05', 911234123, 2, 'male', '', ''),
(3, 'Suha', 'Khalid', 'Mohamed', 'Manar', 'Tripoli', 21999, '2000-03-03', 'Libya', '2019-02-05', 911234123, 2, 'female', '', ''),
(4, 'Asma', 'Louai', 'Taleb', 'Mariam', 'Tripoli', 22001, '2019-03-11', 'Ghana', '2019-03-03', 92333444, 1, 'female', '', ''),
(6, 'Imad', 'Imad', 'Aouda', 'Jouriya', 'Khums', 22002, '2000-03-03', 'Libya', '2019-02-05', 911234123, 1, 'male', '', ''),
(7, 'Farahat', 'Mohamed', 'Mohamed', 'Khawla', 'Tripoli', 21998, '2019-03-01', 'Libya', '2019-03-18', 924443333, 1, 'female', '', ''),
(8, 'Nour', 'Jameel', 'Alakdar', 'Wala', 'Zawiya', 22001, '2000-03-03', 'Libya', '2019-02-05', 92333444, 2, 'female', '', ''),
(9, 'Nour', 'Wajdi', 'Arras', 'Lamya', 'Tripoli', 21998, '2019-03-06', 'France', '2019-03-18', 924443333, 1, 'male', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(4) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) DEFAULT NULL,
  `fname` varchar(15) DEFAULT NULL,
  `sname` varchar(15) DEFAULT NULL,
  `lname` varchar(15) DEFAULT NULL,
  `mname` varchar(15) DEFAULT NULL,
  `addres` varchar(20) DEFAULT NULL,
  `ssn` int(12) DEFAULT NULL,
  `hiring_date` date DEFAULT NULL,
  `dep` varchar(10) DEFAULT NULL,
  `job` enum('admin','teacher','employee','') NOT NULL,
  `gender` enum('male','female','','') DEFAULT NULL,
  `pass` varchar(30) NOT NULL DEFAULT '123',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `fname`, `sname`, `lname`, `mname`, `addres`, `ssn`, `hiring_date`, `dep`, `job`, `gender`, `pass`) VALUES
(1, NULL, 'shahed', 'ali', 'ageli', 'e', 'a', 21996, '2016-09-01', 'en', 'admin', 'male', '123'),
(2, NULL, 'islam', 'omar', 'g', 'm', 'a', 21995, '2019-02-12', 'en', 'admin', 'female', '123'),
(3, NULL, 'malak', 'ahmed', 'adep', 'e', 'a', 21992, '2019-02-01', 'en', 'teacher', 'female', '123'),
(4, NULL, 'Ahmed', NULL, 'Sammy', NULL, 'Tripoli', NULL, NULL, NULL, 'teacher', 'male', '123'),
(5, NULL, 'Hiba', NULL, 'BenAmer', NULL, NULL, NULL, NULL, NULL, 'teacher', 'female', '123'),
(6, NULL, 'Esra', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'employee', NULL, '123');

-- --------------------------------------------------------

--
-- Table structure for table `user_archive`
--

CREATE TABLE IF NOT EXISTS `user_archive` (
  `user_id` int(4) NOT NULL AUTO_INCREMENT,
  `fname` varchar(15) DEFAULT NULL,
  `sname` varchar(15) NOT NULL,
  `lname` varchar(15) DEFAULT NULL,
  `mname` varchar(15) NOT NULL,
  `addres` varchar(20) NOT NULL,
  `ssn` int(12) DEFAULT NULL,
  `hiring_date` date NOT NULL,
  `dep` varchar(10) NOT NULL,
  `job` enum('admin','teacher','employee','') NOT NULL,
  `gender` enum('male','female','','') NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `schedule_fk_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `schedule_fk_coures` FOREIGN KEY (`coures_id`) REFERENCES `course` (`course_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `schedule_fk_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
