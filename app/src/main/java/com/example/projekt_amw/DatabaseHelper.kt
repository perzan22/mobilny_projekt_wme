package com.example.projekt_amw

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context):  SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABSE_VERSION) {

    companion object {
        private const val DATABSE_VERSION = 1
        private const val DATABASE_NAME = "AMW.db"

        // Tabela aktualnosci
        private const val TABLE_NEWS = "aktualnosci"
        private const val NEWS_ID = "_id"
        private const val NEWS_TITLE = "tytul"
        private const val NEWS_CONTENT = "trsc"

        // Tabela kierunki
        private const val TABELA_KIERUNKI = "kierunki"
        private const val KIERUNKI_ID = "_id"
        private const val KIERUNKI_NAZWA = "nazwa"
        private const val KIERUNKI_OPIS = "opis"
        private const val KIERUNKI_ILOSC_ZAPISANYCH = "ilosc_zapisanych"

        // Tabela student
        private const val TABELA_STUDENT = "student"
        private const val STUDENT_ID = "_id"
        private const val STUDENT_IMIE = "imie"
        private const val STUDENT_NAZWISKO = "nazwisko"
        private const val STUDENT_KIERUNEK = "kierunek"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createNewsTableQuery = ("CREATE TABLE $TABLE_NEWS ($NEWS_ID INTEGER PRIMARY KEY, "
                + "$NEWS_TITLE TEXT, $NEWS_CONTENT TEXT)")
        db!!.execSQL(createNewsTableQuery)

        val aktualnosc1 = ContentValues()
        aktualnosc1.put(NEWS_TITLE, "Dni otwarte Akademii Marynarki Wojennej w Gdyni")
        aktualnosc1.put(NEWS_CONTENT, "Zapraszamy na Dzień Otwarty Akademii Marynarki Wojennej w Gdyni! " +
                "Już 26.06.2024 dołącz do nas, aby odkryć tajniki marynistyki, technologii morskich " +
                "i perspektyw zawodowych w marynarce wojennej. Spotkaj się z naszymi " +
                "kpodchorążymi i wykładowcami, zwiedź kampus nad Bałtykiem i doświadcz " +
                "dynamicznego świata AMW.")
        db.insert(TABLE_NEWS, null, aktualnosc1)

        val aktualnosc2 = ContentValues()
        aktualnosc2.put(NEWS_TITLE, "Nasi studenci zostali laureatami konkursu")
        aktualnosc2.put(NEWS_CONTENT, "Studenci Wydziału Mechaniczno-Elektrycznego Akademii Marynarki Wojennej " +
                "zostali laureatami prestiżowego konkursu za inżynierski projekt dla marynarki wojennej. " +
                "Gratulujemy naszym studentom za ich wybitne osiągnięcia i innowacyjne podejście!")
        db.insert(TABLE_NEWS, null, aktualnosc2)

        val createKierunkiTableQuery = ("CREATE TABLE $TABELA_KIERUNKI ($KIERUNKI_ID INTEGER PRIMARY KEY, "
                + "$KIERUNKI_NAZWA TEXT, $KIERUNKI_OPIS TEXT, $KIERUNKI_ILOSC_ZAPISANYCH INTEGER)")
        db.execSQL(createKierunkiTableQuery)

        val informatyka = ContentValues()
        informatyka.put(KIERUNKI_NAZWA, "Informatyka")
        informatyka.put(KIERUNKI_OPIS, "Informatyka na Akademii Marynarki Wojennej w Gdyni to nowoczesny kierunek " +
                "łączący zaawansowaną wiedzę techniczną z praktycznymi umiejętnościami. " +
                "Przygotowujemy studentów do pracy w dynamicznie rozwijającej się branży IT " +
                "i bezpieczeństwa cybernetycznego, z naciskiem na zastosowania morskie.")
        informatyka.put(KIERUNKI_ILOSC_ZAPISANYCH, 0)
        db.insert(TABELA_KIERUNKI, null, informatyka)

        val automatyka = ContentValues()
        automatyka.put(KIERUNKI_NAZWA, "Automatyka i robotyka")
        automatyka.put(KIERUNKI_OPIS, "Automatyka i Robotyka na Akademii Marynarki Wojennej " +
                "to kierunek kształcący specjalistów w dziedzinie " +
                "zaawansowanych systemów sterowania i robotyki. " +
                "Program łączy teoretyczne podstawy z praktycznymi " +
                "projektami, przygotowując studentów do pracy w nowoczesnych " +
                "technologiach i automatyzacji procesów.")
        automatyka.put(KIERUNKI_ILOSC_ZAPISANYCH, 0)
        db.insert(TABELA_KIERUNKI, null, automatyka)

        val mechatronika = ContentValues()
        mechatronika.put(KIERUNKI_NAZWA, "Mexchatronika")
        mechatronika.put(KIERUNKI_OPIS, "Mechatronika na Akademii Marynarki Wojennej to interdyscyplinarny " +
                "kierunek, który łączy inżynierię mechaniczną, elektryczną i komputerową. " +
                "Studenci zdobywają wiedzę na temat projektowania i obsługi nowoczesnych " +
                "systemów mechatronicznych, z naciskiem na zastosowania wojskowe i morskie.")
        mechatronika.put(KIERUNKI_ILOSC_ZAPISANYCH, 0)
        db.insert(TABELA_KIERUNKI, null, mechatronika)

        val mechanika = ContentValues()
        mechanika.put(KIERUNKI_NAZWA, "Mechanika i budowa maszyn")
        mechanika.put(KIERUNKI_OPIS, "Mechanika i Budowa Maszyn na Akademii Marynarki Wojennej " +
                "to kierunek koncentrujący się na projektowaniu, analizie " +
                "i eksploatacji maszyn i urządzeń. Program oferuje solidne " +
                "podstawy teoretyczne oraz praktyczne umiejętności, " +
                "niezbędne do pracy w przemyśle i wojskowych jednostkach technicznych.")
        mechanika.put(KIERUNKI_ILOSC_ZAPISANYCH, 0)
        db.insert(TABELA_KIERUNKI, null, mechanika)

        val createStudentTableQuery = ("CREATE TABLE $TABELA_STUDENT ($STUDENT_ID INTEGER PRIMARY KEY, "
                + "$STUDENT_IMIE TEXT, $STUDENT_NAZWISKO TEXT, $STUDENT_KIERUNEK INTEGER)")
        db.execSQL(createStudentTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NEWS")
        db.execSQL("DROP TABLE IF EXISTS $TABELA_KIERUNKI")
        db.execSQL("DROP TABLE IF EXISTS $TABELA_STUDENT")
        onCreate(db)
    }

    fun insertStudent(student: StudentModel) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STUDENT_IMIE, student.imie)
        values.put(STUDENT_NAZWISKO, student.nazwisko)
        values.put(STUDENT_KIERUNEK, student.kierunek)
        db.insert(TABELA_STUDENT, null, values)

        val updateQuery = "UPDATE $TABELA_KIERUNKI SET $KIERUNKI_ILOSC_ZAPISANYCH = $KIERUNKI_ILOSC_ZAPISANYCH + 1 WHERE ID = ?"
        val statement = db.compileStatement(updateQuery)
        statement.bindLong(1, student.kierunek.toLong())
        statement.executeUpdateDelete()

        db.close()
    }

    @SuppressLint("Range", "Recycle")
    fun getAllNews(): List<AktualnoscModel> {
        val newsList: ArrayList<AktualnoscModel> = ArrayList<AktualnoscModel>()
        val selectQuery = "SELECT * FROM $TABLE_NEWS"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var newsID: Int
        var title: String
        var content: String

        if (cursor.moveToFirst()) {
            do {
                newsID = cursor.getInt(cursor.getColumnIndex(NEWS_ID))
                title = cursor.getString(cursor.getColumnIndex(NEWS_TITLE))
                content = cursor.getString(cursor.getColumnIndex((NEWS_CONTENT)))
                val news = AktualnoscModel(newsID = newsID, title = title, content = content)

                newsList.add(news)

            } while (cursor.moveToNext())
        }

        return newsList
    }

    @SuppressLint("Range", "Recycle")
    fun getAllKierunki(): List<KierunekModel> {
        val kierunkiList: ArrayList<KierunekModel> = ArrayList<KierunekModel>()
        val selectQuery = "SELECT * FROM $TABELA_KIERUNKI"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var kierunekID: Int
        var nazwa: String
        var opis: String
        var iloscZapisanych: Int

        if (cursor.moveToFirst()) {
            do {
                kierunekID = cursor.getInt(cursor.getColumnIndex(KIERUNKI_ID))
                nazwa = cursor.getString(cursor.getColumnIndex(KIERUNKI_NAZWA))
                opis = cursor.getString(cursor.getColumnIndex(KIERUNKI_OPIS))
                iloscZapisanych = cursor.getInt(cursor.getColumnIndex(KIERUNKI_ILOSC_ZAPISANYCH))
                val kierunek = KierunekModel(kierunekID = kierunekID, nazwa = nazwa, opis = opis, iloscZapisanych = iloscZapisanych)

                kierunkiList.add(kierunek)

            } while (cursor.moveToNext())
        }

        return kierunkiList
    }
}