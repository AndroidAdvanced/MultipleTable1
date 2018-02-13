package com.app.mgu.multipletable1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tan on 1/27/2016.
 */
public class StudentCourseRepo {

    private final String TAG = StudentCourseRepo.class.getSimpleName().toString();

    Context mContext;

    public StudentCourseRepo(Context mContext) {
        this.mContext = mContext;
    }

    private StudentCourse studentCourse;

    public static String createTable(){
        return "CREATE TABLE " + StudentCourse.TABLE  + "("
                + StudentCourse.KEY_RunningID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StudentCourse.KEY_StudID + " TEXT, "
                + StudentCourse.KEY_CourseId + " TEXT, "
                + StudentCourse.KEY_Grade + " TEXT )";
    }


    public void insert(StudentCourse studentCourse) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(studentCourse.KEY_StudID, studentCourse.getStudentId());
        values.put(studentCourse.KEY_CourseId, studentCourse.getCourseId());
        values.put(studentCourse.KEY_Grade, studentCourse.getGrade());

        // Inserting Row
        db.insert(StudentCourse.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();


    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(StudentCourse.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<StudentCourseList> getStudentCourse(){

        StudentCourseList studentCourseList = new StudentCourseList();
        List<StudentCourseList> studentCourseLists = new ArrayList<StudentCourseList>();

     /*   SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  " SELECT Student." + Student.KEY_StudID
                + ", Student." + Student.KEY_Name
                + ", Course." + Course.KEY_CourseId
                + ", Course." + Course.KEY_Name + " As CourseName"
                + ", StuCourse." + StudentCourse.KEY_Grade
                + ", Major." + Major.KEY_MajorId
                + ", Major." + Major.KEY_Name + " As MajorName"
                + " FROM " + Student.TABLE + "  As Student "
                + " INNER JOIN " + StudentCourse.TABLE + " StuCourse ON StuCourse." + StudentCourse.KEY_StudID + " =  Student." + Student.KEY_StudID
                + " INNER JOIN " + Course.TABLE + " Course ON Course." + Course.KEY_CourseId + "=  StuCourse." + StudentCourse.KEY_CourseId
                + " INNER JOIN " + Major.TABLE + " Major ON Major." + Major.KEY_MajorId + "=  Student." + Student.KEY_MajorId
                ;
*/
      /*  SELECT Student.StudentId, Student.Name, Course.CourseId, Course.Name As CourseName,
        StuCourse.Grade, Major.MajorId, Major.Name As MajorName FROM Student  As Student
        INNER JOIN StudentCourse StuCourse ON StuCourse.StudentId =  Student.StudentId INNER
        JOIN Course Course ON Course.CourseId=  StuCourse.CourseId INNER JOIN Major Major ON Major.MajorId=  Student.MajorId*/

    //    System.out.println("Query Join :" + selectQuery);

        /*Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                studentCourseList= new StudentCourseList();
                studentCourseList.setStudentId(cursor.getString(cursor.getColumnIndex(Student.KEY_StudID)));
                studentCourseList.setStudentName(cursor.getString(cursor.getColumnIndex(Student.KEY_Name)));
                studentCourseList.setCourseID(cursor.getString(cursor.getColumnIndex(Course.KEY_CourseId)));
                studentCourseList.setCourseName(cursor.getString(cursor.getColumnIndex("CourseName")));
                studentCourseList.setGrade(cursor.getString(cursor.getColumnIndex(StudentCourse.KEY_Grade)));
                studentCourseList.setMajorId(cursor.getString(cursor.getColumnIndex(Major.KEY_MajorId)));
                studentCourseList.setMajorName(cursor.getString(cursor.getColumnIndex("MajorName")));

                studentCourseLists.add(studentCourseList);
            } while (cursor.moveToNext());
        }*/

      //  cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return studentCourseLists;

    }

    //Show you the INNER JOIN
//Only the matched records between tables will show, To join records from both tables,
//we need to use "ON" keyword.
//For example, to join StudentCourse and Course Table, Course.CourseId =  StudentCourse.CourseId
    public List<CourseGradeCount> getCourseGradeCount(){

        CourseGradeCount courseGradeCount = new CourseGradeCount();
        List<CourseGradeCount> courseGradeCounts = new ArrayList<CourseGradeCount>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

       /* String selectQuery =  " SELECT Course." + Course.KEY_CourseId
                + ", Course." + Course.KEY_Name
                + ", StudentCourse." + StudentCourse.KEY_Grade
                + ", COUNT('') AS Total"
                + " FROM " + StudentCourse.TABLE
                + " INNER JOIN " + Course.TABLE + " Course ON Course." + Course.KEY_CourseId + "=  StudentCourse." + StudentCourse.KEY_CourseId
                + " GROUP BY Course." + Course.KEY_CourseId + ", Course." + Course.KEY_Name
                + " ORDER BY Course." + Course.KEY_Name
                ;*/

        String selectQuery =  " SELECT Course." + Course.KEY_CourseId
                + ", Course." + Course.KEY_Name
                + ", StudentCourse." + StudentCourse.KEY_Grade
                + " FROM " + StudentCourse.TABLE
                + " INNER JOIN " + Course.TABLE + " ON StudentCourse." + StudentCourse.KEY_CourseId + " =  Course." + Course.KEY_CourseId
                + " GROUP BY Course." + Course.KEY_CourseId + ", Course." + Course.KEY_Name
                + " ORDER BY Course." + Course.KEY_Name;

        /*+ " GROUP BY Course." + Course.KEY_CourseId + ", Course." + Course.KEY_Name;*/

        System.out.println("Query:::" + selectQuery);

        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        Toast.makeText(mContext,"Count" + cursor.getCount(),Toast.LENGTH_LONG).show();

        // looping through all rows and adding to list

        // Toast.makeText()
        System.out.println("Query:::count" + cursor.getCount());


        if (cursor.moveToFirst()) {
            do {
                courseGradeCount= new CourseGradeCount();
                courseGradeCount.setCourseID(cursor.getString(cursor.getColumnIndex(Course.KEY_CourseId)));
                courseGradeCount.setCourseName(cursor.getString(cursor.getColumnIndex(Course.KEY_Name)));
                courseGradeCount.setGrade(cursor.getString(cursor.getColumnIndex(StudentCourse.KEY_Grade)));
/*
                courseGradeCount.setCount(cursor.getInt(cursor.getColumnIndex("Total")));
*/
                courseGradeCounts.add(courseGradeCount);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return courseGradeCounts;
    }

    //Show you the LEFT OUTER JOIN
//The data from LEFT will be shown even not all records matched
//In below query, Course will be the left table.
//and if you like to see all data from Course,
//remove this line WHERE RunningID IS NULL

    public List<CourseNotTakenByStudent> getCourseNotTakenByStudent(String studentId){

        CourseNotTakenByStudent courseNotTakenByStudent = new CourseNotTakenByStudent();
        List<CourseNotTakenByStudent> courseNotTakenByStudents = new ArrayList<CourseNotTakenByStudent>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery =  " SELECT Course." + Course.KEY_CourseId
                + ", Course." + Course.KEY_Name
                + ", StudentCourse." + StudentCourse.KEY_StudID
                + " FROM " + Course.TABLE
                + " LEFT JOIN " +StudentCourse.TABLE + " ON Course." + Course.KEY_CourseId + "=  StudentCourse." + StudentCourse.KEY_CourseId
                + " AND StudentCourse." + StudentCourse.KEY_StudID + "=?"
                + " WHERE RunningID IS NULL "
                ;

        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery,  new String[] { String.valueOf(studentId) });
        // looping through all rows and adding to list

        Toast.makeText(mContext,"Course Not Taken By Student" + cursor.getCount(),Toast.LENGTH_LONG).show();

        if (cursor.moveToFirst()) {
            do {
                courseNotTakenByStudent= new CourseNotTakenByStudent();
                courseNotTakenByStudent.setCourseID(cursor.getString(cursor.getColumnIndex(Course.KEY_CourseId)));
                courseNotTakenByStudent.setCourseName(cursor.getString(cursor.getColumnIndex(Course.KEY_Name)));
                courseNotTakenByStudents.add(courseNotTakenByStudent);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return courseNotTakenByStudents;

    }

    //Show you update IN SINGLE statement with multiple tables
    public void failALLBUStudent(){

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String selectQuery =  " UPDATE  StudentCourse " +
                "SET Grade= (SELECT 'F' FROM Student WHERE Student.StudentId=StudentCourse.StudentId) " +
                "WHERE EXISTS( " +
                "SELECT * " +
                "FROM Student " +
                "WHERE StudentCourse.StudentId=Student.StudentId AND MajorId='BU' " +
                ") "
                ;

        try{
            db.beginTransaction();

            Log.d(TAG, selectQuery);
            db.execSQL(selectQuery);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }finally {
            db.endTransaction();
        }

        DatabaseManager.getInstance().closeDatabase();
    }

    //Show you DELETE 2 statement execute in TRANSACTIONS
//The reason we use TRANSACTIONS is because we need to make
//sure data will delete properly so that if anything
//happened during the execution of the statement is either COMPLETE or ROLLBACK
//means either both tables data deleted or both tables not delete at all
    public void deleteAllBUStudent(){

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String selectQuery1 =
                " DELETE FROM StudentCourse WHERE StudentId IN (SELECT StudentId FROM Student WHERE MajorId='BU'); ";

        String selectQuery2 =
                " DELETE FROM Student WHERE MajorId='BU';";

        try{

            db.beginTransaction();

            Log.d(TAG, selectQuery1);
            Log.d(TAG, selectQuery2);
            db.execSQL(selectQuery1);
            db.execSQL(selectQuery2);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }finally {
            db.endTransaction();
        }

        DatabaseManager.getInstance().closeDatabase();

    }
}