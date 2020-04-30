package main.java.memoranda.gym;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import main.java.memoranda.database.entities.BeltEntity;
import main.java.memoranda.database.entities.GymClassEntity;
import main.java.memoranda.database.entities.RoleEntity;
import main.java.memoranda.database.SqlConnection;
import main.java.memoranda.database.entities.TrainerAvailabilityEntity;
import main.java.memoranda.database.entities.UserEntity;
import main.java.memoranda.ui.App;

/**
 * ---CreateMethods
 * createAdmin
 * createTrainer
 * createCustomer
 * createGroupClass
 * createPrivateClass
 * ---RemoveMethods
 * readGetUser
 * ---UpdateMethods
 * ---DeleteMethods
 * deleteUser
 */
public class Gym {

    //This variable will be the currently logged in user.
    private UserEntity user;
    private static SqlConnection conn;

    public enum UserRole {
        admin,
        trainer,
        customer
    }

    public Gym() {

        this.user = null;
        this.conn = null;

        try {
            this.conn = SqlConnection.getInstance();
        } catch (SQLException ecp) {
            ecp.printStackTrace();
        }
    }

    //TODO
    public boolean login() {

        //insert login code

        //assign this.user;

        //TODO
        return false;
    }

    /**
     * Returns Role Entity.
     *
     * @return the Role Entity
     */
    public RoleEntity getUserRole() {
        return user.getRole();
    }

