/*
 * (C) Copyright 2019 The PrematicDatabaseQuery Project (Davide Wietlisbach & Philipp Elvin Friedhoff)
 *
 * @author Philipp Elvin Friedhoff
 * @since 07.05.19, 13:58
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

public class ForeignKey {

    private final String collection, field;
    //private final boolean deleteCascade;
    private Option delete, update;

    public ForeignKey(String collection, String field, boolean deleteCascade) {
        this.collection = collection;
        this.field = field;
        //this.deleteCascade = deleteCascade;
    }

    public ForeignKey(String collection, String field) {
        this(collection, field, false);
    }

    public String getCollection() {
        return collection;
    }

    public String getField() {
        return field;
    }

    /*
    public boolean isDeleteCascade() {
        return deleteCascade;
    }

     */
    public enum Option {

        DEFAULT,
        CASCADE,
        SET_NULL

    }
}
