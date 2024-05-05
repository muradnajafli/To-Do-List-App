package com.muradnajafli.todolistapp.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.muradnajafli.todolistapp.utils.DbConstants.DATABASE_NAME
import com.muradnajafli.todolistapp.utils.DbConstants.DATABASE_VERSION
import com.muradnajafli.todolistapp.utils.DbConstants.TASK_ID
import com.muradnajafli.todolistapp.utils.DbConstants.TASK_IS_COMPLETED
import com.muradnajafli.todolistapp.utils.DbConstants.TASK_NAME
import com.muradnajafli.todolistapp.utils.DbConstants.TASK_TABLE_NAME
import com.muradnajafli.todolistapp.utils.DbConstants.TODO_ID
import com.muradnajafli.todolistapp.utils.DbConstants.TODO_NAME
import com.muradnajafli.todolistapp.utils.DbConstants.TODO_TABLE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DbHelper @Inject constructor(
    @ApplicationContext context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            CREATE TABLE $TODO_TABLE_NAME (
                $TODO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $TODO_NAME TEXT
            )
            """
        )

        db?.execSQL(
            """
            CREATE TABLE $TASK_TABLE_NAME (
                $TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $TASK_NAME TEXT,
                $TASK_IS_COMPLETED INTEGER,
                $TODO_ID INTEGER,
                FOREIGN KEY($TODO_ID) REFERENCES $TODO_TABLE_NAME($TODO_ID)
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TODO_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TASK_TABLE_NAME")
        onCreate(db)
    }
}