    //TODO needs refactoring
    public static ArrayList<GymClassEntity> getEnrolledClassesByEmailAndDate(String email,
                                                                             LocalDate selectedCalendarDate) {
        try {
            return App.conn.getDrq().getEnrolledClassByEmailAndDate(email, selectedCalendarDate);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    //DB Encapsulated Create methods /////////////////////////////////////////////////////////

    /*


        String userEmail = extractTrainerEmail();
        double start = Local.getDoubleTime(startTimeCB.getSelectedItem().toString());
        double end = Local.getDoubleTime(endTimeCB.getSelectedItem().toString());
        LocalDate date = this.date;

     */

    /**
     * Creates trainer availability.
     * @param email The email of the trainer.
     * @param startTime The start time.
     * @param endTime The end time.
     * @param date the date.
     * @return
     */
    public Response createTrainerAvailability(String email, double startTime, double endTime,
                                              LocalDate date) {


        TrainerAvailabilityEntity newTAE =
            new TrainerAvailabilityEntity(email, date, startTime, endTime);

        //check if the user is a trainer
        //PRIMARY KEY(TrainerEmail, StartDate, StartTime, EndTime)

        try {

            UserEntity ue = conn.getDrq().getUserByEmail(email);

            if (ue == null) {
                return Response.failure("Error: User does not exists.");
            }
            if (!ue.isTrainer()) {
                return Response.failure("Error: User is not a trainer.");
            }
            ArrayList<TrainerAvailabilityEntity> curAvail =
                conn.getDrq().getTrainerDateTimeAvailabilityByEmail(email);

            //returns values in the format of 1998-04-28T05:00

            //need to see what is the current availabilty
            for (int i = 0; i < curAvail.size(); i++) {
                TrainerAvailabilityEntity te = curAvail.get(i);
                if (te.equals(newTAE)) {
                    return Response.failure("Error: This availability already exists");
                }
            }

            String startDate = date.toString();

            conn.getDcq().insertTrainerAvailability(email,startDate,startTime,endTime);
            curAvail = conn.getDrq().getTrainerDateTimeAvailabilityByEmail(email);

            for (int i = 0; i < curAvail.size(); i++) {
                TrainerAvailabilityEntity te = curAvail.get(i);
                if (te.equals(newTAE)) {
                    return Response.success("Success: Trainers availability has been added.");
                }
            }

        } catch (SQLException ecp) {
            ecp.printStackTrace();
            return Response.failure("Error: SQL error.");
        }

        return Response.failure("Error: You should not see this.");
    }


    /**
     * Creates an admin user if the user does not already exists in the system.
     *
     * @param email The email used.
     * @param fname The first name.
     * @param lname The last name.
     * @param pwd   The password.
     * @return A Response which contains a bool that dictates success/failure and the msg on why
     */
    public Response createAdmin(String email,
                                String fname,
                                String lname,
                                String pwd) {

        try {
            //test if the user already exists.
            UserEntity ue = conn.getDrq().getUserByEmail(email);
            if (ue == null) {
                //starting and training are BLACK based on the admin role
                BeltEntity be = new BeltEntity("black3");
                RoleEntity re = new RoleEntity("admin");
                conn.getDcq().insertUser(email, fname, lname, pwd, re, be, be);
            } else {
                return Response.failure("Error: User already exists.");
            }

        } catch (SQLException ecp) {
            ecp.printStackTrace();
            return Response.failure("Error: SQL error.");
        }

        return Response.success("Success: Admin created.");
    }

    /**
     * Creates an customer user if the user does not already exists in the system.
     * The belt for the customer is automatically assigned white.
     *
     * @param email The email used.
     * @param fname The first name.
     * @param lname The last name.
     * @param pwd   The password.
     * @return A Response which contains a bool that dictates success/failure and the msg on why
     */
    public Response createCustomer(String email,
                                   String fname,
                                   String lname,
                                   String pwd) {

        try {
            //test if the user already exists.
            UserEntity ue = conn.getDrq().getUserByEmail(email);
            if (ue == null) {
                //starting and training are BLACK based on the admin role
                BeltEntity be = new BeltEntity("white");
                RoleEntity re = new RoleEntity("customer");
                conn.getDcq().insertUser(email, fname, lname, pwd, re, be);
            } else {
                return Response.failure("Error: User already exists.");
            }

        } catch (SQLException ecp) {
            ecp.printStackTrace();
            return Response.failure("Error: SQL error.");
        }

        return Response.success("Success: Customer created.");
    }

    /**
     * Creates an trainer user if the user does not already exists in the system.
     * The trainers belt is its training rank.
     *
     * @param email The email used.
     * @param fname The first name.
     * @param lname The last name.
     * @param pwd   The password.
     * @return A Response which contains a bool that dictates success/failure and the msg on why
     */
    public Response createTrainer(String email, String fname, String lname,
                                  String pwd, BeltEntity trainingRank) {

        try {
            //test if the user already exists.
            UserEntity ue = conn.getDrq().getUserByEmail(email);
            if (ue == null) {
                //starting and training are the training rank
                RoleEntity re = new RoleEntity("trainer");
                conn.getDcq().insertUser(email, fname, lname, pwd, re, trainingRank, trainingRank);
            } else {
                return Response.failure("Error: User already exists.");
            }

        } catch (SQLException ecp) {
            ecp.printStackTrace();
            return Response.failure("Error: SQL error.");
        }

        return Response.success("Success: Trainer created.");
    }


    /**
     * Creates a group class if a class does not already exist at the starttime,date, and room number.
     *
     * @param rNum           The room number.
     * @param date           The start date.
     * @param sTime          The start time.
     * @param eTime          The end time.
     * @param tEmail         The email of the trainer.
     * @param maxClassSize   The max class size.
     * @param minBelt        The minimum belt required to enter this class.
     * @param createdByEmail The email of who created this course.
     * @return A Response which contains a bool that dictates success/failure and the msg on why
     */
    public Response createGroupClass(int rNum, LocalDate date, double sTime, double eTime,
                                     String tEmail, int maxClassSize, BeltEntity minBelt,
                                     String createdByEmail) {

        try {

            UserEntity ue = conn.getDrq().getUserByEmail(tEmail);

            if (!BeltEntity.checkBeltRank(ue.getBelt().toString(), minBelt.toString())) {
                Response.failure("Error: Trainer rank is too low");
            }

            ArrayList<GymClassEntity> gce =
                conn.getDrq().getAllClassesByDateTime(date, sTime, rNum);

            if (gce.size() == 0) {
                //unique values rNum + sTime should be unique
                conn.getDcq().insertClass(rNum, date, sTime, eTime,
                    tEmail, maxClassSize, minBelt, createdByEmail);
            } else {
                return Response.failure("Error: Class already exists");
            }

        } catch (SQLException ecp) {
            ecp.printStackTrace();
            return Response.failure("Error: SQL error.");
        }

        return Response.success("Success: Class created.");

    }

    /**
     * Creates a private class if a class does not already exist at the starttime, date, and room number.
     * The private class method wraps the group class, but hard codes the size to 2. Thats because a private
     * class is by definition a class with only 2 people in it.
     *
     * @param rNum           The room number.
     * @param date           The start date.
     * @param sTime          The start time.
     * @param eTime          The end time.
     * @param tEmail         The email of the trainer.
     * @param minBelt        The minimum belt required to enter this class.
     * @param createdByEmail The email of who created this course.
     * @return A Response which contains a bool that dictates success/failure and the msg on why
     */
    public Response createPrivateClass(int rNum, LocalDate date, double sTime, double eTime,
                                       String tEmail, BeltEntity minBelt,
                                       String createdByEmail) {

        return createGroupClass(rNum, date, sTime, eTime,
            tEmail, 2, minBelt, createdByEmail);
    }

    //DB Encapsulated Read methods /////////////////////////////////////////////////////////

    public Response readGetUser(String email) {
        UserEntity ue = null;
        try {
            ue = conn.getDrq().getUserByEmail(email);
            if (ue == null) {
                return Response.failure("Error: User does not exist");
            }
        } catch (SQLException ecp) {
            ecp.printStackTrace();
            return Response.failure("Error: SQL error.");
        }

        return Response.success("Success: User retreived", ue);
    }

    //DB Encapsulated Update methods /////////////////////////////////////////////////////////

    /**
     *  //TODO
     * @param email
     * @return
     */
    public Response updateUserPassword(String email) {

        UserEntity ue = null;
        try {
            ue = conn.getDrq().getUserByEmail(email);

            if (ue == null) {
                return Response.failure("Error: Cannot delete, user does not exist.");
            }

            conn.getDbd().deleteUser(email);
            ue = conn.getDrq().getUserByEmail(email);

            if (ue == null) {
                return Response.success("Success: User deleted");
            }

            return Response.failure("Error: User is not deleted.");
        } catch (SQLException ecp) {
            ecp.printStackTrace();
            return Response.failure("Error: SQL error.");
        }


    }


    //DB Encapsulated Delete methods /////////////////////////////////////////////////////////

    /**
     * Deletes a user from the database.
     * @param email the email of the user.
     * @return the response.
     */
    public Response deleteUser(String email) {
        UserEntity ue = null;
        try {
            ue = conn.getDrq().getUserByEmail(email);

            if (ue == null) {
                return Response.failure("Error: Cannot delete, user does not exist.");
            }

            conn.getDbd().deleteUser(email);
            ue = conn.getDrq().getUserByEmail(email);

            if (ue == null) {
                return Response.success("Success: User deleted");
            }

            return Response.failure("Error: User is not deleted.");
        } catch (SQLException ecp) {
            ecp.printStackTrace();
            return Response.failure("Error: SQL error.");
        }

    }
}


