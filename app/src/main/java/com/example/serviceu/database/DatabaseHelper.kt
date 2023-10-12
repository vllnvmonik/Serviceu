package com.example.serviceu.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val DROP_TABLE_USER = "DROP TABLE IF EXISTS $TABLE_USER"

    private val CREATE_TABLE_USER = ("CREATE TABLE $TABLE_USER(" +
            "$COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COL_USER_FIRSTNAME VARCHAR(30) NOT NULL, " +
            "$COL_USER_LASTNAME VARCHAR(30) NOT NULL, " +
            "$COL_USER_EMAIL TEXT NOT NULL, " +
            "$COL_USER_PHONE INTEGER NOT NULL, " +
            "$COL_USER_GENDER TEXT NOT NULL, " +
            "$COL_USER_ADDRESS TEXT NOT NULL, " +
            "$COL_USER_ROLE TEXT NOT NULL, " +
            "$COL_USER_CATEGORY TEXT NOT NULL, " +
            "$COL_USER_PASSWORD TEXT NOT NULL")

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DROP_TABLE_USER)
    }

    fun createAccount(user: User){
        val db = this.writableDatabase
        val value = ContentValues()
        value.put(COL_USER_FIRSTNAME, user.firstname)
        value.put(COL_USER_LASTNAME, user.lastname)
        value.put(COL_USER_EMAIL, user.email)
        value.put(COL_USER_PHONE, user.phone)
        value.put(COL_USER_GENDER, user.gender)
        value.put(COL_USER_ADDRESS, user.address)
        value.put(COL_USER_ROLE, user.role)
        value.put(COL_USER_CATEGORY, user.category)
        value.put(COL_USER_PASSWORD, user.password)

        db.insert(TABLE_USER, null, value)
        db.close()
    }

    fun loginAccount(email: String, password: String): Boolean{
        val col = arrayOf(COL_USER_ID)
        val db = this.readableDatabase
        val selection = "$COL_USER_EMAIL = ? AND $COL_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(TABLE_USER, col, selection, selectionArgs, null, null, null)
        val cursorCount = cursor.count
        cursor.close()
        db.close()

        return cursorCount > 0
    }

    fun checkEmail(email: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT $COL_USER_ID FROM $TABLE_USER WHERE $COL_USER_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        val emailExists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return emailExists
    }

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "login.db"
        private const val TABLE_USER = "tbl_user"

        private const val COL_USER_ID = "user_id"
        private const val COL_USER_FIRSTNAME = "user_firstname"
        private const val COL_USER_LASTNAME = "user_lastname"
        private const val COL_USER_EMAIL = "user_email"
        private const val COL_USER_PHONE = "user_phone"
        private const val COL_USER_GENDER = "user_gender"
        private const val COL_USER_ADDRESS = "user_address"
        private const val COL_USER_ROLE = "user_role"
        private const val COL_USER_CATEGORY = "user_category"
        private const val COL_USER_PASSWORD = "user_password"
    }
}


