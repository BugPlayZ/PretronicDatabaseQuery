/*
 * (C) Copyright 2019 The PrematicDatabaseQuery Project (Davide Wietlisbach & Philipp Elvin Friedhoff)
 *
 * @author Philipp Elvin Friedhoff
 * @since 03.05.19, 23:39
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

import net.prematic.databasequery.core.aggregation.AggregationBuilder;
import net.prematic.databasequery.core.query.*;

import java.util.Collection;

public interface DatabaseCollection {

    Database getDatabase();

    String getName();

    Type getType();

    long getSize();

    InsertQuery insert();

    FindQuery find();

    UpdateQuery update();

    ReplaceQuery replace();

    DeleteQuery delete();

    void drop();

    void truncate();

    QueryTransaction transact();

    AggregationBuilder newAggregationBuilder(boolean aliasAble);

    Collection<DatabaseCollectionField> getFields();

    boolean hasField(String name);

    DatabaseCollectionField getField(String name);

    void addField(DatabaseCollectionField field);

    void removeField(String field);

    default void removeField(DatabaseCollectionField field) {
        removeField(field.getName());
    }

    enum Type {

        NORMAL,
        EDGE
    }
}