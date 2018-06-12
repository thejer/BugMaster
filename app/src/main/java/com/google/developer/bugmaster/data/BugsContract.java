package com.google.developer.bugmaster.data;

import android.provider.BaseColumns;

public class BugsContract {

    public static final class BugsEntry implements BaseColumns {

        public static final String TABLE_NAME = "bugs",
                COLUMN_FRIENDLY_NAME = "friendlyName",
                COLUMN_SCIENTIFIC_NAME = "scientificName",
                COLUMN_CLASSIFICATION = "classification",
                COLUMN_IMAGE_ASSET = "image",
                COLUMN_DANGER_LEVEL = "dangerLevel";


    }

}
