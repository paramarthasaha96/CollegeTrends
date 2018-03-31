-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2018 at 08:31 PM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `college_trends`
--

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `admin_id` int(11) NOT NULL,
  `admin_user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `cn_notif_maps`
--

CREATE TABLE `cn_notif_maps` (
  `cn_notif_id` int(11) NOT NULL,
  `cn_community_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `communities`
--

CREATE TABLE `communities` (
  `community_id` int(11) NOT NULL,
  `community_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `disc_threads`
--

CREATE TABLE `disc_threads` (
  `disc_thread_id` int(11) NOT NULL,
  `disc_thread_community` int(11) NOT NULL,
  `disc_thread_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `faculty`
--

CREATE TABLE `faculty` (
  `faculty_id` int(11) NOT NULL,
  `faculty_user_id` int(11) NOT NULL,
  `faculty_stream` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `materials`
--

CREATE TABLE `materials` (
  `material_id` int(11) NOT NULL,
  `material_title` varchar(400) NOT NULL,
  `material_content` text NOT NULL,
  `material_link` varchar(400) NOT NULL,
  `material_faculty` int(11) NOT NULL,
  `material_community` int(11) NOT NULL,
  `material_thread` int(11) NOT NULL,
  `material_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `material_tags`
--

CREATE TABLE `material_tags` (
  `mt_material_id` int(11) NOT NULL,
  `mt_tag_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `notif_id` int(11) NOT NULL,
  `notif_topic` varchar(400) NOT NULL,
  `notif_type` int(11) NOT NULL,
  `notif_schedule` int(11) DEFAULT NULL,
  `notif_material` int(11) DEFAULT NULL,
  `notif_datetime` datetime NOT NULL,
  `notif_faculty` int(11) NOT NULL,
  `notif_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `schedules`
--

CREATE TABLE `schedules` (
  `schedule_id` int(11) NOT NULL,
  `schedule_community` int(11) NOT NULL,
  `schedule_type` int(11) NOT NULL,
  `schedule_datetime` datetime NOT NULL,
  `schedule_content` text,
  `schedule_link` varchar(400) NOT NULL,
  `schedule_faculty` int(11) NOT NULL,
  `schedule_thread` int(11) NOT NULL,
  `schedule_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `selected_subjects`
--

CREATE TABLE `selected_subjects` (
  `sel_student_id` int(11) NOT NULL,
  `sel_subject_id` int(11) NOT NULL,
  `sel_year` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sn_notif_maps`
--

CREATE TABLE `sn_notif_maps` (
  `sn_notif_id` int(11) NOT NULL,
  `sn_student_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `streams`
--

CREATE TABLE `streams` (
  `stream_id` int(11) NOT NULL,
  `stream_name` varchar(40) NOT NULL,
  `stream_community` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `stream_years`
--

CREATE TABLE `stream_years` (
  `year_id` int(11) NOT NULL,
  `year_no` int(11) NOT NULL,
  `year_stream_id` int(11) NOT NULL,
  `year_community` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `student_user_id` int(11) NOT NULL,
  `student_stream` int(11) NOT NULL,
  `student_year` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE `subjects` (
  `subject_id` int(11) NOT NULL,
  `subject_name` varchar(40) NOT NULL,
  `subject_stream_year` int(11) NOT NULL,
  `subject_community` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `tag_id` int(11) NOT NULL,
  `tag_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `thread_posts`
--

CREATE TABLE `thread_posts` (
  `post_id` int(11) NOT NULL,
  `post_thread_id` int(11) NOT NULL,
  `post_parent_id` int(11) DEFAULT NULL,
  `post_user` int(11) NOT NULL,
  `post_user_level` int(11) NOT NULL,
  `post_content` text NOT NULL,
  `post_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_fname` varchar(50) NOT NULL,
  `user_lname` varchar(50) NOT NULL,
  `user_mobile` int(10) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `user_pwd` text NOT NULL,
  `user_salt` text NOT NULL,
  `user_role` int(11) NOT NULL,
  `user_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_token`
--

CREATE TABLE `user_token` (
  `token_user_id` int(11) NOT NULL,
  `token_refresh` varchar(200) NOT NULL,
  `token_expiry` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `token_agent` varchar(150) NOT NULL,
  `token_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`admin_id`),
  ADD KEY `admin_user_id` (`admin_user_id`);

--
-- Indexes for table `cn_notif_maps`
--
ALTER TABLE `cn_notif_maps`
  ADD PRIMARY KEY (`cn_notif_id`,`cn_community_id`) USING BTREE,
  ADD KEY `cn_community_id` (`cn_community_id`);

--
-- Indexes for table `communities`
--
ALTER TABLE `communities`
  ADD PRIMARY KEY (`community_id`);

--
-- Indexes for table `disc_threads`
--
ALTER TABLE `disc_threads`
  ADD PRIMARY KEY (`disc_thread_id`),
  ADD KEY `disc_thread_community` (`disc_thread_community`);

--
-- Indexes for table `faculty`
--
ALTER TABLE `faculty`
  ADD PRIMARY KEY (`faculty_id`),
  ADD UNIQUE KEY `faculty_user_id` (`faculty_user_id`),
  ADD KEY `faculty_stream` (`faculty_stream`);

--
-- Indexes for table `materials`
--
ALTER TABLE `materials`
  ADD PRIMARY KEY (`material_id`),
  ADD KEY `material_community` (`material_community`),
  ADD KEY `material_faculty` (`material_faculty`),
  ADD KEY `material_thread` (`material_thread`);

--
-- Indexes for table `material_tags`
--
ALTER TABLE `material_tags`
  ADD PRIMARY KEY (`mt_material_id`,`mt_tag_id`),
  ADD KEY `mt_tag_id` (`mt_tag_id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notif_id`),
  ADD KEY `notif_material` (`notif_material`),
  ADD KEY `notif_faculty` (`notif_faculty`),
  ADD KEY `notif_schedule` (`notif_schedule`);

--
-- Indexes for table `schedules`
--
ALTER TABLE `schedules`
  ADD PRIMARY KEY (`schedule_id`),
  ADD KEY `schedule_thread` (`schedule_thread`),
  ADD KEY `schedule_faculty` (`schedule_faculty`),
  ADD KEY `schedule_community` (`schedule_community`);

--
-- Indexes for table `selected_subjects`
--
ALTER TABLE `selected_subjects`
  ADD PRIMARY KEY (`sel_student_id`,`sel_year`),
  ADD KEY `sel_subject_id` (`sel_subject_id`);

--
-- Indexes for table `sn_notif_maps`
--
ALTER TABLE `sn_notif_maps`
  ADD PRIMARY KEY (`sn_notif_id`,`sn_student_id`) USING BTREE,
  ADD KEY `sn_student_id` (`sn_student_id`);

--
-- Indexes for table `streams`
--
ALTER TABLE `streams`
  ADD PRIMARY KEY (`stream_id`),
  ADD KEY `stream_community` (`stream_community`);

--
-- Indexes for table `stream_years`
--
ALTER TABLE `stream_years`
  ADD PRIMARY KEY (`year_no`,`year_stream_id`),
  ADD UNIQUE KEY `stream_year_id` (`year_id`),
  ADD KEY `year_community` (`year_community`),
  ADD KEY `year_stream_id` (`year_stream_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`),
  ADD UNIQUE KEY `student_user_id` (`student_user_id`),
  ADD KEY `student_stream` (`student_stream`);

--
-- Indexes for table `subjects`
--
ALTER TABLE `subjects`
  ADD PRIMARY KEY (`subject_id`),
  ADD KEY `subjects_ibfk_1` (`subject_stream_year`),
  ADD KEY `subject_community` (`subject_community`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`tag_id`);

--
-- Indexes for table `thread_posts`
--
ALTER TABLE `thread_posts`
  ADD PRIMARY KEY (`post_id`),
  ADD KEY `thread_posts_ibfk_1` (`post_thread_id`),
  ADD KEY `post_user` (`post_user`),
  ADD KEY `post_parent_id` (`post_parent_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_email` (`user_email`),
  ADD UNIQUE KEY `user_mobile` (`user_mobile`);

--
-- Indexes for table `user_token`
--
ALTER TABLE `user_token`
  ADD PRIMARY KEY (`token_user_id`,`token_refresh`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `communities`
--
ALTER TABLE `communities`
  MODIFY `community_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `disc_threads`
--
ALTER TABLE `disc_threads`
  MODIFY `disc_thread_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `faculty`
--
ALTER TABLE `faculty`
  MODIFY `faculty_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `materials`
--
ALTER TABLE `materials`
  MODIFY `material_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notif_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `schedules`
--
ALTER TABLE `schedules`
  MODIFY `schedule_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `streams`
--
ALTER TABLE `streams`
  MODIFY `stream_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `stream_years`
--
ALTER TABLE `stream_years`
  MODIFY `year_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `subjects`
--
ALTER TABLE `subjects`
  MODIFY `subject_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `tag_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `thread_posts`
--
ALTER TABLE `thread_posts`
  MODIFY `post_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admins`
--
ALTER TABLE `admins`
  ADD CONSTRAINT `admins_ibfk_1` FOREIGN KEY (`admin_user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `cn_notif_maps`
--
ALTER TABLE `cn_notif_maps`
  ADD CONSTRAINT `cn_notif_maps_ibfk_1` FOREIGN KEY (`cn_community_id`) REFERENCES `communities` (`community_id`),
  ADD CONSTRAINT `cn_notif_maps_ibfk_2` FOREIGN KEY (`cn_notif_id`) REFERENCES `notifications` (`notif_id`);

--
-- Constraints for table `disc_threads`
--
ALTER TABLE `disc_threads`
  ADD CONSTRAINT `disc_threads_ibfk_1` FOREIGN KEY (`disc_thread_community`) REFERENCES `communities` (`community_id`);

--
-- Constraints for table `faculty`
--
ALTER TABLE `faculty`
  ADD CONSTRAINT `faculty_ibfk_1` FOREIGN KEY (`faculty_user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `faculty_ibfk_2` FOREIGN KEY (`faculty_stream`) REFERENCES `streams` (`stream_id`);

--
-- Constraints for table `materials`
--
ALTER TABLE `materials`
  ADD CONSTRAINT `materials_ibfk_1` FOREIGN KEY (`material_community`) REFERENCES `communities` (`community_id`),
  ADD CONSTRAINT `materials_ibfk_2` FOREIGN KEY (`material_faculty`) REFERENCES `faculty` (`faculty_id`),
  ADD CONSTRAINT `materials_ibfk_3` FOREIGN KEY (`material_thread`) REFERENCES `disc_threads` (`disc_thread_id`);

--
-- Constraints for table `material_tags`
--
ALTER TABLE `material_tags`
  ADD CONSTRAINT `material_tags_ibfk_1` FOREIGN KEY (`mt_material_id`) REFERENCES `materials` (`material_id`),
  ADD CONSTRAINT `material_tags_ibfk_2` FOREIGN KEY (`mt_tag_id`) REFERENCES `tags` (`tag_id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_2` FOREIGN KEY (`notif_material`) REFERENCES `materials` (`material_id`),
  ADD CONSTRAINT `notifications_ibfk_3` FOREIGN KEY (`notif_faculty`) REFERENCES `faculty` (`faculty_id`),
  ADD CONSTRAINT `notifications_ibfk_4` FOREIGN KEY (`notif_schedule`) REFERENCES `schedules` (`schedule_id`);

--
-- Constraints for table `schedules`
--
ALTER TABLE `schedules`
  ADD CONSTRAINT `schedules_ibfk_3` FOREIGN KEY (`schedule_thread`) REFERENCES `disc_threads` (`disc_thread_id`),
  ADD CONSTRAINT `schedules_ibfk_4` FOREIGN KEY (`schedule_faculty`) REFERENCES `faculty` (`faculty_id`),
  ADD CONSTRAINT `schedules_ibfk_5` FOREIGN KEY (`schedule_community`) REFERENCES `communities` (`community_id`);

--
-- Constraints for table `selected_subjects`
--
ALTER TABLE `selected_subjects`
  ADD CONSTRAINT `selected_subjects_ibfk_1` FOREIGN KEY (`sel_student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `selected_subjects_ibfk_2` FOREIGN KEY (`sel_subject_id`) REFERENCES `subjects` (`subject_id`);

--
-- Constraints for table `sn_notif_maps`
--
ALTER TABLE `sn_notif_maps`
  ADD CONSTRAINT `sn_notif_maps_ibfk_1` FOREIGN KEY (`sn_notif_id`) REFERENCES `notifications` (`notif_id`),
  ADD CONSTRAINT `sn_notif_maps_ibfk_2` FOREIGN KEY (`sn_student_id`) REFERENCES `students` (`student_id`);

--
-- Constraints for table `streams`
--
ALTER TABLE `streams`
  ADD CONSTRAINT `streams_ibfk_1` FOREIGN KEY (`stream_community`) REFERENCES `communities` (`community_id`);

--
-- Constraints for table `stream_years`
--
ALTER TABLE `stream_years`
  ADD CONSTRAINT `stream_years_ibfk_1` FOREIGN KEY (`year_community`) REFERENCES `communities` (`community_id`),
  ADD CONSTRAINT `stream_years_ibfk_2` FOREIGN KEY (`year_stream_id`) REFERENCES `streams` (`stream_id`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`student_user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `students_ibfk_2` FOREIGN KEY (`student_stream`) REFERENCES `streams` (`stream_id`);

--
-- Constraints for table `subjects`
--
ALTER TABLE `subjects`
  ADD CONSTRAINT `subjects_ibfk_1` FOREIGN KEY (`subject_stream_year`) REFERENCES `stream_years` (`year_id`),
  ADD CONSTRAINT `subjects_ibfk_2` FOREIGN KEY (`subject_community`) REFERENCES `communities` (`community_id`);

--
-- Constraints for table `thread_posts`
--
ALTER TABLE `thread_posts`
  ADD CONSTRAINT `thread_posts_ibfk_1` FOREIGN KEY (`post_thread_id`) REFERENCES `disc_threads` (`disc_thread_id`),
  ADD CONSTRAINT `thread_posts_ibfk_3` FOREIGN KEY (`post_user`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `thread_posts_ibfk_4` FOREIGN KEY (`post_parent_id`) REFERENCES `thread_posts` (`post_id`);

--
-- Constraints for table `user_token`
--
ALTER TABLE `user_token`
  ADD CONSTRAINT `user_token_ibfk_1` FOREIGN KEY (`token_user_id`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
