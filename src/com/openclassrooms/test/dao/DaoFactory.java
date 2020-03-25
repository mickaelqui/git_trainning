package com.openclassrooms.test.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

public class DaoFactory {
    
    private static final String FICHIER_PROPERTIES       = "/com/openclassrooms/test/dao/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE    = "motdepasse";
    
	private static HikariConfig config = new HikariConfig();
    private static HikariPool connectionPool;

    DaoFactory(HikariPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static DaoFactory getInstance() throws DaoException{
    	
    	Properties properties = new Properties();
    	String url = null ;
        String driver  = null;
        String nomUtilisateur = null;
        String motDePasse = null;
        
        HikariPool connectionPool = null;
        
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

        if ( fichierProperties == null ) {
        	throw new DaoException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
        }
        
    	try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
            motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );
            config.setJdbcUrl( url );
            config.setUsername( nomUtilisateur );
            config.setPassword( motDePasse );
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
    	} catch ( FileNotFoundException e ) {
            throw new DaoException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.", e );
        } catch ( IOException e ) {
            throw new DaoException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
        }

        
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
        	throw new DaoException( "Le driver est introuvable dans le classpath.", e );
        }

        try {
            /*
             * Création d'une configuration de pool de connexions via l'objet
             * BoneCPConfig et les différents setters associés.
             */
            HikariConfig config = new HikariConfig();
            /* Mise en place de l'URL, du nom et du mot de passe */
            config.setJdbcUrl( url );
            config.setUsername( nomUtilisateur );
            config.setPassword( motDePasse );
            /* Paramétrage de la taille du pool */
            
            config.setAutoCommit(false);
            config.setMaximumPoolSize(20);
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
            /* Création du pool à partir de la configuration, via l'objet BoneCP */
            connectionPool = new HikariPool( config );
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new DaoException( "Erreur de configuration du pool de connexions.", e );
        }
        /*
         * Enregistrement du pool créé dans une variable d'instance via un appel
         * au constructeur de DAOFactory
         */
        DaoFactory instance = new DaoFactory( connectionPool );
        return instance;
    }

    public Connection getConnection() throws SQLException {
       
        return connectionPool.getConnection();
    }

    // R�cup�ration du Dao
    public UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDaoImpl(this);
    }
}
