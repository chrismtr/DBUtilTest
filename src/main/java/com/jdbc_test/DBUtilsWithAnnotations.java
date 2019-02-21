package com.jdbc_test;

import com.jdbc_test.annotations.DBColumn;
import com.jdbc_test.annotations.DBTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.StringJoiner;

public class DBUtilsWithAnnotations {

    public static void main(String[] args) throws Exception {

        Connection connection = DBUtilsTest.getConnection();

        Class objClass = Car.class;

        Field[] declaredFields = objClass.getDeclaredFields();
        ComplexMappingItem[] complexMappingItems = new ComplexMappingItem[0];

        for (Field field : declaredFields) {
            DBColumn annotation = field.getAnnotation(DBColumn.class);

            if (annotation != null) {
                complexMappingItems = Arrays.copyOf(complexMappingItems, complexMappingItems.length + 1);

                int lastIndex = complexMappingItems.length - 1;
                complexMappingItems[lastIndex] =
                        new ComplexMappingItem(field.getName(), annotation.name());
                //System.out.println("column = " + annotation.name());
            }
        }

        StringJoiner columnNameJoiner = new StringJoiner(",");
        for (ComplexMappingItem item : complexMappingItems) {

            columnNameJoiner.add(item.getColumnName());
        }
        //System.out.println(columnNameJoiner.toString());

        Annotation classAnnotation = objClass.getAnnotation(DBTable.class);
        if (classAnnotation != null && complexMappingItems.length > 0) {
            String tableName = ((DBTable) classAnnotation).name();
            System.out.println("table = " + tableName);

            String query = "SELECT " + columnNameJoiner.toString() + " FROM " + tableName;

            System.out.println(query);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Object o = objClass.newInstance();
                for (ComplexMappingItem item : complexMappingItems) {
                    Field field = o.getClass().getDeclaredField(item.getFieldName());
                    field.setAccessible(true);
                    field.set(o, resultSet.getString(item.getColumnName()));
                }
                System.out.println(o);
            }
        }
        connection.close();
    }
}

class ComplexMappingItem {

    private String columnName;
    private String fieldName;

    public ComplexMappingItem(String columnName, String fieldName) {
        this.columnName = columnName;
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "ComplexMappingItem{" +
                "columnName='" + columnName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
