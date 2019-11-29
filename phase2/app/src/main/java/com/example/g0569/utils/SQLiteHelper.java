package com.example.g0569.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** The helper to operate SQLite. */
public class SQLiteHelper extends SQLiteOpenHelper {

  private static int version = 1;

  /**
   * Instantiates a new SQLite helper.
   *
   * @param context the context
   * @param name the name of the table
   */
  public SQLiteHelper(Context context, String name) {
    super(context, name, null, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String createNpcData =
            "CREATE TABLE if not exists npc_data"
                    + "("
                    + "    npc_id       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + "    npc_name     TEXT NOT NULL,"
                    + "    damage       INTEGER NOT NULL default 0,"
                    + "    power        TEXT,"
                    + "    chess_layout TEXT,"
                    + "    difficulty   TEXT  NOT NULL,"
                    + "    type         TEXT"
                    + ");";
    db.execSQL(createNpcData);


    String createUsers =
            "CREATE TABLE if not exists users ("
                    + "  uid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + "  username TEXT NOT NULL,"
                    + "  password TEXT NOT NULL,"
                    + "  email TEXT NOT NULL,"
                    + "  created_time DATE NOT NULL"
                    + ");";
    db.execSQL(createUsers);

    String createUserSaves =
            "CREATE TABLE if not exists users_saves"
                    + "("
                    + "    save_id        INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + "    uid            INTEGER NOT NULL,"
                    + "    level1_data    TEXT,"
                    + "    level2_data    TEXT,"
                    + "    level3_data    TEXT,"
                    + "    inventory_data TEXT,"
                    + "    created_time   TEXT    NOT NULL,"
                    + "    progress       INTEGER default 0 NOT NULL"
                    + ");";
    db.execSQL(createUserSaves);

    List<String> testDataList =
            new ArrayList<>(
                    Arrays.asList(
                            "INSERT INTO npc_data (npc_id, npc_name, damage, power, chess_layout, difficulty, type) VALUES (1, 'test_npc_1', 10, 'fire', 'star,1,3', 'easy', 'type1');",
                            "INSERT INTO npc_data (npc_id, npc_name, damage, power, chess_layout, difficulty, type) VALUES (2, 'test_npc_2', 10, 'fire', 'triangle,1,3', 'easy', 'type2');",
                            "INSERT INTO npc_data (npc_id, npc_name, damage, power, chess_layout, difficulty, type) VALUES (3, 'test_npc_3', 15, 'fire', 'triangle,1,3.star.2,3', 'hard', 'type3');",
                            "INSERT INTO npc_data (npc_id, npc_name, damage, power, chess_layout, difficulty, type) VALUES (4, 'test_npc_4', 15, 'fire', 'triangle,1,3.star,1,4', 'hard', 'type4');",
                            "INSERT INTO npc_data (npc_id, npc_name, damage, power, chess_layout, difficulty, type) VALUES (5, 'test_npc_5', 20, 'fire', 'star,1,3.circle,2,3.circle,2,4', 'insane', 'type5');",
                            "INSERT INTO npc_data (npc_id, npc_name, damage, power, chess_layout, difficulty, type) VALUES (6, 'test_npc_6', 20, 'fire', 'star,1,3.triangle,2,3.circle,2,4', 'insane', 'type6');"));
    for (String testData :testDataList ) {
      db.execSQL(testData);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
