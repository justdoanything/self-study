package book.pattern.structural.facade;

import java.sql.Connection;

public class FacadeHelper {
    public static enum DBTypes {
        MYSQL, ORACLE
    }

    public static enum ReportTypes {
        HTML, PDF
    }

    public static void generateReport(DBTypes dbTypes, ReportTypes reportTypes, String tableName) throws RuntimeException {
        Connection connection = null;
        switch (dbTypes) {
            case MYSQL:
                connection = MySqlHelper.getConnection();
                switch (reportTypes){
                    case HTML:
                        MySqlHelper.generateHtmlReport(tableName, connection);
                        break;
                    case PDF:
                        MySqlHelper.generatePdfReport(tableName, connection);
                        break;
                    default:
                        throw new RuntimeException("Report Type Error.");
                }
                break;
            case ORACLE:
                connection = OracleHelper.getConnection();
                switch (reportTypes){
                    case HTML:
                        OracleHelper.generateHtmlReport(tableName, connection);
                        break;
                    case PDF:
                        OracleHelper.generatePdfReport(tableName, connection);
                        break;
                    default:
                        throw new RuntimeException("Report Type Error.");
                }
                break;
            default:
                throw new RuntimeException("Database Type Error.");
        }
    }
}
