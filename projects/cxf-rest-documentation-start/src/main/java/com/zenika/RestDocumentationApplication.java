package com.zenika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication
public class RestDocumentationApplication {
	
	// TODO 04 Lancer l'application et aller sur http://localhost:8080/app/api-docs
	// avec un navigateur. La description du service doit apparaître.

	// TODO 05 Aller sur http://localhost:8080/app/api-docs/contacts
	// La documentation de la méthode contact doit apparaître.
	// Analyser cette documentation (URL, opération HTTP, paramètres, etc).
	// Regarder aussi la partie "models", qui décrit la structure d'un Contact.
	
	// TODO 06 Arrêter l'application
	
	// TODO 08 Lancer l'application et aller sur http://localhost:8080/app/api-docs/contacts
	// S'assurer que toutes les méthodes documentées apparaissent bien dans
	// la documentation.
	
	// TODO 09 Aller sur http://localhost:8080/swagger-ui/index.html
	// Swagger UI s'affiche.
	
	// TODO 10 Entrer http://localhost:8080/app/api-docs dans le premier champ
	// puis appuyer sur "Explore". La description du service apparait.
	
	// TODO 11 Cliquer sur "List Operations".
	
	// TODO 12 Cliquer sur "/contacts" puis "Try it out" pour lister les contacts
	// Les contacts doivent s'afficher.
	
	// TODO 13 Créer un contact en suivant les instructions ci-dessous
	//  - cliquer sur le bandeau de l'opération "POST /contacts"
	//  - cliquer sur le champ en-dessous de "Model Schema" pour avoir un modèle
	// de contact à créer
	//  - modifier le document JSON. Supprimer notamment le champ id. Par exemple :
	// {
	//   "firstname": "Goofy",
	//   "lastname": "Goof",
	//   "age": 25
	// }
	//  - cliquer sur "Try it out" pour créer le contact
	//  - analyser la réponse et récupérer l'identifiant du contact créé (le
	// nombre à la fin de l'URL dans l'entête Location)
	
	// TODO 14 Utiliser Swagger UI pour consulter le contact créé
	
	// TODO 15 Utiliser Swagger UI pour supprimer le contact créé
	
	// TODO 14 Analyser 
	
    public static void main(String[] args) {
        SpringApplication.run(RestDocumentationApplication.class, args);
    }

}
