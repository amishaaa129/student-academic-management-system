package com.college.dao;

import com.college.config.DBConnection;
import com.college.exception.DatabaseException;
import com.college.model.Notice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAO {

    public List<Notice> getAllNotices() throws DatabaseException {

        List<Notice> notices = new ArrayList<>();

        String sql = """
                SELECT n.notice_id, n.title, n.content,
                       u.username, n.created_at
                FROM notices n
                JOIN users u ON n.posted_by = u.user_id
                ORDER BY n.created_at DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                notices.add(new Notice(
                        rs.getInt("notice_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("username"),
                        rs.getTimestamp("created_at").toString()
                ));
            }

            return notices;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to retrieve notices", e
            );
        }
    }
}