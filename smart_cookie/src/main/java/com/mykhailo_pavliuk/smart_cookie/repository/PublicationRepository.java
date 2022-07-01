package com.mykhailo_pavliuk.smart_cookie.repository;

import com.mykhailo_pavliuk.smart_cookie.model.Publication;

import java.util.List;

public interface PublicationRepository {
	Publication getPublication(Long id);

	List<Publication> getAllPublications();

	Publication createPublication(Publication publication);

	Publication updatePublication(Long id, Publication publication);

	void deletePublication(Long id);
}
