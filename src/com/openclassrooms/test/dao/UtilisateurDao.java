package com.openclassrooms.test.dao;

import java.util.List;

import com.openclassrooms.test.beans.Utilisateur;

public interface UtilisateurDao {
	void ajouter( Utilisateur utilisateur ) throws DaoException;
    List<Utilisateur> lister() throws DaoException;
}
