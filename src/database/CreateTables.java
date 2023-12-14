package database;

import java.sql.*;

public class CreateTables {
    private final String databaseUrl;
    private final String user;
    private final String password;

    public CreateTables(String databaseUrl, String user, String password) {
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
    }

    public void createTables() throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, user, password);
        createTableStudents(connection);
        createTableStudentGrade(connection);
        createTableSections(connection);
        createTableTasks(connection);
        connection.close();
    }

    private void createTableStudents(Connection connection) throws SQLException {
        String studentTableQuery = "create table students" +
                "(\n" +
                "    ulearn_id  uuid         not null\n" +
                "        constraint students_pk\n" +
                "            primary key,\n" +
                "    surname    varchar(100) not null,\n" +
                "    name       varchar(100) not null,\n" +
                "    email      varchar(100) not null,\n" +
                "    group_name varchar(300) not null\n" +
                ");\n" +
                "\n" +
                "alter table students\n" +
                "    owner to akhmeto_vi;\n" +
                "\n";
        PreparedStatement statement = connection.prepareStatement(studentTableQuery);
        statement.execute();
    }

    private void createTableStudentGrade(Connection connection) throws SQLException {
        String studentGradeQuery = "create table student_grade\n" +
                "(\n" +
                "    ulearn_id              uuid    not null\n" +
                "        constraint student_grade_students_ulearn_id_fk\n" +
                "            references students,\n" +
                "    max_activities_score   integer not null,\n" +
                "    max_exercise_score     integer not null,\n" +
                "    max_practice_score     integer not null,\n" +
                "    max_seminar_score      integer not null,\n" +
                "    total_activities_score integer not null,\n" +
                "    total_exercise_score   integer not null,\n" +
                "    total_practice_score   integer not null,\n" +
                "    total_seminar_score    integer not null\n" +
                ");\n" +
                "\n" +
                "alter table student_grade\n" +
                "    owner to akhmeto_vi;\n" +
                "\n";
        PreparedStatement statement = connection.prepareStatement(studentGradeQuery);
        statement.execute();
    }

    private void createTableSections(Connection connection) throws SQLException {
        String sectionTableQuery = "create table sections\n" +
                "(\n" +
                "    ulearn_id          uuid         not null\n" +
                "        constraint sections_students_ulearn_id_fk\n" +
                "            references students,\n" +
                "    section_id         uuid         not null,\n" +
                "    section_name       varchar(200) not null,\n" +
                "    max_exercise_score integer      not null,\n" +
                "    max_practice_score integer      not null,\n" +
                "    max_seminar_score  integer      not null\n" +
                ");\n" +
                "\n" +
                "alter table sections\n" +
                "    owner to akhmeto_vi;\n" +
                "\n";
        PreparedStatement statement = connection.prepareStatement(sectionTableQuery);
        statement.execute();
    }

    private void createTableTasks(Connection connection) throws SQLException {
        String tasksTableQuery = "create table tasks\n" +
                "(\n" +
                "    ulearn_id     uuid         not null\n" +
                "        constraint tasks_students_ulearn_id_fk\n" +
                "            references students,\n" +
                "    task_id       uuid         not null,\n" +
                "    task_type     varchar(15)  not null,\n" +
                "    max_score     integer      not null,\n" +
                "    student_score integer      not null,\n" +
                "    task_name     varchar(200) not null,\n" +
                "    section_id    uuid         not null\n" +
                ");\n" +
                "\n" +
                "alter table tasks\n" +
                "    owner to akhmeto_vi;\n" +
                "\n";
        PreparedStatement statement = connection.prepareStatement(tasksTableQuery);
        statement.execute();
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
