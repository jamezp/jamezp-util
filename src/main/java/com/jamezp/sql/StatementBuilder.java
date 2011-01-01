/*
 * The MIT License
 *
 * Copyright 2011 James R. Perkins Jr (JRP).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.jamezp.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A simple statement builder to create SQL statements with.
 *
 * @author James R. Perkins (JRP)
 */
public final class StatementBuilder {

    private final Connection connection;

    private final List<Object> parameters;

    private final String sql;

    /**
     * Private constructor for singleton pattern.
     *
     * @param connection the connection to create the statement with.
     * @param sql        the SQL statement to create the statement for
     */
    private StatementBuilder(final Connection connection, final String sql) {
        this.connection = connection;
        parameters = new ArrayList<Object>();
        this.sql = sql;
    }

    /**
     * Creates a new builder to build either a callable statement or a prepared
     * statement.
     *
     * @param connection the connection to create the statement with.
     * @param sql        the SQL statement to create the statement for
     *
     * @return a new statement builder.
     */
    public static StatementBuilder newBuilder(final Connection connection,
            final String sql) {
        // Validate the parameters
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null.");
        }
        if (sql == null || sql.trim().equals("")) {
            throw new IllegalArgumentException(
                    "The SQL cannot be null or blank.");
        }
        return new StatementBuilder(connection, sql);
    }

    /**
     * Adds a parameter to create the statement with.
     *
     * @param parameter the value for the parameter.
     *
     * @return the current statement builder.
     */
    public StatementBuilder addParameter(final Object parameter) {
        this.parameters.add(parameter);
        return this;
    }

    /**
     * Adds a collection of parameters to create the statement with.
     *
     * @param parameters the parameters to add.
     *
     * @return the current statement builder.
     */
    public StatementBuilder addAllParameters(final Collection<Object> parameters) {
        this.parameters.addAll(parameters);
        return this;
    }

    /**
     * Builds a callable statement with the parameters that were added.
     * <p>
     * Uses the {@link java.sql.ParameterMetaData} to determine which parameters
     * should be registered as output parameters.
     * </p>
     *
     * @return the callable statement that was built.
     */
    public CallableStatement buildCallableStatement() {
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(sql);
            if (!parameters.isEmpty()) {
                int index = 0;
                try {
                    for (Object value : parameters) {
                        index++;
                        callableStatement.setObject(index, value);
                        // Register any parameters as outcoming parameters
                        final ParameterMetaData parameterMetaData = callableStatement.getParameterMetaData();
                        final int mode = parameterMetaData.getParameterMode(index);
                        if (mode == ParameterMetaData.parameterModeInOut || mode == ParameterMetaData.parameterModeOut) {
                            callableStatement.registerOutParameter(index, parameterMetaData.getParameterType(index));
                        }
                    }
                } catch (SQLException e) {
                    final String message = String.format(
                            "The callable statement could be formed with the given values. SqlState: %s Parameter Index: %d Value: %s",
                            e.getSQLState(), index, parameters.get(index));
                    throw new IllegalStateException(message, e);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Could not create callable statement.", e);
        }
        return callableStatement;
    }

    /**
     * Builds a prepared statement with the parameters that were added.
     *
     * @return the prepared statement that was created.
     */
    public PreparedStatement buildPreparedStatement() {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            // Parameter number
            if (!parameters.isEmpty()) {
                int index = 0;
                try {
                    for (Object value : parameters) {
                        index++;
                        preparedStatement.setObject(index, value);
                    }

                } catch (SQLException e) {
                    final String message = String.format(
                            "The prepared statement could be formed with the given values. SqlState: %s Parameter Index: %d Value: %s",
                            e.getSQLState(), index, parameters.get(index));
                    throw new IllegalStateException(message, e);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Could not create prepared statement.", e);
        }
        return preparedStatement;
    }
}
