package main.java.memoranda.database.util;

import main.java.memoranda.database.*;


import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DbReadQueries {

    public static final int MIN_PER_HOUR = 60;
    public static final double MOVE_DECIMAL_LEFT_ONE = .1;
    private String dbURL;

    public DbReadQueries(String url) {
        this.dbURL = url;
    }

    public UserEntity getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE Email=?";

        Connection conn = DriverManager.getConnection(dbURL);
        PreparedStatement pstmt  = conn.prepareStatement(sql);
        pstmt.setString(1,email);
        ResultSet rs  = pstmt.executeQuery();


        UserEntity user = getUserFromResultSet(rs);
        return user;
    }

    public ArrayList<UserEntity> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM user";

        Connection conn = DriverManager.getConnection(dbURL);
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        ArrayList<UserEntity> users = new ArrayList<>();
        while(rs.next()){
            users.add(getUserFromResultSet(rs));
        }
        return users;
    }

    public ArrayList<GymClassEntity> getClassesUserEnrolledInByEmail(String email) throws SQLException, ParseException {
        String sql = "SELECT * FROM GYMCLASS " +
                     "INNER JOIN ENROLLEDUSER on ENROLLEDUSER.ClassId = GYMCLASS.Id " +
                     "WHERE ENROLLEDUSER.UserEmail=?";

        Connection conn = DriverManager.getConnection(dbURL);
        PreparedStatement pstmt  = conn.prepareStatement(sql);
        pstmt.setString(1,email);
        ResultSet rs  = pstmt.executeQuery();

        ArrayList<GymClassEntity> gymClasses = new ArrayList<>();
        while(rs.next()){
            gymClasses.add(getGymClassFromResultSet(rs));
        }
        return gymClasses;
    }

    public ArrayList<TrainerAvailabilityEntity> getTrainerDateTimeAvailabilityByEmail(String email) throws SQLException, ParseException {
        String sql = "SELECT * FROM TRAINERAVAILABILITY WHERE TRAINERAVAILABILITY.TrainerEmail=?";

        Connection conn = DriverManager.getConnection(dbURL);
        PreparedStatement pstmt  = conn.prepareStatement(sql);
        pstmt.setString(1,email);
        ResultSet rs  = pstmt.executeQuery();

        ArrayList<TrainerAvailabilityEntity> trainerAvailabilities = new ArrayList<>();
        while(rs.next()){
            LocalDateTime startDateTime = getLocalDateTimeFromDbFields(
                    rs.getString("StartDate"),
                    rs.getDouble("StartTime"));
            LocalDateTime stopDateTime = getLocalDateTimeFromDbFields(
                    rs.getString("StartDate"),
                    rs.getDouble("EndTime"));
            trainerAvailabilities.add(new TrainerAvailabilityEntity(startDateTime, stopDateTime));
        }
        return trainerAvailabilities;
    }

    private GymClassEntity getGymClassFromResultSet(ResultSet rs) throws SQLException, ParseException {

        LocalDateTime startDateTime = getLocalDateTimeFromDbFields(
                rs.getString("StartDate"),
                rs.getDouble("StartTime"));

        LocalDateTime endDateTime = getLocalDateTimeFromDbFields(
                rs.getString("StartDate"),
                rs.getDouble("EndTime"));

        BeltEntity minBeltRequired = new BeltEntity(BeltEntity.Rank.valueOf(rs.getString("MinBeltRequired")));

        GymClassEntity gymClass = new GymClassEntity(
                rs.getInt("Id"),
                rs.getInt("RoomNumber"),
                startDateTime,
                endDateTime,
                rs.getString("TrainerEmail"),
                rs.getInt("MaxClassSize"),
                minBeltRequired,
                rs.getString("CreatedByEmail")
                );
        return gymClass;
    }

    private LocalDateTime getLocalDateTimeFromDbFields(String strDate, double time) throws ParseException {
        //get LocalDate
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.parse(strDate, f);

        //get LocalTime
        String strTime = String.valueOf(time);
        String[] timeParts = strTime.split("\\.");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = (int) (Integer.parseInt(timeParts[1]) * MIN_PER_HOUR * MOVE_DECIMAL_LEFT_ONE);
        LocalTime localTime = LocalTime.of(hours, minutes);

        return LocalDateTime.of(localDate, localTime);
    }

    private UserEntity getUserFromResultSet(ResultSet rs) throws SQLException {
        String strBelt = rs.getString("Belt");
        BeltEntity belt = null;
        if (strBelt != null){
            belt = new BeltEntity(BeltEntity.Rank.valueOf(strBelt.toLowerCase()));
        }
        String strTrainingBelt = rs.getString("TrainingBelt");
        BeltEntity trainingBelt = null;
        if(strTrainingBelt != null){
            trainingBelt = new BeltEntity(BeltEntity.Rank.valueOf(strTrainingBelt.toLowerCase()));
        }
        String strRole = rs.getString("Role");
        RoleEntity role = new RoleEntity(RoleEntity.UserRole.valueOf(strRole.toLowerCase()));

        return new UserEntity(rs.getString("FirstName"),
                                        rs.getString("LastName"),
                                        rs.getString("Password"),
                                        rs.getString("Email"),
                                        role,
                                        belt,
                                        trainingBelt);
    }
}
