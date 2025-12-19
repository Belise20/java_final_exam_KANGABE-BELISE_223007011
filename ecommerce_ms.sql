-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Dec 19, 2025 at 08:59 AM
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
-- Database: `ecommerce_ms`
--

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` double NOT NULL,
  `status` enum('pending','processing','shipped','delivered','cancelled') DEFAULT 'pending',
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `order_date`, `total_amount`, `status`) VALUES
(1, 2, '2025-10-25 11:57:35', 9000, 'delivered'),
(2, 3, '2025-10-25 11:57:35', 185000, 'processing'),
(3, 2, '2025-10-25 11:57:35', 85000, 'shipped'),
(4, 2, '2025-10-31 10:34:21', 30000, 'pending'),
(5, 7, '2025-10-31 10:48:41', 75000, 'processing'),
(6, 7, '2025-10-31 10:48:52', 75000, 'pending'),
(7, 7, '2025-10-31 10:49:16', 10000, 'processing'),
(8, 8, '2025-10-31 10:56:42', 300000, 'processing'),
(9, 8, '2025-10-31 11:05:19', 10000, 'pending'),
(10, 2, '2025-10-31 11:58:56', 1200000, 'pending'),
(11, 2, '2025-10-31 15:42:27', 75000, 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
CREATE TABLE IF NOT EXISTS `order_items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`item_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`item_id`, `order_id`, `product_id`, `quantity`, `price`) VALUES
(1, 1, 3, 1, 9000),
(2, 2, 4, 1, 85000),
(3, 2, 5, 1, 100000),
(4, 3, 4, 1, 85000),
(5, 4, 8, 2, 15000),
(6, 5, 7, 3, 25000),
(7, 6, 7, 3, 25000),
(8, 7, 6, 1, 10000),
(9, 8, 5, 3, 100000),
(10, 9, 6, 1, 10000),
(11, 10, 3, 1, 1200000),
(12, 11, 7, 3, 25000);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
CREATE TABLE IF NOT EXISTS `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `payment_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` double NOT NULL,
  `payment_method` enum('cash','credit card','mobile money') DEFAULT 'cash',
  `status` enum('paid','pending','failed') DEFAULT 'pending',
  PRIMARY KEY (`payment_id`),
  KEY `order_id` (`order_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`payment_id`, `order_id`, `payment_date`, `amount`, `payment_method`, `status`) VALUES
(1, 1, '2025-10-25 12:05:00', 9000, 'mobile money', 'paid'),
(2, 2, '2025-10-25 12:05:00', 185000, 'credit card', 'pending'),
(3, 3, '2025-10-25 12:05:00', 85000, 'cash', 'failed'),
(4, 3, '2025-10-31 10:44:38', 85000, 'mobile money', 'paid'),
(5, 5, '2025-10-31 10:49:56', 75000, 'credit card', 'paid'),
(6, 7, '2025-10-31 10:50:21', 10000, 'cash', 'paid'),
(7, 8, '2025-10-31 10:57:35', 300000, 'credit card', 'paid');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `quantity` int DEFAULT '0',
  `category` varchar(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `product_name`, `price`, `quantity`, `category`, `created_at`) VALUES
(4, 'HP Laptop', 85000, 10, 'Electronics', '2025-10-23 16:45:35'),
(3, 'TVs FlatScreen', 1200000, 19, 'Electronics', '2025-10-23 16:04:29'),
(5, 'Wireless Mouse', 100000, 27, 'Accessories', '2025-10-23 16:47:34'),
(6, 'Keyboard', 10000, 28, 'Accessories', '2025-10-26 14:24:20'),
(7, 'cable', 25000, 21, 'Accessories', '2025-10-29 14:31:35'),
(8, 'speaker', 15000, 18, 'Electronics', '2025-10-29 14:33:49');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `role` enum('admin','customer') DEFAULT 'customer',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `phone`, `role`, `created_at`) VALUES
(6, 'becky', 'becky12', 'beckyliu@gmail.com', '0798233601', 'customer', '2025-10-29 14:26:12'),
(2, 'Belise', 'beli12', 'belis20@gmail.com', '0784444465', 'customer', '2025-10-22 13:18:12'),
(3, 'Johnson', 'john123', 'johndoe@gmail.com', '0787906489', 'customer', '2025-10-22 13:19:41'),
(4, 'Kangabe', '12345', 'managerecom@gmail.com', '0790893340', 'admin', '2025-10-22 13:22:06'),
(5, 'Giant', '1234567890', 'giant@gmail.com', '0788888888', 'customer', '2025-10-26 12:51:28'),
(7, 'KB', 'kb22', 'kb1234@gmail.com', '0789189234', 'customer', '2025-10-31 10:46:40'),
(8, 'Heaven', '12345', 'heaven@gmail.com', '0734444565', 'customer', '2025-10-31 10:56:03');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
