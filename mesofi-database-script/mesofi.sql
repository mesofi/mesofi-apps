-- phpMyAdmin SQL Dump
-- version 4.2.10.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 10, 2016 at 08:35 PM
-- Server version: 5.6.21
-- PHP Version: 5.5.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mesofi`
--

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ADMIN_ATEST1`
--

CREATE TABLE IF NOT EXISTS `MSF_ADMIN_ATEST1` (
`int_column1` int(11) NOT NULL,
  `description` varchar(250) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MSF_ADMIN_ATEST1`
--

INSERT INTO `MSF_ADMIN_ATEST1` (`int_column1`, `description`) VALUES
(4, 'firstssTest'),
(5, 'second'),
(14, 'last'),
(15, 'sd ggg'),
(16, 'aaa'),
(17, 'ds'),
(18, 'mn'),
(19, 'mn,..'),
(20, 'yyy'),
(21, 'xccx'),
(22, 'aa'),
(23, 'sss'),
(24, 'dsds'),
(25, 'asas'),
(26, ''),
(27, ''),
(28, ''),
(29, ''),
(30, ''),
(31, ''),
(32, 'firstsstetes ggg');

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ADMIN_ATEST2`
--

CREATE TABLE IF NOT EXISTS `MSF_ADMIN_ATEST2` (
  `ID_COLUMN` int(11) NOT NULL,
  `Some_more` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MSF_ADMIN_ATEST2`
--

INSERT INTO `MSF_ADMIN_ATEST2` (`ID_COLUMN`, `Some_more`) VALUES
(1, 'Test');

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ADMIN_COLUMNS`
--

CREATE TABLE IF NOT EXISTS `MSF_ADMIN_COLUMNS` (
`ID` int(11) NOT NULL,
  `ID_TABLE` int(11) NOT NULL,
  `PRIMARY_KEY` tinyint(1) NOT NULL,
  `FOREIGN_KEY` tinyint(4) NOT NULL,
  `REF_ID_TABLE` int(11) NOT NULL,
  `COLUMN_NAME` varchar(200) NOT NULL,
  `ID_COLUMN_TYPE` int(11) NOT NULL COMMENT 'Reference to the column type',
  `PERMITTED_VALUES` text COMMENT 'Supported values for this column',
  `SIZE` int(11) NOT NULL,
  `SCALE` int(11) DEFAULT NULL,
  `NULLABLE` int(11) NOT NULL,
  `AUTO_INCREMENT` tinyint(1) DEFAULT NULL COMMENT 'Indicates if this column is auto increment'
) ENGINE=InnoDB AUTO_INCREMENT=689 DEFAULT CHARSET=latin1 COMMENT='Column Details';

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ADMIN_COLUMN_TYPES`
--

CREATE TABLE IF NOT EXISTS `MSF_ADMIN_COLUMN_TYPES` (
`ID` int(11) NOT NULL,
  `ID_DATABASE_TYPE` int(11) NOT NULL,
  `DATABASE_TYPE` varchar(200) NOT NULL,
  `ID_SQL_TYPE` int(11) NOT NULL,
  `SQL_TYPE` varchar(200) DEFAULT NULL,
  `JAVA_TYPE` varchar(200) DEFAULT NULL,
  `EFFECTIVE_TYPE` tinyint(1) NOT NULL COMMENT 'States whether this type is effective or not'
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1 COMMENT='Contains mapping types for database and Java';

--
-- Dumping data for table `MSF_ADMIN_COLUMN_TYPES`
--

INSERT INTO `MSF_ADMIN_COLUMN_TYPES` (`ID`, `ID_DATABASE_TYPE`, `DATABASE_TYPE`, `ID_SQL_TYPE`, `SQL_TYPE`, `JAVA_TYPE`, `EFFECTIVE_TYPE`) VALUES
(1, 2, 'INT', 4, 'INTEGER', 'Integer', 1),
(2, 2, 'VARCHAR', 12, 'VARCHAR', 'String', 1),
(3, 2, 'TEXT', -1, 'LONGVARCHAR', 'String', 0),
(4, 2, 'DATE', 91, 'DATE', 'Date', 1),
(5, 2, 'TINYINT', -6, 'TINYINT', 'Integer', 0),
(6, 2, 'SMALLINT', 5, 'SMALLINT', 'Integer', 0),
(7, 2, 'MEDIUMINT', 4, 'INTEGER', 'Integer', 0),
(8, 2, 'BIGINT', -5, 'BIGINT', 'Long', 1),
(9, 2, 'DECIMAL', 3, 'DECIMAL', 'java.math.BigDecimal', 1),
(10, 2, 'FLOAT', 7, 'REAL', 'Float', 1),
(11, 2, 'DOUBLE', 8, 'DOUBLE', 'Double', 1),
(12, 2, 'BIT', -7, 'BIT', 'Boolean', 1),
(13, 2, 'DATETIME', 93, 'TIMESTAMP', 'java.sql.Timestamp', 1),
(14, 2, 'TIMESTAMP', 93, 'TIMESTAMP', 'java.sql.Timestamp', 0),
(15, 2, 'TIME', 92, 'TIME', 'java.sql.Time', 1),
(16, 2, 'ENUM', 1, 'CHAR', 'String', 0),
(17, 2, 'CHAR', 1, 'CHAR', 'Unknown', 0),
(18, 2, 'SET', 1, 'CHAR', 'Unknown', 0),
(19, 2, 'LONGBLOB', -4, 'LONGVARBINARY', 'Unknown', 0),
(20, 2, 'INT UNSIGNED', 4, 'INTEGER', 'Integer', 0),
(21, 2, 'BIGINT UNSIGNED', -5, 'BIGINT', 'Unknown', 0);

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ADMIN_CONNECTIONS`
--

CREATE TABLE IF NOT EXISTS `MSF_ADMIN_CONNECTIONS` (
`ID` int(11) NOT NULL,
  `ID_DATABASE_TYPE` int(11) NOT NULL,
  `CONN_NAME` varchar(100) NOT NULL COMMENT 'Connection Name',
  `USER_NAME` varchar(100) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `HOST` varchar(150) NOT NULL,
  `PORT` int(11) NOT NULL,
  `DATABASE_NAME` varchar(150) NOT NULL,
  `SERVICE` tinyint(1) NOT NULL,
  `URL` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ADMIN_PLUGIN`
--

CREATE TABLE IF NOT EXISTS `MSF_ADMIN_PLUGIN` (
`ID` int(11) NOT NULL,
  `CLASS_IMPLEMENTATION` varchar(250) NOT NULL,
  `TITLE` varchar(250) NOT NULL,
  `DESCRIPTION` text NOT NULL,
  `ORDER_SHOWN` int(11) NOT NULL,
  `PRE_SELECTED` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MSF_ADMIN_PLUGIN`
--

INSERT INTO `MSF_ADMIN_PLUGIN` (`ID`, `CLASS_IMPLEMENTATION`, `TITLE`, `DESCRIPTION`, `ORDER_SHOWN`, `PRE_SELECTED`) VALUES
(1, 'mx.com.mesofi.web.admin.techplugin.service.PrimeFacesSpringCoreHibernateProjectBuilderConfigurable', 'Orion', 'Uses Hibernate', 2, 0),
(2, 'mx.com.mesofi.web.admin.techplugin.service.PrimeFacesSpringCoreSpringJdbcProjectBuilderConfigurable', 'Perseus', 'This uses jdbc', 1, 0),
(3, 'mx.com.mesofi.web.admin.techplugin.service.SpringMVCSpringCoreHibernateProjectBuilderConfigurable', 'Andromeda', 'Uses Spring MVC', 3, 1),
(4, 'mx.com.mesofi.web.admin.builder.controller.BuilderAppController', 'Unknown', 'Uses JPA', 4, 0);

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ADMIN_PREFERENCES`
--

CREATE TABLE IF NOT EXISTS `MSF_ADMIN_PREFERENCES` (
`ID` int(11) NOT NULL,
  `ID_PLUGIN` int(11) DEFAULT NULL,
  `PROPERTY_NAME` varchar(100) NOT NULL,
  `PROPERTY_VALUE` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MSF_ADMIN_PREFERENCES`
--

INSERT INTO `MSF_ADMIN_PREFERENCES` (`ID`, `ID_PLUGIN`, `PROPERTY_NAME`, `PROPERTY_VALUE`) VALUES
(1, NULL, 'mainModuleName', 'login'),
(2, NULL, 'authModuleName', 'catalogs');

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ADMIN_TABLES`
--

CREATE TABLE IF NOT EXISTS `MSF_ADMIN_TABLES` (
`ID` int(11) NOT NULL,
  `ID_CONNECTION` int(11) NOT NULL COMMENT 'Foreign key for table MSF_ADMIN_CONNECTIONS',
  `IS_DATABASE_SCHEMA` tinyint(1) NOT NULL COMMENT 'This connection is database or schema?',
  `DATABASE_SCHEMA` varchar(150) NOT NULL,
  `TABLE_NAME` varchar(200) NOT NULL COMMENT 'Table Name',
  `IS_TABLE_NAME_PROCESSED` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'This column controls whether this table will be part of the process or not'
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=latin1 COMMENT='Stores the table details to be processed';

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ALL_CODE_TYPE`
--

CREATE TABLE IF NOT EXISTS `MSF_ALL_CODE_TYPE` (
`ID` int(11) NOT NULL,
  `ID_CODE` int(11) NOT NULL COMMENT 'Reference to MSF_TEMPLATE_CODE',
  `ID_COLUMN_TYPE` int(11) NOT NULL COMMENT 'Reference to MSF_ADMIN_COLUMN_TYPES'
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MSF_ALL_CODE_TYPE`
--

INSERT INTO `MSF_ALL_CODE_TYPE` (`ID`, `ID_CODE`, `ID_COLUMN_TYPE`) VALUES
(1, 1, 1),
(2, 1, 8),
(3, 1, 9),
(4, 1, 10),
(5, 1, 11),
(6, 2, 12),
(7, 3, 2),
(8, 4, 4),
(9, 5, 13),
(10, 6, 1),
(11, 6, 8),
(12, 6, 9),
(13, 7, 10),
(14, 7, 11),
(15, 8, 2),
(16, 8, 12),
(17, 9, 4),
(18, 10, 13),
(19, 11, 1),
(20, 11, 8),
(21, 11, 9),
(22, 11, 10),
(23, 11, 11),
(24, 12, 12),
(25, 13, 2),
(26, 14, 4),
(27, 15, 13),
(28, 16, 1),
(29, 16, 8),
(30, 16, 9),
(31, 16, 10),
(32, 16, 11),
(33, 17, 12),
(34, 18, 1),
(35, 19, 4),
(36, 20, 13),
(37, 21, 1),
(38, 21, 8),
(39, 21, 9),
(40, 21, 10),
(41, 21, 11),
(42, 21, 12),
(43, 22, 2),
(44, 23, 4),
(45, 23, 13),
(46, 24, 1),
(47, 24, 8),
(48, 24, 10),
(49, 24, 11),
(50, 24, 12),
(51, 24, 4),
(52, 24, 13),
(53, 25, 9),
(54, 26, 2),
(55, 27, 1),
(56, 27, 8),
(57, 27, 10),
(58, 27, 11),
(59, 27, 12),
(60, 27, 4),
(61, 28, 2),
(62, 29, 9),
(63, 30, 1),
(64, 30, 8),
(65, 30, 10),
(66, 30, 11),
(67, 30, 12),
(68, 30, 4),
(69, 27, 13),
(70, 30, 13),
(71, 31, 9),
(72, 32, 2),
(73, 33, 1),
(74, 33, 8),
(75, 33, 9),
(76, 33, 10),
(77, 33, 11),
(78, 34, 12),
(79, 35, 2),
(80, 36, 4),
(81, 36, 13),
(82, 37, 1),
(83, 38, 12),
(84, 39, 4),
(85, 39, 13),
(86, 40, 1),
(87, 40, 8),
(88, 40, 9),
(89, 41, 12),
(90, 41, 4),
(91, 41, 13),
(92, 42, 15),
(93, 23, 15),
(94, 24, 15),
(95, 30, 15),
(96, 20, 15),
(97, 43, 2),
(98, 44, 2),
(99, 45, 1),
(100, 46, 2),
(101, 47, 1),
(102, 48, 2),
(103, 49, 1),
(104, 50, 1),
(105, 51, 2),
(106, 52, 1),
(107, 53, 2);

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ALL_USER_MENU`
--

CREATE TABLE IF NOT EXISTS `MSF_ALL_USER_MENU` (
`ID` int(11) NOT NULL,
  `ID_MENU` int(11) NOT NULL,
  `ID_USER` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_AUDIT_BODY_REQ`
--

CREATE TABLE IF NOT EXISTS `MSF_AUDIT_BODY_REQ` (
`ID` int(11) NOT NULL,
  `ID_AUDIT` int(11) NOT NULL,
  `PARAM_NAME` varchar(100) NOT NULL,
  `PARAM_VALUE` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_AUDIT_BODY_RESP`
--

CREATE TABLE IF NOT EXISTS `MSF_AUDIT_BODY_RESP` (
`ID` int(11) NOT NULL,
  `ID_AUDIT` int(11) NOT NULL,
  `RESPONSE` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_AUDIT_HEADER_REQ`
--

CREATE TABLE IF NOT EXISTS `MSF_AUDIT_HEADER_REQ` (
`ID` int(11) NOT NULL,
  `ID_AUDIT` int(11) NOT NULL,
  `PROP_NAME` varchar(100) NOT NULL,
  `PROP_VALUE` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_AUDIT_HEADER_RESP`
--

CREATE TABLE IF NOT EXISTS `MSF_AUDIT_HEADER_RESP` (
`ID` int(11) NOT NULL,
  `ID_AUDIT` int(11) NOT NULL,
  `PROP_NAME` varchar(100) NOT NULL,
  `PROP_VALUE` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_AUDIT_REQ_RESP`
--

CREATE TABLE IF NOT EXISTS `MSF_AUDIT_REQ_RESP` (
`ID` int(11) NOT NULL,
  `TRANS_DT` datetime NOT NULL,
  `HTTP_METHOD` varchar(10) NOT NULL,
  `HTTP_REQUEST` varchar(200) NOT NULL,
  `HTTP_CONTENT_TYPE` varchar(100) DEFAULT NULL,
  `HTTP_CONTENT_LENGTH` int(11) NOT NULL,
  `CLIENT_HOST` varchar(50) NOT NULL,
  `CLIENT_ADDR` varchar(50) NOT NULL,
  `CLIENT_PORT` int(11) NOT NULL,
  `SERVER_CONTENT_TYPE` varchar(100) DEFAULT NULL,
  `SERVER_STATUS` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_ERROR_TRACE`
--

CREATE TABLE IF NOT EXISTS `MSF_ERROR_TRACE` (
`ID` int(11) NOT NULL,
  `ERROR_DT` datetime NOT NULL,
  `TYPE` int(11) NOT NULL,
  `MESSAGE` varchar(500) NOT NULL,
  `TRACE` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_MENUS`
--

CREATE TABLE IF NOT EXISTS `MSF_MENUS` (
`ID` int(11) NOT NULL,
  `ID_MENU_PARENT` int(11) NOT NULL,
  `DS_MENU` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_TEMPLATE_CODE`
--

CREATE TABLE IF NOT EXISTS `MSF_TEMPLATE_CODE` (
`ID` int(11) NOT NULL,
  `POINT_CUT_NAME` varchar(100) NOT NULL,
  `CODE` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MSF_TEMPLATE_CODE`
--

INSERT INTO `MSF_TEMPLATE_CODE` (`ID`, `POINT_CUT_NAME`, `CODE`) VALUES
(1, 'view.filter.input.primefaces', '#set( $maxSize = ${field.size} - 1 )\n#set( $maxNumber = "" )\n#foreach($i in [1..${maxSize}])\n#set($maxNumber = "$maxNumber"+"9" )\n#end\n                            <p:inputText id="filterSearch${velocityCount}" value="#{${classNameInstance}.${classNameSearchLInstance}.${field.fieldJavaType}}" maxlength="${maxSize}" >\n                                <f:validateDoubleRange maximum="${maxNumber}" ></f:validateDoubleRange>\n#if (${field.fractionDigits} != -1)\n                                <f:convertNumber groupingUsed="true" minFractionDigits="${field.fractionDigits}"></f:convertNumber>\n#end\n                            </p:inputText>'),
(2, 'view.filter.input.primefaces', '#if (${field.size} > 1)\n                            <p:inputText id="filterSearch${velocityCount}" value="#{${classNameInstance}.${classNameSearchLInstance}.${field.fieldJavaType}}" maxlength="${field.size}" ></p:inputText>\n#else\n                            <p:selectOneMenu id="filterSearch${velocityCount}" value="#{${classNameInstance}.${classNameSearchLInstance}.${field.fieldJavaType}}" >\n                                <f:selectItem itemLabel="#{msg[''catalog.form.button.empty'']}" itemValue="" noSelectionOption="true" ></f:selectItem>\n                                <f:selectItem itemLabel="true" itemValue="true" ></f:selectItem>\n                                <f:selectItem itemLabel="false" itemValue="false" ></f:selectItem>\n                            </p:selectOneMenu>\n#end'),
(3, 'view.filter.input.primefaces', '#if (${field.permittedValues} && ${field.fieldDataBaseType} == ''ENUM'')\n                            <p:selectOneMenu id="filterSearch${velocityCount}" value="#{${classNameInstance}.${classNameSearchLInstance}.${field.fieldJavaType}}" >\n                                <f:selectItem itemLabel="#{msg[''catalog.form.button.empty'']}" noSelectionOption="true" />\n#foreach( ${permittedValue} in ${field.permittedValues} )\n                                <f:selectItem itemValue="${permittedValue}" itemLabel="${permittedValue}" />\n#end\n                            </p:selectOneMenu>\n#else\n                            <p:inputText id="filterSearch${velocityCount}" value="#{${classNameInstance}.${classNameSearchLInstance}.${field.fieldJavaType}}" maxlength="${field.size}" />\n#end'),
(4, 'view.filter.input.primefaces', '                            <p:calendar id="filterSearch${velocityCount}" value="#{${classNameInstance}.${classNameSearchLInstance}.${field.fieldJavaType}}" pattern="#{msg[''catalog.form.format.date'']}" mask="true" ></p:calendar>'),
(5, 'view.filter.input.primefaces', '                            <p:calendar id="filterSearch${velocityCount}" value="#{${classNameInstance}.${classNameSearchLInstance}.${field.fieldJavaType}}" pattern="#{msg[''catalog.form.format.datetime'']}" mask="true" >\n                                <f:converter converterId="timestampConverter" ></f:converter>\n                            </p:calendar>'),
(6, 'view.table.output', '                            <h:outputText value="#{record.${field.fieldJavaType}}" >\n#if (${field.fractionDigits} != -1)\n                                <f:convertNumber groupingUsed="true" minFractionDigits="${field.fractionDigits}"/>\n#end\n                            </h:outputText>'),
(7, 'view.table.output', '                            <h:outputText value="#{record.${field.fieldJavaType}}" >\n                                <f:convertNumber groupingUsed="true"/>\n                            </h:outputText>'),
(8, 'view.table.output', '                            <h:outputText value="#{record.${field.fieldJavaType}}" />'),
(9, 'view.table.output', '                            <h:outputText value="#{record.${field.fieldJavaType}}" >\n                                <f:convertDateTime type="date" pattern="#{msg[''catalog.form.format.date'']}" />\n                            </h:outputText>'),
(10, 'view.table.output', '                            <h:outputText value="#{record.${field.fieldJavaType}}" >\n                                <f:convertDateTime type="date" pattern="#{msg[''catalog.form.format.datetime'']}" />\n                            </h:outputText>'),
(11, 'view.table.input', '#set( $maxSize = ${field.size} - 1 )\n#set( $maxNumber = "" )\n#foreach($i in [1..${maxSize}])\n#set($maxNumber = "$maxNumber"+"9" )\n#end\n                            <h:inputText value="#{record.${field.fieldJavaType}}" maxlength="${maxSize}" >\n                                <f:validateDoubleRange maximum="${maxNumber}" />\n#if (${field.fractionDigits} != -1)\n                                <f:convertNumber groupingUsed="true" minFractionDigits="${field.fractionDigits}"/>\n#end\n                            </h:inputText>'),
(12, 'view.table.input', '#if (${field.size} > 1)\n                            <p:inputText value="#{record.${field.fieldJavaType}}" maxlength="${field.size}" />\n#else\n                            <p:selectOneMenu value="#{record.${field.fieldJavaType}}" >\n                                <f:selectItem itemLabel="#{msg[''catalog.form.button.empty'']}" itemValue="" noSelectionOption="true" />\n                                <f:selectItem itemLabel="true" itemValue="true" />\n                                <f:selectItem itemLabel="false" itemValue="false" />\n                            </p:selectOneMenu>\n#end'),
(13, 'view.table.input', '#if (${field.permittedValues} && ${field.fieldDataBaseType} == ''ENUM'')\n                            <p:selectOneMenu value="#{record.${field.fieldJavaType}}" >\n#if (${field.optional})\n                                <f:selectItem itemLabel="#{msg[''catalog.form.button.empty'']}" noSelectionOption="true" />\n#end\n#foreach( ${permittedValue} in ${field.permittedValues} )\n                                <f:selectItem itemValue="${permittedValue}" itemLabel="${permittedValue}" />\n#end\n                            </p:selectOneMenu>\n#else\n                            <h:inputText value="#{record.${field.fieldJavaType}}" maxlength="${field.size}" />\n#end'),
(14, 'view.table.input', '                            <p:calendar value="#{record.${field.fieldJavaType}}" pattern="#{msg[''catalog.form.format.date'']}" mask="true" />'),
(15, 'view.table.input', '                            <p:calendar value="#{record.${field.fieldJavaType}}" pattern="#{msg[''catalog.form.format.datetime'']}" mask="true" >\n                                <f:converter converterId="timestampConverter" />\n                            </p:calendar>'),
(16, 'view.form.input.primefaces', '#set( $maxSize = ${field.size} - 1 )\n#set( $maxNumber = "" )\n#foreach($i in [1..${maxSize}])\n#set($maxNumber = "$maxNumber"+"9" )\n#end\n                            <p:inputText id="inputForm${velocityCount}" value="#{${classNameInstance}.${classNameModelInstance}.${field.fieldJavaType}}" maxlength="${maxSize}" disabled="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" styleClass="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Style}">\n                                <f:validateDoubleRange maximum="${maxNumber}" ></f:validateDoubleRange>\n#if (${field.fractionDigits} != -1)\n                                <f:convertNumber groupingUsed="true" minFractionDigits="${field.fractionDigits}"></f:convertNumber>\n#end\n                            </p:inputText>'),
(17, 'view.form.input.primefaces', '#if (${field.size} > 1)\n                            <p:inputText id="inputForm${velocityCount}" value="#{${classNameInstance}.${classNameModelInstance}.${field.fieldJavaType}}" maxlength="${field.size}" disabled="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" styleClass="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Style}" ></p:inputText>\n#else\n                            <p:selectOneMenu id="inputForm${velocityCount}" value="#{${classNameInstance}.${classNameModelInstance}.${field.fieldJavaType}}" disabled="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" styleClass="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Style}" >\n                                <f:selectItem itemLabel="#{msg[''catalog.form.button.empty'']}" itemValue="" noSelectionOption="true" ></f:selectItem>\n                                <f:selectItem itemLabel="true" itemValue="true" ></f:selectItem>\n                                <f:selectItem itemLabel="false" itemValue="false" ></f:selectItem>\n                            </p:selectOneMenu>\n#end'),
(18, 'view.form.input.primefaces', '#if (${field.fieldSqlType} == -1)\n                            <p:inputTextarea id="inputForm${velocityCount}" value="#{${classNameInstance}.${classNameModelInstance}.${field.fieldJavaType}}" maxlength="${field.size}" disabled="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" styleClass="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Style}" rows="3" cols="20" ></p:inputTextarea>\n#else\n                            <p:inputText id="inputForm${velocityCount}" value="#{${classNameInstance}.${classNameModelInstance}.${field.fieldJavaType}}" maxlength="${field.size}" disabled="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" styleClass="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Style}"></p:inputText>\n#end'),
(19, 'view.form.input.primefaces', '                            <p:calendar id="inputForm${velocityCount}" value="#{${classNameInstance}.${classNameModelInstance}.${field.fieldJavaType}}" disabled="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" styleClass="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Style}" pattern="#{msg[''catalog.form.format.date'']}" mask="true" ></p:calendar>'),
(20, 'view.form.input.primefaces', '                            <p:calendar id="inputForm${velocityCount}" value="#{${classNameInstance}.${classNameModelInstance}.${field.fieldJavaType}}" disabled="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" styleClass="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Style}" pattern="#{msg[''catalog.form.format.datetime'']}" mask="true" >\n                                <f:converter converterId="timestampConverter" ></f:converter>\n                            </p:calendar>'),
(21, 'controller.test.req.empty.primefaces', '        if (!${configModelInstance}.is${effectiveField}Enabled() && input.get${effectiveField}() == null) {'),
(22, 'controller.test.req.empty.primefaces', '        if (!${configModelInstance}.is${effectiveField}Enabled() && (input.get${effectiveField}() == null || input.get${effectiveField}().trim().equals(""))) {'),
(23, 'controller.test.req.empty.primefaces', '        if (!${configModelInstance}.is${effectiveField}Enabled() && input.get${effectiveField}() == null) {'),
(24, 'controller.test.req.size.primefaces', '        }'),
(25, 'controller.test.req.size.primefaces', '        } else if (input.get${effectiveField}().scale() > ${field.fractionDigits}) { // Validates fraction digits'),
(26, 'controller.test.req.size.primefaces', '        } else if (!${configModelInstance}.is${effectiveField}Enabled() && input.get${effectiveField}().length() > ${field.size}) {'),
(27, 'controller.test.opt.empty', '## nothing to do with these types'),
(28, 'controller.test.opt.empty', '        if (!${configModelInstance}.is${effectiveField}Enabled() && (input.get${effectiveField}() != null && input.get${effectiveField}().length() > ${field.size})) {'),
(29, 'controller.test.opt.empty', '        if (!${configModelInstance}.is${effectiveField}Enabled() && (input.get${effectiveField}() != null && input.get${effectiveField}().scale() > ${field.fractionDigits})) { // Validates fraction digits'),
(30, 'controller.test.opt.size.primefaces', '## nothing to do with these types'),
(31, 'controller.test.opt.size.primefaces', '            addErrorValidation(isInline, ${field.fieldJavaType}, scaleMsg.replaceAll("<scale>", "${field.fractionDigits}"));\n            ${configModelInstance}.set${effectiveField}Style(errStyle);\n            valid = false;\n        }'),
(32, 'controller.test.opt.size.primefaces', '            addErrorValidation(isInline, ${field.fieldJavaType}, longMsg.replaceAll("<len>", "${field.size}"));\n            ${configModelInstance}.set${effectiveField}Style(errStyle);\n            valid = false;\n        }'),
(33, 'dao.filter.data', '        if (${classNameSearchInstance}.get${fieldJavaType}() != null ) {\n            params.add("%" + ${classNameSearchInstance}.get${fieldJavaType}() + "%");'),
(34, 'dao.filter.data', '#if (${field.size} > 1)\n        if (${classNameSearchInstance}.get${fieldJavaType}() != null ) {\n            java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(${classNameSearchInstance}.get${fieldJavaType}());\n            params.add(bb.getLong());\n#else\n        if (${classNameSearchInstance}.get${fieldJavaType}() != null ) {\n            params.add(${classNameSearchInstance}.get${fieldJavaType}() ? 1 : 0);\n#end'),
(35, 'dao.filter.data', '        if (${classNameSearchInstance}.get${fieldJavaType}() != null && !${classNameSearchInstance}.get${fieldJavaType}().isEmpty()) {\n            params.add("%" + ${classNameSearchInstance}.get${fieldJavaType}() + "%");'),
(36, 'dao.filter.data', '        if (${classNameSearchInstance}.get${fieldJavaType}() != null ) {\n            params.add(${classNameSearchInstance}.get${fieldJavaType}());'),
(37, 'dao.records.populate', '#if (${field.optional})\n            singleRow.set${fieldJavaType}((${field.fieldJavaCastType}) (row.get("${field.fieldName}")));\n#else\n            singleRow.set${fieldJavaType}((${field.fieldJavaCastType}) (row.get("${field.fieldName}")));\n#end'),
(38, 'dao.records.populate', '#if (${field.size} > 1)\n            singleRow.set${fieldJavaType}((byte[]) (row.get("${field.fieldName}")));\n#else\n            singleRow.set${fieldJavaType}((${field.fieldType}) (row.get("${field.fieldName}")));\n#end            '),
(39, 'dao.records.populate', '            singleRow.set${fieldJavaType}((${field.fieldType}) (row.get("${field.fieldName}")));'),
(40, 'sql.finder.condition', '                    AND ${field.fieldName} LIKE UPPER(?)'),
(41, 'sql.finder.condition', '                    AND ${field.fieldName} = ?'),
(42, 'view.filter.input.primefaces', '                            <p:calendar id="filterSearch${velocityCount}" value="#{${classNameInstance}.${classNameSearchLInstance}.${field.fieldJavaType}}" pattern="#{msg[''catalog.form.format.time'']}" timeOnly="true" ></p:calendar>'),
(43, 'view.form.input.primefaces', '#if (${field.permittedValues} && ${field.fieldDataBaseType} == ''ENUM'')\n                            <p:selectOneMenu id="inputForm${velocityCount}" value="#{${classNameInstance}.${classNameModelInstance}.${field.fieldJavaType}}" >\n                                <f:selectItem itemLabel="#{msg[''catalog.form.button.empty'']}" noSelectionOption="true" />\n#foreach( ${permittedValue} in ${field.permittedValues} )\n                                <f:selectItem itemValue="${permittedValue}" itemLabel="${permittedValue}" />\n#end\n                            </p:selectOneMenu>\n#else\n                            <p:inputText id="inputForm${velocityCount}" value="#{${classNameInstance}.${classNameModelInstance}.${field.fieldJavaType}}" maxlength="${field.size}" disabled="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" styleClass="#{${classNameInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Style}"></p:inputText>\n#end'),
(44, 'view.filter.input.springmvc', '                            <form:input id="filterSearch${velocityCount}" type="text" path="${field.fieldJavaType}" class="form-control" placeholder="${${field.fieldJavaType}LabelFilter}" maxlength="${field.size}" />'),
(45, 'view.filter.input.springmvc', '#set( $maxSize = ${field.size} - 1 )\n#set( $maxNumber = "" )\n#foreach($i in [1..${maxSize}])\n#set($maxNumber = "$maxNumber"+"9" )\n#end\n                            <form:input id="filterSearch${velocityCount}" type="text" path="${field.fieldJavaType}" class="form-control" placeholder="${${field.fieldJavaType}LabelFilter}" maxlength="${maxSize}" />'),
(46, 'view.form.input.springmvc', '                            <form:input id="inputForm${velocityCount}" type="text" path="${classNameModelInstance}.${field.fieldJavaType}" class="form-control" placeholder="${${field.fieldJavaType}LabelForm}" maxlength="${field.size}" disabled="${${classNameFormModelInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" />'),
(47, 'view.form.input.springmvc', '#set( $maxSize = ${field.size} - 1 )\n#set( $maxNumber = "" )\n#foreach($i in [1..${maxSize}])\n#set($maxNumber = "$maxNumber"+"9" )\n#end\n                            <form:input id="inputForm${velocityCount}" type="text" path="${classNameModelInstance}.${field.fieldJavaType}" class="form-control" placeholder="${${field.fieldJavaType}LabelForm}" maxlength="${maxSize}" disabled="${${classNameFormModelInstance}.${classNameConfigLInstance}.${field.fieldJavaType}Enabled}" />'),
(48, 'controller.test.req.empty.springmvc', '        if (!input.get${classNameConfigModel}().is${effectiveField}Enabled() && (input.get${classNameModelUInstance}().get${effectiveField}() == null || input.get${classNameModelUInstance}().get${effectiveField}().trim().equals(""))) {'),
(49, 'controller.test.req.empty.springmvc', '        if (!input.get${classNameConfigModel}().is${effectiveField}Enabled() && input.get${classNameModelUInstance}().get${effectiveField}() == null) {'),
(50, 'controller.test.req.size.springmvc', '        }'),
(51, 'controller.test.req.size.springmvc', '        } else if (!input.get${classNameConfigModel}().is${effectiveField}Enabled() && input.get${classNameModelUInstance}().get${effectiveField}().length() > ${field.size}) {'),
(52, 'controller.test.opt.size.springmvc', '## nothing to do with these types'),
(53, 'controller.test.opt.size.springmvc', '            result.put("${classNameModelLInstance}.${field.fieldJavaType}", getMessage(${field.fieldJavaType}, longMsg).replaceAll("<len>", "${field.size}"));\n            //${configModelInstance}.set${effectiveField}Style(errStyle);\n            valid = false;\n        }');

-- --------------------------------------------------------

--
-- Table structure for table `MSF_TEMPLATE_EMAIL`
--

CREATE TABLE IF NOT EXISTS `MSF_TEMPLATE_EMAIL` (
`ID` int(11) NOT NULL,
  `TEMPLATE` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `MSF_USERS`
--

CREATE TABLE IF NOT EXISTS `MSF_USERS` (
`ID` int(11) NOT NULL,
  `EMAIL` varchar(60) NOT NULL,
  `PASSWORD` varchar(60) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `MSF_USERS`
--

INSERT INTO `MSF_USERS` (`ID`, `EMAIL`, `PASSWORD`) VALUES
(-3, 'sss', 'someemail@gmail.com'),
(4, 'rivasarmando271084@gmail.com', 'rivasarmando271084@gmail.com'),
(5, 'zzzz', 'mi240484@gmail.com'),
(6, 'xxxddd', 'xxxffff'),
(7, 'ddssss', 'ssdsa'),
(8, 'rivasarmando271084@gmail.com', 'test');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `MSF_ADMIN_ATEST1`
--
ALTER TABLE `MSF_ADMIN_ATEST1`
 ADD PRIMARY KEY (`int_column1`);

--
-- Indexes for table `MSF_ADMIN_ATEST2`
--
ALTER TABLE `MSF_ADMIN_ATEST2`
 ADD PRIMARY KEY (`ID_COLUMN`);

--
-- Indexes for table `MSF_ADMIN_COLUMNS`
--
ALTER TABLE `MSF_ADMIN_COLUMNS`
 ADD PRIMARY KEY (`ID`), ADD KEY `ID_TABLE` (`ID_TABLE`), ADD KEY `msf_admin_columns_ibfk_2` (`ID_COLUMN_TYPE`);

--
-- Indexes for table `MSF_ADMIN_COLUMN_TYPES`
--
ALTER TABLE `MSF_ADMIN_COLUMN_TYPES`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `MSF_ADMIN_CONNECTIONS`
--
ALTER TABLE `MSF_ADMIN_CONNECTIONS`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `MSF_ADMIN_PLUGIN`
--
ALTER TABLE `MSF_ADMIN_PLUGIN`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `MSF_ADMIN_PREFERENCES`
--
ALTER TABLE `MSF_ADMIN_PREFERENCES`
 ADD PRIMARY KEY (`ID`), ADD KEY `ID_PLUGIN` (`ID_PLUGIN`);

--
-- Indexes for table `MSF_ADMIN_TABLES`
--
ALTER TABLE `MSF_ADMIN_TABLES`
 ADD PRIMARY KEY (`ID`), ADD KEY `ID_CONNECTION` (`ID_CONNECTION`);

--
-- Indexes for table `MSF_ALL_CODE_TYPE`
--
ALTER TABLE `MSF_ALL_CODE_TYPE`
 ADD PRIMARY KEY (`ID`), ADD KEY `msf_template_code_ibfk_1` (`ID_COLUMN_TYPE`), ADD KEY `msf_template_code_ibfk_2` (`ID_CODE`);

--
-- Indexes for table `MSF_ALL_USER_MENU`
--
ALTER TABLE `MSF_ALL_USER_MENU`
 ADD PRIMARY KEY (`ID`), ADD KEY `ID_MENU` (`ID_MENU`), ADD KEY `ID_USER` (`ID_USER`);

--
-- Indexes for table `MSF_AUDIT_BODY_REQ`
--
ALTER TABLE `MSF_AUDIT_BODY_REQ`
 ADD PRIMARY KEY (`ID`), ADD KEY `ID_AUDIT` (`ID_AUDIT`);

--
-- Indexes for table `MSF_AUDIT_BODY_RESP`
--
ALTER TABLE `MSF_AUDIT_BODY_RESP`
 ADD PRIMARY KEY (`ID`), ADD KEY `ID_AUDIT` (`ID_AUDIT`);

--
-- Indexes for table `MSF_AUDIT_HEADER_REQ`
--
ALTER TABLE `MSF_AUDIT_HEADER_REQ`
 ADD PRIMARY KEY (`ID`), ADD KEY `ID_AUDIT` (`ID_AUDIT`);

--
-- Indexes for table `MSF_AUDIT_HEADER_RESP`
--
ALTER TABLE `MSF_AUDIT_HEADER_RESP`
 ADD PRIMARY KEY (`ID`), ADD KEY `ID_AUDIT` (`ID_AUDIT`);

--
-- Indexes for table `MSF_AUDIT_REQ_RESP`
--
ALTER TABLE `MSF_AUDIT_REQ_RESP`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `MSF_ERROR_TRACE`
--
ALTER TABLE `MSF_ERROR_TRACE`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `MSF_MENUS`
--
ALTER TABLE `MSF_MENUS`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `MSF_TEMPLATE_CODE`
--
ALTER TABLE `MSF_TEMPLATE_CODE`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `MSF_TEMPLATE_EMAIL`
--
ALTER TABLE `MSF_TEMPLATE_EMAIL`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `MSF_USERS`
--
ALTER TABLE `MSF_USERS`
 ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `MSF_ADMIN_ATEST1`
--
ALTER TABLE `MSF_ADMIN_ATEST1`
MODIFY `int_column1` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT for table `MSF_ADMIN_COLUMNS`
--
ALTER TABLE `MSF_ADMIN_COLUMNS`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=689;
--
-- AUTO_INCREMENT for table `MSF_ADMIN_COLUMN_TYPES`
--
ALTER TABLE `MSF_ADMIN_COLUMN_TYPES`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `MSF_ADMIN_CONNECTIONS`
--
ALTER TABLE `MSF_ADMIN_CONNECTIONS`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `MSF_ADMIN_PLUGIN`
--
ALTER TABLE `MSF_ADMIN_PLUGIN`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `MSF_ADMIN_PREFERENCES`
--
ALTER TABLE `MSF_ADMIN_PREFERENCES`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `MSF_ADMIN_TABLES`
--
ALTER TABLE `MSF_ADMIN_TABLES`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=164;
--
-- AUTO_INCREMENT for table `MSF_ALL_CODE_TYPE`
--
ALTER TABLE `MSF_ALL_CODE_TYPE`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=108;
--
-- AUTO_INCREMENT for table `MSF_ALL_USER_MENU`
--
ALTER TABLE `MSF_ALL_USER_MENU`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MSF_AUDIT_BODY_REQ`
--
ALTER TABLE `MSF_AUDIT_BODY_REQ`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MSF_AUDIT_BODY_RESP`
--
ALTER TABLE `MSF_AUDIT_BODY_RESP`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MSF_AUDIT_HEADER_REQ`
--
ALTER TABLE `MSF_AUDIT_HEADER_REQ`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MSF_AUDIT_HEADER_RESP`
--
ALTER TABLE `MSF_AUDIT_HEADER_RESP`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MSF_AUDIT_REQ_RESP`
--
ALTER TABLE `MSF_AUDIT_REQ_RESP`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MSF_ERROR_TRACE`
--
ALTER TABLE `MSF_ERROR_TRACE`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MSF_MENUS`
--
ALTER TABLE `MSF_MENUS`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MSF_TEMPLATE_CODE`
--
ALTER TABLE `MSF_TEMPLATE_CODE`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=54;
--
-- AUTO_INCREMENT for table `MSF_TEMPLATE_EMAIL`
--
ALTER TABLE `MSF_TEMPLATE_EMAIL`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `MSF_USERS`
--
ALTER TABLE `MSF_USERS`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `MSF_ADMIN_COLUMNS`
--
ALTER TABLE `MSF_ADMIN_COLUMNS`
ADD CONSTRAINT `msf_admin_columns_ibfk_1` FOREIGN KEY (`ID_TABLE`) REFERENCES `msf_admin_tables` (`ID`) ON DELETE CASCADE,
ADD CONSTRAINT `msf_admin_columns_ibfk_2` FOREIGN KEY (`ID_COLUMN_TYPE`) REFERENCES `MSF_ADMIN_COLUMN_TYPES` (`ID`) ON DELETE CASCADE;

--
-- Constraints for table `MSF_ADMIN_PREFERENCES`
--
ALTER TABLE `MSF_ADMIN_PREFERENCES`
ADD CONSTRAINT `msf_admin_preferences_ibfk_1` FOREIGN KEY (`ID_PLUGIN`) REFERENCES `MSF_ADMIN_PLUGIN` (`ID`);

--
-- Constraints for table `MSF_ADMIN_TABLES`
--
ALTER TABLE `MSF_ADMIN_TABLES`
ADD CONSTRAINT `msf_admin_tables_ibfk_1` FOREIGN KEY (`ID_CONNECTION`) REFERENCES `msf_admin_connections` (`ID`) ON DELETE CASCADE;

--
-- Constraints for table `MSF_ALL_CODE_TYPE`
--
ALTER TABLE `MSF_ALL_CODE_TYPE`
ADD CONSTRAINT `msf_template_code_ibfk_1` FOREIGN KEY (`ID_COLUMN_TYPE`) REFERENCES `MSF_ADMIN_COLUMN_TYPES` (`ID`) ON DELETE CASCADE,
ADD CONSTRAINT `msf_template_code_ibfk_2` FOREIGN KEY (`ID_CODE`) REFERENCES `MSF_TEMPLATE_CODE` (`ID`) ON DELETE CASCADE;

--
-- Constraints for table `MSF_ALL_USER_MENU`
--
ALTER TABLE `MSF_ALL_USER_MENU`
ADD CONSTRAINT `msf_all_user_menu_ibfk_1` FOREIGN KEY (`ID_MENU`) REFERENCES `msf_menus` (`ID`),
ADD CONSTRAINT `msf_all_user_menu_ibfk_2` FOREIGN KEY (`ID_USER`) REFERENCES `msf_users` (`ID`);

--
-- Constraints for table `MSF_AUDIT_BODY_REQ`
--
ALTER TABLE `MSF_AUDIT_BODY_REQ`
ADD CONSTRAINT `msf_audit_body_req_ibfk_1` FOREIGN KEY (`ID_AUDIT`) REFERENCES `msf_audit_req_resp` (`ID`);

--
-- Constraints for table `MSF_AUDIT_BODY_RESP`
--
ALTER TABLE `MSF_AUDIT_BODY_RESP`
ADD CONSTRAINT `msf_audit_body_resp_ibfk_1` FOREIGN KEY (`ID_AUDIT`) REFERENCES `msf_audit_req_resp` (`ID`);

--
-- Constraints for table `MSF_AUDIT_HEADER_REQ`
--
ALTER TABLE `MSF_AUDIT_HEADER_REQ`
ADD CONSTRAINT `msf_audit_header_req_ibfk_1` FOREIGN KEY (`ID_AUDIT`) REFERENCES `msf_audit_req_resp` (`ID`);

--
-- Constraints for table `MSF_AUDIT_HEADER_RESP`
--
ALTER TABLE `MSF_AUDIT_HEADER_RESP`
ADD CONSTRAINT `msf_audit_header_resp_ibfk_1` FOREIGN KEY (`ID_AUDIT`) REFERENCES `msf_audit_req_resp` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
