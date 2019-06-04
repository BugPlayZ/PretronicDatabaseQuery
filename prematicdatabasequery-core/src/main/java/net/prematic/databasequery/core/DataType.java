/*
 * (C) Copyright 2019 The PrematicDatabaseQuery Project (Davide Wietlisbach & Philipp Elvin Friedhoff)
 *
 * @author Philipp Elvin Friedhoff
 * @since 07.05.19, 13:57
 *
 * The PrematicDatabaseQuery Project is under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package net.prematic.databasequery.core;

import java.util.HashMap;
import java.util.Map;

public enum DataType {

    DOUBLE,
    DECIMAL,
    FLOAT,
    INTEGER,
    LONG,
    CHAR,
    STRING,
    LONG_TEXT,
    DATE,
    DATETIME,
    TIMESTAMP,
    BINARY,
    BLOB,
    UUID;


    private final Class<?>[] javaClasses;

    DataType(Class... javaClasses) {
        this.javaClasses = javaClasses;
    }

    public Class[] getJavaClasses() {
        return javaClasses;
    }

    public static final Map<Class<?>, DataType> DATA_TYPES = new HashMap<>();

    static {
        for (DataType value : DataType.values()) {
            for (Class javaClass : value.getJavaClasses()) {
                DATA_TYPES.put(javaClass, value);
            }
        }
    }

    public static DataType getDataTypeByClass(Class<?> clazz) {
        return DATA_TYPES.get(clazz);
    }

}