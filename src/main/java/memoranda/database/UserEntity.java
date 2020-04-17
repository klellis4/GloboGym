/**
 * This class represents the User entity in the database, the user
 * of the program. It contains all necessary information such as name, email,
 * password (used for logging in), their role (trainer, admin, student), and their
 * belt types (belt color, and trainer belt color if applicable)
 *
 */
package main.java.memoranda.database;

import java.util.Objects;

/*
UserEntity is what is used for all SQL queries related to USER table.
 */
public class UserEntity {
    private String _firstName;
    private String _lastName;
    private String _password;
    private String _email;
    private RoleEntity _role;
    private BeltEntity _belt;
    private BeltEntity _trainingBelt;

    /**
     * Constructor for a user who is not a trainer (student)
     */
    public UserEntity(String _firstName, String _lastName, String _password, String _email,
                      RoleEntity _role) {
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._password = _password;
        this._email = _email;
        this._role = _role;
    }

    /**
     * Constructor for a user who IS a trainer
     */
    public UserEntity(String _firstName, String _lastName, String _password, String _email,
                      RoleEntity _role, BeltEntity _belt, BeltEntity _trainingBelt) {
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._password = _password;
        this._email = _email;
        this._role = _role;
        this._belt = _belt;
        this._trainingBelt = _trainingBelt;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        this._email = email;
    }


    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        this._firstName = firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        this._password = password;
    }

    public RoleEntity getRole() {
        return _role;
    }

    public void setRole(RoleEntity role) {
        this._role = role;
    }

    public BeltEntity getBelt() {
        if (_belt == null) {
            return new BeltEntity(BeltEntity.Rank.white);
        } else {
            return _belt;
        }
    }

    public void setBelt(BeltEntity belt) {
        this._belt = belt;
    }

    public BeltEntity getTrainingBelt() {
        return _trainingBelt;
    }

    public void setTrainingBelt(BeltEntity trainingBelt) {
        this._trainingBelt = trainingBelt;
    }

    /**
     * Checks if two users are equal to each other
     *
     * @param o The user to compare
     * @return Returns true if the users are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return _firstName.equals(that._firstName) &&
                _lastName.equals(that._lastName) &&
                _password.equals(that._password) &&
                _email.equals(that._email) &&
                _role.equals(that._role) &&
                Objects.equals(_belt, that._belt) &&
                Objects.equals(_trainingBelt, that._trainingBelt);
    }

    /**
     * Generates a hashcode for a user
     *
     * @return Returns the hashcode as an integer
     */
    @Override
    public int hashCode() {
        return Objects.hash(_firstName, _lastName, _password, _email, _role, _belt, _trainingBelt);
    }
}
