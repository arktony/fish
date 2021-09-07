package org.ark.jdbc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class reportGeneralSelect implements Serializable {

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public ArrayList<ArrayList<String>> reportSelect(String query) {

        ArrayList<ArrayList<String>> item = new ArrayList<>();

        try {
           
            connection = C3P0DataSource.getInstance().getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            while (resultSet.next()) {

                ArrayList<String> itemss = new ArrayList<>();

                for (int i = 0; i < numberOfColumns; i++) {
                    if (resultSet.getString(i + 1) != null) {
                        itemss.add(resultSet.getString(i + 1));
                    } else if (resultSet.getString(i + 1) == null) {
                        itemss.add("");
                    }

                }
                item.add(itemss);

            }

            connection.close();
            statement.close();
            statement.close();

        } catch (SQLException ex) {

            reportInsert("insert into errorlogs(name) values('"+ex+"')");
            Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                connection.close();
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return item;

    }

    public boolean reportInsert(String query) {

        boolean item = false;

        try {
            connection = C3P0DataSource.getInstance().getConnection();
            statement = connection.createStatement();

            if (statement.executeUpdate(query) > -1) {

                item = true;

            };

        } catch (SQLException ex) {
            
            Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
                statement.close();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return item;
    }

    public boolean reportInsertTransaction(ArrayList<String> query) {

        boolean item = false;

        try {
            connection = C3P0DataSource.getInstance().getConnection();

            statement = connection.createStatement();

            statement.executeUpdate("start transaction;");

            for (int i = 0; i < query.size(); i++) {
                if (statement.executeUpdate(query.get(i)) > -1) {

                    item = true;

                };
            }
            
            statement.executeUpdate("commit;");

        } catch (SQLException ex) {
            Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
                statement.close();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return item;
    }

    public String reportSelectxx(String query) {

        String getton = "";
        try {
            connection = C3P0DataSource.getInstance().getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if(resultSet.getString(1)!=null){
                getton = resultSet.getString(1);
                }else{
                getton = "";
                }
                
            }

            connection.close();
            statement.close();
            statement.close();

        } catch (SQLException ex) {

            Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
                statement.close();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return getton;
    }

    public void reportSelectxxx(String query) {

        try {
            connection = C3P0DataSource.getInstance().getConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                //getton=resultSet.getBytes(1);

                InputStream in = resultSet.getBinaryStream(1);
                OutputStream f = new FileOutputStream(new File("test" + 2 + ".jpg"));

                int c = 0;
                while ((c = in.read()) > -1) {
                    f.write(c);
                }
                f.close();
                in.close();
            }

            connection.close();
            statement.close();
            statement.close();

        } catch (SQLException ex) {

            Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
                statement.close();
                statement.close();
            } //return getton;
            catch (SQLException ex) {
                Logger.getLogger(reportGeneralSelect.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    
}
