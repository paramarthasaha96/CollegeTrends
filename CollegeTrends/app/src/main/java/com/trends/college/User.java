package com.trends.college;

class User {

    private String fName;
    private String lName;
    private String email;
    private String phone;
    private String student_id;
    private String student_year;
    private String student_stream;
    private String faculty_id;
    private String faculty_stream;
    private int accountType; //0=student,1=faculty

    User(String fName, String lName, String email, String phone) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
    }

    public User(String fName, String lName, String email, String phone, String faculty_id, String faculty_stream, int accountType) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
        this.faculty_id = faculty_id;
        this.faculty_stream = faculty_stream;
        this.accountType = accountType;
    }

    public User(String fName, String lName, String email, String phone, String student_id, String student_year, String student_stream, int accountType) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
        this.student_id = student_id;
        this.student_year = student_year;
        this.student_stream = student_stream;
        this.accountType = accountType;
    }

    public String getStudent_id() {
        return student_id;
    }

    public String getStudent_year() {
        return student_year;
    }

    String getStudent_stream() {
        return student_stream;
    }

    String getFaculty_id() {
        return faculty_id;
    }

    String getFaculty_stream() {
        return faculty_stream;
    }

    int getAccountType() {
        return accountType;
    }

    String getlName() {
        return lName;
    }

    String getEmail() {
        return email;
    }

    String getPhone() {
        return phone;
    }

    String getfName() {
        return fName;
    }
}