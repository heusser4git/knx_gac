package ch.ibw.knxgac.Model;

import java.io.Serializable;

public class Configuration implements Serializable {
    private Enum<Servertyp> dbServertyp;
    private String dbServer;
    private int dbServerPort = 0;
    private String dbName;
    private String dbUsername;
    private String dbPassword;
    private String csvOutputpath;

    public Configuration(Enum<Servertyp> dbServertyp, String dbServer, int dbServerPort, String dbName, String dbUsername, String dbPassword, String cvsOutputpath) {
        this.dbServertyp = dbServertyp;
        this.dbServer = dbServer;
        this.dbServerPort = dbServerPort;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.csvOutputpath = cvsOutputpath;
    }

    public Configuration() {
    }

    public Enum<Servertyp> getDbServertyp() {
        return dbServertyp;
    }

    public void setDbServertyp(Enum<Servertyp> dbServertyp) {
        this.dbServertyp = dbServertyp;
    }

    public String getDbServer() {
        return dbServer;
    }

    public void setDbServer(String dbServer) {
        this.dbServer = dbServer;
    }
    public int getDbServerPort() {
        return dbServerPort;
    }

    public void setDbServerPort(int dbServerPort) {
        this.dbServerPort = dbServerPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getCsvOutputpath() {
        return csvOutputpath;
    }

    public void setCsvOutputpath(String csvOutputpath) {
        this.csvOutputpath = csvOutputpath;
    }



    @Override
    public String toString() {
        return "DB-Server: " + this.dbServer + "; DB-Name: " + this.dbName + "; DB-User: " + this.dbUsername + "; DB-Pwd: " + this.dbPassword + "; CSV-Outputpath: " + this.csvOutputpath;
    }


    /**
     * Checks if the configuration data are filled in complete
     *
     * @return true = all good, false = not everything is filled in
     */
    public boolean configComplete() {
        if(this.dbServertyp == null || this.dbServertyp.name().length()<1) {
            return false;
        }
        if(this.getDbServer() == null || this.getDbServer().length()<1) {
            return false;
        }
        if(!(this.getDbServerPort()>0)) {
            return false;
        }
        if(this.getDbName()==null || this.getDbName().length()<1) {
            return false;
        }
        if(this.getDbUsername()==null || this.getDbUsername().length()<1) {
            return false;
        }
        if(this.getDbPassword()==null || this.getDbPassword().length()<1) {
            return false;
        }
        if(this.getCsvOutputpath()==null || this.getCsvOutputpath().length()<1) {
            return false;
        }
        return true;
    }
}
