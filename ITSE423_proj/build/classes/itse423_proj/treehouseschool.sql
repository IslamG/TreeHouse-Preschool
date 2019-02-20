-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 13, 2019 at 10:02 AM
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
  `coures_id` int(4) NOT NULL AUTO_INCREMENT,
  `coures_name` varchar(20) DEFAULT NULL,
  `coures_des` text NOT NULL,
  PRIMARY KEY (`coures_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `fname`, `sname`, `lname`, `mname`, `addres`, `ssn`, `hiring_date`, `dep`, `job`, `gender`) VALUES
(1, 'shahed', 'ali', 'ageli', 'e', 'a', 21996, '2016-09-01', 'en', 'admin', 'male'),
(2, 'islam', 'omar', 'g', 'm', 'a', 21995, '2019-02-12', 'en', 'teacher', 'female'),
(3, 'malak', 'ahmed', 'adep', 'e', 'a', 21992, '2019-02-01', 'en', 'teacher', 'male');

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
  ADD CONSTRAINT `schedule_fk_coures` FOREIGN KEY (`coures_id`) REFERENCES `course` (`coures_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `schedule_fk_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_fk_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
