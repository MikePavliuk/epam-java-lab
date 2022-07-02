package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.Publication;

import java.util.List;

public interface PublicationRepository {
	Publication getPublication(long id);

	List<Publication> getAllPublications();

	Publication createPublication(Publication publication);

	Publication updatePublication(long id, Publication publication);

	void deletePublication(long id);
}
