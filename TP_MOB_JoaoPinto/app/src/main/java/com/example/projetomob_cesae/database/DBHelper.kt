package com.example.projetomob_cesae.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projetomob_cesae.model.CoursesModel
import com.example.projetomob_cesae.model.UserModel

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database.dbproj", null, 1) {

    val sql = arrayOf(
        "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)",
        "INSERT INTO users (username, password) VALUES ('admin','pass')",
        "INSERT INTO users (username, password) VALUES ('user','pwd')",
        "CREATE TABLE courses (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, local TEXT, dateBegin TEXT, dateEnd TEXT, price TEXT, duration TEXT, edition TEXT, imageId INT)",
        "INSERT INTO courses (name, local, dateBegin, dateEnd, price, duration, edition, imageId) VALUES ('Software Developer', 'Porto', '2023/10/02', '2024/06/11', 'Gratuito', '1000H', '2.º Edição', -2)",
        "INSERT INTO courses (name, local, dateBegin, dateEnd, price, duration, edition, imageId) VALUES ('Cyber Segurança - Vetores de Ataque e Métodos de Proteção', 'Leça da Palmeira', '2023/09/12', '2023/12/21', 'Gratuito', '200H', '1.º Edição', -1)",
        "INSERT INTO courses (name, local, dateBegin, dateEnd, price, duration, edition, imageId) VALUES ('Data Analyst (SQL)', 'Porto', '2023/09/18', '2023/11/14', 'Gratuito', '50H', '1.º Edição', -3)",
        "INSERT INTO courses (name, local, dateBegin, dateEnd, price, duration, edition, imageId) VALUES ('Excel Avançado', 'Porto', '2023/10/09', '2023/10/23', '165.00€', '15H', '4.º Edição', -4)",
        "INSERT INTO courses (name, local, dateBegin, dateEnd, price, duration, edition, imageId) VALUES ('Multimédia - Foto & Vídeo', 'Porto', '2024/06/03', '2025/01/30', 'Gratuito', '950H', '1.º Edição', -5)",
        "INSERT INTO courses (name, local, dateBegin, dateEnd, price, duration, edition, imageId) VALUES ('Business Intelligence c/ Power BI', 'Leça da Palmeira', '2024/10/10', '2024/01/30', 'Gratuito', '175H', '1.º Edição', -6)",
    )

    override fun onCreate(db: SQLiteDatabase) {
        sql.forEach {
            db.execSQL(it)
        }
    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //db.execSQL("DROP TABLE IF EXISTS cursos")
        //onCreate(db)
    }

    /*-------------------------------------------------------------------------------------------------------
                      CRUD USERS
  * _____________________________________________________________________________________________________*/


    fun insertUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val res = db.insert("users", null, contentValues)
        db.close()
        return res

    }

    fun updateUser(id: Int, username: String, password: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val res = db.update("users", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("users", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getUser(username: String, password: String): UserModel {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        var userModel = UserModel()
        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val usernameIndex = c.getColumnIndex("username")
            val passwordIndex = c.getColumnIndex("password")

            userModel = UserModel(
                id = c.getInt(idIndex), username = c.getString(usernameIndex),
                password = c.getString(passwordIndex)
            )

        }
        db.close()
        return userModel
    }

    fun login(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        var userModel = UserModel()
        if (c.count == 1) {
            db.close()
            return true
        } else
            db.close()
        return false
    }

    /*-------------------------------------------------------------------------------------------------------
                          CRUD COURSES
* _____________________________________________________________________________________________________*/

    fun insertCourse(
        name: String,
        local: String,
        dateBegin: String,
        dateEnd: String,
        price: String,
        duration: String,
        edition: String,
        imageId: Int
    ): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("local", local)
        contentValues.put("dateBegin", dateBegin)
        contentValues.put("dateEnd", dateEnd)
        contentValues.put("price", price)
        contentValues.put("duration", duration)
        contentValues.put("edition", edition)
        contentValues.put("imageId", imageId)
        val res = db.insert("courses", null, contentValues)
        db.close()
        return res
    }

    fun updateCourse(
        id: Int,
        name: String,
        local: String,
        dateBegin: String,
        dateEnd: String,
        price: String,
        duration: String,
        edition: String,
        imageId: Int
    ): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("local", local)
        contentValues.put("dateBegin", dateBegin)
        contentValues.put("dateEnd", dateEnd)
        contentValues.put("price", price)
        contentValues.put("duration", duration)
        contentValues.put("edition", edition)
        contentValues.put("imageId", imageId)
        val res = db.update("courses", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deleteCourse(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("courses", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }


    fun getCourse(id: Int): CoursesModel {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM courses WHERE id = ?", arrayOf(id.toString()))
        var courseModel = CoursesModel()

        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val localIndex = c.getColumnIndex("local")
            val dateBeginIndex = c.getColumnIndex("dateBegin")
            val dateEndIndex = c.getColumnIndex("dateEnd")
            val priceIndex = c.getColumnIndex("price")
            val durationIndex = c.getColumnIndex("duration")
            val editionIndex = c.getColumnIndex("edition")
            val imageIdIndex = c.getColumnIndex("imageId")


            courseModel = CoursesModel(
                id = c.getInt(idIndex),
                name = c.getString(nameIndex),
                local = c.getString(localIndex),
                dateBegin = c.getString(dateBeginIndex),
                dateEnd = c.getString(dateEndIndex),
                price = c.getString(priceIndex),
                duration = c.getString(durationIndex),
                edition = c.getString(editionIndex),
                imageId = c.getInt(imageIdIndex)
            )
        }

        db.close()
        return courseModel
    }

    fun getAllCourse(): ArrayList<CoursesModel> {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM courses", null)
        var listCourseModel = ArrayList<CoursesModel>()

        if (c.count > 0) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val localIndex = c.getColumnIndex("local")
            val dateBeginIndex = c.getColumnIndex("dateBegin")
            val dateEndIndex = c.getColumnIndex("dateEnd")
            val priceIndex = c.getColumnIndex("price")
            val durationIndex = c.getColumnIndex("duration")
            val editionIndex = c.getColumnIndex("edition")
            val imageIdIndex = c.getColumnIndex("imageId")
            do {
                val courseModel = CoursesModel(
                    id = c.getInt(idIndex),
                    name = c.getString(nameIndex),
                    local = c.getString(localIndex),
                    dateBegin = c.getString(dateBeginIndex),
                    dateEnd = c.getString(dateEndIndex),
                    price = c.getString(priceIndex),
                    duration = c.getString(durationIndex),
                    edition = c.getString(editionIndex),
                    imageId = c.getInt(imageIdIndex)
                )
                listCourseModel.add(courseModel)
            } while (c.moveToNext())
        }
        db.close()
        return listCourseModel
    }

    fun getAllCourseSortedByDate(): List<CoursesModel> {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM courses ORDER BY dateBegin", null)
        var listCourseModel = ArrayList<CoursesModel>()

        if (c.count > 0) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val localIndex = c.getColumnIndex("local")
            val dateBeginIndex = c.getColumnIndex("dateBegin")
            val dateEndIndex = c.getColumnIndex("dateEnd")
            val priceIndex = c.getColumnIndex("price")
            val durationIndex = c.getColumnIndex("duration")
            val editionIndex = c.getColumnIndex("edition")
            val imageIdIndex = c.getColumnIndex("imageId")
            do {
                val courseModel = CoursesModel(
                    id = c.getInt(idIndex),
                    name = c.getString(nameIndex),
                    local = c.getString(localIndex),
                    dateBegin = c.getString(dateBeginIndex),
                    dateEnd = c.getString(dateEndIndex),
                    price = c.getString(priceIndex),
                    duration = c.getString(durationIndex),
                    edition = c.getString(editionIndex),
                    imageId = c.getInt(imageIdIndex)
                )
                listCourseModel.add(courseModel)
            } while (c.moveToNext())
        }
        db.close()
        return listCourseModel
    }
}