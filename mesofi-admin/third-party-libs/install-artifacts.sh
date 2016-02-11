#!/bin/bash

# content of your script

clear
echo "Installing third party dependencies..."

# The following libraries are missing drivers for database connection.
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=0.1 -Dpackaging=jar -Dfile=db-drivers/Oracle/ojdbc14.jar -DgeneratePom=true
mvn install:install-file -DgroupId=com.sqlserver -DartifactId=sqljdbc4 -Dversion=0.1 -Dpackaging=jar -Dfile=db-drivers/SqlServer/sqljdbc4.jar -DgeneratePom=true