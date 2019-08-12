package com.royken.bracongo.bracongosc.database;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by vr.kenfack on 30/08/2017.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    public static void main(String[] args) throws SQLException, IOException {

        // Provide the name of .txt file which you have already created and kept in res/raw directory
        try {
            writeConfigFile("ormlite_config.txt");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
