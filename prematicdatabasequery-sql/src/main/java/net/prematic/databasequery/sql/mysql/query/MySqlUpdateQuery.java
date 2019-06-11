/*
 * (C) Copyright 2019 The PrematicDatabaseQuery Project (Davide Wietlisbach & Philipp Elvin Friedhoff)
 *
 * @author Philipp Elvin Friedhoff
 * @since 26.05.19, 16:01
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

package net.prematic.databasequery.sql.mysql.query;

import net.prematic.databasequery.core.impl.QueryOperator;
import net.prematic.databasequery.core.impl.query.AbstractUpdateQuery;
import net.prematic.databasequery.core.impl.query.QueryEntry;
import net.prematic.databasequery.core.impl.query.QueryStringBuildAble;
import net.prematic.databasequery.core.query.result.QueryResult;
import net.prematic.databasequery.sql.mysql.MySqlDatabaseCollection;
import net.prematic.databasequery.sql.mysql.MySqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MySqlUpdateQuery extends AbstractUpdateQuery implements QueryStringBuildAble {

    private String queryString;

    public MySqlUpdateQuery(MySqlDatabaseCollection collection) {
        super(collection);
    }

    @Override
    public QueryResult execute(Object... values) {
        try(Connection connection = ((MySqlDatabaseCollection)getCollection()).getDatabase().getDriver().getConnection()) {
            AtomicInteger index = new AtomicInteger(0);
            for (QueryEntry queryEntry : getEntries()) {
                queryEntry.getValues().clear();
                MySqlUtils.prepareQueryEntry(queryEntry, index, values);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(buildExecuteString());
            List<QueryEntry> queryEntries = new ArrayList<>(getEntries());
            queryEntries.sort(Comparator.comparingInt(queryEntry -> MySqlUtils.getQueryOperatorPriority(queryEntry.getOperator())));
            index.set(1);
            for (QueryEntry queryEntry : queryEntries) {
                for (Object value : queryEntry.getValuesDeep()) {
                    preparedStatement.setObject(index.getAndIncrement(), value);
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public String buildExecuteString(boolean rebuild) {
        if(!rebuild && this.queryString != null) return this.queryString;
        StringBuilder queryString = new StringBuilder();
        queryString.append("UPDATE `").append(((MySqlDatabaseCollection)getCollection()).getDatabase().getName())
                .append("`.`").append(getCollection().getName()).append("` SET ");
        List<QueryEntry> queryEntries = new ArrayList<>(getEntries());
        queryEntries.sort(Comparator.comparingInt(queryEntry -> MySqlUtils.getQueryOperatorPriority(queryEntry.getOperator())));
        boolean first = true;
        AtomicInteger index = new AtomicInteger(1);
        for (QueryEntry queryEntry : queryEntries) {
            if(queryEntry.getOperator() == QueryOperator.SET) {
                if(!first) queryString.append(",");
                else first = false;
                queryString.append("`").append(queryEntry.getData("field")).append("`=?");
            }
        }
        MySqlUtils.buildSearchQuery(queryString, queryEntries);
        this.queryString = queryString.append(";").toString();
        return this.queryString;
    }
}
