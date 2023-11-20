package entities;


import java.util.UUID;

class Student {
    private final UUID ulearnId;
    private final String surname;
    private final String name;
    private final String email;
    private final String group;
    private final StudentGrade studentGrade;


    Student(String surname, UUID ulearnId, String name, String email, String group, StudentGrade studentGrade) {
        this.ulearnId = ulearnId;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.group = group;
        this.studentGrade = studentGrade;
    }

    public UUID getUlearnId() {
        return ulearnId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }

    public StudentGrade getStudentGrade() {
        return studentGrade;
    }
}
